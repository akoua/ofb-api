package com.istic.ofbapi.mapper;

import com.istic.ofbapi.model.Comment;
import com.istic.ofbapi.payload.CommentRequestOnPost;
import com.istic.ofbapi.payload.CommentResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CommentMapper {

    @Mapping(target = "sheet.id", source = "sheetId")
    Comment commentRequestToComment(CommentRequestOnPost commentRequestOnPost);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "sheetId", source = "sheet.id")
    CommentResponse commentToCommentResponse(Comment comment);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "sheetId", source = "sheet.id")
    List<CommentResponse> commentsToCommentResponses(List<Comment> comments);
}
