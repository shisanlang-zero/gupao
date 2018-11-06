package com.skynet.zero.test;

import java.lang.reflect.Field;
import java.util.function.ObjDoubleConsumer;

interface message{
	public static final String msg ="hello world";
}

abstract class info {
	public static final String city ="北京";
	public String id = "14521035555555";
}

class dept extends info implements message {
	private String dname;
}

public class TestReflex {
	
	public static void main(String[] args) throws Exception {
		Class<?> cls = Class.forName("com.skynet.zero.test.dept");
		Field[] field = cls.getFields();
		for (int i = 0; i < field.length; i++) {
			System.out.println(field[i]);
		}
		
		field = cls.getDeclaredFields();
		System.out.println("----------------------------------------");
		for (int i = 0; i < field.length; i++) {
			System.out.println(field[i]);
		}
		
		Object obj = cls.newInstance();
		Field field2 = cls.getDeclaredField("dname");
		field2.setAccessible(true);
		field2.set(obj, "aa");
		System.out.println(field2.get(obj));
	}

}
