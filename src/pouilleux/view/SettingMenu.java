package pouilleux.view;

import pouilleux.controller.*;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import java.awt.SystemColor;
import javax.swing.UIManager;

public class SettingMenu extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public SettingMenu() {
		setBackground(SystemColor.window);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {80, 250, 211, 0};
		gridBagLayout.rowHeights = new int[] {30, 29, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JButton titleMenuButton = new JButton(PropretiesReader.getString("back"));
		titleMenuButton.setBackground(UIManager.getColor("Button.background"));
		titleMenuButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ViewManager.navigateTo("TitleScreen");
			}
		});
		GridBagConstraints gbc_titleMenuButton = new GridBagConstraints();
		gbc_titleMenuButton.anchor = GridBagConstraints.WEST;
		gbc_titleMenuButton.insets = new Insets(0, 0, 5, 5);
		gbc_titleMenuButton.gridx = 0;
		gbc_titleMenuButton.gridy = 0;
		add(titleMenuButton, gbc_titleMenuButton);
		
		JLabel SettingMenuTitle = new JLabel(PropretiesReader.getString("settings")+":");
		SettingMenuTitle.setHorizontalAlignment(SwingConstants.CENTER);
		SettingMenuTitle.setFont(new Font("Tahoma", Font.BOLD, 36));
		GridBagConstraints gbc_SettingMenuTitle = new GridBagConstraints();
		gbc_SettingMenuTitle.anchor = GridBagConstraints.SOUTHWEST;
		gbc_SettingMenuTitle.insets = new Insets(0, 0, 5, 5);
		gbc_SettingMenuTitle.gridx = 1;
		gbc_SettingMenuTitle.gridy = 0;
		add(SettingMenuTitle, gbc_SettingMenuTitle);
		
		JLabel languageLabel = new JLabel(PropretiesReader.getString("language")+":");
		languageLabel.setFont(new Font("Tahoma", Font.PLAIN, 24));
		GridBagConstraints gbc_languageLabel = new GridBagConstraints();
		gbc_languageLabel.insets = new Insets(0, 0, 5, 5);
		gbc_languageLabel.gridx = 0;
		gbc_languageLabel.gridy = 2;
		add(languageLabel, gbc_languageLabel);
		
		JComboBox comboBoxLanguage = new JComboBox(new String[] {PropretiesReader.getString("english"), PropretiesReader.getString("french"), PropretiesReader.getString("german")});
		comboBoxLanguage.setFont(new Font("Tahoma", Font.PLAIN, 24));
		comboBoxLanguage.setMaximumRowCount(3);
		GridBagConstraints gbc_comboBoxLanguage = new GridBagConstraints();
		gbc_comboBoxLanguage.anchor = GridBagConstraints.WEST;
		gbc_comboBoxLanguage.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxLanguage.gridx = 1;
		gbc_comboBoxLanguage.gridy = 2;
		add(comboBoxLanguage, gbc_comboBoxLanguage);
		
		JLabel displayLabel = new JLabel(PropretiesReader.getString("display")+":");
		displayLabel.setFont(new Font("Tahoma", Font.PLAIN, 24));
		GridBagConstraints gbc_displayLabel = new GridBagConstraints();
		gbc_displayLabel.insets = new Insets(0, 0, 5, 5);
		gbc_displayLabel.gridx = 0;
		gbc_displayLabel.gridy = 3;
		add(displayLabel, gbc_displayLabel);
		
		JComboBox comboBoxDisplay = new JComboBox(new String[] {PropretiesReader.getString("windowed"), PropretiesReader.getString("fullscreen")});
		comboBoxDisplay.setFont(new Font("Tahoma", Font.PLAIN, 24));
		comboBoxDisplay.setMaximumRowCount(2);
		GridBagConstraints gbc_comboBoxDisplay = new GridBagConstraints();
		gbc_comboBoxDisplay.anchor = GridBagConstraints.WEST;
		gbc_comboBoxDisplay.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxDisplay.gridx = 1;
		gbc_comboBoxDisplay.gridy = 3;
		add(comboBoxDisplay, gbc_comboBoxDisplay);
		
		JButton applyButton = new JButton(PropretiesReader.getString("apply"));
		applyButton.setBackground(UIManager.getColor("Button.background"));
		applyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Settings.updateSettings(comboBoxLanguage.getSelectedIndex(), comboBoxDisplay.getSelectedIndex());
					ViewManager.navigateTo("TitleScreen");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		applyButton.setFont(new Font("Tahoma", Font.PLAIN, 24));
		GridBagConstraints gbc_applyButton = new GridBagConstraints();
		gbc_applyButton.insets = new Insets(0, 0, 5, 5);
		gbc_applyButton.gridx = 0;
		gbc_applyButton.gridy = 4;
		add(applyButton, gbc_applyButton);
		
		JLabel lblNewLabel = new JLabel(PropretiesReader.getString("mustReOn"));
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.NORTH;
		gbc_lblNewLabel.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 5;
		add(lblNewLabel, gbc_lblNewLabel);

	}

}
