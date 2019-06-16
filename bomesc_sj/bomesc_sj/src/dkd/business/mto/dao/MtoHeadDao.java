package dkd.business.mto.dao;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import dkd.business.mto.domain.MtoDetail;
import dkd.business.mto.domain.MtoHead;
import dkd.paltform.base.QueryResult;
import dkd.paltform.base.dao.BaseDao;
@Repository
public class MtoHeadDao extends BaseDao<MtoHead>{
	@Override
	public QueryResult<Map<String, Object>> getScrollData(Map<String,String> params){
		String sql="select bmh.*,bd.name major_name from b_mto_head bmh left join base_dictionary bd on bmh.major = bd.id order by bmh.state desc,bmh.entity_createdate desc";
		return getScrollData(sql,params,new String[]{"major"});
	}
	/**
	 * @date 2018年1月10日
	 * @author gaoxp
	 * @param mto_no
	 * @return
	 * 描述：
	 */
	public MtoHead findByField(String mto_no){
		MtoHead mtoHead= super.findByField("mto_no", mto_no);
		List<MtoDetail> details = mtoHead.getDetails();
		Collections.sort(details, new Comparator<MtoDetail>() {//使得明细按照行号排列
			@Override
			public int compare(MtoDetail o1, MtoDetail o2) {
				return o1.getMto_row_no()-o2.getMto_row_no();
			}  
		});
		mtoHead.setDetails(details);
		return mtoHead;
	}
}
