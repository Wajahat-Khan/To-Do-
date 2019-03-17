package com.example.wajahat.to_do.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.wajahat.to_do.Data.message;
import com.example.wajahat.to_do.R;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.myTasksViewHolder> {
    public class myTasksViewHolder extends RecyclerView.ViewHolder{
        private TextView from;
        private TextView to;
        private TextView deadline;
        private TextView task;
        private TextView status;
        public myTasksViewHolder(@NonNull View itemView) {
            super(itemView);
            from=itemView.findViewById(R.id.from);
            to=itemView.findViewById(R.id.to);
            deadline=itemView.findViewById(R.id.deadline);
            task=itemView.findViewById(R.id.task);
            status=itemView.findViewById(R.id.status);
        }
    }
    private Context context;
    private final LayoutInflater inflater;
    private List<message> my_tasks;
    private message mg;

    public SearchAdapter(Context context){
        this.context=context;
        this.inflater = LayoutInflater.from(context);

    }

    @Override
    public SearchAdapter.myTasksViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View task=inflater.inflate(R.layout.history,viewGroup,false);
        return new SearchAdapter.myTasksViewHolder(task);
    }

    @Override
    public void onBindViewHolder(@NonNull myTasksViewHolder myTasksViewHolder, int i) {
        if(my_tasks!=null){
            mg=my_tasks.get(i);
            myTasksViewHolder.from.setText(mg.getFrom());
            myTasksViewHolder.to.setText(mg.getTo());
            myTasksViewHolder.task.setText(mg.getTask());
            myTasksViewHolder.deadline.setText(mg.getDeadline());
            myTasksViewHolder.status.setText(mg.getStatus());
        }
    }

    public List<message> getItems(){
        return my_tasks;
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
