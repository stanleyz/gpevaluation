/*
 * All Rights Reserved. 
 */
package org.xopen.gpevaluation.rcp.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.xopen.gpevaluation.rcp.Activator;
import org.xopen.gpevaluation.rcp.model.EvaluationSchemes;
import org.xopen.gpevaluation.rcp.model.IEvaluationNode;
import org.xopen.gpevaluation.rcp.model.IIndustryNode;
import org.xopen.gpevaluation.rcp.model.ISchemeNode;
import org.xopen.gpevaluation.rcp.ui.NavigationView;
import org.xopen.gpevaluation.rcp.ui.ViewIds;

/**
 * This class is ...
 * 
 * @author <a href="mailto:patent@nuaa.edu.cn">郭剑坤</a>
 */
public class DeleteHandler extends AbstractHandler implements IHandler {

	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event);
		IWorkbenchPage page = window.getActivePage();
		NavigationView navigationView = (NavigationView) page
				.findView(ViewIds.NAVIGATION_VIEW);
		ISelection selection = navigationView.getSite().getSelectionProvider()
				.getSelection();

		if (selection == null) {
			MessageDialog.openInformation(PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getShell(),
					Messages.DeleteHandler_0, Messages.DeleteHandler_1);
		}

		if (selection != null & selection instanceof IStructuredSelection) {
			IEditorPart editor = null;
			for (Object c : ((IStructuredSelection) selection).toList()) {
				try {
					if (c instanceof IIndustryNode) {
						if (((IIndustryNode) c).getChildren().size() > 0) {
							MessageDialog.openInformation(PlatformUI
									.getWorkbench().getActiveWorkbenchWindow()
									.getShell(), Messages.DeleteHandler_2,
									Messages.DeleteHandler_3);
							continue;
						} else {
							EvaluationSchemes.getInstance().removeIndustry(
									(IIndustryNode) c);
						}

					} else if (c instanceof ISchemeNode) {
						if (MessageDialog.openConfirm(PlatformUI.getWorkbench()
								.getActiveWorkbenchWindow().getShell(),
								Messages.DeleteHandler_4,
								Messages.DeleteHandler_5)) {
							EvaluationSchemes.getInstance().removeScheme(
									(ISchemeNode) c);
						}

					} else if (c instanceof IEvaluationNode) {
						if (MessageDialog.openConfirm(PlatformUI.getWorkbench()
								.getActiveWorkbenchWindow().getShell(),
								Messages.DeleteHandler_6,
								Messages.DeleteHandler_7)) {
							EvaluationSchemes.getInstance().removeEvaluation(
									(IEvaluationNode) c);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				navigationView.getViewer().remove(c);

				if ((editor = Activator.getOpenededitors().get(c)) != null) {
					PlatformUI.getWorkbench().getActiveWorkbenchWindow()
							.getActivePage().closeEditor(editor, false);
					Activator.getOpenededitors().remove(c);
				}
			}
		}
		return null;
	}
}
