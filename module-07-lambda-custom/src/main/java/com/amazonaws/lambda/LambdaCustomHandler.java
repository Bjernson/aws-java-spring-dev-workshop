package com.amazonaws.lambda;

import com.amazonaws.lambda.io.CustomEventInput;
import com.amazonaws.lambda.io.CustomEventOutput;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

public class LambdaCustomHandler implements RequestHandler<CustomEventInput, CustomEventOutput> {
	
  private AmazonS3 s3 = AmazonS3ClientBuilder.standard().build();

    @Override
    public CustomEventOutput handleRequest(CustomEventInput input, Context context) {
      context.getLogger().log("Received event: " + input);

      // Get the object from the event and show its content type
      String bucket = "seon-virginia-01";
      String key = "test.png";
      try {
          S3Object response = s3.getObject(new GetObjectRequest(bucket, key));
          String contentType = response.getObjectMetadata().getContentType();
          context.getLogger().log("CONTENT TYPE: " + contentType);
          return new CustomEventOutput(10);
          
      } catch (Exception e) {
          e.printStackTrace();
          context.getLogger().log(String.format(
              "Error getting object %s from bucket %s. Make sure they exist and"
              + " your bucket is in the same region as this function.", key, bucket));
          throw e;
      }
    }

}
