package com.cal.base.common.info;

import java.io.Serializable;

import com.cal.base.common.reflect.ObjReflect;

/**
 * 封装分页请求的基础数据-基于easyUI
 * @author andyc
 * @since 2018-1-3
 */
public class RequestInfo implements Serializable {
	
	private static final long serialVersionUID = -3288309404571820189L;

	/**
     * 每页记录数
     */
    protected int rows = 20;  
    
    /**
     * 当前页
     */
    protected int page = 1;
    
    /**
     * 排序字段
     */
    protected String sort;
    
    /**
     * 字段排序顺序
     */
    protected String order;
    
    /**
     * 查询条件
     */
    protected String condition;
    
    /**
     * 过滤规则
     */
    private String filterRules = null;
    
    // 通用查询字段
    protected String createTimeStart;
    
    protected String createTimeEnd;

    protected String creator;
    
    protected String userId;
    
    protected String syscode;
        
    protected String systemdef;
    
    protected String orgId;
	 
    @Override
	public String toString() {
		return ObjReflect.toString(this);
	}
    
	public int getRows() {
        return rows;
    }
    public void setRows(int rows) {
        this.rows = rows;
    }
    public int getPage() {
        return page;
    }
    public void setPage(int page) {
        this.page = page;
    }
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public String getCondition() {
        return condition;
    }
    public void setCondition(String condition) {
        this.condition = condition;
    }
	public String getFilterRules() {
		return filterRules;
	}
	public void setFilterRules(String filterRules) {
		this.filterRules = filterRules;
	}
	public String getCreateTimeStart() {
		return createTimeStart;
	}
	public void setCreateTimeStart(String createTimeStart) {
		this.createTimeStart = createTimeStart == null ? null : createTimeStart.trim();
	}
	public String getCreateTimeEnd() {
		return createTimeEnd;
	}
	public void setCreateTimeEnd(String createTimeEnd) {
		this.createTimeEnd = createTimeEnd == null ? null : createTimeEnd.trim();
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator == null ? null : creator.trim();
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSyscode() {
		return syscode;
	}

	public void setSyscode(String syscode) {
		this.syscode = syscode;
	}

	public String getSystemdef() {
		return systemdef;
	}

	public void setSystemdef(String systemdef) {
		this.systemdef = systemdef;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
}
