/**
 * @author Jp
 *
 */
package com.tracer.webui.presentation.action;

import static com.tracer.common.Constants.ERROR_MESSAGE;
import static com.tracer.common.Constants.FORGOT_PASSWORD_PAGE;
import static com.tracer.common.Constants.EMAIL_SENT_SUCCESS;
import static com.tracer.common.Constants.LOGIN_PAGE;
import static com.tracer.common.Constants.LINK_INVALID_PAGE;
import static com.tracer.common.Constants.RESET_PASSWORD_PAGE;
import static com.tracer.common.Constants.SUCCESS;
import static com.tracer.common.Constants.USER_MANAGER;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.tracer.dao.model.EmailDetails;
import com.tracer.dao.model.ForgotPasswordDetails;
import com.tracer.dao.model.UserDetails;
import com.tracer.dao.model.UserPasswordHistoryDetails;
import com.tracer.service.UserManager;
import com.tracer.util.GmailEmailManager;
import com.tracer.util.PasswordEncy;
import com.tracer.util.StringUtil;
import com.tracer.util.VerifyRecaptcha;
import com.tracer.webui.presentation.form.ForgotPasswordForm;

public class ForgotPasswordAction extends BaseDispatchAction {

	protected transient final Log log = LogFactory.getLog(getClass());

	// ========================================================================

	public ActionForward showForgotPasswordPage(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("START of the ActionForward showForgotPasswordPage");
		try {
			saveToken(request);
		} catch (Exception e) {
			log.error("Problem in the ActionForward showForgotPasswordPage");
			log.error(e);
		}
		log.info("END of the ActionForward showForgotPasswordPage");
		return mapping.findForward(FORGOT_PASSWORD_PAGE);
	}

	// ========================================================================

	public ActionForward isExists(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ForgotPasswordForm forgotPassForm = null;
		ActionMessages actionMessages = new ActionMessages();
		String userName = null;
		ServletConfig config = getServlet();
		String pageForward = "";
		UserManager userManager = null;
		log.info("START of the ActionForward isExists");

		try {
			forgotPassForm = (ForgotPasswordForm) form;
			userName = forgotPassForm.getUserName();
			userManager = (UserManager) getBean(USER_MANAGER);
			if (StringUtil.isNotNull(userName)) {
				UserDetails user = userManager.getDetailsWithUserName(userName);
				if (user != null) {
					ServletContext context = config.getServletContext();
					ForgotPasswordDetails fd = new ForgotPasswordDetails();
					fd.setUserId(user.getUserId());
					fd.setUserName(userName);
					Date currentTime = new Date();
					fd.setCreatedDate(currentTime);
					long randomNuber = (long) Math.floor(Math.random() * 9000000000L) + 1000000000L;
					String hashCode = randomNuber + "";
					log.info("Here is hashcode value ----- " + hashCode);
					request.getSession().setAttribute("hashCode", hashCode);
					request.getSession().setAttribute("emailAddress",
							user.getPrimaryEmail());
					EmailDetails emailDetails = new EmailDetails();
					String emaillAdress = user.getPrimaryEmail();
					String[] recipients = { emaillAdress };
					emailDetails.setRecipients(recipients);
					emailDetails.setSubject("This is your  TraceR Reset Password Link");
					String linkUrl = "http://localhost:8080/Tracer/forgotpassword.do?method=forgotPasswordLinkVerification&guid="+ hashCode;
					emailDetails.setMessageContent(linkUrl);
					boolean mailStatus = GmailEmailManager.getInstance().sendMail(emailDetails);
					log.info("Mail Send success? " + mailStatus);
					if (!mailStatus) {
						ActionMessages actionMessages1 = new ActionMessages();
						actionMessages1.add("email.sending.failure", new ActionMessage("email.sending.failure"));
						saveMessages(request, actionMessages1);
						pageForward = FORGOT_PASSWORD_PAGE;
					} else {
						context.setAttribute(hashCode, fd);
						pageForward = EMAIL_SENT_SUCCESS;
					}

				} else {
					actionMessages.add("error.invalid.username", new ActionMessage("error.invalid.username"));
					saveMessages(request, actionMessages);
					resetToken(request);
					saveToken(request);
					pageForward = FORGOT_PASSWORD_PAGE;
				}

			}

		} catch (Exception e) {
			log.error("Problem in the ActionForward isExists");
			log.error(e);
		}
		log.info("END of the ActionForward isExists");
		return mapping.findForward(pageForward);
	}

	// ========================================================================
	public ActionForward resentUserVerificationLink(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("START of the ActionForward resentUserVerificationLink");
		try {
			String hashCode = (String) request.getSession().getAttribute("hashCode");
			String emailAdd = (String) request.getSession().getAttribute("emailAddress");
			log.info("Email Adress is " + emailAdd);
			log.info("hash code is " + hashCode);
			EmailDetails emailDetails = new EmailDetails();
			String emaillAdress = emailAdd;
			String[] recipients = { emaillAdress };
			emailDetails.setRecipients(recipients);
			emailDetails.setSubject("This is your  TraceR Reset Password Link");
			String linkUrl = "http://localhost:8080/Tracer/forgotpassword.do?method=forgotPasswordLinkVerification&guid="+ hashCode;
			emailDetails.setMessageContent(linkUrl);
			boolean mailStatus = GmailEmailManager.getInstance().sendMail(
					emailDetails);
			if (mailStatus) {
			    request.getSession().removeAttribute("hashCode");
				request.removeAttribute("emailAddress");
				ActionMessages actionMessages = new ActionMessages();
				actionMessages.add("email.sending.success", new ActionMessage("email.sending.success"));
				saveMessages(request, actionMessages);
			} else {
				ActionMessages actionMessages = new ActionMessages();
				actionMessages.add("email.sending.failure", new ActionMessage("email.sending.failure"));
				saveMessages(request, actionMessages);
			}
		} catch (Exception e) {
			log.error("Problem in the ActionForward resentUserVerificationLink");
			log.error(e);
		}
		log.info("END of the ActionForward resentUserVerificationLink");
		return mapping.findForward(EMAIL_SENT_SUCCESS);
	}

	// ========================================================================

	public ActionForward forgotPasswordLinkVerification(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ServletConfig config = getServlet();
		String pageForward = "";
		try {
			ServletContext context = config.getServletContext();
			String accessToken = request.getParameter("guid");
			ForgotPasswordDetails userData = (ForgotPasswordDetails) context.getAttribute(accessToken);
			request.getSession().setAttribute("USER_DETAILS", accessToken);
			if (userData != null) {
				log.info("Here is values--------- " + userData.getCreatedDate());
				Date createdDate = userData.getCreatedDate();
				Date currentDate = new Date();
				log.info(createdDate.getTime()+ " create -------------- current "+ currentDate.getTime());
				long timeDiff = currentDate.getTime() - createdDate.getTime();
				long hours = timeDiff / (1000 * 60 * 60);
				log.info(hours);
				if (hours > 24) {
					context.removeAttribute(accessToken);
					pageForward = LINK_INVALID_PAGE;

				} else {
					pageForward = RESET_PASSWORD_PAGE;
				}
			} else {
				pageForward = LINK_INVALID_PAGE;

			}
		} catch (Exception e) {
			log.error("Problem in the ActionForward forgotPasswordLinkVerification");
			log.error(e);
		}
		return mapping.findForward(pageForward);
	}

	// ========================================================================

	public ActionForward resetPassword(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ForgotPasswordForm forgotPassForm = null;
		ActionMessages actionMessages = new ActionMessages();
		long requestedUserId = 1L;
		UserDetails userDetails = null;
		UserManager userManager = null;
		String pageForward = "";
		ServletConfig config = getServlet();
		ServletContext context = config.getServletContext();
		PasswordEncy passwordEncy = PasswordEncy.getInstance();
		log.info("START of the ActionForward resetPassword");

		try {
			forgotPassForm = (ForgotPasswordForm) form;
			userManager = (UserManager) getBean(USER_MANAGER);
			userDetails = new UserDetails();
			HttpSession session = null;
			log.info("Entered Password-------- "+ forgotPassForm.getNewPassword());
			log.info("END of the ActionForward resetPassword");

			String hashCode = (String) request.getSession().getAttribute("USER_DETAILS");
			ForgotPasswordDetails fd = (ForgotPasswordDetails) context.getAttribute(hashCode);
			requestedUserId = fd.getUserId();
			userDetails = userManager.getUserDetails(requestedUserId);
			userDetails.setUserId(requestedUserId);
			userDetails.setPassword(passwordEncy.encrypt(forgotPassForm.getNewPassword()));
			log.info("UserId is..." + requestedUserId);
			String gRecaptchaResponse = request
					.getParameter("g-recaptcha-response");
			log.info("Response Data..." + gRecaptchaResponse);
			boolean verify = VerifyRecaptcha.verify(gRecaptchaResponse);
			log.info("capctha verification...." + verify);
			List<UserPasswordHistoryDetails> resultSet = userManager.getFivePassword(userDetails);
			log.info("Sizer of the List is: " + resultSet.size());
			ArrayList<String> passwordList = new ArrayList<String>();
			if (resultSet != null) {
				for (int i = 0; i < resultSet.size(); i++) {
					if (i <= 4) {
						passwordList.add(resultSet.get(i).getPassword());
					}
				}
			}

			for (String data : passwordList) {
				log.info("Password is: " + passwordEncy.decrypt(data));

			}
			boolean passwordCheck = passwordList.contains(passwordEncy.encrypt(forgotPassForm.getNewPassword()));
			log.info("Contains password "
					+ passwordList.contains(passwordEncy.encrypt(forgotPassForm.getNewPassword())));
			if (verify) {
				if (!passwordCheck) {
					String result = userManager.updatePassword(userDetails);
					if (result != null && result.equalsIgnoreCase(SUCCESS)) {
						UserPasswordHistoryDetails userPass = new UserPasswordHistoryDetails();
						userPass.setUserDetails(userDetails);
						userPass.setPassword(passwordEncy.encrypt(forgotPassForm.getNewPassword()));
						userManager.storePassword(userPass);
						request.getSession().removeAttribute("USER_DETAILS");
						actionMessages.add("password.reset.success",
								new ActionMessage("password.reset.success"));
						saveMessages(request, actionMessages);
						session = request.getSession(false);
						if (session != null) {
							session.invalidate();
						}
						pageForward = LOGIN_PAGE;
					} else if (result.equalsIgnoreCase(ERROR_MESSAGE)) {
						actionMessages.add("password.update.failure", new ActionMessage("password.update.failure"));
						saveMessages(request, actionMessages);
					}
				} else {
					actionMessages.add("password.repeat.failure",
							new ActionMessage("password.repeat.failure"));
					saveMessages(request, actionMessages);
					pageForward = RESET_PASSWORD_PAGE;
				}
			} else {
				actionMessages.add("invalid.capctha", new ActionMessage("invalid.capctha"));
				saveMessages(request, actionMessages);
				pageForward = RESET_PASSWORD_PAGE;
			}

		} catch (Exception e) {
			log.error("Problem in the ActionForward resetPassword");
			log.error(e);
		}

		return mapping.findForward(pageForward);
	}

}
