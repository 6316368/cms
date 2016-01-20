package org.konghao.cms.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.Thumbnails.Builder;

import org.apache.commons.io.FilenameUtils;
import org.konghao.basic.basemodel.SystemContext;
import org.konghao.basic.model.BaseInfo;
import org.konghao.basic.model.IndexPic;
import org.konghao.basic.model.Topic;
import org.konghao.basic.util.JsonUtil;
import org.konghao.cms.auth.AuthMethod;
import org.konghao.cms.dto.AjaxObj;
import org.konghao.cms.dto.TopicDto;
import org.konghao.cms.service.impl.IndexPicService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/admin/pic")
@Controller
public class IndexPicController extends BaseController{
	public final static int T_W = 120;
    @RequestMapping("/indexPics")
	public String listIndexPic(Model  model){
    	
    	model.addAttribute("datas",iIndexPicService.findIndexPic());
    	Map<String, Integer> minAdnMaxPos = iIndexPicService.getMinAdnMaxPos();
		model.addAttribute("min", minAdnMaxPos.get("min"));
		model.addAttribute("max", minAdnMaxPos.get("max"));
		return "pic/listIndexPic";
	}
    @RequestMapping(value="/addIndexPic",method=RequestMethod.GET)
	public String addIndexPics(Model  model){
    	IndexPic indexPic = new IndexPic();
    	indexPic.setStatus(1);
		model.addAttribute(indexPic);
		return "pic/addIndexPic";
	}
    @RequestMapping(value="/addIndexPic",method=RequestMethod.POST)
	public String addIndexPic(@Validated IndexPic indexPic,BindingResult br) {
		if(br.hasFieldErrors()) {
			return "pic/addIndexPic";
		}
		iIndexPicService.add(indexPic);
//		if(indexPic.getStatus()!=0) {
//			iIndexPicService.generateBody();
//		}
		return "redirect:/jsp/common/addSuc.jsp";
	}
    
    @RequestMapping(value="/uploadIndexPic" ,method=RequestMethod.POST)
    public void uploadIndexPic(HttpSession session,HttpServletResponse resp,MultipartFile  pic){
      resp.setContentType("text/plain;charset=utf-8");	
      PrintWriter out=null;
      AjaxObj obj=new AjaxObj();
      String oldname=pic.getOriginalFilename();
      String newName=new Date().getTime()+"."+FilenameUtils.getExtension(oldname);
      //图片压缩后储存的路径
      String  realpath=SystemContext.getRealPath();
      String thumbPath = realpath+"/resources/indexPic/temp";
      //如果文件不存在的话，自动创建文件夹
       File  file=new File(thumbPath);
		if(!file.exists()){
			file.mkdirs();
		}
      try {
    	  BufferedImage bi=ImageIO.read(pic.getInputStream());
    	  //得到图片的高度和宽度
    	  double  Width= bi.getWidth();
    	  double  height=bi.getHeight();
    	  BaseInfo baseinfo = (BaseInfo)session.getServletContext().getAttribute("baseInfo"); 
    	  //得到默认配置文件中图片设定的图片的宽度和高度
    	  double indexPicWidth = baseinfo.getIndexPicWidth();
    	  double indexPicHeight = baseinfo.getIndexPicHeight();
    	   if(Width>indexPicWidth&&Width/height<indexPicWidth/indexPicHeight){
    		//判断是否进行缩放
    		   Builder<BufferedImage> bf = Thumbnails.of(bi);
    		   if(Width-indexPicWidth>150){
    			   bf.scale((indexPicWidth+150)/Width);
    		   }
    		   else{
    			   bf.scale(1.0f);
    		   }
    		   bf.toFile(thumbPath+"/"+newName);
    		   BufferedImage asBufferedImage = bf.asBufferedImage();
    		   IndexPic  p=new IndexPic();
    		   p.setOldName(oldname);
    		   p.setNewName(newName);
    		   p.setIndexPicHeight(indexPicHeight);
    		   p.setIndexPicWidth(indexPicWidth);
    		   p.setImgWidth(asBufferedImage.getWidth());
    		   p.setImgHeight(asBufferedImage.getHeight());
    		   obj.setObj(p);
    		   obj.setResult(1);
    	   }
    	  
    	   else{
    		   obj.setResult(0);
    		   obj.setMsg("图片的尺寸不在有效范围中");
    	   }
		   out=resp.getWriter();
	} catch (IOException e) {
		obj.setResult(0);
		obj.setMsg(e.getMessage());
		e.printStackTrace();
	}
      out.print(JsonUtil.getInstance().obj2json(obj));
      out.flush();
    }
    @RequestMapping(value="/confirmPic",method=RequestMethod.POST)
    public @ResponseBody AjaxObj confirmPic(HttpSession session,int x,int y,int w,int h,String newName){
    	AjaxObj obj = new AjaxObj();
		try {
			BaseInfo baseinfo = (BaseInfo)session.getServletContext().getAttribute("baseInfo"); 
			//得到默认配置文件中图片设定的图片的宽度和高度
			double indexPicWidth = baseinfo.getIndexPicWidth();
			double indexPicHeight = baseinfo.getIndexPicHeight();
			String path=session.getServletContext().getRealPath("");
			//得到这个源文件的路径
			String tpath = path+"/resources/indexPic/temp/"+newName;
			//得到这个源文件的file对象
			File tf = new File(tpath);
			//读取文件的image的的对象
			BufferedImage bi = ImageIO.read(tf);
			//得到新文件的路径
			String npath=path+"/resources/indexPic";
			File nfile=new  File(npath);
			if(!nfile.exists()){
				nfile.mkdirs();
			}
			npath=npath+"/"+newName;
			//得到缩略图的路径
			String  ttpath=path+"/resources/indexPic/thumbnail";
			File ttfile=new  File(ttpath);
			if(!ttfile.exists()){
				ttfile.mkdirs();
			}
			ttpath=ttpath+"/"+newName;
			Builder<BufferedImage> b=Thumbnails.of(bi);
			//保存原图的路径
			BufferedImage bi2 = b.sourceRegion(x, y, w, h).size((int)indexPicWidth, (int)indexPicHeight).asBufferedImage();
			b.toFile(npath);
			//保存缩略图的路径
			Thumbnails.of(bi2).scale((double)T_W/(double)indexPicWidth).toFile(ttpath);
			//删除源文件下的图片
			tf.delete();
			obj.setResult(1);
			return obj;
		} catch (IOException e) {
			e.printStackTrace();
			obj.setResult(0);
			obj.setMsg(e.getMessage());
		}
    	return obj;
    }
    
    @RequestMapping(value="/shwoindexPic/{id}")
    public String showIndexPic(@PathVariable int id,Model model){
    	model.addAttribute("indexPic", iIndexPicService.load(id));
    	return "pic/showIndexPic";
    }
    
    @RequestMapping(value="/deleteIndexpic/{id}")
    public String deleteIndexpic(@PathVariable int id,Model model){
    	iIndexPicService.delete(id);
    	return "redirect:/admin/pic/indexPics";
    }
    @RequestMapping(value="/updateStatus/{id}")
    public String updateStatus(@PathVariable int id,Model model){
    	iIndexPicService.updateStatus(id);
    	return "redirect:/admin/pic/indexPics";
    }
    
    /**function(到达跟首页图片页面)
	 * @param  @param model
	 * @param  @return         
	 * @return String 
	 * @throws                
	 * @author lh 
	 * @Date   2015年8月7日
	*/
	@RequestMapping(value="/updateIndexpic/{id}",method=RequestMethod.GET)
	@AuthMethod(role="ROLE_PUBLISH")
	public String updateIndexpic(@PathVariable int id,Model  model) {
		IndexPic indexpic=iIndexPicService.load(id);
		model.addAttribute("indexPic", indexpic);
		return "pic/updateIndexPic";
	}
	/**function(跟新首页图片到数据库)
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
	@RequestMapping(value="/updateIndexpic/{id}",method=RequestMethod.POST)
	public String updateIndexpic(@PathVariable int id,@Validated IndexPic  indexPic, BindingResult br){
		if(br.hasErrors()){
			return "pic/updateIndexPic";
		}
		IndexPic tip= iIndexPicService.load(id);
		String realPath = SystemContext.getRealPath();
		realPath +="/resources/indexPic/thumbnail/";
		new File(realPath+tip.getNewName()).delete();
		realPath = SystemContext.getRealPath();
		new File(realPath+"/resources/indexPic/"+tip.getNewName()).delete();
		tip.setLinkType(indexPic.getLinkType());
		tip.setLinkUrl(indexPic.getLinkUrl());
		tip.setNewName(indexPic.getNewName());
		tip.setStatus(indexPic.getStatus());
		tip.setOldName(indexPic.getOldName());
		tip.setSubTitle(indexPic.getSubTitle());
		tip.setTitle(indexPic.getTitle());
		iIndexPicService.update(tip);
		return "redirect:/jsp/common/updateSuc.jsp";
	}
	@RequestMapping("/newPics")
	public String listNewPic(Model model){
		model.addAttribute("datas", attachmentService.listAllIndexPic());
		return "pic/listNewPic";
	}
}
