/**
 * @author Jp
 *
 */
package com.tracer.util;

import static com.tracer.common.Constants.DATE_FORMAT;
import static com.tracer.common.Constants.DATE_FORMAT_DB;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;


public class StringUtil {

  /**
   * Encodes a string using Base64 encoding. Used when storing passwords as
   * cookies.
   * <p>  This is weak encoding in that anyone can use the decodeString
   * routine to reverse the encoding.
   * 
   * @param str The string to encode
   * @return Encoded string
   */
  public static String encodeString(String str) {
    BASE64Encoder encoder = new BASE64Encoder();
    return encoder.encodeBuffer(str.getBytes()).trim();
  }

    
  /**
   * Decodes a string which was encoded using Base64 encoding.
   * 
   * @param str
   *            The string to decode
   * @return Decoded string
   */
  public static String decodeString(String str) {
    BASE64Decoder dec = new BASE64Decoder();
    try {
      return new String(dec.decodeBuffer(str));
    } catch (IOException io) {
      throw new RuntimeException(io.getMessage(), io.getCause());
    }
  }

  public static String capitalize(String s) {
    if (s == null || s.length() == 0) {
      return s;
    }
    char[] chars = s.toCharArray();
    chars[0] = Character.toUpperCase(chars[0]);
    return String.valueOf(chars);
  }

 
  public static boolean isNull(String sourceString) {
    if (sourceString == null || sourceString.trim().equals("")) {
      return true;
    } else {
      return false;
    }

  }

  public static boolean isNotNull(String sourceString) {
    if (sourceString != null && (!"".equals(sourceString.trim()))) {
      return true;
    } else {
      return false;
    }
  }


  public static boolean isDateNotNull(String sourceString) {
    if (sourceString != null
        && (!"".equals(sourceString) && (!"0NaN-NaN-NaN"
            .equals(sourceString)))) {
      return true;
    } else {
      return false;
    }
  }

  public static String trim(String param) {
    String returnParam = param;
    if (null != param) {
      returnParam = param.trim();
    }
    return returnParam;
  }

  
  public static String valueOf(Object obj) {
    String strValue = String.valueOf(obj);
    if("null".equals(strValue)) {
      strValue = null;
    }    
    return strValue;
  }
  
  
  public static Boolean isChecked(Object obj) {
    String strValue = String.valueOf(obj);
    Boolean isChecked = false;
    if("off".equalsIgnoreCase(strValue)) {
      isChecked = false;
    } else {
      isChecked = true;
    }
    return isChecked;
  }
  
  //=========================================================================
  
  public static boolean isValidImageContentType(String contentType) {
    if("image/png".equals(contentType) || "image/gif".equals(contentType) || "image/jpeg".equals(contentType) || "image/pjpeg".equals(contentType)) {
      return true;
    } else {
      return false;
    }
  }
  
  //=========================================================================
  
  public static String removeTrailingValue(StringBuffer source, String value) {
	String result = null;
	
	try {
	 if(source != null && source.length() > 0 && source.toString().contains(value)) {
		result = source.toString();
		result = result.substring(0, result.lastIndexOf(value));
	 }
	} catch (Exception e) {
		e.printStackTrace();
	}
	return result;
  }
  
  //=========================================================================
 
  public static String convertUIDateToDBDate(String uiDate) {
    String dbDate = null;
    Date uDate = null;
    DateFormat udf = new SimpleDateFormat(DATE_FORMAT);
    DateFormat dbdf = new SimpleDateFormat(DATE_FORMAT_DB);
    
    try {
      if(StringUtil.isNotNull(uiDate)) {
        uDate = udf.parse(uiDate);
      } else {
        uDate = new Date();
      }
      dbDate = dbdf.format(uDate);
    } catch (ParseException e) {
        e.printStackTrace();
    }
    return dbDate;
  }
  
  //=========================================================================
  
  public static boolean isValidMobileNumber(String mobileNumber) {
    String regex = "^((\\+91-?)|0)?[1-9]{1}[0-9]{9}$";
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(mobileNumber);
      if (matcher.matches()) {
      return true;
      } else {
        return false;
      }
  }
  
  //=========================================================================
  
  public static void main(String[] args) {
   System.out.println(isValidMobileNumber("0088384950"));  }
}