package com.blogaer.post.repositories.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.blogaer.post.dto.request.FilterDto;
import com.blogaer.post.dto.request.PageDto;
import com.blogaer.post.dto.request.QueryDto;
import com.blogaer.post.entities.Post;
import com.blogaer.post.repositories.PostRepo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;

import io.quarkus.mongodb.FindOptions;
import io.quarkus.mongodb.reactive.ReactiveMongoCollection;
import io.quarkus.mongodb.reactive.ReactiveMongoDatabase;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class PostRepoImpl implements PostRepo {

    @Inject
    ReactiveMongoCollection<Document> mongoCollection;

    @Inject
    ReactiveMongoDatabase mongoDB;

    @Override
    public Multi<Post> getPostsByPage(PageDto pageDto) {
        int number = Integer.parseInt(pageDto.getNumber());
        int size = Integer.parseInt(pageDto.getSize());
        int num = (number == 0 ? 1 : number);
        int limit = size == 0 ? 10 : size;
        int offset = (num - 1) * limit;
        FindOptions options = new FindOptions().skip(offset).limit(limit);

        List<Bson> filterList = new ArrayList<>();
        String userId = pageDto.getUserId();
        if (userId != null) {
            filterList.add(Filters.eq("userId", userId));
        }
        QueryDto queryDto = pageDto.getQueryDto();
        if (queryDto != null) {
            String sort = queryDto.getSort();
            String order = queryDto.getOrder();
            FilterDto filterDto = queryDto.getFilterDto();
            String search = queryDto.getSearch();

            if (sort != null) {
                if ("desc".equalsIgnoreCase(order))
                    options.sort(Sorts.descending(sort.split(",")));
                else
                    options.sort(Sorts.ascending(sort.split(",")));
            }
            if (filterDto != null) {
                List<String> categories = filterDto.getCategories();
                List<String> tags = filterDto.getTags();
                Date startDate = filterDto.getStartDate();
                Date endDate = filterDto.getEndDate();

                if (categories != null)
                    filterList.add(Filters.in("categories", categories));
                if (tags != null)
                    filterList.add(Filters.in("tags", tags));
                if (startDate != null)
                    filterList.add(Filters.gte("updatedAt", startDate));
                if (endDate != null)
                    filterList.add(Filters.lte("updatedAt", endDate));
            }
            if (search != null) {
                filterList.add(Filters.regex("title", Pattern.compile(search)));
                filterList.add(Filters.regex("contentText", Pattern.compile(search)));
            }
        }
        options.filter(Filters.and(filterList));
        ReactiveMongoCollection<Post> postCollection = mongoDB.getCollection("post", Post.class);

        return postCollection.find(options);
    }

    @Override
    public Uni<Post> getPostById(String id) {
        ReactiveMongoCollection<Post> postCollection = mongoDB.getCollection("post", Post.class);
        FindOptions options = new FindOptions().filter(Filters.eq("id", id)).limit(1);

        return postCollection.find(options).toUni();
    }

    @Override
    public Uni<String> addPost(Post post) {
        ReactiveMongoCollection<Post> posCollection = mongoDB.getCollection("post", Post.class);

        return posCollection.insertOne(post)
                .map(result -> result.wasAcknowledged() ? "Post successfully added." : "Adding post failed!")
                .onFailure()
                .recoverWithItem(error -> "Failed to add post >> " + error.getMessage());
    }

    @Override
    public Uni<String> patchPost(Post post) {
        ReactiveMongoCollection<Post> posCollection = mongoDB.getCollection("post", Post.class);
        Document setDoc = new Document();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.valueToTree(post);
        node.fields().forEachRemaining(entry -> {
            if (!entry.getValue().isNull() && !entry.getKey().equals("_id")) {
                setDoc.append(entry.getKey(), mapper.convertValue(entry.getValue(), Object.class));
            }
        });
        if (setDoc.isEmpty()) {
            return Uni.createFrom().failure(new IllegalArgumentException("No fields to patch in the Post object."));
        }
        Document patch = new Document("$set", setDoc);
        Bson options = Filters.eq("id", post.getId());

        return posCollection.updateOne(options, patch)
                .map(result -> result.wasAcknowledged() ? "Post successfully patched." : "Patching post failed!")
                .onFailure()
                .recoverWithItem(error -> "Failed to patch post >> " + error.getMessage());
    }

    @Override
    public Uni<String> deletePost(String id) {
        ReactiveMongoCollection<Post> postCollection = mongoDB.getCollection("post", Post.class);
        Bson filter = Filters.eq("id", id);

        return postCollection.deleteOne(filter)
                .map(result -> result.wasAcknowledged() ? "Post successfully deleted." : "Deleting post failed!")
                .onFailure()
                .recoverWithItem(error -> "Failed to delete post >> " + error.getMessage());
    }
}
