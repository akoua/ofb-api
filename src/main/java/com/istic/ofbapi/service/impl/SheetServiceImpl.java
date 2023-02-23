package com.istic.ofbapi.service.impl;

import com.istic.ofbapi.exception.ResourceNotFoundException;
import com.istic.ofbapi.exception.UnauthorizedException;
import com.istic.ofbapi.mapper.SheetMapper;
import com.istic.ofbapi.model.Sheet;
import com.istic.ofbapi.model.User;
import com.istic.ofbapi.model.role.RoleName;
import com.istic.ofbapi.payload.*;
import com.istic.ofbapi.repository.SheetRepository;
import com.istic.ofbapi.repository.UserRepository;
import com.istic.ofbapi.security.UserPrincipal;
import com.istic.ofbapi.service.SheetService;
import com.istic.ofbapi.utils.AppUtils;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class SheetServiceImpl implements SheetService {
    private final Logger LOGGER = LoggerFactory.getLogger(SheetServiceImpl.class);
    private final SheetRepository sheetRepository;

    private final UserRepository userRepository;

    private final SheetMapper sheetMapper;

    @Override
    public SheetResponse createSheet(SheetRequestOnPost sheetRequestOnPost, UserPrincipal currentUser) {
        User user = userRepository.findById(currentUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException(USER, ID, currentUser.getId()));
        Sheet sheet = sheetMapper.sheetRequestOnPostToSheet(sheetRequestOnPost);
        sheet.setUser(user);
        LOGGER.info(sheet.getPhotoLinks().toString());
        return sheetMapper.sheetToSheetResponse(sheetRepository.save(sheet));
    }

    @Override
    public PagedResponse<SheetResponse> readAllSheets(Integer page, Integer size) {
        AppUtils.validatePageNumberAndSize(page, size);
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, CREATED_AT);
        Page<Sheet> sheetsPage = sheetRepository.findAll(pageable);
        return getSheetsFromSheetsPage(sheetsPage);
    }

    @Override
    public PagedResponse<SheetResponse> readSheetsByUser(Long userId, int page, int size) {
        AppUtils.validatePageNumberAndSize(page, size);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(USER, ID, userId));

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, CREATED_AT);

        Page<Sheet> sheetsPage = sheetRepository.findAllByUser(user, pageable);

        return getSheetsFromSheetsPage(sheetsPage);
    }

    private PagedResponse<SheetResponse> getSheetsFromSheetsPage(Page<Sheet> sheetsPage) {
        List<Sheet> sheets = sheetsPage.getNumberOfElements() == 0 ? Collections.emptyList() : sheetsPage.getContent();

        List<SheetResponse> content = sheetMapper.sheetsToSheetResponses(sheets);

        return new PagedResponse<>(content, sheetsPage.getNumber(), sheetsPage.getSize(), sheetsPage.getTotalElements(),
                sheetsPage.getTotalPages(), sheetsPage.isLast());
    }

    @Override
    public PagedResponse<SheetResponse> readSheetsByCampaign(Long campaignId, int page, int size) {

        return null;

    }

    @Override
    public PagedResponse<SheetResponse> readSheetsByUserAndCampaign(Long userId, Long campaignId, int page, int size) {
        return null;
    }

    @Override
    public SheetResponse readSheet(Long id) {
        Sheet sheet = sheetRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(CAMPAIGN, ID, id));
        return sheetMapper.sheetToSheetResponse(sheet);
    }

    @Override
    public SheetResponse updateSheet(Long id, SheetRequestOnPut sheetRequestOnPut, UserPrincipal currentUser) {
        Sheet sheet = sheetRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(SHEET, ID, id));
        if (sheet.getUser().getId().equals(currentUser.getId())
                || currentUser.getAuthorities().contains(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.toString()))) {
            Sheet updatedSheet = sheetMapper.sheetRequestOnPutToSheet(sheetRequestOnPut);
            sheet.setLongitude(updatedSheet.getLongitude());
            sheet.setLatitude(updatedSheet.getLatitude());
            sheet.setDescription(updatedSheet.getDescription());
            sheet.setPhotoLinks(updatedSheet.getPhotoLinks());
            return sheetMapper.sheetToSheetResponse(sheetRepository.save(sheet));
        }
        ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "You don't have permission to edit this sheet");
        throw new UnauthorizedException(apiResponse);
    }

    @Override
    public ApiResponse deleteSheet(Long id, UserPrincipal currentUser) {
        Sheet sheet = sheetRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(SHEET, ID, id));
        if (sheet.getUser().getId().equals(currentUser.getId())
                || currentUser.getAuthorities().contains(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.toString()))) {
            sheetRepository.deleteById(id);
            return new ApiResponse(Boolean.TRUE, "You successfully deleted sheet");
        }
        ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "You don't have permission to delete this sheet");
        throw new UnauthorizedException(apiResponse);
    }
}
