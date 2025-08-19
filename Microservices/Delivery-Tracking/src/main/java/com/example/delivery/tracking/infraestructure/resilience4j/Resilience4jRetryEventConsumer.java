package com.example.delivery.tracking.infraestructure.resilience4j;

import org.springframework.stereotype.Component;

import io.github.resilience4j.core.registry.EntryAddedEvent;
import io.github.resilience4j.core.registry.EntryRemovedEvent;
import io.github.resilience4j.core.registry.EntryReplacedEvent;
import io.github.resilience4j.core.registry.RegistryEventConsumer;
import io.github.resilience4j.retry.Retry;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class Resilience4jRetryEventConsumer implements RegistryEventConsumer<Retry>{

    @Override
    public void onEntryAddedEvent(EntryAddedEvent<Retry> entryAddedEvent) {
        entryAddedEvent.getAddedEntry().getEventPublisher()
            .onEvent(event -> log.info(event.toString()));
    }

    @Override
    public void onEntryRemovedEvent(EntryRemovedEvent<Retry> entryRemoveEvent) {
        throw new UnsupportedOperationException("Unimplemented method 'onEntryRemovedEvent'");
    }

    @Override
    public void onEntryReplacedEvent(EntryReplacedEvent<Retry> entryReplacedEvent) {
        throw new UnsupportedOperationException("Unimplemented method 'onEntryReplacedEvent'");
    }

}
