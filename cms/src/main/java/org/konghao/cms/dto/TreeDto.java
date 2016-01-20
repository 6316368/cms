package org.konghao.cms.dto;
/**
 * @author   李海  Email:870721131@qq.com
 * @Date	 2015年6月22日		上午10:39:37
 * @类的作用:  用于演示tree的异步加载时使用
 */
public class TreeDto {
  private  int  id ;
  private String name;
  private int isParent;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public int getIsParent() {
	return isParent;
}
public void setIsParent(int isParent) {
	this.isParent = isParent;
}
public TreeDto(int id, String name, int isParent) {
	super();
	this.id = id;
	this.name = name;
	this.isParent = isParent;
}
@Override
public String toString() {
	return "TreeDto [id=" + id + ", name=" + name + ", isParent=" + isParent
			+ "]";
}
  
  
}

