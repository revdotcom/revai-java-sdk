package ai.rev.topicextraction;

import ai.rev.helpers.GenericApiInterface;
import ai.rev.topicextraction.models.TopicExtractionJob;
import ai.rev.topicextraction.models.TopicExtractionSubmission;
import ai.rev.topicextraction.models.TopicExtractionResult;

/**
 * The ApiInterface is a type-safe Retrofit interface that presents all the endpoints that are made
 * to communicate with the Rev AI async API.
 */
public interface ApiInterface implements GenericApiInterface<TopicExtractionSubmission, TopicExtractionJob, TopicExtractionResult> {
}
