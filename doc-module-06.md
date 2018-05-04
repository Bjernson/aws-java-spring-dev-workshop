## module-06 : Create StepFunction and use a Stepfucntion in your application (20 mins)
- Create a Stepfunction 
- Integrate Lambda Functions with a StepFunction

##### Reference

[Create a Step function](https://docs.aws.amazon.com/step-functions/latest/dg/tutorial-lambda-state-machine-cloudformation.html)
[Call a StepFunction] (https://aws.amazon.com/blogs/developer/stepfunctions-fluent-api/)

### 1. Create a StepFunction

### 1.1 Change event class of Lambda for StepFunction 


Create a common Model class (StepEventInput, StepEventOutout)

StepEventInput.java (omit getter/setter)

```
	private String id;
	private String bucket;
	private String prefix;
	private String text;
	private String translated;
	private String sourceLangCode;
	private String targetLangCode;
```

StepEventOutput.java (omit getter/setter)

```
	private String text;
	private String error_message;
```
	
 
#### 1.2 Create a StepFunction using JSON

##### 1. Create a step-function.json in *main/resources/aws*

```
{
  "Comment" : "Machine learning execution with spot instance",
  "StartAt" : "RetrieveInfoFromPhotoUsingRekognition",
  "States"  : {
    "RetrieveInfoFromPhotoUsingRekognition": {
      "Type"      : "Task",
      "Resource"  : "arn:aws:lambda:us-east-1:550622896891:function:MyFunction-workshop-rekognition",
      "Retry" : [ {"ErrorEquals":["HandledError"], "MaxAttempts":3} ],
      "Next"      : "TransInfoUsingTranlate"
    }, 
    "TransInfoUsingTranlate": {
      "Type"      : "Task",
      "Resource"  : "arn:aws:lambda:us-east-1:550622896891:function:MyFunction-workshop-translate",
      "Next"      : "StoreTransDataIntoDynamoDB"
    },
    "StoreTransDataIntoDynamoDB": {
      "Type"      : "Task",
      "Resource"  : "arn:aws:lambda:us-east-1:550622896891:function:MyFunction-workshop-dynamodb",    
      "Retry" : [ {"ErrorEquals":["HandledError"], "MaxAttempts":3} ],
      "End": true
  		}
  	}
}

```

##### 2. Create a input file in same folder (step-input.json)

```
{
  "bucket":"seon-virginia-2016", 
  "prefix":"images/a.jpeg",
  "text" : "Hello, hello",
  "translated" : "",
  "sourceLangCode" :"en",
  "targetLangCode" : "es"
}
```

#### 1.3 Implement a test code

##### 1. Create StepFunctionTest in *hello.aws.stepfunction*

```
final AWSStepFunctions stepFunctionclient = AWSStepFunctionsClientBuilder.defaultClient();

URL inputFile = Application.class.getResource("/aws/step-input.json");
String input = jsonFileRead(inputFile);
StartExecutionRequest startExecutionRequest 
= new StartExecutionRequest()
.withInput(input)
.withStateMachineArn("arn:aws:states:us-east-1:5591:stateMachine:workshop-stepfunction").withSdkRequestTimeout(30000);

StartExecutionResult executionResult = stepFunctionclient.startExecution(startExecutionRequest);
```
Test a code and check the result.