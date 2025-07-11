package com.kosta.board.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kosta.board.entity.BoardLike;

public interface BoardLikeRepository extends JpaRepository<BoardLike, Integer> {
	Optional<BoardLike> findByMember_IdAndArticle_Num(String id, Integer num);
}
