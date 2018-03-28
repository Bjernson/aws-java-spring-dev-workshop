## Module-08 Create a docker and CI/CD for first Deployment on AWS

<hr>

### 1. First CI/CD using CodeStart without Lambda

##### 1. Create a CodeStart project name as "workshop-java"

	1. Select a project template (Java, Webapplication with Spring in EC2)
![Select a Template](./images/module-08/01.png)
	
	2. Select CodeCommit as a code repository 
![Select CodeCommit](./images/module-08/02.png)	
	
	3. Choose your key pair
	4. Choose Cloud9 to edit your code 
![Select CodeCommit](./images/module-08/03.png)		

	5. Select a instance type and launch the project
	6. After creating the project, launch a Cloud9 IDE
	
![Select CodeCommit](./images/module-08/04.png)		

##### 2. Open Cloud9 and configure the dev environtment
	1. Open a Cloud9 IDE and check it's first application in folder
![Cloud9](./images/module-08/05.png)	

	2. check java --version
	3. upgrade java version to 1.8
	

```
sudo yum list available java\*      # check avaiable java version
sudo yum install java-1.8.0 java-1.8.0-openjdk-devel        # install 1.8 java and javac
sudo yum remove java-1.7.0-openjdk  # remove 1.7
java -version											# check java version
```
	4. update JAVA_HOME in the environment variable in .bashrc


```
vi ~/.bashrc
### add follwing content
export JAVA_HOME=/usr/
```
	
	4. Install Maven

```
$ cd /usr/local
$ sudo wget http://www-eu.apache.org/dist/maven/maven-3/3.5.3/binaries/apache-maven-3.5.3-bin.tar.gz
$ sudo tar xzf apache-maven-3.5.3-bin.tar.gz
$ sudo ln -s apache-maven-3.5.3  maven

$ sudo vi /etc/profile.d/maven.sh

# add following content.
export M2_HOME=/usr/local/maven
export PATH=${M2_HOME}/bin:${PATH}

# load the environment variables in current shell using following command.
source /etc/profile.d/maven.sh

# check the loaded environment variables  
echo $PATH             
```
##### 3. Update the instance of Deployment
	1. upgrate Java to 1.8 in the instance of CodeDeploy

##### 3. first fetching source codes from CodeComnit
	1. Perform tasks following the instruction in CodeCommit for the first application
	2. Run following command

	mvn -f pom.xml compile
	mvn -f pom.xml package
	
	3. If you want to run this application, then copy the built application to the Tomcat webapp directory that you configured in your local machine or ec2 instance on AWS
	
##### 4. download this first application on your local Eclipse IDE
	1. open CodeCommit and copy Clone URL
	2. fetch source codes.
	3. if you don't have a CodeCommit username and password, please refer this documentation :
	https://docs.aws.amazon.com/codecommit/latest/userguide/setting-up-gc.html
	4. import this project to your Eclipse IDE
	5. Run follwing commands

```
mvn -f pom.xml compile
mvn -f pom.xml package
```
		
	6. if you got a following errors,
	
	7. add following content in pom.xml
	<properties>
		...
		<maven.compiler.source>1.8</maven.compiler.source>
		<maven.compiler.target>1.8</maven.compiler.target>
	</properties>
	8. check the result of compilation
	
![Eclipse Java Compiler](./images/module-08/06.png)	



##### 5. Create a new pom file for your application.
Firstly, we will use module-04 source code for the created CI/CD.

	1. rename pom.xml as pom-backup.xml
	2. create a new POM file name as "pom.xml" 
	3. merge the pom file in module-04 into workshop-java project you fetched from CodeCommit
	4. delete all source codes in "workshop-java" project and copy all source codes from module-04 to "workshop-java" project
	5. run Application.java and check the result of UnitTest.
	6. if you get a compilation errors in your project, please check the Java compiler version and JRE, change compiler version and JRE in your application (1.8)
	
##### 6. Change appspecc.xml, buildspec.xml and scripts	
	1. Change appsecc.xml, buildspec.xml and scripts	
appspec.xml

```
  build:
    commands:
      - echo Build started on `date`
      - mvn package -Dmaven.test.skip=true
      
```

	2. script/start_server.sh

```
#!/bin/bash
cd /home/ec2-user/javaapp
java -jar ROOT.jar 
```

	3. run follwing commands again and check the output in target folder

```
mvn package
java -jar target/ROOT.jar
```

	
##### 6. commit source codes into CodeCommit
	1. commit source codes in "workshop-java" project into CodeCommit.

```
git add .
git commit -m "add first module-04"
git push 

```


![AWS CodeStar](./images/module-08/07.png)	
##### 7. Change Credentials
You will probablly get a error message in CodeDeploy.

```
Caused by: org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'photoInfoRepository': Cannot resolve reference to bean 'amazonDynamoDB' while setting bean property 'amazonDynamoDB'; nested exception is org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'amazonDynamoDB' defined in class path resource [hello/config/DynamoDBConfig.class]: Bean instantiation via factory method failed; nested exception is org.springframework.beans.BeanInstantiationException: Failed to instantiate [com.amazonaws.services.dynamodbv2.AmazonDynamoDB]: Factory method 'amazonDynamoDB' threw exception; nested exception is org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'amazonAWSCredentials' defined in class path resource [hello/config/DynamoDBConfig.class]: Bean instantiation via factory method failed; nested exception is org.springframework.beans.BeanInstantiationException: Failed to instantiate [com.amazonaws.auth.AWSCredentials]: Factory method 'amazonAWSCredentials' threw exception; nested exception is com.amazonaws.AmazonClientException: Cannot load the credentials from the credential profiles file. Please make sure that your credentials file is at the correct location (/Users/userid/.aws/credentials), and is in a valid format.

```
This is because of a credentials, please check below documentation and fix the source code in DynamoDBConfig

```
public AmazonDynamoDB amazonDynamoDB() {
    AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder.standard()
       .build();
    return amazonDynamoDB;
}
```

<hr>


### 2. CI/CD with Lambda using SAM
[Module-07 or Module-08 is able to contain this steps in each module]

#### 1. Add CodeBuild for Lambda project


####
refer : https://stelligent.com/2017/03/09/using-parameter-store-with-aws-codepipeline/

