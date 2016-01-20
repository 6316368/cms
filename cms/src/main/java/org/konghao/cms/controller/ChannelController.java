package org.konghao.cms.controller;

import java.util.ArrayList;
import java.util.List;

import org.jboss.logging.Param;
import org.konghao.basic.model.Channel;
import org.konghao.basic.model.ChannelTree;
import org.konghao.basic.model.ChannelType;
import org.konghao.basic.util.EnumUtils;
import org.konghao.cms.auth.AuthClass;
import org.konghao.cms.dto.AjaxObj;
import org.konghao.cms.dto.TreeDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author   李海  Email:870721131@qq.com
 * @Date	 2015年2月21日		
 * @类的作用:
 */
@RequestMapping("/admin/channel")
@Controller
@AuthClass
public class ChannelController extends BaseController {
	/**
	 * init:(如果添加不成功就重新初始化信息)
	 * @param @param pid
	 * @param @param model  设定文件
	 * @return void    DOM对象
	 * @throws 
	 */
	private void init(Integer pid, Model model) {
		Channel pc=null;
		if(pid==null){
			pid=0;
		}if(pid==0){
			pc=new Channel();
			pc.setId(Channel.ROOT_ID);
			pc.setName(Channel.ROOT_NAME);
			model.addAttribute("pc", new Channel());
		}else{
			pc=channelService.load(pid);
		}
		model.addAttribute("pc", pc);
		model.addAttribute("types", EnumUtils.enumProp2NameMap(ChannelType.class, "name"));
	}
	
	/**
	 * list:(查询所有的节点)
	 * @param @param model
	 * @param @return  设定文件
	 * @return String    DOM对象
	 * @throws 
	 */
	@RequestMapping("/channels")
	public String list(Model model) {
//	 System.out.println(JsonUtil.getInstance().obj2json(channelService.generateTree()));
//	  model.addAttribute("treeDatas", JsonUtil.getInstance().obj2json(channelService.generateTree()));
	  return "channel/list";
	}
	/**
	 * tree:(通过"@ResponseBody"自动转换成json的数据)
	 * @param @return  设定文件
	 * @return List<ChannelTree>    DOM对象
	 * @throws 
	 */
	@RequestMapping("/treeAll")
	public @ResponseBody List<ChannelTree>tree(){
		return channelService.generateTree();
	}
	/**
	 * tree:(异步加载，根据父类节点的id在加载父类节点下面的子节点)
	 * @param @param pid  此方法只是用于演示,在本次的项目不用异步加载的方式
	 * @param @return  设定文件
	 * @return List<ChannelTree>    DOM对象
	 * @throws 
	 */
	@RequestMapping(value="/treeAs",method=RequestMethod.POST)
	public @ResponseBody List<TreeDto>tree(@Param  Integer  pid){
		List<TreeDto> tds=new ArrayList<TreeDto>();
		if(pid==null){
			tds.add(new TreeDto(0, "网站根栏目", 1));
			return tds;
		}
		List<ChannelTree> cts = channelService.generateTreeByParentByasync(pid);
		int isparent=0;
		for (ChannelTree channelTree : cts) {
			System.out.println("id==="+channelTree.getId()+"name==="+channelTree.getName()+"count==="+channelTree.getCount());
			if(channelTree.getCount().intValue()==0){
				isparent=0;
			}
			else{
				isparent=1;	
			}
			tds.add(new TreeDto(channelTree.getId(), channelTree.getName(), isparent));
		}
		return tds;
	}
	/**
	 * listChild:(根据父类的栏目查询子类的栏目)
	 * @param @param pid
	 * @param @param model
	 * @param @return  设定文件
	 * @return String    DOM对象
	 * @throws 
	 */
	@RequestMapping("/channels/{pid}")
	public String listChild(@PathVariable Integer pid,@Param Integer refresh ,Model  model){
		System.out.println("--------------------------------------");
		Channel pc=null;
		if(refresh==null){
			model.addAttribute("refresh", 0);
		}
		else{
			model.addAttribute("refresh", 1);
		}
		if(pid==null||pid==0){
			pc=new Channel();
			pc.setName(pc.ROOT_NAME);
			pc.setId(pc.ROOT_ID);
		}
		else{
			pc=channelService.load(pid);
		}
		model.addAttribute("pc",pc);
		model.addAttribute("channels", channelService.listByParent(pid));
		return "channel/list_child";
	}
	
	/**
	 * add:(到达添加栏目的页面)
	 * @param @param pid    父 类栏目的id
	 * @param @param model  栏目的对象
	 * @param @return       设定文件
	 * @return String    DOM对象
	 * @throws 
	 */
	@RequestMapping(value="/add/{pid}",method=RequestMethod.GET)
	public String add(@PathVariable Integer pid ,Model  model){
		 init(pid, model);
		 model.addAttribute(new Channel());
	    return "channel/add";
	}
	
	/**
	 * add:(这里用一句话描述这个方法的作用)
	 * @param @param pid       父类节点的id
	 * @param @param channel   要添加的栏目的对象
	 * @param @param br        SpingMVC中利用BindingResult将错误信息返回到页面中
	 * @param @param model     model对象就是channel
	 * @param @return  设定文件
	 * @return String    DOM对象
	 * @throws 
	 */
	@RequestMapping(value="/add/{pid}",method=RequestMethod.POST)
	public String add(@PathVariable Integer pid ,Channel channel,BindingResult br, Model  model){
		System.out.println("br.hasErrors()====="+br.hasErrors());
		if(br.hasErrors()){
			init(pid, model);
			return "channel/add";
		}
		channelService.add(channel, pid);
		return "redirect:/admin/channel/channels/"+pid+"?refresh=1";
	}
	
	/**
	 * delete:(删除一个栏目)
	 * @param @param pid  要删除栏目的父类的id
	 * @param @param cid  要删除栏目的id
	 * @param @return  设定文件
	 * @return String    DOM对象
	 * @throws 
	 */
	@RequestMapping("/delete/{pid}/{id}")
	public String delete(@PathVariable Integer pid,@PathVariable Integer id,Model model){
		channelService.delete(id);
		model.addAttribute("refresh", true);
		return "redirect:/admin/channel/channels/"+pid+"?refresh=1";
	}
	/**
	 * add:(到达添加栏目的页面)
	 * @param @param pid    父 类栏目的id
	 * @param @param model  栏目的对象
	 * @param @return       设定文件
	 * @return String    DOM对象
	 * @throws 
	 */
	@RequestMapping(value="/update/{id}",method=RequestMethod.GET)
	public String update(@PathVariable Integer id ,Model  model){
		Channel c = channelService.load(id);
		Channel pc=null;
		if(c.getParent()==null){
			pc=new Channel();
			pc.setId(Channel.ROOT_ID);
			pc.setName(Channel.ROOT_NAME);
		}
		else{
			pc=c.getParent();
		}
		 model.addAttribute("channel",c);
		 model.addAttribute("pc", c.getParent());
		 model.addAttribute("types", EnumUtils.enumProp2NameMap(ChannelType.class, "name"));
		// model.addAttribute(new Channel());
	    return "channel/update";
	}
	
	/**
	 * add:(这里用一句话描述这个方法的作用)
	 * @param @param pid       父类节点的id
	 * @param @param channel   要添加的栏目的对象
	 * @param @param br        SpingMVC中利用BindingResult将错误信息返回到页面中
	 * @param @param model     model对象就是channel
	 * @param @return  设定文件
	 * @return String    DOM对象
	 * @throws 
	 */
	@RequestMapping(value="/update/{id}",method=RequestMethod.POST)
	public String update(@PathVariable Integer id ,Channel channel,BindingResult br, Model  model){
		System.out.println("br.hasErrors()====="+br.hasErrors());
		if(br.hasErrors()){
			 model.addAttribute("types", EnumUtils.enumProp2NameMap(ChannelType.class, "name"));
			return "channel/update";
		}
		Channel tc = channelService.load(id);
		int pid=0;
		if(tc.getParent()!=null){
			pid=tc.getParent().getId();
		}
		tc.setCustomLink(channel.getCustomLink());
		tc.setCustomLinkUrl(channel.getCustomLinkUrl());
		tc.setIsIndex(channel.getIsIndex());
		tc.setIsTopNav(channel.getIsTopNav());
		tc.setName(channel.getName());
		tc.setRecommend(tc.getRecommend());
		tc.setStatus(channel.getStatus());
		tc.setType(channel.getType());
		channelService.update(tc);
		return "redirect:/admin/channel/channels/"+pid+"?refresh=1";
	}
	
	/**
	 * updateSort:(这里用一句话描述这个方法的作用)
	 * @param @param ids  设定文件
	 * @return void    DOM对象
	 * @throws 
	 */
	@RequestMapping("/channels/updateSort")
	public  @ResponseBody AjaxObj updateSort(@Param Integer[] ids){
		try {
			channelService.updateSort(ids);
		} catch (Exception e) {
			return new AjaxObj(0, e.getMessage());
		}
		return new AjaxObj(1);
	}
	
	
}

