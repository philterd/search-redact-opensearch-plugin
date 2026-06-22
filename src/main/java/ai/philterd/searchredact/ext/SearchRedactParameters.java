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
package ai.philterd.searchredact.ext;

import org.opensearch.action.search.SearchRequest;
import org.opensearch.core.ParseField;
import org.opensearch.core.common.io.stream.StreamInput;
import org.opensearch.core.common.io.stream.StreamOutput;
import org.opensearch.core.common.io.stream.Writeable;
import org.opensearch.core.xcontent.ObjectParser;
import org.opensearch.core.xcontent.ToXContentObject;
import org.opensearch.core.xcontent.XContentBuilder;
import org.opensearch.core.xcontent.XContentParser;
import org.opensearch.search.SearchExtBuilder;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

/**
 * The SearchRedact parameters available in the ext.
 */
public class SearchRedactParameters implements Writeable, ToXContentObject {

    private static final ObjectParser<SearchRedactParameters, Void> PARSER;
    private static final ParseField POLICY = new ParseField("policy");
    private static final ParseField CONTEXT = new ParseField("context");
    private static final ParseField FIELD_NAME = new ParseField("field");

    static {
        PARSER = new ObjectParser<>(SearchRedactParametersExtBuilder.SEARCH_REDACT_PARAMETERS_NAME, SearchRedactParameters::new);
        PARSER.declareString(SearchRedactParameters::setPolicy, POLICY);
        PARSER.declareString(SearchRedactParameters::setContext, CONTEXT);
        PARSER.declareString(SearchRedactParameters::setFieldName, FIELD_NAME);
    }

    /**
     * Get the {@link SearchRedactParameters} from a {@link SearchRequest}.
     * @param request A {@link SearchRequest},
     * @return The SearchRedact {@link SearchRedactParameters parameters}.
     */
    public static SearchRedactParameters getSearchRedactParameters(final SearchRequest request) {

        SearchRedactParametersExtBuilder builder = null;

        if (request.source() != null && request.source().ext() != null && !request.source().ext().isEmpty()) {
            final Optional<SearchExtBuilder> b = request.source()
                    .ext()
                    .stream()
                    .filter(bldr -> SearchRedactParametersExtBuilder.SEARCH_REDACT_PARAMETERS_NAME.equals(bldr.getWriteableName()))
                    .findFirst();
            if (b.isPresent()) {
                builder = (SearchRedactParametersExtBuilder) b.get();
            }
        }

        if (builder != null) {
            return builder.getParams();
        } else {
            return null;
        }

    }

    private String policy;
    private String context;
    private String fieldName;

    /**
     * Creates a new instance.
     */
    public SearchRedactParameters() {}

    /**
     * Creates a new instance.
     * @param input The {@link StreamInput} to read parameters from.
     * @throws IOException Thrown if the parameters cannot be read.
     */
    public SearchRedactParameters(StreamInput input) throws IOException {
        this.policy = input.readString();
        this.context = input.readString();
        this.fieldName = input.readString();
    }

    /**
     * Creates a new instance.
     * @param policy The name of the policy to apply.
     */
    public SearchRedactParameters(String policy) {
        this.policy = policy;
    }

    @Override
    public XContentBuilder toXContent(XContentBuilder xContentBuilder, Params params) throws IOException {
        return xContentBuilder
                .field(POLICY.getPreferredName(), this.policy)
                .field(CONTEXT.getPreferredName(), this.context)
                .field(FIELD_NAME.getPreferredName(), this.fieldName);
    }

    @Override
    public void writeTo(StreamOutput out) throws IOException {
        out.writeString(policy);
        out.writeString(context);
        out.writeString(fieldName);
    }

    /**
     * Create the {@link SearchRedactParameters} from a {@link XContentParser}.
     * @param parser An {@link XContentParser}.
     * @return The {@link SearchRedactParameters}.
     * @throws IOException Thrown if the parameters cannot be read.
     */
    public static SearchRedactParameters parse(XContentParser parser) throws IOException {
        return PARSER.parse(parser, null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final SearchRedactParameters other = (SearchRedactParameters) o;
        return Objects.equals(this.policy, other.getPolicy());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getClass(), this.policy);
    }

    public String getPolicy() {
        return policy;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

}