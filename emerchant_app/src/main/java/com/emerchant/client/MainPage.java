package com.emerchant.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.sksamuel.jqm4gwt.DataIcon;
import com.sksamuel.jqm4gwt.IconPos;
import com.sksamuel.jqm4gwt.JQMContext;
import com.sksamuel.jqm4gwt.JQMDialog;
import com.sksamuel.jqm4gwt.JQMPage;
import com.sksamuel.jqm4gwt.button.JQMButton;
import com.sksamuel.jqm4gwt.form.elements.JQMText;
import com.sksamuel.jqm4gwt.html.Heading;
import com.sksamuel.jqm4gwt.html.ImageLinkButton;
import com.sksamuel.jqm4gwt.html.Paragraph;
import com.sksamuel.jqm4gwt.layout.JQMCollapsible;
import com.sksamuel.jqm4gwt.layout.JQMTable;
import com.sksamuel.jqm4gwt.list.JQMList;
import com.sksamuel.jqm4gwt.list.JQMListItem;

import com.sksamuel.jqm4gwt.toolbar.JQMHeader;
import com.sksamuel.jqm4gwt.toolbar.JQMPanel;

class MonPanierObject {
	public JSONObject product;
	public int nbre;
	public float prixTotal;
	public float prixUnitaire;

	public JSONObject getProduct() {
		return product;
	}

	public void setProduct(JSONObject product) {
		this.product = product;
	}

	public int getNbre() {
		return nbre;
	}

	public void setNbre(int nbre) {
		this.nbre = nbre;
	}

	public float getPrixTotal() {
		return prixTotal;
	}

	public void setPrixTotal(float prixTotal) {
		this.prixTotal = prixTotal;
	}

	public float getPrixUnitaire() {
		return prixUnitaire;
	}

	public void setPrixUnitaire(float prixUnitaire) {
		this.prixUnitaire = prixUnitaire;
	}

}

public class MainPage {
	interface UiBinder extends com.google.gwt.uibinder.client.UiBinder<JQMPage, MainPage> {
	}

	public static final UiBinder BINDER = GWT.create(MainPage.UiBinder.class);

	public static JQMPage createPage() {
		return new MainPage().page;
	}

	private JQMPage page = MainPage.BINDER.createAndBindUi(this);
	private static final JQMDialog dlg = new JQMDialog(new JQMHeader("Dialog Test"));
	private static JQMDialog dlgMsg = null;
	private String json_response;
	private JSONObject merchant;
	private JSONArray categorylist;
	private JSONObject category;
	private JSONArray productTypelist;
	private JSONObject productType;
	private JSONArray productlist;
	private JSONObject product;
	private List<JSONObject> selectedproductlist;
	private List<String> selectedproducttypes = new ArrayList<String>();
	private List<String> allproducttypes = new ArrayList<String>();
	// private List<JSONObject> monPanier = new ArrayList<JSONObject>();
	private List<MonPanierObject> monPanierObjectList = new ArrayList<MonPanierObject>();

	@UiField
	JQMList listWithChecked;

	@UiField
	ImageLinkButton merchantLogo;

	@UiField
	Heading merchantName;

	@UiField
	JQMTable table;

	@UiField
	JQMPanel testView1Panel;

	@UiField
	JQMButton headerButton;

	@UiField
	JQMButton panierButton;

	@UiField
	JQMButton mainButton;

	public void show() {
		LoadHeader();
		LoadNavPanel();
		LoadContentPanel();
		mainButton.setAlwaysActive(true);
		JQMContext.changePage(page);

		Window.addResizeHandler(new ResizeHandler() {

			@Override
			public void onResize(ResizeEvent event) {
				if (mainButton.isAlwaysActive()) {
					if (Window.getClientWidth() / 350 == 3) {
						table.setColumns(3);
						table.setWidth("1000");
						table.getElement().getStyle().setProperty("margin", "0 auto");

					}

					if (Window.getClientWidth() / 350 == 2) {
						table.setColumns(2);
						table.setWidth("700");
						table.getElement().getStyle().setProperty("margin", "0 auto");

					}

					if (Window.getClientWidth() / 350 == 1) {
						table.setColumns(1);
						table.setWidth("500");
						table.getElement().getStyle().setProperty("margin", "0 auto");

					}

				}

			}

		});

		mainButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				mainButton.setAlwaysActive(true);
				panierButton.setAlwaysActive(false);
				if (selectedproducttypes.size() != 0)
					RefreshContentPanel();
				else
					LoadContentPanel();
			}
		});

		panierButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				panierButton.setAlwaysActive(true);
				mainButton.setAlwaysActive(false);
				affichePanier();
			}
		});
	}

	public void parseData() {

		String JSON_URL = "http://localhost:8080/wikifood/rest/merchant?id=2";
		String url = URL.encode(JSON_URL);
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, url);
		builder.setHeader("Content-Type", "application/json");
		builder.setHeader("Accept", "application/json");

		try {
			builder.sendRequest(null, new RequestCallback() {

				@Override
				public void onError(Request request, Throwable exception) {
					Window.alert("Some error occurred: " + exception.getMessage());

				}

				@Override
				public void onResponseReceived(Request request, Response response) {
					json_response = response.getText();
					JSONValue value = JSONParser.parseStrict(json_response);
					merchant = (JSONObject) value.isObject();
					show();
				}

			});

		} catch (RequestException e) {
			// TODO Auto-generated catch block
			Window.alert("error");
			e.printStackTrace();
		}

	}

	private void LoadHeader() {
		merchantName.setText(merchant.get("label1").toString().replaceAll("\"", ""));
	}

	private void LoadNavPanel() {
		merchantLogo.setSrc("data:image/png;base64," + merchant.get("img").toString().replaceAll("\"", ""));
		merchantLogo.setHeight("110px");

		listWithChecked.setFilterable(true);

		categorylist = (JSONArray) merchant.get("categorylist");
		for (int i = 0; i < categorylist.size(); i++) {
			category = (JSONObject) categorylist.get(i);
			listWithChecked.addDivider(category.get("label1").toString().replaceAll("\"", ""));

			productTypelist = (JSONArray) category.get("productTypelist");
			if (productTypelist.size() > 0) {
				for (int j = 0; j < productTypelist.size(); j++) {
					productType = (JSONObject) productTypelist.get(j);
					JQMListItem item = listWithChecked
							.addItem(productType.get("label1").toString().replaceAll("\"", ""));
					item.setCheckBox(IconPos.RIGHT);
					item.getElement().setId(productType.get("id").toString().replaceAll("\"", ""));
					allproducttypes.add(productType.get("id").toString().replaceAll("\"", ""));
					item.addClickHandler(new ClickHandler() {
						@Override
						public void onClick(ClickEvent event) {
							if (!item.isChecked()) {
								selectedproducttypes.add(item.getElement().getId());
								RefreshContentPanel();
							}

							else {
								selectedproducttypes.remove(item.getElement().getId());
								RefreshContentPanel();
							}
						}
					});

				}
			}

		}

	}

	private void LoadContentPanel() {
		table.clear();
		table.setColumns(Window.getClientWidth() / 350);

		selectedproductlist = getSelectedProductlist(allproducttypes);

		for (JSONObject product : selectedproductlist) {
			// galeryFlexTable.setWidget(i, j, createProductForm(product));
			table.add(createProductForm(product));

		}
		// allproducttypes.clear();
	}

	private void RefreshContentPanel() {
		table.clear();
		table.setColumns(Window.getClientWidth() / 350);
		if (!mainButton.isAlwaysActive()) {
			mainButton.setAlwaysActive(true);
			panierButton.setAlwaysActive(false);
		}
		selectedproductlist = getSelectedProductlist(selectedproducttypes);

		for (JSONObject product : selectedproductlist) {
			// galeryFlexTable.setWidget(i, j, createProductForm(product));
			table.add(createProductForm(product));

		}
	}

	private void affichePanier() {
		table.clear();
		table.setColumns(1);
		// selectedproductlist = getSelectedProductlist();

		for (MonPanierObject monPanier : monPanierObjectList) {
			// galeryFlexTable.setWidget(i, j, createProductForm(product));
			table.add(createPanierForm(monPanier));

		}
	}

	private Widget createPanierForm(MonPanierObject monPanierObject) {
		JQMCollapsible c = new JQMCollapsible(monPanierObject.product.get("label1").toString().replaceAll("\"", ""));
		c.setContainerId(monPanierObject.product.get("id").toString().replaceAll("\"", ""));
		// c.add(new Paragraph(product.get("desc1").toString().replaceAll("\"", "")));
		FlexTable layout = new FlexTable();
		layout.setCellSpacing(3);
		FlexCellFormatter cellFormatter = layout.getFlexCellFormatter();

		Image productImg = new Image(
				"data:image/png;base64," + monPanierObject.product.get("img").toString().replaceAll("\"", ""));
		productImg.addStyleName("productImg");
		productImg.setHeight("100px");
		productImg.setWidth("100px");
		layout.setWidget(0, 0, productImg);
		cellFormatter.setRowSpan(0, 0, 3);
		cellFormatter.setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_LEFT);

		layout.setWidget(0, 1, new Paragraph(monPanierObject.product.get("desc1").toString().replaceAll("\"", "")));
		cellFormatter.setColSpan(0, 1, 4);
		cellFormatter.setRowSpan(0, 1, 2);
		cellFormatter.setHorizontalAlignment(0, 1, HasHorizontalAlignment.ALIGN_CENTER);
		// layout.setHTML(0, 1, "<b>" + product.get("desc1").toString().replaceAll("\"",
		// "") + "</b>");

		JQMButton plusArticleButton = new JQMButton("");
		plusArticleButton.setBuiltInIcon(DataIcon.PLUS);
		plusArticleButton.setMini(true);
		plusArticleButton.setSize("10", "10");
		JQMButton moinArticleButton = new JQMButton("");
		moinArticleButton.setBuiltInIcon(DataIcon.MINUS);
		moinArticleButton.setMini(true);
		moinArticleButton.setSize("10", "10");
		JQMButton deleteArticleButton = new JQMButton("Supprimer");
		deleteArticleButton.setBuiltInIcon(DataIcon.DELETE);
		deleteArticleButton.setMini(true);
		JQMText nbreArticleText = new JQMText("");
		nbreArticleText.setValue("" + monPanierObject.getNbre());
		nbreArticleText.setWidth("50");
		Label prixArticle = new Label("prix : " + monPanierObject.getPrixTotal() + " DH");
		layout.setWidget(0, 4, prixArticle);
		cellFormatter.setRowSpan(0, 4, 2);
		cellFormatter.setColSpan(0, 4, 3);
		cellFormatter.setHorizontalAlignment(0, 4, HasHorizontalAlignment.ALIGN_CENTER);
		cellFormatter.setVerticalAlignment(0, 4, HasVerticalAlignment.ALIGN_MIDDLE);

		layout.setWidget(2, 1, plusArticleButton);
		layout.setWidget(2, 2, moinArticleButton);
		layout.setWidget(2, 3, nbreArticleText);
		// cellFormatter.setColSpan(1, 4, 5);

		plusArticleButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				int nbre = ++monPanierObject.nbre;
				float prixUnitaire = monPanierObject.prixUnitaire;
				float prixTotal = prixUnitaire * nbre;
				monPanierObject.prixTotal = prixUnitaire * nbre;
				nbreArticleText.setValue("" + nbre);
				prixArticle.setText("prix : " + prixTotal + " DH");
			}
		});

		moinArticleButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				int nbre = --monPanierObject.nbre;
				if (nbre > 0) {
					float prixUnitaire = monPanierObject.prixUnitaire;
					float prixTotal = prixUnitaire * nbre;
					monPanierObject.prixTotal = prixUnitaire * nbre;
					nbreArticleText.setValue("" + nbre);
					prixArticle.setText("prix : " + prixTotal + " DH");
				} else
					++monPanierObject.nbre;

			}
		});

		// cellFormatter.setHorizontalAlignment(0, 5,
		// HasHorizontalAlignment.ALIGN_LEFT);
		layout.setWidget(2, 6, deleteArticleButton);
		c.add(layout);
		c.setWidth("470");
		c.setCollapsed(false);
		deleteArticleButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				c.setVisible(false);
				monPanierObjectList.remove(monPanierObject);
			}
		});
		return c;

	}

	private Widget createProductForm(JSONObject product) {
		// Create a table to layout the form options
		FlexTable layout = new FlexTable();
		layout.setCellSpacing(6);
		layout.setWidth("270px");
		FlexCellFormatter cellFormatter = layout.getFlexCellFormatter();

		// Add a title to the form
		layout.setHTML(0, 0, "<b>" + product.get("label1").toString().replaceAll("\"", "") + "</b>");
		cellFormatter.setColSpan(0, 0, 3);
		cellFormatter.setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_CENTER);

		Image productImg = new Image("data:image/png;base64," + product.get("img").toString().replaceAll("\"", ""));
		productImg.addStyleName("productImg");

		productImg.setHeight("200px");
		productImg.setWidth("270px");

		layout.setWidget(1, 0, productImg);
		cellFormatter.setColSpan(1, 0, 3);
		cellFormatter.setHorizontalAlignment(1, 0, HasHorizontalAlignment.ALIGN_CENTER);

		// Add some standard form options
		layout.setHTML(2, 0, "<b>Prix   :</b>");
		layout.setWidget(2, 1, new Label(
				product.get("price").toString() + " " + product.get("currency").toString().replaceAll("\"", "")));
		layout.setWidget(2, 2, new Label(
				product.get("promo_price").toString() + " " + product.get("currency").toString().replaceAll("\"", "")));

		Button detailButton = new Button("<image src='img/loupe.png' width='20px' height='20px'>  Details</image>",
				new ClickHandler() {
					public void onClick(ClickEvent event) {
						// Window.alert(product.get("label1").toString().replaceAll("\"", ""));
						afficheDetailProduit(product);
					}
				});

		Button buyButton = new Button(
				"<image src='img/add_panier.png' width='20px' height='20px'>  Ajouter au panier</image>",
				new ClickHandler() {
					public void onClick(ClickEvent event) {
						Widget sender = (Widget) event.getSource();
						JSONObject product = new JSONObject();
						product = getSelectedProductById(sender.getElement().getId());
						// monPanier.add(getSelectedProductById(sender.getElement().getId()));
						MonPanierObject monPanierObject = new MonPanierObject();
						monPanierObject.setProduct(product);
						monPanierObject.setNbre(1);
						monPanierObject.setPrixUnitaire(
								Float.parseFloat(product.get("promo_price").toString().replaceAll("\"", "")));
						monPanierObject.setPrixTotal(
								Float.parseFloat(product.get("promo_price").toString().replaceAll("\"", "")));
						monPanierObjectList.add(monPanierObject);
						Window.alert(getSelectedProductById(sender.getElement().getId()).get("label1").toString()
								.replaceAll("\"", ""));
					}
				});
		buyButton.getElement().setId(product.get("id").toString().replaceAll("\"", ""));
		layout.setWidget(3, 0, detailButton);
		cellFormatter.setColSpan(3, 1, 2);
		layout.setWidget(3, 1, buyButton);

		// Wrap the contents in a DecoratorPanel
		DecoratorPanel decPanel = new DecoratorPanel();
		decPanel.setWidget(layout);
		decPanel.getElement().getStyle().setProperty("margin", "10px");

		return decPanel;
	}

	private static List<String> getCountries() {
		final List<String> items = new ArrayList<String>();
		items.add("United Kingdom");
		items.add("Spain");
		items.add("France");
		items.add("Germany");
		items.add("Japan");
		items.add("China");
		return items;
	}

	private void afficheDetailProduit(JSONObject produit) {
		table.clear();
		table.setColumns(1);

		final JQMList productDetailList = new JQMList();
		productDetailList.withInset(true);
		List<String> items = getCountries();
		productDetailList.addDivider("Countries");
		productDetailList.addItems(items);
		productDetailList.setWidth("470");
		table.add(productDetailList);

	}

	private List<JSONObject> getSelectedProductlist(List<String> selectedproducttypes2) {
		List<JSONObject> list = new ArrayList<JSONObject>();
		categorylist = (JSONArray) merchant.get("categorylist");

		for (int i = 0; i < categorylist.size(); i++) {
			category = (JSONObject) categorylist.get(i);
			productTypelist = (JSONArray) category.get("productTypelist");

			if (productTypelist.size() > 0) {
				for (int j = 0; j < productTypelist.size(); j++) {
					productType = (JSONObject) productTypelist.get(j);
					productlist = (JSONArray) productType.get("productlist");
					if (productlist.size() > 0
							&& selectedproducttypes2.contains(productType.get("id").toString().replaceAll("\"", ""))) {
						// if (productlist.size() > 0) {
						for (int k = 0; k < productlist.size(); k++) {
							product = (JSONObject) productlist.get(k);
							list.add(product);
						}

					}
				}
			}
		}

		return list;

	}

	private JSONObject getSelectedProductById(String id) {
		for (JSONObject product : selectedproductlist) {
			if (id.equals(product.get("id").toString().replaceAll("\"", ""))) {
				return product;
			}

		}

		return null;

	}

	public static native void clickElement(Element elem) /*-{
		elem.click();
	}-*/;

}
