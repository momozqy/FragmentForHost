package com.jay.example.fragmentforhost;

import android.content.Intent;
import android.net.Uri;
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
import android.widget.Toast;

public class Fragment1 extends Fragment {
	MainActivity activity;
	private GridView gridView;
	private int[] images = { R.drawable.anim, R.drawable.plant,
			R.drawable.soil, R.drawable.custommade, R.drawable.nocustommade,
			R.drawable.tongbu, R.drawable.download, R.drawable.more,
			R.drawable.none9, R.drawable.none10, R.drawable.none11,
			R.drawable.none12 };
	private String[] texts = { "野生动物", "林中和林下植物", "土壤微生物", "非定制", "定制", "同步",
			"下载上传中心", "敬请期待", "", "", "", "" };
	// private List<String>
	FragmentManager fManager;
	FragmentTransaction ft;

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
		GridItemAdapter adapter = new GridItemAdapter(images, texts,
				this.getActivity(), wh);
		gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				ft = fManager.beginTransaction();
				switch (position) {
				case 0:
					if (activity.fg5 != null) {
						ft.remove(activity.fg5);
					}
					activity.fg5 = new Wild_animal();
					ft.add(R.id.content, activity.fg5);
					ft.hide(activity.fg1);
					ft.show(activity.fg5);
					ft.commit();
					break;
				case 1:
					if (activity.plant != null) {
						ft.remove(activity.plant);
					}
					activity.plant = new Plant_resources();
					ft.add(R.id.content, activity.plant);
					ft.hide(activity.fg1);
					ft.show(activity.plant);
					ft.commit();
					break;
				case 2:
					if (activity.soil != null) {
						ft.remove(activity.soil);
					}
					activity.soil = new Soil_microbe();
					ft.add(R.id.content, activity.soil);
					ft.hide(activity.fg1);
					ft.show(activity.soil);
					ft.commit();
					break;
				case 3:
					if (activity.non != null) {
						ft.remove(activity.non);
					}
					activity.non = new NonCustomMade();
					ft.add(R.id.content, activity.non);
					ft.hide(activity.fg1);
					ft.show(activity.non);
					ft.commit();
					break;
				case 4:
					if (activity.fg6 == null) {
						activity.fg6 = new CustomMade();
						ft.add(R.id.content, activity.fg6);
					} else {
						ft.remove(activity.fg6);
						activity.fg6 = new CustomMade();
						ft.add(R.id.content, activity.fg6);
					}

					ft.hide(activity.fg1);
					ft.show(activity.fg6);
					ft.commit();
					break;
				case 5:
					showFileChooser();
					break;
				case 6:
					Uri uri  = Uri.parse("http://202.204.121.64");
					Intent it = new Intent(Intent.ACTION_VIEW,uri);
					activity.startActivity(it);
					break;
				case 7:
					break;
				case 8:
					break;
				case 9:
					break;
				case 10:
					break;
				case 11:
					break;
				default:
					break;
				}
				/*
				 * if (activity.fg4 == null) { activity.fg4 = new Fragment4(); }
				 * ft.add(R.id.content, activity.fg4); ft.hide(activity.fg1);
				 * ft.show(activity.fg4); ft.commit();
				 */
			}
		});
		return view;
	}

	private static final int FILE_SELECT_CODE = 88;

	/** 调用文件选择软件来选择文件 **/
	private void showFileChooser() {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType("*/*"); // */*
		// intent.addCategory(Intent.ACTION_DEFAULT); //CATEGORY_OPENABLE
		try {
			getActivity().startActivityForResult(
					Intent.createChooser(intent, "请选择要同步的Execl文件"),
					FILE_SELECT_CODE);
		} catch (android.content.ActivityNotFoundException ex) {
			// Potentially direct the user to the Market with a Dialog
			Toast.makeText(getActivity(), "请安装文件管理器", Toast.LENGTH_SHORT)
					.show();
		}
	}

}
