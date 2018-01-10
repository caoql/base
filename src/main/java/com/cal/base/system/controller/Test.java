package com.cal.base.system.controller;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.cal.base.common.reflect.ObjReflect;

public class Test implements Serializable {
	
	private static final long serialVersionUID = -2089338984651010630L;

	@NotNull(message="不能为空")
	@Size(max=10, min=5, message="{test.name.size}")
	private String name;
	
	private int age;
	
	
	@Override
	public String toString() {
		return ObjReflect.toString(this);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
}
