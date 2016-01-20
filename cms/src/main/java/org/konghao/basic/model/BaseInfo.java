package org.konghao.basic.model;

import org.hibernate.validator.constraints.NotEmpty;

public class BaseInfo {
	/**
	 * @author lihai
	 * @category 网站名称
	 */
	private String name;
	/**
	 * @author lihai
	 * @category 网站地址
	 */
	private String address;
	/**
	 * @author lihai
	 * @category 邮政编码
	 */
	private String zipCode;
	/**
	 * @author lihai
	 * @category 备案号码
	 */
	private String recordCode;
	/**
	 * @author lihai
	 * @category 联系电话
	 */
	private String phone;
	/**
	 * @author lihai
	 * @category 联系邮箱
	 */
	private String email;
	/**
	 * @author lihai
	 * @category 首页名称
	 */
	private String domainName;
	/**
	 * @author lihai
	 * @category 首页图片的宽度
	 */
	private int indexPicWidth;
	/**
	 * @author lihai
	 * @category 首页图片的高度
	 */
	private int indexPicHeight;
	/**
	 * @author lihai
	 * @category 首页图片数量
	 */
	private int indexPicNumber;
	
	
	
	public int getIndexPicNumber() {
		return indexPicNumber;
	}
	public void setIndexPicNumber(int indexPicNumber) {
		this.indexPicNumber = indexPicNumber;
	}
	public String getDomainName() {
		return domainName;
	}
	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}
	
	@NotEmpty(message="网站名称不能为空")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getRecordCode() {
		return recordCode;
	}
	public void setRecordCode(String recordCode) {
		this.recordCode = recordCode;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getIndexPicWidth() {
		return indexPicWidth;
	}
	public void setIndexPicWidth(int indexPicWidth) {
		this.indexPicWidth = indexPicWidth;
	}
	public int getIndexPicHeight() {
		return indexPicHeight;
	}
	public void setIndexPicHeight(int indexPicHeight) {
		this.indexPicHeight = indexPicHeight;
	}
	@Override
	public String toString() {
		return "BaseInfo [name=" + name + ", address=" + address + ", zipCode="
				+ zipCode + ", recordCode=" + recordCode + ", phone=" + phone
				+ ", email=" + email + ", indexPicWidth=" + indexPicWidth
				+ ", indexPicHeight=" + indexPicHeight + "]";
	}
}
