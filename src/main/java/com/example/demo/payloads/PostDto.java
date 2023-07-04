package com.example.demo.payloads;

import java.util.HashSet;
import java.util.Set;

import com.example.demo.entities.Comment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {

    private Integer postId;

    private String title;

    private String content;

    private String imageName;

    private CategoryDto category;

    private UserDto user;

    private Set<Comment> comments=new HashSet<>();
}
