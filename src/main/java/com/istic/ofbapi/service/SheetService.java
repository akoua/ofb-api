package com.istic.ofbapi.service;

import com.istic.ofbapi.payload.*;


public interface SheetService {
    SheetGetDto createSheet(SheetPostDto sheetPostDto);

    PagedResponse<SheetGetDto> readAllSheets(Integer page, Integer size);

    PagedResponse<SheetGetDto> readSheetsByUser(Long userId, int page, int size);

    PagedResponse<SheetGetDto> readSheetsByCampaign(Long campaignId, int page, int size);

    PagedResponse<SheetGetDto> readSheetsByUserAndCampaign(Long userId, Long campaignId, int page, int size);

    SheetGetDto readSheet(Long id);

    SheetGetDto updateSheet(Long id, SheetPutDto sheetPutDto);

    ApiResponse deleteSheet(Long id);
}
