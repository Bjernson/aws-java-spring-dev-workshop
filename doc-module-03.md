
## Module 3 : Using AWS services (time duration : 30 mins)
From this module, we are beginning to develop application using AWS services.
We will complete the following tasks.
- Resize a file and save it to local folder
- Upload a file to S3 using AWS SDK
- Retrieve information from picture using Amazon Rekognition and Translate text using Amazon Translate
- Change database from Mysql to Aurora for Mysql 
- Store a file meta data to DynamoDB

**Start from the module-02**

#### 1. references
Please refer the following information to complete the tasks
 
[Develop S3](https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/examples-s3-objects.html#upload-object)

[Develop Rekognition](https://docs.aws.amazon.com/rekognition/latest/dg/get-started-exercise.html)

[Develop Translate](https://docs.aws.amazon.com/translate/latest/dg/examples-java.html)

#### 2. Basic structure to use AWS services
	1. Declare a client for the services to call
	2. Initialzie a client with various information

for example

```

AmazonTranslate translate = AmazonTranslateClientBuilder
							.standard()
							.withRegion(region)   // set region
							.withCredentials(new AWSStaticCredentialsProvider(credentials)) //set credentials
							.build();

```

##### 3. Implement logics
- Check AWSAIServicesTest and complete AWSAIServices.java, S3FileTransfer.java based on Unit Test
- Create S3 file transferring and translate 

##### 4. Implement DynamoDB 
- Check DynamoDBTest
- Complete the tasks to implement the DDB logics in Unit Test (not logics)

#### 5. Change the database from local MySQL to Aurora MySQL

	1. Open the Amazon RDS console : https://console.aws.amazon.com/rds/home?region=us-east-1#
	2. Select Aurora for MySQL 5.7 Database engine 
	3. Create a DB instance configuring databasename, username, password.
	
![Parameter Store](./images/module-03/01.png)

	4. Wait until Aurora for MySQL launching
	5. Change parameter values in Parameter Store in EC2 to Aurora instance

#### 6. Test a service
Test a IntegratedTransTest.java and Implement a IntegratedTrans.java
