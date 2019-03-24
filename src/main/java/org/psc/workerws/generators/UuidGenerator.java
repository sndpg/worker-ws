package org.psc.workerws.generators;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Slf4j
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UuidGenerator {

    private String internalId;

    public UuidGenerator() {
        internalId = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-hhmmss.nnnnnnnnn"));
        log.info(internalId);
    }

    public UUID getUuid() {
        log.info("generating new uuid with instance: {}", internalId);
        return UUID.randomUUID();
    }

}
