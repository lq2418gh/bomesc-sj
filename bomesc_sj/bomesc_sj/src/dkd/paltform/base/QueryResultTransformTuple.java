package dkd.paltform.base;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.transform.AliasedTupleSubsetResultTransformer;

/**
 * @ClassName: QueryConditionTransformTuple
 * @Description:原生SQL列名转化类
 * @author CST-TONGLZ
 * @date 2015-10-15 下午3:45:27
 * 
 */
public class QueryResultTransformTuple extends AliasedTupleSubsetResultTransformer {
	/**
	 * @Fields serialVersionUID :
	 */
	private static final long serialVersionUID = 1L;
	public static final AliasedTupleSubsetResultTransformer INSTANCE = new QueryResultTransformTuple();

	/**
	 * 
	 */
	public Object transformTuple(Object[] tuple, String[] aliases) {
		Map<String, Object> result = new HashMap<String, Object>(tuple.length);
		for (int i = 0; i < tuple.length; i++) {
			String alias = aliases[i].toLowerCase();
			if (alias != null) {
				result.put(alias, tuple[i]);
			}
		}
		return result;
	}

	@Override
	public boolean isTransformedValueATupleElement(String[] paramArrayOfString, int paramInt) {
		return false;
	}

}
