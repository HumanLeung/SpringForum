package com.company.springforum.dto;

import com.company.springforum.model.Tags;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ArticleDTO {
    private String articleTitle;
    private String articleContent;
    private String articleBgi;
    private List<Tags> tags;
}
