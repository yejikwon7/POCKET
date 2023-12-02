//package bookmark_pack;
package fromYeji;

public class BookmarkData {
    private String title;
    private String category;
    private String tag;
    private int star;
    private String date;
    private String content;

    public BookmarkData(String content, int star, String date,
    		String title, String category, String tag) {
    	this.content = content;
    	this.star = star;
    	this.date = date;
    	this.title = title;
        this.category = category;
        this.tag = tag;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public String getTag() {
        return tag;
    }

    public int getStar() {
        return star;
    }

    public String getDate() {
        return date;
    }

    public String getContent() {
        return content;
    }
}