package com.example.demo.provider;

import com.alibaba.fastjson.JSON;
import com.example.demo.dto.AccessTokenDTO;
import com.example.demo.dto.GiteeUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class GiteeProvider
{
  public String getAccessToken(AccessTokenDTO accessTokenDTO)
  {
    MediaType mediaType = MediaType.get("application/json");
    OkHttpClient client = new OkHttpClient();
    
    RequestBody body = RequestBody.create(JSON.toJSONString(accessTokenDTO), mediaType);
    System.out.println(JSON.toJSONString(accessTokenDTO));
    Request request = new Request.Builder()
      .url("https://gitee.com/oauth/token")
      .post(body)
      .build();
    try (Response response = client.newCall(request).execute())
    {
      String string = response.body().string();
      Map<String, String> res = (Map<String, String>)JSON.parse(string);
      return res.get("access_token");
    }
    catch (IOException e)
    {
      return null;
    }
  }
  
  public GiteeUser getUser(String AccessToken)
  {
    OkHttpClient client = new OkHttpClient();
    Request request = new Request.Builder()
      .url("https://gitee.com/api/v5/user?access_token="+AccessToken)
      .build();
    
    try (Response response = client.newCall(request).execute()) {
      String string = response.body().string();
      return JSON.parseObject(string, GiteeUser.class);
    }
    catch (Exception e)
    {
      return null;
    }
  }
}
