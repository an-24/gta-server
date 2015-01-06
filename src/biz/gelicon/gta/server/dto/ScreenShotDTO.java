package biz.gelicon.gta.server.dto;

import biz.gelicon.gta.server.data.ScreenShot;

public class ScreenShotDTO {
	private String url;
	private String fullImageUrl;

	public ScreenShotDTO() {
	}

	public ScreenShotDTO(ScreenShot screenshot) {
		this.url="inner/diary/thumbnail?id="+screenshot.getId();
		this.fullImageUrl="inner/diary/screenshot.png?id="+screenshot.getId();
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getFullImageUrl() {
		return fullImageUrl;
	}

	public void setFullImageUrl(String fullImageUrl) {
		this.fullImageUrl = fullImageUrl;
	}
}
