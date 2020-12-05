
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
 * The DiagnosticReportLabSample class populates, validates, parse and serializes Clinical Artifact - DiagnosticReport Lab
 */
public class DiagnosticReportLabSample {

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
		Bundle diagnosticReportLabBundle = populateDiagnosticReportLabBundle();

		// Validate it. Validate method return result of validation in boolean
		// If validation result is true then parse, serialize operations are performed	
		if(validate(diagnosticReportLabBundle))
		{
			System.out.println("Validated populated DiagnosticReportLab bundle successfully");

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
			String serializeBundle = parser.encodeResourceToString(diagnosticReportLabBundle);

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


	// Populate Composition for DiagnosticReport Lab
	static Composition populateDiagnosticReportRecordLabCompositionResource()
	{
		Composition composition = new Composition();

		// Set logical id of this artifact
		composition.setId("Composition-01");

		// Set metadata about the resource - Version Id, Lastupdated Date, Profile
		Meta meta = composition.getMeta();
		meta.setVersionId("1");
		meta.setLastUpdatedElement(new InstantType("2020-07-09T15:32:26.605+05:30"));
		meta.addProfile("https://nrces.in/ndhm/fhir/r4/StructureDefinition/DiagnosticReportRecord");

		// Set language of the resource content
		composition.setLanguage("en-IN");

		// Plain text representation of the concept
		Narrative text= composition.getText();
		text.setStatus((NarrativeStatus.GENERATED));
		text.setDivAsString("<div xmlns=\"http://www.w3.org/1999/xhtml\"><h4>Narrative with Details</h4>\r\n      <p><b>id:</b> 1</p>\r\n\t  <p><b>status:</b> final</p>\r\n\t  <p><b>category:</b> Hematology service (Details : {http://snomed.info/sct} code '708196005' = 'Hematology service')</p>\r\n\t  <p><b>code:</b> Lipid 1996 panel - Serum or Plasma (Details : {http://loinc.org} code '24331-1' = 'Lipid 1996 panel - Serum or Plasma')</p>\r\n\t  <p><b>subject:</b> ABC. Generated Summary: id: 1; Medical Record Number = 1234 (System : {https://healthid.ndhm.gov.in}); active; ABC ; ph: +919818512600(HOME); gender: male; birthDate: 1981-01-12</p>\r\n\t  <p><b>issued:</b> 2020-07-10T11:45:33+11:00</p>\r\n\t  <p><b>performer:</b> XYZ Lab Pvt.Ltd.</p>\r\n\t  <p><b>resultInterpreter:</b> Dr. DEF. Generated Summary: id: 1; Medical License number = 7601003178999 (System : {doctor.ndhm.gov.in})</p>\n\t\t\t\t\t\t<h3>Lipid Report for ABC issued 9-July 2020 14:26</h3>\n\t\t\t\t\t\t<pre>\nTest                  Units       Value       Reference Range\nCholesterol           mmol/L      6.3         &lt;4.5\nTriglyceride          mmol/L      1.3         &lt;2.0\n\n      </pre>\n\t\t\t\t\t\t<p>XYZ Lab Pvt.Ltd., Inc signed: Dr. DEF Pathologist</p>\n\t\t\t\t\t</div>");

		// Set version-independent identifier for the Composition
		Identifier identifier = composition.getIdentifier();
		identifier.setSystem("https://ndhm.in/phr");
		identifier.setValue("645bb0c3-ff7e-4123-bef5-3852a4784813");

		// Status can be preliminary | final | amended | entered-in-error
		composition.setStatus(CompositionStatus.FINAL);

		// Kind of composition ("Diagnostic studies report")
		CodeableConcept type = composition.getType();
		type.addCoding(new Coding("http://snomed.info/sct", "721981007", "Diagnostic studies report"));
		type.setText("Diagnostic Report- Lab");

		// Set subject - Who and/or what the composition/DiagnosticReport record is about
		composition.setSubject(new Reference().setReference("Patient/Patient-01"));

		// Set Timestamp
		composition.setDateElement(new DateTimeType("2017-05-27T11:46:09+05:30"));

		// Set author - Who and/or what authored the composition/DiagnosticReport record
		composition.addAuthor(new Reference().setReference("Practitioner/Practitioner-01"));

		// Set a Human Readable name/title
		composition.setTitle("Diagnostic Report- Lab");

		// Composition is broken into sections / DiagnosticReport Lab record contains single section to define the relevant medication requests
		// Entry is a reference to data that supports this section
		Reference reference1 = new Reference();
		reference1.setReference("DiagnosticReport/DiagnosticReport-01");
		reference1.setType("DiagnosticReport");

		Reference reference2 = new Reference();
		reference2.setReference("DocumentReference/DocumentReference-01");
		reference2.setType("DocumentReference");

		SectionComponent section = new SectionComponent();
		section.setTitle("Hematology report");
		section.setCode(new CodeableConcept(new Coding("http://snomed.info/sct", "4321000179101", "Hematology report"))).
		addEntry(reference1).
		addEntry(reference2);
		composition.addSection(section);

		return composition;
	}

	static Bundle populateDiagnosticReportLabBundle()
	{
		Bundle diagnosticReportBundle = new Bundle();

		// Set logical id of this artifact
		diagnosticReportBundle.setId("DiagnosticReport-Lab-example-01");

		// Set metadata about the resource - Version Id, Lastupdated Date, Profile
		Meta meta = diagnosticReportBundle.getMeta();
		meta.setVersionId("1");
		meta.setLastUpdatedElement(new InstantType("2020-07-09T15:32:26.605+05:30"));
		meta.addProfile("https://nrces.in/ndhm/fhir/r4/StructureDefinition/DocumentBundle");

		// Set Confidentiality as defined by affinity domain
		meta.addSecurity(new Coding("http://terminology.hl7.org/CodeSystem/v3-Confidentiality", "V", "very restricted"));

		// Set version-independent identifier for the Composition
		Identifier identifier = diagnosticReportBundle.getIdentifier();
		identifier.setValue("3cf54fc4-0178-4127-bb99-b20711404881");
		identifier.setSystem("http://hip.in");

		// Set Bundle Type
		diagnosticReportBundle.setType(BundleType.DOCUMENT);

		// Set Timestamp
		diagnosticReportBundle.setTimestampElement(new InstantType("2020-07-09T15:32:26.605+05:30"));

		// Add resources entries for bundle with Full URL
		List<BundleEntryComponent> listBundleEntries = diagnosticReportBundle.getEntry();

		BundleEntryComponent bundleEntry1 = new BundleEntryComponent();
		bundleEntry1.setFullUrl("Composition/Composition-01");
		bundleEntry1.setResource(populateDiagnosticReportRecordLabCompositionResource());

		BundleEntryComponent bundleEntry2 = new BundleEntryComponent();
		bundleEntry2.setFullUrl("Patient/Patient-01");
		bundleEntry2.setResource(ResourcePopulator.populatePatientResource());

		BundleEntryComponent bundleEntry3 = new BundleEntryComponent();
		bundleEntry3.setFullUrl("Practitioner/Practitioner-01");
		bundleEntry3.setResource(ResourcePopulator.populatePractitionerResource());

		BundleEntryComponent bundleEntry4 = new BundleEntryComponent();
		bundleEntry4.setFullUrl("Organization/Organization-01");
		bundleEntry4.setResource(ResourcePopulator.populateOrganizationResource());

		BundleEntryComponent bundleEntry5 = new BundleEntryComponent();
		bundleEntry5.setFullUrl("DiagnosticReport/DiagnosticReport-01");
		bundleEntry5.setResource(ResourcePopulator.populateDiagnosticReportImagingMediaResource());

		BundleEntryComponent bundleEntry6 = new BundleEntryComponent();
		bundleEntry6.setFullUrl("Observation/Observation-cholesterol");
		bundleEntry6.setResource(ResourcePopulator.populateCholesterolObservationResource());

		BundleEntryComponent bundleEntry7 = new BundleEntryComponent();
		bundleEntry7.setFullUrl("Observation/Observation-triglyceride");
		bundleEntry7.setResource(ResourcePopulator.populateTriglycerideObservationResource());

		BundleEntryComponent bundleEntry8 = new BundleEntryComponent();
		bundleEntry8.setFullUrl("Specimen/Specimen-01");
		bundleEntry8.setResource(ResourcePopulator.populateSpecimenResource());

		BundleEntryComponent bundleEntry9 = new BundleEntryComponent();
		bundleEntry9.setFullUrl("ServiceRequest/ServiceRequest-01");
		bundleEntry9.setResource(ResourcePopulator.populateServiceRequestResource());

		BundleEntryComponent bundleEntry10 = new BundleEntryComponent();
		bundleEntry10.setFullUrl("Practitioner/Practitioner-02");
		bundleEntry10.setResource(ResourcePopulator.populatePractitionerResource());

		BundleEntryComponent bundleEntry11 = new BundleEntryComponent();
		bundleEntry11.setFullUrl("DocumentReference/DocumentReference-01");
		bundleEntry11.setResource(ResourcePopulator.populateDocumentReferenceResource());

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

		return diagnosticReportBundle;
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