# Usage

Search Redact runs during a `_search` request. Add an `ext.search-redact` block that names the
field to redact and the Phileas policy to apply, and the matching documents come back with that
field redacted.

## Example

Index a document with a free-text field:

```
curl -s -X POST "http://localhost:9200/sample_index/_doc" -H 'Content-Type: application/json' -d'
{
  "name": "Example",
  "description": "My email is test@example.com"
}'
```

Search, redacting the `description` field with a policy that redacts email addresses:

```
curl -s "http://localhost:9200/sample_index/_search" -H "Content-Type: application/json" -d'
{
  "ext": {
    "search-redact": {
      "field": "description",
      "policy": "{\"identifiers\":{\"emailAddress\":{\"emailAddressFilterStrategies\":[{\"strategy\":\"REDACT\",\"redactionFormat\":\"{{{REDACTED-%t}}}\"}]}}}"
    }
  },
  "query": { "match_all": {} }
}'
```

The hit returned by the search has the email address in `description` redacted. The stored document
is unchanged; only the response is redacted.

## Redacting multiple fields

`field` accepts a single field name or a comma-separated list of field names:

```
"field": "description,notes"
```

See [Configuration](configuration.md) for the full set of request parameters and how policies work.
