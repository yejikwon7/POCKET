package bookmark_pack;

import java.sql.*;
import java.util.ArrayList;

public class BookmarkDB {
    public static Connection makeConnection() {
        String url = "jdbc:mysql://localhost/pocket?characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false";
        String id = "root";
        String password = "Pqlamz000!";
        Connection con = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, id, password);
            System.out.println("Database connection success!");
        } catch (ClassNotFoundException e) {
            System.out.println("Cannot find driver!");
        } catch (SQLException e) {
            System.out.println("Connection failed!");
        }

        return con;
    }
    
    // 새로운 메소드 추가: 특정 사용자의 모든 북마크를 가져오는 메소드
    public static ArrayList<Bookmark> getBookmarks(int userId, String tag) {
        ArrayList<Bookmark> bookmarks = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = makeConnection();
            String query = "SELECT * FROM bookmark WHERE bookmark_user = ? AND b_tag = ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, userId);
            pstmt.setString(2, tag);
            rs = pstmt.executeQuery();

            while (rs.next()) {
            	// 수정된 부분: 태그 값을 가져오는 부분
                String title = rs.getString("title");
                int star = rs.getInt("star");
                String tagValue = rs.getString("b_tag");

                // 기존 코드에서는 태그를 따로 가져오지 않았으므로 추가해야 함
                Bookmark bookmark = new Bookmark(title, star, tagValue);
                
                // 기존 코드에서의 태그 파싱 및 설정 부분은 유지
                bookmark.setTagManagerFromString(tagValue);
                bookmarks.add(bookmark);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println("Resource closing error: " + e.getMessage());
            }
        }

        return bookmarks;
    }

    public static Bookmark getBookmarkData(int user) {
        Connection con = makeConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Bookmark bookmarkData = null;

        String sql = "SELECT * FROM bookmark WHERE bookmark_user = ?";

        try {
            System.out.println("Executing SQL query: " + sql); // 디버깅용 출력
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, user);

            rs = pstmt.executeQuery();
            if (rs == null) {
            	return null;
            }
            if (rs.next()) {
                bookmarkData = new Bookmark(
                        rs.getString("content"),
                        rs.getInt("star"),
                        rs.getString("date"),
                        rs.getString("title"),
                        rs.getString("b_category"),
                        rs.getString("b_tag"),
                        rs.getInt("bookmark_user")
                );
                // 문자열로 저장된 태그를 파싱하여 설정
                bookmarkData.setTagManagerFromString(rs.getString("b_tag"));
            }
        } catch (SQLException e) {
            System.out.println("Error executing SQL query: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                System.out.println("Resource closing error: " + e.getMessage());
            }
        }

        return bookmarkData;
    }

    public static boolean updateBookmarkData(Bookmark newData) {
        Connection con = makeConnection();
        PreparedStatement pstmt = null;
        boolean success = false;

        String sql = "UPDATE bookmark SET b_category=?, b_tag=?, star=?, date=?, content=? WHERE title=?";

        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, newData.getCategory());
            pstmt.setString(2, newData.getTagManagerAsString());
            pstmt.setInt(3, newData.getImportance());
            pstmt.setString(4, newData.getDate());
            pstmt.setString(5, newData.getContent());
            pstmt.setString(6, newData.getTitle());

            int result = pstmt.executeUpdate();
            success = result > 0;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                System.out.println("Resource closing error: " + e.getMessage());
            }
        }

        return success;
    }

    public static boolean deleteBookmark(String title) {
        Connection con = makeConnection();
        PreparedStatement pstmt = null;
        boolean success = false;

        String sql = "DELETE FROM bookmark WHERE title=?";

        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, title);

            int result = pstmt.executeUpdate();
            success = result > 0;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                System.out.println("Resource closing error: " + e.getMessage());
            }
        }

        return success;
    }
    
    public static boolean insertBookmark(Bookmark newData) {
        Connection con = makeConnection();
        PreparedStatement pstmt = null;
        boolean success = false;

        String sql = "INSERT INTO bookmark (title, b_category, b_tag, star, date, content, bookmark_user) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, newData.getTitle());
            pstmt.setString(2, newData.getCategory());
            pstmt.setString(3, newData.getTagManagerAsString());
            pstmt.setInt(4, newData.getImportance());
            pstmt.setString(5, newData.getDate());
            pstmt.setString(6, newData.getContent());
            pstmt.setInt(7, newData.getId());

            int result = pstmt.executeUpdate();
            success = result > 0;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                System.out.println("Resource closing error: " + e.getMessage());
            }
        }

        return success;
    }
    
    public static boolean insertCategory(int user, String category) {
    	Connection con = makeConnection();
        PreparedStatement pstmt = null;
        boolean success = false;

        String sql = "INSERT INTO category (category_name, category_user) VALUES (?, ?)";


        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, category);
            pstmt.setInt(2, user);

            int result = pstmt.executeUpdate();
            success = result > 0;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                System.out.println("Resource closing error: " + e.getMessage());
            }
        }

        return success;
    }
    
    public static boolean deleteCategory(int user, String category) {
        Connection con = makeConnection();
        PreparedStatement pstmt = null;
        boolean success = false;

        String sql = "DELETE FROM category WHERE category_user = '" + user + "' AND category_name = '" + category + "'";

        try {
            pstmt = con.prepareStatement(sql);

            int result = pstmt.executeUpdate();
            success = result > 0;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                System.out.println("Resource closing error: " + e.getMessage());
            }
        }

        return success;
    }
    
    public static boolean deleteTag(int user, String no, String tag) {
        Connection con = makeConnection();
        PreparedStatement pstmt = null;
        boolean success = false;

        String sql = "DELETE FROM tag WHERE tag_user = '" + user + "' AND tag_name = '" + tag + "'";

        try {
            pstmt = con.prepareStatement(sql);

            int result = pstmt.executeUpdate();
            success = result > 0;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                System.out.println("Resource closing error: " + e.getMessage());
            }
        }

        return success;
    }
    
    public static boolean insertTag(int user, String category, String tag) {
    	Connection con = makeConnection();
        PreparedStatement pstmt = null;
        boolean success = false;

        String sql = "INSERT INTO tag (tag_name, tag_category, tag_user) VALUES (?, ?, ?)";

        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, tag);
            pstmt.setString(2, category);
            pstmt.setInt(3, user);

            int result = pstmt.executeUpdate();
            success = result > 0;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                System.out.println("Resource closing error: " + e.getMessage());
            }
        }

        return success;
    }
    
    // 사용자 아이디에 해당하는 카테고리 리스트를 가져오는 메서드
    public static ArrayList<String> getCategories(int userId) {
        ArrayList<String> categories = new ArrayList<>();
        Connection con = makeConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean success = false;

        try {
            String sql = "SELECT DISTINCT category_name FROM category WHERE category_user = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, userId);
            
            rs = pstmt.executeQuery();

            while (rs.next()) {
                categories.add(rs.getString("category_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                System.out.println("Resource closing error: " + e.getMessage());
            }
        }

        return categories;
    }

    // 사용자 아이디와 카테고리에 해당하는 태그 리스트를 가져오는 메서드
    public static ArrayList<String> getTags(int userId, String category) {
        ArrayList<String> tags = new ArrayList<>();
        Connection con = makeConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean success = false;

        try {
        	String sql = "SELECT DISTINCT tag_name FROM tag WHERE tag_user = ? AND tag_category = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, userId);
            pstmt.setString(2, category);
            
            rs = pstmt.executeQuery();

            while (rs.next()) {
                tags.add(rs.getString("tag_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                System.out.println("Resource closing error: " + e.getMessage());
            }
        }

        return tags;
    }
//    public static void main(String[] args) {
//        // 새로운 북마크 데이터 생성
//        BookmarkData newBookmark = new BookmarkData(
//                "내용 내용 내용",  // 내용
//                4,               // 별점
//                "2023-12-01",    // 날짜
//                "새로운 북마크",   // 제목
//                "여행",           // 카테고리
//                "태그1, 태그2"    // 태그
//        );
//
//        // 데이터 삽입
//        boolean success = insertBookmark(newBookmark);
//
//        // 삽입 결과 확인
//        if (success) {
//            System.out.println("북마크가 데이터베이스에 성공적으로 삽입되었습니다.");
//        } else {
//            System.out.println("데이터베이스에 북마크를 삽입하는 중 오류가 발생했습니다.");
//        }
//    }
}


