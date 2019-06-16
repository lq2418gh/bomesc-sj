package dkd.business.material.service;


import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dkd.business.designDataManager.domain.StDataTrack;
import dkd.business.designDataManager.service.PiDataTrackSuppService;
import dkd.business.designDataManager.service.StDataTrackTableService;
import dkd.business.material.dao.MaterialArchivesDao;
import dkd.business.material.domain.MaterialArchives;
import dkd.paltform.base.BusinessException;
import dkd.paltform.base.dao.BaseDao;
import dkd.paltform.base.service.BaseService;
import dkd.paltform.util.Constant;

@Service
@Transactional
public class MaterialArchivesService extends BaseService<MaterialArchives>{
	@Autowired
	private MaterialArchivesDao materialArchivesDao;
	@Autowired
	private MaterialClassService materialClassService;
	@Autowired
	private StDataTrackTableService stDataTrackTableService;
	@Autowired
	private PiDataTrackSuppService piDataTrackSuppService;
	@Override
	public BaseDao<MaterialArchives> getDao() {
		return materialArchivesDao;
	}
	/**
	 * @date 2018年1月9日
	 * @author gaoxp
	 * @param id
	 * @return
	 * 描述：根据产品中类id，获取拼接的产品分类编号，前5位
	 */
	public String findTotalCodeBySql(String id){
		return (String)materialArchivesDao.findTotalCodeBySql(id).get(0).get("total_code");
	}
	/**
	 * @date 2018年1月11日
	 * @author gaoxp
	 * @param materialArchives
	 * @return
	 * @param 其中materialArchives中materialCode中保存的是物料分类编号
	 * 描述：保存方法
	 */
	public String save(MaterialArchives materialArchives){
		materialArchives.setIs_del(false);
		materialArchives.setMaterial_code(materialArchives.getMaterial_code().toUpperCase()+getSerialNum(materialArchives.getMaterial_code()));
		if(null!=this.findByField("purchase_material_code", materialArchives.getPurchase_material_code())){
			throw new BusinessException(-1,"流水号","采办物料编码已存在,请检查！");
		};
		materialArchivesDao.create(materialArchives);
		return "保存成功！";
	}
	/**
	 * @date 2018年1月11日
	 * @author gaoxp
	 * @param materialCode
	 * @return
	 * 描述：根据物资分类编号获取流水号
	 */
	private  String getSerialNum(String subMaterialCode){
		List<Map<String, Object>> serialNumList = materialArchivesDao.findSerialNum(subMaterialCode);
		if(null!=serialNumList.get(0).get("serial_num")){
			String serialNumNow = ((String) serialNumList.get(0).get("serial_num")).toLowerCase();
			String aSerialNum = serialNumNow.substring(0, 1);
			String bSerialNum = serialNumNow.substring(1, 2);
			int aIndex=Arrays.binarySearch(Constant.letter, aSerialNum);
			int bIndex=Arrays.binarySearch(Constant.letter, bSerialNum);
			if(aIndex==Constant.letter.length-1&&bIndex==Constant.letter.length-1){
				throw new BusinessException(-1,"流水号","该分类编号下流水号已经达到最大！");
			}
			if(bIndex>=0&&bIndex<Constant.letter.length-1){
				serialNumNow= aSerialNum+Constant.letter[bIndex+1];
			} else
			if(bIndex==Constant.letter.length-1){
				serialNumNow= Constant.letter[aIndex+1]+Constant.letter[0];
			}
			return serialNumNow;
		}else{
			return "00";
		}
	}
//	/**
//	 * @date 2018年1月11日
//	 * @author gaoxp
//	 * @param materialClass
//	 * @return
//	 * 描述：根据物料分类实体查询物料档案是否有相关数据，有的话返回false，用于物料分类更新操作时的校验
//	 */
//	public boolean findByVaryId(MaterialClass materialClass){
//		return materialArchivesDao.findByVaryClass(materialClass);
//	}
	/**
	 * @date 2018年1月12日
	 * @author gaoxp
	 * @param id
	 * 描述：根据id删除物料档案，但是需要到最近的节点查看该物料档案是否被使用
	 */
	public void deleteArchives(String id){
		MaterialArchives materialArchives = findByID(id);
		StDataTrack stDataTrack = stDataTrackTableService.findByField("material_code", materialArchives.getMaterial_code());
		if(null!=stDataTrack){
			throw new BusinessException(-1,"物料编码","物料档案已经被结构数据跟踪表使用，不能删除！");
		}
		if(null!=piDataTrackSuppService.findByField("material_code", materialArchives.getMaterial_code())){
			throw new BusinessException(-1,"物料编码","物料档案已经被管线数据跟踪表使用，不能删除！");
		}
		//这里还应该添加其他专业的校验
		delete(id);
	}
	/**
	 * @date 2018年1月15日
	 * @author gaoxp
	 * @param totalCode
	 * @return
	 * 描述：根据totalCode获取物料档案
	 */
	public List<Map<String, Object>> findByTotalCode(String totalCode){
		return materialArchivesDao.findByTotalCode(totalCode);
	}
}
