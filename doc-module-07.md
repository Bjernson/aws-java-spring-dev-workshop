## Module-07 Add X-Ray

##### reference

[X-Ray SDK](https://docs.aws.amazon.com/xray/latest/devguide/xray-sdk-java.html)

##### 1. Setup X-Ray daemon for local and server
The AWS X-Ray daemon is a software application that listens for traffic on UDP port 2000, gathers raw segment data, and relays it to the AWS X-Ray API. The daemon works in conjunction with the AWS X-Ray SDKs and must be running so that data sent by the SDKs can reach the X-Ray service. 
[install X-Ray daemon](https://docs.aws.amazon.com/xray/latest/devguide/xray-daemon.html)

	1 download X-Ray dsaemon 
	2 run daemon (if it is MacOS, then command like a below)
	
```
/xray_mac -o -n us-east-1 & (for example)
```

##### 2. Add packages in pom.xml

```
	<dependencyManagement>
	  <dependencies>
...		
	    <!-- xray -->
	    <dependency>
	      <groupId>com.amazonaws</groupId>
	      <artifactId>aws-xray-recorder-sdk-bom</artifactId>
	      <version>1.3.1</version>
	      <type>pom</type>
	      <scope>import</scope>
	    </dependency>			        	    	    
	  </dependencies>
	</dependencyManagement> 
	
    <!-- AWS SDK xray -->        
		<dependency>
	    <groupId>com.amazonaws</groupId>
	    <artifactId>aws-xray-recorder-sdk-core</artifactId>
	  </dependency>
	  <dependency>
	    <groupId>com.amazonaws</groupId>
	    <artifactId>aws-xray-recorder-sdk-apache-http</artifactId>
	  </dependency>
	  <dependency>
	    <groupId>com.amazonaws</groupId>
	    <artifactId>aws-xray-recorder-sdk-aws-sdk</artifactId>
	  </dependency>
	  <dependency>
	    <groupId>com.amazonaws</groupId>
	    <artifactId>aws-xray-recorder-sdk-aws-sdk-instrumentor</artifactId>
	  </dependency>
	  <dependency>
	    <groupId>com.amazonaws</groupId>
	    <artifactId>aws-xray-recorder-sdk-sql-postgres</artifactId>
	  </dependency>
	  <dependency>
	    <groupId>com.amazonaws</groupId>
	    <artifactId>aws-xray-recorder-sdk-sql-mysql</artifactId>
	  </dependency>  	
	
```

##### Add X-Ray configuration 
[X-Ray Java configuration](https://docs.aws.amazon.com/xray/latest/devguide/xray-sdk-java-configuration.html)
The X-Ray SDK for Java provides a class named AWSXRay that provides the global recorder, a TracingHandler that you can use to instrument your code. You can configure the global recorder to customize the AWSXRayServletFilter that creates segments for incoming HTTP calls.

```
@Configuration
public class XRayConfig {
  private static final Logger logger = LoggerFactory.getLogger(XRayConfig.class);

  @Bean
  public Filter TracingFilter() {
    return new AWSXRayServletFilter("Workshop");
  }

  @Bean
  public Filter SimpleCORSFilter() {
    return new SimpleCORSFilter();
  }

  static {
  	 System.out.println("\n##### Webconfig.java static AWSXRay ######################\n");
    AWSXRayRecorderBuilder builder = AWSXRayRecorderBuilder.standard().withPlugin(new EC2Plugin());

    URL ruleFile = XRayConfig.class.getResource("/sampling-rules.json");
    builder.withSamplingStrategy(new LocalizedSamplingStrategy(ruleFile));

    AWSXRay.setGlobalRecorder(builder.build());
  }
}
```

##### 2. Add segment to CustomConfigListner
Here is a tips for CustomConfigListner. 
CustomConfigListner is called before executing XRayConfig, this means we need to embed codes CustomConfigListner for prevent from errors.

```
public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
	
	AWSXRay.beginSegment("Workshop : Load ParameterStore");
	
	....
	
	AWSXRay.endSegment();
}		
		
```

##### 3. Configure X-Ray for SQL Queries
Instrument SQL database queries by adding the X-Ray SDK for Java JDBC interceptor to your data source configuration.

- PostgreSQL – com.amazonaws.xray.sql.postgres.TracingInterceptor
- MySQL – com.amazonaws.xray.sql.mysql.TracingInterceptor

So, change codes in CustomConfigListner

```
props.put("spring.mysql.datasource.driver-class-name", "com.mysql.jdbc.Driver");
// for X-Ray
props.put("spring.mysql.datasource.jdbc-interceptors", "com.amazonaws.xray.sql.mysql.TracingInterceptor");   
environment.getPropertySources().addFirst(new PropertiesPropertySource("myProps", props));
```

##### Add code in Client build
To instrument individual clients, remove the aws-sdk-instrumentor submodule from your build and add an XRayClient as a TracingHandler on your AWS SDK client using the service's client builder. 

```
AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder
	.standard()
	.withRequestHandlers(new TracingHandler(AWSXRay.getGlobalRecorder()))  // X-Ray
	.withRegion(region)
	.build();

AmazonTranslate translate = AmazonTranslateClientBuilder
	.standard()
	.withRegion(region)
	.withRequestHandlers(new TracingHandler(AWSXRay.getGlobalRecorder())) // X-Ray
	.build();	  	         
	  	         
```
 
##### 5. Channge UnitTest code
To run unit tests, we need to add X-Ray Segment to generate segment to trace, for example.

```
public class MySqlTest {
	
	@Autowired
	UserRepository repository;
  
	@Test
	public void test () {
		
		AWSXRay.beginSegment("MySQLTest test"); 
		
		repository.deleteAll();
		
		...
		
    AWSXRay.endSegment();
	}
```