package api.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndPoints;
import api.endpoints.UserEndPoints2;
import api.payload.User;
import io.restassured.response.Response;

public class UserTests {
	Faker faker;
	User userPayload;
	public Logger logger;
	
	@BeforeClass
	public void setup()
	{
		
		faker = new Faker();
		userPayload = new User();
		
		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setUsername(faker.name().username());
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		userPayload.setPassword(faker.internet().password(5,10));
		userPayload.setPhone(faker.phoneNumber().cellPhone());
		
		//logs
		logger = (Logger) LogManager.getLogger(this.getClass());
		
	}
	@Test(priority=1)
	public void testPostUser()
	{
		logger.info("********Creating user *****");
//		Response response = UserEndPoints.createUser(userPayload);
		Response response = UserEndPoints2.createUser(userPayload);
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
		logger.info("*******user is created**********");
	}
	@Test(priority=2)
	public void testGetUserByName()
	{
		logger.info("************Reading User info***************");
//		Response response = UserEndPoints.readUser(this.userPayload.getUsername());
		Response response = UserEndPoints2.readUser(this.userPayload.getUsername());
		response.then().log().all();
		Assert.assertEquals(response.statusCode(), 200);
		logger.info("************Reading User is displayed***************");

	}
	@Test(priority=3)
	public void testUpdateByName()
	{
		logger.info("************Updating User info***************");

		//update data using payload
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		
		
//		Response response = UserEndPoints.updateUser(userPayload,this.userPayload.getUsername());
		Response response = UserEndPoints2.updateUser(userPayload,this.userPayload.getUsername());
		//response.then().log().all();
		response.then().log().body();
		//response.then().log().body().statusCode(200);//alternate of below
		
		Assert.assertEquals(response.getStatusCode(), 200);
		logger.info("********* User updated***************");

		//Checking data after update
		Response responseAfterupdate = UserEndPoints.readUser(this.userPayload.getUsername());
		//response.then().log().all();
		Assert.assertEquals(responseAfterupdate.getStatusCode(), 200);
		
	}
	@Test(priority=4)
	public void testDeleteUserByName()
	{
		logger.info("************Deleting user***************");

	//Response response =	UserEndPoints.deleteUser(this.userPayload.getUsername());
	Response response =	UserEndPoints2.deleteUser(this.userPayload.getUsername());
	Assert.assertEquals(response.getStatusCode(), 200);
	logger.info("************user deleted***************");

	}

}
