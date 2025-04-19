package com.hwoolog.api.repository;

import com.hwoolog.api.domain.Post;
import com.hwoolog.api.request.PostSerch;

import java.util.List;

public interface PostRepositoryCustom {

    List<Post> getList(PostSerch postSerch);

}