package ql.cev.ql9;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class TechCulSel extends AppCompatActivity {
    ImageView imageViewSaptak,imageViewTech;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tech_cul_sel);
        imageViewSaptak=findViewById(R.id.saptakselect);
        imageViewTech=findViewById(R.id.techfestselect);
    }
}
