package cn.itcast.action.creditor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

import org.apache.commons.io.IOUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.action.common.BaseAction;

@Namespace("/creditor")
@Controller
@Scope("prototype")
public class CreditorAction extends BaseAction {

	@Action("download")
	public void download() {
		// 1.将资源读取
		// 1.1得到文件位置
		String path = this.getRequest().getSession().getServletContext()
				.getRealPath("/WEB-INF/excelTemplate/ClaimsBatchImportTemplate.xlsx");
		// 设置下载时两个响应头
		String mimeType = this.getRequest().getSession().getServletContext()
				.getMimeType("ClaimsBatchImportTemplate.xlsx");
		this.getResponse().setHeader("content-type", mimeType);
		this.getResponse().setHeader("content-disposition",
				"attachment;filename=" + (new Date().toLocaleString() + ".xlsx"));
		// 1.2获取输入流
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(path);
			// 2.将资源写出,使用response来获取流
			OutputStream os = this.getResponse().getOutputStream();
			IOUtils.copy(fis, os);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}
}
