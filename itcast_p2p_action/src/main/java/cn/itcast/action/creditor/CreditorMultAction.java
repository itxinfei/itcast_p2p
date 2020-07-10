package cn.itcast.action.creditor;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.action.common.BaseAction;
import cn.itcast.domain.creditor.CreditorModel;
import cn.itcast.service.creditor.ICreditorService;
import cn.itcast.utils.ExceiUtils;
import cn.itcast.utils.ExcelRowResultHandler;
import cn.itcast.utils.Response;

@Namespace("/creditor")
@Controller
@Scope("prototype")
public class CreditorMultAction extends BaseAction {

	private File file; // 用来接收上传的文件
	private String fileFileName; // 用来接收上传的文件的名称

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	@Autowired
	private ICreditorService creditorService;

	@Action("upload")
	public void upload() {
		// 0.上传操作
		String uploadPath = this.getRequest().getSession().getServletContext().getRealPath("/WEB-INF/upload");
		File f1 = new File(uploadPath);
		String filename = System.currentTimeMillis() + fileFileName;
		File f2 = new File(f1, filename);
		try {
			// 在common-io下有一个工具类
			FileUtils.copyFile(file, f2);
			// 1.获取的文件是一个excel，应该将它的内容读取出来，封装成List<Creditor>
			// 调用ExcelUtils工具
			ExceiUtils<CreditorModel> eu = new ExceiUtils<CreditorModel>();
			List<CreditorModel> cs = eu.getEntity(f2, new ExcelRowResultHandler<CreditorModel>() {
				@Override
				public CreditorModel invoke(List<Object> list) {
					// 将每一行封装成一个CreditorModel对象.
					return null;
				}
			});
			// 2.调用service完成save操作
			creditorService.addMulti(cs);

			this.getResponse().getWriter().write(Response.build().setStatus("1").toJSON());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
