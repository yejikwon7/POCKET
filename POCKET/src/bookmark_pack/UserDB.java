package bookmark_pack;

import java.io.*;
import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;

public class UserDB {
	public static Connection makeConnection() { // 연결부분을 메소드로
		// 통로 객체 return 필요, return 타입이 connection
		String url = "jdbc:mysql://localhost/pocket?characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false";
		String id = "root";
		String password = "Pqlamz000!";
		Connection con = null; // return
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("driver loading success!");
			con = DriverManager.getConnection(url, id, password); // 연결고리
			System.out.println("database connecting success!");
		} catch(ClassNotFoundException e) {
			System.out.println("cannot find driver!");
		} catch(SQLException e) { // sql 연결 못함
			System.out.println("connection failed!");
		}
		
		return con;
	}
	
    // 회원가입 정보 insert
    public boolean JoinUser(String id, String pwd, String email) {
        System.out.println("회원가입");
        boolean flag = false;
        Connection con = makeConnection();
        PreparedStatement pstmt = null;
        String sql = "insert into member (member_id, member_pwd, email) values (?, ?, ?)";

        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, id);
            pstmt.setString(2, pwd);
            pstmt.setString(3, email);

            int result = pstmt.executeUpdate();
            if (result != 1) {
                flag = false;
                System.out.println("레코드 추가 실패");
            } else {
                flag = true;
            }
        } catch (Exception e) {
            flag = false;
            System.out.println("실패: " + e.getMessage());
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                System.out.println("리소스 닫기 오류: " + e.getMessage());
            }
        }

        return flag;
    }

    // 회원가입 아이디 중복 
    public boolean IdCheck(String id) {
        System.out.println("중복");
        boolean flag = false;
        Connection con = makeConnection();
        PreparedStatement pstmt = null;
        String sql = "select member_id from member where member_id=?";

        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, id);

            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                flag = false;
                System.out.println("이미 존재하는 아이디");
            } else {
                flag = true;
                System.out.println("사용 가능한 아이디");
            }

        } catch (Exception e) {
            flag = false;
            System.out.println("오류: " + e.getMessage());
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                System.out.println("리소스 닫기 오류: " + e.getMessage());
            }
        }

        return flag;
    }
	
	// 로그인
    public int LoginUser(String id, String pwd) {
        int userId = -1;
        try (Connection con = makeConnection();
             PreparedStatement pstmt = con.prepareStatement("select member_num, member_pwd from member where member_id=?")) {
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String storedPwd = rs.getString("member_pwd");
                if (pwd.equals(storedPwd)) {
                    userId = rs.getInt("member_num"); // 로그인 성공 시 사용자 ID를 설정
                    System.out.println("로그인 성공");
                } else {
                    System.out.println("비밀번호 불일치");
                }
            } else {
                System.out.println("아이디가 존재하지 않습니다.");
            }

        } catch (Exception e) {
            System.out.println("로그인 실패: " + e.getMessage());
        }
        return userId;
    }
}