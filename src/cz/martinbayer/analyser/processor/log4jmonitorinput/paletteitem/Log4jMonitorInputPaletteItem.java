package cz.martinbayer.analyser.processor.log4jmonitorinput.paletteitem;

import org.eclipse.swt.widgets.Display;

import cz.martinbayer.analyser.processor.log4jmonitorinput.gui.Log4jMonitorConfigDialog;
import cz.martinbayer.analyser.processor.log4jmonitorinput.gui.Log4jMonitorConfigDialogModel;
import cz.martinbayer.analyser.processor.log4jmonitorinput.gui.paletteitem.processor.Log4jMonitorInputProcLogic;
import cz.martinbayer.analyser.processors.BasicProcessorPaletteItem;

public class Log4jMonitorInputPaletteItem extends BasicProcessorPaletteItem {

	private static final String LABEL = "Log4J Monitor input";
	private Log4jMonitorConfigDialogModel model = new Log4jMonitorConfigDialogModel();

	public Log4jMonitorInputPaletteItem() {
		imagePath = "images/icon.png";
		disabledImagePath = "images/icon_dis.png";
	}

	@Override
	public String getLabel() {
		return LABEL;
	}

	public void openDialog(Log4jMonitorInputProcLogic logic) {
		Log4jMonitorConfigDialog dialog = new Log4jMonitorConfigDialog(Display
				.getDefault().getActiveShell(), logic, model);
		dialog.open();
	}

}
