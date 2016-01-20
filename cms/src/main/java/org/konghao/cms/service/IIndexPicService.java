package org.konghao.cms.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.konghao.basic.basemodel.Pager;
import org.konghao.basic.model.IndexPic;

public interface IIndexPicService {
public void add(IndexPic indexPic);
public void update(IndexPic indexPic);
public void delete(int  id);
public void updateStatus(int id);
public IndexPic load(int id);


/**
 * 根据数量来获取首页图片信息
 * @param num
 * @return
 */
public List<IndexPic> listIndexPicByNum(int num);

public Pager<IndexPic> findIndexPic();
/**
 * 获取所有的首页图片名称
 * @return
 */
public List<String> listAllIndexPicName();

public Map<String,Integer> getMinAdnMaxPos();
/**
 * 更新位置，如果原位置小于新位置，让所有>原始位置，<=新位置的元素全部-1之后更新对象的位置
 * 如果原位置大于新位置，让所有小于原位置>=新位置的元素全部+1，之后更新当前元素
 * @param id
 * @param oldPos原始的位置
 * @param newPos新的位置
 */

public void updatePos(int id,int oldPos,int newPos);

/**function(清空没有被引用的首页图片)
 * @param  @param pics
 * @param  @throws IOException         
 * @return void 
 * @throws                
 * @author lh 
 * @Date   2015年9月15日
*/

public void cleanNoUseIndexPic(List<String> pics) throws IOException;
}
