package com.blogaer.post.utils.helper;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.bson.conversions.Bson;

import com.blogaer.post.utils.refs.TypeRef;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.model.Updates;

import io.smallrye.mutiny.Uni;

public final class Helper {

    private Helper() {
    }

    public static Uni<Void> invokeAndLog(String message) {
        return Uni.createFrom().voidItem().invoke(() -> System.out.println("Publish message >> " + message));
    }

    public static <T> Uni<Void> invokeAndLog(Uni<T> upstreamUni) {
        return upstreamUni
                .invoke(item -> System.out.println("Publish stream >> " + item))
                .replaceWithVoid()
                .onFailure()
                .invoke(failure -> System.err.println(failure.getMessage()));
    }

    public static <T> List<Bson> createBsonList(ObjectMapper mapper, T obj) {
        List<Bson> bsonList = new ArrayList<Bson>();
        Map<String, Object> postMap = mapper.convertValue(obj, TypeRef.STR_OBJ);
        for (Map.Entry<String, Object> entry : postMap.entrySet()) {
            if (entry.getValue() != null) {
                String property = entry.getKey();
                if (!property.equals("id") && !property.equals("createdAt")) {
                    if (property == "updatedAt")
                        bsonList.add(Updates.set(property, Date.from(Instant.now())));
                    else
                        bsonList.add(Updates.set(property, entry.getValue()));
                }
            }
        }

        return bsonList;
    }
}
