package src;
import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class Login extends JDialog implements ActionListener{
	Display dsp;
	Join join;	
	private JPanel panel_Left = new JPanel();
	private JPanel panel_Right = new JPanel();
	private JPanel panel_Bottom = new JPanel();	
	private JLabel lb_Id = new JLabel(" �� �� ��  ");
	private JLabel lb_Pw = new JLabel("��й�ȣ ");	
	public JTextField tf_Id = new JTextField("");
	private JPasswordField pf_Pw = new JPasswordField("");	
	private JButton bt_Ok = new JButton("Ȯ��");
	private JButton bt_Exit = new JButton("����");
	private JButton bt_Join = new JButton("���");

	
	//�α��� ���� �����
	private File dir = new File("UserInfo");
	
	public Login() {
		init();
		action();		
		this.setTitle("�α���");
		this.setSize(215, 140);
		
		//â�� ��� ���� ���� �ҽ�
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int xpos = (int)(screen.getWidth() / 2 - this.getWidth() / 2);
		int ypos = (int)(screen.getHeight() / 2 - this.getHeight() / 2);
		this.setLocation(xpos, ypos);
		this.setResizable(false);
	}
	
	public void init() {
		panel_Left.setLayout(new GridLayout(2, 1));
		panel_Left.add(lb_Id);
		panel_Left.add(lb_Pw);
		
		panel_Right.setLayout(new GridLayout(2, 1, 5, 5));
		tf_Id.setBorder(new LineBorder(Color.gray));
		pf_Pw.setBorder(new LineBorder(Color.gray));
		panel_Right.add(tf_Id);
		panel_Right.add(pf_Pw);

		panel_Bottom.setPreferredSize(new Dimension(0, 38));
		panel_Bottom.add(bt_Join);
		panel_Bottom.add(bt_Ok);
		panel_Bottom.add(bt_Exit);
		
		//��ü ����
		CardLayout card = new CardLayout(5, 5); //�ֺ��ѷ� �����ڸ� �����ֱ����ؼ�
		this.setLayout(card);
		JPanel total = new JPanel();
		total.setLayout(new BorderLayout());
		total.add("West", panel_Left);
		total.add("Center", panel_Right);
		total.add("South", panel_Bottom);
		this.add("view", total);
	}

	public void enter() {
		dsp.setTitle("My Diary");
		dsp.setVisible(true);
		this.setVisible(false);
		
	}
	
	public void action() { //�� ������Ʈ�� �̺�Ʈ ������ �߰�
		bt_Join.addActionListener(this);
		bt_Ok.addActionListener(this);
		bt_Exit.addActionListener(this);
	}
	
	// �̺�Ʈ ����
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == bt_Exit) {
			System.exit(1);
		}else if(e.getSource() == bt_Ok) {
			//����� ���� �˻�
			String id = tf_Id.getText();
			String pass = new String(pf_Pw.getPassword());
			String temp = null;
			String user = "";
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
			}else if(!file.canRead()){
				tf_Id.setText("");
				pf_Pw.setText("");
				JOptionPane.showMessageDialog(this, "����� ����� ���ּ���!", "Ȯ��", 
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			// ����� ������ ��ū���� ����
			StringTokenizer token = new StringTokenizer(user, " || ");
			String[] tUser = new String[token.countTokens()];
			int i = 0;
			while(token.hasMoreTokens()) {
				tUser[i] = token.nextToken();
				i++;
			}
			
			//���� ��ū�� �Է¹��� id�� password�� ��
			boolean boolFlag = false;
			for (int j = 0; j < tUser.length; j++) {
				if(tUser[j].equals(id) && tUser[j+1].equals(pass)) {
					boolFlag = true;
				}
			}
			if(boolFlag == true) {
				this.setVisible(false);
				dsp = new Display(tf_Id.getText());
			}else {
				JOptionPane.showMessageDialog(this, "���̵� �Ǵ� �о����带 Ȯ���ϼ���!", "Ȯ��", 
						JOptionPane.INFORMATION_MESSAGE);
				tf_Id.setText("");
				pf_Pw.setText("");
				return;
			}

		}else if(e.getSource() == bt_Join){
			tf_Id.setText("");
			pf_Pw.setText("");
			join = new Join();			
		}			
	}
}
