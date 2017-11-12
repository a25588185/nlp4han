package com.lc.nlp4han.dependency;

import java.io.OutputStream;
import java.io.PrintStream;

/**
 * 打印错误信息类 
 * @author 王馨苇
 *
 */
public class DependencyParseErrorPrinter extends DependencyParseEvaluateMonitor{
	private PrintStream errOut;
	private int countErrorRes = 0;
	
	public DependencyParseErrorPrinter(OutputStream out){
		errOut = new PrintStream(out);
	}

	/**
	 * 样本和预测的不一样的时候进行输出
	 * @param reference 参考的样本
	 * @param predict 预测的结果
	 */
	@Override
	public void missclassified(DependencySample reference, DependencySample predict) {
		 errOut.println("样本的结果：");
		 errOut.println(reference.toSample());	 
		 errOut.println("预测的结果：");
		 errOut.println(predict.toSample());
		 errOut.println("错误的总数：");
		 errOut.println(countErrorRes++);
	}
}
