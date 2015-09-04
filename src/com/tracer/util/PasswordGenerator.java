/**
 * @author Jp
 *
 */
package com.tracer.util;

public class PasswordGenerator {

  private static PasswordGenerator instance = null;

  // ========================================================================

  private PasswordGenerator() {
  }

  // ========================================================================

  public static synchronized PasswordGenerator getInstance() {

    if (instance == null) {
      instance = new PasswordGenerator();
    }
    return instance;
  }

  // ========================================================================

  public String getGeneratedPassword() {
    String dCase = "abcdefghijklmnopqrstuvwxyz";
    String uCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    //String sChar = "!@#$%&*";
    String intChar = "0123456789";
    char[] pwd = new char[8];
    char c = 'A';
    for (int i = 0; i < 8; i++) {
      switch (i) {
      case 0:
        c = dCase.charAt((int) (Math.random() * 26));
        break;
      case 1:
        c = uCase.charAt((int) (Math.random() * 26));
        break;
      case 2:
        //c = sChar.charAt((int) (Math.random() * 7));
        c = dCase.charAt((int) (Math.random() * 26));
        break;
      case 3:
        c = intChar.charAt((int) (Math.random() * 10));
      case 4:
        c = dCase.charAt((int) (Math.random() * 26));
        break;
      case 5:
        c = uCase.charAt((int) (Math.random() * 26));
        break;
      case 6:
        //c = sChar.charAt((int) (Math.random() * 7));
        c = dCase.charAt((int) (Math.random() * 26));
        break;
      case 7:
        c = intChar.charAt((int) (Math.random() * 10));
        break;
      }
      pwd[i] = (char) c;
    }
    return new String(pwd);
  }

  // ========================================================================
}