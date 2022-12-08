package com.ww.sign;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ww.sign.entity.SubListEntity;

public class SubListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_list);

        initView();
    }

    private void initView() {
        SubListEntity subList = getIntent().getParcelableExtra(Constant.IntentTAG.SUB_LIST);
        if (subList == null || subList.subList == null || subList.subList.isEmpty()) {
            return;
        }
        RecyclerView rvSubList = findViewById(R.id.rv_sub_list);
        rvSubList.setLayoutManager(new LinearLayoutManager(this));
        rvSubList.setAdapter(new SubListAdapter(this, subList.subList));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sub_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_back) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}