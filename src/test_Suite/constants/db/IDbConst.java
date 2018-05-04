package test_Suite.constants.db;

public interface IDbConst {

	public static final String DB_URL_KEY             = "db.url";	
	public static final String DB_USER_KEY            = "db.user";
	public static final String DB_PASSWORD_KEY        = "db.password";
	
	public static final String DATA_SOURCE_NAME         = "jdbc/oracleDB";
	
	public static final String CONTEXT_LOOKUP_KEY       = "java:/comp/env";
	
	public enum dbConnMode
	{
		DB_MODE_DIRECT,    // = 0 -- Regular database connection
		DB_MODE_JNDI	   // = 1 -- JNDI data source database connection 
	}
	
	public static final String DB_CONN_INIT_PROP = "src/test_Suite/db.properties";
	
}
