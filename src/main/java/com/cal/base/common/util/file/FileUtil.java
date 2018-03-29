package com.cal.base.common.util.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.cal.base.common.exception.CommonException;

/**
 * 文件帮助类
 * 
 * @author andyc 2018-3-29
 */
public final class FileUtil {
	
	// 文件分割符，适合不同的平台，如windows和linux
	public static final String separator = File.separator;
	
	// 上传文件存放二级目录
	private static final String UPLOAD_FILE_DISK = "uploadFile";
	
	// 模板文件存放二级目录
	private static final String TEMPLET_FILE_DISK = "templet";
	

	// 文件上传: 抛异常就在调用方法的地方说文件上传失败
	public static List<String> upload(HttpServletRequest request,
			String... fileTypes) throws Exception {
		// 文件路径的集合类
		List<String> filesPathList =  new ArrayList<String>();
		
		// 将当前上下文初始化给 CommonsMutipartResolver（多部分解析器）
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getServletContext());
		// 检查form中是否有enctype="multipart/form-data"-判断是否是文件上传
		if (multipartResolver.isMultipart(request)) {
			// 将request变成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			// 获取multiRequest 中所有的文件名
			Iterator<String> iter = multiRequest.getFileNames();
			while (iter.hasNext()) {
				// 一次遍历所有文件
				MultipartFile multipartFile = multiRequest.getFile(iter.next());
				if (multipartFile != null) {
					// 文件后缀名
					String extend = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf(".") + 1);
					// 检查文件后缀名是否符合预定义的文件格式
					if (fileTypes != null) {
						boolean flag = false;
						StringBuffer sb = new StringBuffer();
						for (String type : fileTypes) {
							if (type.toLowerCase().equals(extend.toLowerCase())) {
								flag = true;
								sb = null;// 考虑到性能问题
								break;
							} 
							sb.append(type + ",");
						}
						if (!flag) {
							throw new CommonException("上传附件格式不对，只能上传" + sb.toString()+ "格式,请检查后再上传");
						}
					}
					// 去掉后缀名的文件名
					String fileName = multipartFile.getOriginalFilename().substring(0, multipartFile.getOriginalFilename().lastIndexOf("."));
					// 项目的根目录下
					String webRealPath = request.getServletContext().getRealPath("");
					// 时间格式化目录
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					String today = sdf.format(new Date());
					// 以后这里要往文件服务器上传
					// 去掉最后一个文件分隔符
					String tempPath = webRealPath.substring(0, webRealPath.lastIndexOf(separator));
					// 项目的根目录退一级目录做拼接
					String fileSaveDisk = tempPath.substring(0, tempPath.lastIndexOf(separator)) + separator + UPLOAD_FILE_DISK + separator + today;
					// 创建文件存放目录
					File fileDist = new File(fileSaveDisk);
					if (!fileDist.exists()) {
						fileDist.mkdirs();
					}
					// 上传-UUID生成避免文件重名
					String pathUrl = fileDist + separator + fileName + "-" + UUID.randomUUID().toString().replace("-", "") + "." + extend;
					// 文件上传
					multipartFile.transferTo(new File(pathUrl));
					// 文件路径回传
					filesPathList.add(pathUrl);
				}
			}
		} 
		return filesPathList;
	}

	// 文件下载
	public static void download(HttpServletRequest request, HttpServletResponse response, String storeName)
			throws Exception {
		// 通用响应头
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;

		// 要下载文件的地址处理
		String realPath = request.getServletContext().getRealPath("");
		String ctxPath = realPath + TEMPLET_FILE_DISK + separator;
		String downLoadPath = ctxPath + storeName;
		
		// 文件长度
		long fileLength = new File(downLoadPath).length();
		// 响应头设置
		response.setHeader("Content-disposition", "attachment; filename="+ new String(storeName.getBytes("gb2312"), "ISO-8859-1"));
		response.setHeader("Content-Length", String.valueOf(fileLength));

		// 二进制流(字节流)处理
		bis = new BufferedInputStream(new FileInputStream(downLoadPath));
		bos = new BufferedOutputStream(response.getOutputStream());
		byte[] buff = new byte[1024];
		int bytesRead;
		while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
			bos.write(buff, 0, bytesRead);
		}
		bis.close();
		bos.close();
	}
}
