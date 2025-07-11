package com.kosta.board.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kosta.board.dto.ArticleDto;
import com.kosta.board.service.BoardService;
import com.kosta.board.util.PageInfo;

@RestController
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	@PostMapping("/write")
	public ResponseEntity<ArticleDto> write(ArticleDto articleDto, 
			@RequestParam(name="ifile", required=false) MultipartFile ifile,
			@RequestParam(name="dfile", required=false) MultipartFile dfile) {
		try {
			Integer articleNum = boardService.writeArticle(articleDto, ifile, dfile);
			ArticleDto nArticleDto = boardService.detailArticle(articleNum);
			return new ResponseEntity<>(nArticleDto, HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/modify")
	public ResponseEntity<ArticleDto> modify(ArticleDto articleDto, 
			@RequestParam(name="ifile", required=false) MultipartFile ifile,
			@RequestParam(name="dfile", required=false) MultipartFile dfile) {
		try {
			boardService.modifyArticle(articleDto, ifile, dfile);
			ArticleDto nArticleDto = boardService.detailArticle(articleDto.getNum());
			return new ResponseEntity<>(nArticleDto, HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
		
	
//	@GetMapping("/detail/{num}")
//	public ResponseEntity<ArticleDto> detail(@PathVariable Integer num) {
//	@GetMapping("/detail")
//	public ResponseEntity<ArticleDto> detail(@RequestParam("num") Integer num) {
	@PostMapping("/detail")
	public ResponseEntity<Map<String,Object>> detail(@RequestBody Map<String,String> param) {
		System.out.println("============");
		System.out.println(param);

		try {
			ArticleDto nArticleDto = boardService.detailArticle(Integer.parseInt(param.get("num")));
			Boolean isLike = boardService.getBoardLike(param.get("id"), Integer.parseInt(param.get("num")));
			Map<String,Object> res = new HashMap<>();
			res.put("board", nArticleDto);
			res.put("isLike", isLike);
			return new ResponseEntity<>(res, HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}	
	
	@PostMapping("/list")
	public ResponseEntity<Map<String,Object>> list(@RequestBody(required=false) Map<String,String> param) {
		String type = null;
		String word = null;	
		PageInfo pageInfo = new PageInfo(1);
		if(param != null) {
			if(param.get("page")!=null) {
				pageInfo.setCurPage(Integer.parseInt(param.get("page")));
			}
			type = param.get("type");
			word = param.get("word");
		}
		try {
			List<ArticleDto> articleList = boardService.searchArticleList(pageInfo, type, word);
			Map<String, Object> res = new HashMap<>();
			res.put("boardList", articleList);
			res.put("pageInfo", pageInfo);
			return new ResponseEntity<>(res, HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/like")
	public ResponseEntity<Boolean> boardLike(@RequestBody Map<String, String> param) {
		try {
			Boolean isLike = boardService.toggleBoardLike(param.get("id"), Integer.parseInt(param.get("num")));
			return new ResponseEntity<>(isLike, HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
}
