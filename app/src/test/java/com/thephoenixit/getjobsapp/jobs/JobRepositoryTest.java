package com.thephoenixit.getjobsapp.jobs;

import java.util.List;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Unit tests for JobRepository (specs/job-board-core scenarios:
 * "Load bundled job listings", "Malformed dataset degrades gracefully").
 */
public class JobRepositoryTest
{
    @Test
    public void loadBundledJobListings_parsesContractFields()
    {
        // Given a jobs.json payload using the Constant contract field names
        String json = "[{"
                + "\"id\": 1,"
                + "\"job_name\": \"Senior Android Developer\","
                + "\"job_company_name\": \"Acme Corp\","
                + "\"job_designation\": \"Senior Engineer\","
                + "\"job_salary\": \"$120,000 - $150,000\","
                + "\"job_desc\": \"Build Android features\","
                + "\"job_qualification\": \"5+ years Android\","
                + "\"job_skill\": \"Android, Kotlin\","
                + "\"job_address\": \"Berlin, Germany\","
                + "\"job_vacancy\": 2,"
                + "\"job_date\": \"2026-01-10\","
                + "\"job_image\": \"img/job.png\""
                + "}]";

        // When the repository parses it
        List<Job> jobs = JobRepository.parse(json);

        // Then one Job per listing with all contract fields populated
        assertEquals(1, jobs.size());
        Job job = jobs.get(0);
        assertEquals(1, job.getId());
        assertEquals("Senior Android Developer", job.getJobName());
        assertEquals("Acme Corp", job.getJobCompanyName());
        assertEquals("Senior Engineer", job.getJobDesignation());
        assertEquals("$120,000 - $150,000", job.getJobSalary());
        assertEquals("Build Android features", job.getJobDesc());
        assertEquals("5+ years Android", job.getJobQualification());
        assertEquals("Android, Kotlin", job.getJobSkill());
        assertEquals("Berlin, Germany", job.getJobAddress());
        assertEquals(2, job.getJobVacancy());
        assertEquals("2026-01-10", job.getJobDate());
        assertEquals("img/job.png", job.getJobImage());
    }

    @Test
    public void malformedDataset_degradesGracefully()
    {
        // Given missing, empty, or not-valid-JSON datasets
        assertNotNull(JobRepository.parse(null));
        assertEquals(0, JobRepository.parse(null).size());
        assertEquals(0, JobRepository.parse("").size());
        assertEquals(0, JobRepository.parse("   ").size());
        assertEquals(0, JobRepository.parse("not json").size());
        assertEquals(0, JobRepository.parse("{\"broken\":").size());
        assertEquals(0, JobRepository.parse("[1, 2, 3]").size());
    }
}
