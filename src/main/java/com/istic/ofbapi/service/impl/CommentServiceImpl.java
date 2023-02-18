package com.istic.ofbapi.service.impl;

import com.istic.ofbapi.exception.ResourceNotFoundException;
import com.istic.ofbapi.exception.UnauthorizedException;
import com.istic.ofbapi.mapper.CommentMapper;
import com.istic.ofbapi.model.Comment;
import com.istic.ofbapi.model.Sheet;
import com.istic.ofbapi.model.User;
import com.istic.ofbapi.model.role.RoleName;
import com.istic.ofbapi.payload.*;
import com.istic.ofbapi.repository.CommentRepository;
import com.istic.ofbapi.repository.SheetRepository;
import com.istic.ofbapi.repository.UserRepository;
import com.istic.ofbapi.security.UserPrincipal;
import com.istic.ofbapi.service.CommentService;
import com.istic.ofbapi.utils.AppUtils;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

import static com.istic.ofbapi.utils.AppConstants.*;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final UserRepository userRepository;

    private final SheetRepository sheetRepository;

    private final CommentMapper commentMapper;

    @Override
    public CommentResponse createComment(CommentRequestOnPost commentRequestOnPost, UserPrincipal currentUser) {
        if (sheetRepository.existsById(commentRequestOnPost.getSheetId())) {
            Comment comment = commentMapper.commentRequestToComment(commentRequestOnPost);
            User user = userRepository.findById(currentUser.getId())
                    .orElseThrow(() -> new ResourceNotFoundException(USER, ID, currentUser.getId()));
            comment.setUser(user);
            return commentMapper.commentToCommentResponse(commentRepository.save(comment));
        }
        throw new ResourceNotFoundException(SHEET, ID, commentRequestOnPost.getSheetId());
    }

    @Override
    public PagedResponse<CommentResponse> readSheetComments(Long sheetId, Integer page, Integer size) {
        Sheet sheet = sheetRepository.findById(sheetId)
                .orElseThrow(() -> new ResourceNotFoundException(SHEET, ID, sheetId));

        AppUtils.validatePageNumberAndSize(page, size);
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, CREATED_AT);
        Page<Comment> commentsPage = commentRepository.findAllBySheet(sheet, pageable);
        List<Comment> comments = commentsPage.getNumberOfElements() == 0 ? Collections.emptyList() : commentsPage.getContent();

        List<CommentResponse> content = commentMapper.commentsToCommentResponses(comments);

        return new PagedResponse<>(content, commentsPage.getNumber(), commentsPage.getSize(), commentsPage.getTotalElements(),
                commentsPage.getTotalPages(), commentsPage.isLast());
    }

    @Override
    public CommentResponse updateComment(Long id, CommentRequestOnPut commentRequestOnPut, UserPrincipal currentUser) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(COMMENT, ID, id));
        if (comment.getUser().getId().equals(currentUser.getId()) || currentUser.getAuthorities().contains(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.toString()))) {
            comment.setContent(commentRequestOnPut.getContent());
            return commentMapper.commentToCommentResponse(commentRepository.save(comment));
        }
        ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "You don't have permission to edit this comment");
        throw new UnauthorizedException(apiResponse);
    }

    @Override
    public ApiResponse deleteComment(Long id, UserPrincipal currentUser) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(COMMENT, ID, id));
        if (comment.getUser().getId().equals(currentUser.getId())
                || currentUser.getAuthorities().contains(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.toString()))) {
            commentRepository.deleteById(id);
            return new ApiResponse(Boolean.TRUE, "You successfully deleted sheet");
        }
        ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "You don't have permission to delete this sheet");
        throw new UnauthorizedException(apiResponse);
    }
}
