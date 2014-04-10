/*
 * All Rights Reserved. 
 */
package org.xopen.gpevaluation.rcp.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.xopen.gpevaluation.rcp.util.DoubleUtil;

/**
 * This class is ...
 * 
 * @author <a href="mailto:patent@nuaa.edu.cn">郭剑坤</a>
 */
public class TestDouble {

	public static void main(String[] args) {

		System.out.println("Please input the double string: ");
		InputStreamReader input = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(input);
		while (true) {
			String a = "";
			try {
				a = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}

			System.out.println(DoubleUtil.valueOf(a));
		}

	}

}
