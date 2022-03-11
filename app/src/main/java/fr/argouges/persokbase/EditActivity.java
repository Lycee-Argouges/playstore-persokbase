package fr.argouges.persokbase;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import fr.argouges.persokbase.ui.filemanager.NIOFileDecryptExample;
import fr.argouges.persokbase.ui.filemanager.NIOFileWriteEncrypt;

public class EditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        TextView textbar = (TextView) findViewById(R.id.textbar) ;
        textbar.setText(R.string.app_name);
        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(getTitle());
        EditText editbox = (EditText) findViewById(R.id.editbox) ;
        //Lecture de la note
        String NoteN = null;
        try {
            NoteN = NIOFileDecryptExample.main();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if("".equals(NoteN) || NoteN==null) {
            editbox.setText("");
        } else {
            editbox.setText(NoteN);
        }
        //Enregistrement de la note
        FloatingActionButton fabedit = (FloatingActionButton) findViewById(R.id.fabedit);
        fabedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(), "Enregistrement de la note !", Toast.LENGTH_SHORT).show();
                String WriteNote = null;
                String SaveNote = editbox.getText().toString();
                if (!"".equals(SaveNote)) {
                    try {
                        WriteNote = NIOFileWriteEncrypt.main(SaveNote);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (WriteNote!=null) {
                        Snackbar.make(view, WriteNote, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    } else {
                        Snackbar.make(view, "Un problème est apparu.", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                } else {
                    try {
                        WriteNote = NIOFileWriteEncrypt.main("");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                finish();
            }
        });
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

}