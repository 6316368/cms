package org.konghao.cms.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.konghao.basic.basemodel.Pager;
import org.konghao.basic.model.Keyword;
import org.konghao.cms.service.BaseService;
import org.konghao.cms.service.IKeyService;
import org.springframework.stereotype.Service;

@Service("keyservice")
public class KeyService extends BaseService implements IKeyService {

	@Override
	public void addOrUpdate(String name) {
         keywordDao.addOrUpdate(name);
	}

	@Override
	public List<Keyword> getKeywordByTimes(int num) {
		List<Keyword> findUseKeyword = keywordDao.findUseKeyword();
		List<Keyword>  list=new ArrayList<Keyword>();
		for (Keyword keyword : findUseKeyword) {
			if(keyword.getTimes()>=num) list.add(keyword);
			else break;
		}
		return list;
	}

	@Override
	public List<Keyword> getMaxTimesKeyword(int num) {
		List<Keyword> ks = keywordDao.findUseKeyword();
		List<Keyword> kks = new ArrayList<Keyword>();
		if(ks.size()<=num) return ks;
		for(int i=0;i<=num;i++) {
			kks.add(ks.get(i));
		}
		return kks;
	}

	@Override
	public Pager<Keyword> findNoUseKeyword() {
		
		return keywordDao.findNoUseKeyword();
	}

	@Override
	public void clearNoUseKeyword() {
		keywordDao.clearNoUseKeyword();

	}

	@Override
	public List<Keyword> findUseKeyword() {
		
		return keywordDao.findUseKeyword();
	}

	@Override
	public List<Keyword> listKeywordByCon(String con) {
		
		return keywordDao.listKeywordByCon(con);
	}

	@Override
	public List<String> listKeywordStringByCon(String con) {
		
		return keywordDao.listKeywordStringByCon(con);
	}

}
