package com.BlogApp.demo.demo.Dtos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BlogDto {

    private Long id;

    private String title;

    private String content;

    private String imgurl;
}
