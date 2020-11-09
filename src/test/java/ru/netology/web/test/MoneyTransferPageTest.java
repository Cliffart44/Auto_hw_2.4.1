package ru.netology.web.test;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.page.LoginPage;
import ru.netology.web.page.TransferPage;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.*;
import static ru.netology.web.data.DataHelper.*;


class MoneyTransferPageTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void shouldTransferMoneyBetweenOwnCards() {
        val loginPage = new LoginPage();
        val authInfo = getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = getVerificationCodeFor(authInfo);
        val dashboard = verificationPage.validVerify(verificationCode);
        val transferPage = new TransferPage();
        transferPage.Transaction("5637", false);
        transferPage.Transaction("4126", false);
        transferPage.Transaction("1986", true);
        dashboard.defineCardsBalance();
        assertEquals(dashboard.getCard1balance(), 17_777);
        assertEquals(dashboard.getCard2balance(), 2_223);
        dashboard.justifyBalance();
        assertEquals(dashboard.getCard1balance(), dashboard.getCard2balance());
    }

    @Test
    void shouldNotTransferMoneyBetweenOwnCards() {
        val loginPage = new LoginPage();
        val authInfo = getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = getVerificationCodeFor(authInfo);
        val dashboard = verificationPage.validVerify(verificationCode);
        val transferPage = new TransferPage();
        transferPage.Transaction("50000", true);
        dashboard.defineCardsBalance();
        System.out.println("Баланс тестовой карты 1 после теста \"shouldNotTransferMoneyBetweenOwnCards\": -" + dashboard.getCard1balance() + " p.");
        System.out.println("Баланс тестовой карты 2 после теста \"shouldNotTransferMoneyBetweenOwnCards\": " + dashboard.getCard2balance() + " p.");
        transferPage.Transaction("50000", false);
    }
}