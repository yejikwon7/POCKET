package bookmark_pack;

import java.time.LocalDate;
import java.util.ArrayList;

public class Bookmark {
	private String title;
	private LocalDate currentDate = LocalDate.now();
	private int year;
	private int month;
	private int day;
	private int importance;
	private String content;
	private CategoryManager1 category1;
	private TagManager1 b_tags;
	private String date;
	private String category;
	private int id;
	
	public Bookmark(String title, int importance) {
		this.title = title;
		this.year = currentDate.getYear();
		this.month = currentDate.getMonthValue();
		this.day = currentDate.getDayOfMonth();
		this.importance = importance;
		this.b_tags = new TagManager1();
	}
	
	public Bookmark(int id, String category) {
		this.id = id;
		this.category = category;
	}
	
	public Bookmark(int id, String category, String b_tags) {
		this.id = id;
		category = "1";
		this.b_tags = new TagManager1();
		this.b_tags.parseTags(b_tags);
	}
	
	public Bookmark(String title, int importance, String b_tags) {
		this.title = title;
		this.importance = importance;
		this.year = currentDate.getYear();
		this.month = currentDate.getMonthValue();
		this.day = currentDate.getDayOfMonth();
		this.b_tags = new TagManager1();
	}

	// 북마크 객체 생성자
	public Bookmark(String content, int importance, String date,
    		String title, String category, String b_tags, int id) {
		this.title = title;
		this.content = content;
		this.date = date;
		this.year = currentDate.getYear();
		this.month = currentDate.getMonthValue();
		this.day = currentDate.getDayOfMonth();
		this.importance = importance;
		this.category = category;
		this.b_tags = new TagManager1();
		this.b_tags.parseTags(b_tags);
		this.id = id;
	}
	
	// 북마크 제목 반환
	public String getTitle() {
		return title;
	}
	
	public String getContent() {
		return content;
	}
	
	// 북마크 작성날짜(연도) 반환
	public int getYear() {
		return year;
	}
	
	// 북마크 작성날짜(월) 반환
	public int getMonth() {
		return month;
	}
	
	// 북마크 작성날짜(일) 반환
	public int getDay() {
		return day;
	}
	
	// 북마크 작성날짜(연도) 업데이트
	public void updateYear() {
		this.year = currentDate.getYear();
	}
		
	// 북마크 작성날짜(월) 업데이트
	public void updateMonth() {
		this.month = currentDate.getMonthValue();
	}
		
	// 북마크 작성날짜(일) 업데이트
	public void updateDay() {
		this.day = currentDate.getDayOfMonth();
	}
	
	// 북마크 중요도 반환
	public int getImportance() {
		return importance;
	}

	// 북마크의 태그 배열 반환
    public TagManager1 getTagManager() {
        return b_tags;
    }

    // 북마크의 태그 배열을 문자열로 반환
    public String getTagManagerAsString() {
        return b_tags.toString();
    }

    // 북마크의 태그 배열을 문자열로 설정
    public void setTagManagerFromString(String tagsString) {
        b_tags.parseTags(tagsString);
    }
	
	public String getDate() {
        return date;
    }

	public String getCategory() {
		return category;
	}
	
	public int getId() {
		return id;
	}
	
	// 아이디에 해당하는 사용자의 카테고리를 불러와서 설정
    public void loadCategoriesFromDB() {
        ArrayList<String> categories = BookmarkDB.getCategories(id);
        for (String categoryName : categories) {
            category1.addCategory(categoryName);
        }
    }

    // 아이디와 카테고리에 해당하는 사용자의 태그를 불러와서 설정
    public void loadTagsFromDB(String categoryName) {
        ArrayList<String> tags = BookmarkDB.getTags(id, categoryName);
        category1.get(categoryName).getTagManager().setTags(tags);
    }
}
