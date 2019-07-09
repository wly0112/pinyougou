package com.pinyougou.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

import java.util.Map;
@RestController
@RequestMapping("/userName")
public class LonginController {
    @RequestMapping("/name.do")
    public Map name(){
          Map  map = new HashMap();
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        map.put("userName",name);
         return map;

    }
}
