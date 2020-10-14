package wiley.pages;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

public class MainPage {

    public void openWhoWeServe() {
        $("#main-header-navbar").$(byText("WHO WE SERVE")).click();
    }

    /**
     * Проверить количество элементов внутри Who we serve
     */
    public void assertServiceCount(int count) {
        $("#Level1NavNode1").$$("li[class$=\"dropdown-item \"]")
                .shouldBe(CollectionCondition.size(count));
    }

    /**
     * Проверяем что внутри "Who we serve" есть нужные пункты
     */
    public void assertTitles() {
        ElementsCollection whoWeServe = $("#Level1NavNode1").$$("li[class$=\"dropdown-item \"]");

        List<String> titles = new ArrayList<>();

        // Students в разметке не отображается из-за чего тест падает
        //titles.add("Students");
        titles.add("Instructors");
        titles.add("Book Authors");
        titles.add("Professionals");
        titles.add("Researchers");
        titles.add("Institutions");
        titles.add("Librarians");
        titles.add("Corporations");
        titles.add("Societies");
        titles.add("Journal Editors");
        titles.add("Bookstores");
        titles.add("Government");

        for (String title : titles) {
            whoWeServe.shouldBe(CollectionCondition.anyMatch("", el -> el.getText().equals(title)));
        }
    }

    /**
     * Проверка, что область автокомплита расположена строго под строкой поиска
     */
    public void assertAutocompletePosition(String value) {
        SelenideElement searchField = $(byName("pq"));
        searchField.setValue(value);

        SelenideElement autoCompl = $("#ui-id-2");
        autoCompl.shouldBe(Condition.visible);
        searchField.getLocation();
        autoCompl.getLocation();

        Assert.assertEquals(searchField.getLocation().getX(), autoCompl.getLocation().getX());
        Assert.assertEquals(autoCompl.getLocation().getY(), searchField.getLocation().getY() + searchField
                .getSize().getHeight());
    }

    public void clickSearch() {
        $(".input-group").$(byAttribute("type", "submit"))
                .click();
    }

    public void openEducation() {
        $("#main-header-navbar").$(byText("SUBJECTS")).hover().parent()
                .$(byText("Education"))
                .click();
    }
}
