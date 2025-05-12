package com.hwoolog.api.repository.post;

import com.hwoolog.api.domain.Post;
import com.hwoolog.api.request.post.PostSerch;

import java.util.List;

public interface PostRepositoryCustom {

    List<Post> getList(PostSerch postSerch);

}