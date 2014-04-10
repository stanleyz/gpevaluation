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
import org.xopen.gpevaluation.rcp.model.EvaluationScheme;
import org.xopen.gpevaluation.rcp.model.EvaluationSchemes;
import org.xopen.gpevaluation.rcp.model.IIndustryNode;
import org.xopen.gpevaluation.rcp.model.ISchemeNode;
import org.xopen.gpevaluation.rcp.model.Industry;
import org.xopen.gpevaluation.rcp.model.IndustryNode;
import org.xopen.gpevaluation.rcp.model.SchemeNode;
import org.xopen.gpevaluation.rcp.model.Session;
import org.xopen.gpevaluation.rcp.provider.SchemeEditorInput;

public class CreateSchemeWizard extends Wizard implements INewWizard {
	private CreateSchemePage schemePage;

	public CreateSchemeWizard() {
		super();
	}

	public boolean performFinish() {
		Calendar date = Calendar.getInstance();
		Industry industry = new Industry();
		if (StringUtils.isNotBlank(schemePage.getIndustry())) {
			industry.setName(schemePage.getIndustry());
		} else {
			industry.setName(SystemConstants.INDUSTRY_OTHERS);
		}
		IIndustryNode industryNode = new IndustryNode(industry);

		EvaluationScheme scheme = new EvaluationScheme();
		scheme.setId(String.valueOf(date.getTimeInMillis()));
		scheme.setName(schemePage.getSchemeName());
		String author = "";
		try {
			author = Session.getInstance().getUser().getFullName();
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
		scheme.setAuthor(author);
		String filePath = schemePage.getSaveTo() + date.getTimeInMillis()
				+ SystemConstants.SCHEME_EXT;
		scheme.setPath(filePath);
		scheme.setRemark(schemePage.getRemark());
		scheme.setCreateTime(date.getTimeInMillis());
		ISchemeNode schemeNode = new SchemeNode(industryNode, scheme);
		try {
			industryNode = EvaluationSchemes.getInstance().createScheme(
					schemeNode);
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
		scheme.setIndustry(industryNode.getName());

		IWorkbenchPage page = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();
		NavigationView navigationView = (NavigationView) page
				.findView(ViewIds.NAVIGATION_VIEW);
		navigationView.getViewer().add(navigationView.getViewer().getInput(),
				industryNode);

		if (!navigationView.getViewer().getExpandedState(industryNode)) {
			navigationView.getViewer().expandToLevel(industryNode, 1);
		}

		try {
			IEditorPart editor = page.openEditor(new SchemeEditorInput(scheme),
					ViewIds.SCHEME_EDITOR);
			Activator.getOpenededitors().put(new SchemeNode(scheme), editor);
		} catch (PartInitException e) {
			e.printStackTrace();
		}

		navigationView.getViewer().setSelection(
				new StructuredSelection(schemeNode));

		return true;
	}

	public void init(IWorkbench workbench, IStructuredSelection selection) {
	}

	public void addPages() {
		schemePage = new CreateSchemePage();
		addPage(schemePage);
	}
}
