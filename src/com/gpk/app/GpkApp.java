package com.gpk.app;

import java.util.ArrayList;
import java.util.HashMap;

import com.gpk.app.model.SelectedMenuListModel;
import com.parse.Parse;
import com.parse.ParseACL;

import android.app.Application;

/**
 * GpkApp contains the constants used in the application
 * @author Ankur
 *
 */
public class GpkApp extends Application {
	
	private static String APPLICATIONID = "VjJ0ibNQIVZ9F3Q0eFfCIt4Ct4zxrVSUQshb72VB";
	private static String CLIENTKEY = "GbJU7aXE5SplglGmwxOb4P4rzHC5Cygqqcg3hTYv";
	
	public static final String CLASS_RESTAURENTS = "Restaurents";
	public static final String CLASS_MENULIST = "MenuList";
	public static final String CLASS_MENU = "Menu";
	
	
	public static Double user_latitude;
	public static Double user_longitude;
	public static Boolean selected_items[];
	
	public static  int mNotifCount = 0;
	public static int countCopy = 0;
	
	public static String objectId;
	public static String resName;
	public static String res_image;
	public static String resAddress;
	
	public static boolean restrauntOnCreate;
	public static boolean BasketonCreate;
	
	public static String menu_resObjectid;
	public static String menu_menuId;
	public static String menu_menuName;
	public static String menu_resName;
	public static String menu_resImage;
	public static String menu_resAddres;
	

	
	public static ArrayList<SelectedMenuListModel> selected_menuList = new ArrayList<SelectedMenuListModel>();
	public static HashMap<String, ArrayList<SelectedMenuListModel>>  selectedHashMap= new HashMap<String, ArrayList<SelectedMenuListModel>>();
	 public static HashMap<String, Boolean> selectedMap = new HashMap<String, Boolean>();
	
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		Parse.initialize(this, APPLICATIONID , CLIENTKEY);
		
		ParseACL defaultACL = new ParseACL();
		 defaultACL.setPublicReadAccess(true);
		 defaultACL.setPublicWriteAccess(true);
		 ParseACL.setDefaultACL(defaultACL, true);
	}

}
