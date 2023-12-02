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
    
//    public class BookmarkDB List<Bookmark> getBookmarks(int start, int size) {
//    	List<Bookmark> list = new ArrayList<>();
//    }

    public static BookmarkData getBookmarkData(int user) {
        Connection con = makeConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        BookmarkData bookmarkData = null;

        String sql = "SELECT * FROM bookmark WHERE bookmark_user = ?";

        try {
            System.out.println("Executing SQL query: " + sql); // 디버깅용 출력
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, user);

            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                bookmarkData = new BookmarkData(
                        rs.getString("content"),
                        rs.getInt("star"),
                        rs.getString("date"),
                        rs.getString("title"),
                        rs.getString("b_category"),
                        rs.getString("b_tag")
                );
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

    public static boolean updateBookmarkData(BookmarkData newData) {
        Connection con = makeConnection();
        PreparedStatement pstmt = null;
        boolean success = false;

        String sql = "UPDATE bookmark SET b_category=?, b_tag=?, star=?, date=?, content=? WHERE title=?";

        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, newData.getCategory());
            pstmt.setString(2, newData.getTag());
            pstmt.setInt(3, newData.getStar());
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
    
    public static boolean insertBookmark(BookmarkData newData) {
        Connection con = makeConnection();
        PreparedStatement pstmt = null;
        boolean success = false;

        String sql = "INSERT INTO bookmark (title, b_category, b_tag, star, date, content) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, newData.getTitle());
            pstmt.setString(2, newData.getCategory());
            pstmt.setString(3, newData.getTag());
            pstmt.setInt(4, newData.getStar());
            pstmt.setString(5, newData.getDate());
            pstmt.setString(6, newData.getContent());

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


