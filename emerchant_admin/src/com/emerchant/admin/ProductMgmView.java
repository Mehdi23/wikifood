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

import com.emerchant.library.DateLabelFormatter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.websystique.spring.model.Product;
import com.websystique.spring.model.ProductType;
import com.websystique.spring.model.Brand;
import com.websystique.spring.model.Category;
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
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import java.awt.GridLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DropMode;
import java.awt.Color;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTabbedPane;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;

@SuppressWarnings("serial")
public class ProductMgmView extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField label1;
	private JTextField label2;
	private JTextField imgPath;
	public String typeOperation;
	public static List<Product> Productlist;
	public static Product Product;
	public static List<Merchant> merchantlist;
	public static Merchant merchant;
	public static List<Brand> brandlist;
	public static Brand brand;
	public static List<Category> categorylist;
	public static Category category;
	public static List<ProductType> productTypelist;
	public static ProductType ProductType;

	public static String serverUrl;
	private JTextField price;
	private JTextField promo_price;
	private JTextField cab;

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

	private static void postForm(Object o, String webservice, JPanel contentPanel) {
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
			System.out.println(jsonString);
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

		GridBagLayout gbl_panel_formulaire = new GridBagLayout();
		gbl_panel_formulaire.columnWidths = new int[] { 30, 148, 356, 0 };
		gbl_panel_formulaire.rowHeights = new int[] { 30, 20, 35, 22, 22, 20, 25, 0 };
		gbl_panel_formulaire.columnWeights = new double[] { 0.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_panel_formulaire.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel_formulaire.setLayout(gbl_panel_formulaire);

		JLabel lblCodeBarre = new JLabel("Code barre");
		lblCodeBarre.setEnabled(false);
		GridBagConstraints gbc_lblCodeBarre = new GridBagConstraints();
		gbc_lblCodeBarre.anchor = GridBagConstraints.WEST;
		gbc_lblCodeBarre.insets = new Insets(0, 0, 5, 5);
		gbc_lblCodeBarre.gridx = 1;
		gbc_lblCodeBarre.gridy = 0;
		panel_formulaire.add(lblCodeBarre, gbc_lblCodeBarre);

		cab = new JTextField();
		GridBagConstraints gbc_cab = new GridBagConstraints();
		gbc_cab.fill = GridBagConstraints.HORIZONTAL;
		gbc_cab.insets = new Insets(0, 0, 5, 0);
		gbc_cab.gridx = 2;
		gbc_cab.gridy = 0;
		panel_formulaire.add(cab, gbc_cab);
		cab.setColumns(10);

		JLabel libelle1 = new JLabel("Libelle de la marque (FR)");
		libelle1.setEnabled(false);
		GridBagConstraints gbc_libelle1 = new GridBagConstraints();
		gbc_libelle1.anchor = GridBagConstraints.WEST;
		gbc_libelle1.insets = new Insets(0, 0, 5, 5);
		gbc_libelle1.gridx = 1;
		gbc_libelle1.gridy = 1;
		panel_formulaire.add(libelle1, gbc_libelle1);
		libelle1.setLabelFor(label1);

		label1 = new JTextField();
		GridBagConstraints gbc_label1 = new GridBagConstraints();
		gbc_label1.fill = GridBagConstraints.BOTH;
		gbc_label1.insets = new Insets(0, 0, 5, 0);
		gbc_label1.gridx = 2;
		gbc_label1.gridy = 1;
		panel_formulaire.add(label1, gbc_label1);
		label1.setColumns(35);

		JLabel libelle2 = new JLabel("Libelle de la marque (EN)");
		libelle2.setEnabled(false);
		GridBagConstraints gbc_libelle2 = new GridBagConstraints();
		gbc_libelle2.anchor = GridBagConstraints.WEST;
		gbc_libelle2.insets = new Insets(0, 0, 5, 5);
		gbc_libelle2.gridx = 1;
		gbc_libelle2.gridy = 2;
		panel_formulaire.add(libelle2, gbc_libelle2);
		libelle2.setLabelFor(label2);

		label2 = new JTextField();
		label2.setForeground(Color.BLACK);
		GridBagConstraints gbc_label2 = new GridBagConstraints();
		gbc_label2.fill = GridBagConstraints.BOTH;
		gbc_label2.insets = new Insets(0, 0, 5, 0);
		gbc_label2.gridx = 2;
		gbc_label2.gridy = 2;
		panel_formulaire.add(label2, gbc_label2);
		label2.setColumns(10);

		JLabel libelle3 = new JLabel("Description de la marque (FR)  ");
		libelle3.setEnabled(false);
		GridBagConstraints gbc_libelle3 = new GridBagConstraints();
		gbc_libelle3.insets = new Insets(0, 0, 5, 5);
		gbc_libelle3.gridx = 1;
		gbc_libelle3.gridy = 3;
		panel_formulaire.add(libelle3, gbc_libelle3);

		JTextArea desc1 = new JTextArea();
		desc1.setRows(2);
		desc1.setDropMode(DropMode.INSERT);
		desc1.setLineWrap(true);
		desc1.setWrapStyleWord(true);
		GridBagConstraints gbc_desc1 = new GridBagConstraints();
		gbc_desc1.fill = GridBagConstraints.BOTH;
		gbc_desc1.insets = new Insets(0, 0, 5, 0);
		gbc_desc1.gridx = 2;
		gbc_desc1.gridy = 3;
		panel_formulaire.add(desc1, gbc_desc1);

		JLabel libelle4 = new JLabel("Description de la marque (EN)");
		libelle4.setEnabled(false);
		GridBagConstraints gbc_libelle4 = new GridBagConstraints();
		gbc_libelle4.anchor = GridBagConstraints.WEST;
		gbc_libelle4.insets = new Insets(0, 0, 5, 5);
		gbc_libelle4.gridx = 1;
		gbc_libelle4.gridy = 4;
		panel_formulaire.add(libelle4, gbc_libelle4);

		JTextArea desc2 = new JTextArea();
		desc2.setRows(2);
		desc2.setWrapStyleWord(true);
		desc2.setLineWrap(true);
		desc2.setBackground(Color.WHITE);
		desc2.setDropMode(DropMode.INSERT);
		GridBagConstraints gbc_desc2 = new GridBagConstraints();
		gbc_desc2.fill = GridBagConstraints.BOTH;
		gbc_desc2.insets = new Insets(0, 0, 5, 0);
		gbc_desc2.gridx = 2;
		gbc_desc2.gridy = 4;
		panel_formulaire.add(desc2, gbc_desc2);

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

		JPanel planel_typeproduit = new JPanel();
		tabbedPane.addTab("Classification", null, planel_typeproduit, null);
		GridBagLayout gbl_planel_typeproduit = new GridBagLayout();
		gbl_planel_typeproduit.columnWidths = new int[] { 0, 0, 0, 0, 0 };
		gbl_planel_typeproduit.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gbl_planel_typeproduit.columnWeights = new double[] { 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_planel_typeproduit.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		planel_typeproduit.setLayout(gbl_planel_typeproduit);

		JLabel lblMarqueDeProduit = new JLabel("Marque de produit");
		GridBagConstraints gbc_lblMarqueDeProduit = new GridBagConstraints();
		gbc_lblMarqueDeProduit.insets = new Insets(0, 0, 5, 5);
		gbc_lblMarqueDeProduit.gridx = 1;
		gbc_lblMarqueDeProduit.gridy = 1;
		planel_typeproduit.add(lblMarqueDeProduit, gbc_lblMarqueDeProduit);

		brandlist = merchant.getBrandlist();
		JComboBox<Object> comboBox_brand = new JComboBox<>(brandlist.toArray());

		GridBagConstraints gbc_comboBox_brand = new GridBagConstraints();
		gbc_comboBox_brand.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox_brand.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_brand.gridx = 3;
		gbc_comboBox_brand.gridy = 1;
		planel_typeproduit.add(comboBox_brand, gbc_comboBox_brand);

		JLabel lblCategorie = new JLabel("Categorie");
		GridBagConstraints gbc_lblCategorie = new GridBagConstraints();
		gbc_lblCategorie.anchor = GridBagConstraints.WEST;
		gbc_lblCategorie.insets = new Insets(0, 0, 5, 5);
		gbc_lblCategorie.gridx = 1;
		gbc_lblCategorie.gridy = 2;
		planel_typeproduit.add(lblCategorie, gbc_lblCategorie);

		categorylist = merchant.getCategorylist();
		JComboBox<Object> comboBox_category = new JComboBox<>(categorylist.toArray());
		category = (Category) comboBox_category.getSelectedItem();

		GridBagConstraints gbc_comboBox_category = new GridBagConstraints();
		gbc_comboBox_category.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox_category.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_category.gridx = 3;
		gbc_comboBox_category.gridy = 2;
		planel_typeproduit.add(comboBox_category, gbc_comboBox_category);

		JLabel lblTypeDeProduit = new JLabel("Type de produit");
		GridBagConstraints gbc_lblTypeDeProduit = new GridBagConstraints();
		gbc_lblTypeDeProduit.insets = new Insets(0, 0, 0, 5);
		gbc_lblTypeDeProduit.anchor = GridBagConstraints.WEST;
		gbc_lblTypeDeProduit.gridx = 1;
		gbc_lblTypeDeProduit.gridy = 3;
		planel_typeproduit.add(lblTypeDeProduit, gbc_lblTypeDeProduit);

		productTypelist = category.getProductTypelist();
		JComboBox<Object> comboBox_producttype = new JComboBox<>(productTypelist.toArray());

		GridBagConstraints gbc_comboBox_producttype = new GridBagConstraints();
		gbc_comboBox_producttype.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_producttype.gridx = 3;
		gbc_comboBox_producttype.gridy = 3;
		planel_typeproduit.add(comboBox_producttype, gbc_comboBox_producttype);

		JPanel panel_prix = new JPanel();
		tabbedPane.addTab("Pricing", null, panel_prix, null);
		GridBagLayout gbl_panel_prix = new GridBagLayout();
		gbl_panel_prix.columnWidths = new int[] { 0, 0, 0, 0, 0 };
		gbl_panel_prix.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel_prix.columnWeights = new double[] { 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_panel_prix.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel_prix.setLayout(gbl_panel_prix);

		JLabel lblUnitDeVente = new JLabel("Unité de vente");
		GridBagConstraints gbc_lblUnitDeVente = new GridBagConstraints();
		gbc_lblUnitDeVente.anchor = GridBagConstraints.WEST;
		gbc_lblUnitDeVente.insets = new Insets(0, 0, 5, 5);
		gbc_lblUnitDeVente.gridx = 1;
		gbc_lblUnitDeVente.gridy = 1;
		panel_prix.add(lblUnitDeVente, gbc_lblUnitDeVente);

		JComboBox<String> comboBox_unit = new JComboBox<>();
		comboBox_unit.setModel(new DefaultComboBoxModel<String>(new String[] { "Unité", "Kg", "Litre", "Packet" }));
		GridBagConstraints gbc_comboBox_unit = new GridBagConstraints();
		gbc_comboBox_unit.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox_unit.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_unit.gridx = 3;
		gbc_comboBox_unit.gridy = 1;
		panel_prix.add(comboBox_unit, gbc_comboBox_unit);

		JLabel lblDeviseDeVente = new JLabel("Devise de vente");
		GridBagConstraints gbc_lblDeviseDeVente = new GridBagConstraints();
		gbc_lblDeviseDeVente.anchor = GridBagConstraints.WEST;
		gbc_lblDeviseDeVente.insets = new Insets(0, 0, 5, 5);
		gbc_lblDeviseDeVente.gridx = 1;
		gbc_lblDeviseDeVente.gridy = 2;
		panel_prix.add(lblDeviseDeVente, gbc_lblDeviseDeVente);

		JComboBox<String> comboBox_currency = new JComboBox<>();
		comboBox_currency.setModel(new DefaultComboBoxModel<String>(new String[] { "MAD", "USD", "EUR" }));
		GridBagConstraints gbc_comboBox_currency = new GridBagConstraints();
		gbc_comboBox_currency.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox_currency.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_currency.gridx = 3;
		gbc_comboBox_currency.gridy = 2;
		panel_prix.add(comboBox_currency, gbc_comboBox_currency);

		JLabel lblPrix = new JLabel("Prix");
		GridBagConstraints gbc_lblPrix = new GridBagConstraints();
		gbc_lblPrix.anchor = GridBagConstraints.WEST;
		gbc_lblPrix.insets = new Insets(0, 0, 5, 5);
		gbc_lblPrix.gridx = 1;
		gbc_lblPrix.gridy = 3;
		panel_prix.add(lblPrix, gbc_lblPrix);

		price = new JTextField();
		GridBagConstraints gbc_price = new GridBagConstraints();
		gbc_price.insets = new Insets(0, 0, 5, 0);
		gbc_price.fill = GridBagConstraints.HORIZONTAL;
		gbc_price.gridx = 3;
		gbc_price.gridy = 3;
		panel_prix.add(price, gbc_price);
		price.setColumns(10);

		JLabel lblDateDexpiration = new JLabel("Date d'expiration");
		GridBagConstraints gbc_lblDateDexpiration = new GridBagConstraints();
		gbc_lblDateDexpiration.anchor = GridBagConstraints.WEST;
		gbc_lblDateDexpiration.insets = new Insets(0, 0, 5, 5);
		gbc_lblDateDexpiration.gridx = 1;
		gbc_lblDateDexpiration.gridy = 4;
		panel_prix.add(lblDateDexpiration, gbc_lblDateDexpiration);

		GridBagConstraints gbc_datePicker = new GridBagConstraints();
		gbc_datePicker.fill = GridBagConstraints.HORIZONTAL;
		gbc_datePicker.insets = new Insets(0, 0, 5, 0);
		gbc_datePicker.gridx = 3;
		gbc_datePicker.gridy = 4;

		UtilDateModel model = new UtilDateModel();
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl expiryDatePanel = new JDatePanelImpl(model, p);
		JDatePickerImpl expiryDatePicker = new JDatePickerImpl(expiryDatePanel, new DateLabelFormatter());
		panel_prix.add(expiryDatePicker, gbc_datePicker);

		JRadioButton available = new JRadioButton("Disponible en stock");
		GridBagConstraints gbc_available = new GridBagConstraints();
		gbc_available.insets = new Insets(0, 0, 5, 0);
		gbc_available.anchor = GridBagConstraints.WEST;
		gbc_available.gridx = 3;
		gbc_available.gridy = 5;
		panel_prix.add(available, gbc_available);

		JLabel lblNombreDarticlesEn = new JLabel("Nombre d'articles en stock");
		GridBagConstraints gbc_lblNombreDarticlesEn = new GridBagConstraints();
		gbc_lblNombreDarticlesEn.insets = new Insets(0, 0, 0, 5);
		gbc_lblNombreDarticlesEn.gridx = 1;
		gbc_lblNombreDarticlesEn.gridy = 6;
		panel_prix.add(lblNombreDarticlesEn, gbc_lblNombreDarticlesEn);

		JSpinner items_number = new JSpinner();
		GridBagConstraints gbc_items_number = new GridBagConstraints();
		gbc_items_number.fill = GridBagConstraints.HORIZONTAL;
		gbc_items_number.gridx = 3;
		gbc_items_number.gridy = 6;
		panel_prix.add(items_number, gbc_items_number);

		JPanel panel_promo = new JPanel();
		tabbedPane.addTab("Promotions", null, panel_promo, null);
		GridBagLayout gbl_panel_promo = new GridBagLayout();
		gbl_panel_promo.columnWidths = new int[] { 0, 0, 0, 0, 0 };
		gbl_panel_promo.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gbl_panel_promo.columnWeights = new double[] { 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_panel_promo.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel_promo.setLayout(gbl_panel_promo);

		JRadioButton promotion = new JRadioButton("Disponible en promotion");
		GridBagConstraints gbc_promotion = new GridBagConstraints();
		gbc_promotion.insets = new Insets(0, 0, 5, 5);
		gbc_promotion.gridx = 1;
		gbc_promotion.gridy = 1;
		panel_promo.add(promotion, gbc_promotion);

		JLabel lblPrixPromotionnel = new JLabel("Prix promotionnel");
		GridBagConstraints gbc_lblPrixPromotionnel = new GridBagConstraints();
		gbc_lblPrixPromotionnel.insets = new Insets(0, 0, 5, 5);
		gbc_lblPrixPromotionnel.anchor = GridBagConstraints.WEST;
		gbc_lblPrixPromotionnel.gridx = 1;
		gbc_lblPrixPromotionnel.gridy = 2;
		panel_promo.add(lblPrixPromotionnel, gbc_lblPrixPromotionnel);

		promo_price = new JTextField();
		GridBagConstraints gbc_promo_price = new GridBagConstraints();
		gbc_promo_price.insets = new Insets(0, 0, 5, 0);
		gbc_promo_price.fill = GridBagConstraints.HORIZONTAL;
		gbc_promo_price.gridx = 3;
		gbc_promo_price.gridy = 2;
		panel_promo.add(promo_price, gbc_promo_price);
		promo_price.setColumns(10);

		JLabel lblDateDexpiration_1 = new JLabel("Date d'expiration");
		GridBagConstraints gbc_lblDateDexpiration_1 = new GridBagConstraints();
		gbc_lblDateDexpiration_1.anchor = GridBagConstraints.WEST;
		gbc_lblDateDexpiration_1.insets = new Insets(0, 0, 0, 5);
		gbc_lblDateDexpiration_1.gridx = 1;
		gbc_lblDateDexpiration_1.gridy = 3;
		panel_promo.add(lblDateDexpiration_1, gbc_lblDateDexpiration_1);

		JDatePanelImpl promo_expiryDatePanel = new JDatePanelImpl(model, p);
		JDatePickerImpl promo_expiryDatePicker = new JDatePickerImpl(promo_expiryDatePanel, new DateLabelFormatter());
		GridBagConstraints gbc_promo_expiryDatePicker = new GridBagConstraints();
		gbc_promo_expiryDatePicker.fill = GridBagConstraints.HORIZONTAL;
		gbc_promo_expiryDatePicker.gridx = 3;
		gbc_promo_expiryDatePicker.gridy = 3;
		panel_promo.add(promo_expiryDatePicker, gbc_promo_expiryDatePicker);

		enableComponents(tabbedPane, false);

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

		// Lorsqu'on selectionne un element dans le ComboBox
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				merchant = (Merchant) comboBox.getSelectedItem();
				table.setModel(getProductTable(contentPanel, merchant.getProductlist()));
				clearTextComponents(panel_formulaire);

				categorylist = merchant.getCategorylist();
				if (categorylist.equals(null)) {
					comboBox_category.setModel(null);
					comboBox_producttype.setModel(null);
				} else {
					comboBox_category.setModel(new DefaultComboBoxModel<Object>(categorylist.toArray()));
					category = (Category) comboBox_category.getSelectedItem();
					if (category.equals(null)) {
						comboBox_producttype.setModel(null);

					} else {
						productTypelist = category.getProductTypelist();
						if (productTypelist.equals(null)) {
							comboBox_producttype.setModel(null);
						} else
							comboBox_producttype.setModel(new DefaultComboBoxModel<Object>(productTypelist.toArray()));
					}
				}
			}
		});

		// Lorsqu'on selectionne un element dans le ComboBox_2
		comboBox_category.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				category = (Category) comboBox_category.getSelectedItem();
				productTypelist = category.getProductTypelist();
				if (productTypelist.equals(null)) {
					comboBox_producttype.setModel(null);
				} else
					comboBox_producttype.setModel(new DefaultComboBoxModel<Object>(productTypelist.toArray()));
			}
		});

		// Lorsqu'on click sur un enregitrement du tableau
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int row = table.rowAtPoint(arg0.getPoint());
				int s = Integer.parseInt(table.getModel().getValueAt(row, 0) + "");
				Product = findProductById(s, merchant.getProductlist());
				label1.setText(Product.getLabel1());
				label2.setText(Product.getLabel2());
				desc1.setText(Product.getDesc1());
				desc2.setText(Product.getDesc2());
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
				enableComponents(tabbedPane, true);
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
					Product.setCab(cab.getText());
					Product.setLabel1(label1.getText());
					Product.setLabel2(label2.getText());
					Product.setDesc1(desc1.getText());
					Product.setDesc2(desc2.getText());
					Product.setPublication_date(new Date(System.currentTimeMillis()));
					Product.setModification_date(new Date(System.currentTimeMillis()));
					Product.setExpiryDate((Date) expiryDatePicker.getModel().getValue());
					Product.setCurrency((String) comboBox_currency.getSelectedItem());
					Product.setUnit((String) comboBox_unit.getSelectedItem());
					Product.setBrand((Brand) comboBox_brand.getSelectedItem());
					Product.setProducttype((ProductType) comboBox_producttype.getSelectedItem());
					Product.setMerchant((Merchant) comboBox.getSelectedItem());
					if (imgPath.getText().length() != 0) {
						Product.setImg(readImageFromPath(imgPath.getText()));
					}

					merchant = (Merchant) comboBox.getSelectedItem();
					merchant.add(Product);
					// webservice = serverUrl + "/merchant";
					// putForm(merchant, webservice, contentPanel);
					webservice = serverUrl + "/product/save";
					postForm(Product, webservice, contentPanel);
					table.setModel(getProductTable(contentPanel, merchant.getProductlist()));
					btnValider.setEnabled(false);
					btnSortir.setEnabled(false);
					enableComponents(tabbedPane, false);
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
					Product.setLabel1(label1.getText());
					Product.setLabel2(label2.getText());
					Product.setDesc1(desc1.getText());
					Product.setDesc2(desc2.getText());
					if (imgPath.getText().length() != 0) {
						Product.setImg(readImageFromPath(imgPath.getText()));
					}

					webservice = serverUrl + "/product";
					putForm(Product, webservice, contentPanel);
					btnValider.setEnabled(false);
					btnSortir.setEnabled(false);
					enableComponents(tabbedPane, false);
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
				enableComponents(tabbedPane, true);
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
				enableComponents(tabbedPane, false);
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
