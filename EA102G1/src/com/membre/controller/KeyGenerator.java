package com.membre.controller;

public class KeyGenerator {
	
	public static String genAuthCode() {
		String str ="";
		do {
			char c  =(char)((int)(Math.random()*75 + 48));
			
			if((int)(c)>=48 && (int)(c)<58) {
			str+=c;
			}else if((int)(c)>64&&(int)(c)<91) {
			str+=c;
			}else if((int)(c)>96&&(int)(c)<=122) {
			str+=c;
			}
		}while (str.length()<8);
		return str;
	}
}
