package bookmark_pack;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.awt.*;

public class CreateBookmarkForm extends JFrame {
	private BookmarkData bookmarkData;
	private JPanel pContent = new JPanel();
	private JPanel cPanel = new JPanel(); // 카테고리 패널
	private JPanel tPanel = new JPanel(); // 태그 패널
	private JPanel starPanel = new JPanel(); // 별 패널
	private int clickedStars = 0;
	private ImageIcon logo = new ImageIcon("images/logo.png");
	private JButton logoBtn = new JButton(logo);
	private JTextField title = new JTextField(50);
	private JTextField content = new JTextField(1000);
	private JButton btnTstore = new JButton("임시저장");
	private JButton btnStore = new JButton("작성");
	private JButton btnMemo = new JButton("메모 추가");
	private CategoryManager1 categoryManager1;
	
	public CreateBookmarkForm(BookmarkData bookmarkData) {
		Container c = getContentPane();
		c.setLayout(null);
		
		// 북마크 GUI 구성
		// 로고 버튼
		logoBtn.setLocation(410, 30);
		logoBtn.setSize(180, 60);
		logoBtn.setIcon(logo);
		logoBtn.setBorderPainted(false);
		logoBtn.setContentAreaFilled(false);
		logoBtn.setFocusPainted(false);
		logoBtn.addActionListener(new LogoActionListener());
		
		// 제목 입력
		title.setLocation(100, 120);
		title.setSize(600, 40);
		title.setHorizontalAlignment(JLabel.CENTER);
		
		// 메모 추가
		btnMemo.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		btnMemo.setLocation(799, 120);
		btnMemo.setSize(100, 38);
		btnMemo.addActionListener(new StoreActionListener());
		
		// 내용 입력
		pContent.setLayout(new BorderLayout());
		pContent.setBounds(100, 180, 800, 400);
		content.setFont(new Font("맑은 고딕", Font.BOLD, 25));
		content.setBounds(100, 180, 800, 400);
		content.setHorizontalAlignment(JLabel.CENTER);
//		JScrollPane contentScroll = new JScrollPane(content);
		
		categoryManager1 = new CategoryManager1();
		categoryManager1.addCategory("여행");
		categoryManager1.addCategory("공부");
		categoryManager1.addCategory("대외활동");
		cPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		cPanel.setBounds(100, 600, 400, 40);
		
		// 카테고리 버튼으로 생성
		for(int i = 0; i < categoryManager1.getSize(); i++) {
			JButton categoryBtn = new JButton(categoryManager1.get(i).getName());
			cPanel.add(categoryBtn);
			categoryBtn.addActionListener(new CategoryActionListener());
		}
		
		tPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		tPanel.setBounds(100, 650, 400, 40);
		categoryManager1.get("여행").getTagManager().addTag("대만");	
        categoryManager1.get("여행").getTagManager().addTag("일본");
        categoryManager1.get("공부").getTagManager().addTag("Java");
        categoryManager1.get("공부").getTagManager().addTag("Python");
        categoryManager1.get("대외활동").getTagManager().addTag("공모전");
        categoryManager1.get("대외활동").getTagManager().addTag("해커톤");
        categoryManager1.get("대외활동").getTagManager().addTag("동아리");
		
        starPanel.setBounds(500, 590, 400, 50);
        starPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        for (int i = 1; i <= 5; i++) {
            JLabel starLabel = createStarLabel();
            starLabel.setFont(new Font("맑은 고딕", Font.BOLD, 30));
            
            starPanel.add(starLabel);
        }
		
		btnTstore.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		btnTstore.setLocation(739, 660);
		btnTstore.setSize(90, 30);
		btnTstore.addActionListener(new TstoreActionListener());
		
		btnStore.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		btnStore.setLocation(839, 660);
		btnStore.setSize(60, 30);
		btnStore.addActionListener(new StoreActionListener());
		
		c.add(logoBtn);
		c.add(title);
		pContent.add(content);
		c.add(pContent);
		c.add(cPanel);
		c.add(tPanel);
		c.add(starPanel);
		c.add(btnTstore);
		c.add(btnStore);
		c.add(btnMemo);
		
		setSize(1000, 800);
		showFrame();
	}
	
	// 로고 버튼 리스너
	class LogoActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// 메인페이지로 돌아가는 코드
		}
	}
	
	// 카테고리 버튼 리스너
	class CategoryActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			tPanel.removeAll();
			if (e.getSource() instanceof JButton) {
    			JButton clickedButton = (JButton) e.getSource();
    	        String buttonText = clickedButton.getText();
    	        // 버튼의 텍스트와 같은 name을 가진 카테고리를 찾고, 그 카테고리의 태그들을 태그 목록 패널에 버튼을 생성
    	        for (int i = 0; i < categoryManager1.getSize(); i++) {
    	        	if (categoryManager1.get(i).getName().equals(buttonText)) {
    	        		TagManager1 c_tags = categoryManager1.get(i).getTagManager();
    	        		if (c_tags != null) {
    	        			for (int j =0; j < c_tags.getSize(); j++) {
    	        			JButton tagButton = new JButton(c_tags.get(j).getName());
    	                	tPanel.add(tagButton);
    	                	tagButton.addActionListener(new TagActionListener());
    	        			}
    	        		}
    	        	}
    	        }
    		}
    		tPanel.revalidate();
            tPanel.repaint();
		}
	}
	
	// 태그 버튼 리스너
	class TagActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// 클릭된 버튼의 텍스트를 get
    		if (e.getSource() instanceof JButton) {
    			JButton clickedButton = (JButton) e.getSource();
    			String buttonText = clickedButton.getText();

    		}
    	}
	}
	
	private JLabel createStarLabel() {
        JLabel starLabel = new JLabel("\u2606"); // 별 문자에 해당하는 Unicode

        starLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	SwingUtilities.invokeLater(() -> {
                    clickedStars++;
                    updateStarColors(starLabel);
                    System.out.println("클릭한 별 개수: " + clickedStars);
                });
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            	//SwingUtilities.invokeLater(() -> updateStarColors(starLabel));
            }

            @Override
            public void mouseExited(MouseEvent e) {
            	//SwingUtilities.invokeLater(() -> updateStarColors(starLabel));
            }
        });

        return starLabel;
    }

    private void updateStarColors(JLabel clickedStar) {
    	char starSymbol = '☆'; // 사용 중인 별 모양을 가정합니다.

    	 Container starPanelContainer = (Container) clickedStar.getParent();

	    // 현재 클릭된 별의 인덱스 가져오기
	    int clickedIndex = starPanelContainer.getComponentZOrder(clickedStar);

	    // 클릭한 별의 색상을 토글하고 나머지 별들의 색상을 변경
	    for (int i = 0; i < starPanelContainer.getComponentCount(); i++) {
	        JLabel starLabel = (JLabel) starPanelContainer.getComponent(i);
	        if (i == clickedIndex) {
	            // 현재 클릭된 별의 색상을 토글 (노란색 ↔ 검정색)
	            Color currentColor = starLabel.getForeground();
	            if (currentColor == Color.YELLOW) {
	                starLabel.setForeground(Color.BLACK);
	            } else {
	                starLabel.setForeground(Color.YELLOW);
	            }
	        } else if (i < clickedIndex) {
	            // 클릭된 별보다 앞에 있는 별들은 노란색으로 변경
	            starLabel.setForeground(Color.YELLOW);
	        } else {
	            // 클릭된 별보다 뒤에 있는 별들은 검정색으로 변경
	            starLabel.setForeground(Color.BLACK);
	        }
	    }
    }
	
	// 임시저장 버튼 리스너
	class TstoreActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// 텍스트 필드에서 제목과 내용을 가져옵니다.
            String bookmarkTitle = title.getText();
            String bookmarkContent = content.getText();

            // 임시 북마크 객체를 생성합니다.
            BookmarkData temporaryBookmark = new BookmarkData(
                bookmarkContent, clickedStars, getCurrentDate(), bookmarkTitle, "", ""
            );

            // 임시 북마크를 데이터베이스에 삽입합니다.
            boolean success = BookmarkDB.insertBookmark(temporaryBookmark);

            if (success) {
                JOptionPane.showMessageDialog(null, "북마크가 임시로 저장되었습니다.");
            } else {
                JOptionPane.showMessageDialog(null, "데이터베이스에 북마크를 저장하는 중 오류가 발생했습니다.");
            }
		}
	}
	
	// 작성 버튼 리스너
	class StoreActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// 텍스트 필드에서 제목과 내용을 가져옵니다.
            String bookmarkTitle = title.getText();
            String bookmarkContent = content.getText();

            // 북마크 객체를 생성합니다.
            BookmarkData newBookmark = new BookmarkData(
                bookmarkContent, clickedStars, getCurrentDate(), bookmarkTitle, "", ""
            );

            // 북마크를 데이터베이스에 업데이트하거나 삽입합니다.
            boolean success;
            if (BookmarkDB.updateBookmarkData(newBookmark)) {
                success = true;
            } else {
                success = BookmarkDB.insertBookmark(newBookmark);
            }

            if (success) {
                JOptionPane.showMessageDialog(null, "북마크가 데이터베이스에 저장되었습니다.");
            } else {
                JOptionPane.showMessageDialog(null, "데이터베이스에 북마크를 저장하는 중 오류가 발생했습니다.");
            }
		}
	}
	
	private String getCurrentDate() {
        // 현재 날짜를 문자열로 가져오는 로직을 구현합니다.
        // 예를 들어, java.time.LocalDate를 사용할 수 있습니다.
        return java.time.LocalDate.now().toString();
    }
	
	private void showFrame() {
		setLocationRelativeTo(null);
		setTitle("북마크 생성");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

}
