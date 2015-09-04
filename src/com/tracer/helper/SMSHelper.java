/**
 * @author Jp
 *
 */
package com.tracer.helper;

public class SMSHelper {

  private static SMSHelper instance = null;

  // ========================================================================

  private SMSHelper() {
  }

  // ========================================================================

  public static synchronized SMSHelper getInstance() {

    if (instance == null) {
      instance = new SMSHelper();
    }
    return instance;
  }

  // ========================================================================

  public void senSMS(String message, int[] mobileNumbers) {
  }

  // ========================================================================
}