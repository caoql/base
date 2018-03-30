package com.cal.base.system.entity.vo;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

import com.cal.base.common.exception.CommonException;
import com.cal.base.common.reflect.ObjReflect;
import com.cal.base.system.entity.po.ResourcePO;

/**
 * 资源付案例界面新增编辑对应的VO
 * 
 * @author andyc 2018-3-30
 *
 */
public class ResourceVO implements Serializable {
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 2630857491860546156L;

	private String resourceId;

	private String name;

	private String url;

	private String type;

	private String description;

	private String nodeOrder;

	private String pid;

	private String isEnabled;

	public ResourcePO toResourcePO() {
		ResourcePO po = new ResourcePO();
		try {
			BeanUtils.copyProperties(po, this);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
			throw new CommonException("ResourceVO转成ResourcePO的过程出错了");
		}
		return po;
	}

	@Override
	public String toString() {
		return ObjReflect.toString(this);
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId == null ? null : resourceId.trim();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url == null ? null : url.trim();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type == null ? null : type.trim();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description == null ? null : description.trim();
	}

	public String getNodeOrder() {
		return nodeOrder;
	}

	public void setNodeOrder(String nodeOrder) {
		this.nodeOrder = nodeOrder == null ? null : nodeOrder.trim();
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid == null ? null : pid.trim();
	}

	public String getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(String isEnabled) {
		this.isEnabled = isEnabled == null ? null : isEnabled.trim();
	}
}