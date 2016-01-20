package org.konghao.basic.model;

import java.math.BigInteger;

/**
 * 系统栏目树对象
 * @author Administrator
 *
 */
public class ChannelTree {
	
	private Integer id;
	private String name;
	private Integer pId;
	private BigInteger  count;
	private boolean  open;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	

	public ChannelTree() {
	}

	public Integer getpId() {
		return pId;
	}

	public void setpId(Integer pId) {
		this.pId = pId;
	}


	

	

	public BigInteger getCount() {
		return count;
	}

	public void setCount(BigInteger count) {
		this.count = count;
	}

	public ChannelTree(Integer id, String name, Integer pId, boolean open) {
		super();
		this.id = id;
		this.name = name;
		this.pId = pId;
		this.open = open;
	}

	public ChannelTree(Integer id, String name, Integer pId, BigInteger count,
			boolean open) {
		super();
		this.id = id;
		this.name = name;
		this.pId = pId;
		this.count = count;
		this.open = open;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	@Override
	public String toString() {
		return "ChannelTree [id=" + id + ", name=" + name + ", pId=" + pId
				+ ", count=" + count + ", open=" + open + "]";
	}


}
