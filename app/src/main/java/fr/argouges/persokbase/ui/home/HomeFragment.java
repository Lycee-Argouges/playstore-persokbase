package fr.argouges.persokbase.ui.home;
//Cette classe est la page ACCEUIL
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.webkit.WebViewCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import fr.argouges.persokbase.MainActivity;
import fr.argouges.persokbase.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private DownloadImageTask downloadBitmap ;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        //final TextView textView = root.findViewById(R.id.text_home);

        ImageView IMG_Home = (ImageView) root.findViewById(R.id.IMG_acceuil);
        downloadBitmap = (DownloadImageTask) new DownloadImageTask(IMG_Home)
                .execute("https://argouges.ent.auvergnerhonealpes.fr/lectureFichiergw.do?ID_FICHIER=3");

        WebView mWebView = (WebView) root.findViewById(R.id.text_home);
        mWebView.loadDataWithBaseURL("file:///android_asset/", WebRawView(R.raw.acceuil), "text/html", "UTF-8", null);

        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                mWebView.reload();
                //textView.setText(s);
            }
        });
        return root;
    }

    private String WebRawView(int RawFile) {
        String lecture = null;
        int value;
        InputStream is = this.getResources().openRawResource(RawFile);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuffer lu = new StringBuffer();
        try {
            while ((value = br.read()) != -1)
                lu.append((char) value);
            lecture = lu.toString();
            if (br != null)
                br.close();
        }  catch (IOException e) {
            e.printStackTrace();
        }
        return lecture;
    }

    public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}