package com.blogaer.post.dto.request;

import java.text.MessageFormat;
import java.util.Objects;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class QueryDto {

    private String sort;
    private String order;
    private FilterDto filterDto;
    private String search;

    public static Builder builder() {
        return new Builder();
    }
    private QueryDto(Builder builder) {
        this.sort = builder.sort;
        this.order = builder.order;
        this.filterDto = builder.filterDto;
        this.search = builder.search;
    }
    public static class Builder {
        private String sort;
        private String order;
        private FilterDto filterDto;
        private String search;

        public Builder sort(String sort) { this.sort = sort; return this; }
        public Builder order(String order) { this.order = order; return this; }
        public Builder filter(FilterDto filter) { this.filterDto = filter; return this; }
        public Builder query(String query) { this.search = query; return this; }

        public QueryDto build() {
            return new QueryDto(this);
        }
    }

    public String getSort() { return sort; }
    public void setSort(String sort) { this.sort = sort; }
    public String getOrder() { return order; }
    public void setOrder(String order) { this.order = order; }
    public FilterDto getFilterDto() { return filterDto; }
    public void setFilter(FilterDto filter) { this.filterDto = filter; }
    public String getSearch() { return search; }
    public void setSearch(String query) { this.search = query; }

    @Override
    public int hashCode() {
        ;
        return Objects.hash(sort, order, filterDto, search);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        QueryDto other = (QueryDto) obj;
        if (sort == null) {
            if (other.sort != null)
                return false;
        } else if (!sort.equals(other.sort)) {
            return false;
        }
        if (order == null) {
            if (other.order != null)
                return false;
        } else if (!order.equals(other.order)) {
            return false;
        }
        if (filterDto == null) {
            if (other.filterDto != null)
                return false;
        } else if (!filterDto.equals(other.filterDto)) {
            return false;
        }
        if (search == null) {
            if (other.search != null)
                return false;
        } else if (!search.equals(other.search)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return MessageFormat.format("QueryDto [sort={0}, order={1}, filter={2}, search={3}]", sort, order, filterDto,
                search);
    }
}
