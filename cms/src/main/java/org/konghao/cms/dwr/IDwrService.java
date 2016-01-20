package org.konghao.cms.dwr;
/**
 * @author   李海  Email:870721131@qq.com
 * @Date	 2015年7月15日		下午6:52:31
 * @类的作用:
 */
public interface IDwrService {
	/**
	 * 添加GroupChannel对象
	 * @param group
	 * @param channel
	 */
	public void addGroupChannel(int gid,int cid);
	/**
	 * 删除用户栏目
	 * @param gid
	 * @param cid
	 */
	public void deleteGroupChannel(int gid,int cid);
	
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
	/**function(删除附件信息)
	 * @param  @param id         
	 * @return void 
	 * @throws                
	 * @author lh 
	 * @Date   2015年7月30日
	*/
	public  void deleteAttach(int id);	
	
	/**function(更新首页图片哦位置信息,)
	 * @param  @param id      要更新的首页图片的id
	 * @param  @param oldpos  要更新的首页图片的原始位置      
	 * @return void           要把首页图片更新的新位置   
	 * @throws                
	 * @author lh 
	 * @Date   2015年9月19日
	 * 更新位置,如果原位置小于新位置,让所以大于原始位置小于等于新位置的元素全部减1之后更新该对象的位置
	 * 更新位置,如果原位置大于新位置,让所以小于原始位置大于等于新位置的元素全部加1之后更新该对象的位置
	*/
	public void updatePicPos(int id, int oldpos,int newPos);
	/**function(更新连接位置信息,)
	 * @param  @param id      要更新的首页图片的id
	 * @param  @param oldpos  要更新的首页图片的原始位置      
	 * @return void           要把首页图片更新的新位置   
	 * @throws                
	 * @author lh 
	 * @Date   2015年9月19日
	 * 更新位置,如果原位置小于新位置,让所以大于原始位置小于等于新位置的元素全部减1之后更新该对象的位置
	 * 更新位置,如果原位置大于新位置,让所以小于原始位置大于等于新位置的元素全部加1之后更新该对象的位置
	*/
	public void updateLinkPos(int id, int oldpos,int newPos);
}

