package com.kosta.board.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
public class BoardLike {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)	
	private Integer num;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="memberId")
	private Member member;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="articleNum")
	private Article article;
	
	@Override
	public String toString() {
		return String.format("(%s, %d)", member.getId(), article.getNum());
	}
}
