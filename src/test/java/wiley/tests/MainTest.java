package wiley.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import wiley.TestBase;
import wiley.pages.EducationPage;
import wiley.pages.MainPage;
import wiley.pages.SearchingPage;

public class MainTest extends TestBase {

    MainPage mPage;
    SearchingPage sPage;
    EducationPage ePage;

    @BeforeClass
    void initialize() {
        mPage = new MainPage();
        sPage = new SearchingPage();
        ePage = new EducationPage();
    }

    @Test(description = "Открытие главной страницы")
    public void login() {
        Selenide.open(Configuration.baseUrl);
    }

    @Test(description = "Проверяем содердимое выпадающего меню Who We Serve", dependsOnMethods = "login")
    public void checkCountItems() {
        mPage.openWhoWeServe();
        mPage.assertServiceCount(12);
        mPage.assertTitles();
    }

    @Test(description = "Проверяем, что окно автокомплита расположено строго под строкой поиска",
          dependsOnMethods = "checkCountItems")
    public void checkSearchingPosition() {
        mPage.assertAutocompletePosition("Java");
    }

    @Test(description = "Проверяем функцию работы поиска, что результатов на странице 10, что все они отображаются",
          dependsOnMethods = "checkSearchingPosition")
    public void searchFuncCheck() {
        mPage.clickSearch();
        sPage.assertResultCount(10);
        sPage.assertTitleSubstring("Java");
        sPage.assertAddToCartBtn();
    }

    @Test(description = "Переход в образование, проверка отображаемых разделов", dependsOnMethods = "searchFuncCheck")
    public void checkSubjects() {
        mPage.openEducation();
        ePage.assertEduHeader();
        ePage.assertSidePanPosition();
        ePage.assertThatSidePanUnderHeader();
        ePage.assertSubjectsTitle();
    }
}
