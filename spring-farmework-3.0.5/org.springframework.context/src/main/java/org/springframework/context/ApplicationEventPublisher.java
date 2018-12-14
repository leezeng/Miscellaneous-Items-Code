/*
 * Copyright 2002-2005 the original author or authors.
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

package org.springframework.context;

/**
 * Interface that encapsulates event publication functionality.
 * Serves as super-interface for ApplicationContext.
 * <p>
 * 封装事件发布功能的接口。 用作ApplicationContext的超级接口。
 *
 * @author Juergen Hoeller
 * @see ApplicationContext
 * @see ApplicationEventPublisherAware
 * @see org.springframework.context.ApplicationEvent
 * @see org.springframework.context.event.EventPublicationInterceptor
 * @since 1.1.1
 */
public interface ApplicationEventPublisher {

    /**
     * Notify all listeners registered with this application of an application
     * event. Events may be framework events (such as RequestHandledEvent)
     * or application-specific events.
     * <p>
     * 通知在此应用程序中注册的应用程序事件的所有侦听器。
     * 事件可以是框架事件（例如RequestHandledEvent）或特定于应用程序的事件。
     *
     * @param event the event to publish 要发布的事件
     * @see org.springframework.web.context.support.RequestHandledEvent
     */
    void publishEvent(ApplicationEvent event);

}
