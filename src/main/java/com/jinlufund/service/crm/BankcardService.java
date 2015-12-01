package com.jinlufund.service.crm;

import com.jinlufund.entity.crm.Bankcard;
import com.jinlufund.entity.crm.Customer;
import com.jinlufund.exception.JinluException;
import com.jinlufund.repository.crm.BankcardRepository;
import com.jinlufund.utils.JpaSpecificationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BankcardService {
	@Autowired
	BankcardRepository bankcardRepository;
	
	public void add(Customer customer,Bankcard input){
		//todo 检测数据是否完整
		input.setCustomerId(customer.getId());
		input.setDeleteOrNot(false);
		bankcardRepository.save(input);
	}

	/**
	 * 通过用户的id找出所有的银行卡号
	 * @param customerId  示例：
	 * 		Map<String,Object> searchParam = new HashMap<>();
	 * 		searchParam.put("customerId_GT_INTEGER",id);
     * @return
     */
	public List<Bankcard> findByCustomerId(Long customerId){
		Map<String,Object> searchParam = new HashMap<>();
		searchParam.put("customerId_GT_INTEGER",customerId);
		Specification<Bankcard> specification = JpaSpecificationUtils.createSpecification(searchParam,Bankcard.class);
		ArrayList<Bankcard> bankcards = (ArrayList<Bankcard>) bankcardRepository.findAll(specification);
		ArrayList<Bankcard> validBankCards = new ArrayList<Bankcard>();
		for (Bankcard b :
				bankcards) {
			if (!b.isDeleteOrNot()){
				validBankCards.add(b);
			}
		}
		return validBankCards;
	}

	public Bankcard findByAccountNo(String accountNo){
		Bankcard bankcard = assertBancardExist(accountNo);
		return bankcard;
	}

	// TODO: 15/11/27 未完成银行卡信息更新
	public void update(Bankcard input){
		String accountNo = input.getAccountNo();
		Bankcard bankcard = assertBancardExist(accountNo);
		bankcard.setDeleteOrNot(input.isDeleteOrNot());
		bankcard.setAccountBankCityId(input.getAccountBankCityId());
		bankcard.setAccountBankName(input.getAccountBankName());
		bankcard.setAccountBankProvinceId(input.getAccountBankProvinceId());
		bankcard.setAccountName(input.getAccountName());
		bankcard.setAccountType(input.getAccountType());
		bankcard.setBankCardStatus(input.getBankCardStatus());
		bankcard.setUsage(input.getUsage());
		bankcardRepository.save(bankcard);
	}
	
	public Bankcard assertBancardExist(String accountNo){
		Bankcard bankCard = bankcardRepository.findOne(accountNo);
		if(bankCard == null||bankCard.isDeleteOrNot()){
			JinluException e = new JinluException();
			e.setErrorCode("bankService.bankcard.notExist");
			e.addParameter("accountNo", accountNo);
			throw e;
		}
		return bankCard;
	}
	public void deleteByCustomerId(Long customerId){
		List<Bankcard> byCustomerId = findByCustomerId(customerId);
		for (Bankcard b :
				byCustomerId) {
			b.setDeleteOrNot(true);
			update(b);
		}
	}
	public void deleteByAccountNo(String accountNo){
		Bankcard bankcard = assertBancardExist(accountNo);
		bankcard.setDeleteOrNot(true);
		update(bankcard);
	}
}
