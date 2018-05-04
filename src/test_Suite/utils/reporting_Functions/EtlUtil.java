/**
 * 
 */
package test_Suite.utils.reporting_Functions;

import static watij.finders.FinderFactory.attribute;
import static watij.finders.SymbolFactory.alt;
import static watij.finders.SymbolFactory.id;
import static watij.finders.SymbolFactory.src;
import static watij.finders.SymbolFactory.tag;
import static watij.finders.SymbolFactory.title;

import java.io.FileReader;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.Assert;

import test_Suite.constants.cases.IGeneralConst;
import test_Suite.constants.reporting_Functions.IEtlConst;
import test_Suite.constants.reporting_Functions.IEtlConst.EetlEFormTypes;
import test_Suite.constants.reporting_Functions.IEtlConst.EetlFoppFormTypes;
import test_Suite.constants.reporting_Functions.IEtlConst.EetlFormlets;
import test_Suite.constants.reporting_Functions.IEtlConst.EetlRepoFieldManagementTypes;
import test_Suite.constants.reporting_Functions.IEtlConst.EetlRepoFieldTypes;
import test_Suite.constants.reporting_Functions.IEtlConst.EetlRptFldTypes;
import test_Suite.constants.reporting_Functions.IRptFuncConst;
import test_Suite.constants.reporting_Functions.IRptFuncConst.EeFormsIdentifier;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.constants.ui.IFiltersConst;
import test_Suite.lib.reporting_Functions.ETL;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.ui.ClicksUtil;
import test_Suite.utils.ui.FiltersUtil;
import test_Suite.utils.ui.IEUtil;
import test_Suite.utils.ui.TablesUtil;
import watij.dialogs.ConfirmDialog;
import watij.elements.Div;
import watij.elements.Form;
import watij.elements.HtmlElement;
import watij.elements.HtmlElements;
import watij.elements.Image;
import watij.elements.Span;
import watij.elements.Spans;
import watij.elements.Table;
import watij.elements.TableBody;
import watij.elements.TableRow;
import watij.runtime.ie.IE;
import au.com.bytecode.opencsv.CSVReader;

/**
 * @author mshakshouki
 *
 */
public class EtlUtil {

	// #####***********************************************************************
	// ### To set up the Global Params Name Vars
	// #####***********************************************************************

	private static Log log = LogFactory.getLog(EtlUtil.class);
	
	public static LinkedHashMap<String, IRptFuncConst.EfieldDataTypes> eListDropdownHT;
	
	static Set<Map.Entry<String, IRptFuncConst.EfieldDataTypes>> set;

	/*------ End of Global Vars --------------*/
	
	public static LinkedHashMap<String, IRptFuncConst.EfieldDataTypes> initLinkedHashMap() throws Exception {

		eListDropdownHT = new LinkedHashMap<String, IRptFuncConst.EfieldDataTypes>();
		eListDropdownHT.put("eList Dropdown",
				IRptFuncConst.EfieldDataTypes.formlet);
		eListDropdownHT.put("Parent DD",
				IRptFuncConst.EfieldDataTypes.numericTP);
		eListDropdownHT.put("Child DD",
				IRptFuncConst.EfieldDataTypes.numericTP);
		eListDropdownHT.put("DD From List",
				IRptFuncConst.EfieldDataTypes.numericTP);
		
		return eListDropdownHT;
	}
	
	public static LinkedHashMap<String, IRptFuncConst.EfieldDataTypes> getHashedFormletAndFields(List<LinkedHashMap<String, IRptFuncConst.EfieldDataTypes>> hashList, EetlFormlets formletIdent) throws Exception {
		
		
		
		for (LinkedHashMap<String, IRptFuncConst.EfieldDataTypes> lhm : hashList) {
	

			set = lhm.entrySet();
			
			log.debug(set.toArray()[0].toString().startsWith(IEtlConst.etlNonProjetFormlets[formletIdent.ordinal()]));
			log.debug(set);
			if(set.toArray()[0].toString().startsWith(IEtlConst.etlNonProjetFormlets[formletIdent.ordinal()]))
			{
				return lhm;
			}			
		}
		
		return null;
	}
	
	public static ETL initializeProjectForm(EetlEFormTypes eType,EeFormsIdentifier formIdent, EetlFormlets formletIdent) throws Exception {
		
		ETL etl = new ETL();
		
		etl.setETypes(eType);
		
		etl.setReportingFieldGroup(IEtlConst.rptFldTypes[EetlRptFldTypes.SUB.ordinal()]);
		
		etl.setReportingCategory("User");
		
		etl.setRptFieldPrefix(IEtlConst.rptFldPrefix[EetlRptFldTypes.SUB.ordinal()]);
		
		etl.setEFormType(IEtlConst.etlEFormTypes[eType.ordinal()]);
		
		etl.setEFormIdent(IRptFuncConst.reportFunc_eFormsIdent[formIdent.ordinal()]);
		
		etl.setHashList(RptFuncUtil.initLinkedHashMap());
		
		etl.setMapHash(getHashedFormletAndFields(etl.getHashList(),formletIdent));		
		
		return etl;
	}
	
	public static ETL initializeNonProjectForm(EetlEFormTypes eType, EetlFormlets formletIdent,EetlRptFldTypes etlFieldType) throws Exception {
		
		ETL etl = new ETL();
		
		etl.setETypes(eType);
		
		etl.setReportingFieldGroup(IEtlConst.rptFldTypes[etlFieldType.ordinal()]);
		
		etl.setReportingCategory("User");
		
		etl.setRptFieldPrefix(IEtlConst.rptFldPrefix[etlFieldType.ordinal()]);
		
		etl.setEFormType(IEtlConst.etlEFormTypes[eType.ordinal()]);
		
		etl.setEFormIdent(IRptFuncConst.reportingFunc_NonProject_eFormsIdent[eType.ordinal()]);
		
		etl.setFormletIdent(IEtlConst.etlNonProjetFormlets[formletIdent.ordinal()]);
		
		etl.setHashList(RptFuncUtil.initLinkedHashMap());
		
		etl.setMapHash(getHashedFormletAndFields(etl.getHashList(),formletIdent));		
		
		return etl;
	}
	
	public static boolean addSubFieldsFromCSV(String filename) throws Exception {
		
		CSVReader reader = new CSVReader(new FileReader(filename));
	    String [] nextLine;
		
		Table tabl = null;
		
		int index = 8;
		
	    while ((nextLine = reader.readNext()) != null) {
	    	
	    	//remember to add for loop
	    	
	    	if(!ClicksUtil.clickImageByAlt("Add Reporting Field"))
	    	{
	    		log.error("Could find Add Reporting Field image!");		
	    		
	    		reader.close();
	    		
	    		return false;
	    		
	    	}
			
//			table.image(alt,"Add Reporting Field").click();
	    	
	    	if(!addReportingFields(nextLine, "",index))
	    	{
	    		log.error("Could not Add Report Fields!");		
	    		
	    		reader.close();
	    		
	    		return false;
	    	}		

			
			GeneralUtil.takeANap(0.5);
			
			tabl = TablesUtil.getTableByInnerTextInSpan(IEtlConst.plannerTree_spanId, nextLine[0].concat("_").concat(String.valueOf(index)));
			
			
			if(null == tabl)
			{
				log.error("Couldn't use the field table!");	    			
				
				reader.close();
				
				return false;
			}
			
			tabl.image(alt,"Show Mapped e.FormFields").click();
			
			///IEtlConst.etLDM_RptLbfEqualFormsIdent[i]
			
			if(!mappReportingField(nextLine,false,false,nextLine[3]))
			{
				log.error("Could not mapp a field, check errors above!");	    			
				
				reader.close();
				return false;
			}
			
		}	    			
		
		reader.close();
		
		return true;
	}
	
	
	public static boolean addReportingFields(String[] arrStr, String folderName, Integer indx) throws Exception {
		
		if(!folderName.isEmpty())
		{
			
			if(!GeneralUtil.selectFullStringInDropdownList(IEtlConst.etlDM_RptFldFolder_Dropdwon_Id, folderName))
			{
				log.error("Could not select Report Folder Name!");
				
				return false;
			}			
		}
		
		if(!GeneralUtil.setTextById(IEtlConst.etlDM_RptFldIdent_TextField_Id, arrStr[1].concat("_".concat(indx.toString()))))
		{
			log.error("Could not set Report Field Identifier!");
			
			return false;			
		}
		
		if(!GeneralUtil.selectFullStringInDropdownList(IEtlConst.etlDM_RptFldType_Dropdown_Id, arrStr[5]))
		{
			log.error("Could not Select Report Field Type!");
			
			return false;
		}	
		
		GeneralUtil.takeANap(0.5);
		
		if(!GeneralUtil.setTextById(IEtlConst.etlDM_RptFldBusinessName0_TextField_Id, (arrStr[2].replace("-", " ")).concat(" ".concat(indx.toString()))))
		{
			log.error("Could not set Report Field Business Name!");
			
			return false;			
		}
		
		if(GeneralUtil.isTextFieldExistsById(IEtlConst.etlDM_RptFldBusinessName1_TextField_Id))
		{			
			GeneralUtil.setTextById(IEtlConst.etlDM_RptFldBusinessName1_TextField_Id, (arrStr[2].replace("-", " ")).concat(" ".concat(indx.toString())));
		}
		
		if(GeneralUtil.isTextFieldExistsById(IEtlConst.etlDM_RptFldBusinessName2_TextField_Id))
		{			
			GeneralUtil.setTextById(IEtlConst.etlDM_RptFldBusinessName2_TextField_Id, (arrStr[2].replace("-", " ")).concat(" ".concat(indx.toString())));
		}
		
		if(GeneralUtil.isTextFieldExistsById(IEtlConst.etlDM_RptFldBusinessName3_TextField_Id))
		{			
			GeneralUtil.setTextById(IEtlConst.etlDM_RptFldBusinessName3_TextField_Id, (arrStr[2].replace("-", " ")).concat(" ".concat(indx.toString())));
		}
		
		ClicksUtil.clickButtons(IClicksConst.saveAndAddAnotherBtn);	
		
		GeneralUtil.takeANap(0.5);
		
		return true;
	}
	
	public static boolean addListFoldersFromCSV(String fileName) throws Exception {
		
		CSVReader reader = new CSVReader(new FileReader(fileName));
	    String [] nextLine;
	    
	    String str = "";
	    
	    String newStr = "";
	    
	    Table tabl = null;
	    
	    Table prevTabl = null;
		
	    while ((nextLine = reader.readNext()) != null) {
	    	
	    	newStr = nextLine[4].replace("-Sub-Fields-LBF", "");
	    	
	    	if(!newStr.equalsIgnoreCase(str))
	    	{
	    		tabl = null;
		    	
		    	if(!addReportingFolder(nextLine))
		    	{
		    		log.error("Could not Add Report Fields!");		
		    		
		    		reader.close();
		    		
		    		return false;
		    	}
		    	
	    		if(null == tabl)
	    		{
	    			tabl = TablesUtil.getTableByInnerTextInSpan(IEtlConst.plannerTree_spanId, newStr.replace("-", "_"));
	    		}
	    		
	    		if(null != prevTabl)
	    		{
	    			log.warn(prevTabl.innerText());
	    			if(prevTabl.image(alt,"nav").exists())
	    			{
	    				log.warn(prevTabl.image(alt,"nav").src());
	    			}
	    			if(prevTabl.image(alt,"nav").src().endsWith("line-middle.gif"))
	    			{
	    				prevTabl.image(alt,"nav").click();
	    			}
	    		}
	    		
		    	str = newStr;
		    	
		    	prevTabl = tabl;
	    	}
	    	
	    	if(!addReportFieldInListFolder(nextLine,tabl))
	    	{
	    		log.error("couldn't add report field: ".concat(nextLine[1]));		
	    		
	    		reader.close();
	    		
	    		return false;
	    	}
	    }		
		
		reader.close();
		
		return true;
	}
	
	public static boolean addReportFieldInListFolder(String[] arrStr, Table table) throws Exception {
		
		Table tabl = null;
		
		if(null == table)
		{
			log.error("Couldn't use the folder table!");
			
			return false;
		}
		
		String newStr = arrStr[4].replace("-Sub-Fields-LBF", "");
		
		
		for(int i=9;i<10;i++)
		{
			
			table.image(alt,"Add Reporting Field").click();
			
			GeneralUtil.takeANap(0.5);
			
			if(!addReportingFields(arrStr, newStr.replace("-", "_"),i))
			{
				log.error("Couldn't add a reporting field!");
				return false;
			}			

			
			GeneralUtil.takeANap(0.5);
			
			tabl = TablesUtil.getTableByInnerTextInSpan(IEtlConst.plannerTree_spanId, arrStr[0].concat("_").concat(String.valueOf(i)));
			
			
			if(null == tabl)
			{
				log.error("Couldn't use the field table!");
				
				return false;
			}
			
			tabl.image(alt,"Show Mapped e.FormFields").click();
			
			///IEtlConst.etLDM_RptLbfEqualFormsIdent[i]
			
			if(!mappReportingField(arrStr,true,true,arrStr[3]))
			{
				log.error("Could not mapp a field, check errors above!");
				return false;
			}
			
		}
		
		
		return true;
	}
	
	public static boolean addReportingFolder(String[] arrStr) throws Exception {
		
		String newStr = arrStr[4].replace("-Sub-Fields-LBF", "");
		
		ClicksUtil.clickLinks(IClicksConst.openReportingFiledsSubmissionjLnk);
		
		GeneralUtil.takeANap(0.5);
		
		ClicksUtil.clickImageByAlt(IClicksConst.dmAddNewRptLstFolder_Alt);
		
		GeneralUtil.takeANap(0.5);
		
		if(!GeneralUtil.setTextById(IEtlConst.etlDM_RptFolderIdent_TextField_Id, newStr.replace("-", "_")))
		{
			log.error("Couldn't Add folder Identifier!");
			
			return false;
		}
		
		if(!GeneralUtil.setTextById(IEtlConst.etlDM_RptFolderBusinessName0_TextField_Id, newStr.replace("-", " ")))
		{
			log.error("Could not set Report Field Business Name!");
			
			return false;			
		}
		
		if(GeneralUtil.isTextFieldExistsById(IEtlConst.etlDM_RptFolderBusinessName1_TextField_Id))
		{			
			GeneralUtil.setTextById(IEtlConst.etlDM_RptFolderBusinessName1_TextField_Id, newStr.replace("-", " "));
		}
		
		if(GeneralUtil.isTextFieldExistsById(IEtlConst.etlDM_RptFolderBusinessName2_TextField_Id))
		{			
			GeneralUtil.setTextById(IEtlConst.etlDM_RptFolderBusinessName2_TextField_Id, newStr.replace("-", " "));
		}
		
		if(GeneralUtil.isTextFieldExistsById(IEtlConst.etlDM_RptFolderBusinessName3_TextField_Id))
		{			
			GeneralUtil.setTextById(IEtlConst.etlDM_RptFolderBusinessName3_TextField_Id, newStr.replace("-", " "));
		}
		
		ClicksUtil.clickButtons(IClicksConst.saveAndAddAnotherBtn);	
		
		GeneralUtil.takeANap(0.5);		
		
		
		return true;
	}
	
	public static boolean addProjectFormletFields(ETL etl) throws Exception {
		
		ClicksUtil.clickLinks(IClicksConst.openReportingFiledsSubmissionjLnk);
		
		ClicksUtil.clickImage(IClicksConst.associateImg);		
		
		GeneralUtil.takeANap(2.0);
		
		if(! selectFOPP(etl))
		{
			log.error("Could not select FOPP");
			
			return false;
		}
		
		GeneralUtil.takeANap(2.0);
		
		if(! selectEForm(etl))
		{
			log.error("Could not select e.Form");
			
			return false;
		}
		
		GeneralUtil.takeANap(2.0);
		
		set = etl.getMapHash().entrySet();
		
		for (Map.Entry<String, IRptFuncConst.EfieldDataTypes> entry : set) {
			
			if(!expandObjectTree(etl.getFormletIdent()))
			{
				log.error("Could not find Formlet");
				return false;
			}
			
			if(entry.getValue().toString().equals("formlet"))
			{
				etl.setFormletIdent(entry.getKey().toString());
			}
			else
			{
				etl.setEFormFieldIdent(entry.getKey().toString());
				
				addEFormField(etl.getEFormFieldIdent(),etl.getFormletIdent());
				
				collapseObjectTree(etl.getFormletIdent());		
			}
		}		
		return true;
	}
	
	public static boolean addFOPPEFormFields(ETL etl) throws Exception 
	{
		
		ClicksUtil.clickLinks(IClicksConst.openReportingFiledsFOPPLnk);
		
		ClicksUtil.clickImage(IClicksConst.associateImg);		
		
		GeneralUtil.takeANap(2.0);
		
		if(! selectFOPP(etl))
		{
			log.error("Could not select FOPP");
			
			return false;
		}
		
		GeneralUtil.takeANap(2.0);
		
		if(! selectEForm(etl))
		{
			log.error("Could not select e.Form");
			
			return false;
		}
		
		GeneralUtil.takeANap(2.0);
		
		set = etl.getMapHash().entrySet();
		
		for (Map.Entry<String, IRptFuncConst.EfieldDataTypes> entry : set) {
			
			if(!expandObjectTree(etl.getFormletIdent()))
			{
				log.error("Could not find Formlet");
				return false;
			}
			
			if(entry.getValue().toString().equals("formlet"))
			{
				etl.setFormletIdent(entry.getKey().toString());
			}
			else
			{
				etl.setEFormFieldIdent(entry.getKey().toString());
				
				addEFormField(etl.getEFormFieldIdent(), etl.getFormletIdent());
				
				collapseObjectTree(etl.getFormletIdent());		
			}
		}		
		return true;
	}
	
	public static boolean addOrgEFormFields(ETL etl) throws Exception {
		
		ClicksUtil.clickLinks(IClicksConst.openReportingFiledsOrgLnk);
		
		ClicksUtil.clickImage(IClicksConst.associateImg);		
		
		GeneralUtil.takeANap(2.0);
		
		if(! selectOrg(etl))
		{
			log.error("Could not select Org");
			
			return false;
		}
		
		GeneralUtil.takeANap(2.0);
		
		if(! selectEForm(etl))
		{
			log.error("Could not select e.Form");
			
			return false;
		}
		
		GeneralUtil.takeANap(2.0);
		
		
		set = etl.getMapHash().entrySet();
		
		for (Map.Entry<String, IRptFuncConst.EfieldDataTypes> entry : set) {
			
			if(!expandObjectTree(etl.getFormletIdent()))
			{
				log.error("Could not find Formlet");
				return false;
			}
			
			if(entry.getValue().toString().equals("formlet"))
			{
				etl.setFormletIdent(entry.getKey().toString());
			}
			else
			{
				etl.setEFormFieldIdent(entry.getKey().toString());
				
				addEFormField(etl.getEFormFieldIdent(),etl.getFormletIdent());
				
				collapseObjectTree(etl.getFormletIdent());		
			}
		}		
		return true;
	}
	
	public static boolean addOrgFormletFileds(ETL etl) throws Exception {
		
		ClicksUtil.clickLinks(IClicksConst.openReportingFieldsListLnk);
		
		ClicksUtil.clickImage(IClicksConst.newImg);
		
		Assert.assertTrue(setNewFormType(etl.getETypes()));		
		GeneralUtil.takeANap(1.0);
		
		Assert.assertTrue(setNewForm(etl.getEFormIdent()));
		GeneralUtil.takeANap(1.0);
		
		set = etl.getMapHash().entrySet();
		
		for (Map.Entry<String, IRptFuncConst.EfieldDataTypes> entry : set) {
			
			if(entry.getValue().toString().equals("formlet"))
			{
				return addAllFormletFileds(entry.getKey());
			}			
		}
		
		return false;
	}
	
	public static boolean addingApplicantReportingFieldsFromEFormfield(ETL etl) throws Exception {
		
		ClicksUtil.clickLinks(IClicksConst.openReportingFiledsApplicantLnk);
		
		ClicksUtil.clickImage(IClicksConst.associateImg);
		
		GeneralUtil.takeANap(2.0);
		
		if(! selectEForm(etl))
		{
			log.error("Could not select e.Form");
			
			return false;
		}
		
		GeneralUtil.takeANap(2.0);
		
		set = etl.getMapHash().entrySet();
		
		for (Map.Entry<String, IRptFuncConst.EfieldDataTypes> entry : set) {
			
			if(!expandObjectTree(etl.getFormletIdent()))
			{
				log.error("Could not find Formlet");
				return false;
			}
				
			if(entry.getValue().toString().equals("formlet"))
			{
				etl.setFormletIdent(entry.getKey().toString());
			}
			else
			{
				etl.setEFormFieldIdent(entry.getKey().toString());
				
				addEFormField(etl.getEFormFieldIdent(),etl.getFormletIdent());
				
				collapseObjectTree(etl.getFormletIdent());
				
			}		
				
		}		
		
		return true;
	}
	
	public static boolean addReportingFieldDetails() throws Exception {
		
		//ClicksUtil.clickLinks(IClicksConst.openReportingFiledsListLnk);
		
		ClicksUtil.clickImage(IClicksConst.addItemImg);
		
		if(!GeneralUtil.isTextFieldExistsById(IEtlConst.etlDM_RptFldIdent_TextField_Id))
		{
			return false;
		}
		
		for (String str : IEtlConst.etlDM_RepoFieldTypes) {
			
			GeneralUtil.setTextById(IEtlConst.etlDM_RptFldIdent_TextField_Id, str.replace(" ", "_"));	
			
			GeneralUtil.selectInDropdownList(IEtlConst.etlDM_RptFldType_Dropdown_Id, str);
			
			settingBusinessName(str);
			
			ClicksUtil.clickButtons(IClicksConst.saveAndAddAnotherBtn);
		}
		
		ClicksUtil.clickButtons(IClicksConst.report_BackToReportingFieldListBtn);
		
		return true;
	}
	
	public static void settingBusinessName(String strName) throws Exception {
		
		String fullName = "BN_".concat(strName);
		
		GeneralUtil.setTextById(IEtlConst.etlDM_RptFldBusinessName0_TextField_Id, fullName);
		
		GeneralUtil.setTextById(IEtlConst.etlDM_RptFldBusinessName1_TextField_Id, fullName);
		
		GeneralUtil.setTextById(IEtlConst.etlDM_RptFldBusinessName2_TextField_Id, fullName);
	}

	public static boolean importReportingFields(String reportingFields) throws Exception
	{
		IE ie = IEUtil.getActiveIE();

		if ( !ClicksUtil.clickLinks(IClicksConst.openReportingFieldsListLnk) )
		{
			log.error("Could not click on link ".concat(IClicksConst.openReportingFieldsListLnk));
			return false;
		}
		log.debug("clicked reportingFields link");

		if ( !ClicksUtil.clickImage(IClicksConst.imprtImg) )
		{
			log.error("Could not click on image ".concat(IClicksConst.imprtImg));
			return false;
		}
		log.debug("clicked import image");

		if ( !GeneralUtil.isFileFieldExistsByIndex(0) )
		{
			log.error("Could not find file field");
			return false;
		}

		ie.fileField(0).set(
			"\"" + GeneralUtil.getWorkspacePath() + IGeneralConst.xmlFilesPath + reportingFields + "\"");

		if ( !ClicksUtil.clickButtons(IClicksConst.uploadBtn) )
		{
			log.error("Could not click on button ".concat(IClicksConst.uploadBtn));
			return false;
		}

		return true;
	}
	
	public static boolean verifyReportingFieldInList(EetlRepoFieldTypes repoType) throws Exception {
		
		ClicksUtil.clickLinks(IClicksConst.openReportingFieldsListLnk);
		
		String fullRepoIdent = "DM_".concat(IEtlConst.etlDM_RepoFieldTypes[repoType.ordinal()].replace(" ", "_"));
		
		FiltersUtil.filterListByLabel(IEtlConst.etlDM_RepoFldIdent_Label, fullRepoIdent, IFiltersConst.exact);
		
		return TablesUtil.findInTable(IEtlConst.etlDM_RepoFieldList_TableId, fullRepoIdent);
	}
	
	public static boolean verifyReportingFieldInList(ETL etl) throws Exception {
		
		ClicksUtil.clickLinks(IClicksConst.openReportingFieldsListLnk);
		
		for (Map.Entry<String, IRptFuncConst.EfieldDataTypes> entry : set) {
			
			set = etl.getMapHash().entrySet();
				
			if(entry.getValue().toString().equals("formlet"))
			{
				etl.setFormletIdent(entry.getKey().toString());
			}
			else
			{
				etl.setEFormFieldIdent(entry.getKey().toString());
				
				String fullRepoIdent = etl.getRptFieldPrefix().concat(etl.getEFormFieldIdent().replace(" ", "_"));
				
				FiltersUtil.filterListByLabel(IEtlConst.etlDM_RepoFldIdent_Label, fullRepoIdent, IFiltersConst.startsWith);
				
				FiltersUtil.extraFilterListByLabel(IEtlConst.etlDM_RepoFieldGroup_Label, "", etl.getReportingFieldGroup());
				
				FiltersUtil.extraFilterListByLabel(IEtlConst.etlDM_Category_Label, "", etl.getReportingCategory());
				
				if(!TablesUtil.findInTable(IEtlConst.etlDM_RepoFieldList_TableId, fullRepoIdent))
				{
					log.error(fullRepoIdent.concat(" field not found in List"));
					
					return false;
				}

				if ( !updateBusinessName(fullRepoIdent) )
				{
					log.error("Could not update the locale's buisness name for " + fullRepoIdent);
					
					return false;
				}
			}			
		}
		return true;
	}

	public static boolean updateBusinessName(String reportIdentifier) throws Exception
	{
		if ( !ClicksUtil.clickLinksByTitle(IEtlConst.etlEditReportingField))
		{
			log.error("Could not click on link: ".concat(reportIdentifier));
			return false;
		}

		// This report identifier will be used for an unique business name.
		String businessName = reportIdentifier.replace('_', ' ').replace('-', ' ');

		// For each of the locales, update the value field with the unique business name.
		if ( !GeneralUtil.setTextById(IEtlConst.etlDM_RptFldBusinessName0_TextField_Id, businessName) )			
		{
			log.error("Could not set the business name! ".concat(businessName));
			return false;
		}

		GeneralUtil.setTextById(IEtlConst.etlDM_RptFldBusinessName1_TextField_Id, businessName);
		
		GeneralUtil.setTextById(IEtlConst.etlDM_RptFldBusinessName2_TextField_Id, businessName);
		
		GeneralUtil.setTextById(IEtlConst.etlDM_RptFldBusinessName3_TextField_Id, businessName);

		// Save the changes and return to the Reporting Field List page.
		ClicksUtil.clickButtons(IClicksConst.saveBtn);
		ClicksUtil.clickLinks(IClicksConst.openReportingFieldsListLnk);

		return true;
	}
	
	public static boolean openReportingFieldMapping(EetlRepoFieldTypes repoType) throws Exception {
		
		Div tDiv = TablesUtil.tableDiv();
		
		String fullRepoIdent = "DM_".concat(IEtlConst.etlDM_RepoFieldTypes[repoType.ordinal()].replace(" ", "_"));
		
		if(!verifyReportingFieldInList(repoType))
		{
			log.error("Could not Verify Reporting Field in List!");
			
			return false;
		}
		
		int indx = TablesUtil.getRowIndex(IEtlConst.etlDM_RepoFieldList_TableId, fullRepoIdent);
		
		if(indx < 0)
		{
			return false;
		}
		
		tDiv.body(id,IEtlConst.etlDM_RepoFieldList_TableId).row(indx).image(alt, "View Mappings - ".concat(fullRepoIdent)).click();
		
		if(!tDiv.body(id,IEtlConst.etlDM_RepoFieldMappings_TBodyID).exists())
		{
			log.error("Error occured during opening Reproting Field Mapping!");
			
			return false;
		}
		
		return true;
	}
	
	public static boolean mappReportingField(String[] str, boolean isInLists, boolean isInFolder, String eFormIdent) throws Exception {
		
		GeneralUtil.takeANap(1.0);
		
		ClicksUtil.clickImage(IClicksConst.newImg);
		
//		if(!GeneralUtil.selectFullStringInDropdownList("main:etlPlanner:_idJsp31:fundingOppDropdown", str[7]))
//		{
//			log.error("Couldn't find the Dropdown to select a FOPP!");
//			return false;
//			
//		}
		
//		if(!GeneralUtil.selectFullStringInDropdownList("main:etlPlanner:_idJsp31:organizationDropdown", str[7]))
//		{
//			log.error("Couldn't find the Dropdown to select a FOPP!");
//			return false;
//			
//		}
		
		GeneralUtil.takeANap(1.0);
		
		if(!GeneralUtil.selectFullStringInDropdownList(IEtlConst.etlEform_FormDd, eFormIdent))
		{
			log.error("Couldn't find the Dropdown to select an e.Form!");
			return false;			
		}
		
		GeneralUtil.takeANap(2.0);
		
		if(!expandObjectTree(str[8]))
		{
			log.error("no forlmet to expand!");
			return false;
		}
		
		GeneralUtil.takeANap(2.0);
		
		if(isInLists)
		{
			if(str[4].contains("Attachment"))
			{
				if(!expandObjectTree(str[4].replace("-", " ")))
				{
					log.error("no forlmet to expand!");
					return false;
				}
			}
			else if(!expandObjectTree(str[4]))
			{
				log.error("no forlmet to expand!");
				return false;
			}			
		}
		
		String newStr = "";
		
		if(str[2].equals("EmailAddress"))
		{
			newStr = "Email Address";
		}
		else
		{
			newStr = str[2];
		}
		
		if(newStr.contains("Grid"))
		{
			if(!addDataGridEFormField(newStr))
			{
				log.error("Could not add field!");
				return false;
			}
		}
		else if(!addEFormField(newStr))
		{
			log.error("Could not add field!");
			return false;
		}
		
		
		
		GeneralUtil.takeANap(2.0);		
		
		return true;
	}
	
	public static boolean selectEForm(ETL etl) throws Exception {
		
		return GeneralUtil.selectFullStringInDropdownList(IEtlConst.etlEform_FormDd, etl.getEFormIdent());
	}
	
	public static boolean selectFOPP(ETL etl) throws Exception {
		
		return GeneralUtil.selectFullStringInDropdownList(IEtlConst.etlEform_FoppDd, etl.getFoppIdent());
	}
	
	public static boolean selectOrg(ETL etl) throws Exception {
		
		return GeneralUtil.selectFullStringInDropdownList(IEtlConst.etlEform_OrgDd, etl.getOrgName());
	}
	
	public static boolean addApplicantProfileFormletFileds(ETL etl) throws Exception {
		
		ClicksUtil.clickLinks(IClicksConst.openReportingFieldsListLnk);
		
		ClicksUtil.clickImage(IClicksConst.newImg);
		
		Assert.assertTrue(setNewFormType(etl.getETypes()));		
		GeneralUtil.takeANap(2.0);
		
		Assert.assertTrue(setNewForm(etl.getEFormIdent()));
		GeneralUtil.takeANap(2.0);
		
		set = etl.getMapHash().entrySet();
		
		for (Map.Entry<String, IRptFuncConst.EfieldDataTypes> entry : set) {
			
			if(entry.getValue().toString().equals("formlet"))
			{
				return addAllFormletFileds(entry.getKey());
			}			
		}
		
		return false;
	}
	
	public static boolean setNewFormType(EetlEFormTypes eFormtype) throws Exception {
		
		return (FiltersUtil.findAndSelectDropdown(IEtlConst.editReportingFields_TableId,IEtlConst.formType_dropDown_label,IEtlConst.etlEFormTypes[eFormtype.ordinal()]));
		
	}
	
	public static boolean setNewFOPP(String foppIdent) throws Exception {		
		
		return (FiltersUtil.findAndFilterDropdown(IEtlConst.editReportingFields_TableId,IEtlConst.fundingOpp_dropDown_label,foppIdent));
	}
	
	public static boolean setNewOrg(String OrgIdent) throws Exception {		
		
		return (FiltersUtil.findAndFilterDropdown(IEtlConst.editReportingFields_TableId,IEtlConst.Organization_dropDown_label,OrgIdent));
	}
	
	public static boolean setNewForm(String formIdent) throws Exception {
		
		return (FiltersUtil.findAndFilterDropdown(IEtlConst.editReportingFields_TableId,IEtlConst.form_dropDown_label,formIdent));
	}
	
	public static boolean setTheForm(String foppIdent, String formIdent,EetlEFormTypes eFormtype,EetlFoppFormTypes eFFtypes) throws Exception {
		
		String formFullIdent = formIdent;
		
		ClicksUtil.clickLinks(IClicksConst.openReportingFieldsListLnk);
		
		ClicksUtil.clickImage(IClicksConst.newImg);
		
		Assert.assertTrue(setNewFormType(eFormtype), "FAIL: could select the Form Type :" + IEtlConst.etlEFormTypes[eFormtype.ordinal()]);
		
		GeneralUtil.takeANap(1.0);
		
		if(!foppIdent.equals(""))
		{
			Assert.assertTrue(setNewFOPP(foppIdent),"FAIL: Could Select FOPP: " + foppIdent);
			formFullIdent = setFormIdentifier(formIdent,eFFtypes);
			GeneralUtil.takeANap(1.0);
		}
		
		Assert.assertTrue(setNewForm(formFullIdent), "Could not select Form: " + formFullIdent);	
		GeneralUtil.takeANap(1.0);
		
		return true;
	}
	
	public static String setFormIdentifier(String formName,EetlFoppFormTypes eFFtypes) throws Exception {
		
		return IEtlConst.etlFoppFormTypes[eFFtypes.ordinal()].concat(formName);
	}
	
	public static boolean addReportingFieldDetails(EetlRptFldTypes rptType,EetlRepoFieldTypes fldType, String fldName, String folderName, boolean isInLists, boolean isInFolder) throws Exception {
	
		Table table = null;
		
		String rptFld;
		
		ClicksUtil.clickLinks(IEtlConst.rptFldTypes[rptType.ordinal()]);
		
		if(isInLists)
		{
			rptFld = "Lists";
		}
		else
		{
			rptFld = IEtlConst.rptFldTypes[rptType.ordinal()];
		}
		
		if(isInFolder)
		{
			manageRepotingFields(rptFld, EetlRepoFieldManagementTypes.expand);
			
			rptFld = folderName.replace(" ", "-");
		}
		
		if(!manageRepotingFields(rptFld, EetlRepoFieldManagementTypes.add))
		{
			log.error("Could not click add Icon");
			
			return false;
		}
		
		GeneralUtil.selectFullStringInDropdownList(IEtlConst.etlDM_RptFldFolder_Dropdwon_Id, folderName.replace(" ", "-"));
		
		GeneralUtil.setTextById(IEtlConst.etlDM_RptFldIdent_TextField_Id, fldName.replace(" ", "_"));
		
		GeneralUtil.selectFullStringInDropdownList(IEtlConst.etlDM_RptFldType_Dropdown_Id, IEtlConst.etlDM_RepoFieldTypes[fldType.ordinal()]);
		
		GeneralUtil.setTextById(IEtlConst.etlDM_RptFldBusinessName0_TextField_Id, fldName);
		
		if(GeneralUtil.isTextFieldExistsById(IEtlConst.etlDM_RptFldBusinessName1_TextField_Id))
		{
			GeneralUtil.setTextById(IEtlConst.etlDM_RptFldBusinessName1_TextField_Id, fldName);
		}
		
		if(GeneralUtil.isTextFieldExistsById(IEtlConst.etlDM_RptFldBusinessName2_TextField_Id))
		{
			GeneralUtil.setTextById(IEtlConst.etlDM_RptFldBusinessName2_TextField_Id, fldName);
		}
		
		ClicksUtil.clickButtons(IClicksConst.saveBtn);
		
		table = getObjectTableInReportingFiledsColumn(fldName.replace(" ", "_"));
		
		if(null == table)			
		{
			log.error("Could not create Reporting Field Details");
			
			return false;			
		}		
		
		return true;
	}
	
	public static boolean deleteObjectFromTree(EetlRptFldTypes rptType,String objName, String folderName, boolean isInLists, boolean isInFolder) throws Exception {
		
		String rptFld;
		
		ClicksUtil.clickLinks(IEtlConst.rptFldTypes[rptType.ordinal()]);
		
		if(isInLists)
		{
			rptFld = "Lists";
		}
		else
		{
			rptFld = IEtlConst.rptFldTypes[rptType.ordinal()];
		}
		
		if(!manageRepotingFields(rptFld, EetlRepoFieldManagementTypes.expand))
		{
			log.error("Could not expand Object ".concat(rptFld));
			
			return false;
		}
		
		if(isInFolder)
		{			
			rptFld = folderName.replace(" ", "-");
			
			if(!manageRepotingFields(rptFld, EetlRepoFieldManagementTypes.expand))
			{
				log.error("Could not expand Object ".concat(rptFld));
				
				return false;
			}
		}
		
		if(!manageRepotingFields(objName.replace(" ", "_"), EetlRepoFieldManagementTypes.delete))
		{
			log.error("Could not delete Object ".concat(objName));
			
			return false;
		}
		
		return true;
	}
	
	public static boolean createFolder(EetlRptFldTypes rptType, String folderName, boolean isInLists) throws Exception {
		
		Table table = null;
		
		String rptFld;
		
		ClicksUtil.clickLinks(IEtlConst.rptFldTypes[rptType.ordinal()]);
		
		if(isInLists)
		{
			rptFld = "Lists";
		}
		else
		{
			rptFld = IEtlConst.rptFldTypes[rptType.ordinal()];
		}
		
		if(!manageRepotingFields(rptFld, EetlRepoFieldManagementTypes.folder))
		{
			log.error("Could not click folder Icon");
			
			return false;
		}
		
		GeneralUtil.setTextById(IEtlConst.etlDM_RptFolderIdent_TextField_Id, folderName.replace(" ", "-"));
		
		GeneralUtil.setTextById(IEtlConst.etlDM_RptFolderBusinessName0_TextField_Id, folderName);
		
		if(GeneralUtil.isTextFieldExistsById(IEtlConst.etlDM_RptFolderBusinessName1_TextField_Id))
		{			
			GeneralUtil.setTextById(IEtlConst.etlDM_RptFolderBusinessName1_TextField_Id, folderName);			
		}
		
		if(GeneralUtil.isTextFieldExistsById(IEtlConst.etlDM_RptFolderBusinessName2_TextField_Id))
		{			
			GeneralUtil.setTextById(IEtlConst.etlDM_RptFolderBusinessName2_TextField_Id, folderName);			
		}
		
		ClicksUtil.clickButtons(IClicksConst.saveBtn);
		
		
		table = getObjectTableInReportingFiledsColumn(folderName.replace(" ", "-"));
		
		if(null == table)
		{
			log.error("Folder haven't been created");
			
			return false;
		}	
		
		return true;
	}
	
	public static boolean manageRepotingFields(String obj, EetlRepoFieldManagementTypes manageType) throws Exception {
		
		Table table = getObjectTableInReportingFiledsColumn(obj);
		
		log.warn(table.id());
		
		Image img;
		
		GeneralUtil.takeANap(1.0);
		
		if(null != table)
		{
			
			switch (manageType) {
			
			case folder:
				
				if(table.image(src,"/images/icons/add_folder.gif/").exists())
				{
					img = table.image(src,"/images/icons/add_folder.gif/");
					
					img.click();
					
					return true;
				}				
				return false;
			
			case add:
				
				if(table.image(src,IClicksConst.addItemImg).exists())
				{
					img = table.image(src,IClicksConst.addItemImg);
					
					img.click();
					
					return true;
				}				
				return false;
			
			case create:
				
				if(table.image(src,IClicksConst.associateImg).exists())
				{
					img = table.image(src,IClicksConst.associateImg);
					
					img.click();
					
					return true;
				}				
				return false;
			
			case delete:
				
				Thread dialogClicker = new Thread() {
					@SuppressWarnings("static-access")
					@Override
					public void run() {
						try {
							IE ie = IEUtil.getActiveIE();
							ConfirmDialog dialog1 = ie.confirmDialog();
							while (dialog1 == null) {
								log.debug("can't yet get handle on confirm dialog1");
								this.sleep(250);
							}

							dialog1.ok();
							log.debug("got confirm Dialog1 and clicked OK.");
						} catch (Exception e) {
							throw new RuntimeException("Unexpected exception", e);
						}
					}
				};
				
				dialogClicker.start();
				log.debug("started dialog clicker thread");
				
				table.image(src, IClicksConst.deleteImg).click();
				
				GeneralUtil.takeANap(1.000);
				
				dialogClicker = null;
				
				return true;
			
			case edit:
				
				table.image(src, IClicksConst.detailImg).click();
				
				return true;
			
			case show:
				
				table.image(src, "images/icons/list.gif").click();
				
				return true;
			
			case expand:
				
//				img = table.image(src,"/-line-/");
//			
//				if(img.src().contains("nav-minus-line-"))
//				{
//					return true;
//				}
//				else if(img.src().contains("nav-plus-line-"))
//				{
//					img.click();
//					
//					return true;
//				}
				expandObjectTree(obj);
				
				return true;
			
			case collapse:
				
//				img = table.image(src,"/-line-/");
//				
//				if(img.src().contains("nav-plus-line-"))
//				{
//					return true;
//				}
//				else if(img.src().contains("nav-minus-line-"))
//				{
//					img.click();
//					
//					return true;
//				}
				collapseObjectTree(obj);
				
				break;

			default:
				
				break;
			}			
		}
		
		return false;
	}
	
	public static Table getObjectTableInReportingFiledsColumn(String obj) throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
		log.debug(obj);
		
		if(ie.span(id,IEtlConst.plannerTree_spanId).exists()) {
			
			for (Table table : ie.span(id,IEtlConst.plannerTree_spanId).tables()) {
				
				log.debug(table.innerText());				
				
				if (table.innerText().endsWith(obj) || table.innerText().startsWith(obj) )
				{
					return table;
				}				
			}			
		}		
		return null;
	}
	
	public static Table getObjectTable(String obj) throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
		log.debug(obj);
		
		if(ie.span(id,IEtlConst.plannerEFormfieldTree_spanId).exists()) {
			
			for (Table table : ie.span(id,IEtlConst.plannerEFormfieldTree_spanId).tables()) {
				
				log.debug(table.innerText());				
				
				if (table.innerText().endsWith(obj) || table.innerText().startsWith(obj) )
				{
					return table;
				}				
			}			
		}		
		return null;
	}
	
	public static Table getDataGridObjectTable(String obj) throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
		log.debug(obj);
		
		if(ie.span(id,IEtlConst.plannerEFormfieldTree_spanId).exists()) {
			
			for (Table table : ie.span(id,IEtlConst.plannerEFormfieldTree_spanId).tables()) {
				
				log.debug(table.innerText());				
				
				if (table.innerText().endsWith(obj) || table.innerText().startsWith(obj) )
				{
					return table;
				}				
			}			
		}		
		return null;
	}
	
	public static HtmlElement getListIdFromPlanner(String startId, String innerText) throws Exception {

		IE ie = IEUtil.getActiveIE();

		HtmlElements eles = ie.htmlElement(id, startId).htmlElements(tag, "li");

		for (HtmlElement ele : eles) {

			if(ele.innerText().trim().startsWith("Formlet: " + innerText)) {

				return ele;
			}			
		}
		return null;
	}
	
	public static boolean isExpanded(String sId) throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
		Spans sp =  ie.htmlElement(id, sId).spans(attribute("class", IClicksConst.expandedSpan));
		
		for (Span span : sp) {
			
			if (span.exists())
				
			return true;
		}
				
		return false;
	}
	
	public static boolean expandObjectTree(String obj) throws Exception {
		
		IE ie = IEUtil.getActiveIE();

		HtmlElement sId = getListIdFromPlanner(IEtlConst.plannerEformField_TopLi_Id, obj);

		if (!isExpanded(sId.id())){

			Spans sp =  ie.htmlElement(id, sId.id()).spans();

			sp.span(1).click();		

		}	else {
			log.warn("Object " + obj + " already expanded!");
		}
		return true;		
	
//		Table table = getObjectTable(obj);
//		
//		if(table != null)
//		{
//			Image img = table.image(src,"/-line-/");
//			
//			if(img.src().contains("nav-minus-line-"))
//			{
//				return true;
//			}
//			else if(img.src().contains("nav-plus-line-"))
//			{
//				img.click();
//				
//				return true;
//			}
//		}		
//		
//		return false;		
	}
	
	public static boolean collapseObjectTree(String obj) throws Exception {
					
			IE ie = IEUtil.getActiveIE();

			HtmlElement sId = getListIdFromPlanner(IEtlConst.plannerEformField_TopLi_Id, obj);

			if (isExpanded(sId.id())){

				Spans sp =  ie.htmlElement(id, sId.id()).spans();

				sp.span(1).click();		

			}	
			
			return true;	
			
//			Table table = getObjectTable(obj);
//		
//		if(table != null)
//		{
//			Image img = table.image(src,"/-line-/");
//			
//			if(img.src().contains("nav-plus-line-"))
//			{
//				return true;
//			}
//			else if(img.src().contains("nav-minus-line-"))
//			{
//				img.click();
//				
//				return true;
//			}
//		}		
//		
//		return false;		
	}
	
	public static boolean addEFormField(String eFormFieldIdent, String formletIdent) throws Exception {

		HtmlElement sId = getListIdFromPlanner(IEtlConst.plannerEformField_TopLi_Id, formletIdent);
		
		if(null == sId)
		{
			log.error("can't get List Object");
			return false;
		}
		
		HtmlElement el = sId.htmlElement(tag, "ul");
		
		if(null == el)
		{
			log.error("can't get List Object");
			return false;
		}
		
		HtmlElements eles = el.htmlElements(tag, "li");
		
		if(null == eles)
		{
			log.error("can't get List Objects");
			return false;
		}
		
		log.warn(eles.elements().size());
		
		log.warn(eles.get(0).innerText());
		
		for (HtmlElement ele : eles) {
			
			log.warn(ele.innerText().trim());
			
			if((ele.innerText().trim().endsWith(eFormFieldIdent)) || (ele.innerText().trim().startsWith(eFormFieldIdent)) )
			{
				ele.link(0).click();
				GeneralUtil.takeANap(1.0);
				return true;
			}
			
		}
		
		
		log.error("Could not find the eformfield to be mapped");
		
		return false;
	}
	
	public static boolean addEFormField(String eFormFieldIdent) throws Exception {
		
		Table table = getObjectTable(eFormFieldIdent);
		
		if(null == table)
		{
			log.error("Table Object is Null");
			return false;
		}
		
		if(!table.image(src, IClicksConst.addItemImg).exists())
		{
			log.error("Image Object is Null");
			return false;
		}
		
		table.image(src, IClicksConst.addItemImg).click();
		
		GeneralUtil.takeANap(1.0);
		
		log.debug("Clicked ".concat(eFormFieldIdent));
		
		return true;
	}
	
	public static boolean addDataGridEFormField(String eFormFieldIdent) throws Exception {
		
		String cellIndex = eFormFieldIdent.substring(eFormFieldIdent.length() - 2);
		
		String newStr = eFormFieldIdent.replace(" ", "-");
		
		String finalStr = newStr.replace("-".concat(cellIndex), "");
		
		TableBody tableBody = getObjectTable("Data-Grid: ".concat(finalStr));
		
		if(null == tableBody)
		{
			log.error("Table Object is Null");
			return false;
		}
		
		if(!tableBody.image(src, IClicksConst.addGridItemImg).exists())
		{
			log.error("Image Object is Null");
			return false;
		}
		
		tableBody.image(src, IClicksConst.addGridItemImg).click();
		
		GeneralUtil.takeANap(1.5);
		
		tableBody = TablesUtil.getTableBodyById("main:etlPlanner:_idJsp31:dataGridPreview");
		
		tableBody.image(alt, "Select Grid Cell: ".concat(cellIndex)).click();
		
		GeneralUtil.takeANap(1.0);
		
		log.debug("Clicked ".concat(eFormFieldIdent));
		
		return true;
	}
	
	public static boolean addAllFormletFileds(String formletIdent) throws Exception {
		
		Table table = getObjectTable(formletIdent);
		
		if(table !=null)
		{
			Image img = table.image(alt,"Quick add all formlet fields: " + formletIdent);
			
			if(img != null)
			{
				Thread dialogClicker = new Thread() {
					@SuppressWarnings("static-access")
					@Override
					public void run() {
						try {
							IE ie = IEUtil.getActiveIE();
							ConfirmDialog dialog1 = ie.confirmDialog();
							while (dialog1 == null) {
								log.debug("can't yet get handle on confirm dialog1");
								this.sleep(250);
							}

							dialog1.ok();
							log.debug("got confirm Dialog1 and clicked OK.");
						} catch (Exception e) {
							throw new RuntimeException("Unexpected exception", e);
						}
					}
				};
				
				dialogClicker.start();
				log.debug("started dialog clicker thread");
				
				img.click();	
				
				GeneralUtil.takeANap(1.000);			
				
				dialogClicker = null;
				
				return true;
			}
		}			
		
		return false;
	}
	
	public static boolean verifyFiledsInList(ETL etl) throws Exception {
		
		ClicksUtil.clickLinks(IClicksConst.openReportingFieldsListLnk);
		
		set = etl.getMapHash().entrySet();
		
		for (Map.Entry<String, IRptFuncConst.EfieldDataTypes> entry : set) {
			
			if(entry.getValue().toString().equals("formlet"))
			{
				etl.setFormletIdent(entry.getKey().toString());
			}
			else
			{
				etl.setEFormFieldIdent(entry.getKey().toString());
				
				filterReportFieldsList(etl);
				
				return isFieldExists(etl);
				
			}		
			
		}
		return false;
	}
	
	public static boolean isFieldExists(ETL etl) throws Exception {
		
		return TablesUtil.findInTable(IEtlConst.reportingFieldList_TableId, etl.getEFormFieldIdent());
	}
	
	public static boolean openReportingFieldDetails(ETL etl) throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
		if(verifyFiledsInList(etl)) 
		{
			ie.table(id,IEtlConst.reportingFieldList_TableId).link(title, "View - ".concat(etl.getEFormFieldIdent())).click();
			
			return true;
		}		
		
		return false;
	}
	
	public static void filterReportFieldsList(ETL etl) throws Exception {
		
		Hashtable<String, String> ht = new Hashtable<String,String>();
		
		Hashtable<String, String> htDD = new Hashtable<String,String>();
		
		ht.put(IFiltersConst.administration_ReportingIdent_Lbl,etl.getEFormFieldIdent());
		
		if(etl.getReportingIdent() !=null) 
		{
			ht.put(IFiltersConst.administration_ReportingIdent_Lbl,etl.getReportingIdent());
		}
		
		if(etl.getReportingStatus() !=null) 
		{
			htDD.put(IFiltersConst.administration_ReportingStatus_Lbl,etl.getReportingStatus());
		}
		
		
		FiltersUtil.filterListByLabel(ht, htDD, false);
			 
	}
	
	public static Table getMainTableInReportingTables() throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
		Form form = ie.form(id, IEtlConst.etl_RTM_FormId);
		
		for (Table table : form.tables()) {			
			
			if(table.innerText().startsWith(IEtlConst.etl_RTM)) 
			{
				return table;
			}			
		}
		
		return null;
	}
	
	public static Table getMainTableInReportingTablesForRefresh() throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
		Form form = ie.form(id, IEtlConst.etl_RTM_FormId);
		
		for (Table table : form.tables()) {			
			
			if(table.innerText().startsWith(IEtlConst.etl_RTM_ReportingTableRefresh_StartWith)) 
			{
				return table;
			}			
		}
		
		return null;
	}
	
	public static Table getSubTableInReportingTable(Table table, String startingWith) throws Exception {
		
		Assert.assertNotNull(table, "FAIL: the Object Table is Null");
		
		for (Table tbl : table.tables() ) {
			
			if(tbl.innerText().startsWith(startingWith))
			{
				return tbl;
			}			
		}
		
		return null;
	}
	
	public static String getLastRebuildStatus() throws Exception {
		
		String emptyString = "";
		
		IE ie = IEUtil.getActiveIE();
		
		Table table = ie.table(id, "j_id_1y:j_id_2i");
		
		
		for (TableRow row : table.rows()) {
			
			if(row.innerText().startsWith("Last Rebuild Status"))
			{
				return row.cell(1).innerText();
			}			
		}
		
		return emptyString;
	}
	
	public static String getLastExecuteStatus() throws Exception {
		
		String emptyString = "";
		
		IE ie = IEUtil.getActiveIE();
		
		Table table = ie.table(id, "j_id_1y:j_id_2v");
		
		for (TableRow row : table.rows()) {
			
			if(row.innerText().startsWith("Last Execute Status"))
			{
				return row.cell(1).innerText();
			}			
		}
		
		return emptyString;
	}
	
	public static boolean rebuildDatamart() throws Exception {
		
		ClicksUtil.clickLinks(IClicksConst.openReportingTablesList);
		
		ClicksUtil.clickButtons(IClicksConst.report_RebuildBtn);
		
		while(getLastRebuildStatus().equalsIgnoreCase("In Progress"))
		{
			log.debug("waiting to Rebuild Datamart!");
		}
		
		if(getLastRebuildStatus().equalsIgnoreCase("Completed Successfully"))
		{
			return true;
		}
		
		log.error(getLastRebuildStatus());
		
		return false;
	}
	
	public static boolean executeDatamart() throws Exception {
		
		ClicksUtil.clickLinks(IClicksConst.openReportingTablesList);
		
		ClicksUtil.clickButtons(IClicksConst.report_ExecuteBtn);
		
		while(getLastExecuteStatus().equalsIgnoreCase("In Progress"))
		{
			log.debug("waiting to Execute Datamart!");
		}
		
		if(getLastExecuteStatus().equalsIgnoreCase("Completed Successfully"))
		{
			return true;
		}
		
		log.error(getLastExecuteStatus());
		
		return false;
	}
}
