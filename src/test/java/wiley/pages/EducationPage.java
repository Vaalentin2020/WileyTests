package wiley.pages;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class EducationPage {

    public void assertEduHeader() {
        $x("//h4[text()=\"Subjects\"]").shouldBe(Condition.visible);
    }

    public void assertSidePanPosition() {
        $(".side-panel")
                .shouldBe(Condition.visible)
                .shouldHave(Condition.cssValue("float", "left"));
    }

    /**
     * Получаем боковую панель, получаем коллекцию ее дочерних эл-ов, проверяем, что первый это header и после него
     * идут items всех subjects, которых 13 шт.
     */
    public void assertThatSidePanUnderHeader() {
        ElementsCollection sidePanel = $$(".side-panel>*");
        sidePanel.get(0).$(byText("Subjects")).shouldBe(Condition.exist);
        sidePanel.get(1).$$("ul li").shouldBe(CollectionCondition.size(13));
    }

    public void assertSubjectsTitle() {
        ElementsCollection subjects = $(".side-panel").$$("ul li");

        List<String> titles = new ArrayList<>();
        titles.add("Information & Library Science");
        titles.add("Education & Public Policy");
        titles.add("K-12 General");
        titles.add("Higher Education General");
        titles.add("Vocational Technology");
        titles.add("Conflict Resolution & Mediation (School settings)");
        titles.add("Curriculum Tools- General");
        titles.add("Special Educational Needs");
        titles.add("Theory of Education");
        titles.add("Education Special Topics");
        titles.add("Educational Research & Statistics");
        titles.add("Literacy & Reading");
        titles.add("Classroom Management");

        for (String title : titles) {
            subjects.shouldBe(CollectionCondition.anyMatch("", el -> el.getText().equals(title)));
        }
    }
}
