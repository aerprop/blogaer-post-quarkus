package com.blogaer.post.utils.mapper;

import java.util.List;

import com.blogaer.post.dto.CommentDto;
import com.blogaer.post.dto.PostDto;
import com.blogaer.post.dto.ThoughtDto;
import com.blogaer.post.entities.Comment;
import com.blogaer.post.entities.Post;
import com.blogaer.post.entities.Thought;

public interface PostMapper {

    PostDto postToPostDto(Post post);

    Post postDtoToPost(PostDto postDto);

    CommentDto commentToCommentDto(Comment comment);

    Comment commentDtoToComment(CommentDto commentDto);

    ThoughtDto thoughtToThoughtDto(Thought thought);

    Thought thoughtDtoToThought(ThoughtDto thoughtDto);

    List<CommentDto> commentListToCommentDtoList(List<Comment> commentList);

    List<Comment> commentDtoListToCommentList(List<CommentDto> commentDtoList);

    List<ThoughtDto> thoughtListToThoughtDtoList(List<Thought> thoughtList);

    List<Thought> thoughtDtoListToThoughtList(List<ThoughtDto> thoughtDtoList);
}
