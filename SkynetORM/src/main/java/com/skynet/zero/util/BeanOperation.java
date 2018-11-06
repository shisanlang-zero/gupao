package com.skynet.zero.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;

/**
 * 本类的功能是根据属性的名称和属性设置传递内容
 * @author Administrator
 *
 */
public class BeanOperation {
	//操作对象
	private Object obj;
	//操作属性
	private String property;
	//操作内容
//	private String value;
	private Object value;
	//要操作的属性
	private Field field;
	//保存成员的名称
	private String fieldName;
	//操作属性的对象 
	private Object currenObj;

	public BeanOperation(Object obj, String property, Object value) throws Exception {
		super();
		this.obj = obj;
		this.property = property;
		this.value = value;
		handleString();
		setFieldValue();
	}
	
	private void handleString() throws Exception {
		String result[] = this.property.split("\\.");
		this.currenObj = this.obj;
		if(result.length == 2) {
			this.field = this.currenObj.getClass().getDeclaredField(result[1]);
			this.fieldName = field.getName();
		} else {
			for (int i = 1; i < result.length; i++) {
				//getter方法是没有参数的
				Method method = this.currenObj.getClass().getMethod("get" + StringUtil.initCap(result[i]));
				this.field = this.currenObj.getClass().getDeclaredField(result[i]);
				this.fieldName = result[i];
				if(i < result.length-1) {
					this.currenObj = method.invoke(this.currenObj);
				}
			}
		}
	}
	
	/**
	 * 设置指定属性的内容
	 * @return
	 * @throws Exception 
	 * @throws  
	 */
//	private void setFieldValue() {
//		//找到setter 方法
//		Method method;
//		try {
//			method = this.currenObj.getClass().getDeclaredMethod("set" + StringUtil.initCap(this.fieldName), this.field.getType());
//			String type = this.field.getType().getSimpleName();
//			
//			if("int".equalsIgnoreCase(type) || "Integer".equalsIgnoreCase(type)) {
//				if(this.value.matches("\\d+")) {
//					//调用setter方法设置值
//					method.invoke(this.currenObj, Integer.valueOf(this.value));
//				}
//			} else if("double".equalsIgnoreCase(type) || "Double".equalsIgnoreCase(type)) {
//				if(this.value.matches("\\d+(\\.\\d+)?")) {
//					//调用setter方法设置值
//					method.invoke(this.currenObj, Double.valueOf(this.value));
//				}
//			} else if("string".equalsIgnoreCase(type) ) {
//				method.invoke(this.currenObj, this.value);
//			} else if("date".equalsIgnoreCase(type) ) {
//				if(this.value.matches("\\d{4}-\\d{2}-\\{2}")) {
//					method.invoke(this.currenObj, new SimpleDateFormat("yyyy-MM-dd").parse(this.value));
//				}
//			}
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}
	
	private void setFieldValue() {
		//找到setter 方法
		Method method;
		try {
			method = this.currenObj.getClass().getDeclaredMethod("set" + StringUtil.initCap(this.fieldName), this.field.getType());
			String type = this.field.getType().getSimpleName();
			String valueType = this.value.getClass().getSimpleName();
			//保存处理之后的数据
			
			if("string".equalsIgnoreCase(type)) {
				String val = null;
				val = this.value.toString();
				if("int".equalsIgnoreCase(type) || "Integer".equalsIgnoreCase(type)) {
					if(val.matches("\\d+")) {
						//调用setter方法设置值
						method.invoke(this.currenObj, Integer.valueOf(val));
					}
				} else if("double".equalsIgnoreCase(type) || "Double".equalsIgnoreCase(type)) {
					if(val.matches("\\d+(\\.\\d+)?")) {
						//调用setter方法设置值
						method.invoke(this.currenObj, Double.valueOf(val));
					}
				} else if("string".equalsIgnoreCase(type) ) {
					method.invoke(this.currenObj, this.value);
				} else if("date".equalsIgnoreCase(type) ) {
					if(val.matches("\\d{4}-\\d{2}-\\{2}")) {
						method.invoke(this.currenObj, new SimpleDateFormat("yyyy-MM-dd").parse(val));
					}
				}
			} else {  //传入的内容为string类型
				String val[] = (String[])this.value;
				this.field.setAccessible(true);
				if("string[]".equalsIgnoreCase(type)) {
//					method.invoke(this.currenObj, val);
					this.field.set(currenObj, val);
				} else if("int[]".equalsIgnoreCase(type) || "Integer[]".equalsIgnoreCase(type)) {
					Integer data[] = new Integer[val.length];
					for (int i = 0; i < val.length; i++) {
						data[i] = Integer.valueOf(val[i]);
					}
//					method.invoke(this.currenObj, data);
					this.field.set(currenObj, data);
				} else if("double[]".equalsIgnoreCase(type) || "Double[]".equalsIgnoreCase(type)) {
					Double data[] = new Double[val.length];
					for (int i = 0; i < val.length; i++) {
						data[i] = Double.valueOf(val[i]);
					}
//					method.invoke(this.currenObj, data);
					this.field.set(currenObj, data);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
	

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}

	public Object getCurrenObj() {
		return currenObj;
	}

	public void setCurrenObj(Object currenObj) {
		this.currenObj = currenObj;
	}
	

}