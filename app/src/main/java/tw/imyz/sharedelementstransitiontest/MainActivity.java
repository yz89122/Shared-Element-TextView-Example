package tw.imyz.sharedelementstransitiontest;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import tw.imyz.sharedelementstransitiontest.transition.TransitionUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView tv1 = findViewById(R.id.tv_1);
        final Activity activity = this;
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, Main2Activity.class);
                TransitionUtils.addTextViewInfo(intent, "hello_world", tv1);
                Pair pair1 = Pair.create(tv1, "hello_world");
                ActivityOptionsCompat activityOptionsCompat =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(activity, pair1);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    activity.startActivity(intent, activityOptionsCompat.toBundle());
                }
            }
        });
    }

}
