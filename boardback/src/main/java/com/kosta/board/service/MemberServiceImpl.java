package com.kosta.board.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kosta.board.dto.MemberDto;
import com.kosta.board.entity.Member;
import com.kosta.board.repository.MemberRepository;

@Service
public class MemberServiceImpl implements MemberService {
	
	@Autowired
	private MemberRepository memberRepository;

	@Override
	public MemberDto login(String id, String password) throws Exception {
		Member member = memberRepository.findById(id).orElseThrow(()->new Exception("아이디오류"));
		if(!member.getPassword().equals(password)) throw new Exception("비밀번호오류");
		return member.toDto();
	}

	@Override
	public void join(MemberDto memberDto) throws Exception {
		Optional<Member> omember = memberRepository.findById(memberDto.getId());
		if(omember.isPresent()) throw new Exception("아이디 중복오류");
		memberRepository.save(memberDto.toEntity());
	}

	@Override
	public Boolean checkMemberId(String id) throws Exception {
		Optional<Member> omember = memberRepository.findById(id);
		if(omember.isPresent()) return true;
		return false;
	}
}
