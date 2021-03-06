package org.konghao.cms.service;

import java.util.List;

import org.konghao.basic.model.Channel;
import org.konghao.basic.model.ChannelTree;

/**
 * @author   李海  Email:870721131@qq.com
 * @Date	 2015年2月9日		下午10:44:47
 * @类的作用:  此类适用于网站栏目的服务类
 */
public interface IChannelService{
	/**
	 * 添加栏目
	 * @param channel
	 * @param pid
	 */
	public  void add(Channel channel,Integer pid);
	/**
	 * 更新栏目
	 * @param channel
	 * @param pid
	 */
	public void update(Channel channel);
	/**
	 * 删除栏目
	 * @param id
	 */
	public void delete(int id);
	
	/**
	 * 清空栏目中的文章
	 * @param id
	 */
	public void clearTopic(int id);
	/**
	 * 根据ID查询一个栏目的对象
	 * @param id
	 */
	public Channel load(int id);
	
	/**
	 * 根据父亲id加载栏目，该方面首先检查SystemContext中是否存在排序如果没有存在把orders加进去
	 * @param pid
	 * @return
	 */
	public List<Channel> listByParent(Integer pid);
	/**
	 * 把所有的栏目获取并生成一颗完整的树
	 * @return
	 */
	public List<ChannelTree> generateTree();
	/**
	 * 根据父类对象获取子类栏目，并且生成树列表
	 * @param pid
	 * @return
	 */
	public List<ChannelTree> generateTreeByParent(Integer pid);
	
	/**
	 * 根据父类对象获取子类栏目，并且生成树列表（演示树的异步加载功能）
	 * @param pid
	 * @return
	 */
	public List<ChannelTree> generateTreeByParentByasync(Integer pid);
	
	/**
	 * updateSort:(储存栏目的排序)
	 * @param @param ids  栏目的id
	 * @return void    DOM对象
	 * @throws 
	 */
	public   void updateSort(Integer[]ids);
	
	/**
	 * updateSort:(获取所有的可以发布文章的栏目,栏目的状态必须为启用的)
	 * @param @param ids  栏目的id
	 * @return void    DOM对象
	 * @throws 
	 */
	public List<Channel> listPublishChannel();
	
	/**
	 * updateSort:(获取所有的导航栏目,栏目的状态必须为启用的)
	 * @param @param ids  栏目的id
	 * @return void    DOM对象
	 * @throws 
	 */
	public List<Channel> listTopNavChannel();
	
	
}

