import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.color.*;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Random;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout.Constraints;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;

import com.sun.javafx.webkit.ThemeClientImpl;
import com.sun.prism.paint.Color;
import com.sun.scenario.effect.impl.sw.java.JSWColorAdjustPeer;
import com.sun.xml.internal.messaging.saaj.soap.JpegDataContentHandler;

import javafx.scene.control.ComboBox;
import javafx.scene.layout.Border;

class RacerClass extends JFrame {
	
	private UIManager.LookAndFeelInfo[] looks;
	private String[] lookNames;
	private JButton[] button;
	private JRadioButton[] radio;
	private ButtonGroup group;
	private JLabel label;
	private JComboBox comboBox;
	private JPanel northPanel,southPanel,centerPanel;
	private GridBagConstraints constraints;
	private JTextArea textArea;
	private JTextField textField;
	private Font font,font1,font2;
	private String str;
	String user;
	private String[] boss;
	private String[] names;
	private int textInd;
	private int len,cnt,pcnt,aspeed=0,acnt=0;
	private int[] pos;
	private int speed,accuracy,gflag,gflg;
	private MyThread1 myThread1;
	private MyThread2 myThread2;
	private Container container;
	private GridBagLayout layout1,layout2;
	private JPanel panel1;
	private Random rand = new Random();

	
	public RacerClass(String tuser) {
		// TODO Auto-generated constructor stub
		super("TypeRacer");
		user = tuser;
		panel1 = new JPanel();
		speed=60; accuracy=97;
		pos = new int[1000];
		looks = UIManager.getInstalledLookAndFeels();
		constraints = new GridBagConstraints();
		container = getContentPane();
		layout1 = new GridBagLayout();
		layout2 = new GridBagLayout();
		setBackground(java.awt.Color.CYAN);
		font = new Font("Courier", Font.BOLD, 20);
		font2 = new Font("Courier", Font.BOLD, 25);
		font1 = new Font("Courier",Font.BOLD, 25);
		
		names = new String[50];
		for(int i=0;i<50;i++) {
			names[i] = new String();
			names[i] = (i+1)+".txt";
		}
				
		//north panel
		northPanel = new JPanel();
		northPanel.setLayout(layout1);
		
		button = new JButton[5];
		button[0] = new JButton("Start Race");
		button[1] = new JButton("Speed - 0wpm");
		button[2] = new JButton("Accuracy - 0%");
		button[3] = new JButton("Time - 0s");
		button[4] = new JButton("Average Speed ( "+user+" ) - 0wpm");
		for(int i=0;i<5;i++) {
			button[i].setPreferredSize(new Dimension(325,50));
			button[i].setFont(font1);
	//		button[i].setBackground(java.awt.Color.YELLOW);
		}
		label = new JLabel("TYPERACER",SwingConstants.CENTER);
		label.setFont(font1);
		label.setOpaque(true);
		label.setBackground(java.awt.Color.WHITE);
		
		comboBox = new JComboBox(names);
		comboBox.setFont(font1);
//		comboBox.setBackground(java.awt.Color.CYAN);

		textArea = new JTextArea(12,80);
		textArea.setPreferredSize(new Dimension(12, 80));
		textArea.setFont(font);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setEditable(false);
		textArea.setForeground(java.awt.Color.BLUE);
		textArea.setText(str);

		textField = new JTextField();
		textField.setFont(font2);
		//textField.setForeground(java.awt.Color.RED);
		textField.setText(null);
		textField.setEditable(false);
		
		constraints.fill = GridBagConstraints.BOTH;
		constraints.weightx = 0;
		
		constraints.weighty = 0;
		
		addComponent(new JLabel(" "), 0, 0, 3, 1);
		addComponent(new JLabel(" "), 1, 0, 3, 1);
		addComponent(button[4], 2, 0, 3, 1);
		addComponent(comboBox, 3, 0, 3, 1);
//		addComponent(new JLabel(" "), 3, 0, 3, 1);
		addComponent(button[1], 4, 1, 1, 1);
		addComponent(button[2], 4, 2, 1, 1);
		addComponent(button[3], 4, 0, 1, 1);
		addComponent(new JLabel(" "), 5, 0, 3, 1);
		addComponent(textArea, 6, 0, 3, 1);
		addComponent(new JLabel(" "), 7, 0, 3, 1);
		addComponent(textField, 8, 0, 3, 1);
		addComponent(new JLabel(" "), 9, 0, 3, 1);
		String tstr=" ";
		for(int i=0;i<70;i++) tstr+=" ";
		addComponent(button[0], 10, 1, 1, 1);
		for(int i=0;i<4;i++) {
			button[i].setHorizontalAlignment(SwingConstants.CENTER);
		}
		//south panel
		southPanel = new JPanel();
		southPanel.setLayout(new GridLayout(1,5));
		
		radio = new JRadioButton[looks.length];
		group = new ButtonGroup();
		
		//center panel
		centerPanel = new JPanel();
		centerPanel.setLayout(new FlowLayout());
		
		// add radio listner
		RadioHandler handler = new RadioHandler();
		for(int i=0;i<looks.length;i++) {
			radio[i] = new JRadioButton(looks[i].getName());
			radio[i].addItemListener(handler);
			southPanel.add(radio[i]);
			group.add(radio[i]);
		}
		
		// add comboBox listner
		TextHandler handler1 = new TextHandler();
		comboBox.addItemListener(handler1);
		
		//back-ground color
		//comboBox.setBackground(java.awt.Color.BLUE);
		northPanel.setBackground(java.awt.Color.PINK);
		southPanel.setBackground(java.awt.Color.PINK);
		centerPanel.setBackground(java.awt.Color.PINK);
		textArea.setBackground(java.awt.Color.LIGHT_GRAY);
		textField.setBackground(java.awt.Color.LIGHT_GRAY);		
		
		//add panel
		add(northPanel,BorderLayout.NORTH);
		add(centerPanel,BorderLayout.CENTER);
		add(southPanel,BorderLayout.SOUTH);
		radio[1].setSelected(true);
		
		//******************************************************************************
		//start button listner
		MyThread myThread = new MyThread();
		
		button[0].addActionListener(
				new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent event) {
						// TODO Auto-generated method stub
						if(true) {
							textField.setText(null);
							textField.setEditable(false);
							speed = 0;
							accuracy = 0;
							gflag = 0;
							cnt=0; pcnt=-1;
							button[3].setText("Time - "+0+"s");
							button[1].setText("Speed - "+0+"wpm");
							button[2].setText("Accuracy - "+0+"%");
							try {
								highlightText(0,boss[0].length(),1);
								myThread1.t.join();
								myThread2.t.join();
							} catch(Exception e) {
								System.out.println(e);
							}
							gflag = 1;
							//Count Down
						//	CgountDown countDown = new CountDown();
						//	MyThread2 myThread2 = new MyThread2();
							gflg=1;
							myThread2 = new MyThread2();
						//	myThread2.run();
							myThread1 = new MyThread1();
						}
					}
				}
		);
		
		//keyboard listner		
		cnt=0; pcnt=-1;
		
	}
	
	private void highlightText(int pos1,int pos2,int flag) throws BadLocationException {
		textArea.setText(str);
	   if(flag==1) textArea.getHighlighter().addHighlight(pos1,pos2,new DefaultHighlighter.DefaultHighlightPainter(java.awt.Color.YELLOW));
	}
	
	private String getString(String txt) {
		int cnt=0;
		String tstr = new String();
		BufferedReader inputStream = null;
		PrintWriter outputStream = null;
		
		try {
			InputStream input = RacerClass.class.getResourceAsStream(txt);
			inputStream = new BufferedReader(new InputStreamReader(input,"UTF-8"));
			int ttstr;
			cnt=0;
			while((ttstr=inputStream.read())!=-1) {
				tstr+=(char)ttstr;
			}
		} catch(Exception e) {
				System.out.println(e);
		} finally {
			if(inputStream!=null) {
				try {
					inputStream.close();
				} catch(Exception e) {
					System.out.println(e);
				}
			}
			if(outputStream!=null) {
				try {
					outputStream.close();
				} catch(Exception e) {
					System.out.println(e);
				}
			}
		}
		return tstr;
	}
	
	private void changeTheLookAndFeel(int value) {
		try {
			UIManager.setLookAndFeel(looks[value].getClassName());
			SwingUtilities.updateComponentTreeUI(this);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private class CountDown implements Runnable {
		Thread t;
		
		public CountDown() {
			// TODO Auto-generated constructor stub
			t = new Thread(this,"CountDown");
			t.start();
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			for(int i=10;i>=0;i--) {
				button[3].setText("CountDown - "+i);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
	
	private class MyThread implements Runnable {
		Thread t;
		 MyThread() {
			// TODO Auto-generated constructor stub
			 System.out.println("yes");
			t = new Thread(this,"MyThread");
			t.start();
		}
		@Override
		
		public void run() {
			// TODO Auto-generated method stub
			try {
					textField.addKeyListener(
							new KeyListener() {
								
							@Override
							public void keyTyped(KeyEvent event) {
								// TODO Auto-generated method stub
								if(textField.isEditable()) {
								String line1 = String.format("%s", event.getKeyChar());
								String line2 = textField.getText();
								String ttstr=line1;
								if(line1.equals("\b")) accuracy++;
								if(!(line1.equals("\b")) && !(line1.equals(" "))) {
									line2=line2+line1;
								}
								pcnt = cnt;
								if(line2.length()>boss[cnt].length()) {
									textField.setBackground(java.awt.Color.RED);
								} else if(boss[cnt].substring(0,line2.length()).equals(line2)) {
									textField.setBackground(java.awt.Color.LIGHT_GRAY);
								} else {
									textField.setBackground(java.awt.Color.RED);
								}
								
								if(cnt==len-1 && boss[cnt].equals(textField.getText()+line1)) {
									cnt=0; pcnt=-1;
									textField.setText(null);
									textField.setEditable(false);
									comboBox.setSelectedIndex(rand.nextInt(50));
									gflag=0;
								} else if(line1.equals(" ")) {
									if(boss[cnt].equals(textField.getText())) {
										speed+=boss[cnt].length()+1;
										if(cnt==len-1) {
											cnt=0; pcnt=-1;
											textField.setText(null);
											textField.setEditable(false);
											comboBox.setSelectedIndex(rand.nextInt(50));
											gflag=0;
										} else {
											try {
												cnt++;
												highlightText(pos[cnt], pos[cnt]+boss[cnt].length(),1);
											} catch(Exception e) {
												System.out.println(e);
											}
										}
									}
								}}
							}
							
							public void keyReleased(KeyEvent arg0) {
								if(cnt-1==pcnt && textField.isEditable()) textField.setText(null);
							}
							public void keyPressed(KeyEvent arg0) {
								if(cnt-1==pcnt && textField.isEditable()) textField.setText(null);
							}
						}
					);
			} catch(Exception e) {
				System.out.println(e);
			}
		}
		
	}
	
	private class MyThread2 implements Runnable {
		Thread t;
		public MyThread2() {
			// TODO Auto-generated constructor stub
			t = new Thread(this,"MyThread1");
			t.start();
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				int cnt=1,tcnt=accuracy;
				for(int i=10;i>=0 && gflag==1;i--) {
					Thread.sleep(500);
					if(gflag==1) {
						if(i==4 || i==3) button[3].setText("Ready - "+i);
						else if(i==2 || i==1 || i==0) button[3].setText("Go - "+i);
						else button[3].setText("CountDown - "+(i));
					}
				}
				gflg=0;
				textField.setText(null);
				textField.setEditable(true);
				speed = 0;
				accuracy = 0;
				cnt=0; pcnt=-1;
				highlightText(0,boss[0].length(),1);
				button[3].setText("Time - 0s");
			} catch(Exception e) {
			}
		}
	}
	
	private class MyThread1 implements Runnable {
		Thread t;
		public MyThread1() {
			// TODO Auto-generated constructor stub
			t = new Thread(this,"MyThread1");
			t.start();
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				int time=1,tcnt=accuracy,tspeed=0;
				for(int i=100;i>=0 && gflag==1;) {
					Thread.sleep(1000);
					if(gflg==0) {
					if(gflag==1) {
						button[3].setText("Time - "+i+"s");
						tspeed=(speed*12/time);
						button[1].setText("Speed - "+((speed*12)/time));
						if(accuracy!=0 || speed!=0) button[2].setText("Accuracy - "+(speed*100/(accuracy*2+speed)));
						else button[2].setText("Accuracy - 0%");
					} else {
						cnt=0; pcnt=-1;
						highlightText(0,boss[0].length(),1);	
					}
					time++; i--;}
				}
				System.out.println("time speed: "+time+" "+speed);
				if(time!=0 && tspeed!=0) {
					aspeed+=(tspeed);
					acnt++;
					button[4].setText("Average Speed ("+user+")"+"- "+(aspeed/acnt));
				}
				if(gflag==1) {
					textField.setText(null);
					textField.setEditable(false);
					comboBox.setSelectedIndex(rand.nextInt(50));
				} else {
					cnt=0; pcnt=-1;
					highlightText(0,boss[0].length(),1);
				}
			} catch(Exception e) {
			}
		}
	}
	
	private class RadioHandler implements ItemListener {
		public void itemStateChanged(ItemEvent arg0) {
			// TODO Auto-generated method stub
			for(int i=0;i<looks.length;i++) {
				if(radio[i].isSelected()) {
					changeTheLookAndFeel(i);
				}
			}
		}
	}
	
	private class TextHandler implements ItemListener {

		@Override
		public void itemStateChanged(ItemEvent event) {
			// TODO Auto-generated method stub
			gflag=0;
			cnt=0; pcnt=-1;
			if(event.getStateChange()==ItemEvent.SELECTED || true) {
				textInd = comboBox.getSelectedIndex();
				if(textInd!=0) str = getString("/"+names[comboBox.getSelectedIndex()]);
				else str="";
				boss = str.split("\\s+");
				str=""; len=boss.length;
				pos[0]=0;
				speed=accuracy=0;
				button[3].setText("Time - 0s");
				for(int i=0;i<boss.length;i++) {
					pos[i+1]=pos[i]+boss[i].length()+1;
					str+=boss[i]+" ";
				//	System.out.println(boss[i]);
				}
				textField.setText(null);
				textField.setEditable(false);
				textField.setBackground(java.awt.Color.LIGHT_GRAY);
				textArea.setText(str);
				try {
					highlightText(0,boss[0].length(),1);
				} catch(Exception e) {
					System.out.println(e);
				}
			} else {
				
			}
		}	
	}
	
	private void addComponent(Component component,int row, int column, int width, int height) {
		constraints.gridx = column;
		constraints.gridy = row;
		constraints.gridwidth = width;
		constraints.gridheight = height;
		layout1.setConstraints(component, constraints);
		northPanel.add(component);
	}
}

class User extends JFrame {
	private JTextField text;
	private JButton button;
	private String str="";
	private JLabel label;
	private JPanel panel;
	
	public User() {
		// TODO Auto-generated constructor stub
		text = new JTextField(5);
		button = new JButton("Submit");
		label = new JLabel();
		panel = new JPanel(new GridLayout(3,1));
		label.setText("User Name");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		button.addActionListener(
				new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
						str = text.getText();
						if(str.length()<=5) {
							label.setText("User Name length > 5");
							label.setBackground(java.awt.Color.RED);
						}
					}
				}
		);
		panel.add(label);
		panel.add(text);
		panel.add(button);
		add(panel);
	}
	
	String rStr() {
		return str;
	}
}

public class TypeRacerClass {
	public static void main(String[] args) {
		String str="x";
		User user = new User();
		user.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		user.setSize(100,150);
		user.setVisible(true);
		while(str.length()<=5) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			str = user.rStr();
		}
		RacerClass racerClass = new RacerClass(str);
		racerClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		racerClass.setSize(1500,1500);
		racerClass.setVisible(true);
	}
}
