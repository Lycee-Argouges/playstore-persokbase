package fr.argouges.persokbase;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import com.google.android.material.shape.CornerFamily;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;

public class NotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_view);
        final TextView textView = findViewById(R.id.notif_text);
        Intent myIntent = getIntent();
        //Si c'est une erreur de connexion
        String getNote = myIntent.getStringExtra("ErrNote");
        if (getNote !=null) {
            textView.setText(getNote);
        }
        //Mise en forme de la page
        float radius = getResources().getDimension(R.dimen.corner_radius);
        ShapeAppearanceModel shapeAppearanceModel = new ShapeAppearanceModel()
                .toBuilder()
                .setAllCorners(CornerFamily.ROUNDED,radius)
                .build();
        MaterialShapeDrawable shapeDrawable = new MaterialShapeDrawable(shapeAppearanceModel);
        shapeDrawable.setFillColor(ColorStateList.valueOf(getResources().getColor(R.color.blue_spot4)));
        ViewCompat.setBackground(textView,shapeDrawable);
    }
}
