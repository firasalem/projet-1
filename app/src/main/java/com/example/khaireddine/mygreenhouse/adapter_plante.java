package com.example.khaireddine.mygreenhouse;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.LinkedList;

/**
 * Created by Khaireddine on 02/04/2018.
 */

public class adapter_plante extends
RecyclerView.Adapter<adapter_plante.WordViewHolder>  {
    private final LinkedList<String> nom_plante;
    private final LinkedList<String> famille;

    private LayoutInflater mInflater;
    public adapter_plante(Context context, LinkedList<String> list, LinkedList<String> famille, LinkedList<String> cycle) {
        mInflater = LayoutInflater.from(context);
        this.nom_plante = list;
        this.famille=famille;
    }
    @Override
    public WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View mItemView = mInflater.inflate(R.layout.item_plante, parent, false);
        return new WordViewHolder(mItemView,this);

    }

    @Override
    public void onBindViewHolder(WordViewHolder holder, int position) {
        String mCurrent = nom_plante.get(position);
        holder.nom_plante.setText(mCurrent);
        holder.famille_plante.setText(mCurrent);
        holder.cycle_vie.setText(mCurrent);
    }

    @Override
    public int getItemCount() {
        return nom_plante.size();
    }

    class WordViewHolder extends RecyclerView.ViewHolder
{

    public final TextView nom_plante,famille_plante,cycle_vie;
    final adapter_plante mAdapter;
    public WordViewHolder(View itemView, adapter_plante adapter) {
        super(itemView);
        nom_plante = (TextView) itemView.findViewById(R.id.plante_item);
        famille_plante = (TextView) itemView.findViewById(R.id.famille_plante);
        cycle_vie=(TextView) itemView.findViewById(R.id.cycle_vie);
        this.mAdapter = adapter;
    }
}

}
