package biz.gelicon.gta.server.dto;

import java.util.TimeZone;

import biz.gelicon.gta.server.GtaSystem;

public class TimeZoneDTO {
	private TimeZone tz;
	
	public TimeZoneDTO(TimeZone tz) {
		this.tz = tz;
	}
	
	public String getId() {
		return tz.getID();
	}
	public String getName() {
		return tz.getID()+" ("+tz.getDisplayName(false,TimeZone.LONG,GtaSystem.getLocale())+")";
	}

}
