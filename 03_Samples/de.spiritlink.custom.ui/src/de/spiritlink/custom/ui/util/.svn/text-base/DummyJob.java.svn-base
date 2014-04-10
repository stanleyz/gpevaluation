/*******************************************************************************
 * Copyright (c) 2007 Spirit Link GmbH
 * All rights reserved.
 * 
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/
package de.spiritlink.custom.ui.util;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

/**
 * 
 * @author Tom Seidel tom.seidel@spiritlink.de
 */
public class DummyJob extends Job {

    /**
     * @param name
     */
    public DummyJob() {
        super("Dummy Job"); //$NON-NLS-1$
        // TODO Auto-generated constructor stub
    }

    /* (non-Javadoc)
     * @see org.eclipse.core.runtime.jobs.Job#run(org.eclipse.core.runtime.IProgressMonitor)
     */
    @Override
    protected IStatus run(final IProgressMonitor monitor) {
        monitor.beginTask("Doing someting", IProgressMonitor.UNKNOWN);
        try {
            Thread.sleep(5000);
        } catch (final InterruptedException e) {
            e.printStackTrace();
        }
        monitor.done();
        return Status.OK_STATUS;
    }

}
