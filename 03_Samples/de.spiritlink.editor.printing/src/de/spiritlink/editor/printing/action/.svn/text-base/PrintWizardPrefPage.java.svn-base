/*******************************************************************************
 * Copyright (c) 2006 Spirit Link GmbH
 * All rights reserved.
 * 
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/
package de.spiritlink.editor.printing.action;

import java.util.Arrays;

import net.sf.paperclips.PrintJob;
import net.sf.paperclips.ui.PrintPreview;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.dialogs.ContainerCheckedTreeViewer;

import de.spiritlink.editor.printing.Activator;
import de.spiritlink.editor.printing.model.ExampleModel;
import de.spiritlink.editor.printing.model.TreeObject;

/**
 * 
 * @author Tom Seidel - tom.seidel@spiritlink.de
 */
public class PrintWizardPrefPage extends WizardPage {

	private ContainerCheckedTreeViewer viewer;

	private static ImageDescriptor zoomInImage = ImageDescriptor
			.createFromURL((FileLocator.find(
					Activator.getDefault().getBundle(), new Path(
							"icons/zoom_in.png"), null)));
	private static ImageDescriptor zoomOutImage = ImageDescriptor
			.createFromURL((FileLocator.find(
					Activator.getDefault().getBundle(), new Path(
							"icons/zoom_out.png"), null)));
	private static ImageDescriptor nextImage = ImageDescriptor
			.createFromURL((FileLocator.find(
					Activator.getDefault().getBundle(), new Path(
							"icons/navigate_left.png"), null)));
	private static ImageDescriptor prevImage = ImageDescriptor
			.createFromURL((FileLocator.find(
					Activator.getDefault().getBundle(), new Path(
							"icons/navigate_right.png"), null)));
	private static ImageDescriptor headerImage = ImageDescriptor
			.createFromURL((FileLocator.find(
					Activator.getDefault().getBundle(), new Path(
							"icons/print.png"), null)));

	ExampleModel model;

	PrintJob printJob = null;
	TreeObject[] checkedElements;

	PrintPreview preview;

	/**
	 * @param model
	 * @param pageName
	 */
	protected PrintWizardPrefPage(final ExampleModel model) {
		super("wizPrefPage");
		this.model = model;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets
	 * .Composite)
	 */
	public void createControl(final Composite parent) {

		setTitle("Print Opportunity");
		setMessage("Select the data for your model.");
		setImageDescriptor(headerImage);
		final Composite comp = new Composite(parent, SWT.NONE);
		comp.setLayout(new GridLayout(2, false));
		GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		comp.setLayoutData(gd);

		final Label customLabel = new Label(comp, SWT.NONE);
		gd = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gd.horizontalSpan = 2;
		customLabel.setText("Please select the data-fields");
		customLabel.setLayoutData(gd);
		gd = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		gd.horizontalSpan = 2;
		gd.heightHint = 120;
		final Tree tree = new Tree(comp, SWT.CHECK | SWT.BORDER);
		tree.setLayoutData(gd);

		this.viewer = new ContainerCheckedTreeViewer(tree);
		this.viewer.setLabelProvider(new LabelProvider() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * org.eclipse.jface.viewers.LabelProvider#getText(java.lang.Object)
			 */
			@Override
			public String getText(final Object element) {
				return ((TreeObject) element).getDataObject();
			}
		});
		this.viewer.setContentProvider(new ITreeContentProvider() {
			public Object[] getChildren(final Object parentElement) {
				return ((TreeObject) parentElement).getChildren();
			}

			public Object getParent(final Object element) {
				return ((TreeObject) element).getParent();
			}

			public boolean hasChildren(final Object element) {
				return ((TreeObject) element).hasChildren();
			}

			public Object[] getElements(final Object inputElement) {
				return getChildren(inputElement);

			}

			public void dispose() {
				// do nothing

			}

			public void inputChanged(final Viewer viewer,
					final Object oldInput, final Object newInput) {
				// do nothing

			}

		});
		this.viewer.setInput(hookModel());
		this.viewer.addCheckStateListener(new ICheckStateListener() {
			public void checkStateChanged(final CheckStateChangedEvent event) {
				validatePage();
				PrintWizardPrefPage.this.printJob = new PrintJob(
						PrintWizardPrefPage.this.model.getLastName(),
						PrintDesign.createPrint(
								PrintWizardPrefPage.this.checkedElements,
								PrintWizardPrefPage.this.model))
						.setMargins(108);
				PrintWizardPrefPage.this.preview
						.setPrintJob(PrintWizardPrefPage.this.printJob);

			}
		});

		// Label separatorLabel = new Label(comp, SWT.SEPARATOR |
		// SWT.HORIZONTAL);
		// gd = new GridData(SWT.FILL, SWT.CENTER, true, false);
		// gd.horizontalSpan = 2;
		// separatorLabel.setLayoutData(gd);
		//        
		//        
		// Label previewLabel = new Label(comp, SWT.NONE);
		// gd = new GridData(SWT.FILL, SWT.CENTER, true, false);
		// gd.horizontalSpan = 2;
		// previewLabel.setText("Print-Preview");
		// previewLabel.setLayoutData(gd);

		final Composite buttonComp = new Composite(comp, SWT.NONE);
		buttonComp.setLayout(new GridLayout(4, false));
		gd = new GridData(SWT.CENTER, SWT.BEGINNING, true, false);
		gd.horizontalSpan = 2;
		buttonComp.setLayoutData(gd);

		final Button preButton = new Button(buttonComp, SWT.PUSH);
		preButton.setImage(nextImage.createImage());
		preButton.setToolTipText("Previous Page");
		gd = new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false);
		preButton.setLayoutData(gd);

		final Button zoomInButton = new Button(buttonComp, SWT.PUSH);
		zoomInButton.setImage(zoomInImage.createImage());
		zoomInButton.setToolTipText("Zoom In...");
		gd = new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false);
		zoomInButton.setLayoutData(gd);

		final Button zoomOutButton = new Button(buttonComp, SWT.PUSH);
		zoomOutButton.setImage(zoomOutImage.createImage());
		zoomOutButton.setToolTipText("Zoom Out...");
		gd = new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false);
		zoomOutButton.setLayoutData(gd);

		final Button nextButton = new Button(buttonComp, SWT.PUSH);
		nextButton.setImage(prevImage.createImage());
		nextButton.setToolTipText("Next Page");
		gd = new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false);
		nextButton.setLayoutData(gd);

		final ScrolledComposite scroll = new ScrolledComposite(comp, SWT.BORDER
				| SWT.H_SCROLL | SWT.V_SCROLL);

		gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd.horizontalSpan = 2;
		scroll.setLayoutData(gd);
		this.preview = new PrintPreview(scroll, SWT.NONE);
		this.preview.setFitVertical(true);
		this.preview.setFitHorizontal(true);
		TreeObject[] selection = new TreeObject[0];
		if (this.viewer.getCheckedElements().length > 0) {
			selection = (TreeObject[]) this.viewer.getCheckedElements();
		}
		this.printJob = new PrintJob(this.model.getLastName(), PrintDesign
				.createPrint(selection, this.model)).setMargins(108);

		scroll.setContent(this.preview);
		scroll.setLayout(new FillLayout());
		final Listener scrollListener = new Listener() {
			public void handleEvent(Event event) {
				Rectangle bounds = scroll.getClientArea();

				scroll.getHorizontalBar()
						.setPageIncrement(bounds.width * 2 / 3);
				scroll.getVerticalBar().setPageIncrement(bounds.height * 2 / 3);

				if (PrintWizardPrefPage.this.preview.isFitHorizontal()) {
					if (PrintWizardPrefPage.this.preview.isFitVertical()) {
						// fit in both directions, just use client area.
					} else {
						Point size = PrintWizardPrefPage.this.preview
								.computeSize(bounds.width, SWT.DEFAULT);
						bounds.width = Math.max(size.x, bounds.width);
						bounds.height = Math.max(size.y, bounds.height);
					}
				} else if (PrintWizardPrefPage.this.preview.isFitVertical()) {
					Point size = PrintWizardPrefPage.this.preview.computeSize(
							SWT.DEFAULT, bounds.height);
					bounds.width = Math.max(size.x, bounds.width);
					bounds.height = Math.max(size.y, bounds.height);
				} else {
					Point size = PrintWizardPrefPage.this.preview.computeSize(
							SWT.DEFAULT, SWT.DEFAULT);
					bounds.width = Math.max(size.x, bounds.width);
					bounds.height = Math.max(size.y, bounds.height);
				}
				PrintWizardPrefPage.this.preview.setBounds(bounds);
			}
		};
		scroll.addListener(SWT.Resize, scrollListener);
		scroll.addListener(SWT.Activate, new Listener() {
			public void handleEvent(final Event e) {
				scroll.setFocus();
			}
		});

		zoomInButton.addListener(SWT.Selection, new Listener() {

			public void handleEvent(final Event event) {
				float scale = PrintWizardPrefPage.this.preview
						.getAbsoluteScale();
				scale *= 1.1;

				PrintWizardPrefPage.this.preview.setFitVertical(false);
				PrintWizardPrefPage.this.preview.setFitHorizontal(false);
				PrintWizardPrefPage.this.preview.setScale(scale);

				final Rectangle bounds = scroll.getClientArea();
				final Point size = PrintWizardPrefPage.this.preview
						.computeSize(scale);
				bounds.width = Math.max(bounds.width, size.x);
				bounds.height = Math.max(bounds.height, size.y);
				PrintWizardPrefPage.this.preview.setBounds(bounds);

			}

		});
		zoomOutButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				float scale = PrintWizardPrefPage.this.preview
						.getAbsoluteScale();
				scale /= 1.1;

				PrintWizardPrefPage.this.preview.setFitVertical(false);
				PrintWizardPrefPage.this.preview.setFitHorizontal(false);
				PrintWizardPrefPage.this.preview.setScale(scale);

				final Rectangle bounds = scroll.getClientArea();
				final Point size = PrintWizardPrefPage.this.preview
						.computeSize(scale);
				bounds.width = Math.max(bounds.width, size.x);
				bounds.height = Math.max(bounds.height, size.y);
				PrintWizardPrefPage.this.preview.setBounds(bounds);
			}
		});

		preButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				PrintWizardPrefPage.this.preview.setPageIndex(Math.max(0,
						PrintWizardPrefPage.this.preview.getPageIndex() - 1));
			}
		});
		nextButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				PrintWizardPrefPage.this.preview.setPageIndex(Math.min(
						PrintWizardPrefPage.this.preview.getPageIndex() + 1,
						PrintWizardPrefPage.this.preview.getPageCount() - 1));
			}
		});
		this.preview.setFitVertical(true);
		this.preview.setFitHorizontal(true);
		this.preview.setPrintJob(this.printJob);

		setControl(comp);
		setPageComplete(false);
	}

	/**
	 * @return
	 */
	private TreeObject hookModel() {
		final TreeObject root = new TreeObject("");
		final TreeObject firstName = new TreeObject("First Name");
		final TreeObject lastName = new TreeObject("Last Name");
		final TreeObject company = new TreeObject("Company");
		final TreeObject street = new TreeObject("Street");
		final TreeObject zip = new TreeObject("Zip");
		final TreeObject city = new TreeObject("City");
		final TreeObject country = new TreeObject("Country");

		root.addChild(firstName);
		root.addChild(lastName);
		root.addChild(street);
		root.addChild(zip);
		root.addChild(city);
		root.addChild(country);

		return root;
	}

	/**
     * 
     */
	protected void validatePage() {
		if (this.viewer.getCheckedElements().length == 0) {
			setErrorMessage("Nothing checked");
			setPageComplete(false);
		} else {
			setErrorMessage(null);
			setPageComplete(true);
		}
		// ugly...
		this.checkedElements = this.viewer.getCheckedElements().length == 0 ? new TreeObject[0]
				: Arrays.asList(this.viewer.getCheckedElements()).toArray(
						new TreeObject[0]);

	}

	/**
	 * @return the checkedElements
	 */
	public TreeObject[] getCheckedElements() {
		return this.checkedElements;
	}

	/**
	 * @return the printJob
	 */
	public PrintJob getPrintJob() {
		return this.printJob;
	}

}
