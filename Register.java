                      
/*This class is used to register the name of the user */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.sql.*;
 //Create the GUI of Register  window
public class Register extends JFrame implements ActionListener
{
	JFrame frame1;
	JTextField textfield;
	JButton btn;
	JLabel Myheading;
	JLabel Mylabel;
	int flag;
	public static void main(String[] args)
{
		new Register();
	}
	public Register()
{
		frame1 = new JFrame("Registration Window");
		textfield=new JTextField();
		btn=new JButton("Save");
		Myheading=new JLabel("Registration");
		Myheading.setFont(new Font("Impact", Font.BOLD,40));
		Mylabel=new JLabel("Enter your Login name");
		Mylabel.setFont(new Font("Serif", Font.PLAIN, 24));
		JPanel panel = new JPanel();
		btn.addActionListener(this);
		panel.add(Myheading);
		panel.add(textfield);
		panel.add(Mylabel);
		panel.add(btn);
		Myheading.setBounds(70,20,400,80);
		Mylabel.setBounds(85,125,300,60);
		textfield.setBounds(100,190,150,30);
		btn.setBounds(120,250,90,30);
		frame1.add(panel);
		panel.setLayout(null);
		frame1.setSize(400, 400);
	        frame1.setVisible(true);
		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	// Click button to save login name in file
	public void actionPerformed(ActionEvent e){
		String name="";
		int flag=1;
		name=textfield.getText();
		String driver="sun.jdbc.odbc.JdbcOdbcDriver";//
		String url="jdbc:odbc:yasham";//
                String quer="SELECT * from table1";
           try 
            {
                Class.forName(driver);
                Connection con=DriverManager.getConnection(url);//
                Statement st=con.createStatement();//
                ResultSet rst=st.executeQuery(quer);//
		PreparedStatement ps=con.prepareStatement("INSERT INTO table1(idd) VALUES (?)");
     		while(rst.next())
	{
                    String rname=rst.getString(2);

			if(rname.equalsIgnoreCase(name))//If user name already exists
                        {
				flag=0;
                            setVisible(false);
                            JOptionPane.showMessageDialog(this,"Sorry :( \n"+"User name already exit\n"+"enter other login name",  "Error",JOptionPane.ERROR_MESSAGE);                       
			frame1.dispose();
			Register rs=new Register();
                        }
	}
			if(flag==1)
		{
			
                                        
				try 
                                    {
                                        
				if(name.equalsIgnoreCase("") )//If user doesn’t enter any name 
                        	{
                                   setVisible(false);
                                    
			JOptionPane.showMessageDialog(this,"Incorrect login name\n","Error",JOptionPane.ERROR_MESSAGE);
			frame1.dispose();
			Register rs=new Register();
				}
				else	//If  user enter new login name than it is register in file
				{
					ps.setString(1, name);
                                        ps.executeUpdate();
JOptionPane.showMessageDialog(this,"successfully registered\n","message",JOptionPane.INFORMATION_MESSAGE);
					
					ps.close();
					con.close();				
					
				}

					}
			catch(SQLException j)
                                    {
                                        System.out.println("sql"+j.getMessage());
                                    }
                                    catch(Exception h)
                                    {

                                        System.out.println("Any other exception "+h.getMessage());
                                    }
				
				}
						
             	
            
		}
		catch(ClassNotFoundException k)
            {
                System.out.println("Class Not Found"+k.getMessage());
            }
             catch(Exception h)
            {
                System.out.println("Any other Exception caught"+h.getMessage());
     	    }
frame1.dispose();
}
 
}      
   			