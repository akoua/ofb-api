//package com.istic.ofbapi.controller;
//
//import com.istic.ofbapi.payload.*;
//import com.istic.ofbapi.security.CurrentUser;
//import com.istic.ofbapi.service.SheetService;
//import com.istic.ofbapi.utils.AppConstants;
//import io.swagger.v3.oas.annotations.security.SecurityRequirement;
//import lombok.AllArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import javax.validation.Valid;
//
//@SecurityRequirement(name = "bearerAuth")
//@RestController
//@RequestMapping("/api")
//@AllArgsConstructor
//public class SheetController {
//
//    private final SheetService sheetService;
//
////    @PostMapping("/v1/sheets")
////    private ResponseEntity<SheetResponse> createSheet(@RequestBody @Valid SheetRequestOnPost sheet, @CurrentUser UserPrincipal currentUser) {
////        return new ResponseEntity<>(sheetService.createSheet(sheet, currentUser), HttpStatus.CREATED);
////    }
//
//    @GetMapping("/v1/sheets")
//    private ResponseEntity<PagedResponse<SheetResponse>> readAllSheets(@RequestParam(value = "page", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
//                                                                       @RequestParam(value = "size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size,
//                                                                       @RequestParam(value = "userId", required = false) Long userId,
//                                                                       @RequestParam(value = "campaignId", required = false) Long campaignId) {
//        if (userId != null && campaignId != null) {
//            return new ResponseEntity<>(sheetService.readSheetsByUserAndCampaign(userId, campaignId, page, size), HttpStatus.OK);
//        } else if (userId != null) {
//            return new ResponseEntity<>(sheetService.readSheetsByUser(userId, page, size), HttpStatus.OK);
//        } else if (campaignId != null) {
//            return new ResponseEntity<>(sheetService.readSheetsByCampaign(campaignId, page, size), HttpStatus.OK);
//        }
//        return new ResponseEntity<>(sheetService.readAllSheets(page, size), HttpStatus.OK);
//    }
//
//    @GetMapping("/v1/sheets/{id}")
//    private ResponseEntity<SheetResponse> readSheet(@PathVariable(name = "id") Long id) {
//        return new ResponseEntity<>(sheetService.readSheet(id), HttpStatus.OK);
//    }
//
////    @PutMapping("/v1/sheets/{id}")
////    private ResponseEntity<SheetResponse> updateSheet(@PathVariable(name = "id") Long id, @RequestBody @Valid SheetRequestOnPut sheet, @CurrentUser UserPrincipal currentUser) {
////        return new ResponseEntity<>(sheetService.updateSheet(id, sheet, currentUser), HttpStatus.OK);
////    }
////
////    @DeleteMapping("/v1/sheets/{id}")
////    private ResponseEntity<ApiResponse> deleteSheet(@PathVariable(name = "id") Long id, @CurrentUser UserPrincipal currentUser) {
////        return new ResponseEntity<>(sheetService.deleteSheet(id, currentUser), HttpStatus.OK);
////    }
//}
