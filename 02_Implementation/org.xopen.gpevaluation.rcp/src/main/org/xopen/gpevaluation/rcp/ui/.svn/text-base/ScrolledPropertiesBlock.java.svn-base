package org.xopen.gpevaluation.rcp.ui;

import org.apache.commons.configuration.ConfigurationException;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.DetailsPart;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.MasterDetailsBlock;
import org.eclipse.ui.forms.SectionPart;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.xopen.gpevaluation.rcp.Activator;
import org.xopen.gpevaluation.rcp.SystemConstants;
import org.xopen.gpevaluation.rcp.model.FactorNode;
import org.xopen.gpevaluation.rcp.model.Factors;
import org.xopen.gpevaluation.rcp.model.IFactorNode;
import org.xopen.gpevaluation.rcp.model.Session;
import org.xopen.gpevaluation.rcp.provider.MasterContentProvider;
import org.xopen.gpevaluation.rcp.provider.MasterLabelProvider;
import org.xopen.gpevaluation.rcp.provider.SchemeEditorInput;

public class ScrolledPropertiesBlock extends MasterDetailsBlock {
	private Composite masterArea, buttonsArea;
	private GridLayout masterLayout;
	private RowLayout buttonsLayout;
	private TreeViewer treeViewer;

	protected void createMasterPart(final IManagedForm managedForm,
			Composite parent) {
		FormToolkit toolkit = managedForm.getToolkit();
		Section section = toolkit.createSection(parent, Section.DESCRIPTION
				| Section.TITLE_BAR);
		section.setText(Messages.ScrolledPropertiesBlock_0);
		section.setDescription(Messages.ScrolledPropertiesBlock_1);
		section.marginWidth = 10;
		section.marginHeight = 5;

		masterArea = toolkit.createComposite(section, SWT.WRAP);
		toolkit.paintBordersFor(masterArea);
		masterLayout = new GridLayout();
		masterLayout.numColumns = 1;
		masterArea.setLayout(masterLayout);

		final Tree tree = toolkit.createTree(masterArea, SWT.H_SCROLL
				| SWT.V_SCROLL);
		GridData gd = new GridData(GridData.FILL_BOTH);
		tree.setLayoutData(gd);
		tree.setLinesVisible(true);

		buttonsArea = toolkit.createComposite(masterArea, SWT.CENTER);
		buttonsLayout = new RowLayout(SWT.HORIZONTAL);
		buttonsLayout.justify = true;
		buttonsLayout.center = true;
		buttonsArea.setLayout(buttonsLayout);

		RowData rd = new RowData(52, 23);

		Button addButton = toolkit.createButton(buttonsArea, Messages.ScrolledPropertiesBlock_2, SWT.PUSH
				| SWT.CENTER);
		addButton.setLayoutData(rd);
		addButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (tree.getSelection().length == 0) {
					PromptBox.Prompt(Messages.ScrolledPropertiesBlock_3, Messages.ScrolledPropertiesBlock_4, SWT.OK
							| SWT.ICON_WARNING | SWT.APPLICATION_MODAL);
				} else {
					IFactorNode node = (IFactorNode) tree.getSelection()[0]
							.getData();
					IFactorNode childNode = node.addChildren();
					treeViewer.add(node, childNode);
					treeViewer.expandToLevel(node, 1);
					treeViewer
							.setSelection(new StructuredSelection(node), true);
				}
			}
		});

		Button removeButton = toolkit.createButton(buttonsArea, Messages.ScrolledPropertiesBlock_5, SWT.PUSH
				| SWT.CENTER);
		removeButton.setLayoutData(rd);
		removeButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (tree.getSelection().length == 0) {
					PromptBox.Prompt(Messages.ScrolledPropertiesBlock_6, Messages.ScrolledPropertiesBlock_7, SWT.OK
							| SWT.ICON_WARNING | SWT.APPLICATION_MODAL);
				} else {
					IFactorNode node = (IFactorNode) tree.getSelection()[0]
							.getData();

					if (node.getFactor().getId().equals(SystemConstants.ROOT)) {
						PromptBox.Prompt(Messages.ScrolledPropertiesBlock_8, Messages.ScrolledPropertiesBlock_9, SWT.OK
								| SWT.ICON_ERROR | SWT.APPLICATION_MODAL);
						return;
					}

					if (PromptBox.Prompt(Messages.ScrolledPropertiesBlock_10, Messages.ScrolledPropertiesBlock_11, SWT.OK
							| SWT.CANCEL | SWT.ICON_QUESTION) == SWT.OK) {
						node.setFactorProperty(SystemConstants.FACTOR_REMOVED,
								true);
						treeViewer.remove(node);
						if (!node.getParent().calculateWeight()) {
							MessageDialog.openWarning(PlatformUI.getWorkbench()
									.getActiveWorkbenchWindow().getShell(),
									Messages.ScrolledPropertiesBlock_12, Messages.ScrolledPropertiesBlock_13);
							treeViewer.setSelection(new StructuredSelection(
									node.getParent()), true);
						}
					}
				}
			}
		});

		final Button toggleButton = toolkit.createButton(buttonsArea, Messages.ScrolledPropertiesBlock_14,
				SWT.PUSH | SWT.CENTER);
		toggleButton.setLayoutData(rd);
		toggleButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (toggleButton.getText().equals(Messages.ScrolledPropertiesBlock_15)) {
					treeViewer.expandAll();
					toggleButton.setText(Messages.ScrolledPropertiesBlock_16);
				} else {
					treeViewer.collapseAll();
					toggleButton.setText(Messages.ScrolledPropertiesBlock_17);
				}
			}
		});

		section.setClient(masterArea);
		final SectionPart sectionPart = new SectionPart(section);
		managedForm.addPart(sectionPart);

		treeViewer = new TreeViewer(tree);
		treeViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				managedForm.fireSelectionChanged(sectionPart, event
						.getSelection());
			}
		});
		treeViewer.setContentProvider(new MasterContentProvider());
		treeViewer.setLabelProvider(new MasterLabelProvider());
		SchemeEditorInput input = (SchemeEditorInput) ((SchemeEditorPage) managedForm
				.getContainer()).getEditorInput();
		treeViewer.setInput(Factors.getInstance().getRoot(
				input.getScheme().getPath()));
		
		/*
		 * check permissions to disable/enable the add/remove buttons
		 */
		boolean b = false;
		try {
			b = Session.getInstance().checkPermission(
					SystemConstants.PERM_ADMIN);
		} catch (ConfigurationException e) {
		}
		
		addButton.setEnabled(b);
		removeButton.setEnabled(b);
	}

	protected void createToolBarActions(IManagedForm managedForm) {
		final ScrolledForm form = managedForm.getForm();

		Action horAction = new Action(Messages.ScrolledPropertiesBlock_18, Action.AS_RADIO_BUTTON) {
			public void run() {
				sashForm.setOrientation(SWT.HORIZONTAL);
				buttonsLayout.type = SWT.HORIZONTAL;
				masterLayout.numColumns = 1;

				masterArea.layout();
				buttonsArea.layout();

				form.reflow(true);
			}
		};
		horAction.setImageDescriptor(Activator.getDefault().getImageRegistry()
				.getDescriptor(Activator.HORIZONTAL_ICON));
		horAction.setChecked(true);
		horAction.setToolTipText(Messages.ScrolledPropertiesBlock_19);
		Action verAction = new Action(Messages.ScrolledPropertiesBlock_20, Action.AS_RADIO_BUTTON) {
			public void run() {
				sashForm.setOrientation(SWT.VERTICAL);
				buttonsLayout.type = SWT.VERTICAL;
				masterLayout.numColumns = 2;

				masterArea.layout();
				buttonsArea.layout();

				form.reflow(true);
			}
		};
		verAction.setImageDescriptor(Activator.getDefault().getImageRegistry()
				.getDescriptor(Activator.VERTICAL_ICON));
		verAction.setChecked(false);
		verAction.setToolTipText(Messages.ScrolledPropertiesBlock_21);
		form.getToolBarManager().add(horAction);
		form.getToolBarManager().add(verAction);
	}

	protected void registerPages(DetailsPart detailsPart) {
		detailsPart.registerPage(FactorNode.class, new FactorDetailsPage(
				treeViewer) {
		});
	}
}
