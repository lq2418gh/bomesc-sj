package dkd.paltform.sequence.dao;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import dkd.paltform.base.dao.BaseDao;
import dkd.paltform.sequence.domain.Sequence;
/**
 * 序号DAO
 * @author 周渤涛
 *
 */
@Repository
public class SequenceDao extends BaseDao<Sequence> {
	/**
	 * 查询序号并返回当前最大数
	 * @param entityType
	 * @param sequenceByValue
	 * @return
	 */
	public int findByFlowNo(Serializable entityType, Serializable sequenceByValue) {
		synchronized (SequenceDao.class) {
			String updateSql = "update base_sequence set flow_no = flow_no + 1 where entity_type = '"+entityType+"' and sequence_by_value = '"+sequenceByValue+"' ";
			String selectSql = "select flow_no from base_sequence where entity_type = '"+entityType+"' and sequence_by_value = '"+sequenceByValue+"' ";
			if(0 == em.createNativeQuery(updateSql).executeUpdate()){
				String insertSql = "insert into base_sequence (entity_type,sequence_by_value,flow_no)values('"+entityType+"','"+sequenceByValue+"',1)";
				return em.createNativeQuery(insertSql).executeUpdate();
			}
			return Integer.parseInt(findBySql(selectSql).get(0).get("flow_no").toString());
		}
	}
}
