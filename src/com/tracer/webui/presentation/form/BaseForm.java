/**
 * @author Jp
 *
 */
package com.tracer.webui.presentation.form;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import org.apache.struts.validator.ValidatorForm;

public class BaseForm extends ValidatorForm implements Serializable {
	private static final long serialVersionUID = -7591039565438191098L;
	protected transient final Log log = LogFactory.getLog(getClass());
	private String method = null;

	//========================================================================

	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	//========================================================================
	
	public boolean equals(Object o) {
		return EqualsBuilder.reflectionEquals(this, o);
	}

	//========================================================================
	
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	//========================================================================
	
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		// Identify the request parameter containing the method name
		String parameter = mapping.getParameter();

		if (parameter != null) {
			// Identify the method name to be dispatched to.
			String method = request.getParameter(parameter);
			MessageResources resources = (MessageResources) request.getAttribute(Globals.MESSAGES_KEY);

			// Identify the localized message for the cancel button
			String cancel = resources.getMessage("core.commons.buttons.cancel");
			String delete = resources.getMessage("core.commons.buttons.delete");

			// if message resource matches the cancel button then no
			// need to validate
			if ((method != null)&& (method.equalsIgnoreCase(cancel) || method.equalsIgnoreCase(delete))) {
				return null;
			}
		}

		// perform regular validation
		return super.validate(mapping, request);
	}

	//========================================================================
	
	public String getMethod() {
		return method;
	}

	//========================================================================
	
	public void setMethod(String method) {
		this.method = method;
	}

	//========================================================================
}
