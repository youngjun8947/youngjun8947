package src;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Home extends JFrame {
	public Container con_Home = this.getContentPane();	
	private JLabel label_img = new JLabel(new ImageIcon("img/home.jpg"));
	public Home() {		
		init();
	}	
	public void init() {
		con_Home.add(label_img);	
		
	}
}
