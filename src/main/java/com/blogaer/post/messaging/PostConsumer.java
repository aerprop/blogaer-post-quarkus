package com.blogaer.post.messaging;

import java.text.MessageFormat;
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
    public Uni<Void> consumePostRpc(Message<byte[]> msg) {
        Metadata meta = msg.getMetadata();
        Optional<IncomingRabbitMQMetadata> incomingMeta = meta.get(IncomingRabbitMQMetadata.class);

        if (incomingMeta.isPresent()) {
            String routingKey = incomingMeta.get().getRoutingKey();
            Optional<String> replyTo = incomingMeta.get().getReplyTo();
            Optional<String> correlationId = incomingMeta.get().getCorrelationId();
            byte[] payload = msg.getPayload();

            try {
                switch (routingKey) {
                    case "post.add.key" -> {
                        PostDto postDto = mapper.readValue(payload, PostDto.class);

                        return Helper.invokeAndLog(service.addPost(postDto)
                                .flatMap(postId -> publisher.publish(replyTo, correlationId, postId)));
                    }
                    case "post.get.by.page.key" -> {
                        PageDto pageDto = mapper.readValue(payload, PageDto.class);
                        int number = Integer.parseInt(pageDto.getNumber());
                        int size = Integer.parseInt(pageDto.getSize());
                        Uni<List<PostDto>> listUni = service.getPostsByPage(pageDto).collect().asList();

                        return listUni.flatMap(list -> {
                            PagedPostDto.Builder dataBuilder = PagedPostDto.builder();
                            dataBuilder.currentPage(number);
                            dataBuilder.totalPages((int) Math.ceil((double) list.size() / size));
                            dataBuilder.totalPosts(list.size());
                            dataBuilder.posts(list);
                            PagedPostDto data = dataBuilder.build();

                            return publisher.publish(replyTo, correlationId, data);
                        });
                    }
                    case "post.get.by.id.key" -> {
                        String id = mapper.readValue(payload, String.class);
                        Uni<PostDto> postDtoUni = service.getPostById(id);

                        return postDtoUni.flatMap(postDto -> {
                            return publisher.publish(replyTo, correlationId, postDto);
                        });
                    }
                    case "post.get.by.user.id.key" -> {
                        PageDto pageDto = mapper.readValue(payload, PageDto.class);
                        int number = Integer.parseInt(pageDto.getNumber());
                        int size = Integer.parseInt(pageDto.getSize());
                        Uni<List<PostDto>> listUni = service.getPostsByPage(pageDto).collect().asList();

                        return listUni.flatMap(list -> {
                            PagedPostDto.Builder dataBuilder = PagedPostDto.builder();
                            dataBuilder.currentPage(number);
                            dataBuilder.totalPages((int) Math.ceil((double) list.size() / size));
                            dataBuilder.totalPosts(list.size());
                            dataBuilder.posts(list);
                            PagedPostDto data = dataBuilder.build();

                            return publisher.publish(replyTo, correlationId, data);
                        });
                    }
                    default -> Helper.invokeAndLog("POST - Routing key do not match, ignoring the message!");
                }
            } catch (Exception e) {
                String errMsg = MessageFormat.format(
                        "Message type >> RPC | Routing key >> {0} | Payload >> {1} | Error message >> {2}",
                        routingKey,
                        payload,
                        e.getMessage());
                Uni<String> errUni = Uni.createFrom().failure(new RuntimeException(errMsg, e));
                e.printStackTrace();

                return Helper.invokeAndLog(errUni);
            }
        }
        return Helper.invokeAndLog("POST - No incoming metadata provided, ignoring the message!");
    }

    @Incoming("PostTopicChannel")
    public Uni<Void> consumeTopic(Message<byte[]> msg) {
        Metadata meta = msg.getMetadata();
        Optional<IncomingRabbitMQMetadata> incomingMeta = meta.get(IncomingRabbitMQMetadata.class);

        if (incomingMeta.isPresent()) {
            String routingKey = incomingMeta.get().getRoutingKey();
            byte[] payload = msg.getPayload();

            try {
                switch (routingKey) {
                    case "post.patch.key" -> {
                        PostDto postDto = mapper.readValue(payload, PostDto.class);
                        System.out.println("POST ID >> " + postDto.getId());

                        return Helper.invokeAndLog(service.patchPost(postDto));
                    }
                    case "post.delete.key" -> {
                        String id = mapper.readValue(payload, String.class);

                        return Helper.invokeAndLog(service.deletePost(id));
                    }
                    default -> Helper.invokeAndLog("POST - Routing key do not match, ignoring the message!");
                }
            } catch (Exception e) {
                String errMsg = MessageFormat.format(
                        "Message type >> Topic | Routing key >> {0} | Payload >> {1} | Error message >> {2}",
                        routingKey,
                        payload,
                        e.getMessage());
                Uni<String> errUni = Uni.createFrom().failure(new RuntimeException(errMsg, e));
                e.printStackTrace();

                return Helper.invokeAndLog(errUni);
            }
        }

        return Helper.invokeAndLog("POST - No incoming metadata provided, ignoring the message!");
    }
}
