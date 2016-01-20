package org.konghao.cms.service;

import javax.inject.Inject;

import org.konghao.basic.dao.IAttachmentDao;
import org.konghao.basic.dao.IChannelDao;
import org.konghao.basic.dao.ICmsLinkDao;
import org.konghao.basic.dao.IGroupDao;
import org.konghao.basic.dao.IIndexPicDao;
import org.konghao.basic.dao.IKeywordDao;
import org.konghao.basic.dao.IRoleDao;
import org.konghao.basic.dao.ITopicDao;
import org.konghao.basic.dao.IUserDao;

public class BaseService {

	protected IGroupDao groupDao;
	protected IUserDao userDao;
	protected IChannelDao channelDao;
	protected IRoleDao roleDao;
	protected ITopicDao topicDao;
	protected IAttachmentDao  attachmentDao;
	protected IIndexPicDao indexPicDao;
	protected ICmsLinkDao  cmsLinkDao;
	
	protected  IKeywordDao keywordDao;
	public IGroupDao getGroupDao() {
		return groupDao;
	}
	@Inject
	public void setGroupDao(IGroupDao groupDao) {
		this.groupDao = groupDao;
	}
	public IUserDao getUserDao() {
		return userDao;
	}
	@Inject
	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}
	public IChannelDao getChannelDao() {
		return channelDao;
	}
	@Inject
	public void setChannelDao(IChannelDao channelDao) {
		this.channelDao = channelDao;
	}
	public IRoleDao getRoleDao() {
		return roleDao;
	}
	@Inject
	public void setRoleDao(IRoleDao roleDao) {
		this.roleDao = roleDao;
	}
	public ITopicDao getTopicDao() {
		return topicDao;
	}
	@Inject
	public void setTopicDao(ITopicDao topicDao) {
		this.topicDao = topicDao;
	}
	public IAttachmentDao getAttachmentDao() {
		return attachmentDao;
	}
	@Inject
	public void setAttachmentDao(IAttachmentDao attachmentDao) {
		this.attachmentDao = attachmentDao;
	}
	public IKeywordDao getKeywordDao() {
		return keywordDao;
	}
	@Inject
	public void setKeywordDao(IKeywordDao keywordDao) {
		this.keywordDao = keywordDao;
	}
	public IIndexPicDao getIndexPicDao() {
		return indexPicDao;
	}
	@Inject
	public void setIndexPicDao(IIndexPicDao indexPicDao) {
		this.indexPicDao = indexPicDao;
	}
	public ICmsLinkDao getCmsLinkDao() {
		return cmsLinkDao;
	}
	@Inject
	public void setCmsLinkDao(ICmsLinkDao cmsLinkDao) {
		this.cmsLinkDao = cmsLinkDao;
	}
	
	
}
