/**
 * 
 */
package org.konghao.basic.basedao;

import java.lang.reflect.ParameterizedType;
import java.math.BigInteger;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.konghao.basic.basemodel.Pager;
import org.konghao.basic.basemodel.SystemContext;

/**
 * @author Administrator
 *
 */
@SuppressWarnings("unchecked")
public class BaseDao<T> implements IBaseDao<T> {
	
	private SessionFactory sessionFactory;
	/**
	 * 创建一个Class的对象来获取泛型的class
	 */
	private Class<?> clz;
	
	public Class<?> getClz() {
		if(clz==null) {
			//获取泛型的Class对象
			clz = ((Class<?>)
					(((ParameterizedType)(this.getClass().getGenericSuperclass())).getActualTypeArguments()[0]));
		}
		return clz;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	@Inject
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public T add(T t) {
		getSession().save(t);
		return t;
	}

	@Override
	public void update(T t) {
		getSession().update(t);
	}

	
	@Override
	public void delete(int id) {
		getSession().delete(this.load(id));
	}


	@Override
	public T load(int id) {
		return (T)getSession().load(getClz(), id);
	}
	
	
	
	
	/**
	 * initSort:(排序方法)
	 * @param @param hql
	 * @param @return  设定文件
	 * @return String    DOM对象
	 * @throws 
	 */
	private String initSort(String hql) {
		String order = SystemContext.getOrder();
		String sort = SystemContext.getSort();
		if(sort!=null&&!"".equals(sort.trim())) {
			hql+=" order by "+sort;
			if(!"desc".equals(order)) hql+=" asc";
			else hql+=" desc";
		}
		System.out.println("hql==========="+hql);
		return hql;
	}
	
	/**
	 * setAliasParameter:(设定别名)
	 * @param @param query
	 * @param @param alias  设定文件
	 * @return void    DOM对象
	 * @throws 
	 */
	@SuppressWarnings("rawtypes")
	private void setAliasParameter(Query query,Map<String,Object> alias) {
		if(alias!=null) {
			Set<String> keys = alias.keySet();
			for(String key:keys) {
				Object val = alias.get(key);
				if(val instanceof Collection) {
					//查询条件是列表
					query.setParameterList(key, (Collection)val);
				} else {
					query.setParameter(key, val);
				}
			}
		}
	}
	
	/**
	 * setParameter:(查询方法的设定参数)
	 * @param @param query
	 * @param @param args  设定文件
	 * @return void    DOM对象
	 * @throws 
	 */
	private void setParameter(Query query,Object[] args) {
		if(args!=null&&args.length>0) {
			int index = 0;
			for(Object arg:args) {
				query.setParameter(index++, arg);
			}
		}
	}
	
	/**
	 * list:(查询一组不分页对象的集合)
	 * @param @param hql
	 * @param @param args
	 * @param @param alias
	 * @param @return  设定文件
	 * @return List<T>    DOM对象
	 * @throws 
	 */
	public List<T> list(String hql, Object[] args, Map<String, Object> alias) {
		hql = initSort(hql);
		Query query = getSession().createQuery(hql);
		setAliasParameter(query, alias);
		setParameter(query, args);
		return query.list();
	}
	public List<T> list(String hql, Object[] args) {
		return this.list(hql, args, null);
	}
	public List<T> list(String hql, Object arg) {
		return this.list(hql, new Object[]{arg});
	}
	public List<T> list(String hql) {
		return this.list(hql,null);
	}
	public List<T> listByAlias(String hql, Map<String, Object> alias) {
		return this.list(hql, null, alias);
	}
	/**end   add**/
	
	
   
	/**
	 * find:(分页的查询对象（基于别名参数的混合的查询方式）)
	 * @param @param hql    hql语句
	 * @param @param args   查询是传的参数
	 * @param @param alias  查询时使用的别名
	 * @param @return  设定文件
	 * @return Pager<T>    DOM对象
	 * @throws 
	 */
	public Pager<T> find(String hql, Object[] args, Map<String, Object> alias) {
		hql = initSort(hql);
		String cq = getCountHql(hql,true);
		Query cquery = getSession().createQuery(cq);
		Query query = getSession().createQuery(hql);
		//设置别名参数
		setAliasParameter(query, alias);
		setAliasParameter(cquery, alias);
		//设置参数
		setParameter(query, args);
		setParameter(cquery, args);
		Pager<T> pages = new Pager<T>();
		setPagers(query,pages);
		List<T> datas = query.list();
		pages.setDatas(datas);
		long total = (Long)cquery.uniqueResult();
		pages.setTotal(total);
		return pages;
	}
	public Pager<T> find(String hql, Object[] args) {
		return this.find(hql, args, null);
	}
	public Pager<T> find(String hql, Object arg) {
		return this.find(hql, new Object[]{arg});
	}
	public Pager<T> find(String hql) {
		return this.find(hql,null);
	}
	public Pager<T> findByAlias(String hql, Map<String, Object> alias) {
		return this.find(hql,null, alias);
	}
	/**end  find一组方法结束**/
	
	
	/**
	 * setPagers:(分页方法参数的设定)
	 * @param @param query
	 * @param @param pages  设定文件
	 * @return void    DOM对象
	 * @throws 
	 */
	@SuppressWarnings("rawtypes")
	private void setPagers(Query query,Pager pages) {
		Integer pageSize = SystemContext.getPageSize();
		Integer pageOffset = SystemContext.getPageOffset();
		if(pageOffset==null||pageOffset<0) pageOffset = 0;
		if(pageSize==null||pageSize<0) pageSize = 15;
		pages.setOffset(pageOffset);
		pages.setSize(pageSize);
		query.setFirstResult(pageOffset).setMaxResults(pageSize);
	}
	
	/**
	 * getCountHql:(分页时得到总记录数)
	 * @param @param hql
	 * @param @param isHql
	 * @param @return  设定文件
	 * @return String    DOM对象
	 * @throws 
	 */
	private String getCountHql(String hql,boolean isHql) {
		String e = hql.substring(hql.indexOf("from"));
		String c = "select count(*) "+e;
		if(isHql)
			c = c.replaceAll("fetch", "");
		return c;
	}
	
	
	/**
	 * queryObject:(通过hql查询对象)
	 * @param @param hql    hql语句
	 * @param @param args   参数列表
	 * @param @param alias  别名
	 * @param @return  设定文件
	 * @return Object    DOM对象
	 * @throws 
	 */
	public Object queryObject(String hql, Object[] args,
			Map<String, Object> alias) {
		Query query = getSession().createQuery(hql);
		setAliasParameter(query, alias);
		setParameter(query, args);
		return query.uniqueResult();
	}
	public Object queryObject(String hql, Object[] args) {
		return this.queryObject(hql, args,null);
	}
	public Object queryObject(String hql, Object arg) {
		return this.queryObject(hql, new Object[]{arg});
	}
	public Object queryObject(String hql) {
		return this.queryObject(hql,null);
	}
	public Object queryObjectByAlias(String hql, Map<String, Object> alias) {
		return this.queryObject(hql,null,alias);
	}
	/**end   add  by lh queryObject的一组方法的结束**/

	
	
	
	/**
	 * updateByHql:(根据hql语句进行更新操作)
	 * @param @param hql   hql语句
	 * @param @param args  参数
	 * @return void    DOM对象
	 * @throws 
	 */
	public void updateByHql(String hql, Object[] args) {
		Query query = getSession().createQuery(hql);
		setParameter(query, args);
		query.executeUpdate();
	}
	public void updateByHql(String hql, Object arg) {
		this.updateByHql(hql,new Object[]{arg});
	}
	public void updateByHql(String hql) {
		this.updateByHql(hql,null);
	}
	/** end   add  by lh updateByHql的一组方法结束*/
	
	
	
	/**
	 * listBySql:(根据sql查询对象但是不进行分页，不包含关联对象)
	 * @param @param sql  hql语句
	 * @param @param args  参数
	 * @param @param alias 别名
	 * @param @param clz   查询的实体类
	 * @param @param hasEntity  该对象是否是hibetnate管理的一个实体，如果不是的话,
	 *                          就需要使用setResultTransformer来进行查询
	 * @param @return  设定文件
	 * @return List<N> 一组对象
	 * @throws 
	 */
	public <N extends Object>List<N> listBySql(String sql, Object[] args,
			Map<String, Object> alias, Class<?> clz, boolean hasEntity) {
		sql = initSort(sql);
		SQLQuery sq = getSession().createSQLQuery(sql);
		setAliasParameter(sq, alias);
		setParameter(sq, args);
		if(hasEntity) {
			sq.addEntity(clz);
		} else 
			sq.setResultTransformer(Transformers.aliasToBean(clz));
		return sq.list();
	}
	public <N extends Object>List<N> listBySql(String sql, Object[] args, Class<?> clz,
			boolean hasEntity) {
		return this.listBySql(sql, args, null, clz, hasEntity);
	}
	public <N extends Object>List<N> listBySql(String sql, Object arg, Class<?> clz,
			boolean hasEntity) {
		return this.listBySql(sql, new Object[]{arg}, clz, hasEntity);
	}
	public <N extends Object>List<N> listBySql(String sql, Class<?> clz, boolean hasEntity) {
		return this.listBySql(sql, null, clz, hasEntity);
	}
	public <N extends Object>List<N> listByAliasSql(String sql, Map<String, Object> alias,
			Class<?> clz, boolean hasEntity) {
		return this.listBySql(sql, null, alias, clz, hasEntity);
	}
	/** start  add  by lh listBySql的一组方法的结束*/
	
	
	
	
	/**
	 * listBySql:(根据sql查询对象但是进行分页，不包含关联对象)
	 * @param @param sql  hql语句
	 * @param @param args  参数
	 * @param @param alias 别名
	 * @param @param clz   查询的实体类
	 * @param @param hasEntity  该对象是否是hibetnate管理的一个实体，如果不是的话,
	 *                          就需要使用setResultTransformer来进行查询
	 * @param @return  设定文件
	 * @return List<N> 一组对象
	 * @throws 
	 */
	public <N extends Object>Pager<N> findBySql(String sql, Object[] args,
			Map<String, Object> alias, Class<?> clz, boolean hasEntity) {
		sql = initSort(sql);
		String cq = getCountHql(sql,false);
		SQLQuery sq = getSession().createSQLQuery(sql);
		SQLQuery cquery = getSession().createSQLQuery(cq);
		setAliasParameter(sq, alias);
		setAliasParameter(cquery, alias);
		setParameter(sq, args);
		setParameter(cquery, args);
		Pager<N> pages = new Pager<N>();
		setPagers(sq, pages);
		if(hasEntity) {
			sq.addEntity(clz);
		} else {
			sq.setResultTransformer(Transformers.aliasToBean(clz));
		}
		List<N> datas = sq.list();
		pages.setDatas(datas);
		long total = ((BigInteger)cquery.uniqueResult()).longValue();
		pages.setTotal(total);
		return pages;
	}
	public <N extends Object>Pager<N> findBySql(String sql, Object[] args, Class<?> clz,
			boolean hasEntity) {
		return this.findBySql(sql, args, null, clz, hasEntity);
	}
	public <N extends Object>Pager<N> findBySql(String sql, Object arg, Class<?> clz,
			boolean hasEntity) {
		return this.findBySql(sql, new Object[]{arg}, clz, hasEntity);
	}
	public <N extends Object>Pager<N> findBySql(String sql, Class<?> clz, boolean hasEntity) {
		return this.findBySql(sql, null, clz, hasEntity);
	}
	public <N extends Object>Pager<N> findByAliasSql(String sql, Map<String, Object> alias,
			Class<?> clz, boolean hasEntity) {
		return this.findBySql(sql, null, alias, clz, hasEntity);
	}
	
	/** start  add  by lh findBySql的一组方法的结束*/
}
