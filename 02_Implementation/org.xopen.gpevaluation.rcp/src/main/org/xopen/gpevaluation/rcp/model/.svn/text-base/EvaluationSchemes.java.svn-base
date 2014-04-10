package org.xopen.gpevaluation.rcp.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.PlatformUI;
import org.xopen.gpevaluation.rcp.SystemConstants;
import org.xopen.gpevaluation.rcp.file.FileFactory;

public class EvaluationSchemes {
	private Configuration schemeListConfig = null;
	private Configuration dataDirConfig = null;

	private static EvaluationSchemes schemes = null;

	private EvaluationSchemes() throws ConfigurationException {
		schemeListConfig = FileFactory
				.getXPathConfiguation(SystemConstants.CONFIDIR
						+ SystemConstants.SCHEMELISTFILE);

		dataDirConfig = new PropertiesConfiguration(SystemConstants.CONFIDIR
				+ SystemConstants.DATACONF);
	}

	public static EvaluationSchemes getInstance() throws ConfigurationException {
		if (schemes == null) {
			schemes = new EvaluationSchemes();
		}
		return schemes;
	}

	public List<IIndustryNode> getIndustries() {
		List<IIndustryNode> industries = new ArrayList<IIndustryNode>();
		String[] industryIds = schemeListConfig.getStringArray("industry/@id");
		for (String id : industryIds) {
			industries.add(new IndustryNode(this.getIndustryById(id)));
		}
		return industries;
	}

	public String[] getIndustryNames() {
		return schemeListConfig.getStringArray("industry/name");
	}

	public List<ISchemeNode> getSchemes(IIndustryNode _industry)
			throws ConfigurationException {
		List<ISchemeNode> schemes = new ArrayList<ISchemeNode>();
		StringBuilder s = new StringBuilder("industry-scheme/industry[@id='");
		s.append(_industry.getIndustry().getId());
		s.append("']/scheme");
		String[] schemeIds = schemeListConfig.getStringArray(s.toString());
		EvaluationScheme scheme;
		for (String _s : schemeIds) {
			scheme = this.getSchemeById(_s);
			if (scheme == null) {
				clearScheme(_industry.getIndustry().getId(), _s);
				continue;
			}
			scheme.setIndustry(_industry.getName());
			schemes.add(new SchemeNode(_industry, scheme));
		}
		return schemes;
	}

	public void clearScheme(String _industryId, String _schemeId)
			throws ConfigurationException {
		StringBuilder builder = new StringBuilder("scheme[@id='");
		builder.append(_schemeId);
		builder.append("']");

		((XMLConfiguration) schemeListConfig).clearTree(builder.toString());

		builder.delete(0, builder.length());
		builder.append("industry-scheme/industry[@id='");
		builder.append(_industryId);
		builder.append("']/scheme");

		int i = schemeListConfig.getList(builder.toString()).indexOf(_schemeId);
		builder.append("[");
		builder.append(i + 1);
		builder.append("]");
		((XMLConfiguration) schemeListConfig).clearProperty(builder.toString());
		((XMLConfiguration) schemeListConfig).save();
	}

	public List<IEvaluationNode> getEvaluations(ISchemeNode _scheme) {
		List<IEvaluationNode> evaluations = new ArrayList<IEvaluationNode>();
		String[] s = { "scheme[@id='", _scheme.getScheme().getId(),
				"']/evaluations/evaluation", "[@id='", "", "']/" };

		String[] evaluationIds = schemeListConfig.getStringArray(StringUtils
				.join(s, "", 0, 3)
				+ "/@id");
		Evaluation eval;
		Configuration config;
		for (String id : evaluationIds) {
			s[4] = id;
			eval = new Evaluation();
			eval.setId(id);
			eval.setName(schemeListConfig.getString(StringUtils.join(s)
					+ "name"));
			eval.setAuthor(schemeListConfig.getString(StringUtils.join(s)
					+ "author"));
			eval.setPath(schemeListConfig.getString(StringUtils.join(s)
					+ "path"));
			config = Evaluations.getInstance().getConfig(eval.getPath());
			eval.setRemark(config.getString("remark"));
			eval.setBackground(config.getString("background"));

			evaluations.add(new EvaluationNode(_scheme, eval));
		}
		return evaluations;
	}

	public Industry getIndustryById(String id) {
		StringBuilder prefix = new StringBuilder("industry[@id='");
		prefix.append(id);
		prefix.append("']/");
		String name = schemeListConfig.getString(prefix.toString() + "name");
		if (name == null) {
			return null;
		}

		Industry industry = new Industry();
		industry.setId(id);
		industry.setName(name);

		return industry;
	}

	public IIndustryNode getIndustryByName(String name) {
		StringBuilder prefix = new StringBuilder("industry[name='");
		prefix.append(name);
		prefix.append("']/");
		String id = schemeListConfig.getString(prefix.toString() + "@id");
		if (id == null) {
			return null;
		}

		Industry industry = new Industry();
		industry.setName(name);
		industry.setId(id);

		return new IndustryNode(industry);
	}

	public EvaluationScheme getSchemeById(String id) {
		StringBuilder prefix = new StringBuilder("scheme[@id='");
		prefix.append(id);
		prefix.append("']/");
		String path = schemeListConfig.getString(prefix.toString() + "path");
		if (path == null) {
			return null;
		}

		EvaluationScheme scheme = new EvaluationScheme();
		scheme.setId(id);
		scheme.setName(schemeListConfig.getString(prefix.toString() + "name"));
		scheme.setAuthor(schemeListConfig.getString(prefix.toString()
				+ "author"));
		scheme.setPath(path);
		if (!(new File(path).exists())) {
			StringBuilder builder = new StringBuilder("找不到评价系统\"");
			builder.append(scheme.getName());
			builder.append("\"的文件：");
			builder.append(new File(path).getAbsolutePath());
			MessageDialog.openError(PlatformUI.getWorkbench().getDisplay()
					.getActiveShell(), "找不到文件", builder.toString());
			return null;
		}
		Configuration config = Factors.getInstance().getConfig(path);
		scheme.setCreateTime(Long.valueOf(config.getString("createdtime")));
		scheme.setRemark(config.getString("remark"));
		scheme.setProducts(config.getString("products"));

		return scheme;
	}

	public IIndustryNode createScheme(ISchemeNode node)
			throws ConfigurationException {
		Factors.getInstance().createSchemeFile(node);

		schemeListConfig.addProperty("/ scheme", "");
		schemeListConfig.addProperty("scheme[last()] @id", node.getScheme()
				.getId());
		schemeListConfig.addProperty("scheme[last()] name", node.getName());
		schemeListConfig.addProperty("scheme[last()] author", node.getScheme()
				.getAuthor());
		schemeListConfig.addProperty("scheme[last()] path", node.getScheme()
				.getPath());

		String industryName = ((IIndustryNode) node.getParent()).getName();
		IIndustryNode industryNode = this.getIndustryByName(industryName);
		if (industryNode == null) {
			industryNode = new IndustryNode(new Industry());
			industryNode.getIndustry().setId(node.getScheme().getId());
			industryNode.getIndustry().setName(industryName);
			schemeListConfig.addProperty("/ industry", "");
			schemeListConfig.addProperty("industry[last()] @id", industryNode
					.getIndustry().getId());
			schemeListConfig.addProperty("industry[last()] name", industryName);

			try {
				schemeListConfig.addProperty("industry-scheme industry", "");
			} catch (IllegalArgumentException e) {
				schemeListConfig.addProperty("/ industry-scheme", "");
				schemeListConfig.addProperty("industry-scheme industry", "");
			}
			schemeListConfig.addProperty(
					"industry-scheme/industry[last()] @id", industryNode
							.getIndustry().getId());
		}

		StringBuilder s = new StringBuilder("industry-scheme/industry[@id='");
		s.append(industryNode.getIndustry().getId());
		s.append("'] scheme");
		schemeListConfig.addProperty(s.toString(), node.getScheme().getId());

		((XMLConfiguration) schemeListConfig).save();

		return industryNode;
	}

	public String[] getSchemeDirs() {
		return dataDirConfig.getStringArray(SystemConstants.SCHEMEDIRS);
	}

	@SuppressWarnings("unchecked")
	public ISchemeNode getScheme(ISchemeNode scheme) {
		StringBuilder s = new StringBuilder("scheme[name='");
		s.append(scheme.getName());
		s.append("'][author='");
		s.append(scheme.getScheme().getAuthor());
		s.append("']/");
		String[] schemeIds = schemeListConfig.getStringArray(s.toString()
				+ "@id");
		IIndustryNode industry = this.getIndustryByName(scheme.getScheme()
				.getIndustry());

		if (schemeIds.length == 1) {
			scheme.getScheme().setId(schemeIds[0]);
			scheme.getScheme().setPath(
					schemeListConfig.getString(s.toString() + "path"));
		} else {
			s.delete(0, s.length());
			s.append("industry-scheme/industry[@id='");
			s.append(industry.getIndustry().getId());
			s.append("']/scheme");
			List<String> includedSchemes = schemeListConfig.getList(s
					.toString());
			for (String id : schemeIds) {
				if (includedSchemes.contains(id)) {
					scheme.getScheme().setId(id);
				}
			}

			s.delete(0, s.length());
			s.append("scheme[@id='");
			s.append(scheme.getScheme().getId());
			s.append("']/");
			scheme.getScheme().setPath(
					schemeListConfig.getString(s.toString() + "path"));
		}

		scheme.setParent(industry);

		return scheme;
	}

	public IEvaluationNode createEvaluation(IEvaluationNode evaluationNode)
			throws ConfigurationException {
		ISchemeNode scheme = (ISchemeNode) evaluationNode.getParent();
		scheme = this.getScheme(scheme);
		evaluationNode.setParent(scheme);

		String[] prefix = { "scheme[@id='", scheme.getScheme().getId(), "']",
				"/", "evaluations", " ", "evaluation" };
		String s = StringUtils.join(prefix);
		try {
			schemeListConfig.addProperty(s, "");
		} catch (IllegalArgumentException e) {
			prefix[3] = " ";
			schemeListConfig
					.addProperty(StringUtils.join(prefix, "", 0, 5), "");
			schemeListConfig.addProperty(s, "");
		}
		s = s.replaceFirst(" ", "/");
		s += "[last()]";

		Evaluations.getInstance().createEvaluation(evaluationNode);

		schemeListConfig.addProperty(s + " @id", evaluationNode.getEvaluation()
				.getId());
		schemeListConfig.addProperty(s + " name", evaluationNode
				.getEvaluation().getName());
		schemeListConfig.addProperty(s + " author", evaluationNode
				.getEvaluation().getAuthor());
		schemeListConfig.addProperty(s + " path", evaluationNode
				.getEvaluation().getPath());

		((XMLConfiguration) schemeListConfig).save();

		return evaluationNode;
	}

	public void removeEvaluation(IEvaluationNode node)
			throws ConfigurationException {
		StringBuilder builder = new StringBuilder("scheme[@id='");
		builder.append(((ISchemeNode) node.getParent()).getScheme().getId());
		builder.append("']/evaluations/evaluation[@id='");
		builder.append(node.getEvaluation().getId());
		builder.append("']");

		((XMLConfiguration) schemeListConfig).clearTree(builder.toString());

		File file = new File(node.getEvaluation().getPath());
		file.delete();
		((XMLConfiguration) schemeListConfig).save();
	}

	@SuppressWarnings("unchecked")
	public void removeScheme(ISchemeNode node) throws ConfigurationException {
		StringBuilder builder = new StringBuilder("scheme[@id='");
		builder.append(node.getScheme().getId());
		builder.append("']");

		((XMLConfiguration) schemeListConfig).clearTree(builder.toString());

		(new File(node.getScheme().getPath())).delete();

		List<IEvaluationNode> nodes = node.getChildren();
		for (IEvaluationNode _node : nodes) {
			(new File(_node.getEvaluation().getPath())).delete();
		}

		builder.delete(0, builder.length());
		builder.append("industry-scheme/industry[@id='");
		builder
				.append(((IIndustryNode) node.getParent()).getIndustry()
						.getId());
		builder.append("']/scheme");

		int i = schemeListConfig.getList(builder.toString()).indexOf(
				node.getScheme().getId());
		builder.append("[");
		builder.append(i + 1);
		builder.append("]");
		((XMLConfiguration) schemeListConfig).clearProperty(builder.toString());
		((XMLConfiguration) schemeListConfig).save();
	}

	public void removeIndustry(IIndustryNode node)
			throws ConfigurationException {
		String[] s = { "industry-scheme/", "industry[@id='",
				node.getIndustry().getId(), "']" };

		((XMLConfiguration) schemeListConfig).clearTree(StringUtils.join(s, "",
				1, 4));

		((XMLConfiguration) schemeListConfig).clearTree(StringUtils.join(s));

		((XMLConfiguration) schemeListConfig).save();
	}

	public void rename(ISimpleNode node, String value)
			throws ConfigurationException {
		SimpleModel model = (SimpleModel) node.getAdaptedInstance();
		String id = model.getId();
		StringBuilder builder = new StringBuilder();
		if (node instanceof IEvaluationNode) {
			builder.delete(0, builder.length());
			builder.append("scheme[@id='");
			builder
					.append(((ISchemeNode) node.getParent()).getScheme()
							.getId());
			builder.append("']/evaluations/evaluation[@id='");
			builder.append(id);
			builder.append("']/name");
			schemeListConfig.setProperty(builder.toString(), value);
		} else if (node instanceof ISchemeNode) {
			builder.delete(0, builder.length());
			builder.append("scheme[@id='");
			builder.append(id);
			builder.append("']/name");
			schemeListConfig.setProperty(builder.toString(), value);

			Configuration config = Factors.getInstance().getConfig(
					((ISchemeNode) node).getScheme().getPath());
			config.setProperty("name", value);
			((XMLConfiguration) config).save();
		} else if (node instanceof IIndustryNode) {
			builder.delete(0, builder.length());
			builder.append("industry[@id='");
			builder.append(id);
			builder.append("']/name");
			schemeListConfig.setProperty(builder.toString(), value);
		}

		((XMLConfiguration) schemeListConfig).save();
	}
}
