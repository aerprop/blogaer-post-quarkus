package com.blogaer.post.entities;

import java.text.MessageFormat;
import java.util.Objects;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class Comment {

    private String id;
    private String commentText;

    public static Builder builder() {
        return new Builder();
    }

    private Comment(Builder builder) {
        this.id = builder.commentId;
        this.commentText = builder.commentText;
    }

    public static class Builder {
        private String commentId;
        private String commentText;

        public Builder commentId(String commentId) {
            this.commentId = commentId;
            return this;
        }

        public Builder commentText(String commentText) {
            this.commentText = commentText;
            return this;
        }

        public Comment build() {
            return new Comment(this);
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String text) {
        this.commentText = text;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, commentText);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Comment other = (Comment) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (commentText == null) {
            if (other.commentText != null)
                return false;
        } else if (!commentText.equals(other.commentText))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return MessageFormat.format("Comment [id={0}, text={1}]", id, commentText);
    }
}
