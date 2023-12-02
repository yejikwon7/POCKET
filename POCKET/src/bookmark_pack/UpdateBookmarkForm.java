package bookmark_pack;

import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.awt.*;
import java.awt.datatransfer.*;

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
	private CategoryManager1 categoryManager1;
//	private ArrayList<JTextArea> memoList = new ArrayList<>();
//    private int memoCounter = 1; // 메모의 순서를 기록하기 위한 변수
//    private Point mousePressedPoint; // 드래그 시작 지점을 저장하기 위한 변수

	
	public UpdateBookmarkForm(Bookmark bookmarkData) {
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
//		btnMemo.addActionListener(new MemoActionListener());
		
		// 내용 입력
		pContent.setLayout(new BorderLayout());
		pContent.setBounds(90, 180, 820, 400);
		content.setBounds(0, 0, 800, 400);
		content.setFont(new Font("맑은 고딕", Font.BOLD, 25));
		content.setLineWrap(true);  // 자동 줄 바꿈 활성화
		content.setWrapStyleWord(true);  // 단어 단위 자동 줄 바꿈 활성화
		// 내용 입력 JTextArea에 MouseListener 및 TransferHandler 추가
//        content.addMouseListener(new ContentMouseListener());
//        content.setTransferHandler(new MemoTransferHandler());
		JScrollPane contentScroll = new JScrollPane(content);
		contentScroll.setBounds(0, 0, 800, 400);
		contentScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		contentScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
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
//		categoryManager1.get("여행").getTagManager().addTag("대만");	
//        categoryManager1.get("여행").getTagManager().addTag("일본");
//        categoryManager1.get("공부").getTagManager().addTag("Java");
//        categoryManager1.get("공부").getTagManager().addTag("Python");
//        categoryManager1.get("대외활동").getTagManager().addTag("공모전");
//        categoryManager1.get("대외활동").getTagManager().addTag("해커톤");
//        categoryManager1.get("대외활동").getTagManager().addTag("동아리");
		
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
//            content.setText(bookmarkData.getContent());
            clickedStars = bookmarkData.getImportance();
            displayStarRating();
            displayCategoriesAndTags();
        } else {
            JOptionPane.showMessageDialog(null, "북마크 정보를 불러오는 데 실패했습니다.", "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
            System.out.println("Title에 대한 Bookmark 데이터가 null입니다: " + this.bookmarkData);
        }
		
		
		setSize(1000, 800);
		showFrame();
	}
	
	// 카테고리와 태그를 표시하는 메서드 추가
    private void displayCategoriesAndTags() {
        // 선택된 카테고리 버튼의 텍스트를 가져와서 설정
        for (Component component : cPanel.getComponents()) {
            if (component instanceof JButton) {
                JButton categoryBtn = (JButton) component;
                if (bookmarkData.getCategory() != null && categoryBtn.getText().equals(bookmarkData.getCategory())) {
                    categoryBtn.setSelected(true);
                    break;
                }
            }
        }

        // 선택된 태그 버튼의 텍스트를 가져와서 설정
        for (Component component : tPanel.getComponents()) {
            if (component instanceof JButton) {
                JButton tagBtn = (JButton) component;
                if (bookmarkData.getTagManager() != null && bookmarkData.getTagManager().contains(tagBtn.getText())) {
                    tagBtn.setSelected(true);
                }
            }
        }
    }
    
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
	
//	// 메모 추가 버튼 리스너
//    class MemoActionListener implements ActionListener {
//        public void actionPerformed(ActionEvent e) {
//        	// 새로운 JTextArea 생성
//            JTextArea memoTextArea = createMemoTextArea();
//            memoList.add(memoTextArea);
//
//            // JFrame에 JTextArea 추가
//            pContent.add(memoTextArea);
//            pContent.revalidate();
//            pContent.repaint();
//        }
//
//        private JTextArea createMemoTextArea() {
//            JTextArea memoTextArea = new JTextArea(5, 30);
//            memoTextArea.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
//            memoTextArea.setLineWrap(true);
//            memoTextArea.setWrapStyleWord(true);
//
//            // JTextArea에 고유한 배경색 부여
//            if (memoCounter % 2 == 0) {
//                memoTextArea.setBackground(new Color(200, 200, 255)); // 파란색 배경
//            } else {
//                memoTextArea.setBackground(new Color(255, 200, 200)); // 빨간색 배경
//            }
//            memoCounter++;
//
//            // JTextArea에 MouseListener 추가
//            memoTextArea.addMouseListener(new ContentMouseListener());
//
//            return memoTextArea;
//        }
//    }
//
// // 내용 입력 JTextArea에 MouseListener 및 TransferHandler 추가
//    class ContentMouseListener extends MouseAdapter {
//        @Override
//        public void mousePressed(MouseEvent e) {
//            mousePressedPoint = e.getPoint();
//        }
//
//        @Override
//        public void mouseReleased(MouseEvent e) {
//            mousePressedPoint = null;
//        }
//    }
//
//    // TransferHandler를 사용하여 JTextArea의 드래그 앤 드롭을 관리
//    class MemoTransferHandler extends TransferHandler {
//        @Override
//        public int getSourceActions(JComponent c) {
//            return TransferHandler.COPY_OR_MOVE;
//        }
//
//        @Override
//        protected Transferable createTransferable(JComponent c) {
//            return new StringSelection(""); // 빈 문자열을 전달
//        }
//
//        @Override
//        public boolean canImport(TransferSupport support) {
//            return support.isDataFlavorSupported(DataFlavor.stringFlavor);
//        }
//
//        @Override
//        public boolean importData(TransferSupport support) {
//            if (!canImport(support)) {
//                return false;
//            }
//
//            JTextArea textArea;
//            try {
//                String data = (String) support.getTransferable().getTransferData(DataFlavor.stringFlavor);
//                textArea = new JTextArea(data);
//                textArea.setBackground(new Color(200, 200, 255)); // 새로운 JTextArea의 배경색 설정
//            } catch (UnsupportedFlavorException | IOException e) {
//                return false;
//            }
//
//            // 드롭 지점 계산
//            JComponent dropComponent = (JComponent) support.getComponent();
//            Point dropPoint = support.getDropLocation().getDropPoint();
//            SwingUtilities.convertPointFromScreen(dropPoint, dropComponent);
//
//            // 새로운 JTextArea를 추가
//            pContent.add(textArea);
//            pContent.revalidate();
//            pContent.repaint();
//
//            return true;
//        }
//    }
	
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

            // 임시 북마크 객체를 생성
            BookmarkData temporaryBookmark = new BookmarkData(
                bookmarkContent, clickedStars, getCurrentDate(), bookmarkTitle, "", ""
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

            // 북마크 객체를 생성
            BookmarkData newBookmark = new BookmarkData(
                bookmarkContent, clickedStars, getCurrentDate(), bookmarkTitle, "", ""
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

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

}
