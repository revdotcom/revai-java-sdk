# Rev.ai Java SDK

[![Build Status](https://travis-ci.org/revdotcom/revai-java-sdk.svg?branch=develop)](https://travis-ci.org/revdotcom/revai-java-sdk)

## Documentation

See the [API docs](https://www.ai.rev.ai/docs) for more information about the API.

## Install the SDK
The recommended way to use the Rev.ai Java SDK is to import it into the project using Maven.

      <dependency>
        <groupId>ai.rev</groupId>
        <artifactId>revai-java-sdk</artifactId>
        <version>1.0.0</version>
      </dependency>
    
## Installing it locally
Once you've cloned the repo you can use Maven to build it locally and install it your local Maven .m2 repository.

    mvn install -Dmaven.test.skip=true

## Support

We support Java 8 and 11.

## Usage

All you need to get started is your Access Token, which can be generated on
your [Settings Page](https://www.ai.rev.ai/access_token). Create a client with the
given Access Token:

```
// Initialize your client with your ai.rev access token
String accessToken = "Your Access Token";
ApiClient apiClient = new ApiClient(accessToken);
```

### Checking credits remaining

```
RevAiAccount revAiAccount = apiClient.getAccount(); 
```

### Submitting a job

You can submit a local file
```
RevAiJob revAiJob = apiClient.submitJobLocalFile("./path/to/file.mp3");
```

or submit via a public url
```
RevAiJob revAiJob = apiClient.submitJobUrl("https://www.ai.ai.rev.ai/FTC_Sample_1.mp3");
```

or from FileInputStream, the filename is optional.
```
File file = new File("./path/to/file.mp3");
FileInputStream fileInputStream;
try {
  fileInputStream = new FileInputStream(file);
} catch (FileNotFoundException e) {
  throw new RuntimeException("Could not find file [" + file.getName() + "]");
}
RevAiJob revAiJob = apiClient.submitJobLocalFile(fileInputStream, String fileName, RevAiJobOptions options);
```

`RevAiJob` objects contain all the information normally found in a successful response from our
[Submit Job](https://www.ai.rev.ai/docs#operation/SubmitTranscriptionJob) endpoint.

If you want to get fancy, all send job methods can take a `RevAiJobOptions` object which contains methods 
to set optional parameters such as: `metadata`, `callback_url`, `skip_diarization`,`skip_punctuation`, 
`speaker_channels_count`, `filter_profanity`, `remove_disfluencies`  and `custom_vocabularies`. These are 
also described in the request body of the [Submit Job](https://www.ai.rev.ai/docs#operation/SubmitTranscriptionJob) endpoint.

### Checking your job's status

You can check the status of your transcription job using its `id`

```
RevAiJob revAiJob = apiClient.getJobDetails(revAiJob.getJobID());
```

`RevAiJob` objects contain all information normally found in a successful response from
our [Get Job](https://www.ai.rev.ai/docs#operation/GetJobById) endpoint

### Checking multiple files

You can retrieve a list of transcription jobs with optional parameters

```
List<RevAiJob> jobs = apiClient.getListOfJobs();

// limit amount of retrieved jobs
List<RevAiJob> jobs = apiClient.getListOfJobs(3);

// get jobs starting after a certain job id
List<RevAiJob> jobs = apiClient.getListOfJobs("Umx5c6F7pH7r");
```

`jobs` will contain a list of RevAiJob objects, having all information normally found in a successful response
from our [Get List of Jobs](https://www.ai.rev.ai/docs#operation/GetListOfJobs) endpoint

### Deleting a job

You can delete a transcription job using its `id`

```
apiClient.deleteJob(revAiJob.getJobID());
```

 All data related to the job, such as input media and transcript, will be permanently deleted.
 A job can only by deleted once it's completed (either with success or failure).


### Getting your transcript

Once your file is transcribed, you can get your transcript in a few different forms:

```
// as plain text
String transcriptText = apiClient.getTranscriptText(revAiJob.getJobID());

// or as an object
RevAiTranscript revAiTranscript = apiClient.getTranscriptObject(revAiJob.getJobID());
```

The text output is a string containing just the text of your transcript. The object form of 
the transcript contains all the information outlined in the response of the 
[Get Transcript](https://www.ai.rev.ai/docs#operation/GetTranscriptById) endpoint when using 
the json response schema.

### Getting captions output

Another way to retrieve your file is captions output. We support both .srt and .vtt outputs. 
See below for an example showing how you can get captions as a readable stream. If your job 
was submitted with multiple speaker channels you are required to provide the id of the channel 
you would like captioned.

```
InputStream inputStream = apiClient.getCaptions(revAiJob.getJobID(), RevAiCaptionType.SRT);

// with speaker channels
int channelId = 1;
InputStream inputStream = apiClient.getCaptions(revAiJob.getJobID(), RevAiCaptionType.VTT, channelId);
```

## Streaming Audio

In order to stream audio, you will need to setup a streaming client and the content type 
for the audio you will be sending.

```
StreamContentType streamContentType = new StreamContentType();
    streamContentType.setContentType("audio/x-raw");
    streamContentType.setLayout("interleaved");
    streamContentType.setFormat("S16LE");
    streamContentType.setRate(16000);
    streamContentType.setChannels(1);

StreamingClient streamingClient = new StreamingClient("Your Access Token");
```

You will need to create Listener that implements the RevAiWebSocketListener in order 
to handle WebSocket events.

```java
public class Listener implements RevAiWebSocketListener {

    @Override
    public void onConnected(ConnectedMessage message) {
        System.out.println("On Connected: " + connectMessage);
    }

    @Override
    public void onHypothesis(Hypothesis hypothesis) {
        System.out.println("On Hypothesis: " + hypothesis);
    }

    @Override
    public void onError(Throwable t, Response response) {
        System.out.println("On Error: " + response.toString());
    }

    @Override
    public void onClose(int code, String reason) {
        System.out.println("On Close: [" + code + "] " + reason);
    }

    @Override
    public void onOpen(Response response) {
        System.out.println("On Open: " + response.toString());
    }
}
```

Now you will be able to connect and start the streaming session by calling the `streamingClient.connect()` method and passing in the Listener! You can supply an optional `SessionConfig` object to the function in order to provide additional information for that session, such as metadata or enabled the profanity filter to be used.

```
Listener listener = new Listener();

SessionConfig sessionConfig = new SessionConfig();
sessionConfig.setMetaData("My first job");
sessionConfig.setFilterProfanity(true);

streamingClient.connect(clientListener, streamContentType, sessionConfig);
```

You can stream data over the WebSocket in the form of a `ByteString` using the `streamingClient.sendAudioData()` method.

```
streamingClient.sendAudioData(ByteString);
```

The streaming connection will close when you call the method `streamingClient.close()` or if you go 15 seconds without sending any audio data.


## For Rev.ai Developers
Before contributing to the project please install the following
* [IntelliJ IDE](https://www.jetbrains.com/idea/)
* [google-java-format plugin](https://plugins.jetbrains.com/plugin/8527-google-java-format)
 
 Before opening a pull-request please run the `Code > Reformat Code` option in any classes that were touched to ensure the code is formatted correctly.
