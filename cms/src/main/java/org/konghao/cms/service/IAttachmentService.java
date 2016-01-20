package org.konghao.cms.service;

import java.io.InputStream;
import java.util.List;

import org.konghao.basic.basemodel.Pager;
import org.konghao.basic.model.Attachment;

/**ClassName:IAttachmentService
 * Function: 针对附件的service接口
 * @author   lh
 * @Date	 2015	2015年7月22日		上午5:14:47
 */
public interface IAttachmentService {
	/**function(添加附件)
	 * @param  @param a         
	 * @return void 
	 * @throws                
	 * @author lh 
	 * @Date   2015年7月22日
	*/
	public void add(Attachment  a,InputStream ins);
	/**function(根据ID删除附件)
	 * @param  @param id         
	 * @return void 
	 * @throws                
	 * @author lh 
	 * @Date   2015年7月22日
	*/
	
	public  void  delete(int id);
	/**function(根据附件ID查找附件对象)
	 * @param  @param id
	 * @param  @return         
	 * @return Attachment 
	 * @throws                
	 * @author lh 
	 * @Date   2015年7月22日
	*/
	public  Attachment  load(int id);
	/**function(查询没有被引用的附件对象并进行分页)
	 * @param  @return         
	 * @return Pager<Attachment> 
	 * @throws                
	 * @author lh 
	 * @Date   2015年7月22日
	*/
	
	public Pager<Attachment> findNoUseAttachment();
	
	/**function(查询没有被引用的附件的数量)
	 * @param  @return         
	 * @return long 
	 * @throws                
	 * @author lh 
	 * @Date   2015年7月22日
	*/
	public long findNoUseAttachmentNum();
	/**
	 * 清空没有被引用的附件
	 */
	public void clearNoUseAttachment();
	/**function(根据文章ID删除某个文章的所有附件)
	 * @param  @param tid      文章ID     
	 * @return void 
	 * @throws                
	 * @author lh 
	 * @Date   2015年7月22日
	*/
	
	public void deleteByTopic(int tid);
	/**function(根据文章ID获取某个文章的附件)
	 * @param  @param tid 文章ID
	 * @param  @return         
	 * @return List<Attachment> 
	 * @throws                
	 * @author lh 
	 * @Date   2015年7月22日
	*/
	public List<Attachment> listByTopic(int tid);
	/**function(根据一个数量获取首页图片的附件信息)  
	 * @param  @param num  数量
	 * @param  @return         
	 * @return List<Attachment> 
	 * @throws                
	 * @author lh 
	 * @Date   2015年7月22日
	*/
	public List<Attachment> listIndexPic(int num);
	/**function(根据栏目ID获取某个栏目中的附件图片信息)
	 * @param  @param cid  栏目ID
	 * @param  @return         
	 * @return Pager<Attachment> 
	 * @throws                
	 * @author lh 
	 * @Date   2015年7月22日
	*/
	
	public Pager<Attachment> findChannelPic(int cid);
	/**function(获取所有的新闻图片信息)
	 * @param  @return         
	 * @return Pager<Attachment> 
	 * @throws                
	 * @author lh 
	 * @Date   2015年7月22日
	*/
	
	public Pager<Attachment> listAllIndexPic();
	/**function(获取某篇文章的属于附件类型的附件对象)
	 * @param  @param tid   文章的id
	 * @param  @return         
	 * @return List<Attachment> 
	 * @throws                
	 * @author lh 
	 * @Date   2015年7月22日
	*/
	public List<Attachment> listAttachByTopic(int tid);
	
	
	/**function(把某个附件更新成主页图片(一般情况都是图片))
	 * @param  @param id     附件      
	 * @return void 
	 * @throws                
	 * @author lh 
	 * @Date   2015年7月30日
	*/
	public  void updateIndexPic(int id);
	/**function(吧某一个附件更新附件信息（应该不是图片）)
	 * @param  @param id         
	 * @return void 
	 * @throws                
	 * @author lh 
	 * @Date   2015年7月30日
	*/
	public  void updateAttachInfo(int id);
//	/**function(删除附件信息)
//	 * @param  @param id         
//	 * @return void 
//	 * @throws                
//	 * @author lh 
//	 * @Date   2015年7月30日
//	*/
//	public  void deleteAttach(int id);	
}
