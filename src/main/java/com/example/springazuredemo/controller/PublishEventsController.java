package com.example.springazuredemo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PublishEventsController {
  @RequestMapping("/publishEvent")
  public String greeting() {
    return "Application deployed successfully on Azure";
  }

}
