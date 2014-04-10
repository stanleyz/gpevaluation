/*
 * All Rights Reserved. 
 */
package org.xopen.gpevaluation.rcp.model;

import java.util.Hashtable;
import java.util.Map;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.tree.DefaultExpressionEngine;
import org.apache.commons.configuration.tree.ExpressionEngine;
import org.apache.commons.configuration.tree.xpath.XPathExpressionEngine;
import org.eclipse.ui.PlatformUI;
import org.xopen.gpevaluation.rcp.file.FileFactory;
import org.xopen.gpevaluation.rcp.ui.EvaluationEditorInput;
import org.xopen.gpevaluation.rcp.ui.PromptBox;

/**
 * This class is ...
 * 
 * @author <a href="mailto:patent@nuaa.edu.cn">郭剑坤</a>
 */
public class Evaluations {
	private static final Map<String, XMLConfiguration> configs = new Hashtable<String, XMLConfiguration>();
	private static final Evaluations EVALUATIONS = new Evaluations();

	private Evaluations() {
	}

	public static Evaluations getInstance() {
		return EVALUATIONS;
	}

	public XMLConfiguration getConfig(String filePath) {
		XMLConfiguration config = configs.get(filePath);
		if (config == null) {
			try {
				config = FileFactory.getXPathConfiguation(filePath);
			} catch (ConfigurationException e) {
				PromptBox
						.Prompt(Messages.Evaluations_0, Messages.Evaluations_1);
				return null;
			}
			configs.put(filePath, config);
		}

		return config;
	}

	public void removeConfig(String filePath) {
		if (configs.get(filePath) != null) {
			configs.remove(filePath);
		}
	}

	private Configuration getConfig() throws ConfigurationException {
		String filePath = ((EvaluationEditorInput) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor()
				.getEditorInput()).getData().getEvaluation().getPath();
		return getConfig(filePath);
	}

	public void createEvaluation(IEvaluationNode evaluationNode)
			throws ConfigurationException {
		ISchemeNode schemeNode = (ISchemeNode) evaluationNode.getParent();
		XMLConfiguration schemeConfig = Factors.getInstance().getConfig(
				schemeNode.getScheme().getPath());
		ExpressionEngine engine = new DefaultExpressionEngine();
		schemeConfig.setExpressionEngine(engine);

		XMLConfiguration evalConfig = this.getConfig(evaluationNode
				.getEvaluation().getPath());
		evalConfig.setRootElementName("evaluation");
		evalConfig.addProperty("/ remark", evaluationNode.getEvaluation()
				.getRemark());
		evalConfig.setExpressionEngine(engine);
		evalConfig.addNodes("scheme", schemeConfig.configurationAt("")
				.getRootNode().getChildren());
		evalConfig.addProperty("scheme.[@id]", schemeNode.getScheme().getId());

		engine = new XPathExpressionEngine();
		schemeConfig.setExpressionEngine(engine);
		evalConfig.setExpressionEngine(engine);
		evalConfig.save();
	}

	public FactorValues getValue(IFactorNode node)
			throws ConfigurationException {
		FactorValues value = new FactorValues();
		Configuration config = this.getConfig();
		StringBuilder builder = new StringBuilder("factor[@id='");
		builder.append(node.getFactor().getId());
		builder.append("']/");

		value.setRealValue(config.getString(builder.toString() + "realvalue"));
		value.setScore(config.getString(builder.toString() + "score"));
		value.setDefiniteScore(config.getString(builder.toString()
				+ "definitescore"));

		return value;
	}

	public void setValue(FactorValues value) throws ConfigurationException {
		Configuration config = this.getConfig();
		StringBuilder builder = new StringBuilder("factor[@id='");
		builder.append(value.getId());
		builder.append("']");

		if (config.getProperty(builder.toString()) == null) {
			config.addProperty("/ factor", "");
			config.addProperty("factor[last()] @id", value.getId());
			config
					.addProperty("factor[last()] realvalue", value
							.getRealValue());
			config.addProperty("factor[last()] score", value.getScore());
			config.addProperty("factor[last()] definitescore", value
					.getDefiniteScore());
		} else {
			config.setProperty(builder.toString() + "/realvalue", value
					.getRealValue());
			config.setProperty(builder.toString() + "/score", value.getScore());
			config.setProperty(builder.toString() + "/definitescore", value
					.getDefiniteScore());
		}
	}
}