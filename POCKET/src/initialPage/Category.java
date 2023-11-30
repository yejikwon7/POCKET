package initialPage;

public class Category {
	private String categoryName;	// 카테고리 이름
	
	// 카테고리 객체 생성자
	public Category(String categoryName) {
        this.categoryName = categoryName;
    }
	
	// 카테고리 이름 수정
	public void setName(String categoryName) {
    	this.categoryName = categoryName;
    }
}