package com.example.swipemenurecyclerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyHolder> {


    //when any item clicked start
    RecyclerTouchListener listener;
    public interface RecyclerTouchListener{
        void onClickItem(View v, int position);
    }

    public void setClickListener(RecyclerTouchListener listener){
        this.listener=listener;
    }
    //itemclicked end

    Context c;
    LayoutInflater layoutInflater;
    ArrayList<GetSet> model=new ArrayList<GetSet>();

    public CustomAdapter(Context c,ArrayList<GetSet> model){

        this.c=c;
        this.model=model;

    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater=LayoutInflater.from(parent.getContext());
        View v=layoutInflater.inflate(R.layout.custom_lv,parent,false);
        return new MyHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, final int position) {
        holder.namer.setText(model.get(position).getName());
        holder.emailr.setText(model.get(position).getEmail());
        holder.phoner.setText(model.get(position).getPhone());
    }

   @Override
    public int getItemCount() {
        return model.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        TextView namer,emailr,phoner;

        public MyHolder(@NonNull final View v) {
            super(v);

            namer=v.findViewById(R.id.namer);
            emailr=v.findViewById(R.id.emailr);
            phoner=v.findViewById(R.id.phoner);

            v.setTag(v);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null)
                        listener.onClickItem(v,getAdapterPosition());
                }
            });



        }
    }
}
