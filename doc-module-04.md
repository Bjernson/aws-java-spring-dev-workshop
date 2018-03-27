
## Module-04 : Using multiple repositories using Spring Data (time durations : 30 mins)
In this module, we will learn how to configure the multiple repositories of Aurora and DynamoDB using Spring Data.
- Create configuration files for DynamoDB and MySQL DB. 
- Create Model classes for each database (User for MySQL, PhotoInfo for DynamoDB)
- Create a model, repository packages and change Model and Repository package path to distinguish it in two repositories 
- Change application.properties
- Test 2 repositories with Unit Test codes

##### 1. Add Spring Data for Database
We arg going to use Spring Data for DynamoDB 

reference : [derjust's Github](https://github.com/derjust/spring-data-dynamodb)

Add it to Pom.xml

```
  <!-- spring-data-dynamo-db -->
  <dependency>
      <groupId>com.github.derjust</groupId>
      <artifactId>spring-data-dynamodb</artifactId>
      <version>4.5.0</version>
  </dependency> 
		    
```

##### 2. Implement DB configuration classes
reference : [dynamodb put item request](https://github.com/aws-samples/aws-dynamodb-examples/blob/master/src/main/java/com/amazonaws/codesamples/lowlevel/LowLevelItemCRUDExample.java)

We need to create configuration class for MySQL and DynamoDB to use each repository
Here is MysqlDBConfig example.

```
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
		entityManagerFactoryRef = "mysqlEntityManager", 
		transactionManagerRef = "mysqlTransactionManager", 
		basePackages = "hello.repository.mysql"
)
public class MysqlDBConfig {
	
	@Autowired
	public ConfigurableEnvironment environment;
	
	/**
	 * MySQL datasource definition.
	 * 
	 * @return datasource.
	 */
	@Bean
	@ConfigurationProperties(prefix = "spring.mysql.datasource")
	public DataSource mysqlDataSource() {
		System.out.println("##### DataSource called");
		DataSource ds = DataSourceBuilder.create().build();
		System.out.println("##### DataSource url = " + environment.getProperty("spring.mysql.datasource.url").toString());
		return ds ;
	}
 
	/**
	 * Entity manager definition. 
	 *  
	 * @param builder an EntityManagerFactoryBuilder.
	 * @return LocalContainerEntityManagerFactoryBean.
	 */
	@Bean(name = "mysqlEntityManager")
	public LocalContainerEntityManagerFactoryBean mysqlEntityManagerFactory(EntityManagerFactoryBuilder builder) {
		return builder
					.dataSource(mysqlDataSource())
					.properties(hibernateProperties())
					.packages(User.class)
					.persistenceUnit("mysqlPU")
					.build();
	}
 
	/**
	 * @param entityManagerFactory
	 * @return
	 */
	@Bean(name = "mysqlTransactionManager")
	public PlatformTransactionManager mysqlTransactionManager(@Qualifier("mysqlEntityManager") EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}
 
	private Map<String, Object> hibernateProperties() {
 
		Resource resource = new ClassPathResource("hibernate.properties");
		try {
			Properties properties = PropertiesLoaderUtils.loadProperties(resource);
			return properties.entrySet().stream()
											.collect(Collectors.toMap(
														e -> e.getKey().toString(),
														e -> e.getValue())
													);
		} catch (IOException e) {
			return new HashMap<String, Object>();
		}
	}

```
Change **spring.datasource** properties to **spring.mysql.datasource** in CustomConfigListner.java

```
ConfigurableEnvironment environment = event.getEnvironment();
Properties props = new Properties();
props.put("spring.mysql.jpa.hibernate.ddl-auto", "update");
props.put("spring.mysql.datasource.url", url);
props.put("spring.mysql.datasource.username", username);
props.put("spring.mysql.datasource.password", password);
props.put("spring.mysql.datasource.driver-class-name", "com.mysql.jdbc.Driver");
environment.getPropertySources().addFirst(new PropertiesPropertySource("myProps", props));

```
These new application properties will be used in this module.

##### 3 Separate Model classes into different package.
hello.model.mysql.User
hello.model.ddb.PhotoInfo

##### 4. Create a test code
Create PhotoInfoTest in hello.repository(test)

```
@Autowired
PhotoInfoRepository repository;

@Test
public void sampleTestCase() {
  repository.deleteAll();
  PhotoInfo p = new PhotoInfo("a.jpeg", "hello", "hallo");	
  repository.save(p);
  
  List<PhotoInfo> result2 = (List<PhotoInfo>) repository.findAll(); 
  
  assertTrue("Not empty", result2.size() > 0);
}
```