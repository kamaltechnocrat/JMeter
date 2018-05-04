/**
 * 
 */
package test_Suite.lib.clients;

import test_Suite.lib.workflow.FundingOpportunity;

/**
 * @author mshakshouki
 *
 */
public class Sov extends FundingOpportunity {
	
	private String foUserId;	
	private String applicantNumber;
	private String projectName;
	private String subProjectName;
	private String fundingOppName;
	private String fundingOppIdent;
	private String currStepName;
	private String inProcessStepName;
	private String projAndSubName;
	private String foProjName;
	private String tmpCurrStepName;
	private String foProjAndSubProjName;
	public Sov() {super();};
	
	
	public Sov(String foUserId,String appNum, String projName, String subProjName, String foppName, String foppIdent, String CurrStepName, String inProcStepName) {
		
		super();
		this.setFoUserId(foUserId);
		this.setApplicantNumber(appNum);
		this.setProjectName(projName);
		this.setSubProjectName(subProjName);
		this.setFundingOppName(foppName);
		this.setFundingOppIdent(foppIdent);
		this.setCurrStepName(CurrStepName);
		this.setFullStepName(inProcStepName);
		this.setInProcessStepName(inProcStepName);
		this.setProjAndSubName(projName);
		this.setTmpCurrStepName(CurrStepName);
		
	}

	/**
	 * @return the applicantNumber
	 */
	public String getApplicantNumber() {
		return applicantNumber;
	}

	/**
	 * @param applicantNumber the applicantNumber to set
	 */
	public void setApplicantNumber(String applicantNumber) {
		this.applicantNumber = applicantNumber;
	}

	/**
	 * @return the projectName
	 */
	public String getProjectName() {
		return projectName;
	}

	/**
	 * @param projectName the projectName to set
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	/**
	 * @return the subProjectName
	 */
	public String getSubProjectName() {
		return subProjectName;
	}

	/**
	 * @param subProjectName the subProjectName to set
	 */
	public void setSubProjectName(String subProjectName) {
		this.subProjectName = subProjectName;
	}

	/**
	 * @return the fundingOppName
	 */
	public String getFundingOppName() {
		return fundingOppName;
	}

	/**
	 * @param fundingOppName the fundingOppName to set
	 */
	public void setFundingOppName(String fundingOppName) {
		this.fundingOppName = fundingOppName;
	}

	/**
	 * @return the fundingOppIdent
	 */
	public String getFundingOppIdent() {
		return fundingOppIdent;
	}

	/**
	 * @param fundingOppIdent the fundingOppIdent to set
	 */
	public void setFundingOppIdent(String fundingOppIdent) {
		this.fundingOppIdent = fundingOppIdent;
	}

	/**
	 * @return the currentStepName
	 */
	public String getCurrStepName() {
		return currStepName;
	}

	/**
	 * @param currentStepName the currentStepName to set
	 */
	public void setCurrStepName(String currStepName) {
		this.currStepName = currStepName;
	}


	/**
	 * @return the inProcessStepName
	 */
	public String getInProcessStepName() {
		return inProcessStepName;
	}


	/**
	 * @param inProcessStepName the inProcessStepName to set
	 */
	public void setInProcessStepName(String inProcessStepName) {
		this.inProcessStepName = inProcessStepName;
	}


	/**
	 * @return the foUserId
	 */
	public String getFoUserId() {
		return foUserId;
	}


	/**
	 * @param foUserId the foUserId to set
	 */
	public void setFoUserId(String foUserId) {
		this.foUserId = foUserId;
	}


	/**
	 * @return the projAndSubName
	 */
	public String getProjAndSubName() {
		return projAndSubName;
	}


	/**
	 * @param projAndSubName the projAndSubName to set
	 */
	public void setProjAndSubName(String projAndSubName) {
		this.projAndSubName = projAndSubName;
	}


	/**
	 * @return the foProjName
	 */
	public String getFoProjName() {
		return foProjName;
	}


	/**
	 * @param foProjName the foProjName to set
	 */
	public void setFoProjName(String foProjName) {
		this.foProjName = foProjName;
	}


	/**
	 * @return the tmpCurrStepName
	 */
	public String getTmpCurrStepName() {
		return tmpCurrStepName;
	}


	/**
	 * @param tmpCurrStepName the tmpCurrStepName to set
	 */
	public void setTmpCurrStepName(String tmpCurrStepName) {
		this.tmpCurrStepName = tmpCurrStepName;
	}


	/**
	 * @return the foProjAndSubProjName
	 */
	public String getFoProjAndSubProjName() {
		return foProjAndSubProjName;
	}


	/**
	 * @param foProjAndSubProjName the foProjAndSubProjName to set
	 */
	public void setFoProjAndSubProjName(String foProjAndSubProjName) {
		this.foProjAndSubProjName = foProjAndSubProjName;
	}
	

}
