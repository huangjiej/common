/**
 * 
 */
package com.hummingbird.common.vo;

import com.hummingbird.common.exception.SignatureException;

/**
 * 校验结果类
 * @author huangjiej_2
 * 2014年11月11日 下午10:29:32
 */
public class ValidateResult {

	/**
	 * 校验结果
	 */
	boolean validate=true;
	/**
	 * 校验详细内容
	 */
	String validateMsg;
	
	/**
	 * 校验后输出内容
	 */
	Object validateObj;
	
	
	/**
	 * 校验结果
	 */
	public boolean isValidate() {
		return validate;
	}
	/**
	 * 校验结果
	 */
	public void setValidate(boolean validate) {
		this.validate = validate;
	}
	/**
	 * 校验详细内容
	 */
	public void setValidateMsg(String validateMsg) {
		this.validateMsg = validateMsg;
	}
	@Override
	public String toString() {
		return "ValidateResult [validate=" + validate + ", validateMsg="
				+ validateMsg + ", validateObj=" + validateObj + "]";
	}
	/**
	 * 校验后输出内容
	 */
	public Object getValidateObj() {
		return validateObj;
	}
	/**
	 * 校验后输出内容
	 */
	public void setValidateObj(Object validateObj) {
		this.validateObj = validateObj;
	}
	/**
	 * 校验详细内容
	 */
	public String getValidateMsg() {
		return validateMsg;
	}
	public void mergeException(Exception e) {
		this.validate=false;
		if (e instanceof NullPointerException) {
			this.validateMsg="发生空指针异常";
		}
		else{
			this.validateMsg= e.getMessage();
		}
		
		
		
	}
	
	
}
