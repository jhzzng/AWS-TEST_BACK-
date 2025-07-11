package com.kosta.board.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kosta.board.dto.MemberDto;
import com.kosta.board.service.MemberService;

@RestController
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	
	@PostMapping("/login")
	public ResponseEntity<MemberDto> login(@RequestBody Map<String,String> param) {
		try {
			MemberDto memberDto = memberService.login(param.get("id"), param.get("password"));
			memberDto.setPassword(null);
			return new ResponseEntity<>(memberDto, HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/join")
	public ResponseEntity<Boolean> join(@RequestBody MemberDto memberdto) {
		try {
			memberService.join(memberdto);
			return new ResponseEntity<>(true, HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/memberDoubleId")
	public ResponseEntity<Boolean> memberDoubleId(@RequestBody Map<String,String> param) {
		try {
			Boolean isDoubleId = memberService.checkMemberId(param.get("id"));
			return new ResponseEntity<>(isDoubleId, HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
}
