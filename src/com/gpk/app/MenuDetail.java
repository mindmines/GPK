package com.gpk.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.gpk.app.model.MenuListModel;
import com.gpk.app.model.SelectedMenuListModel;
import com.loopj.android.image.SmartImageView;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class MenuDetail extends Fragment {

	private ListView lv_menuList;
	private String resObjectid;
	private String menuId;
	private String menuName;
	private ArrayList<MenuListModel> menuListparse = new ArrayList<MenuListModel>();
	private MenuListModel listmodel;
	
	SelectedMenuListModel objseletedmenuList;
	
	private String resName;
	private String resImage;
	private String resAddres;
	private SmartImageView res_image;
	private TextView res_name;
	private TextView tv_address;
	private TextView tv_catg;
	private Button notifCount;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		GpkApp.restrauntOnCreate = false;
		GpkApp.BasketonCreate = false;
		View rootView = inflater.inflate(R.layout.activity_menu_detail, container, false);
		setHasOptionsMenu(true);
		setMenuVisibility(true);
		
		resObjectid = GpkApp.menu_resObjectid;
		menuId = GpkApp.menu_menuId;
		menuName = GpkApp.menu_menuName;
		resName = GpkApp.menu_resName;
		resImage = GpkApp.menu_resImage;
		resAddres = GpkApp.menu_resAddres;
		
		res_image = (SmartImageView)rootView.findViewById(R.id.img_restaurant);
		res_name = (TextView)rootView.findViewById(R.id.res_name);
		tv_address = (TextView)rootView.findViewById(R.id.tv_address);
		tv_catg = (TextView)rootView.findViewById(R.id.tv_catg);
		lv_menuList = (ListView)rootView.findViewById(R.id.lv_menuList);
		
		tv_catg.setText(menuName);
		res_name.setText(resName);
		res_image.setImageUrl(resImage);
		tv_address.setText(resAddres);
		
		
		getMenuListDetails();
		return rootView;
	}
	  

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
			MenuItem item = menu.findItem(R.id.kart);
		    MenuItemCompat.setActionView(item, R.layout.feed_update_count); 
		    notifCount = (Button) MenuItemCompat.getActionView(item);
		    notifCount.setText(Integer.toString(GpkApp.mNotifCount));
		    super.onCreateOptionsMenu(menu,inflater);
	}
	
	/**
	 * GetMenuName Method
	 */
	public void getMenuListDetails(){
		
		ParseQuery<ParseObject> query = ParseQuery.getQuery("MenuList");
		query.whereEqualTo("restaurentid", resObjectid);
		query.findInBackground(new FindCallback<ParseObject>() {
		    public void done(List<ParseObject> objects, ParseException e) {
		        if (e == null) {
		        	for(int i=0;i<objects.size();i++)
			           {
		        		listmodel = new MenuListModel();
		        		String matchMenuId = objects.get(i).getString("menuid");
		        		if(matchMenuId.equals(menuId)){
		        			//Menu Name
			        		listmodel.setMenuName(objects.get(i).getString("name"));
			        		Log.e("menu name", ""+objects.get(i).getString("name"));
			        		//price
			        		listmodel.setPrice(objects.get(i).getInt("price"));
			        		Log.e("price", ""+objects.get(i).getInt("price"));
							 //Menu Id
			        		listmodel.setMenuId(objects.get(i).getObjectId());
			        		Log.e("check menu id", objects.get(i).getObjectId());
			        		menuListparse.add(listmodel);
		        		}
			           }
		        } else {
		            Log.d("score", "Error: " + e.getMessage());
		        }
		        
		        if(menuListparse.size()>0){
		        	lv_menuList.setAdapter(new MenuListAdapter(getActivity(), android.R.layout.simple_list_item_1, menuListparse)); 	
		        	int size = menuListparse.size();
					GpkApp.selected_items = new Boolean[size];
					Arrays.fill(GpkApp.selected_items, Boolean.FALSE);
		        }
		    }
		});
	}
	
	/**
	 * MenuListAdapter populates the items list with name and price
	 * @author Ankur
	 *
	 */
	private class MenuListAdapter extends ArrayAdapter<MenuListModel>{
		Context context;
		ArrayList<MenuListModel> list_model = new ArrayList<MenuListModel>();
		MenuListModel rm;
		
		public MenuListAdapter(Context context, 
				int textViewResourceId, ArrayList<MenuListModel> menuListparse) {
					super(context, textViewResourceId, menuListparse);
					this.context = context;
					this.list_model = (ArrayList<MenuListModel>)menuListparse;
		}
		
		@SuppressLint("ViewHolder")
		@Override
		  public View getView(final int position, View convertView, ViewGroup parent) {
			rm = list_model.get(position);
		    LayoutInflater inflater = (LayoutInflater) context
		        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		    View rowView = inflater.inflate(R.layout.menu_list_row, parent, false);
		    TextView tv_menu_name = (TextView)rowView.findViewById(R.id.tv_menu);
		    TextView tv_menu_price = (TextView)rowView.findViewById(R.id.tv_price);
		    final ImageView checkimg = (ImageView)rowView.findViewById(R.id.img_checked);
		    tv_menu_name.setText(rm.getMenuName());
		    tv_menu_price.setText("Rs. "+Integer.toString(rm.getPrice()));
		   
		    try{
		    		 if(GpkApp.selectedMap!=null ){
		    			// Get a set of the entries
					        Set<Entry<String, Boolean>> setMap = GpkApp.selectedMap.entrySet();
					        // Get an iterator
					        Iterator<Entry<String,  Boolean>> iteratorMap = setMap.iterator();
					        while(iteratorMap.hasNext()) {
						             Map.Entry<String, Boolean> entry =
						                     (Map.Entry<String,Boolean>) iteratorMap.next();
						             String key = entry.getKey();
						             Boolean values = entry.getValue();
						             if(key.equals(list_model.get(position).getMenuId()) && values ){
						            	 checkimg.setVisibility(View.VISIBLE);
						             }
						  }
				    }
		    }catch(Exception e){
		    	e.printStackTrace();
		    }

		    rowView.setOnClickListener(new OnClickListener()
		    {
				@Override
				public void onClick(View v) 
				{
					if (checkimg.getVisibility() == View.VISIBLE) {
						checkimg.setVisibility(View.INVISIBLE);
						GpkApp.selected_items[position] = false;
						if(GpkApp.mNotifCount >0){
							GpkApp.mNotifCount--;
						}
						notifCount.setText(Integer.toString(GpkApp.mNotifCount));
						try{
							String menuid = list_model.get(position).getMenuId();
							Log.e("menuId", menuid);
							String menuIdcheck = GpkApp.selected_menuList.get(position).getMenuId().toString();
							Log.e("checkmenuID", menuIdcheck);
							 objseletedmenuList = new SelectedMenuListModel();
							 
							if(GpkApp.selected_menuList.size()>0 ){
								for(int k=0; k <GpkApp.selected_menuList.size();k++){
									if(GpkApp.selected_menuList.get(k).getMenuId().equals(menuid)){
										GpkApp.selected_menuList.remove(k);
									}
								}
							}
						}catch(Exception e){
							e.printStackTrace();
						}
						GpkApp.selectedMap.put(list_model.get(position).getMenuId(), false);
					}else {
						checkimg.setVisibility(View.VISIBLE);
						GpkApp.selected_items[position] = true;
						GpkApp.mNotifCount++;
						 notifCount.setText(Integer.toString(GpkApp.mNotifCount));
						 objseletedmenuList = new SelectedMenuListModel();
						 objseletedmenuList.setMenuName(list_model.get(position).getMenuName());
						 objseletedmenuList.setPrice(list_model.get(position).getPrice());
						 objseletedmenuList.setMenuId(list_model.get(position).getMenuId());
						 objseletedmenuList.setQuantity(1);
						 
						 GpkApp.selected_menuList.add(objseletedmenuList);
						 GpkApp.selectedMap.put(list_model.get(position).getMenuId(), true);
					}
     			}
			});
		    return rowView;
		  }
	}
}

