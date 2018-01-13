package cache;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JSONCacheImpl<K, V> implements Cache<K, V>, LRU {
	private static final Logger LOG = LoggerFactory.getLogger(JSONCacheImpl.class);
	private static final String PATH_TO_CACHE = "C:\\temp\\cache\\";
	private static final int MAX_SIZE_OF_JSON_CACHE = 1000;

	private Map<K, V> mDataMap;
	private Map<K, Long> mTimeMap;

	private File dataCacheFile;
	private File timeCacheFile;

	public JSONCacheImpl() {
		dataCacheFile = initDataJsonFile();
		timeCacheFile = initTimeJsonFile();
		mDataMap = getHashMapFromJson(dataCacheFile);
		mTimeMap = getDateHashMapFromJson(timeCacheFile);
	}

	@Override
	public void cache(K key, V value) {
		if (mDataMap.size() > MAX_SIZE_OF_JSON_CACHE) {
			removeOldestValueFromCache();
		}

		mDataMap.put(key, value);
		mTimeMap.put(key, getCurrentTime());
		writeToCache();
	}

	@Override
	public void clear() {
		mDataMap.clear();
	}

	@Override
	public int getCacheSize() {
		return mDataMap.size();
	}

	@Override
	public V getCachedObject(K key) {
		return (V) mDataMap.get(key);
	}

	private File initTimeJsonFile() {
		File jsonFileDir = new File(PATH_TO_CACHE);
		if (!jsonFileDir.exists()) {
			jsonFileDir.mkdirs();
		}
		File jsonLRUqueue = new File(PATH_TO_CACHE + "queue.json");
		if (!jsonLRUqueue.exists()) {
			try {
				jsonLRUqueue.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return jsonLRUqueue;
	}

	private File initDataJsonFile() {
		File jsonFileDir = new File(PATH_TO_CACHE);
		if (!jsonFileDir.exists()) {
			jsonFileDir.mkdirs();
		}
		File jsonFile = new File(PATH_TO_CACHE + "cache.json");
		if (!jsonFile.exists()) {
			try {
				jsonFile.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return jsonFile;
	}

	@SuppressWarnings("unchecked")
	private Map<K, V> getHashMapFromJson(File jsonFile) {
		Map<K, V> map = new HashMap<K, V>();
		if (jsonFile != null && jsonFile.length() != 0) {
			try {
				map = new ObjectMapper().readValue(jsonFile, HashMap.class);
				return map;
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	private Map<K, Long> getDateHashMapFromJson(File jsonFile) {
		Map<K, Long> map = new HashMap<K, Long>();
		if (jsonFile != null && jsonFile.length() != 0) {
			try {
				map = new ObjectMapper().readValue(jsonFile, HashMap.class);
				return map;
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return map;
	}

	@Override
	public long getCurrentTime() {
		Date date = new Date();
		return date.getTime();
	}

	@Override
	public void removeOldestValueFromCache() {
		K keyOfMin = null;
		long oldestTime = 0;
		for (K key : mTimeMap.keySet()) {
			if (oldestTime == 0 || oldestTime > (mTimeMap.get(key))) {
				oldestTime = (long) mTimeMap.get(key);
				keyOfMin = key;
			}
		}
		mDataMap.remove(keyOfMin);
		mTimeMap.remove(keyOfMin);
	}

	@Override
	public void recacheValue(K key, V value) {
		mDataMap.remove(key);
		mDataMap.put(key, value);
		mTimeMap.remove(key);
		mTimeMap.put(key, getCurrentTime());
		writeToCache();
	}
	
	private void writeToCache() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(dataCacheFile, mDataMap);
			mapper.writeValue(timeCacheFile, mTimeMap);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
