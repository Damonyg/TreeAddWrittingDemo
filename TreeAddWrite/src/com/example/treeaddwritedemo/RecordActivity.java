package com.example.treeaddwritedemo;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.example.treeaddwritedemo.adapter.DragAdapter;
import com.example.treeaddwritedemo.global.MyApplication;
import com.example.treeaddwritedemo.view.DragGridView;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class RecordActivity extends Activity {
	private DragAdapter record_mDragAdapter;

	//baidu
	private LocationClient baduduManager;
	
	private ImageView record_img_voice;// 话筒
	private ImageView record_img_play;// 播放
	private TextView record_textview_voice_tip;// 话筒提示
	private TextView record_textview_record_delete;// 删除提示
	private TextView record_textview_date;// 日期设定
	private EditText record_edittext_location;// 位置设定
	private RelativeLayout record_layout_record;// 录音进度条
	private TextView record_textview_record_tip;
	private ProgressBar record_progressbar;

	private int year, monthOfYear, dayOfMonth;

	private long start_time = 0;
	private long end_time = 0;

	private boolean isRecord = true;// 是否可以录音
	private boolean isStartRecord = false;// 是否开始录音

	private static final int OFFSET = 500; // 进度条出现消失的时间间隔
	private static final int MSG_SHOW_PROGRESS = 1;
	private static final int MSG_CLOSE_PROGRESS = 2;
	private static final int MSG_RECORD_PROGRESS = 3;// 记录时间
	private static final int MSG_START_RECORD = 4;// 开始录音
	private static final int MSG_END_RECORD = 5;// 结束录音
	private static final int MSG_LESS_RECORD = 6;// 录音时间太短

	// 录音文件播放
	private MediaPlayer myPlayer;
	// 录音
	private MediaRecorder myRecorder;
	// 音频文件保存地址
	private String path;
	private String paths = path;
	private File saveFilePath;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_record);

		Calendar calendar = Calendar.getInstance();
		year = calendar.get(Calendar.YEAR);
		monthOfYear = calendar.get(Calendar.MONTH);
		dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

		RelativeLayout RelativeLayout_back = (RelativeLayout) findViewById(R.id.RelativeLayout_back);
		RelativeLayout_back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});

		record_textview_date = (TextView) findViewById(R.id.textview_date);
		record_textview_date.setText(year + "年" + (monthOfYear + 1) + "月" + dayOfMonth+ "日" );
		record_textview_date.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				/**
				 * 实例化一个DatePickerDialog的对象
				 * 第二个参数是一个DatePickerDialog.OnDateSetListener匿名内部类，
				 * 当用户选择好日期点击done会调用里面的onDateSet方法
				 */
				DatePickerDialog datePickerDialog = new DatePickerDialog(RecordActivity.this,
						new DatePickerDialog.OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
						record_textview_date.setText(year + "年" + (monthOfYear + 1) + "月" + dayOfMonth+ "日" );
					}
				}, year, monthOfYear, dayOfMonth);

				datePickerDialog.show();
			}

		});

		record_edittext_location = (EditText) findViewById(R.id.edittext_location);
		
		DragGridView mDragGridView = (DragGridView) findViewById(R.id.photo_gridview);
		record_mDragAdapter = new DragAdapter(this, MyApplication.getmSelectedImage());
		mDragGridView.setAdapter(record_mDragAdapter);
		mDragGridView.setOnItemClickListener(record_gvItemClickListener);

		record_img_voice = (ImageView)

		findViewById(R.id.imageview_voice);
		record_img_play = (ImageView) findViewById(R.id.imageview_play);
		record_textview_voice_tip = (TextView) findViewById(R.id.textview_voice_tip);
		record_textview_record_delete = (TextView) findViewById(R.id.textview_record_delete);
		record_textview_record_delete.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); // 下划线
		record_textview_record_delete.getPaint().setAntiAlias(true);// 抗锯齿
		record_layout_record = (RelativeLayout) findViewById(R.id.layout_process);
		record_textview_record_tip = (TextView) findViewById(R.id.textview_record_tip);
		record_progressbar = (ProgressBar) findViewById(R.id.record_progressbar);

		if (MyApplication.isRecordVoice()) {
			record_img_play.setVisibility(View.VISIBLE);
			record_textview_record_delete.setVisibility(View.VISIBLE);
		} else {
			record_img_voice.setVisibility(View.VISIBLE);
			record_textview_voice_tip.setVisibility(View.VISIBLE);
		}

		record_img_voice.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub

				int action = event.getAction();
				switch (action) {

				case MotionEvent.ACTION_DOWN:
					mCloseRecordHandler.sendEmptyMessageDelayed(MSG_SHOW_PROGRESS, OFFSET);
					start_time = System.currentTimeMillis();
					break;
				case MotionEvent.ACTION_MOVE:
					break;
				case MotionEvent.ACTION_UP:
					end_time = System.currentTimeMillis();
					RecordTime();
					break;
				case MotionEvent.ACTION_CANCEL:
					break;
				}

				return true;
			}
		});

		record_textview_record_delete.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MyApplication.setRecordVoice(false);
				if (saveFilePath != null)
					if (saveFilePath.exists()) {
						saveFilePath.delete();
					}
				record_img_play.setVisibility(View.INVISIBLE);
				record_textview_record_delete.setVisibility(View.INVISIBLE);
				record_img_voice.setVisibility(View.VISIBLE);
				record_textview_voice_tip.setVisibility(View.VISIBLE);
			}
		});
		myPlayer = new MediaPlayer();
		record_img_play.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (!myPlayer.isPlaying()) {
					BitmapDrawable ad_bitmapdrawable = (BitmapDrawable) getResources()
							.getDrawable(R.drawable.record_pause);
					Bitmap ad_bitmap = ad_bitmapdrawable.getBitmap();
					record_img_play.setImageBitmap(ad_bitmap);
					myPlayer.start();

				} else {
					BitmapDrawable ad_bitmapdrawable = (BitmapDrawable) getResources()
							.getDrawable(R.drawable.record_play);
					Bitmap ad_bitmap = ad_bitmapdrawable.getBitmap();
					record_img_play.setImageBitmap(ad_bitmap);
					myPlayer.pause();
				}

			}
		});

		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			try {
				path = Environment.getExternalStorageDirectory().getCanonicalPath().toString() + "/TREERECORDERS";
				File files = new File(path);
				if (!files.exists()) {
					files.mkdir();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		TextView record_okbtn = (TextView) findViewById(R.id.okbtn);
		record_okbtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// 跳转到录音页面
				Intent intent = new Intent(RecordActivity.this, TeamActivity.class);
				startActivity(intent);
			}
		});
		
		startBaidu();

	}

private BDLocationListener mBdLocationListener = new BDLocationListener() {
		
		@Override
		public void onReceiveLocation(BDLocation location) {
			//Receive Location 
			StringBuffer sb = new StringBuffer(256);
			if (location.getLocType() == BDLocation.TypeGpsLocation){
				sb.append(location.getAddrStr());
			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation){
				sb.append(location.getAddrStr());
			}
			record_edittext_location.setText(sb.toString());
			stopBaidu();
		}
	};

	private void startBaidu() {
		if (baduduManager == null) {
			baduduManager = new LocationClient(this);
			//定位的配置
			LocationClientOption option = new LocationClientOption();
			//定位模式选择，高精度、省电、仅设备
			option.setLocationMode(LocationMode.Hight_Accuracy); 
			//定位坐标系类型选取, gcj02、bd09ll、bd09
			option.setCoorType("gcj02"); 
			//定位时间间隔
			option.setScanSpan(1000);
			//选择定位到地址
			option.setIsNeedAddress(true);
			baduduManager.setLocOption(option);
			//注册定位的成功的回调
			baduduManager.registerLocationListener(mBdLocationListener);
		}
		baduduManager.start();
	}
	
	private void stopBaidu() {
		baduduManager.stop();
	}
	
	// 时间处理
	private void RecordTime() {
		isRecord = false;
		record_progressbar.setVisibility(View.INVISIBLE);
		if (end_time - start_time < OFFSET * 4) {
			record_textview_record_tip.setText("说话时间太短!");
			mCloseRecordHandler.sendEmptyMessageDelayed(MSG_LESS_RECORD, OFFSET * 3);
		} else {
			record_textview_record_tip.setText("正在保存录音...");

			mCloseRecordHandler.sendEmptyMessage(MSG_END_RECORD);
		}

	}

	private OnItemClickListener record_gvItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int mposition, long id) {

			if (mposition == MyApplication.getmSelectedImage().size()) {
				finish();
			} else {
				// 跳转到全屏选择
				Intent intent = new Intent(RecordActivity.this, SelectedImgSwitchActivity.class);
				intent.putExtra("position", mposition);
				startActivity(intent);
			}
		}
	};

	private Handler mCloseRecordHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG_SHOW_PROGRESS:
				if (isRecord) {
					record_textview_record_tip.setText("正在录入...");
					isStartRecord = true;
					mCloseRecordHandler.sendEmptyMessageDelayed(MSG_START_RECORD, OFFSET * 2);
				} else {
					record_textview_record_tip.setText("说话时间太短!");
					record_progressbar.setVisibility(View.INVISIBLE);
				}
				record_textview_record_tip.setVisibility(View.VISIBLE);
				record_layout_record.setVisibility(View.VISIBLE);
				break;
			case MSG_CLOSE_PROGRESS:
				record_textview_record_tip.setVisibility(View.INVISIBLE);
				record_progressbar.setVisibility(View.INVISIBLE);
				record_layout_record.setVisibility(View.INVISIBLE);

				isRecord = true;

				if (MyApplication.isRecordVoice()) {
					record_img_play.setVisibility(View.VISIBLE);
					record_textview_record_delete.setVisibility(View.VISIBLE);
					record_img_voice.setVisibility(View.INVISIBLE);
					record_textview_voice_tip.setVisibility(View.INVISIBLE);
				}
				mCloseRecordHandler.removeMessages(MSG_SHOW_PROGRESS);
				mCloseRecordHandler.removeMessages(MSG_CLOSE_PROGRESS);
				mCloseRecordHandler.removeMessages(MSG_RECORD_PROGRESS);
				break;
			case MSG_RECORD_PROGRESS:
				int progess = record_progressbar.getProgress() + 1;
				record_progressbar.setProgress(progess);

				if (isRecord) {
					record_progressbar.setVisibility(View.VISIBLE);
					mCloseRecordHandler.sendEmptyMessageDelayed(MSG_RECORD_PROGRESS, 200);

				}

				break;
			case MSG_START_RECORD:
				startRecorder();
				record_progressbar.setProgress(0);
				mCloseRecordHandler.sendEmptyMessage(MSG_RECORD_PROGRESS);
				break;
			case MSG_END_RECORD:
				saveRecorder();
				MyApplication.setRecordVoice(true);

				mCloseRecordHandler.sendEmptyMessageDelayed(MSG_CLOSE_PROGRESS, OFFSET * 3);
				break;
			case MSG_LESS_RECORD:
				if (isStartRecord) {
					deleteRecorder();
					mCloseRecordHandler.sendEmptyMessage(MSG_CLOSE_PROGRESS);
				} else {
					mCloseRecordHandler.sendEmptyMessage(MSG_CLOSE_PROGRESS);
				}
				break;

			}
		}
	};

	// 关闭录音
	public void CloseRecorder() {
		if (myRecorder != null) {
			myRecorder.stop();
			myRecorder.release();
			myRecorder = null;
		}
	}

	// 删除不符合条件的录音
	public void deleteRecorder() {
		CloseRecorder();
		if (saveFilePath != null)
			if (saveFilePath.exists())
				saveFilePath.delete();
	}

	// 保存录音
	public void saveRecorder() {
		CloseRecorder();

		try {
			if (myPlayer != null) {
				myPlayer.reset();
				myPlayer.setDataSource(paths);
				myPlayer.prepare();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 开始录音
	public void startRecorder() {
		try {
			if (myRecorder == null) {
				myRecorder = new MediaRecorder();
				// 从麦克风源进行录音
				myRecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
				// 设置输出格式
				myRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
				// 设置编码格式
				myRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
			}
			paths = path + "/temp.amr";
			if (saveFilePath != null)
				if (saveFilePath.exists())
					saveFilePath.delete();
			saveFilePath = new File(paths);
			myRecorder.setOutputFile(saveFilePath.getAbsolutePath());
			saveFilePath.createNewFile();
			myRecorder.prepare();
			// 开始录音
			myRecorder.start();
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		if (record_mDragAdapter != null) {
			record_mDragAdapter.notifyDataSetChanged();
		}
	}

	@Override
	protected void onDestroy() {
		// 释放资源
		if (myPlayer != null) {
			if (myPlayer.isPlaying()) {
				myPlayer.stop();
				myPlayer.release();
			}
			myPlayer.release();
			myPlayer = null;
		}
		if (myRecorder != null) {
			myRecorder.release();
			myRecorder = null;
		}
		super.onDestroy();
	}
}
