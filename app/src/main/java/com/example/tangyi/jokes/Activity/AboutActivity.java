package com.example.tangyi.jokes.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.tangyi.jokes.R;

/**
 * Created by 在阳光下唱歌 on 2016/6/4.
 */
public class AboutActivity extends AppCompatActivity {
    private View view;
    private ImageButton imageButton;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.about_activity);

        view=findViewById(R.id.about_titlebar);
        textView=(TextView)view.findViewById(R.id.textView2);
        textView.setText("关于");
        imageButton=(ImageButton)view.findViewById(R.id.setting_imageButton);
        imageButton.setImageResource(R.drawable.ic_back);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
