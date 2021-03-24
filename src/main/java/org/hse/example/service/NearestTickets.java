package org.hse.example.service;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * Находит минимальное расстояние между счастливыми билетами
 */
public class NearestTickets implements TicketService {
    private int maxNumber;
    private int[] digits;
    private boolean done = false;
    private int ticket = 0;
    private int distance;

    /**
     * @param digitsQnty количество цифр в билете
     */
    public NearestTickets(int digitsQnty) {
        if (digitsQnty <= 0 || digitsQnty % 2 != 0) {
            throw new IllegalArgumentException("Передан некорректный параметр! " + digitsQnty);
        }
        this.maxNumber = (int) (Math.pow(10, digitsQnty) - 1);
        this.digits = new int[digitsQnty];
        this.distance = this.maxNumber;
    }

    /**
     * Выполняет необходимые вычисления
     *
     * @return экземпляр {@link TicketService}
     */
    @Override
    public TicketService doWork() {
        if(done){
            throw new IllegalStateException("Уже выполнено!");
        }

        //todo реализовать средствами Java 8 (с помощью Stream API)
        IntStream
                .rangeClosed(1, maxNumber)
                .filter(this::isLucky)
                .forEach(currentTicket -> {
                    int currentDistance = currentTicket - ticket;
                    if(currentDistance >= distance) {
                        return;
                    }

                    distance = currentDistance;
                    ticket = currentTicket;
                });
        /*for(int currentTicket = 1; currentTicket <= this.maxNumber; currentTicket++){
            if(!isLucky(currentTicket)){
                continue;
            }

            int currentDistance = currentTicket - ticket;
            if(currentDistance < distance) {
                distance = currentDistance;
                ticket = currentTicket;
            }
        }*/
        done = true;
        return this;
    }

    /**
     * @param ticket номер проверяемого билета
     * @return true, если билет счастливый
     */
    private boolean isLucky(int ticket) {
        Arrays.fill(this.digits, 0);
        for (int i = 0, nextNumber = ticket; nextNumber > 0; nextNumber /= 10, i++) {
            this.digits[i] = nextNumber % 10;
        }

        int firstSum = 0, lastSum = 0;
        for (int i = 0; i < this.digits.length; i++) {
            if (i < this.digits.length / 2) {
                firstSum += this.digits[i];
                continue;
            }

            lastSum += this.digits[i];
        }

        return lastSum == firstSum;
    }

    /**
     * Выводит результат работы объекта
     */
    @Override
    public void printResult() {
        if (!done) {
            throw new IllegalStateException("Нечего выводить!");
        }

        String formattedTicket = "%0" + this.digits.length + "d\t";
        System.out.printf("Минимальное расстояние %d\t" + formattedTicket + formattedTicket,
                this.distance,
                this.ticket,
                (this.ticket - this.distance));
    }
}
