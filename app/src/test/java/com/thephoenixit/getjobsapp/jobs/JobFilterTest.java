package com.thephoenixit.getjobsapp.jobs;

import java.util.Arrays;
import java.util.List;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for JobFilter (specs/job-board-core scenario:
 * "Search filters listings").
 */
public class JobFilterTest
{
    private static final Job ANDROID_JOB = new Job(
            1, "Senior Android Developer", "Acme Corp", "Senior Engineer",
            "$120k", "desc", "qual", "Android, Kotlin", "Berlin, Germany",
            2, "2026-01-10", "");
    private static final Job BARISTA_JOB = new Job(
            2, "Barista", "Bean House", "Front of House",
            "€30k", "desc", "qual", "Customer service", "Lyon, France",
            3, "2026-01-12", "");
    private static final Job IOS_JOB = new Job(
            3, "iOS Developer", "Northwind Labs", "Mid-Level Engineer",
            "$100k", "desc", "qual", "Swift, SwiftUI", "Amsterdam, Netherlands",
            1, "2026-01-08", "");

    private static final List<Job> JOBS = Arrays.asList(ANDROID_JOB, BARISTA_JOB, IOS_JOB);

    @Test
    public void search_android_returnsOnlyAndroidJob()
    {
        List<Job> result = JobFilter.filter(JOBS, "android");
        assertEquals(1, result.size());
        assertEquals("Senior Android Developer", result.get(0).getJobName());
    }

    @Test
    public void search_lyon_returnsOnlyBaristaJob()
    {
        List<Job> result = JobFilter.filter(JOBS, "lyon");
        assertEquals(1, result.size());
        assertEquals("Barista", result.get(0).getJobName());
    }

    @Test
    public void search_noMatch_returnsEmptyList()
    {
        assertTrue(JobFilter.filter(JOBS, "underwater basket weaving").isEmpty());
    }

    @Test
    public void search_isCaseInsensitive()
    {
        assertEquals(1, JobFilter.filter(JOBS, "ANDROID").size());
        assertEquals(1, JobFilter.filter(JOBS, "Acme").size());
        assertEquals(1, JobFilter.filter(JOBS, "LYON").size());
    }

    @Test
    public void search_blankOrNull_returnsAllJobs()
    {
        assertEquals(JOBS.size(), JobFilter.filter(JOBS, "").size());
        assertEquals(JOBS.size(), JobFilter.filter(JOBS, "   ").size());
        assertEquals(JOBS.size(), JobFilter.filter(JOBS, null).size());
    }
}
