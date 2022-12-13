package ru.netology;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class generateDate {
    public String generateDate() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

}
