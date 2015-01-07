package biz.gelicon.gta.server.i18n;

import java.util.ListResourceBundle;

public class Bundle_ru extends ListResourceBundle {

	static final Object[][] contents = {
		  {"label.home", "Главная"},
		  {"label.projects", "Проекты"},
		  {"label.workdiary", "Рабочий дневник"},
		  {"label.administrator","Администрирование"},
		  {"label.companyinfo","Информация о компании"},
		  {"label.about","О нас"},
		  {"label.contacts","Контакты и поддержка"},
		  {"label.login","Вход"},
		  {"label.logout","Выход"},
		  {"label.profile","Профиль пользователя"},
		  {"label.username","Имя пользователя"},
		  {"label.lang","Язык"},
		  {"label.tz","Часовой пояс"},
		  {"label.changepassword","Сменить пароль"},
		  {"label.newpassword","Новый пароль"},
		  {"label.youpassword","Пароль"},
		  {"label.confirmpassword","Подтверждение пароля"},
		  {"message.username","Имя не может быть пустым"},
		  {"message.email","Формат e-mail неверный"},
		  {"message.incorrectpassword","Минимальная длина пароли составляет 8 символов"},
		  {"message.noconfirmpassword","Подтверждение пароля неверно"}
	};
	
	@Override
	protected Object[][] getContents() {
		return contents;
	}

}
