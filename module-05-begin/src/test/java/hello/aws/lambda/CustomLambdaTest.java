package hello.aws.lambda;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import com.amazonaws.services.lambda.invoke.LambdaInvokerFactory;

import hello.aws.lambda.io.CustomEventInput;
import hello.aws.lambda.io.CustomEventOutput;

import java.util.List;


@SpringBootTest
public class CustomLambdaTest {
	
	AWSCredentials credentials;
	@Test
	public void callCustomLamdba()
	{
	    try {
	        credentials = new ProfileCredentialsProvider("default").getCredentials();
	    } catch(Exception e) {
	       throw new AmazonClientException("Cannot load the credentials from the credential profiles file. "
	        + "Please make sure that your credentials file is at the correct "
	        + "location (/Users/userid/.aws/credentials), and is in a valid format.", e);
	    }
	    
	    final MyLambdaServices myService = LambdaInvokerFactory.builder()
	    		 .lambdaClient(AWSLambdaClientBuilder.defaultClient())
	    		 .build(MyLambdaServices.class);
	    
	    CustomEventInput input = new CustomEventInput();
	    List<Integer> list = new ArrayList();
	    list.add(1);
    		list.add(5);
    		input.setValues(list);

	    CustomEventOutput output = myService.myCustumFunc(input);  
	    assertEquals((int)output.getValue(), (int)5);
	    
	}
}
