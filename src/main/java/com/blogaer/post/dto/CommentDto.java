package com.blogaer.post.dto;

import java.text.MessageFormat;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class CommentDto {

    private String id;
    private String parentId;
    private String userId;
    private String postId;
    private String text;

    public CommentDto() {}

    public static Builder builder() {
        return new Builder();
    }
    private CommentDto(Builder builder) {
        this.id = builder.id;
        this.parentId = builder.parentId;
        this.userId = builder.userId;
        this.postId = builder.postId;
        this.text = builder.text;
    }
    public static class Builder {
        private String id;
        private String parentId;
        private String userId;
        private String postId;
        private String text;

        public Builder id(String id) { this.id = id; return this; }
        public Builder parentId(String parentId) { this.parentId = parentId; return this; }
        public Builder userId(String userId) { this.userId = userId; return this; }
        public Builder postId(String postId) { this.postId = postId; return this; }
        public Builder text(String text) { this.text = text; return this; }
        
        public CommentDto build() {
            return new CommentDto(this);
        }
    }
    
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getParentId() { return parentId; }
    public void setParentId(String parentId) { this.parentId = parentId; }  
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getPostId() { return postId; }
    public void setPostId(String postId) { this.postId = postId; }
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((parentId == null) ? 0 : parentId.hashCode());
        result = prime * result + ((userId == null) ? 0 : userId.hashCode());
        result = prime * result + ((postId == null) ? 0 : postId.hashCode());
        result = prime * result + ((text == null) ? 0 : text.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CommentDto other = (CommentDto) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (parentId == null) {
            if (other.parentId != null)
                return false;
        } else if (!parentId.equals(other.parentId))
            return false;
        if (userId == null) {
            if (other.userId != null)
                return false;
        } else if (!userId.equals(other.userId))
            return false;
        if (postId == null) {
            if (other.postId != null)
                return false;
        } else if (!postId.equals(other.postId))
            return false;
        if (text == null) {
            if (other.text != null)
                return false;
        } else if (!text.equals(other.text))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return MessageFormat.format("Comment [id={0}, parentId={1} userId={2}, postId{3}, text={4}]", id, parentId, userId, postId, text);
    }
}
