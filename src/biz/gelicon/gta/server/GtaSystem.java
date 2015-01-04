package biz.gelicon.gta.server;

import java.util.Locale;

public class GtaSystem {
	public final static Integer MODE_ADD = 1;
	public final static Integer MODE_EDIT = 2;
	private static Locale locale = Locale.ENGLISH;

	public static Locale getLocale() {
		return locale;
	}

	public static void setLocaleName(String locale) {
		GtaSystem.locale = Locale.forLanguageTag(locale);
	}

}
