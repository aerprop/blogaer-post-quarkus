package com.blogaer.post.utils.mapper.impl;

import com.blogaer.post.dto.DraftDto;
import com.blogaer.post.entities.Draft;
import com.blogaer.post.utils.mapper.DraftMapper;
import com.blogaer.post.utils.mapper.PostMapper;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class DraftMapperImpl implements DraftMapper {

    @Inject
    PostMapper postMapper;

    @Override
    public DraftDto draftToDraftDto(Draft draft) {
        if (draft == null)
            return null;

        DraftDto.Builder draftDto = DraftDto.builder();
        draftDto.id(draft.getId());
        draftDto.userId(draft.getUserId());
        draftDto.title(draft.getTitle());
        draftDto.content(draft.getContent());
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
        draft.content(draftDto.getContent());
        draft.createdAt(draftDto.getCreatedAt());
        draft.updatedAt(draftDto.getUpdatedAt());

        return draft.build();
    }
}
