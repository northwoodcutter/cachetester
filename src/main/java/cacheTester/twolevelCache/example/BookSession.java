package cacheTester.twolevelCache.example;

public interface BookSession {
     public void openBook();
     public void closeBook();
     public int getBookmark();
     public void addBookmark();
}
