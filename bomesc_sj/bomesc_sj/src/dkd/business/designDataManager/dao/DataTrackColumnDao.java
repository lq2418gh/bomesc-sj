package dkd.business.designDataManager.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import dkd.business.designDataManager.domain.DataTrackColumn;
import dkd.paltform.base.dao.BaseDao;

@Repository
public class DataTrackColumnDao extends BaseDao<DataTrackColumn>{

	public List<Map<String,Object>> findByUserAndMajor(Map<String,Object> map){
		String sql = "select id,user_id,major,CONVERT(varchar(max),show_columns) show_columns from c_data_track_columns"
				+ " where user_id=:user and major=:major ";
		return super.findBySql(sql,map);
	}
}
