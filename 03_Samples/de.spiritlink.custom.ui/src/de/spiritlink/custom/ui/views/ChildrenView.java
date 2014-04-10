/*******************************************************************************
 * Copyright (c) 2007 Spirit Link GmbH
 * All rights reserved.
 * 
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/
package de.spiritlink.custom.ui.views;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.part.ViewPart;

import de.spiritlink.custom.ui.model.FolderItem;
import de.spiritlink.custom.ui.model.Item;
import de.spiritlink.custom.ui.util.UIConstants;

/**
 * 
 * @author Tom Seidel tom.seidel@spiritlink.de
 */
public class ChildrenView extends ViewPart implements ISelectionListener {

    private FormToolkit toolkit;
    private ScrolledForm form;
    private Label headerLabel;
    private TableViewer tableViewer;
    public static final String VIEW_ID = "de.spiritlink.custom.ui.views.ChildrenView"; //$NON-NLS-1$

    /* (non-Javadoc)
     * @see org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
     */
    @Override
    public void createPartControl(final Composite parent) {
        final Composite comp = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout(1, false);
        layout.horizontalSpacing = 0;
        layout.verticalSpacing = 0;
        layout.marginHeight = 0;
        layout.marginWidth = 0;
        comp.setLayout(layout);





        this.toolkit = new FormToolkit(UIConstants.FORM_COLOR(comp.getDisplay()));
        this.form = this.toolkit.createScrolledForm(comp);
        //layout = new GridLayout(1, false);
        this.form.getBody().setLayout(layout);
        this.form.setText("Subelements");
        //this.form.setImage(this.image.createImage());
        this.toolkit.decorateFormHeading(this.form.getForm());
        GridData gd = new GridData(SWT.FILL, SWT.FILL, true ,true);
        this.form.setLayoutData(gd);

        final Composite headerComp = new Composite(this.form.getBody(), SWT.NONE);
        layout = new GridLayout(1,false);

        headerComp.setLayout(layout);
        gd = new GridData(SWT.FILL, SWT.BEGINNING, true ,false);
        headerComp.setLayoutData(gd);

        this.headerLabel = new Label(headerComp, SWT.NONE);
        this.headerLabel.setForeground(UIConstants.ORANGE_COLOR);
        this.headerLabel.setFont(UIConstants.SUBHEADER_FONT);
        this.headerLabel.setText("View Children");
        gd = new GridData(SWT.FILL, SWT.FILL, true ,true);


        this.headerLabel.setLayoutData(gd);

        final Composite client = this.toolkit.createComposite(this.form.getBody(), SWT.WRAP);
        layout = new GridLayout(1, false);
        client.setLayout(layout);
        this.toolkit.paintBordersFor(client);
        gd = new GridData(SWT.FILL, SWT.FILL, true, true);
        client.setLayoutData(gd);


        final Table resultTable = new Table(client, SWT.BORDER | SWT.SINGLE | SWT.FULL_SELECTION);
        resultTable.setLinesVisible(true);
        resultTable.setHeaderVisible(true);
        resultTable.setLayoutData(gd);

        final TableLayout tableLayout = new TableLayout();
        tableLayout.addColumnData(new ColumnWeightData(33,200));
        tableLayout.addColumnData(new ColumnWeightData(33,200));
        tableLayout.addColumnData(new ColumnWeightData(33,200));


        resultTable.setLayout(tableLayout);

        final TableColumn tc0 = new TableColumn(resultTable, SWT.NONE);
        tc0.setMoveable(true);
        tc0.setResizable(true);
        tc0.setText("Id");


        final TableColumn tc1 = new TableColumn(resultTable, SWT.NONE);
        tc1.setMoveable(true);
        tc1.setResizable(true);
        tc1.setText("Details 1");


        final TableColumn tc2 = new TableColumn(resultTable, SWT.NONE);
        tc2.setMoveable(true);
        tc2.setResizable(true);
        tc2.setText("Details 2");

        this.tableViewer = new TableViewer(resultTable);
        this.tableViewer.setUseHashlookup(true);
        this.tableViewer.setLabelProvider(new ChildrenLabelProvider());
        this.tableViewer.setContentProvider(new ArrayContentProvider());

        this.tableViewer.setSorter(new ViewerSorter() {
            /* (non-Javadoc)
             * @see org.eclipse.jface.viewers.ViewerComparator#compare(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
             */
            @Override
            public int compare(final Viewer viewer, final Object e1, final Object e2) {
                if (e1 instanceof Item) {
                    return -1;
                }
                return super.compare(viewer, e1, e2);
            }
        });

        this.form.reflow(true);
        getViewSite().setSelectionProvider(this.tableViewer);
        PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService().addSelectionListener(this);
    }

    /* (non-Javadoc)
     * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
     */
    @Override
    public void setFocus() {
        // TODO Auto-generated method stub

    }


    /* (non-Javadoc)
     * @see org.eclipse.ui.ISelectionListener#selectionChanged(org.eclipse.ui.IWorkbenchPart, org.eclipse.jface.viewers.ISelection)
     */
    public void selectionChanged(final IWorkbenchPart part, final ISelection selection) {
        if (part != this) {
            if (!selection.isEmpty()) {
                this.headerLabel.setText("View Children of " + ((Item) ((IStructuredSelection) selection).getFirstElement()).getId());
                this.tableViewer.setInput(((FolderItem) ((IStructuredSelection) selection).getFirstElement()).getChildren());
            } else {
                this.tableViewer.setInput(null);
                this.headerLabel.setText("Noting selected");
            }
        }

    }

}
