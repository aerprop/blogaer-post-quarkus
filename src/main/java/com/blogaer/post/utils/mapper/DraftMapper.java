package com.blogaer.post.utils.mapper;

import com.blogaer.post.dto.DraftDto;
import com.blogaer.post.entities.Draft;

public interface DraftMapper {

    DraftDto draftToDraftDto(Draft draft);

    Draft draftDtoToDraft(DraftDto draftDto);
}
