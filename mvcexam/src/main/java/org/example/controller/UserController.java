package org.example.controller;

import org.example.dto.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {
    // userForm을 처리할 메소드
    @RequestMapping(path = "/userform", method = RequestMethod.GET) // = GetMapping
    public String userform() {
        return "userform";
    }
    @RequestMapping(path="/regist", method=RequestMethod.POST)
    public String regist(@ModelAttribute User user) {
        // requestparam은 값을 하나하나 꺼내서 복잡하게 리턴해야 하지만
        // dto 객체를 이용하면 값들을 한 번에 리턴할 수 있다. => modelAttribute

        System.out.println("사용자가 입력한 user 정보입니다. 해당 정보를 이용하는 코드가 와야합니다.");
        System.out.println(user);
        return "regist";  // views파일에 regist.jsp가 들어온다.
    }
}
