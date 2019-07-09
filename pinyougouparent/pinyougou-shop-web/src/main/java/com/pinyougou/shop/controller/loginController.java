package com.pinyougou.shop.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 获取用户名
 */
@RestController
@RequestMapping("/sellerName")
public class loginController {

    @RequestMapping("/name")
      public Map sellerName(){
  Map map=  new HashMap();
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
            map.put("sellerName",name);
        return map;

      }

}
