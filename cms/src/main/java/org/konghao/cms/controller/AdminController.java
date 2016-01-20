package org.konghao.cms.controller;

import org.konghao.cms.auth.AuthClass;
import org.konghao.cms.auth.AuthMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AuthClass("login")
public class AdminController extends BaseController {//http://localhost:8888/cms/admin
    @RequestMapping("/admin")
    @AuthMethod
	public String index(){
		return "admin/index";
	}
}
