package cache;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RamCacheImpl<K, V> implements Cache<K, V>, LRU {
	private static final int MAX_SIZE_OF_RAM_CACHE = 100;

	private Map<K, V> mRamMap;
	private Map<K, Long> mTimeMap;

	public RamCacheImpl() {
		mRamMap = new HashMap<K, V>();
		mTimeMap = new HashMap<K, Long>();
	}

	@Override
	public void cache(K key, V value) {
		if (mRamMap.get(key) == null) {
			if (mRamMap.size() > MAX_SIZE_OF_RAM_CACHE) {
				removeOldestValueFromCache();
			}
			mRamMap.put(key, value);
			mTimeMap.put(key, getCurrentTime());
		}
		else {
			recacheValue(key, value);
		}
	}

	@Override
	public void clear() {
		mRamMap.clear();
	}

	@Override
	public int getCacheSize() {
		return mRamMap.size();
	}

	@Override
	public V getCachedObject(K key) {
		return (V) mRamMap.get(key);
	}

	@Override
	public void recacheValue(K key, V value) {
		mRamMap.remove(key);
		mRamMap.put(key, value);
		mTimeMap.remove(key);
		mTimeMap.put(key, getCurrentTime());
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
		mTimeMap.remove(keyOfMin);
		mTimeMap.remove(keyOfMin);
	}
}
