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
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.websystique.spring.model.Address;
import com.websystique.spring.model.Email;
import com.websystique.spring.model.Merchant;
import com.websystique.spring.model.Phone;

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
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Vector;

import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.GridLayout;
import javax.swing.DropMode;
import java.awt.Color;
import javax.swing.JTabbedPane;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

@SuppressWarnings("serial")
public class MerchantMgmView extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField1;
	private JTextField textField2;
	private JTextField imgPath;
	public String typeOperation;
	public static List<Merchant> merchantlist;
	public static Merchant merchant;
	private JTextField address_line1;
	private JTextField address_line2;
	private JTextField address_line3;
	private JTextField address_city;
	private JTextField address_country;
	private JTextField phone_num;
	private JTextField email_email;
	private JTextField address_zipcode;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			MerchantMgmView dialog = new MerchantMgmView();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);

		} catch (Exception e) {
			e.printStackTrace();
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
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = null;
		try {
			jsonString = mapper.writeValueAsString(o);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Client client = Client.create();
		WebResource webResource = client.resource(webservice);
		try {
			ClientResponse response = webResource.type("application/json").post(ClientResponse.class, jsonString);
			if (response.getStatus() != 204) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}
		} catch (Exception e2) { // TODAuto-generated catch block
			JOptionPane.showMessageDialog(contentPanel, "Connexion impossible");
		}

	}

	private static void putForm(Merchant o, String webservice, JPanel contentPanel) {
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
			JOptionPane.showMessageDialog(contentPanel, "Donnée modifiée");
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(contentPanel, "Connexion impossible");
		}

	}

	private static void deleteForm(Object o, String webservice, JPanel contentPanel) {
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

	private static DefaultTableModel getMerchantTable(JPanel contentPanel) {
		String jsonString = null;
		ObjectMapper mapper = new ObjectMapper();
		ClientResponse response = null;

		Client client = Client.create();
		WebResource webResource = client.resource("http://localhost:8080/wikifood/rest/merchant/getall");

		try {
			response = webResource.type("application/json").get(ClientResponse.class);
			jsonString = response.getEntity(String.class);
			merchantlist = mapper.readValue(jsonString, new TypeReference<List<Merchant>>() {
			});
			String[] columnNames = { "Id", "Label 1", "Label 1", "Description 1", "Description 2" };
			DefaultTableModel model = new DefaultTableModel(columnNames, 0);

			for (Merchant merchant : merchantlist) {
				Vector<String> row = new Vector<String>();
				row.add("" + merchant.getId());
				row.add(merchant.getLabel1());
				row.add(merchant.getLabel2());
				row.add(merchant.getDesc1());
				row.add(merchant.getDesc2());
				model.addRow(row);
			}
			return model;

		} catch (Exception e2) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(contentPanel, "Connexion impossible");
			return null;
		}
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

	public void clearTextComponents(Container container) {
		Component[] components = container.getComponents();
		for (Component component : components) {
			if (component instanceof JTextField) {
				((JTextField) component).setText("");
			}
			if (component instanceof JTextArea) {
				((JTextArea) component).setText("");
			}
		}
	}

	public void clearImage(JLabel label) {
		label.setIcon(null);

	}

	public Merchant findMerchantById(int id, List<Merchant> merchants) {

		for (Merchant merchant : merchants) {
			if (merchant.getId() == id) {
				return merchant;
			}
		}
		return null;
	}

	public Address findAddressByRank(int rank, List<Address> addresses) {

		for (Address address : addresses) {
			if (address.getRank() == rank) {
				return address;
			}
		}
		return null;
	}

	public Phone findPhoneByRank(int rank, List<Phone> phones) {

		for (Phone phone : phones) {
			if (phone.getRank() == rank) {
				return phone;
			}
		}
		return null;
	}

	public Email findEmailByRank(int rank, List<Email> emails) {

		for (Email email : emails) {
			if (email.getRank() == rank) {
				return email;
			}
		}
		return null;
	}

	/**
	 * Create the dialog.
	 */
	public MerchantMgmView() {
		setBounds(100, 100, 993, 632);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JPanel MerchantInfo = new JPanel();
			MerchantInfo.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(MerchantInfo, BorderLayout.SOUTH);
		}

		JPanel panel_operations = new JPanel();
		panel_operations.setBounds(33, 318, 462, 40);
		contentPanel.add(panel_operations);
		GridBagLayout gbl_panel_operations = new GridBagLayout();
		gbl_panel_operations.columnWidths = new int[] { 129, 79, 71, 81, 0 };
		gbl_panel_operations.rowHeights = new int[] { 23, 0 };
		gbl_panel_operations.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel_operations.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panel_operations.setLayout(gbl_panel_operations);

		JButton btnAjouter = new JButton("Ajouter un commerçant");
		btnAjouter.setIcon(new ImageIcon(MerchantMgmView.class
				.getResource("/META-INF/resources/webjars/open-icon-library/0.11/png/24x24/actions/edit-add.png")));
		GridBagConstraints gbc_btnAjouter = new GridBagConstraints();
		gbc_btnAjouter.insets = new Insets(0, 0, 0, 5);
		gbc_btnAjouter.gridx = 0;
		gbc_btnAjouter.gridy = 0;
		panel_operations.add(btnAjouter, gbc_btnAjouter);

		JPanel panel_imgdisplay = new JPanel();
		panel_imgdisplay.setBounds(804, 76, 163, 162);
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
		btnValider.setIcon(new ImageIcon(MerchantMgmView.class
				.getResource("/META-INF/resources/webjars/open-icon-library/0.11/png/24x24/status/dialog-clean.png")));
		GridBagConstraints gbc_btnValider = new GridBagConstraints();
		gbc_btnValider.insets = new Insets(0, 0, 0, 5);
		gbc_btnValider.gridx = 0;
		gbc_btnValider.gridy = 0;
		panel_control.add(btnValider, gbc_btnValider);

		JButton btnSortir = new JButton("Sortir");

		btnSortir.setEnabled(false);
		btnSortir.setIcon(new ImageIcon(MerchantMgmView.class.getResource(
				"/META-INF/resources/webjars/open-icon-library/0.11/png/24x24/actions/application-exit-3.png")));
		GridBagConstraints gbc_btnSortir = new GridBagConstraints();
		gbc_btnSortir.fill = GridBagConstraints.VERTICAL;
		gbc_btnSortir.insets = new Insets(0, 0, 0, 5);
		gbc_btnSortir.gridx = 1;
		gbc_btnSortir.gridy = 0;
		panel_control.add(btnSortir, gbc_btnSortir);

		JButton btnUpdate = new JButton("Update");
		btnUpdate.setIcon(new ImageIcon(MerchantMgmView.class.getResource(
				"/META-INF/resources/webjars/open-icon-library/0.11/png/24x24/apps/accessories-text-editor-5.png")));
		btnUpdate.setEnabled(false);
		GridBagConstraints gbc_btnUpdate = new GridBagConstraints();
		gbc_btnUpdate.insets = new Insets(0, 0, 0, 5);
		gbc_btnUpdate.gridx = 2;
		gbc_btnUpdate.gridy = 0;
		panel_control.add(btnUpdate, gbc_btnUpdate);

		JButton btnDelete = new JButton("Delete");
		btnDelete.setIcon(new ImageIcon(MerchantMgmView.class.getResource(
				"/META-INF/resources/webjars/open-icon-library/0.11/png/24x24/actions/dialog-cancel-7.png")));
		btnDelete.setEnabled(false);
		GridBagConstraints gbc_btnDelete = new GridBagConstraints();
		gbc_btnDelete.anchor = GridBagConstraints.EAST;
		gbc_btnDelete.gridwidth = 2;
		gbc_btnDelete.insets = new Insets(0, 0, 0, 5);
		gbc_btnDelete.gridx = 3;
		gbc_btnDelete.gridy = 0;
		panel_control.add(btnDelete, gbc_btnDelete);

		JPanel panel = new JPanel();
		panel.setBounds(33, 369, 619, 193);
		contentPanel.add(panel);
		panel.setLayout(new GridLayout(1, 0, 0, 0));

		JTable table = new JTable();

		table.setModel(getMerchantTable(contentPanel));

		table.getColumnModel().getColumn(0).setPreferredWidth(102);
		table.getColumnModel().getColumn(1).setPreferredWidth(140);
		table.getColumnModel().getColumn(2).setPreferredWidth(146);
		table.getColumnModel().getColumn(3).setPreferredWidth(167);
		table.getColumnModel().getColumn(4).setPreferredWidth(168);
		panel.add(table);
		JScrollPane scrollPane = new JScrollPane(table);
		panel.add(scrollPane);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(33, 76, 745, 231);
		contentPanel.add(tabbedPane);

		JScrollPane scrollPane_1 = new JScrollPane();
		tabbedPane.addTab("Informations", null, scrollPane_1, null);

		JPanel panel_info = new JPanel();
		scrollPane_1.setRowHeaderView(panel_info);
		GridBagLayout gbl_panel_info = new GridBagLayout();
		gbl_panel_info.columnWidths = new int[] { 30, 130, 49, 156, 0 };
		gbl_panel_info.rowHeights = new int[] { 20, 20, 36, 36, 0, 0 };
		gbl_panel_info.columnWeights = new double[] { 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_panel_info.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel_info.setLayout(gbl_panel_info);

		JLabel libelle1 = new JLabel("Libelle du commerçant (FR)");
		libelle1.setEnabled(false);
		GridBagConstraints gbc_libelle1 = new GridBagConstraints();
		gbc_libelle1.anchor = GridBagConstraints.WEST;
		gbc_libelle1.insets = new Insets(0, 0, 5, 5);
		gbc_libelle1.gridx = 1;
		gbc_libelle1.gridy = 0;
		panel_info.add(libelle1, gbc_libelle1);

		textField1 = new JTextField();
		libelle1.setLabelFor(textField1);
		GridBagConstraints gbc_textField1 = new GridBagConstraints();
		gbc_textField1.fill = GridBagConstraints.BOTH;
		gbc_textField1.insets = new Insets(0, 0, 5, 0);
		gbc_textField1.gridx = 3;
		gbc_textField1.gridy = 0;
		panel_info.add(textField1, gbc_textField1);
		textField1.setColumns(10);

		JLabel libelle2 = new JLabel("Libelle du commerçant (EN)");
		libelle2.setEnabled(false);
		GridBagConstraints gbc_libelle2 = new GridBagConstraints();
		gbc_libelle2.anchor = GridBagConstraints.WEST;
		gbc_libelle2.insets = new Insets(0, 0, 5, 5);
		gbc_libelle2.gridx = 1;
		gbc_libelle2.gridy = 1;
		panel_info.add(libelle2, gbc_libelle2);

		textField2 = new JTextField();
		libelle2.setLabelFor(textField2);
		textField2.setForeground(Color.BLACK);
		GridBagConstraints gbc_textField2 = new GridBagConstraints();
		gbc_textField2.fill = GridBagConstraints.BOTH;
		gbc_textField2.insets = new Insets(0, 0, 5, 0);
		gbc_textField2.gridx = 3;
		gbc_textField2.gridy = 1;
		panel_info.add(textField2, gbc_textField2);
		textField2.setColumns(10);

		JLabel libelle3 = new JLabel("Description du commerçant (FR)  ");
		libelle3.setEnabled(false);
		GridBagConstraints gbc_libelle3 = new GridBagConstraints();
		gbc_libelle3.insets = new Insets(0, 0, 5, 5);
		gbc_libelle3.gridx = 1;
		gbc_libelle3.gridy = 2;
		panel_info.add(libelle3, gbc_libelle3);

		JTextArea textField3 = new JTextArea();
		libelle3.setLabelFor(textField3);
		textField3.setDropMode(DropMode.INSERT);
		textField3.setLineWrap(true);
		textField3.setWrapStyleWord(true);
		GridBagConstraints gbc_textField3 = new GridBagConstraints();
		gbc_textField3.fill = GridBagConstraints.BOTH;
		gbc_textField3.insets = new Insets(0, 0, 5, 0);
		gbc_textField3.gridx = 3;
		gbc_textField3.gridy = 2;
		panel_info.add(textField3, gbc_textField3);

		JLabel libelle4 = new JLabel("Description du commerçant (EN)");
		libelle4.setEnabled(false);
		GridBagConstraints gbc_libelle4 = new GridBagConstraints();
		gbc_libelle4.anchor = GridBagConstraints.WEST;
		gbc_libelle4.insets = new Insets(0, 0, 5, 5);
		gbc_libelle4.gridx = 1;
		gbc_libelle4.gridy = 3;
		panel_info.add(libelle4, gbc_libelle4);

		JTextArea textField4 = new JTextArea();
		libelle4.setLabelFor(textField4);
		textField4.setWrapStyleWord(true);
		textField4.setLineWrap(true);
		textField4.setBackground(Color.WHITE);
		textField4.setDropMode(DropMode.INSERT);
		GridBagConstraints gbc_textField4 = new GridBagConstraints();
		gbc_textField4.fill = GridBagConstraints.BOTH;
		gbc_textField4.insets = new Insets(0, 0, 5, 0);
		gbc_textField4.gridx = 3;
		gbc_textField4.gridy = 3;
		panel_info.add(textField4, gbc_textField4);

		enableComponents(panel_info, false);

		JLabel libelle5 = new JLabel("Logo du commerçant");
		libelle5.setEnabled(false);
		GridBagConstraints gbc_libelle5 = new GridBagConstraints();
		gbc_libelle5.anchor = GridBagConstraints.WEST;
		gbc_libelle5.insets = new Insets(0, 0, 0, 5);
		gbc_libelle5.gridx = 1;
		gbc_libelle5.gridy = 4;
		panel_info.add(libelle5, gbc_libelle5);

		JButton loadImage = new JButton("Load");
		loadImage.setIcon(new ImageIcon(MerchantMgmView.class.getResource(
				"/META-INF/resources/webjars/open-icon-library/0.11/png/16x16/places/oxygen-style/folder.png")));
		GridBagConstraints gbc_loadImage = new GridBagConstraints();
		gbc_loadImage.insets = new Insets(0, 0, 0, 5);
		gbc_loadImage.gridx = 2;
		gbc_loadImage.gridy = 4;
		panel_info.add(loadImage, gbc_loadImage);

		imgPath = new JTextField();
		libelle5.setLabelFor(imgPath);
		imgPath.setEnabled(false);
		imgPath.setColumns(10);
		GridBagConstraints gbc_imgPath = new GridBagConstraints();
		gbc_imgPath.fill = GridBagConstraints.HORIZONTAL;
		gbc_imgPath.gridx = 3;
		gbc_imgPath.gridy = 4;
		panel_info.add(imgPath, gbc_imgPath);

		JScrollPane scrollPane_2 = new JScrollPane();
		tabbedPane.addTab("Contact", null, scrollPane_2, null);

		JPanel panel_contact = new JPanel();
		scrollPane_2.setViewportView(panel_contact);
		GridBagLayout gbl_panel_contact = new GridBagLayout();
		gbl_panel_contact.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel_contact.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel_contact.columnWeights = new double[] { 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_panel_contact.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel_contact.setLayout(gbl_panel_contact);

		JLabel lblAdresse = new JLabel("Adresse");
		GridBagConstraints gbc_lblAdresse = new GridBagConstraints();
		gbc_lblAdresse.anchor = GridBagConstraints.WEST;
		gbc_lblAdresse.insets = new Insets(0, 0, 5, 5);
		gbc_lblAdresse.gridx = 1;
		gbc_lblAdresse.gridy = 0;
		panel_contact.add(lblAdresse, gbc_lblAdresse);

		JLabel lblLigne = new JLabel("Ligne 1");
		GridBagConstraints gbc_lblLigne = new GridBagConstraints();
		gbc_lblLigne.anchor = GridBagConstraints.WEST;
		gbc_lblLigne.insets = new Insets(0, 0, 5, 5);
		gbc_lblLigne.gridx = 1;
		gbc_lblLigne.gridy = 1;
		panel_contact.add(lblLigne, gbc_lblLigne);

		address_line1 = new JTextField();
		GridBagConstraints gbc_address_line1 = new GridBagConstraints();
		gbc_address_line1.fill = GridBagConstraints.HORIZONTAL;
		gbc_address_line1.insets = new Insets(0, 0, 5, 5);
		gbc_address_line1.gridx = 3;
		gbc_address_line1.gridy = 1;
		panel_contact.add(address_line1, gbc_address_line1);
		address_line1.setColumns(22);

		JLabel lblCodePostal = new JLabel("Code postal");
		GridBagConstraints gbc_lblCodePostal = new GridBagConstraints();
		gbc_lblCodePostal.insets = new Insets(0, 0, 5, 5);
		gbc_lblCodePostal.gridx = 5;
		gbc_lblCodePostal.gridy = 1;
		panel_contact.add(lblCodePostal, gbc_lblCodePostal);

		address_zipcode = new JTextField();
		address_zipcode.setColumns(22);
		GridBagConstraints gbc_address_zipcode = new GridBagConstraints();
		gbc_address_zipcode.insets = new Insets(0, 0, 5, 0);
		gbc_address_zipcode.fill = GridBagConstraints.HORIZONTAL;
		gbc_address_zipcode.gridx = 7;
		gbc_address_zipcode.gridy = 1;
		panel_contact.add(address_zipcode, gbc_address_zipcode);

		JLabel lblLigne_1 = new JLabel("Ligne 2");
		GridBagConstraints gbc_lblLigne_1 = new GridBagConstraints();
		gbc_lblLigne_1.anchor = GridBagConstraints.WEST;
		gbc_lblLigne_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblLigne_1.gridx = 1;
		gbc_lblLigne_1.gridy = 2;
		panel_contact.add(lblLigne_1, gbc_lblLigne_1);

		address_line2 = new JTextField();
		address_line2.setColumns(22);
		GridBagConstraints gbc_address_line2 = new GridBagConstraints();
		gbc_address_line2.insets = new Insets(0, 0, 5, 5);
		gbc_address_line2.fill = GridBagConstraints.HORIZONTAL;
		gbc_address_line2.gridx = 3;
		gbc_address_line2.gridy = 2;
		panel_contact.add(address_line2, gbc_address_line2);

		JLabel lblVille = new JLabel("Ville");
		GridBagConstraints gbc_lblVille = new GridBagConstraints();
		gbc_lblVille.anchor = GridBagConstraints.WEST;
		gbc_lblVille.insets = new Insets(0, 0, 5, 5);
		gbc_lblVille.gridx = 5;
		gbc_lblVille.gridy = 2;
		panel_contact.add(lblVille, gbc_lblVille);

		address_city = new JTextField();
		address_city.setColumns(22);
		GridBagConstraints gbc_address_city = new GridBagConstraints();
		gbc_address_city.insets = new Insets(0, 0, 5, 0);
		gbc_address_city.fill = GridBagConstraints.HORIZONTAL;
		gbc_address_city.gridx = 7;
		gbc_address_city.gridy = 2;
		panel_contact.add(address_city, gbc_address_city);

		JLabel lblLigne_2 = new JLabel("Ligne 3");
		GridBagConstraints gbc_lblLigne_2 = new GridBagConstraints();
		gbc_lblLigne_2.anchor = GridBagConstraints.WEST;
		gbc_lblLigne_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblLigne_2.gridx = 1;
		gbc_lblLigne_2.gridy = 3;
		panel_contact.add(lblLigne_2, gbc_lblLigne_2);

		address_line3 = new JTextField();
		address_line3.setColumns(22);
		GridBagConstraints gbc_address_line3 = new GridBagConstraints();
		gbc_address_line3.insets = new Insets(0, 0, 5, 5);
		gbc_address_line3.fill = GridBagConstraints.HORIZONTAL;
		gbc_address_line3.gridx = 3;
		gbc_address_line3.gridy = 3;
		panel_contact.add(address_line3, gbc_address_line3);

		JLabel lblPays = new JLabel("Pays");
		GridBagConstraints gbc_lblPays = new GridBagConstraints();
		gbc_lblPays.anchor = GridBagConstraints.WEST;
		gbc_lblPays.insets = new Insets(0, 0, 5, 5);
		gbc_lblPays.gridx = 5;
		gbc_lblPays.gridy = 3;
		panel_contact.add(lblPays, gbc_lblPays);

		address_country = new JTextField();
		address_country.setColumns(17);
		GridBagConstraints gbc_address_country = new GridBagConstraints();
		gbc_address_country.fill = GridBagConstraints.HORIZONTAL;
		gbc_address_country.insets = new Insets(0, 0, 5, 0);
		gbc_address_country.gridx = 7;
		gbc_address_country.gridy = 3;
		panel_contact.add(address_country, gbc_address_country);

		JLabel lblTelephone = new JLabel("Telephone");
		GridBagConstraints gbc_lblTelephone = new GridBagConstraints();
		gbc_lblTelephone.insets = new Insets(0, 0, 5, 5);
		gbc_lblTelephone.gridx = 1;
		gbc_lblTelephone.gridy = 5;
		panel_contact.add(lblTelephone, gbc_lblTelephone);

		phone_num = new JTextField();
		phone_num.setColumns(22);
		GridBagConstraints gbc_phone_num = new GridBagConstraints();
		gbc_phone_num.insets = new Insets(0, 0, 5, 5);
		gbc_phone_num.fill = GridBagConstraints.HORIZONTAL;
		gbc_phone_num.gridx = 3;
		gbc_phone_num.gridy = 5;
		panel_contact.add(phone_num, gbc_phone_num);

		JLabel lblType = new JLabel("Type");
		GridBagConstraints gbc_lblType = new GridBagConstraints();
		gbc_lblType.anchor = GridBagConstraints.WEST;
		gbc_lblType.insets = new Insets(0, 0, 5, 5);
		gbc_lblType.gridx = 5;
		gbc_lblType.gridy = 5;
		panel_contact.add(lblType, gbc_lblType);

		JComboBox<String> phone_type = new JComboBox<String>();
		phone_type.setModel(new DefaultComboBoxModel<String>(new String[] { "Mobile", "Home" }));
		GridBagConstraints gbc_phone_type = new GridBagConstraints();
		gbc_phone_type.insets = new Insets(0, 0, 5, 0);
		gbc_phone_type.fill = GridBagConstraints.HORIZONTAL;
		gbc_phone_type.gridx = 7;
		gbc_phone_type.gridy = 5;
		panel_contact.add(phone_type, gbc_phone_type);

		JLabel lblEmail = new JLabel("E-mail");
		GridBagConstraints gbc_lblEmail = new GridBagConstraints();
		gbc_lblEmail.anchor = GridBagConstraints.WEST;
		gbc_lblEmail.insets = new Insets(0, 0, 0, 5);
		gbc_lblEmail.gridx = 1;
		gbc_lblEmail.gridy = 6;
		panel_contact.add(lblEmail, gbc_lblEmail);

		email_email = new JTextField();
		email_email.setColumns(22);
		GridBagConstraints gbc_email_email = new GridBagConstraints();
		gbc_email_email.insets = new Insets(0, 0, 0, 5);
		gbc_email_email.fill = GridBagConstraints.HORIZONTAL;
		gbc_email_email.gridx = 3;
		gbc_email_email.gridy = 6;
		panel_contact.add(email_email, gbc_email_email);

		JLabel label = new JLabel("Type");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.anchor = GridBagConstraints.WEST;
		gbc_label.insets = new Insets(0, 0, 0, 5);
		gbc_label.gridx = 5;
		gbc_label.gridy = 6;
		panel_contact.add(label, gbc_label);

		JComboBox<String> email_type = new JComboBox<String>();
		email_type.setModel(new DefaultComboBoxModel<String>(new String[] { "personnel", "professionel" }));
		GridBagConstraints gbc_email_type = new GridBagConstraints();
		gbc_email_type.fill = GridBagConstraints.HORIZONTAL;
		gbc_email_type.gridx = 7;
		gbc_email_type.gridy = 6;
		panel_contact.add(email_type, gbc_email_type);

		enableComponents(panel_contact, false);
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

		// Lorsqu'on click sur un enregitrement du tableau
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int row = table.rowAtPoint(arg0.getPoint());
				int s = Integer.parseInt(table.getModel().getValueAt(row, 0) + "");
				merchant = findMerchantById(s, merchantlist);
				textField1.setText(merchant.getLabel1());
				textField2.setText(merchant.getLabel2());
				textField3.setText(merchant.getDesc1());
				textField4.setText(merchant.getDesc2());

				address_line1.setText(findAddressByRank(1, merchant.getAddresslist()).getLine1());
				address_line2.setText(findAddressByRank(1, merchant.getAddresslist()).getLine2());
				address_line3.setText(findAddressByRank(1, merchant.getAddresslist()).getLine3());
				address_zipcode.setText(findAddressByRank(1, merchant.getAddresslist()).getZipcode());
				address_city.setText(findAddressByRank(1, merchant.getAddresslist()).getCity());
				address_country.setText(findAddressByRank(1, merchant.getAddresslist()).getCountry());

				phone_type.setSelectedItem(findPhoneByRank(1, merchant.getPhonelist()).getType());
				phone_num.setText(findPhoneByRank(1, merchant.getPhonelist()).getNumber());

				email_type.setSelectedItem(findEmailByRank(1, merchant.getEmaillist()).getType());
				email_email.setText(findEmailByRank(1, merchant.getEmaillist()).getEmail());

				displayImage(imgLabel, merchant.getImg());
				btnUpdate.setEnabled(true);
				btnDelete.setEnabled(true);
				panel_imgdisplay.setVisible(true);
			}
		});

		// Lorsqu'on click sur le bouton "Valider"
		btnValider.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				String webservice;

				switch (typeOperation) {
				case "Create":
					merchant = new Merchant();
					merchant.setLabel1(textField1.getText());
					merchant.setLabel2(textField2.getText());
					merchant.setDesc1(textField3.getText());
					merchant.setDesc2(textField4.getText());
					if (imgPath.getText().length() != 0) {
						merchant.setImg(readImageFromPath(imgPath.getText()));
					}

					Address address = new Address();
					address.setLine1(address_line1.getText());
					address.setLine2(address_line2.getText());
					address.setLine3(address_line3.getText());
					address.setZipcode(address_zipcode.getText());
					address.setCity(address_city.getText());
					address.setCountry(address_country.getText());
					address.setRank(1);

					merchant.add(address);

					Phone phone = new Phone();
					phone.setType(String.valueOf(phone_type.getSelectedItem()));
					phone.setNumber(phone_num.getText());
					phone.setRank(1);

					merchant.add(phone);

					Email email = new Email();
					email.setType(String.valueOf(email_type.getSelectedItem()));
					email.setEmail(email_email.getText());
					email.setRank(1);

					merchant.add(email);

					webservice = "http://localhost:8080/wikifood/rest/merchant/save";
					postForm(merchant, webservice, contentPanel);

					table.setModel(getMerchantTable(contentPanel));
					btnValider.setEnabled(false);
					btnSortir.setEnabled(false);
					enableComponents(panel_info, false);
					enableComponents(panel_contact, false);
					loadImage.setEnabled(false);
					loadImage.setVisible(false);
					panel_imgdisplay.setVisible(true);
					imgLabel.setIcon(null);
					clearTextComponents(panel_info);
					clearTextComponents(panel_contact);
					panel_operations.setVisible(true);
					table.setVisible(true);
					break;
				case "Read":
					// code block
					break;
				case "Update":
					webservice = "http://localhost:8080/wikifood/rest/merchant";
					merchant.setLabel1(textField1.getText());
					merchant.setLabel2(textField2.getText());
					merchant.setDesc1(textField3.getText());
					merchant.setDesc2(textField4.getText());
					if (imgPath.getText().length() != 0) {
						merchant.setImg(readImageFromPath(imgPath.getText()));
					}

					findAddressByRank(1, merchant.getAddresslist()).setLine1(address_line1.getText());
					findAddressByRank(1, merchant.getAddresslist()).setLine2(address_line2.getText());
					findAddressByRank(1, merchant.getAddresslist()).setLine3(address_line3.getText());
					findAddressByRank(1, merchant.getAddresslist()).setZipcode(address_zipcode.getText());
					findAddressByRank(1, merchant.getAddresslist()).setCity(address_city.getText());
					findAddressByRank(1, merchant.getAddresslist()).setCountry(address_country.getText());

					findPhoneByRank(1, merchant.getPhonelist()).setType(String.valueOf(phone_type.getSelectedItem()));
					findPhoneByRank(1, merchant.getPhonelist()).setNumber(phone_num.getText());

					findEmailByRank(1, merchant.getEmaillist()).setType(String.valueOf(email_type.getSelectedItem()));
					findEmailByRank(1, merchant.getEmaillist()).setEmail(email_email.getText());

					putForm(merchant, webservice, contentPanel);
					table.setModel(getMerchantTable(contentPanel));
					btnValider.setEnabled(false);
					btnSortir.setEnabled(false);
					enableComponents(panel_info, false);
					enableComponents(panel_contact, false);
					loadImage.setEnabled(false);
					loadImage.setVisible(false);
					panel_imgdisplay.setVisible(true);
					imgLabel.setIcon(null);
					clearTextComponents(panel_info);
					clearTextComponents(panel_contact);
					panel_operations.setVisible(true);
					table.setVisible(true);
					break;
				case "Delete":

					break;
				default:
					// code block
				}
			}
		});

		// Lorsqu'on click sur le bouton "Ajouter"
		btnAjouter.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnValider.setEnabled(true);
				btnSortir.setEnabled(true);
				enableComponents(panel_info, true);
				enableComponents(panel_contact, true);
				loadImage.setEnabled(true);
				loadImage.setVisible(true);
				panel_imgdisplay.setVisible(true);
				panel_operations.setVisible(false);
				clearTextComponents(panel_info);
				clearTextComponents(panel_contact);
				clearImage(imgLabel);
				btnUpdate.setEnabled(false);
				btnDelete.setEnabled(false);
				table.setVisible(false);
				typeOperation = "Create";

			}
		});

		// Lorsqu'on click sur le bouton "Update"
		btnUpdate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnValider.setEnabled(true);
				btnSortir.setEnabled(true);
				enableComponents(panel_info, true);
				enableComponents(panel_contact, true);
				loadImage.setEnabled(true);
				loadImage.setVisible(true);
				panel_imgdisplay.setVisible(true);
				panel_operations.setVisible(false);
				btnUpdate.setEnabled(false);
				btnDelete.setEnabled(false);
				table.setVisible(false);
				typeOperation = "Update";

			}
		});

		// Lorsqu'on click sur le bouton "sortir"
		btnSortir.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnValider.setEnabled(false);
				btnSortir.setEnabled(false);
				enableComponents(panel_info, false);
				enableComponents(panel_contact, false);
				loadImage.setEnabled(false);
				loadImage.setVisible(false);
				panel_imgdisplay.setVisible(false);
				imgLabel.setIcon(null);
				clearTextComponents(panel_info);
				clearTextComponents(panel_contact);
				clearImage(imgLabel);
				panel_operations.setVisible(true);
				btnUpdate.setEnabled(false);
				btnDelete.setEnabled(false);
				table.setVisible(true);

			}
		});

		// Lorsqu'on click sur le bouton "delete"
		btnDelete.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String webservice = "http://localhost:8080/wikifood/rest/merchant";
				deleteForm(merchant, webservice, contentPanel);
				table.setModel(getMerchantTable(contentPanel));
				imgLabel.setIcon(null);
				clearTextComponents(panel_info);
				clearTextComponents(panel_contact);
				btnUpdate.setEnabled(false);
				btnDelete.setEnabled(false);
			}
		});

	}
}
