package com.example.wajahat.to_do.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wajahat.to_do.Data.message;
import com.example.wajahat.to_do.R;

import java.util.List;

public class myTasksAdapter extends RecyclerView.Adapter<myTasksAdapter.myTasksViewHolder> {


    public class myTasksViewHolder extends RecyclerView.ViewHolder{
        private TextView from;
        private TextView t;
        private TextView deadline;
        public myTasksViewHolder(@NonNull View itemView) {
            super(itemView);
            from=itemView.findViewById(R.id.from);
            t=itemView.findViewById(R.id.task);
            deadline=itemView.findViewById(R.id.deadline);

        }
    }
    private Context context;
    private final LayoutInflater inflater;
    private List<message> my_tasks;

    public myTasksAdapter(Context context){
        this.context=context;
        this.inflater = LayoutInflater.from(context);

    }

    @Override
    public myTasksAdapter.myTasksViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View task=inflater.inflate(R.layout.my_tasks_child,viewGroup,false);
        return new myTasksViewHolder(task);
    }

    @Override
    public void onBindViewHolder(@NonNull myTasksAdapter.myTasksViewHolder myTasksViewHolder, int i) {
        if(my_tasks!=null){
            message mg=my_tasks.get(i);
            myTasksViewHolder.from.setText(mg.getFrom());
            myTasksViewHolder.t.setText(mg.getTask());
            myTasksViewHolder.deadline.setText(mg.getDeadline());
        }
    }
    public void setItems(List<message> words){
        this.my_tasks=words;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        if(my_tasks!=null)
            return my_tasks.size();
        else
            return 0;
    }
}
