package com.sparta.nbcamptodo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TodoImage extends BaseEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String originName;

    private String saveName;

    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    private Todo todo;

    public TodoImage(String originName, String saveName, String url, Todo todo) {
        this.originName = originName;
        this.saveName = saveName;
        this.url = url;
        this.todo = todo;
    }
}
