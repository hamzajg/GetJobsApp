package com.thephoenixit.getjobsapp.jobs;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Pure-Java, case-insensitive keyword filter over job listings.
 * Matches against title, company, skills, location, and designation.
 */
public class JobFilter
{
    private JobFilter()
    {
    }

    /**
     * Returns jobs whose searchable fields contain the keyword (case-insensitive).
     * A null/blank keyword returns all jobs; a null list returns an empty list.
     */
    public static List<Job> filter(List<Job> jobs, String keyword)
    {
        List<Job> result = new ArrayList<>();
        if (jobs == null)
        {
            return result;
        }
        if (keyword == null || keyword.trim().isEmpty())
        {
            result.addAll(jobs);
            return result;
        }
        String needle = keyword.trim().toLowerCase(Locale.ROOT);
        for (Job job : jobs)
        {
            if (matches(job, needle))
            {
                result.add(job);
            }
        }
        return result;
    }

    private static boolean matches(Job job, String needle)
    {
        return contains(job.getJobName(), needle)
                || contains(job.getJobCompanyName(), needle)
                || contains(job.getJobSkill(), needle)
                || contains(job.getJobAddress(), needle)
                || contains(job.getJobDesignation(), needle);
    }

    private static boolean contains(String field, String needle)
    {
        return field != null && field.toLowerCase(Locale.ROOT).contains(needle);
    }
}
