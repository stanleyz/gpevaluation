package org.xopen.gpevaluation.rcp.ui;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.nebula.jface.gridviewer.GridTableViewer;
import org.eclipse.nebula.widgets.grid.Grid;
import org.eclipse.nebula.widgets.grid.GridColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.forms.IDetailsPage;
import org.eclipse.ui.forms.IFormPart;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.xopen.gpevaluation.rcp.SystemConstants;
import org.xopen.gpevaluation.rcp.model.IFactorNode;
import org.xopen.gpevaluation.rcp.model.Session;
import org.xopen.gpevaluation.rcp.provider.TextTopLeftRenderer;
import org.xopen.gpevaluation.rcp.provider.WeightCellModifier;
import org.xopen.gpevaluation.rcp.provider.WeightGridContentProvider;
import org.xopen.gpevaluation.rcp.provider.WeightGridLabelProvider;

public class FactorDetailsPage implements IDetailsPage {
	// Name text
	private Text name, remark, type, function, threshold;
	// Remark text
	private boolean dirty, b=false;
	private Label weight, realWeight;

	private IManagedForm form;
	private IFactorNode factorNode;
	private TreeViewer viewer;
	private Composite composite, baseArea, gridArea;
	private FormToolkit toolkit;
	private GridTableViewer gridViewer;
	private String[] columnNames;
	private Grid grid;
	private final TextTopLeftRenderer renderer = new TextTopLeftRenderer();

	public FactorDetailsPage(TreeViewer viewer) {
		this.viewer = viewer;
	}

	public void createContents(Composite parent) {
		/*
		 * check the permissions
		 */
		try {
			b = Session.getInstance().checkPermission(
					SystemConstants.PERM_ADMIN);
		} catch (ConfigurationException e) {
		}

		factorNode = (IFactorNode) ((Tree) viewer.getControl()).getSelection()[0]
				.getData();

		toolkit = form.getToolkit();

		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		layout.marginBottom = 20;
		parent.setLayout(layout);

		final Section section = toolkit.createSection(parent, Section.TITLE_BAR
				| Section.DESCRIPTION);
		section.marginWidth = 5;
		section.setText(Messages.FactorDetailsPage_0);
		section.setDescription(Messages.FactorDetailsPage_1);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.grabExcessHorizontalSpace = gd.grabExcessVerticalSpace = true;
		section.setLayoutData(gd);

		composite = toolkit.createComposite(section, SWT.WRAP);
		layout = new GridLayout();
		layout.marginWidth = layout.marginHeight = 0;
		layout.numColumns = 2;
		layout.horizontalSpacing = 20;
		composite.setLayout(layout);

		toolkit.createLabel(composite, Messages.FactorDetailsPage_2);
		name = toolkit.createText(composite, "", SWT.SINGLE); //$NON-NLS-1$
		gd = new GridData(GridData.FILL_HORIZONTAL
				| GridData.VERTICAL_ALIGN_BEGINNING);
		gd.widthHint = 10;
		name.setLayoutData(gd);
		name.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				final String s = name.getText();
				if (!s.equals(factorNode.getName())) {
					factorNode.setFactorProperty(SystemConstants.MODEL_NAME, s);
				}

				viewer.update(factorNode, null);
				if (grid != null && !grid.isDisposed()) {
					renderer.setText(s);
					grid.redraw();
				}
			}
		});

		toolkit.createLabel(composite, Messages.FactorDetailsPage_4);
		remark = toolkit.createText(composite, "", SWT.MULTI); //$NON-NLS-1$
		gd = new GridData(GridData.FILL_HORIZONTAL
				| GridData.VERTICAL_ALIGN_BEGINNING);
		gd.heightHint = 50;
		remark.setLayoutData(gd);
		remark.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				final String s = remark.getText();
				if (StringUtils.isNotBlank(s)
						&& (!s.equals(factorNode.getFactor().getRemark()))) {
					factorNode.setFactorProperty(SystemConstants.MODEL_REMARK,
							s);
				}
			}
		});
		remark.setEnabled(b);

		toolkit.createLabel(composite, Messages.FactorDetailsPage_6);
		weight = toolkit.createLabel(composite, ""); //$NON-NLS-1$
		gd = new GridData(GridData.FILL_HORIZONTAL
				| GridData.VERTICAL_ALIGN_BEGINNING);
		weight.setLayoutData(gd);

		toolkit.createLabel(composite, Messages.FactorDetailsPage_3);
		realWeight = toolkit.createLabel(composite, ""); //$NON-NLS-1$
		gd = new GridData(GridData.FILL_HORIZONTAL
				| GridData.VERTICAL_ALIGN_BEGINNING);
		realWeight.setLayoutData(gd);

		toolkit.paintBordersFor(section);
		section.setClient(composite);
	}

	public void commit(boolean onSave) {
	}

	public void dispose() {
	}

	public void initialize(IManagedForm form) {
		this.form = form;
	}

	public boolean isDirty() {
		return dirty;
	}

	public boolean isStale() {
		return false;
	}

	public void refresh() {
	}

	public void setFocus() {
	}

	public boolean setFormInput(Object input) {
		return false;
	}

	public void selectionChanged(IFormPart part, ISelection selection) {
		IStructuredSelection _selection = (IStructuredSelection) selection;
		if (_selection.size() == 1) {
			factorNode = (IFactorNode) _selection.getFirstElement();
		} else {
			factorNode = null;
		}

		createUI();
		update();
	}

	private void update() {
		String s = factorNode.getName();
		name.setText(s == null ? "" : s); //$NON-NLS-1$

		s = factorNode.getFactor().getRemark();
		remark.setText(s == null ? "" : s); //$NON-NLS-1$

		s = factorNode.getFactor().getWeight();
		weight.setText(s == null ? SystemConstants.ONE : s);

		s = factorNode.getFactor().getRealWeight();
		realWeight.setText(s == null ? SystemConstants.ONE : s);

		if (!factorNode.hasChildren()) {
			s = factorNode.getFactor().getFunction();
			function.setText(s == null ? "" : s); //$NON-NLS-1$

			s = factorNode.getFactor().getType();
			type.setText(s == null ? "" : s); //$NON-NLS-1$

			s = factorNode.getFactor().getThreshold();
			threshold.setText(s == null ? "" : s); //$NON-NLS-1$
		}
	}

	private void createUI() {
		if (factorNode.getFactor().getId().equals(SystemConstants.ROOT)) {
			name.setEnabled(false);
		} else {
			name.setEnabled(b);
		}

		if (factorNode.hasChildren()) {
			if (baseArea != null) {
				baseArea.dispose();
			}

			if (gridArea != null) {
				gridArea.dispose();
			}

			gridArea = new Composite(composite, SWT.NONE);
			GridData gd = new GridData(GridData.FILL_BOTH);
			gd.grabExcessVerticalSpace = true;
			gd.minimumHeight = 350;
			gd.horizontalSpan = 2;
			gridArea.setLayoutData(gd);
			final GridLayout gridLayout = new GridLayout();
			gridLayout.marginWidth = gridLayout.marginHeight = 0;
			gridLayout.marginTop = 10;
			gridLayout.numColumns = 1;
			gridArea.setLayout(gridLayout);

			Label label = new Label(gridArea, SWT.NONE);
			label.setText(Messages.FactorDetailsPage_15);
			gd = new GridData(GridData.VERTICAL_ALIGN_BEGINNING);
			label.setLayoutData(gd);

			grid = new Grid(gridArea, SWT.MULTI | SWT.BORDER | SWT.H_SCROLL
					| SWT.V_SCROLL);
			grid.setAutoHeight(true);
			grid.setAutoWidth(true);
			grid.setHeaderVisible(true);
			grid.setRowHeaderVisible(true);
			grid.setRowsResizeable(true);
			grid.setCellSelectionEnabled(true);
			gd = new GridData(GridData.FILL_BOTH);
			grid.setLayoutData(gd);
			grid.setTopLeftRenderer(renderer);

			gridViewer = new GridTableViewer(grid);
			gridViewer.setUseHashlookup(true);
			gridViewer.setContentProvider(new WeightGridContentProvider());
			gridViewer.setLabelProvider(new WeightGridLabelProvider(this));

			final List<IFactorNode> children = factorNode.getChildren();
			renderer.setText(factorNode.getName());
			columnNames = new String[children.size()];

			for (int i = grid.getColumnCount() - 1; i >= 0; i--) {
				grid.getColumn(i).dispose();
			}

			GridColumn column;
			for (int i = 0; i < children.size(); i++) {
				column = new GridColumn(grid, SWT.CENTER);
				columnNames[i] = children.get(i).getFactor().getId();
				column.setText(children.get(i).getName());
				column.setWidth(50);
				column.setResizeable(true);
			}

			gridViewer.setInput(factorNode);

			for (int i = 0; i < children.size(); i++) {
				grid.getItem(i).setHeaderText(children.get(i).getName());
			}

			final CellEditor[] editors = new CellEditor[children.size()];
			for (int i = 0; i < children.size(); i++) {
				editors[i] = new TextCellEditor(grid);
			}
			gridViewer.setColumnProperties(columnNames);
			if (b) {
				gridViewer.setCellEditors(editors);
			}
			gridViewer.setCellModifier(new WeightCellModifier(this));
		}

		else {
			if (gridArea != null) {
				gridArea.dispose();
			}

			if (baseArea == null || baseArea.isDisposed()) {
				baseArea = toolkit.createComposite(composite, SWT.CENTER);
				GridData gd = new GridData(GridData.FILL_BOTH);
				gd.grabExcessVerticalSpace = true;
				gd.horizontalSpan = 2;
				baseArea.setLayoutData(gd);
				final GridLayout gridLayout = new GridLayout();
				gridLayout.marginWidth = gridLayout.marginHeight = 0;
				gridLayout.marginTop = 10;
				gridLayout.numColumns = 2;
				baseArea.setLayout(gridLayout);

				Label label = toolkit.createLabel(baseArea,
						Messages.FactorDetailsPage_18, SWT.WRAP | SWT.LEFT);
				GridData labelGD = new GridData(
						GridData.VERTICAL_ALIGN_BEGINNING);
				labelGD.widthHint = 65;
				labelGD.heightHint = 45;
				label.setLayoutData(labelGD);
				function = toolkit.createText(baseArea, "", SWT.SINGLE); //$NON-NLS-1$
				gd = new GridData(GridData.FILL_HORIZONTAL
						| GridData.VERTICAL_ALIGN_BEGINNING);
				gd.widthHint = 10;
				function.setLayoutData(gd);
				function.addModifyListener(new ModifyListener() {
					public void modifyText(ModifyEvent e) {
						final String s = function.getText();
						if (StringUtils.isNotBlank(s)
								&& (!s.equals(factorNode.getFactor()
										.getFunction()))) {
							factorNode.setFactorProperty(
									SystemConstants.FACTOR_FUNCTION, s);
						}
					}
				});
				function.setEnabled(b);

				label = toolkit.createLabel(baseArea,
						Messages.FactorDetailsPage_5, SWT.WRAP | SWT.LEFT);
				label.setLayoutData(labelGD);
				type = toolkit.createText(baseArea, "", SWT.SINGLE); //$NON-NLS-1$
				gd = new GridData(GridData.FILL_HORIZONTAL
						| GridData.VERTICAL_ALIGN_BEGINNING);
				gd.widthHint = 10;
				type.setLayoutData(gd);
				type.addModifyListener(new ModifyListener() {
					public void modifyText(ModifyEvent e) {
						final String s = type.getText();
						if (StringUtils.isNotBlank(s)
								&& (!s.equals(factorNode.getFactor().getType()))) {
							factorNode.setFactorProperty(
									SystemConstants.FACTOR_TYPE, s);
						}
					}
				});
				type.setEnabled(b);

				label = toolkit.createLabel(baseArea,
						Messages.FactorDetailsPage_7, SWT.WRAP | SWT.LEFT);
				label.setLayoutData(labelGD);
				threshold = toolkit.createText(baseArea, "", SWT.SINGLE); //$NON-NLS-1$
				gd = new GridData(GridData.FILL_HORIZONTAL
						| GridData.VERTICAL_ALIGN_BEGINNING);
				gd.widthHint = 10;
				threshold.setLayoutData(gd);
				threshold.addModifyListener(new ModifyListener() {
					public void modifyText(ModifyEvent e) {
						final String s = threshold.getText();
						if (StringUtils.isNotBlank(s)
								&& (!s.equals(factorNode.getFactor()
										.getThreshold()))) {
							factorNode.setFactorProperty(
									SystemConstants.FACTOR_THRESHOLD, s);
						}
					}
				});
				threshold.setEnabled(b);
			}
		}

		composite.layout();
	}

	public void setDirty(boolean _dirty) {
		this.dirty = _dirty;
		FormPage page = (FormPage) form.getContainer();
		((SchemeEditor) page.getEditor()).setDirty(_dirty);
	}

	public GridTableViewer getGridViewer() {
		return gridViewer;
	}

	public List<String> getColumnNames() {
		return Arrays.asList(columnNames);
	}
}
