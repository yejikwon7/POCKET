package bookmark_pack;

import java.util.ArrayList;
import java.util.Iterator;

public class CategoryData {
	private String categoryName;
	private TagManager1 c_tags;

	// 카테고리 객체 생성자
	public CategoryData(String categoryName) {
        this.categoryName = categoryName;
        this.c_tags = new TagManager1();
    }
	
	// 카테고리 이름 반환
	public String getName() {
        return categoryName;
    }
	
	// 카테고리 이름 수정
	public void setName(String categoryName) {
    	this.categoryName = categoryName;
    }
	
	//카테고리의 태그 배열 반환
	public TagManager1 getTagManager() {
		return c_tags;
	}
}
