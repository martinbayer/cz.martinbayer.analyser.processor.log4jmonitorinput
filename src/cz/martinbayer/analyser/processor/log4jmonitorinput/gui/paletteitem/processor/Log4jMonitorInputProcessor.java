package cz.martinbayer.analyser.processor.log4jmonitorinput.gui.paletteitem.processor;

import java.io.File;

import cz.martinbayer.analyser.impl.ConcreteXMLog;
import cz.martinbayer.analyser.processors.types.InputProcessor;
import cz.martinbayer.logparser.log4j2.monitors.handler.Log4JMonitorHandler;
import cz.martinbayer.utils.StringUtils;

public class Log4jMonitorInputProcessor extends InputProcessor<ConcreteXMLog> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -358055025062680886L;
	private File[] logFiles;
	private String pattern;
	private String dateTimeFormat;
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

	public final void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public final void setDateTimeFormat(String dateTimeFormat) {
		this.dateTimeFormat = dateTimeFormat;
	}

	public final File[] getLogFiles() {
		return logFiles;
	}

	public final String getPattern() {
		return pattern;
	}

	public final String getDateTimeFormat() {
		return dateTimeFormat;
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
		if (StringUtils.isEmtpy(pattern)) {
			sb.append("No pattern specified for processor. ");
		}
		if (StringUtils.isEmtpy(dateTimeFormat)) {
			sb.append("No date time format specified for processor. ");
		}
		if (logFiles == null || logFiles.length == 0) {
			sb.append("No log file selected. ");
		}
		if (sb.length() > 0) {
			sb.insert(0, ": ").insert(0, getName());
		}
		return sb;
	}
}
