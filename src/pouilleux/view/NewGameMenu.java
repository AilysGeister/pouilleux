package pouilleux.view;

import pouilleux.controller.*;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JButton;
import java.awt.Insets;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JTextField;

public class NewGameMenu extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField username1;
	private JTextField username2;
	private JTextField username3;
	private JTextField username4;

	/**
	 * Create the panel.
	 */
	public NewGameMenu(Game game) {
		//Used variable to initialize the game:
		ArrayList<String> usernames = new ArrayList<String>(4);
		
		//Layout:
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JButton backButton = new JButton(PropretiesReader.getString("back"));
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ViewManager.navigateTo("TitleScreen");
			}
		});
		backButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		GridBagConstraints gbc_backButton = new GridBagConstraints();
		gbc_backButton.insets = new Insets(0, 0, 5, 5);
		gbc_backButton.gridx = 0;
		gbc_backButton.gridy = 0;
		add(backButton, gbc_backButton);
		
		JLabel newGameLabel = new JLabel(PropretiesReader.getString("newGame")+":");
		newGameLabel.setFont(new Font("Tahoma", Font.BOLD, 36));
		GridBagConstraints gbc_newGameLabel = new GridBagConstraints();
		gbc_newGameLabel.anchor = GridBagConstraints.WEST;
		gbc_newGameLabel.insets = new Insets(0, 0, 5, 0);
		gbc_newGameLabel.gridx = 2;
		gbc_newGameLabel.gridy = 0;
		add(newGameLabel, gbc_newGameLabel);
		
		JLabel lblNewLabel = new JLabel(PropretiesReader.getString("askPlayerNumber")); //$NON-NLS-1$
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 24));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 2;
		add(lblNewLabel, gbc_lblNewLabel);
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		JComboBox comboBoxPlayerNumber = new JComboBox(new String[] {"1", "2", "3", "4"});
		comboBoxPlayerNumber.setFont(new Font("Tahoma", Font.PLAIN, 24));
		GridBagConstraints gbc_comboBoxPlayerNumber = new GridBagConstraints();
		gbc_comboBoxPlayerNumber.anchor = GridBagConstraints.WEST;
		gbc_comboBoxPlayerNumber.insets = new Insets(0, 0, 5, 0);
		gbc_comboBoxPlayerNumber.gridx = 2;
		gbc_comboBoxPlayerNumber.gridy = 2;
		add(comboBoxPlayerNumber, gbc_comboBoxPlayerNumber);
		
		JLabel lblNewLabel_1 = new JLabel(PropretiesReader.getString("player1")+":");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 24));
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 3;
		add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		username1 = new JTextField();
		GridBagConstraints gbc_username1 = new GridBagConstraints();
		gbc_username1.anchor = GridBagConstraints.WEST;
		gbc_username1.insets = new Insets(0, 0, 5, 0);
		gbc_username1.gridx = 2;
		gbc_username1.gridy = 3;
		add(username1, gbc_username1);
		username1.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel(PropretiesReader.getString("player2")+":"); //$NON-NLS-1$
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 24));
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 0;
		gbc_lblNewLabel_2.gridy = 4;
		add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		username2 = new JTextField();
		GridBagConstraints gbc_username2 = new GridBagConstraints();
		gbc_username2.anchor = GridBagConstraints.WEST;
		gbc_username2.insets = new Insets(0, 0, 5, 0);
		gbc_username2.gridx = 2;
		gbc_username2.gridy = 4;
		add(username2, gbc_username2);
		username2.setColumns(10);
		
		JLabel lblNewLabel_4 = new JLabel(PropretiesReader.getString("player3")+":"); //$NON-NLS-1$
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 24));
		GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
		gbc_lblNewLabel_4.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_4.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_4.gridx = 0;
		gbc_lblNewLabel_4.gridy = 5;
		add(lblNewLabel_4, gbc_lblNewLabel_4);
		
		username3 = new JTextField();
		GridBagConstraints gbc_username3 = new GridBagConstraints();
		gbc_username3.anchor = GridBagConstraints.WEST;
		gbc_username3.insets = new Insets(0, 0, 5, 0);
		gbc_username3.gridx = 2;
		gbc_username3.gridy = 5;
		add(username3, gbc_username3);
		username3.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel(PropretiesReader.getString("player4")+":"); //$NON-NLS-1$
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 24));
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3.gridx = 0;
		gbc_lblNewLabel_3.gridy = 6;
		add(lblNewLabel_3, gbc_lblNewLabel_3);
		
		username4 = new JTextField();
		GridBagConstraints gbc_username4 = new GridBagConstraints();
		gbc_username4.anchor = GridBagConstraints.WEST;
		gbc_username4.insets = new Insets(0, 0, 5, 0);
		gbc_username4.gridx = 2;
		gbc_username4.gridy = 6;
		add(username4, gbc_username4);
		username4.setColumns(10);
		
		JButton playButton = new JButton(PropretiesReader.getString("play"));
		playButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				usernames.add(username1.getText());
		        usernames.add(username2.getText());
		        usernames.add(username3.getText());
		        usernames.add(username4.getText());
		        game.initialize(comboBoxPlayerNumber.getSelectedIndex(), usernames);
		        ViewManager.navigateTo("GameScreen", game);
			}
		});
		
		JLabel lblNewLabel_5 = new JLabel(PropretiesReader.getString("enterUsername")); //$NON-NLS-1$
		GridBagConstraints gbc_lblNewLabel_5 = new GridBagConstraints();
		gbc_lblNewLabel_5.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_5.gridx = 2;
		gbc_lblNewLabel_5.gridy = 7;
		add(lblNewLabel_5, gbc_lblNewLabel_5);
		playButton.setFont(new Font("Tahoma", Font.PLAIN, 24));
		GridBagConstraints gbc_playButton = new GridBagConstraints();
		gbc_playButton.insets = new Insets(0, 0, 0, 5);
		gbc_playButton.gridx = 0;
		gbc_playButton.gridy = 8;
		add(playButton, gbc_playButton);

	}

}
