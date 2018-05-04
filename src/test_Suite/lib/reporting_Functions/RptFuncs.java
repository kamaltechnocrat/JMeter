/**
 * 
 */
package test_Suite.lib.reporting_Functions;


import java.util.*;
import java.io.File;
import java.io.FileInputStream;
import java.sql.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import test_Suite.constants.reporting_Functions.*;

/**
 * @author mshakshouki
 * 
 */
public class RptFuncs {
	private static Log log = LogFactory.getLog(RptFuncs.class);	


	private Class<Object> driverObject;
	private String dbType;
	private String dbUrl;
	private String dbName;
	private String dbOwner;
	private String ownerPwd;
	private Connection dbConnect;
	private DatabaseMetaData meta;	

	private boolean isSQLDB;
	private String query;
	private PreparedStatement preStatement;
	private Statement stmt;
	private ResultSet rstSet;

	private IRptFuncConst.EReportingFunctions functionName;
	private IRptFuncConst.EfieldDataTypes fieldDataTypes;
	private String accessData;
	private String eFormFieldIdent;
	private String eFormIdent;
	private String submissionCode;

	private String orderBy;

	private String eFormFieldValue;

	public RptFuncs() {
	};


	@SuppressWarnings("unchecked")
	public RptFuncs(boolean isSQL) throws Exception {

		isSQL = initDBConnection();
		
		if (isSQL == true) {
			try {
				this
				.setDriverObject((Class<Object>) Class
						.forName(IRptFuncConst.reportFunc_SQL_DriverObject));

				this.setDbUrl(getConnectionProperty("sqlURL"));
				this.setDbName(getConnectionProperty("sqlDatabase"));
				this.setDbOwner(getConnectionProperty("sqlUser"));
				this.setOwnerPwd(getConnectionProperty("sqlPassword"));			


			} catch (Exception e) {
				log.error("Unexpected Exception", e);

				throw new RuntimeException("Unexpected Exception", e);

			}

		} else {

			try {
				this
				.setDriverObject((Class<Object>) Class
						.forName(IRptFuncConst.reportFunc_Ora_DriverObject));

				this.setDbUrl(getConnectionProperty("oraURL"));
				this.setDbName(getConnectionProperty("oraDatabase"));
				this.setDbOwner(getConnectionProperty("oraUser"));
				this.setOwnerPwd(getConnectionProperty("oraPassword"));				


			} catch (Exception e) {
				log.error("Unexpected Exception", e);

				throw new RuntimeException("Unexpected Exception", e);
			}
		}

		try{

			this.setDbConnect(DriverManager.getConnection(
					this.dbUrl + this.dbName
					,this.dbOwner
					,this.ownerPwd));

		} catch (Exception e) {
			log.error("Unexpected Exception", e);

			throw new RuntimeException("Unexpected Exception", e);

		}

	};

	public boolean initDBConnection() throws Exception {

		 this.setDbType(getConnectionProperty("siteBase"));
		 
		 String newDbName = dbType.toLowerCase();
		 
			if (newDbName.contains("sql")){
				return true;
			}
			else 
				return false;
	}

	/**
	 * Access properties file
	 * @param propertyKey
	 * @return property Object
	 * @throws Exception
	 */
	public String getConnectionProperty(String propertyKey) throws Exception {

		Properties p = new Properties();

		p.load(new FileInputStream(new File(
				"src/test_Suite/deployment_path.properties")));

		return p.getProperty(propertyKey);

	}

	/**
	 * Pass query, DB connects, query executes in DB
	 * @param myQuery
	 * @return executed query (ResultSet type)
	 * @throws Exception
	 */
	public ResultSet executeQuery(String myQuery) throws Exception {

		this.setPreStatement(this.getDbConnect().prepareStatement(myQuery));

		ResultSet result = this.getPreStatement().executeQuery();

		return result;

		//this.setStmt(this.getDbConnect().createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY));

		//return this.getStmt().executeQuery(myQuery);
	}
	
	public int executeUpdate(String myQuery) throws Exception {

		this.setPreStatement(this.getDbConnect().prepareStatement(myQuery));

		int result = this.getPreStatement().executeUpdate();

		log.warn(result + " rows affected by query");
		return result;
	}

	/**
	 * @param driverObj
	 * @return retObj, the driver Object
	 * @throws Exception
	 */
	@SuppressWarnings({ "unused", "unchecked" })
	private Class<Object> returnDriverobject(String driverObj) throws Exception {

		Class<Object> retObj = (Class<Object>) Class.forName(driverObj);

		return retObj;
	}



	/**
	 * @return the eFormFieldValue
	 */
	public String getEFormFieldValue() {
		return eFormFieldValue;
	}

	/**
	 * @param formFieldValue the eFormFieldValue to set
	 */
	public void setEFormFieldValue(String formFieldValue) {
		eFormFieldValue = formFieldValue;
	}

	/**
	 * @return the fieldDataTypes
	 */
	public IRptFuncConst.EfieldDataTypes getFieldDataTypes() {
		return fieldDataTypes;
	}

	/**
	 * @param fieldDataTypes the fieldDataTypes to set
	 */
	public void setFieldDataTypes(
			IRptFuncConst.EfieldDataTypes fieldDataTypes) {
		this.fieldDataTypes = fieldDataTypes;
	}

	/**
	 * @return the accessData
	 */
	public String getAccessData() {
		return accessData;
	}

	/**
	 * @param accessData the accessData to set
	 */
	public void setAccessData(String accessData) {
		this.accessData = accessData;
	}

	/**
	 * @return the eFormFieldIdent
	 */
	public String getEFormFieldIdent() {
		return eFormFieldIdent;
	}

	/**
	 * @param formFieldIdent the eFormFieldIdent to set
	 */
	public void setEFormFieldIdent(String formFieldIdent) {
		eFormFieldIdent = formFieldIdent;
	}

	/**
	 * @return the eFormIdent
	 */
	public String getEFormIdent() {
		return eFormIdent;
	}

	/**
	 * @param formIdent the eFormIdent to set
	 */
	public void setEFormIdent(String formIdent) {
		eFormIdent = formIdent;
	}

	/**
	 * @return the submissionCode
	 */
	public String getSubmissionCode() {
		return submissionCode;
	}

	/**
	 * @param submissionCode the submissionCode to set
	 */
	public void setSubmissionCode(String submissionCode) {
		this.submissionCode = submissionCode;
	}

	/**
	 * @return the driverObject
	 */
	public Class<Object> getDriverObject() {
		return driverObject;
	}

	/**
	 * @param class1
	 *            the driverObject to set
	 */
	public void setDriverObject(Class<Object> class1) {
		this.driverObject = class1;
	}

	/**
	 * @return the dbConnect
	 */
	public Connection getDbConnect() {
		return dbConnect;
	}

	/**
	 * @param dbConnect
	 *            the dbConnect to set
	 */
	public void setDbConnect(Connection dbConnect) {
		this.dbConnect = dbConnect;
	}

	/**
	 * @return the meta
	 */
	public DatabaseMetaData getMeta() {
		return meta;
	}

	/**
	 * @param meta
	 *            the meta to set
	 */
	public void setMeta(DatabaseMetaData meta) {
		this.meta = meta;
	}

	/**
	 * @return the query
	 */
	public String getQuery() {
		return query;
	}

	/**
	 * @param query
	 *            the query to set
	 */
	public void setQuery(String query) {
		this.query = query;
	}

	/**
	 * @return the preStatement
	 */
	public PreparedStatement getPreStatement() {
		return preStatement;
	}

	/**
	 * @param preStatement
	 *            the preStatement to set
	 */
	public void setPreStatement(PreparedStatement preStatement) {
		this.preStatement = preStatement;
	}

	/**
	 * @return the rstSet
	 */
	public ResultSet getRstSet() {
		return rstSet;
	}

	/**
	 * @param rstSet
	 *            the rstSet to set
	 */
	public void setRstSet(ResultSet rstSet) {
		this.rstSet = rstSet;
	}	

	/**
	 * @return the functionName
	 */
	public IRptFuncConst.EReportingFunctions getFunctionName() {
		return functionName;
	}

	/**
	 * @param functionName the functionName to set
	 */
	public void setFunctionName(
			IRptFuncConst.EReportingFunctions functionName) {
		this.functionName = functionName;
	}

	/**
	 * @return the dbUrl
	 */
	public String getDbUrl() {
		return dbUrl;
	}

	/**
	 * @param dbUrl the dbUrl to set
	 */
	public void setDbUrl(String dbUrl) {
		this.dbUrl = dbUrl;
	}

	/**
	 * @return the dbName
	 */
	public String getDbName() {
		return dbName;
	}

	/**
	 * @param dbName the dbName to set
	 */
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	/**
	 * @return the dbOwner
	 */
	public String getDbOwner() {
		return dbOwner;
	}

	/**
	 * @param dbOwner the dbOwner to set
	 */
	public void setDbOwner(String dbOwner) {
		this.dbOwner = dbOwner;
	}

	/**
	 * @return the ownerPwd
	 */
	public String getOwnerPwd() {
		return ownerPwd;
	}

	/**
	 * @param ownerPwd the ownerPwd to set
	 */
	public void setOwnerPwd(String ownerPwd) {
		this.ownerPwd = ownerPwd;
	}

	/**
	 * @return the isSQLDB
	 */
	public boolean isSQLDB() {
		return isSQLDB;
	}

	/**
	 * @param isSQLDB the isSQLDB to set
	 */
	public void setSQLDB(boolean isSQLDB) {
		this.isSQLDB = isSQLDB;
	}

	/**
	 * @return the stmt
	 */
	public Statement getStmt() {
		return stmt;
	}

	/**
	 * @param stmt the stmt to set
	 */
	public void setStmt(Statement stmt) {
		this.stmt = stmt;
	}

	/**
	 * @return the orderBy
	 */
	public String getOrderBy() {
		return orderBy;
	}

	/**
	 * @param orderBy the orderBy to set
	 */
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	/**
	 * @return the dbType
	 */
	public String getDbType() {
		return dbType;
	}

	/**
	 * @param dbType the dbType to set
	 */
	public void setDbType(String dbType) {
		this.dbType = dbType;
	}

}
