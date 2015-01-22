package biz.gelicon.gta.server.wrappers;

import javax.xml.bind.annotation.XmlRootElement;

import biz.gelicon.gta.server.utils.Pair;

@XmlRootElement
public class UserWrapper extends Pair<String, Pair<Integer,Integer>> {
	private static final long serialVersionUID = 1083198004006035008L;

	public UserWrapper() {
		super();
	}

	public UserWrapper(String left, Integer teamId, Integer userId) {
		super(left, new Pair<Integer,Integer>(teamId,userId));
	}

}
