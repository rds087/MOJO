import java.awt.GridLayout;

import javax.swing.*;

public class Admin_Page {

	public static boolean createAndShowUI() 
	{
		LoginPanel login = new LoginPanel();
		int response = JOptionPane.showConfirmDialog(null, login,
				"Please Enter UserName and Password",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

		if (response == JOptionPane.OK_OPTION)
		{
			String name = login.getName();
			String pWord = login.getPassword();

			if (name.equals("Saumya") && pWord.equals("Sharma")) 
			{
				return true;
			}
			else 
			{
			  String msg = "Incorrect username and password";
			  JOptionPane.showMessageDialog(null, msg);
			  return false;
			}
		}
		
		return false;
	}

}

class LoginPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField nameField = new JTextField(10);
	private JPasswordField passwordField = new JPasswordField(10);

	public LoginPanel() {
		setLayout(new GridLayout(5, 5, 40, 40)); // 
		add(new JLabel("Name:"));
		add(nameField);
		add(new JLabel("Password:"));
		add(passwordField);
	}

	public String getName() {
		return nameField.getText();
	}

	public String getPassword() {
		return new String(passwordField.getPassword()); // 
	}
}