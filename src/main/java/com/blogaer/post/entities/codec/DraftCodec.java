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

import com.blogaer.post.entities.Draft;
import com.mongodb.MongoClientSettings;

import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.inject.Singleton;

@Singleton
@RegisterForReflection
public class DraftCodec implements CollectibleCodec<Draft> {

    private final Codec<Document> docCodec;

    public DraftCodec() {
        this.docCodec = MongoClientSettings.getDefaultCodecRegistry().get(Document.class);
    }

    @Override
    public void encode(BsonWriter writer, Draft draft, EncoderContext encoderContext) {
        Document doc = new Document();

        String id = draft.getId();
        if (id == null)
            draft.setId(new ObjectId().toHexString());

        try {
            doc.put("_id", new ObjectId(id));
        } catch (IllegalArgumentException e) {
            System.err.println("Error encoding Draft ID >> '" + draft.getId() + "': " + e.getMessage());
            e.printStackTrace();
        }

        doc.put("userId", draft.getUserId());
        doc.put("title", draft.getTitle());
        doc.put("text", draft.getText());
        doc.put("content", draft.getContent());
        doc.put("createdAt", draft.getCreatedAt());
        doc.put("updatedAt", draft.getUpdatedAt());

        docCodec.encode(writer, doc, encoderContext);
    }

    @Override
    public Class<Draft> getEncoderClass() {
        return Draft.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Draft decode(BsonReader reader, DecoderContext decoderContext) {
        Document doc = docCodec.decode(reader, decoderContext);
        Draft.Builder draft = Draft.builder();
        Object id = doc.get("_id");
        if (id instanceof ObjectId) {
            draft.id(((ObjectId) id).toHexString());
        } else if (id instanceof String) {
            draft.id((String) id);
        } else {
            System.err.println("Unexpected _id type in the document: " + id);
        }
        draft.userId(doc.getString("userId"));
        draft.title(doc.getString("title"));
        draft.text(doc.getString("text"));
        draft.content((List<Document>) doc.get("content"));
        draft.createdAt(doc.getDate("createdAt"));
        draft.updatedAt(doc.getDate("updatedAt"));

        return draft.build();
    }

    @Override
    public Draft generateIdIfAbsentFromDocument(Draft draft) {
        if (!documentHasId(draft)) {
            draft.setId(new ObjectId().toHexString());
        }
        return draft;
    }

    @Override
    public boolean documentHasId(Draft draft) {
        return draft.getId() != null && !draft.getId().isEmpty();
    }

    @Override
    public BsonValue getDocumentId(Draft draft) {
        if (documentHasId(draft)) {
            try {
                return new BsonObjectId(new ObjectId(draft.getId()));
            } catch (IllegalArgumentException e) {
                System.err.println("Warning: Draft ID '" + draft.getId() + "' is not a valid ObjectId hex string for BsonObjectId: " + e.getMessage());
                return new BsonString(draft.getId());
            }
        }
        return null;
    }
}
