package cz.martinbayer.analyser.processor.log4jmonitorinput.gui.paletteitem.processor;

import java.io.File;

import cz.martinbayer.analyser.impl.ConcreteE4LogsisLog;
import cz.martinbayer.analyser.processors.types.InputProcessor;
import cz.martinbayer.logparser.log4j2.monitors.handler.Log4JMonitorHandler;

public class Log4jMonitorInputProcessor extends InputProcessor<ConcreteE4LogsisLog> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -358055025062680886L;
	private static final String MONITORS_DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm:ss.SSS";
	private File[] logFiles;
	private transient Log4jMonitorParserListener parserListener;

	public Log4jMonitorInputProcessor() {
		super();
		init();
	}

	@Override
	protected void process() {
		// no need to do any operation against parsed data
	}

	@Override
	protected void read() {
		Log4JMonitorHandler handler = Log4JMonitorHandler.getInstance(logFiles,
				"UTF-8");
		handler.doParse(parserListener);
		System.out.println("size:"
				+ parserListener.getData().getLogRecords().size());
	}

	/**
	 * Set the log files directory location
	 * 
	 * @param directory
	 *            - log files location
	 */

	public final void setLogFiles(File[] logFiles) {
		this.logFiles = logFiles;
	}

	public final File[] getLogFiles() {
		return logFiles;
	}

	@Override
	public void init() {
		super.init();
		parserListener = new Log4jMonitorParserListener(this, logData);
	}

	/**
	 * validation of the processor properties
	 */
	@Override
	protected StringBuffer isSubProcessorValid() {
		StringBuffer sb = new StringBuffer();
		if (logFiles == null || logFiles.length == 0) {
			sb.append("No log file selected. ");
		}
		if (sb.length() > 0) {
			sb.insert(0, ": ").insert(0, getName());
		}
		return sb;
	}

	public String getDateTimeFormat() {
		return MONITORS_DATE_TIME_FORMAT;
	}
}
