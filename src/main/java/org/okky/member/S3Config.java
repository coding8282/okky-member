package org.okky.member;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.amazonaws.regions.Regions.AP_NORTHEAST_2;

@Configuration
class S3Config {
    @Value("${app.aws-access-key}")
    String accessKey;
    @Value("${app.aws-secret-key}")
    String secretKey;

    @Bean
    AmazonS3 s3Client() {
        return AmazonS3ClientBuilder
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
