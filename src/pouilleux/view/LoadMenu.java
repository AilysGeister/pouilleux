package pouilleux.view;

import pouilleux.controller.Game;
import pouilleux.controller.PropretiesReader;
import pouilleux.controller.ViewManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class LoadMenu extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public LoadMenu(Game game) {
		setLayout(new BorderLayout());

        DefaultListModel<String> fileListModel = new DefaultListModel<>();
        JList<String> fileList = new JList<>(fileListModel);
        fileList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(fileList);
        add(scrollPane, BorderLayout.CENTER);
        
        JPanel panel = new JPanel();
        scrollPane.setColumnHeaderView(panel);
        GridBagLayout gbl_panel = new GridBagLayout();
        gbl_panel.columnWidths = new int[]{58, 77, 250, 0};
        gbl_panel.rowHeights = new int[]{44, 0};
        gbl_panel.columnWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
        gbl_panel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
        panel.setLayout(gbl_panel);
        
        JButton backButton = new JButton(PropretiesReader.getString("back"));
        backButton.setFont(new Font("Tahoma", Font.BOLD, 18));
        backButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		ViewManager.navigateTo("TitleScreen");
        	}
        });
        GridBagConstraints gbc_backButton = new GridBagConstraints();
        gbc_backButton.anchor = GridBagConstraints.WEST;
        gbc_backButton.insets = new Insets(0, 0, 0, 5);
        gbc_backButton.gridx = 0;
        gbc_backButton.gridy = 0;
        panel.add(backButton, gbc_backButton);
        
        JLabel lblNewLabel = new JLabel(PropretiesReader.getString("selectSave"));
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 36));
        GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
        gbc_lblNewLabel.anchor = GridBagConstraints.NORTH;
        gbc_lblNewLabel.gridx = 2;
        gbc_lblNewLabel.gridy = 0;
        panel.add(lblNewLabel, gbc_lblNewLabel);

        File savesDir = new File("saves");
        if (savesDir.exists() && savesDir.isDirectory()) {
            File[] files = savesDir.listFiles((dir, name) -> name.endsWith(".csv"));
            if (files != null) {
                for (File file : files) {
                    fileListModel.addElement(file.getName());
                }
            }
        }

        JButton loadButton = new JButton(PropretiesReader.getString("load"));
        loadButton.setFont(new Font("Tahoma", Font.BOLD, 36));
        loadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedFile = fileList.getSelectedValue();
                if (selectedFile != null) {
                    String path = "saves/" + selectedFile;
                    try {
                        Game loadedGame = new Game(path);
                        ViewManager.navigateTo("GameScreen", loadedGame);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(LoadMenu.this, PropretiesReader.getString("loadFail") + ex.getMessage(), PropretiesReader.getString("error"), JOptionPane.ERROR_MESSAGE);
                        ex.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(LoadMenu.this, PropretiesReader.getString("selectedFile"), PropretiesReader.getString("warning"),JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        add(loadButton, BorderLayout.SOUTH);
    }
}
