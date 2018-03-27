## Module-02 : First use of AWS service(Parameter Store) and it's integration (time duration : 40 mins)
- This module is creating CustomListner for retrieving environment parameters from Parameter Store in AWS System Manager. 
- There are many environment paramenters in Spring Data applications, for example, database connection URL, database user name, password or AWS access key and secret key, and there are described in application.properties, generally. the more secured way to retrieve these information is required. 
- Start from moudle-01 and complete the codes with below information.
- module-02 is a starting points to use AWS services with AWS Java SDK.
 
##### 1 add packages in pom.xml

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
  <dependency>
    <groupId>com.amazonaws</groupId>
    <artifactId>aws-java-sdk-s3</artifactId>
  </dependency>
  <dependency>
    <groupId>com.amazonaws</groupId>
    <artifactId>aws-java-sdk-dynamodb</artifactId>
  </dependency>
   
    
  <!-- AWS SDK rekognition -->  
	<dependency>
	  <groupId>com.amazonaws</groupId>
	  <artifactId>aws-java-sdk-rekognition</artifactId>
	</dependency>  
	<!-- AWS SDK translate -->  
	 <dependency>
	  <groupId>com.amazonaws</groupId>
	  <artifactId>aws-java-sdk-translate</artifactId>
	</dependency>            

```
##### 2. Configure AWS CLI to allow application to get access key and secret key
```
> aws configure
> AWS Access Key ID [None]: [your key]
> AWS Secret Access Key [None]: [your key]
```

##### 3. Configuration properties programmatically
- Create CustomConfigListner.java using following information 
- [spring boot application properites](https://stackoverflow.com/questions/29072628/how-to-override-spring-boot-application-properties-programmatically)
- [springboot prior to startup](https://stackoverflow.com/questions/33072452/log-configurationproperties-in-springboot-prior-to-startup)
	
- Create CustomConfigListner.java in hello package
- Create **src/main/resources/META-INF/spring.factories** and register above class in it

```
org.springframework.context.ApplicationListener=hello.CustomConfigListner
```

##### 4. Configure ParameterStore in System Manager 
AWS Systems Manager Parameter Store provides secure, hierarchical storage for configuration data management and secrets management. You can store data such as passwords, database strings, and license codes as parameter values.
Complete the following tasks to configure application parameters for ParameterStore (default region is us-east-1)

	1. Open the Amazon EC2 console at https://console.aws.amazon.com/ec2/
	2. Create parameters in ParameterStore for database URL, database username and password

![Parameter Store](./images/module-02/paramter-store-01.png)


##### 5. Check the availability of parameters in ParameterStore
- Run ParameterStoreTest.java 


##### 6. Modify CustomConfigListner.java 
- Integrate with your parameters in System Manager.
- Add additional parameters you need in your application


##### 7. Check features using unit tests
- Modify Test classes
- Run CustomConfigTest

##### 8. For next implementation
- Please check classes in hello.logics and unit test in hello.logics
