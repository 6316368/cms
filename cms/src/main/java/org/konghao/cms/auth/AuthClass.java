package org.konghao.cms.auth;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * ClassName:AuthClass Function: 
 * 设置权限的类，只要在controller中增加了这个方法，都需要加入权限的控制
 * @author lh
 * @Date 2015 2015年7月17日 下午9:19:40
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthClass {
	/**
	 * function(如果value位admin的话，表示这个类只能是超级管理员访问
	 * 如果value为login的话表示这个类中的方法某些可能是相应的角色可以访问)
	 * @param @return
	 * @return String
	 * @throws
	 * @author lh
	 * @Date 2015年7月17日
	 */
	public String value() default "admin";

}
