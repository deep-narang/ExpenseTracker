import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class RegPage extends JFrame implements ActionListener {

	  //swing stuff
	  JTextField name, username;
	  JPasswordField pass, rpass;
	  JButton reg_log, back;
	  JLabel lname, luname, lpass, lrpass; 
	  
	  //sql stuff
	  Connection conn;
	  PreparedStatement ps;
	  ResultSet rs;
	  
	  RegPage()
	  {
		    super("ExpenseTracker-Register");
		     
		    Image icon = Toolkit.getDefaultToolkit().getImage("D:/Coding and Work/Java/ExpenseTracker/src/download.png");
			setIconImage(icon);
			
	        name = new JTextField();
	        name.setToolTipText("Enter Name here");
	        username = new JTextField();
	        username.setToolTipText("Enter Username here");
	        pass = new JPasswordField();
	        pass.setToolTipText("Enter Password here");
	        rpass = new JPasswordField();
	        rpass.setToolTipText("Re-Enter Password");
	        reg_log = new JButton("Register & Login");
	        reg_log.setToolTipText("Register then Login");
	        lname = new JLabel("Enter Name: ");
	        luname = new JLabel("Enter Username: ");
	        lpass = new JLabel("Enter Password: ");
	        lrpass = new JLabel("Enter Password Again: ");
	        back = new JButton("Back");
	        back.setToolTipText("Click to got back to Login Page");

	        add(name);
	        add(username);
	        add(lname);
	        add(lpass);
	        add(lrpass);
	        add(luname);
	        add(pass);
	        add(rpass);
	        add(reg_log);
	        add(back);

	        reg_log.addActionListener(this);
	        back.addActionListener(this);
	        
	        //creating mysql connection
	        try{
	            Class.forName("com.mysql.jdbc.Driver");
	            conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/ExpenseTracker","root","password");
	        }

	        catch(Exception e)
	        {
	        	 Toolkit.getDefaultToolkit().beep();
	        	 JOptionPane.showMessageDialog(this,"Uanble to connect to Database. Try Again. ","Database Connection Error",JOptionPane.ERROR_MESSAGE);
	        }

	        lname.setBounds(50, 70, 200, 30);
	        name.setBounds(250, 70, 200, 30);
	        luname.setBounds(50, 120, 200, 30);
	        username.setBounds(250, 120, 200, 30);
	        lpass.setBounds(50, 170, 200, 30);
	        pass.setBounds(250, 170, 200, 30);
	        lrpass.setBounds(50, 230, 200, 30);
	        rpass.setBounds(250, 230, 200, 30);
	        back.setBounds(120, 300, 80, 30);
	        reg_log.setBounds(250, 300, 150, 30);

	        setSize(500, 500);
	        setLayout(null);
	        setResizable(false);	   
	        setLocation((Toolkit.getDefaultToolkit().getScreenSize().width-getWidth())/2, (Toolkit.getDefaultToolkit().getScreenSize().height-getHeight())/2 );
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setVisible(true);
	  }
	  
	  public void actionPerformed(ActionEvent ae)
	  {
		  Object obj = new Object();
		  obj=ae.getSource();
		  
		  if(obj==back)
		  {
			  new LoginPage();
			  this.dispose();
		  }
		  
		  if(obj==reg_log)
		  {
			  String st_username, st_name;
			  st_username = username.getText().trim();
			  st_name = name.getText().trim();
			  
			  String st_pass = new String(pass.getPassword()).trim();
			  String st_rpass = new String(rpass.getPassword()).trim();
			  
			  if(st_username.equals("") || st_name.equals("") || st_pass.equals("") || st_rpass.equals(""))
			  {
				  Toolkit.getDefaultToolkit().beep();
            	  JOptionPane.showMessageDialog(this,"Empty Field, Fill all Details","Empty Field!",JOptionPane.WARNING_MESSAGE);
            	  username.setText("");
            	  pass.setText("");
            	  rpass.setText("");
			  }
			  
			  else if(!(st_pass.equals(st_rpass)))
				{
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(this,"Password miss match. Enter Again!","Invalid Username/Password",JOptionPane.WARNING_MESSAGE);						
					pass.setText("");
					rpass.setText("");
				}
			  
			  else
			  {
				  try {
					  ps=conn.prepareStatement("Select * from appData where uname=?");
		              ps.setString(1, st_username);
		              
		              rs=ps.executeQuery();
		              
		              if(rs.next())
		              {
		            	  Toolkit.getDefaultToolkit().beep();
		            	  JOptionPane.showMessageDialog(this,"Username already exsits. Kindly Login.","Username exists",JOptionPane.WARNING_MESSAGE);
		            	  username.setText("");
		            	  pass.setText("");
		            	  rpass.setText("");
		              }
		               
		              else {
		            	  
		            	  if(st_pass.equals(st_rpass))
		            	  {
		            		  ps=conn.prepareStatement("insert into appData(name, uname, password) values(?, ?, ?)");
		            		  ps.setString(1, st_name);
		            		  ps.setString(2, st_username);
		            		  ps.setString(3, st_pass);
		            		  ps.executeUpdate();
		            		  
		            		  ps=conn.prepareStatement("insert into transactions(uname, type, amount) values(?,?,?)");
		            		  ps.setString(1, st_username);
		            		  ps.setString(2, "Salary");
		            		  ps.setDouble(3, 0.0);
		            		  ps.executeUpdate();
		            		  
		            		  ps=conn.prepareStatement("insert into transactions(uname, type, amount) values(?,?,?)");
		            		  ps.setString(1, st_username);
		            		  ps.setString(2, "Saved");
		            		  ps.setDouble(3, 0.0);
		            		  ps.executeUpdate();
		            		  
		            		  ps=conn.prepareStatement("insert into transactions(uname, type, amount) values(?,?,?)");
		            		  ps.setString(1, st_username);
		            		  ps.setString(2, "Housing");
		            		  ps.setDouble(3, 0.0);
		            		  ps.executeUpdate();
		            		  
		            		  ps=conn.prepareStatement("insert into transactions(uname, type, amount) values(?,?,?)");
		            		  ps.setString(1, st_username);
		            		  ps.setString(2, "Food");
		            		  ps.setDouble(3, 0.0);
		            		  ps.executeUpdate();
		            		  
		            		  ps=conn.prepareStatement("insert into transactions(uname, type, amount) values(?,?,?)");
		            		  ps.setString(1, st_username);
		            		  ps.setString(2, "Clothing");
		            		  ps.setDouble(3, 0.0);
		            		  ps.executeUpdate();
		            		  
		            		  ps=conn.prepareStatement("insert into transactions(uname, type, amount) values(?,?,?)");
		            		  ps.setString(1, st_username);
		            		  ps.setString(2, "EMI");
		            		  ps.setDouble(3, 0.0);
		            		  ps.executeUpdate();
		            		  
		            		  ps=conn.prepareStatement("insert into transactions(uname, type, amount) values(?,?,?)");
		            		  ps.setString(1, st_username);
		            		  ps.setString(2, "Insurance");
		            		  ps.setDouble(3, 0.0);
		            		  ps.executeUpdate();
		            		  
		            		  ps=conn.prepareStatement("insert into transactions(uname, type, amount) values(?,?,?)");
		            		  ps.setString(1, st_username);
		            		  ps.setString(2, "Entertainment");
		            		  ps.setDouble(3, 0.0);
		            		  ps.executeUpdate();
		            		  
		            		  ps=conn.prepareStatement("insert into transactions(uname, type, amount) values(?,?,?)");
		            		  ps.setString(1, st_username);
		            		  ps.setString(2, "Child-Care");
		            		  ps.setDouble(3, 0.0);
		            		  ps.executeUpdate();
		            		  
		            		  ps=conn.prepareStatement("insert into transactions(uname, type, amount) values(?,?,?)");
		            		  ps.setString(1, st_username);
		            		  ps.setString(2, "Bills");
		            		  ps.setDouble(3, 0.0);
		            		  ps.executeUpdate();
		            		  
		            		  ps=conn.prepareStatement("insert into transactions(uname, type, amount) values(?,?,?)");
		            		  ps.setString(1, st_username);
		            		  ps.setString(2, "Miscellaneous");
		            		  ps.setDouble(3, 0.0);
		            		  ps.executeUpdate();
		            		  ps=conn.prepareStatement("insert into transactions(uname, type, amount) values(?,?,?)");
		            		  ps.setString(1, st_username);
		            		  ps.setString(2, "Other");
		            		  ps.setDouble(3, 0.0);
		            		  ps.executeUpdate();
		            		  
		            		  new MainPage(st_username);
		            		  this.dispose();
		            	  }
		            	  
		            	  else
		            	  {
		            		  Toolkit.getDefaultToolkit().beep();
		            		  JOptionPane.showMessageDialog(this,"Passwords don't match. Re-enter Password.","Invalid password",JOptionPane.WARNING_MESSAGE);
		            		  pass.setText("");
		            		  rpass.setText("");
		            	  }
		              }
		            	  
				  }
				  
				  catch(Exception e){
					  Toolkit.getDefaultToolkit().beep();
					  JOptionPane.showMessageDialog(this,"Uanble to connect to Database. Try Again. ","Database Connection Error",JOptionPane.ERROR_MESSAGE);
				  }
			  }
			  
		  }
	  }
	  
}
