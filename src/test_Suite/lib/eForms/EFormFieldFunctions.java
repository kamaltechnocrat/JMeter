/**
 * 
 */
package test_Suite.lib.eForms;

/**
 * @author mshakshouki
 * 
 */

import static watij.finders.SymbolFactory.*;
import watij.runtime.ie.IE;
import test_Suite.constants.eForms.*;
import test_Suite.constants.ui.IClicksConst;
import test_Suite.utils.cases.GeneralUtil;
import test_Suite.utils.eForms.EFormsUtil;
import test_Suite.utils.ui.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.Assert;

public class EFormFieldFunctions extends EFormField {

	/**
	 * 
	 */

	private static Log log = LogFactory.getLog(EFormFunctions.class);

	protected boolean retValue;
	protected Integer eFormFieldIndex = 0;
	private Integer formletCounter = 0;
	
	protected String addeFormFieldFuncLinkStartId = "/planner:0:";
	protected String addeFormFieldFuncLinkEndId = null;
	protected String addEFormFieldFuncLinkId = null;
	
	private String eFormFieldFunctionName = null;
	private String sourceField1 = null;
	private String sourceField2 = null;
	private String sourceField3 = null;
	private String sourceField4 = null;
	private String thisFieldValue = null;
	
	private String sourceGridCell = null;
	private String targetGridCell = null;
	
	private String invokeWhen = null;
	
	private String expression = null;
	
	private String eFormFieldReference = null;
	private String systemVariableReference = null;
	private String dataObjectMethod = null;
	
	private String buttonIdentifier = null;
	private String milestoneName = null;
	private String milestoneStartDate = null;
	private String milestoneEndDate = null;
	
	private String beforeOrAfterSubmission = null;
	private Boolean applicantRead = null;
	private Boolean allStepStaffRead = null;
	private Boolean assignedProjectStepOfficersRead = null;
	private Boolean allAssignedProjectOfficersRead = null;
	private Boolean allProjectOfficersRead = null;
	private String[] readAccessGroups = null;
	

	private Boolean applicantWrite = null;
	private Boolean allStepStaffWrite = null;
	private Boolean assignedProjectStepOfficersWrite = null;
	private Boolean allAssignedProjectOfficersWrite = null;
	private Boolean allProjectOfficersWrite = null;
	private String[] writeAccessGroups = null;


	EFormField eformField;

	public EFormFieldFunctions(EFormField eformField) {

		this.formletCounter = eformField.formletCounter;
		this.eFormFieldIndex = eformField.eFormFieldIndex;

		this.eformField = eformField;

	}

	public EFormFieldFunctions() {
		super();
	}

	public boolean openEFormFieldFunctionDetails() throws Exception {

		retValue = false;

		IE ie = IEUtil.getActiveIE();

		this.addeFormFieldFuncLinkEndId = eformField
				.getIdLastPart("Add EFormField Function");

		this.addEFormFieldFuncLinkId = addeFormFieldFuncLinkStartId
				+ this.formletCounter + ":" + this.eFormFieldIndex + ":"
				+ addeFormFieldFuncLinkEndId + "/";

		log.info(this.addEFormFieldFuncLinkId);

		if (ie.link(id, this.addEFormFieldFuncLinkId).exists()) {
			ie.link(id, this.addEFormFieldFuncLinkId).click();
			retValue = true;
		}

		return retValue;
	}

	public boolean addeFormFieldBringForwardFunc(String[] strValues)
			throws Exception {

		retValue = false;

		IE ie = IEUtil.getActiveIE();
		
		EFormsUtil.expandFormPlannerNode(eformField.getTempFormlet(),
				EFormsUtil.EManipulatePlannerNode.expandNode.ordinal());
		
		this.setEFormFieldFunctionName(IAlleFormsFunctionsConst.eFormField_BringForward_FuncType);

		EFormsUtil.openNewOpjectDetail(eformField.getEFormFieldId(),EFormsUtil.EAddObjectsFormPlanner.addEformFieldFunction.ordinal());

		ie.selectList( id, IAlleFormsFunctionsConst.eFormField_FunctionType_DropdownField_Id).select( IAlleFormsFunctionsConst.eFormField_BringForward_FuncType);
		
		//delay
		GeneralUtil.takeANap(2.0);
		
		ie.selectList(id, IAlleFormsFunctionsConst.eFormFieldBringForward_FuncProp_InvokeWhen_DropDown_Id).select(strValues[0]);

		ie.textField( id, IAlleFormsFunctionsConst.eFormFieldBringForward_FuncProp_SourceField_TextField_Id).set(strValues[1]);
		
		ie.textField( id, IAlleFormsFunctionsConst.eFormFieldBringForward_FuncProp_SourceGridCell_TextField_Id).set(strValues[2]);
		
		ie.textField( id, IAlleFormsFunctionsConst.eFormFieldBringForward_FuncProp_TargetGridCell_TextField_Id).set(strValues[3]);		

		ClicksUtil.clickButtons(IClicksConst.saveAndBackOldBtn);
		
		retValue = true;

		return retValue;
	}
	
	public boolean addEFormFieldCalcVisibilityFunc(String[] FuncProps) throws Exception {
		retValue = false;

		IE ie = IEUtil.getActiveIE();
		
		EFormsUtil.expandFormPlannerNode(eformField.getTempFormlet(),
				EFormsUtil.EManipulatePlannerNode.expandNode.ordinal());

		EFormsUtil.openNewOpjectDetail(eformField.getEFormFieldId(),EFormsUtil.EAddObjectsFormPlanner.addEformFieldFunction.ordinal());
		
		if (FuncProps.length == 2) 
		{
			ie.selectList(id,IAlleFormsFunctionsConst.eFormField_FunctionType_DropdownField_Id).select(IAlleFormsFunctionsConst.eFormField_CalcVisibility_FuncType);
			
			//delay
			GeneralUtil.takeANap(2.0);
			ie.textField(id,IAlleFormsFunctionsConst.eFormFieldCalcVisibility_FuncProp_TargetGridCell_TextField_Id).set(FuncProps[0]);
			ie.textField(id,IAlleFormsFunctionsConst.eFormFieldCalcVisibility_FuncProp_VisibilityExp_TextField_Id).set(FuncProps[1]);
			retValue = true;
			ClicksUtil.clickButtons(IClicksConst.saveBtn);
		}
		
		ClicksUtil.clickButtons(IClicksConst.backBtn);
		
		return retValue;
	}
	
	public boolean addEFormFieldCalcValueFunc(String[] FuncProps) throws Exception {
		retValue = false;

		IE ie = IEUtil.getActiveIE();
		
		EFormsUtil.expandFormPlannerNode(eformField.getTempFormlet(),
				EFormsUtil.EManipulatePlannerNode.expandNode.ordinal());

		EFormsUtil.openNewOpjectDetail(eformField.getEFormFieldId(),EFormsUtil.EAddObjectsFormPlanner.addEformFieldFunction.ordinal());
		
		if(FuncProps.length == 3)
		{
			ie.selectList(id, IAlleFormsFunctionsConst.eFormField_FunctionType_DropdownField_Id).select(IAlleFormsFunctionsConst.eFormField_CalcValue_FuncType);
			
			//delay
			GeneralUtil.takeANap(2.0);
			ie.textField(id, IAlleFormsFunctionsConst.eFormFieldCalcValue_FuncProp_TargetGridCell_TextField_Id).set(FuncProps[0]);
			ie.textField(id,IAlleFormsFunctionsConst.eFormFieldCalcValue_FuncProp_ValueExp_TextField_Id).set(FuncProps[1]);
			ie.selectList(id,IAlleFormsFunctionsConst.eFormFieldCalcValue_FuncProp_InvokeWhen_Dropdown_Id).select(FuncProps[2]);
			retValue = true;
			ClicksUtil.clickButtons(IClicksConst.saveBtn);
		}
		
		ClicksUtil.clickButtons(IClicksConst.backBtn);
		
		return retValue;
	}
	
	public boolean addEFormFieldCalcReadOnlyFunc(String[] funcProps) throws Exception {
		retValue = false;

		IE ie = IEUtil.getActiveIE();
		
		EFormsUtil.expandFormPlannerNode(eformField.getTempFormlet(),
				EFormsUtil.EManipulatePlannerNode.expandNode.ordinal());
		
		EFormsUtil.openNewOpjectDetail(eformField.getEFormFieldId(),EFormsUtil.EAddObjectsFormPlanner.addEformFieldFunction.ordinal());
		
		if (funcProps.length == 2)
		{
			ie.selectList(id, IAlleFormsFunctionsConst.eFormField_FunctionType_DropdownField_Id).select(IAlleFormsFunctionsConst.eFormField_CalcReadOnly_FuncType);
			
			//delay
			GeneralUtil.takeANap(2.0);
			ie.textField(id, IAlleFormsFunctionsConst.eFormFieldCalcReadOnly_FuncProp_TargetGridCell_TextField_Id).set(funcProps[0]);
			ie.textField(id, IAlleFormsFunctionsConst.eFormFieldCalcReadOnly_FuncProp_ReadOnlyExp_TextField_Id).set(funcProps[1]);
			
			ClicksUtil.clickButtons(IClicksConst.saveAndBackOldBtn);
			retValue = true;
		}
		
		return retValue;
	}
	
	public boolean selectFunctionType() throws Exception {
		retValue = false;

		IE ie = IEUtil.getActiveIE();
		
		EFormsUtil.expandFormPlannerNode(eformField.getTempFormlet(),
				EFormsUtil.EManipulatePlannerNode.expandNode.ordinal());
		
		log.debug(super.getEFormFieldId());
		log.debug(this.getEFormFieldId());
		
		EFormsUtil.openNewOpjectDetail(eformField.getEFormFieldId(),EFormsUtil.EAddObjectsFormPlanner.addEformFieldFunction.ordinal());
		
		if(ie.selectList(id, IAlleFormsFunctionsConst.eFormField_FunctionType_DropdownField_Id).exists())
		{
			ie.selectList(id, IAlleFormsFunctionsConst.eFormField_FunctionType_DropdownField_Id).select(this.getEFormFieldFunctionName());
			retValue = true;
		}	
		
		return retValue;
	}
	
	public boolean addFunctionWithInvokeWhen() throws Exception {
	
		retValue = false;
		
		boolean isSourceField = false;

		IE ie = IEUtil.getActiveIE();
		
		Assert.assertTrue(selectFunctionType(), "Fail: Could not select " + this.getEFormFieldFunctionName() + " Function Type!");
		
		ie.selectList(id,IEFormFieldsFunctionsConst.eFormFieldBringForward_FuncProp_InvokeWhen_DropDown_Id).select(this.getInvokeWhen());
		
		if(this.getSourceField1() != null)
		{
			isSourceField = true;
			
			ie.textField(id, IEFormFieldsFunctionsConst.eFormFieldBringForward_FuncProp_SourceField_TextField_Id).set(this.getSourceField1());
		}
		
		if(this.getSourceGridCell() != null)
		{
			ie.textField(id, IEFormFieldsFunctionsConst.eFormFieldBringForward_FuncProp_SourceGridCell_TextField_Id).set(this.getSourceGridCell());
			
		}
		
		if(this.getTargetGridCell() != null)
		{
			if(isSourceField)
			{
				ie.textField(id, IEFormFieldsFunctionsConst.eFormFieldBringForward_FuncProp_TargetGridCell_TextField_Id).set(this.getTargetGridCell());
			}
			else
			{
				ie.textField(id, IEFormFieldsFunctionsConst.eFormFieldCalcValue_FuncProp_TargetGridCell_TextField_Id).set(this.getTargetGridCell());
			}
			
		}
		
		if(this.getExpression() != null)
		{
			ie.textField(id, IEFormFieldsFunctionsConst.eFormFieldCalcValue_FuncProp_ValueExp_TextField_Id).set(this.getExpression());
		}
		
		if(this.getEFormFieldReference() != null)
		{
			ie.selectList(id, IEFormFieldsFunctionsConst.eFormFieldCalculatedFunctions_EFormFieldReference_DropDown_Id).select(this.getEFormFieldReference());
		}
		
		if(this.getSystemVariableReference() != null)
		{
			ie.selectList(id, IEFormFieldsFunctionsConst.eFormFieldCalculatedFunctions_SystemVariableReference_DropDown_Id).select(this.getSystemVariableReference());
		}
		
		if(this.getDataObjectMethod() != null)
		{
			ie.selectList(id, IEFormFieldsFunctionsConst.eFormFieldCalculatedFunctions_DataObjectMethod_DropDown_Id).select(this.getDataObjectMethod());
		}
		
		return retValue;
	}
	
	
	public boolean addOtherCalculatedFunctions() throws Exception {
		
		retValue = false;

		IE ie = IEUtil.getActiveIE();
		
		Assert.assertTrue(selectFunctionType(), "Fail: Could not select " + this.getEFormFieldFunctionName() + " Function Type!");
		
		if(this.getTargetGridCell() != null)
		{
			ie.textField(id, IEFormFieldsFunctionsConst.eFormFieldCalcVisibility_FuncProp_TargetGridCell_TextField_Id).set(this.getTargetGridCell());
		}
		
		if(this.getExpression() != null)
		{
			ie.textField(id, IEFormFieldsFunctionsConst.eFormFieldCalcVisibility_FuncProp_VisibilityExp_TextField_Id).set(this.getExpression());
		}
		
		if(this.getEFormFieldReference() != null)
		{
			ie.selectList(id, IEFormFieldsFunctionsConst.eFormFieldCalculatedFunctions_EFormFieldReference_DropDown_Id).select(this.getEFormFieldReference());
		}
		
		if(this.getSystemVariableReference() != null)
		{
			ie.selectList(id, IEFormFieldsFunctionsConst.eFormFieldCalculatedFunctions_SystemVariableReference_DropDown_Id).select(this.getSystemVariableReference());
		}
		
		if(this.getDataObjectMethod() != null)
		{
			ie.selectList(id, IEFormFieldsFunctionsConst.eFormFieldCalculatedFunctions_DataObjectMethod_DropDown_Id).select(this.getDataObjectMethod());
		}

		
		return retValue;
	}
	
	
	public boolean addUserAccessGrant() throws Exception {		
		
		retValue = false;

		IE ie = IEUtil.getActiveIE();
		
		Integer offSet = 0;
		Integer propIndex = 0;
		
		Assert.assertTrue(selectFunctionType(), "Fail: Could not select " + this.getEFormFieldFunctionName() + " Function Type!");
		
		if(this.getExpression() != null)
		{
			offSet = 1;
			ie.textField(id, IEFormFieldsFunctionsConst.eFormFieldCalcVisibility_FuncProp_VisibilityExp_TextField_Id).set(this.getExpression());
		}
		
		if(this.getEFormFieldReference() != null)
		{
			ie.selectList(id, IEFormFieldsFunctionsConst.eFormFieldCalculatedFunctions_EFormFieldReference_DropDown_Id).select(this.getEFormFieldReference());
		}
		
		if(this.getSystemVariableReference() != null)
		{
			ie.selectList(id, IEFormFieldsFunctionsConst.eFormFieldCalculatedFunctions_SystemVariableReference_DropDown_Id).select(this.getSystemVariableReference());
		}
		
		if(this.getDataObjectMethod() != null)
		{
			ie.selectList(id, IEFormFieldsFunctionsConst.eFormFieldCalculatedFunctions_DataObjectMethod_DropDown_Id).select(this.getDataObjectMethod());
		}
		
		if(this.getBeforeOrAfterSubmission() != null)
		{
			ie.selectList(id, IEFormFieldsFunctionsConst.eFormFieldCalcAccessGrant_FuncProp_BeforeOrAfterSub_Dropdown_Id).select(this.getBeforeOrAfterSubmission());
		}
		
		if(this.getTargetGridCell() != null)
		{
			ie.textField(id, IEFormFieldsFunctionsConst.eFormFieldCalcAccessGrant_FuncProp_TargetGridCell_TextField_Id).set(this.getTargetGridCell());
		}
		
		if(this.getApplicantRead() != null)
		{
			propIndex = 3 + offSet;
			ie.checkbox(id, "/" + propIndex.toString() + IEFormFieldsFunctionsConst.eFormFieldCalcAccessGrant_FuncProp_Applicant_Checkbox_Id).set(this.getApplicantRead().booleanValue());
		}
		
		if(this.getApplicantWrite() != null)
		{
			propIndex = 4 + offSet;
			ie.checkbox(id, "/" + propIndex.toString() + IEFormFieldsFunctionsConst.eFormFieldCalcAccessGrant_FuncProp_Applicant_Checkbox_Id).set(this.getApplicantWrite().booleanValue());
		}
		
		if(this.getAllStepStaffRead() != null)
		{
			propIndex = 6 + offSet;
			ie.checkbox(id, "/" + propIndex.toString() + IEFormFieldsFunctionsConst.eFormFieldCalcAccessGrant_FuncProp_AllStepStaff_Checkbox_Id).set(this.getAllStepStaffRead().booleanValue());
		}
		
		if(this.getAllStepStaffWrite() != null)
		{
			propIndex = 7 + offSet;
			ie.checkbox(id, "/" + propIndex.toString() + IEFormFieldsFunctionsConst.eFormFieldCalcAccessGrant_FuncProp_AllStepStaff_Checkbox_Id).set(this.getAllStepStaffWrite().booleanValue());
		}
		
		if(this.getAssignedProjectStepOfficersRead() != null)
		{
			propIndex = 9 + offSet;
			ie.checkbox(id, "/" + propIndex.toString() + IEFormFieldsFunctionsConst.eFormFieldCalcAccessGrant_FuncProp_AssignProjStepOfficers_Checkbox_Id).set(this.getAssignedProjectStepOfficersRead().booleanValue());
		}
		
		if(this.getAssignedProjectStepOfficersWrite() != null)
		{
			propIndex = 10 + offSet;
			ie.checkbox(id, "/" + propIndex.toString() + IEFormFieldsFunctionsConst.eFormFieldCalcAccessGrant_FuncProp_AssignProjStepOfficers_Checkbox_Id).set(this.getAssignedProjectStepOfficersWrite().booleanValue());
		}
		
		if(this.getAllAssignedProjectOfficersRead() != null)
		{
			propIndex = 12 + offSet;
			ie.checkbox(id, "/" + propIndex.toString() + IEFormFieldsFunctionsConst.eFormFieldCalcAccessGrant_FuncProp_AllAssignedProjOfficers_Checkbox_Id).set(this.getAllAssignedProjectOfficersRead().booleanValue());
		}
		
		if(this.getAllAssignedProjectOfficersWrite())
		{
			propIndex = 13 + offSet;
			ie.checkbox(id, "/" + propIndex.toString() + IEFormFieldsFunctionsConst.eFormFieldCalcAccessGrant_FuncProp_AllAssignedProjOfficers_Checkbox_Id).set(this.getAllAssignedProjectOfficersWrite().booleanValue());
		}
		
		if(this.getAllProjectOfficersRead() != null)
		{
			propIndex = 15 + offSet;
			ie.checkbox(id, "/" + propIndex.toString() + IEFormFieldsFunctionsConst.eFormFieldCalcAccessGrant_FuncProp_AllProjOfficers_Checkbox_Id).set(this.getAllProjectOfficersRead().booleanValue());
		}
		
		if(this.getAllProjectOfficersWrite() != null)
		{
			propIndex = 16 + offSet;
			ie.checkbox(id, "/" + propIndex.toString() + IEFormFieldsFunctionsConst.eFormFieldCalcAccessGrant_FuncProp_AllProjOfficers_Checkbox_Id).set(this.getAllProjectOfficersWrite().booleanValue());
		}
		
		if((this.getReadAccessGroups() != null) && (this.getReadAccessGroups().length > 0))
		{
			propIndex = 18 + offSet;
			for(String groupName :this.getReadAccessGroups())
			{
				ie.selectList(id, "/" + propIndex.toString() + IEFormFieldsFunctionsConst.eFormFieldCalcAccessGrant_FuncProp_M2MReadAvailableGroups_SelectList_Id).select(groupName);
				ClicksUtil.clickButtons(IClicksConst.m2MSingleForBtn);
			}
			
		}
		
		if((this.getWriteAccessGroups() != null) && (this.getWriteAccessGroups().length > 0))
		{
			propIndex = 19 + offSet;
			for(String groupName :this.getWriteAccessGroups())
			{
				ie.selectList(id, "/" + propIndex.toString() + IEFormFieldsFunctionsConst.eFormFieldCalcAccessGrant_FuncProp_M2MReadAvailableGroups_SelectList_Id).select(groupName);
				ClicksUtil.clickButtons(IClicksConst.m2MSingleForBtn);
			}
		}

		return retValue;
	}
	
	public void addGPSorNumSumFunction() throws Exception {


		IE ie = IEUtil.getActiveIE();
		
		Assert.assertTrue(selectFunctionType(), "Fail: Could not select " + this.getEFormFieldFunctionName() + " Function Type!");
		
		if((this.getButtonIdentifier() != null) || (this.getSourceField1() != null))
		{
			if(this.getSourceField1() != null)
			{
				ie.textField(id,IEFormFieldsFunctionsConst.eFormFieldGeneratePaymentSchedule_FuncProp_ButtonId_TextField_Id).set(this.getSourceField1());
			}
			else
			{
				ie.textField(id,IEFormFieldsFunctionsConst.eFormFieldGeneratePaymentSchedule_FuncProp_ButtonId_TextField_Id).set(this.getButtonIdentifier());
			}
			
		}
		
		if((this.getMilestoneName() != null) || (this.getSourceField2() != null))
		{
			if(this.getSourceField1() != null)
			{
				ie.textField(id,IEFormFieldsFunctionsConst.eFormFieldGeneratePaymentSchedule_FuncProp_MilestoneName_TextField_Id).set(this.getSourceField2());
			}
			else
			{
				ie.textField(id,IEFormFieldsFunctionsConst.eFormFieldGeneratePaymentSchedule_FuncProp_MilestoneName_TextField_Id).set(this.getMilestoneName());
			}
		}
		
		if((this.getMilestoneStartDate() != null) || (this.getSourceField3() != null))
		{
			if(this.getSourceField1() != null)
			{
				ie.textField(id,IEFormFieldsFunctionsConst.eFormFieldGeneratePaymentSchedule_FuncProp_MilestoneStartDate_TextField_Id).set(this.getSourceField3());
			}
			else
			{
				ie.textField(id,IEFormFieldsFunctionsConst.eFormFieldGeneratePaymentSchedule_FuncProp_MilestoneStartDate_TextField_Id).set(this.getMilestoneStartDate());
			}
		}
		
		if((this.getMilestoneEndDate() != null) || (this.getSourceField4() != null))
		{
			if(this.getSourceField1() != null)
			{
				ie.textField(id,IEFormFieldsFunctionsConst.eFormFieldGeneratePaymentSchedule_FuncProp_MilestoneEndDate_TextField_Id).set(this.getSourceField4());
			}
			else
			{
				ie.textField(id,IEFormFieldsFunctionsConst.eFormFieldGeneratePaymentSchedule_FuncProp_MilestoneEndDate_TextField_Id).set(this.getMilestoneEndDate());
			}
		}
		
		if(this.getThisFieldValue() != null)
		{
			ie.selectList(id, IEFormFieldsFunctionsConst.eFormFieldNumericSum_FuncProp_ThisFieldValue_Dropdown_Id).select(this.getThisFieldValue());
		}
		
	}

	/**
	 * @return the eFormFieldFunctionName
	 */
	public String getEFormFieldFunctionName() {
		return eFormFieldFunctionName;
	}

	/**
	 * @param formFieldFunctionName the eFormFieldFunctionName to set
	 */
	public void setEFormFieldFunctionName(String formFieldFunctionName) {
		eFormFieldFunctionName = formFieldFunctionName;
	}

	/**
	 * @return the sourceField1
	 */
	public String getSourceField1() {
		return sourceField1;
	}

	/**
	 * @param sourceField1 the sourceField1 to set
	 */
	public void setSourceField1(String sourceField1) {
		this.sourceField1 = sourceField1;
	}

	/**
	 * @return the sourceField2
	 */
	public String getSourceField2() {
		return sourceField2;
	}

	/**
	 * @param sourceField2 the sourceField2 to set
	 */
	public void setSourceField2(String sourceField2) {
		this.sourceField2 = sourceField2;
	}

	/**
	 * @return the sourceField3
	 */
	public String getSourceField3() {
		return sourceField3;
	}

	/**
	 * @param sourceField3 the sourceField3 to set
	 */
	public void setSourceField3(String sourceField3) {
		this.sourceField3 = sourceField3;
	}

	/**
	 * @return the sourceField4
	 */
	public String getSourceField4() {
		return sourceField4;
	}

	/**
	 * @param sourceField4 the sourceField4 to set
	 */
	public void setSourceField4(String sourceField4) {
		this.sourceField4 = sourceField4;
	}

	/**
	 * @return the thisFieldValue
	 */
	public String getThisFieldValue() {
		return thisFieldValue;
	}

	/**
	 * @param thisFieldValue the thisFieldValue to set
	 */
	public void setThisFieldValue(String thisFieldValue) {
		this.thisFieldValue = thisFieldValue;
	}

	/**
	 * @return the sourceGridCell
	 */
	public String getSourceGridCell() {
		return sourceGridCell;
	}

	/**
	 * @param sourceGridCell the sourceGridCell to set
	 */
	public void setSourceGridCell(String sourceGridCell) {
		this.sourceGridCell = sourceGridCell;
	}

	/**
	 * @return the targetGridCell
	 */
	public String getTargetGridCell() {
		return targetGridCell;
	}

	/**
	 * @param targetGridCell the targetGridCell to set
	 */
	public void setTargetGridCell(String targetGridCell) {
		this.targetGridCell = targetGridCell;
	}

	/**
	 * @return the invokeWhen
	 */
	public String getInvokeWhen() {
		return invokeWhen;
	}

	/**
	 * @param invokeWhen the invokeWhen to set
	 */
	public void setInvokeWhen(String invokeWhen) {
		this.invokeWhen = invokeWhen;
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
	 * @return the buttonIdentifier
	 */
	public String getButtonIdentifier() {
		return buttonIdentifier;
	}

	/**
	 * @param buttonIdentifier the buttonIdentifier to set
	 */
	public void setButtonIdentifier(String buttonIdentifier) {
		this.buttonIdentifier = buttonIdentifier;
	}

	/**
	 * @return the milestoneName
	 */
	public String getMilestoneName() {
		return milestoneName;
	}

	/**
	 * @param milestoneName the milestoneName to set
	 */
	public void setMilestoneName(String milestoneName) {
		this.milestoneName = milestoneName;
	}

	/**
	 * @return the milestoneStartDate
	 */
	public String getMilestoneStartDate() {
		return milestoneStartDate;
	}

	/**
	 * @param milestoneStartDate the milestoneStartDate to set
	 */
	public void setMilestoneStartDate(String milestoneStartDate) {
		this.milestoneStartDate = milestoneStartDate;
	}

	/**
	 * @return the milestoneEndDate
	 */
	public String getMilestoneEndDate() {
		return milestoneEndDate;
	}

	/**
	 * @param milestoneEndDate the milestoneEndDate to set
	 */
	public void setMilestoneEndDate(String milestoneEndDate) {
		this.milestoneEndDate = milestoneEndDate;
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
	 * @return the applicantRead
	 */
	public Boolean getApplicantRead() {
		return applicantRead;
	}

	/**
	 * @param applicantRead the applicantRead to set
	 */
	public void setApplicantRead(Boolean applicantRead) {
		this.applicantRead = applicantRead;
	}

	/**
	 * @return the allStepStaffRead
	 */
	public Boolean getAllStepStaffRead() {
		return allStepStaffRead;
	}

	/**
	 * @param allStepStaffRead the allStepStaffRead to set
	 */
	public void setAllStepStaffRead(Boolean allStepStaffRead) {
		this.allStepStaffRead = allStepStaffRead;
	}

	/**
	 * @return the assignedProjectStepOfficersRead
	 */
	public Boolean getAssignedProjectStepOfficersRead() {
		return assignedProjectStepOfficersRead;
	}

	/**
	 * @param assignedProjectStepOfficersRead the assignedProjectStepOfficersRead to set
	 */
	public void setAssignedProjectStepOfficersRead(
			Boolean assignedProjectStepOfficersRead) {
		this.assignedProjectStepOfficersRead = assignedProjectStepOfficersRead;
	}

	/**
	 * @return the allAssignedProjectOfficersRead
	 */
	public Boolean getAllAssignedProjectOfficersRead() {
		return allAssignedProjectOfficersRead;
	}

	/**
	 * @param allAssignedProjectOfficersRead the allAssignedProjectOfficersRead to set
	 */
	public void setAllAssignedProjectOfficersRead(
			Boolean allAssignedProjectOfficersRead) {
		this.allAssignedProjectOfficersRead = allAssignedProjectOfficersRead;
	}

	/**
	 * @return the allProjectOfficersRead
	 */
	public Boolean getAllProjectOfficersRead() {
		return allProjectOfficersRead;
	}

	/**
	 * @param allProjectOfficersRead the allProjectOfficersRead to set
	 */
	public void setAllProjectOfficersRead(Boolean allProjectOfficersRead) {
		this.allProjectOfficersRead = allProjectOfficersRead;
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
	 * @return the applicantWrite
	 */
	public Boolean getApplicantWrite() {
		return applicantWrite;
	}

	/**
	 * @param applicantWrite the applicantWrite to set
	 */
	public void setApplicantWrite(Boolean applicantWrite) {
		this.applicantWrite = applicantWrite;
	}

	/**
	 * @return the allStepStaffWrite
	 */
	public Boolean getAllStepStaffWrite() {
		return allStepStaffWrite;
	}

	/**
	 * @param allStepStaffWrite the allStepStaffWrite to set
	 */
	public void setAllStepStaffWrite(Boolean allStepStaffWrite) {
		this.allStepStaffWrite = allStepStaffWrite;
	}

	/**
	 * @return the assignedProjectStepOfficersWrite
	 */
	public Boolean getAssignedProjectStepOfficersWrite() {
		return assignedProjectStepOfficersWrite;
	}

	/**
	 * @param assignedProjectStepOfficersWrite the assignedProjectStepOfficersWrite to set
	 */
	public void setAssignedProjectStepOfficersWrite(
			Boolean assignedProjectStepOfficersWrite) {
		this.assignedProjectStepOfficersWrite = assignedProjectStepOfficersWrite;
	}

	/**
	 * @return the allAssignedProjectOfficersWrite
	 */
	public Boolean getAllAssignedProjectOfficersWrite() {
		return allAssignedProjectOfficersWrite;
	}

	/**
	 * @param allAssignedProjectOfficersWrite the allAssignedProjectOfficersWrite to set
	 */
	public void setAllAssignedProjectOfficersWrite(
			Boolean allAssignedProjectOfficersWrite) {
		this.allAssignedProjectOfficersWrite = allAssignedProjectOfficersWrite;
	}

	/**
	 * @return the allProjectOfficersWrite
	 */
	public Boolean getAllProjectOfficersWrite() {
		return allProjectOfficersWrite;
	}

	/**
	 * @param allProjectOfficersWrite the allProjectOfficersWrite to set
	 */
	public void setAllProjectOfficersWrite(Boolean allProjectOfficersWrite) {
		this.allProjectOfficersWrite = allProjectOfficersWrite;
	}

	/**
	 * @return the writeAccessGroups
	 */
	public String[] getWriteAccessGroups() {
		return writeAccessGroups;
	}

	/**
	 * @param writeAccessGroups the writeAccessGroups to set
	 */
	public void setWriteAccessGroups(String[] writeAccessGroups) {
		this.writeAccessGroups = writeAccessGroups;
	}

	/**
	 * @return the eformField
	 */
	public EFormField getEformField() {
		return eformField;
	}

	/**
	 * @param eformField the eformField to set
	 */
	public void setEformField(EFormField eformField) {
		this.eformField = eformField;
	}
}
