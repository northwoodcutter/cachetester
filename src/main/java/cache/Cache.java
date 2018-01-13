package cache;

public interface Cache <K, V> {
	public void cache(K key,V value);
	public void clear();
	public int getCacheSize();
	public V getCachedObject(K key);
	public void recacheValue(K key, V value);
}
