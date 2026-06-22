# Search Redact Plugin for OpenSearch

This repository is a plugin for Amazon OpenSearch that redacts PII from search results. It uses the [phileas](https://github.com/philterd/phileas/) library for redaction.

There is also a [version](https://github.com/philterd/search-redact-elasticsearch-plugin) available for Elasticsearch.

## Related Topics

If you are here, you may also be interested in:
* [Field-level security in OpenSearch](https://opensearch.org/docs/latest/security/access-control/field-level-security/)
* [Field masking in OpenSearch](https://opensearch.org/docs/latest/security/access-control/field-masking/)

## Build and Install

To build the plugin:

```
./gradlew build
```

To install the plugin:

```
/usr/share/opensearch/bin/opensearch-plugin install --batch file:/path/to/search-redact-1.0.0-SNAPSHOT.zip
```

## Using Docker

To quickly run OpenSearch and the plugin for development or testing:

```
docker compose build
docker compose up
```

## Usage

To use the plugin, create an index and then index some documents:

```
curl -s -X PUT "http://localhost:9200/sample_index" -H 'Content-Type: application/json'
```

```
curl -s -X POST "http://localhost:9200/sample_index/_doc" -H 'Content-Type: application/json' -d'
{
  "name": "Another Example",
  "description": "My email is something@something.com ok!"
}'

curl -s -X POST "http://localhost:9200/sample_index/_doc" -H 'Content-Type: application/json' -d'
{
  "name": "Yet Another Example",
  "description": "No email addresses in this one"
}'

curl -s -X POST "http://localhost:9200/sample_index/_doc" -H 'Content-Type: application/json' -d'
{
  "name": "A Third Example",
  "description": "tom@tom.com"
}'

```

Next, do a search providing a filter policy and specifying which field you want to redact. (For more on policies, see Phileas' documentation on [Policies](https://philterd.github.io/phileas/filter_policies/filter_policies/).) In this example,
we are going to redact email addresses that appear in the `description` field:

```
curl -s http://localhost:9200/sample_index/_search -H "Content-Type: application/json" -d'
   {
    "ext": {
       "search-redact": {
          "field": "description",
          "policy": "{\"identifiers\": {\"emailAddress\":{\"emailAddressFilterStrategies\":[{\"strategy\":\"REDACT\",\"redactionFormat\":\"{{{REDACTED-%t}}}\"}]}}}"
        }
     },
     "query": {
       "match_all": {}
     }
   }'
```

The value of `field` in the request can be a single field, or a comma-separated list of fields to redact.

In the response, you will see the email address in the indexed document has been redacted:

```
{
  "took": 2,
  "timed_out": false,
  "_shards": {
    "total": 1,
    "successful": 1,
    "skipped": 0,
    "failed": 0
  },
  "hits": {
    "total": {
      "value": 3,
      "relation": "eq"
    },
    "max_score": 1,
    "hits": [
      {
        "_index": "sample_index",
        "_id": "3sZ8cJQBeC9RICk83_tV",
        "_score": 1,
        "_source": {
          "name": "Another Example",
          "description": "My email is {{{REDACTED-email-address}}} ok!"
        }
      },
      {
        "_index": "sample_index",
        "_id": "38Z8cJQBeC9RICk83_t1",
        "_score": 1,
        "_source": {
          "name": "Yet Another Example",
          "description": "No email addresses in this one"
        }
      },
      {
        "_index": "sample_index",
        "_id": "4MZ8cJQBeC9RICk83_uI",
        "_score": 1,
        "_source": {
          "name": "A Third Example",
          "description": "{{{REDACTED-email-address}}}"
        }
      }
    ]
  }
}
```

## License
This code is licensed under the Apache 2.0 License. See [LICENSE.txt](LICENSE.txt).

## Copyright
Copyright 2025 Philterd, LLC. See [NOTICE](NOTICE.txt) for details.
