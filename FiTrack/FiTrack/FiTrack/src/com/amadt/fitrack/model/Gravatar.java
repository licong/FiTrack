package com.amadt.fitrack.model;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.util.Log;

public class Gravatar {
	
	static final String gravatarUrl = "http://www.gravatar.com/avatar";

	private String email = "";
	
	public static class MD5Util {
		  public static String hex(byte[] array) {
		      StringBuffer sb = new StringBuffer();
		      for (int i = 0; i < array.length; ++i) {
			  sb.append(Integer.toHexString((array[i]
			      & 0xFF) | 0x100).substring(1,3));        
		      }
		      return sb.toString();
		  }
		  public static String md5Hex (String message) {
			  Log.i("FiTrack", message);
			  
		      try {
			  MessageDigest md = 
			      MessageDigest.getInstance("MD5");
			  return hex (md.digest(message.getBytes("CP1252")));
		      } catch (NoSuchAlgorithmException e) {
		    	  Log.e("FiTrack", e.getMessage());
		      } catch (UnsupportedEncodingException e) {
		    	  Log.e("FiTrack", e.getMessage());
		      }
		      
		      return null;
		  }
		}
	
	public Gravatar(String email) {
		if (email == null)
			email = "";
		
		this.email = email;
	}

	public String getUrl() {
		String email = this.email.trim().toLowerCase();					
		String md5 = MD5Util.md5Hex(email);
		
		String url = String.format("%s/%s?d=identicon", gravatarUrl, md5);
		
		return url;
	}
	
	@Override
	public String toString() {
		return getUrl();
	}
}
