package ru.netology.selenide;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;


import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;


public class CardDeliveryTest {

    public String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public String getExpectedDate() {
        return $x(".//span[@data-test-id='date']//child::input[@placeholder]").getValue();
    }

    @BeforeEach
    void openBrowser() {
        open("http://localhost:9999/");
    }

    @Test
    void shouldSendForm() {
        String planningDate = generateDate(6);

        $("[data-test-id='city'] input").setValue("Москва");
        $x("//input[@placeholder='Дата встречи']").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $x("//input[@placeholder='Дата встречи']").setValue(planningDate);
        $x("//input[@name='name']").setValue("Иванов Иван");
        $x("//input[@name='phone']").setValue("+79001234567");
        $("[data-test-id='agreement']").click();
        $x("//span[@class='button__text']").click();
        $x("//*[contains(text(),'Успешно!')]").shouldBe(Condition.visible, Duration.ofSeconds(15));
        $(".notification__content").shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(15)).shouldBe(Condition.visible);
    }

    @Test
    void shouldSendFormIfDateDefault() {
        String planningDate = getExpectedDate();

        $("[data-test-id='city'] input").setValue("Москва");
        $x("//input[@placeholder='Дата встречи']").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $x("//input[@placeholder='Дата встречи']").setValue(planningDate);
        $x("//input[@name='name']").setValue("Иванов Иван");
        $x("//input[@name='phone']").setValue("+79001234567");
        $("[data-test-id='agreement']").click();
        $x("//span[@class='button__text']").click();
        $x("//*[contains(text(),'Успешно!')]").shouldBe(Condition.visible, Duration.ofSeconds(15));
        $(".notification__content").shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(15)).shouldBe(Condition.visible);
    }

    @Test
    void shouldSendFormIfNameCityWithYo() {
        String planningDate = generateDate(3);

        $("[data-test-id='city'] input").setValue("Орёл");
        $x("//input[@placeholder='Дата встречи']").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $x("//input[@placeholder='Дата встречи']").setValue(planningDate);
        $x("//input[@name='name']").setValue("Иванов Иван");
        $x("//input[@name='phone']").setValue("+79001234567");
        $("[data-test-id='agreement']").click();
        $x("//span[@class='button__text']").click();
        $x("//*[contains(text(),'Успешно!')]").shouldBe(Condition.visible, Duration.ofSeconds(15));
        $(".notification__content").shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(15)).shouldBe(Condition.visible);
    }

    @Test
    void shouldMessageIfInvalidCityTest1() {
        String planningDate = generateDate(3);

        $("[data-test-id='city'] input").setValue("Moscow");
        $x("//input[@placeholder='Дата встречи']").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $x("//input[@placeholder='Дата встречи']").setValue(planningDate);
        $x("//input[@name='name']").setValue("Иванов Иван");
        $x("//input[@name='phone']").setValue("+79001234567");
        $("[data-test-id='agreement']").click();
        $x("//span[@class='button__text']").click();
        $x(".//span[@data-test-id='city']//child::span[@class='input__sub']").
                should(visible, text("Доставка в выбранный город недоступна"));
    }

    @Test
    void shouldMessageIfInvalidCityTest2() {
        String planningDate = generateDate(3);

        $("[data-test-id='city'] input").setValue("Тольятти");
        $x("//input[@placeholder='Дата встречи']").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $x("//input[@placeholder='Дата встречи']").setValue(planningDate);
        $x("//input[@name='name']").setValue("Иванов Иван");
        $x("//input[@name='phone']").setValue("+79001234567");
        $("[data-test-id='agreement']").click();
        $x("//span[@class='button__text']").click();
        $x(".//span[@data-test-id='city']//child::span[@class='input__sub']").
                should(visible, text("Доставка в выбранный город недоступна"));
    }
    @Test
    void shouldMessageIfInvalidDateTest1() {
        String planningDate = generateDate(1);

        $("[data-test-id='city'] input").setValue("Москва");
        $x("//input[@placeholder='Дата встречи']").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $x("//input[@placeholder='Дата встречи']").setValue(planningDate);
        $x("//input[@name='name']").setValue("Иванов Иван");
        $x("//input[@name='phone']").setValue("+79001234567");
        $("[data-test-id='agreement']").click();
        $x("//span[@class='button__text']").click();
        $x(".//span[@data-test-id='date']//child::span[@class='input__sub']").
                should(visible, text("Заказ на выбранную дату невозможен"));
    }

    @Test
    void shouldMessageIfInvalidDateTest2() {
        String planningDate = generateDate(0);

        $("[data-test-id='city'] input").setValue("Москва");
        $x("//input[@placeholder='Дата встречи']").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $x("//input[@placeholder='Дата встречи']").setValue(planningDate);
        $x("//input[@name='name']").setValue("Иванов Иван");
        $x("//input[@name='phone']").setValue("+79001234567");
        $("[data-test-id='agreement']").click();
        $x("//span[@class='button__text']").click();
        $x(".//span[@data-test-id='date']//child::span[@class='input__sub']").
                should(visible, text("Заказ на выбранную дату невозможен"));
    }

    @Test
    void shouldMessageIfInvalidDateTest3() {
        String planningDate = generateDate(-1);

        $("[data-test-id='city'] input").setValue("Москва");
        $x("//input[@placeholder='Дата встречи']").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $x("//input[@placeholder='Дата встречи']").setValue(planningDate);
        $x("//input[@name='name']").setValue("Иванов Иван");
        $x("//input[@name='phone']").setValue("+79001234567");
        $("[data-test-id='agreement']").click();
        $x("//span[@class='button__text']").click();
        $x(".//span[@data-test-id='date']//child::span[@class='input__sub']").
                should(visible, text("Заказ на выбранную дату невозможен"));
    }

    @Test
    void shouldSendFormWithDoubleLastName() {
        String planningDate = generateDate(3);

        $("[data-test-id='city'] input").setValue("Москва");
        $x("//input[@placeholder='Дата встречи']").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $x("//input[@placeholder='Дата встречи']").setValue(planningDate);
        $x("//input[@name='name']").setValue("Иванов-Петров Иван");
        $x("//input[@name='phone']").setValue("+79001234567");
        $("[data-test-id='agreement']").click();
        $x("//span[@class='button__text']").click();
        $x("//*[contains(text(),'Успешно!')]").shouldBe(Condition.visible, Duration.ofSeconds(15));
        $(".notification__content").shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(15)).shouldBe(Condition.visible);
    }
//    bug
    @Test
   void shouldSendFormWithoutLastName() {
       String planningDate = generateDate(3);

        $("[data-test-id='city'] input").setValue("Москва");
        $x("//input[@placeholder='Дата встречи']").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $x("//input[@placeholder='Дата встречи']").setValue(planningDate);
        $x("//input[@name='name']").setValue("Иван");
        $x("//input[@name='phone']").setValue("+79001234567");
        $("[data-test-id='agreement']").click();
        $x("//span[@class='button__text']").click();
        $x(".//span[@data-test-id='name']//child::span[@class='input__sub']").
                should(visible, text("Имя и Фамилия указанны неверно"));
    }
//    bug
    @Test
    void shouldSendFormIfNameAndLastNameWithYo() {
        String planningDate = generateDate(3);

        $("[data-test-id='city'] input").setValue("Москва");
        $x("//input[@placeholder='Дата встречи']").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $x("//input[@placeholder='Дата встречи']").setValue(planningDate);
        $x("//input[@name='name']").setValue("Ерёмин Артём");
        $x("//input[@name='phone']").setValue("+79001234567");
        $("[data-test-id='agreement']").click();
        $x("//span[@class='button__text']").click();
        $x("//*[contains(text(),'Успешно!')]").shouldBe(Condition.visible, Duration.ofSeconds(15));
        $(".notification__content").shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(15)).shouldBe(Condition.visible);
    }

    @Test
    void shouldSendFormIfNameInEnglish() {
        String planningDate = generateDate(3);

        $("[data-test-id='city'] input").setValue("Москва");
        $x("//input[@placeholder='Дата встречи']").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $x("//input[@placeholder='Дата встречи']").setValue(planningDate);
        $x("//input[@name='name']").setValue("Ivanov Ivan");
        $x("//input[@name='phone']").setValue("+79001234567");
        $("[data-test-id='agreement']").click();
        $x("//span[@class='button__text']").click();
        $x(".//span[@data-test-id='name']//child::span[@class='input__sub']").
                should(visible, text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldSendFormIfNameWithSymbol() {
        String planningDate = generateDate(3);

        $("[data-test-id='city'] input").setValue("Москва");
        $x("//input[@placeholder='Дата встречи']").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $x("//input[@placeholder='Дата встречи']").setValue(planningDate);
        $x("//input[@name='name']").setValue("Иванов@ Иван");
        $x("//input[@name='phone']").setValue("+79001234567");
        $("[data-test-id='agreement']").click();
        $x("//span[@class='button__text']").click();
        $x(".//span[@data-test-id='name']//child::span[@class='input__sub']").
                should(visible, text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldSendFormIfPhoneWithoutOneFigure() {
        String planningDate = generateDate(3);

        $("[data-test-id='city'] input").setValue("Москва");
        $x("//input[@placeholder='Дата встречи']").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $x("//input[@placeholder='Дата встречи']").setValue(planningDate);
        $x("//input[@name='name']").setValue("Иванов Иван");
        $x("//input[@name='phone']").setValue("+7900123456");
        $("[data-test-id='agreement']").click();
        $x("//span[@class='button__text']").click();
        $x(".//span[@data-test-id='phone']//child::span[@class='input__sub']").
                should(visible, text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldSendFormIfPhoneWithFigureOneMore() {
        String planningDate = generateDate(3);

        $("[data-test-id='city'] input").setValue("Москва");
        $x("//input[@placeholder='Дата встречи']").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $x("//input[@placeholder='Дата встречи']").setValue(planningDate);
        $x("//input[@name='name']").setValue("Иванов Иван");
        $x("//input[@name='phone']").setValue("+790012345678");
        $("[data-test-id='agreement']").click();
        $x("//span[@class='button__text']").click();
        $x(".//span[@data-test-id='phone']//child::span[@class='input__sub']").
                should(visible, text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldSendFormIfPhoneAnotherFormat() {
        String planningDate = generateDate(3);

        $("[data-test-id='city'] input").setValue("Москва");
        $x("//input[@placeholder='Дата встречи']").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $x("//input[@placeholder='Дата встречи']").setValue(planningDate);
        $x("//input[@name='name']").setValue("Иванов Иван");
        $x("//input[@name='phone']").setValue("89001234567");
        $("[data-test-id='agreement']").click();
        $x("//span[@class='button__text']").click();
        $x(".//span[@data-test-id='phone']//child::span[@class='input__sub']").
                should(visible, text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldSendFormIfPhoneWithSymbol() {
        String planningDate = generateDate(3);

        $("[data-test-id='city'] input").setValue("Москва");
        $x("//input[@placeholder='Дата встречи']").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $x("//input[@placeholder='Дата встречи']").setValue(planningDate);
        $x("//input[@name='name']").setValue("Иванов Иван");
        $x("//input[@name='phone']").setValue("+79001234567@");
        $("[data-test-id='agreement']").click();
        $x("//span[@class='button__text']").click();
        $x(".//span[@data-test-id='phone']//child::span[@class='input__sub']").
                should(visible, text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldSendFormIfEmptyCity() {
        String planningDate = generateDate(3);

        $x("//input[@placeholder='Дата встречи']").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $x("//input[@placeholder='Дата встречи']").setValue(planningDate);
        $x("//input[@name='name']").setValue("Иванов Иван");
        $x("//input[@name='phone']").setValue("+79001234567");
        $("[data-test-id='agreement']").click();
        $x("//span[@class='button__text']").click();
        $x(".//span[@data-test-id='city']//child::span[@class='input__sub']").
                should(visible, text("Поле обязательно для заполнения"));
    }

    @Test
    void shouldSendFormIfEmptyName() {
        String planningDate = generateDate(3);

        $("[data-test-id='city'] input").setValue("Москва");
        $x("//input[@placeholder='Дата встречи']").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $x("//input[@placeholder='Дата встречи']").setValue(planningDate);
        $x("//input[@name='phone']").setValue("+79001234567");
        $("[data-test-id='agreement']").click();
        $x("//span[@class='button__text']").click();
        $x(".//span[@data-test-id='name']//child::span[@class='input__sub']").
                should(visible, text("Поле обязательно для заполнения"));
    }

    @Test
    void shouldSendFormIfEmptyPhone() {
        String planningDate = generateDate(3);

        $("[data-test-id='city'] input").setValue("Москва");
        $x("//input[@placeholder='Дата встречи']").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $x("//input[@placeholder='Дата встречи']").setValue(planningDate);
        $x("//input[@name='name']").setValue("Иванов Иван");
        $("[data-test-id='agreement']").click();
        $x("//span[@class='button__text']").click();
        $x(".//span[@data-test-id='phone']//child::span[@class='input__sub']").
                should(visible, text("Поле обязательно для заполнения"));
    }

    @Test
    void shouldSendFormIfCheckboxEmpty() {
        String planningDate = generateDate(3);

        $("[data-test-id='city'] input").setValue("Москва");
        $x("//input[@placeholder='Дата встречи']").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $x("//input[@placeholder='Дата встречи']").setValue(planningDate);
        $x("//input[@name='name']").setValue("Иванов Иван");
        $x("//input[@name='phone']").setValue("+79001234567");
        $x("//span[@class='button__text']").click();
        $x(".//label[@data-test-id='agreement']").
                should(cssClass("input_invalid"));
    }

//    second task

    @Test
    void shouldSendFormWithChoiceCityAndDate() {

        String validDate = generateDate(7);
        String calendarDate = String.valueOf(LocalDate.now().plusDays(7).getDayOfMonth());
        String planningDate = String.valueOf(LocalDate.now().plusDays(7).getMonth());
        String deliveryDate = String.valueOf(LocalDate.now().plusDays(3).getMonth());

        $("[data-test-id='city'] input").setValue("Ка");
        $$x("//span[@class='menu-item__control']").get(1).click();
        $x("//span[@class='input__icon']").click();

        if (!Objects.equals(planningDate, deliveryDate)) {
            $("[data-step='1']").click();
        }

        $$("table.calendar__layout td").find(text(calendarDate)).click();
        $x("//input[@name='name']").setValue("Иванов Иван");
        $x("//input[@name='phone']").setValue("+79001234567");
        $("[data-test-id='agreement']").click();
        $x("//span[@class='button__text']").click();
        $x("//*[contains(text(),'Успешно!')]").shouldBe(visible, Duration.ofSeconds(15));
        $(".notification__content").shouldHave(Condition.text("Встреча успешно забронирована на " + validDate), Duration.ofSeconds(15)).shouldBe(Condition.visible);
    }
}
