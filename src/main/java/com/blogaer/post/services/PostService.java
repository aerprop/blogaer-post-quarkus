package com.blogaer.post.services;

import com.blogaer.post.dto.PostDto;
import com.blogaer.post.dto.request.PageDto;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

public interface PostService {

    Multi<PostDto> getPostsByPage(PageDto pageDto);
    Uni<PostDto> getPostById(String id);
    Uni<String> addPost(PostDto postDto);
    Uni<String> patchPost(PostDto postDto);
    Uni<String> deletePost(String id);
}
