package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    private SelenideElement addFundsCard1Button = $("[data-test-id='92df3f1c-a033-48e6-8390-206f6b1f56c0'] .button__text");
    private SelenideElement addFundsCard2Button = $("[data-test-id='0f3f5c2a-249e-4c3d-8287-09f7a039391d'] .button__text");
    private SelenideElement totField = $(".money-input .input__control");
    private SelenideElement sourceCardField = $("[data-test-id='from'] .input__control");
    private SelenideElement addFundsButton = $("[data-test-id='action-transfer'] .button__text");
    private SelenideElement cancelButton = $("[data-test-id='action-cancel'] .button__text");

    public void Transaction(String value, boolean sourceOne) {
        if (sourceOne) {
            addFundsCard2Button.click();
            totField.sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE, value);
            sourceCardField.sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE, "5559000000000001");
        } else {
            addFundsCard1Button.click();
            totField.sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE, value);
            sourceCardField.sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE, "5559000000000002");
        }
        addFundsButton.click();
    }
}