package com.example.treeaddwritedemo;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;

import com.example.treeaddwritedemo.adapter.TeamAdapter;
import com.example.treeaddwritedemo.global.MyApplication;
import com.example.treeaddwritedemo.global.ParseUtil;
import com.example.treeaddwritedemo.global.UrlContent;
import com.example.treeaddwritedemo.httpconnect.NetRequestTask;
import com.example.treeaddwritedemo.httpconnect.NetRequestTask.OnCompleteHandler;
import com.example.treeaddwritedemo.util.TreeCategory;
import com.example.treeaddwritedemo.util.TreeData;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TeamActivity extends Activity {

	private ListView team_listview;
	private TeamAdapter team_adapter;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_team);

		initView();
	}

	private void initView() {
		RelativeLayout RelativeLayout_back = (RelativeLayout) findViewById(R.id.RelativeLayout_back);
		RelativeLayout_back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});

		team_listview = (ListView) findViewById(R.id.listView_tree);

		TextView photo_okbtn = (TextView) findViewById(R.id.okbtn);
		photo_okbtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				onCloudAlbumConnectServerJsonHttp();

			}
		});
	}
	

	/***
	 * 提交数据
	 * 
	 */
	private void onCloudAlbumConnectServerJsonHttp() {
		Map<String, String> mParams = new LinkedHashMap<String, String>();
		mParams.put("title", "title");
		mParams.put("publish_date", "2016年3月7日");
		mParams.put("content", "content");
		mParams.put("location", "location");
		mParams.put("lat", "32.123");
		mParams.put("lng", "12.3312");
		Bundle b = new Bundle();
		b.putString("tree_id","280");
		b.putString("cate_id","500");
		b.putString("subcate_id","84");
		mParams.put("trees", b.toString());
		mParams.put("images", "{}");

		NetRequestTask mTask = NetRequestTask.put(UrlContent.HTTP_WRITEING_PUT, mParams);
		mTask.setOnCompleteHandler(new OnCompleteHandler() {

			@Override
			public void onComplete(final String mJson) {
				Log.v("yanggang", mJson);
			}
		});
	}

	

	/***
	 * 解析类目json
	 * 
	 */
	private void onTreeCategoryJson(final String mJson) {
		String mTreeDataString = ParseUtil.onJsonData(mJson);
		// Log.v("yanggang", mTreeDataString);
		ArrayList<TreeCategory> mTreeCategory = new ArrayList<TreeCategory>();
		if (mTreeDataString.equals("errCode")) {

		} else {
			try {

				JSONArray mJsonAry = new JSONArray(mTreeDataString);
				if (mJsonAry != null && mJsonAry.length() > 0) {
					int length = mJsonAry.length();
					for (int i = 0; i < length; i++) {
						mTreeCategory.add(ParseUtil.onJsonTreeCategory(mJsonAry.optJSONObject(i)));
					}
				}
				// Log.v("yanggang", "1");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				// Log.v("yanggang","2");
			}
			if (mTreeCategory != null && mTreeCategory.size() > 0) {
				team_adapter = new TeamAdapter(this, mTreeCategory, R.layout.view_team_list_item);
				team_listview.setAdapter(team_adapter);
			}
		}

	}

	/***
	 * 获取类目数据
	 * 
	 */

	private void onTreeCategoryConnectServerJsonHttp() {
		Map<String, String> mParams = new LinkedHashMap<String, String>();
		mParams.put(UrlContent.HTTP_HEADER_NAME, UrlContent.HTTP_HEADER_KEY);// cloudalbum
		NetRequestTask mTask = NetRequestTask.get(UrlContent.HTTP_ALL_CATEGORY_URL, mParams);
		mTask.setOnCompleteHandler(new OnCompleteHandler() {

			@Override
			public void onComplete(final String mJson) {
				onTreeCategoryJson(mJson);
			}
		});
	}

	/***
	 * 解析树json
	 * 
	 */
	private void onTreeJson(final String mJson) {
		String mTreeDataString = ParseUtil.onJsonData(mJson);
		if (mTreeDataString.equals("errCode")) {

		} else {

			TreeData mTreeData = ParseUtil.onJsonTreeData(mTreeDataString);
			if (mTreeData != null & mTreeData.getData().size() > 0) {
				MyApplication.setmTreeData(mTreeData);
				onTreeCategoryConnectServerJsonHttp();
			}

		}

	}

	/***
	 * 获取树数据
	 * 
	 */

	private void onTreeConnectServerJsonHttp() {
		Map<String, String> mParams = new LinkedHashMap<String, String>();
		mParams.put(UrlContent.HTTP_HEADER_NAME, UrlContent.HTTP_HEADER_KEY);// cloudalbum
		NetRequestTask mTask = NetRequestTask.get(UrlContent.HTTP_TREE_URL, mParams);
		mTask.setOnCompleteHandler(new OnCompleteHandler() {

			@Override
			public void onComplete(final String mJson) {
				onTreeJson(mJson);
			}
		});
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		onTreeConnectServerJsonHttp();
		// onCloudAlbumConnectServerJsonHttp(0, 280);
	}
}
