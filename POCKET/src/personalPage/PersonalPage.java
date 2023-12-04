package personalPage;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class PersonalPage extends JFrame {
	private ImageIcon logoImage;
	private JLabel logoLabel;
	private JLabel welcomeLabel;
	private JButton newbookmarkButton;
	private JPanel categoryPanel;
	private CategoryButtonListener categoryButtonListener = new CategoryButtonListener();
	private JPanel tagPanel;
	private TagButtonListener tagButtonListener = new TagButtonListener();
	private CategoryManager categoryManager;
	private JPanel bookmarkPanel;
	private JScrollPane bookmarkScrollPane;
	private BookmarkManager bookmarkManager;
	
	public PersonalPage() {
		setTitle("POCKET");
		setSize(1000, 800);
		
		Container c = getContentPane();
        c.setLayout(null);
        
        // 로고
        logoImage = new ImageIcon("images/logo.png");
        logoLabel = new JLabel(logoImage);				
		logoLabel.setBounds(400, 25, 200, 50);
		add(logoLabel);
		
		// 로그인 환영 문구
		welcomeLabel = new JLabel("OOO님, 환영합니다!");	
		welcomeLabel.setBounds(850, 5, 150, 20);
		welcomeLabel.setForeground(Color.BLACK);
		add(welcomeLabel);
		
		// 새 북마크 작성
		newbookmarkButton = new JButton("새 북마크 작성");
		newbookmarkButton.setBounds(780, 100, 120, 30);
		add(newbookmarkButton);
		
		// 카테고리 목록을 나타낼 패널
		categoryPanel = new JPanel();
		categoryPanel.setBounds(100, 140, 800, 35);
		categoryPanel.setBackground(Color.LIGHT_GRAY);
		categoryPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		add(categoryPanel);
		
		// 태그 목록을 나타낼 패널
        tagPanel = new JPanel();
        tagPanel.setBounds(100, 180, 800, 35);
        tagPanel.setBackground(Color.LIGHT_GRAY);
        tagPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        add(tagPanel);
        
        // 북마크 목록을 나타낼 패널
        bookmarkPanel = new JPanel();
        bookmarkPanel.setBackground(Color.LIGHT_GRAY);
        bookmarkPanel.setLayout(null);
        bookmarkScrollPane = new JScrollPane(bookmarkPanel);
        bookmarkScrollPane.setBounds(100, 230, 800, 500);
        bookmarkScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        bookmarkScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        bookmarkScrollPane.getVerticalScrollBar().setUnitIncrement(10);
        add(bookmarkScrollPane);
        
        
        
        /* ----- 예시 ----- */
       
        // 임의로 추가한 카테고리 예시
        categoryManager = new CategoryManager();
        categoryManager.addCategory("여행");
        categoryManager.addCategory("코드");
        categoryManager.addCategory("대외활동");
        
    	// 임의로 추가한 태그 예시
        categoryManager.get("여행").getTagManager().addTag("대만");	
        categoryManager.get("여행").getTagManager().addTag("일본");
        categoryManager.get("코드").getTagManager().addTag("Java");
        categoryManager.get("코드").getTagManager().addTag("Python");
        categoryManager.get("대외활동").getTagManager().addTag("공모전");
        categoryManager.get("대외활동").getTagManager().addTag("해커톤");
        categoryManager.get("대외활동").getTagManager().addTag("동아리");
        
        // 임의로 추가한 북마크 예시
        bookmarkManager = new BookmarkManager();
        bookmarkManager.addBookmark("타이베이 동먼역 딤섬 맛집", 4);
        bookmarkManager.addBookmark("버스 투어 예약 관련 정보글", 2);
        bookmarkManager.addBookmark("환전 관련 블로그", 3);
        bookmarkManager.addBookmark("대만 이지카드 지하철 정보", 3);
        bookmarkManager.addBookmark("항공권 예약", 5);
        bookmarkManager.addBookmark("여행 경비 정리", 5);
        bookmarkManager.get("타이베이 동먼역 딤섬 맛집").getTagManager().addTag("대만");
        bookmarkManager.get("버스 투어 예약 관련 정보글").getTagManager().addTag("대만");
        bookmarkManager.get("환전 관련 블로그").getTagManager().addTag("대만");
        bookmarkManager.get("대만 이지카드 지하철 정보").getTagManager().addTag("대만");
        bookmarkManager.get("항공권 예약").getTagManager().addTag("대만");
        bookmarkManager.get("여행 경비 정리").getTagManager().addTag("대만");
        bookmarkManager.get("여행 경비 정리").getTagManager().addTag("일본");
        
        /* ----- 예시 ----- */
        
        
        
        // 카테고리 목록 패널에 카테고리 버튼 생성
        panintCategoryPanel();
        
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	// 카테고리 목록 패널에 카테고리 버튼 생성
	private void panintCategoryPanel() {
		categoryPanel.removeAll();
		// 카테고리 추가 버튼
		JButton addCategoryButton = new JButton("+");
		addCategoryButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String categoryName = JOptionPane.showInputDialog(null, "추가할 카테고리의 이름을 입력해주세요.", "Message", JOptionPane.PLAIN_MESSAGE);
				if (categoryName != null && !categoryName.trim().isEmpty()) {
					categoryManager.addCategory(categoryName);
					panintCategoryPanel();	// 카테고리 목록 패널 업데이트
		        }
			}
		});
		categoryPanel.add(addCategoryButton);
		
		for (int i = 0; i < categoryManager.getSize(); i++) {
        	JPanel buttonPanel = new JPanel();
        	buttonPanel.setLayout(new BorderLayout());
        	
        	String Name = categoryManager.get(i).getName();
        	JButton categoryButton = new JButton(Name);
        	categoryButton.addActionListener(categoryButtonListener);
        	
        	JButton deleteButton = new JButton("X");
        	deleteButton.setForeground(Color.RED);
        	deleteButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    int result = JOptionPane.showConfirmDialog(null, "해당 카테고리를 삭제하시겠습니까?", "Message", JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.YES_OPTION) {
                    	categoryManager.delCategory(Name);
                    	panintCategoryPanel();	// 카테고리 목록 패널 업데이트
                    }
                }
            });
        	
        	buttonPanel.add(categoryButton, BorderLayout.WEST);
        	buttonPanel.add(deleteButton, BorderLayout.EAST);
        	categoryPanel.add(buttonPanel);
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
		addTagButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String tagName = JOptionPane.showInputDialog(null, "추가할 태그의 이름을 입력해주세요.", "Message", JOptionPane.PLAIN_MESSAGE);
				if (tagName != null && !tagName.trim().isEmpty()) {
					categoryManager.get(buttonText).getTagManager().addTag(tagName);
					panintTagPanel(buttonText);	// 태그 목록 패널 업데이트
		        }
			}
		});
		tagPanel.add(addTagButton);
				
	    // buttonText와 같은 name을 가진 카테고리를 찾고, 그 카테고리의 태그들을 태그 목록 패널에 버튼을 생성
	    for (int i = 0; i < categoryManager.getSize(); i++) {
	    	if (categoryManager.get(i).getName().equals(buttonText)) {
	    		TagManager c_tags = categoryManager.get(i).getTagManager();
	    		if (c_tags != null) {
	        		for (int j =0; j < c_tags.getSize(); j++) {
	        		JPanel buttonPanel = new JPanel();
	                buttonPanel.setLayout(new BorderLayout());
	        	        
	       	        String Name = c_tags.get(j).getName();
	      			JButton tagButton = new JButton(Name);
	       			tagButton.addActionListener(tagButtonListener);
	        			
	       			JButton deleteButton = new JButton("X");
	               	deleteButton.setForeground(Color.RED);
	               	deleteButton.addActionListener(new ActionListener() {
	               		public void actionPerformed(ActionEvent e) {
	               			int result = JOptionPane.showConfirmDialog(null, "해당 태그를 삭제하시겠습니까?", "Message", JOptionPane.YES_NO_OPTION);
	                        if (result == JOptionPane.YES_OPTION) {
	                           	c_tags.delTag(Name);
	                           	panintTagPanel(buttonText);	// 태그 목록 패널 업데이트
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
    			ArrayList<Bookmark> toBePrinted = new ArrayList<>();
    			for (int i = 0; i < bookmarkManager.getSize(); i++) {
    				TagManager b_tags = bookmarkManager.get(i).getTagManager();
    				if (b_tags != null) {
    					for (int j = 0; j < b_tags.getSize(); j++) {
    						if (b_tags.get(j).getName().equals(buttonText))
    							toBePrinted.add(bookmarkManager.get(i));
    					}
    				}
    			}
    			// toBePrinted 배열에 있는 북마크들을 북마크 목록 패널에 출력
    			for (int i = 0; i < toBePrinted.size(); i++) {
    				JButton bookmark = new JButton();
    	        	bookmark.setSize(200, 200);
    	        	int x = 75 + 225*(i%3);
    	            int y = 75 + 275*(i/3);
    	        	bookmark.setLocation(x , y);
    	        	bookmark.setBackground(Color.LIGHT_GRAY);
    	            bookmark.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    	        	bookmark.setLayout(null);
    	        	
    	        	Bookmark current = toBePrinted.get(i);
    	        	
    	        	JPanel titlePanel = new JPanel();
    	        	titlePanel.setLayout(new BorderLayout());
    	        	titlePanel.setLocation(15, 15);
    	        	titlePanel.setSize(170, 65);
    	        	titlePanel.setBackground(new Color(255, 255, 255, 0));
    	        	titlePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    	            JLabel titleLabel = new JLabel(current.getTitle());
    	            titlePanel.add(titleLabel, BorderLayout.CENTER);
    	            bookmark.add(titlePanel);
    	            
    	            JPanel datePanel = new JPanel();
    	            datePanel.setLocation(15, 85);
    	            datePanel.setSize(170, 30);
    	            datePanel.setBackground(new Color(255, 255, 255, 0));
    	            datePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    	            JLabel dateLabel = new JLabel(current.getYear() + "." + toBePrinted.get(i).getMonth() + "." + toBePrinted.get(i).getDay());
    	            datePanel.add(dateLabel);
    	            bookmark.add(datePanel);
    	            
    	            JPanel importancePanel = new JPanel();
    	            importancePanel.setLocation(15, 120);
    	            importancePanel.setSize(170, 30);
    	            importancePanel.setBackground(new Color(255, 255, 255, 0));
    	            importancePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    	            for (int j = 0; j < current.getImportance(); j++) {
    	            	JLabel importanceLabel = new JLabel("star");
    	            	importanceLabel.setSize(20, 20);
    	            	importancePanel.add(importanceLabel);
    	            }
    	            bookmark.add(importancePanel);
    	            
    	            JPanel tagPanel = new JPanel();
    	            tagPanel.setLocation(15, 155);
    	            tagPanel.setSize(170, 30);
    	            tagPanel.setBackground(new Color(255, 255, 255, 0));
    	            tagPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    	            for (int j = 0; j < current.getTagManager().getSize(); j++) {
    	            	if (j == 0) {
    	            		JLabel tagLabel = new JLabel(current.getTagManager().get(j).getName());
    	            		tagPanel.add(tagLabel);
    	            	}
    	            	else {
    	            		JLabel tagLabel = new JLabel("/ " + current.getTagManager().get(j).getName());
    	            		tagPanel.add(tagLabel);
    	            	}
    	            	
    	            }
    	            bookmark.add(tagPanel);
    	            
    	            bookmarkPanel.add(bookmark);
    	            bookmarkPanel.setPreferredSize(new Dimension(800, Math.max(bookmarkPanel.getPreferredSize().height, y + 275)));
    			}
    		}
    		bookmarkPanel.revalidate();
    		bookmarkPanel.repaint();
    	}
    }
	
	public static void main(String[] args) {
		new PersonalPage();
	}
}