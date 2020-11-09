package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;
import com.google.common.base.CharMatcher;
import lombok.Getter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

@Getter
public class DashboardPage {
    private int card1balance;
    private int card2balance;
    private SelenideElement heading = $(".heading_size_xl");

    public DashboardPage() {
        heading.shouldBe(visible).shouldHave(text("Ваши карты"));
    }

    public void justifyBalance() {
        defineCardsBalance();
        int inequality = getCard1balance() - getCard2balance();
        int justifyValue = inequality / 2;
        if (inequality == 0) return;
        else if (inequality > 0) new TransferPage().Transaction(Integer.toString(justifyValue), true);
        if (inequality < 0) new TransferPage().Transaction(Integer.toString(justifyValue * (-1)), false);
        defineCardsBalance();
    }

    public void defineCardsBalance() {
        heading.shouldBe(visible).shouldHave(text("Ваши карты"));
        String tmp1 = $("[data-test-id='92df3f1c-a033-48e6-8390-206f6b1f56c0']").getOwnText().substring(20);
        card1balance = Integer.parseInt(CharMatcher.inRange('0', '9').retainFrom(tmp1));
        String tmp2 = $("[data-test-id='0f3f5c2a-249e-4c3d-8287-09f7a039391d']").getOwnText().substring(20);
        card2balance = Integer.parseInt(CharMatcher.inRange('0', '9').retainFrom(tmp2));
    }
}