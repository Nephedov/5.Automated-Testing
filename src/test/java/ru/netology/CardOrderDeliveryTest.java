package ru.netology;


import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class CardOrderDeliveryTest {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy"); // Объявление нужного формата.

    LocalDate dateNow = LocalDate.now(); // Получение текущей даты.
    LocalDate dateAfter = dateNow.plusDays(3); // Добавление нужного количества дней в текущей дате.

    String currentDate = dateNow.format(formatter); // Текущая дата в формате дд.мм.гг
    String futureDate = dateAfter.format(formatter); // Текущая дата плюс три дня. В формате дд.мм.гг


    @BeforeEach
    void setUp() {
        Configuration.headless = true;
        open("http://localhost:7777/");
    }

    @Test
    void positiveRegistrationTest() {
        $("[data-test-id='city'] input").setValue("Казань");
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id='date'] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(futureDate);
        $("[data-test-id='name'] input").setValue("Петров Петр");
        $("[data-test-id='phone'] input").setValue("+71231231212");
        $(".checkbox__box").click();
        $x("//span[contains(text(),'Забронировать')]").click();
        $("[data-test-id='notification']").shouldBe(visible, Duration.ofSeconds(15));


    }

    @Test
    void invalidCityFieldTest() {
        $("[data-test-id='city'] input").setValue("Анапа");
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id='date'] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(futureDate);
        $("[data-test-id='name'] input").setValue("Петров Петр");
        $("[data-test-id='phone'] input").setValue("+71231231212");
        $(".checkbox__box").click();
        $x("//span[contains(text(), 'Забронировать')]").click();
        $(".input_invalid[data-test-id='city'] .input__sub").shouldBe(visible).shouldHave(text("Доставка в выбранный город недоступна"));
    }

    @Test
    void invalidDateFieldTest() {
        $("[data-test-id='city'] input").setValue("Казань");
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id='date'] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(currentDate);
        $("[data-test-id='name'] input").setValue("Петров Петр");
        $("[data-test-id='phone'] input").setValue("+71231231212");
        $(".checkbox__box").click();
        $x("//span[contains(text(), 'Забронировать')]").click();
        $("[data-test-id='date'] .input_invalid .input__sub").shouldBe(visible).shouldHave(text("Заказ на выбранную дату невозможен"));
    }

    @Test
    void invalidNameFieldTest() {
        $("[data-test-id='city'] input").setValue("Казань");
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id='date'] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(futureDate);
        $("[data-test-id='name'] input").setValue("Petrov Petr");
        $("[data-test-id='phone'] input").setValue("+71231231212");
        $(".checkbox__box").click();
        $x("//span[contains(text(), 'Забронировать')]").click();
        $(".input_invalid[data-test-id='name'] .input__sub").shouldBe(visible).shouldHave(text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void invalidPhoneFieldTest() {
        $("[data-test-id='city'] input").setValue("Казань");
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id='date'] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(futureDate);
        $("[data-test-id='name'] input").setValue("Петров Петр");
        $("[data-test-id='phone'] input").setValue("+7123123121");
        $(".checkbox__box").click();
        $x("//span[contains(text(), 'Забронировать')]").click();
        $(".input_invalid[data-test-id='phone'] .input__sub").shouldBe(visible).shouldHave(text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void withoutCheckBoxRegistrationTest() {
        $("[data-test-id='city'] input").setValue("Казань");
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id='date'] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(futureDate);
        $("[data-test-id='name'] input").setValue("Петров Петр");
        $("[data-test-id='phone'] input").setValue("+71231231212");
        $x("//span[contains(text(),'Забронировать')]").click();
        $(".input_invalid[data-test-id='agreement'] .checkbox__text").shouldBe(visible).shouldHave(text("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
    }

    @Test
    void nullCityFieldTest() {
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id='date'] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(futureDate);
        $("[data-test-id='name'] input").setValue("Петров Петр");
        $("[data-test-id='phone'] input").setValue("+71231231212");
        $(".checkbox__box").click();
        $x("//span[contains(text(), 'Забронировать')]").click();
        $(".input_invalid[data-test-id='city'] .input__sub").shouldBe(visible).shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    void nullDateFieldTest() {
        $("[data-test-id='city'] input").setValue("Казань");
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id='date'] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='name'] input").setValue("Петров Петр");
        $("[data-test-id='phone'] input").setValue("+71231231212");
        $(".checkbox__box").click();
        $x("//span[contains(text(), 'Забронировать')]").click();
        $("[data-test-id='date'] .input_invalid .input__sub").shouldBe(visible).shouldHave(text("Неверно введена дата"));
    }

    @Test
    void nullNameFieldTest() {
        $("[data-test-id='city'] input").setValue("Казань");
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id='date'] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(futureDate);
        $("[data-test-id='phone'] input").setValue("+71231231212");
        $(".checkbox__box").click();
        $x("//span[contains(text(), 'Забронировать')]").click();
        $(".input_invalid[data-test-id='name'] .input__sub").shouldBe(visible).shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    void nullPhoneFieldTest() {
        $("[data-test-id='city'] input").setValue("Казань");
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id='date'] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(futureDate);
        $("[data-test-id='name'] input").setValue("Петров Петр");
        $(".checkbox__box").click();
        $x("//span[contains(text(), 'Забронировать')]").click();
        $(".input_invalid[data-test-id='phone'] .input__sub").shouldBe(visible).shouldHave(text("Поле обязательно для заполнения"));
    }
}

