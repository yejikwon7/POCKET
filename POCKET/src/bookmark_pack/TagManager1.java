package bookmark_pack;

import java.util.ArrayList;
import java.util.Iterator;

public class TagManager1 {
	private ArrayList<Tag> tagManager;
	
	// 태그 배열 생성
	public TagManager1() {
		tagManager = new ArrayList<>();
    }
	
	// 태그 추가
    public void addTag(String tagName) {
        Tag addTag = new Tag(tagName);
        tagManager.add(addTag);
    }
    
    // 태그 삭제
    public void delTag(String tagName) {
        Iterator<Tag> iterator = tagManager.iterator();
        while (iterator.hasNext()) {
        	Tag delTag = iterator.next();
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
    public Tag get(int index) {
        return tagManager.get(index);
    }
    // 태그 배열의 객체를 반환 : name으로 탐색
    public Tag get(String name) {
    	for (int i = 0; i < tagManager.size(); i++) {
    		if (tagManager.get(i).getName().equals(name))
    			return tagManager.get(i);
        }
    	return null;
    }
    
    // 해당 태그 이름이 존재하는지 확인하는 메서드 추가
    public boolean contains(String tagName) {
        for (Tag tag : tagManager) {
            if (tag.getName().equals(tagName)) {
                return true;
            }
        }
        return false;
    }
    
    // 태그 목록을 문자열로 반환
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Tag tag : tagManager) {
            result.append(tag.getName()).append(", ");
        }
        // 마지막 콤마와 공백 제거
        if (result.length() > 0) {
            result.setLength(result.length() - 2);
        }
        return result.toString();
    }

    // 문자열을 파싱하여 태그 추가
    public void parseTags(String tagsString) {
        String[] tagsArray = tagsString.split(", ");
        for (String tagName : tagsArray) {
            addTag(tagName);
        }
    }

 // 태그 배열 교체
    public void setTags(ArrayList<String> tagNames) {
        // 기존 태그 지우기
        tagManager.clear();

        // 새로운 태그 추가
        for (String tagName : tagNames) {
            addTag(tagName);
        }
    }
}
