package com.api.gateway.controllers;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.api.gateway.exceptions.GatewayException;
import com.api.gateway.models.ApiModel;
import com.api.gateway.reader.ConfigDefinition;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class GatewayController {

  public static final String PREFIX = "/v1/app/";
  private static final String PREFIX_MATCHER = "/v1/app/**";

  @Autowired
  private ConfigDefinition configDef;

  @RequestMapping(value = {GatewayController.PREFIX_MATCHER}, method = {RequestMethod.GET,RequestMethod.PUT,RequestMethod.POST,RequestMethod.OPTIONS,RequestMethod.DELETE})
  @ResponseBody
  public void gatewayMap(HttpServletRequest req,HttpServletResponse response) throws URISyntaxException, IOException, GatewayException{
    String srcUrl = new AntPathMatcher().extractPathWithinPattern(GatewayController.PREFIX_MATCHER,req.getRequestURI());

    List<ApiModel> matchingApis = configDef.getApis().stream().filter((api) -> 
      api.getSrcPath().trim().toLowerCase().equals(GatewayController.PREFIX + srcUrl.trim())
    ).toList();

    // req.getReader();
    if(matchingApis.size() > 1 || matchingApis.size() == 0){
      throw new RuntimeException("This should not be possible since there is a check for this in the filter chain");
    }

    URI uri = new URI(matchingApis.get(0).getTarget());
    HttpURLConnection con = (HttpURLConnection) uri.toURL().openConnection();
    con.setRequestMethod(req.getMethod());
    OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream(),"UTF-8");
    req.getHeaderNames().asIterator().forEachRemaining(
        (headerKey) ->{
          if (!headerKey.equals("Authorization")){
            con.setRequestProperty(headerKey, req.getHeader(headerKey));
          }
        } 
      );
    req.getReader().transferTo(writer);
    if (con.getResponseCode() == HttpURLConnection.HTTP_OK){
      con.getInputStream().transferTo(response.getOutputStream());
      con.getHeaderFields().forEach((key,value) -> {
        response.addHeader(key, value.toString());
      });;
    }else{
      throw new GatewayException(GatewayException.MSG.REQ_FAILED);
    }
  }
}
