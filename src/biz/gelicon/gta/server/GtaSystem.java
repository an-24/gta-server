package biz.gelicon.gta.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class GtaSystem {
	public final static Integer MODE_ADD = 1;
	public final static Integer MODE_EDIT = 2;
	private static Locale locale = Locale.ENGLISH;

	public static Locale getLocale() {
		return locale;
	}

	public static void setLocaleName(String locale) {
		if(locale==null) GtaSystem.locale = Locale.ENGLISH;else
					GtaSystem.locale = Locale.forLanguageTag(locale);
	}

	public static List<Locale> getAviableLocales() {
		List<Locale> locales = new ArrayList<>();
    	locales.add(Locale.ENGLISH);
    	locales.add(Locale.forLanguageTag("ru"));
		return locales;
	}

}
