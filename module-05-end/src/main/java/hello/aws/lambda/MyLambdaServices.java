package hello.aws.lambda;

import com.amazonaws.services.lambda.invoke.LambdaFunction;

import hello.aws.lambda.io.CustomEventInput;
import hello.aws.lambda.io.CustomEventOutput;

public interface MyLambdaServices {
	@LambdaFunction(functionName="MyCustomFunc")
	CustomEventOutput myCustumFunc(CustomEventInput input);
}
