
import java.util.ArrayList;
import java.util.List;

import org.hl7.fhir.r4.model.AllergyIntolerance;
import org.hl7.fhir.r4.model.Appointment;
import org.hl7.fhir.r4.model.Appointment.AppointmentParticipantComponent;
import org.hl7.fhir.r4.model.Appointment.AppointmentStatus;
import org.hl7.fhir.r4.model.Appointment.ParticipationStatus;
import org.hl7.fhir.r4.model.Attachment;
import org.hl7.fhir.r4.model.Base64BinaryType;
import org.hl7.fhir.r4.model.Binary;
import org.hl7.fhir.r4.model.CarePlan;
import org.hl7.fhir.r4.model.CarePlan.CarePlanIntent;
import org.hl7.fhir.r4.model.CarePlan.CarePlanStatus;
import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.Coding;
import org.hl7.fhir.r4.model.Condition;
import org.hl7.fhir.r4.model.ContactPoint;
import org.hl7.fhir.r4.model.ContactPoint.ContactPointSystem;
import org.hl7.fhir.r4.model.ContactPoint.ContactPointUse;
import org.hl7.fhir.r4.model.DateTimeType;
import org.hl7.fhir.r4.model.DateType;
import org.hl7.fhir.r4.model.DecimalType;
import org.hl7.fhir.r4.model.DiagnosticReport;
import org.hl7.fhir.r4.model.DiagnosticReport.DiagnosticReportMediaComponent;
import org.hl7.fhir.r4.model.DiagnosticReport.DiagnosticReportStatus;
import org.hl7.fhir.r4.model.DocumentReference;
import org.hl7.fhir.r4.model.DocumentReference.DocumentReferenceContentComponent;
import org.hl7.fhir.r4.model.DocumentReference.ReferredDocumentStatus;
import org.hl7.fhir.r4.model.Dosage;
import org.hl7.fhir.r4.model.Encounter;
import org.hl7.fhir.r4.model.Encounter.EncounterHospitalizationComponent;
import org.hl7.fhir.r4.model.Encounter.EncounterStatus;
import org.hl7.fhir.r4.model.Enumerations.AdministrativeGender;
import org.hl7.fhir.r4.model.Enumerations.DocumentReferenceStatus;
import org.hl7.fhir.r4.model.FamilyMemberHistory;
import org.hl7.fhir.r4.model.FamilyMemberHistory.FamilyHistoryStatus;
import org.hl7.fhir.r4.model.FamilyMemberHistory.FamilyMemberHistoryConditionComponent;
import org.hl7.fhir.r4.model.Identifier;
import org.hl7.fhir.r4.model.ImagingStudy;
import org.hl7.fhir.r4.model.ImagingStudy.ImagingStudySeriesComponent;
import org.hl7.fhir.r4.model.ImagingStudy.ImagingStudySeriesInstanceComponent;
import org.hl7.fhir.r4.model.ImagingStudy.ImagingStudyStatus;
import org.hl7.fhir.r4.model.Immunization;
import org.hl7.fhir.r4.model.Immunization.ImmunizationStatus;
import org.hl7.fhir.r4.model.InstantType;
import org.hl7.fhir.r4.model.Media;
import org.hl7.fhir.r4.model.Media.MediaStatus;
import org.hl7.fhir.r4.model.MedicationRequest;
import org.hl7.fhir.r4.model.MedicationRequest.MedicationRequestIntent;
import org.hl7.fhir.r4.model.MedicationRequest.MedicationRequestStatus;
import org.hl7.fhir.r4.model.MedicationStatement;
import org.hl7.fhir.r4.model.MedicationStatement.MedicationStatementStatus;
import org.hl7.fhir.r4.model.Narrative.NarrativeStatus;
import org.hl7.fhir.r4.model.Observation;
import org.hl7.fhir.r4.model.Observation.ObservationReferenceRangeComponent;
import org.hl7.fhir.r4.model.Observation.ObservationStatus;
import org.hl7.fhir.r4.model.Organization;
import org.hl7.fhir.r4.model.Patient;
import org.hl7.fhir.r4.model.Period;
import org.hl7.fhir.r4.model.Practitioner;
import org.hl7.fhir.r4.model.PractitionerRole;
import org.hl7.fhir.r4.model.PractitionerRole.PractitionerRoleAvailableTimeComponent;
import org.hl7.fhir.r4.model.Procedure;
import org.hl7.fhir.r4.model.Procedure.ProcedureStatus;
import org.hl7.fhir.r4.model.Quantity;
import org.hl7.fhir.r4.model.Reference;
import org.hl7.fhir.r4.model.ServiceRequest;
import org.hl7.fhir.r4.model.ServiceRequest.ServiceRequestIntent;
import org.hl7.fhir.r4.model.ServiceRequest.ServiceRequestStatus;
import org.hl7.fhir.r4.model.Specimen;
import org.hl7.fhir.r4.model.Specimen.SpecimenCollectionComponent;
import org.hl7.fhir.r4.model.TimeType;
import org.hl7.fhir.r4.model.Timing;
import org.hl7.fhir.r4.model.Timing.TimingRepeatComponent;
import org.hl7.fhir.r4.model.Timing.UnitsOfTime;

/**
 * The FhirResourcePopulator class populates all the FHIR resources 
 */
public class ResourcePopulator {
	
	// Populate Patient Resource
	public static Patient populatePatientResource()
	{
		Patient patient = new Patient();
		patient.setId("Patient-01");
		patient.getMeta().setVersionId("1").setLastUpdatedElement(new InstantType("2020-07-09T14:58:58.181+05:30")).addProfile("https://nrces.in/ndhm/fhir/r4/StructureDefinition/Patient");
		patient.getText().setStatus(NarrativeStatus.GENERATED).setDivAsString("<div xmlns=\"http://www.w3.org/1999/xhtml\">ABC, 41 year, Male</div>");
		patient.addIdentifier().setType(new CodeableConcept(new Coding("http://terminology.hl7.org/CodeSystem/v2-0203", "MR", "Medical record number"))).setSystem("https://ndhm.in/SwasthID").setValue("1234");
		patient.addName().setText("ABC");
		patient.addTelecom().setSystem(ContactPointSystem.PHONE).setValue("+919818512600").setUse(ContactPointUse.HOME);
		patient.setGender(AdministrativeGender.MALE).setBirthDateElement(new DateType("1981-01-12"));
		return patient;
	}
	
	// Populate Practitioner Resource
	public static Practitioner populatePractitionerResource()
	{
		Practitioner practitioner = new Practitioner();
		practitioner.setId("Practitioner-01");
		practitioner.getMeta().setVersionId("1").setLastUpdatedElement(new InstantType("2019-05-29T14:58:58.181+05:30")).addProfile("https://nrces.in/ndhm/fhir/r4/StructureDefinition/Practitioner");
		practitioner.getText().setStatus(NarrativeStatus.GENERATED).setDivAsString("<div xmlns=\"http://www.w3.org/1999/xhtml\">Dr. DEF, MD (Medicine)</div>");
		practitioner.addIdentifier().setType(new CodeableConcept(new Coding("http://terminology.hl7.org/CodeSystem/v2-0203", "MD", "Medical License number"))).setSystem("https://ndhm.in/DigiDoc").setValue("7601003178999");
		practitioner.addName().setText("Dr. DEF");
		return practitioner;
	}
	
	// Populate Condition Resource
	public static Condition populateConditionResource()
	{
		Condition condition = new Condition();
		condition.setId("Condition-01");
		condition.getMeta().addProfile("https://nrces.in/ndhm/fhir/r4/StructureDefinition/Condition");
		condition.getText().setStatus(NarrativeStatus.GENERATED).setDivAsString("<div xmlns=\"http://www.w3.org/1999/xhtml\">Abdominal pain on 09-July 2020</div>");
		condition.setSubject(new Reference().setReference("Patient/Patient-01"));
		condition.getCode().addCoding(new Coding("http://snomed.info/sct", "21522001", "Abdominal pain")).setText("Abdominal pain");
		return condition;
	}
	
	// Populate Condition Resource
	public static Condition populateSecondConditionResource()
	{
		Condition condition = new Condition();
		condition.setId("Condition-02");
		condition.getMeta().addProfile("https://nrces.in/ndhm/fhir/r4/StructureDefinition/Condition");
		condition.getText().setStatus(NarrativeStatus.GENERATED).setDivAsString("<div xmlns=\"http://www.w3.org/1999/xhtml\">Abdominal pain on 09-July 2020</div>");
		condition.setSubject(new Reference().setReference("Patient/Patient-01"));
		condition.getCode().addCoding(new Coding("http://snomed.info/sct", "22298006", "Myocardial infarction")).setText("pain in the chest, neck, back or arms, as well as fatigue, lightheadedness, abnormal heartbeat and anxiety");
		return condition;
	}
	
	// Populate Condition Resource
	public static Condition populateThirdConditionResource()
	{
		Condition condition = new Condition();
		condition.setId("Condition-03");
		condition.getMeta().addProfile("https://nrces.in/ndhm/fhir/r4/StructureDefinition/Condition");
		condition.getText().setStatus(NarrativeStatus.GENERATED).setDivAsString("<div xmlns=\"http://www.w3.org/1999/xhtml\">Abdominal pain on 09-July 2020</div>");
		condition.setSubject(new Reference().setReference("Patient/Patient-01"));
		condition.getCode().addCoding(new Coding("http://snomed.info/sct", "44054006", "Diabetes mellitus type 2")).setText("Diabetes mellitus type 2");
		return condition;
	}
	
	// Populate Binary Resource
	public static Binary populateBinaryResource()
	{
		Binary binary = new Binary();
		binary.setId("Binary-01");
		binary.getMeta().addProfile("https://nrces.in/ndhm/fhir/r4/StructureDefinition/Binary");
		binary.setContentType("application/pdf");
		binary.setDataElement(new Base64BinaryType("R0lGODlhfgCRAPcAAAAAAIAAAACAAICAAAAAgIAA oxrXyMY2uvGNcIyj    HOeoxkXBh44OOZdn8Ggu+DiPjwtJ2CZyUomCTRGO"));
		return binary;
	}
	
	// Populate Allergy Intolerance Resource
	public static AllergyIntolerance populateAllergyIntoleranceResource()
	{
		AllergyIntolerance allergyIntolerance = new AllergyIntolerance();
		allergyIntolerance.setId("AllergyIntolerance-01");
		allergyIntolerance.getMeta().addProfile("https://nrces.in/ndhm/fhir/r4/StructureDefinition/AllergyIntolerance");
		allergyIntolerance.getCode().addCoding(new Coding("http://snomed.info/sct","227493005", "Cashew nuts"));
		allergyIntolerance.setPatient(new Reference().setReference("Patient/Patient-01"));
		allergyIntolerance.setClinicalStatus(new CodeableConcept(new Coding("http://terminology.hl7.org/CodeSystem/allergyintolerance-clinical", "active", "Active")));
		return allergyIntolerance;
	}
	
	// Populate Appointment Resource
	public static Appointment populateAppointmentResource()
	{
		Appointment appointment = new Appointment();
		appointment.setId("Appointment-01");
		appointment.getMeta().addProfile("https://nrces.in/ndhm/fhir/r4/StructureDefinition/Appointment");
		appointment.setStatus(AppointmentStatus.BOOKED);
		appointment.getParticipant().add(new AppointmentParticipantComponent().setActor(new Reference().setReference("Patient/Patient-01")).
				setStatus(ParticipationStatus.ACCEPTED).setActor(new Reference().setReference("Practitioner/Practitioner-01")).setStatus(ParticipationStatus.ACCEPTED));
		appointment.setStartElement(new InstantType("2020-07-12T09:00:00Z"));
		appointment.setEndElement(new InstantType("2020-07-12T09:30:00Z"));
		appointment.addReasonReference(new Reference().setReference("Condition/Condition-01"));
		appointment.addBasedOn(new Reference().setReference("ServiceRequest/ServiceRequest-01"));
		return appointment;
	}
	
	// Populate Care Plan Resource
	public static CarePlan populateCarePlanResource()
	{
		CarePlan carePlan = new CarePlan();
		carePlan.setId("CarePlan-01");
		carePlan.getMeta().addProfile("https://nrces.in/ndhm/fhir/r4/StructureDefinition/CarePlan");
		carePlan.setStatus(CarePlanStatus.ACTIVE);
		carePlan.setIntent(CarePlanIntent.PLAN);
		carePlan.setSubject(new Reference().setReference("Patient/Patient-01").setDisplay("ABC"));
		return carePlan;
	}
	
	// Populate Diagnostic Report Imaging DCM Resource
	public static DiagnosticReport populateDiagnosticReportImagingDCMResource()
	{	
		DiagnosticReport diagnosticReportImaging = new DiagnosticReport();
		diagnosticReportImaging.setId("DiagnosticReport-01");
		diagnosticReportImaging.getMeta().setVersionId("1").setLastUpdatedElement(new InstantType("2020-07-09T14:58:58.181+05:30")).addProfile("https://nrces.in/ndhm/fhir/r4/StructureDefinition/DiagnosticReportImaging");
		diagnosticReportImaging.setStatus(DiagnosticReportStatus.FINAL);
		diagnosticReportImaging.setCode(new CodeableConcept(new Coding("http://loinc.org", "82692-5", "CT Head and Neck WO contrast")));
		diagnosticReportImaging.getResultsInterpreter().add(new Reference().setReference("Organization/Organization-01").setDisplay("Dr. DEF"));
		diagnosticReportImaging.getMedia().add(new DiagnosticReportMediaComponent(new Reference().setReference("Media/Media-01")));
		diagnosticReportImaging.setConclusion("CT brains: large tumor sphenoid/clivus.");
		return diagnosticReportImaging;	
	}
	
	// Populate Diagnostic Report Imaging Media Resource
	public static DiagnosticReport populateDiagnosticReportImagingMediaResource()
	{	
		DiagnosticReport diagnosticReportImaging = new DiagnosticReport();
		diagnosticReportImaging.setId("DiagnosticReport-01");
		diagnosticReportImaging.getMeta().setVersionId("1").setLastUpdatedElement(new InstantType("2020-07-09T14:58:58.181+05:30")).addProfile("https://nrces.in/ndhm/fhir/r4/StructureDefinition/DiagnosticReportImaging");
		diagnosticReportImaging.setStatus(DiagnosticReportStatus.FINAL);
		diagnosticReportImaging.setCode(new CodeableConcept(new Coding("http://loinc.org", "82692-5", "CT Head and Neck WO contrast")));
		diagnosticReportImaging.getResultsInterpreter().add(new Reference().setReference("Organization/Organization-01").setDisplay("Dr. DEF"));
		diagnosticReportImaging.getMedia().add(new DiagnosticReportMediaComponent(new Reference().setReference("Media/Media-01")));
		diagnosticReportImaging.setConclusion("CT brains: large tumor sphenoid/clivus.");
		return diagnosticReportImaging;	
	}
	
	// Populate Diagnostic Report Lab Resource
	public static DiagnosticReport populateDiagonosticReportLabResource()
	{
		DiagnosticReport diagnosticReportLab = new DiagnosticReport();
		diagnosticReportLab.setId("DiagnosticReport-01");
		diagnosticReportLab.getMeta().setVersionId("1").setLastUpdatedElement(new InstantType("2020-07-09T15:32:26.605+05:30")).addProfile("https://nrces.in/ndhm/fhir/r4/StructureDefinition/DiagnosticReportLab");
		diagnosticReportLab.setStatus(DiagnosticReportStatus.FINAL);
		diagnosticReportLab.setCode(new CodeableConcept(new Coding("http://loinc.org","24331-1","Lipid 1996 panel - Serum or Plasma")));
		diagnosticReportLab.getResultsInterpreter().add(new Reference().setReference("Practitioner/Practitioner-01").setDisplay("Dr. DEF"));
		diagnosticReportLab.setConclusion("Elevated cholesterol/high density lipoprotein ratio");
		diagnosticReportLab.addResult(new Reference().setReference("Observation/Observation-cholesterol")).addResult(new Reference().setReference("Observation/Observation-triglyceride"));
		return diagnosticReportLab;
	}
	
	// Populate Document Reference Resource
	public static DocumentReference populateDocumentReferenceResource()
	{
		DocumentReference documentReference = new DocumentReference();
		documentReference.setId("DocumentReference-01");
		documentReference.getMeta().addProfile("https://nrces.in/ndhm/fhir/r4/StructureDefinition/DocumentReference");
		documentReference.setStatus(DocumentReferenceStatus.CURRENT);
		documentReference.setDocStatus(ReferredDocumentStatus.FINAL);
		documentReference.setSubject(new Reference().setReference("Patient/Patient-01"));
		documentReference.setType(new CodeableConcept(new Coding("http://snomed.info/sct", "4241000179101", "Laboratory report")).setText("Laboratory report"));
		documentReference.getContent().add(new DocumentReferenceContentComponent(new Attachment().setContentType("application/pdf").
				setLanguage("en-IN").setTitle("Laboratory report").setCreationElement(new DateTimeType("2019-05-29T14:58:58.181+05:30")).
				setDataElement(new Base64BinaryType("IDc4NTkxPj4NCnN0YXJ0eHJlZg0KODA2MTQNCiUlRU9G"))));
		return documentReference;
	}
	
	// Populate Encounter Resource
	public static Encounter populateEncounterResource()
	{
		Encounter encounter = new Encounter();
		encounter.setId("Encounter-01");
		encounter.setStatus(EncounterStatus.FINISHED);
		encounter.getMeta().setLastUpdatedElement(new InstantType("2020-07-09T14:58:58.181+05:30")).addProfile("https://nrces.in/ndhm/fhir/r4/StructureDefinition/Encounter");
		encounter.getIdentifier().add(new Identifier().setSystem("https://ndhm.in").setValue("S100"));
		encounter.setClass_(new Coding("http://terminology.hl7.org/CodeSystem/v3-ActCode", "IMP", "inpatient encounter"));
		encounter.setSubject(new Reference().setReference("Patient/Patient-01"));
		encounter.setPeriod(new Period().setStartElement(new DateTimeType("2020-04-20T15:32:26.605+05:30")).setEndElement(new DateTimeType("2020-05-01T15:32:26.605+05:30")));
		encounter.setHospitalization(new EncounterHospitalizationComponent().
				setDischargeDisposition(new CodeableConcept(new Coding("http://terminology.hl7.org/CodeSystem/discharge-disposition","home", "Home")).setText("Discharged to Home Care")));
		return encounter;
	}
	
	// Populate Encounter Resource
	public static Encounter populateSecondEncounterResource()
	{
		Encounter encounter = new Encounter();
		encounter.setId("Encounter-02");
		encounter.setStatus(EncounterStatus.FINISHED);
		encounter.getMeta().setLastUpdatedElement(new InstantType("2020-07-09T14:58:58.181+05:30")).addProfile("https://nrces.in/ndhm/fhir/r4/StructureDefinition/Encounter");
		encounter.getIdentifier().add(new Identifier().setSystem("https://ndhm.in").setValue("S100"));
		encounter.setClass_(new Coding("http://terminology.hl7.org/CodeSystem/v3-ActCode", "IMP", "inpatient encounter"));
		encounter.setSubject(new Reference().setReference("Patient/Patient-01"));
		encounter.setPeriod(new Period().setStartElement(new DateTimeType("2020-04-20T15:32:26.605+05:30")).setEndElement(new DateTimeType("2020-05-01T15:32:26.605+05:30")));
		encounter.setHospitalization(new EncounterHospitalizationComponent().
				setDischargeDisposition(new CodeableConcept(new Coding("http://terminology.hl7.org/CodeSystem/discharge-disposition","home", "Home")).setText("Discharged to Home Care")));
		encounter.addDiagnosis().setCondition(new Reference().setReference("Condition/Condition-01")).setUse(new CodeableConcept(new Coding("http://snomed.info/sct", "33962009", "Chief complaint"))).
		setCondition(new Reference().setReference("Condition/Condition-03")).setUse(new CodeableConcept(new Coding("http://snomed.info/sct", "148006", "Preliminary diagnosis")));
		return encounter;
	}
	
	// Populate Family Member History Resource
	public static FamilyMemberHistory populateFamilyMemberHistoryResource()
	{
		FamilyMemberHistory familyMemberHistory = new FamilyMemberHistory();
		familyMemberHistory.setId("FamilyMemberHistory-01");
		familyMemberHistory.getMeta().addProfile("https://nrces.in/ndhm/fhir/r4/StructureDefinition/FamilyMemberHistory");
		familyMemberHistory.setStatus(FamilyHistoryStatus.COMPLETED);
		familyMemberHistory.getCondition().add(new FamilyMemberHistoryConditionComponent(new CodeableConcept(new Coding("http://snomed.info/sct", "315619001", "FH myocardial infarction male first degree age known")).
				setText("Heart Attack")).setContributedToDeath(true));
		return familyMemberHistory;
	}
	
	// Populate Imaging Study Resource
	public static ImagingStudy populateImagingStudyResource()
	{
		ImagingStudy imagingStudy = new ImagingStudy();
		imagingStudy.setId("ImagingStudy-01");
		imagingStudy.getMeta().addProfile("https://nrces.in/ndhm/fhir/r4/StructureDefinition/ImagingStudy");
		imagingStudy.setStatus(ImagingStudyStatus.AVAILABLE);
		imagingStudy.setSubject(new Reference().setReference("Patient/Patient-01"));
		imagingStudy.getInterpreter().add(new Reference("Practitioner/Practitioner-01"));
		imagingStudy.setNumberOfSeries(1);
		imagingStudy.setNumberOfInstances(1);
		ImagingStudySeriesComponent img = new ImagingStudySeriesComponent();
		img.setUid("2.16.124.113543.6003.2588828330.45298.17418.2723805630").setNumber(1).
		setModality(new Coding("http://snomed.info/sct", "429858000", "CT of head and neck")).setDescription("CT Surview 180").setNumberOfInstances(1).
		setBodySite(new Coding("http://snomed.info/sct", "774007", "Structure of head and/or neck")).getInstance().add(new ImagingStudySeriesInstanceComponent().
				setUid("2.16.124.113543.6003.189642796.63084.16748.2599092903").setSopClass(new Coding("urn:ietf:rfc:3986", "urn:oid:1.2.840.10008.5.1.4.1.1.2", "")).
				setNumber(1).setTitle("CT of head and neck"));
		imagingStudy.getSeries().add(img);
		return imagingStudy;
	}
	
	// Populate Immunization Resource
	public static Immunization populateImmunizationResource()
	{
		Immunization immunization = new Immunization();
		immunization.setId("Immunization-01");
		immunization.getMeta().addProfile("https://nrces.in/ndhm/fhir/r4/StructureDefinition/Immunization");
		immunization.setStatus(ImmunizationStatus.NOTDONE);
		immunization.setVaccineCode(new CodeableConcept(new Coding("http://snomed.info/sct", "314768003", "No consent DTP immunization")));
		immunization.setPatient(new Reference().setReference("Patient/Patient-01"));
		immunization.getReasonCode().add(new CodeableConcept(new Coding("http://terminology.hl7.org/CodeSystem/v3-ActReason", "MEDPREC", "medical precaution")));
		immunization.setOccurrence(new DateTimeType("2013-01-10"));
		immunization.setPrimarySource(true);
		return immunization;
	}
	
	// Populate Media Resource
	public static Media populateMediaResource()
	{
		Media media = new Media();
		media.setId("Media-01");
		media.getMeta().addProfile("https://nrces.in/ndhm/fhir/r4/StructureDefinition/Media");
		media.setStatus(MediaStatus.COMPLETED);
		media.setContent(new Attachment().setContentType("image/jpeg").setDataElement(new Base64BinaryType("/9j/4AAQSkZJRgABAQEASABIAAD/4RBGaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wLwA8P3hwYWNrZXQgYmVnaW49Iu+7vyIgaWQ9Ilc1TTBNcENlaGlIenJlU3pOVGN6a2M5ZCI/PiA8eDp4bXBtZXRhIHhtbG5zOng9ImFkb2JlOm5zOm1ldGEvIiB4OnhtcHRrPSJBZG9iZSBYTVAgQ29yZSA0LjIuMi1jMDYzIDUzLjM1MjYyNCwgMjAwOC8wNy8zMC0xODowNTo0MSAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvIiB4bWxuczpkYz0iaHR0cDovL3B1cmwub3JnL2RjL2VsZW1lbnRzLzEuMS8iIHhtbG5zOnBob3Rvc2hvcD0iaHR0cDovL25zLmFkb2JlLmNvbS9waG90b3Nob3AvMS4wLyIgeG1sbnM6eG1wTU09Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9tbS8iIHhtbG5zOnN0RXZ0PSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvc1R5cGUvUmVzb3VyY2VFdmVudCMiIHhtbG5zOnRpZmY9Imh0dHA6Ly9ucy5hZG9iZS5jb20vdGlmZi8xLjAvIiB4bWxuczpleGlmPSJodHRwOi8vbnMuYWRvYmUuY29tL2V4aWYvMS4wLyIgeG1wOkNyZWF0b3JUb29sPSJBZG9iZSBQaG90b3Nob3AgQ1M0IE1hY2ludG9zaCIgeG1wOkNyZWF0ZURhdGU9IjIwMTAtMDktMTZUMTI6MDg6MjArMTA6MDAiIHhtcDpNb2RpZnlEYXRlPSIyMDEyLTAyLTIyVDIxOjQ3OjUzKzExOjAwIiB4bXA6TWV0YWRhdGFEYXRlPSIyMDEyLTAyLTIyVDIxOjQ3OjUzKzExOjAwIiBkYzpmb3JtYXQ9ImltYWdlL2pwZWciIHBob3Rvc2hvcDpDb2xvck1vZGU9IjMiIHhtcE1NOkluc3RhbmNlSUQ9InhtcC5paWQ6QzA5MTI1MTMwODIwNjgxMThGNjJFN0NBOEIzRUI0RDYiIHhtcE1NOkRvY3VtZW50SUQ9InhtcC5kaWQ6QzA5MTI1MTMwODIwNjgxMThGNjJFN0NBOEIzRUI0RDYiIHhtcE1NOk9yaWdpbmFsRG9jdW1lbnRJRD0ieG1wLmRpZDpDMDkxMjUxMzA4MjA2ODExOEY2MkU3Q0E4QjNFQjRENiIgdGlmZjpPcmllbnRhdGlvbj0iMSIgdGlmZjpYUmVzb2x1dGlvbj0iNzIwMDAwLzEwMDAwIiB0aWZmOllSZXNvbHV0aW9uPSI3MjAwMDAvMTAwMDAiIHRpZmY6UmVzb2x1dGlvblVuaXQ9IjIiIHRpZmY6TmF0aXZlRGlnZXN0PSIyNTYsMjU3LDI1OCwyNTksMjYyLDI3NCwyNzcsMjg0LDUzMCw1MzEsMjgyLDI4MywyOTYsMzAxLDMxOCwzMTksNTI5LDUzMiwzMDYsMjcwLDI3MSwyNzIsMzA1LDMxNSwzMzQzMjs3NEE0OEU0MzU4NDYyNEQyMDI3NzZBRkNGOUU5MTFGQyIgZXhpZjpQaXhlbFhEaW1lbnNpb249Ijc2NiIgZXhpZjpQaXhlbFlEaW1lbnNpb249Ijc3MCIgZXhpZjpDb2xvclNwYWNlPSI2NTUzNSIgZXhpZjpOYXRpdmVEaWdlc3Q9IjM2ODY0LDQwOTYwLDQwOTYxLDM3MTIxLDM3MTIyLDQwOTYyLDQwOTYzLDM3NTEwLDQwOTY0LDM2ODY3LDM2ODY4LDMzNDM0LDMzNDM3LDM0ODUwLDM0ODUyLDM0ODU1LDM0ODU2LDM3Mzc3LDM3Mzc4LDM3Mzc5LDM3MzgwLDM3MzgxLDM3MzgyLDM3MzgzLDM3Mzg0LDM3Mzg1LDM3Mzg2LDM3Mzk2LDQxNDgzLDQxNDg0LDQxNDg2LDQxNDg3LDQxNDg4LDQxNDkyLDQxNDkzLDQxNDk1LDQxNzI4LDQxNzI5LDQxNzMwLDQxOTg1LDQxOTg2LDQxOTg3LDQxOTg4LDQxOTg5LDQxOTkwLDQxOTkxLDQxOTkyLDQxOTkzLDQxOTk0LDQxOTk1LDQxOTk2LDQyMDE2LDAsMiw0LDUsNiw3LDgsOSwxMCwxMSwxMiwxMywxNCwxNSwxNiwxNywxOCwyMCwyMiwyMywyNCwyNSwyNiwyNywyOCwzMDtFNzUyQ0Q2NzMyRDk1MEQ4MTg5N0QyMDYxOEE4MUZGRSI+IDx4bXBNTTpIaXN0b3J5PiA8cmRmOlNlcT4gPHJkZjpsaSBzdEV2dDphY3Rpb249ImNyZWF0ZWQiIHN0RXZ0Omluc3RhbmNlSUQ9InhtcC5paWQ6QzA5MTI1MTMwODIwNjgxMThGNjJFN0NBOEIzRUI0RDYiIHN0RXZ0OndoZW49IjIwMTItMDItMjJUMjE6NDc6NTMrMTE6MDAiIHN0RXZ0OnNvZnR3YXJlQWdlbnQ9IkFkb2JlIFBob3Rvc2hvcCBDUzQgTWFjaW50b3NoIi8+IDwvcmRmOlNlcT4gPC94bXBNTTpIaXN0b3J5PiA8L3JkZjpEZXNjcmlwdGlvbj4gPC9yZGY6UkRGPiA8L3g6eG1wbWV0YT4gICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICA8P3hwYWNrZXQgZW5kPSJ3Ij8+/+EZEEV4aWYAAE1NACoAAAAIAAcBEgADAAAAAQABAAABGgAFAAAAAQAAAGIBGwAFAAAAAQAAAGoBKAADAAAAAQACAAABMQACAAAAHgAAAHIBMgACAAAAFAAAAJCHaQAEAAAAAQAAAKQAAADQAAr8gAAAJxAACvyAAAAnEEFkb2JlIFBob3Rvc2hvcCBDUzQgTWFjaW50b3NoADIwMTI6MDI6MjIgMjE6NDc6NTMAAAOgAQADAAAAAf//AACgAgAEAAAAAQAAAv6gAwAEAAAAAQAAAwIAAAAAAAAABgEDAAMAAAABAAYAAAEaAAUAAAABAAABHgEbAAUAAAABAAABJgEoAAMAAAABAAIAAAIBAAQAAAABAAABLgICAAQAAAABAAAX2gAAAAAAAABIAAAAAQAAAEgAAAAB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t94Y1bG77HIzdpEANfK8HjtjsE9vyO4rTi8c2TsNxdD2PpQB9JzeGPCskg8yzswx6DIGaafBvhNn3f2faZHYEV4EnjGzlAY3zbgOCW6VKnimFz8t+2T330AfQUOi+G7cbI7SxX24qcJobMIgtkSOi4WvndvFNsnAvCW/36F8UW3mg/avm/3qAPodtP0JAXa3swO5OKpu/hRVJYWGB1+UV4TceKYUQt9rO09i1ZA8XacjlWuN340AfRguPCiqtwBYjsDsGfyp48QeG7YNsntk28/KgH9K+bpPGmnc4kOO4qm3jaywThzjoKAPou5+JGiwRsyCWQg4wABWJqPxPkdMWMCICPvvya+f5fHL/MI4Cc9Mmsq48V6lOm0OqD2oA9a1rxq8u5rvUGKdSpauH1LxxAhItFaV/wC8elcLLPLO5eWRnY9yajoA1b/xDqGoEiSYqh/hXisokk5JzRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFACUUUUALxRkUlFAC5FGRSUUALmjNJRQAuaM0lFAC5ozSUUAGaWkoFAC0UUUAFFFFABRRRQAUUUUAFFFFABRRRQAUAkdDRRQAUZNFFAClmPVifqaSiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigBKKKKACiiigAooooAKKKKACiiigAooooAKBRQKAFooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigBKKKKAP/2Q==")).
				setTitle("Computed tomography (CT) of head and neck").setCreationElement(new DateTimeType("2020-07-09T11:46:09+05:30")).
				setLanguage("en-IN"));
		media.setModality(new CodeableConcept(new Coding("http://snomed.info/sct", "429858000", "CT of head and neck")));
		media.setBodySite(new CodeableConcept(new Coding("http://snomed.info/sct", "774007", "Structure of head and/or neck")));
		media.setSubject(new Reference().setReference("Patient/Patient-01"));
		media.setCreated(new DateTimeType("2020-07-10"));
		return media;
	}
	
	// Populate Medication Request Resource
	public static MedicationRequest populateMedicationRequestResource()
	{
		MedicationRequest medicationRequest = new MedicationRequest();
		medicationRequest.setId("MedicationRequest-01");
		medicationRequest.getMeta().addProfile("https://nrces.in/ndhm/fhir/r4/StructureDefinition/MedicationRequest");
		medicationRequest.setStatus(MedicationRequestStatus.ACTIVE);
		medicationRequest.setIntent(MedicationRequestIntent.ORDER);
		medicationRequest.setMedication(new CodeableConcept(new Coding("http://snomed.info/sct", "324252006", "Azithromycin (as azithromycin dihydrate) 250 mg oral capsule")));
		medicationRequest.setSubject(new Reference().setReference("Patient/Patient-01").setDisplay("ABC"));
		medicationRequest.setAuthoredOnElement(new DateTimeType("2020-07-09"));
		medicationRequest.setRequester(new Reference().setReference("Practitioner/Practitioner-01").setDisplay("Dr DEF"));
		medicationRequest.getReasonCode().add(new CodeableConcept(new Coding("http://snomed.info/sct", "11840006", "Traveller's Diarrhea (disorder)")));
		medicationRequest.getReasonReference().add(new Reference().setReference("Condition/Condition-01"));
		medicationRequest.addDosageInstruction(new Dosage().setText("One tablet at once").addAdditionalInstruction(new CodeableConcept(new Coding("http://snomed.info/sct", "311504000", "With or after food"))).
				setTiming(new Timing().setRepeat(new TimingRepeatComponent().setFrequency(1).setPeriod(1).setPeriodUnit(UnitsOfTime.D))).
				setRoute(new CodeableConcept(new Coding("http://snomed.info/sct", "26643006", "Oral Route"))).
				setMethod(new CodeableConcept(new Coding("http://snomed.info/sct", "421521009", "Swallow"))));
		return medicationRequest;
	}
	
	// Populate Medication Request Resource
	public static MedicationRequest populateSecondMedicationRequestResource()
	{
		MedicationRequest medicationRequest = new MedicationRequest();
		medicationRequest.setId("MedicationRequest-02");
		medicationRequest.getMeta().addProfile("https://nrces.in/ndhm/fhir/r4/StructureDefinition/MedicationRequest");
		medicationRequest.setStatus(MedicationRequestStatus.ACTIVE);
		medicationRequest.setIntent(MedicationRequestIntent.ORDER);
		medicationRequest.setMedication(new CodeableConcept().setText("Paracetemol 500mg Oral Tab"));
		medicationRequest.setSubject(new Reference().setReference("Patient/Patient-01").setDisplay("ABC"));
		medicationRequest.setAuthoredOnElement(new DateTimeType("2020-07-09"));
		medicationRequest.setRequester(new Reference().setReference("Practitioner/Practitioner-01").setDisplay("Dr DEF"));
		medicationRequest.getReasonCode().add(new CodeableConcept(new Coding("http://snomed.info/sct", "602001", "Ross river fever")));
		medicationRequest.getReasonReference().add(new Reference().setReference("Condition/Condition-01"));
		medicationRequest.addDosageInstruction(new Dosage().setText("Take two tablets orally with or after meal once a day"));
		return medicationRequest;
	}
	
	// Populate Medication Statement Resource
	public static MedicationStatement populateMedicationStatementResource()
	{
		MedicationStatement medicationStatement = new MedicationStatement();
		medicationStatement.setId("MedicationStatement-01");
		medicationStatement.getMeta().addProfile("https://nrces.in/ndhm/fhir/r4/StructureDefinition/MedicationStatement");
		medicationStatement.setStatus(MedicationStatementStatus.COMPLETED);
		medicationStatement.setMedication(new CodeableConcept(new Coding("http://snomed.info/sct", "134463001", "Telmisartan 20 mg oral tablet")));
		medicationStatement.setSubject(new Reference().setReference("Patient/Patient-01"));
		medicationStatement.setDateAssertedElement(new DateTimeType("2020-02-02T14:58:58.181+05:30"));
		return medicationStatement;
	}
	
	// Populate Observation Resource
	public static Observation populateObservationResource()
	{
		Observation observation = new Observation();
		observation.setId("Observation-01");
		observation.getMeta().addProfile("https://nrces.in/ndhm/fhir/r4/StructureDefinition/Observation");
		observation.setStatus(ObservationStatus.FINAL);
		observation.setCode(new CodeableConcept(new Coding("http://loinc.org", "35200-5", "Cholesterol [Moles/​volume] in Serum or Plasma")).setText("Cholesterol"));
		observation.setValue(new Quantity().setValueElement(new DecimalType("6.3")).setCode("258813002").setUnit("mmol/L").setSystem("http://snomed.info/sct"));
		observation.getReferenceRange().add(new ObservationReferenceRangeComponent().setHigh(new Quantity().setValueElement(new DecimalType("6.3")).setCode("258813002").setUnit("mmol/L").setSystem("http://snomed.info/sct")));
		observation.setSubject(new Reference().setReference("Patient/Patient-01"));
		return observation;
	}
	
	// Populate Observation/Cholesterol Resource
	public static Observation populateCholesterolObservationResource()
	{
		Observation observation = new Observation();
		observation.setId("Observation-cholesterol");
		observation.getMeta().addProfile("https://nrces.in/ndhm/fhir/r4/StructureDefinition/Observation");
		observation.setStatus(ObservationStatus.FINAL);
		observation.setCode(new CodeableConcept(new Coding("http://loinc.org", "35217-9", "Triglyceride [Moles/​volume] in Serum or Plasma")).setText("Triglycerides, serum by Enzymatic method"));
		observation.setValue(new Quantity().setValueElement(new DecimalType("6.3")).setCode("258813002").setUnit("mmol/L").
				setSystem("http://snomed.info/sct"));
		observation.getReferenceRange().add(new ObservationReferenceRangeComponent().setHigh(new Quantity().
				setValueElement(new DecimalType("4.5")).setCode("258813002").setUnit("mmol/L").setSystem("http://snomed.info/sct")));
		observation.setSubject(new Reference().setReference("Patient/Patient-01"));
		observation.addPerformer().setReference("Organization/Organization-02");
		return observation;
	}
	
	// Populate Observation/Triglyceride Resource
	public static Observation populateTriglycerideObservationResource()
	{
		Observation observation = new Observation();
		observation.setId("Observation-triglyceride");
		observation.getMeta().addProfile("https://nrces.in/ndhm/fhir/r4/StructureDefinition/Observation");
		observation.setStatus(ObservationStatus.FINAL);
		observation.setCode(new CodeableConcept(new Coding("http://loinc.org", "35217-9", "Triglyceride [Moles/​volume] in Serum or Plasma")).setText("Triglycerides, serum by Enzymatic method"));
		observation.setValue(new Quantity().setValueElement(new DecimalType("1.3")).setCode("258813002").setUnit("mmol/L").
				setSystem("http://snomed.info/sct"));
		observation.getReferenceRange().add(new ObservationReferenceRangeComponent().setHigh(new Quantity().
				setValueElement(new DecimalType("2.0")).setCode("258813002").setUnit("mmol/L").setSystem("http://snomed.info/sct")));
		observation.setSubject(new Reference().setReference("Patient/Patient-01"));
		observation.addPerformer().setReference("Organization/Organization-02");
		return observation;
	}
	
	// Populate Organization Resource
	public static Organization populateOrganizationResource()
	{
		Organization organization = new Organization();
		organization.setId("Organization-01");
		organization.getMeta().addProfile("https://nrces.in/ndhm/fhir/r4/StructureDefinition/Organization");
		organization.getIdentifier().add(new Identifier().setType(new CodeableConcept(new Coding("http://terminology.hl7.org/CodeSystem/v2-0203", "PRN", "Provider number"))).
				setSystem("https://facility.ndhm.gov.in").setValue("4567878"));
		organization.setName("XYZ Lab Pvt.Ltd");
		List<ContactPoint> list = new ArrayList<ContactPoint>();
		ContactPoint contact1 = new ContactPoint();
		contact1.setSystem(ContactPointSystem.PHONE).setValue("+91 243 2634 1234").setUse(ContactPointUse.WORK);
		ContactPoint contact2 = new ContactPoint();
		contact2.setSystem(ContactPointSystem.EMAIL).setValue("contact@labs.xyz.org").setUse(ContactPointUse.WORK);
		list.add(contact1);
		list.add(contact2);
		organization.setTelecom(list);
		return organization;
	}
	
	// Populate Organization Resource
	public static Organization populateSecondOrganizationResource()
	{
		Organization organization = new Organization();
		organization.setId("Organization-02");
		organization.getMeta().addProfile("https://nrces.in/ndhm/fhir/r4/StructureDefinition/Organization");
		organization.getIdentifier().add(new Identifier().setType(new CodeableConcept(new Coding("http://terminology.hl7.org/CodeSystem/v2-0203", "PRN", "Provider number"))).
				setSystem("https://facility.ndhm.gov.in").setValue("4567878"));
		organization.setName("XYZ Lab Pvt.Ltd");
		List<ContactPoint> list = new ArrayList<ContactPoint>();
		ContactPoint contact1 = new ContactPoint();
		contact1.setSystem(ContactPointSystem.PHONE).setValue("+91 243 2634 1234").setUse(ContactPointUse.WORK);
		ContactPoint contact2 = new ContactPoint();
		contact2.setSystem(ContactPointSystem.EMAIL).setValue("contact@labs.xyz.org").setUse(ContactPointUse.WORK);
		list.add(contact1);
		list.add(contact2);
		organization.setTelecom(list);
		return organization;
	}
	
	// Populate Practitioner Role Resource
	public static PractitionerRole populatePractitionerRoleResource()
	{
		PractitionerRole practitionerRole = new PractitionerRole();
		practitionerRole.setId("PractitionerRole-01");
		practitionerRole.getMeta().addProfile("https://nrces.in/ndhm/fhir/r4/StructureDefinition/PractitionerRole");
		practitionerRole.getIdentifier().add(new Identifier().setType(new CodeableConcept(new Coding("http://terminology.hl7.org/CodeSystem/v2-0203", "EI", "Employee number"))).
				setValue("23").setSystem("http://www.ndhm.in/practitioners"));
		practitionerRole.setActive(true);
		practitionerRole.setPeriod(new Period().setStartElement(new DateTimeType("2012-01-01")).setEndElement(new DateTimeType("2012-03-31")));
		practitionerRole.setPractitioner(new Reference().setReference("Practitioner/Practitioner-01").setDisplay("Dr DEF"));
		practitionerRole.setOrganization(new Reference().setReference("Organization/Organization-01"));
		practitionerRole.getCode().add(new CodeableConcept(new Coding("http://snomed.info/sct", "85733003", "General pathologist")));
		practitionerRole.getSpecialty().add(new CodeableConcept(new Coding("http://snomed.info/sct", "408443003", "General Medical Practice")));
		practitionerRole.addAvailableTime(new PractitionerRoleAvailableTimeComponent().setAllDay(true).setAvailableStartTimeElement(new TimeType("09:00:00")).setAvailableEndTimeElement(new TimeType("12:00:00")));
		return practitionerRole;
	}
	
	// Populate Procedure Resource
	public static Procedure populateProcedureResource()
	{
		Procedure procedure = new Procedure();
		procedure.setId("Procedure-01");
		procedure.getMeta().addProfile("https://nrces.in/ndhm/fhir/r4/StructureDefinition/Procedure");
		procedure.setStatus(ProcedureStatus.COMPLETED);
		procedure.setCode(new CodeableConcept(new Coding("http://snomed.info/sct", "36969009", "Placement of stent in coronary artery")).setText("Placement of stent in coronary artery"));
		procedure.setSubject(new Reference().setReference("Patient/Patient-01"));
		procedure.setPerformed(new DateTimeType("2019-05-12"));
		procedure.getComplication().add(new CodeableConcept(new Coding("http://snomed.info/sct", "131148009", "Bleeding")));
		return procedure;
	}
	
	// Populate Procedure Resource
	public static Procedure populateSecondProcedureResource()
	{
		Procedure procedure = new Procedure();
		procedure.setId("Procedure-02");
		procedure.getMeta().addProfile("https://nrces.in/ndhm/fhir/r4/StructureDefinition/Procedure");
		procedure.setStatus(ProcedureStatus.COMPLETED);
		procedure.setCode(new CodeableConcept(new Coding("http://snomed.info/sct", "232717009", "Coronary artery bypass grafting")).setText("Coronary artery bypass grafting"));
		procedure.setSubject(new Reference().setReference("Patient/Patient-01"));
		procedure.setPerformed(new DateTimeType("2019-05-12"));
		return procedure;
	}
	
	// Populate Service Request Resource
	public static ServiceRequest populateServiceRequestResource()
	{
		ServiceRequest serviceRequest = new ServiceRequest();
		serviceRequest.setId("ServiceRequest-01");
		serviceRequest.getMeta().addProfile("https://nrces.in/ndhm/fhir/r4/StructureDefinition/ServiceRequest");
		serviceRequest.setStatus(ServiceRequestStatus.ACTIVE);
		serviceRequest.setIntent(ServiceRequestIntent.ORIGINALORDER);
		serviceRequest.setCode(new CodeableConcept(new Coding("http://snomed.info/sct", "16254007", "Lipid Panel")));
		serviceRequest.setSubject(new Reference().setReference("Patient/Patient-01"));
		serviceRequest.setOccurrence(new DateTimeType("2020-07-08T09:33:27+07:00"));
		serviceRequest.setRequester(new Reference().setReference("Practitioner/2").setDisplay("Dr PQR"));
		return serviceRequest;
	}
	
	// Populate Specimen Resource
	public static Specimen populateSpecimenResource()
	{
		Specimen specimen = new Specimen();
		specimen.setId("Specimen-01");
		specimen.getMeta().addProfile("https://nrces.in/ndhm/fhir/r4/StructureDefinition/Specimen");
		specimen.setType(new CodeableConcept(new Coding("http://snomed.info/sct", "119364003", "Serum specimen")));
		specimen.setSubject(new Reference().setReference("Patient/Patient-01"));
		specimen.setReceivedTimeElement(new DateTimeType("2015-07-08T06:40:17Z"));
		specimen.setCollection(new SpecimenCollectionComponent().setCollected(new DateTimeType("2020-07-08T06:40:17Z")));
		return specimen;
	}
	
}