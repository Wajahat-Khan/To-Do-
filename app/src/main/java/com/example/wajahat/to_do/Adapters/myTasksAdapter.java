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
import com.example.wajahat.to_do.MyTasks;
import com.example.wajahat.to_do.R;

import java.util.List;

public class myTasksAdapter extends RecyclerView.Adapter<myTasksAdapter.myTasksViewHolder> {

    public interface MyAdapterListener { void onClick(View view, int position, List<message> items);
    }

    public class myTasksViewHolder extends RecyclerView.ViewHolder{
        private TextView from;
        private TextView t;
        private TextView deadline;
        private Button complete;
        public myTasksViewHolder(@NonNull View itemView) {
            super(itemView);
            from=itemView.findViewById(R.id.from);
            t=itemView.findViewById(R.id.task);
            deadline=itemView.findViewById(R.id.deadline);
            complete=itemView.findViewById(R.id.completed);
            complete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    myAdapterListener.onClick(view, getAdapterPosition(),getItems());
                }
            });
        }
    }
    private Context context;
    private final LayoutInflater inflater;
    private List<message> my_tasks;
    public MyAdapterListener myAdapterListener;
    int count=1;

    public myTasksAdapter(Context context, MyAdapterListener myAdapterListener){
        this.context=context;
        this.inflater = LayoutInflater.from(context);
        this.myAdapterListener=myAdapterListener;

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
