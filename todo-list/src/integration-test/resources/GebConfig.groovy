import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.htmlunit.HtmlUnitDriver


environments {

    htmlUnit {
        driver = { new HtmlUnitDriver(true) }
    }

    chrome {
        driver = { new ChromeDriver() }
    }

    firefox {
        driver = { new FirefoxDriver() }
    }
}