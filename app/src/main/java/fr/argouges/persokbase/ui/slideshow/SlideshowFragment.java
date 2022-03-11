package fr.argouges.persokbase.ui.slideshow;
//Cette classe est la page BASE DE CONNAISSANCE
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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

import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import fr.argouges.persokbase.MainActivity;
import fr.argouges.persokbase.R;
import fr.argouges.persokbase.ScrollingActivity;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;

    @SuppressLint("ClickableViewAccessibility")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        //final TextView textView = root.findViewById(R.id.text_slideshow);

        WebView mWebView = (WebView) root.findViewById(R.id.text_kbase);
        mWebView.loadDataWithBaseURL("file:///android_asset/", WebRawView(R.raw.kbase), "text/html", "UTF-8",null);
        WebView mWebViewSub1 = (WebView) root.findViewById(R.id.text_kbase_sub1);
        mWebViewSub1.loadDataWithBaseURL("file:///android_asset/", WebRawView(R.raw.kbase_sub1), "text/html", "UTF-8",null);
        WebView mWebViewSub2 = (WebView) root.findViewById(R.id.text_kbase_sub2);
        mWebViewSub2.loadDataWithBaseURL("file:///android_asset/", WebRawView(R.raw.kbase_sub2), "text/html", "UTF-8",null);
        WebView mWebViewEnd = (WebView) root.findViewById(R.id.text_kbase_end);
        mWebViewEnd.loadDataWithBaseURL("file:///android_asset/", WebRawView(R.raw.kbase_end), "text/html", "UTF-8",null);

        final Intent appscrolling = new Intent().setClass(getContext(), ScrollingActivity.class);
        //
        //  Ici on définit les boutons qui vont définir les activités
        //
        //Définition du bouton 1
        Button B1_kbase = (Button) root.findViewById(R.id.B1_kbase);
        B1_kbase.setOnClickListener(new View.OnClickListener() {@Override
        public void onClick(View view) {
                appscrolling.putExtra("ActivB",R.raw.b1kbase);
                appscrolling.putExtra("HelpB","une panne d'alimentation");
                startActivity(appscrolling);}
        });
        //Définition du bouton 2
        Button B2_kbase = (Button) root.findViewById(R.id.B2_kbase);
        B2_kbase.setOnClickListener(new View.OnClickListener() {@Override
        public void onClick(View view) {
            appscrolling.putExtra("ActivB",R.raw.b2kbase);
            appscrolling.putExtra("HelpB","une panne de réseau");
            startActivity(appscrolling);}
        });
        //Définition du bouton 3
        Button B3_kbase = (Button) root.findViewById(R.id.B3_kbase);
        B3_kbase.setOnClickListener(new View.OnClickListener() {@Override
        public void onClick(View view) {
            appscrolling.putExtra("ActivB",R.raw.b3kbase);
            appscrolling.putExtra("HelpB","une panne matérielle");
            startActivity(appscrolling);}
        });
        //Définition du bouton 4
        Button B4_kbase = (Button) root.findViewById(R.id.B4_kbase);
        B4_kbase.setOnClickListener(new View.OnClickListener() {@Override
        public void onClick(View view) {
            appscrolling.putExtra("ActivB",R.raw.b4kbase);
            appscrolling.putExtra("HelpB","une panne de démarrage");
            startActivity(appscrolling);}
        });
        //Définition du bouton 5
        Button B5_kbase = (Button) root.findViewById(R.id.B5_kbase);
        B5_kbase.setOnClickListener(new View.OnClickListener() {@Override
        public void onClick(View view) {
            appscrolling.putExtra("ActivB",R.raw.b5kbase);
            appscrolling.putExtra("HelpB","un problème d'affichage");
            startActivity(appscrolling);}
        });
        //Définition du bouton 6
        Button B6_kbase = (Button) root.findViewById(R.id.B6_kbase);
        B6_kbase.setOnClickListener(new View.OnClickListener() {@Override
        public void onClick(View view) {
            appscrolling.putExtra("ActivB",R.raw.b6kbase);
            appscrolling.putExtra("HelpB","un problème de pilote");
            startActivity(appscrolling);}
        });
        //Définition du bouton 7
        Button B7_kbase = (Button) root.findViewById(R.id.B7_kbase);
        B7_kbase.setOnClickListener(new View.OnClickListener() {@Override
        public void onClick(View view) {
            appscrolling.putExtra("ActivB",R.raw.b7kbase);
            appscrolling.putExtra("HelpB","un problème d'impression");
            startActivity(appscrolling);}
        });
        //Les opérations de maintenance
        //Définition du bouton b1
        Button Bb1_kbase = (Button) root.findViewById(R.id.Bb1_kbase);
        Bb1_kbase.setOnClickListener(new View.OnClickListener() {@Override
        public void onClick(View view) {
            appscrolling.putExtra("ActivB",R.raw.bb1kbase);
            appscrolling.putExtra("HelpB","une difficulté pour l'installation d'un logiciel");
            startActivity(appscrolling);}
        });
        //Définition du bouton b2
        Button Bb2_kbase = (Button) root.findViewById(R.id.Bb2_kbase);
        Bb2_kbase.setOnClickListener(new View.OnClickListener() {@Override
        public void onClick(View view) {
            appscrolling.putExtra("ActivB",R.raw.bb2kbase);
            appscrolling.putExtra("HelpB","une difficulté pour l'installation d'un périphérique");
            startActivity(appscrolling);}
        });
        //Les licences
        //Définition du bouton sb1
        Button Bsb1_kbase = (Button) root.findViewById(R.id.Bsb1_kbase);
        Bsb1_kbase.setOnClickListener(new View.OnClickListener() {@Override
        public void onClick(View view) {
            appscrolling.putExtra("ActivB",R.raw.bsb1kbase);
            appscrolling.putExtra("HelpB","une difficulté concernant la licence libre");
            startActivity(appscrolling);}
        });
        //Définition du bouton sb2
        Button Bsb2_kbase = (Button) root.findViewById(R.id.Bsb2_kbase);
        Bsb2_kbase.setOnClickListener(new View.OnClickListener() {@Override
        public void onClick(View view) {
            appscrolling.putExtra("ActivB",R.raw.bsb2kbase);
            appscrolling.putExtra("HelpB","une difficulté concernant la licence copyleft");
            startActivity(appscrolling);}
        });
        //Définition du bouton sb3
        Button Bsb3_kbase = (Button) root.findViewById(R.id.Bsb3_kbase);
        Bsb3_kbase.setOnClickListener(new View.OnClickListener() {@Override
        public void onClick(View view) {
            appscrolling.putExtra("ActivB",R.raw.bsb3kbase);
            appscrolling.putExtra("HelpB","une difficulté concernant la licence ouverte");
            startActivity(appscrolling);}
        });

        slideshowViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                mWebView.reload();
                mWebViewEnd.reload();
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