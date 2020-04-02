package org.worldbuild.cloud.profile;


import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.security.InvalidParameterException;
import java.util.Base64;

@Log4j2
@SpringBootApplication
public class ProfileService {

	public static void main(String[] args) {
		getSecretV1();
		SpringApplication.run(ProfileService.class, args);
	}

	public static void getSecretV1() {
		String secretName = "prod/gps/db";
		String region = "ap-south-1";
		// Create a Secrets Manager client
		AWSSecretsManager client  = AWSSecretsManagerClientBuilder.standard()
				.withRegion(region)
				.build();
		String secret = null, decodedBinarySecret = null;
		GetSecretValueRequest getSecretValueRequest = new GetSecretValueRequest().withSecretId(secretName);
		GetSecretValueResult getSecretValueResult = null;
		try {
			getSecretValueResult = client.getSecretValue(getSecretValueRequest);
		} catch (DecryptionFailureException e) {
			// Secrets Manager can't decrypt the protected secret text using the provided KMS key.
			// Deal with the exception here, and/or rethrow at your discretion.
			log.error("DecryptionFailureException",e);
		} catch (InternalServiceErrorException e) {
			// An error occurred on the server side.
			// Deal with the exception here, and/or rethrow at your discretion.
			log.error("InternalServiceErrorException",e);
		} catch (InvalidParameterException e) {
			// You provided an invalid value for a parameter.
			// Deal with the exception here, and/or rethrow at your discretion.
			log.error("InvalidParameterException",e);
		} catch (InvalidRequestException e) {
			// You provided a parameter value that is not valid for the current state of the resource.
			// Deal with the exception here, and/or rethrow at your discretion.
			log.error("InvalidRequestException",e);
		} catch (ResourceNotFoundException e) {
			// We can't find the resource that you asked for.
			// Deal with the exception here, and/or rethrow at your discretion.
			log.error("ResourceNotFoundException",e);
		}
		// Decrypts secret using the associated KMS CMK.
		// Depending on whether the secret is a string or binary, one of these fields will be populated.
		if (getSecretValueResult.getSecretString() != null) {
			secret = getSecretValueResult.getSecretString();
		} else {
			decodedBinarySecret = new String(Base64.getDecoder().decode(getSecretValueResult.getSecretBinary()).array());
		}
		log.info(secret);
		log.info(decodedBinarySecret);
	}
	/*public static void getSecretV2() {
		String secretName = "prod/gps/db";
		String region = "ap-south-1";
		SecretsManagerClient client = SecretsManagerClient.builder().region(Region.of(region)).build();
		GetSecretValueResponse response = null;
		try {
			response = client.getSecretValue(GetSecretValueRequest.builder().secretId(secretName).build());
		} catch (DecryptionFailureException e) {
			log.error("InternalServiceErrorException",e);
		} catch (InternalServiceErrorException e) {
			log.error("InternalServiceErrorException",e);
		} catch (InvalidParameterException e) {
			log.error("InternalServiceErrorException",e);
		} catch (InvalidRequestException e) {
			log.error("InternalServiceErrorException",e);
		} catch (ResourceNotFoundException e) {
			log.error("InternalServiceErrorException",e);
		}
		String secret=response.secretString();
		log.info(secret);
	}*/
}

