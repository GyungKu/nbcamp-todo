package com.sparta.nbcamptodo.dto;

import com.sparta.nbcamptodo.entity.TodoImage;
import lombok.Getter;

@Getter
public class TodoImageDto {

    private Long id;
    private String originName;
    private String saveName;
    private String url;

    public TodoImageDto(TodoImage todoImage) {
        this.id = todoImage.getId();
        this.originName = todoImage.getOriginName();
        this.saveName = todoImage.getSaveName();
        this.url = todoImage.getUrl();
    }
}
