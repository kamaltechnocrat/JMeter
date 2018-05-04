/**
 * 
 */
package test_Suite.constants.clients;

/**
 * @author mshakshouki
 *
 */
public interface ISovConst {
	
	
	//Use the ENUM as an index to access the array	
	public static final String[] sovFoppsNames = {"Tech Ed Equip FY13","NorD FY14 AOE FUNDING_OPP","CFP_FY13_DOE FUNDING_OPP","CFP_FY14_AOE FUNDING_OPP","Tech Ed Equip FY14"};
	
	public static final String[] sovFoppsIdents = {"Tech Ed Equip FY13","NorD FY14 AOE FUNDING_OPP","CFP_FY13_DOE FUNDING_OPP","CFP_FY14_AOE FUNDING_OPP","Tech Ed Equip FY14"};
	
	public enum ESovFOPPs {
		TechEdFY13,
		NORDFY14,
		CFPFY13DOE,
		CFPFY14AOE,
		TechEdFY14
	}
	
	
	public static final String[] sovTechEdStepsNames = {"Grant Application","Initial Review","Program Team Review","Grant Agreement Created","Finance Grant Agreement Review","Director Grant Agreement Review","CFO Approval e.Sign","Grant Award e.Sign","CFO Grant Agreement e.Sign","Grant Agreement Approved","Approved Award","Finance Create Submission Schedule","Quarterly Financial Report","Program Team Review of Payment Request","Finance Report Review","TIPGAP Payment"};
	
	public static final String[] sovNordFy14StepsNames = {"Grant Application","Grant Application e.Sign","To Offset the index","Program Team Review","Program Officer Approval","Grant Agreement Created","Finance Grant Agreement Review","Director Grant Agreement Review","CFO Approval e.Sign","Grant Award e.Sign","CFO Grant Agreement e.Sign","Grant Agreement Approved","Approved Award","Finance Create Submission Schedule","Quarterly Financial Report","Finance Report Review","TIPGAP Payment"};
	
	public static final String[] sovCfpFy13StepsNames = {"CFP Application","Superintendent Application e.Sign","Initial Review","CFP Team Review","Program Officer Approval","Grant Agreement Created","Finance Grant Agreement Review","Director Grant Agreement Review","CFO Approval e.Sign","Superintendent Grant Agreement e.Sign","CFO Grant Agreement e.Sign","Grant Agreement Approved","Approved Award","Finance Create Submission Schedule","Quarterly Financial Report","Finance Report Review","TIPGAP Payment"};
	
	public static final String[] sovCfpFy14StepsNames = {"CFP Application","Grant Application e.Sign","Initial Review","CFP Team Review","Program Officer Approval","Grant Agreement Created","Finance Grant Agreement Review","Director Grant Agreement Review","CFO Approval e.Sign","Grant Award e.Sign","CFO Grant Agreement e.Sign","Grant Agreement Approved","Approved Award","Finance Create Submission Schedule","Quarterly Financial Report","Finance Report Review","TIPGAP Payment"};
	
	public static final Integer[] sovEvaluationSteps = {2,4,6,7,8,10};
	
	public static final Integer[] sovEvaluationStepsPA = {15,16};
	
	public static final Integer[] sovNordEvaluationSteps = {4,6,7,8,10};
	
	public static final Integer[] sovNordEvaluationStepsPA = {15,16};
	
	public static final Integer[] sovTechEdEvaluationSteps = {1,2,4,5,6,8};
	
	public static final Integer[] sovTechEdEvaluationStepsPA = {13,14,15};
	
	
	
	//Use the ENUM as an index to access the array
	public static final String[] sovTechEdProjectsNames = {"TEE Wind FY13","TEE Stjay FY13","TEE Cana FY13","TEE CoHol FY13","TEE Barr FY13","TEE Lynd FY14", "TEE Burl FY13","TEE Essex FY13","TEE Hart FY13","TEE Miss FY13","TEE Nowest FY13","TEE Staf FY13", "TEE PAHA FY13", "TEE RBend FY13","TEE RVall FY13","TEE SoWes FY13","TTT NoCo FY13"};
	
	public static final String[] sovTechEdFoUsersNames = {"RonaldStahley","KathrynDaley","ChristopherMasson","jaynichols","JohnBacon","ChristopherMasson","MarkAliquo","judithDenova","TomDeBalsi","RebeccaHart","MarthaGagner","JosephCiccolo","MarkBouvier", "BethCobb", "ScottFarr","JamesCulkeen","dianebinette"};
	
	public static final String[] sovTechEdInProcessStepsNames = {"Director Grant Agreement Review","CFO Approval e.Sign","Program Team Review of Payment Request","Program Team Review","Grant Agreement Approved","Grant Application","Grant Agreement Created", "Grant Award e.Sign","Grant Agreement Approved","Approved Award", "Finance Create Submission Schedule", "Quarterly Financial Reports", "Program Team Review of Payment Request", "Finance Report Review", "TIPGAP Payment","Grant Application","Program Team Review"};
	
	public enum ESovTechEdApplicantsNum {
		U006,
		P004,
		T041,
		T068,
		U041,
		P003,
		T037,
		U046,
	    T093,
	    U007,
	    U048,
	    T173,
	    V001,
	    U030,
	    V016,
	    V009,
	    U022
	   
	}
	
	
	
	
	//Use the ENUM as an index to access the array
	public static final String[] sovNordFy14ProjectsNames = {"dms 060413","NorD Fy14 SU60"};
	
	public static final String[] sovNordFy14FoUsersNames = {"JohnBacon","KarenGallese"};
	
	public static final String[] sovNordFy14InProcessStepsNames = {"Finance Create Submission Schedule","CFO Grant Agreement e.Sign"};
	
	public enum ESovNordFy14ApplicantsNum {
		SU061,
		SU060		
	}
	
	
	public static final String[] sovCfpFy13ProjectsNames = {"CFP Addison Northeast FY13","CFP Addison Rutland FY13","CFP Southwest Vermont FY13","CFP Bennington Rutland FY13","CFP Chittenden South FY13","CFP Franklin Northeast FY13","CFP Franklin Northwest FY13","CFP Franklin West FY13","CFP Franklin Central FY13","CFP Grand Isle FY13","CFP Lamoille South FY13","CFP Orange Windsor FY13","CFP North Country FY13","CFP Rutland South FY13","CFP Orleans Central FY13","CFP Rutland Central FY13","CFP Rutland Southwest FY13","CFP Washington South FY13","CFP Windsor Southeast FY13","CFP Battenkill Valley FY13","CFP Barre FY13","CFP Hartford FY13","CFP Milton FY13","CFP Rutland City FY13","CFP Winooski FY13"};
	
	public static final String[] sovCfpFy13FoUsersNames = {"DavidAdams","RonaldRyan","CathyMcClure","DanielFrench","ElainePinckney","jaynichols","JohnMcCarthy","NedKirsch","JulieRegimbal","RobertPhillips","TracyWrend","DavidBickford","RobertKern","DanaColeLevesque","StephenUrgenson","DebraTaylor","JoanPaustian","LaurieGossens","DaveBaker","KarenGallese","JohnBacon","TomDeBalsi","JohnBarone","MaryMoran","MaryMartineau"};
	
	public static final String[] sovCfpFy13InProcessNames = {"Grant Agreement Created","Superintendent Grant Agreement e.Sign","CFP Application","Finance Create Submission Schedule","Initial Review","CFP Application","Superintendent Application e.Sign","CFP Application","CFP Application","Finance Create Submission Schedule","Superintendent Application e.Sign","Superintendent Grant Agreement e.Sign ","Initial Review","CFP Application","Superintendent Grant Agreement e.Sign","Finance Create Submission Schedule","CFP Application","Finance Grant Agreement Review","Superintendent Grant Agreement e.Sign","CFP Application","Program Officer Approval","Quarterly Financial Report","CFP Application","CFP Application","Initial Review"};
	
	public enum ESovCfpFy13ApplicantsNum {
		SU001,
		SU004,
		SU005,
		SU006,
		SU014,
		SU020,
		SU021,
		SU022,
		SU023,
		SU024,
		SU026,
		SU030,
		SU031,
		SU033,
		SU034,
		SU037,
		SU038,
		SU043,
		SU052,
		SU060,
		SU061,
		T093,
		T126,
		T173,
		T249
		
	}
	
	
	public static final String[] sovCfpFy14ProjectsNames = {"Barre CFP FY14","CFP Rutland City FY14","dms 080813 fy14","ACSU FY14","WIP dms 080813 fy14"};
	
	public static final String[] sovCfpFy14FoUsersNames = {"JohnBacon","MaryMoran","GailConley","GailConley","GailConley"};
	
	public static final String[] sovCfpFy14InProcessStepsNames = {"TIPGAP Payment","Approved Award","Initial Review ","Program Officer Approval","Finance Report Review"};
	
	public enum ESovCfpFy14ApplicantsNum {
		SU061,
		T173,
		SU003,
		SU0033,
		SU00333
	}
	
	
	public static final String SuperAppeSignPwdId = "g3-form:eFormFieldList:0:password";
	
	
}
