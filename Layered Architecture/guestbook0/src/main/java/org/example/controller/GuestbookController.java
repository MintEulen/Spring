package org.example.controller;

import org.example.dto.Guestbook;
import org.example.service.GuestbookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
public class GuestbookController {
    @Autowired
    GuestbookService guestbookService;

    //path가 list로 들어오면 @RequestParam으로 "start"라는 값을 꺼내서 사용
    //이 때 값이 없으면 defaultValue = 0으로 시작한다. => 0부터 시작
    //required 설정은 파라미터가 필수인지 아닌지 설정(기본값 true)
    @GetMapping(path="/list")
    public String list(@RequestParam(name="start", required=false, defaultValue="0") int start,
                       ModelMap model,
                       @CookieValue(value = "count",defaultValue = "0", required = true) String value,
                       // 첫 번째 파라미터는 cookie의 name으로 지정했던 값,
                       // 두 번째 파라미터는 해당 쿠키가 없었을 때 defaultValue에 지정한 값으로 할당
                       // required 속성을 ture로 지정 시, count 이름을 가진 쿠키가 존재하지 않을 시 스프링 MVC는 익셉션을 발생시킨다.
                       HttpServletResponse response) {

            try{
                int i = Integer.parseInt(value);
                value = Integer.toString(++i);
            }catch (Exception ex) {
                value = "1";
            }


        Cookie cookie = new Cookie("count", value);
        cookie.setMaxAge(60*60*24*365); //1년 동안 유지
        cookie.setPath("/"); // 경로 이하에 모두 쿠키 적용
        response.addCookie(cookie); // 쿠키는 매번 새로 만들어서 클라이언트에 전송
        // Cookie~여기까지는 쿠키를 만드는 일

        // start로 시작하는 방명록 목록 구하기
        List<Guestbook> list = guestbookService.getGuestbooks(start);

        // 전체 페이지수 구하기
        int count = guestbookService.getCount();
        int pageCount = count / GuestbookService.LIMIT;
        if(count % GuestbookService.LIMIT > 0)
            pageCount++;

        // 페이지 수만큼 start의 값을 리스트로 저장
        // 예를 들면 페이지수가 3이면
        // 0, 5, 10 이렇게 저장된다.
        // list?start=0 , list?start=5, list?start=10 으로 링크가 걸린다.
        List<Integer> pageStartList = new ArrayList<>();
        for(int i = 0; i < pageCount; i++) {
            pageStartList.add(i * GuestbookService.LIMIT);
        }

        model.addAttribute("list", list);
        model.addAttribute("count", count);
        model.addAttribute("pageStartList", pageStartList);
        model.addAttribute("cookieCount", value);

        return "list";
    }

    @PostMapping(path="/write")
    public String write(@ModelAttribute Guestbook guestbook,
                        HttpServletRequest request) {
        String clientIp = request.getRemoteAddr();
        System.out.println("clientIp : " + clientIp);
        guestbookService.addGuestbook(guestbook, clientIp);
        return "redirect:list";
    }

    @GetMapping(path="/delete")
    public String delete(@RequestParam(name="id", required=true) Long id,
                         @SessionAttribute("isAdmin") String isAdmin,
                         HttpServletRequest request,
                         RedirectAttributes redirectAttr) {
        if(isAdmin == null || !"true".equals(isAdmin)) { // 세션값이 true가 아닐 경우
            redirectAttr.addFlashAttribute("errorMessage", "로그인을 하지 않았습니다.");
            //flashMap에 오류메시지를 담아서 redirect로 loginform으로 이동
            return "redirect:loginform";
        }
        String clientIp = request.getRemoteAddr(); // request로부터 clientIp 꺼내온다.
        guestbookService.deleteGuestbook(id, clientIp);
        //기존에 구현해놓은 guestbookService가 가지고 있는 deleteGusetbook()
        //이라는 메서드에다가 방명록의 id랑 clientIp를 넘겨주면 삭제해준다.
        return "redirect:list";
    }
}