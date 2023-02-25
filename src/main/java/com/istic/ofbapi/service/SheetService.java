//package com.istic.ofbapi.service;
//
//import com.istic.ofbapi.payload.*;
//import com.istic.ofbapi.security.UserPrincipal;
//
//
//public interface SheetService {
//    SheetResponse createSheet(SheetRequestOnPost sheetRequestOnPost, UserPrincipal currentUser);
//
//    PagedResponse<SheetResponse> readAllSheets(Integer page, Integer size);
//
//    PagedResponse<SheetResponse> readSheetsByUser(Long userId, int page, int size);
//
//    PagedResponse<SheetResponse> readSheetsByCampaign(Long campaignId, int page, int size);
//
//    PagedResponse<SheetResponse> readSheetsByUserAndCampaign(Long userId, Long campaignId, int page, int size);
//
//    SheetResponse readSheet(Long id);
//
//    SheetResponse updateSheet(Long id, SheetRequestOnPut sheetRequestOnPut, UserPrincipal currentUser);
//
//    ApiResponse deleteSheet(Long id, UserPrincipal currentUser);
//}
