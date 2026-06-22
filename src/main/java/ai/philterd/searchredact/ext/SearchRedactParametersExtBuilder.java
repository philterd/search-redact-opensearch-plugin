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

import org.opensearch.core.common.io.stream.StreamInput;
import org.opensearch.core.common.io.stream.StreamOutput;
import org.opensearch.core.xcontent.XContentBuilder;
import org.opensearch.core.xcontent.XContentParser;
import org.opensearch.search.SearchExtBuilder;

import java.io.IOException;
import java.util.Objects;

/**
 * Subclass of {@link SearchExtBuilder} to access SearchRedact parameters.
 */
public class SearchRedactParametersExtBuilder extends SearchExtBuilder {

    /**
     * The name of the "ext" section containing SearchRedact parameters.
     */
    public static final String SEARCH_REDACT_PARAMETERS_NAME = "search-redact";

    private SearchRedactParameters params;

    /**
     * Creates a new instance.
     */
    public SearchRedactParametersExtBuilder() {}

    /**
     * Creates a new instance from a {@link StreamInput}.
     * @param input A {@link StreamInput} containing the parameters.
     * @throws IOException Thrown if the stream cannot be read.
     */
    public SearchRedactParametersExtBuilder(StreamInput input) throws IOException {
        this.params = new SearchRedactParameters(input);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getClass(), this.params);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof SearchRedactParametersExtBuilder)) {
            return false;
        }

        return this.params.equals(((SearchRedactParametersExtBuilder) obj).getParams());
    }

    @Override
    public String getWriteableName() {
        return SEARCH_REDACT_PARAMETERS_NAME;
    }

    @Override
    public void writeTo(StreamOutput out) throws IOException {
        this.params.writeTo(out);
    }

    @Override
    public XContentBuilder toXContent(XContentBuilder builder, Params params) throws IOException {
        return builder.value(this.params);
    }

    /**
     * Parses the search-redact section of the ext block.
     * @param parser A {@link XContentParser parser}.
     * @return The {@link SearchRedactParameters paramers}.
     * @throws IOException Thrown if the SearchRedact parameters cannot be read.
     */
    public static SearchRedactParametersExtBuilder parse(XContentParser parser) throws IOException {
        final SearchRedactParametersExtBuilder builder = new SearchRedactParametersExtBuilder();
        builder.setParams(SearchRedactParameters.parse(parser));
        return builder;
    }

    /**
     * Gets the {@link SearchRedactParameters params}.
     * @return The {@link SearchRedactParameters params}.
     */
    public SearchRedactParameters getParams() {
        return params;
    }

    /**
     * Set the {@link SearchRedactParameters params}.
     * @param params The {@link SearchRedactParameters params}.
     */
    public void setParams(SearchRedactParameters params) {
        this.params = params;
    }

}