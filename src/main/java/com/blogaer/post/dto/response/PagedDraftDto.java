package com.blogaer.post.dto.response;

import java.text.MessageFormat;
import java.util.List;

import com.blogaer.post.dto.DraftDto;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class PagedDraftDto {

    private int currentPage;
    private int totalPages;
    private int totalDrafts;
    private List<DraftDto> drafts;

    public PagedDraftDto() {
    }

    public static Builder builder() {
        return new Builder();
    }

    private PagedDraftDto(Builder builder) {
        this.currentPage = builder.currentPage;
        this.totalPages = builder.totalPages;
        this.totalDrafts = builder.totalDrafts;
        this.drafts = builder.drafts;
    }

    public static class Builder {
        private int currentPage;
        private int totalPages;
        private int totalDrafts;
        private List<DraftDto> drafts;

        public Builder currentPage(int currentPage) { this.currentPage = currentPage; return this; }
        public Builder totalPages(int totalPages) { this.totalPages = totalPages; return this; }
        public Builder totalDrafts(int totalDrafts) { this.totalDrafts = totalDrafts; return this; }
        public Builder drafts(List<DraftDto> drafts) { this.drafts = drafts; return this; }

        public PagedDraftDto build() {
            return new PagedDraftDto(this);
        }
    }

    public int getCurrentPage() { return currentPage; }
    public void setCurrentPage(int currentPage) { this.currentPage = currentPage; }
    public double getTotalPages() { return totalPages; }
    public void setTotalPages(int totalPages) { this.totalPages = totalPages; }
    public int getTotalDrafts() { return totalDrafts; }
    public void setTotalDrafts(int totalDrafts) { this.totalDrafts = totalDrafts; }
    public List<DraftDto> getDrafts() { return drafts; }
    public void setDrafts(List<DraftDto> drafts) { this.drafts = drafts; }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + currentPage;
        result = prime * result + totalPages;
        result = prime * result + totalDrafts;
        result = prime * result + ((drafts == null) ? 0 : drafts.hashCode());
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
        PagedDraftDto other = (PagedDraftDto) obj;
        if (currentPage != other.currentPage)
            return false;
        if (totalPages != other.totalPages)
            return false;
        if (totalDrafts != other.totalDrafts)
            return false;
        if (drafts == null) {
            if (other.drafts != null)
                return false;
        } else if (!drafts.equals(other.drafts))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return MessageFormat.format("PagedDraftDto [currentPage={0}, totalPages={1}, totalDrafts={2}, drafts={3}]",
                currentPage, totalPages, totalDrafts, drafts);
    }
}
