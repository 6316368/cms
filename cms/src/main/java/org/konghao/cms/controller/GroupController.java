package org.konghao.cms.controller;

import java.util.List;

import org.konghao.basic.model.ChannelTree;
import org.konghao.basic.model.Group;
import org.konghao.cms.auth.AuthClass;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author   李海  Email:870721131@qq.com
 * @Date	 2015年2月7日		下午10:21:28
 * @类的作用:   用于操作用户组的GroupController
 */
@RequestMapping("/admin/group")
@Controller
@AuthClass 
public class GroupController extends BaseController {
     /**
     * list:(显示所有的组)
     * @param @param model
     * @param @return  设定文件
     * @return String    DOM对象
     * @throws 
     */
    @RequestMapping("/groups")
	public  String list(Model model){
    	model.addAttribute("datas",groupService.findGroup());
		return "group/list";
	}
    /**
     * list:(到达用户组的页面)
     * @param @param model
     * @param @return  设定文件
     * @return String    DOM对象
     * @throws 
     */
    @RequestMapping(value="/add",method=RequestMethod.GET)
    public String add(Model model){
    	model.addAttribute(new Group());
    	return "group/add";
    }
    /**
     * add:(添加用户到数据库中)
     * @param @param group 用户的对象
     * @param @param br  验证信息
     * @param @return  设定文件
     * @return String    DOM对象
     * @throws 
     */
    @RequestMapping(value="/add",method=RequestMethod.POST)
    public  String add(@Validated Group group ,BindingResult br){
    	if(br.hasErrors()){
    		return "group/add";
    	}
    	groupService.add(group);
    	return "redirect:/admin/group/groups";
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
    	model.addAttribute(groupService.load(id));
    	return "group/update";
    }
    /**
     * update:(修改用户并跟新数据库的中信息)
     * @param @param id
     * @param @param group
     * @param @param br
     * @param @return  设定文件
     * @return String    DOM对象
     * @throws 
     */
    @RequestMapping(value="update/{id}",method=RequestMethod.POST)
    public  String update(@PathVariable int id,@Validated  Group group,BindingResult br){
    	if(br.hasErrors()){
    		return "group/update";
    	}
    	Group gr = groupService.load(id);
    	gr.setName(group.getName());
    	gr.setDescr(group.getDescr());
    	groupService.update(gr);
    	return "redirect:/admin/group/groups";
    }
    /**
     * delete:(删除用户组的信息)
     * @param @param id
     * @param @return  设定文件
     * @return String    DOM对象
     * @throws 
     */
    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
		groupService.delete(id);
		return "redirect:/admin/group/groups";
	}
    /**
     * show:(点击用户组显示用户组的信息)
     * @param @param id
     * @param @param model
     * @param @return  设定文件
     * @return String    DOM对象
     * @throws 
     */
    @RequestMapping("/{id}")
	public String show(@PathVariable int id,Model model) {
		model.addAttribute(groupService.load(id));
		model.addAttribute("us", userService.listGroupUsers(id));
		return "group/show";
	}
    /**
     * show:(清空某一个组下所有的用户)
     * @param @param id
     * @param @param model
     * @param @return  设定文件
     * @return String    DOM对象
     * @throws 
     */
    @RequestMapping("/clearUsers/{id}")
	public String clearGroupUsers(@PathVariable int id) {
		groupService.deleteGroupUsers(id);
		return "redirect:/admin/group/groups";
	}
    
    /**
     * listChannels:(根据组id来查询当前组管理的栏目)
     * @param @param gid 组id
     * @param @param model
     * @param @return  设定文件
     * @return String    DOM对象
     * @throws 
     */
    @RequestMapping("/listChannels/{gid}")
    public String listChannels(@PathVariable int gid,Model  model ){
    	model.addAttribute("group", groupService.load(gid));
    	return "group/listChannel";
    }
    /**
     * groupTree:(根据组id来查询当前组管理的栏目并生成一个树机构)
     * @param @param gid
     * @param @return  设定文件
     * @return List<ChannelTree>    DOM对象
     * @throws 
     */
    @RequestMapping("/groupTree/{gid}")
    public  @ResponseBody  List<ChannelTree> groupTree(@PathVariable  Integer  gid){
    	 return groupService.generateGroupChannelTree(gid);
    }
    
    /**
     * setChannels:(设置用户组的管理栏目,但是在设置前需要查询出该用户已经管理的栏目)
     * @param @param gid
     * @param @param model
     * @param @return  设定文件
     * @return String    DOM对象
     * @throws 
     */
    @RequestMapping("/setChannels/{gid}")
	public String setChannels(@PathVariable int gid,Model model) {
		model.addAttribute(groupService.load(gid));
		model.addAttribute("cids",groupService.listGroupChannelIds(gid));
		return "/group/setChannel";
	}
    
}

