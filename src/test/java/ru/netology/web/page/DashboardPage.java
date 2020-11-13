package ru.netology.web.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.google.common.base.CharMatcher;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class DashboardPage {
    private String dataTestId;
    private SelenideElement heading = $(".heading_size_xl");
    private SelenideElement addFundsCard1Button = $("[data-test-id='92df3f1c-a033-48e6-8390-206f6b1f56c0'] .button__text");
    private SelenideElement addFundsCard2Button = $("[data-test-id='0f3f5c2a-249e-4c3d-8287-09f7a039391d'] .button__text");

    public DashboardPage() {
        heading.shouldBe(visible).shouldHave(text("Ваши карты"));
    }

    private void defineCard(String number) {
        ElementsCollection cards = $$("li div");
        int required = Integer.parseInt(CharMatcher.inRange('0', '9').retainFrom(number.substring(15, 19)));
        for (SelenideElement card : cards) {
            int found = Integer.parseInt(CharMatcher.inRange('0', '9').retainFrom(card.getText().substring(15, 19)));
            if (found == required) dataTestId = card.getAttribute("data-test-id");
        }
    }

    public TransferPage moneyTransfer(String targetCardNumber) {
        defineCard(targetCardNumber);
        $("[data-test-id='" + dataTestId + "'] .button__text").click();
        return new TransferPage();
    }
}