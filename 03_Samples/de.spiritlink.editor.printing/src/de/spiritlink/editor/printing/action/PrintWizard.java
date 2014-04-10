/*******************************************************************************
 * Copyright (c) 2006 Spirit Link GmbH
 * All rights reserved.
 * 
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/
package de.spiritlink.editor.printing.action;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.wizard.Wizard;

import de.spiritlink.editor.printing.model.ExampleModel;

/**
 * 
 * @author Tom Seidel - tom.seidel@spiritlink.de
 */
public class PrintWizard extends Wizard {
    
    private PrintWizardPrefPage page1 = null;
    /* (non-Javadoc)
     * @see org.eclipse.jface.wizard.Wizard#performFinish()
     */
    @Override
    public boolean performFinish() {
        boolean returnValue = true;
        PrintingJob job = new PrintingJob(page1.getPrintJob());
        try {
            getContainer().run(false,false,job);
        } catch (InvocationTargetException e) {
            returnValue = false;
            ErrorDialog.openError(
                    getShell(), "Error", "Error while printing",null) ;
        } catch (InterruptedException e) {
            // Printer-dialog was canceld
            returnValue = false;
        }
        return returnValue;
    }
    
    public PrintWizard(ExampleModel model) {
        super();
        this.page1 = new PrintWizardPrefPage(model);
        setNeedsProgressMonitor(true);
        
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.jface.wizard.Wizard#canFinish()
     */
    @Override
    public boolean canFinish() {
        return this.page1.isPageComplete();
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.jface.wizard.Wizard#addPages()
     */
    @Override
    public void addPages() {
        addPage(this.page1);
    }


}
