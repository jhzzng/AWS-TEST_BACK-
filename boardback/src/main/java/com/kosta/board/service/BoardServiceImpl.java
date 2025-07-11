package com.kosta.board.service;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kosta.board.dto.ArticleDto;
import com.kosta.board.entity.Article;
import com.kosta.board.entity.BoardLike;
import com.kosta.board.entity.Member;
import com.kosta.board.repository.ArticleRepository;
import com.kosta.board.repository.BoardLikeRepository;
import com.kosta.board.util.PageInfo;

@Service
public class BoardServiceImpl implements BoardService {
	@Autowired
	EntityManager entityManager;

	@Autowired
	private ArticleRepository articleRepository;

	@Autowired
	private BoardLikeRepository boardLikeRepository;

	@Value("${iupload.path}")
	private String iuploadPath;

	@Value("${dupload.path}")
	private String duploadPath;
	
	@Override
	@Transactional
	public Integer writeArticle(ArticleDto articleDto, MultipartFile ifile, MultipartFile dfile) throws Exception {
		if(ifile!=null && !ifile.isEmpty()) {
			articleDto.setImgFileName(ifile.getOriginalFilename());
			File upFile = new File(iuploadPath,articleDto.getImgFileName());
			ifile.transferTo(upFile);
		}
		if(dfile!=null && !dfile.isEmpty()) {
			articleDto.setFileName(dfile.getOriginalFilename());
			File upFile = new File(duploadPath, articleDto.getFileName());
			dfile.transferTo(upFile);
		}
		Article article = articleDto.toEntity();
		articleRepository.save(article);
		Integer articleNum = article.getNum();
		entityManager.clear();
		return articleNum;
	}

	@Override
	public ArticleDto detailArticle(Integer num) throws Exception {
		System.out.println(num);
		Article article = articleRepository.findById(num).orElseThrow(()->new Exception("글번호 오류"));
		System.out.println(article.getMember());
		return article.toDto();
	}

	@Override
	public List<ArticleDto> searchArticleList(PageInfo pageInfo, String type, String word) throws Exception {
		PageRequest pageRequest = PageRequest.of(pageInfo.getCurPage()-1,10,Sort.by(Sort.Direction.DESC, "num"));
		Page<Article> pages = null;
		
		if(word==null || word.trim().equals("")) {
			pages = articleRepository.findAll(pageRequest);
		} else {
			if(type.equals("title")) {
				pages = articleRepository.findByTitleContains(word, pageRequest);
			} else if(type.equals("content")) {
				pages = articleRepository.findByContentContains(word, pageRequest);
			} else if(type.equals("writer")) {
				pages = articleRepository.findByMember_Name(word, pageRequest);
			}
		}
		pageInfo.setAllPage(pages.getTotalPages());
		Integer startPage = (pageInfo.getAllPage()-1)/10*10+1;
		Integer endPgae = Math.min(startPage+10-1, pageInfo.getAllPage());
		pageInfo.setStartPage(startPage);
		pageInfo.setEndPage(endPgae);
		return pages.getContent().stream().map(a->a.toDto()).collect(Collectors.toList());
	}

	@Override
	public void modifyArticle(ArticleDto articleDto, MultipartFile ifile, MultipartFile dfile) throws Exception {
		Article article = articleRepository.findById(articleDto.getNum()).orElseThrow(()->new Exception("게시글번호오류"));

		article.setTitle(articleDto.getTitle());
		article.setContent(articleDto.getContent());

		if(ifile!=null && !ifile.isEmpty()) {
			article.setImgFileName(ifile.getOriginalFilename());
			File upFile = new File(iuploadPath, article.getImgFileName());
			ifile.transferTo(upFile);
		}
		if(dfile!=null && !dfile.isEmpty()) {
			article.setFileName(dfile.getOriginalFilename());
			File upFile = new File(duploadPath, article.getFileName());
			dfile.transferTo(upFile);
		}
		articleRepository.save(article);
	}

	@Override
	public Boolean getBoardLike(String id, Integer num) throws Exception {
		Optional<BoardLike>  oboardLike = boardLikeRepository.findByMember_IdAndArticle_Num(id, num);
		if(oboardLike.isPresent()) return true;
		return false;
	}

	@Override
	public Boolean toggleBoardLike(String id, Integer num) throws Exception {
		Optional<BoardLike>  oboardLike = boardLikeRepository.findByMember_IdAndArticle_Num(id, num);
		if(oboardLike.isEmpty()) {
			boardLikeRepository.save(BoardLike.builder()
										.member(Member.builder().id(id).build())
										.article(Article.builder().num(num).build())
										.build());
			return true;
		} else {
			boardLikeRepository.delete(oboardLike.get());
			return false;
		}
	}
}
