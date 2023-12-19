package com.sparta.nbcamptodo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Getter
@AllArgsConstructor
public class PageDto {

    private Integer page;
    private Integer size;

    public Pageable toPageable() {
        return PageRequest.of(page-1, size);
    }

}
