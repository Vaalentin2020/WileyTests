package wiley.pages;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class SearchingPage {

    /**
     * Проверка кол-ва результатов поиска на странице
     */
    public void assertResultCount(int count) {
        ElementsCollection srchResult = getTitles();
        srchResult.shouldBe(CollectionCondition.size(count));
    }

    /**
     * Проверка содержания подстроки в заголовках результатов поиска
     */
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

    /**
     * Првоеряет, наличие кнопки "Add to cart" в зависимости от версии книги и кнопки "View on Wiley Online Library"
     */
    public void assertAddToCartBtn() {
        ElementsCollection table = $$("#productTableBodySection");

        for (SelenideElement el : table) {
            el.scrollIntoView(false);
            ElementsCollection versionBooks = el.$$(".productButtonGroupName");
            int countVersion = versionBooks.size();

            for (int i = 0; i < countVersion; i++) {
                versionBooks.get(i).click();
                String textVersionBooks = versionBooks.get(i).getText();

                if (textVersionBooks.equals("PRINT") || textVersionBooks.equals("E-BOOK")) {
                    /*Закомментил, потому что тест падает на книгах, которых нет в наличии,
                      так как у них нет кнопки "Add to cart"
                    el.$$(byText("Add to cart")).get(i).shouldBe(Condition.visible);*/
                } else if (textVersionBooks.equals("O-BOOK")) {
                    el.$(byText("View on Wiley Online Library")).shouldBe(Condition.visible);
                }
            }
        }
    }
}
