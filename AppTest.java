package com.qait.svm.RestAssuredWS;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertEquals;

import java.util.List;
import org.json.JSONObject;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;

public class AppTest {
	int id;
	ResponseSpecification checkStatusCodeAndContentTypejson = new ResponseSpecBuilder().expectStatusCode(200)
			.expectContentType(ContentType.JSON).build();
/**
 * This method will create a glowal id which i will be using for my test frequently
 */
	@BeforeTest
	public void method_will_create_new_id_for_use() {

		id = given().when().get("http://10.0.1.86/snl/rest/v1/board/new.json").then().extract()
				.path("response.board.id");

	}
/**
 * This method will test the format of WSDL documentation
 */
	@Test(priority = 2)
	public void test_ResponseHeaderData_and_status_code_ShouldBeCorrect_with_format() {

		given().when().get("http://10.0.1.86/snl/soap/v1/wsdl").then().assertThat().statusCode(200).and()
				.contentType("text/xml");

	}
/**
 * This mrthod will test validity of any board with the id provided by @beforeTest method
 */
	@Test(priority = 3)
	public void test_userid_with_given_id_is_valid() {

		int userid = id;
		String version = "v1";
		given().pathParam("Uid", userid).when().get("http://10.0.1.86/snl/rest/{version}/board/{Uid}.json", version)
				.then().assertThat().body("response.board.id", equalTo(userid));
	}
/**
 * this method will check the status code and format for the given id and check for the list of boards
 */
	@Test(priority = 0)
	public void get_list_of_boards() {

		String version = "v1";
		String format = "json";
		given().

				when().get("http://10.0.1.86/snl/rest/{V}/board.{F}", version, format).then().assertThat()
				.spec(checkStatusCodeAndContentTypejson).and().body("response.status", equalTo(1));
	}

	/**
	 * Adding 4 players, if we add 5th player Test with priority=5 will fail. I
	 * am commenting 5th player for your reference
	 */
	@Test(priority = 4)
	public void checking_register_player() {
		System.out.println("ID is= " + id);

		JSONObject jsonObject1 = new JSONObject();
		jsonObject1.put("board", id);
		// ===============================================================
		JSONObject jsonObject2 = new JSONObject();
		jsonObject2.put("name", "Shivam Bharadwaj");

		jsonObject1.put("player", jsonObject2);

		given().contentType("application/json").body(jsonObject1.toString()).when()
				.post("http://10.0.1.86/snl/rest/v1/player.json").then().spec(checkStatusCodeAndContentTypejson);

		// ============================================================================

		JSONObject jsonObject3 = new JSONObject();
		jsonObject3.put("name", "Shivam ");

		jsonObject1.put("player", jsonObject3);

		given().contentType("application/json").body(jsonObject1.toString()).when()
				.post("http://10.0.1.86/snl/rest/v1/player.json").then().spec(checkStatusCodeAndContentTypejson);

		// =========================================================
		JSONObject jsonObject4 = new JSONObject();
		jsonObject4.put("name", "Bharadwaj");

		jsonObject1.put("player", jsonObject4);

		given().contentType("application/json").body(jsonObject1.toString()).when()
				.post("http://10.0.1.86/snl/rest/v1/player.json").then().spec(checkStatusCodeAndContentTypejson);

		// =========================================================
		JSONObject jsonObject5 = new JSONObject();
		jsonObject5.put("name", "Mintu");

		jsonObject1.put("player", jsonObject5);

		given().contentType("application/json").body(jsonObject1.toString()).when()
				.post("http://10.0.1.86/snl/rest/v1/player.json").then().spec(checkStatusCodeAndContentTypejson);

		// ==========================================================

		/*
		 * JSONObject jsonObject6=new JSONObject(); jsonObject6.put("name",
		 * "Chintu");
		 * 
		 * jsonObject1.put("player", jsonObject6);
		 * 
		 * given().contentType("application/json").
		 * body(jsonObject1.toString()). when().
		 * post("http://10.0.1.86/snl/rest/v1/player.json"). then()
		 * .spec(checkStatusCodeAndContentTypejson);
		 */
	}
/**
 * This method will test for a maxixmum of 4 players on board
 */
	@Test(priority = 5)
	public void testing_new_board_has_max_4_players() {

		String version = "v1";
		String format = "json";

		List<String> listOfPlayers = given().

				when().get("http://10.0.1.86/snl/rest/{V}/board/{ID}.{F}", version, id, format).then().extract()
				.path("response.board.players");

		assertEquals(listOfPlayers.size(), 4);
	}

	/**
	 * This method will test the new board with status report amd content type
	 */
	@Test(priority = 1)
	public void testing_new_board() {

		String version = "v1";
		String format = "json";
		given().

				when().get("http://10.0.1.86/snl/rest/{V}/board/new.{F}", version, format).then().assertThat()
				.spec(checkStatusCodeAndContentTypejson).and().body("response.board.turn", equalTo(1));
	}

	/**
	 * This method uses the BasicAuthentication using the credentials provided in documentation
	 */
	@Test(priority = 7)
	public void testing_API_With_BasicAuthentication() {

		given().auth().preemptive().basic("su", "root_pass").when()
				.get("http://10.0.1.86/snl/rest/v2/board/{uid}.json", id).then().assertThat()
				.spec(checkStatusCodeAndContentTypejson);
	}

	/**
	 * This method will delete previously generated id player i.e.(currentId-1)a
	 * nd will keep alive the current id player
	 */
	@Test(priority = 8)
	public void delete_player_must_delete_player() {
		String s = given().when().delete("http://10.0.1.86/snl/rest/v1/board/{uid}.json", id - 1).then().extract()
				.path("response.success");
		assertEquals(s, "OK");

	}

}