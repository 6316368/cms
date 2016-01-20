package org.konghao.cms.service.impl;

import java.util.List;

import org.konghao.basic.model.Channel;
import org.konghao.basic.model.ChannelTree;
import org.konghao.basic.model.CmsException;
import org.konghao.cms.service.BaseService;
import org.konghao.cms.service.IChannelService;
import org.springframework.stereotype.Service;

/**
 * @author   李海  Email:870721131@qq.com
 * @Date	 2015年2月9日		下午10:48:35
 * @类的作用:
 */
@Service("channelService")
public class ChannelService extends BaseService implements IChannelService {
	
	@Override
	public void add(Channel channel, Integer pid) {
		Integer orders = channelDao.getMaxOrderByParent(pid);
		if(pid!=null&&pid>0){
			Channel pc =channelDao.load(pid);
			if(pc==null) throw new CmsException("要添加栏目的父类对象不正确!");
			channel.setParent(pc);
		}
		channel.setOrders(orders+1);
		channelDao.add(channel);
	}
	@Override
	public void update(Channel channel) {
		channelDao.update(channel);
	}
	@Override
	public void delete(int id) {
		//1、需要判断是否存在子栏目
		List<Channel> cs = channelDao.listByParent(id);
		if(cs!=null&&cs.size()>0)throw new CmsException("要删除的栏目还有子栏目，无法删除");
		//TODO 2、需要判断是否存在文章
		//TODO 3、需要删除和组的关联关系
		channelDao.delete(id);
	}
	@Override
	public void clearTopic(int id) {
		//TODO 
	}
	@Override
	public Channel load(int id) {
		return channelDao.load(id);
	}
	@Override
	public List<Channel> listByParent(Integer pid) {
		return channelDao.listByParent(pid);
	}
	@Override
	public List<ChannelTree> generateTree() {
		return channelDao.generateTree();
	}
	@Override
	public List<ChannelTree> generateTreeByParent(Integer pid) {
		return channelDao.generateTreeByParent(pid);
	}
	@Override
	public List<ChannelTree> generateTreeByParentByasync(Integer pid) {
		return channelDao.generateTreeByParentByasync(pid);
		
	}
	@Override
	public void updateSort(Integer[] ids) {
		channelDao.updateSort(ids);
		
	}
	@Override
	public List<Channel> listPublishChannel() {
		return channelDao.listPublishChannel();
	}
	
	@Override
	public List<Channel> listTopNavChannel() {
		return channelDao.listTopNavChannel();
	}
	
}

