package biz.gelicon.gta.server.wrappers;

import javax.xml.bind.annotation.XmlRootElement;

import biz.gelicon.gta.server.utils.Pair;

@XmlRootElement
public class TeamWrapper extends Pair<String, Integer> {
	private static final long serialVersionUID = 6326740661222841665L;

	public TeamWrapper() {
		super();
	}

	public TeamWrapper(String left, Integer right) {
		super(left, right);
	}

}
