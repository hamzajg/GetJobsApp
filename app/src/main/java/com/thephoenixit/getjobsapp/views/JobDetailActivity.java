package com.thephoenixit.getjobsapp.views;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.thephoenixit.getjobsapp.R;
import com.thephoenixit.getjobsapp.jobs.Job;
import com.thephoenixit.getjobsapp.jobs.JobRepository;

import java.util.List;

/**
 * Shows the full detail of one job listing. Job is not Parcelable, so the
 * caller passes the job id (and list position as a fallback) and the job is
 * re-fetched from JobRepository.
 */
public class JobDetailActivity extends AppCompatActivity
{
    public static final String EXTRA_JOB_ID = "extra_job_id";
    public static final String EXTRA_JOB_POSITION = "extra_job_position";

    private static final int NO_JOB_ID = -1;
    private static final int NO_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Job job = findJob();
        if (job == null)
        {
            finish();
            return;
        }

        setContentView(R.layout.activity_job_detail);
        setTitle(job.getJobName());
        bind(job);
    }

    private Job findJob()
    {
        List<Job> jobs = new JobRepository(this).getJobs();
        int jobId = getIntent().getIntExtra(EXTRA_JOB_ID, NO_JOB_ID);
        for (Job job : jobs)
        {
            if (job.getId() == jobId)
            {
                return job;
            }
        }

        int position = getIntent().getIntExtra(EXTRA_JOB_POSITION, NO_POSITION);
        if (position >= 0 && position < jobs.size())
        {
            return jobs.get(position);
        }
        return null;
    }

    private void bind(Job job)
    {
        setText(R.id.txt_detail_name, job.getJobName());
        setText(R.id.txt_detail_company, job.getJobCompanyName());
        setText(R.id.txt_detail_designation, job.getJobDesignation());
        setText(R.id.txt_detail_salary, job.getJobSalary());
        setText(R.id.txt_detail_description, job.getJobDesc());
        setText(R.id.txt_detail_qualification, job.getJobQualification());
        setText(R.id.txt_detail_skill, job.getJobSkill());
        setText(R.id.txt_detail_address, job.getJobAddress());
        setText(R.id.txt_detail_vacancy, String.valueOf(job.getJobVacancy()));
        setText(R.id.txt_detail_date, job.getJobDate());
    }

    private void setText(int viewId, String value)
    {
        TextView textView = findViewById(viewId);
        if (textView != null)
        {
            textView.setText(value == null ? "" : value);
        }
    }
}
