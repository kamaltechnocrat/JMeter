/**
 * 
 */
package test_Suite.lib.users;

import java.util.ArrayList;

import test_Suite.lib.workflow.POProjects;
/**
 * @author apankov
 *
 */
public class Applicants {

	private String    applicantName;
	private String    applicantNumber;
	private String    applicantType;
	private String    applicantOrg;
	private ArrayList<POProjects> arrProjects;
	private ArrayList<Object> arrRegistrants;
	
	/**
	 * Default Applicants class method
	 */
	public Applicants() {

	}
	
	public String getApplicantName() {
		return applicantName;
	}

	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}

	public String getApplicantNumber() {
		return applicantNumber;
	}

	public void setApplicantNumber(String applicantNumber) {
		this.applicantNumber = applicantNumber;
	}

	public String getApplicantOrg() {
		return applicantOrg;
	}

	public void setApplicantOrg(String applicantOrg) {
		this.applicantOrg = applicantOrg;
	}

	public String getApplicantType() {
		return applicantType;
	}

	public void setApplicantType(String applicantType) {
		this.applicantType = applicantType;
	}

	public ArrayList<POProjects> getArrProjects() {
		return arrProjects;
	}

	public void setArrProjects(ArrayList<POProjects> arrProjects) {
		this.arrProjects = arrProjects;
	}

	public ArrayList<Object> getArrRegistrants() {
		return arrRegistrants;
	}

	public void setArrRegistrants(ArrayList<Object> arrRegistrants) {
		this.arrRegistrants = arrRegistrants;
	}

}
