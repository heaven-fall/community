package com.example.demo.dto;

import lombok.Data;

import java.util.List;

@Data
public class PaginationDTO
{
  private List<QuestionDTO> questions;
  private Integer current;
  private Integer total;
}
