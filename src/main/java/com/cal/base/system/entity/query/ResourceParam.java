package com.cal.base.system.entity.query;

import java.io.Serializable;

import com.cal.base.common.info.RequestInfo;
import com.cal.base.common.reflect.ObjReflect;

/**
 * 资源管理界面查询条件封装
 * @author andyc
 * 2018-3-30
 */
public class ResourceParam extends RequestInfo implements Serializable {
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -1354736731725043101L;

	private String name;
	
	private String isEnabled;
	
	 private String type;
	
	@Override
	public String toString() {
		return ObjReflect.toString(this);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public String getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(String isEnabled) {
		this.isEnabled = isEnabled == null ? null : isEnabled.trim();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type == null ? null : type.trim();
	}
}
