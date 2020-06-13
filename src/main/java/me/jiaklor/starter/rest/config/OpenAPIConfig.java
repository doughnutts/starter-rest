package me.jiaklor.starter.rest.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class OpenAPIConfig {

    @Bean("starter-rest-openapi")
    public OpenAPI openAPI(
            @Value("${springdoc.info.title:}") String title,
            @Value("${springdoc.info.description:}") String description,
            @Value("${springdoc.info.version:}") String version,
            @Value("${springdoc.info.termsOfService:}") String termsOfService,
            @Value("${springdoc.info.license.name:}") String licenseName,
            @Value("${springdoc.info.license.url:}") String licenseURL,
            @Value("${springdoc.info.contact.name:}") String contactName,
            @Value("${springdoc.info.contact.url:}") String contactURL,
            @Value("${springdoc.info.contact.email:}") String contactEmail,
            @Value("${springdoc.docs.url:}") String docsURL,
            @Value("${springdoc.docs.description:}") String docsDescription)
    {
        return new OpenAPI()
                .info(getInfo(title, description, version, termsOfService, licenseName, licenseURL, contactName, contactURL, contactEmail))
                .externalDocs(new ExternalDocumentation().url(docsURL).description(docsDescription));
    }

    private Info getInfo(
            String title,
            String description,
            String version,
            String termsOfService,
            String licenseName,
            String licenseURL,
            String contactName,
            String contactURL,
            String contactEmail)
    {
        Info info = new Info()
                .title(title)
                .description(description)
                .version(version)
                .termsOfService(termsOfService)
                .contact(new Contact().name(contactName).url(contactURL).email(contactEmail));
        if (!StringUtils.isEmpty(licenseURL)) info.license(new License().name(licenseName).url(licenseURL));
        return info;
    }

    @Bean("starter-rest-corsfilter")
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
