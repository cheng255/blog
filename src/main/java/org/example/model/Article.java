package org.example.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * 文章类
 * @author nuonuo
 * @create 2021-01-15 16:17
 */
@Setter
@Getter
@ToString
public class Article {
    private Integer id;
    private String title;
    private String content;
    private Date createTime;
    private Integer viewCount;
    private Integer userId;
}
