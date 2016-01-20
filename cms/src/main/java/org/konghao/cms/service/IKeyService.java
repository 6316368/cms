package org.konghao.cms.service;

import java.util.List;

import org.konghao.basic.basemodel.Pager;
import org.konghao.basic.model.Keyword;

/**ClassName:IKeyService
 * Function: 对关键子的service
 * @author   lh
 * @Date	 2015	2015年7月22日		上午5:34:17
 */
public interface IKeyService {
	
	/**function(添加或更新关键字,如果这个关键字不存在就进行添加，如果存在就进行更新，让引用次数加1)
	 * @param  @param name  关键字名称        
	 * @return void 
	 * @throws                
	 * @author lh 
	 * @Date   2015年7月22日
	*/
	public void addOrUpdate(String name);
	/**function(获取引用次数大于等于某个数的关键字)
	 * @param  @param num
	 * @param  @return         
	 * @return List<Keyword> 
	 * @throws                
	 * @author lh 
	 * @Date   2015年7月22日
	*/
	public List<Keyword> getKeywordByTimes(int num);
	/**function(获取引用次数最多个num个关键字)
	 * @param  @param num
	 * @param  @return         
	 * @return List<Keyword> 
	 * @throws                
	 * @author lh 
	 * @Date   2015年7月22日
	*/
	
	public List<Keyword> getMaxTimesKeyword(int num);
	/**function(查找没有使用的关键字)
	 * @param  @return         
	 * @return Pager<Keyword> 
	 * @throws                
	 * @author lh 
	 * @Date   2015年7月22日
	*/
	
	public Pager<Keyword> findNoUseKeyword();
	/**function(清空没有使用的关键字)
	 * @param           
	 * @return void 
	 * @throws                
	 * @author lh 
	 * @Date   2015年7月22日
	*/
	
	public void clearNoUseKeyword();
	/**function(查找正在被引用的关键字)
	 * @param  @return         
	 * @return List<Keyword> 
	 * @throws                
	 * @author lh 
	 * @Date   2015年7月22日
	*/
	
	public List<Keyword> findUseKeyword();
	/**function(根据某个条件从keyword表中查询关键字)
	 * @param  @param con
	 * @param  @return         
	 * @return List<Keyword> 
	 * @throws                
	 * @author lh 
	 * @Date   2015年7月22日
	*/
	public List<Keyword> listKeywordByCon(String con);
	
	public List<String> listKeywordStringByCon(String con);
	
}
