package com.emerchant.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.StackPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.sksamuel.jqm4gwt.IconPos;
import com.sksamuel.jqm4gwt.JQMContext;
import com.sksamuel.jqm4gwt.JQMDialog;
import com.sksamuel.jqm4gwt.JQMPage;
import com.sksamuel.jqm4gwt.button.JQMButton;
import com.sksamuel.jqm4gwt.events.TapHandler;
import com.sksamuel.jqm4gwt.html.Heading;
import com.sksamuel.jqm4gwt.html.ImageLinkButton;
import com.sksamuel.jqm4gwt.layout.JQMTable;
import com.sksamuel.jqm4gwt.list.JQMList;
import com.sksamuel.jqm4gwt.list.JQMListItem;

import com.sksamuel.jqm4gwt.toolbar.JQMHeader;
import com.sksamuel.jqm4gwt.toolbar.JQMPanel;

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
	private List<JSONObject> monPanier = new ArrayList<JSONObject>();

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

	public void show() {
		LoadHeader();
		LoadNavPanel();
		LoadContentPanel();
		JQMContext.changePage(page);
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
					selectedproducttypes.add(productType.get("id").toString().replaceAll("\"", ""));
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
		table.setColumns(RootPanel.get("start").getOffsetWidth() / 270);
		// table.setColumns(3);
		selectedproductlist = getSelectedProductlist();

		for (JSONObject product : selectedproductlist) {
			// galeryFlexTable.setWidget(i, j, createProductForm(product));
			table.add(createProductForm(product));

		}
		selectedproducttypes.clear();
	}

	private void RefreshContentPanel() {
		table.clear();
		// table.setColumns(RootPanel.get("product-div").getOffsetWidth() / 270);
		table.setColumns(3);
		selectedproductlist = getSelectedProductlist();

		for (JSONObject product : selectedproductlist) {
			// galeryFlexTable.setWidget(i, j, createProductForm(product));
			table.add(createProductForm(product));

		}
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
						Window.alert(product.get("label1").toString().replaceAll("\"", ""));
					}
				});

		Button buyButton = new Button(
				"<image src='img/add_panier.png' width='20px' height='20px'>  Ajouter au panier</image>",
				new ClickHandler() {
					public void onClick(ClickEvent event) {
						Widget sender = (Widget) event.getSource();
						// Window.alert(getSelectedProductById(sender.getElement().getId()).get("label1").toString()
						// .replaceAll("\"", ""));
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

	private List<JSONObject> getSelectedProductlist() {
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
							&& selectedproducttypes.contains(productType.get("id").toString().replaceAll("\"", ""))) {
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

	public static native void clickElement(Element elem) /*-{
		elem.click();
	}-*/;

}
