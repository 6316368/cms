package org.konghao.cms.dwr.impl;

import javax.inject.Inject;

import org.konghao.cms.dwr.IDwrService;
import org.konghao.cms.service.IAttachmentService;
import org.konghao.cms.service.ICmsLinkService;
import org.konghao.cms.service.IGroupService;
import org.konghao.cms.service.IIndexPicService;
import org.konghao.cms.service.impl.IndexPicService;
import org.springframework.stereotype.Service;

/**
 * @author   李海  Email:870721131@qq.com
 * @Date	 2015年7月15日		下午6:55:55
 * @类的作用:
 */
@Service("dwrService")
public class DwrServiceImpl  implements IDwrService {
	private IGroupService groupservice;
	private  IAttachmentService attachmentService;
	private  IIndexPicService indexPicService;
	private ICmsLinkService  cmsLinkService;
	public IGroupService getGroupservice() {
		return groupservice;
	}
    @Inject
	public void setGroupservice(IGroupService groupservice) {
		this.groupservice = groupservice;
	}
	public IAttachmentService getAttachmentService() {
		return attachmentService;
	}
	@Inject
	public void setAttachmentService(IAttachmentService attachmentService) {
		this.attachmentService = attachmentService;
	}
	
	
	public IIndexPicService getIndexPicService() {
		return indexPicService;
	}
	@Inject
	public void setIndexPicService(IIndexPicService indexPicService) {
		this.indexPicService = indexPicService;
	}
	
	
	public ICmsLinkService getCmsLinkService() {
		return cmsLinkService;
	}
	@Inject
	public void setCmsLinkService(ICmsLinkService cmsLinkService) {
		this.cmsLinkService = cmsLinkService;
	}
	@Override
	public void addGroupChannel(int gid, int cid) {
		groupservice.addGroupChannel(gid, cid);
	}

	@Override
	public void deleteGroupChannel(int gid, int cid) {
		groupservice.deleteGroupChannel(gid, cid);
	}
	@Override
	public void updateIndexPic(int id) {
		attachmentService.updateIndexPic(id);
		
	}
	@Override
	public void updateAttachInfo(int id) {
		attachmentService.updateAttachInfo(id);
	}
	@Override
	public void deleteAttach(int id) {
		attachmentService.delete(id);
	}
	@Override
	public void updatePicPos(int id, int oldpos,int newPos) {
		indexPicService.updatePos(id, oldpos, newPos);
	}
	public void updateLinkPos(int id, int oldpos,int newPos){
		cmsLinkService.updatePos(id, oldpos, newPos);
	}

}

