package test_Suite.utils.reporting_Functions;

import java.io.File;
import java.io.FileInputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.Assert;

import test_Suite.constants.reporting_Functions.IRptFuncConst;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.workflow.IProjectsConst;
import test_Suite.lib.reporting_Functions.RptFuncs;
import test_Suite.lib.workflow.FOProject;
import test_Suite.lib.workflow.Project;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.eForms.EFormsUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.IEUtil;
import watij.runtime.ie.IE;

/**
 * @author mshakshouki
 * 
 */
public class RptFuncUtil {

	private static Log log = LogFactory.getLog(RptFuncUtil.class);

	/*
	 * public static enum EfieldDataTypes { formlet, stringTP, dateTP,
	 * booleanTP, numericTP }
	 */


	public static LinkedHashMap<String, IRptFuncConst.EfieldDataTypes> oneFormletHT;

	public static LinkedHashMap<String, IRptFuncConst.EfieldDataTypes> noPropHT;
	public static LinkedHashMap<String, IRptFuncConst.EfieldDataTypes> dropdownHT;
	public static LinkedHashMap<String, IRptFuncConst.EfieldDataTypes> dropdownLookupHT;
	public static LinkedHashMap<String, IRptFuncConst.EfieldDataTypes> typePropHT;
	public static LinkedHashMap<String, IRptFuncConst.EfieldDataTypes> minAndMaxHT;
	public static LinkedHashMap<String, IRptFuncConst.EfieldDataTypes> eListHT;
	public static LinkedHashMap<String, IRptFuncConst.EfieldDataTypes> eListDropdownHT;
	public static LinkedHashMap<String, IRptFuncConst.EfieldDataTypes> eListDataGridHT;

	public static List<LinkedHashMap<String, IRptFuncConst.EfieldDataTypes>> hashList;
	static Set<Map.Entry<String, IRptFuncConst.EfieldDataTypes>> set;

	public static IRptFuncConst.EfieldDataTypes fieldType;
	public static String formletName;
	public static String fieldName;
	public static String projectName;
	public static String accessData;
	public static String eFormName;
	public static String subCode;
	public static String orderBy;

	public static Integer retInteger;
	public static Integer eListArrIndex;

	public static String[] eFormFieldValues;

	public static ArrayList<String[][]> FormletFieldsValues;

	static FOProject foProj;

	static ResultSet rstSet;

;

	public static ResultSet rsUpdate;
	public static ResultSet rsResult;

	public static List<LinkedHashMap<String, IRptFuncConst.EfieldDataTypes>> initLinkedHashMapFormlet()
			throws Exception {

		hashList = new ArrayList<LinkedHashMap<String, IRptFuncConst.EfieldDataTypes>>();

		hashList.add(0, oneFormletHT);

		return hashList;
	}

	public static List<LinkedHashMap<String, IRptFuncConst.EfieldDataTypes>> initLinkedHashMap()
			throws Exception {

		noPropHT = new LinkedHashMap<String, IRptFuncConst.EfieldDataTypes>();
		noPropHT.put("No-Properties-eFormFields",
				IRptFuncConst.EfieldDataTypes.formlet);
		noPropHT.put("Checkbox",
				IRptFuncConst.EfieldDataTypes.booleanTP);
		noPropHT.put("Date", IRptFuncConst.EfieldDataTypes.dateTP);
		noPropHT.put("EIN Number",
				IRptFuncConst.EfieldDataTypes.stringTP);
		noPropHT.put("Email Address",
				IRptFuncConst.EfieldDataTypes.stringTP);
		noPropHT.put("Web Address",
				IRptFuncConst.EfieldDataTypes.stringTP);

		dropdownHT = new LinkedHashMap<String, IRptFuncConst.EfieldDataTypes>();
		dropdownHT.put("Dropdown-eFormFields",
				IRptFuncConst.EfieldDataTypes.formlet);
		dropdownHT.put("Dropdown",
				IRptFuncConst.EfieldDataTypes.numericTP);
		dropdownHT.put("Child Dropdown",
				IRptFuncConst.EfieldDataTypes.numericTP);
		dropdownHT.put("Dropdown From List",
				IRptFuncConst.EfieldDataTypes.numericTP);

		dropdownLookupHT = new LinkedHashMap<String, IRptFuncConst.EfieldDataTypes>();
		dropdownLookupHT.put("Dropdown-Lookup-Property-eFormFields",
				IRptFuncConst.EfieldDataTypes.formlet);
		dropdownLookupHT.put("Many-to-Many",
				IRptFuncConst.EfieldDataTypes.numericTP);
		dropdownLookupHT.put("Radio Button",
				IRptFuncConst.EfieldDataTypes.numericTP);

		typePropHT = new LinkedHashMap<String, IRptFuncConst.EfieldDataTypes>();
		typePropHT.put("Type-Properties-eFormFields",
				IRptFuncConst.EfieldDataTypes.formlet);
		typePropHT.put("Phone Number",
				IRptFuncConst.EfieldDataTypes.stringTP);
		typePropHT.put("Postal Code",
				IRptFuncConst.EfieldDataTypes.stringTP);

		minAndMaxHT = new LinkedHashMap<String, IRptFuncConst.EfieldDataTypes>();
		minAndMaxHT.put("Min-And-Max-Properties-eFormFields",
				IRptFuncConst.EfieldDataTypes.formlet);
		minAndMaxHT.put("Long Text",
				IRptFuncConst.EfieldDataTypes.stringTP);
		minAndMaxHT.put("Numeric",
				IRptFuncConst.EfieldDataTypes.numericTP);
		minAndMaxHT.put("Password",
				IRptFuncConst.EfieldDataTypes.stringTP);
		minAndMaxHT.put("Short Text",
				IRptFuncConst.EfieldDataTypes.stringTP);
		minAndMaxHT.put("Numeric2",
				IRptFuncConst.EfieldDataTypes.numericTP);
		minAndMaxHT.put("Review Score",
				IRptFuncConst.EfieldDataTypes.numericTP);

		eListHT = new LinkedHashMap<String, IRptFuncConst.EfieldDataTypes>();
		eListHT.put("eList", IRptFuncConst.EfieldDataTypes.formlet);
		eListHT.put("FName", IRptFuncConst.EfieldDataTypes.stringTP);
		eListHT.put("LName", IRptFuncConst.EfieldDataTypes.stringTP);
		eListHT.put("Street Num",
				IRptFuncConst.EfieldDataTypes.numericTP);
		eListHT.put("Street Name",
				IRptFuncConst.EfieldDataTypes.stringTP);
		eListHT.put("PNumber", IRptFuncConst.EfieldDataTypes.stringTP);
		eListHT.put("PCode", IRptFuncConst.EfieldDataTypes.stringTP);
		eListHT
		.put("Province",
				IRptFuncConst.EfieldDataTypes.numericTP);
		eListHT.put("countries",
				IRptFuncConst.EfieldDataTypes.numericTP);
		eListHT.put("contactInfo",
				IRptFuncConst.EfieldDataTypes.stringTP);

		eListDropdownHT = new LinkedHashMap<String, IRptFuncConst.EfieldDataTypes>();
		eListDropdownHT.put("eList Dropdown",
				IRptFuncConst.EfieldDataTypes.formlet);
		eListDropdownHT.put("Parent DD",
				IRptFuncConst.EfieldDataTypes.numericTP);
		eListDropdownHT.put("Child DD",
				IRptFuncConst.EfieldDataTypes.numericTP);
		eListDropdownHT.put("DD From List",
				IRptFuncConst.EfieldDataTypes.numericTP);

		eListDataGridHT = new LinkedHashMap<String, IRptFuncConst.EfieldDataTypes>();
		eListDataGridHT.put("eList Data Grid",
				IRptFuncConst.EfieldDataTypes.formlet);
		eListDataGridHT.put("eList DG",
				IRptFuncConst.EfieldDataTypes.stringTP);

		hashList = new ArrayList<LinkedHashMap<String, IRptFuncConst.EfieldDataTypes>>();

		hashList.add(0, noPropHT);
		hashList.add(1, dropdownHT);
		hashList.add(2, dropdownLookupHT);
		hashList.add(3, typePropHT);
		hashList.add(4, minAndMaxHT);
		hashList.add(5, eListHT);
		hashList.add(6, eListDropdownHT);
		hashList.add(7, eListDataGridHT);

		return hashList;
	}

	public static void runTheQueries(RptFuncs reportFunc)
			throws Exception {

		try {

			log.warn("Function Name:*************** "
					+ reportFunc.getFunctionName() + " ***************");
			log.warn("EForm Name:.................. "
					+ reportFunc.getEFormIdent() + " ...............");

			for (LinkedHashMap<String, IRptFuncConst.EfieldDataTypes> lhm : hashList) {

				set = lhm.entrySet();

				for (Map.Entry<String, IRptFuncConst.EfieldDataTypes> entry : set) {

					reportFunc.setOrderBy(IRptFuncConst.reportFunc_OrderBy_IdAndField);
					orderBy = reportFunc.getOrderBy();

					reportFunc.setEFormFieldIdent(entry.getKey());					
					reportFunc.setFieldDataTypes(entry.getValue());

					eListArrIndex = 0;

					if (!reportFunc.isSQLDB()) {

						setAccessData(reportFunc);
					}					

					if (reportFunc.getFieldDataTypes() == IRptFuncConst.EfieldDataTypes.formlet) {

						formletName = reportFunc.getEFormFieldIdent();
						log.warn("Formlet Name:----"
								+ reportFunc.getEFormFieldIdent() + "----");

						retInteger = -1;

						setOriginalData(reportFunc);

					} else {

						initializeQuery(reportFunc);

						log.warn("Field Name: "
								+ reportFunc.getEFormFieldIdent()
								+ ", Its Type: "
								+ reportFunc.getFieldDataTypes());

						rstSet = reportFunc.executeQuery(reportFunc.getQuery());

						displayResultSet(rstSet, reportFunc);
					}
				}
				log.warn("---------End of Formlet--------");
			}

			//dbConnect = null;

		} catch (Exception e) {
			log.error("Unexpected Exception", e);

			throw new RuntimeException("Unexpected Exception", e);
		}
	}

	
	public static void run_DataArchive_Queries(RptFuncs reportFunc,FOProject foProj)
			throws Exception {

		try {
			initializeQuery_DataArchive(reportFunc,foProj);

			rstSet = reportFunc.executeQuery(reportFunc.getQuery());

			displayResultSet(rstSet, reportFunc);


		} catch (Exception e) {
			log.error("Unexpected Exception", e);

			throw new RuntimeException("Unexpected Exception", e);
		}
	}


	public static void setOriginalData(RptFuncs reportFunc)
			throws Exception {

		String formletIdent = reportFunc.getEFormFieldIdent();

		FormletFieldsValues = new ArrayList<String[][]>();

		eListArrIndex = 0;

		if (formletIdent == "No-Properties-eFormFields") {

			FormletFieldsValues.add(0,
					IRptFuncConst.reportFunc_NoPropertiesFormlet);

		} else if (formletIdent == "Type-Properties-eFormFields") {

			FormletFieldsValues.add(0,
					IRptFuncConst.reportFunc_TypesPropertiesFormlet);

		} else if (formletIdent == "Min-And-Max-Properties-eFormFields") {

			FormletFieldsValues.add(0,
					IRptFuncConst.reportFunc_MinAndMaxFormlet);

		} else if (formletIdent == "eList") {

			FormletFieldsValues.add(0,
					IRptFuncConst.reportFunc_eListFormlet);

		} else if (formletIdent == "eList Dropdown") {

			FormletFieldsValues.add(0,
					IRptFuncConst.reportFunc_eListDropdownFormlet);

		} else if (formletIdent == "eList Data Grid") {

			FormletFieldsValues.add(0,
					IRptFuncConst.reportFunc_eListDataGridFormlet);
		} else if (formletIdent == "Dropdown-Lookup-Properties-eFormFields") {

			FormletFieldsValues.add(0,
					IRptFuncConst.reportFunc_LookupDropdownFormlet);

		} else if (formletIdent == "Dropdown-eFormFields") {

			FormletFieldsValues.add(0,
					IRptFuncConst.reportFunc_DropdownFormlet);			
		}		
	}

	public static void setAccessData(RptFuncs reportingFunc)
			throws Exception {			

		switch (reportingFunc.getFieldDataTypes()) {

		case stringTP:

			if (reportingFunc.getEFormFieldIdent() == "Long Text") {
				reportingFunc
				.setAccessData(IRptFuncConst.reportFunc_Ora_AnyData_AccessNClob);
				reportingFunc
				.setOrderBy(IRptFuncConst.reportFunc_OrderBy_Id);
				orderBy = reportingFunc.getOrderBy();
			}

			else
				reportingFunc
				.setAccessData(IRptFuncConst.reportFunc_Ora_AnyData_AccessVarchar2);

			break;
		case dateTP:

			reportingFunc
			.setAccessData(IRptFuncConst.reportFunc_Ora_AnyData_AccessDate);

			break;
		case booleanTP:

			reportingFunc
			.setAccessData(IRptFuncConst.reportFunc_Ora_AnyData_AccessNumber);

			break;
		case numericTP:

			reportingFunc
			.setAccessData(IRptFuncConst.reportFunc_Ora_AnyData_AccessNumber);

			break;

		default:
			break;
		}
	}

	public static void displayResultSet(ResultSet rstSet,
			RptFuncs reportingFunc) throws Exception {


		String retMessage;

		switch (reportingFunc.getFieldDataTypes()) {

		case stringTP: // String

			retInteger += 1;

			while (rstSet.next()) {

				retMessage = "String";

				logOriginalValues(retInteger);

				logResult(rstSet, rstSet.getString("the_Field"), retMessage);
			}

			break;
		case dateTP: // Date

			retInteger += 1;

			while (rstSet.next()) {

				retMessage = "String";
				logOriginalValues(retInteger);
				logResult(rstSet, rstSet.getString("the_Field"), retMessage);
			}

			break;
		case booleanTP: // Boolean

			retInteger += 1;

			while (rstSet.next()) {

				retMessage = "Boolean";
				logOriginalValues(retInteger);
				logResult(rstSet, rstSet.getBoolean("the_Field"), retMessage);
			}

			break;
		case numericTP: // Numeric

			retInteger += 1;

			while (rstSet.next()) {

				retMessage = "Numeric";
				logOriginalValues(retInteger);
				logResult(rstSet, rstSet.getInt("the_Field"), retMessage);
			}

			break;

		default:
			break;
		}
	}

	public static void logOriginalValues(Integer listIndex) throws Exception {

		if (!FormletFieldsValues.isEmpty()) {

			String[][] arr = FormletFieldsValues.get(0);			

			log.warn("Original Value: " + arr[eListArrIndex][retInteger]);

			if(formletName.startsWith("eList") &&  eListArrIndex <( arr.length - 1))
			{
				eListArrIndex += 1;

			}
			else
				eListArrIndex = 0;
		}
	}

	public static void logResult(ResultSet rstSet, Object retData,
			String message) throws Exception {

		log.warn("Returned Data: " + retData);

		log.warn("The ID: "
		+ rstSet.getInt("the_ID"));

		if (retData != null && message != "String") {

			log.warn("Data Size: This is " + message + " Value!");

		} else if (retData == null) {

			log.warn("Data Size: This is NULL Value!");

		} else {

			log.warn("Data Size: " + retData.toString().length());
		}

	}
	/*
	public static String getConnectionProperty(String propertyKey)
			throws Exception {

		Properties p = new Properties();

		p.load(new FileInputStream(new File(
				"src/test_Suite/deployment_path.properties")));

		return p.getProperty(propertyKey);
	}*/

	public static void initializeQuery(RptFuncs reportingFunc)
			throws Exception {

		fieldName = reportingFunc.getEFormFieldIdent();
		eFormName = reportingFunc.getEFormIdent();
		accessData = reportingFunc.getAccessData();
		subCode = reportingFunc.getSubmissionCode();

		switch (reportingFunc.getFunctionName()) {

		case oraApplicant:

			reportingFunc.setQuery(getORAApplicantQuery());

			break;

		case oraProgram:

			reportingFunc.setQuery(getORAAdminQuery());

			break;

		case oraOrganization:

			reportingFunc.setQuery(getORAOrgQuery());

			break;

		case oraProject:

			reportingFunc.setQuery(getORAProjectQuery());

			break;

		case sqlApplicant:

			reportingFunc.setQuery(getSQLApplicantQuery());

			break;

		case sqlProgram:

			reportingFunc.setQuery(getSQLAdminQuery());

			break;

		case sqlOrganization:

			reportingFunc.setQuery(getSQLOrgQuery());

			break;

		case sqlProject:

			reportingFunc.setQuery(getSQLProjectQuery());

			break;

		default:
			break;
		}

	}


	public static boolean initializeQuery_DataArchive(RptFuncs reportingFunc,FOProject foProj)
			throws Exception {

		projectName = foProj.getProjFOFullName();

		if (reportingFunc.getDbType().toString().equals("sql")){

			reportingFunc.setQuery(getSQLQuery_DataArchive());

			log.warn("Connected to SQL database");
			return true;
		}

		else if(reportingFunc.getDbType().toString().equals("ora")){

			reportingFunc.setQuery(getORAQuery_DataArchive());

			log.warn("Connected to ORA database");

			return true;

		}
		else
		{
			log.error(reportingFunc.getDbType().toString());
		}

		return false;

	}


	public static String getSQLQuery_DataArchive() throws Exception {

		return "UPDATE workflow_projects "
				+ "SET date_created = DATEADD(YEAR, -7, date_created)"
				+"WHERE po_project_name = '" +projectName+ "' "
				+ "AND status_id = (SELECT lookup_value_id FROM lookup_values WHERE lookup_id = ( select lookup_id FROM lookups WHERE lookup_name = 'Case Status Types') AND constant = 'G3_CASE_STATUS_CLOSED')"
				+ "AND program_id = (SELECT program_id FROM programs WHERE program_name = 'A-Gnrl-PA-FOPP')";

	}

	public static String getORAQuery_DataArchive() throws Exception {

		return "UPDATE workflow_projects "
				+ "SET date_created = date_created - numtoyminterval(7,'year')"
				+"WHERE po_project_name = '" +projectName+ "' "
				+ " AND status_id = (SELECT lookup_value_id FROM lookup_values WHERE lookup_id = ( select lookup_id FROM lookups WHERE lookup_name = 'Case Status Types') AND CONSTANT = 'G3_CASE_STATUS_CLOSED')"
				+ "AND program_id = (SELECT program_id FROM programs WHERE program_name = 'A-Gnrl-PA-FOPP')";

	}


	public static String getSQLOrgQuery() throws Exception {

		return "SELECT organization_id the_ID, "
				+ "a.answer the_Field,date_created,form_submission_id "
				+ "FROM GET_ORGANIZATION_EFORM_FIELD('" + fieldName + "','"
				+ eFormName + "',DEFAULT) AS a " + orderBy;
	}

	public static String getSQLAdminQuery() throws Exception {

		return "SELECT program_id the_ID, "
				+ "a.answer the_Field,date_created,form_submission_id "
				+ "FROM GET_PROGRAM_EFORM_FIELD('" + fieldName + "','"
				+ eFormName + "',DEFAULT) AS a " + orderBy;
	}

	public static String getSQLApplicantQuery() throws Exception {

		return "SELECT applicant_id the_ID, "
				+ "a.answer the_Field,date_created,form_submission_id "
				+ "FROM GET_APPLICANT_EFORM_FIELD('" + fieldName + "','"
				+ eFormName + "',DEFAULT) AS a " + orderBy;
	}

	public static String getSQLProjectQuery() throws Exception {
		return "SELECT project_case_id the_ID, step_id, date_submitted, "
				+ "a.answer AS the_Field, date_created, form_submission_id "
				+ "FROM GET_PROJECT_CASE_EFORM_FIELD('" + fieldName + "','"
				+ eFormName + "'," + subCode + ",DEFAULT,DEFAULT) AS a "
				+ orderBy;
	}

	public static String getORAOrgQuery() throws Exception {

		return "SELECT organization_id the_ID, SYS.ANYDATA." + accessData
				+ "(answer)the_Field,date_created,form_submission_id "
				+ "FROM TABLE(EFORM_ANSWERS.GET_ORGANIZATION_EFORM_FIELD('"
				+ fieldName + "','" + eFormName + "')) " + orderBy;
	}

	public static String getORAAdminQuery() throws Exception {

		return "SELECT program_id the_ID, SYS.ANYDATA." + accessData
				+ "(answer)the_Field,date_created,form_submission_id "
				+ "FROM TABLE(EFORM_ANSWERS.GET_PROGRAM_EFORM_FIELD('"
				+ fieldName + "','" + eFormName + "')) " + orderBy;
	}

	public static String getORAApplicantQuery() throws Exception {

		return "SELECT applicant_id the_ID, SYS.ANYDATA." + accessData
				+ "(answer)the_Field,date_created,form_submission_id "
				+ "FROM TABLE(EFORM_ANSWERS.GET_APPLICANT_EFORM_FIELD('"
				+ fieldName + "','" + eFormName + "')) " + orderBy;
	}

	public static String getORAProjectQuery() throws Exception {
		return "SELECT project_case_id the_ID, step_id, date_submitted, SYS.ANYDATA."
				+ accessData
				+ "(answer) the_Field, date_created, form_submission_id "
				+ "FROM TABLE(EFORM_ANSWERS.GET_PROJECT_CASE_EFORM_FIELD('"
				+ fieldName
				+ "','"
				+ eFormName
				+ "',"
				+ subCode
				+ ")) "
				+ orderBy;
	}

	public static void fill_SubmissionSchedule(Project proj, int claimNum,
			String[][] initialClaim) throws Exception {
		try {

			ClicksUtil.clickLinks(IClicksConst.openAgreementDetailsLnk);

			Assert.assertTrue(GeneralUtil.setTextByTitle(IProjectsConst.gps_AgreementDetails_TextFldTtl, "This is Award Form"), "FAIL: Could not set text filed in Agreement Details");

			ClicksUtil.clickButtons(IClicksConst.saveAndNextBtn);

			ClicksUtil.clickImage(IClicksConst.newImg);

			for (int i = 1; i <= claimNum; i++) {

				Assert.assertTrue(GeneralUtil.setTextByTitle(IProjectsConst.gps_SubmissionDetails_SubNameTtl,initialClaim[1][1].concat(Integer.toString(i))),"FAIL: could not set Claim Name!");

				Assert.assertTrue(GeneralUtil.setTextByTitle(IProjectsConst.gps_SubmissionDetails_PubStartDateTtl, proj.getStartDate()), "FAIL: Could not set the Start Date");

				Assert.assertTrue(GeneralUtil.setTextByTitle(IProjectsConst.gps_SubmissionDetails_ScheduleDueDateTtl, proj.getEndDate()),"FAIL: Could not set Due Date");

				Assert.assertTrue(GeneralUtil.setTextByTitle(IProjectsConst.gps_SubmissionDetails_PubEndDateTtl, proj.getEndDate()),"FIAL: Could not set End Date");

				Assert.assertTrue(GeneralUtil.selectFullStringInDropdownListByTitle(IProjectsConst.gps_SubmissionDetails_SubFormTtl, initialClaim[1][0]),"FAIL: Could not select PA Submission Form!");

				Assert.assertTrue(GeneralUtil.toggleCheckBoxByTitle(IProjectsConst.gps_SubmissionDetails_RequiredTtl, Boolean.parseBoolean(initialClaim[1][2])), "FAIL: Could not set Required checkbox!");			

				if (i < claimNum)
					ClicksUtil.clickButtons(IClicksConst.saveAndAddAnotherBtn);
				else
					ClicksUtil.clickButtons(IClicksConst.saveAndBackToListBtn);

			}

		} catch (Exception e) {
			log.error("Unexpected Exception", e);

			throw new RuntimeException("Unexpected Exception", e);
		}
	}

	public static void fill_NoProperties_FieldsData() throws Exception {

		ClicksUtil.clickLinks("No Propreties eFormFields");

		EFormsUtil
		.taggleCheckbox(
				0,
				Boolean
				.parseBoolean(IRptFuncConst.reportFunc_NoPropertiesFormlet[0][0]));

		EFormsUtil.enterDateToDateField(0);

		EFormsUtil.enterTextToTextField(1,
				IRptFuncConst.reportFunc_NoPropertiesFormlet[0][2]);

		EFormsUtil.enterTextToTextField(2,
				IRptFuncConst.reportFunc_NoPropertiesFormlet[0][3]);

		EFormsUtil.enterTextToTextField(3,
				IRptFuncConst.reportFunc_NoPropertiesFormlet[0][4]);

		ClicksUtil.clickButtons(IClicksConst.saveBtn);
	}

	public static void fill_ApprovalDropdown_FieldsData() throws Exception {

		ClicksUtil.clickLinks("Dropdown eFormFields");

		EFormsUtil.selectFromDropDown(0,
				IRptFuncConst.reportFunc_DropdownFormlet[0][0]);

		GeneralUtil.takeANap(1.5);

		EFormsUtil.selectFromDropDown(1,
				IRptFuncConst.reportFunc_DropdownFormlet[0][1]);

		GeneralUtil.takeANap(1.5);

		EFormsUtil.selectFromDropDown(2,
				IRptFuncConst.reportFunc_DropdownFormlet[0][2]);

		ClicksUtil.clickButtons(IClicksConst.saveBtn);
	}

	public static void fill_Dropdown_FieldsData() throws Exception {

		IE ie = IEUtil.getActiveIE();

		Integer fieldCount = 0;

		ClicksUtil.clickLinks("Dropdown eFormFields");

		if (ie.selectLists().length() == 5) {
			EFormsUtil.selectFromDropDown(fieldCount,
					IRptFuncConst.reportFunc_DropdownFormlet[0][0]);
			fieldCount += 1;

			GeneralUtil.takeANap(2.0);
		}

		EFormsUtil.selectFromDropDown(fieldCount,
				IRptFuncConst.reportFunc_DropdownFormlet[0][1]);

		GeneralUtil.takeANap(2.0);

		fieldCount += 1;

		EFormsUtil.selectFromDropDown(fieldCount,
				IRptFuncConst.reportFunc_DropdownFormlet[0][2]);

		fieldCount += 1;

		EFormsUtil.selectFromDropDown(fieldCount,
				IRptFuncConst.reportFunc_DropdownFormlet[0][3]);

		fieldCount += 1;

		if (ie.selectList(fieldCount).exists())
			EFormsUtil.selectFromDropDown(fieldCount,
					IRptFuncConst.reportFunc_DropdownFormlet[0][4]);

		ClicksUtil.clickButtons(IClicksConst.saveBtn);

	}

	public static void fill_MinAndMaxProperties_FieldsData() throws Exception {
		IE ie = IEUtil.getActiveIE();

		ClicksUtil.clickLinks("Min And Max Properties eFormFields");

		EFormsUtil
		.enterTextToTextField(
				0,
				EFormsUtil
				.createRandomString(
						IRptFuncConst.reportFunc_MinAndMaxFormlet[0][0],
						Integer
						.parseInt(IRptFuncConst.reportFunc_MinAndMaxFormlet[0][1])));

		EFormsUtil.enterTextToTextField(1,
				IRptFuncConst.reportFunc_MinAndMaxFormlet[0][1]);

		EFormsUtil.enterTextToTextField(2,
				IRptFuncConst.reportFunc_MinAndMaxFormlet[0][2]);

		EFormsUtil
		.enterTextToTextField(
				3,
				EFormsUtil
				.createRandomString(
						IRptFuncConst.reportFunc_MinAndMaxFormlet[0][3],
						Integer
						.parseInt(IRptFuncConst.reportFunc_MinAndMaxFormlet[0][4])));

		EFormsUtil.enterTextToTextField(4,
				IRptFuncConst.reportFunc_MinAndMaxFormlet[0][4]);

		if (ie.textField(5).exists())
			EFormsUtil.enterTextToTextField(5,
					IRptFuncConst.reportFunc_MinAndMaxFormlet[0][5]);

		ClicksUtil.clickButtons(IClicksConst.saveBtn);

	}

	public static void fill_TypesProperties_FieldsData() throws Exception {


		ClicksUtil.clickLinks("Types Properties eFormFields");

		EFormsUtil.enterTextToTextField(0,
				IRptFuncConst.reportFunc_TypesPropertiesFormlet[0][0]);

		EFormsUtil.enterTextToTextField(1,
				IRptFuncConst.reportFunc_TypesPropertiesFormlet[0][1]);

		ClicksUtil.clickButtons(IClicksConst.saveBtn);

	}

	public static void fill_LookupDropdwon_FieldsData() throws Exception {


		ClicksUtil.clickLinks("Dropdown Lookup Property eFormFields");

		for (int x = 0; x < 13; x += 2) {
			EFormsUtil.taggleCheckbox(x,
					Boolean.parseBoolean(IRptFuncConst.reportFunc_LookupDropdownFormlet[0][0]));
		}

		EFormsUtil.taggleRadioButton(7,
				Boolean.parseBoolean(IRptFuncConst.reportFunc_LookupDropdownFormlet[0][1]));

		ClicksUtil.clickButtons(IClicksConst.saveBtn);
	}

	public static void fill_eList_FormletData() throws Exception {


		ClicksUtil.clickLinks("eList");

		ClicksUtil.clickImage(IClicksConst.newImg);

		for (int x = 0; x < IRptFuncConst.reportFunc_eListFormlet.length; x++) {

			EFormsUtil.enterTextToTextField(0,
					IRptFuncConst.reportFunc_eListFormlet[x][0]);
			EFormsUtil.enterTextToTextField(1,
					IRptFuncConst.reportFunc_eListFormlet[x][1]);
			EFormsUtil.enterTextToTextField(2,
					IRptFuncConst.reportFunc_eListFormlet[x][2]);
			EFormsUtil.enterTextToTextField(3,
					IRptFuncConst.reportFunc_eListFormlet[x][3]);

			EFormsUtil.enterTextToTextField(4,
					IRptFuncConst.reportFunc_eListFormlet[x][4]);

			EFormsUtil.enterTextToTextField(5,
					IRptFuncConst.reportFunc_eListFormlet[x][5]);

			EFormsUtil.selectFromDropDown(0,
					IRptFuncConst.reportFunc_eListFormlet[x][6]);
			
			GeneralUtil.takeANap(0.5);

			EFormsUtil.selectFromDropDown(1,
					IRptFuncConst.reportFunc_eListFormlet[x][7]);

			GeneralUtil.takeANap(0.5);

			ClicksUtil.clickButtons(IClicksConst.saveAndAddAnotherBtn);

			GeneralUtil.takeANap(1.0);
		}

	}

	public static void fill_eListDataGrid_FormletData() throws Exception {

		IE ie = IEUtil.getActiveIE();

		ClicksUtil.clickLinks("eList Data Grid");

		ClicksUtil.clickImage(IClicksConst.newImg);

		for (Integer x = 0; x < IRptFuncConst.reportFunc_eListDataGridFormlet.length; x++) {

			EFormsUtil
			.selectFromDropDown(
					0,
					IRptFuncConst.reportFunc_eListDataGridFormlet[x][0]);

			GeneralUtil.takeANap(1.0);

			EFormsUtil
			.selectFromDropDown(
					1,
					IRptFuncConst.reportFunc_eListDataGridFormlet[x][2]);

			GeneralUtil.takeANap(1.0);

			if (ie.textField(0).exists()) {
				EFormsUtil
				.enterTextToTextField(
						0,
						IRptFuncConst.reportFunc_eListDataGridFormlet[x][3]);
				EFormsUtil
				.enterTextToTextField(
						1,
						IRptFuncConst.reportFunc_eListDataGridFormlet[x][4]);
			}

			GeneralUtil.takeANap(0.5);

			ClicksUtil.clickButtons(IClicksConst.saveAndAddAnotherBtn);

			GeneralUtil.takeANap(1.0);
		}

	}

	public static void fill_eListDropdown_FormletData() throws Exception {

		IE ie = IEUtil.getActiveIE();

		ClicksUtil.clickLinks("eList Dropdown");

		ClicksUtil.clickImage(IClicksConst.newImg);
		String[][] tempList = null;

		if(GeneralUtil.currentLocale.equals(Locale.US))
		{
			tempList = IRptFuncConst.reportFunc_eListDropdownFormlet_USLocale;
		}
		else
		{
			tempList = IRptFuncConst.reportFunc_eListDropdownFormlet;
		}		

		for (Integer x = 0; x < tempList.length; x++) {



			EFormsUtil
			.selectFromDropDown(
					0,
					tempList[x][0]);

			GeneralUtil.takeANap(1.5);

			EFormsUtil
			.selectFromDropDown(
					1,
					tempList[x][1]);
			
			GeneralUtil.takeANap(1.5);

			if(ie.selectList(2).exists())
			{
				EFormsUtil
				.selectFromDropDown(
						2,
						tempList[x][2]);
			}		

			GeneralUtil.takeANap(0.5);

			//ClicksUtil.clickButtons(IClicksConst.saveBtn);

			//GeneralUtil.takeANap(1.0);

			ClicksUtil.clickButtons(IClicksConst.saveAndAddAnotherBtn);

			GeneralUtil.takeANap(1.0);
		}
	}

	/**
	 * Does update function in DB directly
	 * @param rsResult
	 * @param reportFunc
	 * @param query
	 * @throws SQLException
	 */
	public static void updateQueryMethod(RptFuncs reportFunc, String query) throws SQLException{

		try {
			reportFunc.executeUpdate(query);

		} catch (Exception e) {
			log.error("Unexpected Exception", e);

		}
	}

	/**
	 * Does select function in DB directly
	 * @param rsResult
	 * @param reportFunc
	 * @param query
	 * @throws SQLException
	 */
	public static void selectQueryMethod(ResultSet rsResult, RptFuncs reportFunc, String query) throws SQLException{
		try {
			rsResult = reportFunc.executeQuery(query);
		} catch (Exception e) {
			log.error("Unexpected Exception", e);

		}

		if (rsResult.next()) {
			String	result = rsResult.getString(1);
			System.out.println("\nANSWER is : "+ result);
		}

	}

	
	
	/**
	 * Closes projects in specified FOPP - Oracle
	 * @param foppName
	 * @return String - Ora Query
	 * @throws Exception
	 */
	public static String da_Ora_CloseProjs_Query(String foppName) throws Exception {
		
		return "UPDATE workflow_projects " +
					"SET status_id = (SELECT lookup_value_id FROM lookup_values WHERE lookup_id = ( " +
						"SELECT lookup_id FROM lookups WHERE lookup_name = 'Case Status Types') AND CONSTANT = 'G3_CASE_STATUS_CLOSED')" + 
					"WHERE status_id = (SELECT lookup_value_id FROM lookup_values WHERE lookup_id = ( " +
						"SELECT lookup_id FROM lookups WHERE lookup_name = 'Case Status Types') AND CONSTANT ='G3_CASE_STATUS_OPEN') " +
					"AND program_id = (SELECT program_id FROM programs WHERE program_name = '" + foppName + "')";
	}

	/**
	 * Closes projects in specified FOPP  - SQL
	 * @param foppName
	 * @return String - SQL Query
	 * @throws Exception
	 */
	public static String da_SQL_CloseProjs_Query(String dbName, String foppName) throws Exception {

		return "UPDATE [" + dbName + "].[" + dbName + "].[workflow_projects] " +
					"SET [status_id] = (SELECT [lookup_value_id] FROM [" + dbName + "].[" + dbName + "].[lookup_values] WHERE [lookup_id] = ( " +
						"SELECT [lookup_id] FROM [" + dbName + "].[" + dbName + "].[lookups] WHERE [lookup_name] = 'Case Status Types') AND [constant] = 'G3_CASE_STATUS_CLOSED')" +
					"WHERE [status_id] = (SELECT [lookup_value_id] FROM [" + dbName + "].[" + dbName + "].[lookup_values] WHERE [lookup_id] = (  " +
						"SELECT [lookup_id] FROM [" + dbName + "].[" + dbName + "].[lookups] WHERE [lookup_name] = 'Case Status Types') AND [constant] = 'G3_CASE_STATUS_OPEN')" +
					"AND [program_id] = (SELECT [program_id] FROM [" + dbName + "].[" + dbName + "].[programs] WHERE [program_name] ='" + foppName + "')";
	}

	/**
	 * Ages projects in specified FOPP by specified age - Oracle
	 * @param age
	 * @param foppName
	 * @return String - Ora Query
	 * @throws Exception
	 */
	public static String da_Ora_AgeProjs_Query(int age, String foppName) throws Exception {

		return "UPDATE workflow_projects " +
					"SET date_created = date_created - numtoyminterval(" + age + ",'year') " +
					"WHERE status_id = (SELECT lookup_value_id FROM lookup_values WHERE lookup_id = (" +
						"SELECT lookup_id FROM lookups WHERE lookup_name = 'Case Status Types') AND CONSTANT = 'G3_CASE_STATUS_CLOSED') " +
					"AND program_id = (SELECT program_id FROM programs WHERE program_name = '" + foppName +"')";
	}

	/**
	 * Ages projects in specified FOPP by specified age  - SQL
	 * @param age
	 * @param foppName
	 * @return String - SQL Query
	 * @throws Exception
	 */
	public static String da_SQL_AgeProjs_Query(String dbName, int age, String foppName) throws Exception {

		return "UPDATE [" + dbName + "].[" + dbName + "].[workflow_projects] " +
					"SET [date_created] = DATEADD(YEAR, -" + age +", [date_created]) " +
					"WHERE [status_id] = (SELECT [lookup_value_id] FROM [" + dbName + "].[" + dbName + "].[lookup_values] WHERE [lookup_id] = ( " +
						"SELECT [lookup_id] FROM [" + dbName + "].[" + dbName + "].[lookups] WHERE [lookup_name] = 'Case Status Types') AND [constant] = 'G3_CASE_STATUS_CLOSED') " +
					"AND [program_id] = (SELECT [program_id] FROM [" + dbName + "].[" + dbName + "].[programs] WHERE [program_name] ='" + foppName +"')";
	}
	
	
	public static String getConnectionProperty(String propertyKey) throws Exception {

		Properties p = new Properties();

		p.load(new FileInputStream(new File(
				"src/test_Suite/deployment_path.properties")));
	
		return p.getProperty(propertyKey);

	}
}

