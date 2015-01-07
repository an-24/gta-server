package biz.gelicon.gta.server.dto;

import java.util.Locale;

import biz.gelicon.gta.server.GtaSystem;

public class LocaleDTO {
	private Locale locale;

	public LocaleDTO() {
	}
	
	public LocaleDTO(Locale locale) {
		this.locale = locale;
	}
	public String getId() {
		return locale.toLanguageTag();
	}
	public String getName() {
		return locale.getDisplayName(GtaSystem.getLocale());
	}

}
