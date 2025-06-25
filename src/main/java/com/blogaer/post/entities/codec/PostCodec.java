package com.blogaer.post.entities.codec;

import java.util.List;

import org.bson.BsonObjectId;
import org.bson.BsonReader;
import org.bson.BsonString;
import org.bson.BsonValue;
import org.bson.BsonWriter;
import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.CollectibleCodec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.types.ObjectId;

import com.blogaer.post.entities.Post;
import com.mongodb.MongoClientSettings;

import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.inject.Singleton;

@Singleton
@RegisterForReflection
public class PostCodec implements CollectibleCodec<Post> {

    private final Codec<Document> docCodec;

    public PostCodec() {
        this.docCodec = MongoClientSettings.getDefaultCodecRegistry().get(Document.class);
    }

    @Override
    public void encode(BsonWriter writer, Post post, EncoderContext encoderContext) {
        Document doc = new Document();

        String id = post.getId();
        if (id == null)
            post.setId(new ObjectId().toHexString());

        try {
            doc.put("_id", new ObjectId(id));
        } catch (IllegalArgumentException e) {
            System.err.println("Error encoding Post ID >> '" + post.getId() + "': " + e.getMessage());
            e.printStackTrace();
        }

        doc.put("userId", post.getUserId());
        doc.put("title", post.getTitle());
        doc.put("text", post.getText());
        doc.put("content", post.getContent());
        doc.put("categories", post.getCategories());
        doc.put("tags", post.getTags());
        doc.put("reads", post.getReads());
        doc.put("createdAt", post.getCreatedAt());
        doc.put("updatedAt", post.getUpdatedAt());

        docCodec.encode(writer, doc, encoderContext);
    }

    @Override
    public Class<Post> getEncoderClass() {
        return Post.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Post decode(BsonReader reader, DecoderContext decoderContext) {
        Document doc = docCodec.decode(reader, decoderContext);
        Post.Builder post = Post.builder();
        Object id = doc.get("_id");
        if (id instanceof ObjectId) {
            post.id(((ObjectId) id).toHexString());
        } else if (id instanceof String) {
            post.id((String) id);
        } else {
            System.err.println("Unexpected _id type in the document: " + id);
        }
        post.userId(doc.getString("userId"));
        post.title(doc.getString("title"));
        post.text(doc.getString("text"));
        post.content((List<Object>) doc.get("content"));
        post.categories((List<String>) doc.get("categories"));
        post.tags((List<String>) doc.get("tags"));
        post.reads(doc.getInteger("reads"));
        post.createdAt(doc.getDate("createdAt"));
        post.updatedAt(doc.getDate("updatedAt"));

        return post.build();
    }

    @Override
    public Post generateIdIfAbsentFromDocument(Post post) {
        if (!documentHasId(post)) {
            post.setId(new ObjectId().toHexString());
        }
        return post;
    }

    @Override
    public boolean documentHasId(Post post) {
        return post.getId() != null && !post.getId().isEmpty();
    }

    @Override
    public BsonValue getDocumentId(Post post) {
        if (documentHasId(post)) {
            try {
                return new BsonObjectId(new ObjectId(post.getId()));
            } catch (IllegalArgumentException e) {
                System.err.println("Warning: Post ID '" + post.getId() + "' is not a valid ObjectId hex string for BsonObjectId: " + e.getMessage());
                return new BsonString(post.getId());
            }
        }
        return null;
    }

}
