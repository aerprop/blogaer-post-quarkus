package com.blogaer.post.utils.mapper.impl;

import java.util.stream.Collectors;

import org.bson.Document;

import com.blogaer.post.dto.DraftDto;
import com.blogaer.post.entities.Draft;
import com.blogaer.post.utils.mapper.DraftMapper;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class DraftMapperImpl implements DraftMapper {

    @Inject
    ObjectMapper mapper;

    @Override
    public DraftDto draftToDraftDto(Draft draft) {
        if (draft == null)
            return null;

        DraftDto.Builder draftDto = DraftDto.builder();
        draftDto.id(draft.getId());
        draftDto.userId(draft.getUserId());
        draftDto.title(draft.getTitle());
        draftDto.content(draft.getContent().stream().map(content -> (Object) content).collect(Collectors.toList()));
        draftDto.createdAt(draft.getCreatedAt());
        draftDto.updatedAt(draft.getUpdatedAt());

        return draftDto.build();
    }

    @Override
    public Draft draftDtoToDraft(DraftDto draftDto) {
        if (draftDto == null)
            return null;

        Draft.Builder draft = Draft.builder();
        draft.id(draftDto.getId());
        draft.userId(draftDto.getUserId());
        draft.title(draftDto.getTitle());
        draft.content(draftDto.getContent().stream().map(content -> (Document) content).collect(Collectors.toList()));
        draft.createdAt(draftDto.getCreatedAt());
        draft.updatedAt(draftDto.getUpdatedAt());

        return draft.build();
    }
}
