package com.maple.userauth.server;

import jakarta.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.mockserver.integration.ClientAndServer;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ServiceMockManager {

    private final Map<MockService, ServiceMock> serviceMockMap = new HashMap<>();

    @PostConstruct
    public void postConstruct() {

        for (final MockService service : MockService.values()) {

            createServiceMockFor(service);
        }
    }

    private void createServiceMockFor(final MockService service) {

        log.info("Creating ServiceMock for {}", service.getId());

        val serviceMock = new ServiceMock(service);
        serviceMockMap.put(service, serviceMock);
    }

    public ClientAndServer serverFor(final MockService service) {

        return serviceMockMap.get(service)
                             .getMockServer();
    }

    public void reset() {

        serviceMockMap.entrySet()
                      .stream()
                      .map(Entry::getValue)
                      .map(ServiceMock.class::cast)
                      .forEach(ServiceMock::reset);
    }

}
