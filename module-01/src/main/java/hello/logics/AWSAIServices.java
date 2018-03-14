package hello.logics;

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.rekognition.model.AmazonRekognitionException;
import com.amazonaws.services.rekognition.model.DetectLabelsRequest;
import com.amazonaws.services.rekognition.model.DetectLabelsResult;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.Label;
import com.amazonaws.services.rekognition.model.S3Object;
import com.amazonaws.services.translate.AmazonTranslate;
import com.amazonaws.services.translate.AmazonTranslateClient;
import com.amazonaws.services.translate.AmazonTranslateClientBuilder;
import com.amazonaws.services.translate.model.TranslateTextRequest;
import com.amazonaws.services.translate.model.TranslateTextResult;
import java.util.List;

public class AWSAIServices {
	
	public void retrieveInformation(String bucket, String photo, Regions region)
	{

    AWSCredentials credentials;
    try {
        credentials = new ProfileCredentialsProvider("default").getCredentials();
    } catch(Exception e) {
       throw new AmazonClientException("Cannot load the credentials from the credential profiles file. "
        + "Please make sure that your credentials file is at the correct "
        + "location (/Users/userid/.aws/credentials), and is in a valid format.", e);
    }

    AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder
  	         .standard()
  	         .withRegion(region)
  	         .withCredentials(new AWSStaticCredentialsProvider(credentials))
  	         .build();

    DetectLabelsRequest request = new DetectLabelsRequest()
  		  .withImage(new Image()
  		  .withS3Object(new S3Object()
  		  .withName(photo).withBucket(bucket)))
  		  .withMaxLabels(10)
  		  .withMinConfidence(75F);

    try {
       DetectLabelsResult result = rekognitionClient.detectLabels(request);
       List <Label> labels = result.getLabels();

       System.out.println("Detected labels for " + photo);
       for (Label label: labels) {
          System.out.println("#### = " + label.getName() + ": " + label.getConfidence().toString());
       }
    } catch(AmazonRekognitionException e) {
       e.printStackTrace();
    }
	}
	
	public String translate(String text, String sourceLangCode, String targetLangCode, Regions region)
	{
		return null;
	}

}
