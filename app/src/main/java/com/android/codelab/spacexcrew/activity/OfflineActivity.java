package com.android.codelab.spacexcrew.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.codelab.spacexcrew.adapter.OfflineAdapter;
import com.android.codelab.spacexcrew.R;
import com.android.codelab.spacexcrew.databinding.ActivityMainBinding;
import com.android.codelab.spacexcrew.databinding.ActivityOfflineBinding;
import com.android.codelab.spacexcrew.network.ApiClient;
import com.android.codelab.spacexcrew.network.ApiInterface;
import com.android.codelab.spacexcrew.room.CrewDatabase;
import com.android.codelab.spacexcrew.room.CrewEntity;
import com.android.codelab.spacexcrew.viewModel.AppViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class OfflineActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ApiInterface apiInterface;
    private List<CrewEntity> crewEntityList;
    private OfflineAdapter offlineAdapter;
    private TextView tvNoData;

    private ActivityOfflineBinding binding;
    private AppViewModel appViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // viewBinding & dataBinding
        binding = ActivityOfflineBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // ViewModel
        appViewModel = new ViewModelProvider(this).get(AppViewModel.class);


        // ViewBinding will do the job of observing
        binding.setViewModel(appViewModel);
        binding.setLifecycleOwner(this);


        apiInterface = ApiClient.getClient().create(ApiInterface.class);


        new GetCrews(this).execute();


    }


    class GetCrews extends AsyncTask<Void, Void, List<CrewEntity>> {

        Context context;

        public GetCrews(Context context) {
            this.context = context;

        }

        @Override
        protected List<CrewEntity> doInBackground(Void... voids) {


            return CrewDatabase.getInstance(OfflineActivity.this).crewDAO().getAll();
        }

        @Override
        protected void onPostExecute(List<CrewEntity> crewEntities) {
            if (crewEntities != null) {
                // create and set the adapter on RecyclerView instance to display list
                offlineAdapter = new OfflineAdapter(OfflineActivity.this, crewEntities);
                binding.recyclerviewOffline.setAdapter(offlineAdapter);
                offlineAdapter.notifyDataSetChanged();
                crewEntityList = crewEntities;

            }
            if (offlineAdapter.getItemCount() == 0) {
                binding.tvNoData.setVisibility(View.VISIBLE);
            } else {
                binding.tvNoData.setVisibility(View.GONE);

            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        if (menu != null) {

            menu.findItem(R.id.menuOffline).setVisible(false);
        }
        return true;


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menuDeleteAll) {

            deleteAllData();


            return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteAllData() {

        new DeleteCrews(this).execute();

        if (offlineAdapter.getItemCount() > 0) {
            Snackbar.make(binding.recyclerviewOffline, "All Data deleted!!", Snackbar.LENGTH_SHORT).show();
            binding.tvNoData.setVisibility(View.VISIBLE);
            offlineAdapter.notifyDataSetChanged();

        } else if (offlineAdapter.getItemCount() == 0) {
            Snackbar.make(binding.recyclerviewOffline, "No Data found!!", Snackbar.LENGTH_SHORT).show();

        }

        offlineAdapter.updateListItems(crewEntityList);
    }

    class DeleteCrews extends AsyncTask<Void, Void, Boolean> {

        Context context;

        public DeleteCrews(Context context) {
            this.context = context;

        }


        @Override
        protected Boolean doInBackground(Void... voids) {
            CrewDatabase.getInstance(OfflineActivity.this).crewDAO().deleteAll();
            return true;

        }


    }
}