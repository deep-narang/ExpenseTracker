import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.lang.*;
import java.util.*;

public class ForgotPassword extends JFrame implements ActionListener{
	
	JLabel lName, lUsername, lPassword, lRePassword;
	JTextField tName, tUsername;
	JPasswordField tPassword, tRePassword;
	JButton change, back;
	
	  //mySql Stuff
    Connection conn;
    PreparedStatement ps;
    ResultSet rs;
    
	ForgotPassword()
	{
		super("ExpenseTracker-Change Password");
		Image icon = Toolkit.getDefaultToolkit().getImage("D:/Coding and Work/Java/ExpenseTracker/src/download.png");
		setIconImage(icon);
		lName = new JLabel("Enter Name:");
		lUsername = new JLabel("Enter Username:");
		lPassword = new JLabel("Enter New Password:");
		lRePassword = new JLabel("Re-Enter New Password:");
		
		tName = new JTextField();
		tUsername = new JTextField();
		tPassword = new JPasswordField();
		tRePassword = new JPasswordField();
		
		tName.setToolTipText("Enter Name here");
		tUsername.setToolTipText("Enter Name here");
		tPassword.setToolTipText("Enter Name here");
		tRePassword.setToolTipText("Enter Name here");
		
		change = new JButton("Change Password");
		back=new JButton("Cancel");
		
		change.setToolTipText("Click to Change Password");
		back.setToolTipText("Click to go back to Login Page");
	
		add(lName);
		add(lUsername);
		add(lPassword);
		add(lRePassword);
		add(tName);
		add(tUsername);
		add(tPassword);
		add(tRePassword);
		add(change);
		add(back);
		
		change.addActionListener(this);
		back.addActionListener(this);
		
		try{
            Class.forName("com.mysql.jdbc.Driver");
            conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/ExpenseTracker","root","password");
        }

        catch(Exception e)
        {
        	 Toolkit.getDefaultToolkit().beep();
        	JOptionPane.showMessageDialog(this,"Database Connection Error.","DB Error",JOptionPane.ERROR_MESSAGE);
        }

		lName.setBounds(50, 70, 200, 30);
        tName.setBounds(250, 70, 200, 30);
        lUsername.setBounds(50, 120, 200, 30);
        tUsername.setBounds(250, 120, 200, 30);
        lPassword.setBounds(50, 170, 200, 30);
        tPassword.setBounds(250, 170, 200, 30);
        lRePassword.setBounds(50, 230, 200, 30);
        tRePassword.setBounds(250, 230, 200, 30);
        back.setBounds(120, 300, 80, 30);
        change.setBounds(250, 300, 150, 30);
        
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
		
		else if(obj==change)
		{
			 int a=JOptionPane.showConfirmDialog(this,"Are you sure?");  
			 if(a==JOptionPane.YES_OPTION){  
			  					
			String stName=tName.getText().trim();
			String stUsername=tUsername.getText().trim();
			String stPassword=new String(tPassword.getPassword()).trim();
			String stRePassword=new String(tRePassword.getPassword()).trim();
			
			if(stName.equals("") || stPassword.equals("") || stUsername.equals("") || stRePassword.equals(""))
			{
				Toolkit.getDefaultToolkit().beep();
				JOptionPane.showMessageDialog(this,"Check Username and Password and enter again!","Invalid Username/Password",JOptionPane.WARNING_MESSAGE);						
				tPassword.setText("");
				tRePassword.setText("");
			}
			
			else if(!(stPassword.equals(stRePassword)))
			{
				Toolkit.getDefaultToolkit().beep();
				JOptionPane.showMessageDialog(this,"Password miss match. Enter Again!","Invalid Username/Password",JOptionPane.WARNING_MESSAGE);						
				tPassword.setText("");
				tRePassword.setText("");
			}
			
			else
			{
				try {
					ps=conn.prepareStatement("Select * from appData where uname=?");
					ps.setString(1, stUsername);
					rs=ps.executeQuery();
					
					if(rs.next())
					{
						String sqlName=rs.getString(2);
						String sqlUsername = rs.getString(3);

						if(sqlName.equals(stName) && sqlUsername.equals(stUsername))
						{
							ps=conn.prepareStatement("update appData set password=? where uname=?");
							ps.setString(1, stPassword);
							ps.setString(2, stUsername);
							ps.executeUpdate();
							JOptionPane.showMessageDialog(this,"Password Chnaged!");
							new LoginPage();
							this.dispose();
						}
						
						else {
							Toolkit.getDefaultToolkit().beep();
							JOptionPane.showMessageDialog(this,"Invalid Name/Username","Invalid User",JOptionPane.WARNING_MESSAGE);						
							tPassword.setText("");
							tRePassword.setText("");
						}
					}
				
					
					else {
						Toolkit.getDefaultToolkit().beep();
						JOptionPane.showMessageDialog(this,"Invalid Name/Username","Invalid User",JOptionPane.WARNING_MESSAGE);						
						tPassword.setText("");
						tRePassword.setText("");
				}
			}
				
				catch(Exception e11)
				{
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(this,"Unable to connect to Database.","DB Error",JOptionPane.WARNING_MESSAGE);						
				}
			}
		}
		}
	}
}
