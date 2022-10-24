package org.example.controller;

import org.example.dto.Guestbook;
import org.example.service.GuestbookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping(path="/guestbooks")
public class GuestbookApiController {
    @Autowired
    GuestbookService guestbookService;

    @GetMapping
    // path가 없는 이유는 위에 RequestMapping에 guestbook이 있기 때문
    // Content-Type이 application/json, Get방식으로 요청이 들어오면 List메소드가 실행된다.
    // Application/json 요청이라 DispatcherServlet은 JsonMessageConver를 내부적으로 사용한다
    // Map<String, Object> 이 MAP 객체를 json으로 변환해서 전송한다.
    public Map<String, Object> list(@RequestParam(name = "start", required = false, defaultValue = "0") int start) {

        List<Guestbook> list = guestbookService.getGuestbooks(start);

        int count = guestbookService.getCount();
        int pageCount = count / GuestbookService.LIMIT;
        if (count % GuestbookService.LIMIT > 0)
            pageCount++;

        List<Integer> pageStartList = new ArrayList<>();
        for (int i = 0; i < pageCount; i++) {
            pageStartList.add(i * GuestbookService.LIMIT);
        }

        Map<String, Object> map = new HashMap<>();
        map.put("list", list);
        map.put("count", count);
        map.put("pageStartList", pageStartList);

        return map; // 결과값으로 map객체를 반환한다.
    }

    @PostMapping
    // write 메소드를 호출, 결과는 Guestbook 객체로 반환한다.
    // 마찬가지로 json 메소드로 변환돼서 클라이언트로 간다.
    public Guestbook write(@RequestBody Guestbook guestbook,
                           HttpServletRequest request) {
        String clientIp = request.getRemoteAddr();
        // id가 입력된 guestbook이 반환된다.
        Guestbook resultGuestbook = guestbookService.addGuestbook(guestbook, clientIp);
        return resultGuestbook;
    }

    @DeleteMapping("/{id}")
    // 12줄에서 지정한 guestbooks/id가 온다.
    // @pathvariable 어노테이션으로 값을 읽어들인다.
    // 해당메소드가 성공하면 키 값은 success로 주고 값은 true나 false가 리턴된다.
    public Map<String, String> delete(@PathVariable(name = "id") Long id,
                                      HttpServletRequest request) {
        String clientIp = request.getRemoteAddr();

        int deleteCount = guestbookService.deleteGuestbook(id, clientIp);
        return Collections.singletonMap("success", deleteCount > 0 ? "true" : "false");
    }
}