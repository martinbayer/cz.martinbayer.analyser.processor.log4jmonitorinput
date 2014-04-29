package cz.martinbayer.analyser.processor.log4jmonitorinput;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.swt.events.MouseEvent;

import cz.martinbayer.analyser.impl.ConcreteE4LogsisLog;
import cz.martinbayer.analyser.processor.log4jmonitorinput.gui.paletteitem.processor.Log4jMonitorInputProcLogic;
import cz.martinbayer.analyser.processor.log4jmonitorinput.paletteitem.Log4jMonitorInputPaletteItem;
import cz.martinbayer.analyser.processors.IProcessorItemWrapper;
import cz.martinbayer.analyser.processors.IProcessorLogic;
import cz.martinbayer.analyser.processors.IProcessorsPaletteItem;

public class Log4jMonitorInputProcItemWrapper implements
		IProcessorItemWrapper<ConcreteE4LogsisLog> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8804723076311710597L;
	private Log4jMonitorInputProcLogic logic;
	private Log4jMonitorInputPaletteItem item;

	@Override
	public IProcessorLogic<ConcreteE4LogsisLog> getProcessorLogic() {
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
		item.openDialog((Log4jMonitorInputProcLogic) getProcessorLogic());

	}

	@Override
	public IProcessorItemWrapper<ConcreteE4LogsisLog> getInstance() {
		return new Log4jMonitorInputProcItemWrapper();
	}

	@Override
	public void setContext(IEclipseContext ctx) {
		Activator.setEclipseContext(ctx);
	}

}
