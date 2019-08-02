package com.emerchant.admin;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.Label;
import java.awt.GridBagLayout;
import java.awt.Button;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class MenuView {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuView window = new MenuView();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MenuView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 900, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnFichier = new JMenu("Fichier");
		menuBar.add(mnFichier);
		
		JMenu mnEdition = new JMenu("Edition");
		menuBar.add(mnEdition);
		
		JMenu mnRaccourci = new JMenu("Raccourci");
		menuBar.add(mnRaccourci);
		
		JMenu mnSupport = new JMenu("Support");
		menuBar.add(mnSupport);
		frame.getContentPane().setLayout(new GridLayout(1, 0, 0, 0));
		
		Panel panel = new Panel();
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		Label label = new Label("New label");
		label.setBounds(143, 36, 62, 22);
		panel.add(label);
		
		Panel panel_1 = new Panel();
		panel_1.setBounds(39, 76, 295, 82);
		panel.add(panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{0, 0, 0, 0};
		gbl_panel_1.rowHeights = new int[]{0, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		Label label_1 = new Label("Gestion des marques \r\nde produits");
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.insets = new Insets(0, 0, 0, 5);
		gbc_label_1.gridx = 0;
		gbc_label_1.gridy = 0;
		panel_1.add(label_1, gbc_label_1);
		
		Button button = new Button("New button");
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				BrandMgmView brandMgmView= new BrandMgmView();
				brandMgmView.setVisible(true);
			}
		});
		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.gridx = 2;
		gbc_button.gridy = 0;
		panel_1.add(button, gbc_button);
	}
}
