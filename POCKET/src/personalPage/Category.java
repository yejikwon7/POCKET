package personalPage;

import java.util.ArrayList;
import java.util.Iterator;

public class Category {
	private String categoryName;
	private TagManager c_tags;

	// 카테고리 객체 생성자
	public Category(String categoryName) {
        this.categoryName = categoryName;
        this.c_tags = new TagManager();
    }
	
	// 카테고리 이름 반환
	public String getName() {
        return categoryName;
    }
	
	// 카테고리 이름 수정
	public void setName(String categoryName) {
    	this.categoryName = categoryName;
    }
	
	// 카테고리의 태그 배열 반환
	public TagManager getTagManager() {
		return c_tags;
	}
}