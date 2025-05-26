package com.blogaer.post.entities;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class Post {

    @BsonId
    private ObjectId id;
    private String userId;
    private String title;
    private String contentText;
    private List<Object> content;
    private List<String> categories;
    private List<String> tags;
    private List<Comment> comments;
    private List<Thought> thoughts;
    private int reads;
    private Date createdAt;
    private Date updatedAt;
    
    public static Builder builder() {
        return new Builder();
    }
    
    private Post(Builder builder) {
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
        private ObjectId id;
        private String userId;
        private String title;
        private String contentText;
        private List<Object> content;
        private List<String> categories;
        private List<String> tags;
        private List<Comment> comments;
        private List<Thought> thoughts;
        private int reads;
        private Date createdAt;
        private Date updatedAt;
        
        public Builder id(ObjectId id) { this.id = id; return this; }
        public Builder userId(String userId) { this.userId = userId; return this; }
        public Builder title(String title) {this.title = title; return this;}
        public Builder contentText(String contentText) {this.contentText = contentText; return this;}
        public Builder content(List<Object> content) {this.content = content; return this;}
        public Builder categories(List<String> categories) {this.categories = categories; return this;}
        public Builder tags(List<String> tags) {this.tags = tags; return this;}
        public Builder comments(List<Comment> comments) {this.comments = comments; return this;}
        public Builder thoughts(List<Thought> thoughts) {this.thoughts = thoughts; return this;}
        public Builder reads(int reads) {this.reads = reads; return this;}
        public Builder createdAt(Date createdAt) {this.createdAt = createdAt; return this;}
        public Builder updatedAt(Date updatedAt) {this.updatedAt = updatedAt; return this;}
    
        public Post build() {
            return new Post(this);
        }
    }
    
    public ObjectId getId() { return id; }
    public void setId(ObjectId id) { this.id = id; }
    public String getUserId() { return userId; }
    public void setUserId( String userId) { this.userId = userId; }
    public String getTitle() { return title; }
    public void setTitle( String title) { this.title = title; }
    public String getContentText() { return contentText; }
    public void setContentText(String contentText) { this.contentText = contentText; }
    public List<Object> getContent() { return content; }
    public void setContent( List<Object> content) { this.content = content; }
    public List<String> getCategories() { return categories; }
    public void setCategories(List<String> categories) { this.categories = categories; }
    public List<String> getTags() { return tags; }
    public void setTags(List<String> tags) { this.tags = tags; }
    public List<Comment> getComments() { return comments; }
    public void setComments(List<Comment> comments) { this.comments = comments; }
    public List<Thought> getThoughts() { return thoughts; }
    public void setThoughts(List<Thought> thoughts) { this.thoughts = thoughts; }
    public int getReads() { return reads; }
    public void setReads(int reads) { this.reads = reads; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, title, content);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Post other = (Post) obj;
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
        if (categories == null) {
            if (other.categories != null)
                return false;
        } else if (!categories.equals(other.categories)) {
            return false;
        }
        if (tags == null) {
            if (other.tags != null)
                return false;
        } else if (!tags.equals(other.tags)) {
            return false;
        }
        if (comments == null) {
            if (other.comments != null)
                return false;
        } else if (!comments.equals(other.comments)) {
            return false;
        }
        if (thoughts == null) {
            if (other.thoughts != null)
                return false;
        } else if (!thoughts.equals(other.thoughts)) {
            return false;
        }
        if (reads != other.reads)
            return false;
        if (createdAt == null) {
            if (other.createdAt != null)
                return false;
        } else if (!createdAt.equals(other.createdAt)) {
            return false;
        }
        if (updatedAt == null) {
            if (other.updatedAt != null)
                return false;
        } else if (!updatedAt.equals(other.updatedAt)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return MessageFormat.format("Post [id={0}, userId={1}, title={2}, content={3}, categories={4}, tags={5}, comments={6}, thoughts={7}, reads={8}, createdAt={9}, updatedAt={10}]", id, userId, title, content, categories, tags, comments, thoughts, reads, createdAt, updatedAt);
    }
}
