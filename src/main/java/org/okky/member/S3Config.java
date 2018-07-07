package org.okky.member;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.amazonaws.regions.Regions.AP_NORTHEAST_2;

@Configuration
class S3Config {
    @Bean
    AmazonS3 s3Client(AWSCredentialsProvider provider) {
        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(provider)
                .withRegion(AP_NORTHEAST_2)
                .build();
    }
}
