package com.example.demo.dto;

import lombok.Data;

@Data
public class GiteeUser
{
  private String name;
  private Long id;
  private String bio;
  private String avatarUrl;
}
