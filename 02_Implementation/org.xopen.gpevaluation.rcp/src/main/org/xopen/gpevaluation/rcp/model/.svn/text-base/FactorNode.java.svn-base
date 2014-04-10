package org.xopen.gpevaluation.rcp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.configuration.ConfigurationException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.PlatformUI;
import org.xopen.gpevaluation.rcp.SystemConstants;
import org.xopen.gpevaluation.rcp.service.WeightCaculator;
import org.xopen.gpevaluation.rcp.ui.PromptBox;
import org.xopen.gpevaluation.rcp.ui.SchemeEditor;
import org.xopen.gpevaluation.rcp.util.DoubleUtil;
import org.xopen.gpevaluation.rcp.util.MathEvaluator;

public class FactorNode implements IFactorNode {
	private IFactorNode parent;
	private List<IFactorNode> children;
	private Factor factor;

	public FactorNode(IFactorNode _parent, Factor _factor) {
		this.parent = _parent;
		this.factor = _factor;
	}

	public FactorNode() {
		this(null, new Factor());
	}

	public List<IFactorNode> getChildren() {
		if (this.children == null) {
			try {
				this.children = Factors.getInstance().getChildren(this);
			} catch (ConfigurationException e) {
				e.printStackTrace();
			}
		}

		return this.children;
	}

	public Image getImage() {
		return null;
	}

	public String getName() {
		return factor.getName();
	}

	public IFactorNode getParent() {
		return this.parent;
	}

	public boolean hasChildren() {
		if (children == null) {
			this.getChildren();
		}
		return children.size() > 0 ? true : false;
	}

	public Factor getFactor() {
		return this.factor;
	}

	public void setFactorProperty(String _string, Object _newValue) {
		try {
			Factors.getInstance().firePropertyChange(_string, _newValue,
					this.factor);

			((SchemeEditor) PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getActivePage()
					.getActiveEditor()).setDirty(true);

			if (_string.equals(SystemConstants.FACTOR_REMOVED)) {
				this.getParent().getChildren().remove(this);
			}
		} catch (Exception e) {
			PromptBox.Prompt(Messages.FactorNode_0, Messages.FactorNode_1);
			return;
		}
	}

	public IFactorNode addChildren() {
		try {
			Factor factor = Factors.getInstance().addNode(this.getFactor());

			((SchemeEditor) PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getActivePage()
					.getActiveEditor()).setDirty(true);
			if (children == null) {
				children = new ArrayList<IFactorNode>();
			}

			IFactorNode node = new FactorNode(this, factor);
			children.add(node);

			return node;
		} catch (ConfigurationException e) {
			PromptBox.Prompt(Messages.FactorNode_2, Messages.FactorNode_3);
			return null;
		}
	}

	public void setAdaptedInstance(Object ai) {
		this.factor = (Factor) ai;
	}

	public void setParent(Object p) {
		this.parent = (IFactorNode) p;
	}

	public Object getAdaptedInstance() {
		return this.factor;
	}

	public FactorValues getFactorValue() {
		try {
			return Evaluations.getInstance().getValue(this);
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}

		return null;
	}

	public void setFactorValues(String realValue) throws ConfigurationException {
		FactorValues value = new FactorValues();
		value.setId(this.getFactor().getId());
		value.setRealValue(realValue);

		try {
			MathEvaluator m = new MathEvaluator(this.getFactor().getFunction()
					.toLowerCase());
			m.setVariable(SystemConstants.X, Double.valueOf(realValue));
			value.setScore(String.valueOf(m.getValue()));
		} catch (Exception e) {
			value.setScore(SystemConstants.ROOT);
		}

		double d = DoubleUtil.valueOf(value.getScore())
				* DoubleUtil.valueOf(this.getFactor().getRealWeight());
		value.setDefiniteScore(String.valueOf(d));

		Evaluations.getInstance().setValue(value);
		setFatherValues(this);
	}

	public void setFatherValues(IFactorNode node) throws ConfigurationException {
		if (node.getParent() != null) {
			double d = 0d;
			IFactorNode _parent = node.getParent();
			for (IFactorNode _node : _parent.getChildren()) {
				d += DoubleUtil.valueOf(_node.getFactorValue()
						.getDefiniteScore());
			}

			FactorValues value = new FactorValues();
			value.setId(_parent.getFactor().getId());
			value.setDefiniteScore(String.valueOf(d));

			Evaluations.getInstance().setValue(value);

			setFatherValues(_parent);
		}
	}

	public boolean calculateWeight() {
		Properties p;
		String s;
		int length = children.size();
		double[][] importances = new double[length][length];
		for (int i = 0; i < length; i++) {
			p = children.get(i).getFactor().getImportances();
			for (int j = 0; j < length; j++) {
				s = (String) p.get(children.get(j).getFactor().getId());
				importances[i][j] = (s == null ? DoubleUtil
						.valueOf(SystemConstants.ONE) : DoubleUtil.valueOf(s));
			}
		}

		double[] relatedWeight = WeightCaculator.getRelatedWeight(importances);
		boolean consistency = true;
		if (length > 2) {
			consistency = WeightCaculator.checkConsistency(WeightCaculator
					.getMaxSignature(importances, relatedWeight), length);
		}

		if (length < 3 || consistency) {
			Factor _factor;
			for (int i = 0; i < length; i++) {
				_factor = children.get(i).getFactor();
				try {
					Factors.getInstance().firePropertyChange(
							SystemConstants.FACTOR_WEIGHT,
							String.valueOf(relatedWeight[i]), _factor);
				} catch (Exception e) {
					MessageDialog.openError(PlatformUI.getWorkbench()
							.getActiveWorkbenchWindow().getShell(),
							Messages.FactorNode_4, Messages.FactorNode_5);
				}
			}
		}

		return consistency;
	}

	public void clearChildren() {
		children = null;
	}

	public void calculateRealWeight() {
		double d = DoubleUtil.valueOf(this.getParent().getFactor()
				.getRealWeight());
		if (d == 0)
			d = 1;
		d *= DoubleUtil.valueOf(this.getFactor().getWeight());

		try {
			Factors.getInstance().firePropertyChange(
					SystemConstants.FACTOR_DEFINITE_WEIGHT, String.valueOf(d),
					this.getFactor());
		} catch (Exception e) {
			MessageDialog.openError(PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getShell(),
					Messages.FactorNode_4, Messages.FactorNode_5);
		}

		for (int i = 0; i < children.size(); i++) {
			children.get(i).calculateRealWeight();
		}
	}

	public void setName(String name) {
	}
}
