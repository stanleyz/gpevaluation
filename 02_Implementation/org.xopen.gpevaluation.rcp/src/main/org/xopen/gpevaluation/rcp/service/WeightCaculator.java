/*
 * All Rights Reserved. 
 */
package org.xopen.gpevaluation.rcp.service;

import java.util.HashMap;

import org.apache.commons.math.linear.Array2DRowRealMatrix;
import org.apache.commons.math.linear.RealMatrix;

/**
 * This class is ...
 * 
 * @author <a href="mailto:patent@nuaa.edu.cn">郭剑坤</a>
 */
public class WeightCaculator {
	/**
	 * 得到相对重要度
	 * 
	 * @param importance
	 *            相对权重数组
	 * @return 相对重要度
	 */
	public static double[] getRelatedWeight(final double[][] importance) {
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

		return weight;
	}

	/**
	 * 计算特征矩阵的最大特征根
	 * 
	 * @param importance
	 *            相对权重数组
	 * @param weight
	 *            相对重要度数组
	 * @return 最大特征根
	 */
	public static double getMaxSignature(final double[][] importance,
			final double[] weight) {
		final double[][] w = new double[weight.length][1];
		double maxSignature = 0d;
		final int length = weight.length;

		for (int i = 0; i < weight.length; i++) {
			w[i][0] = weight[i];
		}

		RealMatrix importanceMatrix = new Array2DRowRealMatrix(importance);
		RealMatrix weightMatrix = new Array2DRowRealMatrix(w);
		final double[][] result = importanceMatrix.multiply(weightMatrix)
				.getData();

		for (int i = 0; i < result.length; i++) {
			maxSignature += result[i][0] / (length * weight[i]);
		}

		return maxSignature;
	}

	/**
	 * 一致性检验
	 * 
	 * 如果一致性指标C.R.(Consistency Ratio)等于1的时候怎么办?
	 * 
	 * @param maxSignature
	 *            矩阵最大特征根
	 * @param n
	 *            矩阵阶数
	 * @param ri
	 *            查表得到的平均一致性指标RI(random index)
	 * @return 是否符合一致性要求.
	 */
	public static boolean checkConsistency(final double maxSignature,
			final int n, final double ri) {
		final double cr = ((maxSignature - n) / (n - 1)) / ri;
		return cr < 0.1 ? true : false;
	}

	public static boolean checkConsistency(final double maxSignature,
			final int n) {
		final double cr = ((maxSignature - n) / (n - 1)) / getRI(n);
		return cr < 0.1 ? true : false;
	}

	public static double getRI(final int n) {
		HashMap<Integer, Double> riTable = new HashMap<Integer, Double>();
		riTable.put(3, 0.58);
		riTable.put(4, 0.90);
		riTable.put(5, 1.12);
		riTable.put(6, 1.24);
		riTable.put(7, 1.32);
		riTable.put(8, 1.41);
		riTable.put(9, 1.45);
		riTable.put(10, 1.49);
		riTable.put(11, 1.52);
		riTable.put(12, 1.54);
		riTable.put(13, 1.56);
		riTable.put(14, 1.58);
		riTable.put(15, 1.59);

		return riTable.get(n);
	}
}
