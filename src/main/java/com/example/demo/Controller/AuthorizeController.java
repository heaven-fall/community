package com.example.demo.Controller;

import com.example.demo.dto.AccessTokenDTO;
import com.example.demo.dto.GiteeUser;
import com.example.demo.provider.GiteeProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
public class AuthorizeController
{
  @Autowired
  private GiteeProvider giteeProvider;
  
  @Value("gitee.client_secret")
  private String secret;
  @Value("gitee.client_id")
  private String id;
  @Value("gitee.redirect_uri")
  private String uri;
  
  @GetMapping("/callback")
  public String callback(@RequestParam(name="code") String code)
  {
    AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
    accessTokenDTO.setGrant_type("authorization_code");
    accessTokenDTO.setCode(code);
    accessTokenDTO.setClient_secret(secret);
    accessTokenDTO.setRedirect_uri(uri);
    accessTokenDTO.setClient_id(id);
    GiteeUser user = giteeProvider.getUser(giteeProvider.getAccessToken(accessTokenDTO));
    System.out.println(user.getName());
    return "index";
  }
}
