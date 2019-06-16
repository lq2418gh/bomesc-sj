package dkd.paltform.base;

import java.sql.Types;

import org.hibernate.dialect.SQLServerDialect;
import org.hibernate.type.StandardBasicTypes;

public class MySQLServerDialect extends SQLServerDialect {
	public MySQLServerDialect() {
		super();
		registerColumnType(Types.VARCHAR, "nvarchar($l)");
		registerColumnType(Types.CLOB, "nvarchar(max)");
		registerHibernateType(Types.NVARCHAR, StandardBasicTypes.STRING.getName());
	}
}