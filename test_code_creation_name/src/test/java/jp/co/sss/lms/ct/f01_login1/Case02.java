package jp.co.sss.lms.ct.f01_login1;

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
		// 画面遷移
		goTo("http://localhost:8080/lms");
		// エビデンス取得
		getEvidence(new Object() {
		});
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
		visibilityTimeout(By.cssSelector(".help-inline.error"), 10);

		// 正しいエラーメッセージが表示されているか？
		assertEquals("* ログインに失敗しました。", webDriver
				.findElement(By.cssSelector(".help-inline.error"))
				.getText());

		// ログイン実行結果のキャプチャを取得する
		getEvidence(new Object() {
		});
	}

}
