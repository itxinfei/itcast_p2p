package cn.itcast.service.creditor;

import java.util.List;

import cn.itcast.domain.creditor.CreditorModel;

public interface ICreditorService {

	void addMulti(List<CreditorModel> cs);

}
