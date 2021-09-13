package npc.com.b2_nguyencongphuong;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class SecondaryActivity extends AppCompatActivity {
    SecondaryDialView secondaryDialView;
    @Override
    protected void onCreate(Bundle savedInstantsState) {
        super.onCreate(savedInstantsState);
        setContentView(R.layout.activity_secondary);
        secondaryDialView = findViewById(R.id.secondaryDialView);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_secondary,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int n = item.getOrder();
        secondaryDialView.setSelectionCount(n);
        return super.onOptionsItemSelected(item);
    }
}
