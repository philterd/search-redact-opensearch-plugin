# Configuration

Search Redact is configured per search request, in the `ext.search-redact` block of a `_search`
request. There is nothing to configure in `opensearch.yml`.

## Request parameters

| Parameter | Required | Description |
|-----------|----------|-------------|
| `field` | Yes | The field to redact, or a comma-separated list of fields. Each named field in every hit is redacted in the response. |
| `policy` | Yes | A [Phileas policy](https://philterd.github.io/phileas/) as a JSON string. The policy defines which identifiers to detect and how to redact them. |

## Policies

The `policy` value is a standard Phileas policy. Phileas detects structured identifiers (such as
Social Security numbers, credit cards, and email addresses) with pattern matching and validators,
and names and other entities with NLP models, and then applies the redaction strategy you choose
(for example `REDACT`, with an optional `redactionFormat`).

See the Phileas documentation for the full policy reference:

- [Filter policies](https://philterd.github.io/phileas/filter_policies/filter_policies/)
- [Filters](https://philterd.github.io/phileas/filter_policies/filters/)
- [Filter strategies](https://philterd.github.io/phileas/filter_policies/filter_strategies/)

## Validating output

Detection is probabilistic. Test your policy against representative documents and confirm the fields
are redacted as you expect before relying on it. You are responsible for validating the output
against your own data.
