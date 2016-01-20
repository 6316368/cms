package org.konghao.basic.dao;

import java.util.List;

import org.konghao.basic.basedao.IBaseDao;
import org.konghao.basic.model.Role;

public interface IRoleDao extends IBaseDao<Role> {
	public List<Role> listRole();
	public void deleteRoleUsers(int rid);
}
