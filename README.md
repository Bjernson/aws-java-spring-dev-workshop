# Workshop for Java web application for AWS migration

## Preparation
Java SDK 8

## 1. v0.1 Web application (Building a first web applicaiton with Spring Boot)
- This is very simple web application using MySQL and web URL. 
- create a web app using spring boot framework 
### Mission : check this v0.1 application and run the application to check your development environment
#### download v0.1

1. Setup mysql and configure username/password
install MySQL and create database, user name and password (demouser/12345678)
mysql> create database workshop; -- Create the new database
mysql> create user 'demouser'@'localhost' identified by '12345678'; -- Creates the user
mysql> grant all on workshop.* to 'demouser'@'%'; -- Gives all the privileges to the new user on the newly created 

2. Aurora -
superuser : sa
CREATE USER 'demouser'@'%' IDENTIFIED BY '12345678';
grant all on workshop.* to 'demouser'@'%';
#GRANT ALL PRIVILEGES ON *.* TO 'demouser'@'%'  WITH GRANT OPTION;
#FLUSH PRIVILEGES ;

CREATE TABLE `User` (
  `id` integer NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 # spring-boot-dynamo-mysql-multi


2. Download Git 
git clone https://github.com/aws-asean-builders/workshop_java_app_spring_boot
git checkout tags/v0.1

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


## V0.5 Change Logics to Lambda and X-Ray 
### Download module-05
### 1.Create a Lambda project - CustomEvent
- create a Lambda project using AWS Eclipse plugin
- select "Custom Event"
- see the "CustomLambda" project
- upload function to "US-EAST-1" name as "My-Custom-Function"

### implement 3 lambda functions.
- retrieve information from images
- translate text
- save 


## V0.6 Create a docker and CI/CD for first Deployment on AWS

## V0.7 DevSecsOps

## V0.8 Create 


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
1. Configuration - parameter store conection
2, Repository - Spring data