/*
 * Copyright 2002-2012 the original author or authors.
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

package org.springframework.jms.connection;

import org.springframework.jms.JmsException;

import javax.jms.JMSException;

/**
 * Exception thrown when a synchronized local transaction failed to complete
 * (after the main transaction has already completed).
 *
 * @author Juergen Hoeller
 * @since 2.0
 * @see ConnectionFactoryUtils
 */
@SuppressWarnings("serial")
public class SynchedLocalTransactionFailedException extends JmsException {

	/**
	 * Create a new SynchedLocalTransactionFailedException.
	 * @param msg the detail message
	 * @param cause the root cause
	 */
	public SynchedLocalTransactionFailedException(String msg, JMSException cause) {
		super(msg, cause);
	}

}