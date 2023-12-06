package bookmark_pack;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class PersonalPage extends JFrame {
	private ImageIcon logo = new ImageIcon("images/logo.png");
	private JButton logoBtn = new JButton(logo);
	private JLabel welcomeLabel;
	private JButton newbookmarkButton;
	private JPanel categoryPanel;
	private CategoryButtonListener categoryButtonListener = new CategoryButtonListener();
	private JPanel tagPanel;
	private TagButtonListener tagButtonListener = new TagButtonListener();
	private CategoryManager1 categoryManager;
	private JPanel bookmarkPanel;
	private JScrollPane bookmarkScrollPane;
	private static BookmarkManager bookmarkManager;
	private static int id;
	private static String uid;
	private TagManager1 tagManager;
	private static int bookmarkNum;
	
	public PersonalPage(int id, String uid) {
		this.id = id;
		this.uid = uid;
		setTitle("POCKET");
		setSize(1000, 800);
		
		Container c = getContentPane();
        c.setLayout(null);
        
        // 로고
        logoBtn.setLocation(410, 30);
		logoBtn.setSize(180, 60);
		logoBtn.setIcon(logo);
		logoBtn.setBorderPainted(false);
		logoBtn.setContentAreaFilled(false);
		logoBtn.setFocusPainted(false);
		logoBtn.addActionListener(new LogoActionListener());
		add(logoBtn);
		
		// 로그인 환영 문구
		welcomeLabel = new JLabel(uid + "님, 환영합니다!");	
		Font welcomeFont = new Font("맑은 고딕", Font.BOLD, 13);
		welcomeLabel.setFont(welcomeFont);
		welcomeLabel.setBounds(850, 5, 150, 20);
		welcomeLabel.setForeground(Color.BLACK);
		add(welcomeLabel);
		
		// 새 북마크 작성
		newbookmarkButton = new JButton("새 북마크 작성");
		newbookmarkButton.addActionListener(new NewActionListener());
		Font newbookmarkFont = new Font("맑은 고딕", Font.BOLD, 12);
		newbookmarkButton.setFont(newbookmarkFont);
		newbookmarkButton.setBackground(Color.decode("#EEEEEE"));
		newbookmarkButton.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 3));
		newbookmarkButton.setBounds(780, 100, 120, 30);
		
		// 새 북마크 작성 버튼 마우스 오버에 대한 처리를 담당하는 리스너
		newbookmarkButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                // 마우스가 컴포넌트에 들어왔을 때
            	newbookmarkButton.setBorder(BorderFactory.createLineBorder(Color.decode("#00ADB5"), 3));
            	newbookmarkButton.setBackground(Color.WHITE);
            }
            public void mouseExited(MouseEvent e) {
                // 마우스가 컴포넌트에서 나갔을 때
            	newbookmarkButton.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 3));
            	newbookmarkButton.setBackground(Color.decode("#EEEEEE"));
            }
        });
		add(newbookmarkButton);
		
		// 카테고리 목록을 나타낼 패널
		categoryPanel = new JPanel();
		categoryPanel.setBounds(100, 140, 800, 35);
		categoryPanel.setBackground(Color.decode("#EEEEEE"));
		categoryPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		add(categoryPanel);
		
		// 태그 목록을 나타낼 패널
        tagPanel = new JPanel();
        tagPanel.setBounds(100, 180, 800, 35);
        tagPanel.setBackground(Color.decode("#EEEEEE"));
        tagPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        add(tagPanel);
        
        // 북마크 목록을 나타낼 패널
        bookmarkPanel = new JPanel();
        bookmarkPanel.setBackground(Color.decode("#EEEEEE"));
        bookmarkPanel.setLayout(null);
        bookmarkScrollPane = new JScrollPane(bookmarkPanel);
        bookmarkScrollPane.setBounds(100, 230, 800, 500);
        bookmarkScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        bookmarkScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        bookmarkScrollPane.getVerticalScrollBar().setUnitIncrement(10);
        add(bookmarkScrollPane);
        
        categoryManager = new CategoryManager1();
        bookmarkManager = new BookmarkManager();
        
        
        // 카테고리 목록 패널에 카테고리 버튼 생성
        panintCategoryPanel();
        loadCategoriesAndTags();
        
		setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
    private void loadCategoriesAndTags() {
    	// 카테고리 정보를 데이터베이스에서 가져와 UI 업데이트
        ArrayList<String> categories = BookmarkDB.getCategories(id);
        for (String categoryName : categories) {
            categoryManager.addCategory(categoryName);

            // 각 카테고리에 대해 태그 정보를 데이터베이스에서 가져와 UI 업데이트
            ArrayList<String> tags = BookmarkDB.getTags(id, categoryName);
            for (String tagName : tags) {
                categoryManager.get(categoryName).getTagManager().addTag(tagName);
            }
        }

        // 카테고리와 태그 목록 패널을 업데이트
        panintCategoryPanel();
    }
    
    public static void updateBookmarks() {
    }
	
	// 로고 버튼 리스너
	class LogoActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// 메인페이지로 돌아가는 코드
			dispose();
			new PersonalPage(id, uid);
		}
	}
	
	// 북마크 보기 리스너
	class BookmarActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			dispose();
			bookmarkNum = BookmarkDB.getBookmarkNum();
			new BookmarkForm(id, uid, bookmarkNum);
		}
	}
	
	// 새 북마크 생성 버튼
	class NewActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			new CreateBookmarkForm(id, uid);
		}
	}
	
	// 카테고리 목록 패널에 카테고리 버튼 생성
	private void panintCategoryPanel() {
		categoryPanel.removeAll();
		// 카테고리 추가 버튼
		JButton addCategoryButton = new JButton("+");
		addCategoryButton.setBackground(Color.WHITE);
		addCategoryButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String categoryName = JOptionPane.showInputDialog(null, "추가할 카테고리의 이름을 입력해주세요.", "Message", JOptionPane.PLAIN_MESSAGE);
				if (categoryName != null && !categoryName.trim().isEmpty()) {
					categoryManager.addCategory(categoryName);
					panintCategoryPanel();	// 카테고리 목록 패널 업데이트
		        }

	            // 북마크를 데이터베이스에 업데이트하거나 삽입합니다.
	            boolean success;
	            if (BookmarkDB.insertCategory(id, categoryName)) {
	                success = true;
	            } else {
	                success = BookmarkDB.insertCategory(id, categoryName);
	            }

	            if (success) {
	                JOptionPane.showMessageDialog(null, "북마크가 데이터베이스에 저장되었습니다.");
	            } else {
	                JOptionPane.showMessageDialog(null, "데이터베이스에 북마크를 저장하는 중 오류가 발생했습니다.");
	            }
			}
		});
		categoryPanel.add(addCategoryButton);
		
		if (categoryManager != null) {
			for (int i = 0; i < categoryManager.getSize(); i++) {
	        	JPanel buttonPanel = new JPanel();
	        	buttonPanel.setLayout(new BorderLayout());
	        	
	        	String Name = categoryManager.get(i).getName();
	        	JButton categoryButton = new JButton(Name);	
	        	categoryButton.setBackground(Color.WHITE);
	        	Font CategoryFont = new Font("맑은 고딕", Font.BOLD, 10);
	        	categoryButton.setFont(CategoryFont);
	        	categoryButton.addActionListener(categoryButtonListener);
	        	
	        	JButton deleteButton = new JButton("X");
	        	deleteButton.setBackground(Color.WHITE);
	        	deleteButton.setForeground(Color.RED);
	        	deleteButton.addActionListener(new ActionListener() {
	                public void actionPerformed(ActionEvent e) {
	                    int result = JOptionPane.showConfirmDialog(null, "해당 카테고리를 삭제하시겠습니까?", "Message", JOptionPane.YES_NO_OPTION);
	                    if (result == JOptionPane.YES_OPTION) {
	                    	categoryManager.delCategory(Name);
	                    	panintCategoryPanel();	// 카테고리 목록 패널 업데이트
	                    	
	                    	// DB 삭제
                            boolean success = BookmarkDB.deleteCategory(id, Name);

                            if (success) {
                                JOptionPane.showMessageDialog(null, "북마크가 삭제되었습니다.", "Success", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(null, "북마크 삭제에 실패했습니다.", "Error", JOptionPane.ERROR_MESSAGE);
                            }
	                        
	                    }
	                }
	            });
	        	
	        	buttonPanel.add(categoryButton, BorderLayout.WEST);
	        	buttonPanel.add(deleteButton, BorderLayout.EAST);
	        	categoryPanel.add(buttonPanel);
	        }
		}
		
		categoryPanel.revalidate();
		categoryPanel.repaint();
	}
	
	// 카테고리 버튼 클릭 시, 태그 목록 패널에 태그 버튼들을 생성하는 CategoryButtonListener
	private class CategoryButtonListener implements ActionListener {
    	public void actionPerformed(ActionEvent e) {
    		// 클릭된 버튼의 텍스트를 get
    		if (e.getSource() instanceof JButton) {
			JButton clickedButton = (JButton) e.getSource();
	        String buttonText = clickedButton.getText();
    		panintTagPanel(buttonText);	
    		}	
    	}
	}
	// 태그 목록 패널에 태그 버튼 생성
	private void panintTagPanel(String buttonText) {
		tagPanel.removeAll();
		// 태그 추가 버튼
		JButton addTagButton = new JButton("+");
		addTagButton.setBackground(Color.WHITE);
		addTagButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String tagName = JOptionPane.showInputDialog(null, "추가할 태그의 이름을 입력해주세요.", "Message", JOptionPane.PLAIN_MESSAGE);
				if (tagName != null && !tagName.trim().isEmpty()) {
					categoryManager.get(buttonText).getTagManager().addTag(tagName);
					panintTagPanel(buttonText);	// 태그 목록 패널 업데이트

		            // 태그를 데이터베이스에 업데이트하거나 삽입합니다.
		            boolean success;
		            if (BookmarkDB.insertTag(id, buttonText, tagName)) {
		                success = true;
		            } else {
		                success = BookmarkDB.insertTag(id, buttonText, tagName);
		            }

		            if (success) {
		                JOptionPane.showMessageDialog(null, "북마크가 데이터베이스에 저장되었습니다.");
		            } else {
		                JOptionPane.showMessageDialog(null, "데이터베이스에 북마크를 저장하는 중 오류가 발생했습니다.");
		            }
		            
		        }
			}
		});
		tagPanel.add(addTagButton);
				
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
	      			tagButton.setBackground(Color.WHITE);
	      			Font tagFont = new Font("맑은 고딕", Font.BOLD, 10);
	      			tagButton.setFont(tagFont);
	       			tagButton.addActionListener(tagButtonListener);
	        			
	       			JButton deleteButton = new JButton("X");
	       			deleteButton.setBackground(Color.WHITE);
	               	deleteButton.setForeground(Color.RED);
	               	deleteButton.addActionListener(new ActionListener() {
	               		public void actionPerformed(ActionEvent e) {
	               			int result = JOptionPane.showConfirmDialog(null, "해당 태그를 삭제하시겠습니까?", "Message", JOptionPane.YES_NO_OPTION);
	                        if (result == JOptionPane.YES_OPTION) {
	                           	c_tags.delTag(Name);
	                           	panintTagPanel(buttonText);	// 태그 목록 패널 업데이트
	                           	
	                         // DB 삭제
	                            boolean success = BookmarkDB.deleteTag(id, "1", Name);

	                            if (success) {
	                                JOptionPane.showMessageDialog(null, "북마크가 삭제되었습니다.", "Success", JOptionPane.INFORMATION_MESSAGE);
	                            } else {
	                                JOptionPane.showMessageDialog(null, "북마크 삭제에 실패했습니다.", "Error", JOptionPane.ERROR_MESSAGE);
	                            }
	                        }
	                        
	                        
	                    }
	               	});
	                	
	                buttonPanel.add(tagButton, BorderLayout.WEST);
	               	buttonPanel.add(deleteButton, BorderLayout.EAST);
	               	tagPanel.add(buttonPanel);
	        		}
	        	}
	        }
	    }
	    tagPanel.revalidate();
	    tagPanel.repaint(); 
	}
	
	// 태그 버튼 클릭 시, 북마크 목록 패널에 북마크들을 생성하는 TagButtonListener
    private class TagButtonListener implements ActionListener {
    	public void actionPerformed(ActionEvent e) {
    		bookmarkPanel.removeAll();
    		// 클릭된 버튼의 텍스트를 get
    		if (e.getSource() instanceof JButton) {
    			JButton clickedButton = (JButton) e.getSource();
    			String buttonText = clickedButton.getText();
    			// buttonText와 같은 name을 가진 태그를 포함하는 북마크들을 "toBePrinted"라는 동적배열에 임시 저장
    			ArrayList<Bookmark> bookmarks = BookmarkDB.getBookmarks(id, buttonText);
    			for (int i = 0; i < bookmarkManager.getSize(); i++) {
    				TagManager1 b_tags = bookmarkManager.get(i).getTagManager();
    				if (b_tags != null) {
    					for (int j = 0; j < b_tags.getSize(); j++) {
    						if (b_tags.get(j).getName().equals(buttonText))
    							bookmarks.add(bookmarkManager.get(i));
    					}
    				}
    			}
    			
    			// toBePrinted 배열에 있는 북마크들을 북마크 목록 패널에 출력
    			for (int i = 0; i < bookmarks.size(); i++) {
    				JButton bookmark = new JButton();
    				bookmark.setSize(200, 200);
    	        	int x = 75 + 225 * (i % 3);
    	            int y = 75 + 275 * (i / 3);
    	        	bookmark.setLocation(x , y);
    	        	bookmark.setBackground(Color.decode("#EEEEEE"));
    	        	bookmark.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 3));
    	        	bookmark.setLayout(null);
    	        	
    	        	Bookmark current = bookmarks.get(i);
    	        	bookmark.addActionListener(new ActionListener() {
	               		public void actionPerformed(ActionEvent e) {
	               			bookmarkNum = BookmarkDB.getBookmarkNum();
	               			new BookmarkForm(id, uid, bookmarkNum);
                    }
               	});
    	        	
	        	JPanel titlePanel = new JPanel();
	        	titlePanel.setLayout(new BorderLayout());
	        	titlePanel.setLocation(15, 15);
	        	titlePanel.setSize(170, 65);
	        	titlePanel.setBackground(new Color(255, 255, 255, 0));
	        	titlePanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 3));
	            JLabel titleLabel = new JLabel(current.getTitle());
	            Font titleFont = new Font("맑은 고딕", Font.BOLD, 12);
	            titleLabel.setFont(titleFont);
	            titlePanel.add(titleLabel, BorderLayout.CENTER);
	            bookmark.add(titlePanel);
	            
	            JPanel datePanel = new JPanel();
	            datePanel.setLocation(15, 85);
	            datePanel.setSize(170, 30);
	            datePanel.setBackground(new Color(255, 255, 255, 0));
	            datePanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 3));
	            JLabel dateLabel = new JLabel(current.getYear() + "." + bookmarks.get(i).getMonth() + "." + bookmarks.get(i).getDay());
	            Font dateFont = new Font("맑은 고딕", Font.BOLD, 12);
	            dateLabel.setFont(dateFont);
	            datePanel.add(dateLabel);
	            bookmark.add(datePanel);
	            
	            JPanel importancePanel = new JPanel();
	            importancePanel.setLocation(15, 120);
	            importancePanel.setSize(170, 30);
	            importancePanel.setBackground(new Color(255, 255, 255, 0));
	            importancePanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 3));
	            for (int j = 0; j < current.getImportance(); j++) {
	            	ImageIcon starImage = new ImageIcon("images/star.png");
	            	JLabel importanceLabel = new JLabel(starImage);
	            	importancePanel.add(importanceLabel);
	            }
	            bookmark.add(importancePanel);
	            
	            JPanel tagPanel = new JPanel();
	            tagPanel.setLocation(15, 155);
	            tagPanel.setSize(170, 30);
	            tagPanel.setBackground(new Color(255, 255, 255, 0));
	            tagPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 3));
	            for (int j = 0; j < current.getTagManager().getSize(); j++) {
	            	if (j == 0) {
	            		JLabel tagLabel = new JLabel(current.getTagManager().get(j).getName());
	            		Font tagFont = new Font("맑은 고딕", Font.BOLD, 12);
	            		tagLabel.setFont(tagFont);
	            		tagPanel.add(tagLabel);
	            	}
	            	else {
	            		JLabel tagLabel = new JLabel("/ " + current.getTagManager().get(j).getName());
	            		tagPanel.add(tagLabel);
	            	}
	            	
	            }
	            bookmark.add(tagPanel);
	            
		        // 북마크 마우스 오버에 대한 처리를 담당하는 리스너
	            bookmark.addMouseListener(new MouseAdapter() {
	                public void mouseEntered(MouseEvent e) {
	                    // 마우스가 컴포넌트에 들어왔을 때
	                	bookmark.setBackground(Color.WHITE);
	                	bookmark.setBorder(BorderFactory.createLineBorder(Color.decode("#00ADB5"), 3));
	                	titlePanel.setBorder(BorderFactory.createLineBorder(Color.decode("#00ADB5"), 3));
	                	datePanel.setBorder(BorderFactory.createLineBorder(Color.decode("#00ADB5"), 3));
	                	importancePanel.setBorder(BorderFactory.createLineBorder(Color.decode("#00ADB5"), 3));
	                	tagPanel.setBorder(BorderFactory.createLineBorder(Color.decode("#00ADB5"), 3));
	                }
	                public void mouseExited(MouseEvent e) {
	                    // 마우스가 컴포넌트에서 나갔을 때
	                	bookmark.setBackground(Color.decode("#EEEEEE"));
	                    bookmark.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 3));
	                    titlePanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 3));
	                    datePanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 3));
	                    importancePanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 3));
	                    tagPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 3));
	                }
	            });
	            
	            bookmarkPanel.add(bookmark);
	            bookmarkPanel.setPreferredSize(new Dimension(800, Math.max(bookmarkPanel.getPreferredSize().height, y + 275)));
			}
		}
    		bookmarkPanel.revalidate();
    		bookmarkPanel.repaint();
    	}
    }
	
}
