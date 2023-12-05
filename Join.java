    package src;
	import javax.swing.*;
	import java.awt.*;
	import java.awt.event.*;
	import java.io.*;
	import java.util.*;

	public class Join implements ActionListener {
		Login login = new Login();
		JDialog join = new JDialog();
		
		//�Է� ���� ������
		private JWindow ok = new JWindow(); //��ϼ���
		private JWindow err = new JWindow(); //����
		private JLabel err_Msg = new JLabel();
		private JLabel ok_Msg = new JLabel();
		private JButton err_Ok = new JButton("OK");
		private JButton win_Ok = new JButton("OK");
		
		private JPanel panel_Join = new JPanel();
		private JPanel panel_Left = new JPanel();
		private JPanel panel_Right = new JPanel();
		private JPanel panel_Bottom = new JPanel();
		
		private JLabel lb_Name = new JLabel("  ��  �� ");
		private JLabel lb_Id = new JLabel(" ���̵� ");
		private JLabel lb_Pw1 = new JLabel("��й�ȣ");
		private JLabel lb_Pw2 = new JLabel("  Ȯ   �� ");
		
		private JButton bt_Ok = new JButton("���");
		private JButton bt_Cancel = new JButton("���");
		
		private JTextField tf_Name = new JTextField();
		private JTextField tf_Id = new JTextField();
		private JPasswordField pf_Pw1 = new JPasswordField(null);
		private JPasswordField pf_Pw2 = new JPasswordField(null);

		//ȸ������ ��� �� �ʿ��� ���� ����
		private File dir = new File("UserInfo");
		private File fDairy = new File("Diary");
		private File fSchdule = new File("Schedule");
		private File fAddress = new File("Address");
		public Join() {
			init();
			action();
			join.setTitle("�� ��");
			join.setSize(200, 180);
			
			Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
			int xpos = (int)(screen.getWidth() / 2 - join.getWidth() / 2);
			int ypos = (int)(screen.getHeight() / 2 - join.getHeight() / 2);
			join.setLocation(xpos, ypos);
			
			join.setVisible(true);	
			
			ok.setSize(180, 65);
			ok.setLocation(xpos + 20, ypos + 50);
			err.setSize(180, 65);
			err.setLocation(xpos + 20, ypos + 50);
		}
		
		public void init() {
			join.setLayout(new CardLayout(5, 5));
			panel_Left.setLayout(new GridLayout(4,1));
			panel_Right.setLayout(new GridLayout(4,1));
			panel_Left.add(lb_Name);
			panel_Left.add(lb_Id);
			panel_Left.add(lb_Pw1);
			panel_Left.add(lb_Pw2);
			panel_Right.add(tf_Name);
			panel_Right.add(tf_Id);
			panel_Right.add(pf_Pw1);
			panel_Right.add(pf_Pw2);
			panel_Bottom.add(bt_Ok);
			panel_Bottom.add(bt_Cancel);
			
			panel_Join.setLayout(new BorderLayout());
			panel_Join.add("West", panel_Left);
			panel_Join.add("Center", panel_Right);
			panel_Join.add("South", panel_Bottom);
			join.add("view", panel_Join);
			
			ok.setLayout(new FlowLayout());
			ok.add(ok_Msg);
			ok.add(win_Ok);
			win_Ok.setPreferredSize(new Dimension(51, 25));
			
			err.setLayout(new FlowLayout());
			err.add(err_Msg);
			err.add(err_Ok);
			err_Ok.setPreferredSize(new Dimension(51, 25));

			
		}
		
		public void action() {
			bt_Ok.addActionListener(this);
			bt_Cancel.addActionListener(this);
			err_Ok.addActionListener(this);
			win_Ok.addActionListener(this);
		}
		
		public void actionPerformed(ActionEvent e) {		

			if (e.getSource() == bt_Ok) {
				String temp = "";
				String user = "";
				String name = tf_Name.getText();
				String id = tf_Id.getText();
				String pw1 = new String(pf_Pw1.getPassword());
				String pw2 = new String(pf_Pw2.getPassword());
				
				if(name.equals("") || id.equals("") || pw1.equals("") || pw2.equals("")) {
					err_Msg.setText("ĭ�� ��� ä���ּ���!");
					err.setVisible(true);
					return;
				}else if(!(pw1.equals(pw2))) {
					System.out.println(pw1.toString() + "    " + pw2.toString());
					err_Msg.setText("�о����尡 ���� �ٸ��ϴ�");
					err.setVisible(true);
					pf_Pw1.setText("");
					pf_Pw2.setText("");
					return;
				
				}else {//����� �Է�������� ����� ������ ���Ϸ� ����
					if(!dir.exists()) {
						dir.mkdir();
						fDairy.mkdir();
						fAddress.mkdir();
						fSchdule.mkdir();					
					}else {
						File file = new File(dir, "UserData");	
						BufferedReader br = null;
						if(file.canRead()) {
							try {
								br = new BufferedReader(new FileReader(file));
								while((temp = br.readLine()) != null) {
									user += temp + "\n";
								}
								br.close();
							}catch(Exception ee){}
						}
						
						// ID �ߺ��� �˻� �ϱ� ���� ���� �ִ� ���� ��
						StringTokenizer token = new StringTokenizer(user, " || ");
						String[] tUser = new String[token.countTokens()];
						int i = 0;
						while(token.hasMoreTokens()) {
							tUser[i] = token.nextToken();
							i++;
						}
						for (int j = 0; j < tUser.length; j++) {
							if(tUser[j].equals(id)) {
								JOptionPane.showMessageDialog(login, "������ ID�� �����մϴ�!", "Ȯ��", 
										JOptionPane.INFORMATION_MESSAGE);
								tf_Id.setText("");
								return;
							}
						}
						//�ߺ����� �ʴ� ID�� ���� ����� ������ �߰� ��Ű�� �ܰ�
						file = new File(dir, "UserData");
						try{
							PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file)));
							user  += name + " || " + id + " || " + pw1 + " || " + "\n";
							out.println(user);
							out.close();
						}catch(IOException ee){}		
						ok_Msg.setText("���������� ��ϵǾ����ϴ�!");
						ok.setVisible(true);	
						tf_Id.setText("");
						tf_Name.setText("");
						pf_Pw1.setText("");
						pf_Pw2.setText("");	
					}
				}
			}else if(e.getSource() == bt_Cancel) {
				join.setVisible(false);
			}else if(e.getSource() == err_Ok) {
				err.setVisible(false);
				join.setVisible(true);
			}else if(e.getSource() == win_Ok){
				ok.setVisible(false);
				join.setVisible(false);
			}
		}
	}
