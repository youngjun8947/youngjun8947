    package src; 
	import java.io.*;
	import java.awt.*;
	import javax.swing.*;

	import java.awt.event.*;
	import javax.swing.border.*;
	import java.text.SimpleDateFormat;
	import java.util.Calendar;
	import java.util.TimeZone;

	public class Diary extends JFrame implements ActionListener, WindowListener{
		public Container con_Diary = this.getContentPane();
		
		public JDialog dig_Write = new JDialog(this, "�ϱ⾲��", true);
		private JPanel panel_List = new JPanel();
		private JPanel panel_View = new JPanel();
		private JPanel panel_Button1 = new JPanel();
		private JPanel panel_Button2 = new JPanel();
		private JButton bt_Write = new JButton("�ϱ⾲��");
		private JButton bt_Del = new JButton("�����ϱ�");
		private JButton bt_Refresh = new JButton("���ΰ�ħ");
		private JButton bt_Modify = new JButton("�����ϱ�");
		private JButton bt_Wwrite = new JButton("�ۼ��Ϸ�");
		private JButton bt_Wcancel = new JButton("�� ��");
		private List list = new List();
		private JScrollPane sp_List  = new JScrollPane(list);
		private JTextArea ta_View = new JTextArea();
		private JScrollPane sp_View = new JScrollPane(ta_View);
		
		// �ϱ� �ۼ�	
		private JTextField tf_Title = new JTextField();
		private JTextArea ta_Write = new JTextArea();
		private JScrollPane sp_Write = new JScrollPane(ta_Write);
		
		//���� ��¥ ���
		private Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"));	
		private SimpleDateFormat form = new SimpleDateFormat();
		private String date = "";
		
		//���� �����
		private File dir;
		private String title = "";
		private String user_Id = "";
			
		private Dimension screen;
		private int xpos, ypos;
		
		
		public Diary(String id) {
			user_Id = id;
			dir = new File("Diary//" + user_Id);
			listLoad();
			action();
			view();
			list();
			init();
			write();

			
			screen = Toolkit.getDefaultToolkit().getScreenSize();
			xpos = (int)(screen.getWidth() / 2 - this.getWidth() / 2);
			ypos = (int)(screen.getHeight() / 2 - this.getHeight() / 2);
		}
		
		public void list() {
			sp_List.setWheelScrollingEnabled(true);
			panel_List.setBorder(new TitledBorder("�ϱ���"));
			panel_List.setLayout(new BorderLayout());
			panel_List.add("Center", sp_List);
			panel_Button1.setLayout(new FlowLayout());
			panel_Button1.add(bt_Write);
			panel_Button1.add(bt_Refresh);
			panel_List.add("South", panel_Button1);
			
		}
		
		public void view() { //�ϱ� ���� �� ���� ����
			sp_View.setWheelScrollingEnabled(true);
			ta_View.setEditable(false);
			ta_View.setBackground(Color.lightGray);
			panel_View.setBorder(new TitledBorder("���뺸��"));
			panel_View.setLayout(new BorderLayout());
			panel_View.add("Center", sp_View);
			panel_Button2.setLayout(new FlowLayout());
			panel_Button2.add(bt_Modify);
			panel_Button2.add(bt_Del);
			panel_View.add("South", panel_Button2);
		}
		
		public void init() { //��ü ȭ�� �ʱ�ȭ����
			con_Diary.setLayout(new BorderLayout(5, 0));
			con_Diary.add("West", panel_List);
			con_Diary.add("Center", panel_View);
		}

		
		public void write() { //�ϱ� �ۼ��ϱ�		
			form.applyPattern("[yy.MM.dd] ");
			date = form.format(cal.getTime()); // ��¥ ����
			
			JPanel jp_Top = new JPanel();
			JPanel jp_Bottom = new JPanel();
			JLabel lb_Head = new JLabel("Ÿ��Ʋ", JLabel.CENTER);
			JLabel lb_Title = new JLabel(" �� �� ");
			jp_Top.setLayout(new BorderLayout());
			jp_Top.add("North", lb_Head);
			jp_Top.add("West", lb_Title);
			jp_Top.add("Center", tf_Title);
			jp_Bottom.add(bt_Wwrite);
			jp_Bottom.add(bt_Wcancel);
		
			dig_Write.setLayout(new CardLayout(5, 5));
			JPanel total = new JPanel();
			total.setLayout(new BorderLayout());
			total.add("North", jp_Top);
			total.add("Center", sp_Write);
			total.add("South", jp_Bottom);
			dig_Write.add("view", total);
		}
		
		public void action() { //�׼� ������ ����
			bt_Modify.addActionListener(this);
			bt_Write.addActionListener(this);
			bt_Wwrite.addActionListener(this);
			bt_Wcancel.addActionListener(this);
			bt_Del.addActionListener(this);
			bt_Refresh.addActionListener(this);
			list.addActionListener(this);
			super.addWindowListener(this);

		}
		
		public void listLoad() {	// �ϱ� ��� �ٽ� �ҷ�����(Refresh)
			String[] str_list = dir.list();
			list.removeAll();
			if(str_list != null) {
				for(int i = 0; i < str_list.length; i++) {
					list.add(str_list[i]);
				}
			}
		}
		
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == bt_Modify) { // �����ϱ�
				if(bt_Modify.getText().equals("�����ϱ�")) {
					String data = ta_View.getText().trim();
					if (data.equals("")) {
						JOptionPane.showMessageDialog(this, "�����Ϸ��� �ϱ⸦ �����ϼ���");
						return;
					}
					ta_View.setEditable(true);
					ta_View.setBackground(Color.white);
					bt_Modify.setText("�����Ϸ�");
					title = list.getSelectedItem();
				}else {
					String data = ta_View.getText().trim();
					File file = new File(dir, title);
					try{
						PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file)));
						out.println(data);
						out.close();
					}catch (IOException ee) {}
					bt_Modify.setText("�����ϱ�");
					ta_View.setText(data);
					ta_View.setBackground(Color.lightGray);
					ta_View.setEditable(false);
				}
				
			}if(bt_Modify.getText() == "�����Ϸ�" && e.getSource() != bt_Modify) {
				JOptionPane.showMessageDialog(this, "�������� �ϱ⸦ ���� �����ϼ���", "Ȯ��", 
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}else if (e.getSource() == bt_Write) { //�ϱ⾲��		

				dig_Write.setSize(300, 400);
				dig_Write.setLocation(xpos - 150, ypos - 150);
				dig_Write.setResizable(false);
				dig_Write.setVisible(true);	
				
			}else if(e.getSource() == bt_Wcancel) { //�ۼ� ���
				tf_Title.setText("");
				ta_Write.setText("");
				dig_Write.setVisible(false);
					
			}else if(e.getSource() == bt_Wwrite){ //�ϱ� ���
				String title = tf_Title.getText();
				String content = ta_Write.getText();
				if(title == null || title.trim().length() == 0) {
					JOptionPane.showMessageDialog(this, "������ �Է��ϼ���!", "Ȯ��", 
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}else if(content == null || content.trim().length() == 0) {
					JOptionPane.showMessageDialog(this, "������ �Է��� �ּ���!", "Ȯ��", 
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				title = date + title.trim();
				if(!dir.exists()) {
					dir.mkdir();
				}
				File file = new File(dir, title);
				try{
					PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file)));
					out.println(title + "\n\n" + ta_Write.getText().trim());
					out.close();
				}catch(IOException ee){}
				tf_Title.setText("");
				ta_Write.setText("");
				dig_Write.setVisible(false);
				listLoad();
				
				
			}else if(e.getSource() == list) { // ����Ʈ Ŭ��
				if(bt_Modify.getText() == "�����Ϸ�") { //�ϱ⸦ �������̸� �̺�Ʈ ���� ����
					return;
				}
				String data = list.getSelectedItem();
				String strTmp = null;
				String f_Input = "";
				System.out.println("ddd");
				System.out.println(data);
				File file1 = new File(dir, data);	
				BufferedReader br = null;
				if(file1.canRead()) {
					try {
						br = new BufferedReader(new FileReader(file1));
						ta_View.setText("");
						while((strTmp = br.readLine()) != null) {
							f_Input += strTmp + "\n";
						}
						ta_View.setText(f_Input);
						br.close();
					}catch(Exception ee){}
					ta_View.setText("");
					ta_View.append(f_Input);
					
				}			
				
			}else if(e.getSource() == bt_Del) { // �����ϱ�
				String data = list.getSelectedItem();
				if (data == null) {
					JOptionPane.showMessageDialog(this, "�����Ϸ��� �ϱ⸦ �����ϼ���");
					return;
				}
				File file = new File(dir, data);
				file.delete();
				ta_View.setText("");
				listLoad();
				
			}else if(e.getSource() == bt_Refresh) { // ���ΰ�ħ
				if(bt_Modify.getText() == "�����Ϸ�") {
					return;
				}
				listLoad();
			}
		}

		public void windowActivated(WindowEvent e) {
			
		}

		public void windowClosed(WindowEvent e) {
			
			System.exit(0);
			
		}

		public void windowClosing(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		public void windowDeactivated(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		public void windowDeiconified(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		public void windowIconified(WindowEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		public void windowOpened(WindowEvent e) {

			
		}


	}
