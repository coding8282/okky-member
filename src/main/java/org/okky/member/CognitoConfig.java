package org.okky.member;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.amazonaws.regions.Regions.AP_NORTHEAST_2;

@Configuration
class CognitoConfig {
    @Bean
    AWSCognitoIdentityProvider cognitoClient(AWSCredentialsProvider provider) {
        return AWSCognitoIdentityProviderClientBuilder
                .standard()
                .withCredentials(provider)
                .withRegion(AP_NORTHEAST_2)
                .build();
    }
}
