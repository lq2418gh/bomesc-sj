package dkd.paltform.sequence.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import dkd.paltform.base.BusinessException;
import dkd.paltform.sequence.dao.SequenceDao;
import dkd.paltform.util.StringUtils;

/**
 * 序号表逻辑层
 * @author 周渤涛
 *
 */
@Service
public class SequenceService {

	@Autowired
	private SequenceDao sequenceDao;
	
	/**
	 * 返回序号
	 * @param entityType
	 * @param sequenceByValue
	 * 用法：StringUtils.full0(7, sequenceService.getFlowNo("arrival.OutBound", payType) + "");
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public int getFlowNo(String entityType, String sequenceByValue) {
		if(StringUtils.isEmpty(entityType))//entityType字段必须不为空
			throw new BusinessException(-1, "entityType", "entityType不允许为空");
		if(null == sequenceByValue || StringUtils.isEmpty(sequenceByValue))
			sequenceByValue = " ";//sequenceByValue字段可以为null，为null时转换为空格
		return sequenceDao.findByFlowNo(entityType, sequenceByValue);
	}
}
