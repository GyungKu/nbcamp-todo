package com.sparta.nbcamptodo.entity;

import jakarta.persistence.*;

@Entity
public class Comment {

    @Id @GeneratedValue
    private Long id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Todo todo;

}
