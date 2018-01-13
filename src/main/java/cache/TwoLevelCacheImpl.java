package cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TwoLevelCacheImpl<K, V> implements Cache<K, V> {
	private static final Logger logger = LoggerFactory.getLogger(TwoLevelCacheImpl.class);

	private Cache<K, V> mRamCache;
	private Cache<K, V> mJSONCache;

	public TwoLevelCacheImpl() {
		mRamCache = new RamCacheImpl<K, V>();
		mJSONCache = new JSONCacheImpl<K, V>();
		logger.info("TwoLevelCacheImpl()");
	}

	@Override
	public void cache(K key, V value) {
		mRamCache.cache(key, value);
		mJSONCache.cache(key, value);
	    logger.info("TwoLevelCacheImpl cache");
	}

	@Override
	public void clear() {
		mRamCache.clear();
		mJSONCache.clear();
	}

	@Override
	public int getCacheSize() {
		return mRamCache.getCacheSize() + mJSONCache.getCacheSize();
	}

	@Override
	public V getCachedObject(K key) {
		V object = mRamCache.getCachedObject(key);
		if (object != null) {
			return object;
		} else {
			object = mJSONCache.getCachedObject(key);
			if (object != null) {
				return object;
			}
			else {
				return null;
			}
		}
	}

	@Override
	public void recacheValue(K key, V value) {
		mRamCache.recacheValue(key, value);
		mJSONCache.recacheValue(key, value);
	}
}
