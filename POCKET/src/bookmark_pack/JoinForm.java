package bookmark_pack;
// 회원가입 GUI
import javax.swing.*;

import java.awt.event.*;
import java.awt.*;
import java.util.ArrayList;
import java.sql.*;
import java.text.ParseException;
import java.io.*;

public class JoinForm extends JDialog {
	private LoginForm owner;
	private UserDB uDB = new UserDB();
	private int repeatCheck = 0;
	private JLabel lbTitle;
	private JLabel lbEmail;
	private JLabel lbId;
	private JLabel lbPw;
	private JLabel lbRe;
	private JTextField tfEmail = new JTextField(30);
	private JTextField tfId = new JTextField(20);
	private JPasswordField tfPw = new JPasswordField(15);
	private JPasswordField tfRe = new JPasswordField(15);
	private JButton btnRepeat = new JButton("중복");
	private JButton btnSignup = new JButton("회원가입");
	private ImageIcon logo = new ImageIcon("images/logo.png");
	private JButton logoBtn = new JButton(logo);
	
	public JoinForm(LoginForm owner) {
		super(owner, "회원가입", true);
		this.owner = owner;
		
		Container c = getContentPane();
		c.setLayout(null);
		
		// 회원가입 GUI 구성
		lbTitle = new JLabel("회원가입");
		lbTitle.setFont(new Font("맑은 고딕", Font.BOLD, 30));
		lbTitle.setLocation(345, 100);
		lbTitle.setSize(200, 50);
		
		lbEmail = new JLabel("이메일");
		lbEmail.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		lbEmail.setLocation(187, 169);
		lbEmail.setSize(114, 50);
		
		lbId = new JLabel("아이디");
		lbId.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		lbId.setLocation(187, 209);
		lbId.setSize(114, 50);
		
		lbPw = new JLabel("비밀번호");
		lbPw.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		lbPw.setLocation(187, 249);
		lbPw.setSize(114, 50);
		
		lbRe = new JLabel("비밀번호 확인");
		lbRe.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		lbRe.setLocation(187, 289);
		lbRe.setSize(114, 50);
		
		tfEmail.setLocation(314, 180);
		tfEmail.setSize(250, 30);
		
		tfId.setLocation(314, 220);
		tfId.setSize(250, 30);
		
		// 로고 버튼 달기
		logoBtn.setSize(180, 65);
		logoBtn.setLocation(310, 30);
		logoBtn.setIcon(logo);
		logoBtn.setBorderPainted(false);
		logoBtn.setContentAreaFilled(false);
		logoBtn.setFocusPainted(false);
		logoBtn.addActionListener(new LogoActionListener());
		
		btnRepeat.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		btnRepeat.setLocation(569, 220);
		btnRepeat.setSize(60, 30);
		btnRepeat.setBackground(Color.decode("#EEEEEE"));
		btnRepeat.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 3));
		btnRepeat.addActionListener(new RepeatActionListener());
		
		// 새 북마크 작성 버튼 마우스 오버에 대한 처리를 담당하는 리스너
		btnRepeat.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                // 마우스가 컴포넌트에 들어왔을 때
            	btnRepeat.setBorder(BorderFactory.createLineBorder(Color.decode("#00ADB5"), 3));
            	btnRepeat.setBackground(Color.WHITE);
            }
            public void mouseExited(MouseEvent e) {
                // 마우스가 컴포넌트에서 나갔을 때
            	btnRepeat.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 3));
            	btnRepeat.setBackground(Color.decode("#EEEEEE"));
            }
        });
		
		tfPw.setLocation(314, 260);
		tfPw.setSize(250, 30);
		
		tfRe.setLocation(314, 300);
		tfRe.setSize(250, 30);
		
		btnSignup.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		btnSignup.setLocation(350, 350);
		btnSignup.setSize(100, 30);
		btnSignup.setBackground(Color.decode("#EEEEEE"));
		btnSignup.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 3));
		btnSignup.addActionListener(new SignupActionListener());
		
		// 새 북마크 작성 버튼 마우스 오버에 대한 처리를 담당하는 리스너
		btnSignup.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                // 마우스가 컴포넌트에 들어왔을 때
            	btnSignup.setBorder(BorderFactory.createLineBorder(Color.decode("#00ADB5"), 3));
            	btnSignup.setBackground(Color.WHITE);
            }
            public void mouseExited(MouseEvent e) {
                // 마우스가 컴포넌트에서 나갔을 때
            	btnSignup.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 3));
            	btnSignup.setBackground(Color.decode("#EEEEEE"));
            }
        });
		
		c.add(logoBtn);
		c.add(lbTitle);
		c.add(lbEmail);
		c.add(lbId);
		c.add(lbPw);
		c.add(lbRe);
		c.add(tfEmail);
		c.add(tfId);
		c.add(btnRepeat);
		c.add(tfPw);
		c.add(tfRe);
		c.add(btnSignup);
		
		setSize(800, 500);
		addListeners();
		showFrame();
	}
	
	// 중복 확인 버튼 리스너
	class RepeatActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String uid = tfId.getText();
			// Id 중복
			if(uDB.IdCheck(uid)) { // DB 접속
				System.out.println("아이디 중복 없음");
				JOptionPane.showMessageDialog(JoinForm.this, "가능한 아이디입니다.");
				repeatCheck = 1;
			}
			
			else {
				System.out.println("아이디 중복");
				JOptionPane.showMessageDialog(JoinForm.this, "이미 존재하는 아이디입니다.");
			}
		}
	}
	
	// 회원가입 버튼 리스너
	class SignupActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// 변수에 tf의 아이디, 비밀번호 초기화
			String uid = tfId.getText();
			String upwd = String.valueOf(tfPw.getPassword());
			String uemail = tfEmail.getText();
			
			// 정보 하나라도 비어있는 경우
			if(isBlank()) {
				JOptionPane.showMessageDialog(JoinForm.this, "모든 정보를 입력해주세요.");
			}
			
			// 모두 입력한 경우
			else {
				// Id 중복
				if(repeatCheck == 3) {
					JOptionPane.showMessageDialog(JoinForm.this, "이미 존재하는 아이디입니다.");
					tfId.requestFocus();
				}
				
				// Pw와 Re가 일치하지 않은 경우
				else if(!String.valueOf(tfPw.getPassword()).equals(String.valueOf(tfRe.getPassword()))) {
					JOptionPane.showMessageDialog(JoinForm.this, "비밀번호와 일치하지 않습니다.");
					tfPw.requestFocus();
				}
				
				// 회원가입 성공
				else if(uDB.JoinUser(uid, upwd, uemail)){
					System.out.println("회원가입 성공");
					JOptionPane.showMessageDialog(JoinForm.this, "회원가입을 완료했습니다!");
					dispose();
					owner.setVisible(true);
				}
				
				else {
					System.out.println("회원가입 실패");
					JOptionPane.showMessageDialog(null, "회원가입에 실패하였습니다.");
					tfId.setText("");
					tfPw.setText("");
					tfEmail.setText("");
					tfRe.setText("");
				}
			}
		}
	}
	
	// 로고 버튼 리스너
	class LogoActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// 메인페이지로 돌아가는 코드
		}
	}
	
	private void addListeners() {
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				dispose();
				owner.setVisible(true);
			}
		});
	}
	
	public boolean isBlank() {
		boolean result = false;
		if(tfId.getText().isEmpty()) {
			tfId.requestFocus();
			return true;
		}
		
		if(String.valueOf(tfPw.getPassword()).isEmpty()) {
			tfPw.requestFocus();
			return true;
		}
		
		if(String.valueOf(tfRe.getPassword()).isEmpty()) {
			tfRe.requestFocus();
			return true;
		}
		
		if(tfEmail.getText().isEmpty()) {
			tfEmail.requestFocus();
			return true;
		}
		
		return result;
	}
	
	private void showFrame() {
		setLocationRelativeTo(owner);
		setTitle("회원가입");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}

}