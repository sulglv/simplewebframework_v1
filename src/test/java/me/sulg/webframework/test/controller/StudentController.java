package me.sulg.webframework.test.controller;

import me.sulg.webframework.annotation.ActionMapper;
import me.sulg.webframework.annotation.Controller;
import me.sulg.webframework.define.RequestDefine;

@Controller
public class StudentController {

    @ActionMapper(path = "/student/test1", method = RequestDefine.GET)
    public void test1() {
        System.out.println("student test1");
    }

    @ActionMapper(path = "/student/test2", method = RequestDefine.POST)
    public void test2() {
        System.out.println("student test2");
    }

}
