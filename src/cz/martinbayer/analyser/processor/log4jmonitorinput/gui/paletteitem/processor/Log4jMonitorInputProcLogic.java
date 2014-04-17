package cz.martinbayer.analyser.processor.log4jmonitorinput.gui.paletteitem.processor;

import java.io.File;

import cz.martinbayer.analyser.impl.ConcreteE4LogsisLog;
import cz.martinbayer.analyser.processors.IProcessorLogic;
import cz.martinbayer.analyser.processors.types.LogProcessor;

public class Log4jMonitorInputProcLogic implements
		IProcessorLogic<ConcreteE4LogsisLog> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7105281747338915979L;
	private Log4jMonitorInputProcessor processor;

	@Override
	public LogProcessor<ConcreteE4LogsisLog> getProcessor() {
		if (processor == null) {
			processor = new Log4jMonitorInputProcessor();
		}
		return processor;
	}

	public void setLogFiles(File[] logFiles) {
		((Log4jMonitorInputProcessor) getProcessor()).setLogFiles(logFiles);
	}
}
