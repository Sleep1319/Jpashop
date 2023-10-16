package jpabook.jpashop.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j//api요청 처리 해주는 어노테이션
public class HomeController {

    @RequestMapping("/")
    public String home() {
        log.info("home cotroller");//로그를 남기는 slf4j기능
        return "home";
    }
}
