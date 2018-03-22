package hello.repository;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.GetItemRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ResourceInUseException;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import hello.model.PhotoInfo;

@SpringBootTest
public class DynamoDBTest {
	 private AmazonDynamoDB amazonDynamoDB;
	 private DynamoDBMapper dynamoDBMapper;
	 
	 @Test
	 public void testDDB() {
		  amazonDynamoDB = AmazonDynamoDBClientBuilder.standard()
	   .build();
		  dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);

		  try {
		  	// create table
			  CreateTableRequest tableRequest = dynamoDBMapper.generateCreateTableRequest(PhotoInfo.class);
			  tableRequest.setProvisionedThroughput(new ProvisionedThroughput(1L, 1L));
			  amazonDynamoDB.createTable(tableRequest);
			} catch (ResourceInUseException e) {
				System.out.println("\n####### Table already exist!\n");
			}
		  
		  GetItemRequest request = null;
		  HashMap<String,AttributeValue> key_to_get = new HashMap<String,AttributeValue>();

		  key_to_get.put("id", new AttributeValue("c4691cd0-75a1-4b39-a967-dc083cb6fbf3"));
		  request = new GetItemRequest()
		  					.withKey(key_to_get)
		  					.withTableName("PhotoInfo");
		  Map<String,AttributeValue> returned_item = amazonDynamoDB.getItem(request).getItem();
		  
		  assertEquals(returned_item.size(), 5);
		  
	 }
}
