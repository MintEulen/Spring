package org.example;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class  PlusController {
    @GetMapping(path = "/plusform")
    public String plusform() { // plusform이라는 요청이 들어왔을 때 요청을 처리하는 메소드 지정
        return "plusForm"; // 이 요청이 들어왔을 때 컨트롤러는 딱히 할 일이 없어서 'plusForm이라는 뷰를 보여줘'
    }

    @PostMapping(path = "/plus")
    public String plus(@RequestParam(name = "value1", required = true) int value1,
                       // HTML form태그에 plusForm에서 name을 지정했는데 그 name을 매핑
                       // 이름이 value1으로 들어오는 요청은 plus라는 메소드 내에서 int형 변수 value1이라는 이름으로 사용
                       @RequestParam(name = "value2", required = true) int value2,
                       // 이름이 value2로 들어오는 요청은 plus라는 메소드 내에서 int형 변수 value2라는 이름으로 사용
                       ModelMap modelMap) {
                       // 종속을 낮추기 위해 spring이 제공하는 ModelMap 객체 사용,
                       // 요청을 modelMap에 넣으면 Spring이 알아서 request scope에다가 지정

        int result = value1 + value2;
        modelMap.addAttribute("value1", value1); // key에 value값을 저장 (key, value)
        modelMap.addAttribute("value2", value2);
        modelMap.addAttribute("result", result);
        return "plusResult"; // string view name
    }
}