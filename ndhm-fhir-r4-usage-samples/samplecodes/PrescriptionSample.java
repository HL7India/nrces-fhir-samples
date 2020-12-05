
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
 * The PrescriptionSample class populates, validates, parse and serializes Clinical Artifact - Prescription
 */
public class PrescriptionSample {

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
		Bundle prescriptionBundle = populatePrescriptionBundle();

		// Validate it. Validate method return result of validation in boolean
		// If validation result is true then parse, serialize operations are performed
		if(validate(prescriptionBundle))	
		{
			System.out.println("Validated populated Prescripton bundle successfully");

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
				scanner.close();
				return;
			}

			// Indent the output
			parser.setPrettyPrint(true);

			// Serialize populated bundle
			String serializeBundle = parser.encodeResourceToString(prescriptionBundle);

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

	// Populate Composition for Prescription
	static Composition populatePrescriptionCompositionResource()
	{
		Composition composition = new Composition();	

		// Set logical id of this artifact
		composition.setId("Composition-01");

		// Set metadata about the resource - Version Id, Lastupdated Date, Profile
		Meta meta = composition.getMeta();
		meta.setVersionId("1");
		meta.setLastUpdatedElement(new InstantType("2020-07-09T15:32:26.605+05:30"));
		meta.addProfile("https://nrces.in/ndhm/fhir/r4/StructureDefinition/PrescriptionRecord");

		// Set language of the resource content
		composition.setLanguage("en-IN");

		// Plain text representation of the concept
		Narrative text= composition.getText();
		text.setStatus((NarrativeStatus.GENERATED));
		text.setDivAsString("<div xmlns=\"http://www.w3.org/1999/xhtml\">Prescription report</div>");

		// Set version-independent identifier for the Composition
		Identifier identifier = composition.getIdentifier();
		identifier.setSystem("https://ndhm.in/phr");
		identifier.setValue("645bb0c3-ff7e-4123-bef5-3852a4784813");

		// Status can be preliminary | final | amended | entered-in-error
		composition.setStatus(CompositionStatus.FINAL);

		// Kind of composition ("Prescription record ")
		composition.setType(new CodeableConcept(new Coding("http://snomed.info/sct", "440545006", "Prescription record")));

		// Set subject - Who and/or what the composition/Prescription record is about
		composition.setSubject(new Reference().setReference("Patient/Patient-01"));

		// Set Timestamp
		composition.setDateElement(new DateTimeType("2017-05-27T11:46:09+05:30"));

		// Set author - Who and/or what authored the composition/Presciption record
		composition.addAuthor(new Reference().setReference("Practitioner/Practitioner-01"));

		// Set a Human Readable name/title
		composition.setTitle("Prescription record");

		// Composition is broken into sections / Prescription record contains single section to define the relevant medication requests
		// Entry is a reference to data that supports this section
		Reference reference1 = new Reference();
		reference1.setReference("MedicationRequest/MedicationRequest-01");
		reference1.setType("MedicationRequest");

		Reference reference2 = new Reference();
		reference2.setReference("MedicationRequest/MedicationRequest-02");
		reference2.setType("MedicationRequest");

		Reference reference3 = new Reference();
		reference3.setReference("Binary/Binary-01");
		reference3.setType("Binary");

		SectionComponent section = new SectionComponent();
		section.setTitle("Prescription record");
		section.setCode(new CodeableConcept(new Coding("http://snomed.info/sct", "440545006", "Prescription record"))).
		addEntry(reference1).
		addEntry(reference2).
		addEntry(reference3);
		composition.addSection(section);	

		return composition;
	}

	// Populate Prescription Bundle
	static Bundle populatePrescriptionBundle()
	{
		Bundle prescriptionBundle = new Bundle();

		// Set logical id of this artifact
		prescriptionBundle.setId("prescription-bundle-01");

		// Set metadata about the resource
		Meta meta = prescriptionBundle.getMeta();
		meta.setVersionId("1");
		meta.setLastUpdatedElement(new InstantType("2020-07-09T15:32:26.605+05:30"));
		meta.addProfile("https://nrces.in/ndhm/fhir/r4/StructureDefinition/DocumentBundle");

		// Set Confidentiality as defined by affinity domain
		meta.addSecurity(new Coding("http://terminology.hl7.org/CodeSystem/v3-Confidentiality", "V", "very restricted"));

		// Set version-independent identifier for the Bundle
		Identifier identifier = prescriptionBundle.getIdentifier();
		identifier.setValue("bc3c6c57-2053-4d0e-ac40-139ccccff645");
		identifier.setSystem("http://hip.in");

		// Set Bundle Type 
		prescriptionBundle.setType(BundleType.DOCUMENT);

		// Set Timestamp 
		prescriptionBundle.setTimestampElement(new InstantType("2020-07-09T15:32:26.605+05:30"));

		// Add resources entries for bundle with Full URL
		List<BundleEntryComponent> listBundleEntries = prescriptionBundle.getEntry();

		BundleEntryComponent bundleEntry1 = new BundleEntryComponent();
		bundleEntry1.setFullUrl("Composition/Composition-01");
		bundleEntry1.setResource(populatePrescriptionCompositionResource());

		BundleEntryComponent bundleEntry2 = new BundleEntryComponent();
		bundleEntry2.setFullUrl("Patient/Patient-01");
		bundleEntry2.setResource(ResourcePopulator.populatePatientResource());

		BundleEntryComponent bundleEntry3 = new BundleEntryComponent();
		bundleEntry3.setFullUrl("Practitioner/Practitioner-01");
		bundleEntry3.setResource(ResourcePopulator.populatePractitionerResource());

		BundleEntryComponent bundleEntry4 = new BundleEntryComponent();
		bundleEntry4.setFullUrl("MedicationRequest/MedicationRequest-01");
		bundleEntry4.setResource(ResourcePopulator.populateMedicationRequestResource());

		BundleEntryComponent bundleEntry5 = new BundleEntryComponent();
		bundleEntry5.setFullUrl("MedicationRequest/MedicationRequest-02");
		bundleEntry5.setResource(ResourcePopulator.populateSecondMedicationRequestResource());

		BundleEntryComponent bundleEntry6 = new BundleEntryComponent();
		bundleEntry6.setFullUrl("Condition/Condition-01");
		bundleEntry6.setResource(ResourcePopulator.populateConditionResource());

		BundleEntryComponent bundleEntry7 = new BundleEntryComponent();
		bundleEntry7.setFullUrl("Binary/Binary-01");
		bundleEntry7.setResource(ResourcePopulator.populateBinaryResource());

		listBundleEntries.add(bundleEntry1);
		listBundleEntries.add(bundleEntry2);
		listBundleEntries.add(bundleEntry3);
		listBundleEntries.add(bundleEntry4);
		listBundleEntries.add(bundleEntry5);
		listBundleEntries.add(bundleEntry6);
		listBundleEntries.add(bundleEntry7);

		return prescriptionBundle;
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
