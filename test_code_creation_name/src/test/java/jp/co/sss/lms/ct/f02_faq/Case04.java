package jp.co.sss.lms.ct.f02_faq;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * 結合テスト よくある質問機能
 * ケース04
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース04 よくある質問画面への遷移")
public class Case04 {

	/** 前処理 */
	@BeforeAll
	static void before() {
		createDriver();
	}

	/** 後処理 */
	@AfterAll
	static void after() {
		closeDriver();
	}

	@Test
	@Order(1)
	@DisplayName("テスト01 トップページURLでアクセス")
	void test01() throws Exception {
		// 指定のURLの画面を開く
		webDriver.get("http://localhost:8080/lms");
		// 開いたページのキャプチャを取得する
		File index_access_success = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(index_access_success, new File("evidence/Case01/index_access_success.png"));
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() throws Exception {
		// ユーザID・パスワード入力欄にDBに登録されていないユーザーを入力し、ログインを実行
		webDriver.findElement(By.id("loginId")).sendKeys("StudentAA01");
		webDriver.findElement(By.id("password")).sendKeys("ItTest2023");
		webDriver.findElement(By.cssSelector(".btn.btn-primary")).click();

		// ログイン成功による画面遷移が反映されるまで待機
		WebDriverWait loginWait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
		loginWait.until(ExpectedConditions.urlContains("/detail"));

		// ログイン実行結果のキャプチャを取得する
		File login_success = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(login_success, new File("evidence/Case03/login_success.png"));
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 上部メニューの「ヘルプ」リンクからヘルプ画面に遷移")
	void test03() throws Exception {
		webDriver.findElement(By.className("dropdown-toggle")).click();
		webDriver.findElement(By.linkText("ヘルプ")).click();

		// リンク押下による画面遷移が反映されるまで待機
		WebDriverWait helpWait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
		helpWait.until(ExpectedConditions.urlContains("/help"));

		// リンク押下実行結果のキャプチャを取得する
		File help_select = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(help_select, new File("evidence/Case04/help_select.png"));
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「よくある質問」リンクからよくある質問画面を別タブに開く")
	void test04() throws Exception {
		// 現在のウィンドウを保持
		String currentWindow = webDriver.getWindowHandle();

		webDriver.findElement(By.linkText("よくある質問")).click();

		// リンク押下によるタブが開くまで待機 (タブが2以上になる)
		WebDriverWait faqWait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
		faqWait.until(driver -> driver.getWindowHandles().size() > 1);
		// 新しく開いたタブに切り替え
		for (String windowHandle : webDriver.getWindowHandles()) {
			if (!windowHandle.equals(currentWindow)) {
				webDriver.switchTo().window(windowHandle);
			}
		}
		// 新しく開いたタブはURLが/faqになっているか？
		assertTrue(webDriver.getCurrentUrl().contains("/faq"));

		// リンク押下実行結果のキャプチャを取得する
		File heq_select = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(heq_select, new File("evidence/Case04/faq_select.png"));
	}

}
