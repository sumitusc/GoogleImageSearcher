package com.example.googleimagesearcher;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class SearchOptions extends Activity {
	
	Spinner spnrImageSize;
	Spinner spnrColorFilter;
	Spinner spnrImageType;
	EditText etSiteFilter;
	Button btnSave;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_options);
		
		addListenerOnButton();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_options, menu);
		return true;
	}
	
	public void addListenerOnButton() {
		spnrImageSize = (Spinner) findViewById(R.id.spnrImageSize);
		spnrColorFilter = (Spinner) findViewById(R.id.spnrColorFilter);
		spnrImageType = (Spinner) findViewById(R.id.spnrImageType);
		etSiteFilter = (EditText) findViewById(R.id.etSiteFilter);
		btnSave = (Button) findViewById(R.id.btnSave);
		
		btnSave.setOnClickListener(new OnClickListener() {
			@Override
			  public void onClick(View v) {
				
				String imageSize = String.valueOf(spnrImageSize.getSelectedItem());
				String colorFilter = String.valueOf(spnrColorFilter.getSelectedItem());
				String imageType = String.valueOf(spnrImageType.getSelectedItem());
				String siteFilter = etSiteFilter.getText().toString();
			    
			    SearchOptionsFilters filters = new SearchOptionsFilters(imageSize, colorFilter,imageType, siteFilter);
			    Intent  result =  new Intent();
			    result.putExtra("filters", filters);
			    setResult(RESULT_OK, result);
			    finish();
			  }
		});
	}

}
