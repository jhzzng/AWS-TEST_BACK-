package com.kosta.board.entity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import com.kosta.board.dto.ArticleDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@DynamicInsert
public class Article {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer num;

	@Column
	private String title;
	
	@Column(columnDefinition = "LONGTEXT")
	@Lob
	private String content;
	
	@Column
	@CreationTimestamp
	private Date createDate;
	
	@Column
	@ColumnDefault("0")
	private Integer viewcnt;
	
	@Column
	private String imgFileName;
	
	@Column
	private String fileName;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="writer")
	private Member member;
	
	@OneToMany(mappedBy = "article", fetch=FetchType.LAZY)
	private List<BoardLike> boardLikeList = new ArrayList<>();
	
	public ArticleDto toDto() {
		return ArticleDto.builder()
				.num(num)
				.title(title)
				.content(content)
				.viewcnt(viewcnt)
				.imgFileName(imgFileName)
				.fileName(fileName)
				.writer(member.getId())
				.name(member.getName())
				.createDate(createDate)
				.build();
	}

	@Override
	public String toString() {
		return "Article [num=" + num + ", title=" + title + ", content=" + content + ", createDate=" + createDate
				+ ", viewcnt=" + viewcnt + ", imgFileName=" + imgFileName + ", fileName=" + fileName + "]";
	}
	
	
}
