import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.HeadlessException;
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

public class SignInPage1 extends JFrame {

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

	public SignInPage1() throws ClassNotFoundException, SQLException {
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
		
		// 경고문구
		/*
		이름 : 이름을 입력 해 주세요.
		아이디 : 아이디를 입력 해 주세요.
		비밀번호 : 비밀번호를 입력 해 주세요.
		핸드폰 번호 : 핸드폰 번호를 정확히 입력 해 주세요. ex) 010-1234-5678
		생년월일 : 생년월일을 정확히 입력 해 주세요. ex) YYYY-MM-DD
		닉네임 : 닉네입을 입력 해 주세요.
		 */
	}
	
	class idDoubleCheck implements ActionListener {
		public void actionPerformed(ActionEvent e) {
					try {
						checkIDMethod(signUpIdF.getText());
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		}
	}
	// 중복확인
	
	class addMember implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			
			String a = nameF.getText();
			String b = signUpIdF.getText();
			String ee = phoneNumberF.getText();
			String f = birthF.getText();
			String g = nickNameF.getText();
			String asd = ""; 
			String asd2 = "";
			
			for(int i= 0; i<signUpPwF.getPassword().length; i++) {
				asd = asd + signUpPwF.getPassword()[i];
			}
			for(int i= 0; i<signUpPwCheckF.getPassword().length; i++) {
				asd2 = asd2 + signUpPwCheckF.getPassword()[i];
			}
			
			if(a.equals("") || b.equals("")  || asd.equals("")  || asd2.equals("") || ee.equals("")  || f.equals("") || g.equals("")) {
				JOptionPane.showMessageDialog(null, "모든 항목을 빠짐없이 입력 해 주세요");
			} else if (a != null && b != null &&  asd != null  && asd2 != null && ee != null  && f != null && g != null) 
					if(checkIDMethod(signUpIdF.getText()) != false) {
						JOptionPane.showMessageDialog(null, "이미 존재하는 아이디 입니다");
					} else if(passCheck() == false) {
						JOptionPane.showMessageDialog(null, "비밀번호를 정확히 입력 해 주세요");
					} else if (phoneNumCheck() == false){
						insertMember();
					} 
		}
//			try {
//				if(checkIDMethod(signUpIdF.getText()) == false) {
//					if (passCheck() == true) {
//						if(a.equals("") || b.equals("")  || c.equals("")  || d.equals("") || ee.equals("")  || f.equals("")) {
//							JOptionPane.showMessageDialog(null, "모든 항목을 빠짐없이 입력 해 주세요");
//							try {
//								insertMember();
//							} catch (SQLException e1) {
//								// TODO Auto-generated catch block
//								e1.printStackTrace();
//							}
//						} 
//					} else {
//						JOptionPane.showMessageDialog(null, "비밀번호를 정확히 입력 해 주세요");
//					}
//				} else {
//					JOptionPane.showMessageDialog(null, "이미 존재하는 아이디 입니다");
//				}
//			} catch (HeadlessException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			} catch (SQLException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
	// 회원정보 DB에 등록
	
	public boolean checkIDMethod(String id) throws SQLException {
		int check = 0;
		char alpha;
		int code;
		
		boolean result = false;
		String sql = "select id from student where id = ?";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, id);
		rs = pstmt.executeQuery();
		
		if (check == 0) {
			for (int i = 0; i < id.length(); i++) {
				alpha = id.charAt(i);
				code = (int) alpha;	
				if (code >= 0 && code <= 47 || code >= 58 && code <= 64 || code >= 91 && code <= 96
						|| code >= 123 && code <= 127) {
					check = 1; 
					result = true;
					JOptionPane.showMessageDialog(null, "아이디에 특수문자는 포함될 수 없습니다.");
				}
			}
			
			if (check == 0) {
				if (rs.next()) {
					result = true;
					JOptionPane.showMessageDialog(null, "이미 존재하는 아이디 입니다");
				} else {
					result = false;
					JOptionPane.showMessageDialog(null, "사용 가능한 아이디 입니다");
				}
			}
		}
		return result;
		
	}
	// ID 특수문자 포함 안되게
	// ID OK면 DB에 있는 아이디인지 체크
		
	public boolean passCheck() {
		
		String str = "";	
		char str1[] = signUpPwF.getPassword();
		char str2[] = signUpPwCheckF.getPassword();
		boolean result = false;
		boolean flag = true;

		if (str1.length == str2.length) {
			for (int i = 0; i < str1.length; i++) {
				if (str1[i] != str2[i]) {
					flag = false;
					break;
				}
				str += str1[i];
			}
			if (flag) {
				result = true;
				JOptionPane.showMessageDialog(null, nameF.getText() + "님 가입 완료 되었습니다.");
			} else {
				result = false;
			}
		} else {
			JOptionPane.showMessageDialog(null, "비밀번호를 확인 해 주세요");
		}
		return result;
		// 비밀번호란, 비밀번호 확인란 서로 일치하는지 확인
		// 비밀번호 length 다르면
	}

	public void birthCheck(String a) {
		String birth = birthF.getText();
		String regex = "^010-([1-9]{1}\\d{3})-(\\d{4})$";
	}
	
	public void phoneNumCheck(String asd) {
		String pNum = phoneNumberF.getText();
		String regex = "^010-([1-9]{1}\\d{3})-(\\d{4})$";
		if(pNum.matches(regex) == true) {
			System.out.println("ok");
		} else if(pNum.matches(regex) == false) {
			System.out.println("asd");
		}
	}
	
	public void insertMember() throws SQLException {

		String Id = signUpIdF.getText();
		String password = signUpPwF.getText();
		String phoneNumber = phoneNumberF.getText();
		String name = nameF.getText();
		String birthday = birthF.getText();
		String nickName = nickNameF.getText();

		stmt = conn.createStatement();
		int result = stmt.executeUpdate("insert into choongang.student values(0,'" + Id + "','" + password + "','"
				+ phoneNumber + "','" + name + "','" + birthday + "','" + nickName + "','" + null + "')");
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

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		new SignInPage1();
		
	}
}
}
