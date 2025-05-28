package com.api.gateway.controllers;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.api.gateway.exceptions.GatewayException;
import com.api.gateway.exceptions.GatewayException.MSG;
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

  @CrossOrigin(origins = {"http://localhost","localhost","http://localhost:5173","localhost:5173"})
  @RequestMapping(value = {GatewayController.PREFIX_MATCHER}, method = {RequestMethod.GET,RequestMethod.PUT,RequestMethod.POST,RequestMethod.DELETE})
  @ResponseBody
  public void gatewayMap(HttpServletRequest req,HttpServletResponse response) throws GatewayException{
    String srcUrl = new AntPathMatcher().extractPathWithinPattern(GatewayController.PREFIX_MATCHER,req.getRequestURI());

    List<ApiModel> matchingApis = configDef.getApis().stream().filter((api) -> 
      api.getSrcPath().trim().toLowerCase().equals(GatewayController.PREFIX + srcUrl.trim())
    ).toList();

    if(matchingApis.size() > 1 || matchingApis.size() == 0){
      throw new RuntimeException("This should not be possible since there is a check for this in the filter chain");
    }

    URI uri;
    HttpURLConnection con;

    try {
      uri = URI.create(matchingApis.get(0).getTarget());
      con = (HttpURLConnection) uri.toURL().openConnection();
    } catch (IOException e) {
      throw new RuntimeException("Incorrect URI configured");
    }

    con.setDoOutput(true);

    try {
      con.setRequestMethod(req.getMethod());
    } catch (ProtocolException e) {
      throw new GatewayException(MSG.BAD_INCORMING_REQ);
    }
    req.getHeaderNames().asIterator().forEachRemaining(
      (headerKey) ->{
        if (!headerKey.equals("Authorization")){
          // auth is done here no need to pass it to the underlying service
          con.setRequestProperty(headerKey, req.getHeader(headerKey));
        }
      } 
    );

    try(OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream(),"UTF-8")) {
      req.getReader().transferTo(writer);
      req.getReader().close();
      writer.close();
      if (con.getResponseCode() == HttpURLConnection.HTTP_OK){
        con.getInputStream().transferTo(response.getOutputStream());
        con.getHeaderFields().forEach((key,value) -> {
          String out = "";
          for (String item: value){
            out = out + item;
          }
          response.addHeader(key, out);
        });
      }else{
        throw new GatewayException(GatewayException.MSG.REQ_FAILED, Integer.toString(con.getResponseCode()));
      }
    } catch (IOException e) {
        throw new GatewayException(GatewayException.MSG.REQ_FAILED);
    }
    
  }
}
