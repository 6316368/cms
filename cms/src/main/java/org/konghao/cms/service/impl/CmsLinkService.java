package org.konghao.cms.service.impl;

import java.util.List;
import java.util.Map;

import org.konghao.basic.basemodel.Pager;
import org.konghao.basic.model.CmsLink;
import org.konghao.cms.service.BaseService;
import org.konghao.cms.service.ICmsLinkService;
import org.springframework.stereotype.Service;

@Service("CmsLinkService")
public class CmsLinkService extends  BaseService implements ICmsLinkService {

	@Override
	public void add(CmsLink cmslink) {
        cmsLinkDao.add(cmslink);
	}

	@Override
	public void delete(int id) {
		cmsLinkDao.delete(id);

	}

	@Override
	public void update(CmsLink cmslink) {
		cmsLinkDao.update(cmslink);

	}

	@Override
	public CmsLink load(int id) {
		return cmsLinkDao.load(id);
	}
	@Override
	public Pager<CmsLink> findByType(String type) {
		return cmsLinkDao.findByType(type);
	}

	@Override
	public List<CmsLink> listByType(String type) {
		return cmsLinkDao.listByType(type);
	}

	@Override
	public List<String> listAllType() {
		return cmsLinkDao.listAllType();
	}

	@Override
	public Map<String, Integer> getMinAndMaxPos() {
		return cmsLinkDao.getMinAndMaxPos();
	}

	@Override
	public void updatePos(int id, int oldPos, int newPos) {
		cmsLinkDao.updatePos(id, oldPos, newPos);

	}

}
