package com.hwoolog.api.repository;

import com.hwoolog.api.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {
}
