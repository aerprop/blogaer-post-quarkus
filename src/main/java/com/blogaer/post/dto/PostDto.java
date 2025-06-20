package com.blogaer.post.dto;

import java.text.MessageFormat;
import java.time.Instant;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.bson.Document;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class PostDto {

    private String id;
    private String userId;
    private String title;
    private String text;
    private List<Document> content;
    private List<String> categories;
    private List<String> tags;
    private int reads;
    private Date createdAt;
    private Date updatedAt;

    public PostDto() {}

    public static Builder builder() {
        return new Builder();
    }

    private PostDto(Builder builder) {
        this.id = builder.id;
        this.userId = builder.userId;
        this.title = builder.title;
        this.text = builder.text != null ? builder.text : "";
        this.content = builder.content != null ? builder.content : Collections.emptyList();
        this.categories = builder.categories != null ? builder.categories : Collections.emptyList();
        this.tags = builder.tags != null ? builder.tags : Collections.emptyList();
        this.reads = builder.reads;
        this.createdAt = builder.createdAt != null ? builder.createdAt : Date.from(Instant.now());
        this.updatedAt = builder.updatedAt != null ? builder.createdAt : Date.from(Instant.now());
    }

    public static class Builder {
        private String id;
        private String userId;
        private String title;
        private String text;
        private List<Document> content;
        private List<String> categories;
        private List<String> tags;
        private int reads;
        private Date createdAt;
        private Date updatedAt;

        public Builder id(String id) { this.id = id; return this; }
        public Builder userId(String userId) { this.userId = userId; return this; }
        public Builder title(String title) { this.title = title; return this; }
        public Builder text(String text) { this.text = text; return this; }
        public Builder content(List<Document> content) { this.content = content; return this; }
        public Builder categories(List<String> categories) { this.categories = categories; return this; }
        public Builder tags(List<String> tags) { this.tags = tags; return this; }
        public Builder reads(int reads) { this.reads = reads; return this; }
        public Builder createdAt(Date createdAt) { this.createdAt = createdAt; return this; }
        public Builder updatedAt(Date updatedAt) { this.updatedAt = updatedAt; return this; }

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
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public List<Document> getContent() { return content; }
    public void setContent(List<Document> content) { this.content = content; }
    public List<String> getCategories() { return categories; }
    public void setCategories(List<String> categories) { this.categories = categories; }
    public List<String> getTags() { return tags; }
    public void setTags(List<String> tags) { this.tags = tags; }
    public int getReads() { return reads; }
    public void setReads(int reads) { this.reads = reads; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((userId == null) ? 0 : userId.hashCode());
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        result = prime * result + ((text == null) ? 0 : text.hashCode());
        result = prime * result + ((content == null) ? 0 : content.hashCode());
        result = prime * result + ((categories == null) ? 0 : categories.hashCode());
        result = prime * result + ((tags == null) ? 0 : tags.hashCode());
        result = prime * result + reads;
        result = prime * result + ((createdAt == null) ? 0 : createdAt.hashCode());
        result = prime * result + ((updatedAt == null) ? 0 : updatedAt.hashCode());
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
        PostDto other = (PostDto) obj;
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
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        if (text == null) {
            if (other.text != null)
                return false;
        } else if (!text.equals(other.text))
            return false;
        if (content == null) {
            if (other.content != null)
                return false;
        } else if (!content.equals(other.content))
            return false;
        if (categories == null) {
            if (other.categories != null)
                return false;
        } else if (!categories.equals(other.categories))
            return false;
        if (tags == null) {
            if (other.tags != null)
                return false;
        } else if (!tags.equals(other.tags))
            return false;
        if (reads != other.reads)
            return false;
        if (createdAt == null) {
            if (other.createdAt != null)
                return false;
        } else if (!createdAt.equals(other.createdAt))
            return false;
        if (updatedAt == null) {
            if (other.updatedAt != null)
                return false;
        } else if (!updatedAt.equals(other.updatedAt))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return MessageFormat.format(
                "PostDto [id={0}, userId={1}, title={2}, content={3}, categories={4}, tags={5}, reads={6}, createdAt={7}, updatedAt={8}]",
                id, userId, title, content, categories, tags, reads, createdAt, updatedAt);
    }
}
