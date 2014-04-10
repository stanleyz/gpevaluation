/*******************************************************************************
 * Copyright (c) 2006 Spirit Link GmbH
 * All rights reserved.
 * 
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/
package de.spiritlink.editor.printing.action;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import de.spiritlink.editor.printing.editor.EditorContributor;
import de.spiritlink.editor.printing.editor.ExampleEditorInput;
import de.spiritlink.editor.printing.model.ExampleModel;

/**
 * 
 * @author Tom Seidel - tom.seidel@spiritlink.de
 */
public class InitWorkbenchAction extends Job {

    /**
     * @param name
     */
    public InitWorkbenchAction() {
        super("Opening Editor");
        // TODO Auto-generated constructor stub
    }
    /* (non-Javadoc)
     * @see de.spiritlink.jobs.jobinterface.ActionJobAdapter#run(org.eclipse.core.runtime.IProgressMonitor)
     */
    @Override
    public IStatus run(IProgressMonitor monitor) {
        final ExampleEditorInput input = new ExampleEditorInput(ExampleModel.MOCK);
        Display.getDefault().syncExec(new Runnable() {
            public void run() {
                try {
                    PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(input,EditorContributor.getEditorIdByClass(ExampleModel.class));
                } catch (PartInitException e) {
                    e.printStackTrace();
                }
            }
            
        });
        return Status.OK_STATUS;
    }

}
