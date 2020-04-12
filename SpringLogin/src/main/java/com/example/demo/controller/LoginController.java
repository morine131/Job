package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

//import SpringLogin.app.service.UserService;

@Controller
public class LoginController {

//    @Autowired
//    UserService userService;

    @GetMapping("/login")
    public String getSignUp(Model model) {
        return "login";
    }

    //FormのSubmitを押すとPostメソッドがリクエストされます。（）に書かれたURLのリクエストを受け取るとこのメソッドが発動します。
    @PostMapping("/login")
    public String postSignUp(Model model) {
        /*
        今回はリダイレクトを使います。画面遷移などファイル間をまたぐ場合はリダイレクト使います。
        イメージ的にはGetメソッドを呼び出していると考えていいでしょう。
        試しにリダイレクトせず通常のフォワード(return "ｘｘｘ";)とすると画面自体は表示されますがURLが変わりません。
        こうすると、遷移先で受け取りたいデータなどが受け取れないことがあるので、リダイレクトを使用したほうが良いでしょう。
        */
        return "redirect:/userList";
    }

}
