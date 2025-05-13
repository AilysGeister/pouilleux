package pouilleux.view;

import javax.swing.JPanel;
import pouilleux.model.*;
import pouilleux.controller.PropretiesReader;
import pouilleux.controller.ViewManager;

import javax.swing.JButton;
import javax.swing.JToolBar;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class EndGame extends JPanel {

	
	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public EndGame(Player loser) {
		setLayout(new BorderLayout(0, 0));
		
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		toolBar.setBackground(new Color(218, 165, 32));
		add(toolBar, BorderLayout.NORTH);
		
		JButton titleScreenButton = new JButton(PropretiesReader.getString("titleScreen"));
		titleScreenButton.setBackground(new Color(184, 140, 27));
		titleScreenButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		titleScreenButton.setToolTipText(PropretiesReader.getString("warningUnsave"));
		titleScreenButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ViewManager.navigateTo("TitleScreen");
			}
		});
		toolBar.add(titleScreenButton);
		
		JButton quitButton = new JButton(PropretiesReader.getString("quit"));
		quitButton.setToolTipText(PropretiesReader.getString("warningUnsave"));
		quitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		quitButton.setBackground(new Color(184, 140, 27));
		quitButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		toolBar.add(quitButton);
		
		JPanel panel = new ImagePanel("resources/images/board.png");
		add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] {30, 30, 64};
		gbl_panel.rowHeights = new int[] {10, 30, 64};
		gbl_panel.columnWeights = new double[]{0.0, 1.0, 1.0};
		gbl_panel.rowWeights = new double[]{0.0, 1.0, 0.0};
		panel.setLayout(gbl_panel);
		
		JLabel labelLooser = new JLabel(PropretiesReader.getString("loser")); //$NON-NLS-1$
		labelLooser.setFont(new Font("Tahoma", Font.BOLD, 50));
		GridBagConstraints gbc_labelLooser = new GridBagConstraints();
		gbc_labelLooser.insets = new Insets(0, 0, 5, 5);
		gbc_labelLooser.gridx = 1;
		gbc_labelLooser.gridy = 0;
		panel.add(labelLooser, gbc_labelLooser);
		
		JLabel loserUsername = new JLabel(loser.getUsername()); //$NON-NLS-1$
		loserUsername.setFont(new Font("Tahoma", Font.BOLD, 46));
		GridBagConstraints gbc_loserUsername = new GridBagConstraints();
		gbc_loserUsername.insets = new Insets(0, 0, 5, 5);
		gbc_loserUsername.gridx = 1;
		gbc_loserUsername.gridy = 1;
		panel.add(loserUsername, gbc_loserUsername);
		
		JPanel loserImage = new ImagePanel("resources/images/player"+(loser.getIdentifier()+1)+".png");
		GridBagConstraints gbc_loserImage = new GridBagConstraints();
		gbc_loserImage.fill = GridBagConstraints.BOTH;
		gbc_loserImage.insets = new Insets(0, 0, 5, 0);
		gbc_loserImage.gridx = 2;
		gbc_loserImage.gridy = 1;
		panel.add(loserImage, gbc_loserImage );
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(218, 165, 32));
		add(panel_1, BorderLayout.SOUTH);
		
	}

}
