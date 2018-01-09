package com.cal.base.common.info;


/**
 * 封装用来返回easyUI格式的查询分页数据
 * @author andyc
 * 2017-10-25
 */
public class ResponsePageInfo extends ResponseInfo {
    /**
     * 记录详细信息
     */
    public Object rows;
    
    /**
     * 总共有多少记录
     */
    public long total;
    
    /**
     * 总共多少页
     */
    public int pages;
    
    /**
     * 当前第几页
     */
    public int pageNum;
    
    /**
     * 每页的条数
     */
    public int pageSize;
}
