//package com.example.RoadToConcert.service;
//
//
//import java.util.HashMap;
//import java.util.Map;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.cloud.context.config.annotation.RefreshScope;
//import org.springframework.stereotype.Service;
//
//@Service
//@RefreshScope
//public class DynamicConfigService {
//
//  @Value("${my.name}")
//  private String name;
//
//  @Value("${my.age}")
//  private String age;
//
//
//  public Map<String, String> getConfig() {
//
//    Map<String, String> map = new HashMap<>();
//
//    map.put("name", name);
//    map.put("age", age);
//
//    return map;
//
//
//  }
//
//
//}
