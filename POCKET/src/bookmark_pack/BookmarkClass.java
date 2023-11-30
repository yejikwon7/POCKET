//package bookmark_pack;
//
//import java.util.Date;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//
//class BookmarkClass {
//    private int bookId;
//    //private int userId;
//    private String title;
//    private String content;
//    private LocalDate currentDate = LocalDate.now();
//    private Date createdDate;
//    private Date modifiedDate;
//    private int importance;
//    private int year;
//    private int month;
//    private int day;
////    private TagManager b_tags;
//    private boolean save;
//    
//    public BookmarkClass(String title, int importance) {
//    	this.title = title;
//		this.year = currentDate.getYear();
//		this.month = currentDate.getMonthValue();
//		this.day = currentDate.getDayOfMonth();
//		this.importance = importance;
////		this.b_tags = new TagManager();
//    }
//    
//    // 북마크 객체 생성자
//    public BookmarkClass(int bookId, String title, String content, int importance) {
//        this.bookId = bookId;
//        //this.userNo = userNo;
//        this.title = title;
//        this.content = content;
//        this.importance = importance;
//        this.createdDate = new Date();
//        this.modifiedDate = new Date();
//    }
//    
//    // 북마크 작성날짜(연도) 반환
// 	public int getYear() {
// 		return year;
// 	}
// 	
// 	// 북마크 작성날짜(월) 반환
// 	public int getMonth() {
// 		return month;
// 	}
// 	
// 	// 북마크 작성날짜(일) 반환
// 	public int getDay() {
// 		return day;
// 	}
// 	
// 	// 북마크 작성날짜(연도) 업데이트
// 	public void updateYear() {
// 		this.year = currentDate.getYear();
// 	}
// 		
// 	// 북마크 작성날짜(월) 업데이트
// 	public void updateMonth() {
// 		this.month = currentDate.getMonthValue();
// 	}
// 		
// 	// 북마크 작성날짜(일) 업데이트
// 	public void updateDay() {
// 		this.day = currentDate.getDayOfMonth();
// 	}
//    
//    public int getBookId() {
//    	return bookId;
//    }
//    
//    public void setBookId(int bookId) {
//    	this.bookId = bookId;
//    }
//    
//    public String getTitle() {
//    	return title;
//    }
//    
//    public void setTitle(String title) {
//    	this.title = title;
//    }
//    
//    public String getContent() {
//    	return content;
//    }
//    
//    public void setContent(String content) {
//        this.content = content;
//        this.modifiedDate = new Date();
//    }
//    
//    public Date getCreatedDate() {
//    	return createdDate;
//    }
//    
//    public void setCreatedDate(Date createdDate) {
//    	this.createdDate = createdDate;
//    }
//    
//    public Date getModifiedDate() {
//    	return modifiedDate;
//    }
//
//    public void setModifiedDate(Date modifiedDate) {
//        this.modifiedDate = modifiedDate;
//    }
//    
//    public int getImportance() {
//    	return importance;
//    }
//    
//    public void setimportance(int importance) {
//    	this.importance = importance;
//    }
//    
//    public boolean isSave() {
//    	return save;
//    }
//    
//    // 북마크의 태그 배열 반환
//// 	public TagManager getTagManager() {
//// 		return b_tags;
//// 	}
//
//    // Getter 및 Setter 메소드
//
//    @Override
//    public String toString() {
//        return "Bookmark{" +
//                "bookId =" + bookId +
//                ", title='" + title + '\'' +
//                ", content='" + content + '\'' +
//                ", importance='" + importance + '\'' +
//                ", createdDate='" + createdDate + '\'' +
//                ", modifiedDate='" + modifiedDate + '\'' +
//                '}';
//    }
//}
//
//// 임시저장
//
//class Draft {
//    private int draftId;
//    private String title;
//    private String content;
//    private int star;
//    private Date createdDate;
//
//    public Draft(int draftId, String title, String content, int star) {
//        this.draftId = draftId;
//        this.title = title;
//        this.content = content;
//        this.star = star;
//        this.createdDate = new Date();
//    }
//
//    // Getter 및 Setter 메서드
//
//    @Override
//    public String toString() {
//        return "Draft{" +
//                "draftId=" + draftId +
//                ", title='" + title + '\'' +
//                ", content='" + content + '\'' +
//                ", star='" + star + '\'' +
//                ", createdDate=" + createdDate +
//                '}';
//    }
//}
//
//class ShowBookmark {
//    private List<BookmarkClass> bookmarks;
//    private List<Draft> drafts;
//
//    public ShowBookmark() {
//        this.bookmarks = new ArrayList<>();
//        this.drafts = new ArrayList<>();
//    }
//
//    public void addBookmark(BookmarkClass bookmark) {
//    	bookmarks.add(bookmark);
//    }
//
//    public void deleteBookmark(int bookId) {
//    	bookmarks.removeIf(bookmark -> bookmark.getBookId() == bookId);
//    }
//
//    public List<BookmarkClass> getBookmarks() {
//        return new ArrayList<>(bookmarks);
//    }
//    
//    public void saveDraft(Draft draft) {
//        drafts.add(draft);
//    }
//
//    public List<Draft> getDrafts() {
//        return new ArrayList<>(drafts);
//    }
//
//    // 기타 게시판 관련 메서드들...
//
//    public static void main(String[] args) {
//        // 간단한 테스트 코드
//    	ShowBookmark showBookmark = new ShowBookmark();
//        
//        // 임시 저장
//        Draft draft1 = new Draft(1, "임시 저장 글", "임시 저장 글 내용입니다.", 3);
//        showBookmark.saveDraft(draft1);
//
//        BookmarkClass bookmark1 = new BookmarkClass(1, "첫 번째 글", "안녕하세요. 첫 번째 글입니다.", 5);
//        BookmarkClass bookmark2 = new BookmarkClass(2, "두 번째 글", "두 번째 글입니다. 반갑습니다.", 4);
//
//        showBookmark.addBookmark(bookmark1);
//        showBookmark.addBookmark(bookmark2);
//
//        List<BookmarkClass> allBookmarks = showBookmark.getBookmarks();
//        for (BookmarkClass bookmark : allBookmarks) {
//            System.out.println(bookmark);
//        }
//        
//        // 임시 저장글 목록 조회
//        List<Draft> allDrafts = showBookmark.getDrafts();
//        for (Draft draft : allDrafts) {
//            System.out.println(draft);
//        }
//
//        // 게시글 수정 예시: createdDate는 변경하지 않고 modifiedDate만 갱신
//        BookmarkClass modifiedBookmark = allBookmarks.get(0);
//        modifiedBookmark.setContent("수정된 내용입니다.");
//        modifiedBookmark.setModifiedDate(new Date());
//
//        System.out.println("수정 후 게시글 목록:");
//        allBookmarks = showBookmark.getBookmarks();
//        for (BookmarkClass bookmark : allBookmarks) {
//            System.out.println(bookmark);
//        }
//    }
//}