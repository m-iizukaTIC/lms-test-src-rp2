package jp.co.sss.lms.ct.f02_faq;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
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
 * ケース05
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース05 キーワード検索 正常系")
public class Case05 {

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

	@Test
	@Order(5)
	@DisplayName("テスト05 キーワード検索で該当キーワードを含む検索結果だけ表示")
	void test05() throws Exception {
		// 「何か」を入力値として検索を実行する
		WebElement form = webDriver.findElement(By.id("form"));
		form.sendKeys("何か");
		WebElement search = webDriver.findElement(By.cssSelector("input[type='submit']"));
		search.click();

		// 検索結果が表示されるまで待機
		pageLoadTimeout(5);

		// 検索によって表示された項目全てに「何か」が入っているか確認
		List<WebElement> results = webDriver.findElements(By.cssSelector("dl dt span:nth-child(2)"));
		for (WebElement result : results) {
			String text = result.getText();
			assertTrue(text.contains("何か"));
		}

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 「クリア」ボタン押下で入力したキーワードを消去")
	void test06() throws Exception {
		// 「クリア」ボタンを押下する
		WebElement clear = webDriver.findElement(By.cssSelector("input[value='クリア']"));
		clear.click();

		// 「クリア」ボタンを押下した結果が表示されるまで待機
		pageLoadTimeout(5);

		// 検索欄が空欄になっているか確認する
		WebElement form = webDriver.findElement(By.id("form"));
		String clearForm = form.getText();
		assertEquals("", clearForm);

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

}
