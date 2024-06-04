package eu.dissco.core.datacitepublisher;

import static eu.dissco.core.datacitepublisher.schemas.DigitalSpecimen.MaterialSampleType.ORGANISM_PART;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import eu.dissco.core.datacitepublisher.configuration.InstantDeserializer;
import eu.dissco.core.datacitepublisher.configuration.InstantSerializer;
import eu.dissco.core.datacitepublisher.domain.datacite.DataCiteConstants;
import eu.dissco.core.datacitepublisher.domain.datacite.DcAlternateIdentifier;
import eu.dissco.core.datacitepublisher.domain.datacite.DcAttributes;
import eu.dissco.core.datacitepublisher.domain.datacite.DcContributor;
import eu.dissco.core.datacitepublisher.domain.datacite.DcCreator;
import eu.dissco.core.datacitepublisher.domain.datacite.DcDate;
import eu.dissco.core.datacitepublisher.domain.datacite.DcDescription;
import eu.dissco.core.datacitepublisher.domain.datacite.DcNameIdentifiers;
import eu.dissco.core.datacitepublisher.domain.datacite.DcRelatedIdentifiers;
import eu.dissco.core.datacitepublisher.domain.datacite.DcSubject;
import eu.dissco.core.datacitepublisher.domain.datacite.DcTitle;
import eu.dissco.core.datacitepublisher.domain.datacite.DcType;
import eu.dissco.core.datacitepublisher.domain.datacite.UriScheme;
import eu.dissco.core.datacitepublisher.schemas.DigitalSpecimen;
import eu.dissco.core.datacitepublisher.schemas.DigitalSpecimen.MaterialSampleType;
import eu.dissco.core.datacitepublisher.schemas.DigitalSpecimen.TopicCategory;
import eu.dissco.core.datacitepublisher.schemas.DigitalSpecimen.TopicDiscipline;
import eu.dissco.core.datacitepublisher.schemas.DigitalSpecimen.TopicDomain;
import eu.dissco.core.datacitepublisher.schemas.MediaObject;
import eu.dissco.core.datacitepublisher.schemas.MediaObject.LinkedDigitalObjectType;
import eu.dissco.core.datacitepublisher.schemas.MediaObject.MediaFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class TestUtils {

  private TestUtils() {
  }

  public static final String SUFFIX = "QR1-P21-9FW";
  public static final String PREFIX = "10.3535";
  public static final String PID = "https://doi.org/" + PREFIX + "/" + SUFFIX;
  public static final String DOI = PREFIX + "/" + SUFFIX;
  public static final String ROR = "https://ror.org/0566bfb96";
  public static final String HOST_NAME = "Naturalis Biodiversity Center";
  public static final String REFERENT_NAME = "New digital object";
  public static final String PID_ISSUE_DATE = "2024-03-08'T'11:17:13Z";
  public static final String LOCS = "<locations><location href=\"https://sandbox.dissco.tech/ds/10.3535/QR1-P21-9FW\" id=\"0\" weight=\"1\"/><location href=\"https://sandbox.dissco.tech/api/v1/specimens/10.3535/QR1-P21-9FW\" id=\"1\" weight=\"0\"/></locations>";
  public static final List<String> LOCS_ARR = List.of(
      "https://sandbox.dissco.tech/ds/10.3535/QR1-P21-9FW",
      "https://sandbox.dissco.tech/api/v1/specimens/10.3535/QR1-P21-9FW");
  public static final TopicDiscipline TOPIC_DISCIPLINE = TopicDiscipline.BOTANY;
  public static final TopicCategory TOPIC_CATEGORY = TopicCategory.ALGAE;
  public static final TopicDomain TOPIC_DOMAIN = TopicDomain.LIFE;
  public static final MaterialSampleType MATERIAL_SAMPLE_TYPE = ORGANISM_PART;
  public static final String LOCAL_ID = "PLANT-123";
  public static final ObjectMapper MAPPER;
  public static final XmlMapper XML_MAPPER;

  static {
    var mapper = new ObjectMapper().findAndRegisterModules();
    SimpleModule dateModule = new SimpleModule();
    dateModule.addSerializer(Instant.class, new InstantSerializer());
    dateModule.addDeserializer(Instant.class, new InstantDeserializer());
    mapper.registerModule(dateModule);
    mapper.setSerializationInclusion(Include.NON_NULL);
    MAPPER = mapper;
  }

  static {
    XML_MAPPER = new XmlMapper();
  }

  public static DcAttributes givenSpecimenAttributes(String doi) {
    return DcAttributes.builder()
        .suffix(SUFFIX)
        .doi(doi)
        .creators(List.of(DcCreator.builder()
            .name(HOST_NAME)
            .nameIdentifiers(List.of(givenIdentifier()))
            .build()))
        .titles(List.of(DcTitle.builder()
            .title(REFERENT_NAME).build()))
        .publicationYear(2024)
        .contributors(List.of(DcContributor.builder()
            .name(HOST_NAME)
            .nameIdentifiers(List.of(givenIdentifier()))
            .build()))
        .alternateIdentifiers(List.of(DcAlternateIdentifier.builder()
            .alternateIdentifierType("primarySpecimenObjectId")
            .alternateIdentifier(LOCAL_ID).build()))
        .dates(List.of(DcDate.builder()
            .date("2024-03-08")
            .build()))
        .relatedIdentifiers(List.of(
            DcRelatedIdentifiers.builder()
                .relationType("IsVariantFormOf")
                .relatedIdentifier(
                    "https://sandbox.dissco.tech/api/v1/specimens/10.3535/QR1-P21-9FW")
                .relatedIdentifierType("URL")
                .build()))
        .descriptions(givenSpecimenDescription())
        .types(givenType(DataCiteConstants.TYPE_DS))
        .url("https://sandbox.dissco.tech/ds/10.3535/QR1-P21-9FW")
        .build();
  }

  public static DcType givenType(String resourceType){
    return DcType.builder()
        .resourceType(resourceType)
        .build();
  }

  public static DcAttributes givenSpecimenAttributes() {
    return givenSpecimenAttributes(DOI);
  }

  public static DcAttributes givenSpecimenAttributesFull() {
    return DcAttributes.builder()
        .suffix(SUFFIX)
        .doi(DOI)
        .creators(List.of(DcCreator.builder()
            .name(HOST_NAME)
            .nameIdentifiers(List.of(givenIdentifier()))
            .build()))
        .titles(List.of(DcTitle.builder()
            .title(REFERENT_NAME).build()))
        .publicationYear(2024)
        .contributors(List.of(DcContributor.builder()
            .name(HOST_NAME)
            .nameIdentifiers(List.of(givenIdentifier()))
            .build()))
        .alternateIdentifiers(List.of(DcAlternateIdentifier.builder()
            .alternateIdentifierType("primarySpecimenObjectId")
            .alternateIdentifier(LOCAL_ID).build()))
        .dates(List.of(DcDate.builder()
            .date("2024-03-08")
            .build()))
        .relatedIdentifiers(List.of(
            DcRelatedIdentifiers.builder()
                .relationType("IsVariantFormOf")
                .relatedIdentifier(
                    "https://sandbox.dissco.tech/api/v1/specimens/10.3535/QR1-P21-9FW")
                .relatedIdentifierType("URL")
                .build()))
        .types(givenType(DataCiteConstants.TYPE_DS))
        .url("https://sandbox.dissco.tech/ds/10.3535/QR1-P21-9FW")
        .subjects(List.of(
            DcSubject.builder()
                .subjectScheme("topicDiscipline")
                .subject(TOPIC_DISCIPLINE.value())
                .build(),
            DcSubject.builder()
                .subjectScheme("topicDomain")
                .subject(TOPIC_DOMAIN.value())
                .build(),
            DcSubject.builder()
                .subject(TOPIC_CATEGORY.value())
                .subjectScheme("topicCategory")
                .build())
        )
        .descriptions(givenSpecimenDescriptionFull())
        .build();
  }

  public static JsonNode givenSpecimenJson() {
    return MAPPER.valueToTree(givenSpecimenAttributes());
  }

  public static DcAttributes givenMediaAttributes() {
    return DcAttributes.builder()
        .suffix(SUFFIX)
        .doi(DOI)
        .creators(List.of(DcCreator.builder()
            .name(HOST_NAME)
            .nameIdentifiers(List.of(givenIdentifier()))
            .build()))
        .titles(List.of(DcTitle.builder()
            .title(REFERENT_NAME)
            .build()))
        .publicationYear(2024)
        .contributors(List.of(DcContributor.builder()
            .name(HOST_NAME)
            .nameIdentifiers(List.of(givenIdentifier()))
            .build()))
        .subjects(List.of(
            DcSubject.builder()
                .subjectScheme("linkedDigitalObjectType")
                .subject(LinkedDigitalObjectType.DIGITAL_SPECIMEN.value())
                .build()))
        .alternateIdentifiers(List.of(
            DcAlternateIdentifier.builder()
                .alternateIdentifierType("primaryMediaId")
                .alternateIdentifier(LOCAL_ID).build()))
        .dates(List.of(DcDate.builder()
            .date("2024-03-08")
            .build()))
        .relatedIdentifiers(List.of(
            DcRelatedIdentifiers.builder()
                .relationType("IsVariantFormOf")
                .relatedIdentifier(
                    "https://sandbox.dissco.tech/api/v1/specimens/10.3535/QR1-P21-9FW")
                .relatedIdentifierType("URL").build()))
        .descriptions(givenMediaDescriptionFull())
        .types(givenType(DataCiteConstants.TYPE_MO))
        .url("https://sandbox.dissco.tech/ds/10.3535/QR1-P21-9FW")
        .build();
  }

  public static DcAttributes givenMediaAttributesFull() {
    return DcAttributes.builder()
        .suffix(SUFFIX)
        .doi(DOI)
        .creators(List.of(DcCreator.builder()
            .name(HOST_NAME)
            .nameIdentifiers(List.of(givenIdentifier()))
            .build()))
        .titles(List.of(DcTitle.builder()
            .title(REFERENT_NAME)
            .build()))
        .publicationYear(2024)
        .contributors(List.of(DcContributor.builder()
            .name(HOST_NAME)
            .nameIdentifiers(List.of(givenIdentifier()))
            .build()))
        .alternateIdentifiers(List.of(
            DcAlternateIdentifier.builder()
                .alternateIdentifierType("primaryMediaId")
                .alternateIdentifier(LOCAL_ID).build()))
        .dates(List.of(DcDate.builder()
            .date("2024-03-08")
            .build()))
        .relatedIdentifiers(List.of(
            DcRelatedIdentifiers.builder()
                .relationType("IsVariantFormOf")
                .relatedIdentifier(
                    "https://sandbox.dissco.tech/api/v1/specimens/10.3535/QR1-P21-9FW")
                .relatedIdentifierType("URL").build()))
        .descriptions(givenMediaDescriptionFull())
        .types(givenType(DataCiteConstants.TYPE_MO))
        .url("https://sandbox.dissco.tech/ds/10.3535/QR1-P21-9FW")
        .subjects(List.of(
            DcSubject.builder()
                .subjectScheme("mediaFormat")
                .subject(MediaFormat.IMAGE.value())
                .build(),
            DcSubject.builder()
                .subjectScheme("linkedDigitalObjectType")
                .subject(LinkedDigitalObjectType.DIGITAL_SPECIMEN.value())
                .build())
        )
        .build();
  }

  private static List<DcDescription> givenSpecimenDescription() {
    return List.of(
        DcDescription.builder().description(
            "Digital Specimen for the physical specimen hosted at " + HOST_NAME + "."
        ).build()
    );
  }

  private static List<DcDescription> givenSpecimenDescriptionFull() {
    var descriptions = new ArrayList<>(givenSpecimenDescription());
    descriptions.add(DcDescription.builder().description(
        "Material sample type is " + MATERIAL_SAMPLE_TYPE + ".").build());
    return descriptions;
  }

  private static List<DcDescription> givenMediaDescriptionFull() {
    return List.of(
        DcDescription.builder()
            .description("Media object hosted at " + HOST_NAME + ".")
            .build(),
        DcDescription.builder()
            .description(
                "Is media for an object of type " + LinkedDigitalObjectType.DIGITAL_SPECIMEN.value()
                    + ".")
            .build()
    );
  }

  public static DigitalSpecimen givenDigitalSpecimen() {
    return new DigitalSpecimen()
        .with10320Loc(LOCS)
        .withPid(PID)
        .withIssuedForAgent(ROR)
        .withIssuedForAgentName(HOST_NAME)
        .withPidRecordIssueDate(PID_ISSUE_DATE)
        .withPrimarySpecimenObjectId(LOCAL_ID)
        .withSpecimenHost(ROR)
        .withSpecimenHostName(HOST_NAME)
        .withReferentName(REFERENT_NAME);
  }

  public static DigitalSpecimen givenDigitalSpecimenFull() {
    return givenDigitalSpecimen()
        .withTopicDiscipline(TOPIC_DISCIPLINE)
        .withTopicCategory(TOPIC_CATEGORY)
        .withTopicDomain(TOPIC_DOMAIN)
        .withMaterialSampleType(MATERIAL_SAMPLE_TYPE);
  }

  public static MediaObject givenMediaObject() {
    return new MediaObject()
        .with10320Loc(LOCS)
        .withPid(PID)
        .withIssuedForAgent(ROR)
        .withIssuedForAgentName(HOST_NAME)
        .withPidRecordIssueDate(PID_ISSUE_DATE)
        .withPrimaryMediaId(LOCAL_ID)
        .withReferentName(REFERENT_NAME)
        .withMediaHostName(HOST_NAME)
        .withMediaHost(ROR)
        .withLinkedDigitalObjectType(LinkedDigitalObjectType.DIGITAL_SPECIMEN);
  }

  public static MediaObject givenMediaObjectFull() {
    return givenMediaObject()
        .withMediaFormat(MediaFormat.IMAGE);
  }


  public static DcNameIdentifiers givenIdentifier() {
    return DcNameIdentifiers
        .builder()
        .nameIdentifier(ROR)
        .schemeUri(UriScheme.ROR.getUri())
        .nameIdentifierScheme(UriScheme.ROR.getSchemeName())
        .build();
  }


}
