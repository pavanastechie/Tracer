/**
 * @author Jp
 *
 */
package com.tracer.webui.presentation.form;

public class LoginForm extends BaseForm {
	private static final long serialVersionUID = -7506781428686465334L;
	private String userName = null;
	private String password = null;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
