package au.edu.uow.UserInterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import au.edu.uow.Collection.CDAlbum;
import au.edu.uow.Collection.DVDAlbum;
/**
 * This class is the client userinterface class.
 * @author Xiangyu Ren, 4218693, xr857
 * @version v.1.0
 */
public class UserInterface  implements WindowListener {
	private int width=500;
	private int height=500;
	private JFrame frame=new JFrame();
	private JPanel panel=new JPanel();
	private JPanel panel1=new JPanel();
	private JPanel panel2=new JPanel();
	private JPanel panel3=new JPanel();
	private JList<String> list=null;
	private JTextArea  content=new JTextArea();
	private JLabel label=new JLabel();
	private JMenuItem item,item1,item2,item3,item4,item5;
	private DefaultListModel<String> model=new DefaultListModel<String>();
	private JSplitPane split;
	private JTextField field1=new JTextField();
	private JTextField field2=new JTextField();
	private JTextField field3=new JTextField();
	private JTextField field4=new JTextField();
	private JTextField field5=new JTextField();
	private String hostName="localhost";
	private Socket socket=null;
	private BufferedReader buffer=null;
	private PrintWriter pout=null;
	private ObjectInputStream oin=null;
	private ObjectOutputStream oout=null;
	/**
	 * This method is the entrance.
	 * 
	 * 
	 */
	public void start(){ //entrance
		init();
	}
	/**
	 * This method initializes all.
	 * 
	 * 
	 */
	public void init(){ 
		frame.setLayout(new BorderLayout());
		initPanel();
		frame.add(panel,BorderLayout.CENTER);
		label=new JLabel("Connect server first");
		panel3.setLayout(new FlowLayout(0)); 
		panel3.add(label);
		frame.add(panel3,BorderLayout.PAGE_END);
		addMenu();
		frame.setVisible(true);
		content.setEditable(false);
		Toolkit toolkit=Toolkit.getDefaultToolkit();
		int x=(int)(toolkit.getScreenSize().getWidth()-width)/2;
		int y=(int)(toolkit.getScreenSize().getHeight()-height)/2;
		frame.setLocation(x, y);
		frame.setSize(width, height);
		frame.addWindowListener(this);
	}
	/**
	 * This method initializes all pannels.
	 * 
	 * 
	 */
	public void initPanel(){ 
		panel.setLayout(new GridLayout(7, 1));
		panel.setBackground(new Color(30,116,131));
		JLabel label=new JLabel("");
		panel.add(label);
		JLabel label1=new JLabel("My CD/DVD Collection",JLabel.CENTER);
		label1.setForeground(Color.WHITE);
		label1.setFont(new java.awt.Font("Dialog",   1,   25));
		panel.add(label1);
		JLabel label2=new JLabel("create by Xiangyu Ren",JLabel.CENTER);
		label2.setFont(new java.awt.Font("Dialog",   1,   20));
		panel.add(label2);
		JLabel label3=new JLabel("for",JLabel.CENTER);
		label3.setFont(new java.awt.Font("Dialog",   1,   20));
		panel.add(label3);
		JLabel label4=new JLabel("Java Programming and Applications",JLabel.CENTER);
		label4.setFont(new java.awt.Font("Dialog",   1,   20));
		panel.add(label4);
		JLabel label5=new JLabel("");
		panel.add(label5);
		JLabel label6=new JLabel("");
		panel.add(label6);
	}
	/**
	 * This method adds all menus.
	 * 
	 * 
	 */
	public void addMenu(){  //add  menu
		JMenuBar bar=new JMenuBar(); 
		JMenu menu=new JMenu("Collection");
		JMenu menu1=new JMenu("Setting");
		JMenu menu2=new JMenu("Help");
		item=new JMenuItem("Connect server");
		item1=new JMenuItem("Add an album");
		item2=new JMenuItem("Delete an album");
		item3=new JMenuItem("Exit");
		item4=new JMenuItem("About");
		item5=new JMenuItem("Now Time");

		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(item.getText().equals("Connect server"))
					connectServer();
				else{
					closeStreams();
	/*--------------init frame and panel------------*/
					frame.dispose();
					item.setText("Connect Server");
					frame=new JFrame();
					panel=new JPanel();
					panel1=new JPanel();
					panel2=new JPanel();
					panel3=new JPanel();
	/*--------------init end---------------*/
					model.clear();
					start();
				}
			}
		});
		item1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addAnAlbum();  
			}

		});
		item2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				deleteAnAlbum();
			}
		});
		item3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				closeStreams();
				System.exit(0);	
			}
		}); 
		item4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(frame,"My CD/DVD Collection ver 3.0\n" +
						"          based on Swing  \n" +
						"          by Xiangyu Ren");
			}
		});
		item5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
				String res=sdf.format(new Date());
				JOptionPane.showMessageDialog(frame,"Now Time\n" +
						"        "+res+" \n" );
			}
		});
		menu.add(item);
		menu.add(item1);
		menu.add(item2);
		menu.add(item3);
		menu2.add(item4);
		menu1.add(item5);

		item1.setEnabled(false);
		item2.setEnabled(false);
		menu.insertSeparator(1);
		menu.insertSeparator(4);
		bar.add(menu);    //opreate
		//	bar.add(menu1);   //setting
		bar.add(menu2);   //about
		frame.setJMenuBar(bar);
	}
	/**
	 * This method connects to Server.
	 * 
	 * 
	 */
	private void connectServer() { //content server
		label.setText("Please wait a mement");
		System.out.println("Client:  connect to server..");
		boolean flag=initClient();
		if(flag){
			String str=null;
			try {
				pout=new PrintWriter(socket.getOutputStream());
				pout.println("GetAllAlbumTitles");
				pout.flush();
				System.out.println("Client:  begin to read..");
				buffer=new BufferedReader(new InputStreamReader(socket.getInputStream()));
				while((str=buffer.readLine())==null) ;
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			System.out.println("Client:  initialization over..");
			String[] album=str.split("#");
			for(int i=0;i<album.length;i++)
				model.addElement(album[i]);		
			list=new JList<String>(model);
			list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			list.setVisibleRowCount(10);
			list.setFixedCellWidth(1000);
			list.setLayoutOrientation(JList.VERTICAL);
			list.addListSelectionListener(new ListSelectionListener() {

				public void valueChanged(ListSelectionEvent arg0) {
					label.setText("Getting data from server ...");
					content.setText("");
					content.setSize(panel2.getWidth()-4,500);
					if(!list.isSelectionEmpty()){
						System.out.println("Client:  user has operated");
						Object a=getAlbumByTitle(list.getSelectedValue().toString());
						System.out.println("Client:  client gets data succefully..");
						if(a instanceof CDAlbum){
							CDAlbum b=(CDAlbum)a;
							content.append("CD Title: "+b.getTitle()+"\n");
							content.append("Genre: "+b.getGenre()+"\n");
							content.append("Artist: "+b.getArtist()+"\n");
							content.append("Track: "+"\n");
							String[] temp=b.getTrack();
							for(int i=0;i<temp.length;i++)
								  content.append(temp[i]+"\n");
						}else{
							DVDAlbum b=(DVDAlbum)a;
							content.append("DVD Title: "+b.getTitle()+"\n");
							content.append("Genre: "+b.getGenre()+"\n");
							content.append("Director: "+b.getDirector()+"\n");
							content.append("PlotOutline: "+"\n");
							content.append(b.getPlotOutline()+"\n");
						}
						System.out.println("Client:  display data..");
					}
					System.out.println("Client:  waiting user operating..");
					label.setText("Connect server success ");
				}
			});


			content.append("Album Detail");	

			JScrollPane scrollPanel=new JScrollPane(list);
			panel1.setLayout(new BorderLayout());
			panel1.add(scrollPanel,BorderLayout.LINE_START);
			panel1.updateUI();
			frame.remove(panel);
			content.setBackground(Color.GRAY);
			content.setAutoscrolls(true);
			content.setLineWrap(true);
			panel2.setSize(350, 500);
			content.setSize(panel2.getWidth()-4,500);
			content.setWrapStyleWord(true);
			panel2.setLayout(new BorderLayout());
			panel2.add(content,BorderLayout.WEST);
			panel2.setBackground(Color.GRAY);
			split=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
					panel1, panel2);
			split.setDividerLocation(150);
			frame.add(split, BorderLayout.CENTER);
			label.setText("Connect server success ");
			addMenu(); 
			item.setText("Disconnect");
			item1.setEnabled(true);
			item2.setEnabled(true);

		}
	}
	/**
	 * This method initializes client.
	 * 
	 * 
	 */
	private boolean initClient(){
		try {
			hostName=JOptionPane.showInputDialog("hostName", "localhost");
			System.out.println("Client:  HostName:"+hostName+"#");
			if(hostName!=null){  //  user can cancel input hostName
				socket=new Socket(hostName,8888);
				System.out.println("Client:  building connection..");
				System.out.println("Client:  connect succefully..");
				return true;
			}
		}catch (ConnectException e) {
			JOptionPane.showMessageDialog(frame,"please check hostName what you input.","Conntect error",JOptionPane.OK_OPTION);
		}  catch (UnknownHostException e) {
			JOptionPane.showMessageDialog(frame,"please check hostName what you input.","UnknownHost error",JOptionPane.OK_OPTION);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * This method adds an album.
	 * 
	 * 
	 */
	private void addAnAlbum() {
		clearText(); 
		frame.remove(split);
		panel.removeAll();
		panel.setBackground(Color.LIGHT_GRAY);
		label.setText("Add an album");
		panel.setLayout(new GridLayout(6,2,60,40));
		panel.add(new JLabel("CD or DVD",JLabel.CENTER));
		panel.add(field1);
		panel.add(new JLabel("Title",JLabel.CENTER));
		panel.add(field2);
		panel.add(new JLabel("Genre",JLabel.CENTER));
		panel.add(field3);
		panel.add(new JLabel("Artist/Director",JLabel.CENTER));
		panel.add(field4);
		panel.add(new JLabel("Tracks/PlotOutline",JLabel.CENTER));
		panel.add(field5);
		JButton button1=new JButton("Reset");
		button1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				clearText();	
			}
		});
		JButton button2=new JButton("OK");
		button2.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String str1,str2,str3,str4,str5;
				str1=field1.getText().toString().replaceAll("\\s","").toUpperCase();
				str2=field2.getText().toString();
				str3=field3.getText().toString();
				str4=field4.getText().toString();
				str5=field5.getText().toString();
				boolean flag=true;
				if(str1.equals("CD")){
					CDAlbum album=new CDAlbum(str1, str2, str3, str4, str5.split("#"));
					pout.println("AddAlbum");
					pout.flush();
					try {
						oout=new ObjectOutputStream(socket.getOutputStream());
						oout.writeObject(album);
						oout.flush();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					flag=true;
				}else if(str1.equals("DVD")){
					DVDAlbum album=new DVDAlbum(str1, str2, str3, str4, str5);
					pout.println("AddAlbum");
					pout.flush();
					try {
						oout=new ObjectOutputStream(socket.getOutputStream());
						oout.writeObject(album);
						oout.flush();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					flag=true;
				}else{
					JOptionPane.showMessageDialog(panel, "CD or DVD Error");
					field1.setText("");
					flag=false;
				}
				if(flag){
					label.setText("Connect server sunccess ..");
					String str=null;
					try {
						while((str=buffer.readLine())==null);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					if(str.equals("1")){
						frame.remove(panel);
						frame.add(split);
						model.addElement(str2);
						split.updateUI();
						JOptionPane.showMessageDialog(panel, "add an album successfully!!");
					}
					else{
						frame.remove(panel);
						frame.add(split);
						split.updateUI();
						JOptionPane.showMessageDialog(panel, "add an album unsuccessfully because there is a same title album existing !!");
					}
				}
			}
		});
		panel.add(button1);
		panel.add(button2);
		panel.setSize(300,300);
		frame.add(panel,BorderLayout.CENTER);
	}
	/**
	 * This method deletes an album.
	 * 
	 * 
	 */
	private void deleteAnAlbum() {
		label.setText("Deleting an album ...");
		int i=list.getSelectedIndex();
		new JOptionPane();
		int res=JOptionPane.showConfirmDialog(frame, "Delete "+list.getSelectedValue().toString()+" ","Config Delete Opreate",JOptionPane.YES_NO_OPTION);
		if(res==JOptionPane.YES_OPTION){
			pout.println("DeleteAlbum");
			pout.flush();
			pout.println(list.getSelectedValue().toString());
			pout.flush();
			model.removeElementAt(i);
			label.setText("Connect server success ");
			list.setSelectedIndex(1);
		}else{
			label.setText("Connect server success ");
		}
	}

	/**
	 * This method get album by title.
	 * 
	 * 
	 */

	public Object getAlbumByTitle(String title){
		Object album=null;
		System.out.println("Client:  client requests "+title+"information..");
		try {
			pout.println("GetAlbumDetail");
			pout.flush();
			pout.println(title);
			pout.flush();
			System.out.println("Client:  reading server returned information..");
			System.out.println("Client:  client' connection situation:"+socket.isConnected()+"  disconnection situation:"+socket.isClosed());
			oin=new ObjectInputStream(socket.getInputStream());
			while((album=oin.readObject())==null) ;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return album;
	}

	/**
	 * This method clears the input text.
	 * 
	 * 
	 */
	public void clearText(){
		field1.setText("");		
		field2.setText("");		
		field3.setText("");		
		field4.setText("");		
		field5.setText("");	
	}
	public void windowActivated(WindowEvent arg0) {

	}
	public void windowClosed(WindowEvent arg0) {
	}
	public void windowClosing(WindowEvent arg0) {
		closeStreams();
		System.exit(0);	
	}
	public void closeStreams(){
		if(pout!=null){
			pout.println("Bye");
			pout.flush();
			pout.close();
		}
		try {
			if(buffer!=null)
				buffer.close();
			if(oin!=null)
				oin.close();
			if(oout!=null)
				oout.close();
			if(socket!=null)
				socket.close();
		} catch (IOException e) {
			System.out.println("close streams Exception");
			e.printStackTrace();
		}
	}
	public void windowDeactivated(WindowEvent arg0) {

	}
	public void windowDeiconified(WindowEvent arg0) {

	}
	public void windowIconified(WindowEvent arg0) {

	}
	public void windowOpened(WindowEvent arg0) {

	}
}

