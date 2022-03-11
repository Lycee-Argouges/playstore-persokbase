package fr.argouges.persokbase;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static android.view.View.GONE;

public class ScrollingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        TextView textbar = (TextView) findViewById(R.id.textbar) ;
        textbar.setText(R.string.app_name);
        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(getTitle());

        Intent myIntent = getIntent();
        int viewHtml = myIntent.getIntExtra("ActivB",0);
        WebView mWebView = (WebView) findViewById(R.id.ViewHTML);
        mWebView.loadDataWithBaseURL("file:///android_asset/",WebRawView(viewHtml), "text/html", "UTF-8",null);

        Intent i = new Intent(Intent.ACTION_SEND);
        String getHelp = myIntent.getStringExtra("HelpB");
        if (TextUtils.isEmpty(getHelp)) {
            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            String getPhone = myIntent.getStringExtra("HelpP");
            if (TextUtils.isEmpty(getPhone)) {
                fab.setVisibility(GONE);
            } else {
                fab.setImageResource(R.drawable.ic_baseline_phone_enabled);
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", "0476449104", null));
                        startActivity(intent);
                    }
                });
            }
        } else {
            i.setType("message/rfc822");
            i.putExtra(Intent.EXTRA_EMAIL, new String[]{"info.argouges@gmail.com"});
            i.putExtra(Intent.EXTRA_SUBJECT, "Demande d'aide pour " + getHelp);
            i.putExtra(Intent.EXTRA_TEXT, "Bonjour. Je rencontre " + getHelp + ". Je suis en salle ");

            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        startActivity(Intent.createChooser(i, "Envoyer l'email..."));
                /*Snackbar.make(view, "Envoie de l'email en cours... ", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(getApplicationContext(), "Aucun client de messagerie n'est installé !", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
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