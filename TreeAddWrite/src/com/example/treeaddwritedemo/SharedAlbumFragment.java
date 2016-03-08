package com.example.treeaddwritedemo;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import com.example.treeaddwritedemo.adapter.SharedAlbumListViewAdapter;
import com.example.treeaddwritedemo.global.MyApplication;
import com.example.treeaddwritedemo.global.ParseUtil;
import com.example.treeaddwritedemo.global.UrlContent;
import com.example.treeaddwritedemo.httpconnect.NetRequestTask;
import com.example.treeaddwritedemo.httpconnect.NetRequestTask.OnCompleteHandler;
import com.example.treeaddwritedemo.util.CloudAlbumData;
import com.example.treeaddwritedemo.util.Tree;
import com.example.treeaddwritedemo.util.TreeData;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

public class SharedAlbumFragment extends Fragment {

	private static final int CONNECTION_TIMEOUT = 10000;
	private Spinner spinner;
	private ArrayList<String> mItems;

	private ListView sharedalbum_listview;
	// private Thread mThread;

	private static final int MSG_SUCCESS = 0;// 获取信息成功的标识
	private static final int MSG_FAILURE = 1;// 获取信息失败的标识
	private static final int NETWORK_ERROR = 2;// 获取网络失败的标识
	private static final int MESSAGE_NULL = 3; // 账号或密码为空
	private static final int MESSAGE_ERROR = 4; // 账号或密码错误

	private CloudAlbumData mCloudAlbumData = null;
	private TreeData mTreeData = null;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View parentView = inflater.inflate(R.layout.view_photo_sharedalbum, container, false);

		// 初始化控件
		spinner = (Spinner) parentView.findViewById(R.id.sharedalbum_spinner);

		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

				// Tree mTree = mTreeData.getData().get(pos);
				// if (!mTree.getId().equals(""))
				// onCloudAlbumConnectServerJsonHttp(0, mTree.getId());

				onCloudAlbumConnectServerJsonHttp(0, "280");
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// Another interface callback
			}
		});

		sharedalbum_listview = (ListView) parentView.findViewById(R.id.sharedalbum_listview);
		;
		return parentView;
	}

	/**
	 * 为Spinner绑定数据
	 */
	private void spinnerView() {
		// 建立Adapter并且绑定数据源
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,
				mItems);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 绑定 Adapter到控件
		spinner.setAdapter(adapter);
	};

	/**
	 * 为ListView绑定数据
	 */
	private void listView() {
		SharedAlbumListViewAdapter adapter = new SharedAlbumListViewAdapter(getActivity(), mCloudAlbumData.getImgs(),
				R.layout.view_photo_sharedalbum_list_item);
		sharedalbum_listview.setAdapter(adapter);
	};

	/***
	 * 解析云相册json
	 * 
	 */
	private void onCloudAlbumJson(final String mJson) {
		String mCloudAlbumDataString = ParseUtil.onJsonData(mJson);
		if (mCloudAlbumDataString.equals("errCode")) {

		} else {
			mCloudAlbumData = ParseUtil.onJsonCloudAlbumData(mCloudAlbumDataString);
			MyApplication.setCloudalbum_img_path(mCloudAlbumData.getImg_path());
			listView();
		}

	}

	/***
	 * 获取云相册数据
	 * 
	 */
	private void onCloudAlbumConnectServerJsonHttp(int mPage, String mTreeid) {
		
		NetRequestTask mTask = NetRequestTask
				.get(UrlContent.HTTP_CLOUDALBUM_URL + mPage + UrlContent.HTTP_PAT_CLOUDALBUM_URL + mTreeid, null);
		mTask.setOnCompleteHandler(new OnCompleteHandler() {

			@Override
			public void onComplete(final String mJson) {
				Log.v("yanggang", mJson);
				onCloudAlbumJson(mJson);
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
			if (mItems != null) {
				mItems.clear();
			} else {
				mItems = new ArrayList<String>();
			}
			mTreeData = ParseUtil.onJsonTreeData(mTreeDataString);
			if (mTreeData != null & mTreeData.getData().size() > 0) {
				int length = mTreeData.getData().size();
				for (int i = 0; i < length; i++) {
					Tree mTree = mTreeData.getData().get(i);

					if (mTree.getDisplay_name().equals(""))
						mItems.add(mTree.getLabel());
					else
						mItems.add(mTree.getDisplay_name());
				}
				spinnerView();
			}
		}

	}

	/***
	 * 获取树数据
	 * 
	 */

	private void onTreeConnectServerJsonHttp() {
		NetRequestTask mTask = NetRequestTask.get(UrlContent.HTTP_TREE_URL, null);
		mTask.setOnCompleteHandler(new OnCompleteHandler() {

			@Override
			public void onComplete(final String mJson) {
				if (mJson.equals("errNet")) {
					if (MyApplication.getCloudalbum_img_path() != null
							&& MyApplication.getCloudalbum_img_path().length() > 0) {
						mCloudAlbumData.setImg_path(MyApplication.getCloudalbum_img_path());
						listView();
					}
				} else
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

	// private Handler mHandler = new Handler() {
	// // 重写handleMessage()方法，此方法在UI线程运行
	// @Override
	// public void handleMessage(Message msg) {
	// switch (msg.what) {
	// // 如果成功，则跳转
	// case MSG_SUCCESS:
	// String name = msg.obj.toString();
	// break;
	// // 否则提示失败
	// case MSG_FAILURE:
	// String name1 = msg.obj.toString();
	// Log.v("yanggang", name1);
	// break;
	// }
	// }
	// };

	// public static String doHttpGet(String serverURL) throws Exception {
	// HttpParams httpParameters = new BasicHttpParams();
	// HttpConnectionParams.setConnectionTimeout(httpParameters,
	// CONNECTION_TIMEOUT);
	// HttpConnectionParams.setSoTimeout(httpParameters, CONNECTION_TIMEOUT);
	// HttpClient hc = new DefaultHttpClient();
	// HttpGet get = new HttpGet(serverURL);
	// get.addHeader("Content-Type",
	// "application/x-www-form-urlencoded;charset=utf-8");// 在头文件中设置转码
	// get.addHeader("X-EL-KEY", "12345678");
	// get.setParams(httpParameters);
	// HttpResponse response = null;
	// try {
	// response = hc.execute(get);
	// } catch (UnknownHostException e) {
	// throw new Exception("Unable to access " + e.getLocalizedMessage());
	// } catch (SocketException e) {
	// throw new Exception(e.getLocalizedMessage());
	// }
	// int sCode = response.getStatusLine().getStatusCode();
	// if (sCode == HttpStatus.SC_OK) {
	// return EntityUtils.toString(response.getEntity());
	// } else{
	// throw new Exception("StatusCode is " + sCode);
	// }
	// }
	// Runnable runnable = new Runnable() {
	// // 重写run()方法，此方法在新的线程中运行
	// @Override
	// public void run() {
	// try {
	//
	// String mJson =
	// doHttpGet("http://115.28.155.160/api/resource/list?page=0&tree_id=280");
	// JSONObject mJsonObj = new JSONObject(mJson);
	// int errCode = mJsonObj.optInt("errCode");
	// if(errCode == 0)
	// mHandler.obtainMessage(MSG_SUCCESS,
	// mJsonObj.optInt("data")).sendToTarget();
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// }
	// };

}
