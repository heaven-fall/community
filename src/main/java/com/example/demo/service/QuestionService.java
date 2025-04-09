package com.example.demo.service;

import com.example.demo.Mapper.QuestionMapper;
import com.example.demo.Mapper.UserMapper;
import com.example.demo.dto.PaginationDTO;
import com.example.demo.dto.QuestionDTO;
import com.example.demo.model.Question;
import com.example.demo.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService
{
  @Autowired
  private UserMapper userMapper;
  @Autowired
  private QuestionMapper questionMapper;
  public PaginationDTO list(Integer page, Integer size)
  {
    List<Question> questionList = questionMapper.list(size * (page - 1), size);
    List<QuestionDTO> questionDTOList = new ArrayList<>();
    for (Question question : questionList)
    {
      User user = userMapper.findById(question.getCreator());
      QuestionDTO questionDTO = new QuestionDTO();
      BeanUtils.copyProperties(question, questionDTO);
      questionDTO.setUser(user);
      questionDTOList.add(questionDTO);
    }
    PaginationDTO pages = new PaginationDTO();
    pages.setQuestions(questionDTOList);
    pages.setCurrent(page);
    pages.setTotal((int)Math.ceil((double) questionMapper.count() / size));
    return pages;
  }
}
