package com.server;

import com.server.bean.PeopleBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * Spring-测试debug
 *
 * @author CYX
 * @create 2018-10-18-15:07
 */
public class AppMainContext {

	public static void main(String[] args) {

		String appFilePath = "./conf/applicationContext01.xml";
		ApplicationContext applicationContext = new FileSystemXmlApplicationContext(appFilePath);
		PeopleBean peopleBean = (PeopleBean) applicationContext.getBean("cyxPeoPleBean");
		System.out.println(peopleBean.toString());
	}

}
