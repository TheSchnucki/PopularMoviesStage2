package com.theschnucki.popularmoviesstage2;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.theschnucki.popularmoviesstage2.model.Trailer;

import java.util.List;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerAdapterViewHolder>{

    private static final String TAG = TrailerAdapter.class.getSimpleName();

    private List<Trailer> mTrailerList;

    /**
     *An on click Handler to interface with the RecyclerView
     */
    private final TrailerAdapterOnClickHandler mClickHandler;

    public interface TrailerAdapterOnClickHandler {
        void onClick (Trailer trailer);
    }

    public TrailerAdapter(TrailerAdapterOnClickHandler clickHandler ) {
        mClickHandler = clickHandler;
    }

    public class TrailerAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView nameTv;
        public TextView typeTv;

        public TrailerAdapterViewHolder(View view) {
            super(view);

            nameTv = view.findViewById(R.id.name_tv);
            typeTv = view.findViewById(R.id.type_tv);

            view.setOnClickListener(this);
        }

        //this gets called by the child views on click
        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            Trailer trailer = mTrailerList.get(adapterPosition);
            mClickHandler.onClick(trailer);
        }
    }

    //this method gets called when new ViewHolder is created
    @Override
    public TrailerAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.trailer_item, viewGroup, false);
        return new TrailerAdapterViewHolder(view);
    }

    //called by RecyclerView to put Data into ViewHolder
    @Override
    public void onBindViewHolder(TrailerAdapterViewHolder trailerAdapterViewHolder, int position) {

        trailerAdapterViewHolder.nameTv.setText(mTrailerList.get(position).getName());
        trailerAdapterViewHolder.typeTv.setText(mTrailerList.get(position).getType());
    }

    @Override
    public int getItemCount() {
        if (null == mTrailerList) return 0;
        return mTrailerList.size();
    }

    //Method to refresh the data
    public void setTrailerList(List<Trailer> trailerList){
        mTrailerList = trailerList;
        notifyDataSetChanged();
    }
}
