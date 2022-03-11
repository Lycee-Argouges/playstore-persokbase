package fr.argouges.persokbase.ui.support;
//Cette classe est la page VULNERABILITES
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

public class SupportFragment extends Fragment {

    private SupportViewModel supportViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        supportViewModel =
                new ViewModelProvider(this).get(SupportViewModel.class);
        View root = inflater.inflate(R.layout.fragment_support, container, false);
        //final TextView textView = root.findViewById(R.id.text_support);

        WebView mWebView = (WebView) root.findViewById(R.id.text_kvul);
        mWebView.loadDataWithBaseURL("file:///android_asset/",WebRawView(R.raw.kvul), "text/html", "UTF-8",null);
        WebView mendWebView = (WebView) root.findViewById(R.id.text_kvul_end);
        mendWebView.loadDataWithBaseURL("file:///android_asset/",WebRawView(R.raw.kvul_end), "text/html", "UTF-8",null);

        final Intent appscrolling = new Intent().setClass(getContext(), ScrollingActivity.class);
        //
        //  Ici on définit les boutons qui vont définir les activités
        //
        //Définition du bouton 1
        Button B1_kvul = (Button) root.findViewById(R.id.B1_kvul);
        B1_kvul.setOnClickListener(new View.OnClickListener() {@Override
        public void onClick(View view) {
            appscrolling.putExtra("ActivB",R.raw.kvul_b1);
            appscrolling.putExtra("HelpP","Poste 204");
            startActivity(appscrolling);}
        });
        //Définition du bouton 2
        Button B2_kvul = (Button) root.findViewById(R.id.B2_kvul);
        B2_kvul.setOnClickListener(new View.OnClickListener() {@Override
        public void onClick(View view) {
            appscrolling.putExtra("ActivB",R.raw.kvul_b2);
            appscrolling.putExtra("HelpP","Poste 204");
            startActivity(appscrolling);}
        });
        //Définition du bouton 3
        Button B3_kvul = (Button) root.findViewById(R.id.B3_kvul);
        B3_kvul.setOnClickListener(new View.OnClickListener() {@Override
        public void onClick(View view) {
            appscrolling.putExtra("ActivB",R.raw.kvul_b3);
            appscrolling.putExtra("HelpP","Poste 204");
            startActivity(appscrolling);}
        });
        //Définition du bouton 4
        Button B4_kvul = (Button) root.findViewById(R.id.B4_kvul);
        B4_kvul.setOnClickListener(new View.OnClickListener() {@Override
        public void onClick(View view) {
            appscrolling.putExtra("ActivB",R.raw.kvul_b4);
            appscrolling.putExtra("HelpP","Poste 204");
            startActivity(appscrolling);}
        });

        supportViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
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
