package com.tracer.util;

/**
 * @author Jaya Prakash Manne
 */


import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tracer.dao.model.EmailDetails;


public class GmailEmailManager {
  protected transient final Log log = LogFactory.getLog(getClass());
  static String senderEmail = "pavandevu@gmail.com";
  static String senderPassword = "DevuPavan@48";
  public static Properties properties = null;
  public static GmailEmailManager instance = null;
   
  //========================================================================
    
  // Making this class as a single-ton class.
  private GmailEmailManager() {}
    
  //========================================================================
    
  public static synchronized GmailEmailManager getInstance() {
    
    if(instance == null || properties == null) {
      properties = new Properties();
      properties.put("mail.smtp.user", senderEmail);
      properties.put("mail.smtp.host", "smtp.gmail.com");
      properties.put("mail.smtp.port", "465");
      properties.put("mail.smtp.starttls.enable", "true");
      properties.put("mail.smtp.auth", "true");
      // properties.put("mail.smtp.debug", "true");
      properties.put("mail.smtp.socketFactory.port", "465");
      properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
      properties.put("mail.smtp.socketFactory.fallback", "false");
      instance = new GmailEmailManager();
    } 
    return instance;	
  }

  //========================================================================
  
  public boolean sendMail(EmailDetails emailDetails) {
    log.info("START of the method sendMail");
    Session session = null;
    MimeMessage msg = null;
    Address[] toAddresses = null;
    String[] recipients = null;
    Address toAddress = null;
    int toAddressCount = 0;
    boolean mailStatus = false;
    String subject = null;
    String messageContent = null; 
    
    try {
      if (emailDetails != null) {
        recipients = emailDetails.getRecipients();
        subject = emailDetails.getSubject();
        messageContent = emailDetails.getMessageContent();
      }
      if(recipients != null && recipients.length > 0 && StringUtil.isNotNull(subject) && StringUtil.isNotNull(messageContent)) {
        //SecurityManager security = System.getSecurityManager();
        session = Session.getInstance(properties, new GMailAuthenticator(senderEmail, senderPassword));
        //session.setDebug(true);
        msg = new MimeMessage(session);
        toAddresses = new Address[recipients.length];
          
        for (int i = 0; i < recipients.length; i++) {
          if(StringUtil.isNotNull(recipients[i])) {
            toAddress = new InternetAddress(recipients[i].trim());
            toAddresses[toAddressCount] = toAddress;
            toAddressCount++;
          }
        }
        msg.setRecipients(RecipientType.TO, toAddresses);

        // Setting the Subject and Content Type
        msg.setSubject(subject);
        //msg.setText(message);
        msg.setText(messageContent);
     

        try {
          Transport.send(msg);
          mailStatus = true;
        } catch (Exception e) {
          log.error("PROBLEM in transporting the mail in the method sendMail");
          log.error(e);
          e.printStackTrace();
        }  
      }
    } catch (Exception e) {
      e.printStackTrace();
      log.error("PROBLEM in transporting the mail in the method sendMail");
      e.printStackTrace();
    }
    log.info("END of the method sendMail");
    return mailStatus;
  }

  //========================================================================
  
 /* private class SMTPAuthenticator extends javax.mail.Authenticator {
    public PasswordAuthentication getPasswordAuthentication() {
      return new PasswordAuthentication(senderEmail, senderPassword);
    }
  }
*/
  //========================================================================
  
  private class GMailAuthenticator extends Authenticator {
     String user;
     String pw;
     public GMailAuthenticator (String username, String password) {
        super();
        this.user = username;
        this.pw = password;
     }
    public PasswordAuthentication getPasswordAuthentication() {
       return new PasswordAuthentication(user, pw);
    }
  }
  
  //========================================================================
  public static void main(String[] args) {
    EmailDetails emailDetails = new EmailDetails();
    String[] recipients = {"pavanastechie@gmail.com"};
    emailDetails.setRecipients(recipients);
    emailDetails.setSubject("Test Mail from Java Program without attachment");
    emailDetails.setMessageContent("Here goes the body");
    boolean mailStatus = GmailEmailManager.getInstance().sendMail(emailDetails);
    System.out.println(mailStatus);
  }
  
  //========================================================================
  
}
