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
        bookmarkManager.addBookmark("대만 여행 경비 정리", 5);
        bookmarkManager.addBookmark("버스 투어 예약 관련 정보글", 2);
        bookmarkManager.addBookmark("환전 관련 블로그", 3);
        bookmarkManager.addBookmark("대만 이지카드 지하철 정보", 3);
        bookmarkManager.addBookmark("항공권 예약", 5);
        bookmarkManager.addBookmark("일본 여행 경비 정리", 5);
        bookmarkManager.get("타이베이 동먼역 딤섬 맛집").getTagManager().addTag("대만");
        bookmarkManager.get("대만 여행 경비 정리").getTagManager().addTag("대만");
        bookmarkManager.get("버스 투어 예약 관련 정보글").getTagManager().addTag("대만");
        bookmarkManager.get("환전 관련 블로그").getTagManager().addTag("대만");
        bookmarkManager.get("대만 이지카드 지하철 정보").getTagManager().addTag("대만");
        bookmarkManager.get("항공권 예약").getTagManager().addTag("대만");
        bookmarkManager.get("일본 여행 경비 정리").getTagManager().addTag("일본");
        
        // 카테고리 목록 패널에 카테고리 버튼 생성
        for (int i = 0; i < categoryManager.getSize(); i++) {
        	JButton categoryButton = new JButton(categoryManager.get(i).getName());
        	categoryPanel.add(categoryButton);
        	categoryButton.addActionListener(categoryButtonListener);
        }
        
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	// 카테고리 버튼 클릭 시, 태그 목록 패널에 태그 버튼들을 생성하는 CategoryButtonListener
	private class CategoryButtonListener implements ActionListener {
    	public void actionPerformed(ActionEvent e) {
    		tagPanel.removeAll();
    		// 클릭된 버튼의 텍스트를 get
    		if (e.getSource() instanceof JButton) {
    			JButton clickedButton = (JButton) e.getSource();
    	        String buttonText = clickedButton.getText();
    	        // 버튼의 텍스트와 같은 name을 가진 카테고리를 찾고, 그 카테고리의 태그들을 태그 목록 패널에 버튼을 생성
    	        for (int i = 0; i < categoryManager.getSize(); i++) {
    	        	if (categoryManager.get(i).getName().equals(buttonText)) {
    	        		TagManager c_tags = categoryManager.get(i).getTagManager();
    	        		if (c_tags != null) {
    	        			for (int j =0; j < c_tags.getSize(); j++) {
    	        			JButton tagButton = new JButton(c_tags.get(j).getName());
    	                	tagPanel.add(tagButton);
    	                	tagButton.addActionListener(tagButtonListener);
    	        			}
    	        		}
    	        	}
    	        }
    		}
    		tagPanel.revalidate();
            tagPanel.repaint();
    	}	
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
    				JPanel bookmark = new JPanel();
    	        	bookmark.setSize(200, 200);
    	        	int x = 75 + 225*(i%3);
    	            int y = 75 + 275*(i/3);
    	        	bookmark.setLocation(x , y);
    	        	bookmark.setBackground(new Color(255, 255, 255, 0));
    	            bookmark.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    	        	bookmark.setLayout(new BoxLayout(bookmark, BoxLayout.Y_AXIS));
    	            JLabel titleLabel = new JLabel("Title : " + toBePrinted.get(i).getTitle());
    	            JLabel importanceLabel = new JLabel("Importance : " + toBePrinted.get(i).getImportance());
    	            JLabel dateLabel = new JLabel("Date : " + toBePrinted.get(i).getYear() + "." + toBePrinted.get(i).getMonth() + "." + toBePrinted.get(i).getDay());
					// 태그 출력 부분 . . .
//    	            TagManager b_tags = toBePrinted.get(i).getTagManager();
//    	            StringBuilder stringBuilder = new StringBuilder();
//    	            for (Tag tag : b_tags) {
//    	                stringBuilder.append(tag).append(" ");
//    	            }
//    	            String b_tagsLabelText = stringBuilder.toString();
//    	            JLabel b_tagsLabel = new JLabel(b_tagsLabelText);
    	            bookmark.add(titleLabel);
    	            bookmark.add(importanceLabel);
    	            bookmark.add(dateLabel);
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