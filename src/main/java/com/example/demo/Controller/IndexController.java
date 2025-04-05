package com.example.demo.Controller;


import com.example.demo.Mapper.UserMapper;
import com.example.demo.model.User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController
{
  @Autowired
  private UserMapper userMapper;
  
  @GetMapping("/")
  public String index(HttpServletRequest request, Model model)
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
    return "index";
  }
}
