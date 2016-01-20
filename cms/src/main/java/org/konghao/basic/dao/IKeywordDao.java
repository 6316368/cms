package org.konghao.basic.dao;

import java.util.List;

import org.konghao.basic.basedao.IBaseDao;
import org.konghao.basic.basemodel.Pager;
import org.konghao.basic.model.Keyword;

public interface IKeywordDao extends IBaseDao<Keyword>{
	/**
	 * 添加或者更新关键字
	 * 如果这个关键字不存在就进行添加，如果存在就进行更新，让引用次数加1
	 * @param name
	 */
	public void addOrUpdate(String name);
	/**
	 * 查找没有使用的关键字
	 * @return
	 */
	public Pager<Keyword> findNoUseKeyword();
	/**
	 * 清空没有使用的关键字
	 */
	public void clearNoUseKeyword();
	/**
	 * 查找正在被引用的关键字
	 * @return
	 */
	public List<Keyword> findUseKeyword();
	/**
	 * 根据某个条件从keyword表中查询关键字
	 * @param con
	 * @return
	 */
	public List<Keyword> listKeywordByCon(String con);
	/**
	 * 关键字的搜索补功能
	 * @param con
	 * @return
	 */
	public List<String> listKeywordStringByCon(String con);
}
