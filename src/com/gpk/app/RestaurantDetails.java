package com.gpk.app;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.gpk.app.model.MenuModel;
import com.loopj.android.image.SmartImageView;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class RestaurantDetails extends Fragment implements OnItemClickListener{
	private SmartImageView res_image;
	private TextView res_name;
	private String resName = null;
	private String resObjectid = null;
	private String resImage = null;
	private ListView lv_menus;
	private ArrayList<MenuModel> menu_list = new ArrayList<MenuModel>();
	private String resAddres = null;
	private TextView tv_address;
	MenuModel objMenumodel;
	private Button notifCount;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		GpkApp.restrauntOnCreate = true;
		GpkApp.BasketonCreate = false;
		
		View rootView = inflater.inflate(R.layout.restraunt_detail, container, false);
		
		setHasOptionsMenu(true);
		setMenuVisibility(true);
		
		
		res_image = (SmartImageView)rootView.findViewById(R.id.img_restaurant);
		res_name = (TextView)rootView.findViewById(R.id.res_name);
		lv_menus = (ListView)rootView.findViewById(R.id.lv_menus);
		tv_address = (TextView)rootView.findViewById(R.id.tv_address);
		
		resObjectid = GpkApp.objectId;
		resName = GpkApp.resName;
		resImage = GpkApp.res_image;
		resAddres  = GpkApp.resAddress;
		
		res_image.setImageUrl(resImage);
		res_name.setText(resName);
		tv_address.setText(resAddres);


		menu_list.clear();
		
		getMenuName();
		
		lv_menus.setOnItemClickListener(this);
		return rootView;
	}
	
	
	/**
	 * GetMenuName Method
	 */
	public void getMenuName(){
		
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Menu");
		query.whereEqualTo("restaurentid", resObjectid);
		query.findInBackground(new FindCallback<ParseObject>() {
		    public void done(List<ParseObject> objects, ParseException e) {
		        if (e == null) {
		        	for(int i=0;i<objects.size();i++)
			           {
		        		objMenumodel = new MenuModel();
		        		
		        		//Menu Name
						 objMenumodel.setMenuName(objects.get(i).getString("name"));
						 
						 //Menu Id
						 objMenumodel.setMenuId(objects.get(i).getObjectId());
						 
						 //Add to list
						 menu_list.add(objMenumodel);
						 Log.e("nitish", ""+menu_list.get(i).getMenuName());
			           }
		        } else {
		            Log.d("tag", "Error: " + e.getMessage());
		        }
		        
		        if(objects.size() == menu_list.size()){
					lv_menus.setAdapter(new RestaurantListAdapter(getActivity(), android.R.layout.simple_list_item_1, menu_list));
				}else {
					lv_menus.setAdapter(new RestaurantListAdapter(getActivity(), android.R.layout.simple_list_item_1, menu_list));
				}
		    }
		});
	}
	
	

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		String resobjectId = resObjectid;
		String menuId = menu_list.get(position).getMenuId();
	    String menuName = menu_list.get(position).getMenuName();
	    
	    FragmentManager fm = getActivity().getSupportFragmentManager();
		 FragmentTransaction ft = fm.beginTransaction();
		    Fragment llf = new MenuDetail();
		    ft.replace(R.id.mainFragment, llf);
		   
		    
		    GpkApp.menu_resObjectid =resobjectId;
		    GpkApp.menu_menuId = menuId;
		    GpkApp.menu_menuName = menuName;
		    GpkApp.menu_resAddres = resAddres;
		    GpkApp.menu_resName = resName;
		    GpkApp.menu_resImage = resImage;
		    
		   
		    ft.addToBackStack(null);
		    ft.commit();
	}

	
	private class RestaurantListAdapter extends ArrayAdapter<MenuModel>{

		Context context;
		ArrayList<MenuModel> list_model = new ArrayList<MenuModel>();
		MenuModel rm;
		public RestaurantListAdapter(Context context, 
				int textViewResourceId, ArrayList<MenuModel> menu_list) {
			super(context, textViewResourceId, menu_list);
			this.context = context;
			this.list_model = (ArrayList<MenuModel>)menu_list;
		}
		
		
		@SuppressLint("ViewHolder")
		@Override
		  public View getView(int position, View convertView, ViewGroup parent) {
			rm = list_model.get(position);
		    LayoutInflater inflater = (LayoutInflater) context
		        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		    View rowView = inflater.inflate(R.layout.resdetailsrow, parent, false);
		   
		    TextView tv_menu_name = (TextView)rowView.findViewById(R.id.tv_menu);
		    tv_menu_name.setText(rm.getMenuName());
		    
		    return rowView;
		  }
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
	
		
		    MenuItem item = menu.findItem(R.id.kart);
		    MenuItemCompat.setActionView(item, R.layout.feed_update_count); 
		    notifCount = (Button) MenuItemCompat.getActionView(item);
		    notifCount.setText(Integer.toString(GpkApp.mNotifCount));
		    super.onCreateOptionsMenu(menu,inflater);
	}
	
}
