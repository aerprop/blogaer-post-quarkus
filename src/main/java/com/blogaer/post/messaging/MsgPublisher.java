package com.blogaer.post.messaging;

import java.util.Optional;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.eclipse.microprofile.reactive.messaging.Metadata;

import com.blogaer.post.utils.helper.Helper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.smallrye.mutiny.Uni;
import io.smallrye.reactive.messaging.MutinyEmitter;
import io.smallrye.reactive.messaging.rabbitmq.OutgoingRabbitMQMetadata;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class MsgPublisher {

    @Inject
    ObjectMapper mapper;

    @Inject
    @Channel("PostPubChannel")
    MutinyEmitter<byte[]> emitter;

    public Uni<Void> publish(Optional<String> replyToQueue, Optional<String> correlationId, Object data) {
        if (!replyToQueue.isPresent() && !correlationId.isPresent()) {
            return Helper.invokeAndLog("replyTo queue & correlationId not provided!");
        }
        try {
            byte[] byteData = mapper.writeValueAsBytes(data);
            OutgoingRabbitMQMetadata metadata = OutgoingRabbitMQMetadata.builder()
                    .withRoutingKey(replyToQueue.get())
                    .withCorrelationId(correlationId.get())
                    .withContentType("application/json")
                    .build();
            Message<byte[]> msg = Message.of(byteData, Metadata.of(metadata));

            return emitter.sendMessage(msg)
                    .onSubscription().invoke(() -> System.out.println("Publishing message..."))
                    .onItem().invoke(item -> System.out.println("Messaged published"))
                    .onFailure().invoke(failure -> failure.printStackTrace());
        } catch (JsonProcessingException e) {
            e.printStackTrace();

            return Helper.invokeAndLog("JsonProcessingException! >> " + e.getMessage());
        }
    }
}
