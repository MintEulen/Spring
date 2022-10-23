package org.example.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.servlet.http.HttpServletRequest;

@Controller
public class GoodsController {
    @GetMapping("/goods/{id}") //url 요청이 들어오면 id를 pathvariable로 받겠다.
    public String getGoodsById(@PathVariable(name = "id") int id,
                               // id로 넘어온 값을 현재 메소드에서 사용할 int형 변수 id가 받도록 한다.
                               @RequestHeader(value="User-Agent", defaultValue="myBrowser") String userAgent,
                               // 헤더에서 넘어오는 정보들을 꺼내가지고 사용한다.
                               // 이때 받아온 정보를 String 변수인 userAgent로 받아서 사용한다.
                               HttpServletRequest request, // request가 가진 값들도 꺼내서 보여준다.
                               ModelMap model) // modelmap을 이용해서 값들을 등록한다. -> jsp가 값들을 꺼내서 사용한다.
     {
        String path = request.getServletPath();

        System.out.println("id : " + id);
        System.out.println("user_agent : " + userAgent);
        System.out.println("path : " + path);

        model.addAttribute("id", id);
        model.addAttribute("userAgent", userAgent);
        model.addAttribute("path", path);
        return "goodsById";
    }


}
