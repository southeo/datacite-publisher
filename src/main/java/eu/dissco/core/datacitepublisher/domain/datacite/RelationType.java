package eu.dissco.core.datacitepublisher.domain.datacite;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum RelationType {
  @JsonProperty("IsCitedBy") IS_CITED_BY,
  @JsonProperty("Cites") CITES,
  @JsonProperty("IsSupplementTo") IS_SUPPLEMENT_TO,
  @JsonProperty("IsSupplementedBy") IS_SUPPLEMENTED_BY,
  @JsonProperty("IsContinuedBy") IS_CONTINUED_BY,
  @JsonProperty("Continues") CONTINUES,
  @JsonProperty("IsDescribedBy") IS_DESCRIBED_BY,
  @JsonProperty("Describes") DESCRIBES,
  @JsonProperty("HasMetadata") HAS_METADATA,
  @JsonProperty("IsMetadataFor") IS_METADATA_FOR,
  @JsonProperty("HasVersion") HAS_VERSION,
  @JsonProperty("IsVersionOf") IS_VERSION_OF,
  @JsonProperty("IsNewVersionOf") IS_NEW_VERSION_OF,
  @JsonProperty("IsPreviousVersionOf") IS_PREVIOUS_VERSION_OF,
  @JsonProperty("IsPartOf") IS_PART_OF,
  @JsonProperty("HasPart") HAS_PART,
  @JsonProperty("IsPublishedIn") IS_PUBLISHED_IN,
  @JsonProperty("IsReferencedBy") IS_REFERENCED_BY,
  @JsonProperty("References") REFERENCES,
  @JsonProperty("IsDocumentedBy") IS_DOCUMENTED_BY,
  @JsonProperty("Documents") DOCUMENTS,
  @JsonProperty("IsCompiledBy") IS_COMPILED_BY,
  @JsonProperty("Compiles") COMPILES,
  @JsonProperty("IsVariantFormOf") IS_VARIANT_FORM_OF,
  @JsonProperty("IsOriginalFormOf") IS_ORIGINAL_FORM_OF,
  @JsonProperty("IsIdenticalTo") IS_IDENTICAL_TO,
  @JsonProperty("IsReviewedBy") IS_REVIEWED_BY,
  @JsonProperty("Reviews") REVIEWS,
  @JsonProperty("IsDerivedFrom") IS_DERIVED_FROM,
  @JsonProperty("IsSourceOf") IS_SOURCE_OF,
  @JsonProperty("IsRequiredBy") IS_REQUIRED_BY,
  @JsonProperty("Requires") REQUIRES,
  @JsonProperty("IsObsoletedBy") IS_OBSOLETED_BY,
  @JsonProperty("Obsoletes") OBSOLETES;
}
