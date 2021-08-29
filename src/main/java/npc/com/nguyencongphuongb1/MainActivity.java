package npc.com.nguyencongphuongb1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Handler handler;
    private TextView textView;
    private Button btnStart;
    public static final int UP_NUMBER = 100;
    public static final int NUMBER_DONE = 101;
    private boolean isUpdate;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getViews();
        processHandler();
    }

    private void getViews() {
        textView = findViewById(R.id.txtNumber);
        btnStart = findViewById(R.id.btnStart);
        btnStart.setOnClickListener(this);
    }

    private void processHandler() {
        handler = new Handler(getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case UP_NUMBER:
                        isUpdate = true;
                        textView.setText(String.valueOf(msg.arg1));
                        break;
                    case NUMBER_DONE:
                        textView.setText("SUCCESS!");
                        isUpdate = false;
                        break;
                    default:
                        break;
                }
            }
        };
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnStart:
                if (!isUpdate)
                    updateNumber();
                break;
            default:
                break;
        }
    }

    private void updateNumber() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 11; i++) {
                    Message msg = new Message();
                    msg.what = UP_NUMBER;
                    msg.arg1 = i;
                    handler.sendMessage(msg);
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                handler.sendEmptyMessage(NUMBER_DONE);
            }
        }).start();
    }
}