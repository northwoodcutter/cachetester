package cacheTester.twolevelCache.example;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cache.Cache;
import cache.TwoLevelCacheImpl;

public class BookSessionImpl implements BookSession {
	private static final Logger LOG = LoggerFactory.getLogger(BookSessionImpl.class);
	private static final String BOOK_MARK_KEY = "bookmarkKey";

	private Page mCurrentPage;

	@Override
	public void openBook() {
		mCurrentPage = new Page();
		mCurrentPage.setPageNumder(getBookmark());
		LOG.info("current page number = " + mCurrentPage.getPageNumder());
	}

	@Override
	public void closeBook() {
		addBookmark();
	}

	@Override
	public int getBookmark() {
		Cache<String, Integer> cache = new TwoLevelCacheImpl<String, Integer>();
		if (cache.getCachedObject(BOOK_MARK_KEY) != null) {
			return cache.getCachedObject(BOOK_MARK_KEY);
		} else {
			return 0;
		}
	}

	@Override
	public void addBookmark() {
		Random r = new Random();
		int pageNumber = r.nextInt(300);
		Cache<String, Integer> cache = new TwoLevelCacheImpl<String, Integer>();
		if (cache.getCachedObject(BOOK_MARK_KEY) != null) {
			cache.recacheValue(BOOK_MARK_KEY, pageNumber);
		}
	}
}
