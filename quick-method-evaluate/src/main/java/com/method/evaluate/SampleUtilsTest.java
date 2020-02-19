package com.method.evaluate;

import com.github.houbb.junitperf.core.annotation.JunitPerfConfig;
import com.github.houbb.junitperf.core.report.impl.HtmlReporter;

import java.util.WeakHashMap;

/**
 *
 * @author wangxc
 * @date: 2019-11-16 09:43
 *
 */
public class SampleUtilsTest {


	/**
	 * 2个线程运行。
	 * 准备时间：1000ms
	 * 运行时间: 2000ms
	 * @throws InterruptedException if any
	 */
	@JunitPerfConfig(duration = 2000)
	public void junitPerfConfigTest() throws InterruptedException {
		System.out.println("junitPerfConfigTest");
		Thread.sleep(200);
	}

	@JunitPerfConfig(warmUp = 1000, duration = 5000, reporter = {HtmlReporter.class})
	public void print1Test() {
		Long sum = 0L;
		for (long i = 0; i < Integer.MAX_VALUE; i++) {
			sum += i;
		}
		System.out.println(sum);
	}

	@JunitPerfConfig(duration = 5000)
	public void print2Test() {
		long sum = 0L;
		for (long i = 0; i < Integer.MAX_VALUE; i++) {
			sum += i;
		}
		System.out.println(sum);
	}
}
