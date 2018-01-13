package cache;

public interface LRU{
	public long getCurrentTime();
	public void removeOldestValueFromCache();
}
