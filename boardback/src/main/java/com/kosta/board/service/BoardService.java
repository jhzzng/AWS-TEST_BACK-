package com.kosta.board.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.kosta.board.dto.ArticleDto;
import com.kosta.board.util.PageInfo;

public interface BoardService {
	Integer writeArticle(ArticleDto articleDto, MultipartFile ifile, MultipartFile dfile) throws Exception;
	ArticleDto detailArticle(Integer num) throws Exception;
	List<ArticleDto> searchArticleList(PageInfo pageInfo, String type, String word) throws Exception;
	void modifyArticle(ArticleDto articleDto, MultipartFile ifile, MultipartFile dfile) throws Exception;
	Boolean getBoardLike(String id, Integer num) throws Exception;
	Boolean toggleBoardLike(String id, Integer num) throws Exception;
}
