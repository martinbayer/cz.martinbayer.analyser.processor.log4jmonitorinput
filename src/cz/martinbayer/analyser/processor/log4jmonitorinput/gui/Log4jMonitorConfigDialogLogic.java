package cz.martinbayer.analyser.processor.log4jmonitorinput.gui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FilenameUtils;

public class Log4jMonitorConfigDialogLogic implements PropertyChangeListener {

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getSource() instanceof Log4jMonitorConfigDialogModel) {
			Log4jMonitorConfigDialogModel model = (Log4jMonitorConfigDialogModel) evt
					.getSource();

			File directory;
			if (model.getSelectedDirectoryPath() != null
					&& (directory = new File(model.getSelectedDirectoryPath()))
							.exists() && directory.isDirectory()) {
				List<String> extsList = model.getSelectedExtensions() != null ? model
						.getSelectedExtensions() : new ArrayList<String>();
				Set<String> extensionsSet = new HashSet<String>(extsList);
				ArrayList<File> files = getFiles(directory, extensionsSet,
						false);
				model.setSelectedFiles(files.toArray(new File[] {}));
			}
		}
	}

	/**
	 * 
	 * @param directory
	 *            - MUST BE DIRECTORY!!!
	 * @param extensionsSet
	 *            - files with these extensions will be returned
	 * @param subfolders
	 *            - True if files in subfolders should be used
	 * @return - list of files from the directory
	 */
	private ArrayList<File> getFiles(File directory, Set<String> extensionsSet,
			boolean subfolders) {
		ArrayList<File> files = new ArrayList<>();
		if (subfolders) {
			for (File dir : directory.listFiles()) {
				if (dir.isDirectory() && !dir.isHidden()) {
					files.addAll(getFiles(dir, extensionsSet, subfolders));
				}
			}
		}
		for (File file : directory.listFiles()) {
			if (file.isFile()
					&& !file.isHidden()
					&& extensionsSet.contains(FilenameUtils.getExtension(file
							.getName()))) {
				files.add(file);
			}
		}
		return files;
	}
}
