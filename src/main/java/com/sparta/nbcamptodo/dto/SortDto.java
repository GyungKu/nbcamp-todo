package com.sparta.nbcamptodo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SortDto {

    private String sortBy;
    private boolean isAsc;

}
