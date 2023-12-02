package bookmark_pack;

import java.util.ArrayList;
import java.util.Iterator;

public class BookmarkManager {
	private ArrayList<Bookmark> bookmarkManager;
	
	// 북마크 배열 생성자
	public BookmarkManager() {
		bookmarkManager = new ArrayList<>();
	}
		
	// 북마크 추가
	public void addBookmark(String title, int importance) {
		Bookmark addBookmark = new Bookmark(title, importance);
		bookmarkManager.add(addBookmark);
	}
	    
	// 북마크 삭제
	public void delBookmark() {
		
	}
	
    // 북마크 배열의 크기를 반환
    public int getSize() {
    	return bookmarkManager.size();
    }
    
    // 북마크 배열의 객체를 반환 : index로 탐색
    public Bookmark get(int index) {
        return bookmarkManager.get(index);
    }
    
    // 북마크 배열의 객체의 객체를 반환 : title로 탐색
    public Bookmark get(String title) {
    	int index = -1;
    	for (int i = 0; i < bookmarkManager.size(); i++) {
    		if (bookmarkManager.get(i).getTitle().equals(title)) {
    			index = i;
    			break;
    		}
    	}
    	return (index != -1) ? bookmarkManager.get(index) : null;
    }
}
