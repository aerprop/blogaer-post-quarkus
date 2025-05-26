package com.blogaer.post.messaging;

import java.util.Optional;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Message;

import io.smallrye.mutiny.Uni;
import io.smallrye.reactive.messaging.MutinyEmitter;
import io.smallrye.reactive.messaging.rabbitmq.OutgoingRabbitMQMetadata;
import jakarta.inject.Inject;

public class MsgPublisher {

    @Inject
    @Channel("publisher")
    MutinyEmitter<Object> emitter;

    public Uni<Void> publish(Optional<String> replyToQueue, Optional<String> correlationId, Object data) {
        if (!replyToQueue.isPresent() && !correlationId.isPresent()) {
            return Uni.createFrom().voidItem();
        }
        OutgoingRabbitMQMetadata.Builder metadata = OutgoingRabbitMQMetadata.builder();
        replyToQueue.ifPresent(metadata::withRoutingKey);
        correlationId.ifPresent(metadata::withCorrelationId);
        metadata.build();
        Message<Object> msg = Message.of(data).addMetadata(metadata);

        return emitter.sendMessage(msg).invoke(item -> System.out.println("Message sent to ~> " + replyToQueue));
    }
}
