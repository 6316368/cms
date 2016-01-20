package org.konghao.cms.service.impl;

import java.util.Date;
import java.util.List;

import org.konghao.basic.basemodel.Pager;
import org.konghao.basic.model.Attachment;
import org.konghao.basic.model.Channel;
import org.konghao.basic.model.CmsException;
import org.konghao.basic.model.Topic;
import org.konghao.basic.model.User;
import org.konghao.cms.service.BaseService;
import org.konghao.cms.service.ITopicService;
import org.springframework.stereotype.Service;
@Service("topicservice")
public class TopicService extends BaseService implements ITopicService {
	

	@Override
	public void add(Topic topic, int cid, int uid, Integer[] aids)  {
		Channel c = channelDao.load(cid);
        User u = userDao.load(uid);
        if(c==null||u==null){
        	throw  new CmsException("要添加的文章必须要有用户和栏目");
        }
        else{
        	topic.setAuthor(u.getNickname());
        	topic.setCname(c.getName());
        	topic.setCreateDate(new Date());
        	topic.setChannel(c);
        	topic.setUser(u);
        	topicDao.add(topic);
        	if(aids!=null){
        		for (Integer aid : aids) {
        			Attachment a = attachmentDao.load(aid);
        			if(a==null)continue;
        			a.setTopic(topic);
        		}
        	}
        	
        }
	}

	@Override
	public void add(Topic topic, int cid, int uid)  {
		add(topic, cid, uid, null);

	}

	@Override
	public void delete(int id) {
		List<Attachment> atts = attachmentDao.listByTopic(id);
        attachmentDao.deleteByTopic(id);
        topicDao.delete(id);
        //删除硬盘上的文件
        for (Attachment attachment : atts) {
			AttachmentService.deleteAttachFiles(attachment);
		}
                
	}

	@Override
	public void update(Topic topic, int cid, Integer[] aids) {
		Channel c = channelDao.load(cid);
		if(c==null)throw new CmsException("要更新的文章必须有用户和栏目");
		topic.setCname(c.getName());
		topic.setChannel(c);
		topicDao.update(topic);
        addTopicAtts(topic, aids);
	}
	
	private void addTopicAtts(Topic topic,Integer[] aids) {
		if(aids!=null) {
			for(Integer aid:aids) {
				Attachment a = attachmentDao.load(aid);
				if(a==null) continue;
				a.setTopic(topic);
			}
		}
	}

	@Override
	public void update(Topic topic, int cid) {
      update(topic, cid, null);
	}

	@Override
	public Topic load(int id) {
		return topicDao.load(id);
	}

	@Override
	public Pager<Topic> find(Integer cid, String title, Integer status) {
		return topicDao.find(cid, title, status);
	}

	@Override
	public Pager<Topic> find(Integer uid, Integer cid, String title,
			Integer status) {
		return topicDao.find(uid, cid, title, status);
	}

	@Override
	public Pager<Topic> searchTopicByKeyword(String keyword) {
		return topicDao.searchTopicByKeyword(keyword);
	}

	@Override
	public Pager<Topic> searchTopic(String con) {
		return topicDao.searchTopic(con);
	}

	@Override
	public Pager<Topic> findRecommendTopic(Integer ci) {
		return topicDao.findRecommendTopic(ci);
	}

	@Override
	public List<Topic> listTopicByChannelAndNumber(int cid, int num) {
		return topicDao.listTopicByChannelAndNumber(cid, num);
	}

	@Override
	public List<Topic> listTopicsByChannel(int cid) {
		return topicDao.listTopicsByChannel(cid);
	}

	@Override
	public boolean isUpdateIndex(int cid) {
		return topicDao.isUpdateIndex(cid);
	}
	@Override
	public Topic loadLastedTopicByColumn(int cid) {
		return topicDao.loadLastedTopicByColumn(cid);
	}
	@Override
	public void upateStatus(int id) {
		Topic t = topicDao.load(id);
		System.out.println("---------T------------"+t.getTitle());
		if(t.getStatus()==0){
			t.setStatus(1);
		} 
		else {
			t.setStatus(0);	
		}
		topicDao.update(t);
	}

}
