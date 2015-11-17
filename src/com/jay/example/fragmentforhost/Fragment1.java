package com.jay.example.fragmentforhost;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class Fragment1 extends Fragment {
	MainActivity activity;
	private GridView gridView;
	private int[] images = { R.drawable.anim, R.drawable.apparel,
			R.drawable.auto, R.drawable.beauty, R.drawable.electrical,
			R.drawable.food, R.drawable.forestry, R.drawable.house,
			R.drawable.jewelry, R.drawable.sports, R.drawable.custommade,
			R.drawable.nocustommade };
	FragmentManager fManager;
	FragmentTransaction ft;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fg1, container, false);
		activity = (MainActivity) this.getActivity();
		fManager = activity.getSupportFragmentManager();
		gridView = (GridView) view.findViewById(R.id.gridVew);
		WindowManager windowManager = getActivity().getWindowManager();
		Display display = windowManager.getDefaultDisplay();
		@SuppressWarnings("deprecation")
		int wh = display.getWidth();
		GridItemAdapter adapter = new GridItemAdapter(images,
				this.getActivity(), wh);
		gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				ft = fManager.beginTransaction();
				switch(position){
					case 0:
						if(activity.fg5==null){
							activity.fg5 = new Animal();
							ft.add(R.id.content, activity.fg5);
						}
						ft.hide(activity.fg1);
						ft.show(activity.fg5);
						ft.commit();
						break;
					case 1:
						break;
					case 2:
						break;
					case 3:
						break;
					case 4:
						break;
					case 5:
						break;
					case 6:
						break;
					case 7:
						break;
					case 8:
						break;
					case 9:
						break;
					case 10:
						if(activity.fg6==null){
							activity.fg6 = new CustomMade();
							ft.add(R.id.content, activity.fg6);
						}
						else{
							ft.remove(activity.fg6);
							activity.fg6 = new CustomMade();
							ft.add(R.id.content, activity.fg6);
						}
						
						ft.hide(activity.fg1);
						ft.show(activity.fg6);
						ft.commit();
						break;
					case 11:
						break;
					default:
						break;
				}
				/*
				if (activity.fg4 == null) {
					activity.fg4 = new Fragment4();
				}
				ft.add(R.id.content, activity.fg4);
				ft.hide(activity.fg1);
				ft.show(activity.fg4);
				ft.commit();*/
			}
		});
		return view;
	}
}
