package com.example.demo.Controller;


import com.example.demo.Mapper.QuestionMapper;
import com.example.demo.Mapper.UserMapper;
import com.example.demo.dto.PaginationDTO;
import com.example.demo.dto.QuestionDTO;
import com.example.demo.model.Question;
import com.example.demo.model.User;
import com.example.demo.service.QuestionService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class IndexController
{
  @Autowired
  private UserMapper userMapper;
  @Autowired
  private QuestionMapper questionMapper;
  @Autowired
  private QuestionService questionService;
  
  @GetMapping("/")
  public String index(HttpServletRequest request, Model model, @RequestParam(name = "p", defaultValue = "1") Integer page, @RequestParam(name = "s", defaultValue = "5") Integer size)
  {
    Cookie[] cookies = request.getCookies();
    if (cookies == null)
    {
      return "index";
    }
    for (Cookie cookie : cookies)
    {
      if (cookie.getName().equals("token"))
      {
        User user = userMapper.findByToken(cookie.getValue());
        if (user != null)
        {
          request.getSession().setAttribute("user", user);
        }
        break;
      }
    }
    PaginationDTO list = questionService.list(page, size);
    model.addAttribute("pages", list);
    return "index";
  }
}
