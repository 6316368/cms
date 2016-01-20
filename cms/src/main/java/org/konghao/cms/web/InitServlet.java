package org.konghao.cms.web;

import java.util.Map;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.konghao.basic.model.BaseInfo;
import org.konghao.basic.util.ConfigBase;
import org.konghao.basic.util.HashMap2ObjUntil;
import org.konghao.cms.auth.AuthUtil;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
public class InitServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static WebApplicationContext wc;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		//初始化spring的工厂
		wc = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
		//初始化权限信息
		Map<String,Set<String>> auths = AuthUtil.initAuth("org.konghao.cms.controller");
		System.out.println("-----------------"+auths);
		this.getServletContext().setAttribute("allAuths", auths);
		/**
		 * 初始化查询网站的基本信息
		 */
		ConfigBase configbase=new ConfigBase("baseinfo.properties");
		BaseInfo b=(BaseInfo)HashMap2ObjUntil.getInstance().hashMap2Obj(configbase, BaseInfo.class);
		this.getServletContext().setAttribute("baseInfo", b);
		System.out.println("------------------------系统初始化成功:"+auths+"-----------------------------");
	}
	public static WebApplicationContext getWc() {
		return wc;
	}

}
