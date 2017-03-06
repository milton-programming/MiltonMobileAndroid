package edu.milton.miltonmobileandroid.food.meals;

import android.annotation.SuppressLint;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressLint("SimpleDateFormat")
class MealsMenuItem {

	private int numericalID;
	private Date itemDate;
	private String itemName;
	private String itemClass;
	private String itemTime;
	private SimpleDateFormat dateParser;
	private boolean isHeading;

    MealsMenuItem(boolean heading, String itemName) {
        setHeading(heading);
        this.setItemName(itemName);
    }

    MealsMenuItem(boolean heading, JSONObject jobj) {
        setHeading(heading);
        dateParser = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if (!jobj.isNull("id")) {
                setNumericalID(Integer.parseInt(jobj.getString("id")));
            }
			if (!jobj.isNull("mealName")) {
				setItemName(jobj.getString("mealName"));
			}
			if (!jobj.isNull("date")) {
				setItemDate(dateParser.parse(jobj.getString("date")));
			}
			if (!jobj.isNull("mealClass")) {
				setItemClass(jobj.getString("mealClass"));
			}
			if (!jobj.isNull("mealTime")) {
				setItemTime(jobj.getString("mealTime"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void setNumericalID(int numericalID) {
		this.numericalID = numericalID;
	}

	private void setItemDate(Date itemDate) {
		this.itemDate = itemDate;
	}

	String getItemName() {
		return itemName;
	}

	private void setItemName(String itemName) {
		this.itemName = itemName;
	}

	private void setItemClass(String itemClass) {
		this.itemClass = itemClass;
	}

	private void setItemTime(String itemTime) {
		this.itemTime = itemTime;
	}

	boolean isHeading() {
		return isHeading;
	}

	private void setHeading(boolean isHeading) {
		this.isHeading = isHeading;
	}

	
}
