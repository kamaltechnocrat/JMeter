/**
 * 
 */
package test_Suite.lib.reporting_Functions;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import test_Suite.constants.reporting_Functions.IRptFuncConst;
import test_Suite.constants.reporting_Functions.IEtlConst.EetlEFormTypes;
import test_Suite.constants.reporting_Functions.IEtlConst.EetlFoppFormTypes;

/**
 * @author mshakshouki
 *
 */
public class ETL {

	/**
	 * 
	 */
	private static Log log = LogFactory.getLog(ETL.class);
	

	private Class<Object> driverObject;
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
	private IRptFuncConst.EfieldDataTypes fieldDataTypes;
	
	private String eFormType;
	private String eFormIdent;
	private String formletIdent;
	private String eFormFieldIdent;
	private String reportingIdent;
	private String reportingStatus;
	private String reportingFieldGroup;
	private String reportingCategory;
	private String reportableFieldType;
	private String rptFieldPrefix;
	
	private String orgName;
	
	private List<LinkedHashMap<String, IRptFuncConst.EfieldDataTypes>> hashList;
	
	LinkedHashMap<String, IRptFuncConst.EfieldDataTypes> mapHash;
	
	private EetlEFormTypes eTypes;
	
	private EetlFoppFormTypes eFoppTypes;
	
	private String foppIdent;
	
	public ETL() {
	}

	@SuppressWarnings("unchecked")
	public ETL(boolean isSQL) {
		
		this.setSQLDB(isSQL);
		
		if (this.isSQLDB()) {
			try {
				this
						.setDriverObject((Class<Object>) Class
								.forName(IRptFuncConst.reportFunc_SQL_DriverObject));
				
				this.setDbUrl(getConnectionProperty("sqlURL"));
				this.setDbName(getConnectionProperty("etlSQLDatabase"));
				this.setDbOwner(getConnectionProperty("etlSQLUser"));
				this.setOwnerPwd(getConnectionProperty("etlSQLPassword"));			
				
				
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
				this.setDbOwner(getConnectionProperty("etlOraUser"));
				this.setOwnerPwd(getConnectionProperty("etlOraPassword"));				
				
				
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
	
	public String getConnectionProperty(String propertyKey) throws Exception {
		
		Properties p = new Properties();
		
		p.load(new FileInputStream(new File(
				"src/test_Suite/deployment_path.properties")));

		return p.getProperty(propertyKey);		
	}
	

	/**
	 * @return the meta
	 */
	public DatabaseMetaData getMeta() {
		return meta;
	}


	/**
	 * @param meta the meta to set
	 */
	public void setMeta(DatabaseMetaData meta) {
		this.meta = meta;
	}


	/**
	 * @return the driverObject
	 */
	public Class<Object> getDriverObject() {
		return driverObject;
	}


	/**
	 * @param driverObject the driverObject to set
	 */
	public void setDriverObject(Class<Object> driverObject) {
		this.driverObject = driverObject;
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
	 * @return the dbConnect
	 */
	public Connection getDbConnect() {
		return dbConnect;
	}


	/**
	 * @param dbConnect the dbConnect to set
	 */
	public void setDbConnect(Connection dbConnect) {
		this.dbConnect = dbConnect;
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
	 * @return the query
	 */
	public String getQuery() {
		return query;
	}


	/**
	 * @param query the query to set
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
	 * @param preStatement the preStatement to set
	 */
	public void setPreStatement(PreparedStatement preStatement) {
		this.preStatement = preStatement;
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
	 * @return the rstSet
	 */
	public ResultSet getRstSet() {
		return rstSet;
	}


	/**
	 * @param rstSet the rstSet to set
	 */
	public void setRstSet(ResultSet rstSet) {
		this.rstSet = rstSet;
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
	public void setFieldDataTypes(IRptFuncConst.EfieldDataTypes fieldDataTypes) {
		this.fieldDataTypes = fieldDataTypes;
	}


	/**
	 * @return the eFoppTypes
	 */
	public EetlFoppFormTypes getEFoppTypes() {
		return eFoppTypes;
	}

	/**
	 * @param foppTypes the eFoppTypes to set
	 */
	public void setEFoppTypes(EetlFoppFormTypes foppTypes) {
		eFoppTypes = foppTypes;
	}

	/**
	 * @return the mapHash
	 */
	public LinkedHashMap<String, IRptFuncConst.EfieldDataTypes> getMapHash() {
		return mapHash;
	}

	/**
	 * @param mapHash the mapHash to set
	 */
	public void setMapHash(
			LinkedHashMap<String, IRptFuncConst.EfieldDataTypes> mapHash) {
		this.mapHash = mapHash;
	}

	/**
	 * @return the eTypes
	 */
	public EetlEFormTypes getETypes() {
		return eTypes;
	}

	/**
	 * @param types the eTypes to set
	 */
	public void setETypes(EetlEFormTypes types) {
		eTypes = types;
	}

	/**
	 * @return the hashList
	 */
	public List<LinkedHashMap<String, IRptFuncConst.EfieldDataTypes>> getHashList() {
		return hashList;
	}

	/**
	 * @param hashList the hashList to set
	 */
	public void setHashList(
			List<LinkedHashMap<String, IRptFuncConst.EfieldDataTypes>> hashList) {
		this.hashList = hashList;
	}

	/**
	 * @return the eFormType
	 */
	public String getEFormType() {
		return eFormType;
	}

	/**
	 * @param formType the eFormType to set
	 */
	public void setEFormType(String formType) {
		eFormType = formType;
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
	 * @return the formletIdent
	 */
	public String getFormletIdent() {
		return formletIdent;
	}

	/**
	 * @param formletIdent the formletIdent to set
	 */
	public void setFormletIdent(String formletIdent) {
		this.formletIdent = formletIdent;
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
	 * @return the reportingIdent
	 */
	public String getReportingIdent() {
		return reportingIdent;
	}

	/**
	 * @param reportingIdent the reportingIdent to set
	 */
	public void setReportingIdent(String reportingIdent) {
		this.reportingIdent = reportingIdent;
	}

	/**
	 * @return the reportingStatus
	 */
	public String getReportingStatus() {
		return reportingStatus;
	}

	/**
	 * @param reportingStatus the reportingStatus to set
	 */
	public void setReportingStatus(String reportingStatus) {
		this.reportingStatus = reportingStatus;
	}

	/**
	 * @return the foppIdent
	 */
	public String getFoppIdent() {
		return foppIdent;
	}

	/**
	 * @param foppIdent the foppIdent to set
	 */
	public void setFoppIdent(String foppIdent) {
		this.foppIdent = foppIdent;
	}

	/**
	 * @return the orgName
	 */
	public String getOrgName() {
		return orgName;
	}

	/**
	 * @param orgName the orgName to set
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	/**
	 * @return the reportingFieldGroup
	 */
	public String getReportingFieldGroup() {
		return reportingFieldGroup;
	}

	/**
	 * @param reportingFieldGroup the reportingFieldGroup to set
	 */
	public void setReportingFieldGroup(String reportingFieldGroup) {
		this.reportingFieldGroup = reportingFieldGroup;
	}

	/**
	 * @return the reportingCategory
	 */
	public String getReportingCategory() {
		return reportingCategory;
	}

	/**
	 * @param reportingCategory the reportingCategory to set
	 */
	public void setReportingCategory(String reportingCategory) {
		this.reportingCategory = reportingCategory;
	}

	/**
	 * @return the reportableFieldType
	 */
	public String getReportableFieldType() {
		return reportableFieldType;
	}

	/**
	 * @param reportableFieldType the reportableFieldType to set
	 */
	public void setReportableFieldType(String reportableFieldType) {
		this.reportableFieldType = reportableFieldType;
	}

	/**
	 * @return the rptFieldPrefix
	 */
	public String getRptFieldPrefix() {
		return rptFieldPrefix;
	}

	/**
	 * @param rptFieldPrefix the rptFieldPrefix to set
	 */
	public void setRptFieldPrefix(String rptFieldPrefix) {
		this.rptFieldPrefix = rptFieldPrefix;
	}	
	
}
