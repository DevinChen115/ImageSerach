package com.example.imagesearchtodo;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class ImageSetting extends Activity {
	
	Spinner spImageSize, spColorFilter, spImageType;
	Button btnSave;
	EditText etSite;
	String imageSizeSetting, colorFilterSetting, imageTypesetting, siteFilterSetting;
	Intent intent;
	Bundle bundle;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_setting);
		init();
		spImageSize.setOnItemSelectedListener(new ItemSelected());
		spColorFilter.setOnItemSelectedListener(new ItemSelected());
		spImageType.setOnItemSelectedListener(new ItemSelected());
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.image_setting, menu);
		return true;
	}
	
	public void init(){
		spImageSize = (Spinner) findViewById (R.id.spImageSize);
		spColorFilter = (Spinner) findViewById (R.id.spColorFilter);
		spImageType = (Spinner) findViewById (R.id.spImageType);
		btnSave = (Button) findViewById (R.id.btnSave);
		etSite = (EditText) findViewById (R.id.etSiteFilter);
		imageSizeSetting="small";
		colorFilterSetting="black";
		imageTypesetting="faces";
		siteFilterSetting="google.com";
		intent = this.getIntent();

	}
	
	public void saveSetting(View v){		
		siteFilterSetting = etSite.getText().toString();
		bundle = intent.getExtras();
		bundle.putString("size", imageSizeSetting);
		bundle.putString("color", colorFilterSetting);
		bundle.putString("type", imageTypesetting);
		bundle.putString("site", siteFilterSetting);
		intent.putExtras(bundle);

		ImageSetting.this.setResult(RESULT_OK,intent);
		ImageSetting.this.finish();
	}
	
	
	class ItemSelected implements OnItemSelectedListener{

		@Override
		public void onItemSelected(AdapterView<?> parent, View v, int pos,
				long id) {
			// TODO Auto-generated method stub
				switch (parent.getId()){
				case R.id.spImageSize:
					imageSizeSetting = parent.getItemAtPosition(pos).toString();
					break;
				case R.id.spColorFilter:
					colorFilterSetting = parent.getItemAtPosition(pos).toString();
					break;
				case R.id.spImageType:
					imageTypesetting =  parent.getItemAtPosition(pos).toString();
					break;
				default:
					break;
				}
				

		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
	}

}
