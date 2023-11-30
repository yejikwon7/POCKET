package initialPage;

public class Tag {
	private String tagName;
	
	// 태그 객체 생성자
	public Tag(String tagName) {
        this.tagName = tagName;
    }
	
	// 태그 이름 수정
	public void setName(String tagName) {
    	this.tagName = tagName;
    }
}
