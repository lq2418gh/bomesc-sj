package dkd.paltform.fileinfo.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import dkd.paltform.base.QueryResult;
import dkd.paltform.base.dao.BaseDao;
import dkd.paltform.fileinfo.domain.FileInfo;

@Repository
public class FileInfoDao extends BaseDao<FileInfo>{
	@Override
	public QueryResult<Map<String, Object>> getScrollData(Map<String,String> params){
		return getScrollData("select id,entity_createdate,entity_createuser,file_name,path from base_fileinfo order by entity_createdate desc",params);
	}
}
