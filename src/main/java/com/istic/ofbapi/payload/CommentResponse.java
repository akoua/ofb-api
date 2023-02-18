package com.istic.ofbapi.payload;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class CommentResponse {

    private Long id;

    private Long userId;

    private Long sheetId;

    private String content;

    private Date createdAt;

    private Date updatedAt;

}
