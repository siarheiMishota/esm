package com.epam.esm.configuration;

import com.epam.esm.service.configuration.ServiceConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(ServiceConfiguration.class)
public class ControllerConfiguration {
}
