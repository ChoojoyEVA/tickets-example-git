package org.hse.example.service;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Интерфейс-строитель экземпляров {@link TicketService}
 */
public interface TicketServiceBuilder {
    /**
     * @return построитель
     */
    Supplier<TicketService> supplier();

    /**
     * @return экземпляр {@link TicketCounterServiceImpl}
     */
    default TicketService build() {
        return supplier().get();
    }
}
