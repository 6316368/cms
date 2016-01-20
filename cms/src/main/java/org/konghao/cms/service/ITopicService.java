package org.konghao.cms.service;

import java.util.List;

import org.konghao.basic.basemodel.Pager;
import org.konghao.basic.model.Topic;

/**ClassName:ITopicService
 * Function: 针对文章的操作的service接口
 * @author   lh
 * @Date	 2015	2015年7月21日		下午10:40:23
 */
public interface ITopicService {
	/**function(添加带有附件信息的文章)
	 * @param  @param topic 文章的对象
	 * @param  @param cid   栏目
	 * @param  @param uid   文章的用户
	 * @param  @param aids  文件的附件ID       
	 * @return void 
	 * @author lh 
	 * @throws Exception 
	 * @Date   2015年7月21日
	*/
	public void  add( Topic topic,int cid,int uid,Integer[]aids) ;
	/**function(添加没有附件信息的文章)
	 * @param  @param topic 文章的对象
	 * @param  @param cid    栏目
	 * @param  @param uid   文章的用户
	 * @return void 
	 * @author lh 
	 * @throws Exception 
	 * @Date   2015年7月21日
	*/
	public void  add( Topic topic,int cid,int uid) ;
	/**function(删除文章,需要先删除附件信息,还需要删除附件的文件对象)
	 * @param  @param  附件ID        
	 * @return void 
	 * @throws                
	 * @author lh 
	 * @Date   2015年7月21日
	*/
	public   void delete(int id);
	/**function(带附件的更新文章的信息带附件)
	 * @param  @param topic  文章对象
	 * @param  @param cid    栏目
	 * @param  @param aids   附件ID     
	 * @return void 
	 * @throws                
	 * @author lh 
	 * @Date   2015年7月21日
	*/
	public void update(Topic topic,int cid,Integer[]aids);
	/**function(不带附件的更新文章的信息带附件)
     * @param  @param topic  文章对象
	 * @param  @param cid    栏目
	 * @return void 
	 * @throws                
	 * @author lh 
	 * @Date   2015年7月21日
	*/
	public void update(Topic topic,int cid);
	
	/**function(根据文章的ID查找文件的对象)
	 * @param  @param id  文章的ID
	 * @param  @return         
	 * @return Topic      文章的对象信息
	 * @throws                
	 * @author lh 
	 * @Date   2015年7月21日
	*/
	public  Topic load(int id);
	/**function(根据栏目和文章标题和文章状态进行文章的检索)
	 * @param  @param cid    文章的栏目
	 * @param  @param title  文章的标题
	 * @param  @param status 文章的状态
	 * @param  @return         
	 * @return Pager<Topic>  返回文章的对象并进行分页
	 * @throws                
	 * @author lh 
	 * @Date   2015年7月21日
	*/
	
	public Pager<Topic> find(Integer cid,String title,Integer status);
	/**function(根据用户ID，栏目ID和文章标题和文章状态进行检索)
	 * @param  @param uid    用户ID
	 * @param  @param cid    文章栏目ID
	 * @param  @param title  文章的标题
	 * @param  @param status 文章的状态
	 * @param  @return         
	 * @return Pager<Topic>  文章的对象并进行分页
	 * @throws                
	 * @author lh 
	 * @Date   2015年7月21日
	*/
	public Pager<Topic> find(Integer uid,Integer cid,String title,Integer status);
	/**function(根据关键字进行文章的检索，仅仅只是检索关键字类似的)
	 * @param  @param keyword 文章的关键字
	 * @param  @return         
	 * @return Pager<Topic>  文章的对象并进行分页
	 * @throws                
	 * @author lh 
	 * @Date   2015年7月21日
	*/
	public Pager<Topic> searchTopicByKeyword(String keyword);
	/**function(通过某个条件来检索，该条件会在标题，内容和摘要中检索)
	 * @param  @param con 查找的条件
	 * @param  @return         
	 * @return Pager<Topic> 文章的对象并进行分页
	 * @throws                
	 * @author lh 
	 * @Date   2015年7月21日
	*/
	public Pager<Topic> searchTopic(String con);
	/**function(检索某个栏目的推荐文章，如果cid为空，表示检索全部的文章)
	 * @param  @param ci  文章的栏目ID
	 * @param  @return         
	 * @return Pager<Topic> 
	 * @throws                
	 * @author lh 
	 * @Date   2015年7月21日
	*/
	public Pager<Topic> findRecommendTopic(Integer ci);
	/**function(根据栏目和文章的数量获取该栏目中的文章)
	 * @param  @param cid  栏目id
	 * @param  @param num  文章数量
	 * @param  @return         
	 * @return List<Topic> 
	 * @throws                
	 * @author lh 
	 * @Date   2015年7月21日
	*/
	public List<Topic> listTopicByChannelAndNumber(int cid,int num);
	/**function(根据栏目获取该栏目中的文章)
	 * @param  @param cid  栏目id
	 * @param  @return         
	 * @return List<Topic> 
	 * @throws                
	 * @author lh 
	 * @Date   2015年7月21日
	*/
	public List<Topic> listTopicsByChannel(int cid);
	/**function(判断所添加文章的栏目是否需要进行更新)
	 * @param  @param cid  栏目ID
	 * @param  @return         
	 * @return boolean 
	 * @throws                
	 * @author lh 
	 * @Date   2015年7月21日
	*/
	public boolean isUpdateIndex(int cid);
	/**function(获取某个栏目中的最新的可用文章)
	 * @param  @param cid  栏目ID
	 * @param  @return         
	 * @return Topic 
	 * @throws                
	 * @author lh 
	 * @Date   2015年7月21日
	*/
	public Topic loadLastedTopicByColumn(int cid);
	
	/**function(更新文章的状态)
	 * @param  @param 文章id         
	 * @return void 
	 * @throws                
	 * @author lh 
	 * @Date   2015年7月21日
	*/
	public void upateStatus(int id);
}
