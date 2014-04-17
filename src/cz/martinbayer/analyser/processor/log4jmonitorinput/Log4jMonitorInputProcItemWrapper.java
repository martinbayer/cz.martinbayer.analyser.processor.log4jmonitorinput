package cz.martinbayer.analyser.processor.log4jmonitorinput;

import org.eclipse.swt.events.MouseEvent;

import cz.martinbayer.analyser.impl.ConcreteXMLog;
import cz.martinbayer.analyser.processor.log4jmonitorinput.gui.paletteitem.processor.Log4jMonitorInputProcLogic;
import cz.martinbayer.analyser.processor.log4jmonitorinput.paletteitem.Log4jMonitorInputPaletteItem;
import cz.martinbayer.analyser.processors.IProcessorItemWrapper;
import cz.martinbayer.analyser.processors.IProcessorLogic;
import cz.martinbayer.analyser.processors.IProcessorsPaletteItem;

public class Log4jMonitorInputProcItemWrapper implements
		IProcessorItemWrapper<ConcreteXMLog> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8804723076311710597L;
	private Log4jMonitorInputProcLogic logic;
	private Log4jMonitorInputPaletteItem item;

	@Override
	public IProcessorLogic<ConcreteXMLog> getProcessorLogic() {
		if (logic == null) {
			logic = new Log4jMonitorInputProcLogic();
		}
		return logic;
	}

	@Override
	public IProcessorsPaletteItem getProcessorPaletteItem() {
		if (item == null) {
			item = new Log4jMonitorInputPaletteItem();
		}
		return item;
	}

	@Override
	public void mouseDoubleClicked(MouseEvent e) {
		item.openDialog(logic);

	}

	@Override
	public IProcessorItemWrapper<ConcreteXMLog> getInstance() {
		return new Log4jMonitorInputProcItemWrapper();
	}

}
