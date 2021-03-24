package org.hse.example;

import org.hse.example.service.*;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Реализация примера со счастливыми билетами
 * Тут что-то надо написать
 */
public class TicketsExample {
    private static Supplier<TicketService> supplier = () -> new TicketCounterServiceImpl(6);
    private static Function<Integer, TicketService> builder = digitsQty -> {
        if(digitsQty > 5){
            return new NearestTickets(digitsQty);
        }

        return new TicketCounterServiceImpl(digitsQty);
    };
    private static Integer i;

    /**
     * Точка входа
     *
     * @param args строка аргументов. В настоящее время не используется
     */
    public static void main(String[] args) {
        System.out.println("Hello world!");

        Function<Integer, TicketService> builder = digitsQty -> {
            if(digitsQty > TicketsExample.i){
                return new NearestTickets(digitsQty);
            }

            return new TicketCounterServiceImpl(digitsQty);
        };

        TicketsExample.i = 3;

        builder.apply(6).doWork().printResult();
    }

}
