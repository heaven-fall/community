package com.example.demo.Controller;

import com.example.demo.Mapper.QuestionMapper;
import com.example.demo.Mapper.UserMapper;
import com.example.demo.model.Question;
import com.example.demo.model.User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PublishController
{
  @Autowired
  QuestionMapper questionMapper;
  @Autowired
  UserMapper userMapper;

  @PostMapping("/publish")
  public String dopost(
    @RequestParam String title,
    @RequestParam String description,
    @RequestParam String tag,
    HttpServletRequest request,
    Model model
  )
  {
    model.addAttribute("title", title);
    model.addAttribute("description", description);
    model.addAttribute("tag", tag);
    if (title == null || title.isEmpty())
    {
      model.addAttribute("error", "标题不能为空");
      return "publish";
    }
    if (description == null || description.isEmpty())
    {
      model.addAttribute("error", "描述不能为空");
      return "publish";
    }
    if (tag == null || tag.isEmpty())
    {
      model.addAttribute("error", "标签不能为空");
      return "publish";
    }
    Cookie[] cookies = request.getCookies();
    User user = null;
    for (Cookie cookie : cookies)
    {
      if (cookie.getName().equals("token"))
      {
        user = userMapper.findByToken(cookie.getValue());
        if (user != null)
        {
          request.getSession().setAttribute("user", user);
          break;
        }
      }
    }
    if (user == null)
    {
      model.addAttribute("error", "用户未登录");
      return "publish";
    }
    Question question = new Question();
    question.setTitle(title);
    question.setDescription(description);
    question.setTag(tag);
    question.setCreator(user.getId());
    question.setGmtCreate(System.currentTimeMillis());
    question.setGmtModified(question.getGmtCreate());

    questionMapper.create(question);
    return "redirect:/";
  }

  @GetMapping("/publish")
  public String publish()
  {
    return "publish";
  }
}
