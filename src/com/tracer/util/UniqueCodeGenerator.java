/**
 * @author Jp
 *
 */
package com.tracer.util;

import java.util.Random;

public class UniqueCodeGenerator {

  private static UniqueCodeGenerator instance = null;
  private static final String dCase = "abcdefghijklmnopqrstuvwxyz";
  private static final String uCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
  private static final String sChar = "!@#$%&*";
  private static final String intChar = "0123456789";
  private static final String[] codes = {"TSE", "TSM", "ASM", "ZBM", "RBM", "CBM", "CIR", "REG", "ZON", "HUB", "DST", "BPL"};
  
  
  //========================================================================
  
  private UniqueCodeGenerator() {}
  
  //========================================================================
  
  public static synchronized UniqueCodeGenerator getInstance() {

    if (instance == null) {
      instance = new UniqueCodeGenerator();
    }
    return instance;
  }
  
  //========================================================================
  
  public String getUniqueCode() {
    Random r = new Random();
    String uCode = "";
    
    try {
      while (uCode.length () != 12) {
        int rPick = r.nextInt(4);
        if (rPick == 0){
          int spot = r.nextInt(25);
          uCode += dCase.charAt(spot);
        } else if (rPick == 1) {
          int spot = r.nextInt (25);
          uCode += uCase.charAt(spot);
        } else if (rPick == 2) {
          int spot = r.nextInt (9);
          uCode += intChar.charAt(spot);
        } else if (rPick == 3){
          int spot = r.nextInt (7);
          uCode += sChar.charAt (spot);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return uCode;
  }
  
  //========================================================================
  
  public String getUniqueCodeWithoutSpecialChars() {
    Random r = new Random();
    String uCode = "";
    
    try {
      while (uCode.length () != 12) {
        int rPick = r.nextInt(4);
        if (rPick == 0){
          int spot = r.nextInt(25);
          uCode += dCase.charAt(spot);
        } else if (rPick == 1) {
          int spot = r.nextInt (25);
          uCode += uCase.charAt(spot);
        } else if (rPick == 2) {
          int spot = r.nextInt (9);
          uCode += intChar.charAt(spot);
        } else if (rPick == 3){
          int spot = r.nextInt (25);
          uCode += uCase.charAt(spot);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return uCode;
  }
  
  //========================================================================
  
  public String getRunnerCode() {
    return codes[0].concat(getUniqueCode());
  }
  
  //========================================================================
  
  public String getTeamLeaderCode() {
    return codes[1].concat(getUniqueCode());
  }
  
  //========================================================================
  
  public String getHubManagerCode() {
    return codes[2].concat(getUniqueCode());
  }
  
  //========================================================================
  
  public String getZoneManagerCode() {
    return codes[3].concat(getUniqueCode());
  }
  
  //========================================================================
  
  public String getRegionManagerCode() {
    return codes[4].concat(getUniqueCode());
  }
  
  //========================================================================
  
  public String getCircleManagerCode() {
    return codes[5].concat(getUniqueCode());
  }
  
  //========================================================================
  
  public String getCircleCode() {
    return codes[6].concat(getUniqueCodeWithoutSpecialChars());
  }
  
  //========================================================================
  
  public String getRegionCode() {
    return codes[7].concat(getUniqueCodeWithoutSpecialChars());
  }
  
  //========================================================================
  
  public String getZoneCode() {
    return codes[8].concat(getUniqueCodeWithoutSpecialChars());
  }
  
  //========================================================================
  
  public String getHubCode() {
    return codes[9].concat(getUniqueCodeWithoutSpecialChars());
  }
  
  //========================================================================
  
  public String getDistributorCode() {
    return codes[10].concat(getUniqueCode());
  }
  
  //========================================================================
  
  public String getBeatPlanCode() {
    return codes[11].concat(getUniqueCode());
  }
  
  //========================================================================
  
 /* public static void main(String[] args) {
	  System.out.println(UniqueCodeGenerator.getInstance().getCircleCode());
  }*/
}