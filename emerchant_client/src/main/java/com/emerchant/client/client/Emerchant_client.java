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
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Emerchant_client implements EntryPoint {
	private String json_response;

	// Layout components
	private DockLayoutPanel main_panel = new DockLayoutPanel(Unit.EM);
	private VerticalPanel content_panel = new VerticalPanel();
	private VerticalPanel nav_panel = new VerticalPanel();

	// Navigation bar components
	private Button nav_button1 = new Button("Catégorie 1");
	private Button nav_button2 = new Button("Catégorie 2");
	private Button nav_button3 = new Button("Catégorie 3");
	private Button nav_button4 = new Button("Catégorie 4");
	private Button nav_button5 = new Button("Catégorie 5");

	// Content Panel Components
	private FlexTable stocksFlexTable = new FlexTable();
	private HorizontalPanel addPanel = new HorizontalPanel();
	private TextBox newSymbolTextBox = new TextBox();
	private Button addStockButton = new Button("Add");
	private Label lastUpdatedLabel = new Label();

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
					Window.alert("no");

				}

				@Override
				public void onResponseReceived(Request request, Response response) {
					json_response = response.getText();
					JSONValue value = JSONParser.parseStrict(json_response);
					JSONObject merchant = (JSONObject) value.isObject();
					
					//JavaScriptObject merchant = parseJson(json_response);
					Window.alert("Affichage d'objet: " + merchant.get("emaillist"));
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
		nav_panel.add(nav_button1);
		nav_panel.add(nav_button2);
		nav_panel.add(nav_button3);
		nav_panel.add(nav_button4);
		nav_panel.add(nav_button5);

		// Create table for stock data.
		stocksFlexTable.setText(0, 0, "Symbol");
		stocksFlexTable.setText(0, 1, "Price");
		stocksFlexTable.setText(0, 2, "Change");
		stocksFlexTable.setText(0, 3, "Remove");

		// Add styles to elements in the stock list table.
		stocksFlexTable.getRowFormatter().addStyleName(0, "watchListHeader");
		stocksFlexTable.addStyleName("watchList");
		stocksFlexTable.getCellFormatter().addStyleName(0, 1, "watchListNumericColumn");
		stocksFlexTable.getCellFormatter().addStyleName(0, 2, "watchListNumericColumn");
		stocksFlexTable.getCellFormatter().addStyleName(0, 3, "watchListRemoveColumn");

		// Assemble Add Stock panel.
		addPanel.add(newSymbolTextBox);
		addPanel.add(addStockButton);
		addPanel.addStyleName("addPanel");

		// Assemble Main panel.
		content_panel.add(stocksFlexTable);
		content_panel.add(addPanel);
		content_panel.add(lastUpdatedLabel);

		main_panel.addNorth(new HTML("header"), 10);
		main_panel.addSouth(new HTML("footer"), 2);
		main_panel.addWest(nav_panel, 10);
		main_panel.add(content_panel);

		// Associate the Main panel with the HTML host page.
		RootLayoutPanel.get().add(main_panel);

	}

	public static <T extends JavaScriptObject> T parseJson(String jsonStr) {
		return JsonUtils.safeEval(jsonStr);
	}

}
