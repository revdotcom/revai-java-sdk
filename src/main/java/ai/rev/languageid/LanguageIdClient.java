package ai.rev.languageid;

import ai.rev.helpers.ClientHelper;
import ai.rev.languageid.models.LanguageIdJob;
import ai.rev.languageid.models.LanguageIdJobOptions;
import ai.rev.languageid.models.LanguageIdResult;
import ai.rev.speechtotext.FileStreamRequestBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Retrofit;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The LanguageIdClient object provides methods to send and retrieve information from all the
 * Rev AI Language Identification API endpoints using the Retrofit HTTP client.
 */
public class LanguageIdClient {

    private OkHttpClient client;

    /**
     * Interface that LanguageIdClient methods use to make requests
     */
    public LanguageIdInterface apiInterface;

    /**
     * Constructs the API client used to send HTTP requests to Rev AI. The user access token can be
     * generated on the website at <a
     * href="https://www.rev.ai/access_token">https://www.rev.ai/access_token</a>.
     *
     * @param accessToken Rev AI authorization token associate with the account.
     * @throws IllegalArgumentException If the access token is null or empty.
     */
    public LanguageIdClient(String accessToken) {
        if (accessToken == null || accessToken.isEmpty()) {
            throw new IllegalArgumentException("Access token must be provided");
        }
        this.client = ClientHelper.createOkHttpClient(accessToken);
        Retrofit retrofit = ClientHelper.createRetrofitInstance(client, "languageid", "v1");
        this.apiInterface = retrofit.create(LanguageIdInterface.class);
    }

    /**
     * Manually closes the connection when the code is running in a JVM
     */
    public void closeConnection() {
        client.dispatcher().executorService().shutdown();
        client.connectionPool().evictAll();
    }

    /**
     * This method sends a GET request to the /jobs endpoint and returns a list of {@link LanguageIdJob}
     * objects.
     *
     * @param limit The maximum number of jobs to return. The default is 100, max is 1000.
     * @param startingAfter The job ID at which the list begins.
     * @return A list of {@link LanguageIdJob} objects.
     * @throws IOException If the response has a status code > 399.
     * @see LanguageIdJob
     * @see <a
     * href="https://docs.rev.ai/api/language-identification/reference/#operation/GetListOfLanguageIdentificationJobs">https://docs.rev.ai/api/language-identification/reference/#operation/GetListOfLanguageIdentificationJobs</a>
     */
    public List<LanguageIdJob> getListOfJobs(Integer limit, String startingAfter) throws IOException {
        Map<String, String> options = new HashMap<>();
        if (startingAfter != null) {
            options.put("starting_after", startingAfter);
        }
        if (limit != null) {
            options.put("limit", String.valueOf(limit));
        }
        return apiInterface.getListOfJobs(options).execute().body();
    }

    /**
     * Overload of {@link LanguageIdClient#getListOfJobs(Integer, String)} without the optional startingAfter
     * parameter.
     *
     * @param limit The maximum number of jobs to return. The default is 100, max is 1000.
     * @return A list of {@link LanguageIdJob} objects.
     * @throws IOException If the response has a status code > 399.
     */
    public List<LanguageIdJob> getListOfJobs(Integer limit) throws IOException {
        return getListOfJobs(limit, null);
    }

    /**
     * Overload of {@link LanguageIdClient#getListOfJobs(Integer, String)} without the optional limit
     * parameter.
     *
     * @param startingAfter The job ID at which the list begins.
     * @return A list of {@link LanguageIdJob} objects.
     * @throws IOException If the response has a status code > 399.
     */
    public List<LanguageIdJob> getListOfJobs(String startingAfter) throws IOException {
        return getListOfJobs(null, startingAfter);
    }

    /**
     * Overload of {@link LanguageIdClient#getListOfJobs(Integer, String)} without the optional limit and
     * startingAfter parameter.
     *
     * @return A list of {@link LanguageIdJob} objects.
     * @throws IOException If the response has a status code > 399.
     */
    public List<LanguageIdJob> getListOfJobs() throws IOException {
        return getListOfJobs(null, null);
    }

    /**
     * This method sends a GET request to the /jobs/{id} endpoint and returns a {@link LanguageIdJob}
     * object.
     *
     * @param id The ID of the job to return an object for.
     * @return A {@link LanguageIdJob} object.
     * @throws IOException If the response has a status code > 399.
     * @throws IllegalArgumentException If the job ID is null.
     */
    public LanguageIdJob getJobDetails(String id) throws IOException {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Job ID must be provided");
        }
        return apiInterface.getJobDetails(id).execute().body();
    }

    /**
     * This method sends a DELETE request to the /jobs/{id} endpoint.
     *
     * @param id The id of the job to be deleted.
     * @throws IOException If the response has a status code > 399.
     * @throws IllegalArgumentException If the job ID is null.
     * @see <a
     * href="https://docs.rev.ai/api/language-identification/reference/#operation/DeleteLanguageIdentificationJobById">https://docs.rev.ai/api/language-identification/reference/#operation/DeleteLanguageIdentificationJobById</a>
     */
    public void deleteJob(String id) throws IOException {
        if (id == null) {
            throw new IllegalArgumentException("Job ID must be provided");
        }
        apiInterface.deleteJob(id).execute();
    }

    /**
     * The method sends a GET request to the /jobs/{id}/result endpoint and returns a {@link LanguageIdResult} object.
     *
     * @param id The id of the job to return a result for.
     * @return LanguageIdResult The result object.
     * @throws IOException If the response has a status code > 399.
     * @see LanguageIdResult
     * @see <a
     * href="https://docs.rev.ai/api/language-identification/reference/#operation/GetLanguageIdentificationResultById">https://docs.rev.ai/api/language-identification/reference/#operation/GetLanguageIdentificationResultById</a>
     */
    public LanguageIdResult getResultObject(String id) throws IOException {
        return apiInterface.getResultObject(id).execute().body();
    }

    /**
     * The method sends a POST request to the /jobs endpoint, starts a language id job for the
     * provided options and returns a {@link LanguageIdJob} object.
     *
     * @param options The language id options associated with this job.
     * @return LanguageIdJob A representation of the language id job.
     * @throws IOException If the response has a status code > 399.
     * @see LanguageIdJob
     * @see <a
     * href="https://docs.rev.ai/api/language-identification/reference/#operation/SubmitLanguageIdentificationJob">https://docs.rev.ai/api/language-identification/reference/#operation/SubmitLanguageIdentificationJob</a>
     */
    public LanguageIdJob submitJob(LanguageIdJobOptions options) throws IOException {
        return apiInterface.submitJob(options).execute().body();
    }

    /**
     * The method sends multipart/form POST request to the /jobs endpoint, starts a language id job for the
     * provided local media file and returns a {@link LanguageIdJob} object.
     *
     * @param filePath A local path to the file on the computer.
     * @param options  The language id options associated with this job.
     * @return LanguageIdJob A representation of the language id job.
     * @throws IOException If the response has a status code > 399.
     * @throws IllegalArgumentException If the file path is null.
     * @see LanguageIdJob
     * @see <a
     * href="https://docs.rev.ai/api/language-identification/reference/#operation/SubmitLanguageIdentificationJob">https://docs.rev.ai/api/language-identification/reference/#operation/SubmitLanguageIdentificationJob</a>
     */
    public LanguageIdJob submitJobLocalFile(String filePath, LanguageIdJobOptions options) throws IOException {
        if (filePath == null) {
            throw new IllegalArgumentException("File path must be provided");
        }
        if (options == null) {
            options = new LanguageIdJobOptions();
        }
        File file = new File(filePath);
        return submitMultipartRequest(
                new FileInputStream(file.getAbsoluteFile()), file.getName(), options);
    }

    /**
     * The method sends a multipart/form POST request to the /jobs endpoint, starts a language id job for the
     * provided media file provided by InputStream and returns a {@link LanguageIdJob} object.
     *
     * @param inputStream An InputStream of the media file.
     * @param fileName The name of the file being streamed.
     * @param options The language id options associated with this job.
     * @return LanguageIdJob A representation of the language id job.
     * @throws IOException If the response has a status code > 399.
     * @throws IllegalArgumentException If the InputStream provided is null.
     * @see LanguageIdJob
     * @see <a
     * href="https://docs.rev.ai/api/language-identification/reference/#operation/SubmitLanguageIdentificationJob">https://docs.rev.ai/api/language-identification/reference/#operation/SubmitLanguageIdentificationJob</a>
     */
    public LanguageIdJob submitJobLocalFile(
            InputStream inputStream, String fileName, LanguageIdJobOptions options) throws IOException {
        if (inputStream == null) {
            throw new IllegalArgumentException("File stream must be provided");
        }
        if (options == null) {
            options = new LanguageIdJobOptions();
        }
        if (fileName == null) {
            fileName = "audio_file";
        }
        return submitMultipartRequest(inputStream, fileName, options);
    }

    /**
     * An overload of {@link LanguageIdClient#submitJobLocalFile(InputStream, String, LanguageIdJobOptions)}
     * without the optional filename and language id options.
     *
     * @param inputStream An InputStream of the media file.
     * @return LanguageIdJob A representation of the language id job.
     * @throws IOException If the response has a status code > 399.
     * @see LanguageIdJob
     * @see <a
     * href="https://docs.rev.ai/api/language-identification/reference/#operation/SubmitLanguageIdentificationJob">https://docs.rev.ai/api/language-identification/reference/#operation/SubmitLanguageIdentificationJob</a>
     */
    public LanguageIdJob submitJobLocalFile(InputStream inputStream) throws IOException {
        return submitJobLocalFile(inputStream, null, null);
    }

    /**
     * An overload of {@link LanguageIdClient#submitJobLocalFile(InputStream, String, LanguageIdJobOptions)}
     * without the additional language id options.
     *
     * @param inputStream An InputStream of the media file.
     * @param fileName The name of the file being streamed.
     * @return LanguageIdJob A representation of the language id job.
     * @throws IOException If the response has a status code > 399.
     * @see LanguageIdJob
     * @see <a
     * href="https://docs.rev.ai/api/language-identification/reference/#operation/SubmitLanguageIdentificationJob">https://docs.rev.ai/api/language-identification/reference/#operation/SubmitLanguageIdentificationJob</a>
     */
    public LanguageIdJob submitJobLocalFile(InputStream inputStream, String fileName) throws IOException {
        return submitJobLocalFile(inputStream, fileName, null);
    }

    /**
     * An overload of {@link LanguageIdClient#submitJobLocalFile(InputStream, String, LanguageIdJobOptions)}
     * without the optional filename.
     *
     * @param inputStream An InputStream of the media file.
     * @param options The language id options associated with this job.
     * @return LanguageIdJob A representation of the language id job.
     * @throws IOException If the response has a status code > 399.
     * @see LanguageIdJob
     * @see <a
     * href="https://docs.rev.ai/api/language-identification/reference/#operation/SubmitLanguageIdentificationJob">https://docs.rev.ai/api/language-identification/reference/#operation/SubmitLanguageIdentificationJob</a>
     */
    public LanguageIdJob submitJobLocalFile(InputStream inputStream, LanguageIdJobOptions options)
            throws IOException {
        return submitJobLocalFile(inputStream, null, options);
    }

    private LanguageIdJob submitMultipartRequest(
            InputStream inputStream, String fileName, LanguageIdJobOptions options) throws IOException {
        RequestBody fileRequest = FileStreamRequestBody.create(inputStream, MediaType.parse("audio/*"));
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("media", fileName, fileRequest);
        return apiInterface.submitJobLocalFile(filePart, options).execute().body();
    }
}
