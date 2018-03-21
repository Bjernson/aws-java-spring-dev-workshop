# Workshop for Java web application for AWS migration

## 0. Preparation
##### 1. Install all required SDK, packages in your dev environment
- Java SDK 8, git client
- Eclipse Oxygen 2
- AWS plugin for Eclipse 

The installation time generally takes 10~30mins. Attendees should to prepare all installations for their developing environment before starting this workshop.

## 1. Module-01 :  Web application - Building a first web application with Spring Boot
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

Aurora

```
superuser : sa
CREATE USER 'demouser'@'%' IDENTIFIED BY '12345678';
grant all on workshop.* to 'demouser'@'%';
#GRANT ALL PRIVILEGES ON *.* TO 'demouser'@'%'  WITH GRANT OPTION;
#FLUSH PRIVILEGES ;
```




3. Launch App

4. Test app using 'curl' for API
curl 'localhost:8080/workshop/deleteall'
curl 'localhost:8080/workshop/add?name=First&email=ex1@gmail.com'
curl 'localhost:8080/workshop/all'

5. Check Unit Test
run unit test, MainControllerTest.java

6. Run web page localhost:8080/index.html
- Run CRUD for User data
- see user list, add/update/delete user




## v0.2 : First use of AWS service(Parameter Store) and it's integration
### 1. Mission 
1. Check the current code changes
2. Complete the code : add the custom configuration integrated with parameter store in your region

### 2. download v0.2
git checkout tags/v0.2

### 3. Check code changes 
#### 3.1 pom.xml
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

#### 3.1 Check added files
Configuration properties programmatically
https://stackoverflow.com/questions/29072628/how-to-override-spring-boot-application-properties-programmatically
https://stackoverflow.com/questions/33072452/log-configurationproperties-in-springboot-prior-to-startup

- check CustomConfigListner.java
- check register the class in src/main/resources/META-INF/spring.factories:
org.springframework.context.ApplicationListener=hello.CustomConfigListner

### 4. Configure a Role for EC2 instance or your dev environment(Mac or Windows)
aws configure
AWS Access Key ID [None]:
AWS Secret Access Key [None]:


### 5. Configuring System Manager Properties Store
- Run ParameterStoreTest.java and find out how to configure the Properties in Parameter Store of System Manager
- Properly configure the Parameter Store
add datasource.url, datasource.username, datasource.password in ap-southeast-1


### 6. Modify CustomConfigListner.java to integrate with your parameters in System Manager.
Add your paratmeter you need in your application

### 7. Run ParameterStoreTest.java 
- check the result


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