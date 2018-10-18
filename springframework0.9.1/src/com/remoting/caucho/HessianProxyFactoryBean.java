package com.remoting.caucho;

import java.lang.reflect.UndeclaredThrowableException;
import java.net.MalformedURLException;

import com.aop.framework.ProxyFactory;
import com.caucho.hessian.client.HessianProxyFactory;
import com.caucho.hessian.client.HessianRuntimeException;
import com.remoting.support.AuthorizableRemoteProxyFactoryBean;
import org.aopalliance.intercept.MethodInvocation;
import org.aopalliance.intercept.MethodInterceptor;

import com.remoting.RemoteAccessException;

/**
 * Factory bean for Hessian proxies. Behaves like the proxied service when
 * used as bean reference, exposing the specified service interface.
 * The service URL must be an HTTP URL exposing a Hessian service.
 * Supports authentication via username and password.
 *
 * <p>Hessian is a slim, binary RPC protocol.
 * For information on Hessian, see the
 * <a href="http://www.caucho.com/hessian">Hessian website</a>
 *
 * <p>Note: Hessian services accessed with this proxy factory do not have to be
 * exported via HessianServiceExporter, as there isn't any special handling involved.
 *
 * @author Juergen Hoeller
 * @since 13.05.2003
 * @see HessianServiceExporter
 */
public class HessianProxyFactoryBean extends AuthorizableRemoteProxyFactoryBean {

	protected Object createProxy() throws MalformedURLException {
		HessianProxyFactory proxyFactory = new HessianProxyFactory();
		proxyFactory.setUser(getUsername());
		proxyFactory.setPassword(getPassword());
		Object source = proxyFactory.create(getServiceInterface(), getServiceUrl());

		// Create AOP interceptor wrapping source
		ProxyFactory pf = new ProxyFactory(source);
		pf.addInterceptor(0, new MethodInterceptor() {
			public Object invoke(MethodInvocation invocation) throws Throwable {
				try {
					return invocation.proceed();
				}
				catch (HessianRuntimeException ex) {
					Throwable rootCause = (ex.getRootCause() != null) ? ex.getRootCause() : ex;
					throw new RemoteAccessException("Error on remote access", rootCause);
				}
				catch (UndeclaredThrowableException ex) {
					throw new RemoteAccessException("Error on remote access", ex.getUndeclaredThrowable());
				}
			}
		});
		return pf.getProxy();
	}

}
