package org.konghao.cms.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.jboss.logging.Param;
import org.konghao.basic.model.ChannelTree;
import org.konghao.basic.model.Role;
import org.konghao.basic.model.RoleType;
import org.konghao.basic.model.User;
import org.konghao.cms.auth.AuthClass;
import org.konghao.cms.auth.AuthMethod;
import org.konghao.cms.dto.UserDto;
import org.konghao.cms.web.CmsSessionContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 * ClassName:UserController
 * 类的作用:  与用户有关的Controller的处理类
 * @author  李海
 * @Date	2015年2月1日下午10:14:02
 */
@RequestMapping("/admin/user")
@Controller
@AuthClass("login")
public class UserController extends BaseController {
	
	private void initAdduser(Model model){
		model.addAttribute("roles",roleService.listRole());
		model.addAttribute("groups",groupService.listGroup());
	}
	
	/**
	 * list:(返回所有的用户信息的列表)
	 * @param @param model
	 * @param @return  设定文件
	 * @return String    DOM对象
	 * @throws 
	 */
	@RequestMapping("/users")
	public String list(Model model) {
		model.addAttribute("datas",userService.findUser());
		return "user/list";
	}
	/**
	 * list:(到达添加用户的界面)
	 * @param @param model
	 * @param @return  设定文件
	 * @return String    DOM对象
	 * @throws 
	 */
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public String add(Model model) {
		model.addAttribute("userDto",new UserDto());
		initAdduser(model);
		return "user/add";
	}
	/**
	 * add:(添加用户的信息)
	 * @param  userdto  保存用信息以及用户组和权限的信息
	 * @param  br   用于验证的信息
	 * @param  model  model对象 
	 * @param @Valid SpringMVC 使用JSR-303进行校验 @Valid 
	 *              这里一个@Valid的参数后必须紧挨着一个BindingResult 参数，否则spring会在校验不通过时直接抛出异常
	 * @param  设定文件
	 * @return DOM对象
	 * @throws 
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public String add(@Valid UserDto userdto ,BindingResult br,Model model) {
	    if(br.hasErrors()){
	    	 initAdduser(model);
	         return "user/add";
	    }
	    userService.add(userdto.getUser(), userdto.getRoleIds(), userdto.getGroupIds());
		return "redirect:/admin/user/users";
	}
	/**
	 * add:(删除用户的信息)
	 * @param  userdto  保存用信息以及用户组和权限的信息
	 * @param  br   用于验证的信息
	 * @param  model  model对象
	 * @param  设定文件
	 * @return DOM对象
	 * @throws 
	 */
	@RequestMapping(value="/delete/{id}",method=RequestMethod.GET)
	public String delete(@PathVariable int  id) {
	    userService.delete(id);
		return "redirect:/admin/user/users";
	}
	
	
	
	
	/**
	 * list:(更新用户信息)
	 * @param @param model
	 * @param @return  设定文件
	 * @return String    DOM对象
	 * @throws 
	 */
	@RequestMapping(value="/update/{id}",method=RequestMethod.GET)
	@AuthMethod
	public String update(@PathVariable int id, Model model) {
		User user = userService.load(id);
		model.addAttribute("userDto",new UserDto(user,userService.listUserRoleIds(id),userService.listUserGroupIds(id)));
		initAdduser(model);
		return "user/update";
	}
	/**
	 * add:(添加用户的信息)
	 * @param  userdto  跟新用信息以及用户组和权限的信息
	 * @param  br   用于验证的信息
	 * @param  model  model对象
	 * @param  设定文件
	 * @return DOM对象  
	 * @throws 
	 */
	@RequestMapping(value="/update/{id}",method=RequestMethod.POST)
	@AuthMethod
	public String update(@PathVariable int id,@Valid UserDto userdto ,BindingResult br,Model model) {
	    if(br.hasErrors()){
	    	 initAdduser(model);
	         return "user/update";
	    }
	    User user = userService.load(id);
	    user.setNickname(userdto.getNickname());
	    user.setPhone(userdto.getPhone());
	    user.setEmail(userdto.getEmail());
	    userService.update(user, userdto.getRoleIds(), userdto.getGroupIds());
		return "redirect:/admin/user/users";
	}
	
	/**
	 * add:(更新用户的信息)
	 * @param  userdto  更新用信息以及用户组和权限的信息
	 * @param  br   用于验证的信息
	 * @param  model  model对象
	 * @param  设定文件
	 * @return DOM对象
	 * @throws 
	 */
	@RequestMapping(value="/updateStatus/{id}",method=RequestMethod.GET)
	public String updateStatus(@PathVariable int  id) {
	    userService.updateStatus(id);;
		return "redirect:/admin/user/users";
	}
	
	/**
	 * show:(点击用户名显示用户的详细的信息)
	 * @param @param id  用户的ID
	 * @param @param model  用户的model
	 * @param @return  设定文件
	 * @return String    DOM对象
	 * @throws 
	 */
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public String show(@PathVariable int id,Model model) {
		model.addAttribute(userService.load(id));
		model.addAttribute("gs",userService.listUserGroups(id));
		model.addAttribute("rs",userService.listUserRoles(id));
		return "user/show";
	}
	 /**
     * listChannels:(根据用户id来查询当前组管理的栏目)
     * @param @param gid 用户id
     * @param @param model
     * @param @return  设定文件
     * @return String    DOM对象
     * @throws 
     */
    @RequestMapping("/listChannels/{uid}")
    public String listChannels(@PathVariable int uid,Model  model ){
    	model.addAttribute("user", userService.load(uid));
    	List<Role> roles = userService.listUserRoles(uid);
    	for (Role role : roles) {
			if(role.getRoleType()==RoleType.ROLE_ADMIN){
				model.addAttribute("isAdmin", 1);
			}
			else{
				model.addAttribute("isAdmin", 0);
			}
		}
    	return "user/listChannel";
    }
    /**
     * groupTree:(根据用户id来查询当前组管理的栏目并生成一个树机构)
     * @param @param uid
     * @param @return  设定文件
     * @return List<ChannelTree>    DOM对象
     * @throws 
     */
    @RequestMapping("/userTree/{uid}")
    public  @ResponseBody  List<ChannelTree> groupTree(@PathVariable  Integer  uid,@Param  Integer  isAdmin){
    	System.out.println("------------------isAdmin"+isAdmin);
    	if(isAdmin!=null&&isAdmin==1){
    		return channelService.generateTree();
    	}
    	else{
    		
    		return groupService.generateUserChannelTree(uid);
    		
    	}
    }
    /**function(查询个人信息)
     * @param  @param model
     * @param  @param session
     * @param  @return         
     * @return String 
     * @throws                
     * @author lh 
     * @Date   2015年7月16日
    */
    @RequestMapping("/showSelf")
    @AuthMethod
    public String showSelf(Model model,HttpSession session){
    	User  user=(User)session.getAttribute("user");
    	model.addAttribute(user);
    	model.addAttribute("gs", userService.listUserGroups(user.getId()));
    	model.addAttribute("rs", userService.listUserRoles(user.getId()));
    	return "user/show";
    }
    
    
    /**
	 * list:(更新用户信息)
	 * @param @param model
	 * @param @return  设定文件
	 * @return String    DOM对象
	 * @throws 
	 */
	@RequestMapping(value="/updateSelf",method=RequestMethod.GET)
	@AuthMethod
	public String updateSelf(HttpSession session, Model model) {
		User user=(User)session.getAttribute("user");
		model.addAttribute(new UserDto(user));
		return "user/updateSelf";
	}
	
	
	
	
	/**
	 * add:(更新用户的信息)
	 * @param  userdto  跟新用信息以及用户组和权限的信息
	 * @param  br   用于验证的信息
	 * @param  model  model对象
	 * @param  设定文件
	 * @return DOM对象  
	 * @throws 
	 */
	@RequestMapping(value="/updateSelf",method=RequestMethod.POST)
	@AuthMethod
	public String updateSelf(HttpSession sesssion, @Valid UserDto userdto ,BindingResult br,Model model) {
	    if(br.hasErrors()){
	         return "user/updateSelf";
	    }
	    User user = (User)sesssion.getAttribute("user");
	    user.setNickname(userdto.getNickname());
	    user.setPhone(userdto.getPhone());
	    user.setEmail(userdto.getEmail());
	    userService.update(user);
		return "redirect:/admin/user/showSelf";
	}
	
	/**function(到达更新密码)
	 * @param  @return         
	 * @return String 
	 * @throws                
	 * @author lh 
	 * @Date   2015年7月16日
	*/
	@RequestMapping(value="/updatePwd",method=RequestMethod.GET)
	@AuthMethod
	public String updatePwd(Model model,HttpSession session ){
		User user=(User)session.getAttribute("user");
		model.addAttribute(user);
		return "user/updatePwd";
	}
	/**function(到达更新密码)
	 * @param  @return         
	 * @return String 
	 * @throws                
	 * @author lh 
	 * @Date   2015年7月16日
	*/
	@RequestMapping(value="/updatePwd",method=RequestMethod.POST)
	@AuthMethod
	public String updatePwd(int id,String oldPwd,String password,HttpServletResponse response){
		userService.updatePwd(id, oldPwd, password);
		return "redirect:/admin/user/showSelf";
	}
	/**function(退出系统)
	 * @param  @return         
	 * @return String 
	 * @throws                
	 * @author lh 
	 * @Date   2015年7月16日
	*/
	@RequestMapping("/logout")
	@AuthMethod
	public String logout(Model model,HttpSession session) {
		CmsSessionContext.removeSession(session);
		session.invalidate();
		return "redirect:/login";
	}
	
	
}
