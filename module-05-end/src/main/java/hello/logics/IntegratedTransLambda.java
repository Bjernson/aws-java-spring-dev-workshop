package hello.logics;

import static org.junit.Assert.assertEquals;

import org.springframework.stereotype.Service;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import com.amazonaws.services.lambda.invoke.LambdaInvokerFactory;

import hello.aws.lambda.MyLambdaServices;
import hello.aws.lambda.io.DDBEventInput;
import hello.aws.lambda.io.DDBEventOutput;
import hello.aws.lambda.io.RekoEventInput;
import hello.aws.lambda.io.RekoEventOutput;
import hello.aws.lambda.io.TransEventInput;
import hello.aws.lambda.io.TransEventOutput;


@Service
public class IntegratedTransLambda {
	final MyLambdaServices myService = LambdaInvokerFactory.builder()
 		 .lambdaClient(AWSLambdaClientBuilder.defaultClient())
 		 .build(MyLambdaServices.class);
	
	public String RetrieveAndSave(String bucket, String photoPath, Regions region)
	{
		String result = null;
		try {
			// 1. call rekognition
			RekoEventInput reko_input = new RekoEventInput();
			
			reko_input.setBucket("seon-virginia-2016");
			reko_input.setPath("images/a.jpeg");
			 
			RekoEventOutput reko_output = myService.myRekognitionFunc(reko_input);  
			System.out.println("#### rekog output = " + reko_output.getMessage());
			
			// 2. call trans
			TransEventInput trans_input = new TransEventInput();
			
			String trans_origin_info = reko_output.getMessage();
			trans_input.setText(trans_origin_info);
			trans_input.setSourceLangCode("en");
			trans_input.setTargetLangCode("es");
			 
			TransEventOutput trans_output = myService.myTranslateFunc(trans_input); 
			String trans_target_info = trans_output.getMessage();
			System.out.println("#### rekog output = " + trans_target_info);
			
			// 3. call ddb
			DDBEventInput ddb_input = new DDBEventInput();
			
			String prefix = bucket + "/" + photoPath;
			ddb_input.setPrefix("seon-virginia-2016/images/a.jpeg");
			ddb_input.setPhotoInfo(trans_origin_info);
			ddb_input.setTransInfo(trans_target_info);
			 
			DDBEventOutput ddb_output = myService.myDynamoDBFunc(ddb_input);  
			
			result = "SUCCESS";

		} catch(Exception e) {
			result = "FAIL : " + e.getMessage();
		}
		
		return result;
	}

}
