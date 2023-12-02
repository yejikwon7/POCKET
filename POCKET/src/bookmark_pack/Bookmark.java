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
	private TagManager1 b_tags;
	private String date;
	private String category;
	
	public Bookmark(String title, int importance) {
		this.title = title;
		this.year = currentDate.getYear();
		this.month = currentDate.getMonthValue();
		this.day = currentDate.getDayOfMonth();
		this.importance = importance;
		this.b_tags = new TagManager1();
	}

	// 북마크 객체 생성자
	public Bookmark(String content, int star, String date,
    		String title, String category, String b_tags) {
		this.title = title;
		this.content = content;
		this.date = date;
		this.year = currentDate.getYear();
		this.month = currentDate.getMonthValue();
		this.day = currentDate.getDayOfMonth();
		this.importance = importance;
		this.category = category;
		this.b_tags = new TagManager1();
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
	
	public String getDate() {
        return date;
    }

	public String getCategory() {
		return category;
	}
}
