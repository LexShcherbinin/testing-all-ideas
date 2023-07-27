package spring.firstmvc.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
//@RequestMapping("/api")
public class FirstController {

  @GetMapping("/hello")
  public String helloPage() {
    return "first/hello";
  }

  @GetMapping("/goodbye")
  public String goodByePage() {
    return "first/goodbye";
  }

  @GetMapping("/hello-with-params")
  public String method1(
      @RequestParam(value = "name", required = false) String name,
      @RequestParam(value = "surname", required = false) String surname,
      Model model
  ) {
    String text = String.format("Hello, %s %s\n", name, surname);

//    System.out.println(text);

    model.addAttribute("message", text);
    return "first/hello";
  }

//  @GetMapping("/hello-with-params")
//  public String method2(HttpServletRequest httpServletRequest) {
//
//    System.out.printf("Hello, %s %s\n", httpServletRequest.getParameter("name"), httpServletRequest.getParameter("surname"));
//    return "first/hello";
//  }

}
