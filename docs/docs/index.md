# Search Redact for OpenSearch

Search Redact is an open source OpenSearch plugin that redacts PII and PHI from search results at
query time, before they reach the user. It runs inside your OpenSearch cluster and uses the
[Phileas](https://philterd.github.io/phileas/) engine for detection.

The plugin is licensed under the Apache License, Version 2.0. The source is on
[GitHub](https://github.com/philterd/search-redact-opensearch-plugin). There is also a
[version for Elasticsearch](https://github.com/philterd/search-redact-elasticsearch-plugin).

## How it works

You add an `ext` block to a `_search` request that names the field (or fields) to redact and the
Phileas policy to apply. The matching documents come back with those fields redacted in the
response. Your indexed documents are not modified: redaction is applied to the search response, not
to the stored data.

Because the policy is part of the request, different applications or roles can apply different
redaction to the same index without reindexing.

## A note on detection

Detection is probabilistic. The plugin is designed to reduce how much sensitive data appears in
search results; it does not catch every instance, and you are responsible for validating the output
against your own data.

## Next steps

- [Installation](installation.md)
- [Usage](usage.md)
- [Configuration](configuration.md)
