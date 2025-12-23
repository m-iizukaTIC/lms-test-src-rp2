package jp.co.sss.lms.ct.f02_faq;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

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
		// 画面遷移
		goTo("http://localhost:8080/lms");
		// アクセスが成功しているか確認
		String url = webDriver.getCurrentUrl();
		assertTrue(url.contains("/lms/"));
		// エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() throws Exception {

		// ユーザID・パスワード入力欄にDBに登録されていないユーザーを入力し、ログインを実行
		WebElement loginId = webDriver.findElement(By.id("loginId"));
		loginId.sendKeys("StudentAA01");
		WebElement password = webDriver.findElement(By.id("password"));
		password.sendKeys("ItTest2023");
		WebElement login = webDriver.findElement(By.cssSelector(".btn.btn-primary"));
		login.click();

		// ログイン成功による画面遷移が反映されるまで待機
		pageLoadTimeout(5);

		// ログインが成功しているか確認
		String url = webDriver.getCurrentUrl();
		assertTrue(url.contains("/detail"));

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 上部メニューの「ヘルプ」リンクからヘルプ画面に遷移")
	void test03() throws Exception {

		// 「機能」＞「ヘルプ」リンクを押下
		WebElement function = webDriver.findElement(By.className("dropdown-toggle"));
		function.click();
		WebElement help = webDriver.findElement(By.linkText("ヘルプ"));
		help.click();

		// リンク押下による画面遷移が反映されるまで待機
		pageLoadTimeout(5);

		// リンク押下による画面遷移が成功しているか確認
		String url = webDriver.getCurrentUrl();
		assertTrue(url.contains("/help"));

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「よくある質問」リンクからよくある質問画面を別タブに開く")
	void test04() throws Exception {

		// 現在のウィンドウを保持
		String currentWindow = webDriver.getWindowHandle();

		// 「よくある質問」リンクを押下
		WebElement faq = webDriver.findElement(By.linkText("よくある質問"));
		faq.click();

		// リンク押下によるタブが開くまで待機
		pageLoadTimeout(5);

		// 新しく開いたタブに切り替え
		Set<String> handles = webDriver.getWindowHandles();
		for (String windowHandle : handles) {
			if (!windowHandle.equals(currentWindow)) {
				webDriver.switchTo().window(windowHandle);
			}
		}

		// 新しく開いたタブはURLが/faqになっているか？
		String url = webDriver.getCurrentUrl();
		assertTrue(url.contains("/faq"));

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

}
