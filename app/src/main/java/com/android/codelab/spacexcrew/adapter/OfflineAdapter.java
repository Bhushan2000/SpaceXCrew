
package com.android.codelab.spacexcrew.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.AsyncTask;
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
import com.android.codelab.spacexcrew.room.CrewDatabase;
import com.android.codelab.spacexcrew.room.CrewEntity;
import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class OfflineAdapter extends RecyclerView.Adapter<OfflineAdapter.SVH> {
    private Context context;
    private List<CrewEntity> offlineList;


    public OfflineAdapter(Context context, List<CrewEntity> offlineList) {
        this.context = context;
        this.offlineList = offlineList;
    }

    // Room
    private CrewEntity crewEntity;


    @NonNull
    @NotNull
    @Override
    public SVH onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_offline, parent, false);

        return new SVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull OfflineAdapter.SVH holder, int position) {


        CrewEntity spaceX = offlineList.get(position);

        holder.tvName.setText(spaceX.getName());
        holder.tvAgency.setText(spaceX.getAgency());
        holder.tvWikipedia.setText(spaceX.getWikipedia());

        holder.tvStatus.setText(spaceX.getStatus());
        holder.tvWikipedia.setLinkTextColor(ColorStateList.valueOf(Color.parseColor("#FF6200EE")));


        Glide.with(context).asDrawable().placeholder(R.drawable.ic_placeholder).load(spaceX.getImage()).into(holder.ivImage);



        holder.btnDelete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Room Operations
                        crewEntity = new CrewEntity(spaceX.getId(), spaceX.getName(), spaceX.getAgency(), spaceX.getWikipedia(), spaceX.getStatus(), spaceX.getImage());

                        // create worker thread to insert data into database
                        new DeleteTask(context, crewEntity, holder.btnDelete, offlineList).execute();

                        offlineList.remove(holder.getAdapterPosition());
                        notifyItemRemoved(position);

                    }
                }
        );




    }


    private static class DeleteTask extends AsyncTask<Void, Void, Boolean> {


        Context context;
        CrewEntity crewEntity;
        Button btnAdd;
        List<CrewEntity> offlineList;

        public DeleteTask(Context context, CrewEntity crewEntity, Button btnAdd, List<CrewEntity> offlineList) {
            this.context = context;
            this.crewEntity = crewEntity;
            this.btnAdd = btnAdd;
            this.offlineList = offlineList;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            CrewDatabase.getInstance(context).crewDAO().deleteCrew(crewEntity);
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean) {

                Snackbar.make(btnAdd, "deleted", Snackbar.LENGTH_SHORT).show();


            }
        }


    }

    @Override
    public int getItemCount() {
        return offlineList.size();
    }

    class SVH extends RecyclerView.ViewHolder {
        TextView tvName, tvAgency, tvWikipedia, tvStatus;
        ImageView ivImage;
        Button btnDelete;


        public SVH(@NonNull @NotNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvAgency = itemView.findViewById(R.id.tvAgency);
            tvWikipedia = itemView.findViewById(R.id.tvWikipedia);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            ivImage = itemView.findViewById(R.id.ivImage);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }

    public void updateListItems(List<CrewEntity> crewEntityList) {


        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffUtilCallBacks(offlineList, crewEntityList));
        offlineList.clear();
        this.offlineList.addAll(crewEntityList);
        diffResult.dispatchUpdatesTo(this);


    }
}
