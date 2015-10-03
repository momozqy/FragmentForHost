package com.jay.example.fragmentforhost;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class Fragment1 extends Fragment {
	MainActivity activity;
	private GridView gridView;
	private String[] titles = new String[] { "美女卷珠帘", "美女回眸", "美女很有趣", "美女醉酒",
			"美女微笑", "美女如脱兔", "美女柳叶弯眉" };
	private String[] description = new String[] { "啦啦啦", "嘎嘎嘎", "哇哇哇", "喵喵喵",
			"刚刚刚", "当当当", "咔咔咔" };
	private int[] images = { R.drawable.ic_launcher, R.drawable.ic_launcher,
			R.drawable.ic_launcher, R.drawable.ic_launcher,
			R.drawable.ic_launcher, R.drawable.ic_launcher,
			R.drawable.ic_launcher };
	FragmentManager fManager;
	FragmentTransaction ft;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fg1, container, false);
		activity = (MainActivity) this.getActivity();
		fManager = activity.getSupportFragmentManager();
		gridView = (GridView) view.findViewById(R.id.gridVew);
		GridItemAdapter adapter = new GridItemAdapter(titles, images,
				description, this.getActivity());
		gridView.setAdapter(adapter);

		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				ft = fManager.beginTransaction();
				if (activity.fg4 == null) {
					activity.fg4 = new Fragment4();
				}
				ft.add(R.id.content, activity.fg4);
				ft.hide(activity.fg1);
				ft.show(activity.fg4);
				ft.commit();
			}
		});
		return view;
	}
}
