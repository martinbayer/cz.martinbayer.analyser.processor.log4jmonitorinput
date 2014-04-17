package cz.martinbayer.analyser.processor.log4jmonitorinput.gui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;

import org.apache.commons.io.FilenameUtils;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.databinding.viewers.ViewersObservables;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import cz.martinbayer.analyser.processor.log4jmonitorinput.gui.paletteitem.processor.Log4jMonitorInputProcLogic;
import cz.martinbayer.utils.gui.SWTUtils;

public class Log4jMonitorConfigDialog extends TitleAreaDialog {

	private Log4jMonitorInputProcLogic logic;
	private Label patternLabel, dateTimeFormatLabel;
	private Text patternText, dateTimeFormatText;
	private DirectoryDialog directoryDialog;
	private SelectionAdapter directoryDialogSelection;
	private Button chooseDirectoryBtn;
	private Label chooseDirectoryLabel;
	private Text chooseDirectoryText;
	private Label possibleExtensionsLabel;
	private ListViewer possibleExtensionsList;
	private Log4jMonitorConfigDialogModel dialogModel;
	private Label filesCountLabel;
	private Text filesCountText;
	private Label filesSizeLabel;
	private Text filesSizeText;

	public Log4jMonitorConfigDialog(Shell parentShell,
			Log4jMonitorInputProcLogic logic,
			Log4jMonitorConfigDialogModel dialogModel) {
		super(parentShell);
		this.dialogModel = dialogModel;
		this.logic = logic;
		initControlsAndActions(parentShell);
	}

	@Override
	public void create() {
		super.create();
		setTitle("Logback source configuration");
		setMessage("Select folder with logback log records",
				IMessageProvider.INFORMATION);
	}

	private void initControlsAndActions(Shell parentShell) {
		directoryDialog = new DirectoryDialog(parentShell, SWT.NONE);
		directoryDialogSelection = new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (dialogModel.getSelectedDirectoryPath() != null) {
					directoryDialog.setFilterPath(dialogModel
							.getSelectedDirectoryPath());
				}
				String selectedDirectory = directoryDialog.open();
				if (selectedDirectory != null) {
					directorySelected(selectedDirectory);
				}
			}
		};

	}

	private void directorySelected(String selectedDirectory) {
		dialogModel.setSelectedDirectoryPath(selectedDirectory);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite parentComposite = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(parentComposite, SWT.None);
		GridLayout layout = new GridLayout(5, true);
		layout.marginWidth = 10;
		container.setLayout(layout);
		GridData data = new GridData();
		data.horizontalSpan = 1;
		data.horizontalAlignment = GridData.END;
		patternLabel = new Label(container, SWT.NONE);
		patternLabel.setText("Specify pattern:");
		patternLabel.setLayoutData(data);

		data = new GridData();
		data.grabExcessHorizontalSpace = true;
		data.horizontalSpan = 4;
		data.horizontalAlignment = GridData.FILL;
		patternText = new Text(container, SWT.BORDER);
		patternText.setLayoutData(data);

		data = new GridData();
		data.horizontalSpan = 1;
		data.horizontalAlignment = GridData.END;
		dateTimeFormatLabel = new Label(container, SWT.NONE);
		dateTimeFormatLabel.setText("Used date/time format:");
		dateTimeFormatLabel.setLayoutData(data);

		data = new GridData();
		data.grabExcessHorizontalSpace = true;
		data.horizontalSpan = 4;
		data.horizontalAlignment = GridData.FILL;
		dateTimeFormatText = new Text(container, SWT.BORDER);
		dateTimeFormatText.setLayoutData(data);

		data = new GridData();
		data.horizontalSpan = 1;
		data.horizontalAlignment = GridData.END;
		chooseDirectoryLabel = new Label(container, SWT.NONE);
		chooseDirectoryLabel.setText("Choose files/folders:");
		chooseDirectoryLabel.setLayoutData(data);

		data = new GridData();
		data.grabExcessHorizontalSpace = true;
		data.horizontalSpan = 3;
		data.horizontalAlignment = GridData.FILL;
		chooseDirectoryText = new Text(container, SWT.BORDER);
		chooseDirectoryText.setLayoutData(data);

		data = new GridData();
		data.horizontalSpan = 1;
		chooseDirectoryBtn = new Button(container, SWT.NONE);
		chooseDirectoryBtn.setImage(SWTUtils.getImage("images", "folder.png",
				getClass()));
		chooseDirectoryBtn.addSelectionListener(directoryDialogSelection);

		data = new GridData();
		data.horizontalSpan = 1;
		data.horizontalAlignment = GridData.END;
		possibleExtensionsLabel = new Label(container, SWT.NONE);
		possibleExtensionsLabel.setText("Choose extension(s):");

		data = new GridData();
		data.horizontalSpan = 4;
		data.horizontalAlignment = GridData.BEGINNING;
		data.heightHint = 200;
		possibleExtensionsList = new ListViewer(container, SWT.BORDER
				| SWT.V_SCROLL | SWT.MULTI);
		possibleExtensionsList.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element) {
				return "*." + super.getText(element);
			}
		});

		ObservableListContentProvider contentProvider = new ObservableListContentProvider();
		possibleExtensionsList.setContentProvider(contentProvider);
		possibleExtensionsList.getControl().setLayoutData(data);

		data = new GridData();
		data.horizontalSpan = 1;
		data.horizontalAlignment = GridData.END;
		filesCountLabel = new Label(container, SWT.NONE);
		filesCountLabel.setText("Files count:");
		filesCountLabel.setLayoutData(data);

		data = new GridData();
		data.horizontalSpan = 1;
		data.horizontalAlignment = GridData.FILL;
		filesCountText = new Text(container, SWT.BORDER);
		filesCountText.setEditable(false);
		filesCountText.setLayoutData(data);

		data = new GridData();
		data.horizontalSpan = 3;
		Label emptyCell = new Label(container, SWT.NONE);
		emptyCell.setLayoutData(data);

		data = new GridData();
		data.horizontalSpan = 1;
		data.horizontalAlignment = GridData.END;
		filesSizeLabel = new Label(container, SWT.NONE);
		filesSizeLabel.setText("Files size:");
		filesSizeLabel.setLayoutData(data);

		data = new GridData();
		data.horizontalSpan = 1;
		data.horizontalAlignment = GridData.FILL;
		filesSizeText = new Text(container, SWT.BORDER);
		filesSizeText.setEditable(false);
		filesSizeText.setLayoutData(data);

		data = new GridData();
		data.horizontalSpan = 3;
		data.horizontalAlignment = GridData.BEGINNING;
		Label kbUnitLabel = new Label(container, SWT.NONE);
		kbUnitLabel.setText("kB");
		kbUnitLabel.setLayoutData(data);

		initBinding();
		initListeners();
		return container;
	}

	private void initListeners() {
		dialogModel.addPropertyChangeListener(
				Log4jMonitorConfigDialogModel.PROPERTY_SELECTED_EXTENSIONS,
				new Log4jMonitorConfigDialogLogic());
	}

	private void initBinding() {
		// create new Context
		DataBindingContext ctx = new DataBindingContext();

		/* create binding for root directory text field */
		IObservableValue target = WidgetProperties.text(SWT.Modify).observe(
				chooseDirectoryText);
		IObservableValue model = BeanProperties.value(
				Log4jMonitorConfigDialogModel.class,
				Log4jMonitorConfigDialogModel.PROPERTY_SELECTED_DIRECTORY_PATH)
				.observe(dialogModel);
		ctx.bindValue(target, model);

		/* create binding for pattern */
		target = WidgetProperties.text(SWT.Modify).observe(patternText);
		model = BeanProperties.value(Log4jMonitorConfigDialogModel.class,
				Log4jMonitorConfigDialogModel.PROPERTY_PATTERN).observe(
				dialogModel);
		ctx.bindValue(target, model);

		/* create binding for date time format */
		target = WidgetProperties.text(SWT.Modify).observe(dateTimeFormatText);
		model = BeanProperties.value(Log4jMonitorConfigDialogModel.class,
				Log4jMonitorConfigDialogModel.PROPERTY_DATE_TIME_FORMAT)
				.observe(dialogModel);
		ctx.bindValue(target, model);

		/* create binding for list with possible extensions */
		IObservableList listTarget = ViewersObservables
				.observeMultiPostSelection(possibleExtensionsList);
		IObservableList listModel = BeanProperties.list(
				Log4jMonitorConfigDialogModel.class,
				Log4jMonitorConfigDialogModel.PROPERTY_SELECTED_EXTENSIONS)
				.observe(dialogModel);
		ctx.bindList(listTarget, listModel);

		WritableList extensionsList = new WritableList();
		possibleExtensionsList.setInput(extensionsList);
		listModel = BeanProperties.list(Log4jMonitorConfigDialogModel.class,
				Log4jMonitorConfigDialogModel.PROPERTY_AVAILABLE_EXTENSIONS)
				.observe(dialogModel);
		ctx.bindList(extensionsList, listModel);

		target = WidgetProperties.text().observe(filesCountText);
		model = BeanProperties.value(Log4jMonitorConfigDialogModel.class,
				Log4jMonitorConfigDialogModel.PROPERTY_FILES_COUNT).observe(
				dialogModel);
		ctx.bindValue(target, model);

		target = WidgetProperties.text().observe(filesSizeText);
		model = BeanProperties.value(Log4jMonitorConfigDialogModel.class,
				Log4jMonitorConfigDialogModel.PROPERTY_FILES_SIZE).observe(
				dialogModel);
		ctx.bindValue(target, model);

		dialogModel.addPropertyChangeListener(
				Log4jMonitorConfigDialogModel.PROPERTY_SELECTED_DIRECTORY_PATH,
				new PropertyChangeListener() {

					@Override
					public void propertyChange(PropertyChangeEvent evt) {
						HashSet<String> extensionsSet = new HashSet<>();
						/*
						 * search for all sub files extensions and set them to
						 * the extensions list. If selected item exists, select
						 * it again, otherwise select nothing
						 */
						File dir;

						if (evt.getNewValue() instanceof String
								&& (dir = new File((String) evt.getNewValue()))
										.exists() && dir.isDirectory()) {
							for (File file : dir.listFiles()) {
								String extension = null;
								if (file.isFile()
										&& !file.isHidden()
										&& (extension = FilenameUtils
												.getExtension(file.getName())) != null) {
									extensionsSet.add(extension);
								}
							}
						}
						dialogModel
								.setAvailableExtensions(new ArrayList<String>(
										extensionsSet));
					}
				});
	}

	// @Override
	// protected Point getInitialSize() {
	// return new Point(600, 450);
	// }

	@Override
	protected void okPressed() {
		if (validateSelectedFolder()) {
			logic.setLogFiles(dialogModel.getSelectedFiles());
		}
		logic.setPattern(dialogModel.getPattern());
		logic.setDateTimeFormat(dialogModel.getDateTimeFormat());
		super.okPressed();
	}

	private boolean validateSelectedFolder() {
		if (dialogModel.getSelectedDirectoryPath() == null
				|| new File(dialogModel.getSelectedDirectoryPath()).exists()) {
			return true;
		}
		return false;
	}
}
