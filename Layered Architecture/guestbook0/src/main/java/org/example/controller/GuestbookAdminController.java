package org.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
public class GuestbookAdminController {

    @GetMapping(path="/loginform")
    public String loginform() {
        return "loginform";
    }

    @PostMapping(path="/Login")
    public String Login(@RequestParam(name="passwd", required=true) String passwd,
                        HttpSession session,
                        RedirectAttributes redirectAttr) {

        if("1234".equals(passwd)) {
            session.setAttribute("isAdmin", "true");
        }else {
            redirectAttr.addFlashAttribute("errorMessage","암호가 틀렸습니다.");
            return "redirect:/loginform";
        }
        return "redirect:/list";
    }

    @GetMapping(path="/logout")
    public String login(HttpSession session) {
        session.removeAttribute("isAdmin");
        return "redirect:/list";
    }

}