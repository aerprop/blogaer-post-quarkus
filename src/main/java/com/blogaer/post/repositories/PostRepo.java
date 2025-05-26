package com.blogaer.post.repositories;

import com.blogaer.post.dto.request.PageDto;
import com.blogaer.post.entities.Post;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

public interface PostRepo {

    Multi<Post> getPostsByPage(PageDto pageDto);
    Uni<Post> getPostById(String id);
    Uni<String> addPost(Post post);
    Uni<String> patchPost(Post post);
    Uni<String> deletePost(String id);
}
