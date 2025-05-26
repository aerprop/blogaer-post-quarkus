package com.blogaer.post.services.impl;

import com.blogaer.post.dto.PostDto;
import com.blogaer.post.dto.request.PageDto;
import com.blogaer.post.repositories.PostRepo;
import com.blogaer.post.services.PostService;
import com.blogaer.post.utils.mapper.PostMapper;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;

public class PostServiceImpl implements PostService {
    @Inject
    PostRepo repo;

    @Inject
    PostMapper mapper;

    @Override
    public Multi<PostDto> getPostsByPage(PageDto pageDto) {
        return repo.getPostsByPage(pageDto).map(mapper::postToPostDto);
    }

    @Override
    public Uni<PostDto> getPostById(String id) {
        return repo.getPostById(id).map(mapper::postToPostDto);
    }

    @Override
    public Uni<String> addPost(PostDto postDto) {
        return repo.addPost(mapper.postDtoToPost(postDto));
    }

    @Override
    public Uni<String> patchPost(PostDto postDto) {
        return repo.patchPost(mapper.postDtoToPost(postDto));
    }

    @Override
    public Uni<String> deletePost(String id) {
        return repo.deletePost(id);
    }
}
