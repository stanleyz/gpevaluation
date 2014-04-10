/*
 * All Rights Reserved. 
 */
package org.xopen.gpevaluation.rcp.ui;

import java.util.Calendar;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.xopen.gpevaluation.rcp.Activator;
import org.xopen.gpevaluation.rcp.SystemConstants;
import org.xopen.gpevaluation.rcp.model.Evaluation;
import org.xopen.gpevaluation.rcp.model.EvaluationNode;
import org.xopen.gpevaluation.rcp.model.EvaluationScheme;
import org.xopen.gpevaluation.rcp.model.EvaluationSchemes;
import org.xopen.gpevaluation.rcp.model.IEvaluationNode;
import org.xopen.gpevaluation.rcp.model.ISchemeNode;
import org.xopen.gpevaluation.rcp.model.SchemeNode;
import org.xopen.gpevaluation.rcp.model.Session;

/**
 * This class is ...
 * 
 * @author <a href="mailto:patent@nuaa.edu.cn">郭剑坤</a>
 */
public class CreateEvaluationWizard extends Wizard implements INewWizard {
	private CreateEvaluationPage evalPage;

	public CreateEvaluationWizard() {
		super();
	}

	public boolean performFinish() {
		Calendar date = Calendar.getInstance();
		ISchemeNode schemeNode = new SchemeNode(new EvaluationScheme());
		String[] s = StringUtils.split(evalPage.getScheme(),
				SystemConstants.DASH);
		schemeNode.getScheme().setIndustry(s[0]);
		schemeNode.getScheme().setName(s[1]);
		schemeNode.getScheme().setAuthor(s[2]);

		IEvaluationNode evaluationNode = new EvaluationNode(schemeNode,
				new Evaluation());
		evaluationNode.getEvaluation().setId(
				String.valueOf(date.getTimeInMillis()));
		evaluationNode.getEvaluation().setName(evalPage.getEvalSerial());
		StringBuilder builder = new StringBuilder(evalPage.getSaveTo());
		builder.append(date.getTimeInMillis());
		builder.append(SystemConstants.EVALUATION_EXT);
		evaluationNode.getEvaluation().setPath(builder.toString());
		evaluationNode.getEvaluation().setRemark(evalPage.getRemark());
		String author = "";
		try {
			author = Session.getInstance().getUser().getFullName();
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
		evaluationNode.getEvaluation().setAuthor(author);

		try {
			evaluationNode = EvaluationSchemes.getInstance().createEvaluation(
					evaluationNode);
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}

		schemeNode = (ISchemeNode) evaluationNode.getParent();
		IWorkbenchPage page = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();
		NavigationView navigationView = (NavigationView) page
				.findView(ViewIds.NAVIGATION_VIEW);
		navigationView.getViewer().add(schemeNode, evaluationNode);

		if (!navigationView.getViewer()
				.getExpandedState(schemeNode.getParent())) {
			navigationView.getViewer().expandToLevel(schemeNode.getParent(), 1);
		}
		if (!navigationView.getViewer().getExpandedState(schemeNode)) {
			navigationView.getViewer().expandToLevel(schemeNode, 1);
		}

		try {
			IEditorPart editor = page.openEditor(new EvaluationEditorInput(
					evaluationNode), ViewIds.EVALUATION_EDITOR);
			Activator.getOpenededitors().put(evaluationNode, editor);
		} catch (PartInitException e) {
			e.printStackTrace();
		}

		navigationView.getViewer().setSelection(
				new StructuredSelection(evaluationNode));
		return true;
	}

	public void init(IWorkbench workbench, IStructuredSelection selection) {
	}

	public void addPages() {
		evalPage = new CreateEvaluationPage();
		addPage(evalPage);
	}

}
