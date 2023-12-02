package bookmark_pack;

import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JButton;

public class CategoryManager1 {
	private ArrayList<CategoryData> categoryManager ;
	
	// 카테고리 배열 생성자
	public CategoryManager1() {
		categoryManager = new ArrayList<>();
    }
	
	// 카테고리 추가
    public void addCategory(String categoryName) {
    	CategoryData addCategory = new CategoryData(categoryName);
        categoryManager.add(addCategory);
    }
    
    // 카테고리 삭제
    public void delCategory(String categoryName) {
        Iterator<CategoryData> iterator = categoryManager.iterator();
        while (iterator.hasNext()) {
        	CategoryData delCategory = iterator.next();
            if (delCategory.getName().equals(categoryName)) {
                iterator.remove();
                break;
            }
        }
    }
    
    // 카테고리 배열의 크기를 반환
    public int getSize() {
    	return categoryManager.size();
    }
    
    // 카테고리 배열의 객체를 반환 : index로 탐색
    public CategoryData get(int index) {
        return categoryManager.get(index);
    }
    // 카테고리 배열의 객체를 반환 : name으로 탐색
    public CategoryData get(String name) {
    	for (int i = 0; i < categoryManager.size(); i++) {
    		if (categoryManager.get(i).getName().equals(name))
    			return categoryManager.get(i);
        }
    	return null;
    }
}
