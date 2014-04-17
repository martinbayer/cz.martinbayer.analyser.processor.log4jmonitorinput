package cz.martinbayer.analyser.processor.log4jmonitorinput.gui.paletteitem.processor;

import java.io.File;

import cz.martinbayer.analyser.impl.ConcreteXMLog;
import cz.martinbayer.analyser.processors.IProcessorLogic;
import cz.martinbayer.analyser.processors.types.LogProcessor;

public class Log4jMonitorInputProcLogic implements
		IProcessorLogic<ConcreteXMLog> {

	private Log4jMonitorInputProcessor processor;

	@Override
	public LogProcessor<ConcreteXMLog> getProcessor() {
		if (processor == null) {
			processor = new Log4jMonitorInputProcessor();
		}
		return processor;
	}

	public void setLogFiles(File[] logFiles) {
		((Log4jMonitorInputProcessor) getProcessor()).setLogFiles(logFiles);
	}

	public void setPattern(String pattern) {
		((Log4jMonitorInputProcessor) getProcessor()).setPattern(pattern);
	}

	public void setDateTimeFormat(String dateTimeFormat) {
		((Log4jMonitorInputProcessor) getProcessor())
				.setDateTimeFormat(dateTimeFormat);
	}
}
