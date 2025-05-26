package com.blogaer.post.dto;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class PostDto {

    private String id;
    private String userId;
    private String title;
    private String contentText;
    private List<Object> content;
    private List<String> categories;
    private List<String> tags;
    private List<CommentDto> comments;
    private List<ThoughtDto> thoughts;
    private int reads;
    private Date createdAt;
    private Date updatedAt;

    public static Builder builder() {
        return new Builder();
    }

    private PostDto(Builder builder) {
        this.id = builder.id;
        this.userId = builder.userId;
        this.title = builder.title;
        this.contentText = builder.contentText != null ? builder.contentText : "";
        this.content = builder.content != null ? builder.content : Collections.emptyList();
        this.categories = builder.categories != null ? builder.categories : Collections.emptyList();
        this.tags = builder.tags != null ? builder.tags : Collections.emptyList();
        this.comments = builder.comments != null ? builder.comments : Collections.emptyList();
        this.thoughts = builder.thoughts != null ? builder.thoughts : Collections.emptyList();
        this.reads = builder.reads;
        this.createdAt = builder.createdAt != null ? builder.createdAt : new Date();
        this.updatedAt = builder.updatedAt != null ? builder.createdAt : new Date();
    }
    public static class Builder {
        private String id;
        private String userId;
        private String title;
        private String contentText;
        private List<Object> content;
        private List<String> categories;
        private List<String> tags;
        private List<CommentDto> comments;
        private List<ThoughtDto> thoughts;
        private int reads;
        private Date createdAt;
        private Date updatedAt;

        public Builder id(String id) {this.id = id; return this;}
        public Builder userId(String userId) {this.userId = userId; return this;}
        public Builder title(String title) {this.title = title; return this;}
        public Builder contentText(String contentText) {this.contentText = contentText; return this;}
        public Builder content(List<Object> content) {this.content = content; return this;}
        public Builder categories(List<String> categories) {this.categories = categories; return this;}
        public Builder tags(List<String> tags) {this.tags = tags; return this;}
        public Builder comments(List<CommentDto> comments) {this.comments = comments; return this;}
        public Builder thoughts(List<ThoughtDto> thoughts) {this.thoughts = thoughts; return this;}
        public Builder reads(int reads) {this.reads = reads; return this;}
        public Builder createdAt(Date createdAt) {this.createdAt = createdAt; return this;}
        public Builder updatedAt(Date updatedAt) {this.updatedAt = updatedAt; return this;}

        public PostDto build() {
            return new PostDto(this);
        }
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getContentText() { return contentText; }
    public void setContentText(String contentText) { this.contentText = contentText; }
    public List<Object> getContent() { return content; }
    public void setContent(List<Object> content) { this.content = content; }
    public List<String> getCategories() { return categories; }
    public void setCategories(List<String> categories) { this.categories = categories; }
    public List<String> getTags() { return tags; }
    public void setTags(List<String> tags) { this.tags = tags; }
    public List<CommentDto> getComments() { return comments; }
    public void setComments(List<CommentDto> comments) { this.comments = comments; }
    public List<ThoughtDto> getThoughts() { return thoughts; }
    public void setThoughts(List<ThoughtDto> thoughts) { this.thoughts = thoughts; }
    public int getReads() { return reads; }
    public void setReads(int reads) { this.reads = reads; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
    @Override
    public int hashCode() { return Objects.hash(id, userId, title, content); }

    @Override
    public boolean equals( Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PostDto other = (PostDto) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id)) {
            return false;
        }
        if (userId == null) {
            if (other.userId != null)
                return false;
        } else if (!userId.equals(other.userId)) {
            return false;
        }
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title)) {
            return false;
        }
        if (content == null) {
            if (other.content != null)
                return false;
        } else if (!content.equals(other.content)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return MessageFormat.format("PostDto [id={0}, userId={1}, title={2}, content={3}, categories={4}, tags={5}, comments={6}, thoughts={7}, reads={8}, createdAt={9}, updatedAt={10}]", id, userId, title, content, categories, tags, comments, thoughts, reads, createdAt, updatedAt);
    }
}
