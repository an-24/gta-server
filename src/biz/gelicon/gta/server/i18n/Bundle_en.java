package biz.gelicon.gta.server.i18n;

import java.util.ListResourceBundle;

public class Bundle_en extends ListResourceBundle {

	static final Object[][] contents = {
		  {"label.home", "Home"},
		  {"label.projects", "Projects"},
		  {"label.workdiary", "Work diary"},
		  {"label.administrator","Administrator"},
		  {"label.companyinfo","Company Info"},
		  {"label.about","About us"},
		  {"label.contacts","Contact & support"},
		  {"label.login","Log in"},
		  {"label.logout","Log out"},
		  {"label.profile","Profile"},
		  {"label.username","User name"},
		  {"label.lang","Language"},
		  {"label.tz","Time zone"},
		  {"label.changepassword","Change password"},
		  {"label.newpassword","New password"},
		  {"label.youpassword","You password"},
		  {"label.confirmpassword","Confirm password"},
		  {"message.username","You must enter the user name"},
		  {"message.email","Error in entering of e-mail"},
		  {"message.incorrectpassword","The password is shorter than 8 characters"},
		  {"message.noconfirmpassword","Password could not be confirmed"}
	};
	
	@Override
	protected Object[][] getContents() {
		return contents;
	}

}
