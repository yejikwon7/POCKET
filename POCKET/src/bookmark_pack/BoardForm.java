package bookmark_pack;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class BoardForm extends JFrame {
	private JPanel panel = new JPanel();
	private ImageIcon logo = new ImageIcon("images/logo.png");
	private JButton logoBtn = new JButton(logo);
	private JLabel title;
	private JLabel category;
	private JLabel tag;
	private JLabel star;
	private JLabel author;
	private JLabel date;
	private JLabel content;
	private JButton btnUpdate = new JButton("수정");
	private JButton btnDelete = new JButton("삭제");
	
	public BoardForm() {
		Container c = getContentPane();
		c.setLayout(null);
		
		panel.setBounds(0, 0, 1000, 800); // 패널의 위치와 크기 설정
		panel.setLayout(null);
		
		// 북마크 GUI 구성
		logoBtn.setLocation(410, 30);
		logoBtn.setSize(180, 65);
		logoBtn.setIcon(logo);
		logoBtn.setBorderPainted(false);
		logoBtn.setContentAreaFilled(false);
		logoBtn.setFocusPainted(false);
		logoBtn.addActionListener(new LogoActionListener());
		
		title = new JLabel("제목");
		title.setFont(new Font("맑은 고딕", Font.BOLD, 30));
		title.setLocation(200, 120);
		title.setSize(600, 50);
		title.setHorizontalAlignment(JLabel.CENTER);
		
		category = new JLabel("카테고리");
		category.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		category.setLocation(200, 185);
		category.setSize(100, 30);
		category.setHorizontalAlignment(JLabel.LEFT);
		JScrollPane categoryScroll = new JScrollPane(category);
		
		tag = new JLabel("태그");
		tag.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		tag.setLocation(200, 230);
		tag.setSize(100, 30);
		tag.setHorizontalAlignment(JLabel.LEFT);
		JScrollPane tagScroll = new JScrollPane(tag);
		
		star = new JLabel("중요도");
		star.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		star.setLocation(700, 185);
		star.setSize(100, 30);
		star.setHorizontalAlignment(JLabel.RIGHT);
		
		date = new JLabel("날짜");
		date.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		date.setLocation(700, 230);
		date.setSize(100, 30);
		date.setHorizontalAlignment(JLabel.RIGHT);
		
		content = new JLabel("내용");
		content.setFont(new Font("맑은 고딕", Font.BOLD, 25));
		content.setLocation(200, 300);
		content.setSize(600, 500);
		content.setHorizontalAlignment(JLabel.CENTER);
		JScrollPane contentScroll = new JScrollPane(panel,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		btnUpdate.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		btnUpdate.setLocation(810, 700);
		btnUpdate.setSize(60, 30);
		btnUpdate.addActionListener(new UpdateActionListener());
		
		btnDelete.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		btnDelete.setLocation(880, 700);
		btnDelete.setSize(60, 30);
		btnDelete.addActionListener(new DeleteActionListener());
		
		c.add(panel);
		panel.add(logoBtn);
		panel.add(title);
		panel.add(category);
		panel.add(tag);
		panel.add(star);
		panel.add(date);
		panel.add(content);
		panel.add(btnUpdate);
		panel.add(btnDelete);
		
		setSize(1000, 800);
		showFrame();
	}
	
	// 로고 버튼 리스너
	class LogoActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// 메인페이지로 돌아가는 코드
		}
	}
	
	// 수정 버튼 리스너
	class UpdateActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			
		}
	}
	
	// 삭제 버튼 리스너
	class DeleteActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int result = JOptionPane.showConfirmDialog(null, 
					"해당 북마크를 삭제하시겠습니까?", 
					"Message", JOptionPane.YES_NO_OPTION);
			if(result == JOptionPane.CLOSED_OPTION) {
				// 사용자가 창 닫은 경우 삭제하지 않음
			}
			
			else if(result == JOptionPane.YES_OPTION) {
				// 사용자가 "예"를 선택한 경우
			}
			
			else {
				// 사용가자 "아니오"를 선택한 경우
			}
		}
	}
	
	private void showFrame() {
		setLocationRelativeTo(null);
		setTitle("북마크");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new BoardForm();
	}

}
