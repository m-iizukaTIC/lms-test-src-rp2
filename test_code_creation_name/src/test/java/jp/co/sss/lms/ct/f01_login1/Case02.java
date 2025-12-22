package jp.co.sss.lms.ct.f01_login1;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;

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
 * 結合テスト ログイン機能①
 * ケース02
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース02 受講生 ログイン 認証失敗")
public class Case02 {

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
	@DisplayName("テスト02 DBに登録されていないユーザーでログイン")
	void test02() throws Exception {

		// ユーザID・パスワード入力欄にDBに登録されていないユーザーを入力し、ログインを実行
		webDriver.findElement(By.id("loginId")).sendKeys("NotUser001");
		webDriver.findElement(By.id("password")).sendKeys("NotUser001");
		webDriver.findElement(By.cssSelector(".btn.btn-primary")).click();

		// ログイン失敗によるエラーメッセージが反映されるまで待機
		WebDriverWait loginWait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
		loginWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".help-inline.error")));

		// ログイン実行結果のキャプチャを取得する
		File login_false = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(login_false, new File("evidence/Case02/login_false.png"));
	}

}
