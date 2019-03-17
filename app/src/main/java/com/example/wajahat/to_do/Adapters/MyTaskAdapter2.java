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

public class MyTaskAdapter2 extends RecyclerView.Adapter<MyTaskAdapter2.myTasksViewHolder2> {

    public interface MyAdapterListener { void onClick(View view, int position, List<message> items);
    }

    public class myTasksViewHolder2 extends RecyclerView.ViewHolder{
        private TextView to;
        private TextView t;
        private TextView deadline;
        private TextView status;
        public myTasksViewHolder2(@NonNull View itemView) {
            super(itemView);
            to=itemView.findViewById(R.id.to2);
            t=itemView.findViewById(R.id.task);
            deadline=itemView.findViewById(R.id.deadline);
            status=itemView.findViewById(R.id.status);
        }
    }
    private Context context;
    private final LayoutInflater inflater;
    private List<message> my_tasks;

    public MyTaskAdapter2(Context context){
        this.context=context;
        this.inflater = LayoutInflater.from(context);
    }


    public List<message> getItems(){
        return my_tasks;
    }

    public void setItems(List<message> words){
        this.my_tasks=words;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public myTasksViewHolder2 onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View task=inflater.inflate(R.layout.my_assigned_child,viewGroup,false);
        return new myTasksViewHolder2(task);
    }

    @Override
    public void onBindViewHolder(@NonNull myTasksViewHolder2 myTasksViewHolder2, int i) {
        if(my_tasks!=null){
            message mg=my_tasks.get(i);
            myTasksViewHolder2.to.setText(mg.getTo());
            myTasksViewHolder2.t.setText(mg.getTask());
            myTasksViewHolder2.deadline.setText(mg.getDeadline());
            myTasksViewHolder2.status.setText(mg.getStatus());
        }
    }

    @Override
    public int getItemCount() {
        if(my_tasks!=null)
            return my_tasks.size();
        else
            return 0;
    }
}
