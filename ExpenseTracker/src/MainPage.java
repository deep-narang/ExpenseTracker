import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.lang.*;
import javax.swing.table.*;

public class MainPage extends JFrame implements ActionListener{
	
	JButton updates, reset, signout;
	String uname;
	
	JComboBox cb1;
			
	JLabel l1, l_amt, l2, ernt, spnt;
	JTable table;
	JTextField amt;
	JScrollPane jp;
	JPanel panel; 
	
	//mySql Stuff
    Connection conn;
    PreparedStatement ps;
    ResultSet rs;
    
	MainPage(String uname)
	{
		super("ExpenseTracker-MainPage");
		this.uname=uname;
		
		Image icon = Toolkit.getDefaultToolkit().getImage("D:/Coding and Work/Java/ExpenseTracker/src/download.png");
		setIconImage(icon);
		updates = new JButton("Add Data");
		reset= new JButton("Reset");
		signout=new JButton("Sign Out");
		
		panel=new JPanel();
		amt=new JTextField();
		
		l1=new JLabel("Welcome, @"+uname);
		l_amt=new JLabel("Enter Amount:");
		l2=new JLabel("Select Type:");
		
		String[] coloumns= {"Sr. No.", "Type", "Total Amount"};
		String row[] = new String[12];
		
		String data[]= {"Salary", "Saved", "Housing", "Food", "Clothing", "EMI", "Insurance", "Entertainment", "Child-Care", "Bills", "Miscellaneous", "Other"};
		cb1=new JComboBox(data);
		
		cb1.setToolTipText("Select one Option out of these.");
		amt.setToolTipText("Enter Amount here.");
		
		 //sql stuff
        try{
        	int i=0;
        	
            Class.forName("com.mysql.jdbc.Driver");
            conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/ExpenseTracker","root","password");
            
            ps=conn.prepareStatement("select sum(amount) from transactions where uname=? and type='Salary'");
            ps.setString(1, uname);
            rs=ps.executeQuery();
            if(rs.next())
            {
            	row[i++]=rs.getString(1);
            }
            
            ps=conn.prepareStatement("select sum(amount) from transactions where uname=? and type='Saved'");
            ps.setString(1, uname);
            rs=ps.executeQuery();
            if(rs.next())
            	row[i++]=rs.getString(1);
            	
            ps=conn.prepareStatement("select sum(amount) from transactions where uname=? and type='Housing'");
            ps.setString(1, uname);
            rs=ps.executeQuery();
            if(rs.next())
            	row[i++]=rs.getString(1);
            	
            	
            ps=conn.prepareStatement("select sum(amount) from transactions where uname=? and type='Food'");
            ps.setString(1, uname);
            rs=ps.executeQuery();
            if(rs.next())
            	row[i++]=rs.getString(1);
            	
            	
            ps=conn.prepareStatement("select sum(amount) from transactions where uname=? and type='Clothing'");
            ps.setString(1, uname);
            rs=ps.executeQuery();
            if(rs.next())
            	row[i++]=rs.getString(1);
            	
            	
            ps=conn.prepareStatement("select sum(amount) from transactions where uname=? and type='EMI'");
            ps.setString(1, uname);
            rs=ps.executeQuery();
            if(rs.next())
            	row[i++]=rs.getString(1);
            	
            	
            	
            ps=conn.prepareStatement("select sum(amount) from transactions where uname=? and type='Insurance'");
            ps.setString(1, uname);
            rs=ps.executeQuery();
            if(rs.next())
            	row[i++]=rs.getString(1);
            	
            	
            	
            ps=conn.prepareStatement("select sum(amount) from transactions where uname=? and type='Entertainment'");
            ps.setString(1, uname);
            rs=ps.executeQuery();
            if(rs.next())
            	row[i++]=rs.getString(1);
            	
            	
            	
            ps=conn.prepareStatement("select sum(amount) from transactions where uname=? and type='Child-Care'");
            ps.setString(1, uname);
            rs=ps.executeQuery();
            if(rs.next())
            	row[i++]=rs.getString(1);
            	
            	
            	
            ps=conn.prepareStatement("select sum(amount) from transactions where uname=? and type='Bills'");
            ps.setString(1, uname);
            rs=ps.executeQuery();
            if(rs.next())
            	row[i++]=rs.getString(1);
            	
            	
            	
            ps=conn.prepareStatement("select sum(amount) from transactions where uname=? and type='Miscellaneous'");
            ps.setString(1, uname);
            rs=ps.executeQuery();
            if(rs.next())
            	row[i++]=rs.getString(1);
            	
            	
            	
            ps=conn.prepareStatement("select sum(amount) from transactions where uname=? and type='Other'");
            ps.setString(1, uname);
            rs=ps.executeQuery();
            if(rs.next())
            	row[i++]=rs.getString(1);
            
        }

        catch(Exception e)
        {
        	System.out.println(e);
        	 Toolkit.getDefaultToolkit().beep();
        	 JOptionPane.showMessageDialog(this,"Database Connection Error.","DB Error",JOptionPane.ERROR_MESSAGE);
        }
		
        String entry[][]={
        		{"Sr. No.", "Spent On", "Amount"},
        		{"1", "Salary", row[0]},
        		{"2", "Saved", row[1]}, 
        		{"3", "Housing", row[2]}, 
        		{"4", "Food", row[3]},
        		{"5", "Clothing", row[4]},
        		{"6", "EMI", row[5]},
        		{"7", "Insurance", row[6]},
        		{"8", "Entertainment", row[7]},
        		{"9", "Child-Care", row[8]},
        		{"10", "Bills", row[9]},
        		{"11", "Miscellaneous", row[10]},
        		{"12", "Other", row[11]}
        };
        
        table = new JTable(entry, coloumns);
        table.setEnabled(false);
        table.setRowHeight(table.getRowHeight() + 7);
        table.setBorder(BorderFactory.createEtchedBorder());
                
        add(l_amt);
        add(updates);
        add(signout);
        add(reset);
        add(l1);
        add(l_amt);
        add(table);
        add(amt);
        add(l2);
        add(cb1);
        
        signout.addActionListener(this);
        updates.addActionListener(this);
        reset.addActionListener(this);
        
        l1.setBounds(150, 10, 180, 30);
        signout.setBounds(350, 10, 100, 30);
        table.setBounds(50, 50, 400, 300);
        l2.setBounds(10, 370, 100, 30);
        cb1.setBounds(100, 370, 120, 30);
        l_amt.setBounds(248, 370, 80, 30);
        amt.setBounds(336, 370, 100, 30);
        reset.setBounds(70, 420, 150, 30);
        updates.setBounds(280, 420, 150, 30);
               
		setSize(500, 500);
        setLayout(null);
        setResizable(false);      
        setLocation((Toolkit.getDefaultToolkit().getScreenSize().width-getWidth())/2, (Toolkit.getDefaultToolkit().getScreenSize().height-getHeight())/2 );
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		Object obj= new Object();
		obj=ae.getSource();
		
		if(obj == signout)
		{
			int a=JOptionPane.showConfirmDialog(this,"Are you sure?");  
			 if(a==JOptionPane.YES_OPTION){ 

					new LoginPage();
					this.dispose(); 
			 }
		}
		
		else if(obj==reset)
		{
			try {
				ps=conn.prepareStatement("Delete from transactions where uname=?");
				ps.setString(1, uname);
				ps.executeUpdate();
				new MainPage(uname);
				this.dispose();
				JOptionPane.showMessageDialog(this,"Update Made!");
			}
			
			catch(Exception e22) {
				 Toolkit.getDefaultToolkit().beep();
	        	 JOptionPane.showMessageDialog(this,"Unable to make changes right now.","DB Error",JOptionPane.ERROR_MESSAGE);
			}
		}
		
		else if(obj==updates)
		{
			String type=""+cb1.getItemAt(cb1.getSelectedIndex());
			Double amount=Double.parseDouble(amt.getText());
			try {
			ps=conn.prepareStatement("Insert into transactions (uname, type, amount) values(?,?,?)");
			ps.setString(1, uname);
			ps.setString(2, type);
			ps.setDouble(3, amount);
			ps.executeUpdate();
			new MainPage(uname);
			this.dispose();
			JOptionPane.showMessageDialog(this,"Update Made!");
			
			
			}
			
			catch(Exception e11)
			{
				Toolkit.getDefaultToolkit().beep();
	        	 JOptionPane.showMessageDialog(this,"Unable to make changes right now.","DB Error",JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
}
