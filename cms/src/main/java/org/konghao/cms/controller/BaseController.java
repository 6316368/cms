package org.konghao.cms.controller;
import javax.inject.Inject;

import org.konghao.cms.service.IAttachmentService;
import org.konghao.cms.service.IChannelService;
import org.konghao.cms.service.ICmsLinkService;
import org.konghao.cms.service.IGroupService;
import org.konghao.cms.service.IIndexPicService;
import org.konghao.cms.service.IKeyService;
import org.konghao.cms.service.IRoleService;
import org.konghao.cms.service.ITopicService;
import org.konghao.cms.service.IUserService;
/**
 * ClassName:BaseController 
 * 类的作用: 写一个公共的Controller,然其他的Controller继承这个Controller
 *         避免在每个Controller中有很多的service的信息
 * @author 李海
 * @Date  2015年2月1日 下午10:07:05
 */
public class BaseController {
	protected IUserService userService;
	protected IGroupService groupService;
	protected IRoleService roleService;
	protected IChannelService channelService;
	protected IKeyService keyService;
	protected IAttachmentService attachmentService;
	protected ITopicService topicService;
	protected IIndexPicService iIndexPicService;
	protected ICmsLinkService   cmsLinkService;
	protected IUserService getUserService() {
		return userService;
	}

	@Inject
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public IGroupService getGroupService() {
		return groupService;
	}
    @Inject
	public void setGroupService(IGroupService groupService) {
		this.groupService = groupService;
	}

	public IRoleService getRoleService() {
		return roleService;
	}
    @Inject
	public void setRoleService(IRoleService roleService) {
		this.roleService = roleService;
	}

	public IChannelService getChannelService() {
		return channelService;
	}
	@Inject
	public void setChannelService(IChannelService channelService) {
		this.channelService = channelService;
	}

	public IKeyService getKeyService() {
		return keyService;
	}
	@Inject
	public void setKeyService(IKeyService keyService) {
		this.keyService = keyService;
	}

	public IAttachmentService getAttachmentService() {
		return attachmentService;
	}
	@Inject
	public void setAttachmentService(IAttachmentService attachmentService) {
		this.attachmentService = attachmentService;
	}

	public ITopicService getTopicService() {
		return topicService;
	}
    @Inject
	public void setTopicService(ITopicService topicService) {
		this.topicService = topicService;
	}

	public IIndexPicService getiIndexPicService() {
		return iIndexPicService;
	}
	@Inject
	public void setiIndexPicService(IIndexPicService iIndexPicService) {
		this.iIndexPicService = iIndexPicService;
	}

	public ICmsLinkService getCmsLinkService() {
		return cmsLinkService;
	}
	@Inject
	public void setCmsLinkService(ICmsLinkService cmsLinkService) {
		this.cmsLinkService = cmsLinkService;
	}

	

	
}
