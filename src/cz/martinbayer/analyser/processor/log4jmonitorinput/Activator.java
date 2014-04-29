package cz.martinbayer.analyser.processor.log4jmonitorinput;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import cz.martinbayer.analyser.processors.IProcessorItemWrapper;

public class Activator implements BundleActivator {

	private static BundleContext context;
	private static IEclipseContext eclipseContext;

	static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext
	 * )
	 */
	@Override
	public void start(BundleContext bundleContext) throws Exception {
		bundleContext.registerService(IProcessorItemWrapper.class.getName(),
				new Log4jMonitorInputProcItemWrapper(), null);
		Activator.context = bundleContext;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
	}

	public static void setEclipseContext(IEclipseContext eclipseContext) {
		Activator.eclipseContext = eclipseContext;
	}

	public synchronized static IEclipseContext getEclipseContext() {
		return Activator.eclipseContext;
	}
}
