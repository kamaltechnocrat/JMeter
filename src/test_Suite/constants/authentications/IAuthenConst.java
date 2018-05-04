/**
 * 
 */
package test_Suite.constants.authentications;

/**
 * @author mshakshouki
 *
 */
public interface IAuthenConst {
	
	public static enum EAuthProviderTypes {
		
		Def,
		GUI,
		SSO
	}
	
	/***************************
	 * Fields Tags Ids
	 *****************************/
	
	public static final String defPwd = "a11";
	
	public static final String guiPwd = "aaa";
	
	//***** PO fileds ***************//
	
	public static final String poUName_txtField_id = "left_bar:frmLogin:userName";
	
	public static final String poPassword_txtField_id = "left_bar:frmLogin:password";
	
	public static final String poForgotPassword_Link_id = "left_bar:frmLogin:forgotPassword";
	
	public static final String poForgotPassword_Link_text = "Forgot your password?";	
	
//	public static final String poLeftMenu_Span_id = "left_bar:menu:menuForm:menu"; //unused
	
	public static final String poChangePassword_Link_id = "grantiumMainMenu:j_id55"; //"left_bar:menu:menuForm:menu:0:3:lnkNode";
	
//	public static final String poChangePassword_Link_text = "Change\u00A0Password";
	
	public static final String poLogout_Span_id = "header_right:headerForm:logout_image";
	
	public static final String poLogout_Link_text = "Logout";
	
//	public static final String poLogout_Button_id = "left_bar:login:frmLogin:loginButton";
	
	public static final String login_Button_value = "Login";
	
	public static final String poLoginForm_id = "left_bar:frmLogin";
	
	public static final String forgotPwd_txtField_id = "g3-form:userNameOrEmail";
	
	public static final String poForgotPwd_txtField_id = "WelcomeWorkspace:forgotPasswordForm:userNameOrEmail";
	
	public static final String passwordResetBtn_value = "Send Password Reset Link";
	
	//******** FO Fields ***************//
	
	public static final String foUName_txtField_id = "g3-form:login-userName";
	
	public static final String foPassword_txtField_id = "g3-form:login-password";
	
	public static final String foForgotPassword_Link_id = "g3-form:forgotPassword";
	
	public static final String foForgotPassword_Link_text = "Forgot your password?";
	
	public static final String foCreateProfile_InLoginPage_Link_id = "g3-form:lnkCreateProfile";
	
	public static final String foCreateProfile_InLoginPage_Link_text = "Create Profile";
	
	public static final String foCreateProfile_InWorkspace_Link_id = "g3-form:editProfileLink";
	
	public static final String foCreateProfile_InWorkspace_Link_text = "My Profile";
	
	public static final String foLoginForm_id = "g3-login-form";
	
	public static final String foChangePassword_Link_id = "g3-form:editPasswordLink";
	
	public static final String foChangePassword_Link_text = "Change Password";
	
//	public static final String foLogout_link_id = "g3-form:_idJsp13";
	
	

}
