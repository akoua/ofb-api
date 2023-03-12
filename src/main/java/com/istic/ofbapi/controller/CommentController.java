package com.istic.ofbapi.controller;

import com.istic.ofbapi.payload.*;
import com.istic.ofbapi.security.CurrentUser;
import com.istic.ofbapi.security.UserPrincipal;
import com.istic.ofbapi.service.CommentService;
import com.istic.ofbapi.utils.AppConstants;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/v1/comments")
    private ResponseEntity<CommentResponse> createComment(@RequestBody @Valid CommentRequestOnPost commentRequestOnPost, @CurrentUser UserPrincipal currentUser) {
        return new ResponseEntity<>(commentService.createComment(commentRequestOnPost, currentUser), HttpStatus.CREATED);
    }

    @GetMapping("/v1/sheets/{id}/comments")
    private ResponseEntity<PagedResponse<CommentResponse>> readSheetComments(@PathVariable(name = "id") Long postId, @RequestParam(value = "page", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
                                                                             @RequestParam(value = "size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size) {
        return new ResponseEntity<>(commentService.readSheetComments(postId, page, size), HttpStatus.OK);
    }

    @PutMapping("/v1/comments/{id}")
    private ResponseEntity<CommentResponse> updateComment(@PathVariable(name = "id") Long id, @RequestBody @Valid CommentRequestOnPut commentRequestOnPut, @CurrentUser UserPrincipal currentUser) {
        return new ResponseEntity<>(commentService.updateComment(id, commentRequestOnPut, currentUser), HttpStatus.OK);
    }

    @DeleteMapping("/v1/comments/{id}")
    private ResponseEntity<ApiResponse> deleteComment(@PathVariable(name = "id") Long id, @CurrentUser UserPrincipal currentUser) {
        return new ResponseEntity<>(commentService.deleteComment(id, currentUser), HttpStatus.OK);
    }


}
