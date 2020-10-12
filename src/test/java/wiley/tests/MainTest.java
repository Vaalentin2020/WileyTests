package wiley.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import wiley.TestBase;
import wiley.pages.MainPage;

public class MainTest extends TestBase {

    MainPage mPage;

    @BeforeClass
    void initialize() {
        mPage = new MainPage();
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

    @Test(description = "Проверяем, что окно автокомплита расположено строго под строкой поиска",dependsOnMethods = "login")
    public void checkSearchingPosition() {
        mPage.assertAutocompletePosition("Java");
    }

    @Test(description = "Проверяем функцию работы поиска", dependsOnMethods = "checkSearchingPosition")
    public void searchFuncCheck() {

    }
}
