package cn.itcast.service.creditor.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itcast.dao.creditor.ICreditorDAO;
import cn.itcast.domain.creditor.CreditorModel;
import cn.itcast.service.creditor.ICreditorService;

@Service
public class CreditorServiceImpl implements ICreditorService {

	@Autowired
	private ICreditorDAO creditorDao;

	@Override
	public void addMulti(List<CreditorModel> cs) {

		creditorDao.save(cs);
	}
}
