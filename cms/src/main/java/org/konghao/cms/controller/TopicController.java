package org.konghao.cms.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.konghao.basic.basemodel.SystemContext;
import org.konghao.basic.model.Attachment;
import org.konghao.basic.model.ChannelTree;
import org.konghao.basic.model.Topic;
import org.konghao.basic.model.User;
import org.konghao.basic.util.JsonUtil;
import org.konghao.cms.auth.AuthClass;
import org.konghao.cms.auth.AuthMethod;
import org.konghao.cms.dto.AjaxObj;
import org.konghao.cms.dto.TopicDto;
import org.neo4j.cypher.internal.compiler.v2_1.planner.logical.steps.pickBestPlan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@AuthClass("login")
@RequestMapping("/admin/topic")
public class TopicController extends BaseController {
	private final static List<String> imgTypes = Arrays.asList("jpg","jpeg","gif","png");
	private void initList(String con, Integer cid, Model model,
			HttpSession session,int status) {
		SystemContext.setSort("t.publishDate");
		SystemContext.setOrder("desc");
		boolean isAdmin = (Boolean)session.getAttribute("isAdmin");
		if(isAdmin){
			model.addAttribute("datas", topicService.find(cid, con, status));
		}
		else{
			User user = (User)session.getAttribute("user");
			model.addAttribute("datas",topicService.find(user.getId(), cid, con, status));
		}
		if(con==null) con="";
		SystemContext.removeOrder();
		SystemContext.removeSort();
		model.addAttribute("con",con);
		model.addAttribute("cid",cid);
		model.addAttribute("cs",channelService.listPublishChannel());
	};
	
	@RequestMapping("/audits")
	@AuthMethod(role="ROLE_PUBLISH,ROLE_AUDIT")
	public String auditslist(@RequestParam(required=false) String con,@RequestParam(required=false) Integer cid,Model model,HttpSession session){
		initList(con, cid, model, session,1);
		return "topic/andits";
	}
	@RequestMapping("/unaudits")
	@AuthMethod(role="ROLE_PUBLISH,ROLE_AUDIT")
	public String unauditslist(@RequestParam(required=false) String con,@RequestParam(required=false) Integer cid,Model model,HttpSession session){
		initList(con, cid, model, session,0);
		return "topic/unaudits";
	}
	@RequestMapping("/changeStatus/{id}")
	@AuthMethod(role="ROLE_PUBLISH")
	public String changeStatus(@PathVariable int id,Integer status){
		System.out.println("--------id----------"+id);
		System.out.println("--------status----------"+status);
		topicService.upateStatus(id);
		if(status==0){
			return "redirect:/admin/topic/audits";
		}
		else{
			return "redirect:/admin/topic/unaudits";
		}
	}
	@RequestMapping("/delete/{id}")
	@AuthMethod(role="ROLE_PUBLISH")
	public String delete(@PathVariable int id,Integer status){
		Topic t = topicService.load(id);
		topicService.delete(id);
//		if(topicService.isUpdateIndex(t.getChannel().getId())) {
//			iIndexPicService.generateBody();
//		}
//		if(status==0){
//			return "redirect:/admin/topic/andits";
//		}
//		else{
			return "";
//		}
	}
	
	@RequestMapping(value="/searchKeyword")
	@AuthMethod(role="ROLE_PUBLISH")
	public @ResponseBody List<String> searchKeyword(String term) {
		return keyService.listKeywordStringByCon(term);
	}
	
	
	/**
	 * tree:(通过"@ResponseBody"自动转换成json的数据)
	 * @param @return  设定文件
	 * @return List<ChannelTree>    DOM对象
	 * @throws 
	 */
	@RequestMapping("/treeAll")
	@AuthMethod(role="ROLE_PUBLISH")
	public @ResponseBody List<ChannelTree>tree(){
		return channelService.generateTree();
	}
	
	@RequestMapping(value="/upload",method=RequestMethod.POST)//返回的是json类型的值，而uploadify只能接受字符串
	public  void  upload(MultipartFile attach,HttpServletResponse resp) throws IOException{
		resp.setContentType("text/plain;charset=utf-8");
		AjaxObj ao = null;
		Attachment  att=new Attachment();
		String ext=FilenameUtils.getExtension(attach.getOriginalFilename());
		att.setIsAttach(0);
		if(imgTypes.contains(ext)) {
			att.setIsImg(1);
		}
		else{
			att.setIsImg(0);
		}
		att.setOldName(FilenameUtils.getBaseName(attach.getOriginalFilename()));
		att.setSuffix(ext);
		att.setType(attach.getContentType());
		att.setTopic(null);
		att.setSize(attach.getSize());
		att.setNewName(String.valueOf(new Date().getTime())+"."+ext);
		ao = new AjaxObj(1,null,att);
		attachmentService.add(att, attach.getInputStream());;
		resp.getWriter().write(JsonUtil.getInstance().obj2json(ao));
	}
	
	@RequestMapping(value="/add",method=RequestMethod.GET)
	@AuthMethod(role="ROLE_PUBLISH")
	public String add(Model  model) {
		Topic t=new Topic();
		t.setPublishDate(new Date());
		TopicDto td = new TopicDto(t);
		model.addAttribute("topicDto",td);
		return "topic/add";
	}
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public String add(@Validated  TopicDto topicDto,Integer[] aids,String[] aks,  BindingResult br,HttpSession  session){
		if(br.hasErrors()){
			return "topic/add";
		}
		Topic t = topicDto.getTopic();
		User user = (User)session.getAttribute("user");
		StringBuffer keys = new StringBuffer();
		if(aks!=null){
			for (String k : aks) {
				keys.append(k).append("|");
				keyService.addOrUpdate(k);
			}
			
		}
		t.setKeyword(keys.toString());
		topicService.add(t, topicDto.getCid(), user.getId(), aids);
		return "redirect:/jsp/common/addSuc.jsp";
	}
	
	/**function(点击文章名称查看文章的具体内容)
	 * @param  @return         
	 * @return String 
	 * @throws                
	 * @author lh 
	 * @Date   2015年8月7日
	*/
	@RequestMapping("/{id}")
	public  String show(@PathVariable int id,Model   model){
		model.addAttribute(topicService.load(id));
		List<Attachment> listByTopic = attachmentService.listByTopic(id);
		model.addAttribute("atts",listByTopic);
		return "topic/show";
	}
	/**function(到达跟新文章的页面)
	 * @param  @param model
	 * @param  @return         
	 * @return String 
	 * @throws                
	 * @author lh 
	 * @Date   2015年8月7日
	*/
	@RequestMapping(value="/update/{id}",method=RequestMethod.GET)
	@AuthMethod(role="ROLE_PUBLISH")
	public String update(@PathVariable int id,Model  model) {
		Topic t=topicService.load(id);
		String keyword = t.getKeyword();
		if(keyword!=null&&!"".equals(keyword.trim())){
			model.addAttribute("keywords",keyword.split("\\|"));
		}
		model.addAttribute("keywords",keyword.split("\\|"));
		TopicDto td = new TopicDto(t);
		model.addAttribute("topicDto",td);
		model.addAttribute("atts",attachmentService.listByTopic(id));
		model.addAttribute("cname", t.getChannel().getName());
		model.addAttribute("cid", t.getChannel().getId());
		return "topic/update";
	}
	/**function(跟新文章到数据库)
	 * @param  @param topicDto
	 * @param  @param aids
	 * @param  @param aks
	 * @param  @param br
	 * @param  @param session
	 * @param  @return         
	 * @return String 
	 * @throws                
	 * @author lh 
	 * @Date   2015年8月7日
	*/
	@RequestMapping(value="/update/{id}",method=RequestMethod.POST)
	public String update(@PathVariable int id,@Validated  TopicDto topicDto,Integer[] aids,String[] aks,  BindingResult br,HttpSession  session){
		if(br.hasErrors()){
			return "topic/update";
		}
		Topic tt = topicService.load(id);
		Topic t = topicDto.getTopic();
		StringBuffer keys = new StringBuffer();
		if(aks!=null){
			for (String k : aks) {
				keys.append(k).append("|");
				keyService.addOrUpdate(k);
			}
		}
		tt.setKeyword(keys.toString());
		tt.setChannelPicId(t.getChannelPicId());
		tt.setContent(t.getContent());
		tt.setPublishDate(tt.getPublishDate());
		tt.setRecommend(t.getRecommend());
		tt.setStatus(t.getStatus());
		tt.setSummary(t.getSummary());
		tt.setTitle(t.getTitle());
		topicService.update(tt, topicDto.getCid(), aids);
		return "redirect:/jsp/common/updateSuc.jsp";
	}
}
