package cn.itcast.dao.creditor;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.itcast.domain.creditor.CreditorModel;

public interface ICreditorDAO extends JpaRepository<CreditorModel, Integer>{

}
