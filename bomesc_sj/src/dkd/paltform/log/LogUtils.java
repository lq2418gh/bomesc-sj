package dkd.paltform.log;

import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dkd.paltform.authority.domain.User;
import dkd.paltform.base.BusinessException;
import dkd.paltform.dictionary.service.DictionaryService;
import dkd.paltform.log.domain.Log;
import dkd.paltform.log.service.LogService;
import dkd.paltform.util.SpringUtil;
import dkd.paltform.util.StringUtils;
import dkd.paltform.util.UUIDGenerator;
/**
 * 
 * @date 2016-10-12
 * @author wzm
 * 描述：日志工具类
 */
@Component
public class LogUtils {
	@Autowired
	private LogService logService;
	@Autowired
	private DictionaryService dictionaryService;
	private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/**
	 * 
	 * @date 2016-10-12
	 * @author wzm
	 * 描述：新旧实体比较，columns为需要比较的字段，columnNames为需要比较的字段的中文名称，若为空则去字段名称；要在update之前调用。
	 * 	若该字段为引用或集合类型，在字段后以‘-’拼上该类型需要查的字段，默认为name，如user-code，目前只支持到三级
	 * 	entityType 实体的类型,例如销售订单,部门等
	 * 	operaterType 操作类型,例如订单审核,部门新增,出货单查询
	 * 	entityGroup 大分类 --》进港、出港、基础设置、系统设置
	 * 	description 在比较之前添加的操作详情
	 *  dicCodes 数据字典替换对应code key:字段名 value:parentCode可以为空
	 */
	@SuppressWarnings("rawtypes")
	public void insertLogByUpdate(Object oldEntity,Object newEntity,String[] columns,String[] columnNames
			,String entityType,String operaterType,String entityGroup,String description,Map<String,String> dicCodes,String doc_num){
		StringBuffer sb = new StringBuffer();
		if(oldEntity.getClass().getName().equals(newEntity.getClass().getName())){
			if(columns != null && columns.length != 0){
				//字段名、方法名、旧字段值、新字段值、引用类型方法名。
				String column,methodName,oldValue,newValue,columnMethodName;
				String[] columnChildren;
				//新旧字段值
				Object oldTemp,newTemp;
				//方法，若该字段为引用类型或集合，methodTemp存放引用类型中的方法。
				Method method,methodTemp;
				//若中文名称为空，则用列名代替
				if(columnNames == null || columnNames.length == 0){
					columnNames = columns;
				}
				try{
					//循环字段数组进行比较
					for(int i = 0;i < columns.length;i++){
						columnChildren = columns[i].split("-");
						column = columnChildren[0];	
						if(columnChildren.length > 1){
							columnMethodName = "get" + columnChildren[1].substring(0,1).toUpperCase() + columnChildren[1].substring(1);
						}else{
							//属性所取字段值默认为name
							columnMethodName = "getName";						
						}
						methodName = "get" + column.substring(0,1).toUpperCase() + column.substring(1);
						method = oldEntity.getClass().getMethod(methodName);
						//旧值
						oldTemp = method.invoke(oldEntity);
						if(oldTemp instanceof Boolean){
							oldValue = (boolean)oldTemp ? "是" : "否";
						}else if(oldTemp instanceof Date){
							oldValue = df.format((Date)oldTemp);
						}else if(oldTemp instanceof Collection<?>){
							Collection c = (Collection)oldTemp;
							StringBuffer tempSb = new StringBuffer("");
							for(Object obj : c){
								methodTemp = obj.getClass().getMethod(columnMethodName);
								if(columnChildren.length > 2){
									Object o = methodTemp.invoke(obj);
									if(o != null){
										String childrenMethodName = "get" + columnChildren[2].substring(0,1).toUpperCase() + columnChildren[2].substring(1);
										methodTemp = o.getClass().getMethod(childrenMethodName);
										tempSb.append((methodTemp.invoke(o) == null ? "" : methodTemp.invoke(o)).toString() + ",");										
									}else{
										tempSb.append(",");
									}
								}else{
									tempSb.append((methodTemp.invoke(obj) == null ? "" : methodTemp.invoke(obj)).toString() + ",");									
								}
							}
							if(tempSb.length() > 1){
								oldValue = tempSb.substring(0,tempSb.length() - 1);
							}else{
								oldValue = "";
							}
						}else{
							if(oldTemp == null){
								oldValue = "";
							}else{
								//若该字段为其他实体引用，则取name字段。
								try{
									methodTemp = oldTemp.getClass().getMethod(columnMethodName);
									if(columnChildren.length > 2){
										Object o = methodTemp.invoke(oldTemp);
										if(o != null){
											String childrenMethodName = "get" + columnChildren[2].substring(0,1).toUpperCase() + columnChildren[2].substring(1);
											methodTemp = o.getClass().getMethod(childrenMethodName);
											oldValue = (methodTemp.invoke(o) == null ? "" : methodTemp.invoke(o)).toString();										
										}else{
											oldValue = "";
										}
									}else{
										oldValue = (methodTemp.invoke(oldTemp) == null ? "" : methodTemp.invoke(oldTemp)).toString();									
									}
								}catch(NoSuchMethodException e){
									oldValue = oldTemp.toString();
								}
							}
						}
						//新值
						newTemp = method.invoke(newEntity);
						if(newTemp instanceof Boolean){
							newValue = (boolean)newTemp ? "是" : "否";
						}else if(newTemp instanceof Date){
							newValue = df.format((Date)newTemp);
						}else if(newTemp instanceof Collection<?>){
							Collection c = (Collection)newTemp;
							StringBuffer tempSb = new StringBuffer("");
							for(Object obj : c){
								methodTemp = obj.getClass().getMethod(columnMethodName);
								if(columnChildren.length > 2){
									Object o = methodTemp.invoke(obj);
									if(o != null){
										String childrenMethodName = "get" + columnChildren[2].substring(0,1).toUpperCase() + columnChildren[2].substring(1);
										methodTemp = o.getClass().getMethod(childrenMethodName);
										tempSb.append((methodTemp.invoke(o) == null ? "" : methodTemp.invoke(o)).toString() + ",");										
									}else{
										tempSb.append(",");
									}
								}else{
									tempSb.append((methodTemp.invoke(obj) == null ? "" : methodTemp.invoke(obj)).toString() + ",");									
								}
							}
							if(tempSb.length() > 1){
								newValue = tempSb.substring(0,tempSb.length() - 1);
							}else{
								newValue = "";
							}
						}else{
							if(newTemp == null){
								newValue = "";
							}else{
								//若该字段为其他实体引用，则取name字段。
								try{
									methodTemp = newTemp.getClass().getMethod(columnMethodName);
									if(columnChildren.length > 2){
										Object o = methodTemp.invoke(newTemp);
										if(o != null){
											String childrenMethodName = "get" + columnChildren[2].substring(0,1).toUpperCase() + columnChildren[2].substring(1);
											methodTemp = o.getClass().getMethod(childrenMethodName);
											newValue = (methodTemp.invoke(o) == null ? "" : methodTemp.invoke(o)).toString();										
										}else{
											newValue = "";
										}
									}else{
										newValue = (methodTemp.invoke(newTemp) == null ? "" : methodTemp.invoke(newTemp)).toString();									
									}
								}catch(NoSuchMethodException e){
									newValue = newTemp.toString();								
								}
							}
						}
						if(dicCodes != null){
							for (String key : dicCodes.keySet()) {
								if(column.equals(key)){
									if(!StringUtils.isEmpty(oldValue)){
										oldValue = dictionaryService.findByID(oldValue).getName();	
									}
									if(!StringUtils.isEmpty(newValue)){
										newValue = dictionaryService.findByID(newValue).getName();										
									}
									break;
								}
							}
						}
						if(!oldValue.equals(newValue)){
							sb.append((columnNames.length > i ? columnNames[i] : columns[i]) + "：‘" + oldValue + "’→‘" + newValue + "’、");
						}
					}
					//插入日志
					if(StringUtils.isEmpty(description)){
						description = "详细操作如下：";
					}else{
						description += "，详细操作如下：";
					}
					if(sb.length() > 0){
						description += sb.substring(0, sb.length() - 1) + "。";
					}else{
						description += "该单据未发生变化。";
					}
					method = oldEntity.getClass().getMethod("getId");
					insertLog(entityType,operaterType,description,method.invoke(oldEntity).toString(),entityGroup,doc_num);
				}catch(Exception e){
					e.printStackTrace();
					throw new BusinessException(-1, "", "日志插入错误，读取数据错误！");		
				}
			}
		}else{
			throw new BusinessException(-1, "", "日志插入错误，类型不匹配！");
		}
	}
	/**
	 * 
	 * @date 2016-10-12
	 * @author wzm
	 * 描述：没有中文名称的比较方法，应该用不到吧。。。。
	 */
	public void insertLogByUpdate(Object oldEntity,Object newEntity,String[] columns,String entityType,String operaterType,String entityGroup,Map<String,String> dicCodes,String description,String doc_num){
		insertLogByUpdate(oldEntity,newEntity,columns,null,entityType,operaterType,entityGroup,description,dicCodes, doc_num);
	}
	/**
	 * 
	 * @date 2016-10-12
	 * @author wzm
	 * 描述：新实体插入，columns为需要比较的字段，columnNames为需要比较的字段的中文名称，若为空则去字段名称；要在create之后调用。
	 *  若该字段为引用或集合类型，在字段后以‘-’拼上该类型需要查的字段，默认为name，如user-code，目前只支持到三级
	 * 	entityType 实体的类型,例如销售订单,部门等
	 * 	operaterType 操作类型,例如订单审核,部门新增,出货单查询
	 * 	entityGroup 大分类 --》进港、出港、基础设置、系统设置
	 * 	description 在插入明细之前添加的操作详情
	 * 	dicCodes 数据字典替换对应code key:字段名 value:parentCode可以为空
	 */
	@SuppressWarnings("rawtypes")
	public void insertLogByCreate(Object entity,String[] columns,String[] columnNames
			,String entityType,String operaterType,String entityGroup,String description,Map<String,String> dicCodes,String doc_num){
		StringBuffer sb = new StringBuffer();
		if(columns != null && columns.length != 0){
			//字段名、方法名、值、字段方法名。
			String column,methodName,value,columnMethodName;
			String[] columnChildren;
			//方法名，若字段为引用类型或集合，methodTemp存放引用类型的方法
			Method method,methodTemp;
			//字段值
			Object tempObj;
			if(columnNames == null || columnNames.length == 0){
				columnNames = columns;
			}
			try{
				for(int i = 0;i < columns.length;i++){
					columnChildren = columns[i].split("-");
					column = columnChildren[0];	
					if(columnChildren.length > 1){
						columnMethodName = "get" + columnChildren[1].substring(0,1).toUpperCase() + columnChildren[1].substring(1);
					}else{
						//属性所取字段值默认为name
						columnMethodName = "getName";						
					}
					
					methodName = "get" + column.substring(0,1).toUpperCase() + column.substring(1);
					method = entity.getClass().getMethod(methodName);
					tempObj = method.invoke(entity);
					if(tempObj instanceof Boolean){
						value = (boolean)tempObj ? "是" : "否";
					}else if(tempObj instanceof Date){
						value = df.format((Date)tempObj);
					}else if(tempObj instanceof Collection<?>){
						Collection c = (Collection)tempObj;
						StringBuffer tempSb = new StringBuffer("");
						for(Object obj : c){
							methodTemp = obj.getClass().getMethod(columnMethodName);
							if(columnChildren.length > 2){
								Object o = methodTemp.invoke(obj);
								if(o != null){
									String childrenMethodName = "get" + columnChildren[2].substring(0,1).toUpperCase() + columnChildren[2].substring(1);
									methodTemp = o.getClass().getMethod(childrenMethodName);
									tempSb.append((methodTemp.invoke(o) == null ? "" : methodTemp.invoke(o)).toString() + ",");										
								}else{
									tempSb.append(",");
								}
							}else{
								tempSb.append((methodTemp.invoke(obj) == null ? "" : methodTemp.invoke(obj)).toString() + ",");									
							}
						}
						if(tempSb.length() > 1){
							value = tempSb.substring(0,tempSb.length() - 1);
						}else{
							value = "";
						}
					}else{
						if(tempObj == null){
							value = "";
						}else{
							//若该字段为其他实体引用，则取name字段。
							try{
								methodTemp = tempObj.getClass().getMethod(columnMethodName);
								if(columnChildren.length > 2){
									Object o = methodTemp.invoke(tempObj);
									if(o != null){
										String childrenMethodName = "get" + columnChildren[2].substring(0,1).toUpperCase() + columnChildren[2].substring(1);
										methodTemp = o.getClass().getMethod(childrenMethodName);
										value = (methodTemp.invoke(o) == null ? "" : methodTemp.invoke(o)).toString();										
									}else{
										value = "";
									}
								}else{
									value = (methodTemp.invoke(tempObj) == null ? "" : methodTemp.invoke(tempObj)).toString();									
								}
							}catch(NoSuchMethodException e){
								value = tempObj.toString();
							}
						}
					}
					if(dicCodes != null){
						for (String key : dicCodes.keySet()) {
							if(column.equals(key)){
								if(!StringUtils.isEmpty(value)){
									value = dictionaryService.findByID(value).getName();									
								}
								break;
							}
						}
					}
					sb.append((columnNames.length > i ? columnNames[i] : columns[i]) + "：‘" + value + "’、");
				}
				
				//插入日志
				if(StringUtils.isEmpty(description)){
					description = "";	
				}else{
					description += "，";
				}
				if(sb.length() > 0){
					description += "详细操作如下：" + sb.substring(0, sb.length() - 1) + "。";
				}
				method = entity.getClass().getMethod("getId");
				insertLog(entityType,operaterType,description,method.invoke(entity).toString(),entityGroup,doc_num);
			}catch(Exception e){
				e.printStackTrace();
				throw new BusinessException(-1, "", "日志插入错误，读取数据错误！");		
			}
		}
	}
	/**
	 * 
	 * @date 2016-10-12
	 * @author wzm
	 * 描述：没有中文名称的插入方法，应该用不到吧。。。。
	 */
	public void insertLogByCreate(Object entity,String[] columns,String entityType,String operaterType,String entityGroup,Map<String,String> dicCodes,String description,String doc_num){
		insertLogByCreate(entity,columns,null,entityType,operaterType,entityGroup,description,dicCodes,doc_num);
	}
	/**
	 * 
	 * @date 2016-10-17
	 * @author wzm
	 * 描述：单独插入日志
	 */
	public void insertLog(String entityType,String operaterType,String description,String entityId,String entityGroup,String entity_code){
		User currentUser = SpringUtil.getCurrentUser();
		if(currentUser != null){
			String address = SpringUtil.getRequest().getRemoteAddr();
			address = address.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : address;
			String username = currentUser.getName();
			Log log = new Log();
			log.setId(UUIDGenerator.getUUID());
			log.setEntity_type(entityType);
			log.setIp_address(address);
			log.setOperater_type(operaterType);
			log.setOperate_time(new Date());
			log.setUser_uame(username);
			log.setDescription(description);
			log.setEntity_id(entityId);
			log.setEntity_group(entityGroup);
			log.setEntity_code(entity_code);
			logService.create(log);
		}
	}
}