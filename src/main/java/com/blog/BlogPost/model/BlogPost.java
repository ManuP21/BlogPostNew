package com.blog.BlogPost.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "blog_post")
public class BlogPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "body")
    private String body;

    @Column(name = "tags")
    private String tags;
}
