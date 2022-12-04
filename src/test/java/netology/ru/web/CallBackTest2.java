package netology.ru.web;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static java.time.Duration.ofMillis;
import static java.time.Duration.ofSeconds;
import static java.time.format.DateTimeFormatter.ofPattern;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class CallBackTest2 {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void shouldSendValidFormUsingComplexElements() {
        $("[data-test-id='city'] input").setValue("Ка");
        $(".input__menu").find(withText("Калуга")).click();
        SelenideElement dateField = $("[placeholder='Дата встречи']");
        dateField.sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        LocalDate currentDate = LocalDate.now();
        LocalDate dateOfMeeting = LocalDate.now().plusDays(7);
        if (currentDate.getMonthValue() != dateOfMeeting.getMonthValue()) {
            $(".calendar__arrow_direction_right[data-step='1']").click();
        }
        $$("td.calendar__day").find(exactText(String.valueOf(dateOfMeeting.getDayOfMonth()))).click();
        $("[data-test-id='name'] input").setValue("Василий Васильев");
        $("[data-test-id='phone'] input").setValue("+12345678999");
        $("[data-test-id=agreement] .checkbox__box").click();
        $(withText("Забронировать")).click();
        String dateOfMeetingFormatted = dateOfMeeting.format(ofPattern("dd.MM.yyyy"));
        $("[data-test-id=notification] .notification__content").shouldBe(visible, ofSeconds(15))
                .shouldHave(exactText("Встреча успешно забронирована на " + dateOfMeetingFormatted));
    }
}