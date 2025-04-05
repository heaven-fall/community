package com.example.demo.Controller;

import com.example.demo.Mapper.UserMapper;
import com.example.demo.dto.AccessTokenDTO;
import com.example.demo.dto.GiteeUser;
import com.example.demo.model.User;
import com.example.demo.provider.GiteeProvider;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.UUID;

@Controller
public class AuthorizeController
{
  @Autowired
  private GiteeProvider giteeProvider;
  @Autowired
  private UserMapper userMapper;
  
  @Value("${gitee.client_secret}")
  private String secret;
  @Value("${gitee.client_id}")
  private String id;
  @Value("${gitee.redirect_uri}")
  private String uri;
  
  @GetMapping("/callback")
  public String callback(@RequestParam(name = "code") String code, HttpServletRequest request, HttpServletResponse response)
  {
    AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
    accessTokenDTO.setGrant_type("authorization_code");
    accessTokenDTO.setCode(code);
    accessTokenDTO.setClient_secret(secret);
    accessTokenDTO.setRedirect_uri(uri);
    accessTokenDTO.setClient_id(id);
    GiteeUser giteeUser = giteeProvider.getUser(giteeProvider.getAccessToken(accessTokenDTO));
    if (giteeUser != null)
    {
      User user = new User();
      user.setAccountId(String.valueOf(giteeUser.getId()));
      user.setName(giteeUser.getName());
      user.setToken(UUID.randomUUID().toString());
      user.setGmtCreate(System.currentTimeMillis());
      user.setGmtModified(user.getGmtCreate());
      user.setAvatarUrl(giteeUser.getAvatarUrl());
      userMapper.insert(user);
      response.addCookie(new Cookie("token", user.getToken()));
      return "redirect:/";
    }
    return "redirect:/";
  }
}
