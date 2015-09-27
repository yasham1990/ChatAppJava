                           
/*The Server1 class is used wait for incoming requests and to service them when they come in*/
import java.io.*;
import java.net.*;
import java.util.*;
public class Server1{
	ServerSocket servsock;
	Socket Recievesock,Removesock,Updatesock;
	ArrayList arl=new ArrayList();
	ArrayList arl1=new ArrayList();
	ArrayList arl2=new ArrayList();
	ArrayList alname=new ArrayList();
		Server1()throws IOException
{
		servsock=new ServerSocket(1004); // create server socket
		while(true){
			Recievesock=servsock.accept();	 //accept the client socket
			Removesock=servsock.accept();
			Updatesock=servsock.accept();
			arl.add(Recievesock);	// add the client socket in arraylist
			arl1.add(Removesock);
			arl2.add(Updatesock);
			System.out.println("Client  Connected");
	//new thread for maintaining the list of user name
MyThread2 m=new MyThread2(Updatesock,arl2,alname);
 Thread t2=new Thread(m);
			t2.start();
//new thread for receive and sending the messages
			MyThread3 r=new MyThread3(Recievesock,arl);
			Thread t=new Thread(r);
			t.start();
			// new thread for update the list of user name
			MyThread4 my=new MyThread4(Removesock,arl1,Recievesock,Updatesock); 
			Thread t1=new Thread(my);
			t1.start();
			}
	}
	public static void main(String[] args)
{
		try{
			new Server1();
                       			
		      }catch (IOException e){}
	}
}
//This class is used to update the list of user name
class MyThread4 implements Runnable
{
	Socket Recievesock,Removesock,Updatesock;
	static ArrayList arl1;
	DataInputStream ddin;
	String sockname;
	


MyThread4(Socket Removesock,ArrayList arl1,Socket Recievesock,Socket Updatesock)
{
		this.Removesock=Removesock;
		this.arl1=arl1;
		this.Recievesock=Recievesock;
		this.Updatesock=Updatesock;
		}
	public void run()
{	
		try{
		ddin=new DataInputStream(Removesock.getInputStream());
		while(true)
{
		sockname=ddin.readUTF();
		System.out.println("Exit  :"+sockname);
		MyThread2.alname.remove(sockname); //remove the logout user name from arraylist
		MyThread2.every();
		arl1.remove(Removesock);
		MyThread3.arl.remove(Recievesock);
		MyThread2.arl2.remove(Updatesock);
		if(arl1.isEmpty())
		System.exit(0); //all client has been logout
		}
		         }catch(Exception ie){}
	}
}


// class is used to maintain the list of all online users
class MyThread2 implements Runnable
{
	Socket Updatesock;
	static ArrayList arl2;
	static ArrayList alname;
	static DataInputStream din1;	
	static DataOutputStream dout1;
MyThread2(Socket Updatesock,ArrayList arl2,ArrayList alname)
{
		this.Updatesock=Updatesock;
		this.arl2=arl2;
		this.alname=alname;
	}
	public void run()
{
		try{
		din1= new DataInputStream(Updatesock.getInputStream());
		alname.add(din1.readUTF()); // store the user name in arraylist
		every();
		       }catch(Exception oe){System.out.println("Main expression"+oe);
}
}
// send the list of user name to all client
	static void every()throws Exception{
		Iterator i1=arl2.iterator();
		Socket st1;		
while(i1.hasNext())
{
			st1=(Socket)i1.next();
			dout1=new DataOutputStream(st1.getOutputStream());
			ObjectOutputStream obj=new ObjectOutputStream(dout1);
			obj.writeObject(alname); //write the list of users in stream of all clients
			dout1.flush();
			obj.flush();
		}
	}
}
//class is used to receive the message and send it to all clients
class MyThread3 implements Runnable
{
	Socket Recievesock;
	static ArrayList arl;
	DataInputStream din;
	DataOutputStream dout;
MyThread3(Socket Recievesock, ArrayList arl)
{
		this.Recievesock=Recievesock;
		this.arl=arl;
	}
	public void run(){
		String str;
		int i=1;
		try{
		din=new DataInputStream(Recievesock.getInputStream());
		     }catch(Exception e){}
		while(i==1)
{
		try{
			str=din.readUTF();  //read the message
			distribute(str);
		        }catch (IOException e){}
		}
	}
// send it to all clients
	public void distribute(String str)throws IOException
{
		Iterator i=arl.iterator();
		Socket st;
		while(i.hasNext())
{
			st=(Socket)i.next();
			dout=new DataOutputStream(st.getOutputStream());
			dout.writeUTF(str);
			dout.flush();
		}
	}
}
