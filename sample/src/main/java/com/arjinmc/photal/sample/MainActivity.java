package com.arjinmc.photal.sample;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.arjinmc.photal.activity.PhotoSelectorActivity;
import com.arjinmc.photal.config.Config;
import com.arjinmc.photal.config.Constant;
import com.arjinmc.photal.widget.RecyclerViewItemDecoration;

public class MainActivity extends AppCompatActivity {

    private String[] mSampleList;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSampleList = getResources().getStringArray(R.array.sample_list);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.addItemDecoration(
                new RecyclerViewItemDecoration.Builder(this)
                        .color(Color.BLACK).thickness(2).paddingStart(20).paddingEnd(20).create());
        mRecyclerView.setAdapter(new MyAdapter());

    }


    private class MyAdapter extends RecyclerView.Adapter<ItemViewHolder> {

        @Override
        public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ItemViewHolder(LayoutInflater.from(getBaseContext())
                    .inflate(R.layout.item_sample_list, parent, false));
        }

        @Override
        public void onBindViewHolder(ItemViewHolder holder, int position) {
            holder.tvName.setText(mSampleList[position]);
            holder.llParent.setOnClickListener(new ItemClickListener(position));
        }

        @Override
        public int getItemCount() {
            return mSampleList.length;
        }
    }


    private class ItemViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout llParent;
        private TextView tvName;

        public ItemViewHolder(View itemView) {
            super(itemView);
            llParent = (LinearLayout) itemView;
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
        }
    }

    private class ItemClickListener implements View.OnClickListener {

        private int position;

        public ItemClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            switch (position) {
                case 0:
                    startActivityForResult(new Intent(MainActivity.this, PhotoSelectorActivity.class), 1);
                    break;
                case 1:
                    Intent selectOneIntent = new Intent(MainActivity.this, PhotoSelectorActivity.class);
                    selectOneIntent.setAction(Constant.ACTION_CHOOSE_SINGLE);
                    startActivityForResult(selectOneIntent, 1);
                    break;
            }
        }
    }


    private void startAct(Class clz) {
        startActivity(new Intent(this, clz));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Config.SELECTOR_RESULT_CODE) {
            String[] paths = data.getStringArrayExtra(Constant.BUNDLE_KEY);
            String result = getPath(paths);
            Log.e("path", result);
            Toast.makeText(getBaseContext(), result, Toast.LENGTH_SHORT).show();
        }
    }


    private String getPath(String[] path) {
        StringBuilder stringBuilder = new StringBuilder();
        int len = path.length;
        for (int i = 0; i < len; i++) {
            stringBuilder.append(path[i] + "\n");
        }
        return stringBuilder.toString();
    }


}
