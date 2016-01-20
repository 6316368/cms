package org.konghao.cms.controller;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.konghao.basic.basemodel.SystemContext;
import org.konghao.basic.model.BaseInfo;
import org.konghao.basic.util.ConfigBase;
import org.konghao.basic.util.HashMap2ObjUntil;
import org.konghao.cms.auth.AuthClass;
import org.konghao.cms.service.BaseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**ClassName:SystemController
 * Function: 用于对网站进行管理的类
 * @author   lh
 * @Date	 2015	2015年8月21日		下午11:28:46
 */
@RequestMapping("/admin/system")
@Controller
@AuthClass
public class SystemController extends BaseController{

	/**function(显示网站的基本信息)
	 * @param  @return         
	 * @return String 
	 * @throws                
	 * @author lh 
	 * @Date   2015年8月21日
	*/
	@RequestMapping("/baseinfo")
	public String showBaseInfo() {
		return "system/showBaseInfo";
	}
	
	
	/**function(去修改网站信息的界面)
	 * @param  @param session
	 * @param  @param model
	 * @param  @return         
	 * @return String 
	 * @throws                
	 * @author lh 
	 * @Date   2015年8月21日
	*/
	@RequestMapping(value="/baseinfo/update",method=RequestMethod.GET)
	public String updateBaseInfo(HttpSession session,Model model) {
		model.addAttribute("baseInfo", session.getServletContext().getAttribute("baseInfo"));
		return "system/updateBaseInfo";
	}
	/**function(修改网站信息并同时更新文件信息)
	 * @param  @param baseInfo
	 * @param  @param br
	 * @param  @param session
	 * @param  @return         
	 * @return String 
	 * @throws                
	 * @author lh 
	 * @Date   2015年8月21日
	*/
	@RequestMapping(value="/baseinfo/update",method=RequestMethod.POST)
	public String updateBaseInfo(@Validated BaseInfo baseInfo,BindingResult br,HttpSession session) {
		if(br.hasErrors()) {
			return "system/updateBaseInfo";
		}
		 ConfigBase  configbase=new ConfigBase("baseinfo.properties");
//		 Class<? extends Object> clz = baseInfo.getClass();
//		 Field[] fields = clz.getDeclaredFields();
//         for (Field field : fields) {// --for() begin
//        	   System.out.println("---field---"+field.getName()+"-----value--------"+baseInfo);
//        	 // configbase.put(field.getName(), baseInfo.getAddress());
//         }
		 configbase.put("name", baseInfo.getName());
		 configbase.put("address", baseInfo.getAddress());
		 configbase.put("zipCode", baseInfo.getZipCode());
		 configbase.put("recordCode", baseInfo.getRecordCode());
		 configbase.put("phone", baseInfo.getPhone());
		 configbase.put("email", baseInfo.getEmail());
		 configbase.put("indexPicWidth", String.valueOf(baseInfo.getIndexPicWidth()));
		 configbase.put("indexPicHeight", String.valueOf(baseInfo.getIndexPicHeight()));
		 configbase.put("indexPicNumber", String.valueOf(baseInfo.getIndexPicNumber()));
		 configbase.put("domainName", baseInfo.getDomainName());
         configbase.saveConfigfile();
         BaseInfo bi = (BaseInfo)HashMap2ObjUntil.getInstance().hashMap2Obj(configbase, BaseInfo.class);
		 session.getServletContext().setAttribute("baseInfo", bi);
		return "redirect:/admin/system/baseinfo";
	}
	@RequestMapping("/cleans")
	public String listNeedClear(Model  model){
		//没有被引用的福建数量
		model.addAttribute("attNums", attachmentService.findNoUseAttachmentNum());
		model.addAttribute("indexPics", listNouseIndexPicNum(iIndexPicService.listAllIndexPicName()));
		
		return "system/cleans";
	}
	
	/**function(查询没有被使用的首页的图片)
	 * @param  @return         
	 * @return File[] 
	 * @throws                
	 * @author lh 
	 * @Date   2015年9月15日
	*/
	
	private File[] listPicFile(){
		String path=SystemContext.getRealPath();
		File f = new File(path+"/resources/indexPic");
		File[] fns=f.listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				if(pathname.isDirectory()){
					return false;
				}
				return true;
			}
		});
		return fns;
	}
	
	/**function()返回没有被on个的首页图片的数量
	 * @param  @param pics
	 * @param  @return         
	 * @return int 
	 * @throws                
	 * @author lh 
	 * @Date   2015年9月15日
	*/
	private int listNouseIndexPicNum(List<String> pics){
		File[] fns = listPicFile();
		int count =0;
		for (File file : fns) {
			if(!pics.contains(file.getName())){
				count ++;
			}
		}
		return count;
		
	}
	/**function()返回没有被on个的首页图片的列表
	 * @param  @param pics
	 * @param  @return         
	 * @return int 
	 * @throws                
	 * @author lh 
	 * @Date   2015年9月15日
	*/
	private List<String> listNouseIndexPic(List<String> pics){
		File[] fns = listPicFile();
		List<String> npics=new ArrayList<String>();
		for (File f : fns) {
			if(!pics.contains(f.getName())){
				npics.add(f.getName());
			}
		}
		return npics;
	}
	
	/**function(查询所有没有被引用的附件和首页图片)
	 * @param  @param name
	 * @param  @param model
	 * @param  @return         
	 * @return String 
	 * @throws                
	 * @author lh 
	 * @Date   2015年9月15日
	*/
	
	@RequestMapping("/cleanList/{name}")
	public String cleanList(@PathVariable String name,Model model) {
		if(name.equals("atts")) {
			model.addAttribute("datas", attachmentService.findNoUseAttachment());
			return "system/cleanAtts";
		} else if(name.equals("pics")) {
			model.addAttribute("datas", listNouseIndexPic(iIndexPicService.listAllIndexPicName()));
			return "system/cleanPics";
		}
		return "";
	}
	
	/**function(清空没有被引用的福建和首页图片)
	 * @param  @param name
	 * @param  @param model
	 * @param  @return         
	 * @return String 
	 * @throws IOException 
	 * @throws                
	 * @author lh 
	 * @Date   2015年9月15日
	*/
	@RequestMapping("/clean/{name}")
	public String clean(@PathVariable String name,Model model) throws IOException {
		if(name.equals("atts")) {
			attachmentService.clearNoUseAttachment();
		} else if(name.equals("pics")) {
			iIndexPicService.cleanNoUseIndexPic(listNouseIndexPic(iIndexPicService.listAllIndexPicName()));
		}
		return "redirect:/admin/system/cleans";
	}
	
}
