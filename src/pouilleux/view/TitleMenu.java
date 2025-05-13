package pouilleux.view;

import pouilleux.controller.*;
import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.GridLayout;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;
import javax.swing.UIManager;

public class TitleMenu extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public TitleMenu() {
		setBackground(SystemColor.window);
		setForeground(Color.BLACK);
		setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel title = new JLabel(PropretiesReader.getString("pouilleux"));
		title.setBackground(new Color(0, 0, 0));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setFont(new Font("Tahoma", Font.BOLD, 56));
		add(title);
		
		JButton newGameButton = new JButton(PropretiesReader.getString("newGame"));
		newGameButton.setBackground(UIManager.getColor("Button.background"));
		newGameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ViewManager.navigateTo("NewGameMenu");
			}
		});
		newGameButton.setFont(new Font("Tahoma", Font.PLAIN, 24));
		add(newGameButton);
		
		JButton loadGameButton = new JButton(PropretiesReader.getString("load"));
		loadGameButton.setBackground(UIManager.getColor("Button.background"));
		loadGameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ViewManager.navigateTo("LoadMenu");
			}
		});
		loadGameButton.setFont(new Font("Tahoma", Font.PLAIN, 24));
		add(loadGameButton);
		
		JButton settingsButton = new JButton(PropretiesReader.getString("settings"));
		settingsButton.setBackground(UIManager.getColor("Button.background"));
		settingsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ViewManager.navigateTo("SettingMenu");
			}
		});
		settingsButton.setFont(new Font("Tahoma", Font.PLAIN, 24));
		add(settingsButton);
		
		JButton quitButton = new JButton(PropretiesReader.getString("quit"));
		quitButton.setBackground(UIManager.getColor("Button.background"));
		quitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		quitButton.setFont(new Font("Tahoma", Font.PLAIN, 24));
		add(quitButton);

	}

}
