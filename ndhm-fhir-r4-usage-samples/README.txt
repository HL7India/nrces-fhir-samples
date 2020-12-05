********************************************************
		Introduction
********************************************************

FHIR resources can be used in many different contexts in healthcare. In order to include differnt use cases, specifications need to be able to describe restrictions on how one or more resource(s) are used, including defining extensions, and controlling how terminology is used. These things are done using a "Profile", which itself is represented as a resource.

NDHM FHIR profiles are designed for the NDHM Health Data Interchange Specifications 1.0. These includes following clinical artifacts:
1. DiagnosticReportRecord
2. DischargeSummaryRecord
3. OPConsultRecord
4. PrescriptionRecord

The purpose of NDHM FHIR Sample Codes is to demonstarte how to populate, validate clinical artifacts against  NDHM profiles, serialize and parse NDHM clinical artifacts programtically using FHIR HAPI library,  that can be captured and exchanged as per NDHM Health Data Interchange Specifications 1.0. 

Resources in these sample codes are based on FHIR R4. Populated resources includes mandatory elements and terminology requirements that MUST be present.

Note: Sample Codes are compiled and tested on AdoptOpenJDK 1.8. 

********************************************************
	Pre-Requisite requirements
********************************************************

1. JDK 1.8 or above to be installed on machine
      Set the environment variables for Java.
	e.g : Setting the environment variables for Java considering AdoptOpenJDK 1.8 is intalled.  
		Windows: Add System Environment Variable: JAVA_HOME - C:\Program Files\Java\jdk8u202-b08
		LINUX: modify '/etc/profile': JAVA_HOME - export JAVA_HOME=/usr/lib/jvm/jdk8u202-b08

	Append the path value for JAVA in the PATH environment variable.
	e.g :  Windows: C:\Program Files\Java\jdk8u202-b08\bin
	       LINUX: modify '/etc/profile': export PATH=/usr/lib/jvm/jdk8u202-b08/bin:$PATH

2. Dependencies required
   FHIR Hapi library - HAPI FHIR 5.2.0 (Labrador)
   a. hapi-fhir-base-5.0.2-sources.jar
   b. hapi-fhir-structures-r4-5.0.2.jar
   c. hapi-fhir-validation-5.0.2.jar
   d. hapi-fhir-validation-resources-r4-5.0.2.jar
   e. hapi-fhir-structures-r5-5.0.2.jar (As of HAPI-FHIR v0.8, a separate FHIR strcture JAR must be added to your classpath (or project pom.xml if you are using Maven))

3. NDHM FHIR profiles. Download profiles from: https://www.nrces.in/ndhm/fhir/r4/downloads.html. Copy downloaded profiles to a folder. 
   Mention profiles folder path in Sample codes where the placeholder is '<path for folder where all profiles are copied>'.

********************************************************
	Things To Consider
********************************************************

1. The systems used in the examples are temporary place holders and the values may be different in actual implementation
2. The resource identifiers in the examples are simple number and instead, a point-in-time / system generated URN / UID can be used. The source may preserve the identifiers for referring the resource for later purposes


********************************************************
	What zip includes 
********************************************************
1. Sample Codes
	a. ResourcePopulator:
		Populates all the required resources that are defined to exchange quality data and reuquired to populate Clinical Artifacts.

	b. PrescriptionSample
		Populate Prescription sample bundle, validtes populated bundle, serialize it and write it in json/xml format. Then parse the existing written file and validates it again.

        c. OPConsultNoteSample
		Populate OPConsultNote sample bundle, validtes populated bundle, serialize it and write it in json/xml format. Then parse the existing written file and validates it again.

	d. DischargeSummarySample
		Populate DischargeSummary sample bundle, validtes populated bundle, serialize it and write it in json/xml format. Then parse the existing written file and validates it again.

	e. DiagnosticReportLabSample
		Populate DiagnosticReportLab sample bundle, validtes populated bundle, serialize it and write it in json/xml format. Then parse the existing written file and validates it again.

	f. DiagnosticReportImagingMediaSample
		Populate DiagnosticReportImagingMedia sample bundle, validtes populated bundle, serialize it and write it in json/xml format. Then parse the existing written file and validates it again.

	g. DiagnosticReportImagingDcmSample
		Populate DiagnosticReportImagingDcm sample bundle, validtes populated bundle, serialize it and write it in json/xml format. Then parse the existing written file and validates it again.


********************************************************
			FINISH
********************************************************
