/*******************************************************************************
 * Copyright (c) 2006 Spirit Link GmbH
 * All rights reserved.
 * 
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/
package de.spiritlink.editor.printing.action;

import java.lang.reflect.InvocationTargetException;

import net.sf.paperclips.PaperClips;
import net.sf.paperclips.PrintJob;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.SWT;
import org.eclipse.swt.printing.PrintDialog;
import org.eclipse.swt.printing.PrinterData;
import org.eclipse.swt.widgets.Display;

/**
 * 
 * @author Tom Seidel - tom.seidel@spiritlink.de
 */
public class PrintingJob implements IRunnableWithProgress {

	private PrintJob jobDelegate = null;

	private boolean canceled = false;

	/**
	 * @param jobDelegate
	 */
	public PrintingJob(PrintJob jobDelegate) {
		this.jobDelegate = jobDelegate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.operation.IRunnableWithProgress#run(org.eclipse.core
	 * .runtime.IProgressMonitor)
	 */
	public void run(IProgressMonitor monitor) throws InvocationTargetException,
			InterruptedException {
		monitor.beginTask("Printing", IProgressMonitor.UNKNOWN);
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				PrintDialog dialog = new PrintDialog(Display.getDefault()
						.getActiveShell(), SWT.NONE);
				PrinterData printerData = dialog.open();
				if (printerData != null) {
					PaperClips.print(PrintingJob.this.jobDelegate, printerData);
				} else {
					canceled = true;
				}
			}

		});

		if (this.canceled) {
			throw new InterruptedException();
		}

	}

}
