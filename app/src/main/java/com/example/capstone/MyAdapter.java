package com.example.capstone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{
    ArrayList<String> data1 = new ArrayList<String>();
    ArrayList<String> data2 = new ArrayList<String>();
    onClickListener onClickListener;

    Context context;

    public MyAdapter(Context ct, ArrayList<String> name, ArrayList<String> link, onClickListener onClickListener){

    context = ct;
        data1 = link;
        data2 = name;
        this.onClickListener = onClickListener;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.link_row,parent,false);

        return new MyViewHolder(view,onClickListener);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.myText1.setText(data1.get(position));
        holder.myText2.setText(data2.get(position));


    }

    @Override
    public int getItemCount() {
        return data1.size();
    }

    public interface onClickListener{
        void onClick(int position);

    }
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView myText1, myText2;
        onClickListener onClickListener;

        public MyViewHolder(@NonNull View itemView,onClickListener onClickListener) {
    super(itemView);
            myText1 = itemView.findViewById(R.id.linkName);
            myText2 = itemView.findViewById(R.id.link);
            this.onClickListener = onClickListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onClickListener.onClick(getAdapterPosition());
        }
    }
}
