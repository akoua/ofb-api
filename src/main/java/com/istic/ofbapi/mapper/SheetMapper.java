package com.istic.ofbapi.mapper;

import com.istic.ofbapi.model.Sheet;
import com.istic.ofbapi.payload.SheetGetDto;
import com.istic.ofbapi.payload.SheetPostDto;
import com.istic.ofbapi.repository.SheetRepository;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface SheetMapper {

    @Mapping(target = "user.id", source = "userId")
    Sheet sheetPostDtoToSheet(SheetPostDto sheetPostDto);

    @Mapping(target = "userId", source = "user.id")
    SheetGetDto sheetToSheetGetDto(Sheet sheet);

    List<SheetGetDto> sheetsToSheetsGetDto(List<Sheet> sheets);


}
