package com.carlosarroyoam.users.resource;

import java.util.logging.Logger;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

@Dependent
public class LoggerProducer {

	@Produces
	public Logger createLogger(InjectionPoint injectionPoint) {
		return Logger.getLogger(injectionPoint.getBean().getBeanClass().getName());
	}

}
