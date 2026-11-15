package com.thephoenixit.getjobsapp.jobs;

import android.content.Context;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.thephoenixit.getjobsapp.utils.Constant.JOB_ADDRESS;
import static com.thephoenixit.getjobsapp.utils.Constant.JOB_COMPANY_NAME;
import static com.thephoenixit.getjobsapp.utils.Constant.JOB_DATE;
import static com.thephoenixit.getjobsapp.utils.Constant.JOB_DESC;
import static com.thephoenixit.getjobsapp.utils.Constant.JOB_DESIGNATION;
import static com.thephoenixit.getjobsapp.utils.Constant.JOB_ID;
import static com.thephoenixit.getjobsapp.utils.Constant.JOB_IMAGE;
import static com.thephoenixit.getjobsapp.utils.Constant.JOB_NAME;
import static com.thephoenixit.getjobsapp.utils.Constant.JOB_QUALIFICATION;
import static com.thephoenixit.getjobsapp.utils.Constant.JOB_SALARY;
import static com.thephoenixit.getjobsapp.utils.Constant.JOB_SKILL;
import static com.thephoenixit.getjobsapp.utils.Constant.JOB_VACANCY;

/**
 * Loads the bundled jobs.json dataset once and caches the parsed list.
 * Every failure mode (missing asset, empty file, malformed JSON) degrades to
 * an empty list — it never throws.
 */
public class JobRepository
{
    private static final String ASSET_FILE = "jobs.json";

    private final Context context;
    private List<Job> jobs;

    public JobRepository(Context context)
    {
        this.context = context;
    }

    public List<Job> getJobs()
    {
        if (jobs == null)
        {
            jobs = load();
        }
        return jobs;
    }

    public List<Job> search(String keyword)
    {
        return JobFilter.filter(getJobs(), keyword);
    }

    private List<Job> load()
    {
        return parse(readAsset());
    }

    private String readAsset()
    {
        if (context == null)
        {
            return null;
        }
        try
        {
            InputStream inputStream = context.getAssets().open(ASSET_FILE);
            try
            {
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                byte[] buffer = new byte[4096];
                int read;
                while ((read = inputStream.read(buffer)) != -1)
                {
                    output.write(buffer, 0, read);
                }
                return output.toString("UTF-8");
            }
            finally
            {
                inputStream.close();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Parses jobs.json text into Job objects. Package-private so unit tests
     * can inject JSON without Android assets. Returns an empty list for
     * null/blank/malformed input.
     */
    static List<Job> parse(String json)
    {
        if (json == null || json.trim().isEmpty())
        {
            return Collections.emptyList();
        }
        try
        {
            JSONArray array = new JSONArray(json);
            List<Job> result = new ArrayList<>();
            for (int i = 0; i < array.length(); i++)
            {
                JSONObject object = array.optJSONObject(i);
                if (object == null)
                {
                    continue;
                }
                result.add(new Job(
                        object.optInt(JOB_ID, 0),
                        optString(object, JOB_NAME),
                        optString(object, JOB_COMPANY_NAME),
                        optString(object, JOB_DESIGNATION),
                        optString(object, JOB_SALARY),
                        optString(object, JOB_DESC),
                        optString(object, JOB_QUALIFICATION),
                        optString(object, JOB_SKILL),
                        optString(object, JOB_ADDRESS),
                        object.optInt(JOB_VACANCY, 0),
                        optString(object, JOB_DATE),
                        optString(object, JOB_IMAGE)));
            }
            return result;
        }
        catch (JSONException e)
        {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    private static String optString(JSONObject object, String key)
    {
        String value = object.optString(key, "");
        return value == null ? "" : value;
    }
}
