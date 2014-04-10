package de.spiritlink.custom.ui.views;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.internal.WorkbenchWindow;
import org.eclipse.ui.internal.progress.ProgressRegion;
import org.eclipse.ui.part.ViewPart;

import de.spiritlink.custom.ui.Activator;
import de.spiritlink.custom.ui.model.FolderItem;
import de.spiritlink.custom.ui.util.UIConstants;


public class BrowseView extends ViewPart {
	public static final String VIEW_ID = "de.spiritlink.custom.ui.view"; //$NON-NLS-1$

    
    TreeViewer viewer;
   
    private FormToolkit toolkit;

    private ScrolledForm form;

    private ProgressRegion progressRegion;

	
	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
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
        layout = new GridLayout(1, false);
        this.form.getBody().setLayout(layout);

        this.form.setText("Overview"); //$NON-NLS-1$
        this.toolkit.decorateFormHeading(this.form.getForm());
        this.form.getToolBarManager().add(new DummyJobAction());
        this.form.getToolBarManager().update(true);
        GridData gd = new GridData(SWT.FILL, SWT.FILL, true ,true);

        final Composite client = this.toolkit.createComposite(this.form.getBody(), SWT.WRAP);
        layout = new GridLayout(1, false);
        layout.horizontalSpacing = 0;
        layout.verticalSpacing = 0;
        layout.marginHeight = 0;
        layout.marginWidth = 0;
        client.setLayout(layout);
        client.setLayoutData(gd);
        this.form.setLayout(layout);

        
       
        
        final Tree browseTree = new Tree(client, SWT.SINGLE |SWT.H_SCROLL | SWT.V_SCROLL);
        gd = new GridData(SWT.FILL, SWT.FILL, true, true);
        browseTree.setLayoutData(gd);
        this.viewer = new TreeViewer(browseTree);

       
        this.viewer.setUseHashlookup(true);
        this.viewer.setContentProvider(new BrowseContentProvider());
        this.viewer.setLabelProvider(new BrowseLabelProvider());
        this.viewer.addFilter(new ViewerFilter() {
            @Override
            public boolean select(final Viewer viewer, final Object parentElement,
                    final Object element) {
                return (element instanceof FolderItem);
            }
        });
        this.viewer.setInput(Activator.getDefault().getModel().getChildren());
        getViewSite().setSelectionProvider(this.viewer);
        this.form.setLayoutData(gd);
        createProgressIndicator(comp);
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	@Override
    public void setFocus() {
		this.viewer.getControl().setFocus();
	}
    
     private void createProgressIndicator(final Composite parent) {
            
            final Composite comp = new Composite(parent, SWT.NONE);
            comp.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WIDGET_NORMAL_SHADOW));
            final GridData gd = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
            gd.heightHint = 1;
            comp.setLayoutData(gd);
            
            final WorkbenchWindow window = (WorkbenchWindow) PlatformUI.getWorkbench().getActiveWorkbenchWindow();
            this.progressRegion = new ProgressRegion();
            this.progressRegion.createContents(parent, window);
            this.progressRegion.getControl().setVisible(true);
            this.progressRegion.getControl().setLayoutData(new GridData(SWT.BEGINNING, SWT.FILL, true, false));
            
        }
}