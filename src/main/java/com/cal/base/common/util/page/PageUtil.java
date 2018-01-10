package com.cal.base.common.util.page;

import java.util.List;

import com.cal.base.common.info.RequestInfo;
import com.cal.base.common.info.ResponsePageInfo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 分页的帮助类
 * @author andyc
 * @since 2018-1-3
 */
public class PageUtil {
	
	/**
	 * 分页前通用请求参数封装
	 * @param info 分页返回的通用数据封装
	 * @param param 请求的数据参数封装
	 */
	public static void pageBefore(ResponsePageInfo info, RequestInfo param) {
		//从第几条开始
        int pageNum = param.getPage();
        //要查多少条
        int pageSize = param.getRows();
        //分页
        PageHelper.startPage(pageNum, pageSize);
	}
	
	/**
	 * 分页后返回数据封装
	 * @param info 分页返回的通用数据封装
	 * @param list 数据库查询出来的数据
	 */
	public static <T> void pageAfter(ResponsePageInfo info, List<T> list) {
		//获取分页结果
        PageInfo<T> page=new PageInfo<T>(list);
        //结果集
        info.rows = list;
        //总记录数
        info.total = page.getTotal();
        //总页数
        info.pages = page.getPages();
        //当前页
        info.pageNum = page.getPageNum();
        //每页的数量
        info.pageSize = page.getPageSize();
	}
}
