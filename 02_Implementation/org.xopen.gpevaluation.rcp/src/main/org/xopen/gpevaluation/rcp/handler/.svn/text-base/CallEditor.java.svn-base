package org.xopen.gpevaluation.rcp.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.HandlerUtil;
import org.xopen.gpevaluation.rcp.Activator;
import org.xopen.gpevaluation.rcp.model.IEvaluationNode;
import org.xopen.gpevaluation.rcp.model.IIndustryNode;
import org.xopen.gpevaluation.rcp.model.ISchemeNode;
import org.xopen.gpevaluation.rcp.model.ISimpleNode;
import org.xopen.gpevaluation.rcp.provider.SchemeEditorInput;
import org.xopen.gpevaluation.rcp.ui.EvaluationEditorInput;
import org.xopen.gpevaluation.rcp.ui.NavigationView;
import org.xopen.gpevaluation.rcp.ui.ViewIds;

public class CallEditor extends AbstractHandler implements IHandler {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.
	 * ExecutionEvent)
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event);
		IWorkbenchPage page = window.getActivePage();
		NavigationView navigationView = (NavigationView) page
				.findView(ViewIds.NAVIGATION_VIEW);
		ISelection selection = navigationView.getSite().getSelectionProvider()
				.getSelection();

		if (selection != null & selection instanceof IStructuredSelection) {
			IEditorPart editor = null;
			for (Object c : ((IStructuredSelection) selection).toList()) {
				if (navigationView.getViewer().getExpandedState(c)) {
					navigationView.getViewer().collapseToLevel(c, 1);
				} else {
					navigationView.getViewer().expandToLevel(c, 1);
				}

				if (c instanceof IIndustryNode) {
					continue;
				} else if (c instanceof ISchemeNode) {
					SchemeEditorInput input = new SchemeEditorInput(
							((ISchemeNode) c).getScheme());
					try {
						editor = page.openEditor(input, ViewIds.SCHEME_EDITOR);
					} catch (PartInitException e) {
						e.printStackTrace();
					}
				} else if (c instanceof IEvaluationNode) {
					EvaluationEditorInput input = new EvaluationEditorInput(
							(IEvaluationNode) c);
					try {
						editor = page.openEditor(input,
								ViewIds.EVALUATION_EDITOR);
					} catch (PartInitException e) {
						e.printStackTrace();
					}
				}

				Activator.getOpenededitors().put((ISimpleNode) c, editor);
			}
		}
		return null;
	}

}
