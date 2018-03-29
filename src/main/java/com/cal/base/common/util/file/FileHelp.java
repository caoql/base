package com.cal.base.common.util.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase.FileSizeLimitExceededException;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * 原始JavaWeb的学习文件上传和下载处理
 * @author andyc
 * 2018-3-29
 */
public final class FileHelp {
	// 定义允许上传的文件扩展名
	private String Ext_Name = "gif,jpg,jpeg,png,bmp,swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb,doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2";

	public void upload(HttpServletRequest request, HttpServletResponse response) {
		// 得到上传文件的保存目录，将上传文件存放在WEB-INF目录下，不允许外界直接访问，保证上传文件的安全
		String savePath = request.getServletContext().getRealPath(
				"WEB-INF/upload");
		File saveFileDir = new File(savePath);
		if (!saveFileDir.exists()) {
			// 创建临时目录
			saveFileDir.mkdirs();
		}

		// 上传时生成临时文件保存目录
		String tmpPath = request.getServletContext().getRealPath("WEB-INF/tem");
		File tmpFile = new File(tmpPath);
		if (!tmpFile.exists()) {
			// 创建临时目录
			tmpFile.mkdirs();
		}

		// 消息提示
		String message = "";
		try {
			// 使用Apache文件上传组件处理文件上传步骤：
			// 1.创建一个DiskFileItemFactory工厂
			DiskFileItemFactory factory = new DiskFileItemFactory();
			// 设置工厂的缓冲区的大小，当上传的文件大小超过缓冲区的大小时，就会生成一个临时文件存放到指定的临时目录当中
			factory.setSizeThreshold(1024 * 10);// 设置缓冲区的大小为100KB，如果不指定，那么默认缓冲区的大小是10KB
			// 设置上传时生成的临时文件的保存目录
			factory.setRepository(tmpFile);
			// 2.创建一个文件上传解析器
			ServletFileUpload upload = new ServletFileUpload(factory);
			// 监听文件上传进度
			upload.setProgressListener(new ProgressListener() {
				@Override
				public void update(long readedBytes, long totalBytes,
						int currentItem) {
					System.out.println("当前已处理：" + readedBytes
							+ "-----------文件大小为：" + totalBytes + "--"
							+ currentItem);
				}
			});
			// 解决上传文件名的中文乱码问题
			upload.setHeaderEncoding("UTF-8");
			// 3.判断提交上来的数据是否是上传表单的数据
			if (!ServletFileUpload.isMultipartContent(request)) {
				// 按照传统方式获取数据
				return;
			}

			// 设置上传单个文件的最大值
			upload.setFileSizeMax(1024 * 1024 * 1);// 1M
			// 设置上传文件总量的最大值，就是本次上传的所有文件的总和的最大值
			upload.setSizeMax(1024 * 1024 * 10);// 10M

			List items = upload.parseRequest(request);
			Iterator itr = items.iterator();
			while (itr.hasNext()) {
				FileItem item = (FileItem) itr.next();
				// 如果fileitem中封装的是普通的输入想数据
				if (item.isFormField()) {
					String name = item.getFieldName();
					// 解决普通输入项数据中文乱码问题
					String value = item.getString("UTF-8");
					// value = new String(value.getBytes("iso8859-1"),"UTF-8");
					System.out.println(name + "=" + value);
				} else// 如果fileitem中封装的是上传文件
				{
					// 得到上传文件的文件名
					String fileName = item.getName();
					System.out.println("文件名：" + fileName);
					if (fileName == null && fileName.trim().length() == 0) {
						continue;
					}
					// 注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的
					// 如: C:\Users\H__D\Desktop\1.txt 而有些则是 ： 1.txt
					// 处理获取到的上传文件的文件名的路径部分，只保留文件名部分
					fileName = fileName
							.substring(fileName.lastIndexOf("\\") + 1);

					// 得到上传文件的扩展名
					String fileExt = fileName.substring(
							fileName.lastIndexOf(".") + 1).toLowerCase();
					// 检查扩展名
					// 如果需要限制上传的文件类型，那么可以通过文件的扩展名来判断上传的文件类型是否合法
					System.out.println("上传的文件的扩展名是：" + fileExt);
					if (!Ext_Name.contains(fileExt)) {
						System.out.println("上传文件扩展名是不允许的扩展名：" + fileExt);
						message = message + "文件：" + fileName
								+ "，上传文件扩展名是不允许的扩展名：" + fileExt + "<br/>";
						break;
					}

					// 检查文件大小
					if (item.getSize() == 0)
						continue;
					if (item.getSize() > 1024 * 1024 * 1) {
						System.out.println("上传文件大小：" + item.getSize());
						message = message + "文件：" + fileName + "，上传文件大小超过限制大小："
								+ upload.getFileSizeMax() + "<br/>";
						break;
					}

					// 得到存文件的文件名
					String saveFileName = makeFileName(fileName);

					// 保存文件方法一// 获取item中的上传文件的输入流
					InputStream is = item.getInputStream();
					// 创建一个文件输出流
					FileOutputStream out = new FileOutputStream(savePath + "\\"
							+ saveFileName);
					// 创建一个缓冲区
					byte buffer[] = new byte[1024];
					// 判断输入流中的数据是否已经读完的标致
					int len = 0;
					while ((len = is.read(buffer)) > 0) {
						out.write(buffer, 0, len);
					}
					// 关闭输出流
					out.close();
					// 关闭输入流
					is.close();
					// 删除临时文件
					item.delete();

					message = message + "文件：" + fileName + "，上传成功<br/>";

					// 保存文件方法二
					// File uploadedFile = new File(savePath, saveFileName);
					// item.write(uploadedFile);

				}

			}

		} catch (FileSizeLimitExceededException e) {
			message = message + "上传文件大小超过限制<br/>";
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
		}

	}

	private String makeFileName(String fileName) {
		// 为防止文件覆盖的现象发生，要为上传文件产生一个唯一的文件名
		return UUID.randomUUID().toString().replaceAll("-", "") + "_"
				+ fileName;
	}

	// 下载
	public void download(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 得到要下载的文件名
		String fileName = request.getParameter("filename"); // 2323928392489-美人鱼.avi
		fileName = new String(fileName.getBytes("iso8859-1"), "UTF-8");
		// 上传的文件都是保存在/WEB-INF/upload目录下的子目录当中
		String fileSaveRootPath = request.getServletContext().getRealPath(
				"/WEB-INF/upload");
		// 得到要下载的文件
		File file = new File(fileSaveRootPath + "\\" + fileName);
		// 如果文件不存在
		if (!file.exists()) {
			// request.setAttribute("message", "您要下载的资源已被删除！！");
			// request.getRequestDispatcher("/message.jsp").forward(request,
			// response);
			return;
		}
		// 处理文件名
		String realname = fileName.substring(fileName.indexOf("_") + 1);
		// 设置响应头，控制浏览器下载该文件
		response.setHeader("content-disposition", "attachment;filename="
				+ URLEncoder.encode(realname, "UTF-8"));
		// 读取要下载的文件，保存到文件输入流
		FileInputStream in = new FileInputStream(fileSaveRootPath + "\\"
				+ fileName);
		// 创建输出流
		OutputStream out = response.getOutputStream();
		// 创建缓冲区
		byte buffer[] = new byte[1024];
		int len = 0;
		// 循环将输入流中的内容读取到缓冲区当中
		while ((len = in.read(buffer)) > 0) {
			// 输出缓冲区的内容到浏览器，实现文件下载
			out.write(buffer, 0, len);
		}
		// 关闭文件输入流
		in.close();
		// 关闭输出流
		out.close();
	}
}
