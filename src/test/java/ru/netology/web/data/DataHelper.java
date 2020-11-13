package ru.netology.web.data;

import com.codeborne.selenide.SelenideElement;
import com.google.common.base.CharMatcher;
import lombok.Value;
import ru.netology.web.page.TransferPage;
import org.jetbrains.annotations.NotNull;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

public class DataHelper {
    private DataHelper() {
    }

    @Value
    public static class AuthInfo {
        String login;
        String password;
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static AuthInfo getOtherAuthInfo(AuthInfo original) {
        return new AuthInfo("petya", "123qwerty");
    }

    public static String cardNumber(int item) {
        String[] cards = {" ", "5559 0000 0000 0001", "5559 0000 0000 0002"};
        if (item >= 1 && item <= 2) {
            return cards[item];
        }
        return null;
    }

    @Value
    public static class VerificationCode {
        String code;
    }

    public static VerificationCode getVerificationCodeFor(AuthInfo authInfo) {
        return new VerificationCode("12345");
    }

    @Value
    public static class Balance {
        private static SelenideElement heading = $(".heading_size_xl");
        private static SelenideElement addFundsCard1Button = $("[data-test-id='92df3f1c-a033-48e6-8390-206f6b1f56c0'] .button__text");
        private static SelenideElement addFundsCard2Button = $("[data-test-id='0f3f5c2a-249e-4c3d-8287-09f7a039391d'] .button__text");

        @NotNull
        public static int[] cardsBalance() {
            heading.shouldBe(visible).shouldHave(text("Ваши карты"));
            int[] result = new int[3];
            String tmp = $("[data-test-id='92df3f1c-a033-48e6-8390-206f6b1f56c0']").getOwnText().substring(20);
            result[1] = Integer.parseInt(CharMatcher.inRange('0', '9').retainFrom(tmp));
            String tmp2 = $("[data-test-id='0f3f5c2a-249e-4c3d-8287-09f7a039391d']").getOwnText().substring(20);
            result[2] = Integer.parseInt(CharMatcher.inRange('0', '9').retainFrom(tmp2));
            return result;
        }

        @NotNull
        public static int[] justifyBalance(int cardOne, int cardTwo) {
            heading.shouldBe(visible).shouldHave(text("Ваши карты"));
            int inequality = cardOne - cardTwo;
            int justifyValue = inequality / 2;
            if (inequality == 0) return cardsBalance();
            else if (inequality > 0) {
                addFundsCard2Button.click();
                new TransferPage().transaction(Integer.toString(justifyValue), cardNumber(1));
                return cardsBalance();
            }
            if (inequality < 0) {
                addFundsCard1Button.click();
                new TransferPage().transaction(Integer.toString(justifyValue * (-1)), cardNumber(2));
                return cardsBalance();
            }
            return cardsBalance();
        }
    }
}