package com.istic.ofbapi.service.impl;

import com.istic.ofbapi.mapper.SheetMapper;
import com.istic.ofbapi.model.Sheet;
import com.istic.ofbapi.payload.PagedResponse;
import com.istic.ofbapi.payload.SheetGetDto;
import com.istic.ofbapi.payload.SheetPostDto;
import com.istic.ofbapi.repository.SheetRepository;
import com.istic.ofbapi.service.SheetService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.istic.ofbapi.utils.AppConstants.CREATED_AT;

@Service
@AllArgsConstructor
public class SheetServiceImpl implements SheetService {
    private final SheetRepository sheetRepository;

    private final SheetMapper sheetMapper;

    @Override
    public SheetGetDto createSheet(SheetPostDto sheetPostDto) {
        Sheet sheet = sheetMapper.sheetPostDtoToSheet(sheetPostDto);
        return sheetMapper.sheetToSheetGetDto(sheetRepository.save(sheet));
    }

    @Override
    public PagedResponse<SheetGetDto> readAllSheets(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, CREATED_AT);

        Page<Sheet> sheetsPage = sheetRepository.findAll(pageable);

        return getSheetsFromSheetsPage(sheetsPage);

    }

    @Override
    public PagedResponse<SheetGetDto> readSheetsByUser(Long userId, int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, CREATED_AT);

        Page<Sheet> sheetsPage = sheetRepository.findAllByUserId(userId, pageable);

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

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, CREATED_AT);

        Page<Sheet> sheetsPage = sheetRepository.findAll(pageable);

        return getSheetsFromSheetsPage(sheetsPage);

    }

    @Override
    public SheetGetDto readSheet(Long id) {
        Optional<Sheet> sheetOptional = sheetRepository.findById(id);
        return sheetOptional.map(sheetMapper::sheetToSheetGetDto).orElse(null);
    }

    @Override
    public SheetGetDto updateSheet(Long id, SheetPostDto sheetPostDto) {
        Sheet updatedSheet = sheetMapper.sheetPostDtoToSheet(sheetPostDto);
        Optional<Sheet> sheetOptional = sheetRepository.findById(id);
        if (sheetOptional.isPresent()) {
            Sheet sheet = sheetOptional.get();
            sheet.setLongitude(updatedSheet.getLongitude());
            sheet.setLatitude(updatedSheet.getLatitude());
            sheet.setDescription(updatedSheet.getDescription());
            sheet.setPhotoLinks(updatedSheet.getPhotoLinks());
            return sheetMapper.sheetToSheetGetDto(sheetRepository.save(sheet));
        }
        return null;
    }

    @Override
    public void deleteSheet(Long id) {
        sheetRepository.deleteById(id);
    }
}
