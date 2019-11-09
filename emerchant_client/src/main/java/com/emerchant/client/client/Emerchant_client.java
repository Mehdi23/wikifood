package com.emerchant.client.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
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
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.StackPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Emerchant_client implements EntryPoint {
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

	private ScrollPanel content_panel = new ScrollPanel();
	// private VerticalPanel content_panel = new VerticalPanel();
	private VerticalPanel nav_panel = new VerticalPanel();

	public void onModuleLoad() {
		parseData();

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
					LoadMainPage();
				}

			});

		} catch (RequestException e) {
			// TODO Auto-generated catch block
			Window.alert("error");
			e.printStackTrace();
		}

	}

	private void LoadMainPage() {

		// Navigation bar load
		LoadNavPanel();

		// Content panel load
		LoadContentPanel();

		// main_panel.addNorth(new HTML("header"), 10);
		// main_panel.addSouth(new HTML("footer"), 2);
		// main_panel.addWest(nav_panel, 15);
		// main_panel.add(content_panel);

		// Associate the Main panel with the HTML host page.
		// RootLayoutPanel.get().add(main_panel);
		RootPanel.get("product-div").add(content_panel);
		RootPanel.get("stack_panel").add(nav_panel);

		nav_panel.addStyleName("nav_panel");
		content_panel.addStyleName("content_panel");

	}

	private void LoadContentPanel() {
		int content_panel_size = RootPanel.get("product-div").getOffsetWidth();
		int content_panel_columns_number = content_panel_size / 270;

		int i = 1;
		int j = 1;

		FlexTable galeryFlexTable = new FlexTable();
		selectedproductlist = getSelectedProductlist();

		for (JSONObject product : selectedproductlist) {
			if (j > content_panel_columns_number) {
				j = 1;
				i++;
			}

			galeryFlexTable.setWidget(i, j, createProductForm(product));

			j++;

		}
		selectedproducttypes.clear();
		content_panel.add(galeryFlexTable);

	}

	private void refrechContentPanel() {
		content_panel.clear();
		int content_panel_size = RootPanel.get("product-div").getOffsetWidth();
		int content_panel_columns_number = content_panel_size / 270;
		int i = 1;
		int j = 1;

		FlexTable galeryFlexTable = new FlexTable();
		selectedproductlist = getSelectedProductlist();

		for (JSONObject product : selectedproductlist) {
			if (j > content_panel_columns_number) {
				j = 1;
				i++;
			}

			galeryFlexTable.setWidget(i, j, createProductForm(product));

			j++;

		}

		content_panel.add(galeryFlexTable);

	}

	private void LoadNavPanel() {
		categorylist = (JSONArray) merchant.get("categorylist");
		StackPanel stackPanel = new StackPanel();

		for (int i = 0; i < categorylist.size(); i++) {
			category = (JSONObject) categorylist.get(i);
			VerticalPanel filtersPanel = new VerticalPanel();
			filtersPanel.setSpacing(4);

			productTypelist = (JSONArray) category.get("productTypelist");
			if (productTypelist.size() > 0) {
				for (int j = 0; j < productTypelist.size(); j++) {
					productType = (JSONObject) productTypelist.get(j);
					final CheckBox checkbox = new CheckBox(productType.get("label1").toString().replaceAll("\"", ""));
					checkbox.getElement().setId(productType.get("id").toString().replaceAll("\"", ""));
					selectedproducttypes.add(checkbox.getElement().getId());
					filtersPanel.add(checkbox);
					checkbox.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
						@Override
						public void onValueChange(ValueChangeEvent<Boolean> event) {
							if (event.getValue()) {
								selectedproducttypes.add(checkbox.getElement().getId());
								refrechContentPanel();
							} else {
								selectedproducttypes.remove(checkbox.getElement().getId());
								refrechContentPanel();
							}

						}
					});
				}
			}

			stackPanel.add(filtersPanel, category.get("label1").toString().replaceAll("\"", ""));

		}
		stackPanel.setWidth("200px");
		nav_panel.add(stackPanel);

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

		layout.setWidget(1, 0, productImg);
		cellFormatter.setColSpan(1, 0, 3);
		cellFormatter.setHorizontalAlignment(1, 0, HasHorizontalAlignment.ALIGN_CENTER);

		// Add some standard form options
		layout.setHTML(2, 0, "<b>Prix   :</b>");
		layout.setWidget(2, 1, new Label(
				product.get("price").toString() + " " + product.get("currency").toString().replaceAll("\"", "")));
		layout.setWidget(2, 2, new Label(
				product.get("promo_price").toString() + " " + product.get("currency").toString().replaceAll("\"", "")));

		// Create the dialog box
		final DialogBox dialogBox = detailDialogBox(product);
		dialogBox.setGlassEnabled(true);
		dialogBox.setAnimationEnabled(true);
		Button detailButton = new Button("<image src='img/loupe.png' width='20px' height='20px'>  Details</image>",
				new ClickHandler() {
					public void onClick(ClickEvent event) {
						dialogBox.center();
						dialogBox.show();
					}
				});

		Button buyButton = new Button(
				"<image src='img/add_panier.png' width='20px' height='20px'>  Ajouter au panier</image>",
				new ClickHandler() {
					public void onClick(ClickEvent event) {
						Widget sender = (Widget) event.getSource();
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
		return decPanel;
	}

	private DialogBox detailDialogBox(JSONObject product) {
		// Create a dialog box and set the caption text
		final DialogBox dialogBox = new DialogBox();
		dialogBox.ensureDebugId("cwDialogBox");
		dialogBox.setText("Details du produit");

		// Create a table to layout the content
		VerticalPanel dialogContents = new VerticalPanel();
		dialogContents.setSpacing(4);
		dialogBox.setWidget(dialogContents);

		// Add some text to the top of the dialog
		// HTML details = new HTML("Details du produit");
		// dialogContents.add(details);
		// dialogContents.setCellHorizontalAlignment(details,
		// HasHorizontalAlignment.ALIGN_CENTER);

		FlexTable layout = new FlexTable();
		layout.setCellSpacing(6);
		layout.setWidth("400px");
		layout.setHTML(2, 0, "<b>Code barre :</b>");
		layout.setWidget(2, 1, new Label(product.get("cab").toString().replaceAll("\"", "")));
		layout.setHTML(3, 0, "<b>Description :</b>");
		layout.setWidget(3, 1, new Label(product.get("desc1").toString().replaceAll("\"", "")));
		layout.setHTML(4, 0, "<b>Unité de vente :</b>");
		layout.setWidget(4, 1, new Label(product.get("unit").toString().replaceAll("\"", "")));
		layout.setHTML(5, 0, "<b>Disponibilité en stock :</b>");
		layout.setWidget(5, 1, new Label(product.get("available").toString().replaceAll("\"", "")));
		layout.setHTML(6, 0, "<b>Quantité en stock :</b>");
		layout.setWidget(6, 1, new Label(product.get("items_number").toString().replaceAll("\"", "")));
		layout.setHTML(7, 0, "<b>Disponibilité en promotion :</b>");
		layout.setWidget(7, 1, new Label(product.get("promotion").toString().replaceAll("\"", "")));
		layout.setHTML(8, 0, "<b>Date de fin de promotion :</b>");
		layout.setWidget(8, 1, new Label(product.get("promo_expiryDate").toString().replaceAll("\"", "")));
		layout.setHTML(9, 0, "<b>Marque :</b>");
		layout.setWidget(9, 1, new Label(product.get("brand").toString().replaceAll("\"", "")));

		dialogContents.add(layout);

		// Add a close button at the bottom of the dialog
		Button closeButton = new Button("Fermer", new ClickHandler() {
			public void onClick(ClickEvent event) {
				dialogBox.hide();
			}
		});
		dialogContents.add(closeButton);
		if (LocaleInfo.getCurrentLocale().isRTL()) {
			dialogContents.setCellHorizontalAlignment(closeButton, HasHorizontalAlignment.ALIGN_LEFT);

		} else {
			dialogContents.setCellHorizontalAlignment(closeButton, HasHorizontalAlignment.ALIGN_RIGHT);
		}

		// Return the dialog box
		return dialogBox;
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

	private JSONObject getSelectedProductById(String id) {
		for (JSONObject product : selectedproductlist) {
			if (id.equals(product.get("id").toString().replaceAll("\"", ""))) {
				return product;
			}

		}

		return null;

	}

}
