package com.around.me.autocomplete;

import android.test.InstrumentationTestCase;

import com.around.me.TestUtils;
import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;
import com.squareup.okhttp.mockwebserver.RecordedRequest;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * The type Google places auto complete tests.
 */
public class GooglePlacesAutoCompleteTests extends InstrumentationTestCase {

    private MockWebServer server;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        server = new MockWebServer();
    }

    @Override
    protected void tearDown() throws Exception {
        server.shutdown();
        super.tearDown();
    }

    /**
     * Test _ should _ add _ api _ key _ to _ requests.
     *
     * @throws InterruptedException the interrupted exception
     */
    public void test_should_add_api_key_to_requests() throws InterruptedException, IOException, JSONException {
        // arrange
        String apikey = "bababa";
        server.enqueue(new MockResponse());

        server.play();
        URL url = server.getUrl("/");

        GooglePlacesAutoComplete autoComplete = new GooglePlacesAutoComplete(
                url.toString(),
                apikey
        );

        // act
        autoComplete.search("1234");

        // assert
        assertThat(server.getRequestCount()).isEqualTo(1);
        RecordedRequest recordedRequest = server.takeRequest();
        assertThat(recordedRequest.getPath()).contains("key=" + apikey);
    }

    /**
     * Test _ should _ add _ input _ to _ requests.
     *
     * @throws IOException the iO exception
     */
    public void test_should_add_input_to_requests() throws IOException, InterruptedException, JSONException {
        // arrange
        String input = "cacaca";
        server.enqueue(new MockResponse());

        server.play();
        URL url = server.getUrl("/");

        GooglePlacesAutoComplete autoComplete = new GooglePlacesAutoComplete(
                url.toString(),
                "b"
        );

        // act
        autoComplete.search(input);

        // assert
        assertThat(server.getRequestCount()).isEqualTo(1);
        RecordedRequest recordedRequest = server.takeRequest();
        assertThat(recordedRequest.getPath()).contains("input=" + input);
    }

    /**
     * Test _ should _ send _ request _ to _ autocomplete _ url.
     *
     * @throws IOException          the iO exception
     * @throws InterruptedException the interrupted exception
     */
    public void test_should_send_request_to_autocomplete_url() throws IOException, InterruptedException, JSONException {
        // arrange
        server.enqueue(new MockResponse());

        server.play();
        URL url = server.getUrl("/");

        GooglePlacesAutoComplete autoComplete = new GooglePlacesAutoComplete(
                url.toString(),
                "key"
        );

        // act
        autoComplete.search("input123");

        // assert
        assertThat(server.getRequestCount()).isEqualTo(1);
        RecordedRequest recordedRequest = server.takeRequest();
        assertThat(recordedRequest.getPath()).contains("/maps/api/place/autocomplete/json?");
    }

    public void test_should_return_results_for_input_bar() throws Exception {
        String jsonBody = TestUtils.readFromAssets("autocomplete_for_bar.json", getInstrumentation().getContext());
        server.enqueue(new MockResponse().setResponseCode(200).setBody(jsonBody));

        server.play();
        URL url = server.getUrl("/");

        GooglePlacesAutoComplete autoComplete = new GooglePlacesAutoComplete(
                url.toString(),
                "key"
        );

        // act
        List<String> results = autoComplete.search("input123");

        // assert
        assertThat(results)
                .hasSize(5)
                .contains(
                        "Barcelona, Spain",
                        "Barbados",
                        "Bar-Ilan University, Ramat Gan, Israel",
                        "Bari, Italy",
                        "Barra da Tijuca, Rio de Janeiro - State of Rio de Janeiro, Brazil");
    }
}
