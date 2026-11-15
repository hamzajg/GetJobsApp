package com.thephoenixit.getjobsapp.views;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.thephoenixit.getjobsapp.R;
import com.thephoenixit.getjobsapp.jobs.Job;
import com.thephoenixit.getjobsapp.jobs.JobRepository;

import java.util.List;

public class MainActivity extends AppCompatActivity
{

    private JobRepository jobRepository;
    private JobAdapter jobAdapter;
    private TextView emptyView;

    com.google.android.gms.ads.AdView mAdView;
    com.google.android.gms.ads.AdRequest adRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAdView = (com.google.android.gms.ads.AdView) findViewById(R.id.adView);
        adRequest = new com.google.android.gms.ads.AdRequest.Builder().build();

        jobRepository = new JobRepository(this);
        setupJobList();
    }

    private void setupJobList()
    {
        ViewGroup container = findViewById(R.id.Container);
        getLayoutInflater().inflate(R.layout.view_job_list, container, true);

        RecyclerView recyclerView = findViewById(R.id.recycler_jobs);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        jobAdapter = new JobAdapter(new JobAdapter.OnJobClickListener()
        {
            @Override
            public void onJobClick(Job job)
            {
                // Wave 3 (job-board-core): open JobDetailActivity for this job.
                startActivity(new Intent(MainActivity.this, JobDetailActivity.class)
                        .putExtra(JobDetailActivity.EXTRA_JOB_ID, job.getId()));
            }
        });
        recyclerView.setAdapter(jobAdapter);

        emptyView = findViewById(R.id.txt_empty);
        EditText searchView = findViewById(R.id.edt_search);
        searchView.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                applyFilter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s)
            {
            }
        });

        applyFilter("");
    }

    private void applyFilter(String keyword)
    {
        List<Job> results = jobRepository.search(keyword);
        jobAdapter.updateJobs(results);
        emptyView.setVisibility(results.isEmpty() ? View.VISIBLE : View.GONE);
    }
}
