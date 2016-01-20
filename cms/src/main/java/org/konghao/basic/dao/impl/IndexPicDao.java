package org.konghao.basic.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.konghao.basic.basedao.BaseDao;
import org.konghao.basic.basemodel.Pager;
import org.konghao.basic.dao.IIndexPicDao;
import org.konghao.basic.model.IndexPic;
import org.springframework.stereotype.Repository;

@Repository("indexPicDao")
public class IndexPicDao extends BaseDao<IndexPic> implements IIndexPicDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<IndexPic> listIndexPicByNum(int num) {
		String hql = "from IndexPic where status=1 order by pos";
		return this.getSession().createQuery(hql)
					.setFirstResult(0).setMaxResults(num).list();
	}
	
	@Override
	public IndexPic add(IndexPic ip) {
		//让其他的元素的序号先增加1
		String hql = "update IndexPic ip set ip.pos=ip.pos+1 where pos>=?";
		this.getSession().createQuery(hql).setParameter(0, 1).executeUpdate();
		ip.setPos(1);
		super.add(ip);
		return ip;
	}
	
	public void delete(int id) {
		IndexPic ip = this.load(id);
		int pos = ip.getPos();
		String hql = "update IndexPic ip set ip.pos=ip.pos-1 where pos>?";
		this.getSession().createQuery(hql).setParameter(0, pos).executeUpdate();
		this.getSession().delete(ip);
	}

	@Override
	public Pager<IndexPic> findIndexPic() {
		return this.find("from IndexPic order by pos");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> listAllIndexPicName() {
		String hql = "select ip.newName from IndexPic ip";
		return this.getSession().createQuery(hql).list();
	}

	@Override
	public Map<String, Integer> getMinAdnMaxPos() {
		String hql = "select max(pos),min(pos) from IndexPic";
		Object[] obj = (Object[])this.getSession().createQuery(hql).uniqueResult();
		Map<String,Integer> mm = new HashMap<String,Integer>();
		mm.put("max", (Integer)obj[0]);
		mm.put("min", (Integer)obj[1]);
		return mm;
	}
	/**function(更新首页图片哦位置信息,)
	 * @param  @param id      要更新的首页图片的id
	 * @param  @param oldpos  要更新的首页图片的原始位置      
	 * @return void           要把首页图片更新的新位置   
	 * @throws                
	 * @author lh 
	 * @Date   2015年9月19日
	 * 更新位置,如果原位置小于新位置,让所以大于原始位置小于等于新位置的元素全部减1之后更新该对象的位置
	 * 更新位置,如果原位置大于新位置,让所以小于原始位置大于等于新位置的元素全部加1之后更新该对象的位置
	*/
	@Override
	public void updatePos(int id, int oldPos, int newPos) {
		IndexPic ip = this.load(id);
		if(oldPos==newPos) {
			return;
		}
		String hql = "";
		if(oldPos<newPos) {
			hql = "update IndexPic set pos=pos-1 where pos>? and pos<=?";
		} else {
			hql = "update IndexPic set pos=pos+1 where pos<? and pos>=?";
		}
		this.getSession().createQuery(hql)
			.setParameter(0, oldPos)
			.setParameter(1, newPos).executeUpdate();
		ip.setPos(newPos);
		this.update(ip);
	}


}
