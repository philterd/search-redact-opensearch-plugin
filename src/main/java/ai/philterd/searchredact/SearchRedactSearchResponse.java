/*
 *     Copyright 2025 Philterd, LLC @ https://www.philterd.ai
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ai.philterd.searchredact;

import ai.philterd.searchredact.ext.SearchRedactParametersExtBuilder;
import org.opensearch.action.search.SearchResponse;
import org.opensearch.action.search.SearchResponseSections;
import org.opensearch.action.search.ShardSearchFailure;
import org.opensearch.core.xcontent.ToXContent;
import org.opensearch.core.xcontent.XContentBuilder;

import java.io.IOException;

public class SearchRedactSearchResponse extends SearchResponse {

    private static final String EXT_SECTION_NAME = "ext";
    private static final String SEARCH_REDACT_POLICY_NAME = "policy";

    private final String policy;

    /**
     * Creates a new SearchRedact search response.
     *
     * @param internalResponse The internal response.
     * @param scrollId         The scroll ID.
     * @param totalShards      The count total shards.
     * @param successfulShards The count of successful shards.
     * @param skippedShards    The count of skipped shards.
     * @param tookInMillis     The time took in milliseconds.
     * @param shardFailures    An array of {@link ShardSearchFailure}.
     * @param clusters         The {@link Clusters}.
     * @param policy           The policy.
     */
    public SearchRedactSearchResponse(
            SearchResponseSections internalResponse,
            String scrollId,
            int totalShards,
            int successfulShards,
            int skippedShards,
            long tookInMillis,
            ShardSearchFailure[] shardFailures,
            SearchResponse.Clusters clusters,
            String policy
    ) {
        super(internalResponse, scrollId, totalShards, successfulShards, skippedShards, tookInMillis, shardFailures, clusters);
        this.policy = policy;
    }

    @Override
    public XContentBuilder toXContent(XContentBuilder builder, ToXContent.Params params) throws IOException {

        builder.startObject();
        innerToXContent(builder, params);

        builder.startObject(EXT_SECTION_NAME);
        builder.startObject(SearchRedactParametersExtBuilder.SEARCH_REDACT_PARAMETERS_NAME);
        builder.field(SEARCH_REDACT_POLICY_NAME, this.policy);
        builder.endObject();
        builder.endObject();
        builder.endObject();

        return builder;

    }

}
