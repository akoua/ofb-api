package com.istic.ofbapi.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class CommentRequestOnPost {

    @NotNull
    private Long sheetId;

    @NotBlank
    private String content;
}
