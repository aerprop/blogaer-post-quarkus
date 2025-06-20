package com.blogaer.post.dto.response;

import java.text.MessageFormat;
import java.util.List;

import com.blogaer.post.dto.PostDto;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class PagedPostDto {

    private int currentPage;
    private int totalPages;
    private int totalPosts;
    private List<PostDto> posts;

    public PagedPostDto() {}

    public static Builder builder() {
        return new Builder();
    }

    private PagedPostDto(Builder builder) {
        this.currentPage = builder.currentPage;
        this.totalPages = builder.totalPages;
        this.totalPosts = builder.totalPosts;
        this.posts = builder.posts;
    }

    public static class Builder {
        private int currentPage;
        private int totalPages;
        private int totalPosts;
        private List<PostDto> posts;

        public Builder currentPage(int currentPage) { this.currentPage = currentPage; return this; }
        public Builder totalPages(int totalPages) { this.totalPages = totalPages; return this; }
        public Builder totalPosts(int totalPosts) { this.totalPosts = totalPosts; return this; }
        public Builder posts(List<PostDto> posts) { this.posts = posts; return this; }

        public PagedPostDto build() {
            return new PagedPostDto(this);
        }
    }

    public int getCurrentPage() { return currentPage; }
    public void setCurrentPage(int currentPage) { this.currentPage = currentPage; }
    public double getTotalPages() { return totalPages; }
    public void setTotalPages(int totalPages) { this.totalPages = totalPages; }
    public int getTotalPosts() { return totalPosts; }
    public void setTotalPosts(int totalPosts) { this.totalPosts = totalPosts; }
    public List<PostDto> getPosts() { return posts; }
    public void setPosts(List<PostDto> posts) { this.posts = posts; }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + currentPage;
        result = prime * result + totalPages;
        result = prime * result + totalPosts;
        result = prime * result + ((posts == null) ? 0 : posts.hashCode());
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
        PagedPostDto other = (PagedPostDto) obj;
        if (currentPage != other.currentPage)
            return false;
        if (totalPages != other.totalPages)
            return false;
        if (totalPosts != other.totalPosts)
            return false;
        if (posts == null) {
            if (other.posts != null)
                return false;
        } else if (!posts.equals(other.posts))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return MessageFormat.format("PagedPostDto [currentPage={0}, totalPages={1}, totalPosts={2}, posts={3}]",
                currentPage, totalPages, totalPosts, posts);
    }
}
