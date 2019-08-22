package com.emerchant.admin;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Graphics2D;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.websystique.spring.model.Product;
import com.websystique.spring.model.Merchant;

import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.GridLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DropMode;
import java.awt.Color;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTabbedPane;

@SuppressWarnings("serial")
public class ProductMgmView extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField1;
	private JTextField textField2;
	private JTextField imgPath;
	public String typeOperation;
	public static List<Product> Productlist;
	public static Product Product;
	public static List<Merchant> merchantlist;
	public static Merchant merchant;
	public static String serverUrl;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ProductMgmView dialog = new ProductMgmView();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static String getServerUrl() {
		try (InputStream input = new FileInputStream("emerchant.properties")) {

			Properties prop = new Properties();

			// load a properties file
			prop.load(input);

			// get the property value and print it out
			return prop.getProperty("server.url");

		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		}

	}

	private static BufferedImage resizeImage(BufferedImage originalImage, int type) {
		BufferedImage resizedImage = new BufferedImage(150, 150, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, 150, 150, null);
		g.dispose();

		return resizedImage;
	}

	private static byte[] readImageFromPath(String path) {
		BufferedImage bImage = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		File fichier;
		byte[] imageInByte = null;
		fichier = new File(path);
		try {
			bImage = ImageIO.read(fichier);
			ImageIO.write(bImage, "jpg", baos);
			imageInByte = baos.toByteArray();

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return imageInByte;
	}

	private static void displayImage(JLabel label, byte[] imageInByte) {
		if (imageInByte != null) {
			InputStream in = new ByteArrayInputStream(imageInByte);
			BufferedImage bImage;
			try {
				bImage = ImageIO.read(in);
				int type = bImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : bImage.getType();
				BufferedImage resizedImage = resizeImage(bImage, type);

				ImageIcon img = new ImageIcon(resizedImage);
				label.setIcon(img);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	@SuppressWarnings("unused")
	private static void postForm(Merchant o, String webservice, JPanel contentPanel) {
		String jsonString = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			jsonString = mapper.writeValueAsString(o);
		} catch (JsonProcessingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Client client = Client.create();
		WebResource webResource = client.resource(webservice);
		try {
			ClientResponse response = webResource.type("application/json").post(ClientResponse.class, jsonString);
			if (response.getStatus() != 204) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}
			JOptionPane.showMessageDialog(contentPanel, "Donnée sauvegardée");
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(contentPanel, "Connexion impossible");
		}

	}

	private static void putForm(Object o, String webservice, JPanel contentPanel) {
		String jsonString = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			jsonString = mapper.writeValueAsString(o);
		} catch (JsonProcessingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Client client = Client.create();
		WebResource webResource = client.resource(webservice);
		try {
			ClientResponse response = webResource.type("application/json").put(ClientResponse.class, jsonString);
			if (response.getStatus() != 204) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}
			JOptionPane.showMessageDialog(contentPanel, "Donnée sauvegardée");
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(contentPanel, "Connexion impossible");
		}

	}

	private static void deleteForm(Product o, String webservice, JPanel contentPanel) {
		String jsonString = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			jsonString = mapper.writeValueAsString(o);
		} catch (JsonProcessingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Client client = Client.create();
		WebResource webResource = client.resource(webservice);
		try {
			ClientResponse response = webResource.type("application/json").delete(ClientResponse.class, jsonString);
			if (response.getStatus() != 204) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}
			JOptionPane.showMessageDialog(contentPanel, "Donnée supprimée");
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(contentPanel, "Connexion impossible");
		}

	}

	private static List<Merchant> getMerchantList(JPanel contentPanel) {
		String jsonString = null;
		ObjectMapper mapper = new ObjectMapper();
		ClientResponse response = null;

		Client client = Client.create();
		WebResource webResource = client.resource(serverUrl + "/merchant/getall");
		try {
			response = webResource.type("application/json").get(ClientResponse.class);
			jsonString = response.getEntity(String.class);
			List<Merchant> merchantlist = Arrays.asList(mapper.readValue(jsonString, Merchant[].class));
			return merchantlist;

		} catch (Exception e2) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(contentPanel, "Connexion impossible");
			return null;
		}
	}

	private static DefaultTableModel getProductTable(JPanel contentPanel, List<Product> ProductList) {

		String[] columnNames = { "Id", "Label 1", "Label 1", "Description 1", "Description 2" };
		DefaultTableModel model = new DefaultTableModel(columnNames, 0);

		for (Product Product : ProductList) {
			Vector<String> row = new Vector<String>();
			row.add("" + Product.getId());
			row.add(Product.getLabel1());
			row.add(Product.getLabel2());
			row.add(Product.getDesc1());
			row.add(Product.getDesc2());
			model.addRow(row);
		}
		return model;
	}

	public void enableComponents(Container container, boolean enable) {
		Component[] components = container.getComponents();
		for (Component component : components) {
			component.setEnabled(enable);
			if (component instanceof Container) {
				enableComponents((Container) component, enable);
			}
		}
	}

	@SuppressWarnings("rawtypes")
	public void clearTextComponents(Container container) {
		Component[] components = container.getComponents();
		for (Component component : components) {
			if (component instanceof JTextField) {
				((JTextField) component).setText("");
			}
			if (component instanceof JTextArea) {
				((JTextArea) component).setText("");
			}

			if (component instanceof JComboBox) {
				((JComboBox) component).setSelectedItem(null);
			}
		}
	}

	public void clearImage(JLabel label) {
		label.setIcon(null);

	}

	public Product findProductById(int id, List<Product> Products) {

		for (Product Product : Products) {
			if (Product.getId() == id) {
				return Product;
			}
		}
		return null;
	}

	public Merchant findMerchantByProductId(int id, List<Merchant> merchants) {

		for (Merchant merchant : merchants) {
			for (Product Product : merchant.getProductlist()) {
				if (Product.getId() == id) {
					return merchant;
				}
			}
		}
		return null;
	}

	/**
	 * Create the dialog.
	 */
	public ProductMgmView() {
		serverUrl = getServerUrl();
		System.out.println(serverUrl);
		setType(Type.POPUP);
		setBounds(100, 100, 885, 695);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
		}

		JPanel panel_imgdisplay = new JPanel();
		panel_imgdisplay.setBounds(674, 160, 163, 162);
		contentPanel.add(panel_imgdisplay);
		GridBagLayout gbl_panel_imgdisplay = new GridBagLayout();
		gbl_panel_imgdisplay.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel_imgdisplay.rowHeights = new int[] { 0, 0, 0, 0, 0, 0 };
		gbl_panel_imgdisplay.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_panel_imgdisplay.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel_imgdisplay.setLayout(gbl_panel_imgdisplay);
		JPanel panel_2 = new JPanel();
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.insets = new Insets(0, 0, 5, 5);
		gbc_panel_2.gridx = 3;
		gbc_panel_2.gridy = 0;
		panel_imgdisplay.add(panel_2, gbc_panel_2);

		JLabel imgLabel = new JLabel("");
		panel_2.add(imgLabel);

		JPanel panel_control = new JPanel();
		panel_control.setBounds(33, 11, 641, 40);
		contentPanel.add(panel_control);
		GridBagLayout gbl_panel_control = new GridBagLayout();
		gbl_panel_control.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel_control.rowHeights = new int[] { 0, 0 };
		gbl_panel_control.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel_control.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panel_control.setLayout(gbl_panel_control);

		JButton btnValider = new JButton("valider");

		btnValider.setEnabled(false);
		btnValider.setIcon(new ImageIcon(ProductMgmView.class
				.getResource("/META-INF/resources/webjars/open-icon-library/0.11/png/24x24/status/dialog-clean.png")));
		GridBagConstraints gbc_btnValider = new GridBagConstraints();
		gbc_btnValider.insets = new Insets(0, 0, 0, 5);
		gbc_btnValider.gridx = 0;
		gbc_btnValider.gridy = 0;
		panel_control.add(btnValider, gbc_btnValider);

		JButton btnSortir = new JButton("Sortir");

		btnSortir.setEnabled(false);
		btnSortir.setIcon(new ImageIcon(ProductMgmView.class.getResource(
				"/META-INF/resources/webjars/open-icon-library/0.11/png/24x24/actions/application-exit-3.png")));
		GridBagConstraints gbc_btnSortir = new GridBagConstraints();
		gbc_btnSortir.fill = GridBagConstraints.VERTICAL;
		gbc_btnSortir.insets = new Insets(0, 0, 0, 5);
		gbc_btnSortir.gridx = 1;
		gbc_btnSortir.gridy = 0;
		panel_control.add(btnSortir, gbc_btnSortir);

		JButton btnUpdate = new JButton("Update");
		btnUpdate.setIcon(new ImageIcon(ProductMgmView.class.getResource(
				"/META-INF/resources/webjars/open-icon-library/0.11/png/24x24/apps/accessories-text-editor-5.png")));
		btnUpdate.setEnabled(false);
		GridBagConstraints gbc_btnUpdate = new GridBagConstraints();
		gbc_btnUpdate.insets = new Insets(0, 0, 0, 5);
		gbc_btnUpdate.gridx = 2;
		gbc_btnUpdate.gridy = 0;
		panel_control.add(btnUpdate, gbc_btnUpdate);

		JButton btnDelete = new JButton("Delete");
		btnDelete.setIcon(new ImageIcon(ProductMgmView.class.getResource(
				"/META-INF/resources/webjars/open-icon-library/0.11/png/24x24/actions/dialog-cancel-7.png")));
		btnDelete.setEnabled(false);
		GridBagConstraints gbc_btnDelete = new GridBagConstraints();
		gbc_btnDelete.anchor = GridBagConstraints.EAST;
		gbc_btnDelete.gridwidth = 2;
		gbc_btnDelete.insets = new Insets(0, 0, 0, 5);
		gbc_btnDelete.gridx = 3;
		gbc_btnDelete.gridy = 0;
		panel_control.add(btnDelete, gbc_btnDelete);

		JButton btnAjouter = new JButton("Add");
		GridBagConstraints gbc_btnAjouter = new GridBagConstraints();
		gbc_btnAjouter.insets = new Insets(0, 0, 0, 5);
		gbc_btnAjouter.gridx = 5;
		gbc_btnAjouter.gridy = 0;
		panel_control.add(btnAjouter, gbc_btnAjouter);
		btnAjouter.setIcon(new ImageIcon(ProductMgmView.class
				.getResource("/META-INF/resources/webjars/open-icon-library/0.11/png/24x24/actions/edit-add.png")));

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(33, 76, 598, 31);
		contentPanel.add(panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel_1.rowHeights = new int[] { 0, 0 };
		gbl_panel_1.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_panel_1.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panel_1.setLayout(gbl_panel_1);

		JLabel lblCommercant = new JLabel("Commercant");
		GridBagConstraints gbc_lblCommercant = new GridBagConstraints();
		gbc_lblCommercant.insets = new Insets(0, 0, 0, 5);
		gbc_lblCommercant.gridx = 1;
		gbc_lblCommercant.gridy = 0;
		panel_1.add(lblCommercant, gbc_lblCommercant);
		lblCommercant.setEnabled(false);

		merchantlist = getMerchantList(contentPanel);
		JComboBox<Object> comboBox = new JComboBox<>(merchantlist.toArray());
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 5;
		gbc_comboBox.gridy = 0;
		panel_1.add(comboBox, gbc_comboBox);

		merchant = (Merchant) comboBox.getSelectedItem();

		JPanel panel = new JPanel();
		panel.setBounds(33, 402, 619, 234);
		contentPanel.add(panel);
		panel.setLayout(new GridLayout(1, 0, 0, 0));

		JTable table = new JTable();

		table.setModel(getProductTable(contentPanel, merchant.getProductlist()));

		table.getColumnModel().getColumn(0).setPreferredWidth(102);
		table.getColumnModel().getColumn(1).setPreferredWidth(140);
		table.getColumnModel().getColumn(2).setPreferredWidth(146);
		table.getColumnModel().getColumn(3).setPreferredWidth(167);
		table.getColumnModel().getColumn(4).setPreferredWidth(168);
		panel.add(table);
		JScrollPane scrollPane = new JScrollPane(table);
		panel.add(scrollPane);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(33, 139, 598, 232);
		contentPanel.add(tabbedPane);

		JPanel panel_formulaire = new JPanel();
		tabbedPane.addTab("Informations", null, panel_formulaire, null);

		enableComponents(panel_formulaire, false);
		GridBagLayout gbl_panel_formulaire = new GridBagLayout();
		gbl_panel_formulaire.columnWidths = new int[] { 30, 148, 356, 0 };
		gbl_panel_formulaire.rowHeights = new int[] { 30, 20, 35, 22, 22, 20, 25, 0 };
		gbl_panel_formulaire.columnWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel_formulaire.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel_formulaire.setLayout(gbl_panel_formulaire);

		JLabel libelle1 = new JLabel("Libelle de la marque (FR)");
		libelle1.setEnabled(false);
		GridBagConstraints gbc_libelle1 = new GridBagConstraints();
		gbc_libelle1.anchor = GridBagConstraints.WEST;
		gbc_libelle1.insets = new Insets(0, 0, 5, 5);
		gbc_libelle1.gridx = 1;
		gbc_libelle1.gridy = 1;
		panel_formulaire.add(libelle1, gbc_libelle1);
		libelle1.setLabelFor(textField1);

		textField1 = new JTextField();
		GridBagConstraints gbc_textField1 = new GridBagConstraints();
		gbc_textField1.fill = GridBagConstraints.BOTH;
		gbc_textField1.insets = new Insets(0, 0, 5, 0);
		gbc_textField1.gridx = 2;
		gbc_textField1.gridy = 1;
		panel_formulaire.add(textField1, gbc_textField1);
		textField1.setColumns(35);

		JLabel libelle2 = new JLabel("Libelle de la marque (EN)");
		libelle2.setEnabled(false);
		GridBagConstraints gbc_libelle2 = new GridBagConstraints();
		gbc_libelle2.anchor = GridBagConstraints.WEST;
		gbc_libelle2.insets = new Insets(0, 0, 5, 5);
		gbc_libelle2.gridx = 1;
		gbc_libelle2.gridy = 2;
		panel_formulaire.add(libelle2, gbc_libelle2);
		libelle2.setLabelFor(textField2);

		textField2 = new JTextField();
		textField2.setForeground(Color.BLACK);
		GridBagConstraints gbc_textField2 = new GridBagConstraints();
		gbc_textField2.fill = GridBagConstraints.BOTH;
		gbc_textField2.insets = new Insets(0, 0, 5, 0);
		gbc_textField2.gridx = 2;
		gbc_textField2.gridy = 2;
		panel_formulaire.add(textField2, gbc_textField2);
		textField2.setColumns(10);

		JLabel libelle3 = new JLabel("Description de la marque (FR)  ");
		libelle3.setEnabled(false);
		GridBagConstraints gbc_libelle3 = new GridBagConstraints();
		gbc_libelle3.insets = new Insets(0, 0, 5, 5);
		gbc_libelle3.gridx = 1;
		gbc_libelle3.gridy = 3;
		panel_formulaire.add(libelle3, gbc_libelle3);

		JTextArea textField3 = new JTextArea();
		textField3.setRows(2);
		textField3.setDropMode(DropMode.INSERT);
		textField3.setLineWrap(true);
		textField3.setWrapStyleWord(true);
		GridBagConstraints gbc_textField3 = new GridBagConstraints();
		gbc_textField3.fill = GridBagConstraints.BOTH;
		gbc_textField3.insets = new Insets(0, 0, 5, 0);
		gbc_textField3.gridx = 2;
		gbc_textField3.gridy = 3;
		panel_formulaire.add(textField3, gbc_textField3);

		JLabel libelle4 = new JLabel("Description de la marque (EN)");
		libelle4.setEnabled(false);
		GridBagConstraints gbc_libelle4 = new GridBagConstraints();
		gbc_libelle4.anchor = GridBagConstraints.WEST;
		gbc_libelle4.insets = new Insets(0, 0, 5, 5);
		gbc_libelle4.gridx = 1;
		gbc_libelle4.gridy = 4;
		panel_formulaire.add(libelle4, gbc_libelle4);

		JTextArea textField4 = new JTextArea();
		textField4.setRows(2);
		textField4.setWrapStyleWord(true);
		textField4.setLineWrap(true);
		textField4.setBackground(Color.WHITE);
		textField4.setDropMode(DropMode.INSERT);
		GridBagConstraints gbc_textField4 = new GridBagConstraints();
		gbc_textField4.fill = GridBagConstraints.BOTH;
		gbc_textField4.insets = new Insets(0, 0, 5, 0);
		gbc_textField4.gridx = 2;
		gbc_textField4.gridy = 4;
		panel_formulaire.add(textField4, gbc_textField4);

		JLabel libelle5 = new JLabel("Logo de la marque");
		libelle5.setEnabled(false);
		GridBagConstraints gbc_libelle5 = new GridBagConstraints();
		gbc_libelle5.anchor = GridBagConstraints.WEST;
		gbc_libelle5.insets = new Insets(0, 0, 5, 5);
		gbc_libelle5.gridx = 1;
		gbc_libelle5.gridy = 5;
		panel_formulaire.add(libelle5, gbc_libelle5);
		libelle5.setLabelFor(imgPath);

		imgPath = new JTextField();
		imgPath.setEnabled(false);
		imgPath.setColumns(10);
		GridBagConstraints gbc_imgPath = new GridBagConstraints();
		gbc_imgPath.fill = GridBagConstraints.HORIZONTAL;
		gbc_imgPath.insets = new Insets(0, 0, 5, 0);
		gbc_imgPath.gridx = 2;
		gbc_imgPath.gridy = 5;
		panel_formulaire.add(imgPath, gbc_imgPath);

		JButton loadImage = new JButton("Load");
		loadImage.setIcon(new ImageIcon(ProductMgmView.class.getResource(
				"/META-INF/resources/webjars/open-icon-library/0.11/png/16x16/places/oxygen-style/folder.png")));
		GridBagConstraints gbc_loadImage = new GridBagConstraints();
		gbc_loadImage.anchor = GridBagConstraints.EAST;
		gbc_loadImage.gridx = 2;
		gbc_loadImage.gridy = 6;
		panel_formulaire.add(loadImage, gbc_loadImage);

		loadImage.setVisible(false);

		// Lorsqu'on click sur le FileChooser pour selectionner une photo
		loadImage.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				FileFilter imagesFilter = new FileNameExtensionFilter("Images", "bmp", "gif", "jpg", "jpeg", "png");
				JFileChooser dialogue = new JFileChooser();
				dialogue.addChoosableFileFilter(imagesFilter);
				dialogue.setAcceptAllFileFilterUsed(false);
				File fichier;
				BufferedImage bImage = null;

				if (dialogue.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					fichier = dialogue.getSelectedFile();
					try {
						bImage = ImageIO.read(fichier);
						int type = bImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : bImage.getType();
						BufferedImage resizedImage = resizeImage(bImage, type);

						ImageIcon img = new ImageIcon(resizedImage);
						imgLabel.setIcon(img);
						imgPath.setText(fichier.getPath());

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}
		});
		enableComponents(tabbedPane, false);

		// Lorsqu'on selectionne un element dans le ComboBox
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				merchant = (Merchant) comboBox.getSelectedItem();
				table.setModel(getProductTable(contentPanel, merchant.getProductlist()));
				clearTextComponents(panel_formulaire);
			}
		});

		// Lorsqu'on click sur un enregitrement du tableau
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int row = table.rowAtPoint(arg0.getPoint());
				int s = Integer.parseInt(table.getModel().getValueAt(row, 0) + "");
				Product = findProductById(s, merchant.getProductlist());
				textField1.setText(Product.getLabel1());
				textField2.setText(Product.getLabel2());
				textField3.setText(Product.getDesc1());
				textField4.setText(Product.getDesc2());
				if (Product.getImg() != null) {
					imgLabel.setVisible(true);
					displayImage(imgLabel, Product.getImg());
				} else {
					imgLabel.setVisible(false);

				}
				;
				btnUpdate.setEnabled(true);
				btnDelete.setEnabled(true);
				panel_imgdisplay.setVisible(true);
			}
		});

		// Lorsqu'on click sur le bouton "Ajouter"
		btnAjouter.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnValider.setEnabled(true);
				btnSortir.setEnabled(true);
				enableComponents(panel_formulaire, true);
				loadImage.setEnabled(true);
				loadImage.setVisible(true);
				panel_imgdisplay.setVisible(true);
				clearTextComponents(panel_formulaire);
				clearImage(imgLabel);
				btnUpdate.setEnabled(false);
				btnDelete.setEnabled(false);
				table.setVisible(false);
				comboBox.setEnabled(true);
				typeOperation = "Create";
			}
		});

		// Lorsqu'on click sur le bouton "Valider"
		btnValider.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				Merchant merchant;
				String webservice;

				switch (typeOperation) {
				case "Create":
					Product = new Product();
					Product.setLabel1(textField1.getText());
					Product.setLabel2(textField2.getText());
					Product.setDesc1(textField3.getText());
					Product.setDesc2(textField4.getText());
					if (imgPath.getText().length() != 0) {
						Product.setImg(readImageFromPath(imgPath.getText()));
					}

					merchant = (Merchant) comboBox.getSelectedItem();
					merchant.add(Product);
					webservice = serverUrl + "/merchant";
					putForm(merchant, webservice, contentPanel);
					table.setModel(getProductTable(contentPanel, merchant.getProductlist()));
					btnValider.setEnabled(false);
					btnSortir.setEnabled(false);
					enableComponents(panel_formulaire, false);
					loadImage.setEnabled(false);
					loadImage.setVisible(false);
					panel_imgdisplay.setVisible(true);
					imgLabel.setIcon(null);
					clearTextComponents(panel_formulaire);
					table.setVisible(true);
					merchantlist = getMerchantList(contentPanel);
					comboBox.setModel(new DefaultComboBoxModel<Object>(merchantlist.toArray()));
					merchant = (Merchant) comboBox.getSelectedItem();
					table.setModel(getProductTable(contentPanel, merchant.getProductlist()));
					break;

				case "Update":
					Product.setLabel1(textField1.getText());
					Product.setLabel2(textField2.getText());
					Product.setDesc1(textField3.getText());
					Product.setDesc2(textField4.getText());
					if (imgPath.getText().length() != 0) {
						Product.setImg(readImageFromPath(imgPath.getText()));
					}

					webservice = serverUrl + "/product";
					putForm(Product, webservice, contentPanel);
					btnValider.setEnabled(false);
					btnSortir.setEnabled(false);
					enableComponents(panel_formulaire, false);
					loadImage.setEnabled(false);
					loadImage.setVisible(false);
					panel_imgdisplay.setVisible(true);
					imgLabel.setIcon(null);
					clearTextComponents(panel_formulaire);
					table.setVisible(true);
					merchantlist = getMerchantList(contentPanel);
					comboBox.setModel(new DefaultComboBoxModel<Object>(merchantlist.toArray()));
					merchant = (Merchant) comboBox.getSelectedItem();
					table.setModel(getProductTable(contentPanel, merchant.getProductlist()));

					break;

				default:
					// code block
				}
			}
		});

		// Lorsqu'on click sur le bouton "Update"
		btnUpdate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnValider.setEnabled(true);
				btnSortir.setEnabled(true);
				enableComponents(panel_formulaire, true);
				loadImage.setEnabled(true);
				loadImage.setVisible(true);
				panel_imgdisplay.setVisible(true);
				btnUpdate.setEnabled(false);
				btnDelete.setEnabled(false);
				table.setVisible(false);
				comboBox.setEnabled(true);
				typeOperation = "Update";
			}
		});

		// Lorsqu'on click sur le bouton "sortir"
		btnSortir.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnValider.setEnabled(false);
				btnSortir.setEnabled(false);
				enableComponents(panel_formulaire, false);
				loadImage.setEnabled(false);
				loadImage.setVisible(false);
				panel_imgdisplay.setVisible(false);
				imgLabel.setIcon(null);
				clearTextComponents(panel_formulaire);
				clearImage(imgLabel);
				btnUpdate.setEnabled(false);
				btnDelete.setEnabled(false);
				table.setVisible(true);
				comboBox.setEnabled(true);

			}
		});

		// Lorsqu'on click sur le bouton "delete"
		btnDelete.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String webservice = serverUrl + "/product";
				deleteForm(Product, webservice, contentPanel);
				imgLabel.setIcon(null);
				clearTextComponents(panel_formulaire);
				btnUpdate.setEnabled(false);
				btnDelete.setEnabled(false);
				merchantlist = getMerchantList(contentPanel);
				comboBox.setModel(new DefaultComboBoxModel<Object>(merchantlist.toArray()));
				merchant = (Merchant) comboBox.getSelectedItem();
				table.setModel(getProductTable(contentPanel, merchant.getProductlist()));
			}
		});

	}
}
