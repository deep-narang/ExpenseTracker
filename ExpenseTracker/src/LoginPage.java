import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.lang.*;

public class LoginPage extends JFrame implements ActionListener {
	
		//Swing Stuff
	  	JTextField uname;
	    JPasswordField pass;
	    JButton login, reg, forgot_pass;
	    JLabel l1, l2;
	    JLabel title, author;
	    
	    //mySql Stuff
	    Connection conn;
	    PreparedStatement ps;
	    ResultSet rs;
	
	LoginPage()
	{
		super("ExpenseTracker-Login");
		
		Image icon = Toolkit.getDefaultToolkit().getImage("D:/Coding and Work/Java/ExpenseTracker/src/download.png");
		setIconImage(icon);
		
        uname = new JTextField();
        uname.setToolTipText("Enter Username here");
        pass = new JPasswordField();
        pass.setToolTipText("Enter Password here");
        login = new JButton("Login");
        login.setToolTipText("Click to login");
        reg = new JButton("Register");
        reg.setToolTipText("Not Registered? Click here.");
        forgot_pass=new JButton("Forgot Password");
        forgot_pass.setToolTipText("Forgot Passsword? Click to change it.");
        
        l1 = new JLabel("Enter Username: ");
        l2 = new JLabel("Enter Password: ");
        
        title=new JLabel("Welcome to Expense Tracker!");
        title.setFont(new Font("Verdena", Font.BOLD, 18));
        title.setForeground(Color.DARK_GRAY);
        
        author=new JLabel("Author: Deepanshu Narang (1802328)");
        author.setFont(new Font("Verdena", Font.ITALIC, 10));
        author.setForeground(Color.BLACK);
        author.setToolTipText("Deepanshu Narang, 1802328, ECE-Z");

        add(uname);
        add(pass);
        add(l1);
        add(l2);
        add(login);
        add(reg);
        add(forgot_pass);
        add(title);
        add(author);
        
        login.addActionListener(this);
        reg.addActionListener(this);
        forgot_pass.addActionListener(this);
        
        l1.setBounds(70, 150, 200, 30);
        uname.setBounds(220, 150, 200, 30);
        l2.setBounds(70, 200, 200, 30);
        pass.setBounds(220, 200, 200, 30);

        reg.setBounds(210, 300, 100, 30);
        login.setBounds(330, 300, 100, 30);
        forgot_pass.setBounds(50, 300, 140, 30);
        
        title.setBounds(110, 30, 300, 100);
        author.setBounds(250, 420, 180, 50);
        
        //sql stuff
        try{
            Class.forName("com.mysql.jdbc.Driver");
		conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/ExpenseTracker","root","password");
        }

        catch(Exception e)
        {
        	 Toolkit.getDefaultToolkit().beep();
        	JOptionPane.showMessageDialog(this,"Database Connection Error.","DB Error",JOptionPane.ERROR_MESSAGE);
        }

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
		
		if(obj==reg)
		{
			new RegPage();
			this.dispose();
		}
		
		else if(obj==login)
		{
			String st_uname=uname.getText().trim();
			String st_pass=new String(pass.getPassword()).trim();
			
			try {
				ps=conn.prepareStatement("select * from appData where uname=?");
				ps.setString(1, st_uname);
				rs=ps.executeQuery();
				
				if(rs.next())
				{
					String sql_uname=rs.getString(3);
					String sql_pass=rs.getString(4);
				
					if(st_uname.equals(sql_uname) && st_pass.equals(sql_pass))
					{
						new MainPage(st_uname);
						this.dispose();
					}
					
					else
					{
						Toolkit.getDefaultToolkit().beep();
						JOptionPane.showMessageDialog(this,"Check Username and Password and enter again!","Invalid Username/Password",JOptionPane.WARNING_MESSAGE);						
						pass.setText("");
					}
				}	
				
				else
				{
					 Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(this,"Username not found. Kindly register.","Invalid Username/Password",JOptionPane.WARNING_MESSAGE);
				}
			}
			
			catch(Exception e2)
			{
				System.out.println(e2);
				 Toolkit.getDefaultToolkit().beep(); 
				JOptionPane.showMessageDialog(this,"Database Connection Error.","DB Error",JOptionPane.ERROR_MESSAGE);
			}
		}
		
		else if(obj==forgot_pass)
		{
			new ForgotPassword();
			this.dispose();
		}
	}

	public static void main(String[] args) {
		new LoginPage();

	}

}
