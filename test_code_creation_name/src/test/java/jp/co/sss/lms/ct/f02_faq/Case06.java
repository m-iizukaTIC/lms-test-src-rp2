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
 * ケース06
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース06 カテゴリ検索 正常系")
public class Case06 {

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
	@DisplayName("テスト05 カテゴリ検索で該当カテゴリの検索結果だけ表示")
	void test05() throws Exception {
		// 【研修関係】のリンクを押下し、質問項目を検索
		WebElement link = webDriver.findElement(By.linkText("【研修関係】"));
		link.click();

		// 検索結果が表示されるまで待機
		pageLoadTimeout(5);

		// カテゴリ検索ができているか確認
		String url = webDriver.getCurrentUrl();
		assertTrue(url.contains("/faq?frequentlyAskedQuestionCategoryId=1"));

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 検索結果の質問をクリックしその回答を表示")
	void test06() throws Exception {
		// 指定した項目をクリックし、回答が表示されているか確認
		WebElement question = webDriver.findElement(
				By.xpath("//dt[contains(., '研修の申し込みはどのようにすれば良いですか？')]"));
		question.click();

		// 表示されている回答
		WebElement answer = webDriver.findElement(
				By.xpath("//dd[contains(., '営業担当がいる場合は、営業担当までご連絡ください。')]"));
		String answerText = answer.getText();
		String answerReplace = answerText.replaceAll("\\s+", "");

		// 想定されている回答
		String answerModel = "A."
				+ "営業担当がいる場合は、営業担当までご連絡ください。\r\n"
				+ "申し込み方法についてご案内させていただきます。\r\n"
				+ "なお、弊社営業営業がいない場合は、東京ITスクール運営事務局までご連絡いただけると幸いです。";
		String answerModelReplace = answerModel.replaceAll("\\s+", "");

		// 比較による回答内容確認
		assertEquals(answerReplace, answerModelReplace);

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

}
