package com.blogaer.post.repositories;

import com.blogaer.post.dto.request.PageDto;
import com.blogaer.post.entities.Draft;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

public interface DraftRepo {

    Multi<Draft> getDraftsByPage(PageDto pageDto);
    Uni<Draft> getDraftById(String id);
    Uni<String> addDraft(Draft draft);
    Uni<String> patchDraft(Draft draft);
    Uni<String> deleteDraft(String id);
}
