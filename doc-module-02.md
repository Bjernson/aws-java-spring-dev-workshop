## Module-02 : First use of AWS service(Parameter Store) and it's integration (time duration : 40 mins)
- This module introduce you to how to configure CustomListner for retrieving environment parameters from Parameter Store in AWS System Manager. 
- There are many environment paramenters in Spring Data applications, for example, database connection URL, database user name, password or AWS access key and secret key, and there are described in application.properties, generally. the more secured way to retrieve these information is required. 
- Start from moudle-01 and complete the codes with below information.
- module-02 is a starting points to use AWS services with AWS Java SDK.

- Create a parameters in ParameterStore on AWS
- Create a Aurora database and create user and user table

 
**Start from Module-01**
 
### 1. Configure Dev environment


##### 1. Configure AWS CLI to allow application to get access key and secret key

```
> aws configure
> AWS Access Key ID [None]: [your key]
> AWS Secret Access Key [None]: [your key]
```

##### 2. Configure ParameterStore in System Manager 
- AWS Systems Manager Parameter Store provides secure, hierarchical storage for configuration data management and secrets management. You can store data such as passwords, database strings, and license codes as parameter values.
Complete the following tasks to configure application parameters for ParameterStore (select your region, for example, us-east-1 or ap-southeast-1)

	1. Open the Amazon EC2 console at https://console.aws.amazon.com/ec2/
	2. Create parameters in ParameterStore for database URL, database username and password
	3. Specify datasource.url as "jdbc:h2:file:~/WorkshopDB"
	4. Specify datasource.username as "sa"
	5. Specify datasource.password as "12345678"

![Parameter Store](./images/module-02/paramter-store-01.png)


### 2. Change source code

Reference :

https://stackoverflow.com/questions/29072628/how-to-override-spring-boot-application-properties-programmatically)

https://stackoverflow.com/questions/33072452/log-configurationproperties-in-springboot-prior-to-startup)
	
	
##### 1. Add packages in pom.xml

```
  <dependencyManagement>
    <dependencies>
      <dependency>
        <groupId>com.amazonaws</groupId>
        <artifactId>aws-java-sdk-bom</artifactId>
        <version>1.11.289</version>
        <type>pom</type>
        <scope>import</scope>
      </dependency>
    </dependencies>
  </dependencyManagement>    
    
  <!-- AWS SDK System Manager -->  
  <dependency>
    <groupId>com.amazonaws</groupId>
    <artifactId>aws-java-sdk-ssm</artifactId>
  </dependency> 
         
```


##### 2. Configuration properties and Java class

	1. Create CustomConfigListner.java in hello package

```
package hello;

import java.util.Properties;

import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;

import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagement;
import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagementClientBuilder;
import com.amazonaws.services.simplesystemsmanagement.model.GetParameterRequest;
import com.amazonaws.services.simplesystemsmanagement.model.GetParameterResult;


public class CustomConfigListner implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {
	
	
	// DO !! overide this method with your Parameter Store, refer ParameterStoreTest.java and 
	@Override
	public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
		AWSSimpleSystemsManagement client = AWSSimpleSystemsManagementClientBuilder.defaultClient();
		GetParameterRequest parameterRequest = new GetParameterRequest();
		parameterRequest.withName("datasource.url").setWithDecryption(Boolean.valueOf(true));
		GetParameterResult parameterResult = client.getParameter(parameterRequest);
		String url = parameterResult.getParameter().getValue();

		parameterRequest.withName("datasource.username").setWithDecryption(Boolean.valueOf(true));
		parameterResult = client.getParameter(parameterRequest);
		String username = parameterResult.getParameter().getValue();	
		
		parameterRequest.withName("datasource.password").setWithDecryption(Boolean.valueOf(true));
		parameterResult = client.getParameter(parameterRequest);
		String password = parameterResult.getParameter().getValue();
//		String version = parameterResult.getParameter().getVersion().toString();
		
    ConfigurableEnvironment environment = event.getEnvironment();
    Properties props = new Properties();
    props.put("spring.datasource.url", url);
    props.put("spring.datasource.username", username);
    props.put("spring.datasource.password", password);
    environment.getPropertySources().addFirst(new PropertiesPropertySource("myProps", props));
    
    System.out.println("##### url = " + url);
    System.out.println("##### username = " + username);
    System.out.println("##### password = " + password);	
	 }
	
}

```


##### 3. Check the availability of parameters in ParameterStore
	1. Create ParameterStoreTest.java in ser/test/java
	2. Add below codes
	
```
	package hello;


import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertEquals;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;



import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagement;
import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagementClientBuilder;
import com.amazonaws.services.simplesystemsmanagement.model.GetParameterRequest;
import com.amazonaws.services.simplesystemsmanagement.model.GetParameterResult;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ParameterStoreTest {
	@Test
    public void testGetParamenterFromStore() {
			AWSSimpleSystemsManagement client = AWSSimpleSystemsManagementClientBuilder.defaultClient();
			GetParameterRequest parameterRequest = new GetParameterRequest();
			parameterRequest.withName("datasource.password").setWithDecryption(Boolean.valueOf(true));
			GetParameterResult parameterResult = client.getParameter(parameterRequest);
			String password = parameterResult.getParameter().getValue();
			String version = parameterResult.getParameter().getVersion().toString();
			assertEquals(password, "12345678");
			assertEquals(version, "1");		
    }

}

```

3. Run ParameterStoreTest.java

### 3. Run your application

##### 1. Run your application 
	
```
mvn package -Dmaven.test.skip=true

java -jar target/module-02-0.1.0.jar

```
	  


