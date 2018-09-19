package com.quick.druid.config.mybatis;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author vector
 * @Data 2018/9/19 0019
 * @Description SpringBoot中使用AOP 监控sql耗时 https://blog.csdn.net/Eacter/article/details/56016126
 */
@Aspect
@Component
public class MapperAspect {
	private static final Logger logger = LoggerFactory.getLogger(MapperAspect.class);

	@AfterReturning("execution(* com.mycompany.financial.nirvana..*Mapper.*(..))")
	public void logServiceAccess(JoinPoint joinPoint) {
		logger.info("Completed: " + joinPoint);
	}


	/**
	 * 监控com.quick.druid.mapper..*Mapper包及其子包的所有public方法
	 */
	@Pointcut("execution(* com.quick.druid.mapper..*Mapper.*(..))")
	private void pointCutMethod() {
	}

	/**
	 * 声明环绕通知
	 * @param pjp
	 * @return
	 * @throws Throwable
	 */
	@Around("pointCutMethod()")
	public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
		long begin = System.nanoTime();
		Object obj = pjp.proceed();
		long end = System.nanoTime();

		logger.info("调用Mapper方法：{}，\n参数：{}，\n执行耗时：{}纳秒，\r\n耗时：{}毫秒，\r\n耗时：{}秒",
				pjp.getSignature().toString(), Arrays.toString(pjp.getArgs()),
				(end - begin), (end - begin) / 1000000,(end - begin) / 1000000000);
		return obj;
	}
}
