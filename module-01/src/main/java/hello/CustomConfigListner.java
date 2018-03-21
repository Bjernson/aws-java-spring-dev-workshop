package hello;

import java.util.Properties;

import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;


public class CustomConfigListner implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {
	
	
	// DO !! overide this method with your Parameter Store, refer ParameterStoreTest.java and 
	@Override
	public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
	    ConfigurableEnvironment environment = event.getEnvironment();
	    Properties props = new Properties();
	    props.put("spring.datasource.password", "12345678");
	    environment.getPropertySources().addFirst(new PropertiesPropertySource("myProps", props));
	 }
	
}