package org.konghao.cms.controller;

import java.util.List;

import org.konghao.basic.basemodel.BackupFile;
import org.konghao.basic.basemodel.SystemContext;
import org.konghao.basic.util.BackupFileUtil;
import org.konghao.cms.auth.AuthClass;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@AuthClass
@Controller
@RequestMapping("/admin")
public class BackupController {

	/**function(备份的文件)
	 * @param  @return         
	 * @return String 
	 * @throws                
	 * @author lh 
	 * @Date   2015年9月30日
	*/
	
	@RequestMapping(value="/backup/add",method=RequestMethod.GET)
	public String backup(){
		return "backup/add";
	}
	/**function(列出所有的备份文件)
	 * @param  @param model
	 * @param  @return         
	 * @return String 
	 * @throws                
	 * @author lh 
	 * @Date   2015年9月30日
	*/
	
	@RequestMapping(value="/backups")
	public String list(Model  model){
		List<BackupFile> listBackups = BackupFileUtil.getInstance(SystemContext.getRealPath()).listBackups();
		model.addAttribute("backups", listBackups);
		return "backup/list";
	}
	/**function(开始进行备份)
	 * @param  @param backupFilename
	 * @param  @return         
	 * @return String 
	 * @throws                
	 * @author lh 
	 * @Date   2015年10月1日
	*/
	@RequestMapping(value="/backup/add",method=RequestMethod.POST)
	public String backup(String backupFilename) {
		BackupFileUtil bfu = BackupFileUtil.getInstance(SystemContext.getRealPath());
		bfu.backup(backupFilename);
		return "redirect:/admin/backups";
	}
	/**function(删除备份文件)
	 * @param  @param name
	 * @param  @param type
	 * @param  @return         
	 * @return String 
	 * @throws                
	 * @author lh 
	 * @Date   2015年10月1日
	*/
	
	@RequestMapping("delete/{name}")
	public String delete(@PathVariable String name,String type) {
		BackupFileUtil bfu = BackupFileUtil.getInstance(SystemContext.getRealPath());
		bfu.delete(name+"."+type);
		return "redirect:/admin/backups";
	}
	
	/**function(根据名称恢复备份)
	 * @param  @param name
	 * @param  @param type
	 * @param  @return         
	 * @return String 
	 * @throws                
	 * @author lh 
	 * @Date   2015年10月1日
	*/
	
	@RequestMapping("resume/{name}")
	public String resume(@PathVariable String name,String type) {
		BackupFileUtil bfu = BackupFileUtil.getInstance(SystemContext.getRealPath());
		bfu.resume(name+"."+type);
//		indexService.generateTop();
//		indexService.generateBody();
//		indexService.generateBottom();
		return "redirect:/admin/backups";
	}
}
