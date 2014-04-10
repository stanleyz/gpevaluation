/*
 * Xopen, All Rights Reserved. 
 */
package org.xopen.gpevaluation.rcp.test;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.math.linear.Array2DRowRealMatrix;
import org.apache.commons.math.linear.RealMatrix;
import org.eclipse.nebula.widgets.grid.Grid;
import org.eclipse.nebula.widgets.grid.GridColumn;
import org.eclipse.nebula.widgets.grid.GridEditor;
import org.eclipse.nebula.widgets.grid.GridItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;

/**
 * This class is ...
 * 
 * @author <a href="mailto:phinux.zx.zhang@gmail.com">Phinux Zhang</a>
 */
public class TestCommon {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String[] test = { "1", getName(), "3" };
		System.out.println(StringUtils.join(test, "\n"));
		StringBuilder stringBuilder = new StringBuilder("aa bb");
		stringBuilder.append("haha");
		System.out.println(stringBuilder.toString());

		int[][] a = { { 1, 2, 3 }, { 1, 2, 3 }, { 1, 2, 3 } };
		System.out.println(a.length);

		System.out.println(Math.pow(16, 1d / 4));

		double[][] w1 = { { 1d, 5d, 3d }, { 1d / 5, 1d, 1d / 3 },
				{ 1d / 3, 3d, 1d } };
		RealMatrix m = new Array2DRowRealMatrix(w1);
		double[][] w2 = { { 0.637d }, { 0.106d }, { 0.258d } };
		RealMatrix n = new Array2DRowRealMatrix(w2);
		RealMatrix p = m.multiply(n);
		double[][] r = p.getData();
		System.out.println("Column count: " + r.length);
		System.out.println("Row: " + p.getRowDimension());

		for (int i = 0; i < r.length; i++) {
			System.out.println(r[i][0]);
		}

		double[] b = getRelatedWeight(w1);

		for (int i = 0; i < b.length; i++) {
			System.out.println(b[i]);
		}

		double max=getMaxSignature(w1, b);
		System.out.println(max);
		
		boolean e=checkConsistency(max, 3, 0.58);
		System.out.println(e);
		
		String f="1/10";
		System.out.println(Double.valueOf(f));
	}

	public static double[] getRelatedWeight(double[][] importance) {
		final int length = importance.length;
		final double[] weight = new double[length];
		double sum = 0;

		for (int i = 0; i < length; i++) {
			/*
			 * 赋初始值为一,因为下面要用到连乘,而且相对权重一定大于0;
			 */
			if (weight[i] == 0) {
				weight[i] = 1;
			}

			/*
			 * 开始得到各列的乘积
			 */
			for (int j = 0; j < length; j++) {
				weight[i] *= importance[i][j];
			}

			// 得到方根
			weight[i] = Math.pow(weight[i], 1d / length);
			sum += weight[i];
		}

		// 进行归一化处理
		for (int i = 0; i < length; i++) {
			weight[i] = weight[i] / sum;
		}

		RealMatrix importanceMatrix = new Array2DRowRealMatrix(importance);
		RealMatrix weightMatrix = new Array2DRowRealMatrix(weight);
		double[][] result = importanceMatrix.multiply(weightMatrix).getData();
		for (int i = 0; i < result.length; i++) {
			System.out.println(result[i][0]);
		}

		return weight;
	}

	public static double getMaxSignature(double[][] importance, double[] weight) {
		final double[][] w = new double[3][1];
		double maxSignature=0d;
		int length=weight.length;
		
		for (int i = 0; i < weight.length; i++) {
			w[i][0] = weight[i];
		}

		RealMatrix importanceMatrix = new Array2DRowRealMatrix(importance);
		RealMatrix weightMatrix = new Array2DRowRealMatrix(w);
		double[][] result = importanceMatrix.multiply(weightMatrix).getData();
		
		for (int i = 0; i < result.length; i++) {
			maxSignature += result[i][0]/(length*weight[i]);
			System.out.println(result[i][0]);
		}
		
		return maxSignature;
	}
	
	public static boolean checkConsistency(final double maxSignature,
			final int n, final double ri) {
		final double cr = ((maxSignature - n) / (n - 1)) / ri;
		System.out.println(cr);
		return cr < 0.1 ? true : false;
	}

	public void createEditor() {

		final Grid grid = new Grid(null, SWT.BORDER);
		grid.setLinesVisible(true);
		for (int i = 0; i < 3; i++) {
			GridColumn column = new GridColumn(grid, SWT.NONE);
			column.setWidth(100);
		}
		GridItem[] items = grid.getItems();
		for (int i = 0; i < items.length; i++) {
			GridEditor editor = new GridEditor(grid);
			CCombo combo = new CCombo(grid, SWT.NONE);
			combo.setText("CCombo");
			combo.add("item 1");
			combo.add("item 2");
			editor.minimumWidth = 50;
			editor.grabHorizontal = true;
			editor.setEditor(combo, items[i], 0);

			editor = new GridEditor(grid);
			Text text = new Text(grid, SWT.NONE);
			text.setText("Text");
			editor.grabHorizontal = true;
			editor.setEditor(text, items[i], 1);

			editor = new GridEditor(grid);
			Button button = new Button(grid, SWT.CHECK);
			button.pack();
			editor.minimumWidth = button.getSize().x;
			editor.horizontalAlignment = SWT.LEFT;
			editor.setEditor(button, items[i], 2);
		}
	}

	private static String getName() {
		return "Name";
	}
}
