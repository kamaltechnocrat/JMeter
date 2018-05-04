/**
 * Organizations class
 */
package test_Suite.lib.cases;

import java.util.ArrayList;

/**
 * @author apankov
 * Organizations class to use in test scripts where Organizations are involved
 */
public class Organizations 
{
	private String orgId;
	private String orgForm;
	private boolean inheritParentChanges;
	private boolean orgFormIncluded;
	//Inheritable / Inherited Properties
	private String orgType;
	private String orgStatus;
	private String orgDefLoc;
	private String orgOfficer;
	//Names
	private String [] orgFullNames;
	private String [] orgShortNames;
	//Child Organizations
	private ArrayList<Organizations> childOrganizations;
	private String    parentOrg;
	private int       updateOrCreate;
	/**
	 * Default constructor method
	 */
	public Organizations() {
		
	}
	
	public boolean isInheritParentChanges() {
		return inheritParentChanges;
	}
	public void setInheritParentChanges(boolean inheritParentChanges) {
		this.inheritParentChanges = inheritParentChanges;
	}
	
	public String getOrgDefLoc() {
		return orgDefLoc;
	}
	public void setOrgDefLoc(String orgDefLoc) {
		this.orgDefLoc = orgDefLoc;
	}
	
	public String getOrgForm() {
		return orgForm;
	}
	public void setOrgForm(String orgForm) {
		this.orgForm = orgForm;
	}
	
	public String[] getOrgFullNames() {
		return orgFullNames;
	}
	public void setOrgFullNames(String[] orgFullNames) {
		this.orgFullNames = orgFullNames;
	}
	
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	
	public String getOrgOfficer() {
		return orgOfficer;
	}
	public void setOrgOfficer(String orgOfficer) {
		this.orgOfficer = orgOfficer;
	}
	
	public String[] getOrgShortNames() {
		return orgShortNames;
	}
	public void setOrgShortName(String[] orgShortNames) {
		this.orgShortNames = orgShortNames;
	}
	
	public String getOrgStatus() {
		return orgStatus;
	}
	public void setOrgStatus(String orgStatus) {
		this.orgStatus = orgStatus;
	}
	
	public String getOrgType() {
		return orgType;
	}
	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	public ArrayList<Organizations> getChildOrganizations() {
		return childOrganizations;
	}

	public void setChildOrganizations(ArrayList<Organizations> childOrganizations) {
		this.childOrganizations = childOrganizations;
	}

	public String getParentOrg() {
		return parentOrg;
	}

	public void setParentOrg(String parentOrg) {
		this.parentOrg = parentOrg;
	}

	public int getUpdateOrCreate() {
		return updateOrCreate;
	}

	public void setUpdateOrCreate(int updateOrCreate) {
		this.updateOrCreate = updateOrCreate;
	}

	/**
	 * @return the orgFormIncluded
	 */
	public boolean isOrgFormIncluded() {
		return orgFormIncluded;
	}

	/**
	 * @param orgFormIncluded the orgFormIncluded to set
	 */
	public void setOrgFormIncluded(boolean orgFormIncluded) {
		this.orgFormIncluded = orgFormIncluded;
	}

	/**
	 * @param orgShortNames the orgShortNames to set
	 */
	public void setOrgShortNames(String[] orgShortNames) {
		this.orgShortNames = orgShortNames;
	}
	
	/*
	public void selectOrgForm()
	{
		try
		{
			if(this.orgForm != null)
			{
				
				new Thread(new Runnable() { 
					public void run() { 
						try { 
								ie = IEUtil.getActiveIE();
								ie.selectList(id, ORG_FORM).select(orgForm);

    						} 
						catch (Exception e) { 
						} 
					} 
				}).start();

				GeneralUtil.takeANap(2.0);
				ie.sendKeys("Microsoft Internet Explorer", " ", false); 
				ClicksUtil.clickButtons(IClicksConst.saveBtn);

			}

		}
		catch(Exception ex)
		{
			log.error("Unexpected Exception in selectOrgForm() " + ex.getMessage());
		}
	}
*/

}
