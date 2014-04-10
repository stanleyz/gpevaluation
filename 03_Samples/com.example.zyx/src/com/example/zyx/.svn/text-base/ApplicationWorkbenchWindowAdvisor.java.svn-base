package com.example.zyx;


import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

public class ApplicationWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor {

	int Width=700;
	int Height=470;
    public ApplicationWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer) {
    	super(configurer);
    }

    public ActionBarAdvisor createActionBarAdvisor(IActionBarConfigurer configurer) {
        return new ApplicationActionBarAdvisor(configurer);
    }
    
    public void preWindowOpen() {
        IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
        configurer.setInitialSize(new Point(Width, Height));
        configurer.setShowCoolBar(false);
        configurer.setShowStatusLine(false);
        //configurer.setTitle("Frog-across-River RCP Game");
    }
}
