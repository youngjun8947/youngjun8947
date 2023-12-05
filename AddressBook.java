    package src;
	import java.awt.*;
	import java.awt.event.*;
	import javax.swing.*;
	import javax.swing.border.*;
	import javax.swing.table.*;
	import java.io.*;
	import java.util.*;

	public class AddressBook extends JFrame implements ActionListener, MouseListener{
		public Container con_Address = this.getContentPane();
		
		private JPanel panel_Left = new JPanel();
		private JPanel panel_Input = new JPanel();
		private JPanel panel_Search = new JPanel();
		private JPanel panel_Sleft = new JPanel();
		private JPanel panel_Sright = new JPanel();
		private JPanel panel_Choose = new JPanel();
		
		//�Է¸��
		private JLabel lb_Name = new JLabel("  �� ��");
		private JTextField tf_Name = new JTextField(12);
		private JLabel lb_Phone = new JLabel("  �� ȭ");
		private JTextField tf_Phone = new JTextField(12);
		private JLabel lb_Addr = new JLabel("  �� ��");
		private JTextField tf_Addr = new JTextField(100);
		private JLabel lb_Memo = new JLabel("  �� ��");
		private JTextField tf_Memo = new JTextField(100);
		private JButton bt_Add = new JButton("�� ��");
		private JButton bt_Init = new JButton("�ٽ��Է�");
			
		//�ּҷ� �߰�, ����
		private String[] info_temp = new String[4];
		private String name, phone, addr, memo;
		private int snum;
		
		//�˻����
		private JLabel lb_Search = new JLabel("�˻����� : ");
		private JLabel lb_Word = new JLabel("�˻��ܾ� : ");
		private JCheckBox cb_Name = new JCheckBox("�� ��");
		private JCheckBox cb_Phone = new JCheckBox("�� ȭ");
		private JTextField tf_Word = new JTextField(10);
		private JButton bt_Search = new JButton("�� ��");
		private JTextArea ta_Result = new JTextArea("�˻����");
		private JScrollPane sp_Result = new JScrollPane(ta_Result);
		
		//����Ʈ���
		private String[] str = {"�� ��", "��ȭ��ȣ", "�� ��", "�� ��"};  
		private DefaultTableModel dtm = new DefaultTableModel(str, 0); //30��
		private DefaultTableColumnModel dtcm = new DefaultTableColumnModel(); //���ʺ� ��������
		private JTable table = new JTable(dtm, dtcm);
		private JScrollPane jsp = new JScrollPane(table);
		private JPanel panel_Right = new JPanel();
		private TableColumn tc1, tc2, tc3, tc4; //�� �ʺ� ����
		private JButton bt_Modi = new JButton("�����ϱ�");
		private JButton bt_Del = new JButton("�����ϱ�");
		
		//���� �����
		private Vector vData = new Vector(); 
		private String user_Id;
		private File dir;
		
		public AddressBook(String id) {
			user_Id = id;
			dir = new File("Address//" + user_Id);
			load();
			action();
			init();
			list();
		}
		
		public void init() {
			// �����Է�
			
			panel_Input.setBorder(new TitledBorder("�����Է�"));
			panel_Input.setLayout(new BorderLayout());
			JPanel input_lb = new JPanel();
			JPanel input_tf = new JPanel();
			input_lb.setLayout(new GridLayout(4,1));
			input_lb.setPreferredSize(new Dimension(40, 0));
			input_tf.setLayout(new GridLayout(4,1));
			input_lb.add(lb_Name);
			input_tf.add(tf_Name);
			input_lb.add(lb_Phone);
			input_tf.add(tf_Phone);
			input_lb.add(lb_Addr);
			input_tf.add(tf_Addr);
			input_lb.add(lb_Memo);
			input_tf.add(tf_Memo);
			
			JPanel input_bt = new JPanel();
			input_bt.setPreferredSize(new Dimension(0, 40));
			input_bt.add(bt_Init);
			input_bt.add(bt_Add);
			
			panel_Input.add("West", input_lb);
			panel_Input.add("Center", input_tf);
			panel_Input.add("South", input_bt);

			//�˻�ȭ��
			panel_Search.setBorder(new TitledBorder("��  ��"));
			JPanel panel_Top = new JPanel();
			panel_Top.setLayout(new FlowLayout());
			panel_Top.add(tf_Word);
			panel_Top.add(bt_Search);
			panel_Search.add("North", panel_Top);
			
			ta_Result.setEditable(false);

			JPanel panel_South = new JPanel();
			panel_South.setLayout(new GridLayout(1, 1));
			panel_South.add(sp_Result);
			sp_Result.setPreferredSize(new Dimension(200, 90));
			panel_Search.add("Center", panel_South);
			
			
			//��ü ȭ�� ����
			panel_Left.setLayout(new GridLayout(2,1));
			panel_Left.setPreferredSize(new Dimension(220, 0));
			panel_Left.add(panel_Input);
			panel_Left.add(panel_Search);
			con_Address.add("West", panel_Left);	
			con_Address.add("Center", panel_Right);
			
		}
		
		public void list() { //����Ʈ ����
			JPanel list_Bottom = new JPanel();
			tc1 = new TableColumn(0, 2);
			tc1.setHeaderValue("�� ��" );
			tc2 = new TableColumn(1, 40);
			tc2.setHeaderValue("��ȭ��ȣ");
			tc3 = new TableColumn(2, 100);
			tc3.setHeaderValue("��    ��");
			tc4 = new TableColumn(3, 20);
			tc4.setHeaderValue("�� ��");
			dtcm.addColumn(tc1);
			dtcm.addColumn(tc2);
			dtcm.addColumn(tc3);
			dtcm.addColumn(tc4);

			
			list_Bottom.setLayout(new FlowLayout());
			list_Bottom.add(bt_Modi);
			list_Bottom.add(bt_Del);
			panel_Right.setBorder(new TitledBorder("��� ����"));
			panel_Right.setLayout(new BorderLayout());
			panel_Right.add("Center", jsp);
			panel_Right.add("South", list_Bottom);
		}
		
		public void action() {
			bt_Del.addActionListener(this);
			bt_Init.addActionListener(this);
			bt_Add.addActionListener(this);
			bt_Modi.addActionListener(this);
			bt_Search.addActionListener(this);
			table.addMouseListener(this);
		}

		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == bt_Del) { //���� �ּ����� ����
				int line = table.getSelectedRow();
				if (line == -1){
					JOptionPane.showMessageDialog(this, "�����Ϸ���  ����� �����ϼ���");
					return;
				}
				dtm.removeRow(line); //���õ� Row ����
				for(int i = 0; i < 4; i++) {
					vData.remove(line*4);
				}
				save();

				
			}else if (e.getSource() == bt_Init) {//�ʵ� �ʱ�ȭ
				tf_Addr.setText("");
				tf_Memo.setText("");
				tf_Name.setText("");
				tf_Phone.setText("");

			}else if (e.getSource() == bt_Add) { // �����Է�
				name = tf_Name.getText();
				phone = tf_Phone.getText();
				addr = tf_Addr.getText();
				memo = tf_Memo.getText();
				info_temp[0] = name;
				info_temp[1] = phone;
				info_temp[2] = addr;
				info_temp[3] = memo;
				if(name.equals("") || phone.equals("") || addr.equals("")) {
					JOptionPane.showMessageDialog(this, "�̸�, ��ȭ��ȣ, �ּҸ� �Է��ϼ���!", "Ȯ��", 
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				
				if(bt_Add.getText() == "�Ϸ�"){ //����
					for(int i = 0; i < info_temp.length; i++) {
						dtm.setValueAt(info_temp[i], snum, i); //���̺� ä���	
						vData.remove(snum*4); // 4�� �����
					}
					
					bt_Add.setText("�߰�");
					save(); //�ڵ� ��� ����
				}else 
					dtm.addRow(info_temp);
					
				//�Է� ���� ���Ϳ� ����
				for (int i = 0; i < info_temp.length; i++ ) {
					vData.addElement(info_temp[i]);
				}
				save(); //�ڵ� ��� ����
				// �ʵ� �ʱ�ȭ
				tf_Addr.setText("");
				tf_Memo.setText("");
				tf_Name.setText("");
				tf_Phone.setText("");
				
			}else if (e.getSource() == bt_Modi) {//���� ����
				snum = table.getSelectedRow();
				if (snum == -1){
					JOptionPane.showMessageDialog(this, "�����Ϸ��� ����� �����ϼ���");
					return;
				}
				
				for(int i = 0 ; i < info_temp.length; i++) {
					info_temp[i] = (String)dtm.getValueAt(snum, i);
				}
				
				tf_Name.setText(info_temp[0]);
				tf_Phone.setText(info_temp[1]);
				tf_Addr.setText(info_temp[2]);
				tf_Memo.setText(info_temp[3]);

				
				bt_Add.setText("�Ϸ�");
				
			}else if (e.getSource() == bt_Search) { //���� �˻�
				table.clearSelection();
				String[] search = new String[vData.size()];
				for (int i = 0; i < vData.size() ; i++) {
					search[i] = (String)vData.elementAt(i);
				}
				String keyword = tf_Word.getText();
				boolean bool = false;
				int count = 0;
				for(int i = 0; i < search.length; i++) {
					if(keyword.equals("")) {
						ta_Result.setText("�˻�� �Է��ϼ���!");
						return;
					}else if(search[i].indexOf(keyword) != -1) {
						table.addRowSelectionInterval((int)i/4, (int)i/4);
						table.setSelectionForeground(Color.red);
						bool = true;
						count++;
					}
				}
				if(bool) 
					ta_Result.setText(count + "�� ã�ҽ��ϴ�!");
				else 
					ta_Result.setText("�˻������ �����ϴ�!");			
			}
			
		}

		
		public void save() {//������ ������ ���Ϸ� ����
			try {
				File file = new File(dir, "AddrData");
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
					if ((j+1)%4 == 0 && (j+1) != str2.length) {
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
			File file = new File(dir, "AddrData");
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
