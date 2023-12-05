    package src;
	import java.awt.*;
	import javax.swing.*;
	import javax.swing.border.*;
	import java.awt.event.*;
	import java.io.*;
	import java.util.*;

	public class Display extends JFrame implements ActionListener, WindowListener{
		private Container contentPane = super.getContentPane(); 
		
		//��ü
		private MyDiary myDiary;
		private Home home;
		private Diary diary;
		private Schedule schedule;
		private AddressBook address;
		private Login login;
		private Join join;
		
		private JPanel panel_Main = new JPanel(); // ���� ���
		private JPanel panel_Menubar = new JPanel(); // ��ܸ޴��� �г�
		private JPanel panel_Toolbar = new JPanel(); // ���� �г�
		private JPanel panel_Center = new JPanel(); // ��� �г�
		private JMenuBar menubar = new JMenuBar(); // �޴���
		private int xpos, ypos;
		
		//���ϸ޴� ����
		private JMenu menu_File = new JMenu(" ��   ��  ");
		private JMenuItem file_Pass = new JMenuItem("��й�ȣ ����");
		private JDialog dlg_pass = new JDialog(this, true);
		private JButton bt_change = new JButton("����");
		private JButton bt_cancel = new JButton("���");
		private JTextField tf_id= new JTextField();
		private JPasswordField pf_pass = new JPasswordField();
		private JPasswordField pf_pass1 = new JPasswordField();
		private JMenuItem file_User = new JMenuItem("����� ��ȯ");
		private JMenuItem file_Exit = new JMenuItem("��  �� (Exit)");

		//���  �� ����
		private JTabbedPane jtp = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
		private ImageIcon icon_Home = createImageIcon("img/home.png");
		private ImageIcon icon_Diary = createImageIcon("img/diary.png");
		private ImageIcon icon_Schedule = createImageIcon("img/schedule.png");
		private ImageIcon icon_Address = createImageIcon("img/home.png");
		
		//���������
		private File dir = new File("UserInfo");
		public String user_Id;
		
		//����޼���
		private JDialog dlg_warn = new JDialog(this, true);
		private JLabel lb_warn = new JLabel();
		private JButton bt_warn = new JButton("Ȯ��");	
		
		public Display(String id) {
			this.user_Id = id; // �α��� ����� ID ���Ϲ���		
			home = new Home();
			diary = new Diary(user_Id);
			schedule = new Schedule(user_Id);
			address = new AddressBook(user_Id);
			
			menuBar(); // ���� �޴��� ���� �޼ҵ� ȣ��
			center();		
			init();
			action();	
			changePass();
			this.setTitle("MyDiary   " + user_Id + "�� �ݰ����ϴ�^^" );
			this.setSize(640, 450);

			Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
			xpos = (int)(screen.getWidth() / 2 - this.getWidth() / 2);
			ypos = (int)(screen.getHeight() / 2 - this.getHeight() / 2);
			this.setLocation(xpos, ypos);		
			this.setResizable(false);
			this.setVisible(true);
		}
		
		
		// ���� �޴��� ����
		public void menuBar() {
			menu_File.add(file_Pass);
			menu_File.addSeparator();
			menu_File.add(file_User);
			menu_File.addSeparator();
			menu_File.add(file_Exit);
			menubar.add(menu_File);	
			panel_Menubar.setLayout(new GridLayout());
			panel_Menubar.add(menubar);
		}

			
		//ȭ�� �߰� ����
		public void center() {
			
			panel_Center.setLayout(new BorderLayout());
			jtp.addTab("Home",icon_Home, home.con_Home, "");
			jtp.addTab("Diary", icon_Diary, diary.con_Diary);
			jtp.addTab("Scheduler", icon_Schedule,schedule.con_Schedule);
			jtp.addTab("AddressBook", icon_Address, address.con_Address);
			panel_Center.add(jtp, "Center");		
		}	
		
		//ȭ�� �ʱ�ȭ ����(��ü ȭ�� ����)
		public void init() {
			panel_Main.setLayout(new BorderLayout()); //��� ȭ�� ����
			panel_Main.add(panel_Toolbar, "North");
			panel_Main.add(panel_Center, "Center");
			
			//��ü ȭ�� ����
			contentPane.add(panel_Menubar, "North");
			contentPane.add(panel_Main, "Center");

		}
		
		public void action() {
			this.addWindowListener(this);
			//���ϸ޴�
			file_Exit.addActionListener(this);
			file_Pass.addActionListener(this);
			bt_change.addActionListener(this);
			bt_cancel.addActionListener(this);
			file_User.addActionListener(this);
			bt_warn.addActionListener(this);		
		}
		
		
	    /** Returns an ImageIcon, or null if the path was invalid. */
	    protected static ImageIcon createImageIcon(String path) {
	        java.net.URL imgURL = Display.class.getResource(path);
	        if (imgURL != null) {
	            return new ImageIcon(imgURL);
	        } else {
	            System.err.println("Couldn't find file: " + path);
	            return null;
	        }
	    }

	     //�̺�Ʈ ����
	    public void actionPerformed(ActionEvent e) {
	    	if(e.getSource() == file_Pass) { //�н����� ����
	    		pf_pass.setText("");
	    		pf_pass.setText("");
	    		dlg_pass.setVisible(true);    		
				
	    	}else if(e.getSource() == bt_change) {    		
	    		//���Ͽ��� ��й�ȣ ���
	    		String temp = ""; // ���� ������ ����
	    		String user = ""; //���� �ִ� ���� ������ ����
				File file = new File(dir, "UserData");	
	    		if(!dir.exists()) {
	    			dir.mkdir();
	    		}else {
	    			BufferedReader br = null;
	    			if(file.canRead()) { //�б� �����ϸ� �о����
	    				try {
	    					br = new BufferedReader(new FileReader(file));
	    					while((temp = br.readLine()) != null) {
	    						user += temp + "\n"; //user�� ��Ʈ�� ������ ����
	    					}
	    					br.close();
	    				}catch(Exception ee){}
	    			}
	    		}
				StringTokenizer token = new StringTokenizer(user, " || " + "\n");
				String[] tUser = new String[token.countTokens()];
				int i = 0;
				while(token.hasMoreTokens()) {
					tUser[i] = token.nextToken();
					i++;
				}
				String id = tf_id.getText();
				String pass = new String(pf_pass.getPassword());
				String pass1 = new String(pf_pass1.getPassword());
				temp = "";
				
				if(!pass.equals(pass1)) {

					pf_pass.setText("");
					pf_pass1.setText("");
					warning("��й�ȣ�� Ȯ�ΰ��� ���� �ٸ��ϴ�!", 270, 100);
					return;
				}
				//���� ��ū�� �Է¹��� id�� password�� ��
				boolean boolFlag = false;
				i = 1;
				for (int j = 0; j < tUser.length; j++) {				
					if(!pass.equals("") && j % 3 == 1 && tUser[j].equals(id)) {
						tUser[j+1] = pass;
						boolFlag = true;
					}else {	}
					i += 4;
				}
				user = "";
				for(int k = 0; k < tUser.length; k++) {
					if(k%3 == 2) {
						user += tUser[k] + " || " + "\n\n";
					}else {
						user += tUser[k] + " || ";
					}
				}
				if(boolFlag == true) {
					file = new File(dir, "UserData");
					try{
						PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file)));
						out.print(user);
						out.close();
					}catch(IOException ee){}	
					warning("���������� ���� �Ͽ����ϴ�!", 240, 100);
					pf_pass.setText("");
					pf_pass1.setText("");
					dlg_pass.setVisible(false);
				}else {
					warning("��й�ȣ�� �Է��ϼ���!", 200, 100);
				}
	    		
	  
	    	}else if(e.getSource() == bt_cancel) {
	    		dlg_pass.setVisible(false);
	    	}else if(e.getSource() == file_User) {
	    		this.setVisible(false);
	    		login = new Login();
	    		login.setVisible(true);
	    	}else if(e.getSource() == file_Exit) { //����
	    		System.exit(0);
	    		
	    	}else if(e.getSource() == bt_warn) {
	    		dlg_warn.setVisible(false);
	    	}
	    }


	    
	    
		public void windowActivated(WindowEvent arg0) {

		}
		public void windowClosed(WindowEvent arg0) {
			
		}
		public void windowClosing(WindowEvent arg0) {
			System.exit(0);		
		}
		public void windowDeactivated(WindowEvent arg0) {
		}
		public void windowDeiconified(WindowEvent arg0) {
		}
		public void windowIconified(WindowEvent arg0) {
		}
		public void windowOpened(WindowEvent arg0) {
		}
		
		
		
		public void changePass() { //��й�ȣ ����
			//ȭ�鱸��
			JPanel total = new JPanel();
			JPanel left = new JPanel();
			JPanel right = new JPanel();
			JPanel bottom = new JPanel();
			dlg_pass.setTitle("��й�ȣ ����");
			CardLayout card = new CardLayout(5, 5);
			dlg_pass.setLayout(card);
			left.setLayout(new GridLayout(3, 1));
			left.add(new JLabel(" �� �� �� "));
			left.add(new JLabel("��й�ȣ "));
			left.add(new JLabel("  Ȯ   �� "));
			right.setLayout(new GridLayout(3, 1));
			right.add(tf_id);
			tf_id.setText(user_Id);
			tf_id.setEditable(false);
			right.add(pf_pass);
			right.add(pf_pass1);
			bottom.add(bt_change);
			bottom.add(bt_cancel);
			total.setLayout(new BorderLayout());
			total.add("West", left);
			total.add("Center", right);
			total.add("South", bottom);
			dlg_pass.add("view", total); 

			dlg_pass.setSize(200, 160);
			Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
			int xpos = (int)(screen.getWidth() / 2 - this.getWidth() / 2);
			int ypos = (int)(screen.getHeight() / 2 - this.getHeight() / 2);
			dlg_pass.setLocation(xpos -200, ypos -175);				
		}
			
		public void warning(String msg, int x, int y) { //����� ������ ����޼��� ���
			lb_warn.setText(msg);
			dlg_warn.setLayout(new FlowLayout());
			dlg_warn.add(lb_warn);
			dlg_warn.add(bt_warn);
			dlg_warn.setSize(x, y); //200, 100
			dlg_warn.setLocation(370, 300);
			dlg_warn.setVisible(true);
		}
		
	}
