package biz.gelicon.gta.server.wrappers;

import javax.xml.bind.annotation.XmlRootElement;

import biz.gelicon.gta.server.data.Message;
import biz.gelicon.gta.server.utils.Pair;

@XmlRootElement
public class MessageWrapper extends Pair<String, Message> {
	private static final long serialVersionUID = -1108849744184069230L;

	public MessageWrapper() {
		super();
	}

	public MessageWrapper(String left, Message right) {
		super(left, right);
	}

}
