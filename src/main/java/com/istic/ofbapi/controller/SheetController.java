package com.istic.ofbapi.controller;

import com.istic.ofbapi.payload.PagedResponse;
import com.istic.ofbapi.payload.SheetGetDto;
import com.istic.ofbapi.payload.SheetPostDto;
import com.istic.ofbapi.service.SheetService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.istic.ofbapi.utils.AppConstants;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/sheets")
@AllArgsConstructor
public class SheetController {

    private final SheetService sheetService;

    @PostMapping
    private ResponseEntity<SheetGetDto> createSheet(@RequestBody @Valid SheetPostDto sheet) {
        return new ResponseEntity<>(sheetService.createSheet(sheet), HttpStatus.CREATED);
    }

    @GetMapping
    private ResponseEntity<PagedResponse<SheetGetDto>> readAllSheets(@RequestParam(value = "page", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
                                                                     @RequestParam(value = "size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size,
                                                                     @RequestParam(value = "userId", required = false) Long userId,
                                                                     @RequestParam(value = "campaignId", required = false) Long campaignId) {

        if (userId != null && campaignId != null) {
            return new ResponseEntity<>(sheetService.readSheetsByUserAndCampaign(userId, campaignId, page, size), HttpStatus.OK);
        } else if (userId != null) {
            return new ResponseEntity<>(sheetService.readSheetsByUser(userId, page, size), HttpStatus.OK);
        } else if (campaignId != null) {
            return new ResponseEntity<>(sheetService.readSheetsByCampaign(campaignId, page, size), HttpStatus.OK);
        }
        return new ResponseEntity<>(sheetService.readAllSheets(page, size), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    private ResponseEntity<SheetGetDto> readSheet(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(sheetService.readSheet(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    private ResponseEntity<SheetGetDto> updateSheet(@PathVariable(name = "id") Long id, @RequestBody @Valid SheetPostDto sheet) {
        return new ResponseEntity<>(sheetService.updateSheet(id, sheet), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    private void deleteSheet(@PathVariable(name = "id") Long id) {
        sheetService.deleteSheet(id);
    }
}
