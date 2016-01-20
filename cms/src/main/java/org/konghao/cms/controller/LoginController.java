package org.konghao.cms.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.konghao.basic.model.Role;
import org.konghao.basic.model.RoleType;
import org.konghao.basic.model.User;
import org.konghao.basic.util.Captcha;
import org.konghao.cms.web.CmsSessionContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
@Controller
public class LoginController extends BaseController {
	boolean flag=false;
	
	/**(到达登录的界面)
     * @param  @return         
     * @return String 
     * @throws                
     * @author lh 
     * @Date   2015年7月16日
    */
    @RequestMapping(value="/login/{id}" ,method=RequestMethod.GET)
    public  String login(@PathVariable int id,Model  model){
    	System.out.println("id===============测试【表情】的值"+id);
    	if(id==1){
    		model.addAttribute("id", 1);
    	}
    	return "admin/login";
    }
    
    /**(到达登录的界面)
     * @param  @return         
     * @return String 
     * @throws                
     * @author lh 
     * @Date   2015年7月16日
    */
    @RequestMapping(value="/login" ,method=RequestMethod.GET)
    public  String login(Model  model){
    	System.out.println("第二次开始设置id的值-----------------------------");
    	if(!flag){
    		model.addAttribute("id", 1);
    		flag=true;
    	}
    	return "admin/login";
    }
    
    
    
	/**function(用于登录的方法)
     * @param  @param username 用户名
     * @param  @param password 用户密码
     * @param  @param checkcode验证码
     * @param  @param model    model对象
     * @param  @param session  session
     * @param  @return  String         
     * @author lh 
     * @Date   2015年7月16日
    */
    @RequestMapping(value="/login",method=RequestMethod.POST)
    public String login(String username,String password,String checkcode,Model model,HttpSession session){
    	String sc = session.getAttribute("checkcode").toString();
    	if(!sc.equals(checkcode)){
    		model.addAttribute("error", "验证码出错，请重新输入");
    		return "admin/login";
    	}
    	User login = userService.login(username, password);
    	session.setAttribute("user", login);
    	List<Role> listUserRoles = userService.listUserRoles(login.getId());
    	boolean isAdmin = isAdmin(listUserRoles);
    	session.setAttribute("isAdmin", isAdmin);
    	if(!isAdmin) {
			session.setAttribute("allActions", getAllActions(listUserRoles, session));
			session.setAttribute("isAudit", isRole(listUserRoles,RoleType.ROLE_AUDIT));
			session.setAttribute("isPublish", isRole(listUserRoles,RoleType.ROLE_PUBLISH));
		}
    	session.removeAttribute("checkcode");
		CmsSessionContext.addSessoin(session);
    	return "redirect:/admin";
    }
    
    
    @RequestMapping("/drawCheckCode")
    public void drawCheckCode(HttpServletResponse response ,HttpSession session) throws Exception{
    	System.out.println("开始进入设置验证码");
    	// 设置响应的类型格式为图片格式  
        response.setContentType("image/jpeg");  
        // 禁止图像缓存。  
        response.setHeader("Pragma", "no-cache");  
        response.setHeader("Cache-Control", "no-cache");  
        response.setDateHeader("Expires", 0);  
        Captcha c=Captcha.getInstance();
         c.set(100, 20);
         
       String checkcode=  c.generateCheckcode();
       session.setAttribute("checkcode", checkcode);
       ServletOutputStream os = response.getOutputStream();  
       ImageIO.write(c.generateCheckImg(checkcode), "jpg", os);
    }
    public boolean isAdmin(List<Role> listUserRoles){
    	for (Role r : listUserRoles) {
			if(r.getRoleType()==RoleType.ROLE_ADMIN){
				return true;
			}
		}
    	return false;
    	
    }
    
    @SuppressWarnings("unchecked")
	private Set<String> getAllActions(List<Role> rs,HttpSession session) {
		Set<String> actions = new HashSet<String>();
		Map<String,Set<String>> allAuths = (Map<String,Set<String>>)session.getServletContext().getAttribute("allAuths");
		actions.addAll(allAuths.get("base"));
		
		for(Role r:rs) {
			if(r.getRoleType()==RoleType.ROLE_ADMIN) 
				continue;
			
			
			System.out.println("----------------"+r.getRoleType().name());
			System.out.println("----------------"+allAuths.get(r.getRoleType().name()));
			//actions.addAll(allAuths.get(r.getRoleType().name()));
		}
		return actions;
	}

	private boolean isRole(List<Role> rs,RoleType rt) {
		for(Role r:rs) {
			if(r.getRoleType()==rt) return true;
		}
		return false;
	}
    
}
