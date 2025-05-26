package com.blogaer.post.dto.request;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class FilterDto {

    private List<String> categories;
    private List<String> tags;
    private Date startDate;
    private Date endDate;

    public static Builder builder() {
        return new Builder();
    }
    private FilterDto(Builder builder) {
        this.categories = builder.categories;
        this.tags = builder.tags;
        this.startDate = builder.startDate;
        this.endDate = builder.endDate;
    }
    public static class Builder {
        private List<String> categories;
        private List<String> tags;
        private Date startDate;
        private Date endDate;
    
        public Builder categories(List<String> categories) { this.categories = categories; return this; }
        public Builder tags(List<String> tags) { this.tags = tags; return this; }
        public Builder firstDate(Date firstDate) { this.startDate = firstDate; return this; }
        public Builder lastDate(Date lastDate) { this.endDate = lastDate; return this; }
    
        public FilterDto build() {
            return new FilterDto(this);
        }
    }

    public List<String> getCategories() { return categories; }
    public void setCategories(List<String> categories) { this.categories = categories; }
    public List<String> getTags() { return tags; }
    public void setTags(List<String> tags) { this.tags = tags; }
    public Date getStartDate() { return startDate; }
    public void setStartDate(Date firsDate) { this.startDate = firsDate; }
    public Date getEndDate() { return endDate; }
    public void setEndDate(Date lastDate) { this.endDate = lastDate; }

    @Override
    public int hashCode() {
        return Objects.hash(categories, tags, startDate, endDate);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        FilterDto other = (FilterDto) obj;
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
        if (startDate == null) {
            if (other.startDate != null)
                return false;
        } else if (!startDate.equals(other.startDate)) {
            return false;
        }
        if (endDate == null) {
            if (other.endDate != null)
                return false;
        } else if (!endDate.equals(other.endDate)) {
            return false;
        }
        return true;
    }
    @Override
    public String toString() {
        return MessageFormat.format("FilterDto [categories={0}, tags={1}, firstDate={2}, lastDate={3}]", categories, tags, startDate, endDate);
    }

}
