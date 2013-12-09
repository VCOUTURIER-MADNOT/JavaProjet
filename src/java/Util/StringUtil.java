package Util;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public class StringUtil {
    
    private static final String HEX_DIGITS = "0123456789abcdef";
    
    public static String toMD5(String _textToHash) {
    	java.security.MessageDigest msgDigest;
    	String hash = "";
    	
		try {
			msgDigest = java.security.MessageDigest.getInstance("MD5");
	        msgDigest.update(StringToByteArray(_textToHash));
	        byte[] digest = msgDigest.digest();
	        hash = ByteArrayToHexString(digest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
        
        return hash;
    }
    
    	public static byte[] StringToByteArray(String _String)
	{
		try {
			return _String.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
    public static String ByteArrayToHexString(byte[] _byteArray) {
    	 
        StringBuffer sb = new StringBuffer(_byteArray.length * 2);
        for (int i = 0; i < _byteArray.length; i++) {
            int b = _byteArray[i] & 0xFF;
             sb.append(HEX_DIGITS.charAt(b >>> 4))
               .append(HEX_DIGITS.charAt(b & 0xF));
        }
        return sb.toString();
    }
    
    public static String ByteArrayToXMLString(byte[] _byteArray) {
    	try {
			return new String(_byteArray, "UTF-8").trim();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    	
    	return null;
    }
	
}
