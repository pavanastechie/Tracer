/**
 * @author Bhargava
 *
 */
package com.tracer.webui.presentation.form;

import org.apache.struts.upload.FormFile;

public class BeatPlanBulkUploadForm extends BaseForm {

  private static final long serialVersionUID = 1L;
  private FormFile file;

  public FormFile getFile() {
    return file;
  }

  public void setFile(FormFile file) {
    this.file = file;
  }

}
