package com.emerchant.client.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
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
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.StackPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Emerchant_client implements EntryPoint {
	private String json_response;
	private JSONObject merchant;

	// Layout components
	private DockLayoutPanel main_panel = new DockLayoutPanel(Unit.EM);
	private VerticalPanel content_panel = new VerticalPanel();
	private VerticalPanel nav_panel = new VerticalPanel();

	
	
	
	public void onModuleLoad() {

		Button submit = new Button("JSON");
		submit.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				parseData();
			}
		});

		RootPanel.get("stockList").add(submit);

		// LoadMainPage();
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

		main_panel.addNorth(new HTML("header"), 10);
		main_panel.addSouth(new HTML("footer"), 2);
		main_panel.addWest(nav_panel, 15);
		main_panel.add(content_panel);

		// Associate the Main panel with the HTML host page.
		RootLayoutPanel.get().add(main_panel);

		nav_panel.addStyleName("nav_panel");
		content_panel.addStyleName("content_panel");

	}

	public static <T extends JavaScriptObject> T parseJson(String jsonStr) {
		return JsonUtils.safeEval(jsonStr);
	}

	private void LoadContentPanel() {

		JSONArray categorylist = (JSONArray) merchant.get("categorylist");
		JSONObject category;
		JSONArray productTypelist;
		JSONObject productType;
		JSONArray productlist;
		JSONObject product;
		FlexTable galeryFlexTable = new FlexTable();

		for (int i = 0; i < categorylist.size(); i++) {
			category = (JSONObject) categorylist.get(i);
			final Label category_label = new Label(category.get("label1").toString());
			productTypelist = (JSONArray) category.get("productTypelist");

			galeryFlexTable.setWidget(i * 12, 0, category_label);

			if (productTypelist.size() > 0) {
				for (int j = 0; j < productTypelist.size(); j++) {
					productType = (JSONObject) productTypelist.get(j);
					final Label productType_label = new Label(productType.get("label1").toString());
					galeryFlexTable.setWidget(i * 12 + 2 * j + 1, 1, productType_label);

					productlist = (JSONArray) productType.get("productlist");

					if (productlist.size() > 0) {
						for (int k = 0; k < productlist.size(); k++) {
							product = (JSONObject) productlist.get(k);
							//final Label product_label = new Label(product.get("label1").toString());
							//galeryFlexTable.setWidget(i * 12 + 2 * j + 2, k+2, product_label);
							galeryFlexTable.setWidget(i * 12 + 2 * j + 2, k+1, createProductForm());		
						}

					}

				}

			}
		}
		content_panel.add(galeryFlexTable);

	}

	@SuppressWarnings("deprecation")
	private void LoadNavPanel() {
		Label category_label = new Label("Nos Categories");
		category_label.addStyleName("category_label");

		FlexTable categoryFlexTable = new FlexTable();

		nav_panel.add(category_label);

		JSONArray categorylist = (JSONArray) merchant.get("categorylist");
		JSONObject category;

		Label nos_promo = new Label("Nos promotions");
		Label nos_contact = new Label("Contactez nous");

		StackPanel stackPanel = new StackPanel();

		for (int i = 0; i < categorylist.size(); i++) {
			category = (JSONObject) categorylist.get(i);
			final Hyperlink nav_link = new Hyperlink(category.get("label1").toString(), "");
			categoryFlexTable.setWidget(i, 1, nav_link);
			nav_link.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					Window.alert("hello " + nav_link.getText());
				}
			});

			nav_link.addStyleName("button_category");
		}

		stackPanel.add(categoryFlexTable, "Nos Categories");
		stackPanel.add(nos_promo, "Nos promotions");
		stackPanel.add(nos_contact, "Contactez nous");

		nav_panel.add(stackPanel);

	}
	
	
	private Widget createProductForm() {
	    // Create a table to layout the form options
	    FlexTable layout = new FlexTable();
	    layout.setCellSpacing(6);
	    layout.setWidth("200px");
	    FlexCellFormatter cellFormatter = layout.getFlexCellFormatter();

	    // Add a title to the form
	    layout.setHTML(0, 0, "Pizza Hawaienne");
	    cellFormatter.setColSpan(0, 0, 2);
	    cellFormatter.setHorizontalAlignment(
	        0, 0, HasHorizontalAlignment.ALIGN_CENTER);
	    
	    Image productImg = new Image();
	    productImg.setUrl("http://www.google.com/images/logo.gif");
	    
	    productImg.addStyleName("productImg");
 
	    layout.setWidget(1, 0, productImg);
	    cellFormatter.setColSpan(1, 0, 2);
	    cellFormatter.setHorizontalAlignment(
	        1, 0, HasHorizontalAlignment.ALIGN_CENTER);
	    
	    // Add some standard form options
	    layout.setHTML(2, 0, "Prix");
	    layout.setWidget(2, 1, new Label("100 MAD"));
	    layout.setHTML(3, 0, "Promotion");
	    layout.setWidget(3, 1, new Label("99 MAD"));

	    // Wrap the contents in a DecoratorPanel
	    DecoratorPanel decPanel = new DecoratorPanel();
	    decPanel.setWidget(layout);
	    return decPanel;
	  }
	
	/*
	private Widget createProductForm() {
	    // Create a table to layout the form options
	    FlexTable layout = new FlexTable();
	    layout.setCellSpacing(6);
	    layout.setWidth("300px");
	    FlexCellFormatter cellFormatter = layout.getFlexCellFormatter();

	    // Add a title to the form
	    layout.setHTML(0, 0, "Saisissez Vos critère de recherche");
	    cellFormatter.setColSpan(0, 0, 2);
	    cellFormatter.setHorizontalAlignment(
	        0, 0, HasHorizontalAlignment.ALIGN_CENTER);

	    // Add some standard form options
	    layout.setHTML(1, 0, "Nom");
	    layout.setWidget(1, 1, new TextBox());
	    layout.setHTML(2, 0, "Description");
	    layout.setWidget(2, 1, new TextBox());

	    // Create some advanced options
	    HorizontalPanel genderPanel = new HorizontalPanel();
	    
	    String[] genderOptions = {"Masculin", "Feminin"};
	    for (int i = 0; i < genderOptions.length; i++) {
	      genderPanel.add(new RadioButton("gender", genderOptions[i]));
	    }
	    Grid advancedOptions = new Grid(2, 2);
	    advancedOptions.setCellSpacing(6);
	    advancedOptions.setHTML(0, 0, "Lieu");
	    advancedOptions.setWidget(0, 1, new TextBox());
	    advancedOptions.setHTML(1, 0, "Sex");
	    advancedOptions.setWidget(1, 1, genderPanel);

	    // Add advanced options to form in a disclosure panel
	    DisclosurePanel advancedDisclosure = new DisclosurePanel(
	        "Critères avances");
	    advancedDisclosure.setAnimationEnabled(true);
	    advancedDisclosure.ensureDebugId("cwDisclosurePanel");
	    advancedDisclosure.setContent(advancedOptions);
	    layout.setWidget(3, 0, advancedDisclosure);
	    cellFormatter.setColSpan(3, 0, 2);

	    // Wrap the contents in a DecoratorPanel
	    DecoratorPanel decPanel = new DecoratorPanel();
	    decPanel.setWidget(layout);
	    return decPanel;
	  }*/

	

}
