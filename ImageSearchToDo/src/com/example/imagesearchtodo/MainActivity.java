package com.example.imagesearchtodo;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.example.imagesearchtodo.ImageDisplayActivity;
import com.example.imagesearchtodo.ImageResult;
import com.example.imagesearchtodo.ImageResultArrayAdapter;
import com.loopj.android.http.*;


public class MainActivity extends Activity {
	
	Button btnSearch;
	EditText etSearch;
	GridView gvImage;
	ArrayList<ImageResult> imageResults = new ArrayList<ImageResult>();
	ImageResultArrayAdapter imageAdapter;
	String size, color, type, site;
	//protected static final int MENU_SETTING = Menu.FIRST;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		imageAdapter = new ImageResultArrayAdapter(this,imageResults);
		gvImage.setAdapter(imageAdapter);
		gvImage.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View parent, int position, long rowId) {
				Intent i = new Intent(getApplicationContext(), ImageDisplayActivity.class);
				ImageResult imageResult = imageResults.get(position);
				i.putExtra("result", imageResult);
				startActivity(i);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//menu.add(0, MENU_SETTING, 0, "Setting");
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	public void goToSearch(View v){
		String query = etSearch.getText().toString();
		System.out.println("https://ajax.googleapis.com/ajax/services/search/images?rsz=8&" + "start=" + 0
				+ "&v=1.0&q=" + Uri.encode(query) + getSearchURL());
		Toast.makeText(this, "Searching for " + query, Toast.LENGTH_SHORT).show();
		AsyncHttpClient client = new AsyncHttpClient();
		// https://ajax.googleapis.com/ajax/services/search/images?q=Android&v=1.0
		client.get("https://ajax.googleapis.com/ajax/services/search/images?rsz=8&" + "start=" + 0
				+ "&v=1.0&q=" + Uri.encode(query) + getSearchURL(), new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject response) {
				JSONArray imageJsonResults = null;
				try {
					imageJsonResults = response.getJSONObject("responseData").getJSONArray(
							"results");
					imageResults.clear();
					imageAdapter.addAll(ImageResult.fromJSONArray(imageJsonResults));
					Log.d("DEBUG", imageResults.toString());
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch (item.getItemId()){
		case R.id.action_settings:
			Intent intent = new Intent();
			Bundle bundle = new Bundle();
			intent.putExtras(bundle);
			intent.setClass(MainActivity.this,ImageSetting.class);
			startActivityForResult(intent,0);
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		switch(resultCode){
		case RESULT_OK:
			Bundle bundle = data.getExtras();
			size = bundle.getString("size");
			color = bundle.getString("color");
			type = bundle.getString("type");
			site = bundle.getString("site");
			Log.d("aaaaaaaa", size);
			Log.d("aaaaaaaa", color);
			Log.d("aaaaaaaa", type);
			Log.d("aaaaaaaa", site);
			break;
		default:
			break;
		}
	}
	
	public void initView(){
		btnSearch = (Button)findViewById(R.id.btnSearch);
		gvImage = (GridView)findViewById(R.id.gvImage);
		etSearch = (EditText)findViewById(R.id.etSearch);
		size="small";
		color="black";
		type="faces";
		site="google.com";
	}
	
	private String getSearchURL(){
		StringBuffer result= new StringBuffer();
		if (size.equals("small")){
			result.append("&imgsz=icon");
		}if (size.equals("medium")){
			result.append("&imgsz=medium");
		}if (size.equals("large")){
			result.append("&imgsz=xxlarge");
		}if (size.equals("extra-large")){
			result.append("&imgsz=huge");
		}if (color.equals("black")){
			result.append("&imgcolor=black");
		}if (color.equals("blue")){
			result.append("&imgcolor=blue");
		}if (color.equals("brown")){
			result.append("&imgcolor=brown");
		}if (color.equals("gray")){
			result.append("&imgcolor=gray");
		}if (color.equals("green")){
			result.append("&imgcolor=green");
		}if (type.equals("faces")){
			result.append("&imgtype=face");
		}if (type.equals("photo")){
			result.append("&imgtype=photo");
		}if (type.equals("clip")){
			result.append("&imgtype=clipart");
		}if (type.equals("art")){
			result.append("&imgtype=lineart");
		}if (type.equals("line art")){
			result.append("&imgtype=lineart");
		}
		result.append("&as_sitesearch="+site);
		return result.toString();
	}

}
