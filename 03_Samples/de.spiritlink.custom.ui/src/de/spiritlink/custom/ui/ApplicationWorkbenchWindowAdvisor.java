package de.spiritlink.custom.ui;

import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.CoolBar;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import de.spiritlink.custom.ui.views.AboutAction;
import de.spiritlink.custom.ui.views.DummyAction;


public class ApplicationWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor {

    private Composite logo;

    private Control toolbar;

    private Control page;

    private static Image bannerright = AbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "icons/richclient.jpg").createImage(); //$NON-NLS-1$
    private static Image bannerfill = AbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "icons/fill.jpg").createImage(); //$NON-NLS-1$
    private static Image bannerleft = AbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "icons/left.jpg").createImage(); //$NON-NLS-1$
    private static Image bannerabout = AbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "icons/alt_window_16.gif").createImage(); //$NON-NLS-1$

    
	public ApplicationWorkbenchWindowAdvisor(final IWorkbenchWindowConfigurer configurer) {
		super(configurer);
	}

	@Override
    public ActionBarAdvisor createActionBarAdvisor(
			final IActionBarConfigurer configurer) {
		return new ApplicationActionBarAdvisor(configurer);
	}

	@Override
    public void preWindowOpen() {
        final IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
        configurer.setInitialSize(new Point(1024, 600));
        configurer.setShowCoolBar(false);
        configurer.setShowStatusLine(true);
        configurer.setShowProgressIndicator(true);
        configurer.setTitle("My customized RCP Product"); //$NON-NLS-1$
        configurer.getWorkbenchConfigurer().setSaveAndRestore(true);
	}
    
     /* (non-Javadoc)
     * @see org.eclipse.ui.application.WorkbenchWindowAdvisor#createWindowContents(org.eclipse.swt.widgets.Shell)
     */
    @Override
    public void createWindowContents(final Shell shell) {
        final IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
        final Menu menu = configurer.createMenuBar();
        shell.setMenuBar(menu);
        final FormLayout layout = new FormLayout();
        layout.marginWidth = 0;
        layout.marginHeight = 0;
        shell.setLayout(layout);
        this.logo = new Composite(
                getWindowConfigurer().getWindow().getShell(), SWT.NONE);
        this.logo.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));

        final GridLayout gLayout = new GridLayout(3,false);
        gLayout.horizontalSpacing = 0;
        gLayout.marginHeight = 0;
        gLayout.marginWidth = 0;
        gLayout.marginWidth = 0;
        gLayout.verticalSpacing = 0;

        this.logo.setLayout(gLayout);

        final Label left = new Label(this.logo, SWT.NONE);
        left.setImage(bannerleft);

        GridData gd = new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false);
        left.setLayoutData(gd);

        final Composite fill = new Composite(this.logo, SWT.NONE);
        fill.setBackgroundImage(bannerfill);
        fill.setLayout(new GridLayout(1,false));

       

        gd = new GridData(SWT.BEGINNING, SWT.CENTER, true, true);
        gd.horizontalIndent = 3;
        
        // very simple implementation of a toolbar....
        final ToolBarManager tbm = new ToolBarManager(new ToolBar(fill, SWT.FLAT));
        tbm.getControl().setBackgroundImage(bannerfill);
        tbm.getControl().setLayoutData(gd);
        tbm.add(new DummyAction());
        tbm.add(new AboutAction());
        tbm.update(true);
       
        gd = new GridData(SWT.FILL, SWT.FILL, true, true);
        fill.setLayoutData(gd);

        final Label right = new Label(this.logo, SWT.NONE);
        right.setImage(bannerright);

        gd = new GridData(SWT.END, SWT.BEGINNING, false, false);
        right.setLayoutData(gd);

        this.toolbar = configurer.createCoolBarControl(shell);
        ((CoolBar) this.toolbar).setLocked(true);
        this.page = configurer.createPageComposite(shell);


        layoutNormal();
    }

    /**
     * Create the progress indicator for the receiver.
     * @param shell the parent shell
     */


    private void layoutNormal() {
        FormData data = new FormData();
        data.top = new FormAttachment(0, 5);
        data.left = new FormAttachment(0, 5);
        data.right = new FormAttachment(100, -5);
        this.logo.setLayoutData(data);
        data = new FormData();
        data.top = new FormAttachment(this.logo, 5, SWT.BOTTOM);
        data.left = new FormAttachment(0, 0);
        data.right = new FormAttachment(this.page, 0, SWT.LEFT);
        this.toolbar.setLayoutData(data);
        data = new FormData();
        data.top = new FormAttachment(this.logo, 5, SWT.BOTTOM);
        data.left = new FormAttachment(0, 5);
        data.right = new FormAttachment(100, -5);
        data.bottom = new FormAttachment(100,-5);
        this.page.setLayoutData(data);
        layout();
    }

    private void layout() {
        getWindowConfigurer().getWindow().getShell().layout(true);
        if (this.page != null) {
            ((Composite) this.page).layout(true);
        }
    }
}
