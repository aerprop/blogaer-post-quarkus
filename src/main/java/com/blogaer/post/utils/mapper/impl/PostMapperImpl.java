package com.blogaer.post.utils.mapper.impl;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

import com.blogaer.post.dto.CommentDto;
import com.blogaer.post.dto.PostDto;
import com.blogaer.post.dto.ThoughtDto;
import com.blogaer.post.entities.Comment;
import com.blogaer.post.entities.Post;
import com.blogaer.post.entities.Thought;
import com.blogaer.post.utils.mapper.PostMapper;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PostMapperImpl implements PostMapper {

    @Override
    public PostDto postToPostDto(Post post) {
        if (post == null)
            return null;

        PostDto.Builder postDto = PostDto.builder();

        postDto.id(post.getId().toString());
        postDto.userId(post.getUserId());
        postDto.title(post.getTitle());
        postDto.content(post.getContent());
        postDto.categories(post.getCategories());
        postDto.tags(post.getTags());
        postDto.comments(commentListToCommentDtoList(post.getComments()));
        postDto.thoughts(thoughtListToThoughtDtoList(post.getThoughts()));
        postDto.reads(post.getReads());
        postDto.createdAt(post.getCreatedAt());
        postDto.updatedAt(post.getUpdatedAt());

        return postDto.build();
    }

    @Override
    public Post postDtoToPost(PostDto postDto) {
        if (postDto == null)
            return null;

        Post.Builder post = Post.builder();

        post.id(new ObjectId(postDto.getId()));
        post.userId(postDto.getUserId());
        post.title(postDto.getTitle());
        post.content(postDto.getContent());
        post.categories(postDto.getCategories());
        post.tags(postDto.getTags());
        post.comments(commentDtoListToCommentList(postDto.getComments()));
        post.thoughts(thoughtDtoListToThoughtList(postDto.getThoughts()));
        post.reads(postDto.getReads());
        post.createdAt(postDto.getCreatedAt());
        post.updatedAt(postDto.getUpdatedAt());

        return post.build();
    }

    @Override
    public CommentDto commentToCommentDto(Comment comment) {
        if (comment == null)
            return null;

        CommentDto.Builder commentDto = CommentDto.builder();

        commentDto.commentId(comment.getId());
        commentDto.commentText(comment.getCommentText());

        return commentDto.build();
    }

    @Override
    public Comment commentDtoToComment(CommentDto commentDto) {
        if (commentDto == null)
            return null;

        Comment.Builder comment = Comment.builder();

        comment.commentId(commentDto.getCommentId());
        comment.commentText(commentDto.getCommentText());

        return comment.build();
    }

    @Override
    public ThoughtDto thoughtToThoughtDto(Thought thought) {
        if (thought == null)
            return null;

        ThoughtDto.Builder thoughtDto = ThoughtDto.builder();

        thoughtDto.id(thought.getId());
        thoughtDto.thoughts(thought.getThoughts());

        return thoughtDto.build();
    }

    @Override
    public Thought thoughtDtoToThought(ThoughtDto thoughtDto) {
        if (thoughtDto == null)
            return null;

        Thought.Builder thought = Thought.builder();

        thought.id(thoughtDto.getId());
        thought.thoughts(thoughtDto.getThoughts());

        return thought.build();
    }

    @Override
    public List<CommentDto> commentListToCommentDtoList(List<Comment> commentList) {
        if (commentList == null)
            return null;

        List<CommentDto> commentDtoList = new ArrayList<CommentDto>(commentList.size());
        for (Comment comment : commentList) {
            commentDtoList.add(commentToCommentDto(comment));
        }

        return commentDtoList;
    }

    @Override
    public List<Comment> commentDtoListToCommentList(List<CommentDto> commentDtoList) {
        if (commentDtoList == null)
            return null;

        List<Comment> commentList = new ArrayList<Comment>(commentDtoList.size());
        for (CommentDto commentDto : commentDtoList) {
            commentList.add(commentDtoToComment(commentDto));
        }

        return commentList;
    }

    @Override
    public List<ThoughtDto> thoughtListToThoughtDtoList(List<Thought> thoughtList) {
        if (thoughtList == null)
            return null;

        List<ThoughtDto> thoughtDtoList = new ArrayList<ThoughtDto>(thoughtList.size());
        for (Thought thought : thoughtList) {
            thoughtDtoList.add(thoughtToThoughtDto(thought));
        }

        return thoughtDtoList;
    }

    @Override
    public List<Thought> thoughtDtoListToThoughtList(List<ThoughtDto> thoughtDtoList) {
        if (thoughtDtoList == null)
            return null;

        List<Thought> thoughtList = new ArrayList<Thought>(thoughtDtoList.size());
        for (ThoughtDto thoughtDto : thoughtDtoList) {
            thoughtList.add(thoughtDtoToThought(thoughtDto));
        }

        return thoughtList;
    }

}
