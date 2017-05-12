package com.arjinmc.photal.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.arjinmc.photal.selector.PhotoGridSelectorActivity;

public class MainActivity extends AppCompatActivity{

    private String[] mSampleList;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSampleList = getResources().getStringArray(R.array.sample_list);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext(),LinearLayoutManager.VERTICAL,false));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getBaseContext(), LinearLayout.VERTICAL));
        mRecyclerView.setAdapter(new MyAdapter());

    }


    private class MyAdapter extends RecyclerView.Adapter<ItemViewHolder>{

        @Override
        public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ItemViewHolder(LayoutInflater.from(getBaseContext())
                    .inflate(R.layout.item_sample_list,parent,false));
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


    private class ItemViewHolder extends RecyclerView.ViewHolder{

        private LinearLayout llParent;
        private TextView tvName;

        public ItemViewHolder(View itemView) {
            super(itemView);
            llParent = (LinearLayout) itemView;
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
        }
    }

    private class ItemClickListener implements View.OnClickListener{

        private int position;

        public ItemClickListener(int position){
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            switch (position){
                case 0:
                    startAct(PhotoGridSelectorActivity.class);
                    break;
            }
        }
    }


    private void startAct(Class clz){
        startActivity(new Intent(this,clz));
    }

}
