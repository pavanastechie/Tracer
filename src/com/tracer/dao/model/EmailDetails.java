package com.tracer.dao.model;

/**
 * @author Jaya Prakash Manne
 */



public class EmailDetails implements java.io.Serializable {
  private static final long serialVersionUID = 2118267537842473514L;
  private String[] recipients;
  private String subject;
  private String messageContent;
 
  
  public EmailDetails () { }
  
  public EmailDetails(String[] recipients, String subject, String messageContent) {
    this.recipients = recipients;
    this.subject = subject;
    this.messageContent = messageContent;
    
  }

  public String[] getRecipients() {
    return recipients;
  }
  public void setRecipients(String[] recipients) {
    this.recipients = recipients;
  }
  public String getSubject() {
    return subject;
  }
  public void setSubject(String subject) {
    this.subject = subject;
  }
  public String getMessageContent() {
    return messageContent;
  }
  public void setMessageContent(String messageContent) {
    this.messageContent = messageContent;
  }
}
