package com.blogaer.post.dto;

import java.text.MessageFormat;
import java.util.Objects;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class CommentDto {

    private String commentId;
    private String commentText;

    public static Builder builder() {
        return new Builder();
    }
    private CommentDto(Builder builder) {
        this.commentId = builder.commentId;
        this.commentText = builder.commentText;
    }
    
    public static class Builder {
        private String commentId;
        private String commentText;
    
        public Builder commentId(String commentId) { this.commentId = commentId; return this; }
        public Builder commentText(String commentText) { this.commentText = commentText; return this; }
    
        public CommentDto build() {
            return new CommentDto(this);
        }
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    @Override
    public int hashCode() {
        return Objects.hash(commentId, commentText);
    }

    @Override
    public boolean equals(
            Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CommentDto other = (CommentDto) obj;
        if (commentId == null) {
            if (other.commentId != null)
                return false;
        } else if (!commentId.equals(other.commentId)) {
            return false;
        }
        if (commentText == null) {
            if (other.commentText != null)
                return false;
        } else if (!commentText.equals(other.commentText)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return MessageFormat.format("CommentDto [commentId={0}, commentText={1}]", commentId, commentText);
    }
}
