package bookmark_pack;

import java.util.ArrayList;
import java.util.Iterator;

public class TagManager1 {
	private ArrayList<TagData> tagManager;
	
	// 태그 배열 생성
	public TagManager1() {
		tagManager = new ArrayList<>();
    }
	
	// 태그 추가
    public void addTag(String tagName) {
        TagData addTag = new TagData(tagName);
        tagManager.add(addTag);
    }
    
    // 태그 삭제
    public void delTag(String tagName) {
        Iterator<TagData> iterator = tagManager.iterator();
        while (iterator.hasNext()) {
        	TagData delTag = iterator.next();
            if (delTag.getName().equals(tagName)) {
                iterator.remove();
                break;
            }
        }
    }
    
    // 태그 배열의 크기를 반환
    public int getSize() {
    	return tagManager.size();
    }
    
    // 태그 배열의 객체를 반환 : index로 탐색
    public TagData get(int index) {
        return tagManager.get(index);
    }
    // 태그 배열의 객체를 반환 : name으로 탐색
    public TagData get(String name) {
    	for (int i = 0; i < tagManager.size(); i++) {
    		if (tagManager.get(i).getName().equals(name))
    			return tagManager.get(i);
        }
    	return null;
    }
}
