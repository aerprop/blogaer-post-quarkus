package com.blogaer.post.repositories.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.blogaer.post.dto.request.FilterDto;
import com.blogaer.post.dto.request.PageDto;
import com.blogaer.post.dto.request.QueryDto;
import com.blogaer.post.entities.Draft;
import com.blogaer.post.repositories.DraftRepo;
import com.blogaer.post.utils.helper.Helper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Updates;

import io.quarkus.mongodb.FindOptions;
import io.quarkus.mongodb.reactive.ReactiveMongoClient;
import io.quarkus.mongodb.reactive.ReactiveMongoCollection;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class DraftRepoImpl implements DraftRepo {

    @Inject
    ReactiveMongoClient mongoClient;

    @Inject
    ObjectMapper mapper;

    @Override
    public Multi<Draft> getDraftsByPage(PageDto pageDto) {
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
                Date startDate = filterDto.getStartDate();
                Date endDate = filterDto.getEndDate();

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
        if (!filterList.isEmpty()) {
            options.filter(Filters.and(filterList));
        }
        ReactiveMongoCollection<Draft> draftCollection = mongoClient.getDatabase("blogaer_post")
                .getCollection("draft", Draft.class);

        return draftCollection.find(options);
    }

    @Override
    public Uni<Draft> getDraftById(String id) {
        ReactiveMongoCollection<Draft> draftCollection = mongoClient.getDatabase("blogaer_post")
                .getCollection("draft", Draft.class);
        FindOptions options = new FindOptions().filter(Filters.eq("_id", new ObjectId(id))).limit(1);

        return draftCollection.find(options).toUni();
    }

    @Override
    public Uni<String> addDraft(Draft draft) {
        ReactiveMongoCollection<Draft> draftCollection = mongoClient.getDatabase("blogaer_post")
                .getCollection("draft", Draft.class);

        return draftCollection.insertOne(draft)
                .map(result -> result.wasAcknowledged()
                        ? result.getInsertedId().asObjectId().getValue().toHexString()
                        : null)
                .onFailure().recoverWithItem(error -> "Failed to add draft >> " + error.getMessage());
    }

    @Override
    public Uni<String> patchDraft(Draft draft) {
        ReactiveMongoCollection<Draft> draftCollection = mongoClient.getDatabase("blogaer_post")
                .getCollection("draft", Draft.class);

        List<Bson> bsonList = Helper.createBsonList(mapper, draft);
        if (bsonList.isEmpty()) {
            return Uni.createFrom().failure(new IllegalArgumentException("No fields to patch in the Draft object!"));
        }
        Bson patch = Updates.combine(bsonList);
        Bson options = Filters.eq("_id", new ObjectId(draft.getId()));

        return draftCollection.updateOne(options, patch)
                .map(result -> result.wasAcknowledged() ? "Draft successfully patched." : "Patching draft failed!")
                .onFailure().recoverWithItem(error -> "Failed to patch draft >> " + error.getMessage());
    }

    @Override
    public Uni<String> deleteDraft(String id) {
        ReactiveMongoCollection<Draft> draftCollection = mongoClient.getDatabase("blogaer_post")
                .getCollection("draft", Draft.class);
        Bson filter = Filters.eq("_id", new ObjectId(id));

        return draftCollection.deleteOne(filter)
                .map(result -> result.wasAcknowledged() ? "Draft successfully deleted." : "Deleting draft failed!")
                .onFailure().recoverWithItem(error -> "Failed to delete draft >> " + error.getMessage());
    }

}
