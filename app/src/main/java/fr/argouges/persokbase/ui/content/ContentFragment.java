package fr.argouges.persokbase.ui.content;
//Cette classe est la page RESSOURCES
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import fr.argouges.persokbase.R;
import fr.argouges.persokbase.ScrollingActivity;

public class ContentFragment extends Fragment {

    private ContentViewModel contentViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        contentViewModel =
                new ViewModelProvider(this).get(ContentViewModel.class);
        View root = inflater.inflate(R.layout.fragment_content, container, false);
        //final TextView textView = root.findViewById(R.id.text_content);


        WebView mWebView = (WebView) root.findViewById(R.id.text_kres);
        mWebView.loadDataWithBaseURL("file:///android_asset/",WebRawView(R.raw.kres), "text/html", "UTF-8",null);
        WebView mWebViewSub1 = (WebView) root.findViewById(R.id.text_kres_sub1);
        mWebViewSub1.loadDataWithBaseURL("file:///android_asset/",WebRawView(R.raw.kres_sub1), "text/html", "UTF-8",null);
        WebView mWebViewSub2 = (WebView) root.findViewById(R.id.text_kres_sub2);
        mWebViewSub2.loadDataWithBaseURL("file:///android_asset/",WebRawView(R.raw.kres_sub2), "text/html", "UTF-8",null);
        WebView mWebViewSub3 = (WebView) root.findViewById(R.id.text_kres_sub3);
        mWebViewSub3.loadDataWithBaseURL("file:///android_asset/",WebRawView(R.raw.kres_sub3), "text/html", "UTF-8",null);
        WebView mWebViewSub4 = (WebView) root.findViewById(R.id.text_kres_sub4);
        mWebViewSub4.loadDataWithBaseURL("file:///android_asset/",WebRawView(R.raw.kres_sub4), "text/html", "UTF-8",null);
        WebView mendWebView = (WebView) root.findViewById(R.id.text_kres_end);
        mendWebView.loadDataWithBaseURL("file:///android_asset/",WebRawView(R.raw.kres_end), "text/html", "UTF-8",null);

        //WebView mWebViewEnd = (WebView) root.findViewById(R.id.text_kres_end);
        //mWebViewEnd.loadData(WebRawView(R.raw.kres_end), "text/html", "UTF-8");

        final Intent appscrolling = new Intent().setClass(getContext(), ScrollingActivity.class);
        //
        //  Ici on définit les boutons qui vont définir les activités
        //
        //Définition du bouton 1
        Button B1_kres = (Button) root.findViewById(R.id.B1_kres);
        B1_kres.setOnClickListener(new View.OnClickListener() {@Override
        public void onClick(View view) {
            appscrolling.putExtra("ActivB",R.raw.kres_b1);
            startActivity(appscrolling);}
        });
        //Définition du bouton 2
        Button B2_kres = (Button) root.findViewById(R.id.B2_kres_sub1);
        B2_kres.setOnClickListener(new View.OnClickListener() {@Override
        public void onClick(View view) {
            appscrolling.putExtra("ActivB",R.raw.kres_b2);
            startActivity(appscrolling);}
        });
        //Définition du bouton 3
        Button B3_kres = (Button) root.findViewById(R.id.B3_kres_sub2);
        B3_kres.setOnClickListener(new View.OnClickListener() {@Override
        public void onClick(View view) {
            appscrolling.putExtra("ActivB",R.raw.kres_b3);
            startActivity(appscrolling);}
        });
        //Définition du bouton 4
        Button B4_kres = (Button) root.findViewById(R.id.B4_kres_sub3);
        B4_kres.setOnClickListener(new View.OnClickListener() {@Override
        public void onClick(View view) {
            appscrolling.putExtra("ActivB",R.raw.kres_b4);
            startActivity(appscrolling);}
        });
        //Définition du bouton 5
        Button B5_kres = (Button) root.findViewById(R.id.B5_kres_sub4);
        B5_kres.setOnClickListener(new View.OnClickListener() {@Override
        public void onClick(View view) {
            appscrolling.putExtra("ActivB",R.raw.kres_b5);
            startActivity(appscrolling);}
        });

        contentViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
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
}
