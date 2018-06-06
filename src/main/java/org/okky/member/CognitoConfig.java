package org.okky.member;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.amazonaws.regions.Regions.AP_NORTHEAST_2;

@Configuration
class CognitoConfig {
    @Value("${app.aws-access-key}")
    String accessKey;
    @Value("${app.aws-secret-key}")
    String secretKey;

    @Bean
    AWSCognitoIdentityProvider cognitoClient() {
        return AWSCognitoIdentityProviderClientBuilder
                .standard()
                .withCredentials(credential())
                .withRegion(AP_NORTHEAST_2)
                .build();
    }

    @Bean
    AWSStaticCredentialsProvider credential() {
        BasicAWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        return new AWSStaticCredentialsProvider(credentials);
    }
}
