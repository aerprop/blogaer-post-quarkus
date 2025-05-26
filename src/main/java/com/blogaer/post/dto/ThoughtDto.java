package com.blogaer.post.dto;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class ThoughtDto {

    private String id;
    private List<String> thoughts;

    public static Builder builder() {
        return new Builder();
    }
    private ThoughtDto(Builder builder) {
        this.id = builder.id;
        this.thoughts = builder.thoughts;
    }
    
    public static class Builder {
        private String id;
        private List<String> thoughts;
    
        public Builder id(String id) { this.id = id; return this; }
        public Builder thoughts(List<String> thoughts) { this.thoughts = thoughts; return this; }
    
        public ThoughtDto build() {
            return new ThoughtDto(this);
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getThoughts() {
        return thoughts;
    }

    public void setThoughts(List<String> thoughts) {
        this.thoughts = thoughts;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, thoughts);
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
        ThoughtDto other = (ThoughtDto) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id)) {
            return false;
        }
        if (thoughts == null) {
            if (other.thoughts != null)
                return false;
        } else if (!thoughts.equals(other.thoughts)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return MessageFormat.format("ThoughtDto [id={0}, thoughts={1}]", id, thoughts);
    }
}
