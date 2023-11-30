package bookmark_pack;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

class Bookmark {
    private int bookId;
    //private int userId;
    private String title;
    private String content;
    private Date createdDate;
    private Date modifiedDate;
    private int star;
    private boolean save;

    public Bookmark(int bookId, String title, String content, int star) {
        this.bookId = bookId;
        //this.userNo = userNo;
        this.title = title;
        this.content = content;
        this.star = star;
        this.createdDate = new Date();
        this.modifiedDate = new Date();
    }
    
    public int getBookId() {
    	return bookId;
    }
    
    public void setBookId(int bookId) {
    	this.bookId = bookId;
    }
    
    public String getTitle() {
    	return title;
    }
    
    public void setTitle(String title) {
    	this.title = title;
    }
    
    public String getContent() {
    	return content;
    }
    
    public void setContent(String content) {
        this.content = content;
        this.modifiedDate = new Date();
    }
    
    public Date getCreatedDate() {
    	return createdDate;
    }
    
    public void setCreatedDate(Date createdDate) {
    	this.createdDate = createdDate;
    }
    
    public Date getModifiedDate() {
    	return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
    
    public int getStar() {
    	return star;
    }
    
    public void setStar(int star) {
    	this.star = star;
    }
    
    public boolean isSave() {
    	return save;
    }

    // Getter 및 Setter 메소드

    @Override
    public String toString() {
        return "Bookmark{" +
                "bookId =" + bookId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", star='" + star + '\'' +
                ", createdDate='" + createdDate + '\'' +
                ", modifiedDate='" + modifiedDate + '\'' +
                '}';
    }
}

// 임시저장

class Draft {
    private int draftId;
    private String title;
    private String content;
    private int star;
    private Date createdDate;

    public Draft(int draftId, String title, String content, int star) {
        this.draftId = draftId;
        this.title = title;
        this.content = content;
        this.star = star;
        this.createdDate = new Date();
    }

    // Getter 및 Setter 메서드

    @Override
    public String toString() {
        return "Draft{" +
                "draftId=" + draftId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", star='" + star + '\'' +
                ", createdDate=" + createdDate +
                '}';
    }
}

class ShowBookmark {
    private List<Bookmark> bookmarks;
    private List<Draft> drafts;

    public ShowBookmark() {
        this.bookmarks = new ArrayList<>();
        this.drafts = new ArrayList<>();
    }

    public void addBookmark(Bookmark bookmark) {
    	bookmarks.add(bookmark);
    }

    public void deleteBookmark(int bookId) {
    	bookmarks.removeIf(bookmark -> bookmark.getBookId() == bookId);
    }

    public List<Bookmark> getBookmarks() {
        return new ArrayList<>(bookmarks);
    }
    
    public void saveDraft(Draft draft) {
        drafts.add(draft);
    }

    public List<Draft> getDrafts() {
        return new ArrayList<>(drafts);
    }

    // 기타 게시판 관련 메서드들...

    public static void main(String[] args) {
        // 간단한 테스트 코드
    	ShowBookmark showBookmark = new ShowBookmark();
        
        // 임시 저장
        Draft draft1 = new Draft(1, "임시 저장 글", "임시 저장 글 내용입니다.", 3);
        showBookmark.saveDraft(draft1);

        Bookmark bookmark1 = new Bookmark(1, "첫 번째 글", "안녕하세요. 첫 번째 글입니다.", 5);
        Bookmark bookmark2 = new Bookmark(2, "두 번째 글", "두 번째 글입니다. 반갑습니다.", 4);

        showBookmark.addBookmark(bookmark1);
        showBookmark.addBookmark(bookmark2);

        List<Bookmark> allBookmarks = showBookmark.getBookmarks();
        for (Bookmark bookmark : allBookmarks) {
            System.out.println(bookmark);
        }
        
        // 임시 저장글 목록 조회
        List<Draft> allDrafts = showBookmark.getDrafts();
        for (Draft draft : allDrafts) {
            System.out.println(draft);
        }

        // 게시글 수정 예시: createdDate는 변경하지 않고 modifiedDate만 갱신
        Bookmark modifiedBookmark = allBookmarks.get(0);
        modifiedBookmark.setContent("수정된 내용입니다.");
        modifiedBookmark.setModifiedDate(new Date());

        System.out.println("수정 후 게시글 목록:");
        allBookmarks = showBookmark.getBookmarks();
        for (Bookmark bookmark : allBookmarks) {
            System.out.println(bookmark);
        }
    }
}