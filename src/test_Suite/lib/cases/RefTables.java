/**
 * 
 */
package test_Suite.lib.cases;

import org.apache.commons.lang.RandomStringUtils;

import test_Suite.constants.cases.IRefTablesConst;

/**
 * @author mshakshouki
 *
 */
public class RefTables implements IRefTablesConst {
	
	//private static Log log = LogFactory.getLog(RefTables.class);
	//private IE ie;
	private boolean newRefTable;
	private String refTableLetter;
	private String refTablePrefix;
	private String refTableSuffix;
	private String refTableIdent;
	private String refTableFullIdent;
	private String refTableEFormFullIdent;
	private String refTableOrgAccess;
	private String refTableKeyField;
	private String refTableDataFilePath;	
	
	
	/**
	 * 
	 */
	public RefTables() {
		super();
		this.refTableLetter = RandomStringUtils.randomAlphabetic(5);		
	}	
	
	/**
	 * @param newRefTable
	 * @param refTableEFormFullName
	 * @param refTableOrgAccess
	 * @param refTableKeyField
	 */
	public RefTables(boolean newRefTable, String refTableEFormFullIdent,
			String refTableOrgAccess, String refTableKeyField) {
		
		super();		
		this.refTableLetter = RandomStringUtils.randomAlphabetic(5);
		this.newRefTable = newRefTable;
		this.refTableEFormFullIdent = refTableEFormFullIdent;
		this.refTableOrgAccess = refTableOrgAccess;
		this.refTableKeyField = refTableKeyField;	
		
		this.refTableFullIdent = this.refTableLetter.concat(" ").concat(this.refTableEFormFullIdent.replace("-", " "));	
	}


	/**
	 * @param newRefTable
	 * @param refTablePrefix
	 * @param refTableSuffix
	 * @param refTableIdent
	 * @param refTableEFormFullName
	 * @param refTableOrgAccess
	 * @param refTableKeyField
	 */
	public RefTables(boolean newRefTable, String refTablePrefix,
			String refTableSuffix, String refTableIdent,
			String refTableEFormFullIdent, String refTableOrgAccess,
			String refTableKeyField) {
		
		super();
		this.newRefTable = newRefTable;
		this.refTablePrefix = refTablePrefix;
		this.refTableSuffix = refTableSuffix;
		this.refTableIdent = refTableIdent;
		this.refTableEFormFullIdent = refTableEFormFullIdent;
		this.refTableOrgAccess = refTableOrgAccess;
		this.refTableKeyField = refTableKeyField;
		
		this.refTableLetter = RandomStringUtils.randomAlphabetic(5);
		
		this.refTableFullIdent = this.refTableLetter.concat(" ").concat(this.refTableEFormFullIdent.replace("-", " "));
	}
	
	public void initializeGnrl()
	{
		this.refTableLetter = RandomStringUtils.randomAlphabetic(5);
		this.refTablePrefix = IRefTablesConst.refTablePrefix;
		this.refTableIdent =  IRefTablesConst.refTableIdent;
		this.refTableSuffix = "";
		this.refTableFullIdent = this.refTableLetter.concat(this.refTablePrefix)
				.concat(this.refTableIdent).concat(this.refTableSuffix);
	}
	
	public void initialize()
	{
		this.refTableFullIdent = this.refTableLetter.concat(this.refTablePrefix)
				.concat(this.refTableIdent).concat(this.refTableSuffix);
		
	}
	
	/**
	 * @return the newRefTable
	 */
	public boolean isNewRefTable() {
		return newRefTable;
	}
	/**
	 * @param newRefTable the newRefTable to set
	 */
	public void setNewRefTable(boolean newRefTable) {
		this.newRefTable = newRefTable;
	}
	/**
	 * @return the refTableLetter
	 */
	public String getRefTableLetter() {
		return refTableLetter;
	}
	/**
	 * @param refTableLetter the refTableLetter to set
	 */
	public void setRefTableLetter(String refTableLetter) {
		this.refTableLetter = refTableLetter;
	}
	/**
	 * @return the refTablePrefix
	 */
	public String getRefTablePrefix() {
		return refTablePrefix;
	}
	/**
	 * @param refTablePrefix the refTablePrefix to set
	 */
	public void setRefTablePrefix(String refTablePrefix) {
		this.refTablePrefix = refTablePrefix;
	}
	/**
	 * @return the refTableSuffix
	 */
	public String getRefTableSuffix() {
		return refTableSuffix;
	}
	/**
	 * @param refTableSuffix the refTableSuffix to set
	 */
	public void setRefTableSuffix(String refTableSuffix) {
		this.refTableSuffix = refTableSuffix;
	}
	/**
	 * @return the refTableIdent
	 */
	public String getRefTableIdent() {
		return refTableIdent;
	}
	/**
	 * @param refTableIdent the refTableIdent to set
	 */
	public void setRefTableIdent(String refTableIdent) {
		this.refTableIdent = refTableIdent;
	}
	/**
	 * @return the refTableFullIdent
	 */
	public String getRefTableFullIdent() {
		return refTableFullIdent;
	}
	/**
	 * @param refTableFullIdent the refTableFullIdent to set
	 */
	public void setRefTableFullIdent(String refTableFullIdent) {
		this.refTableFullIdent = refTableFullIdent;
	}
	/**
	 * @return the refTableEFormFullName
	 */
	public String getRefTableEFormFullIdent() {
		return refTableEFormFullIdent;
	}
	/**
	 * @param refTableEFormFullName the refTableEFormFullName to set
	 */
	public void setRefTableEFormFullIdent(String refTableEFormFullIdent) {
		this.refTableEFormFullIdent = refTableEFormFullIdent;
	}
	/**
	 * @return the refTableOrgAccess
	 */
	public String getRefTableOrgAccess() {
		return refTableOrgAccess;
	}
	/**
	 * @param refTableOrgAccess the refTableOrgAccess to set
	 */
	public void setRefTableOrgAccess(String refTableOrgAccess) {
		this.refTableOrgAccess = refTableOrgAccess;
	}
	/**
	 * @return the refTableKeyField
	 */
	public String getRefTableKeyField() {
		return refTableKeyField;
	}
	/**
	 * @param refTableKeyField the refTableKeyField to set
	 */
	public void setRefTableKeyField(String refTableKeyField) {
		this.refTableKeyField = refTableKeyField;
	}
	/**
	 * @return the refTableDataFilePath
	 */
	public String getRefTableDataFilePath() {
		return refTableDataFilePath;
	}
	/**
	 * @param refTableDataFilePath the refTableDataFilePath to set
	 */
	public void setRefTableDataFilePath(String refTableDataFilePath) {
		this.refTableDataFilePath = refTableDataFilePath;
	}
	
	

	
}
