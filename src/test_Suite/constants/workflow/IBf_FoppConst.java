/**
 * 
 */
package test_Suite.constants.workflow;

import test_Suite.constants.preTest.IPreTestConst;

/**
 * @author mshakshouki
 *
 */
public interface IBf_FoppConst {
	
	public static enum EPostFix {SRC, TRGT, AWARDED};
	
	public static final String preFix = "-BF-FOPP-";
	public static final String[] postFix = {"-Source","-Target","-Awarded-SRC-Target"};
	
	public static final String[] stepOfficerGrp = {"Staff"};
	public static final String stepProjOfficer = IPreTestConst.ProgPOfficer;
	
	public static final String[] stepStaffGrp = {"Staff"};
	public static final String[] stepStaffUsrs = {IPreTestConst.ProgPOfficer};
	
	public static final boolean NEW_PROJ = Boolean.TRUE;
	public static final boolean EX_PROJ = Boolean.FALSE;
	
	public static final boolean NEW_ORG = Boolean.TRUE;
	public static final boolean EX_ORG = Boolean.FALSE;
	
	
	public static final String[] projNames = {
		"PO-",
		"FO-",
		"PO-Trnsfr-",
		"FO-Trnsfr-",
		"FO-Wizard-",
		"PO-Amend-",
		"FO-Amend-",
		"PO-Same-Step-",
		"FO-Same-Step-",
		"PO-Diff-Step-",
		"FO-Diff-Step-",
		"PO-Not-Awarded-",
		"FO-Not-Awarded-",
		"PO-Awarded-",
		"FO-Awarded-"};
	
	public static enum EProjNames {
		POPROJ,
		FOPROJ,
		POTRNSFR,
		FOTRNSFR,
		FOWZRD,
		POAMEND,
		FOAMEND,
		POSAME,
		FOSAME,
		PODIFF,
		FODIFF,
		POAWARDLESS,
		FOAWARDLESS,
		POAWARDED,
		FOAWARDED
	}
	
	
	
	
	/******** Steps Used *******************/

	public static final String bf_FOPP_Submission[][] = {
		{"Submission", "Applicant Submission", "true", "No",stepOfficerGrp[0],stepStaffGrp[0]}
		, {"BF-FOPP-Submission-eForm"}
		, {stepProjOfficer}
		, stepStaffUsrs
		};
	
	public static final String bf_FOPP_SubmissionB[][] = {
		{"Submission-B", "Applicant Submission", "true", "Optional (Yes)",stepOfficerGrp[0],stepStaffGrp[0]}
		, {"BF-FOPP-Submission-eForm"}
		, {stepProjOfficer}
		, stepStaffUsrs
		};
	
	public static final String bf_FOPP_Review[][] = {
		{"Review", "Review", "true", "Optional (Yes)",stepOfficerGrp[0],stepStaffGrp[0]}
		, {"BF-FOPP-Review-eForm", "Quorum", "1", "true","false"}
		, {stepProjOfficer}
		, stepStaffUsrs
		};
	
	public static final String bf_FOPP_Approval[][] = {
		{"Approval", "Approval", "true", "Optional (Yes)",stepOfficerGrp[0],stepStaffGrp[0]}
		, {"BF-FOPP-ProjectApproval-eForm", "Quorum", "1", "false","false"}
		, {stepProjOfficer}
		, stepStaffUsrs
		};
	
	public static final String bf_FOPP_PO_Submission[][] = {
		{"PO-Submission", "Program Office Submission", "true", "No",stepOfficerGrp[0],stepStaffGrp[0]}
		, {"BF-FOPP-PO-Submission-eForm"}
		, {stepProjOfficer}
		, stepStaffUsrs
		};
	
	public static final String bf_FOPP_AwardCrit[][] = {
		{"Award", "Award", "true", "Optional (Yes)",stepOfficerGrp[0],stepStaffGrp[0]}
		, {"BF-FOPP-Award-eForm"}
		, {stepProjOfficer}
		, stepStaffUsrs
		};
	
	public static final String bf_FOPP_initialClaim[][] = {
		{"Post-Award-Submission", "Initial Post Award Submission", "true", "Optional (Yes)",stepOfficerGrp[0],stepStaffGrp[0]}
		, {"BF-FOPP-Post-Award-Submission"}
		, {stepProjOfficer}
		, stepStaffUsrs
		};
	
	public static final String bf_FOPP_postAwardCrit[][] = {
		{"Post-Award", "Post-Award", "true", "Optional (Yes)",stepOfficerGrp[0],stepStaffGrp[0]}
		, {bf_FOPP_AwardCrit[0][0], bf_FOPP_initialClaim[1][0]}
		, {stepProjOfficer}
		, stepStaffUsrs
		};	
	

}
