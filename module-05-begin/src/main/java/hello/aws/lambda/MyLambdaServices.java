package hello.aws.lambda;

import com.amazonaws.services.lambda.invoke.LambdaFunction;

public interface MyLambdaServices {
	@LambdaFunction(functionName="MyCustomFunc")
	CustomEventOutput myCustumFunc(CustomEventInput input);
}
