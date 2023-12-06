package bookmark_pack;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class BookmarkForm extends JFrame {
	private Bookmark bookmarkData;
	private JPanel panel = new JPanel();
	private JEditorPane pContent = new JEditorPane();
	private ImageIcon logo = new ImageIcon("images/logo.png");
	private JButton logoBtn = new JButton(logo);
	private JLabel laTitle;
	private JLabel category;
	private JLabel tag;
	private JLabel star;
	private JLabel date;
	private JButton btnUpdate = new JButton("수정");
	private JButton btnDelete = new JButton("삭제");
	private String user;
	private static int id;
	private static String uid;
	private static int bookmarkNum;
	
	public BookmarkForm(int id, String uid, int bookmarkNum) {
		Container c = getContentPane();
		c.setLayout(null);
		this.id = id;
		this.uid = uid;
		this.bookmarkNum = bookmarkNum;
		
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
		
		laTitle = new JLabel("제목");
		laTitle.setFont(new Font("맑은 고딕", Font.BOLD, 30));
		laTitle.setLocation(200, 120);
		laTitle.setSize(600, 50);
		laTitle.setHorizontalAlignment(JLabel.CENTER);
		
		category = new JLabel("카테고리");
		category.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		category.setLocation(200, 185);
		category.setSize(150, 30);
		category.setHorizontalAlignment(JLabel.LEFT);
		
		tag = new JLabel("태그");
		tag.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		tag.setLocation(200, 230);
		tag.setSize(150, 30);
		tag.setHorizontalAlignment(JLabel.LEFT);
		
		star = new JLabel("중요도");
		star.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		star.setLocation(700, 185);
		star.setSize(150, 30);
		star.setHorizontalAlignment(JLabel.RIGHT);
		
		date = new JLabel("날짜");
		date.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		date.setLocation(700, 230);
		date.setSize(150, 30);
		date.setHorizontalAlignment(JLabel.RIGHT);
		
		pContent.setLayout(new BorderLayout());
		pContent.setBounds(190, 300, 620, 400);
		pContent.setContentType("text/html");
		pContent.setFont(new Font("맑은 고딕", Font.PLAIN, 25));
		JScrollPane contentScroll = new JScrollPane(pContent);
		contentScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		contentScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		contentScroll.getVerticalScrollBar().setUnitIncrement(10);
		contentScroll.setBounds(190, 300, 620, 400);
		contentScroll.setPreferredSize(new Dimension(620, 400));
		
		btnUpdate.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		btnUpdate.setLocation(810, 700);
		btnUpdate.setSize(60, 30);
		btnUpdate.setBackground(Color.decode("#EEEEEE"));
		btnUpdate.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 3));
		btnUpdate.addActionListener(new UpdateActionListener());
		
		// 버튼 마우스 오버에 대한 처리를 담당하는 리스너
		btnUpdate.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                // 마우스가 컴포넌트에 들어왔을 때
            	btnUpdate.setBorder(BorderFactory.createLineBorder(Color.decode("#00ADB5"), 3));
            	btnUpdate.setBackground(Color.WHITE);
            }
            public void mouseExited(MouseEvent e) {
                // 마우스가 컴포넌트에서 나갔을 때
            	btnUpdate.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 3));
            	btnUpdate.setBackground(Color.decode("#EEEEEE"));
            }
        });
		
		btnDelete.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		btnDelete.setLocation(880, 700);
		btnDelete.setSize(60, 30);
		btnDelete.setBackground(Color.decode("#EEEEEE"));
		btnDelete.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 3));
		btnDelete.addActionListener(new DeleteActionListener());
		
		// 버튼 마우스 오버에 대한 처리를 담당하는 리스너
		btnDelete.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                // 마우스가 컴포넌트에 들어왔을 때
            	btnDelete.setBorder(BorderFactory.createLineBorder(Color.decode("#00ADB5"), 3));
            	btnDelete.setBackground(Color.WHITE);
            }
            public void mouseExited(MouseEvent e) {
                // 마우스가 컴포넌트에서 나갔을 때
            	btnDelete.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 3));
            	btnDelete.setBackground(Color.decode("#EEEEEE"));
            }
        });
		
		c.add(panel);
		panel.add(logoBtn);
		panel.add(laTitle);
		panel.add(category);
		panel.add(tag);
		panel.add(star);
		panel.add(date);
		panel.add(btnUpdate);
		panel.add(btnDelete);
		c.add(contentScroll);
		
		setSize(1000, 800);
		showFrame();
		
		this.bookmarkData = BookmarkDB.getBookmarkData(this.id);

        if (bookmarkData != null) {
            displayBookmarkData();
        } else {
            JOptionPane.showMessageDialog(null, "북마크 정보를 불러오는 데 실패했습니다.", "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
            System.out.println("Title에 대한 Bookmark 데이터가 null입니다: " + id);
        }
	
	}
	
	private void displayBookmarkData() {
        laTitle.setText(bookmarkData.getTitle());
        category.setText("Category " + bookmarkData.getCategory());
        tag.setText("Tag " + bookmarkData.getTagManager());
        star.setText("Star " + bookmarkData.getImportance());
        date.setText("Date " + bookmarkData.getDate());
        pContent.setText("<html>" + bookmarkData.getContent() + "</html>");
    }
	
	// 로고 버튼 리스너
	class LogoActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// 메인페이지로 돌아가는 코드
			new PersonalPage(id, uid);
		}
	}
	
	// 수정 버튼 리스너
	class UpdateActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (bookmarkData != null) {
                UpdateBookmarkForm updateForm = new UpdateBookmarkForm(bookmarkData, id, uid);
                updateForm.setVisible(true);
            }
        }
    }
	
	// 삭제 버튼 리스너
	class DeleteActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int result = JOptionPane.showConfirmDialog(null, 
					"해당 북마크를 삭제하시겠습니까?", 
					"Message", JOptionPane.YES_NO_OPTION);
			if (result == JOptionPane.YES_OPTION) {
                boolean success = BookmarkDB.deleteBookmark(bookmarkData.getTitle());

                if (success) {
                    JOptionPane.showMessageDialog(null, "북마크가 삭제되었습니다.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    dispose(); // 현재 화면을 닫음
                } else {
                    JOptionPane.showMessageDialog(null, "북마크 삭제에 실패했습니다.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
		}
	}
	
	private void showFrame() {
		setLocationRelativeTo(null);
		setTitle("북마크 보기");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}

}
