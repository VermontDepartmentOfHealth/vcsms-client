package gov.nist.healthcare.vcsms.example;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import gov.nist.healthcare.vcsms.domain.ClientConfiguration;
import gov.nist.healthcare.vcsms.domain.DistributionAvailableRequestStatus;
import gov.nist.healthcare.vcsms.domain.RESTClientInfo;
import gov.nist.healthcare.vcsms.domain.RemoteResponse;
import gov.nist.healthcare.vcsms.exception.ResourceNotFoundException;
import gov.nist.healthcare.vcsms.service.impl.NISTVCSMSClientImpl;
import gov.nist.healthcare.vcsms.service.impl.VCSMSClientImpl;

public class Main {

	public static void main(String[] args) throws ResourceNotFoundException, IOException {

		// get config values
		Properties prop = new Properties();
		FileInputStream ip = new FileInputStream("src/main/java/gov/nist/healthcare/vcsms/example/config.properties");
		prop.load(ip);

		
		ClientConfiguration config = new ClientConfiguration(prop.getProperty("ROOT_PATH"), 
															 prop.getProperty("GROUP_MNEMONIC"), 
															 prop.getProperty("NODE_ID"), 
															 prop.getProperty("FTP_HOST"), 
															 prop.getProperty("USERNAME"), 
															 prop.getProperty("PASSWORD"));

		// initialize REST client
		VCSMSClientImpl client = new VCSMSClientImpl(config);															 

		DistributionAvailableRequestStatus packages = client.getAllAvailablePackageLists();

		// initialize NIST client
		NISTVCSMSClientImpl nistClient = new NISTVCSMSClientImpl(config, new RESTClientInfo("1.0.0","TEST Client"), prop.getProperty("DOWNLOAD_LOCATION"));



		RemoteResponse response = nistClient.checkResource(prop.getProperty("RESOURCE_ID"));

		System.out.println(response.isStatus());
		System.out.println(response.getName());
		System.out.println(response.isChanged());
	}
}
