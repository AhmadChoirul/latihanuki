package com.achoi.latihanuki;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.MyviewHolder> {
    Context context;
    List<ToDo> todo;

    public ToDoAdapter(Context context, List<ToDo> todo) {
        this.context = context;
        this.todo = todo;
    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyviewHolder(LayoutInflater.from(context).inflate(R.layout.todo_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, int position) {
        holder.titletodo.setText(todo.get(position).getTitletodo());
        holder.desctodo.setText(todo.get(position).getDesctodo());
        holder.datetodo.setText(todo.get(position).getDatetodo());

        final String getTitleTodo = todo.get(position).getTitletodo();
        final String getDescTodo = todo.get(position).getDesctodo();
        final String getDateTodo = todo.get(position).getDatetodo();
        final String getIdTodo = todo.get(position).getIdtodo();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context,EditTodo.class);
                i.putExtra("titletodo",getTitleTodo);
                i.putExtra("desctodo",getDescTodo);
                i.putExtra("datetodo",getDateTodo);
                i.putExtra("idtodo",getIdTodo);

                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return todo.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        TextView titletodo,desctodo,datetodo;
        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            titletodo = (TextView)itemView.findViewById(R.id.titleTodo);
            desctodo = (TextView)itemView.findViewById(R.id.descTodo);
            datetodo = (TextView)itemView.findViewById(R.id.dateTodo);

        }
    }
}
