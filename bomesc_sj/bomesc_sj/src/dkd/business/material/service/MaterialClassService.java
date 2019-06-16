package dkd.business.material.service;



import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import dkd.business.material.dao.MaterialClassDao;
import dkd.business.material.domain.MaterialClass;
import dkd.paltform.base.BusinessException;
import dkd.paltform.base.dao.BaseDao;
import dkd.paltform.base.service.BaseService;
import dkd.paltform.log.LogDescription;
import dkd.paltform.util.StringUtils;
import dkd.paltform.util.json.JSONUtil;

@Service
@Transactional
public class MaterialClassService extends BaseService<MaterialClass> {
	@Autowired
	private MaterialClassDao materialClassDao;
	@Autowired
	private MaterialArchivesService materialArchivesService;
	@Override
	public BaseDao<MaterialClass> getDao() {
		return materialClassDao;
	}
	@Transactional(propagation = Propagation.SUPPORTS)
	public String findNextClass(String parentId) {
		return JSONUtil.getJsonByTreeEntity(materialClassDao.findNextClass(parentId),StringUtils.isEmpty(parentId));
	}
	/**
	 * @Title: savaClass
	 * @Description:保存
	 * @param
	 * @author gaoxp 
	 * @return MaterialClass
	 * @throws
	 */
	@LogDescription(entityType = "物资分类信息", operaterType = "物资分类保存", entityId = "#{#materialClass.id}", description = "物资分类信息保存：#{#materialClass.code}",entityGroup="基础信息")
	@Transactional(propagation = Propagation.REQUIRED)
	public MaterialClass savaClass(MaterialClass materialClass) {
		MaterialClass parent;
		//新增分类
		if (StringUtils.isEmpty(materialClass.getId())) {
			if (materialClassDao.findByValidate(materialClass) != null) {
				throw new BusinessException(-1, "code", "父分类下该编号已经存在！");
			}
			if(materialClass.getParent() != null && materialClass.getParent().getId() != null){
				parent = materialClassDao.findByID(materialClass.getParent().getId());
				materialClass.setParent(parent);
				materialClass.setLevels(parent.getLevels()+1);
			}else{
				materialClass.setLevels(1);
				materialClass.setParent(null);
			}			
			materialClassDao.create(materialClass);
		//更新分类	
		} else {
			if (materialClassDao.findByValidate(materialClass) != null) {
				throw new BusinessException(-1, "code", "父分类该编号已经存在");
			}
			//这里增加一个校验，假如分类已经建立了相应的物料编码，则不能更新
			if(findArchivesByClassId(materialClass.getId())){
				throw new BusinessException(-1, "物料档案", "该分类已经建立了物料档案，无法更新！");
			}
			materialClassDao.update(materialClass);
		}
		return materialClass;
	}
	/**
	 * @date 2018年1月9日
	 * @author gaoxp
	 * @param id
	 * 描述：根据id删除相应分类
	 */
	public void deleteClass(String id){
		MaterialClass materialClass = new MaterialClass();
		materialClass.setId(id);
		MaterialClass sonMaterialClass = getDao().findByField("parent", materialClass);
		if(sonMaterialClass!=null){
			throw new BusinessException(-1, "parent", "该分类下有子分类，不能删除！");
		}
		if(findArchivesByClassId(id)){
			throw new BusinessException(-1, "物料档案", "该分类已经建立了物料档案，无法删除！");
		}
		//这里还应该增加一个校验，校验该分类下是否建立了物料编码
		getDao().delete(id);
	}
	/**
	 * @date 2018年1月15日
	 * @author gaoxp
	 * @param id
	 * 描述：根据物料分类编码获取物料档案
	 */
	public boolean findArchivesByClassId(String id){
		String total_code = materialArchivesService.findTotalCodeBySql(id);
		List<Map<String, Object>> materialArchivesList = materialArchivesService.findByTotalCode(total_code);
		if(null!=materialArchivesList&&materialArchivesList.size()>0){
			return true;
		}else{
			return false;
		}
	}
	 
}
