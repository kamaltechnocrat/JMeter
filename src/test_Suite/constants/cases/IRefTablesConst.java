/**
 * 
 */
package test_Suite.constants.cases;
/**
 * @author mshakshouki
 *
 */


public interface IRefTablesConst {
	  
    public static final String refTableIdent_TxtFieldId = "RefTableDetailsForm:refTableIdentifier";
    
    public static final String refTableEForm_DropdownId = "RefTableDetailsForm:eForm_input";
        
    public static final String refTableOrg_DropdownId = "RefTableDetailsForm:organizationAccess_input";
    
    public static final String refTableDeleteAllContent_ChkboxId = "importReferenceTableForm:deleteAllRows_input";
        
    public static final String refTableList_Id ="g3-form:data_withoutLetterFilter_data"; 
    
    public static final String budget_Limit_Table_Id = "g3-form:data_withoutLetterFilter_data"; 
        
    public static final String submissionSchedule_TableId = "g3-form:data_withoutLetterFilter_data";
          
    public static final String budgetYear_TxtField_lbl_Id = "g3-form:filters:0:stringFilterItem";
    
	public static final String refTablePrefix = "-Gnrl-";
	
	public static final String refTableIdent = "RefTable";
	
	public static final String sf_budget_Year_TxtFieldId = "g3-form:eFormFieldList:0:textBox";
	
	public static final String sf_expense_Type_DropdownId = "g3-form:eFormFieldList:1:numericDropdown";
	
	public static final String sf_budget_Limit_TxtFieldId = "g3-form:eFormFieldList:2:textBox";
	
	public static final String sf_budget_short_TxtFieldId = "g3-form:eFormFieldList:3:textBox";
	
	public static final String df_budget_Year_TxtFieldId = "g3-form:eFormFieldList:0:textBox";
	
	public static final String df_expense_category_DropdownId = "g3-form:eFormFieldList:1:numericDropdown";
	
	public static final String dfList_expense_Type_DropdownId = "g3-form:eFormFieldList:3:numericDropdown";
	
	public static final String df_expense_Type_Id = "g3-form:eFormFieldList:2:numericDropdown";
	
	public static final String dfList_budget_Limit_TxtFieldId = "g3-form:eFormFieldList:4:textBox";
	
	public static final String df_budget_Limit_Id = "g3-form:eFormFieldList:3:textBox";
	
	public static final String df_budget_short_TxtFieldId = "g3-form:eFormFieldList:2:textBox";
	
	public static final String budget_Limits_Table_Id = "g3-form:j_id_30:data_withoutLetterFilter"; 
		
	public static final String sf_budget_Year_FieldTtl = "Yearly Budgets";
	
	public static final String sf_Expense_Type_FieldTtl = "Expense Type";
	
	public static final String sf_expense_type_dropdown = "Expense Type";
	
	public static final String sf_budget_Limit_FieldTtl = "Budget Limit";
	
	public static final String sf_short_FieldTtl = "Short";
	
    public static final String df_budget_Year_FieldTtl = "Yearly Budgets";
	
	public static final String df_expense_type_dropdown = "Expense Type";
	
	public static final String df_budget_Limit_FieldTtl = "Budget Limit";
	
	public static final String df_expense_Category_dropdown = "Expense Category";
	
	public static final String allocated_budgetTtl = "Allocated Budget";
	
	public static final String start_Date = "Start Date";
	
	public static final String end_Date = "End Date";
	
	public static final String refTableName_lbl ="Reference Table Name";
	
	public static final String refTable_TxtField_lbl ="Budget Reference Table";
	
	public static final String budgetRefTable_Name = "Budget Reference Table";
	
	public static final String refTable_BudgetYear_Lbl = "Yearly Budgets:";
	
	public static final String refTable_Expense_Category_Lbl = "Expense Category:"; 
	
	public static final String refTable_startsWith = "Starts with";
	
	public static final String refTable_Exact = "Exact";
	
	public static final String refTable_Contains = "Contains";
	
	public static final String refTable_EndsWith = "Ends with";
	
	public static final String budgetYear2017 = "2017";
	
	public static final String budgetYear2014 = "2014";
	
	public static final String budgetYear2018 = "2018";
	
	public static final String budgetYear2019 = "2019";
	
	public static final String expense_Capital = "EXPENSE_CAPITAL";
	
	public static final String capital_Equipment = "CAPITAL_EQUIPMENT";
	
	public static final String enterAnyTextFiledTtl = "Enter Any Text";
	
	public static final String textFieldData = "Submit";
	
	public static final String textFieldData1 = "-Award";
	
	public static final String textFieldData2 = "-Initial Claim";
	
	public static final String statusReady = "Ready";
	
	public static final String statusInProgress = "In Progress";
	
	public static final String openProjects = "Open Projects";
	
	public static final String versionNumber1 = "1";
	
	public static final String versionNumber2 = "2";
	
	public static final String versionNumber3 = "3";
	
	public static final String changeAmender = "Shakshouki";
	
	public static final String agreementTypeTtl = "Agreement Type";
	
	public static final String requestedEquipmentFundingTtl = "Requested Equipment Funding";
	
	public static final String operationalBFTtl = "Operational BF";
	
	public static final String requestedTotalTtl = "Requested Total";
	
    public static final String equipmentExpenseTtl = "Equipment Expense";
	
	public static final String operationalExpenseTtl = "Operational Expense";
	
	public static final String agreementTypeTxt = "Contract";
	
	public static final String equipmentTxt = "$2000.00";
	
	public static final String operationalTxt = "$3000.00";
	
	public static final String totalTxt = "$5000.00";
	
	public static final String refTableEForm = "Budget-Reference-Table";
	
	public static final String refTableDataXML = "YearlyBudgets.xml";
	
	public static final String zipFile = "RefTablesWorkFlow.zip";
	
    public static enum EReferenceTable {
		
		CAPITAL_BUILDINGS,CAPITAL_EQUIPMENT,OPERATIONS_OTHER,OPERATIONS_OVERHEAD,OPERATIONS_SALARIES,OPERATIONS_SUPPLIES,OPERATIONS_TRAVEL

	}

	public static final String refTable_Expense_Type_Lbl = "Expense Type:";
	
	public static final String[] values =  new String[] {"CAPITAL_BUILDINGS","CAPITAL_EQUIPMENT","OPERATIONS_OTHER","OPERATIONS_OVERHEAD","OPERATIONS_SALARIES","OPERATIONS_SUPPLIES","OPERATIONS_TRAVEL"};
	
	public static final String[] budgetYear = new String[] {"2014","2015","2016","2017","2018","2019","2020"};
	
	public static final String[] budgetLimit = new String[] {"$10,000.00","$22,000","125,000.00", "7,000.00", "$10,000.00", "4,500.00", "2,200.00" };
    

	
	public static final String[][] budgetYear_Fields_eList = {
		
		
		{"2014", "CAPITAL_EQUIPMENT"},
		
	    {"2015", "CAPITAL_BUILDINGS"},
	    
		{"2018", "OPERATIONS_SUPPLIES"},
		
		{"2016", "OPERATIONS_OVERHEAD"},
		
		{"2016", "OPERATIONS_SALARIES"},
		
		{"2016", "OPERATIONS_SUPPLIES"},
		
		{"2016", "OPERATIONS_TRAVEL"},
		
		{"2019", "CAPITAL_BUILDINGS"},
		
		{"2016", "OPERATIONS_OTHER"},
		
		{"2017", "CAPITAL_EQUIPMENT"},
		
		{"2018", "CAPITAL_EQUIPMENT"},
		
	};
	
	
	public static final String[][] verify_budgetYear_Fields_eList = {
		
        {"2014", "CAPITAL_EQUIPMENT","$10,000.00", "CAPITAL_EQUIPMENT", "CAPITAL_EQUIPMENT"},
        
		{"2015", "CAPITAL_BUILDINGS","$22,000.00","CAPITAL_BUILDINGS", "CAPITAL_BUILDINGS"},
		
		{"2018", "OPERATIONS_SUPPLIES","$5,000.00", "OPERATIONS_SUPPLIES", "OPERATIONS_SUPPLIES"},
		
		{"2016", "OPERATIONS_OVERHEAD","$7,000.00", "OPERATIONS_OVERHEAD", "OPERATIONS_OVERHEAD"},
		
		{"2016", "OPERATIONS_SALARIES","$125,000.00", "OPERATIONS_SALARIES", "OPERATIONS_SALARIES"},
		
		{"2016", "OPERATIONS_SUPPLIES","$4,500.00", "OPERATIONS_SUPPLIES", "OPERATIONS_SUPPLIES"},
		
		{"2016", "OPERATIONS_TRAVEL","$10,000.00", "OPERATIONS_TRAVEL", "OPERATIONS_TRAVEL"},
	
	    {"2019", "CAPITAL_BUILDINGS", "$90,000.00", "CAPITAL_BUILDINGS", "CAPITAL_BUILDINGS"},
	    
	    {"2016", "OPERATIONS_OTHER", "$2,250.00", "OPERATIONS_OTHER", "OPERATIONS_OTHER"},
	    
	    {"2017", "CAPITAL_EQUIPMENT","$12,500.00", "CAPITAL_EQUIPMENT", "CAPITAL_EQUIPMENT"},
	    
	    {"2018", "CAPITAL_EQUIPMENT","$12,500.00", "CAPITAL_EQUIPMENT", "CAPITAL_EQUIPMENT"}
	    
	    };
	
	
       public static final String[][] budgetYear_Fields_eList_Amend = {
		
		
	{"2017", "CAPITAL_EQUIPMENT"},
	
    {"2017", "CAPITAL_BUILDINGS"},
    
	{"2017", "OPERATIONS_TRAVEL"},
	
	{"2017", "OPERATIONS_SALARIES"},
	
	{"2017", "OPERATIONS_OTHER"},
	
	{"2017", "OPERATIONS_TRAVEL"},
	
	{"2017","OPERATIONS_OVERHEAD"},
	
	{"2017", "OPERATIONS_SUPPLIES"}
		
	};
	

public static final String[][] verify_budgetYear_Fields_eList_Amend = {
	
    {"2017", "CAPITAL_EQUIPMENT", "$12,500.00"},
	
    {"2017", "CAPITAL_BUILDINGS", "$23,000.00"},
    
	{"2017", "OPERATIONS_TRAVEL", "$10,500.00"},
	
	{"2017", "OPERATIONS_SALARIES", "$130,000.00"},
	
	{"2017", "OPERATIONS_OTHER", "$2,300.00"},
	
	{"2017", "OPERATIONS_TRAVEL", "$7,250.00"},
	
	{"2017","OPERATIONS_OVERHEAD", "$5,000.00"},

    };



	

	public static final String[][] yearlyLimit_Fields_eList = {
	
	{"2014","EXPENSE_CAPITAL","CAPITAL_EQUIPMENT"},
	
	{"2015", "EXPENSE_CAPITAL","CAPITAL_BUILDINGS"},

	{"2018", "EXPENSE_OPERATIONS","OPERATIONS_SUPPLIES"},
	
	{"2016", "EXPENSE_OPERATIONS", "OPERATIONS_OVERHEAD"},
	
	{"2016", "EXPENSE_OPERATIONS", "OPERATIONS_SALARIES"},
	
	{"2016", "EXPENSE_OPERATIONS",  "OPERATIONS_SUPPLIES"},
	
	{"2016", "EXPENSE_OPERATIONS", "OPERATIONS_TRAVEL"},
	
	{"2018", "EXPENSE_CAPITAL", "CAPITAL_BUILDINGS"},
	
	{"2016","EXPENSE_OPERATIONS", "OPERATIONS_OTHER"},
	
	 {"2017", "EXPENSE_CAPITAL", "CAPITAL_EQUIPMENT"},

	{"2018", "EXPENSE_CAPITAL","CAPITAL_EQUIPMENT"},
	
	};
	
	
	public static final String[][] yearlyLimit_Fields_eList_Amend = {
		
		{"2017", "EXPENSE_CAPITAL","CAPITAL_EQUIPMENT"},
		
	    {"2017","EXPENSE_CAPITAL", "CAPITAL_BUILDINGS"},
	    
		{"2017","EXPENSE_OPERATIONS", "OPERATIONS_TRAVEL"},
		
		{"2017","EXPENSE_OPERATIONS", "OPERATIONS_SALARIES"},
		
		{"2017","EXPENSE_OPERATIONS", "OPERATIONS_OTHER"},
		
		{"2017","EXPENSE_OPERATIONS", "OPERATIONS_TRAVEL"},
		
		{"2017","EXPENSE_OPERATIONS","OPERATIONS_OVERHEAD"},
		
		{"2017","EXPENSE_OPERATIONS", "OPERATIONS_SUPPLIES"}
			
		};
	
	public static final String[][] verify_yearlyLimit_Fields_eList = {
	
		    {"2014", "CAPITAL_EQUIPMENT","EXPENSE_CAPITAL","$10,000.00", "CAPITAL_EQUIPMENT"},
			
			{"2015", "CAPITAL_BUILDINGS","EXPENSE_CAPITAL","$22,000.00","CAPITAL_BUILDINGS"},
		
			{"2018", "OPERATIONS_SUPPLIES","EXPENSE_OPERATIONS","$5,000.00", "OPERATIONS_SUPPLIES"},
			
			{"2016", "OPERATIONS_OVERHEAD","EXPENSE_OPERATIONS","$7,000.00", "OPERATIONS_OVERHEAD"},
			
			{"2016", "OPERATIONS_SALARIES","EXPENSE_OPERATIONS","$125,000.00", "OPERATIONS_SALARIES"},
			
			{"2016", "OPERATIONS_SUPPLIES","EXPENSE_OPERATIONS","$4,500.00", "OPERATIONS_SUPPLIES"},
			
			{"2016", "OPERATIONS_TRAVEL","EXPENSE_OPERATIONS","$10,000.00", "OPERATIONS_TRAVEL"},
			
			{"2018","CAPITAL_BUILDINGS","EXPENSE_CAPITAL", "$90,000.00","CAPITAL_BUILDINGS"},
			
			{"2016", "OPERATIONS_OTHER","EXPENSE_OPERATIONS", "$2,250.00","OPERATIONS_OTHER"},
			
			 {"2017",  "CAPITAL_EQUIPMENT","EXPENSE_CAPITAL","$12,500.00", "CAPITAL_EQUIPMENT"},
			 
			 {"2018", "CAPITAL_EQUIPMENT","EXPENSE_CAPITAL", "$12,500.00", "CAPITAL_EQUIPMENT"}
			
			
			
	};
	
	
	public static final String[][] verify_yearlyLimit_Fields = {
		
	    {"2014","EXPENSE_CAPITAL", "CAPITAL_EQUIPMENT","$10,000.00"},
		
		{"2015","EXPENSE_CAPITAL","CAPITAL_BUILDINGS","$22,000.00"},
	
		{"2018","EXPENSE_OPERATIONS","OPERATIONS_SUPPLIES","$5,000.00"},
		
		{"2016","EXPENSE_OPERATIONS","OPERATIONS_OVERHEAD","$7,000.00"},
		
		{"2016","EXPENSE_OPERATIONS","OPERATIONS_SALARIES","$125,000.00"},
		
		{"2016","EXPENSE_OPERATIONS","OPERATIONS_SUPPLIES","$4,500.00"},
		
		{"2016","EXPENSE_OPERATIONS","OPERATIONS_TRAVEL","$10,000.00"},
		
		{"2018","EXPENSE_CAPITAL","CAPITAL_BUILDINGS", "$90,000.00"},
		
		{"2016","EXPENSE_OPERATIONS","OPERATIONS_OTHER", "$2,250.00"},
		
		 {"2017","EXPENSE_CAPITAL", "CAPITAL_EQUIPMENT","$12,500.00"},
		 
		 {"2018","EXPENSE_CAPITAL", "CAPITAL_EQUIPMENT", "$12,500.00"}
		
		
		
};
	
	
	public static final String[][] verify_yearlyLimit_Fields_eList_Amend = {
		
		{"2017","EXPENSE_CAPITAL","CAPITAL_EQUIPMENT", "$12,500.00","CAPITAL_EQUIPMENT"},
		
	    {"2017","EXPENSE_CAPITAL", "CAPITAL_BUILDINGS", "$23,000.00","CAPITAL_BUILDINGS"},
	    
		{"2017","EXPENSE_OPERATIONS", "OPERATIONS_TRAVEL", "$10,500.00","OPERATIONS_TRAVEL"},
		
		{"2017","EXPENSE_OPERATIONS", "OPERATIONS_SALARIES", "$130,000.00","OPERATIONS_SALARIES"},
		
		{"2017","EXPENSE_OPERATIONS", "OPERATIONS_OTHER", "$2,300.00","OPERATIONS_OTHER"},
		
		{"2017","EXPENSE_OPERATIONS", "OPERATIONS_TRAVEL", "$7,250.00","OPERATIONS_TRAVEL"},
		
		{"2017","EXPENSE_OPERATIONS","OPERATIONS_OVERHEAD", "$5,000.00","OPERATIONS_OVERHEAD"},
		
		
};
	
	public static final String[][] reference_Table_Row_List ={
		
		{"2019", "EXPENSE_CAPITAL", "CAPITAL_EQUIPMENT","$10,000.00","Date","Date"},
		
		{"2019","EXPENSE_CAPITAL","CAPITAL_BUILDINGS","$90,000","Date","Date"}
		
	
	};
	
	
	public static final String[] refTable =
		
		{"CAPITAL_BUILDINGS","CAPITAL_EQUIPMENT","OPERATIONS_OTHER","OPERATIONS_OVERHEAD","OPERATIONS_SALARIES","OPERATIONS_SUPPLIES","OPERATIONS_TRAVEL"};

	
	public static final String[] dbFilterData = 
			
		{"CAPITAL_BUILDINGS","CAPITAL_EQUIPMENT"};
			
			
	public static final String[] dbData =
			
		{"OPERATIONS_OTHER","OPERATIONS_OVERHEAD","OPERATIONS_SALARIES","OPERATIONS_SUPPLIES","OPERATIONS_TRAVEL"};
			
	
}
	


   


