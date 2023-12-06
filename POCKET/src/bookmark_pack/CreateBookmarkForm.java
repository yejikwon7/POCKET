package bookmark_pack;

import javax.swing.*;
import java.awt.event.*;
import java.net.URI;
import java.util.ArrayList;
import java.awt.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.*;
import java.io.IOException;
import java.util.List;

public class CreateBookmarkForm extends JFrame {
	private Bookmark bookmarkData;
	private JEditorPane pContent = new JEditorPane();
	private JPanel cPanel = new JPanel(); // 카테고리 패널
	private JPanel tPanel = new JPanel(); // 태그 패널
	private JPanel starPanel = new JPanel(); // 별 패널
	private int clickedStars = 0;
	private ImageIcon logo = new ImageIcon("images/logo.png");
	private JButton logoBtn = new JButton(logo);
	private JTextField title = new JTextField(50);
	private JButton btnTstore = new JButton("임시저장");
	private JButton btnStore = new JButton("작성");
	private JButton btnMemo = new JButton("메모 추가");
	private CategoryManager1 categoryManager;
	private TagManager1 tagManager;
	private static int id;
	private static String uid;
	private String clickedCategory = null;
	private String clickedTag = null;
	private JButton urlBtn = new JButton("입력");
	private JTextField addressField = new JTextField("https://www.example.com");
	private String contenturl;
	
	public CreateBookmarkForm(int id, String uid) {
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
		title.setLocation(100, 110);
		title.setSize(600, 40);
		title.setFont(new Font("맑은 고딕", Font.BOLD, 25));
		title.setHorizontalAlignment(JLabel.LEFT);
		
		// 내용 입력
		pContent.setLayout(new BorderLayout());
		pContent.setBounds(90, 200, 820, 400);
		pContent.setContentType("text/html");
		pContent.setFont(new Font("맑은 고딕", Font.PLAIN, 25));
		JScrollPane contentScroll = new JScrollPane(pContent);
		contentScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		contentScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		contentScroll.getVerticalScrollBar().setUnitIncrement(10);
		contentScroll.setBounds(90, 200, 820, 400);
		
		// 주소 입력 텍스트필드
		addressField.setBounds(100, 160, 730, 30);
		addressField.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		
		// 주소 입력 버튼
		urlBtn.setBounds(840, 160, 60, 30);
		urlBtn.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		urlBtn.setBackground(Color.decode("#EEEEEE"));
		urlBtn.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 3));
		urlBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                String url = addressField.getText();
                String contenturl = loadWebPage(pContent, url);
 
        		pContent.setText(contenturl);
            }
		});
		
		// 버튼 마우스 오버에 대한 처리를 담당하는 리스너
		urlBtn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                // 마우스가 컴포넌트에 들어왔을 때
            	urlBtn.setBorder(BorderFactory.createLineBorder(Color.decode("#00ADB5"), 3));
            	urlBtn.setBackground(Color.WHITE);
            }
            public void mouseExited(MouseEvent e) {
                // 마우스가 컴포넌트에서 나갔을 때
            	urlBtn.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 3));
            	urlBtn.setBackground(Color.decode("#EEEEEE"));
            }
        });

		
		categoryManager = new CategoryManager1();
		tagManager = new TagManager1();
		cPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		cPanel.setBounds(100, 620, 400, 40);
		
		tPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		tPanel.setBounds(100, 670, 400, 40);
		
        starPanel.setBounds(500, 610, 400, 50);
        starPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        for (int i = 1; i <= 5; i++) {
            JLabel starLabel = createStarLabel();
            starLabel.setFont(new Font("맑은 고딕", Font.BOLD, 30));
            
            starPanel.add(starLabel);
        }
		
		btnTstore.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		btnTstore.setLocation(739, 680);
		btnTstore.setSize(90, 30);
		btnTstore.setBackground(Color.decode("#EEEEEE"));
		btnTstore.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 3));
		btnTstore.addActionListener(new TstoreActionListener());
		
		// 버튼 마우스 오버에 대한 처리를 담당하는 리스너
		btnTstore.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                // 마우스가 컴포넌트에 들어왔을 때
            	btnTstore.setBorder(BorderFactory.createLineBorder(Color.decode("#00ADB5"), 3));
            	btnTstore.setBackground(Color.WHITE);
            }
            public void mouseExited(MouseEvent e) {
                // 마우스가 컴포넌트에서 나갔을 때
            	btnTstore.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 3));
            	btnTstore.setBackground(Color.decode("#EEEEEE"));
            }
        });
		
		btnStore.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		btnStore.setLocation(839, 680);
		btnStore.setSize(60, 30);
		btnStore.setBackground(Color.decode("#EEEEEE"));
		btnStore.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 3));
		btnStore.addActionListener(new StoreActionListener());
		
		// 버튼 마우스 오버에 대한 처리를 담당하는 리스너
		btnStore.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                // 마우스가 컴포넌트에 들어왔을 때
            	btnStore.setBorder(BorderFactory.createLineBorder(Color.decode("#00ADB5"), 3));
            	btnStore.setBackground(Color.WHITE);
            }
            public void mouseExited(MouseEvent e) {
                // 마우스가 컴포넌트에서 나갔을 때
            	btnStore.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 3));
            	btnStore.setBackground(Color.decode("#EEEEEE"));
            }
        });
		
		c.add(logoBtn);
		c.add(title);
		c.add(contentScroll);
		c.add(cPanel);
		c.add(tPanel);
		c.add(starPanel);
		c.add(btnTstore);
		c.add(btnStore);
		c.add(btnMemo);
		c.add(addressField);
		c.add(urlBtn);
		
        // 프레임이 생성되면서 데이터베이스로부터 카테고리와 태그를 불러옴
		loadCategoriesAndTags();
		paintCategoryPanel();
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

        // 카테고리와 태그 목록 패널을 업데이트
        paintCategoryPanel();
    }
	
	// 카테고리 목록 패널에 카테고리 버튼 생성
	private void paintCategoryPanel() {
		cPanel.removeAll();
		
		if (categoryManager != null) {
			for (int i = 0; i < categoryManager.getSize(); i++) {
	        	JPanel buttonPanel = new JPanel();
	        	buttonPanel.setLayout(new BorderLayout());
	        	
	        	String Name = categoryManager.get(i).getName();
	        	JButton categoryButton = new JButton(Name);	
	        	categoryButton.addActionListener(new CategoryButtonListener());
	        	categoryButton.setBackground(Color.WHITE);
	        	
	        	buttonPanel.add(categoryButton, BorderLayout.WEST);
	        	cPanel.add(buttonPanel);
	        }
		}
		
		cPanel.revalidate();
		cPanel.repaint();
	}
	
	// 로고 버튼 리스너
	class LogoActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// 메인페이지로 돌아가는 코드
			dispose();
			new PersonalPage(id, uid);
		}
	}
	
	// url 추출
	private static String loadWebPage(JEditorPane pContent, String url) {
        
        try {
            // Jsoup을 사용하여 웹 페이지의 본문만 추출
            String bodyContent = extractBodyContent(url);

            // JEditorPane에 본문 설정
            pContent.setText("<html>" + bodyContent + "</html>");

        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return pContent.getText();
    }
	
	private static String extractBodyContent(String url) throws IOException {
		// Jsoup을 사용하여 웹 페이지를 파싱
        Document document = Jsoup.connect(url).get();

        // 본문 내용 추출
        Element body = document.body();

        // 본문에서 필요한 부분을 추출 (예: 본문 내의 모든 p 태그의 내용)
        Elements paragraphs = body.select("p, table, img");

        // 추출한 내용과 이미지를 순서대로 저장하는 리스트
        List<String> contentList = new ArrayList<>();
        List<String> imageList = new ArrayList<>();

        // 본문 내용을 리스트에 추가
        for (Element paragraph : paragraphs) {
            contentList.add(paragraph.outerHtml());
        }

        // 리스트에 저장된 내용을 문자열로 합치기
        StringBuilder urlcontent = new StringBuilder();
        for (String item : contentList) {
        	urlcontent.append(item).append("<br>");
        }
        
        return urlcontent.toString();
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
		tPanel.removeAll();
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
	    tagButton.setBackground(Color.decode("#00ADB5"));
	}

	private void resetTagColor(JButton tagButton) {
	    tagButton.setBackground(null); // 원래대로 되돌림
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
            }

            @Override
            public void mouseExited(MouseEvent e) {
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
	        if (i == clickedIndex && i < clickedStars) {
	            // 클릭된 별보다 뒤에 있는 별들은 색상을 변경하지 않음
	            continue;
	        }

	        if (i <= clickedIndex) {
	            // 현재 클릭된 별의 색상을 토글 (노란색 ↔ 검정색)
	            Color currentColor = starLabel.getForeground();
	            if (currentColor == Color.decode("#F2CB05")) {
	                starLabel.setForeground(Color.BLACK);
	            } else {
	                starLabel.setForeground(Color.decode("#F2CB05"));
	            }
	        } else if (i < clickedIndex) {
	            // 클릭된 별보다 앞에 있는 별들은 노란색으로 변경
	            starLabel.setForeground(Color.decode("#F2CB05"));
	        } else {
	            // 클릭된 별보다 뒤에 있는 별들은 검정색으로 변경
	            starLabel.setForeground(Color.BLACK);
	        }
	    }
	    // 클릭된 별의 개수 업데이트
	    clickedStars = clickedIndex + 1;
    }
	
	// 임시저장 버튼 리스너
	class TstoreActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// 텍스트 필드에서 제목과 내용을 가져옵니다.
            String bookmarkTitle = title.getText();
            String bookmarkContent = pContent.getText();

            // 임시 북마크 객체를 생성합니다.
            Bookmark temporaryBookmark = new Bookmark(
                bookmarkContent, clickedStars, getCurrentDate(), bookmarkTitle, clickedCategory, clickedTag, id
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
            String bookmarkContent = pContent.getText();

            // 북마크 객체를 생성합니다.
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
        // 현재 날짜를 문자열로 가져오는 로직
        return java.time.LocalDate.now().toString();
    }
	
	private void showFrame() {
		setLocationRelativeTo(null);
		setTitle("북마크 생성");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}

}
