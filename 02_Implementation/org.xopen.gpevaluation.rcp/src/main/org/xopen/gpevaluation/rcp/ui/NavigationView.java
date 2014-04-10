package org.xopen.gpevaluation.rcp.ui;

import org.apache.commons.configuration.ConfigurationException;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.NotEnabledException;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.part.ViewPart;
import org.xopen.gpevaluation.rcp.handler.CommandIds;
import org.xopen.gpevaluation.rcp.model.EvaluationSchemes;
import org.xopen.gpevaluation.rcp.provider.MasterContentProvider;
import org.xopen.gpevaluation.rcp.provider.MasterLabelProvider;

public class NavigationView extends ViewPart {
	public static final String ID = ViewIds.NAVIGATION_VIEW;

	private TreeViewer viewer;

	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */
	public void createPartControl(Composite parent) {
		viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL
				| SWT.V_SCROLL);
		viewer.setContentProvider(new MasterContentProvider());
		viewer.setLabelProvider(new MasterLabelProvider());
		try {
			viewer.setInput(EvaluationSchemes.getInstance().getIndustries());
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
		getSite().setSelectionProvider(viewer);

		hookDoubleClickCommand();
	}

	private void hookDoubleClickCommand() {
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				IHandlerService hanlderService = (IHandlerService) getSite()
						.getService(IHandlerService.class);
				try {
					hanlderService.executeCommand(CommandIds.CALL_EDITOR, null);
				} catch (ExecutionException e) {
					e.printStackTrace();
				} catch (NotDefinedException e) {
					e.printStackTrace();
				} catch (NotEnabledException e) {
					e.printStackTrace();
				} catch (NotHandledException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		viewer.getControl().setFocus();
	}

	public TreeViewer getViewer() {
		return viewer;
	}
}