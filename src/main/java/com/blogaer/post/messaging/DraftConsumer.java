package com.blogaer.post.messaging;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.eclipse.microprofile.reactive.messaging.Metadata;

import com.blogaer.post.dto.DraftDto;
import com.blogaer.post.dto.request.PageDto;
import com.blogaer.post.dto.response.PagedDraftDto;
import com.blogaer.post.services.DraftService;
import com.blogaer.post.utils.helper.Helper;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.smallrye.mutiny.Uni;
import io.smallrye.reactive.messaging.rabbitmq.IncomingRabbitMQMetadata;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class DraftConsumer {

    @Inject
    ObjectMapper mapper;

    @Inject
    DraftService service;

    @Inject
    MsgPublisher publisher;

    @Incoming("DraftRpcChannel")
    public Uni<Void> consumeDraftRpc(Message<byte[]> msg) {
        Metadata meta = msg.getMetadata();
        Optional<IncomingRabbitMQMetadata> incomingMeta = meta.get(IncomingRabbitMQMetadata.class);

        if (incomingMeta.isPresent()) {
            String routingKey = incomingMeta.get().getRoutingKey();
            Optional<String> replyTo = incomingMeta.get().getReplyTo();
            Optional<String> correlationId = incomingMeta.get().getCorrelationId();
            byte[] payload = msg.getPayload();

            try {
                switch (routingKey) {
                    case "draft.add.key" -> {
                        DraftDto draftDto = mapper.readValue(payload, DraftDto.class);

                        return Helper.invokeAndLog(service.addDraft(draftDto)
                                .flatMap(draftId -> publisher.publish(replyTo, correlationId, draftId)));
                    }
                    case "draft.get.by.id.key" -> {
                        String id = mapper.readValue(payload, String.class);
                        Uni<DraftDto> draftDtoUni = service.getDraftById(id);

                        return draftDtoUni.flatMap(draftDto -> publisher.publish(replyTo, correlationId, draftDto));
                    }
                    case "draft.get.by.user.id.key" -> {
                        PageDto pageDto = mapper.readValue(payload, PageDto.class);
                        int number = Integer.parseInt(pageDto.getNumber());
                        int size = Integer.parseInt(pageDto.getSize());
                        Uni<List<DraftDto>> listUni = service.getDraftsByPage(pageDto).collect().asList();

                        return listUni.flatMap(list -> {
                            PagedDraftDto.Builder dataBuilder = PagedDraftDto.builder();
                            dataBuilder.currentPage(number);
                            dataBuilder.totalPages((int) Math.ceil((double) list.size() / size));
                            dataBuilder.totalDrafts(list.size());
                            dataBuilder.drafts(list);
                            PagedDraftDto data = dataBuilder.build();

                            return publisher.publish(replyTo, correlationId, data);
                        });
                    }
                    default -> Helper.invokeAndLog("DRAFT - Routing key doesn't match, ignoring the message!");
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
        return Helper.invokeAndLog("DRAFT - No incoming metadata provided, ignoring the message!");
    }

    @Incoming("DraftTopicChannel")
    public Uni<Void> consumeTopic(Message<byte[]> msg) {
        Metadata meta = msg.getMetadata();
        Optional<IncomingRabbitMQMetadata> incomingMeta = meta.get(IncomingRabbitMQMetadata.class);

        if (incomingMeta.isPresent()) {
            String routingKey = incomingMeta.get().getRoutingKey();
            byte[] payload = msg.getPayload();

            try {
                switch (routingKey) {
                    case "draft.patch.key" -> {
                        DraftDto draftDto = mapper.readValue(payload, DraftDto.class);

                        return Helper.invokeAndLog(service.patchDraft(draftDto));
                    }
                    case "draft.delete.key" -> {
                        String id = mapper.readValue(payload, String.class);

                        return Helper.invokeAndLog(service.deleteDraft(id));
                    }
                    default -> Helper.invokeAndLog("DRAFT - Routing key do not match, ignoring the message!");
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

        return Helper.invokeAndLog("DRAFT - No incoming metadata provided, ignoring the message!");
    }
}
