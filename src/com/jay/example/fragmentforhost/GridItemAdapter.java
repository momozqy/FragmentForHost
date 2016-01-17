package com.jay.example.fragmentforhost;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GridItemAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private List<GridItem> gridItemList;
	private List<String> textList;
	public int pix;

	static class ViewHolder {
		public ImageView image;
		public TextView title;
		// public TextView time;
	}

	public GridItemAdapter(int[] images, String[] texts, Context context,
			int width) {
		super();
		float f = context.getResources().getDisplayMetrics().density;
		pix = (int) (((float) width - f * 4) / 3.0 + 0.5f);
		gridItemList = new ArrayList<GridItem>();
		textList = new ArrayList<String>();
		inflater = LayoutInflater.from(context);
		for (int i = 0; i < images.length; i++) {
			GridItem picture = new GridItem(images[i]);
			gridItemList.add(picture);
		}
		for (int i = 0; i < texts.length; i++) {
			textList.add(texts[i]);
		}
	}

	public int getCount() {
		if (null != gridItemList) {
			return gridItemList.size();
		} else {
			return 0;
		}
	}

	public Object getItem(int position) {
		return gridItemList.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (convertView == null) {
			LayoutParams params = new LayoutParams(pix, pix);

			convertView = inflater.inflate(R.layout.grid_item, null);
			convertView.setLayoutParams(params);
			viewHolder = new ViewHolder();
			viewHolder.title = (TextView) convertView.findViewById(R.id.title);
			viewHolder.image = (ImageView) convertView.findViewById(R.id.image);
			// viewHolder.time = (TextView) convertView
			// .findViewById(R.id.description);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.title.setText(textList.get(position));
		// viewHolder.time.setText(gridItemList.get(position).getTime());
		viewHolder.image.setImageResource(gridItemList.get(position)
				.getImageId());
		return convertView;
	}
}