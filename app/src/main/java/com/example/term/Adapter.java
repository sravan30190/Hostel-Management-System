package com.example.term;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private List<ModelClass> userList;

    public Adapter(List<ModelClass>userList)
    {
        this.userList=userList;
    }
    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_design,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
        String t1=userList.get(position).getReason();
        String t2=userList.get(position).getOuttime();
        String t3=userList.get(position).getIntime();
        String t4=userList.get(position).getStatus();
        holder.setDate(t1,t2,t3,t4);

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView reason;
        private TextView outtime;
        private TextView intime;
        private TextView stat;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            reason=itemView.findViewById(R.id.hreason);
            outtime=itemView.findViewById(R.id.houttime);
            intime=itemView.findViewById(R.id.hintime);
            stat=itemView.findViewById(R.id.hstatus);
        }

        public void setDate(String t1, String t2, String t3,String t4) {
            reason.setText(t1);
            outtime.setText(t2);
            intime.setText(t3);
            stat.setText(t4);

        }
    }
}
