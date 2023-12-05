package src;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.event.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class Schedule extends JFrame implements ActionListener, MouseListener {
	public Container con_Schedule = this.getContentPane();
	
	private JPanel panel_total = new JPanel();
	private JPanel panel_Top = new JPanel();
	private JPanel panel_Left = new JPanel();
	private JPanel panel_Center = new JPanel();
	private JPanel panel_Bottom = new JPanel();
	
	
	//������ �߰� �� ����, �˻�
	private JLabel lb_Year = new JLabel("��");
	private JTextField tf_year = new JTextField(3);
	private JLabel lb_Month = new JLabel(" ��");
	private JTextField tf_Month = new JTextField(2);
	private JLabel lb_Day = new JLabel(" ��  ");
	private JTextField tf_Day = new JTextField(2);
	private JButton bt_Today = new JButton("����");
	private JLabel lb_Hour = new JLabel(" ��" );
	private JTextField tf_Hour = new JTextField(2);
	private JLabel lb_Min = new JLabel(" ��           ");
	private JTextField tf_Min = new JTextField(2);
	private JTextField tf_Schdule = new JTextField(18);
	private JTextArea ta_Memo = new JTextArea();
	private JScrollPane sp_Memo = new JScrollPane(ta_Memo);
	private JButton bt_Add = new JButton("��   ��");

	private JTextField tf_Search = new JTextField(12);
	private JButton bt_Search = new JButton("�˻�");
	
	//������ �߰�, ����
	private String[] info_temp = new String[3];
	private String daytime, schdule, memo;
	private int snum;
	
	//�߾� ����
	private JPanel panel_View = new JPanel();
	
	//��������
	private JTextArea ta_View = new JTextArea();
	private JScrollPane sp_View = new JScrollPane(ta_View);
	
	//����Ʈ���
	private String[] str = {"�� ��", "�� ��", "�� ��"};  
	private DefaultTableModel dtm = new DefaultTableModel(str, 0); //30��
	private DefaultTableColumnModel dtcm = new DefaultTableColumnModel(); //���ʺ� ��������
	private JTable table = new JTable(dtm, dtcm);
	private JScrollPane jsp = new JScrollPane(table);
	private JPanel panel_Right = new JPanel();
	private TableColumn tc1, tc2, tc3; //�� �ʺ� ����
	private JButton bt_Modify = new JButton("��   ��");
	private JButton bt_Del = new JButton("��   ��");
	
	//���� ��¥ ���
	private Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"));	
	private SimpleDateFormat form = new SimpleDateFormat();
	private String year, month, day;

	//���� �����
	private Vector vData = new Vector(); 
	private String user_Id;
	private File file;
	private File dir;
	
	public Schedule(String id) {
		user_Id = id;
		dir = new File("Schedule//" + user_Id);
		init();
		left();
		list();
		center();
		action();
		load();
		
		//calendar();
	}
	
	public void init() {

		
		con_Schedule.add("West", panel_Left);
		con_Schedule.add("Center", panel_Center);
		con_Schedule.add("South", panel_Bottom);

	}

	
	public void left() {
		//�����Է�
		panel_Left.setBorder(new TitledBorder("������ �Է� / ���� / �˻�"));
		panel_Left.setLayout(new BorderLayout());
		JPanel left_Date = new JPanel();
		JPanel left_Time = new JPanel();
		JPanel left_Top = new JPanel();
		JPanel left_Center = new JPanel();
		JPanel left_sche = new JPanel();
		JPanel left_memo = new JPanel();
		JPanel left_Bottom = new JPanel();
		
		//���� ��¥ ������
		form.applyPattern("yyyy");
		year = form.format(cal.getTime());
		form.applyPattern("MM");
		month = form.format(cal.getTime());
		form.applyPattern("dd");
		day = form.format(cal.getTime());
		today(); // �ؽ�Ʈ �ʵ�� ���� ��¥ �Է�
		//����� �ð�, ����
		JPanel top_Sub = new JPanel(); 
		top_Sub.setLayout(new BorderLayout());
		left_Date.setLayout(new FlowLayout());
		left_Date.add(tf_year);
		left_Date.add(lb_Year);
		left_Date.add(tf_Month);
		left_Date.add(lb_Month);
		left_Date.add(tf_Day);
		left_Date.add(lb_Day);
		left_Date.add(bt_Today);
		bt_Today.setPreferredSize(new Dimension(40, 20));
		bt_Today.setMargin(new Insets(0, 0, 0, 0));
		left_Time.setLayout(new FlowLayout(FlowLayout.RIGHT));
		left_Time.add(tf_Hour);
		left_Time.add(lb_Hour);
		left_Time.add(tf_Min);
		left_Time.add(lb_Min);
		left_sche.setBorder(new TitledBorder("�� ��"));
		left_sche.add(tf_Schdule);
		top_Sub.add("North", left_Date);
		top_Sub.add("Center", left_Time);
		tf_year.setPreferredSize(new Dimension(0, 18));
		tf_Month.setPreferredSize(new Dimension(0, 18));
		tf_Day.setPreferredSize(new Dimension(0, 18));
		tf_Hour.setPreferredSize(new Dimension(0, 18));
		tf_Min.setPreferredSize(new Dimension(0, 18));
		left_Top.setLayout(new GridLayout(2,1));
		left_Top.add(top_Sub);
		left_Top.add(left_sche);
		
		
		//������ �Է�/ ������
		left_memo.setBorder(new TitledBorder("�� ��"));
		sp_Memo.setWheelScrollingEnabled(true);
		sp_Memo.setAutoscrolls(true);
		sp_Memo.setPreferredSize(new Dimension(210, 70));
		left_memo.add("Center", sp_Memo);
		left_Center.setLayout(new BorderLayout());
		left_Center.add("Center", left_memo);
		
		//�˻�
		JPanel search = new JPanel();
		search.setBorder(new TitledBorder("���� �˻�"));
		bt_Search.setPreferredSize(new Dimension(60, 23));
		search.add("Center", tf_Search);
		search.add("East", bt_Search);		
				
		//��ư
		JPanel button = new JPanel();
		button.setLayout(new FlowLayout());
		button.add(bt_Add);
		
		left_Bottom.setLayout(new BorderLayout());
		left_Bottom.add("Center", search);
		left_Bottom.add("North", button);
		
		
		panel_Left.setLayout(new GridLayout(3,1));
		panel_Left.add(left_Top);
		panel_Left.add(left_Center);
		panel_Left.add(left_Bottom);

	}
	
	public void center() {		
		
		panel_View.setBorder(new TitledBorder("�޸� ����"));
		panel_View.add("Center", sp_View);
		sp_View.setPreferredSize(new Dimension(360, 50));
		sp_View.setWheelScrollingEnabled(true);
		
		panel_Center.setLayout(new BorderLayout());		
		panel_Center.add("Center", panel_Right);
		//panel_Center.add("South", panel_View);
		
	}
	
	
	public void list() { //����Ʈ ����
		table.setSelectionForeground(Color.black);
		JPanel list_Bottom = new JPanel();
		tc1 = new TableColumn(0, 75);
		tc1.setHeaderValue("�� ��" );
		tc2 = new TableColumn(1, 150);
		tc2.setHeaderValue(" ��   ��  ");
		tc3 = new TableColumn(2, 30);
		tc3.setHeaderValue(" ��    �� ");
		dtcm.addColumn(tc1);
		dtcm.addColumn(tc2);
		dtcm.addColumn(tc3);

		
		list_Bottom.setLayout(new FlowLayout());
		list_Bottom.add(bt_Modify);
		list_Bottom.add(bt_Del);
		panel_Right.setBorder(new TitledBorder("���� ����"));
		panel_Right.setLayout(new BorderLayout());
		panel_Right.add("Center", jsp);
		panel_Right.add("South", list_Bottom);
	}

	
	public void action() { //�׼� ������ ����
		table.addMouseListener(this);
		bt_Del.addActionListener(this);
		bt_Add.addActionListener(this);
		bt_Modify.addActionListener(this);
		bt_Search.addActionListener(this);
		bt_Today.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == bt_Del) { //���� ������ ����
			int line = table.getSelectedRow();
			if (line == -1){
				JOptionPane.showMessageDialog(this, "�����Ϸ��� ����� �����ϼ���");
				return;
			}
			
			dtm.removeRow(line); //���õ� Row ����
			for(int i = 0; i < 3; i++) {
				vData.remove(line*3);
			}
			save();

			
		}else if (e.getSource() == bt_Add) { // �����Է�
			daytime = tf_year.getText() +"."+ tf_Month.getText() + "." + tf_Day.getText() +
					"(" + tf_Hour.getText() + ":"+  tf_Min.getText() + ")";
			schdule = tf_Schdule.getText();
			memo = ta_Memo.getText();
			info_temp[0] = daytime;
			info_temp[1] = schdule;
			info_temp[2] = memo;
			if(memo.equals("") || daytime.equals("") || schdule.equals("")) {
				JOptionPane.showMessageDialog(this, "��¥�� ������ �Է��� �ּ���!", "Ȯ��", 
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			
			if(bt_Add.getText() == "��  ��"){ //����
				for(int i = 0; i < info_temp.length; i++) {
					dtm.setValueAt(info_temp[i], snum, i); //���̺� ä���	
					vData.remove(snum*3); // 3�� �����
				}
				
				bt_Add.setText("��  ��");
				save(); //�ڵ� ��� ����
			}else 
				dtm.addRow(info_temp);
			
			//�Է� ���� ���Ϳ� ����
			for (int i = 0; i < info_temp.length; i++ ) {
				vData.addElement(info_temp[i]);
			}
			
			// �ʵ� �ʱ�ȭ
			tf_Hour.setText("");
			tf_Min.setText("");
			tf_Schdule.setText("");
			ta_Memo.setText("");
			save();
			
		}else if (e.getSource() == bt_Modify) {//���� ����
			snum = table.getSelectedRow();
			if (snum == -1){
				JOptionPane.showMessageDialog(this, "�����Ϸ��� ����� �����ϼ���");
				return;
			}
			for(int i = 0 ; i < info_temp.length; i++) {
				info_temp[i] = (String)dtm.getValueAt(snum, i);
			}
			StringTokenizer token = new StringTokenizer(info_temp[0], "." + ":" + "(" + ")");
			String[] str = new String[token.countTokens()]; // while�� �ȿ� �־�� �Ѵ�.
			int i = 0;
			while (token.hasMoreTokens()) {
				str[i] = token.nextToken();
				i++;
			}
			tf_year.setText(str[0]);
			tf_Month.setText(str[1]);
			tf_Day.setText(str[2]);
			tf_Hour.setText(str[3]);
			tf_Min.setText(str[4]);			
			tf_Schdule.setText(info_temp[1]);
			ta_Memo.setText(info_temp[2]);
			
			bt_Add.setText("��  ��");
			
		}else if (e.getSource() == bt_Search) { //�˻�
			table.clearSelection();
			String[] search = new String[vData.size()];
			for (int i = 0; i < vData.size() ; i++) {
				search[i] = (String)vData.elementAt(i);
			}
			String keyword = tf_Search.getText();
			boolean bool = false;
			for(int i = 0; i < search.length; i++) {
				if(keyword.equals("")){
					JOptionPane.showMessageDialog(this, "�˻�� �Է��ϼ���!", "Ȯ��", 
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}else if(search[i].indexOf(keyword) != -1) {
					table.addRowSelectionInterval((int)i/3, (int)i/3);
					table.setSelectionForeground(Color.red);
					bool = true;
				}
			}
			if(bool == false) {
				JOptionPane.showMessageDialog(this, "��ġ�ϴ� ����� �����ϴ�!", "Ȯ��", 
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			
		}else if (e.getSource() == bt_Today) {
			today();
		}
		
	}

	
	public void save() {//������ ������ ���Ϸ� ����
		try {
			File file = new File(dir, "Schedule");
			if(!dir.exists()) {
				dir.mkdir();
			}
			BufferedWriter out = new BufferedWriter(new FileWriter(file));
			String[] str2 = new String[vData.size()];
			String str = "";
			for (int i = 0; i < vData.size() ; i++) {
				str2[i] = (String)vData.elementAt(i);
			}
			for (int j = 0; j < str2.length ; j++) {
				if ((j+1)%3 == 0 && (j+1) != str2.length) {
					str += str2[j] + "||\n";
				} else {
					str += str2[j] + "||";
				}
			} 
			out.write(str, 0, str.length());
			out.newLine();
			out.close();
		}catch ( IOException exc) {
			exc.printStackTrace();
		}
	}
	
	public void load() { //���Ϸ� ���� �о ���ͷ� ������ ���̺��� ���
		File file = new File(dir, "Schedule");
		try{
			if(!dir.exists()) {
				dir.mkdir();
			}
			if ( ! file.exists() ) {
				file.createNewFile();
			}

			BufferedReader in = new BufferedReader(new FileReader(file));
			String str = "";
			vData = new Vector();
			while ((str = in.readLine()) != null ) {
				StringTokenizer token = new StringTokenizer(str, "||" + "\n");
				int i = 0;
				String[] str2 = new String[15]; // while�� �ȿ� �־�� �Ѵ�.
				while (token.hasMoreTokens()) {
					str2[i] = token.nextToken();
					vData.addElement(str2[i]);
					i++;
				}

				dtm.addRow(str2);
			} 
			in.close();
		}catch( IOException e) {
			e.printStackTrace();
		}
	}
	
	public void today() {
		tf_year.setText(year);
		tf_Month.setText(month);
		tf_Day.setText(day);
	}

	public void mouseClicked(MouseEvent e) {
		table.setSelectionForeground(Color.black);	
	}
	public void mouseEntered(MouseEvent arg0) {
	}

	public void mouseExited(MouseEvent arg0) {
	}

	public void mousePressed(MouseEvent arg0) {
	}

	public void mouseReleased(MouseEvent arg0) {
	}

}
