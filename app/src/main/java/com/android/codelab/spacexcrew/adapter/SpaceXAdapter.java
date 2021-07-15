package com.android.codelab.spacexcrew.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.android.codelab.spacexcrew.R;
import com.android.codelab.spacexcrew.diffutil.DiffUtilCallBacks;
import com.android.codelab.spacexcrew.diffutil.DiffUtilCallBacksForMainActivity;
import com.android.codelab.spacexcrew.model.SpaceX;
import com.android.codelab.spacexcrew.room.CrewDatabase;
import com.android.codelab.spacexcrew.room.CrewEntity;
import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SpaceXAdapter extends RecyclerView.Adapter<SpaceXAdapter.SVH> {
    private List<SpaceX> list;
    private Context context;
    private List<CrewEntity> crewIdList = new ArrayList();

    private static final String TAG = "SpaceXAdapter";


    public SpaceXAdapter(List<SpaceX> list, Context context) {
        this.list = list;
        this.context = context;
    }

    // Room
    private CrewEntity crewEntity;


    @NonNull
    @NotNull
    @Override
    public SVH onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);

        return new SVH(view);
    }


    @Override
    public void onBindViewHolder(@NonNull @NotNull SpaceXAdapter.SVH holder, int position) {


        SpaceX spaceX = list.get(position);

        holder.tvName.setText(spaceX.getName());
        holder.tvAgency.setText(spaceX.getAgency());
        holder.tvWikipedia.setText(spaceX.getWikipedia());

        holder.tvStatus.setText(spaceX.getStatus());
        holder.tvWikipedia.setLinkTextColor(ColorStateList.valueOf(Color.parseColor("#FF6200EE")));


        Glide.with(context).asDrawable().placeholder(R.drawable.ic_placeholder).load(spaceX.getImage()).into(holder.ivImage);





        holder.btnAdd.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {




                        // Room Operations
                        crewEntity = new CrewEntity(0, spaceX.getName(), spaceX.getAgency(), spaceX.getWikipedia(), spaceX.getStatus(), spaceX.getImage());

                        // create worker thread to insert data into database
                        new InsertTask(context, crewEntity, holder.btnAdd).execute();
                    }
                }
        );


    }


    private static class InsertTask extends AsyncTask<Void, Void, Boolean> {


        Context context;
        CrewEntity crewEntity;
        Button btnAdd;

        public InsertTask(Context context, CrewEntity crewEntity, Button btnAdd) {
            this.context = context;
            this.crewEntity = crewEntity;
            this.btnAdd = btnAdd;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            CrewDatabase.getInstance(context).crewDAO().insertCrew(crewEntity);
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean) {

                Snackbar.make(btnAdd, "Added", Snackbar.LENGTH_SHORT).show();

            }
        }


    }




    @Override
    public int getItemCount() {
        return list.size();
    }

    class SVH extends RecyclerView.ViewHolder {
        TextView tvName, tvAgency, tvWikipedia, tvStatus;
        ImageView ivImage;
        Button btnAdd;


        public SVH(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvAgency = itemView.findViewById(R.id.tvAgency);
            tvWikipedia = itemView.findViewById(R.id.tvWikipedia);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            ivImage = itemView.findViewById(R.id.ivImage);
            btnAdd = itemView.findViewById(R.id.btnAdd);
        }
    }

    public void updateListItems(List<SpaceX> spaceXList) {


        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffUtilCallBacksForMainActivity(list, spaceXList));
        list.clear();
        this.list.addAll(spaceXList);
        diffResult.dispatchUpdatesTo(this);


    }

}
