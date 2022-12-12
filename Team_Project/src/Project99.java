import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Project99 extends JFrame {
	
	Connection conn;
	Statement stmt;
	ResultSet rs;
	PreparedStatement pstmt;
	
	Container con = this.getContentPane();
	JTextField nameF = new JTextField();
	JTextField signUpIdF = new JTextField();
	JPasswordField signUpPwF = new JPasswordField();
	JPasswordField signUpPwCheckF = new JPasswordField();
	JTextField phoneNumberF = new JTextField();
	JTextField birthF = new JTextField();
	JTextField nickNameF = new JTextField();
	JLabel nameL = new JLabel("이름");
	JLabel signUpIdL = new JLabel("아이디");
	JLabel signupPwL = new JLabel("비밀번호");
	JLabel signUpPwCheckL = new JLabel("비밀번호 확인");
	JLabel phoneNumberL = new JLabel("휴대폰 번호");
	JLabel birthL = new JLabel("생년월일");
	JLabel nickNameL = new JLabel("닉네임");
	JButton doublecheck, ok, cancel, getMember;
	JPanel signupPanel = new JPanel();
	
	public Project99() throws ClassNotFoundException, SQLException {
		getConn();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 창 닫기 버튼 누르면 꺼지게 설정
		
		setBounds(50, 50, 1000, 800);
		setVisible(true);
		con.add(signupPanel, BorderLayout.CENTER);
		this.add(signupPanel);
		signupPanel.setVisible(true);
		signupPanel.setLayout(null);
		this.add(signupPanel);
		signupPanel.add(nameL);
		nameL.setBounds(350, 20, 100, 30);
		signupPanel.add(nameF);
		nameF.setBounds(420, 25, 120, 25);
		signupPanel.add(signUpIdL);
		signUpIdL.setBounds(350, 80, 100, 30);
		signupPanel.add(signUpIdF);
		signUpIdF.setBounds(420, 85, 120, 25);
		signupPanel.add(signupPwL);
		signupPwL.setBounds(350, 140, 100, 30);
		signupPanel.add(signUpPwF);
		signUpPwF.setBounds(420, 145, 120, 25);
		signupPanel.add(signUpPwCheckL);
		signUpPwCheckL.setBounds(320, 200, 100, 30);
		signupPanel.add(signUpPwCheckF);
		signUpPwCheckF.setBounds(420, 205, 120, 25);
		signupPanel.add(phoneNumberL);
		phoneNumberL.setBounds(350, 260, 100, 30);
		signupPanel.add(phoneNumberF);
		phoneNumberF.setBounds(420, 265, 120, 25);
		signupPanel.add(birthL);
		birthL.setBounds(350, 320, 100, 30);
		signupPanel.add(birthF);
		birthF.setBounds(420, 325, 120, 25);
		signupPanel.add(nickNameL);
		nickNameL.setBounds(350, 380, 100, 30);
		signupPanel.add(nickNameF);
		nickNameF.setBounds(420, 385, 120, 25);
		// 필드, 라벨 레이아웃
		
		doublecheck = new JButton("중복체크");
		signupPanel.add(doublecheck);
		doublecheck.setBounds(570, 83, 90, 30);
		ok = new JButton("확인");
		signupPanel.add(ok);
		ok.setBounds(350, 440, 90, 30);
		cancel = new JButton("취소");
		signupPanel.add(cancel);
		cancel.setBounds(500, 440, 90, 30);
		// 버튼 레이아웃
		
		doublecheck.addActionListener(new idDoubleCheck());
		// 중복체크 버튼 클릭
		ok.addActionListener(new addMember());
		// 확인 버튼 클릭
	}
	class idDoubleCheck implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				checkIDMethod(signUpIdF.getText());
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
	// 중복확인
		
	class addMember implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				insertMember();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
	// 회원정보 DB에 등록
	
	public void checkIDMethod(String id) throws SQLException {
		int check = 0;
		char alpha;
		int code;
		
		boolean result = false;
		String sql = "select id from student where id = ?";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, id);
		rs = pstmt.executeQuery();
		
		if(check == 0) {
		for (int i = 0; i < id.length(); i++) {
			alpha = id.charAt(i);
			code = (int) alpha;
			if (code >= 0 && code <= 47 || code >= 58 && code <= 64 || code >= 91 && code <= 96
					|| code >= 123 && code <= 127) {
				check = 1;
				JOptionPane.showMessageDialog(this, "아이디에 특수문자는 포함될 수 없습니다.");
			}
		}
		
		if(check == 0) {
			if (rs.next()) {
				result = true;
				JOptionPane.showMessageDialog(this, "이미 존재하는 아이디 입니다");
			} else {
				result = false;
				JOptionPane.showMessageDialog(this, "사용 가능한 아이디 입니다");
			}
		}
		}
	}
	// ID 특수문자 포함 안되게
	// ID OK면 DB에 있는 아이디인지 체크
		
	public boolean passCheck() {
		
		String str = "";
		char str1[] = signUpPwF.getPassword();
		char str2[] = signUpPwCheckF.getPassword();
		boolean result = false;
		boolean flag = true;
		for (int i = 0; i < str1.length; i++) {
			if (str1[i] != str2[i]) {
				flag = false;
				break;
			}
			str += str1[i];
		}
		if (flag) {
			System.out.println(str);
			result = true;
		} else {
			System.out.println("암호 불일치");
			result = false;
		}
		return result;
	}
	// 비밀번호란, 비밀번호 확인란 서로 일치하는지 확인	
	
	public void insertMember() throws SQLException {
		
		String Id = signUpIdF.getText();
		String password = signUpPwF.getText();
		String phoneNumber = phoneNumberF.getText();
		String name = nameF.getText();
		String birthday = birthF.getText();
		String nickName = nickNameF.getText();
		
		stmt = conn.createStatement();
		int result = stmt.executeUpdate("insert into choongang.student values(0,'" + Id + "','" + password
				+ "','" + phoneNumber + "','" + name + "','" + birthday + "','" + nickName + "','"+null+"')");
		System.out.println(result + "건 입력 성공");
	}
	// 확인버튼 눌렀을때 DB로 값들 전송
		
	public void getConn() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/choongang", "root", "mysql");
			System.out.println("DB연결 성공");
		} catch (Exception e) {
			System.out.println("DB연결 에러발생" + e.getMessage());
		}
	}
	// DB접속
			
	public void join() {
		String name = nameF.getText();
		String id = signUpIdF.getText();
		String passWord = signUpPwF.getText();
		String passWordC = signUpPwCheckF.getText();
		String pNumber = phoneNumberF.getText();
		String birth = birthF.getText();
		String nickName = nickNameF.getText();
			
	}
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		new Project99();
	}


}