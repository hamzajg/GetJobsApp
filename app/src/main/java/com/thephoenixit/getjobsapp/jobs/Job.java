package com.thephoenixit.getjobsapp.jobs;

/**
 * Plain model for a single job listing.
 * Field names mirror the contract keys in Constant (job_name, job_company_name, ...).
 */
public class Job
{
    private final int id;
    private final String jobName;
    private final String jobCompanyName;
    private final String jobDesignation;
    private final String jobSalary;
    private final String jobDesc;
    private final String jobQualification;
    private final String jobSkill;
    private final String jobAddress;
    private final int jobVacancy;
    private final String jobDate;
    private final String jobImage;

    public Job(int id, String jobName, String jobCompanyName, String jobDesignation,
               String jobSalary, String jobDesc, String jobQualification, String jobSkill,
               String jobAddress, int jobVacancy, String jobDate, String jobImage)
    {
        this.id = id;
        this.jobName = jobName;
        this.jobCompanyName = jobCompanyName;
        this.jobDesignation = jobDesignation;
        this.jobSalary = jobSalary;
        this.jobDesc = jobDesc;
        this.jobQualification = jobQualification;
        this.jobSkill = jobSkill;
        this.jobAddress = jobAddress;
        this.jobVacancy = jobVacancy;
        this.jobDate = jobDate;
        this.jobImage = jobImage;
    }

    public int getId()
    {
        return id;
    }

    public String getJobName()
    {
        return jobName;
    }

    public String getJobCompanyName()
    {
        return jobCompanyName;
    }

    public String getJobDesignation()
    {
        return jobDesignation;
    }

    public String getJobSalary()
    {
        return jobSalary;
    }

    public String getJobDesc()
    {
        return jobDesc;
    }

    public String getJobQualification()
    {
        return jobQualification;
    }

    public String getJobSkill()
    {
        return jobSkill;
    }

    public String getJobAddress()
    {
        return jobAddress;
    }

    public int getJobVacancy()
    {
        return jobVacancy;
    }

    public String getJobDate()
    {
        return jobDate;
    }

    public String getJobImage()
    {
        return jobImage;
    }
}
