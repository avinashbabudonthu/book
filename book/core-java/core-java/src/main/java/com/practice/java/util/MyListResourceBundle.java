package com.practice.java.util;

import java.util.ListResourceBundle;

public class MyListResourceBundle extends ListResourceBundle {

	private Object[][] contents = { { "name", "jack" }, { "course", "Java" }, { "grade", 3.9 } };

	@Override
	protected Object[][] getContents() {
		return contents;
	}

}
