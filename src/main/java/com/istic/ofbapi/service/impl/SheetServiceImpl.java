package com.istic.ofbapi.service.impl;

import com.istic.ofbapi.exception.ResourceNotFoundException;
import com.istic.ofbapi.mapper.SheetMapper;
import com.istic.ofbapi.model.Sheet;
import com.istic.ofbapi.model.User;
import com.istic.ofbapi.payload.*;
import com.istic.ofbapi.repository.SheetRepository;
import com.istic.ofbapi.repository.UserRepository;
import com.istic.ofbapi.service.SheetService;
import com.istic.ofbapi.utils.AppUtils;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

import static com.istic.ofbapi.utils.AppConstants.*;

@Service
@AllArgsConstructor
public class SheetServiceImpl implements SheetService {
    private final SheetRepository sheetRepository;

    private final UserRepository userRepository;

    private final SheetMapper sheetMapper;

    @Override
    public SheetGetDto createSheet(SheetPostDto sheetPostDto) {
        userRepository.findById(sheetPostDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException(USER, ID, sheetPostDto.getUserId()));
        Sheet sheet = sheetMapper.sheetPostDtoToSheet(sheetPostDto);
        return sheetMapper.sheetToSheetGetDto(sheetRepository.save(sheet));
    }

    @Override
    public PagedResponse<SheetGetDto> readAllSheets(Integer page, Integer size) {
        AppUtils.validatePageNumberAndSize(page, size);
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, CREATED_AT);

        Page<Sheet> sheetsPage = sheetRepository.findAll(pageable);

        return getSheetsFromSheetsPage(sheetsPage);

    }

    @Override
    public PagedResponse<SheetGetDto> readSheetsByUser(Long userId, int page, int size) {
        AppUtils.validatePageNumberAndSize(page, size);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(USER, ID, userId));

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, CREATED_AT);

        Page<Sheet> sheetsPage = sheetRepository.findAllByUser(user, pageable);

        return getSheetsFromSheetsPage(sheetsPage);
    }

    private PagedResponse<SheetGetDto> getSheetsFromSheetsPage(Page<Sheet> sheetsPage) {
        List<Sheet> sheets = sheetsPage.getNumberOfElements() == 0 ? Collections.emptyList() : sheetsPage.getContent();

        List<SheetGetDto> content = sheetMapper.sheetsToSheetsGetDto(sheets);

        return new PagedResponse<>(content, sheetsPage.getNumber(), sheetsPage.getSize(), sheetsPage.getTotalElements(),
                sheetsPage.getTotalPages(), sheetsPage.isLast());
    }

    @Override
    public PagedResponse<SheetGetDto> readSheetsByCampaign(Long campaignId, int page, int size) {

        return null;

    }

    @Override
    public PagedResponse<SheetGetDto> readSheetsByUserAndCampaign(Long userId, Long campaignId, int page, int size) {
        return null;
    }

    @Override
    public SheetGetDto readSheet(Long id) {
        Sheet sheet = sheetRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(SHEET, ID, id));
        return sheetMapper.sheetToSheetGetDto(sheet);
    }

    @Override
    public SheetGetDto updateSheet(Long id, SheetPutDto sheetPutDto) {
        Sheet sheet = sheetRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(SHEET, ID, id));
        Sheet updatedSheet = sheetMapper.sheetPutDtoToSheet(sheetPutDto);
        sheet.setLongitude(updatedSheet.getLongitude());
        sheet.setLatitude(updatedSheet.getLatitude());
        sheet.setDescription(updatedSheet.getDescription());
        sheet.setPhotoLinks(updatedSheet.getPhotoLinks());
        return sheetMapper.sheetToSheetGetDto(sheetRepository.save(sheet));
    }

    @Override
    public ApiResponse deleteSheet(Long id) {
        Sheet sheet = sheetRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(SHEET, ID, id));
        sheetRepository.delete(sheet);
        return new ApiResponse(Boolean.TRUE, "You successfully deleted sheet");
    }
}
