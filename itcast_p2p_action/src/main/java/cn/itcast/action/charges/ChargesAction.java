package cn.itcast.action.charges;

import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.action.common.BaseAction;
import cn.itcast.action.filter.GetHttpResponseHeader;
import cn.itcast.cache.BaseCacheService;
import cn.itcast.dao.bankCardInfo.IBankCardInfoDAO;
import cn.itcast.domain.bankCardInfo.BankCardInfo;
import cn.itcast.service.bankCardInfo.IBankCardInfoService;
import cn.itcast.service.charges.IChargeService;

@Namespace("/charges")
@Controller
@Scope("prototype")
public class ChargesAction extends BaseAction {
	@Autowired
	private BaseCacheService baseCacheService;

	@Autowired
	private IBankCardInfoService bankCardInfoService;

	@Autowired
	private IChargeService chargeService;

	// 充值操作
	@Action("charge")
	public void charge() {
		// 1.得表请求参数
		String chargeMoney = this.getRequest().getParameter("chargeMoney");
		// 2.根据当前用户id，得到用户的绑定的银行卡号
		String token = GetHttpResponseHeader.getHeadersInfo(this.getRequest());
		Map<String, Object> hmap = baseCacheService.getHmap(token);
		int userId = (int) hmap.get("id");
		// 查询银行卡号
		BankCardInfo bci = bankCardInfoService.findByUserId(userId);
		String bankCardNum = bci.getBankCardNum();
		// 3.调用service完成充值操作
		chargeService.recharge(bankCardNum, Double.parseDouble(chargeMoney),userId);
	}
}
