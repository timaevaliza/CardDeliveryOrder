import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class CardDeliveryOrderTest {

    public  String generateDate(int days, String pattern){
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test

    void shouldSuccessfullyOrderCardDelivery() {
        String planningDate = generateDate(3, "dd.MM.yyyy");

        Selenide.open("http://localhost:9999");
        SelenideElement form = $$("form").find(Condition.visible);

        form.$("[data-test-id='city'] input").setValue("Барнаул");
        form.$("[data-test-id='date'] input")
                .press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE)
                .setValue(planningDate);;
        form.$("[data-test-id='name'] input").setValue("Иванов Иван");
        form.$("[data-test-id='phone'] input").setValue("+79513451234");
        form.$("[data-test-id='agreement']").click();
        form.$("button.button").click();
        $("[data-test-id='notification']")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate));
    }

}
