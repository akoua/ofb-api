//package com.istic.ofbapi.service;
//
//import com.istic.ofbapi.payload.*;
//import com.istic.ofbapi.security.UserPrincipal;
//
//public interface CommentService {
//    CommentResponse createComment(CommentRequestOnPost commentRequestOnPost, UserPrincipal currentUser);
//
//    PagedResponse<CommentResponse> readSheetComments(Long sheetId, Integer page, Integer size);
//
//    CommentResponse updateComment(Long id, CommentRequestOnPut commentRequestOnPut, UserPrincipal currentUser);
//
//    ApiResponse deleteComment(Long id, UserPrincipal currentUser);
//}
