package org.konghao.cms.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.konghao.basic.basemodel.Pager;
import org.konghao.basic.basemodel.SystemContext;
import org.konghao.basic.dao.IIndexPicDao;
import org.konghao.basic.model.IndexPic;
import org.konghao.cms.service.BaseService;
import org.konghao.cms.service.IIndexPicService;
import org.springframework.stereotype.Service;
@Service("indexPicService")
public class IndexPicService extends BaseService implements IIndexPicService{
	@Override
	public void add(IndexPic indexPic) {
		indexPic.setCreateDate(new Date());
		indexPicDao.add(indexPic);
		
	}

	@Override
	public void update(IndexPic indexPic) {
		indexPicDao.update(indexPic);
		
	}

	@Override
	public void delete(int  id) {
		IndexPic pic = indexPicDao.load(id);
		String realPath = SystemContext.getRealPath();
		realPath +="/resources/indexPic/thumbnail/";
		System.out.println("----------------"+realPath+pic.getNewName());
		new File(realPath+pic.getNewName()).delete();
		realPath = SystemContext.getRealPath();
		new File(realPath+"/resources/indexPic/"+pic.getNewName()).delete();
		indexPicDao.delete(id);
	}

	@Override
	public void updateStatus(int id) {
		IndexPic indexpic = indexPicDao.load(id);
		if(indexpic.getStatus()==0){
			indexpic.setStatus(1);
		}
		else{
			indexpic.setStatus(0);
		}
		indexPicDao.update(indexpic);
	}

	@Override
	public IndexPic load(int id) {
		return indexPicDao.load(id);
	}

	@Override
	public List<IndexPic> listIndexPicByNum(int num) {
		return indexPicDao.listIndexPicByNum(num);
	}

	@Override
	public Pager<IndexPic> findIndexPic() {
		return indexPicDao.findIndexPic();
	}

	@Override
	public List<String> listAllIndexPicName() {
		
		return indexPicDao.listAllIndexPicName();
	}

	@Override
	public Map<String, Integer> getMinAdnMaxPos() {
		return indexPicDao.getMinAdnMaxPos();
	}

	@Override
	public void updatePos(int id, int oldPos, int newPos) {
		indexPicDao.updatePos(id, oldPos, newPos);
	}

	@Override
	public void cleanNoUseIndexPic(List<String> pics) throws IOException {
		String rp = SystemContext.getRealPath();
		//首先删除临时文件夹
		File temp = new File(rp+"/resources/indexPic/temp");
		FileUtils.deleteDirectory(temp);
		//其次删除缩略图
		for(String f:pics) {
			new File(rp+"/resources/indexPic/thumbnail/"+f).delete();
			new File(rp+"/resources/indexPic/"+f).delete();
		}
	}

}
