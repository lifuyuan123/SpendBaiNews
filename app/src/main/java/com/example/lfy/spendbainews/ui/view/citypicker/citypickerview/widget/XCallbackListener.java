package com.example.lfy.spendbainews.ui.view.citypicker.citypickerview.widget;


public abstract class XCallbackListener {

	protected abstract void callback(Object... obj);

	public void call(Object... obj) {
			callback(obj);
	}

}
