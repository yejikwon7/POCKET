package initialPage;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class InitialPage extends JFrame {
	private ImageIcon logoImage = new ImageIcon("images/X.jpg");
	private JLabel logoLabel;	
	private JButton joinLoginButton;
	private JPanel categoriesPanel;
	private ArrayList<JButton> categoryButtons;
	private JPanel tagsPanel;
	private ArrayList<JButton> tagButtons;
	private JPanel pagesPanel;
	private ArrayList<JPanel> pagePanels;
	private JScrollPane scrollPane;
	
	public InitialPage() {
		setTitle("POCKET");
		setSize(1000, 800);
		
		Container c = getContentPane();
        c.setLayout(null);

		// 로고
		logoLabel = new JLabel(logoImage);
		logoLabel.setBounds(400, 20, 200, 50);
		c.add(logoLabel);
		
		// 회원가입/로그인
		joinLoginButton = new JButton("회원가입/로그인");
		joinLoginButton.setBounds(800, 20, 150, 30);
		c.add(joinLoginButton);
		
		// 카테고리 배열
		categoriesPanel = new JPanel();
		categoriesPanel.setBounds(100, 100, 800, 40);
		categoriesPanel.setBackground(Color.LIGHT_GRAY);
		categoriesPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		categoryButtons = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
            JButton categoryButton = new JButton("Category " + (i + 1));
            categoryButtons.add(categoryButton);
            categoriesPanel.add(categoryButton);
        }
		c.add(categoriesPanel);
		
		// 태그 배열
		tagsPanel = new JPanel();
		tagsPanel.setBounds(100, 150, 800, 40);
		tagsPanel.setBackground(Color.LIGHT_GRAY);
		tagsPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		tagButtons = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
            JButton tagButton = new JButton("Tag " + (i + 1));
            tagButtons.add(tagButton);
            tagsPanel.add(tagButton);
        }
		c.add(tagsPanel);
		
		// 북마크 페이지 배열
		pagesPanel = new JPanel();
		pagesPanel.setBounds(100, 200, 800, 1500);
        pagesPanel.setLayout(new GridLayout(0, 3));
        pagePanels = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            JPanel pagePanel = new JPanel();
            pagePanel.setSize(250, 250);
            pagePanel.setBackground(Color.LIGHT_GRAY);
            // 페이지 정보를 표시하는 라벨 등을 추가 (제목, 태그, 중요도, 작성 날짜, 작성자)
            // 여기서는 라벨만 추가하고 내용은 비워둠
            JLabel titleLabel = new JLabel("Title " + (i + 1));
            pagePanel.add(titleLabel);
            pagePanels.add(pagePanel);
            pagesPanel.add(pagePanel);
        }
        c.add(pagesPanel);
        
        scrollPane = new JScrollPane(pagesPanel);
        scrollPane.setBounds(100, 200, 800, 500);
        c.add(scrollPane);
        
		setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public static void main(String[] args) {
		new InitialPage();
	}
}
