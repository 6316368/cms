package org.konghao.cms.dto;
/**
 * @author    李海  Email:870721131@qq.com
 * @Date	  2015年6月27日		上午10:22:27
 * @类的作用:    此类的作用是用于ajax返回请求时返回的信息,是失败还是成功
 */
public class AjaxObj {
	/**
	 * 0表示失败
	 * 1表示成功
	 */
	private int result;
	/**
	 * 提示信息
	 */
	private String msg;
	/**
	 * 附加对象，用来存储一些特定的返回信息
	 */
	private Object obj;
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}
	
	public AjaxObj() {
	}
	public AjaxObj(int result, String msg, Object obj) {
		super();
		this.result = result;
		this.msg = msg;
		this.obj = obj;
	}
	public AjaxObj(int result, String msg) {
		super();
		this.result = result;
		this.msg = msg;
	}
	public AjaxObj(int result) {
		super();
		this.result = result;
	}
}

