package com.blogaer.post.services;

import com.blogaer.post.dto.DraftDto;
import com.blogaer.post.dto.request.PageDto;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

public interface DraftService {

    Multi<DraftDto> getDraftsByPage(PageDto pageDto);
    Uni<DraftDto> getDraftById(String id);
    Uni<String> addDraft(DraftDto draftDto);
    Uni<String> patchDraft(DraftDto draftDto);
    Uni<String> deleteDraft(String id);
}
