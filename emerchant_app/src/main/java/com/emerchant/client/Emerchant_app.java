package com.emerchant.client;

import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.sksamuel.jqm4gwt.ScriptUtils;
/**
 * Entry point classes define <code>onModuleLoad()</code>.
 * 
 * 
 */

public class Emerchant_app implements EntryPoint {
	
	
	/** This is the entry point method. */
	@Override
	public void onModuleLoad() {
		ScriptUtils.waitJqmLoaded(new Callback<Void, Throwable>() {

			@Override
			public void onSuccess(Void result) {
				runExamples();
			}

			@Override
			public void onFailure(Throwable reason) {
				Window.alert(reason.getMessage());
			}
		});
	}

	private static void runExamples() {		
		MainPage mainPage = new MainPage();
        mainPage.parseData();		
	}
	
	

	
}
