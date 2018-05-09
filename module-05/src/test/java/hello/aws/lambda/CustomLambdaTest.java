package hello.aws.lambda;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import com.amazonaws.services.lambda.invoke.LambdaInvokerFactory;

import hello.aws.lambda.io.CustomEventInput;
import hello.aws.lambda.io.CustomEventOutput;
import hello.aws.lambda.io.DDBEventInput;
import hello.aws.lambda.io.DDBEventOutput;
import hello.aws.lambda.io.RekoEventInput;
import hello.aws.lambda.io.RekoEventOutput;
import hello.aws.lambda.io.TransEventInput;
import hello.aws.lambda.io.TransEventOutput;

import java.util.List;


@SpringBootTest
public class CustomLambdaTest {
    
	@Test
	public void callCustomLamdba()
	{
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
	
	@Test
	public void callDynamoDBLamdba()
	{
		final MyLambdaServices myService = LambdaInvokerFactory.builder()
		 		 .lambdaClient(AWSLambdaClientBuilder.defaultClient())
		 		 .build(MyLambdaServices.class);
		 
		DDBEventInput input = new DDBEventInput();
		
		input.setBucket("seon-virginia-2016");
		input.setPrefix("/images/a.jpeg");
		input.setText("hello");
		input.setTranslated("hallo");
		 
		DDBEventOutput output = myService.myDynamoDBFunc(input);  
		assertEquals(output.getResult(), "SUCCESS");
	}
	
//	@Test
//	public void callRekognitionLamdba()
//	{
//		final MyLambdaServices myService = LambdaInvokerFactory.builder()
//		 		 .lambdaClient(AWSLambdaClientBuilder.defaultClient())
//		 		 .build(MyLambdaServices.class);
//		 
//		RekoEventInput input = new RekoEventInput();
//		
//		input.setBucket("seon-virginia-2016");
//		input.setPath("images/a.jpeg");
//		 
//		RekoEventOutput output = myService.myRekognitionFunc(input);  
//		assertNotNull(output.getText());
//	}	
	
	@Test
	public void callTranslateLamdba()
	{
		final MyLambdaServices myService = LambdaInvokerFactory.builder()
		 		 .lambdaClient(AWSLambdaClientBuilder.defaultClient())
		 		 .build(MyLambdaServices.class);
		 
		TransEventInput input = new TransEventInput();
		
		input.setText("world");
		input.setSourceLangCode("en");
		input.setTargetLangCode("es");
		 
		TransEventOutput output = myService.myTranslateFunc(input);  
		
		assertEquals(output.getTranslated(), "mundjo");
	}		
}
