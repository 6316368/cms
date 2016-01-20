package org.konghao.cms.controller;

import org.konghao.basic.model.Role;
import org.konghao.basic.model.RoleType;
import org.konghao.basic.util.EnumUtils;
import org.konghao.cms.auth.AuthClass;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author   李海  Email:870721131@qq.com
 * @Date	 2015年2月7日		下午10:56:13
 * @类的作用:  有关角色的RoleController
 */
@Controller
@RequestMapping("/admin/role")
@AuthClass
public class RoleController extends BaseController {
     /**
     * list:(显示所有的角色)
     * @param @param model
     * @param @return  设定文件
     * @return String    DOM对象
     * @throws 
     */
    @RequestMapping("/roles")
	public String list(Model model){
		model.addAttribute("roles", roleService.listRole());
		return "role/list";
	}
    
    /**
     * add:(添加角色)
     * @param @param model
     * @param @return  设定文件
     * @return String    DOM对象
     * @throws 
     */
    @RequestMapping(value="/add",method=RequestMethod.GET)
	public String add(Model model) {
		model.addAttribute(new Role());
		model.addAttribute("types", EnumUtils.enum2Name(RoleType.class));
		return "role/add";
	}
    /**
     * add:(添加角色到数据库)
     * @param @param model
     * @param @return  设定文件
     * @return String    DOM对象
     * @throws 
     */
    @RequestMapping(value="/add",method=RequestMethod.POST)
    public String add(Role role ){
    	roleService.add(role);
    	return "redirect:/admin/role/roles";
    }
    /**
     * update:(到达修改的页面)
     * @param @param id
     * @param @param model
     * @param @return  设定文件
     * @return String    DOM对象
     * @throws 
     */
    @RequestMapping(value="update/{id}",method=RequestMethod.GET)
    public  String update(@PathVariable int id,Model model){
    	model.addAttribute(roleService.load(id));
    	return "role/update";
    }
    /**
     * update:(更新角色信息)
     * @param @param id
     * @param @param role
     * @param @return  设定文件
     * @return String    DOM对象
     * @throws 
     */
    @RequestMapping(value="/update/{id}",method=RequestMethod.POST)
    public String update(@PathVariable int id,@Validated  Role role,BindingResult br){
    	if(br.hasErrors()){
    		return "role/update";
    	}
    	Role r = roleService.load(id);
    	r.setName(role.getName());
    	r.setRoleType(role.getRoleType());
    	roleService.update(r);
    	return "redirect:/admin/role/roles";
    }
    /**
     * show:(查看某角色的信息,并查看该角色下的用户)
     * @param @param id
     * @param @param model
     * @param @return  设定文件
     * @return String    DOM对象
     * @throws 
     */
    @RequestMapping("/{id}")
    public String show(@PathVariable int id,Model model){
    	model.addAttribute(roleService.load(id));
    	model.addAttribute("us", userService.listRoleUsers(id));
    	return "role/show";
    }
    /**
     * delete:(删除角色信息)
     * @param @param id
     * @param @return  设定文件
     * @return String    DOM对象
     * @throws 
     */
    @RequestMapping("/delete/{id}")
    public  String delete(@PathVariable int id){
    	roleService.delete(id);
    	return "redirect:/admin/role/roles";
    }
    
    /**
     * show:(清空某一个角色所有的用户)
     * @param @param id
     * @param @param model
     * @param @return  设定文件
     * @return String    DOM对象
     * @throws 
     */
    @RequestMapping("/clearUsers/{id}")
	public String clearUsers(@PathVariable int id) {
		roleService.deleteRoleUsers(id);
		return "redirect:/admin/role/roles";
	}
   
}

