package com.example.demo.dto;

import lombok.Data;
import com.example.demo.model.User;

@Data
public class QuestionDTO
{
  private int id;
  private String title;
  private String description;
  private String tag;
  private Long gmtCreate;
  private Long gmtModified;
  private Integer creator;
  private Integer viewCount;
  private Integer commentCount;
  private Integer likeCount;
  private User user;
}
