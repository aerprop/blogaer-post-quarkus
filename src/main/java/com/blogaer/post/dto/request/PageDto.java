package com.blogaer.post.dto.request;

import java.text.MessageFormat;
import java.util.Objects;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class PageDto {

    private String number;
    private String size;
    private String userId;
    private QueryDto queryDto;

    public PageDto() {}

    public static Builder builder() {
        return new Builder();
    }
    private PageDto(Builder builder) {
        this.number = builder.number;
        this.size = builder.size;
        this.userId = builder.userId;
        this.queryDto = builder.queryDto;
    }
    
    public static class Builder {
        private String number;
        private String size;
        private String userId;
        private QueryDto queryDto;
    
        public Builder number(String number) { this.number = number; return this; }
        public Builder size(String size) { this.size = size; return this; }
        public Builder userId(String userId) { this.userId = userId; return this; }
        public Builder queryDto(QueryDto queryDto) { this.queryDto = queryDto; return this; }
        
        public PageDto build() {
            return new PageDto(this);
        }
    }

    public String getNumber() { return number; }
    public void setNumber(String number) { this.number = number; }
    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public QueryDto getQueryDto() { return queryDto; }
    public void setQueryDto(QueryDto queryDto) { this.queryDto = queryDto; }

    @Override
    public int hashCode() {
        return Objects.hash(number, size);
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PageDto other = (PageDto) obj;
        if (number == null) {
            if (other.number != null)
                return false;
        } else if (!number.equals(other.number)) {
            return false;
        }
        if (size == null) {
            if (other.size != null)
                return false;
        } else if (!size.equals(other.size)) {
            return false;
        }
        if (queryDto == null) {
            if (other.queryDto != null)
                return false;
        } else if (!queryDto.equals(other.queryDto)) {
            return false;
        }
        return true;
    }
    @Override
    public String toString() {
        return MessageFormat.format("PageDto [number={0}, size={1}, userId={2}, queryDto={3}]", number, size, userId, queryDto);
    }
}
