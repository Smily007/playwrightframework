package com.base;

import com.microsoft.playwright.*;

import java.io.File;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.testng.annotations.*;

public class Baseclass {
	protected Playwright playwright;
	protected Browser browser;
	protected BrowserContext context;
	protected Page page;

	public void setup(String browserType) {
		playwright = Playwright.create();

		switch (browserType.toLowerCase()) {
		case "chrome":
			browser = playwright.chromium()
					.launch(new BrowserType.LaunchOptions().setChannel("chrome").setHeadless(false));
			break;

		case "chromium":
			browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
			break;

		case "firefox":
			browser = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(false));
			break;

		case "webkit":
			browser = playwright.webkit().launch(new BrowserType.LaunchOptions().setHeadless(false));
			break;

		default:
			throw new IllegalArgumentException("Unsupported browser type: " + browserType);
		}

		context = browser.newContext();
		page = context.newPage();
	}

	public void navigate(String url) {
		page.navigate(url);
	}

	public static String captureScreen(Page page, String tname) {
        // Generate timestamp
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

        // Create dynamic screenshot file path
        String screenshotDir = System.getProperty("user.dir") + "/screenshots/";
        System.out.println(screenshotDir);

        String destination = screenshotDir + tname + "_" + timeStamp + ".png";

        // Ensure the directory exists
        File directory = new File(screenshotDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        try {
            // Take the screenshot using Playwright
            page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(destination)));
        } catch (Exception e) {
            System.out.println("Error while capturing screenshot: " + e.getMessage());
        }

        return destination;
	}

	public static void printEachItemText(List<ElementHandle> elementHandles, String locatorString) {

		// For-each loop over the element handles
		for (ElementHandle element : elementHandles) {
			String text = element.textContent();
			System.out.println("Item text: " + text);
		}
	}

	@AfterSuite
	public void teardown() {
		browser.close();
		playwright.close();
	}
}
