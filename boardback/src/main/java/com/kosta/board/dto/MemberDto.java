package com.kosta.board.dto;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;

import com.kosta.board.entity.Article;
import com.kosta.board.entity.BoardLike;
import com.kosta.board.entity.Member;

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
public class MemberDto {
	private String id;
	private String name;
	private String password;
	private String email;
	private String address;
	private String detailAddress;
	public Member toEntity() {
		return Member.builder()
				.id(id)
				.name(name)
				.password(password)
				.email(email)
				.address(address)
				.detailAddress(detailAddress).build();
	}
}
