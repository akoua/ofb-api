package com.istic.ofbapi.mapper;

import com.istic.ofbapi.model.Sheet;
import com.istic.ofbapi.payload.SheetRequestOnPost;
import com.istic.ofbapi.payload.SheetRequestOnPut;
import com.istic.ofbapi.payload.SheetResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface SheetMapper {

    Sheet sheetRequestOnPostToSheet(SheetRequestOnPost sheetRequestOnPost);

    @Mapping(target = "userId", source = "user.id")
    SheetResponse sheetToSheetResponse(Sheet sheet);

    List<SheetResponse> sheetsToSheetResponses(List<Sheet> sheets);

    Sheet sheetRequestOnPutToSheet(SheetRequestOnPut sheetRequestOnPut);
    
}
