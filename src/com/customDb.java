package com;


import java.io.IOException;
import java.net.BindException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.Objects;

import org.json.JSONObject;

import lombok.extern.slf4j.Slf4j;

/**
 * customDb class will act as a local storage
 * @author Gowtham M
 * Since 19.09
 */
@Slf4j
public class customDb {

    /** the Constant os*/
	private static final osProcess os = new unixProcess();
	/** the Constant PORT*/
	private static final int PORT = 9081;
	/** the Constant socket*/
	private static ServerSocket socket;
	
	public customDb(){
		try{
			socket = new ServerSocket(PORT, 0,InetAddress.getByAddress(new byte[] {127,0,0,1}));
		}catch(BindException e){
			log.error("only one client process is allowed at any time");
			System.exit(1);
		}catch (IOException e) {
			log.error("unexpected Error");
			System.exit(2);
		}
	}

	/**
	 * create method is used to insert the value in customDb
	 * @param String key
	 * @param JSONObject value
	 */
	public void create(String key, JSONObject value){
		if(key.length() <= 32 && value.length() <= 16000){
			if(Objects.isNull(os.get(key))){
				os.insert(key, value);
			}else{
				log.error("The given key is already present");
			}
		}else{
			log.error("May be the given parameters is not valid. Please give maximum String length "
					+ "is 32 and JSONObject capacity is 16kb");
		}
	}

	/**
	 * createWithTime method is used to insert the value with time in customDb.
	 * key will be expired after the given time
	 * @param String key
	 * @param JSONObject value
	 * @param long timeToLive
	 */
	public void createWithTime(String key, JSONObject value, long timeToLive){
		if(key.length() <= 32 && value.length() <= 16000){
			if(Objects.isNull(os.get(key))){
				os.insert(key, value, timeToLive);
			}else{
				log.error("The given key is already present");
			}
		}else{
			log.error("May be the given parameters is not valid. Please give maximum String length "
					+ "is 32 and JSONObject capacity is 16kb");
		}
	}

	
	/**
	 * get method is used to fetch the value from customDb using key 
	 * @param String key
	 * @return JSONObject
	 */
	public JSONObject get(String key){
		if(Objects.isNull(os.get(key))){
			log.error("The given key is not present in customDb");
		}
		return os.get(key);	
	}

	/**
	 * delete method is used to remove the value from customDb using key
	 * @param key
	 */
	public void delete(String key){
		os.delete(key);
	}
}
