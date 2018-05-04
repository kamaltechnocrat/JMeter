/**
 * 
 */
package test_Suite.lib.eForms;

/**
 * @author mshakshouki
 *
 */

import static watij.finders.SymbolFactory.*;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import watij.runtime.ie.IE;
import test_Suite.constants.eForms.*;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.eForms.EFormsUtil;
import test_Suite.utils.ui.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.Assert;

public class FormletFunctions extends Formlet {

	/**
	 * 
	 */
	private static Log log = LogFactory.getLog(EFormFunctions.class);
	
	protected boolean retValue;
	
	protected Integer formletIndex = 0;
	
	protected String expandFormletStartId = "/planner:0:";
	
	protected String expandFormletEndId = ":t2/";
	
	protected String expandFormletId = null;	
	
	private String formletFunctionName = null;
	
	private String proprety1 = null;
	private String proprety2 = null;
	private String proprety3 = null;
	private String proprety4 = null;
	private String proprety5 = null;
	private String proprety6 = null;
	private String proprety7 = null;
	private String proprety8 = null;
	
	private String expression = null;
	
	private String comparisonOperation = null;
	
	private String eFormFieldReference = null;
	private String systemVariableReference = null;
	private String dataObjectMethod = null;
	
	private String submissionPreventedMessage = null;
	
	private String beforeOrAfterSubmission = null;
	private Boolean applicantReadOnly = null;
	private Boolean allStepStaffReadOnly = null;
	private Boolean assignedProjectStepOfficersReadOnly = null;
	private Boolean allAssignedProjectOfficersReadOnly = null;
	private Boolean allProjectOfficersReadOnly = null;
	private String[] readAccessGroups = null;
	
	private static Enumeration<String> Keystrng;
	private static Enumeration<String> Elementstrng;
	private static Enumeration<Boolean> ElementBool;
	
	ArrayList<String> errorSmall;
	
	public FormletFunctions(Formlet formlet) {
		
		this.setFormletIndex(formlet.getFormletCounter());
		
		this.setExpandFormletId(expandFormletStartId + this.getFormletIndex().toString() + expandFormletEndId);
	}

	public FormletFunctions(EForm form, String formletType,String eFormType, String preFix) {
		
		super(form,formletType);
		
		this.setFormletIndex(super.getFormletCounter());
		
		this.setExpandFormletId(expandFormletStartId + this.getFormletIndex().toString() + expandFormletEndId);
	}

	public FormletFunctions(EForm form, Formlet formlet) {
		
		super(form,formlet.getFormletType());
		
		this.setFormletIndex(super.getFormletCounter());
		
		this.setExpandFormletId(expandFormletStartId + this.getFormletIndex().toString() + expandFormletEndId);
	}


	public boolean addLBFFunction(Formlet funcFormlet) throws Exception {

		// Setup Formlet Function

		String[] strFormletFuncProp = new String[4];
		strFormletFuncProp[0] = IFormletsFunctionsConst.formletFunc_ListBF_InvokeWhen_Values_Array[1];
		strFormletFuncProp[1] = funcFormlet.getFormletId();

		strFormletFuncProp[2] = "yes";
		strFormletFuncProp[3] = "true";

		return this.addListBringForwardFunction(strFormletFuncProp,
				funcFormlet);
	}
	
	
	public boolean addCalcFormletAccessGrant(Object[] objPropValues) throws Exception {
		
		retValue = false;		
		
		IE ie = IEUtil.getActiveIE();
		EFormsUtil.expandFormPlannerNode(super.getFormletId(),
				EFormsUtil.EManipulatePlannerNode.expandNode.ordinal());
		
		log.debug(super.getFormletId());
		

		Assert.assertTrue(EFormsUtil.openNewOpjectDetail("Formlet Functions",EFormsUtil.EAddObjectsFormPlanner.addFormletFunction.ordinal()), "Something wrong");

		
		if (objPropValues.length == 8)
		{
			
			ie.selectList(id, IAlleFormsFunctionsConst.formletFunctions_FunctionType_Dropdown_Id).select(IAlleFormsFunctionsConst.formlet_CalculatedFormletUserAccessGrant_FunctionType);
			
			//delay
			GeneralUtil.takeANap(2.0);
			
			ie.textField(id, IAlleFormsFunctionsConst.formletCalcUserAccessGrant_FuncPropFields_Id[0]).set(objPropValues[0].toString());
			
			ie.selectList(id, IAlleFormsFunctionsConst.formletCalcUserAccessGrant_FuncPropFields_Id[1]).select(objPropValues[1].toString());
			
			for(int x=2; x<=6; x++)
			{
				if(objPropValues[x].toString().matches("true"))
				{
					ie.checkbox(id, IAlleFormsFunctionsConst.formletCalcUserAccessGrant_FuncPropFields_Id[x]).set(true);
				}
				else
				{
					ie.checkbox(id, IAlleFormsFunctionsConst.formletCalcUserAccessGrant_FuncPropFields_Id[x]).set(false);
				}
			}
			
			if(GeneralUtil.isObjectExistsInList(IAlleFormsFunctionsConst.formletCalcUserAccessGrant_FuncPropFields_Id[7], objPropValues[7].toString()))
			{
				ie.selectList(id, IAlleFormsFunctionsConst.formletCalcUserAccessGrant_FuncPropFields_Id[7]).select(objPropValues[7].toString());
				
				ClicksUtil.clickButtons(IClicksConst.m2MSingleForBtn);
				log.info("Selected");
				
				retValue = true;
			}
			else
			{
				if(GeneralUtil.isObjectExistsInList(IAlleFormsFunctionsConst.formletCalcUserAccessGrant_FuncPropFields_Id[8], objPropValues[7].toString()))
				{
					
					log.info("Already Selected");
					
					retValue = true;
				}
				else
				{
					log.info("Does not exists");
				}
			}
			
			ClicksUtil.clickButtons(IClicksConst.saveAndBackOldBtn);
			
		}
		
		return retValue;
	}
	
	public boolean addCalcSubmissionConditionFunction(String[] funcProp) throws Exception {
		retValue = false;		
		
		IE ie = IEUtil.getActiveIE();
		
		EFormsUtil.expandFormPlannerNode(super.getFormletId(),
				EFormsUtil.EManipulatePlannerNode.expandNode.ordinal());
		
		log.debug(super.getFormletId());
		

		Assert.assertTrue(EFormsUtil.openNewOpjectDetail("Formlet Functions",EFormsUtil.EAddObjectsFormPlanner.addFormletFunction.ordinal()), "Something wrong");
		
		if (funcProp.length == 2)
		{
			ie.selectList(id, IAlleFormsFunctionsConst.formletFunctions_FunctionType_Dropdown_Id).select(IAlleFormsFunctionsConst.formlet_CalculatedSubmissionCondition_FunctionType);
			//adding delay
			GeneralUtil.takeANap(2.0);
			ie.textField(id, IAlleFormsFunctionsConst.formletCalcSubCondition_FuncPropFields_Id[0]).set(funcProp[0]);
			ie.selectList(id, IAlleFormsFunctionsConst.formletCalcSubCondition_FuncPropFields_Id[1]).select(funcProp[1]);
			retValue = true;
			
			ClicksUtil.clickButtons(IClicksConst.saveBtn);
		}
		
		ClicksUtil.clickButtons(IClicksConst.backBtn);
		
		return retValue;
	}
	
	public boolean fillAndTestAnyFunctionDetails(ArrayList<String> funcData, Formlet formlet) throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
		//EFormsUtil.expandFormPlannerNode(formlet.getFormletId(),
			//	EFormsUtil.EManipulatePlannerNode.expandNode.ordinal());
		
		this.setFormletFunctionName(funcData.get(0));
		
		//Assert.assertTrue(EFormsUtil.openNewOpjectDetailInFormlet(formlet,"Formlet Functions",EFormsUtil.EAddObjectsFormPlanner.addFormletFunction.ordinal()), "Something wrong");
		
		if(!funcData.get(0).equals(""))
		{
			
			ie.selectList(id,IFormletsFunctionsConst.formletFunctions_FunctionType_Dropdown_Id).select(funcData.get(0));
			GeneralUtil.takeANap(0.5);
		}
		
		if(!funcData.get(1).equals(""))
		{
			
			ie.selectList(id,IFormletsFunctionsConst.formletFunc_ListBF_InvokeWhen_Dropdown_Id).select(funcData.get(1));
			
		}
		//if(!funcData.get(2).equals(""))
		//{			
			ie.textField(id,IFormletsFunctionsConst.formletFunc_ListBF_SourceFormlet_TextField_Id).set(funcData.get(2));
		//}
		if(!funcData.get(3).equals(""))
		{
			
			ie.selectList(id,IFormletsFunctionsConst.formletFunc_ListBF_Synchronize_Dropdown_Id).select(funcData.get(3));
			
		}
		//if(!funcData.get(4).equals(""))
		//{
			
			ie.textField(id,IFormletsFunctionsConst.formletFunc_ListBF_ConditionalExpression_TextField_Id).set(funcData.get(4));
			
		//}
		if(!funcData.get(5).equals(""))
		{
			ie.selectList(id,IFormletsFunctionsConst.formletFunc_ListBF_SystemVariable_Dropdown_Id).select(funcData.get(5));
		}
		if(!funcData.get(6).equals(""))
		{
			ie.selectList(id,IFormletsFunctionsConst.formletFunc_ListBF_DataObjectMethod_Dropdown_Id).select(funcData.get(6));
		}
		
		ClicksUtil.clickButtons(IClicksConst.saveBtn);
		
		errorSmall = null;
		
		errorSmall = GeneralUtil.checkForErrorMessages();
		
		if(errorSmall != null && !errorSmall.isEmpty())
		{
			for (String string : errorSmall) {
				
				log.error("Validation error: " + string);
				
			}
			
			return false;
		}
		
		
		return true;
	}
	
	public boolean addListBringForwardFunction(String[] funcProp, Formlet formlet) throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
		EFormsUtil.expandFormPlannerNode(formlet.getFormletId(),
				EFormsUtil.EManipulatePlannerNode.expandNode.ordinal());
		
		this.setFormletFunctionName(IFormletsFunctionsConst.formlet_ListBringForward_FunctionType);
		
		Assert.assertTrue(EFormsUtil.openNewOpjectDetailInFormlet(formlet,"Formlet Functions",EFormsUtil.EAddObjectsFormPlanner.addFormletFunction.ordinal()), "Something wrong");
		
		ie.selectList(id,IFormletsFunctionsConst.formletFunctions_FunctionType_Dropdown_Id).select(IFormletsFunctionsConst.formlet_ListBringForward_FunctionType);
		
		GeneralUtil.takeANap(0.5);
		
		ie.selectList(id,IFormletsFunctionsConst.formletFunc_ListBF_InvokeWhen_Dropdown_Id).select(funcProp[0]);
		
		ie.textField(id,IFormletsFunctionsConst.formletFunc_ListBF_SourceFormlet_TextField_Id).set(funcProp[1]);
		
		ie.selectList(id,IFormletsFunctionsConst.formletFunc_ListBF_Synchronize_Dropdown_Id).select(funcProp[2]);
		
		ie.textField(id,IFormletsFunctionsConst.formletFunc_ListBF_ConditionalExpression_TextField_Id).set(funcProp[3]);
		
		ClicksUtil.clickButtons(IClicksConst.saveBtn);
		
		errorSmall = null;
		
		errorSmall = GeneralUtil.checkForErrorMessages();
		
		if(errorSmall != null && !errorSmall.isEmpty())
		{
			for (String string : errorSmall) {
				
				log.error("Adding List Bring Function Caused this Validation Error: " + string);
			}
			
			return false;
		}
		
		return true;
		
	}
	
	/**
	 * @return the formletIndex
	 */
	public Integer getFormletIndex() {
		return formletIndex;
	}

	/**
	 * @param formletIndex the formletIndex to set
	 */
	public void setFormletIndex(Integer formletIndex) {
		this.formletIndex = formletIndex;
	}

	/**
	 * @return the expandFormletStartId
	 */
	public String getExpandFormletStartId() {
		return expandFormletStartId;
	}

	/**
	 * @param expandFormletStartId the expandFormletStartId to set
	 */
	public void setExpandFormletStartId(String expandFormletStartId) {
		this.expandFormletStartId = expandFormletStartId;
	}

	/**
	 * @return the expandFormletEndId
	 */
	public String getExpandFormletEndId() {
		return expandFormletEndId;
	}

	/**
	 * @param expandFormletEndId the expandFormletEndId to set
	 */
	public void setExpandFormletEndId(String expandFormletEndId) {
		this.expandFormletEndId = expandFormletEndId;
	}

	/**
	 * @return the expandFormletId
	 */
	public String getExpandFormletId() {
		return expandFormletId;
	}

	/**
	 * @param expandFormletId the expandFormletId to set
	 */
	public void setExpandFormletId(String expandFormletId) {
		this.expandFormletId = expandFormletId;
	}

	/**
	 * @throws Exception
	 * 
	 * This will add any Formlet Function, when setting the name and Its Properties
	 */
	public void addAnyFormletFunction() throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
		//Selecting the Name
		
		EFormsUtil.expandFormPlannerNode(super.getFormletId(),
				EFormsUtil.EManipulatePlannerNode.expandNode.ordinal());
		

		Assert.assertTrue(EFormsUtil.openNewOpjectDetail("Formlet Functions",EFormsUtil.EAddObjectsFormPlanner.addFormletFunction.ordinal()), "Something wrong");
		
		ie.selectList(id, IFormletsFunctionsConst.formletFunctions_FunctionType_Dropdown_Id).select(this.getFormletFunctionName());
		
		GeneralUtil.takeANap(1.0);
		
		//Setting any Text Fields where applicable
		if(this.getProprety1() != null)
		{
			ie.textField(id, IFormletsFunctionsConst.formlet_FuncProp_SourceField_1_TextField_Id).set(this.getProprety1());
		}
		
		if(this.getProprety2() != null)
		{
			ie.textField(id, IFormletsFunctionsConst.formlet_FuncProp_SourceField_2_TextField_Id).set(this.getProprety2());
		}
		
		if(this.getProprety3() != null)
		{
			ie.textField(id, IFormletsFunctionsConst.formlet_FuncProp_SourceField_3_TextField_Id).set(this.getProprety3());
		}
		
		if(this.getProprety4() != null)
		{
			ie.textField(id, IFormletsFunctionsConst.formlet_FuncProp_SourceField_4_TextField_Id).set(this.getProprety4());
		}
		
		if(this.getProprety5() != null)
		{
			ie.textField(id, IFormletsFunctionsConst.formlet_FuncProp_SourceField_5_TextField_Id).set(this.getProprety5());
		}
		
		if(this.getProprety6() != null)
		{
			ie.textField(id, IFormletsFunctionsConst.formlet_FuncProp_SourceField_6_TextField_Id).set(this.getProprety6());
		}
		
		if(this.getProprety7() != null)
		{
			ie.textField(id, IFormletsFunctionsConst.formlet_FuncProp_SourceField_7_TextField_Id).set(this.getProprety7());
		}
		
		if(this.getProprety8() != null)
		{
			ie.textField(id, IFormletsFunctionsConst.formlet_FuncProp_SourceField_8_TextField_Id).set(this.getProprety8());
		}
		
		//Setting up Calculated Functions Props
		
		if(this.getExpression() != null)
		{
			ie.textField(id, IFormletsFunctionsConst.formletCalculated_FuncProp_TextArea_Id).set(this.getExpression());
		}
		
		if(this.getEFormFieldReference() != null)
		{
			ie.selectList(id,IFormletsFunctionsConst.formletCalculated_FuncProp_EFormFieldRef_Dropdown_Id).select(this.getEFormFieldReference());
		}
		
		if(this.getSystemVariableReference() != null)
		{
			ie.selectList(id,IFormletsFunctionsConst.formletCalculated_FuncProp_SystemVariablesRef_Dropdown_Id).select(this.getSystemVariableReference());
		}
		
		if(this.getDataObjectMethod() != null)
		{
			ie.selectList(id,IFormletsFunctionsConst.formletCalculated_FuncProp_DataObjectMethod_Dropdown_Id).select(this.getDataObjectMethod());
		}
		
		if(this.getSubmissionPreventedMessage() != null)
		{
			ie.selectList(id,IFormletsFunctionsConst.formletCalcSubCondition_FuncProp_SubPreventedMessage_Dropdwon_Id).select(this.getSubmissionPreventedMessage());
		}
		
		if(this.getBeforeOrAfterSubmission() != null)
		{
			ie.selectList(id,IFormletsFunctionsConst.formletCalcUserAccessGrant_FuncProp_BeforeOrAfterSub_DropDown_Id).select(this.getBeforeOrAfterSubmission());
		}
		
		if(this.getApplicantReadOnly() != null)
		{
			ie.checkbox(id,IFormletsFunctionsConst.formletCalcUserAccessGrant_FuncProp_ApplicantRead_Checkbox_Id).set(this.getApplicantReadOnly());
		}
		
		if(this.getAllStepStaffReadOnly() != null)
		{
			ie.checkbox(id,IFormletsFunctionsConst.formletCalcUserAccessGrant_FuncProp_AllStepStaffRead_Checkbox_Id).set(this.getAllStepStaffReadOnly());
		}
		
		if(this.getAssignedProjectStepOfficersReadOnly() != null)
		{
			ie.checkbox(id, IFormletsFunctionsConst.formletCalcUserAccessGrant_FuncProp_AssignProjStepOfficersRead_Checkbox_Id).set(this.getAssignedProjectStepOfficersReadOnly());
		}
		
		if(this.getAllAssignedProjectOfficersReadOnly() != null)
		{
			ie.checkbox(id, IFormletsFunctionsConst.formletCalcUserAccessGrant_FuncProp_AllAssignProjOfficersRead_Checkbox_Id).set(this.getAllAssignedProjectOfficersReadOnly());					
		}
		
		if(this.getAllProjectOfficersReadOnly() != null)
		{
			ie.checkbox(id, IFormletsFunctionsConst.formletCalcUserAccessGrant_FuncProp_AllProjOfficersRead_Checkbox_Id).set(this.getAllProjectOfficersReadOnly());
		}
		
		if(this.getReadAccessGroups() != null)
		{
			for(int x=0; x <this.getReadAccessGroups().length; x++)
			{
				ie.selectList(id, IFormletsFunctionsConst.formletCalcUserAccessGrant_FuncProp_M2MAvailableGroups_SelectList_Id).select(this.getReadAccessGroups()[x]);
				
				ClicksUtil.clickButtons(IClicksConst.m2MSingleForBtn);
			}
		}
		
		if(this.getComparisonOperation() != null)
		{
			ie.selectList(id, IFormletsFunctionsConst.formletSimpleComparision_FuncProp_CompareOperation_Dropdown_Id).select(this.getComparisonOperation());
		}
		
		ClicksUtil.clickButtons(IClicksConst.saveAndBackOldBtn);
		
	}
	
	
	public void modifyAnyTextFields(Hashtable<String, String> hashTable) throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
		Keystrng = hashTable.keys();
		Elementstrng = hashTable.elements();
		
		while (Keystrng.hasMoreElements()){
			
			ie.textField(id, Keystrng.nextElement()).set(Elementstrng.nextElement());
		}
	}
	
	public void modifyAnySelectFields(Hashtable<String, String> hashTable) throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
		Keystrng = hashTable.keys();
		Elementstrng = hashTable.elements();
		
		while (Keystrng.hasMoreElements()){
			
			ie.selectList(id, Keystrng.nextElement()).select(Elementstrng.nextElement());
		}
	}
	
	public void modifyAnyCheckBox(Hashtable<String, Boolean> hashTable) throws Exception {
		
		IE ie = IEUtil.getActiveIE();
		
		Keystrng = hashTable.keys();
		ElementBool = hashTable.elements();
		
		while (Keystrng.hasMoreElements()){
			
			ie.checkbox(id,Keystrng.nextElement()).set(ElementBool.nextElement());
		}
	}

	/**
	 * @return the proprety1
	 */
	public String getProprety1() {
		return proprety1;
	}

	/**
	 * @param proprety1 the proprety1 to set
	 */
	public void setProprety1(String proprety1) {
		this.proprety1 = proprety1;
	}

	/**
	 * @return the proprety2
	 */
	public String getProprety2() {
		return proprety2;
	}

	/**
	 * @param proprety2 the proprety2 to set
	 */
	public void setProprety2(String proprety2) {
		this.proprety2 = proprety2;
	}

	/**
	 * @return the proprety3
	 */
	public String getProprety3() {
		return proprety3;
	}

	/**
	 * @param proprety3 the proprety3 to set
	 */
	public void setProprety3(String proprety3) {
		this.proprety3 = proprety3;
	}

	/**
	 * @return the proprety4
	 */
	public String getProprety4() {
		return proprety4;
	}

	/**
	 * @param proprety4 the proprety4 to set
	 */
	public void setProprety4(String proprety4) {
		this.proprety4 = proprety4;
	}

	/**
	 * @return the proprety5
	 */
	public String getProprety5() {
		return proprety5;
	}

	/**
	 * @param proprety5 the proprety5 to set
	 */
	public void setProprety5(String proprety5) {
		this.proprety5 = proprety5;
	}

	/**
	 * @return the proprety6
	 */
	public String getProprety6() {
		return proprety6;
	}

	/**
	 * @param proprety6 the proprety6 to set
	 */
	public void setProprety6(String proprety6) {
		this.proprety6 = proprety6;
	}

	/**
	 * @return the proprety7
	 */
	public String getProprety7() {
		return proprety7;
	}

	/**
	 * @param proprety7 the proprety7 to set
	 */
	public void setProprety7(String proprety7) {
		this.proprety7 = proprety7;
	}

	/**
	 * @return the proprety8
	 */
	public String getProprety8() {
		return proprety8;
	}

	/**
	 * @param proprety8 the proprety8 to set
	 */
	public void setProprety8(String proprety8) {
		this.proprety8 = proprety8;
	}

	/**
	 * @return the expression
	 */
	public String getExpression() {
		return expression;
	}

	/**
	 * @param expression the expression to set
	 */
	public void setExpression(String expression) {
		this.expression = expression;
	}

	/**
	 * @return the comparisonOperation
	 */
	public String getComparisonOperation() {
		return comparisonOperation;
	}

	/**
	 * @param comparisonOperation the comparisonOperation to set
	 */
	public void setComparisonOperation(String comparisonOperation) {
		this.comparisonOperation = comparisonOperation;
	}

	/**
	 * @return the eFormFieldReference
	 */
	public String getEFormFieldReference() {
		return eFormFieldReference;
	}

	/**
	 * @param formFieldReference the eFormFieldReference to set
	 */
	public void setEFormFieldReference(String formFieldReference) {
		eFormFieldReference = formFieldReference;
	}

	/**
	 * @return the systemVariableReference
	 */
	public String getSystemVariableReference() {
		return systemVariableReference;
	}

	/**
	 * @param systemVariableReference the systemVariableReference to set
	 */
	public void setSystemVariableReference(String systemVariableReference) {
		this.systemVariableReference = systemVariableReference;
	}

	/**
	 * @return the dataObjectMethod
	 */
	public String getDataObjectMethod() {
		return dataObjectMethod;
	}

	/**
	 * @param dataObjectMethod the dataObjectMethod to set
	 */
	public void setDataObjectMethod(String dataObjectMethod) {
		this.dataObjectMethod = dataObjectMethod;
	}

	/**
	 * @return the submissionPreventedMessage
	 */
	public String getSubmissionPreventedMessage() {
		return submissionPreventedMessage;
	}

	/**
	 * @param submissionPreventedMessage the submissionPreventedMessage to set
	 */
	public void setSubmissionPreventedMessage(String submissionPreventedMessage) {
		this.submissionPreventedMessage = submissionPreventedMessage;
	}

	/**
	 * @return the beforeOrAfterSubmission
	 */
	public String getBeforeOrAfterSubmission() {
		return beforeOrAfterSubmission;
	}

	/**
	 * @param beforeOrAfterSubmission the beforeOrAfterSubmission to set
	 */
	public void setBeforeOrAfterSubmission(String beforeOrAfterSubmission) {
		this.beforeOrAfterSubmission = beforeOrAfterSubmission;
	}

	/**
	 * @return the applicantReadOnly
	 */
	public Boolean getApplicantReadOnly() {
		return applicantReadOnly;
	}

	/**
	 * @param applicantReadOnly the applicantReadOnly to set
	 */
	public void setApplicantReadOnly(Boolean applicantReadOnly) {
		this.applicantReadOnly = applicantReadOnly;
	}

	/**
	 * @return the allStepStaffReadOnly
	 */
	public Boolean getAllStepStaffReadOnly() {
		return allStepStaffReadOnly;
	}

	/**
	 * @param allStepStaffReadOnly the allStepStaffReadOnly to set
	 */
	public void setAllStepStaffReadOnly(Boolean allStepStaffReadOnly) {
		this.allStepStaffReadOnly = allStepStaffReadOnly;
	}

	/**
	 * @return the assignedProjectStepOfficersReadOnly
	 */
	public Boolean getAssignedProjectStepOfficersReadOnly() {
		return assignedProjectStepOfficersReadOnly;
	}

	/**
	 * @param assignedProjectStepOfficersReadOnly the assignedProjectStepOfficersReadOnly to set
	 */
	public void setAssignedProjectStepOfficersReadOnly(
			Boolean assignedProjectStepOfficersReadOnly) {
		this.assignedProjectStepOfficersReadOnly = assignedProjectStepOfficersReadOnly;
	}

	/**
	 * @return the allAssignedProjectOfficersReadOnly
	 */
	public Boolean getAllAssignedProjectOfficersReadOnly() {
		return allAssignedProjectOfficersReadOnly;
	}

	/**
	 * @param allAssignedProjectOfficersReadOnly the allAssignedProjectOfficersReadOnly to set
	 */
	public void setAllAssignedProjectOfficersReadOnly(
			Boolean allAssignedProjectOfficersReadOnly) {
		this.allAssignedProjectOfficersReadOnly = allAssignedProjectOfficersReadOnly;
	}

	/**
	 * @return the allProjectOfficersReadOnly
	 */
	public Boolean getAllProjectOfficersReadOnly() {
		return allProjectOfficersReadOnly;
	}

	/**
	 * @param allProjectOfficersReadOnly the allProjectOfficersReadOnly to set
	 */
	public void setAllProjectOfficersReadOnly(Boolean allProjectOfficersReadOnly) {
		this.allProjectOfficersReadOnly = allProjectOfficersReadOnly;
	}

	/**
	 * @return the readAccessGroups
	 */
	public String[] getReadAccessGroups() {
		return readAccessGroups;
	}

	/**
	 * @param readAccessGroups the readAccessGroups to set
	 */
	public void setReadAccessGroups(String[] readAccessGroups) {
		this.readAccessGroups = readAccessGroups;
	}

	/**
	 * @return the formletFunctionName
	 */
	public String getFormletFunctionName() {
		return formletFunctionName;
	}

	/**
	 * @param formletFunctionName the formletFunctionName to set
	 */
	public void setFormletFunctionName(String formletFunctionName) {
		this.formletFunctionName = formletFunctionName;
	}

}
