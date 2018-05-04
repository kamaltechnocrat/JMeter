/**
 * 
 */
package test_Suite.lib.workflow;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import test_Suite.constants.workflow.IProgramsConst.EstepManagment;
import test_Suite.constants.workflow.IStepsConst.EDecisionProp;
import test_Suite.constants.workflow.IStepsConst.EEvalProp;
import test_Suite.constants.workflow.IStepsConst.EPostAwardProp;
import test_Suite.constants.workflow.IStepsConst.EStepParams;
import test_Suite.constants.workflow.IStepsConst.EStepProps;
import test_Suite.constants.workflow.IStepsConst.EStepsType;
import test_Suite.constants.workflow.IStepsConst.ESubProp;
import test_Suite.constants.workflow.IStepsConst.EWSSProp;

/**
 * @author mshakshouki
 *
 */
public class FOPPSteps {
	
	private FundingOpportunity fopp = null;
	
	private String stepIdent;
	private String stepName;
	private String stepFullIdent;
	private String stepFullName;
	private String stepType;
	private String stepStartDate;
	private String stepEndDate;
	private boolean stepCritical;
	private String stepReExecute;

	private EStepsType eStepType;	
	private LinkedHashMap<EStepsType, Steps> stepsLHM;
	private Set<Map.Entry<EStepsType, Steps>> stepsSet;	

	private String[] stepParamsArr;	
	private LinkedHashMap<EStepParams,Object> stepParamsLHM;
	private Set<Map.Entry<EStepParams, Object>> stepParamsSet;
	
	private String eFormName = null;
	private String eFormIdent = null;
	
	private String stepEvalType;
	private String stepQuorumAmnt;
	private boolean stepAutoAssign;
	private boolean stepCOIRules;
	
	private List<String> paIPASSeFormsIdentLst;	
	private List<String> wssStepsIdentLst;	
	private List<String> decisionStepsPropLst;
	
	private String[] stepPropsArr;	
	private LinkedHashMap<EStepProps,Object> stepPropsLHM;
	private Set<Map.Entry<EStepProps, Object>> stepPropsSet;
	
	private List<String> stepEFormAccessLst;
	private List<String> stepEFormDataLst;
	
	private String includeAppProfile = "Never";
	private String includeAdminEForm = "Never";

	private List<String> stepStatusLabelLst;
	private List<String> stepSubStatusLableLst;
	private List<String> stepDocumentsLst;
	
	private LinkedHashMap<EstepManagment, List<String>> stepMngmtLHM;
	private Set<Map.Entry<EstepManagment, List<String>>> stepMngmtSet;
	
	private List<String> stepStaffsGrpsLst = null;
	private List<String> stepOfficersGrpsLst = null;
	
	private String stepOfficerGrp;
	private String stepStaffGrp;
	
	private LinkedHashMap<String, List<String>> stepStaffLHM;
	private Set<Map.Entry<String, List<String>>> stepStaffSet;
	
	private LinkedHashMap<String, List<String>> stepOfficersLHM;
	private Set<Map.Entry<String, List<String>>> stepOfficersSet;
	
	private LinkedHashMap<String, Notifications> notifsLHM;
	private Set<Map.Entry<String, Notifications>> notifsSet;

	/*******Constractors***************/
	
	public FOPPSteps() {}
	
	public FOPPSteps(FundingOpportunity fopp, String stepName,EStepsType eStepType) {
		
		this.fopp = fopp;
		
		this.stepFullIdent = fopp.getFoppLetter() + fopp.getFoppPreFix() + stepName + fopp.getFoppPostfix();
		
		this.stepFullName = removeDashes(this.stepFullIdent);	
		
		this.eStepType = eStepType;
	}
	
	public FOPPSteps(FundingOpportunity fopp, String[][] stepDblArr,EStepsType eStepType) throws Exception {
		
		this.eStepType = eStepType;		
		
		this.fopp = fopp;
		
		this.setStepParamsArr(stepDblArr[0]);
		
		initializeStepParams();
		
		this.stepPropsArr = stepDblArr[1];
		
		setStepProp();
	}
	
	/******** end of Constractors *****************/
	
	/*********Class Methods *********************/
	
	public void initializeStepParams() throws Exception {
		
		this.stepName = this.stepParamsArr[0];
		
		this.stepType = this.stepParamsArr[1];
		
		this.stepFullIdent = this.fopp.getFoppLetter() + this.fopp.getFoppPreFix() + this.stepName + this.fopp.getFoppPostfix();
		
		this.stepFullName = removeDashes(this.stepFullIdent);
		
		this.stepStartDate = this.fopp.getStartDate();
		
		this.stepEndDate = this.fopp.getEndDate();
		
		this.stepCritical = Boolean.parseBoolean(stepParamsArr[2]);
		
		this.stepReExecute = this.stepParamsArr[3];
		
		this.stepOfficerGrp = this.stepParamsArr[4];
		
		this.stepStaffGrp = this.stepParamsArr[5];
		
		LinkedHashMap<EStepParams,Object> lhm = new LinkedHashMap<EStepParams,Object>();
		
		this.stepName = this.stepParamsArr[0];		
		this.stepFullIdent = this.fopp.getFoppLetter() + this.fopp.getFoppPreFix() + this.stepName + this.fopp.getFoppPostfix();		
		this.stepFullName = removeDashes(this.stepFullIdent);
		
		this.stepStaffGrp = stepParamsArr[5];
		
		lhm.put(EStepParams.NAME, this.stepParamsArr[0]);
		lhm.put(EStepParams.IDENT, this.stepFullIdent);
		lhm.put(EStepParams.NOTES, this.stepFullName);
		lhm.put(EStepParams.TYPE, this.stepParamsArr[1]);
		lhm.put(EStepParams.OFFICER, this.stepParamsArr[4]);
		lhm.put(EStepParams.STARTDATE, this.fopp.getStartDate());
		lhm.put(EStepParams.ENDDATE, this.fopp.getEndDate());
		lhm.put(EStepParams.CRITICAL, Boolean.parseBoolean(this.stepParamsArr[2]));
		lhm.put(EStepParams.RE_EXECUTE, this.stepParamsArr[3]);
		lhm.put(EStepParams.TITLE, this.stepFullName);
		
		this.stepParamsLHM = lhm;
		this.stepParamsSet = lhm.entrySet();		
	}
	
	public void setStepProp() throws Exception {
		
		switch (this.eStepType) {
		
		case SUB: {
			setSubProp();
			break;
		}
		
		case POSS: {
			setSubProp();
			break;
		}
		
		case AWARD: {
			setSubProp();
			break;
		}
		
		case REVIEW: {
			setEvalProp();
			break;
		}
		
		case APPROVAL: {
			setEvalProp();
			break;
		}
		
		case POST_AWARD: {
			setPostAwardProp();
			break;
		}
		
		case IPASS: {
			break;
		}
		
		case DECISION: {
			setDecisionProp();
			break;
		}
		
		case WSS: {
			setWSSProp();
			break;
		}
		default:
			break;
		}		
	}
	
	public void setEvalProp() throws Exception {
		
		LinkedHashMap<EStepProps,Object> lhm = new LinkedHashMap<EStepProps,Object>();
		
		lhm.put(EStepProps.valueOf(EEvalProp.values()[0].name()), this.stepPropsArr[0] + this.fopp.getFoppPostfix());
		
		for(int x=1; x<this.stepPropsArr.length; x++) {
			
			lhm.put(EStepProps.valueOf(EEvalProp.values()[x].name()), this.stepPropsArr[x]);
		}
		
		this.stepPropsLHM = lhm;
		this.stepPropsSet = lhm.entrySet();
	}
	
	public void setSubProp() throws Exception {
		
		LinkedHashMap<EStepProps,Object> lhm = new LinkedHashMap<EStepProps,Object>();
		
		for(int x=0; x<this.stepPropsArr.length; x++) {
			
			lhm.put(EStepProps.valueOf(ESubProp.values()[x].name()), this.stepPropsArr[x] + this.fopp.getFoppPostfix());
		}
		
		this.stepPropsLHM = lhm;
		this.stepPropsSet = lhm.entrySet();		
	}
	
	public void setClaimProp() throws Exception {
		
	}
	
	public void setPostAwardProp() throws Exception {
		
		LinkedHashMap<EStepProps,Object> lhm = new LinkedHashMap<EStepProps,Object>();
		
		for(int x=0; x<this.stepPropsArr.length; x++) {
			
			lhm.put(EStepProps.valueOf(EPostAwardProp.values()[x].name()), this.stepPropsArr[x]);
		}
		
		this.stepPropsLHM = lhm;
		this.stepPropsSet = lhm.entrySet();
		
	}
	
	public void setWSSProp() throws Exception {
		
		LinkedHashMap<EStepProps,Object> lhm = new LinkedHashMap<EStepProps,Object>();
		
		for(int x=0; x<this.stepPropsArr.length; x++) {
			
			lhm.put(EStepProps.valueOf(EWSSProp.values()[x].name()), this.stepPropsArr[x]);
		}
		
		this.stepPropsLHM = lhm;
		this.stepPropsSet = lhm.entrySet();
		
	}
	
	public void setDecisionProp() throws Exception {
		
		LinkedHashMap<EStepProps,Object> lhm = new LinkedHashMap<EStepProps,Object>();
		
		for(int x=0; x<this.stepPropsArr.length; x++) {
			
			lhm.put(EStepProps.valueOf(EDecisionProp.values()[x].name()), this.stepPropsArr[x]);
		}
		
		this.stepPropsLHM = lhm;
		this.stepPropsSet = lhm.entrySet();
		
	}
	
	public void initializeStep(Object[] stepProps) throws Exception {
		
	}
	
	public String removeDashes(String string)
	{
		return string.replace("-", " ");
	}


	
	/********* end of Class Methods ************/
	
	/******** GETTERS AND SETTER ************/

	/**
	 * @return the stepIdent
	 */
	public String getStepIdent() {
		return stepIdent;
	}

	/**
	 * @param stepIdent the stepIdent to set
	 */
	public void setStepIdent(String stepIdent) {
		this.stepIdent = stepIdent;
	}

	/**
	 * @return the stepName
	 */
	public String getStepName() {
		return stepName;
	}

	/**
	 * @param stepName the stepName to set
	 */
	public void setStepName(String stepName) {
		this.stepName = stepName;
	}

	/**
	 * @return the stepFullIdent
	 */
	public String getStepFullIdent() {
		return stepFullIdent;
	}

	/**
	 * @param stepFullIdent the stepFullIdent to set
	 */
	public void setStepFullIdent(String stepFullIdent) {
		this.stepFullIdent = stepFullIdent;
	}

	/**
	 * @return the stepFullName
	 */
	public String getStepFullName() {
		return stepFullName;
	}

	/**
	 * @param stepFullName the stepFullName to set
	 */
	public void setStepFullName(String stepFullName) {
		this.stepFullName = stepFullName;
	}

	/**
	 * @return the stepType
	 */
	public String getStepType() {
		return stepType;
	}

	/**
	 * @param stepType the stepType to set
	 */
	public void setStepType(String stepType) {
		this.stepType = stepType;
	}

	/**
	 * @return the stepStartDate
	 */
	public String getStepStartDate() {
		return stepStartDate;
	}

	/**
	 * @param stepStartDate the stepStartDate to set
	 */
	public void setStepStartDate(String stepStartDate) {
		this.stepStartDate = stepStartDate;
	}

	/**
	 * @return the stepEndDate
	 */
	public String getStepEndDate() {
		return stepEndDate;
	}

	/**
	 * @param stepEndDate the stepEndDate to set
	 */
	public void setStepEndDate(String stepEndDate) {
		this.stepEndDate = stepEndDate;
	}

	/**
	 * @return the stepCritical
	 */
	public boolean isStepCritical() {
		return stepCritical;
	}

	/**
	 * @param stepCritical the stepCritical to set
	 */
	public void setStepCritical(boolean stepCritical) {
		this.stepCritical = stepCritical;
	}

	/**
	 * @return the stepReExecute
	 */
	public String getStepReExecute() {
		return stepReExecute;
	}

	/**
	 * @param stepReExecute the stepReExecute to set
	 */
	public void setStepReExecute(String stepReExecute) {
		this.stepReExecute = stepReExecute;
	}

	/**
	 * @return the stepEvalType
	 */
	public String getStepEvalType() {
		return stepEvalType;
	}

	/**
	 * @param stepEvalType the stepEvalType to set
	 */
	public void setStepEvalType(String stepEvalType) {
		this.stepEvalType = stepEvalType;
	}

	/**
	 * @return the stepQuorumAmnt
	 */
	public String getStepQuorumAmnt() {
		return stepQuorumAmnt;
	}

	/**
	 * @param stepQuorumAmnt the stepQuorumAmnt to set
	 */
	public void setStepQuorumAmnt(String stepQuorumAmnt) {
		this.stepQuorumAmnt = stepQuorumAmnt;
	}

	/**
	 * @return the stepAutoAssign
	 */
	public boolean isStepAutoAssign() {
		return stepAutoAssign;
	}

	/**
	 * @param stepAutoAssign the stepAutoAssign to set
	 */
	public void setStepAutoAssign(boolean stepAutoAssign) {
		this.stepAutoAssign = stepAutoAssign;
	}

	/**
	 * @return the stepCOIRules
	 */
	public boolean isStepCOIRules() {
		return stepCOIRules;
	}

	/**
	 * @param stepCOIRules the stepCOIRules to set
	 */
	public void setStepCOIRules(boolean stepCOIRules) {
		this.stepCOIRules = stepCOIRules;
	}

	/**
	 * @return the paIPASSeFormsIdentLst
	 */
	public List<String> getPaIPASSeFormsIdentLst() {
		return paIPASSeFormsIdentLst;
	}

	/**
	 * @param paIPASSeFormsIdentLst the paIPASSeFormsIdentLst to set
	 */
	public void setPaIPASSeFormsIdentLst(List<String> paIPASSeFormsIdentLst) {
		this.paIPASSeFormsIdentLst = paIPASSeFormsIdentLst;
	}

	/**
	 * @return the wssStepsIdentLst
	 */
	public List<String> getWssStepsIdentLst() {
		return wssStepsIdentLst;
	}

	/**
	 * @param wssStepsIdentLst the wssStepsIdentLst to set
	 */
	public void setWssStepsIdentLst(List<String> wssStepsIdentLst) {
		this.wssStepsIdentLst = wssStepsIdentLst;
	}

	/**
	 * @return the decisionStepsPropLst
	 */
	public List<String> getDecisionStepsPropLst() {
		return decisionStepsPropLst;
	}

	/**
	 * @param decisionStepsPropLst the decisionStepsPropLst to set
	 */
	public void setDecisionStepsPropLst(List<String> decisionStepsPropLst) {
		this.decisionStepsPropLst = decisionStepsPropLst;
	}

	/**
	 * @return the eStepType
	 */
	public EStepsType getEStepType() {
		return eStepType;
	}

	/**
	 * @param stepType the eStepType to set
	 */
	public void setEStepType(EStepsType stepType) {
		eStepType = stepType;
	}

	/**
	 * @return the fopp
	 */
	public FundingOpportunity getFopp() {
		return fopp;
	}

	/**
	 * @param fopp the fopp to set
	 */
	public void setFopp(FundingOpportunity fopp) {
		this.fopp = fopp;
	}

	/**
	 * @return the stepPropsArr
	 */
	public String[] getStepPropsArr() {
		return stepPropsArr;
	}

	/**
	 * @param stepPropsArr the stepPropsArr to set
	 */
	public void setStepPropsArr(String[] stepPropsArr) {
		this.stepPropsArr = stepPropsArr;
	}

	/**
	 * @return the stepDetailsParamsArr
	 */
	public String[] getStepParamsArr() {
		return stepParamsArr;
	}

	/**
	 * @param stepDetailsParamsArr the stepDetailsParamsArr to set
	 */
	public void setStepParamsArr(String[] stepParamsArr) {
		this.stepParamsArr = stepParamsArr;
	}

	/**
	 * @return the stepPropsLHM
	 */
	public LinkedHashMap<EStepProps, Object> getStepPropsLHM() {
		return stepPropsLHM;
	}

	/**
	 * @param stepPropsLHM the stepPropsLHM to set
	 */
	public void setStepPropsLHM(LinkedHashMap<EStepProps, Object> stepPropsLHM) {
		this.stepPropsLHM = stepPropsLHM;
	}

	/**
	 * @return the stepPropsSet
	 */
	public Set<Map.Entry<EStepProps, Object>> getStepPropsSet() {
		return stepPropsSet;
	}

	/**
	 * @param stepPropsSet the stepPropsSet to set
	 */
	public void setStepPropsSet(Set<Map.Entry<EStepProps, Object>> stepPropsSet) {
		this.stepPropsSet = stepPropsSet;
	}

	/**
	 * @return the stepParamsLHM
	 */
	public LinkedHashMap<EStepParams, Object> getStepParamsLHM() {
		return stepParamsLHM;
	}

	/**
	 * @param stepParamsLHM the stepParamsLHM to set
	 */
	public void setStepParamsLHM(LinkedHashMap<EStepParams, Object> stepParamsLHM) {
		this.stepParamsLHM = stepParamsLHM;
	}

	/**
	 * @return the stepParamsSet
	 */
	public Set<Map.Entry<EStepParams, Object>> getStepParamsSet() {
		return stepParamsSet;
	}

	/**
	 * @param stepParamsSet the stepParamsSet to set
	 */
	public void setStepParamsSet(Set<Map.Entry<EStepParams, Object>> stepParamsSet) {
		this.stepParamsSet = stepParamsSet;
	}

	/**
	 * @return the stepsLHM
	 */
	public LinkedHashMap<EStepsType, Steps> getStepsLHM() {
		return stepsLHM;
	}

	/**
	 * @param stepsLHM the stepsLHM to set
	 */
	public void setStepsLHM(LinkedHashMap<EStepsType, Steps> stepsLHM) {
		this.stepsLHM = stepsLHM;
	}

	/**
	 * @return the stepsSet
	 */
	public Set<Map.Entry<EStepsType, Steps>> getStepsSet() {
		return stepsSet;
	}

	/**
	 * @param stepsSet the stepsSet to set
	 */
	public void setStepsSet(Set<Map.Entry<EStepsType, Steps>> stepsSet) {
		this.stepsSet = stepsSet;
	}

	/**
	 * @return the stepEFormAccessLst
	 */
	public List<String> getStepEFormAccessLst() {
		return stepEFormAccessLst;
	}

	/**
	 * @param stepEFormAccessLst the stepEFormAccessLst to set
	 */
	public void setStepEFormAccessLst(List<String> stepEFormAccessLst) {
		this.stepEFormAccessLst = stepEFormAccessLst;
	}

	/**
	 * @return the stepEFormDataLst
	 */
	public List<String> getStepEFormDataLst() {
		return stepEFormDataLst;
	}

	/**
	 * @param stepEFormDataLst the stepEFormDataLst to set
	 */
	public void setStepEFormDataLst(List<String> stepEFormDataLst) {
		this.stepEFormDataLst = stepEFormDataLst;
	}

	/**
	 * @return the includeAppProfile
	 */
	public String getIncludeAppProfile() {
		return includeAppProfile;
	}

	/**
	 * @param includeAppProfile the includeAppProfile to set
	 */
	public void setIncludeAppProfile(String includeAppProfile) {
		this.includeAppProfile = includeAppProfile;
	}

	/**
	 * @return the includeAdminEForm
	 */
	public String getIncludeAdminEForm() {
		return includeAdminEForm;
	}

	/**
	 * @param includeAdminEForm the includeAdminEForm to set
	 */
	public void setIncludeAdminEForm(String includeAdminEForm) {
		this.includeAdminEForm = includeAdminEForm;
	}

	/**
	 * @return the stepStatusLabelLst
	 */
	public List<String> getStepStatusLabelLst() {
		return stepStatusLabelLst;
	}

	/**
	 * @param stepStatusLabelLst the stepStatusLabelLst to set
	 */
	public void setStepStatusLabelLst(List<String> stepStatusLabelLst) {
		this.stepStatusLabelLst = stepStatusLabelLst;
	}

	/**
	 * @return the stepSubStatusLableLst
	 */
	public List<String> getStepSubStatusLableLst() {
		return stepSubStatusLableLst;
	}

	/**
	 * @param stepSubStatusLableLst the stepSubStatusLableLst to set
	 */
	public void setStepSubStatusLableLst(List<String> stepSubStatusLableLst) {
		this.stepSubStatusLableLst = stepSubStatusLableLst;
	}

	/**
	 * @return the stepDocumentsLst
	 */
	public List<String> getStepDocumentsLst() {
		return stepDocumentsLst;
	}

	/**
	 * @param stepDocumentsLst the stepDocumentsLst to set
	 */
	public void setStepDocumentsLst(List<String> stepDocumentsLst) {
		this.stepDocumentsLst = stepDocumentsLst;
	}

	/**
	 * @return the stepMngmtLHM
	 */
	public LinkedHashMap<EstepManagment, List<String>> getStepMngmtLHM() {
		return stepMngmtLHM;
	}

	/**
	 * @param stepMngmtLHM the stepMngmtLHM to set
	 */
	public void setStepMngmtLHM(
			LinkedHashMap<EstepManagment, List<String>> stepMngmtLHM) {
		this.stepMngmtLHM = stepMngmtLHM;
	}

	/**
	 * @return the stepMngmtSet
	 */
	public Set<Map.Entry<EstepManagment, List<String>>> getStepMngmtSet() {
		return stepMngmtSet;
	}

	/**
	 * @param stepMngmtSet the stepMngmtSet to set
	 */
	public void setStepMngmtSet(
			Set<Map.Entry<EstepManagment, List<String>>> stepMngmtSet) {
		this.stepMngmtSet = stepMngmtSet;
	}

	/**
	 * @return the stepStaffsGrpsLst
	 */
	public List<String> getStepStaffsGrpsLst() {
		return stepStaffsGrpsLst;
	}

	/**
	 * @param stepStaffsGrpsLst the stepStaffsGrpsLst to set
	 */
	public void setStepStaffsGrpsLst(List<String> stepStaffsGrpsLst) {
		this.stepStaffsGrpsLst = stepStaffsGrpsLst;
	}

	/**
	 * @return the stepOfficersGrpsLst
	 */
	public List<String> getStepOfficersGrpsLst() {
		return stepOfficersGrpsLst;
	}

	/**
	 * @param stepOfficersGrpsLst the stepOfficersGrpsLst to set
	 */
	public void setStepOfficersGrpsLst(List<String> stepOfficersGrpsLst) {
		this.stepOfficersGrpsLst = stepOfficersGrpsLst;
	}

	/**
	 * @return the stepOfficerGrp
	 */
	public String getStepOfficerGrp() {
		return stepOfficerGrp;
	}

	/**
	 * @param stepOfficerGrp the stepOfficerGrp to set
	 */
	public void setStepOfficerGrp(String stepOfficerGrp) {
		this.stepOfficerGrp = stepOfficerGrp;
	}

	/**
	 * @return the stepStaffGrp
	 */
	public String getStepStaffGrp() {
		return stepStaffGrp;
	}

	/**
	 * @param stepStaffGrp the stepStaffGrp to set
	 */
	public void setStepStaffGrp(String stepStaffGrp) {
		this.stepStaffGrp = stepStaffGrp;
	}

	/**
	 * @return the stepStaffLHM
	 */
	public LinkedHashMap<String, List<String>> getStepStaffLHM() {
		return stepStaffLHM;
	}

	/**
	 * @param stepStaffLHM the stepStaffLHM to set
	 */
	public void setStepStaffLHM(LinkedHashMap<String, List<String>> stepStaffLHM) {
		this.stepStaffLHM = stepStaffLHM;
	}

	/**
	 * @return the stepStaffSet
	 */
	public Set<Map.Entry<String, List<String>>> getStepStaffSet() {
		return stepStaffSet;
	}

	/**
	 * @param stepStaffSet the stepStaffSet to set
	 */
	public void setStepStaffSet(Set<Map.Entry<String, List<String>>> stepStaffSet) {
		this.stepStaffSet = stepStaffSet;
	}

	/**
	 * @return the stepOfficersLHM
	 */
	public LinkedHashMap<String, List<String>> getStepOfficersLHM() {
		return stepOfficersLHM;
	}

	/**
	 * @param stepOfficersLHM the stepOfficersLHM to set
	 */
	public void setStepOfficersLHM(
			LinkedHashMap<String, List<String>> stepOfficersLHM) {
		this.stepOfficersLHM = stepOfficersLHM;
	}

	/**
	 * @return the stepOfficersSet
	 */
	public Set<Map.Entry<String, List<String>>> getStepOfficersSet() {
		return stepOfficersSet;
	}

	/**
	 * @param stepOfficersSet the stepOfficersSet to set
	 */
	public void setStepOfficersSet(
			Set<Map.Entry<String, List<String>>> stepOfficersSet) {
		this.stepOfficersSet = stepOfficersSet;
	}

	/**
	 * @return the eFormName
	 */
	public String getEFormName() {
		return eFormName;
	}

	/**
	 * @param formName the eFormName to set
	 */
	public void setEFormName(String formName) {
		eFormName = formName;
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
	 * @return the notifsLHM
	 */
	public LinkedHashMap<String, Notifications> getNotifsLHM() {
		return notifsLHM;
	}

	/**
	 * @param notifsLHM the notifsLHM to set
	 */
	public void setNotifsLHM(LinkedHashMap<String, Notifications> notifsLHM) {
		this.notifsLHM = notifsLHM;
	}

	/**
	 * @return the notifsSet
	 */
	public Set<Map.Entry<String, Notifications>> getNotifsSet() {
		return notifsSet;
	}

	/**
	 * @param notifsSet the notifsSet to set
	 */
	public void setNotifsSet(Set<Map.Entry<String, Notifications>> notifsSet) {
		this.notifsSet = notifsSet;
	}
	
}
