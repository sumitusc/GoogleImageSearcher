package com.example.googleimagesearcher;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

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
import android.widget.SearchView.OnSuggestionListener;
import android.widget.Toast;

public class ImageSearch extends Activity {
	
	private final int REQUEST_CODE = 20;
	private final int MAX_PAGES = 9;
	
	EditText etQuery;
	Button btnSearch;
	GridView gvResults;
	
	ArrayList<ImageResult> imageResults = new ArrayList<ImageResult>();
	ImageResultArrayAdapter imageAdapter;
	ImageQueryFilters imageQueryFilters = new ImageQueryFilters();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_search);
		
		setupViews();
		imageAdapter = new ImageResultArrayAdapter(this, imageResults);
		gvResults.setAdapter(imageAdapter);
		gvResults.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> adapter, View parent, int position, long rowId) {
				Intent i = new Intent(getApplicationContext(),ImageDisplayActivity.class);
				ImageResult imageResult = imageResults.get(position);
				i.putExtra("result", imageResult);
				startActivity(i);
			}
		});
		
		gvResults.setOnScrollListener(new EndlessScrollListener() {
			@Override
		    public void onLoadMore(int page, int totalItemsCount) {
				customLoadMoreDataFromApi(page); 
			}
		});
		
	}
	
	public void customLoadMoreDataFromApi(int offset) {
		if(offset > MAX_PAGES){
			return;
		}
		imageQueryFilters.setStart(offset);
		String url = generateUrl();
		//Log.d("DEBUG", "New URL="+url);
		
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(url, new JsonHttpResponseHandler(){
			
			@Override
			public void onSuccess(JSONObject response){
				//Log.d("DEBUG", "On Success");
				JSONArray imageJsonResults = null;
				try{
					//Log.d("DEBUG", "Got Response");
					imageJsonResults = response.getJSONObject("responseData").getJSONArray("results");
					//imageResults.clear();
					imageAdapter.addAll(ImageResult.fromJSONArray(imageJsonResults));
					//Log.d("DEBUG", "MORE RESULTS="+imageResults.toString());
				}catch(JSONException e){
					e.printStackTrace();
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.image_search, menu);
		return true;
	}
	
	public void setupViews(){
		etQuery = (EditText) findViewById(R.id.etQuery);
		btnSearch = (Button) findViewById(R.id.btnSearch);
		gvResults = (GridView) findViewById(R.id.gvResults);
	}
	
	public void onImageSearch(View v){
		String query = etQuery.getText().toString();
		
		if(query == null || query.isEmpty()){
			Toast.makeText(this, "No search expression found!", Toast.LENGTH_SHORT).show();
			return;
		}
		
		imageQueryFilters.setSearchExpression(query);
		
		String url = generateUrl();
		//Log.d("DEBUG", url);
		
		AsyncHttpClient client = new AsyncHttpClient();
		//Query String Example:
		//https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=android
		//String baseUrl = "https://ajax.googleapis.com/ajax/services/search/images?rsz=8&";
		//String url = baseUrl + "start=" + 0 + "&v=1.0&q=" + Uri.encode(query);
		//Log.d("DEBUG", "url: "+url);
		client.get(url, new JsonHttpResponseHandler(){
			
			@Override
			public void onSuccess(JSONObject response){
				//Log.d("DEBUG", "On Success");
				JSONArray imageJsonResults = null;
				try{
					//Log.d("DEBUG", "Got Response");
					imageJsonResults = response.getJSONObject("responseData").getJSONArray("results");
					imageResults.clear();
					imageAdapter.addAll(ImageResult.fromJSONArray(imageJsonResults));
					//Log.d("DEBUG", imageResults.toString());
				}catch(JSONException e){
					e.printStackTrace();
				}
			}
		});
			
	}
	
	private String generateUrl(){
				
		String BASE_URL = "https://ajax.googleapis.com/ajax/services/search/images?";
		String version = "v="+imageQueryFilters.getVersion();
		String start = "&start="+imageQueryFilters.getStart();
		String rsz = "&rsz="+imageQueryFilters.getResultsPerPage();
		String expression = "&q="+Uri.encode(imageQueryFilters.getSearchExpression());
		String imgcolor = (imageQueryFilters.getImageColor() != null && !imageQueryFilters.getImageColor().isEmpty()) ? "&imgcolor="+imageQueryFilters.getImageColor(): "";
		String imgsz = (imageQueryFilters.getImageSize() != null && !imageQueryFilters.getImageSize().isEmpty()) ? "&imgsz="+imageQueryFilters.getImageSize(): "";
		String imgtype = (imageQueryFilters.getImageType() != null && !imageQueryFilters.getImageType().isEmpty()) ? "&imgtype="+imageQueryFilters.getImageType(): "";
		String as_sitesearch = (imageQueryFilters.getSiteSearchFilter() != null && !imageQueryFilters.getSiteSearchFilter().isEmpty()) ? "&as_sitesearch="+imageQueryFilters.getSiteSearchFilter(): "";

		String query = BASE_URL
				+ version
				+ expression
				+ start
				+ rsz
				+ imgcolor
				+ imgsz
				+ imgtype
				+ as_sitesearch;
				
		return query;
	}
	
	public void onOptionsAction(MenuItem mi){
		//Toast.makeText(this, "Checked Changed", Toast.LENGTH_LONG).show();
		Intent i = new Intent(this, SearchOptions.class);
		startActivityForResult(i,REQUEST_CODE);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
			SearchOptionsFilters filters = (SearchOptionsFilters) data.getSerializableExtra("filters");
			Toast.makeText(this, "Image Size: "+filters.getImageSize()
					+"\nColor Filter: "+filters.getColorFilter()
					+"\nImage Type: "+filters.getImageType()
					+"\nSite Filter: "+filters.getSiteFilter(), 
					Toast.LENGTH_SHORT).show();
			
			
			imageQueryFilters.setImageSize(filters.getImageSize());
			imageQueryFilters.setImageColor(filters.getColorFilter());
			imageQueryFilters.setImageType(filters.getImageType());
			imageQueryFilters.setSiteSearchFilter(filters.getSiteFilter());
			
			//Log.d("DEBUG","SIZE="+imageQueryFilters.getImageSize());
			//Log.d("DEBUG","COLOR="+imageQueryFilters.getImageColor());
			//Log.d("DEBUG","TYPE="+imageQueryFilters.getImageType());
			//Log.d("DEBUG","SITE="+imageQueryFilters.getSiteSearchFilter());
			
		}
	}

}
