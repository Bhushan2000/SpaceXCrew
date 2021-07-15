package com.android.codelab.spacexcrew.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.Toast;

import com.android.codelab.spacexcrew.R;
import com.android.codelab.spacexcrew.adapter.OfflineAdapter;
import com.android.codelab.spacexcrew.adapter.SpaceXAdapter;
import com.android.codelab.spacexcrew.databinding.ActivityMainBinding;
import com.android.codelab.spacexcrew.model.SpaceX;
import com.android.codelab.spacexcrew.network.ApiInterface;
import com.android.codelab.spacexcrew.room.CrewEntity;
import com.android.codelab.spacexcrew.viewModel.AppViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private SpaceXAdapter spaceXAdapter;

    // 6 connect ViewModel with UI and Listening to "LIVEDATA"
    AppViewModel appViewModel;

    // ViewBinding
    ActivityMainBinding binding;



    private List<SpaceX> spaceXList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // viewBinding & dataBinding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // ViewModel
        appViewModel = new ViewModelProvider(this).get(AppViewModel.class);


        // ViewBinding will do the job of observing
        binding.setViewModel(appViewModel);
        binding.setLifecycleOwner(this);
        // swipeRefresh
        binding.swiperefresh.setOnRefreshListener(this);

        if (isInterNetAvailable()) {

            getData();

        } else {
            binding.relativeLayout.setVisibility(View.GONE);
            binding.tvInternet.setVisibility(View.VISIBLE);

        }


    }


    private void getData() {

        appViewModel.getListMutableLiveData().observe(this, new Observer<List<SpaceX>>() {
            @Override
            public void onChanged(List<SpaceX> spaceXES) {
                binding.relativeLayout.setVisibility(View.GONE);
                if (spaceXES != null) {

                    binding.relativeLayout.setVisibility(View.GONE);
                    spaceXAdapter = new SpaceXAdapter(spaceXES, getApplicationContext());
                    binding.recyclerView.setAdapter(spaceXAdapter);
                    Snackbar.make(binding.recyclerView, "Success", Snackbar.LENGTH_SHORT).show();
                    spaceXList = spaceXES;



                } else {
                    binding.relativeLayout.setVisibility(View.GONE);
                    Snackbar.make(binding.recyclerView, "Check Internet Connection!!", Snackbar.LENGTH_SHORT).show();
                }


            }


        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        if (menu != null) {

            menu.findItem(R.id.menuDeleteAll).setVisible(false);
        }
        return true;


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menuOffline) {

            Intent searchIntent = new Intent(this, OfflineActivity.class);
            startActivity(searchIntent);

            return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private boolean isInterNetAvailable() {
        // To check internet in our device we need context
        // And Also ConnectivityManager

        ConnectivityManager connectivityManager = (ConnectivityManager) MainActivity.this
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();


    }

    @Override
    public void onRefresh() {


        binding.recyclerView.removeAllViews();




        //  used to set the colors used in the progress animation.
        binding.swiperefresh.setColorSchemeColors(
                getResources().getColor(R.color.blue),
                getResources().getColor(R.color.red),
                getResources().getColor(R.color.yellow),
                getResources().getColor(R.color.blue),
                getResources().getColor(R.color.green),
                getResources().getColor(R.color.red)
        );
        // used to set the background color of the spinner.
        binding.swiperefresh.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.white));

        Toast.makeText(this, "Refreshing...", Toast.LENGTH_LONG).show();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                getData();

                binding.swiperefresh.setRefreshing(false);

            }
        }, 3000);
    }
}


