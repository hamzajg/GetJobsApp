package com.thephoenixit.getjobsapp.views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thephoenixit.getjobsapp.R;
import com.thephoenixit.getjobsapp.jobs.Job;

import java.util.ArrayList;
import java.util.List;

/**
 * Binds job listings to item_job rows. The click listener is wired in
 * MainActivity; wave 3 of job-board-core replaces the no-op with the
 * job detail screen.
 */
public class JobAdapter extends RecyclerView.Adapter<JobAdapter.JobViewHolder>
{
    public interface OnJobClickListener
    {
        void onJobClick(Job job);
    }

    private final List<Job> jobs = new ArrayList<>();
    private final OnJobClickListener listener;

    public JobAdapter(OnJobClickListener listener)
    {
        this.listener = listener;
    }

    public void updateJobs(List<Job> newJobs)
    {
        jobs.clear();
        if (newJobs != null)
        {
            jobs.addAll(newJobs);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public JobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_job, parent, false);
        return new JobViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JobViewHolder holder, int position)
    {
        holder.bind(jobs.get(position));
    }

    @Override
    public int getItemCount()
    {
        return jobs.size();
    }

    class JobViewHolder extends RecyclerView.ViewHolder
    {
        private final TextView titleView;
        private final TextView companyView;
        private final TextView designationView;
        private final TextView salaryView;
        private final TextView addressView;

        JobViewHolder(View itemView)
        {
            super(itemView);
            titleView = itemView.findViewById(R.id.txt_job_title);
            companyView = itemView.findViewById(R.id.txt_job_company);
            designationView = itemView.findViewById(R.id.txt_job_designation);
            salaryView = itemView.findViewById(R.id.txt_job_salary);
            addressView = itemView.findViewById(R.id.txt_job_address);
        }

        void bind(final Job job)
        {
            titleView.setText(job.getJobName());
            companyView.setText(job.getJobCompanyName());
            designationView.setText(job.getJobDesignation());
            salaryView.setText(job.getJobSalary());
            addressView.setText(job.getJobAddress());
            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if (listener != null)
                    {
                        listener.onJobClick(job);
                    }
                }
            });
        }
    }
}
