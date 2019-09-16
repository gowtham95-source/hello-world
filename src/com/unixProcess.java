package com;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.json.JSONObject;

/**
 * 
 * @author Gowtham M
 * Since 19.09
 *
 */
public class unixProcess implements osProcess {
	
	// concurrentHashMap is thread safe.
	/**  the constant storeMap */
	private static final Map<String, DataValue> storeMap = new ConcurrentHashMap<>();

	@Override
	public void insert(final String key, final JSONObject value) {
	    storeMap.put(key, new DataValue(value, 0));
	}
	
	@Override
	public void insert(final String key, final JSONObject value, long ttl) {
	    storeMap.put(key, new DataValue(value, ttl));
	}
	
	@Override
	public JSONObject get(String key) {
		DataValue data = storeMap.get(key);
		JSONObject result = null;
        if (data != null) {
            long diff = TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis() - data.insertTime);
            if (diff >= data.ttl && data.ttl != 0) {
            	storeMap.remove(key);
                data.value = null;
                data.ttl = 0;
            }
            result = data.value;
        }
        return result;
	}
	
	@Override
	public void delete(String key) {
		storeMap.remove(key);
		
	}
	
	/**
	 * DataValue class will store the data with time
	 * @author Gowtham M
	 */
	private final class DataValue {
	        public JSONObject value;
	        public long insertTime;
	        public long ttl;

	        DataValue(JSONObject value, long ttl) {
	            this.value = value;
	            this.ttl = ttl;
	            insertTime = System.currentTimeMillis();
	            }
	        }
}
