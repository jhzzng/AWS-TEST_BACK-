package com.kosta.board.dto;

import java.sql.Date;

import com.kosta.board.entity.Article;
import com.kosta.board.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArticleDto {
	private Integer num;
	private String title;
	private String content;
	private Date createDate;
	private Integer viewcnt;
	private String imgFileName;
	private String fileName;
	private String writer;
	private String name;
	
	public Article toEntity() {
		return Article.builder()
				.num(num)
				.title(title)
				.content(content)
				.createDate(createDate)
				.viewcnt(viewcnt)
				.imgFileName(imgFileName)
				.fileName(fileName)
				.member(Member.builder().id(writer).name(name).build())
				.createDate(createDate)
				.build();
	}
}
