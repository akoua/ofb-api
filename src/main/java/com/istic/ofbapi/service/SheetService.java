package com.istic.ofbapi.service;

import com.istic.ofbapi.payload.PagedResponse;
import com.istic.ofbapi.payload.SheetGetDto;
import com.istic.ofbapi.payload.SheetPostDto;
import org.springframework.http.ResponseEntity;


public interface SheetService {
    SheetGetDto createSheet(SheetPostDto sheetPostDto);

    PagedResponse<SheetGetDto> readAllSheets(Integer page, Integer size);

    PagedResponse<SheetGetDto> readSheetsByUser(Long userId, int page, int size);

    PagedResponse<SheetGetDto> readSheetsByCampaign(Long campaignId, int page, int size);

    PagedResponse<SheetGetDto> readSheetsByUserAndCampaign(Long userId, Long campaignId, int page, int size);

    SheetGetDto readSheet(Long id);

    SheetGetDto updateSheet(Long id, SheetPostDto sheetPostDto);

    void deleteSheet(Long id);
}
