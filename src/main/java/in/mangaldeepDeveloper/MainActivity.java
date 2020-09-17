package in.mangaldeepDeveloper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
RecyclerView recyclerView;
WallpaperAdapter wallpaperAdapter;
List<WallpaperModel> wallpaperModelList;
int pageNumber = 1;
Boolean isScrolling = false;
int currentItems, totalItems, scrollOutItems;
String Url = "https://api.pexels.com/v1/curated?page="+pageNumber+"&per_page=80";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        wallpaperModelList = new ArrayList<>();
        wallpaperAdapter = new WallpaperAdapter(this,wallpaperModelList);


        recyclerView.setAdapter(wallpaperAdapter);

        final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItems = gridLayoutManager.getChildCount();
                totalItems = gridLayoutManager.getItemCount();
                scrollOutItems = gridLayoutManager.findFirstVisibleItemPosition();
                if(isScrolling&&(currentItems+scrollOutItems==totalItems)){
                    isScrolling = false;
                    fetchApi();
                }
            }
        });
        fetchApi();
    }

    public void fetchApi() {
        StringRequest request = new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("photos");
                    int length = jsonArray.length();
                    for(int i =0; i< length; i++){
                        JSONObject object = jsonArray.getJSONObject(i);
                        int id= object.getInt("id");
                        JSONObject object1 = object.getJSONObject("src");
                        String originalUrl = object1.getString("original");
                        String mediumurl = object1.getString("medium");
                        WallpaperModel wallpaperModel = new WallpaperModel(id,originalUrl,mediumurl);
                        wallpaperModelList.add(wallpaperModel);
                    }
                    wallpaperAdapter.notifyDataSetChanged();
                    pageNumber++;
                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization","563492ad6f917000010000015b773dd14222478bb4043f0c424efac4");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.search){
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            final EditText editText = new EditText(this);
            editText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            alert.setMessage("Enter Query");
            alert.setTitle("Search Wallpaper");
            alert.setView(editText);
            alert.setPositiveButton("Search", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String query = editText.getText().toString().toLowerCase();
                    Url = "https://api.pexels.com/v1/search/?page="+pageNumber+"&per_page=80&query="+query;
                   
                    wallpaperModelList.clear();
                    fetchApi();
                }
            });
            alert.show();
        }
        return super.onOptionsItemSelected(item);
    }
}