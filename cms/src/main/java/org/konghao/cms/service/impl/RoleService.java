package org.konghao.cms.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.konghao.basic.dao.IRoleDao;
import org.konghao.basic.dao.IUserDao;
import org.konghao.basic.model.CmsException;
import org.konghao.basic.model.Role;
import org.konghao.basic.model.User;
import org.konghao.cms.service.BaseService;
import org.konghao.cms.service.IRoleService;
import org.springframework.stereotype.Service;

@Service("roleService")
public class RoleService  extends BaseService implements IRoleService {
	
	
	public IRoleDao getRoleDao() {
		return roleDao;
	}
	@Inject
	public void setRoleDao(IRoleDao roleDao) {
		this.roleDao = roleDao;
	}

	public IUserDao getUserDao() {
		return userDao;
	}
	@Inject
	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public void add(Role role) {
		roleDao.add(role);
	}

	@Override
	public void delete(int id) {
		List<User> us = userDao.listRoleUsers(id);
		if(us!=null&&us.size()>0) throw new CmsException("删除的角色对象中还有用户，不能删除");
		roleDao.delete(id);
	}

	@Override
	public void update(Role role) {
		roleDao.update(role);
	}

	@Override
	public Role load(int id) {
		return roleDao.load(id);
	}

	@Override
	public List<Role> listRole() {
		return roleDao.listRole();
	}

	@Override
	public void deleteRoleUsers(int rid) {
		roleDao.deleteRoleUsers(rid);
	}

}
