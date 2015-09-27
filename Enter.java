                            
/*The Enter class is used to enter the chat window */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.sql.*;
//Create the GUI of the Enter window 
public class Enter extends JFrame  implements ActionListener
{
	JFrame frame1;
	JTextField textfield;
	JButton btn,btn1;
	JLabel Myheading;
	JLabel Mylabel;
	DataOutputStream dout;
	DataOutputStream dlout;
	int flag;
	public static void main(String[] args){
		new Enter();
		}
	public Enter(){
		frame1 = new JFrame("Zoom In Page");
		textfield=new JTextField();
		btn=new JButton("Enter");
                btn1=new JButton("Not Interested");
		Myheading=new JLabel("Wanna Talk");
		Myheading.setFont(new Font("TimesNewRoman", Font.BOLD,35));
               	Myheading.setForeground(Color.black);
		Mylabel=new JLabel("Please enter your Username");
		Mylabel.setFont(new Font("TimesNewRoman", Font.PLAIN, 20));
		JPanel panel = new JPanel();
		btn.addActionListener(this);
		btn1.addActionListener(this);
		panel.add(Myheading);
                panel.add(Mylabel);
		panel.add(textfield);
		panel.add(btn);
		panel.add(btn1);
		Myheading.setBounds(110,30,300,80);
		Mylabel.setBounds(80,120,300,60);
		textfield.setBounds(125,200,150,30);
		btn.setBounds(80,250,90,30);
		btn1.setBounds(180,250,150,30);
		frame1.add(panel);
		panel.setLayout(null);
		frame1.setSize(400, 400);
	    	frame1.setVisible(true);
		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);}
	// pass the user Username to Client1 class
	public void actionPerformed(ActionEvent e)
 {
		int flag=0;
		String Username="";
		Username=textfield.getText();
            	if(e.getSource()==btn1)
		frame1.dispose();
		if(e.getSource()==btn)
          {
		
		String driver="sun.jdbc.odbc.JdbcOdbcDriver";
                String url="jdbc:odbc:yasham";
                String quer="SELECT * from table1";
		try 
            {
                Class.forName(driver);
                Connection con=DriverManager.getConnection(url);
                Statement st=con.createStatement();
                ResultSet rst=st.executeQuery(quer);

     		while(rst.next())
	{
                    String rname=rst.getString(2);
			if(rname.equalsIgnoreCase(Username))
                        {
				 flag=1;  
			}
	}
			if(flag==1)
			{			
			setVisible(false);                 
			frame1.dispose();
			Client1 mc=new Client1(Username);
			}

			else
                        {
				setVisible(false);
			JOptionPane.showMessageDialog(this,"Incorrect login name",
            			"Error",JOptionPane.ERROR_MESSAGE);
			frame1.dispose();
			Enter es=new Enter();
			
      			}


}
		          catch(ClassNotFoundException k)
            {
                System.out.println("Class Not Found"+k.getMessage());
            }
             catch(Exception h)
            {                System.out.println("Any other Exception caught"+h.getMessage());
            }

}
}
}