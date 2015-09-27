                                                 
/* Client1 class is used as the user interface for entering the messages and the viewing of list of logged in users*/
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Iterator;
//create the GUI of the client window
public class Client1 extends WindowAdapter implements ActionListener
{
	JFrame MyFrame;
	JList MyList;
	JList MyList1;
	JTextField textfield =new JTextField();
	DefaultListModel Dfmodel;
	DefaultListModel Dfmodel1;
	JButton btn;
	JButton lout;
	JScrollPane scrollpane;
	JScrollPane scrollpane1;
	JLabel Mylabel;
	Socket Recievesock,Removesock,Updatesock;
	DataInputStream din;
	DataOutputStream dout;
	DataOutputStream dlout;
	DataOutputStream dout1;
	DataInputStream din1;
	String Username;
	Client1(String Username)throws IOException
{
		MyFrame = new JFrame("Client Window");
		Dfmodel=new DefaultListModel();
		Dfmodel1=new DefaultListModel();
		Mylabel=new JLabel("Message");
		MyList=new JList(Dfmodel);
		MyList1=new JList(Dfmodel1);
		btn=new JButton("Broadcast");
		lout=new JButton("Exit");
		scrollpane=new JScrollPane(MyList);
		scrollpane1=new JScrollPane(MyList1);
		JPanel panel = new JPanel();
		btn.addActionListener(this);
		lout.addActionListener(this);
		panel.add(textfield);
panel.add(btn);
		panel.add(scrollpane);
		panel.add(Mylabel);
		panel.add(lout);
		panel.add(scrollpane1);
		scrollpane.setBounds(10,20,180,150);
		scrollpane.setBorder(BorderFactory.createTitledBorder("Enter or Edit Text:"));
		scrollpane1.setBounds(250,20,100,150);
		scrollpane1.setBorder(BorderFactory.createTitledBorder("Logged in Users:"));
		Mylabel.setBounds(20,180,80,30);
		textfield.setBounds(100,180,140,30);
		btn.setBounds(260,180,110,30);
		lout.setBounds(260,230,90,30);
		MyFrame.add(panel);
		panel.setLayout(null);
		MyFrame.setSize(400, 400);
	   	MyFrame.setVisible(true);
		MyFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.Username=Username;
		MyFrame.addWindowListener(this);
		Recievesock=new Socket("localhost",1004);	//creates a socket object
		Removesock=new Socket("localhost",1004);
		Updatesock=new Socket("localhost",1004);
	               //create inputstream for a particular socket
		din=new DataInputStream(Recievesock.getInputStream());
		//create outputstream for a particular socket
		dout=new DataOutputStream(Recievesock.getOutputStream());
		dout.writeUTF(Username+" has Logged in"); //sending a message for login
		dlout=new DataOutputStream(Removesock.getOutputStream());
		dout1=new DataOutputStream(Updatesock.getOutputStream());
		din1=new DataInputStream(Updatesock.getInputStream());
// creating a thread for maintaning the MyList of user Username
		MyThread1 m1=new MyThread1(dout1,Dfmodel1,Username,din1);
		Thread t1=new Thread(m1);
		t1.start();			
		
//creating a thread for receiving a messages
		MyThread m=new MyThread(din,Dfmodel);
		Thread t=new Thread(m);
		t.start();
}
	public void actionPerformed(ActionEvent e)
{
			if(e.getSource()==btn)   // Broadcasting the messages
{	
			String str="";
			str=textfield.getText();
			textfield.setText("");
			str=Username+": > "+str;
			try{
			dout.writeUTF(str);
			System.out.println(str);
			dout.flush();
			     }catch(IOException ae)
{	
System.out.println(ae);
}
			}
			if (e.getSource()==lout)  // client exit
{
			MyFrame.dispose();
			try{
			dout.writeUTF(Username+" has exit"); //sending the message for exit
			dlout.writeUTF(Username);
			dlout.flush();
			Thread.currentThread().sleep(1000);
			System.exit(1);
			}catch(Exception oe){}
		}
	}
	public void windowClosing(WindowEvent w)
{
			try{
			dlout.writeUTF(Username);
			dlout.flush();	
			Thread.currentThread().sleep(1000);
			System.exit(1);
			}catch(Exception oe){}
		}
}
//This  class is used to maintaning the MyList of user Username
class MyThread1 implements Runnable{
	DataOutputStream dout1;
	DefaultListModel Dfmodel1;	
	DataInputStream din1;
	String Username,lname;
	ArrayList alname=new ArrayList();  //stores the MyList of user names
	ObjectInputStream obj; // read the MyList of user names
	int i=0;



MyThread1(DataOutputStream dout1,DefaultListModel Dfmodel1,String  Username,DataInputStream din1)
{
		this.dout1=dout1;
		this.Dfmodel1=Dfmodel1;
		this.Username=Username;
		this.din1=din1;
	}
	public void run(){
		try{
		dout1.writeUTF(Username);  // write the user Username in output stream
		while(true){
			obj=new ObjectInputStream(din1); //read the MyList of user names
			alname=(ArrayList)obj.readObject(); 
			if(i>0)
			Dfmodel1.clear(); 
			Iterator i1=alname.iterator();
			System.out.println(alname);
			while(i1.hasNext())
{
			lname=(String)i1.next();
			i++;
			Dfmodel1.addElement(lname);  //add the user names in MyList box
				}
			          }
		      }catch(Exception oe){}
	}
}
//This class is used to received the messages
class MyThread implements Runnable
{
	DataInputStream din;
	DefaultListModel Dfmodel;
	MyThread(DataInputStream din, DefaultListModel Dfmodel){
		this.din=din;
		this.Dfmodel=Dfmodel;
	}
	public void run()
{
		String str1="";
		while(true){
			try{
				str1=din.readUTF(); // receive the message
				// add the message in MyList box
				Dfmodel.addElement(str1);
			     }catch(Exception e){}
		                   }
		}
}
