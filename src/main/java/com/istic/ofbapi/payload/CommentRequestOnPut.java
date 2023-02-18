package com.istic.ofbapi.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class CommentRequestOnPut {

    @NotBlank
    private String content;
}
