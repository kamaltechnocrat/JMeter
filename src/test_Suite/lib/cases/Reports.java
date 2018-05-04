package test_Suite.lib.cases;

import static watij.finders.SymbolFactory.*;
import watij.dialogs.ConfirmDialog;
import watij.elements.Div;
import watij.runtime.ie.IE;
import test_Suite.constants.ui.*;
import test_Suite.constants.cases.*;
import test_Suite.constants.eForms.IEFormsConst;
import test_Suite.utils.ui.*;
import test_Suite.utils.cases.*;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.Assert;

public class Reports
{
	
	public String reportLetter = null;	
	private String accessUser[];	
	private String ParamType[] = null;	
	private String ParamName[] = null;	
	private String ParamLabels[] = null;
	private String lookupName = null;	
	private boolean retValue = false;	
	private String paramsValues[] = null;	
	private String reportViewerFullPath = null;
	private String reportTitle = null;
	private String reportStatus = null;
	
	private static Log log = LogFactory.getLog(Reports.class);  

	 /************************
	 * Added by Alex Pankov - to add one Report of any kind with settable parameters
	 * ***********************/
	
	private String reportIdent    =  null;	
	private String reportOrgName  =  null;	
	private String reportOrgAccess = null;	
	private String reportName     =  null;		
	private String [] reportTitles = null;
	private String reportType = null;
	
	/***************************************
	 * To be Used ETL Reproting Config.
	 ******************************************/
	
	// End of added variables
	
	/**
	 * @return the reportType
	 */
	public String getReportType() {
		return reportType;
	}


	/**
	 * @param reportType the reportType to set
	 */
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}


	private ArrayList<String> arrList;
	
	public Reports()
	{
		
		
	}
	
	
	public void initReprots() throws Exception
	{		
		Properties p = new Properties();
		
		p.load(new FileInputStream(new File("src/test_Suite/deployment_path.properties")));
		
		String reportBaseUrl = p.getProperty("reportBaseUrl");
		
		setReportViewerFullPath(reportBaseUrl + IReportsConst.reportViewer);
		
	}
	
	
	public void addNewReport(int reportNameIndex, String[] reportNames) throws Exception
	{
		
		IE ie = IEUtil.getActiveIE();
		
		ClicksUtil.clickLinks(IClicksConst.openReportsList);
		
		ClicksUtil.clickImage(IClicksConst.newImg);
		
		//this.setReportName(reportNames[reportNameIndex]);
		//ie.textField(id, "/reportIdentifier/").set(getReportLetter() + IReportsConst.reportId);
		
		ie.textField(id, "/reportIdentifier/").set(this.getReportName());
		
		setReportIdent(getReportLetter() + IReportsConst.reportId);
		
		ie.selectList(id, "/reportStatus/").select(IFiltersConst.reportStatusActive);
		
		ie.selectList(id, "/reportType/").select(IReportsConst.reportType);
		
		ie.textField(id, "/properties:0:lengthlimitedtextfield/").set(getReportViewerFullPath());
		
		ie.textField(id,"/properties:1:lengthlimitedtextfield/").set(this.getReportName());
		
		ie.selectList(id, "/primaryOrganization/").select(this.getReportOrgName());
		
		ie.selectList(id, "/organizationAccess/").select(this.getReportOrgAccess());
		
		
		
		ie.textField(id, "/locales:0:reportTitle/").set(this.getReportTitle());
		
		if (ie.textField(id, "/locales:1:reportTitle/").exists())
		{
			ie.textField(id, "/locales:1:reportTitle/").set(this.getReportTitle());
		}
		
		if (ie.textField(id, "/locales:2:reportTitle/").exists())
		{
			ie.textField(id, "/locales:2:reportTitle/").set(this.getReportTitle());
		}		
		
		ClicksUtil.clickButtons(IClicksConst.saveBtn);		
		
		ClicksUtil.clickButtons(IClicksConst.reportPlannerBtn);			
		
	}
	/*
	 * **********************
	 * Added by Alex Pankov - to add one Report of any kind with settable parameters
	 * **********************
	 */
	public boolean addOneNewReport() throws Exception
	{
		IE ie = IEUtil.getActiveIE();
		
		ClicksUtil.clickLinks(IClicksConst.openReportsList);
		if(GeneralUtil.isImageExistsBySrc(IClicksConst.newImg))
		{
			ClicksUtil.clickImage(IClicksConst.newImg);
			ie.textField(id, IReportsConst.report_Ident).set(this.reportIdent);
			ie.selectList(id, IReportsConst.report_Status).select(IFiltersConst.reportStatusActive);
			ie.selectList(id, IReportsConst.report_Type).select(IReportsConst.reportType);
			ie.textField(id, IReportsConst.report_URLStart_Id + 0 + IReportsConst.report_URLEnd_Id).set(this.reportViewerFullPath);
			ie.textField(id, IReportsConst.report_URLStart_Id + 1 + IReportsConst.report_URLEnd_Id).set(this.reportName);
			ie.selectList(id, IEFormsConst.formPrimaryOrg_DropdownField_Id).select(this.reportOrgName);
			if(reportOrgAccess != null)
			{
				ie.selectList(id, IEFormsConst.formOrgAccess_DropdownField_Id).select(this.reportOrgAccess);
			}			
			
				for(int i=0; i<3; i++)
				{
					if (ie.textField(id, IReportsConst.report_LocaleStart_Id + i + IReportsConst.report_LocaleEnd_Id).exists())
					{
						ie.textField(id, IReportsConst.report_LocaleStart_Id + i + IReportsConst.report_LocaleEnd_Id).set(this.reportTitle);
					}
				}
			
			ClicksUtil.clickButtons(IClicksConst.saveBtn);
			
			GeneralUtil.takeANap(1.0);
			
			if(ie.span(id, IReportsConst.report_Msg).exists())
			{
				log.info("Unable to add new Report. Report with the same name may be existing, or some report parameters are missing");
								
				return false;
			}
			
			ClicksUtil.clickButtons(IClicksConst.backBtn);
			
			return true;
		}
		
		return false;
		
	}
	
	public void allowReportAccess() throws Exception
	{
		IE ie = IEUtil.getActiveIE();
		int ind = -1;
		
		Div tDiv = TablesUtil.tableDiv();
		
		if (accessUser != null && accessUser.length > 0)
		{
			for(int i=0; i<reportTitles.length; i++)
			{
				log.info("Title: " + reportTitles[i]);
				ind = TablesUtil.getRowIndex(ITablesConst.reprotsTableId, reportTitles[i]);
				log.info("Row index " + ind);
				if(ind > -1)
				{
					tDiv.body(id, ITablesConst.reprotsTableId).row(ind).cell(IReportsConst.eReportsGridFields.reportTitle.ordinal()).link(0).click();
					ClicksUtil.clickImageByAlt(IReportsConst.report_ManageReportAccess);
					for(int j=0; j < this.accessUser.length; j++)
					{
						try
						{
							ie.selectList(0).select(this.accessUser[j]);
							ClicksUtil.clickButtons(IClicksConst.m2MSingleForBtn);
						}
						catch(Exception notFound)
						{	
							log.debug("User  " + this.accessUser[j] + " not found in the list " + notFound.getMessage());
						}
					}
					ClicksUtil.clickButtons(IClicksConst.saveBtn);
					ClicksUtil.clickButtons(IClicksConst.backBtn);
					ClicksUtil.clickButtons(IClicksConst.backBtn);
					break;
				}
			}
		}
	}
	
	public boolean fillReportDetailsFields() throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
		retValue = false;
		arrList = null;
		
		ClicksUtil.clickLinks(IClicksConst.openReportsList);
		
		if(GeneralUtil.isImageExistsBySrc(IClicksConst.newImg))
		{
			ClicksUtil.clickImage(IClicksConst.newImg);
			
			if(ie.textField(id, IReportsConst.report_Ident).exists())
			{
				ie.textField(id, IReportsConst.report_Ident).set(this.reportIdent);
				
				retValue = true;
			}			
			
			
			ie.selectList(id, IReportsConst.report_Status).select(IFiltersConst.reportStatusActive);
			
			if(ie.selectList(id, IReportsConst.report_Type).exists())
			{
				ie.selectList(id, IReportsConst.report_Type).select(IReportsConst.reportType);
				
				retValue = true;
			}
			else
			{
				retValue = false;
			}
			
			if(!this.getReportViewerFullPath().contentEquals(""))
			{
				ie.textField(id, IReportsConst.report_URLStart_Id + 0 + IReportsConst.report_URLEnd_Id).set(this.getReportViewerFullPath());
			}
			
			if(!this.getReportName().contentEquals(""))
			{
				ie.textField(id, IReportsConst.report_URLStart_Id + 1 + IReportsConst.report_URLEnd_Id).set(this.getReportName());
			}			
			
			if(!this.getReportOrgName().contentEquals(""))
			{
				ie.selectList(id, IEFormsConst.formPrimaryOrg_DropdownField_Id).select(this.getReportOrgName());
			}
			
			if(!this.getReportTitle().contentEquals(""))
			{
				
				for(int i=0; i<3; i++)
				{
					if (ie.textField(id, IReportsConst.report_LocaleStart_Id + i + IReportsConst.report_LocaleEnd_Id).exists())
					{
						ie.textField(id, IReportsConst.report_LocaleStart_Id + i + IReportsConst.report_LocaleEnd_Id).set(this.getReportTitle());
					}
				}
				
			}
			
			ClicksUtil.clickButtons(IClicksConst.saveBtn);
			
			arrList = GeneralUtil.checkForErrorMessages();
			
			if(arrList != null && !arrList.isEmpty())
			{
				for(String string : arrList)
				{
					log.error("Validation Error In Report Paramaters: " + string);
				}
				
				retValue =  false;
			}				
		}
		
		return retValue;
	}
	
	public boolean editReportDetails() throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
		retValue = true;
		
		ClicksUtil.clickLinks(IClicksConst.openReportsList);
		
		Assert.assertTrue(ReportsUtil.isReportInReportsList(this), "FAIL: Could not find report In list");
		
		ClicksUtil.clickLinks(this.getReportIdent());
		
		Assert.assertTrue(ReportsUtil.openReportDetails(), "FAIL: Could not open Report details");
		
		if(ie.textField(id, IReportsConst.report_Ident).exists())
		{
			ie.textField(id, IReportsConst.report_Ident).set(this.reportIdent);
			
			retValue = false;
		}	
		
		if(ie.selectList(id, IReportsConst.report_Status).exists() && !this.getReportStatus().contentEquals(""))
		{
			ie.selectList(id, IReportsConst.report_Status).select(this.getReportStatus());
		}		
		
		if(ie.selectList(id, IReportsConst.report_Type).exists())
		{
			ie.selectList(id, IReportsConst.report_Type).select(IReportsConst.reportType);
			retValue = false;
		}
		
		if(!this.getReportViewerFullPath().contentEquals(""))
		{		
			ie.textField(id, IReportsConst.report_URLStart_Id + 0 + IReportsConst.report_URLEnd_Id).set(this.getReportViewerFullPath());
			
		}
		
		if(!this.getReportName().contentEquals(""))
		{
			ie.textField(id, IReportsConst.report_URLStart_Id + 1 + IReportsConst.report_URLEnd_Id).set(this.getReportName());
		}	
		
		if(!this.getReportOrgName().contentEquals(""))
		{
			ie.selectList(id, IEFormsConst.formPrimaryOrg_DropdownField_Id).select(this.reportOrgName);
		}
		
		if(!this.getReportTitle().contentEquals(""))
		{
			
			for(int i=0; i<3; i++)
			{
				if (ie.textField(id, IReportsConst.report_LocaleStart_Id + i + IReportsConst.report_LocaleEnd_Id).exists())
				{
					ie.textField(id, IReportsConst.report_LocaleStart_Id + i + IReportsConst.report_LocaleEnd_Id).set(this.getReportTitle());
				}
			}
			
		}
		
		ClicksUtil.clickButtons(IClicksConst.saveBtn);
		
		arrList = GeneralUtil.checkForErrorMessages();
		
		if(arrList != null && !arrList.isEmpty())
		{
			for(String string : arrList)
			{
				log.error("Validation Error In Report Paramaters: " + string);
			}
			
			retValue =  false;
		}	
		
	
		
		return retValue;
	}
	
	//****************************
	// end of added methods
	//****************************
	
	/**
	 * @deprecated Use {@link #addReportParamsAndAccess(boolean,boolean)} instead
	 */
	public boolean addReportParams(boolean userAccess) throws Exception
	{
		return addReportParamsAndAccess(userAccess, true);
	}
	
	public boolean editReportParameters() throws Exception {
		
		IE ie = IEUtil.getActiveIE();		
		
		
		ie.textField(id, "/reportParameterName/").set(this.ParamName[0]);
		
		if(!ie.selectList(id, "/reportParameterType/").exists())
		{
			return false;
		}
		
		ie.selectList(id, "/reportParameterType/").select(this.ParamType[0]);
		
		GeneralUtil.takeANap(0.500);
		
		if(ie.selectList(id, "/lookupType/").exists() && this.lookupName != null)
		{
			ie.selectList(id, "/lookupType/").select(this.lookupName);
		}
		
		ie.textField(id, "/locales:0:reportParameterLabel/").set(this.ParamLabels[0]);
		
		if (ie.textField(id, "/locales:1:reportParameterLabel/").exists())
		{
			ie.textField(id, "/locales:1:reportParameterLabel/").set(this.ParamLabels[0]);
		}
		
		if (ie.textField(id, "/locales:2:reportParameterLabel/").exists())
		{
			ie.textField(id, "/locales:2:reportParameterLabel/").set(this.ParamLabels[0]);
		}
		
		ClicksUtil.clickButtons(IClicksConst.saveBtn);
		
		return false;
	}
	
	public boolean fillReportParameters() throws Exception {
		
		IE ie = IEUtil.getActiveIE();

		ClicksUtil.clickImage("images/icons/add_items.gif");
		
		ie.textField(id, "/reportParameterName/").set(this.ParamName[0]);
		
		ie.selectList(id, "/reportParameterType/").select(this.ParamType[0]);
		GeneralUtil.takeANap(0.500);
		if(ie.selectList(id, "/lookupType/").exists() && this.lookupName != null)
		{
			ie.selectList(id, "/lookupType/").select(this.lookupName);
		}
		
		ie.textField(id, "/locales:0:reportParameterLabel/").set(this.ParamLabels[0]);
		
		if (ie.textField(id, "/locales:1:reportParameterLabel/").exists())
		{
			ie.textField(id, "/locales:1:reportParameterLabel/").set(this.ParamLabels[0]);
		}
		
		if (ie.textField(id, "/locales:2:reportParameterLabel/").exists())
		{
			ie.textField(id, "/locales:2:reportParameterLabel/").set(this.ParamLabels[0]);
		}
		
		ClicksUtil.clickButtons(IClicksConst.saveBtn);
		

		
		arrList = GeneralUtil.checkForErrorMessages();
		
		if(arrList == null || arrList.isEmpty())
		{
			return true;
		}
		else
		{
			for(String string : arrList)
			{
				log.error("Validation Error In Report Paramaters: " + string);
			}
		}		
		return false;
	}


	public boolean addReportParamsAndAccess(boolean userAccess, boolean isParamUsed) throws Exception
	{
		
		IE ie = IEUtil.getActiveIE();		
		retValue = false;		
		int rowIndx = -1;
		
		ClicksUtil.clickLinks(IClicksConst.openReportsList);
		
		FiltersUtil.filterListByLabel(IFiltersConst.administration_ReportIdent_Lbl, this.getReportName(), IFiltersConst.exact);		
		
		rowIndx = TablesUtil.getRowIndex(ITablesConst.reprotsTableId, this.getReportName());
		
		Div tDiv = TablesUtil.tableDiv();
		
		if (rowIndx > -1)
		{
			tDiv.body(id, ITablesConst.reprotsTableId).row(rowIndx).cell(1).link(0).click();			
			if (userAccess)
			{
				ie.image(alt, "Manage Report Access").click();				
				for(int i=0; i < this.accessUser.length; i++)
				{
					ie.selectList(0).select(this.accessUser[i]);					
					ClicksUtil.clickButtons(IClicksConst.m2MSingleForBtn);
				}				
				ClicksUtil.clickButtons(IClicksConst.saveBtn);				
				ClicksUtil.clickButtons(IClicksConst.backBtn);		
				retValue = true;
			}
			
		if(isParamUsed)
		{
			for(int i=0; i < this.ParamType.length; i++)
			{
				retValue = false;
				ClicksUtil.clickImage("images/icons/add_items.gif");
				
				ie.textField(id, "/reportParameterName/").set(this.ParamName[i]);
				
				ie.selectList(id, "/reportParameterType/").select(this.ParamType[i]);
				GeneralUtil.takeANap(0.500);
				if(ie.selectList(id, "/lookupType/").exists() && this.lookupName != null)
				{
					ie.selectList(id, "/lookupType/").select(this.lookupName);
				}
				
				ie.textField(id, "/locales:0:reportParameterLabel/").set(this.ParamLabels[i]);
				
				if (ie.textField(id, "/locales:1:reportParameterLabel/").exists())
				{
					ie.textField(id, "/locales:1:reportParameterLabel/").set(this.ParamLabels[i]);
				}
				
				if (ie.textField(id, "/locales:2:reportParameterLabel/").exists())
				{
					ie.textField(id, "/locales:2:reportParameterLabel/").set(this.ParamLabels[i]);
				}
				
				ClicksUtil.clickButtons(IClicksConst.saveBtn);			
				ClicksUtil.clickButtons(IClicksConst.backBtn);				
				
				retValue = true;
			}		
		}
			
			
		}		
		
		return retValue;
	}
	
	public boolean addUsersAndGroupToReportAccess() throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
		ie.image(alt, "Manage Report Access").click();
		
		for(int i=0; i < this.accessUser.length; i++)
		{
			ie.selectList(0).select(this.accessUser[i]);					
			ClicksUtil.clickButtons(IClicksConst.m2MSingleForBtn);
		}				
		ClicksUtil.clickButtons(IClicksConst.saveBtn);				
		ClicksUtil.clickButtons(IClicksConst.backBtn);		
		
		
		return true;
	}
	
	public boolean doesGroupsExistsInReportAccessM2M(String m2mList, String groupAccess) throws Exception {
		IE ie = IEUtil.getActiveIE();
		ie.image(alt, "Manage Report Access").click();
		if(GeneralUtil.isObjectExistsInList(m2mList, groupAccess))
		{			
			ClicksUtil.clickButtons(IClicksConst.backBtn);
			
			return true;
			
		}
		
		return false;
		
		
	}
	
	
	 
	
	public boolean openReportPlanner() throws Exception {

		int rowIndx = -1;
		
		ClicksUtil.clickLinks(IClicksConst.openReportsList);	
		
		FiltersUtil.filterListByLabel(IFiltersConst.administration_ReportIdent_Lbl, this.getReportName(),IFiltersConst.exact);
		
		rowIndx = TablesUtil.getRowIndex(ITablesConst.reprotsTableId, this.getReportName());
		
		Div tDiv = TablesUtil.tableDiv();
		
		if (rowIndx > -1)
		{
			tDiv.body(id, ITablesConst.reprotsTableId).row(rowIndx).cell(1).link(0).click();
			return true;
		}
		return false;
	}
	
	public boolean lanchReportWithoutParameters() throws Exception {
			
		int rowIndx = -1;
		
		ClicksUtil.clickLinks(IClicksConst.openMyReportsListLnk);
		
		FiltersUtil.filterListByLabel(IFiltersConst.grantManagement_ReportName_Lbl, this.getReportTitle(), IFiltersConst.exact);
		
		rowIndx = TablesUtil.getRowIndex(ITablesConst.myReprotsTableId, this.getReportTitle());
		
		Div tDiv = TablesUtil.tableDiv();
		
		if(rowIndx > -1 )
		{
			tDiv.body(id, ITablesConst.myReprotsTableId).row(rowIndx).cell(0).link(0).click();
			
			ClicksUtil.clickButtons(IClicksConst.launchReport);
			
			return true;
		}
		
		return false;
	}
	
	
	public boolean lanuchReport() throws Exception
	{
		IE ie = IEUtil.getActiveIE();
		
		boolean retVal = false;
		
		int rowIndx = -1;
		
		ClicksUtil.clickLinks(IClicksConst.openMyReportsListLnk);		
		
		FiltersUtil.filterListByLabel(IFiltersConst.grantManagement_ReportName_Lbl, this.getReportTitle(),IFiltersConst.exact);	
		
		rowIndx = TablesUtil.getRowIndex(ITablesConst.myReprotsTableId, this.getReportTitle());	

		Div tDiv = TablesUtil.tableDiv();
		
		if (rowIndx > -1)
		{
			tDiv.body(id, ITablesConst.myReprotsTableId).row(rowIndx).cell(0).link(0).click();	
			
			for(int i=0; i < this.ParamType.length; i++)
			{
				
				if (this.ParamType[i] == IReportsConst.withParamsReportsType[0])
				{
					ie.selectList(0).select(this.paramsValues[i]);	
					
					retVal = true;				
				}
				else if (this.ParamType[i] == IReportsConst.withParamsReportsType[4])
				{
					ie.selectList(id, "/projectsPrograms/").select(this.paramsValues[i]);					
					GeneralUtil.takeANap(2.000);					
					if (ie.selectList(id, "main:selectParameterSubview:selectParameterForm:parametersTest:0:projects").enabled())
					{	
						retVal = GeneralUtil.selectFullStringInDropdownList("main:selectParameterSubview:selectParameterForm:parametersTest:0:projects", this.paramsValues[i+1]);
					}
				}			
				else if (this.ParamType[i] == IReportsConst.withParamsReportsType[7])
				{
					ie.textField(0).selectList(this.paramsValues[i]);
					
					retVal = true;
				}	
			}
			
			ClicksUtil.clickButtons(IClicksConst.launchReport);
			
		}
		
		return retVal;
	}
	
	
	public boolean deleteReport() throws Exception
	{
		
		retValue = false;
		
		int rowIndx = -1;
		
		ClicksUtil.clickLinks(IClicksConst.openReportsList);		
		FiltersUtil.filterListByLabel(IFiltersConst.administration_ReportIdent_Lbl, this.getReportIdent(), IFiltersConst.exact);		
		rowIndx = TablesUtil.getRowIndex(ITablesConst.reprotsTableId, this.getReportIdent());
		
		Div tDiv = TablesUtil.tableDiv();
		
		if (rowIndx > -1) 
		{
			if (tDiv.body(id,ITablesConst.reprotsTableId).row(rowIndx).cell(0).image(src, IClicksConst.deleteImg).exists())				
			{
				Thread dialogClicker = new Thread()
				{
					@Override
					public void run() {
						try
						{
							IE ie = IEUtil.getActiveIE();
							ConfirmDialog dialog1 = ie.confirmDialog();
							while (dialog1==null)
							{
								log.debug("can't yet get handle on confirm dialog1");
								GeneralUtil.takeANap(0.250);
							}
							
							dialog1.ok();
							log.debug("got confirmDialog1 and clicked OK.");							
							
						}
						catch (Exception e)
						{
							throw new RuntimeException("Unexpected exception",e);
						}
					}
				};
				
				dialogClicker.start();
				log.debug("started dialog clicker thread");
				
				log.debug("clicking Delete Report");
				tDiv.body(id,ITablesConst.reprotsTableId).row(rowIndx).cell(0).image(src, IClicksConst.deleteImg).click();

				
				GeneralUtil.takeANap(1.000);
				
				retValue = true;
				
				dialogClicker = null;
			}
			
		}
		
		return retValue;
	}
	
	public String getLookupName() {
		return lookupName;
	}



	public void setLookupName(String lookupName) {
		this.lookupName = lookupName;
	}


	public String[] getParamName() {
		return ParamName;
	}


	public void setParamName(String[] paramName) {
		ParamName = paramName;
	}
	

	public String[] getParamType() {
		return ParamType;
	}


	public void setParamType(String[] paramType) {
		ParamType = paramType;
	}



	public String[] getAccessUser() {
		return accessUser;
	}



	public void setAccessUser(String[] accessUser) {
		this.accessUser = accessUser;
	}



	public String getReportLetter() {
		return reportLetter;
	}



	public void setReportLetter(String reportLetter) {
		this.reportLetter = reportLetter;
	}


	public String[] getParamsValues() {
		return paramsValues;
	}


	public void setParamsValues(String[] paramsValues) {
		this.paramsValues = paramsValues;
	}


	public String getReportViewerFullPath() {
		return reportViewerFullPath;
	}


	public void setReportViewerFullPath(String reportViewerFullPath) {
		this.reportViewerFullPath = reportViewerFullPath;
	}
	
	 /* **********************
	 * Added by Alex Pankov - to add one Report of any kind with settable parameters
	 * ***********************/
	public String getReportIdent() {
		return reportIdent;
	}

	public void setReportIdent(String reportIdent) {
		this.reportIdent = reportIdent;
	}

	public String getReportOrgName() {
		return reportOrgName;
	}

	public void setReportOrgName(String reportOrgName) {
		this.reportOrgName = reportOrgName;
	}
	
	public String getReportOrgAccess() {
		return reportOrgAccess;
	}

	public void setReportOrgAccess(String reportOrgAccess) {
		this.reportOrgAccess = reportOrgAccess;
	}
	
	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	
	public String [] getReportTitles() {
		return reportTitles;
	}

	/**
	 * @return the reportTitle
	 */
	public String getReportTitle() {
		return reportTitle;
	}


	public void setReportTitles(String [] reportTitles) {
		this.reportTitles = reportTitles;
	}
	 /* **********************
	 * End of added code
	 * ***********************/


	/**
	 * @param reportTitle the reportTitle to set
	 */
	public void setReportTitle(String reportTitle) {
		this.reportTitle = reportTitle;
	}


	/**
	 * @return the reportStatus
	 */
	public String getReportStatus() {
		return reportStatus;
	}


	/**
	 * @param reportStatus the reportStatus to set
	 */
	public void setReportStatus(String reportStatus) {
		this.reportStatus = reportStatus;
	}


	/**
	 * @return the paramLabels
	 */
	public String[] getParamLabels() {
		return ParamLabels;
	}


	/**
	 * @param paramLabels the paramLabels to set
	 */
	public void setParamLabels(String[] paramLabels) {
		ParamLabels = paramLabels;
	}
}
