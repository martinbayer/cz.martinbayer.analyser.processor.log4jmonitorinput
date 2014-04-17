package cz.martinbayer.analyser.processor.log4jmonitorinput.gui.paletteitem.processor;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import cz.martinbayer.analyser.impl.ConcreteE4LogsisLog;
import cz.martinbayer.analyser.processors.model.ELogLevel;
import cz.martinbayer.analyser.processors.model.E4LogsisLogData;
import cz.martinbayer.logparser.log4j2.pattern.monitors.types.DateTimePattern;
import cz.martinbayer.logparser.log4j2.pattern.monitors.types.LevelPattern;
import cz.martinbayer.logparser.log4j2.pattern.monitors.types.MessageExceptionPattern;
import cz.martinbayer.logparser.log4j2.pattern.monitors.types.ThreadPattern;
import cz.martinbayer.logparser.logic.ILogParserEvent;
import cz.martinbayer.logparser.logic.ILogParserListener;
import cz.martinbayer.logparser.logic.LogParserPhase;

public class Log4jMonitorParserListener implements ILogParserListener {
	private ConcreteE4LogsisLog object;
	private E4LogsisLogData<ConcreteE4LogsisLog> logData;
	private Log4jMonitorInputProcessor inputProcessor;

	public Log4jMonitorParserListener(
			Log4jMonitorInputProcessor inputProcessor,
			E4LogsisLogData<ConcreteE4LogsisLog> logData) {
		this.inputProcessor = inputProcessor;
		this.logData = logData;
	}

	@Override
	public void parsed(ILogParserEvent event) {
		if (event.getPhase() == LogParserPhase.START) {
			object = new ConcreteE4LogsisLog();
		} else if (event.getPhase() == LogParserPhase.FINISH) {
			logData.addLogRecord(object);
			object = null;
		} else {
			if (object == null || event.getGroupName() == null) {
				throw new NullPointerException("Log event haven't started yet");
			}

			switch (event.getGroupName()) {
			case ThreadPattern.GROUP_NAME:
				object.setThreadName(event.getGroupValue());
				break;
			case LevelPattern.GROUP_NAME:
				object.setLogLevel(ELogLevel.valueOf(event.getGroupValue()));
				break;
			case DateTimePattern.GROUP_NAME:
				if (inputProcessor.getDateTimeFormat() != null) {
					try {
						object.setEventDateTime(new SimpleDateFormat(
								inputProcessor.getDateTimeFormat()).parse(event
								.getGroupValue()));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				break;
			case MessageExceptionPattern.GROUP_NAME_EXCEPTION:
				object.setErrorMessage(event.getGroupValue());
				break;
			case MessageExceptionPattern.GROUP_NAME_MESSAGE:
				object.setMessage(event.getGroupValue());
				break;
			default:
				break;
			}
		}
	}

	public E4LogsisLogData<ConcreteE4LogsisLog> getData() {
		return logData;
	}
}
