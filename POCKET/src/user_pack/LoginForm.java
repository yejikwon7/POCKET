package user_pack;
//로그인 GUI
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.ArrayList;

public class LoginForm extends JFrame {
	private UserDataSet users;
	
	private JLabel lbTitle;
	private JLabel lbId;
	private JLabel lbPw;
	private JTextField tfId = new JTextField(20);
	private JPasswordField tfPw = new JPasswordField(15);
	private JButton btnLogin = new JButton("로그인");
	private JButton btnJoin = new JButton("회원가입");
	private ImageIcon logo = new ImageIcon("images/logo.png");
	private JButton logoBtn = new JButton(logo);
	
	public LoginForm() {
		Container c = getContentPane();
		c.setLayout(null);
		
		users = new UserDataSet();
		
		// 로그인 GUI 구성
		lbTitle = new JLabel("로그인");
		lbTitle.setFont(new Font("맑은 고딕", Font.BOLD, 30));
		lbTitle.setLocation(355, 100);
		lbTitle.setSize(200, 50);
		
		lbId = new JLabel("아이디");
		lbId.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		lbId.setLocation(220, 169);
		lbId.setSize(90, 50);
		
		lbPw = new JLabel("비밀번호");
		lbPw.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		lbPw.setLocation(220, 209);
		lbPw.setSize(90, 50);
		
		tfId.setLocation(305, 180);
		tfId.setSize(250, 30);
		
		tfPw.setLocation(305, 220);
		tfPw.setSize(250, 30);
		
		// 로고 버튼 달기
		logoBtn.setSize(180, 60);
		logoBtn.setLocation(310, 30);
		logoBtn.setIcon(logo);
		logoBtn.setBorderPainted(false);
		logoBtn.setContentAreaFilled(false);
		logoBtn.setFocusPainted(false);
		logoBtn.addActionListener(new LogoActionListener());
		
		// 로그인 버튼
		btnLogin.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		btnLogin.setLocation(275, 275);
		btnLogin.setSize(100, 30);
		btnLogin.addActionListener(new LoginActionListener());
		
		// 회원가입 버튼
		btnJoin.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		btnJoin.setLocation(425, 275);
		btnJoin.setSize(100, 30);
		btnJoin.addActionListener(new JoinActionListener());
		
		c.add(logoBtn);
		c.add(lbTitle);
		c.add(lbId);
		c.add(lbPw);
		c.add(tfId);
		c.add(tfPw);
		c.add(btnLogin);
		c.add(btnJoin);
		
		setSize(800, 500);
		addListeners();
		showFrame();
	}
	
	public UserDataSet getUsers() {
		return users;
	}
	
	public String getTfId() {
		return tfId.getText();
	}
	
	// 로그인 버튼 리스너
	class LoginActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// 아이디칸이 빈 경우
			if(tfId.getText().isEmpty()) {
				JOptionPane.showMessageDialog(LoginForm.this, 
						"아이디를 입력하세요",
						"로그인폼",
						JOptionPane.WARNING_MESSAGE);
			}
			
			// 존재하는 아이디인 경우
			else if(users.contains(new User(tfId.getText()))) {
				// 비밀번호 칸이 빈 경우
				if(String.valueOf(tfPw.getPassword()).isEmpty()) {
					JOptionPane.showMessageDialog(LoginForm.this, 
							"비밀번호를 입력하세요",
							"로그인폼",
							JOptionPane.WARNING_MESSAGE);
				}
				
				// 비밀번호가 일치하지 않는 경우
				else if(!users.getUser(tfId.getText()).getPw().equals(String.valueOf(tfPw.getPassword()))) {
					JOptionPane.showMessageDialog(LoginForm.this, 
							"비밀번호가 일치하지 않습니다.");
				}
				
				// 모두 성공
				else {
					// 메인페이지로 돌아가는 코드 추가
//					InformationForm infoForm = new InformationForm(LoginForm.this);
//					infoForm.setTaCheck(users.getUser(tfId.getText()).toString());
					setVisible(false);
					tfId.setText("");
					tfPw.setText("");
//					infoForm.setVisible(true);
				}
			}
			
			// 존재하지 않는 Id인 경우
			else {
				JOptionPane.showMessageDialog(LoginForm.this, 
						"존재하지 않는 아이디입니다.");
			}
		}
	}
	
	// 회원가입 버튼 리스너
	class JoinActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			setVisible(false);
			new JoinForm(LoginForm.this);
			tfId.setText("");
			tfPw.setText("");
		}
	}
	
	// 로고 버튼 리스너
	class LogoActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// 메인페이지로 돌아가는 코드
		}
	}
	
	public void addListeners() {
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				int choice = JOptionPane.showConfirmDialog(LoginForm.this,
						"로그인 및 회원가입을 종료합니다.",
						"종료",
						JOptionPane.OK_CANCEL_OPTION,
						JOptionPane.WARNING_MESSAGE);
				if (choice == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});
	}
	
	public void showFrame() {
		setTitle("로그인");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new LoginForm();
	}

}
