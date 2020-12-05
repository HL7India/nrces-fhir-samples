
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.hl7.fhir.common.hapi.validation.support.CommonCodeSystemsTerminologyService;
import org.hl7.fhir.common.hapi.validation.support.InMemoryTerminologyServerValidationSupport;
import org.hl7.fhir.common.hapi.validation.support.PrePopulatedValidationSupport;
import org.hl7.fhir.common.hapi.validation.support.SnapshotGeneratingValidationSupport;
import org.hl7.fhir.common.hapi.validation.support.ValidationSupportChain;
import org.hl7.fhir.common.hapi.validation.validator.FhirInstanceValidator;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Bundle.BundleEntryComponent;
import org.hl7.fhir.r4.model.Bundle.BundleType;
import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.Coding;
import org.hl7.fhir.r4.model.Composition;
import org.hl7.fhir.r4.model.Composition.CompositionStatus;
import org.hl7.fhir.r4.model.Composition.SectionComponent;
import org.hl7.fhir.r4.model.DateTimeType;
import org.hl7.fhir.r4.model.Identifier;
import org.hl7.fhir.r4.model.InstantType;
import org.hl7.fhir.r4.model.Meta;
import org.hl7.fhir.r4.model.Narrative;
import org.hl7.fhir.r4.model.Narrative.NarrativeStatus;
import org.hl7.fhir.r4.model.Reference;
import org.hl7.fhir.r4.model.StructureDefinition;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.context.support.DefaultProfileValidationSupport;
import ca.uhn.fhir.parser.DataFormatException;
import ca.uhn.fhir.parser.IParser;
import ca.uhn.fhir.validation.FhirValidator;
import ca.uhn.fhir.validation.SingleValidationMessage;
import ca.uhn.fhir.validation.ValidationResult;
import in.nrces.ndhm.fhir.r4.resources.ResourcePopulator;

/**
 * The OPConsultNoteSample class populates, validates, parse and serializes Clinical Artifact - OPConsultNote
 */
public class OPConsultNoteSample {

	// The FHIR context is the central starting point for the use of the HAPI FHIR API
	// It should be created once, and then used as a factory for various other types of objects (parsers, clients, etc.)
	static FhirContext ctx = FhirContext.forR4();

	static FhirInstanceValidator instanceValidator;
	static FhirValidator validator;

	public static void main(String[] args) throws DataFormatException, IOException
	{
		//Initialize validation support and loads all required profiles
		init();
				
		// Populate the resource
		Bundle OPConsultNoteBundle = populateOPConsultNoteBundle();

		// Validate it. Validate method return result of validation in boolean
		// If validation result is true then parse, serialize operations are performed
		if(validate(OPConsultNoteBundle))	
		{
			System.out.println("Validated populated OPConsultNote bundle successfully");

			// Instantiate a new parser
			IParser parser; 

			// Enter file path (Eg: C://generatedexamples//bundle-prescriptionrecord.json)
			// Depending on file type xml/json instantiate the parser
			File file;
			Scanner scanner = new Scanner(System.in);
			System.out.println("\nEnter file path to write bundle");
			String filePath = scanner.nextLine();
			if(FilenameUtils.getExtension(filePath).equals("json"))
			{
				parser = ctx.newJsonParser();
			}
			else if(FilenameUtils.getExtension(filePath).equals("xml"))
			{
				parser = ctx.newXmlParser();
			}
			else
			{
				System.out.println("Invalid file extention!");
				if(scanner!=null)
					scanner.close();
				return;
			}

			// Indent the output
			parser.setPrettyPrint(true);

			// Serialize populated bundle
			String serializeBundle = parser.encodeResourceToString(OPConsultNoteBundle);

			// Write serialized bundle in xml/json file
			file = new File(filePath);
			file.createNewFile();	
			FileWriter writer = new FileWriter(file);
			writer.write(serializeBundle);
			writer.flush();
			writer.close();
			scanner.close();

			// Parse the xml/json file
			IBaseResource resource = parser.parseResource(new FileReader(new File(filePath)));

			// Validate Parsed file
			if(validate(resource)){
				System.out.println("Validated parsed file successfully");
			}
			else{
				System.out.println("Failed to validate parsed file");
			}
		}
		else
		{
			System.out.println("Failed to validate populate Prescription bundle");
		}
	}

	// Populate Composition for OPConsultNote
	static Composition populateOPConsultNoteCompositionResource()
	{
		Composition composition = new Composition();

		// Set logical id of this artifact
		composition.setId("Composition-01");

		// Set metadata about the resource - Version Id, Lastupdated Date, Profile
		Meta meta = composition.getMeta();
		meta.setVersionId("1");
		meta.setLastUpdatedElement(new InstantType("2020-07-09T15:32:26.605+05:30"));
		meta.addProfile("https://nrces.in/ndhm/fhir/r4/StructureDefinition/OPConsultRecord");

		// Set language of the resource content
		composition.setLanguage("en-IN");

		// Plain text representation of the concept
		Narrative text= composition.getText();
		text.setStatus((NarrativeStatus.GENERATED));
		text.setDivAsString("<div xmlns=\"http://www.w3.org/1999/xhtml\"><h4>Narrative with Details</h4><p>This is a OP Consult Note for Patient ABC. Generated Summary: id: 1; Medical Record Number = 1234 (System : {https://healthid.ndhm.gov.in}); active; ABC ; ph: +919818512600(HOME); gender: male; birthDate: 1981-01-12</p></div>");

		// Set version-independent identifier for the Composition
		Identifier identifier = composition.getIdentifier();
		identifier.setSystem("https://ndhm.in/phr");
		identifier.setValue("645bb0c3-ff7e-4123-bef5-3852a4784813");

		// Status can be preliminary | final | amended | entered-in-error
		composition.setStatus(CompositionStatus.FINAL);

		// Kind of composition ("Clinical consultation report")
		CodeableConcept type = composition.getType();
		type.addCoding(new Coding("http://snomed.info/sct", "371530004", "Clinical consultation report"));
		type.setText("Clinical Consultation report");

		//composition.setType(new CodeableConcept(new Coding("http://snomed.info/sct", "371530004", "Clinical consultation report")).setText("Clinical Consultation report"));

		// Set subject - Who and/or what the composition/OPConsultNote record is about
		Reference reference = new Reference();
		reference.setReference("Patient/Patient-01");
		reference.setDisplay("ABC");
		composition.setSubject(reference);

		// Set Context of the Composition
		composition.setEncounter(new Reference().setReference("Encounter/Encounter-01"));

		// Set Timestamp
		composition.setDateElement(new DateTimeType("2017-05-27T11:46:09+05:30"));

		// Set author - Who and/or what authored the composition/OPConsultNote record
		Reference referenceAuthor = new Reference();
		referenceAuthor.setReference("Practitioner/Practitioner-01");
		referenceAuthor.setDisplay("Dr DEF");
		composition.addAuthor(referenceAuthor);

		// Set a Human Readable name/title
		composition.setTitle("Consultation Report");

		// Set Custodian - Organization which maintains the composition
		Reference referenceCustodian = new Reference();
		referenceCustodian.setReference("Organization/Organization-01");
		referenceCustodian.setDisplay("UVW Hospital");
		composition.setCustodian(referenceCustodian);

		// Composition is broken into sections / OPConsultNote record contains single section to define the relevant medication requests
		// Entry is a reference to data that supports this section
		SectionComponent section1 = new SectionComponent();
		section1.setTitle("Chief complaints");
		section1.setCode(new CodeableConcept(new Coding("http://snomed.info/sct", "422843007", "Chief complaint section"))).
		addEntry(new Reference().setReference("Condition/Condition-01"));

		SectionComponent section2 = new SectionComponent();
		section2.setTitle("Allergies");
		section2.setCode(new CodeableConcept(new Coding("http://snomed.info/sct", "722446000", "Allergy record"))).
		addEntry(new Reference().setReference("AllergyIntolerance/AllergyIntolerance-01"));

		SectionComponent section3 = new SectionComponent();
		section3.setTitle("Medical History");
		section3.setCode(new CodeableConcept(new Coding("http://snomed.info/sct", "371529009", "History and physical report"))).
		addEntry(new Reference().setReference("Condition/Condition-02"));

		SectionComponent section4 = new SectionComponent();
		section4.setTitle("Investigation Advice");
		section4.setCode(new CodeableConcept(new Coding("http://snomed.info/sct", "721963009", "Order document"))).
		addEntry(new Reference().setReference("ServiceRequest/ServiceRequest-01"));

		SectionComponent section5 = new SectionComponent();
		section5.setTitle("Medications");
		section5.setCode(new CodeableConcept(new Coding("http://snomed.info/sct", "721912009", "Medication summary document"))).
		addEntry(new Reference().setReference("MedicationStatement/MedicationStatement-01")).
		addEntry(new Reference().setReference("MedicationRequest/MedicationRequest-01"));

		SectionComponent section6 = new SectionComponent();
		section6.setTitle("Procedure");
		section6.setCode(new CodeableConcept(new Coding("http://snomed.info/sct", "371525003", "Clinical procedure report"))).
		addEntry(new Reference().setReference("Procedure/Procedure-01"));

		SectionComponent section7 = new SectionComponent();
		section7.setTitle("Follow Up");
		section7.setCode(new CodeableConcept(new Coding("http://snomed.info/sct", "736271009", "Outpatient care plan"))).
		addEntry(new Reference().setReference("Appointment/Appointment-01"));

		SectionComponent section8 = new SectionComponent();
		section8.setTitle("Document Reference");
		section8.setCode(new CodeableConcept(new Coding("http://snomed.info/sct", "371530004", "Clinical consultation report"))).
		addEntry(new Reference().setReference("DocumentReference/DocumentReference-01"));

		List<SectionComponent> sectionList = new ArrayList<SectionComponent>();
		sectionList.add(section1);
		sectionList.add(section2);
		sectionList.add(section3);
		sectionList.add(section4);
		sectionList.add(section5);
		sectionList.add(section6);
		sectionList.add(section7);
		sectionList.add(section8);
		composition.setSection(sectionList);

		return composition;
	}

	static Bundle populateOPConsultNoteBundle()
	{
		Bundle opCounsultNoteBundle = new Bundle();

		// Set logical id of this artifact
		opCounsultNoteBundle.setId("OPConsultNote-example-01");

		// Set metadata about the resource - Version Id, Lastupdated Date, Profile
		Meta meta = opCounsultNoteBundle.getMeta();
		meta.setVersionId("1");
		meta.setLastUpdatedElement(new InstantType("2020-07-09T15:32:26.605+05:30"));
		meta.addProfile("https://nrces.in/ndhm/fhir/r4/StructureDefinition/DocumentBundle");

		// Set Confidentiality as defined by affinity domain
		meta.addSecurity(new Coding("http://terminology.hl7.org/CodeSystem/v3-Confidentiality", "V", "very restricted"));

		// Set version-independent identifier for the Composition
		Identifier identifier = opCounsultNoteBundle.getIdentifier();
		identifier.setValue("305fecc2-4ba2-46cc-9ccd-efa755aff51d");
		identifier.setSystem("http://hip.in");

		// Set Bundle Type
		opCounsultNoteBundle.setType(BundleType.DOCUMENT);

		// Set Timestamp
		opCounsultNoteBundle.setTimestampElement(new InstantType("2020-07-09T15:32:26.605+05:30"));

		// Add resources entries for bundle with Full URL
		List<BundleEntryComponent> listBundleEntries = opCounsultNoteBundle.getEntry();

		BundleEntryComponent bundleEntry1 = new BundleEntryComponent();
		bundleEntry1.setFullUrl("Composition/Composition-01");
		bundleEntry1.setResource(populateOPConsultNoteCompositionResource());

		BundleEntryComponent bundleEntry2 = new BundleEntryComponent();
		bundleEntry2.setFullUrl("Practitioner/Practitioner-01");
		bundleEntry2.setResource(ResourcePopulator.populatePractitionerResource());

		BundleEntryComponent bundleEntry3 = new BundleEntryComponent();
		bundleEntry3.setFullUrl("Organization/Organization-01");
		bundleEntry3.setResource(ResourcePopulator.populateOrganizationResource());

		BundleEntryComponent bundleEntry4 = new BundleEntryComponent();
		bundleEntry4.setFullUrl("Patient/Patient-01");
		bundleEntry4.setResource(ResourcePopulator.populatePatientResource());

		BundleEntryComponent bundleEntry5 = new BundleEntryComponent();
		bundleEntry5.setFullUrl("Encounter/Encounter-01");
		bundleEntry5.setResource(ResourcePopulator.populateEncounterResource());

		BundleEntryComponent bundleEntry6 = new BundleEntryComponent();
		bundleEntry6.setFullUrl("AllergyIntolerance/AllergyIntolerance-01");
		bundleEntry6.setResource(ResourcePopulator.populateAllergyIntoleranceResource());

		BundleEntryComponent bundleEntry7 = new BundleEntryComponent();
		bundleEntry7.setFullUrl("Appointment/Appointment-01");
		bundleEntry7.setResource(ResourcePopulator.populateAppointmentResource());

		BundleEntryComponent bundleEntry8 = new BundleEntryComponent();
		bundleEntry8.setFullUrl("Condition/Condition-01");
		bundleEntry8.setResource(ResourcePopulator.populateConditionResource());

		BundleEntryComponent bundleEntry9 = new BundleEntryComponent();
		bundleEntry9.setFullUrl("Condition/Condition-02");
		bundleEntry9.setResource(ResourcePopulator.populateSecondConditionResource());

		BundleEntryComponent bundleEntry10 = new BundleEntryComponent();
		bundleEntry10.setFullUrl("Condition/Condition-03");
		bundleEntry10.setResource(ResourcePopulator.populateThirdConditionResource());

		BundleEntryComponent bundleEntry11 = new BundleEntryComponent();
		bundleEntry11.setFullUrl("Procedure/Procedure-01");
		bundleEntry11.setResource(ResourcePopulator.populateProcedureResource());

		BundleEntryComponent bundleEntry12 = new BundleEntryComponent();
		bundleEntry12.setFullUrl("ServiceRequest/ServiceRequest-01");
		bundleEntry12.setResource(ResourcePopulator.populateServiceRequestResource());

		BundleEntryComponent bundleEntry13 = new BundleEntryComponent();
		bundleEntry13.setFullUrl("MedicationStatement/MedicationStatement-01");
		bundleEntry13.setResource(ResourcePopulator.populateMedicationStatementResource());

		BundleEntryComponent bundleEntry14 = new BundleEntryComponent();
		bundleEntry14.setFullUrl("MedicationRequest/MedicationRequest-01");
		bundleEntry14.setResource(ResourcePopulator.populateMedicationRequestResource());

		BundleEntryComponent bundleEntry15 = new BundleEntryComponent();
		bundleEntry15.setFullUrl("DocumentReference/DocumentReference-01");
		bundleEntry15.setResource(ResourcePopulator.populateDocumentReferenceResource());

		listBundleEntries.add(bundleEntry1);
		listBundleEntries.add(bundleEntry2);
		listBundleEntries.add(bundleEntry3);
		listBundleEntries.add(bundleEntry4);
		listBundleEntries.add(bundleEntry5);
		listBundleEntries.add(bundleEntry6);
		listBundleEntries.add(bundleEntry7);
		listBundleEntries.add(bundleEntry8);
		listBundleEntries.add(bundleEntry9);
		listBundleEntries.add(bundleEntry10);
		listBundleEntries.add(bundleEntry11);
		listBundleEntries.add(bundleEntry12);
		listBundleEntries.add(bundleEntry13);
		listBundleEntries.add(bundleEntry14);
		listBundleEntries.add(bundleEntry15);

		return opCounsultNoteBundle;
	}

	/**
	 * This method initiates loading of FHIR default profiles and NDHM profiles for validation 
	 */
	static void init() throws DataFormatException, FileNotFoundException
	{

		// Create xml parser object for reading profiles
		IParser parser = ctx.newXmlParser();

		// Create a chain that will hold our modules
		ValidationSupportChain supportChain = new ValidationSupportChain();
		
		// Add Default Profile Support
		// DefaultProfileValidationSupport supplies base FHIR definitions. This is generally required
		// even if you are using custom profiles, since those profiles will derive from the base
		// definitions.
		DefaultProfileValidationSupport defaultSupport = new DefaultProfileValidationSupport(ctx);
		
		// Create a PrePopulatedValidationSupport which can be used to load custom definitions.
		// In this example we're loading all the custom Profile Structure Definitions, in other scenario we might
		// load many StructureDefinitions, ValueSets, CodeSystems, etc.
		PrePopulatedValidationSupport prePopulatedSupport = new PrePopulatedValidationSupport(ctx);
		StructureDefinition sd ;
		
		/** LOADING PROFILES **/
		// Read all Profile Structure Definitions 
		String[] fileList = new File("<path for folder where all profiles are copied>").list(new WildcardFileFilter("*.xml"));
		for(String file:fileList)
		{
			//Parse All Profiles and add to prepopulated support
			sd = parser.parseResource(StructureDefinition.class, new FileReader("<path for folder where all profiles are copied>"+file));
			prePopulatedSupport.addStructureDefinition(sd);
		}

		//Add Snapshot Generation Support
		SnapshotGeneratingValidationSupport snapshotGenerator = new SnapshotGeneratingValidationSupport(ctx);

		//Add prepopulated support consisting all structure definitions and Terminology support
		supportChain.addValidationSupport(defaultSupport);
		supportChain.addValidationSupport(prePopulatedSupport);
		supportChain.addValidationSupport(snapshotGenerator);
		supportChain.addValidationSupport(new InMemoryTerminologyServerValidationSupport(ctx));
		supportChain.addValidationSupport(new CommonCodeSystemsTerminologyService(ctx));

		// Create a validator using the FhirInstanceValidator module and register.
		instanceValidator = new FhirInstanceValidator(supportChain);
		validator = ctx.newValidator().registerValidatorModule(instanceValidator);

	}

	/**
	 * This method validates the FHIR resources 
	 */
	static boolean validate(IBaseResource resource)
	{
		// Validate
		ValidationResult result = validator.validateWithResult(resource);

		// The result object now contains the validation results
		for (SingleValidationMessage next : result.getMessages()) {
			System.out.println(next.getSeverity().name() + " : " + next.getLocationString() + " " + next.getMessage());
		}

		return result.isSuccessful();
	}
}
