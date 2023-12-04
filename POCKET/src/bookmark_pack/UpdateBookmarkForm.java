package bookmark_pack;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.awt.*;

public class UpdateBookmarkForm extends JFrame {
	private Bookmark bookmarkData;
	private JPanel pContent = new JPanel();
	private JPanel cPanel = new JPanel(); // 카테고리 패널
	private JPanel tPanel = new JPanel(); // 태그 패널
	private JPanel starPanel = new JPanel(); // 별 패널
	private int clickedStars = 0;
	private ImageIcon logo = new ImageIcon("images/logo.png");
	private JButton logoBtn = new JButton(logo);
	private JTextField title = new JTextField(50);
	private JTextArea content = new JTextArea();
	private JButton btnTstore = new JButton("임시저장");
	private JButton btnStore = new JButton("작성");
	private JButton btnMemo = new JButton("메모 추가");
	private CategoryManager1 categoryManager;
	private TagManager1 tagManager;
	private static int id;
	private static String uid;
	private String clickedCategory = null;
	private String clickedTag = null;
	
	public UpdateBookmarkForm(Bookmark bookmarkData, int id, String uid) {
		Container c = getContentPane();
		c.setLayout(null);
		this.id = id;
		this.uid = uid;
		
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
//		
//		// 메모 추가
//		btnMemo.setFont(new Font("맑은 고딕", Font.BOLD, 15));
//		btnMemo.setLocation(799, 120);
//		btnMemo.setSize(100, 38);
////		btnMemo.addActionListener(new MemoActionListener());
//		
		// 내용 입력
		pContent.setLayout(new BorderLayout());
		pContent.setBounds(90, 180, 820, 400);
		content.setBounds(0, 0, 800, 400);
		content.setFont(new Font("맑은 고딕", Font.BOLD, 25));
		content.setLineWrap(true);  // 자동 줄 바꿈 활성화
		content.setWrapStyleWord(true);  // 단어 단위 자동 줄 바꿈 활성화
		JScrollPane contentScroll = new JScrollPane(content);
		contentScroll.setBounds(0, 0, 800, 400);
		contentScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		contentScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		categoryManager = new CategoryManager1();
		tagManager = new TagManager1();
		cPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		cPanel.setBounds(100, 600, 400, 40);
		
		tPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		tPanel.setBounds(100, 650, 400, 40);
		
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
		pContent.add(contentScroll, BorderLayout.CENTER);
		c.add(pContent);
		c.add(cPanel);
		c.add(tPanel);
		c.add(starPanel);
		c.add(btnTstore);
		c.add(btnStore);
		c.add(btnMemo);
		
		this.bookmarkData = bookmarkData;
		
		if (bookmarkData != null) {
            // 북마크 데이터를 표시
            title.setText(bookmarkData.getTitle());
            SwingUtilities.invokeLater(() -> {
                content.setText(bookmarkData.getContent());
                content.repaint();
            });
            clickedStars = bookmarkData.getImportance();
            displayStarRating();
        } else {
            JOptionPane.showMessageDialog(null, "북마크 정보를 불러오는 데 실패했습니다.", "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
            System.out.println("Title에 대한 Bookmark 데이터가 null입니다: " + this.bookmarkData);
        }
		
		paintCategoryPanel();
		loadCategoriesAndTags();
		setSize(1000, 800);
		showFrame();
	}
	
	private void loadCategoriesAndTags() {
		// 카테고리 정보를 데이터베이스에서 가져와 UI 업데이트
        ArrayList<String> categories = BookmarkDB.getCategories(id);
        // 기존의 카테고리 목록을 초기화
        categoryManager.clearCategories();

        for (String categoryName : categories) {
            // 중복 추가를 방지하기 위해 해당 카테고리가 이미 존재하는지 확인
            if (!categoryManager.containsCategory(categoryName)) {
                categoryManager.addCategory(categoryName);

                // 각 카테고리에 대해 태그 정보를 데이터베이스에서 가져와 UI 업데이트
                ArrayList<String> tags = BookmarkDB.getTags(id, categoryName);
                for (String tagName : tags) {
                    categoryManager.get(categoryName).getTagManager().addTag(tagName);
                }
            }
        }
        paintCategoryPanel();
        if (clickedCategory != null) {
            paintTagPanel(clickedCategory);
        }
    }
	
	// 카테고리 목록 패널에 카테고리 버튼 생성
	private void paintCategoryPanel() {
		//cPanel.removeAll();
		
		if (categoryManager != null) {
			for (int i = 0; i < categoryManager.getSize(); i++) {
	        	JPanel buttonPanel = new JPanel();
	        	buttonPanel.setLayout(new BorderLayout());
	        	
	        	String Name = categoryManager.get(i).getName();
	        	JButton categoryButton = new JButton(Name);	
	        	categoryButton.addActionListener(new CategoryButtonListener());
	        	
	        	buttonPanel.add(categoryButton, BorderLayout.WEST);
//		        	buttonPanel.add(deleteButton, BorderLayout.EAST);
	        	cPanel.add(buttonPanel);
	        }
		}
		
		cPanel.revalidate();
		cPanel.repaint();
	}
	
	// 카테고리 버튼 클릭 시, 태그 목록 패널에 태그 버튼들을 생성하는 CategoryButtonListener
	private class CategoryButtonListener implements ActionListener {
    	public void actionPerformed(ActionEvent e) {
    		// 클릭된 버튼의 텍스트를 get
    		if (e.getSource() instanceof JButton) {
    			JButton clickedButton = (JButton) e.getSource();
                String buttonText = clickedButton.getText();
                clickedCategory = buttonText; // 클릭된 카테고리를 저장
                paintTagPanel(buttonText);
    		}	
    	}
	}
		
	// 태그 목록 패널에 태그 버튼 생성
	private void paintTagPanel(String buttonText) {
		//tPanel.removeAll();
	    // buttonText와 같은 name을 가진 카테고리를 찾고, 그 카테고리의 태그들을 태그 목록 패널에 버튼을 생성
	    for (int i = 0; i < categoryManager.getSize(); i++) {
	    	if (categoryManager.get(i).getName().equals(buttonText)) {
	    		TagManager1 c_tags = categoryManager.get(i).getTagManager();
	    		if (c_tags != null) {
	        		for (int j =0; j < c_tags.getSize(); j++) {
	        		JPanel buttonPanel = new JPanel();
	                buttonPanel.setLayout(new BorderLayout());
	        	        
	       	        String Name = c_tags.get(j).getName();
	      			JButton tagButton = new JButton(Name);
	      			
	      		// 이전에 클릭된 태그가 있으면 색깔을 원래대로 되돌림
                    if (clickedTag != null && clickedTag.equals(Name)) {
                        resetTagColor(tagButton);
                    }

                    // 태그 버튼에 ActionListener 추가
                    tagButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // 클릭된 태그의 정보를 저장하고 색깔을 변경함
                            clickedTag = Name;
                            setTagColor(tagButton);
                        }
                    });

	                	
	                buttonPanel.add(tagButton, BorderLayout.WEST);
	               	tPanel.add(buttonPanel);
	        		}
	        	}
	        }
	    }
	    tPanel.revalidate();
	    tPanel.repaint(); 
	}
	
	// setTagColor 및 resetTagColor 메서드 추가
	private void setTagColor(JButton tagButton) {
	    tagButton.setBackground(Color.GREEN);
	}

	private void resetTagColor(JButton tagButton) {
	    tagButton.setBackground(null); // 원래대로 되돌림
	}

	
//	// 카테고리와 태그를 표시하는 메서드 추가
//    private void displayCategoriesAndTags() {
//        // 선택된 카테고리 버튼의 텍스트를 가져와서 설정
//        for (Component component : cPanel.getComponents()) {
//            if (component instanceof JButton) {
//                JButton categoryBtn = (JButton) component;
//                if (bookmarkData.getCategory() != null && categoryBtn.getText().equals(bookmarkData.getCategory())) {
//                    categoryBtn.setSelected(true);
//                    break;
//                }
//            }
//        }
//
//        // 선택된 태그 버튼의 텍스트를 가져와서 설정
//        for (Component component : tPanel.getComponents()) {
//            if (component instanceof JButton) {
//                JButton tagBtn = (JButton) component;
//                if (bookmarkData.getTagManager() != null && bookmarkData.getTagManager().contains(tagBtn.getText())) {
//                    tagBtn.setSelected(true);
//                }
//            }
//        }
//    }
    
    private void displayStarRating() {
        for (int i = 0; i < clickedStars; i++) {
            JLabel starLabel = (JLabel) starPanel.getComponent(i);
            starLabel.setForeground(Color.YELLOW);
        }
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
    	        for (int i = 0; i < categoryManager.getSize(); i++) {
    	        	if (categoryManager.get(i).getName().equals(buttonText)) {
    	        		TagManager1 c_tags = categoryManager.get(i).getTagManager();
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
    	char starSymbol = '☆'; // 사용 중인 별 모양을 가정

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
			// 텍스트 필드에서 제목과 내용을 가져옴
            String bookmarkTitle = title.getText();
            String bookmarkContent = content.getText();
            String category = categoryManager.toString();
            String tag = tagManager.toString();

            // 임시 북마크 객체를 생성
            Bookmark temporaryBookmark = new Bookmark(
                bookmarkContent, clickedStars, getCurrentDate(), bookmarkTitle, clickedCategory, clickedTag, id
            );

            // 임시 북마크를 데이터베이스에 삽입
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
			// 텍스트 필드에서 제목과 내용을 가져옴
            String bookmarkTitle = title.getText();
            String bookmarkContent = content.getText();
            String category = categoryManager.toString();
            String tag = tagManager.toString();

            // 북마크 객체를 생성
            Bookmark newBookmark = new Bookmark(
                bookmarkContent, clickedStars, getCurrentDate(), bookmarkTitle, clickedCategory, clickedTag, id
            );

            // 북마크를 데이터베이스에 업데이트하거나 삽입
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
            
            dispose();
            new PersonalPage(id, uid);
		}
	}
	
	private String getCurrentDate() {
        // 현재 날짜를 문자열로 가져오는 로직을 구현
        return java.time.LocalDate.now().toString();
    }
	
	private void showFrame() {
		setLocationRelativeTo(null);
		setTitle("북마크 수정");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}

}
