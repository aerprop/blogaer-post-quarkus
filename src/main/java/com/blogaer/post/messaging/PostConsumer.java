package com.blogaer.post.messaging;

import java.util.List;
import java.util.Optional;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.eclipse.microprofile.reactive.messaging.Metadata;

import com.blogaer.post.dto.PostDto;
import com.blogaer.post.dto.request.PageDto;
import com.blogaer.post.dto.response.PagedPostDto;
import com.blogaer.post.services.PostService;
import com.blogaer.post.utils.helper.Helper;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.smallrye.mutiny.Uni;
import io.smallrye.reactive.messaging.rabbitmq.IncomingRabbitMQMetadata;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class PostConsumer {

    @Inject
    ObjectMapper mapper;

    @Inject
    PostService service;

    @Inject
    MsgPublisher publisher;

    @Incoming("PostRpcChannel")
    public Uni<Void> consumeRpc(Message<?> msg) {
        Metadata meta = msg.getMetadata();
        Optional<IncomingRabbitMQMetadata> incomingMeta = meta.get(IncomingRabbitMQMetadata.class);

        if (incomingMeta.isPresent()) {
            String routingKey = incomingMeta.get().getRoutingKey();
            Optional<String> replyTo = incomingMeta.get().getReplyTo();
            Optional<String> correlationId = incomingMeta.get().getCorrelationId();
            Object payload = msg.getPayload();

            switch (routingKey) {
                case "post.get.by.page.key" -> {
                    PageDto pageDto = mapper.convertValue(payload, PageDto.class);
                    int number = Integer.parseInt(pageDto.getNumber());
                    int size = Integer.parseInt(pageDto.getSize());
                    Uni<List<PostDto>> listDtoUni = service.getPostsByPage(pageDto).collect().asList();

                    return listDtoUni.flatMap(list -> {
                        PagedPostDto.Builder data = PagedPostDto.builder();
                        data.currentPage(number);
                        data.totalPages((int) Math.ceil((double) list.size() / size));
                        data.totalPosts(list.size());

                        return publisher.publish(replyTo, correlationId, data);
                    });
                }
                case "post.get.by.id.key" -> {
                    String id = payload.toString();
                    Uni<PostDto> postDtoUni = service.getPostById(id);

                    return postDtoUni.flatMap(postDto -> publisher.publish(replyTo, correlationId, postDto));
                }
                case "post.get.by.user.id.key" -> {
                    PageDto pageDto = mapper.convertValue(payload, PageDto.class);
                    int number = Integer.parseInt(pageDto.getNumber());
                    int size = Integer.parseInt(pageDto.getSize());
                    Uni<List<PostDto>> listDtoUni = service.getPostsByPage(pageDto).collect().asList();

                    return listDtoUni.flatMap(list -> {
                        PagedPostDto.Builder data = PagedPostDto.builder();
                        data.currentPage(number);
                        data.totalPages((int) Math.ceil((double) list.size() / size));
                        data.totalPosts(list.size());

                        return publisher.publish(replyTo, correlationId, data);
                    });
                }
                default -> Helper.ackVoidAndLog("Routing key do not match, ignoring the message!");
            }
        }

        return Helper.ackVoidAndLog("No incoming metadata provided, ignoring the message!");
    }

    @Incoming("PostTopicChannel")
    public Uni<Void> consumeTopic(Message<?> msg) {
        Metadata meta = msg.getMetadata();
        Optional<IncomingRabbitMQMetadata> incomingMeta = meta.get(IncomingRabbitMQMetadata.class);

        if (incomingMeta.isPresent()) {
            String routingKey = incomingMeta.get().getRoutingKey();
            Object payload = msg.getPayload();

            switch (routingKey) {
                case "post.add.key" -> {
                    PostDto postDto = mapper.convertValue(payload, PostDto.class);

                    return Helper.ackVoidAndLog(service.addPost(postDto));
                }
                case "post.patch.key" -> {
                    PostDto postDto = mapper.convertValue(payload, PostDto.class);

                    return Helper.ackVoidAndLog(service.patchPost(postDto));
                }
                case "post.delete.key" -> {
                    String id = payload.toString();

                    return Helper.ackVoidAndLog(service.deletePost(id));
                }
                default -> Helper.ackVoidAndLog("Routing key do not match, ignoring the message!");
            }
        }

        return Helper.ackVoidAndLog("No incoming metadata provided, ignoring the message!");
    }
}
