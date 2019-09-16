package com;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import static org.junit.Assert.*;
import  org.junit.Test;

/**
 * customDbTest file for test the customDb
 * @author Gowtham M
 * Since 19.09
 */
@RunWith(MockitoJUnitRunner.class)
public class customDbTest {
	private static final customDb obj = new customDb();
	@Test
	public void insertTest() throws JSONException{
		JSONObject objJson = new JSONObject();
		objJson.put("test", true);
		// user inserts data with default time(key will not be expired at any time).
		obj.create("freshWorks", objJson);
	}
	@Test
	public void insertWithTimeTest() throws JSONException{
		JSONObject objJson = new JSONObject();
		objJson.put("testAssignMent", true);
		obj.createWithTime("freshWorksAssignment", objJson, 1);
		// user inserts data with specific time(key will be expired with given time)
		assertEquals("The given key is inserted", objJson, obj.get("freshWorksAssignment"));
	}
	@Test
	public void readTest() throws JSONException{
		JSONObject objJson = new JSONObject();
		objJson.put("getTest", true);
		obj.create("readTest", objJson);
		// get the data from customDb
		assertEquals("check read test", objJson, obj.get("readTest"));
	}
	@Test
	public void insertWithTimeExpireKeyTest() throws JSONException, InterruptedException{
		JSONObject objJson = new JSONObject();
		objJson.put("testAssignMent1", true);
		obj.createWithTime("freshWorksAssignment1", objJson, 1);
		// before expiring key output(time given is 1 minute)
		assertEquals("check read test", objJson, obj.get("freshWorksAssignment1"));
		Thread.sleep(60000);
		// after expiring key output(after 1 minute key will be expired) will be null.
		assertEquals("check read test", null, obj.get("freshWorksAssignment1"));
	}
	@Test
	public void deleteTest() throws JSONException{
		JSONObject objJson = new JSONObject();
		objJson.put("testAssignMentDelete", true);
		obj.createWithTime("freshWorksAssignmentDelete", objJson, 1);
		assertEquals("The given key is inserted", objJson, obj.get("freshWorksAssignmentDelete"));
		obj.delete("freshWorksAssignmentDelete");
		assertEquals("The given key is deleted", null, obj.get("freshWorksAssignmentDelete"));
		// check log error
	}
	@Test
	public void moreThanOneUserTest(){
		// if we try to attempt multiple client process it will show 
		// "only one client process is allowed at any given time" log error.
		// we cannot create more than instance and access the customDb. 
        // customDb object = new customDb();
	}
	@Test
	public void checkKeySizeTest() throws JSONException{
		JSONObject objJson = new JSONObject();
		objJson.put("checkSize", true);
		obj.create("abcdefghijklmnopqrstuvwxyzabcdefghijklmno", objJson);
		// check log error
		// "May be the given parameters is not valid. Please give maximum String length is 32 
		// and JSONObject capacity is 16kb"
	}
}
