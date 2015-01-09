package com.gpk.app;

import android.app.AlertDialog;
import android.app.FragmentManager.OnBackStackChangedListener;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class BaseActivity extends ActionBarActivity {
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
	setContentView(R.layout.base);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

			MenuInflater inflater = getMenuInflater();
		    inflater.inflate(R.menu.main, menu);
		    MenuItem item = menu.findItem(R.id.kart);
		    MenuItemCompat.setActionView(item, R.layout.feed_update_count); 
		    return super.onCreateOptionsMenu(menu);
	}
		
 
		 /**
		  * On selecting action bar icons
		  * */
		@Override
		 public boolean onOptionsItemSelected(MenuItem item) {
		     // Take appropriate action for each action item click
		     switch (item.getItemId()) {
		     case R.id.kart_item_count:
		    	 callBasketFragment();
		         return true;
		     default:
		         return super.onOptionsItemSelected(item);
		     }
		 }

		/**
		 * Show Alert on back press
		 */
		private void showAlert(){
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					this);
				alertDialogBuilder
					.setMessage("Are you sure you'd like to change restaurants? you current order will be lost.")
					.setCancelable(false)
					.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							// current activity
							BaseActivity.this.finish();
							GpkApp.mNotifCount = 0;
							GpkApp.countCopy = 0;
							GpkApp.selectedMap.clear();
							GpkApp.selected_menuList.clear();
							GpkApp.selectedHashMap.clear();
						}
					  })
					.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							// the dialog box and do nothing
							dialog.cancel();
						}
					});
					// create alert dialog
					AlertDialog alertDialog = alertDialogBuilder.create();
					// show it
					alertDialog.show();
		}
		
		/**
		 * {@link OnBackStackChangedListener}
		 */
		@Override
		public void onBackPressed() {
			if(GpkApp.restrauntOnCreate){
				showAlert();
			}else if (GpkApp.BasketonCreate) {
				callMenuDetailFragment();
			}else {
				callRestaurantDetailsFragment();
			}
		}
		
		/*
		 * callRestaurantDetailsFragment sets the RestaurantDetails class on the current fragment 
		 */
		private void callRestaurantDetailsFragment() {
		      FragmentManager fm = getSupportFragmentManager();
		      FragmentTransaction fragmentTransaction = fm.beginTransaction();
		      fragmentTransaction.addToBackStack(null);
		      Fragment fr = new RestaurantDetails();
		      fragmentTransaction.replace(R.id.mainFragment, fr);
		      fragmentTransaction.commit();
		 }
		
		
		/*
		 * callBasketFragment sets the Basket class on the current fragment 
		 */
		private void callBasketFragment() {
			  FragmentManager fm = getSupportFragmentManager();
		      FragmentTransaction fragmentTransaction = fm.beginTransaction();
		      fragmentTransaction.addToBackStack(null);
		      Fragment fr = new Basket();
		      fragmentTransaction.replace(R.id.mainFragment, fr);
		      fragmentTransaction.commit();
		 }
		
		/*
		 * callMenuDetailFragment sets the MenuDetail class on the current fragment 
		 */
		private void callMenuDetailFragment() {
			  FragmentManager fm = getSupportFragmentManager();
		      FragmentTransaction fragmentTransaction = fm.beginTransaction();
		      fragmentTransaction.addToBackStack(null);
		      Fragment fr = new MenuDetail();
		      fragmentTransaction.replace(R.id.mainFragment, fr);
		      fragmentTransaction.commit();
		 }
		
}
