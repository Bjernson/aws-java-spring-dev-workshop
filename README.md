
# Workshop for Java web application for AWS migration

<hr>

## 0. Preparation
##### 1. Install all required SDK, packages in your dev environment
- Java SDK 8, git client
- Eclipse Oxygen 2
- AWS plugin for Eclipse 
- AWS CLI in your development environment

The installation time generally takes 10~30mins. Attendees should to prepare all installations for their developing environment before starting this workshop.

<hr>

## 1. Module-01 :  Web application - Building a first web application with Spring Boot (time duration : 30 mins)
- This is a simple web application using Spring Boot and MySQL
- Check module-01 application and run this application to check the application structure and it's execution.

##### 1. Download source codes 
```
git clone https://github.com/aws-asean-builders/aws-java-spring-dev-workshop
```

##### 2. Configure environment.
- Setup MySQL and configure username/password
- install MySQL in your development environment, it depends on your dev OS, for example MacOs or Linux
- After installataion, create database and configure user name and password, for example (demouser/12345678)

```
## create database, user and it's privilege

mysql> create database workshop; -- Create the new database
mysql> create user 'demouser'@'localhost' identified by '12345678'; -- Creates the user
mysql> grant all on workshop.* to 'demouser'@'%'; -- Gives all the privileges to the new user on the newly created 


## crate User table

CREATE TABLE `User` (
  `id` integer NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 
```

##### 3. Check the structure of application
- Check **application.properties** and **spring.factories** in META-INF of resource folder. This file is for **CustomConfigListner.java** to change the environment configuration using Configuration Listner
- Check package structure, boot, controller, model, repository
- UserRepository is for the JPA 
- Check the **pom.xml**, it contains Spring Boot, JPA, MySQL, Thymeleaf

```
       <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>    
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
			  <!-- thymeleaf-->  
			  <dependency>
			    <groupId>org.springframework.boot</groupId>
			    <artifactId>spring-boot-starter-thymeleaf</artifactId>
			  </dependency>     
		    <dependency>
		      <groupId>org.webjars</groupId>
		      <artifactId>bootstrap</artifactId>
		      <version>3.3.7-1</version>
		    </dependency>			    		     
        <!-- Use MySQL Connector -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency> 
 ```
##### 4. Run your test codes
Run MainControllerTest with JUnit Runner and check the console output and it's result. if you get a error messages then take a look at how to fix the problem.
We have 2 kinds of unit test, one is mock test, the other is integration test, please check 2 files in test folder.


##### 5. Test app using 'curl' for API
Launch your application in your Eclipse IDE and run 'curl' command like below

```
curl 'localhost:8080/workshop/deleteall'
curl 'localhost:8080/workshop/add?name=First&email=ex1@gmail.com'
curl 'localhost:8080/workshop/all'
```

##### 6. Run web page localhost:8080/index.html
- Run CRUD for User data
- see user list, add/update/delete user

##### Appedix. create a Spring Boot project from scratch
Please check this blog for creating a spring boot project from scratch using Maven. 
[add later]


<hr>

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

![Parameter Store](/imgs/module-02/paramter-store-01.png)

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


## Module 3 (time duration : 40 mins)
### Start : from V0.3-begin
### Missions
#### 1. Mission 1 : Check a file resizing class, pick up your images and save it to local folder
#### 2. Mission 2 : Complete upload a file to S3 using AWS SDK
#### 3. Mission 3 : Complete Retrieve information from picture and Translate text using Amazon Translate
#### 4. Mission 4 : change database from Mysql to Aurora for Mysql 
#### 5. Mission 5 : Connect AWS DynamoDB using Spring Data
### End : V0.3-final

### Mission 1. Download v0.3-begin
git checkout tags/v0.3


### 3. AWS SDK 
refer S3 : https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/examples-s3-objects.html#upload-object
refer rekognition : https://docs.aws.amazon.com/rekognition/latest/dg/get-started-exercise.html
refer translate :https://docs.aws.amazon.com/translate/latest/dg/examples-java.html
refer dynamodb spring data :https://github.com/spring-data-dynamodb/spring-data-dynamodb

### 4. [TODO] With above documentations and S3FileTransterTest, AWSAIServicesTest, please complete AWSAIServices.java, S3FileTransfer.java

### 5. Mission 5
add dependencies in pom.xml
    <dependency>
        <groupId>com.amazonaws</groupId>
        <artifactId>aws-java-sdk-dynamodb</artifactId>
        <version>1.11.34</version>
    </dependency>
    <dependency>
        <groupId>com.github.derjust</groupId>
        <artifactId>spring-data-dynamodb</artifactId>
        <version>5.0.2</version>
    </dependency>

add dynamodb endpoint url in application.properties



### Spring Cloud
https://cloud.spring.io/spring-cloud-aws/
Now Spring Cloud support S3, SNS, SQS, ElastiCache,CloudFormation and RDS


### 5. Using System Manager for configuration
- connect to S3
- connect to DynamoDB

## v0.4 Add logics for file transferring
### 1. Mission : add logics for file transferring and data retrieving from files. 
1. if it is a picture, we will resize it to original and small size (2 size)
2. will retrieve information from pi and mov, location information, transfer it to target language and send it to friends

 
- How to create local Lambda 
- Unit test for Lambda in Eclipse
- h


## V0.5 Change Logics to Lambda  
This module requires a knowledge session for StepFunction service.
### Start from module-04
### 1.Create a Lambda project - CustomEvent
- create a Lambda project using AWS Eclipse plugin
- select "Custom Event"
- see the "CustomLambda" project
- upload function to "US-EAST-1" name as "My-Custom-Function"

### 2. implement 3 lambda functions.
- retrieve information from images
- translate text
- save text to DDB

#### 2.1 reference for Lambda 
dynamodb putitemrequest : https://github.com/aws-samples/aws-dynamodb-examples/blob/master/src/main/java/com/amazonaws/codesamples/lowlevel/LowLevelItemCRUDExample.java


## module-06. Create StepFunction and use a stepfucntion in your application
This module requires a knowledge session for StepFunction service.
 
### 1. Create a StepFunction using CloudFormation
#### 1.1 reference for StepFunction
https://docs.aws.amazon.com/step-functions/latest/dg/tutorial-lambda-state-machine-cloudformation.html

### 2. Create a service logic to call a StepFunction.
ref: https://aws.amazon.com/blogs/developer/stepfunctions-fluent-api/


## Module-07 Add X-Ray
ref : https://docs.aws.amazon.com/xray/latest/devguide/xray-sdk-java.html

### 1. Setup X-Ray daemon for local and server
ref : https://docs.aws.amazon.com/xray/latest/devguide/xray-daemon.html
The AWS X-Ray daemon is a software application that listens for traffic on UDP port 2000, gathers raw segment data, and relays it to the AWS X-Ray API. The daemon works in conjunction with the AWS X-Ray SDKs and must be running so that data sent by the SDKs can reach the X-Ray service. 

#### 1.1 download daemon 
#### 1.2 run daemon
/xray_mac -o -n us-east-1 & (for example)
#### 1.3 check the 

#### 2.add packages in pom.xml
x-ray packages

#### add code
1. configuraiton file
2. SQL
3. add segment to CustomConfigListner
4. add code to Client
5. check X-Ray using Unit Test.




## Module-08 Create a docker and CI/CD for first Deployment on AWS


## Module-08 DevSecsOps

## Module-09 Custom Metrics and CloudWath Logs for data analytics


## git examples

git tag -l v1.1.*

git tag v1.0.2

git push origin v1.0.3

git clone

git checkout tags/<tag_name> 

git checkout tags/<tag_name> -b <branch_name>

delete tags

git push --delete origin <tag_name>

git tag --delete <tag_name>


## blog
1. Configuration - parameter store connection
2, Repository - Spring data