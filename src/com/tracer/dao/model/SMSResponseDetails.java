package com.tracer.dao.model;

import java.io.Serializable;

public class SMSResponseDetails implements Serializable {
  private static final long serialVersionUID = -7527679512934501062L;
  private String errorCode;
  private String errorMessage;
  private String jobId;
  private MessageData[] messageData;

  public String getErrorCode() {
    return errorCode;
  }
  public void setErrorCode(String errorCode) {
    this.errorCode = errorCode;
  }
  public String getErrorMessage() {
    return errorMessage;
  }
  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  public String getJobId() {
    return jobId;
  }
  public void setJobId(String jobId) {
    this.jobId = jobId;
  }
  public MessageData[] getMessageData() {
    return messageData;
  }
  public void setMessageData(MessageData[] messageData) {
    this.messageData = messageData;
  }

  public static class MessageData {
    private String number;
    private MessageParts[] messageParts;

    public String getNumber() {
      return number;
    }
    public void setNumber(String number) {
      this.number = number;
    }
    public MessageParts[] getMessageParts() {
      return messageParts;
    }
    public void setMessageParts(MessageParts[] messageParts) {
      this.messageParts = messageParts;
    }

    public static class MessageParts {
      private String msgId;
      private Integer partId;
      private String text;

      public String getMsgId() {
        return msgId;
      }
      public void setMsgId(String msgId) {
        this.msgId = msgId;
      }
      public Integer getPartId() {
        return partId;
      }
      public void setPartId(Integer partId) {
        this.partId = partId;
      }
      public String getText() {
        return text;
      }
      public void setText(String text) {
        this.text = text;
      }
    }// End of Class MessageParts
  } // End of Class MessageData
}
