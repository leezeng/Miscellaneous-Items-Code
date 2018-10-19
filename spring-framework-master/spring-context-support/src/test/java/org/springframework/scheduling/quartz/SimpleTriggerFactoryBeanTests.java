/*
 * Copyright 2002-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.scheduling.quartz;

import org.junit.Test;
import org.quartz.SimpleTrigger;

import java.text.ParseException;

import static org.junit.Assert.*;

/**
 * @author Stephane Nicoll
 */
public class SimpleTriggerFactoryBeanTests {

	@Test
	public void createWithoutJobDetail() throws ParseException {
		SimpleTriggerFactoryBean factory = new SimpleTriggerFactoryBean();
		factory.setName("myTrigger");
		factory.setRepeatCount(5);
		factory.setRepeatInterval(1000L);
		factory.afterPropertiesSet();
		SimpleTrigger trigger = factory.getObject();
		assertEquals(5, trigger.getRepeatCount());
		assertEquals(1000L, trigger.getRepeatInterval());
	}

}
