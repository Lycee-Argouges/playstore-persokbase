package fr.argouges.persokbase.ui.activity;
//Cette classe est la page BONNES PRATIQUES
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

public class ActivityFragment extends Fragment {

    private ActivityViewModel activityViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        activityViewModel =
                new ViewModelProvider(this).get(ActivityViewModel.class);
        View root = inflater.inflate(R.layout.fragment_activity, container, false);
        //final TextView textView = root.findViewById(R.id.text_activity);

        WebView mWebView = (WebView) root.findViewById(R.id.text_kpra);
        mWebView.loadDataWithBaseURL("file:///android_asset/",WebRawView(R.raw.kpra), "text/html", "UTF-8",null);
        WebView mendWebView = (WebView) root.findViewById(R.id.text_kpra_end);
        mendWebView.loadDataWithBaseURL("file:///android_asset/",WebRawView(R.raw.kpra_end), "text/html", "UTF-8",null);

        final Intent appscrolling = new Intent().setClass(getContext(), ScrollingActivity.class);
        //
        //  Ici on définit les boutons qui vont définir les activités
        //
        //Définition du bouton 1
        Button B1_kpra = (Button) root.findViewById(R.id.B1_kpra);
        B1_kpra.setOnClickListener(new View.OnClickListener() {@Override
        public void onClick(View view) {
            appscrolling.putExtra("ActivB",R.raw.kpra_b1);
            startActivity(appscrolling);}
        });
        //Définition du bouton 2
        Button B2_kpra = (Button) root.findViewById(R.id.B2_kpra);
        B2_kpra.setOnClickListener(new View.OnClickListener() {@Override
        public void onClick(View view) {
            appscrolling.putExtra("ActivB",R.raw.kpra_b2);
            startActivity(appscrolling);}
        });
        //Définition du bouton 3
        Button B3_kpra = (Button) root.findViewById(R.id.B3_kpra);
        B3_kpra.setOnClickListener(new View.OnClickListener() {@Override
        public void onClick(View view) {
            appscrolling.putExtra("ActivB",R.raw.kpra_b3);
            startActivity(appscrolling);}
        });

        //Définition du bouton b1
        Button Bb1_kpra = (Button) root.findViewById(R.id.Bb1_kpra);
        Bb1_kpra.setOnClickListener(new View.OnClickListener() {@Override
        public void onClick(View view) {
            appscrolling.putExtra("ActivB",R.raw.kpra_bb1);
            startActivity(appscrolling);}
        });
        //Définition du bouton b2
        Button Bb2_kpra = (Button) root.findViewById(R.id.Bb2_kpra);
        Bb2_kpra.setOnClickListener(new View.OnClickListener() {@Override
        public void onClick(View view) {
            appscrolling.putExtra("ActivB",R.raw.kpra_bb2);
            startActivity(appscrolling);}
        });

        activityViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
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
