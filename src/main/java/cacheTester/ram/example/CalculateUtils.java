package cacheTester.ram.example;

import cache.Cache;
import cache.RamCacheImpl;

public class CalculateUtils {

	public static int factorial(int pow) {
		Cache<Integer, Integer> ramCache = new RamCacheImpl<Integer, Integer>();
		ramCache.cache(0, 1);
		int key = 0;
		while (key < pow) {
			int result = ramCache.getCachedObject(key) * (key + 1);
			ramCache.cache(key + 1, result);
			key++;
		}
		return ramCache.getCachedObject(pow);
	}
}
