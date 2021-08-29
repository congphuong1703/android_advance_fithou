package npc.com.nguyencongphuongb1;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AsyncActivity extends AppCompatActivity implements View.OnClickListener {

    private ProgressBar progressBar;
    private Button btnShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getViews();
    }

    private void getViews() {
        progressBar = findViewById(R.id.progressBar);
        btnShow = findViewById(R.id.btnShow);
        btnShow.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnShow:
                new ProgressAsyncTask().execute();
                break;
            default:
                break;
        }
    }


    public class ProgressAsyncTask extends AsyncTask<Void, Integer, String> {

        @Override
        protected String doInBackground(Void... voids) {
            for (int i = 0; i < 20; i++) {
                publishProgress(i);
            }
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "DONE";
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(AsyncActivity.this, s, Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onProgressUpdate(Integer... values){
            progressBar.setProgress(values[0]);

        }
    }
}
