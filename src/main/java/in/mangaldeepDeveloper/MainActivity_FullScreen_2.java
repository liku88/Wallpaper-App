package in.mangaldeepDeveloper;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;

import java.io.IOException;

public class MainActivity_FullScreen_2 extends AppCompatActivity {
 String orginalUrl= "";
 PhotoView photoView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__full_screen_2);
        getSupportActionBar().hide();
        Intent intent = getIntent();
        orginalUrl = intent.getStringExtra("orginalLink");
        photoView = findViewById(R.id.photoView);
        Glide.with(this).load(orginalUrl).into(photoView);

    }

    public void setWallpaper(View view) {
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(this);
        Bitmap bitmap = ((BitmapDrawable)photoView.getDrawable()).getBitmap();
        try {
            wallpaperManager.setBitmap(bitmap);
            Toast.makeText(this,"Wallpaper Succesfully set",Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void downloadWallpaper(View view) {
        DownloadManager downloadManager = (DownloadManager)getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(orginalUrl);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        downloadManager.enqueue(request);
    }
}