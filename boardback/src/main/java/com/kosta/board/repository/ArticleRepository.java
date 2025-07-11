package com.kosta.board.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import com.kosta.board.entity.Article;

public interface ArticleRepository extends JpaRepository<Article, Integer> {
	Page<Article> findByTitleContains(String word, PageRequest pageRequest);
	Page<Article> findByContentContains(String word, PageRequest pageRequest);
	Page<Article> findByMember_Name(String word, PageRequest pageRequest);
}
