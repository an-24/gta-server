package biz.gelicon.gta.server.reports;

import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportEngineFactory;

public class BirtEngine {
	private static IReportEngine birtEngine = null;
	
	public synchronized static IReportEngine getInstance() throws BirtException {
		if(birtEngine == null) {
			EngineConfig config = new EngineConfig();
			config.setEngineHome("");
			Platform.startup(config);
			IReportEngineFactory factory = (IReportEngineFactory) Platform
					.createFactoryObject(IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY);
		    birtEngine = factory.createReportEngine(config);
		}
		return birtEngine;
	}
	
}
