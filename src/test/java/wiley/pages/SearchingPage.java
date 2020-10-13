package wiley.pages;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.byAttribute;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class SearchingPage {

    public void assertResultCount(int count) {
        ElementsCollection srchResult = getTitles();
        srchResult.shouldBe(CollectionCondition.size(count));
    }

    public void assertTitleSubstring(String substring) {
        ElementsCollection srchResult = getTitles();
        srchResult.shouldBe(CollectionCondition.allMatch("", el -> el.getText().contains(substring)));
        for (SelenideElement el : srchResult) {
            el.shouldBe(Condition.visible);
        }
    }

    public ElementsCollection getTitles() {
        return $(".products-list").$$(".product-title");
    }

    public void assertAddToCartBtn() {
        ElementsCollection table = $$("#productTableBodySection");

        for (SelenideElement el : table) {
            el.scrollIntoView(false);
            ElementsCollection versionBooks = el.$$(".productButtonGroupName");
            int countVersion = versionBooks.size();

            for (int i = 0; i < countVersion; i++) {
                versionBooks.get(i).click();

                if (versionBooks.get(i).getText().equals("PRINT") || versionBooks.get(i).getText().equals("E-BOOK")) {
                    $x("//div[contains(@id, '[0-9]+_Print')]");
                    el.$(byText("Add to cart")).shouldBe(Condition.visible);
                } else if (versionBooks.get(i).getText().equals("O-BOOK")) {
                    el.$(byText("View on Wiley Online Library")).shouldBe(Condition.visible);
                }
            }
        }
    }
}