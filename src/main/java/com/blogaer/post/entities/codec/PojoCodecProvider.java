package com.blogaer.post.entities.codec;

import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;

import com.blogaer.post.entities.Draft;
import com.blogaer.post.entities.Post;

import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
@RegisterForReflection
public class PojoCodecProvider implements CodecProvider {

    @Inject
    PostCodec postCodec;

    @Inject
    DraftCodec draftCodec;

    @SuppressWarnings("unchecked")
    @Override
    public <T> Codec<T> get(Class<T> clazz, CodecRegistry registry) {
        if (clazz.equals(Post.class)) {
            return (Codec<T>) postCodec;
        } else if (clazz.equals(Draft.class)) {
            return (Codec<T>) draftCodec;
        }
        return null;
    }
}
