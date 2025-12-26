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
 * ケース09
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース09 受講生 レポート登録 入力チェック")
public class Case09 {

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
	@DisplayName("テスト03 上部メニューの「ようこそ○○さん」リンクからユーザー詳細画面に遷移")
	void test03() {

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
	@Order(4)
	@DisplayName("テスト04 該当レポートの「修正する」ボタンを押下しレポート登録画面に遷移")
	void test04() {
		// 該当レポートの「詳細」ボタンを押下する (想定：2022年10月5日(水))
		// 少しスクロール
		scrollBy("300");
		// 見つかるまでスクロールする (識別子：初期false)
		boolean reportFound = false;
		By target = By.xpath("//tr[td[contains(text(),'週報')]]");
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

		// 「修正する」ボタンを押下する
		WebElement reports = webDriver.findElement(target);
		WebElement detail = reports.findElement(By.cssSelector("input[type='submit'][value='修正する']"));
		detail.click();

		// 詳細ボタン押下による画面遷移が反映されるまで待機
		pageLoadTimeout(5);

		// レポート詳細画面に遷移しているか確認
		String url = webDriver.getCurrentUrl();
		assertTrue(url.contains("/report/regist"));

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を修正して「提出する」ボタンを押下しエラー表示：学習項目が未入力")
	void test05() {

		// 学習項目の内容を削除し、理解度を入力する
		WebElement studyItem = webDriver.findElement(By.id("intFieldName_0"));
		studyItem.clear();
		WebElement undersdand = webDriver.findElement(By.id("intFieldValue_0"));
		undersdand.sendKeys("3");

		// 「提出する」ボタン押下
		WebElement submit = webDriver.findElement(By.xpath("//button[text()='提出する']"));
		submit.click();

		// 「提出する」ボタン押下による画面遷移が反映されるまで待機
		pageLoadTimeout(5);

		// レポート登録画面のままか確認
		String url = webDriver.getCurrentUrl();
		assertTrue(url.contains("/report/regist"));

		// 対象のエラーメッセージが表示されているか確認
		String error = webDriver.getPageSource();
		assertTrue(error.contains("* 理解度を入力した場合は、学習項目は必須です。"));

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：理解度が未入力")
	void test06() {

		// 理解度の内容を削除し、学習項目を入力する
		WebElement studyItem = webDriver.findElement(By.id("intFieldName_0"));
		studyItem.sendKeys("学習項目は入力する");
		WebElement undersdand = webDriver.findElement(By.id("intFieldValue_0"));
		undersdand.clear();

		// 「提出する」ボタン押下
		WebElement submit = webDriver.findElement(By.xpath("//button[text()='提出する']"));
		submit.click();

		// 「提出する」ボタン押下による画面遷移が反映されるまで待機
		pageLoadTimeout(5);

		// レポート登録画面のままか確認
		String url = webDriver.getCurrentUrl();
		assertTrue(url.contains("/report/regist"));

		// 対象のエラーメッセージが表示されているか確認
		String error = webDriver.getPageSource();
		assertTrue(error.contains("* 学習項目を入力した場合は、理解度は必須です。"));

		// エビデンス取得
		getEvidence(new Object() {
		});

	}

	@Test
	@Order(7)
	@DisplayName("テスト07 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度が数値以外")
	void test07() {

		// 目標の達成度に数値以外を入力する
		WebElement achieve = webDriver.findElement(By.id("content_0"));
		achieve.clear();
		achieve.sendKeys("数値以外");

		// 「提出する」ボタン押下
		WebElement submit = webDriver.findElement(By.xpath("//button[text()='提出する']"));
		submit.click();

		// 「提出する」ボタン押下による画面遷移が反映されるまで待機
		pageLoadTimeout(5);

		// レポート登録画面のままか確認
		String url = webDriver.getCurrentUrl();
		assertTrue(url.contains("/report/regist"));

		// 対象のエラーメッセージが表示されているか確認
		String error = webDriver.getPageSource();
		assertTrue(error.contains("* 目標の達成度は半角数字で入力してください。"));

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(8)
	@DisplayName("テスト08 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度が範囲外")
	void test08() {

		// 目標の達成度に1～10以外の数値を入力する
		WebElement achieve = webDriver.findElement(By.id("content_0"));
		achieve.clear();
		achieve.sendKeys("11");

		// 「提出する」ボタン押下
		WebElement submit = webDriver.findElement(By.xpath("//button[text()='提出する']"));
		submit.click();

		// 「提出する」ボタン押下による画面遷移が反映されるまで待機
		pageLoadTimeout(5);

		// レポート登録画面のままか確認
		String url = webDriver.getCurrentUrl();
		assertTrue(url.contains("/report/regist"));

		// 対象のエラーメッセージが表示されているか確認
		String error = webDriver.getPageSource();
		assertTrue(error.contains("* 1～10の範囲で入力してください。"));

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(9)
	@DisplayName("テスト09 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度・所感が未入力")
	void test09() {

		// 目標の達成度の内容を削除する
		WebElement achieve = webDriver.findElement(By.id("content_0"));
		achieve.clear();
		// 所感の内容を削除する
		WebElement impression = webDriver.findElement(By.id("content_1"));
		impression.clear();

		// 「提出する」ボタン押下
		WebElement submit = webDriver.findElement(By.xpath("//button[text()='提出する']"));
		submit.click();

		// 「提出する」ボタン押下による画面遷移が反映されるまで待機
		pageLoadTimeout(5);

		// レポート登録画面のままか確認
		String url = webDriver.getCurrentUrl();
		assertTrue(url.contains("/report/regist"));

		// 対象のエラーメッセージが表示されているか確認
		String error = webDriver.getPageSource();
		assertTrue(error.contains("* 目標の達成度は必須です。"));
		assertTrue(error.contains("* 所感は必須です。"));

	}

	@Test
	@Order(10)
	@DisplayName("テスト10 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：所感・一週間の振り返りが2000文字超")
	void test10() {

		// 2000文字を超える内容作成
		StringBuilder overLimit = new StringBuilder("0123456789");
		for (int i = 0; i < 9; i++) {
			overLimit.append(overLimit.toString());
		}

		// 所感に2000文字を超える内容を入力する
		WebElement impression = webDriver.findElement(By.id("content_1"));
		impression.clear();
		impression.sendKeys(overLimit);

		// 少しスクロール
		scrollBy("300");

		// 一週間の振り返りに2000文字を超える内容を入力する
		WebElement week = webDriver.findElement(By.id("content_2"));
		week.clear();
		week.sendKeys(overLimit);

		// 「提出する」ボタン押下
		WebElement submit = webDriver.findElement(By.xpath("//button[text()='提出する']"));
		submit.click();

		// 「提出する」ボタン押下による画面遷移が反映されるまで待機
		pageLoadTimeout(5);

		// レポート登録画面のままか確認
		String url = webDriver.getCurrentUrl();
		assertTrue(url.contains("/report/regist"));

		// 対象のエラーメッセージが表示されているか確認
		String error = webDriver.getPageSource();
		assertTrue(error.contains("* 目標の達成度は必須です。"));
		assertTrue(error.contains("* 所感は必須です。"));
	}

}
