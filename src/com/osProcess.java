package com;

import org.json.JSONObject;

/**
 * @author Gowtham M
 * Since 19.09
 */
public interface osProcess{

	/**
	 * insert method is used to insert the value in customDb
	 * @param String key
	 * @param JSONObject value
	 */
	public void insert(String key, JSONObject value);

	/**
	 * insert method is used to insert the value with time in customDb.
	 * key will be expired after the given time
	 * @param String key
	 * @param JSONObject value
	 * @param long timeToLive
	 */
	void insert(String key, JSONObject value, long timeToLive);

	/**
	 * get method is used to fetch the value from customDb using key 
	 * @param String key
	 * @return JSONObject
	 */
	public JSONObject get(String key);

	/**
	 * delete method is used to remove the value from customDb using key
	 * @param key
	 */
	public void delete(String key);


}
