package com.kosta.board.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.kosta.board.dto.MemberDto;

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
public class Member {
	@Id
	private String id;
	
	@Column
	private String name;
	
	@Column
	private String password;
	
	@Column
	private String email;
	
	@Column
	private String address;
	
	@Column
	private String detailAddress;
	
	@OneToMany(mappedBy="member", fetch=FetchType.LAZY)
	private List<Article> articleList = new ArrayList<>();
	
	@OneToMany(mappedBy="member", fetch=FetchType.EAGER)
	private List<BoardLike> boardLikeList = new ArrayList<>();
	
	public MemberDto toDto() {
		return MemberDto.builder()
				.id(id)
				.name(name)
				.email(email)
				.address(address)
				.detailAddress(detailAddress).build();
	}

	@Override
	public String toString() {
		return "Member [id=" + id + ", name=" + name + ", password=" + password + ", email=" + email + ", address="
				+ address + ", detailAddress=" + detailAddress + "]";
	}

}
