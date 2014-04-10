package org.xopen.gpevaluation.rcp.model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.lang.StringUtils;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.PlatformUI;
import org.xopen.gpevaluation.rcp.SystemConstants;
import org.xopen.gpevaluation.rcp.file.FileFactory;
import org.xopen.gpevaluation.rcp.provider.SchemeEditorInput;
import org.xopen.gpevaluation.rcp.ui.EvaluationEditorInput;
import org.xopen.gpevaluation.rcp.ui.PromptBox;
import org.xopen.gpevaluation.rcp.util.StringUtil;

public class Factors {
	private static final Map<String, XMLConfiguration> configs = new Hashtable<String, XMLConfiguration>();
	private static final Map<String, IFactorNode> roots = new HashMap<String, IFactorNode>();
	private static final Factors FACTORS = new Factors();

	private Factors() {
	}

	public static Factors getInstance() {
		return FACTORS;
	}

	public XMLConfiguration getConfig(String filePath) {
		XMLConfiguration config = configs.get(filePath);
		if (config == null) {
			try {
				config = FileFactory.getXPathConfiguation(filePath);
			} catch (ConfigurationException e) {
				PromptBox.Prompt(Messages.Factors_0, Messages.Factors_1);
				return null;
			}

			if (config != null) {
				configs.put(filePath, config);
			}
		}

		return config;
	}

	public IFactorNode getRoot(String filePath) {
		IFactorNode node = roots.get(filePath);
		if (node == null) {
			node = new FactorNode();
			roots.put(filePath, node);
		}

		return node;
	}

	public void removeConfig(String filePath) {
		if (configs.get(filePath) != null) {
			configs.remove(filePath);
		}
		if (roots.get(filePath) != null) {
			roots.get(filePath).clearChildren();
		}
	}

	private Configuration getConfig() throws ConfigurationException {
		IEditorInput editorInput = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor()
				.getEditorInput();
		String filePath = "";
		if (editorInput instanceof SchemeEditorInput) {
			filePath = ((SchemeEditorInput) editorInput).getScheme().getPath();
		} else if (editorInput instanceof EvaluationEditorInput) {
			filePath = ((ISchemeNode) ((EvaluationEditorInput) editorInput)
					.getData().getParent()).getScheme().getPath();
		}
		return getConfig(filePath);
	}

	public List<IFactorNode> getChildren(IFactorNode factorNode)
			throws ConfigurationException {
		final Configuration config = getConfig();
		final List<IFactorNode> factorNodes = new ArrayList<IFactorNode>();
		if (factorNode.getFactor().getId() == null) {
			factorNodes.add(getRoot(config, factorNode));
			return factorNodes;
		}

		final StringBuilder builder = new StringBuilder("factor[father='");
		builder.append(factorNode.getFactor().getId());
		builder.append("']/@id");
		final String[] ids = config.getStringArray(builder.toString());

		final String[] condition = { "factor[@id='", "", "']/" };
		String s;
		Object d;
		for (String id : ids) {
			Factor childFactor = new Factor();
			condition[1] = id;
			s = StringUtils.join(condition);

			d = config.getProperty(s + SystemConstants.FACTOR_REMOVED);
			if ((d != null) && (Boolean.valueOf(d.toString()) == true)) {
				continue;
			}

			childFactor.setRemoved(false);
			childFactor.setFatherId(factorNode.getFactor().getId());
			childFactor.setId(id);
			childFactor.setName(config
					.getString(s + SystemConstants.MODEL_NAME));
			childFactor.setRemark(config.getString(s
					+ SystemConstants.MODEL_REMARK));
			childFactor.setLevel(config.getString(s
					+ SystemConstants.FACTOR_LEVEL));
			childFactor.setWeight(config.getString(s
					+ SystemConstants.FACTOR_WEIGHT));
			childFactor.setRealWeight(config.getString(s
					+ SystemConstants.FACTOR_DEFINITE_WEIGHT));

			if ((d = config.getProperty(s + SystemConstants.FACTOR_IMPORTANCES)) != null) {
				if (d instanceof Properties) {
					childFactor.setImportances((Properties) d);
				} else {
					childFactor.setImportances(StringUtil.getProperties(
							(String) d, SystemConstants.SEPARATOR));
				}
			} else {
				childFactor.setImportances(new Properties());
			}

			childFactor.setType(config.getString(s
					+ SystemConstants.FACTOR_TYPE));
			childFactor.setFunction(config.getString(s
					+ SystemConstants.FACTOR_FUNCTION));
			childFactor.setThreshold(config.getString(s
					+ SystemConstants.FACTOR_THRESHOLD));

			factorNodes.add(new FactorNode(factorNode, childFactor));
		}

		return factorNodes;
	}

	private IFactorNode getRoot(Configuration config, IFactorNode factorNode) {
		Factor factor = new Factor();
		factor.setId(SystemConstants.ROOT);
		factor.setName(config.getString(SystemConstants.MODEL_NAME));
		factor.setRemark(config.getString(SystemConstants.MODEL_REMARK));
		return new FactorNode(factorNode, factor);
	}

	/**
	 * This method is used to set the variables to the specified
	 * <code>Factor</code> object.
	 * 
	 * @param propertyName
	 * @param newValue
	 * @param factor
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	private void setFactor(String propertyName, Object newValue, Factor factor)
			throws SecurityException, NoSuchMethodException,
			IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		String setMethodName = "set" + StringUtils.capitalize(propertyName);
		Method method = factor.getClass().getMethod(setMethodName,
				newValue.getClass());
		method.invoke(factor, newValue);
	}

	/**
	 * This method is used to update the data and call
	 * <code>setFactor(String propertyName, Object newValue, Factor factor)</code>
	 * to set the variables to the <code>Factor</code> object
	 * 
	 * @param propertyName
	 * @param newValue
	 * @param factor
	 * @throws ConfigurationException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws SecurityException
	 */
	public void firePropertyChange(String propertyName, Object newValue,
			Factor factor) throws ConfigurationException, SecurityException,
			IllegalArgumentException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException {
		final Configuration config = getConfig();
		if (factor.getId().equals(SystemConstants.ROOT)) {
			config.setProperty(propertyName, newValue);
		} else {
			final String[] condition = { "factor[@id='", factor.getId(), "']",
					"/" };
			final String property = StringUtils.join(condition) + propertyName;
			final String newProperty = StringUtils.join(condition, "", 0, 3)
					+ " " + propertyName;

			if (config.getProperty(property) == null) {
				config.addProperty(newProperty, newValue);
			} else {
				config.setProperty(property, newValue);
			}
		}

		setFactor(propertyName, newValue, factor);
	}

	public void firePropertyChange(Map<String, Object> property, String factorId)
			throws ConfigurationException {
		final Configuration config = getConfig();
		if (!factorId.equals(SystemConstants.ROOT)) {
			final String[] condition = { "factor[@id='", factorId, "']", "/" };
			String prefix;
			for (String s : property.keySet()) {
				prefix = StringUtils.join(condition) + s;
				final String newProperty = StringUtils
						.join(condition, "", 0, 3)
						+ " " + s;

				if (config.getProperty(prefix) == null) {
					config.addProperty(newProperty, property.get(s));
				} else {
					config.setProperty(prefix, property.get(s));
				}
			}
		} else {
			for (String s : property.keySet()) {
				config.setProperty(s, property.get(s));
			}
		}

		// setFactor(propertyName, newValue, factor);
	}

	public void firePropertyChange(String propertyName, Object newValue,
			String factorId) throws ConfigurationException, SecurityException,
			IllegalArgumentException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException {
		Factor factor = new Factor();
		factor.setId(factorId);
		this.firePropertyChange(propertyName, newValue, factor);
	}

	public Factor addNode(Factor father) throws ConfigurationException {
		final Configuration config = getConfig();
		Factor factor = new Factor();

		StringBuilder builder = new StringBuilder("factor[id='");
		builder.append(father.getId());
		builder.append("']/");
		builder.append(SystemConstants.FACTOR_LEVEL);

		if (config.getProperty(builder.toString()) != null) {
			config.clearProperty(builder.toString());
		}

		String maxId = String.valueOf((Integer.valueOf(
				config.getString("maxid")).intValue() + 1));
		config.setProperty("maxid", maxId);
		config.addProperty("/ factor", "");
		config.addProperty("factor[last()] @id", maxId);
		config.addProperty("factor[last()] name", SystemConstants.UNNAMED
				+ maxId);
		config.addProperty("factor[last()] father", father.getId());
		config.addProperty("factor[last()] level", SystemConstants.LEVEL_BASE);

		factor.setId(String.valueOf(maxId));
		factor.setName(SystemConstants.UNNAMED + maxId);
		factor.setFatherId(father.getId());
		factor.setLevel(SystemConstants.LEVEL_BASE);
		factor.setImportances(new Properties());

		return factor;
	}

	public void createSchemeFile(ISchemeNode node)
			throws ConfigurationException {
		XMLConfiguration config = (XMLConfiguration) getConfig(node.getScheme()
				.getPath());
		config.setRootElementName("scheme");
		config.addProperty("/ name", node.getName());
		config.addProperty("/ remark", node.getScheme().getRemark());
		config.addProperty("/ author", node.getScheme().getAuthor());
		config.addProperty("/ createdtime", String.valueOf(node.getScheme()
				.getCreateTime()));
		config.addProperty("/ maxid", SystemConstants.ROOT);

		config.save();
	}
}
