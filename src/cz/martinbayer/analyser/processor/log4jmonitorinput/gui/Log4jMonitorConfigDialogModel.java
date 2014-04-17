package cz.martinbayer.analyser.processor.log4jmonitorinput.gui;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cz.martinbayer.utils.model.ObservableModelObject;

public class Log4jMonitorConfigDialogModel extends ObservableModelObject implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6279002328106622227L;
	public static final String PROPERTY_SELECTED_DIRECTORY_PATH = "selectedDirectoryPath";
	public static final String PROPERTY_SELECTED_EXTENSIONS = "selectedExtensions";
	public static final String PROPERTY_SELECTED_FILES = "selectedFiles";
	public static final String PROPERTY_AVAILABLE_EXTENSIONS = "availableExtensions";
	public static final String PROPERTY_FILES_COUNT = "filesCount";
	public static final String PROPERTY_FILES_SIZE = "filesSize";
	public static final String PROPERTY_PATTERN = "pattern";
	public static final String PROPERTY_DATE_TIME_FORMAT = "dateTimeFormat";

	private String selectedDirectoryPath;
	private List<String> selectedExtensions;
	private File[] selectedFiles;
	private ArrayList<String> availableExtensions;
	private int filesCount;
	private long filesSize;
	private String pattern;
	private String dateTimeFormat;

	public final String getSelectedDirectoryPath() {
		return selectedDirectoryPath;
	}

	public final void setSelectedDirectoryPath(String selectedDirectoryPath) {
		firePropertyChange(PROPERTY_SELECTED_DIRECTORY_PATH,
				this.selectedDirectoryPath,
				this.selectedDirectoryPath = selectedDirectoryPath);
	}

	public final List<String> getSelectedExtensions() {
		return selectedExtensions;
	}

	public final void setSelectedExtensions(List<String> selectedExtensions) {
		firePropertyChange(PROPERTY_SELECTED_EXTENSIONS,
				this.selectedExtensions,
				this.selectedExtensions = selectedExtensions);
	}

	public final File[] getSelectedFiles() {
		return selectedFiles;
	}

	public final void setSelectedFiles(File[] selectedFiles) {
		firePropertyChange(PROPERTY_SELECTED_FILES, this.selectedFiles,
				this.selectedFiles = selectedFiles);
		setFilesCount(this.selectedFiles.length);
		updateFilesSize();
	}

	private void updateFilesSize() {
		long size = 0;
		for (File f : this.selectedFiles) {
			size += f.length() / 1024;
		}
		setFilesSize(size);
	}

	public final ArrayList<String> getAvailableExtensions() {
		return availableExtensions;
	}

	public final void setAvailableExtensions(
			ArrayList<String> availableExtensions) {
		firePropertyChange(PROPERTY_AVAILABLE_EXTENSIONS,
				this.availableExtensions,
				this.availableExtensions = availableExtensions);
		setSelectedExtensions(availableExtensions);
	}

	public final int getFilesCount() {
		return filesCount;
	}

	public final void setFilesCount(int filesCount) {
		firePropertyChange(PROPERTY_FILES_COUNT, this.filesCount,
				this.filesCount = filesCount);
	}

	public final long getFilesSize() {
		return filesSize;
	}

	public final void setFilesSize(long filesSize) {
		firePropertyChange(PROPERTY_FILES_SIZE, this.filesSize,
				this.filesSize = filesSize);
	}

	public final String getPattern() {
		return pattern;
	}

	public final void setPattern(String pattern) {
		firePropertyChange(PROPERTY_PATTERN, this.pattern,
				this.pattern = pattern);
	}

	public final String getDateTimeFormat() {
		return this.dateTimeFormat;
	}

	public final void setDateTimeFormat(String dateTimeFormat) {
		firePropertyChange(PROPERTY_DATE_TIME_FORMAT, this.dateTimeFormat,
				this.dateTimeFormat = dateTimeFormat);
	}

}
