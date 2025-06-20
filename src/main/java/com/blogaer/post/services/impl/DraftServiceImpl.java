package com.blogaer.post.services.impl;

import com.blogaer.post.dto.DraftDto;
import com.blogaer.post.dto.request.PageDto;
import com.blogaer.post.repositories.DraftRepo;
import com.blogaer.post.services.DraftService;
import com.blogaer.post.utils.mapper.DraftMapper;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class DraftServiceImpl implements DraftService {

    @Inject
    DraftRepo repo;

    @Inject
    DraftMapper mapper;

    @Override
    public Multi<DraftDto> getDraftsByPage(PageDto pageDto) {
        return repo.getDraftsByPage(pageDto).map(mapper::draftToDraftDto);
    }

    @Override
    public Uni<DraftDto> getDraftById(String id) {
        return repo.getDraftById(id).map(mapper::draftToDraftDto);
    }

    @Override
    public Uni<String> addDraft(DraftDto draftDto) {
        return repo.addDraft(mapper.draftDtoToDraft(draftDto));
    }

    @Override
    public Uni<String> patchDraft(DraftDto draftDto) {
        return repo.patchDraft(mapper.draftDtoToDraft(draftDto));
    }

    @Override
    public Uni<String> deleteDraft(String id) {
        return repo.deleteDraft(id);
    }
}
