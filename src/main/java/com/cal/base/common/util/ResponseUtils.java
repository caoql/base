package com.cal.base.common.util;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.zip.GZIPOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class ResponseUtils {

	/**
	 * 使用GZIPOutputStream流来压缩数据,设置响应头Content-Encoding来告诉浏览器，服务器发送回来的数据压缩后的格式
	 * @param request
	 * @param response
	 * @param data
	 * @throws IOException 
	 */
	public static void writeGZIP(HttpServletRequest request,
			HttpServletResponse response, byte[] data) throws IOException {
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		GZIPOutputStream gout = new GZIPOutputStream(bout); // buffer
		gout.write(data);
		gout.close();
		// 得到压缩后的数据
		byte g[] = bout.toByteArray();
		response.setHeader("Content-Encoding", "gzip");
		response.setHeader("Content-Length", g.length + "");
		response.getOutputStream().write(g);
	}
	
	/**
	 * 把图片以JPEG的格式写回浏览器
	 * @param request
	 * @param response
	 * @param path 如"download/curry.jpg"
	 * @throws IOException 
	 */
	public static void writeJpeg(HttpServletRequest request,
			HttpServletResponse response, String path) throws IOException {
		response.setHeader("content-type", "image/jpeg");
		InputStream is = request.getServletContext().getResourceAsStream(path);
		OutputStream os = response.getOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = is.read(buffer)) != -1) {
			os.write(buffer, 0 ,len);
		}
	}
	
	/**
	 * 下载文件
	 * @param request
	 * @param response
	 * @param path 如"download/curry.jpg"
	 * @throws IOException
	 */
	public static void downloadFile(HttpServletRequest request,
			HttpServletResponse response, String path, String filename) throws IOException {
		response.setHeader("content-disposition", "attachment;filename=" + filename);
		InputStream is = request.getServletContext().getResourceAsStream(path);
		OutputStream os = response.getOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = is.read(buffer)) != -1) {
			os.write(buffer, 0 ,len);
		}
	}
	
	public static void  downloadFileByOutputStream(HttpServletRequest request,HttpServletResponse response, String path) throws IOException {
		 //1.获取要下载的文件的绝对路径
        String realPath = request.getServletContext().getRealPath(path);
        //2.获取要下载的文件名
        String fileName = realPath.substring(realPath.lastIndexOf("\\")+1);
        //3.设置content-disposition响应头控制浏览器以下载的形式打开文件
        response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        //4.获取要下载的文件输入流
        InputStream in = new FileInputStream(realPath);
        int len = 0;
        //5.创建数据缓冲区
        byte[] buffer = new byte[1024];
        //6.通过response对象获取OutputStream流
        OutputStream out = response.getOutputStream();
        //7.将FileInputStream流写入到buffer缓冲区
        while ((len = in.read(buffer)) > 0) {
        //8.使用OutputStream将缓冲区的数据输出到客户端浏览器
            out.write(buffer,0,len);
        }
        in.close();
	}
}
