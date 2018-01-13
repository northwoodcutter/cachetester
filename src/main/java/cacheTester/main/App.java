package cacheTester.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cache.JSONCacheImpl;
import cacheTester.ram.example.CalculateUtils;
import cacheTester.twolevelCache.example.BookSession;
import cacheTester.twolevelCache.example.BookSessionImpl;

public class App {
	private static final Logger LOG = LoggerFactory.getLogger(JSONCacheImpl.class);

	public static void main(String[] args) {
		int pow = 7;
		int fact = CalculateUtils.factorial(pow);
		LOG.info("fact = " + fact);
		
		BookSession session = new BookSessionImpl();
		session.openBook();
		session.closeBook();
	}
}
