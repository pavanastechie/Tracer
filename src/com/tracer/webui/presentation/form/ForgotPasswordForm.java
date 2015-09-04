/**
 * @author Jp
 *
 */
package com.tracer.webui.presentation.form;

public class ForgotPasswordForm extends BaseForm {

	private static final long serialVersionUID = -5332493899826362494L;
	private String userName = null;
	private String newPassword;
	private String confirmNewPassword;

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmNewPassword() {
		return confirmNewPassword;
	}

	public void setConfirmNewPassword(String confirmNewPassword) {
		this.confirmNewPassword = confirmNewPassword;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
