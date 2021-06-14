package com.company.springforum.dto;

import com.company.springforum.model.Tags;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MarksDTO {
    private int markId;
    private int articleId;
    private String articleTitle;
    private String articleContent;
    private String articleBgi;
    private Integer lookCount;
    private String avatar;
    private String username;
    private Long userId;
    private List<Tags> tags;
    private int CommentsCount;
}
