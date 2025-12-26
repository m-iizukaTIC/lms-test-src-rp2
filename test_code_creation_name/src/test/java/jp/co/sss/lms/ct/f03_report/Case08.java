package jp.co.sss.lms.ct.f03_report;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

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
 * 結合テスト レポート機能
 * ケース08
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース08 受講生 レポート修正(週報) 正常系")
public class Case08 {

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

		// ユーザID・パスワード入力欄にDBに登録されているユーザーを入力し、ログインを実行
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
	@DisplayName("テスト03 提出済の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() {

		// 「提出済」の「詳細」ボタンを押下する (想定：2022年10月5日(水))
		// 見つかるまでスクロールする (識別子：初期false)
		boolean reportFound = false;
		By target = By.xpath("//tr[td[text()='2022年10月5日(水)']]");
		for (int i = 0; i < 10; i++) {
			// もし対象日の要素を回収できたらループから抜ける
			if (!webDriver.findElements(target).isEmpty()) {
				reportFound = true;
				break;
			}
			// 見つかっていなかったらスクロールを続ける
			scrollBy("300");
			pageLoadTimeout(5);
		}

		// 見つかったかどうか確認
		assertTrue(reportFound);

		// 提出済みかどうか確認
		WebElement already = webDriver.findElement(target);
		assertTrue(already.findElement(By.xpath(".//span[text()='提出済み']")).isDisplayed());
		WebElement detail = already.findElement(By.cssSelector("input[type='submit']"));
		detail.click();

		// 詳細ボタン押下よる画面遷移が反映されるまで待機
		pageLoadTimeout(5);

		// セクション画面に遷移しているか確認
		String url = webDriver.getCurrentUrl();
		assertTrue(url.contains("/section/detail"));

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「確認する」ボタンを押下しレポート登録画面に遷移")
	void test04() {

		// 「確認する」ボタンを押下する
		WebElement submit = webDriver.findElement(By.xpath("//input[@type='submit' and contains(@value,'確認する')]"));
		submit.click();

		// 確認するするボタン押下による画面遷移が反映されるまで待機
		pageLoadTimeout(5);

		// レポート登録画面に遷移しているか確認
		String url = webDriver.getCurrentUrl();
		assertTrue(url.contains("/report/regist"));

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を修正して「提出する」ボタンを押下しセクション詳細画面に遷移")
	void test05() {

		// 報告内容を取得し書き換える
		WebElement textarea = webDriver.findElement(
				By.xpath("//textarea[starts-with(@id,'content_')]"));
		textarea.clear();
		textarea.sendKeys("これはテストによる修正です。");

		// 「提出する」ボタン押下
		WebElement submit = webDriver.findElement(By.xpath("//button[text()='提出する']"));
		submit.click();

		// 提出するボタン押下による画面遷移が反映されるまで待機
		pageLoadTimeout(5);

		// レポート登録画面に遷移しているか確認
		String url = webDriver.getCurrentUrl();
		assertTrue(url.contains("/section/detail"));

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 上部メニューの「ようこそ○○さん」リンクからユーザー詳細画面に遷移")
	void test06() {

		// 「ようこそ〇〇さん」リンクを押下する
		WebElement user = webDriver.findElement(By.linkText("ようこそ受講生ＡＡ１さん"));
		user.click();

		// 「ようこそ〇〇さん」リンク押下による画面遷移が反映されるまで待機
		pageLoadTimeout(5);

		// ユーザ詳細画面に遷移しているか確認
		String url = webDriver.getCurrentUrl();
		assertTrue(url.contains("/user/detail"));

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 該当レポートの「詳細」ボタンを押下しレポート詳細画面で修正内容が反映される")
	void test07() {

		// 該当レポートの「詳細」ボタンを押下する (想定：2022年10月5日(水))
		// 少しスクロール
		scrollBy("300");
		// 見つかるまでスクロールする (識別子：初期false)
		boolean reportFound = false;
		By target = By.xpath("//tr[td[text()='2022年10月5日(水)']]");
		for (int i = 0; i < 10; i++) {
			// もし対象日の要素を回収できたらループから抜ける
			if (!webDriver.findElements(target).isEmpty()) {
				reportFound = true;
				break;
			}
			// 見つかっていなかったらスクロールを続ける
			scrollBy("300");
			pageLoadTimeout(5);
		}

		// 見つかったかどうか確認
		assertTrue(reportFound);

		// 「詳細」ボタンを押下する
		WebElement reports = webDriver.findElement(target);
		WebElement detail = reports.findElement(By.cssSelector("input[type='submit'][value='詳細']"));
		detail.click();

		// 詳細ボタン押下による画面遷移が反映されるまで待機
		pageLoadTimeout(5);

		// レポート詳細画面に遷移しているか確認
		String url = webDriver.getCurrentUrl();
		assertTrue(url.contains("/report/detail"));

		// レポート内容を確認し、修正が反映されているか確認。
		WebElement report = webDriver.findElement(By.xpath("//td[contains(text(),'これはテストによる修正です。')]"));
		String content = report.getText();
		assertEquals("これはテストによる修正です。", content);

		// エビデンス取得
		getEvidence(new Object() {
		});
	}
}
