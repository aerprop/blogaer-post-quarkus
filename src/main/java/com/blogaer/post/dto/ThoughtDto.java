package com.blogaer.post.dto;

import java.text.MessageFormat;
import java.util.List;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class ThoughtDto {

    private String id;
    private String userId;
    private String postId;
    private List<String> thoughts;

    public ThoughtDto() {}

    public static Builder builder() {
        return new Builder();
    }
    private ThoughtDto(Builder builder) {
        this.id = builder.id;
        this.userId = builder.userId;
        this.postId = builder.postId;
        this.thoughts = builder.thoughts;
    }
    public static class Builder {
        private String id;
        private String userId;
        private String postId;
        private List<String> thoughts;
        
        public Builder id(String id) { this.id = id; return this; }
        public Builder userId(String userId) { this.userId = userId; return this; }
        public Builder postId(String postId) { this.postId = postId; return this; }
        public Builder thoughts(List<String> thoughts) { this.thoughts = thoughts; return this; }
        
        public ThoughtDto build() {
            return new ThoughtDto(this);
        }
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public List<String> getThoughts() { return thoughts; }
    public void setPostId(String postId) { this.postId = postId; }
    public String getPostId() { return postId; }
    public void setThoughts(List<String> thoughts) { this.thoughts = thoughts; }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((userId == null) ? 0 : userId.hashCode());
        result = prime * result + ((postId == null) ? 0 : postId.hashCode());
        result = prime * result + ((thoughts == null) ? 0 : thoughts.hashCode());
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
    ThoughtDto other = (ThoughtDto) obj;
    if (id == null) {
        if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
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
    if (thoughts == null) {
        if (other.thoughts != null)
            return false;
    } else if (!thoughts.equals(other.thoughts))
        return false;
    return true;
    }

    @Override
    public String toString() {
        return MessageFormat.format("Thought [id={0}, userId={1}, postId={2} thoughts={3}]", id, userId, postId, thoughts);
    }
}
