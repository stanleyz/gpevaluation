/*******************************************************************************
 * Copyright (c) 2000, 2003 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipsercp.hyperbola.actions;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;

public class ProgressAction extends Action implements
		IWorkbenchWindowActionDelegate {

	private IWorkbenchWindow window;

	public ProgressAction() {
	}

	public void run() {
		// Busy cursor
		BusyIndicator.showWhile(window.getShell().getDisplay(), new Runnable() {
			public void run() {
				sleep(2000);
			}
		});
		// Progress dialog
		ProgressMonitorDialog pd = new ProgressMonitorDialog(window.getShell());
		try {
			pd.run(true, true, new IRunnableWithProgress() {
				public void run(IProgressMonitor monitor) {
					monitor.beginTask("Long running action", 100);
					for (int i = 0; i < 10; i++) {
						if (monitor.isCanceled())
							return;
						monitor.subTask("working " + i);
						monitor.worked(10);
						sleep(1000);
					}
					monitor.done();
				}
			});
		} catch (InvocationTargetException e) {
		} catch (InterruptedException e) {
		}

		// Progress Service
		try {
			PlatformUI.getWorkbench().getProgressService().busyCursorWhile(
					new IRunnableWithProgress() {
						public void run(IProgressMonitor monitor) {
							monitor.beginTask("Long running action", 100);
							for (int i = 0; i < 10; i++) {
								if (monitor.isCanceled())
									return;
								monitor.subTask("working on step " + i);
								monitor.worked(10);
								sleep(1000);
							}
							monitor.done();
						}
					});
		} catch (InvocationTargetException e1) {
		} catch (InterruptedException e1) {
		}

		// Job
		Job job = new Job("long running action") {
			protected org.eclipse.core.runtime.IStatus run(
					IProgressMonitor monitor) {
				monitor.beginTask("Long running action", 100);
				for (int i = 0; i < 10; i++) {
					if (monitor.isCanceled())
						return Status.CANCEL_STATUS;
					monitor.subTask("working on step " + i);
					monitor.worked(10);
					ProgressAction.this.sleep(1000);
				}
				monitor.done();
				return Status.OK_STATUS;
			}
		};
		job.setUser(true);
		job.schedule();
	}

	private void sleep(long mills) {
		try {
			Thread.sleep(mills);
		} catch (InterruptedException e) {
		}
	}

	public void dispose() {
	}

	public void init(IWorkbenchWindow window) {
		this.window = window;
	}

	public void run(IAction action) {
		run();
	}

	public void selectionChanged(IAction action, ISelection selection) {
	}
}
