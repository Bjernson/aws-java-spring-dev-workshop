# Workshop for Java web application for AWS migration

## 1. v0.1 Web application (Building a first web applicaiton with Spring Boot)
- This is very simple web application using MySQL and web URL. 
- create a web app using spring boot framework 
### Mission : check this v0.1 application and run the application to check your development environment
#### download v0.1
git clone https://github.com/baida21/workshop_java_app_spring_boot.git

git  checkout tags/v0.1

2. Setup mysql
install MySQL and create database, user name and password
mysql> create database workshop; -- Create the new database
mysql> create user 'demouser'@'localhost' identified by '12345678'; -- Creates the user
mysql> grant all on workshop.* to 'demouser'@'localhost'; -- Gives all the privileges to the new user on the newly created database

2. Download Git 
git clone https://github.com/baida21/workshop_java_app_spring_boot.git
git checkout tags/v0.1

3. Launch App

4. Test app using 'curl'
curl 'localhost:8080/workshop/deleteall'
curl 'localhost:8080/workshop/add?name=First&email=ex1@gmail.com'
curl 'localhost:8080/workshop/all'

5. Check Unit Test
run unit test, MainControllerTest.java


## v0.2 : First use of AWS service(Parameter Store) and it's integration
### Mission 
1. Check the current code changes
2. Complete the code : add the custom configuration integrated with parameter store in your region

###  Check code changes
1. Maven pom config for AWS SDK for Java
include follwoing maven dependencies
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
    
    
    <dependency>
      <groupId>com.amazonaws</groupId>
      <artifactId>aws-java-sdk-s3</artifactId>
    </dependency>
    <dependency>
      <groupId>com.amazonaws</groupId>
      <artifactId>aws-java-sdk-dynamodb</artifactId>
    </dependency>
    <dependency>
      <groupId>com.amazonaws</groupId>
      <artifactId>aws-java-sdk-ssm</artifactId>
    </dependency>           

2. Check Configuration properties programmatically
https://stackoverflow.com/questions/29072628/how-to-override-spring-boot-application-properties-programmatically
https://stackoverflow.com/questions/33072452/log-configurationproperties-in-springboot-prior-to-startup

- check CustomConfigListner.java
- check register the class in src/main/resources/META-INF/spring.factories:
org.springframework.context.ApplicationListener=hello.CustomConfigListner

3. Configure a Role for EC2 instance or your dev environment(Mac or Windows)
aws configure
AWS Access Key ID [None]:
AWS Secret Access Key [None]:


4. Configuring System Manager Properties Store
- Run ParameterStoreTest.java and find out how to configure the Properties in Parameter Store of System Manager
- Properly configure the Parameter Store
add datasource.url, datasource.username, datasource.password in ap-southeast-1

5. Modify CustomConfigListner.java to integrate with your parameters in System Manager.


6. download result : v2.0-result version. check your code 


## download v3.0 
check 


5. Using System Manager for configuration
- connect to S3
- connect to DynamoDB

## Change your application logics to Lambda 
- How to create local Lambda 
- Unit test for Lambda in Eclipse
- h

4. Create CI/CD for first Deployment on AWS
- 

5. DevSecOps for  

## git

git tag -l v1.1.*

git tag v1.0.2

git push origin v1.0.3

git clone

git checkout tags/<tag_name> 

git checkout tags/<tag_name> -b <branch_name>

delete tags

git push --delete origin <tag_name>

git tag --delete <tag_name>