package sparklab;

import junit.framework.Assert;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import spark.*;
import spark.template.freemarker.FreeMarkerRoute;
import sparklab.SparkTestUtil.UrlResponse;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class MainTest {

    static SparkTestUtil testUtil;

    @AfterClass
    public static void tearDown() {
        //Spark.clearRoutes();
        //Spark.stop();
    }

    @BeforeClass
    public static void setup() {
        testUtil = new SparkTestUtil(4567);

        get(new Route("/") {
            @Override
            public Object handle(Request request, Response response) {
                return "Hello World!";
            }
        });

        get(new Route("/hello/:name") {
            @Override
            public Object handle(Request request, Response response) {
                return "Hello " + request.params(":name");
            }
        });

        get(new Route("/say/*/to/*") {
            @Override
            public Object handle(Request request, Response response) {
                return "Listen, " + request.splat()[1] + ": " + request.splat()[0] + "!";
            }
        });

        get(new FreeMarkerRoute("/test") {
            @Override
            public Object handle(Request request, Response response) {
                Map<String, Object> attributes = new HashMap<String, Object>();
                attributes.put("user", "Fabricio");
                return modelAndView(attributes, "test.ftl");
            }
        });

        try {
            Thread.sleep(500);
        } catch (Exception e) {
        }
    }

    @Test
    public void testGetRoot() {
        try {
            SparkTestUtil.UrlResponse response = testUtil.doMethod("GET", "/", null);
            Assert.assertEquals(200, response.status);
            Assert.assertEquals("Hello World!", response.body);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testGetHello() {
        try {
            UrlResponse response = testUtil.doMethod("GET", "/hello/java", null);
            Assert.assertEquals(200, response.status);
            Assert.assertEquals("Hello java", response.body);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testGetSayTo() {
        try {
            UrlResponse response = testUtil.doMethod("GET", "/say/hello/to/java", null);
            Assert.assertEquals(200, response.status);
            Assert.assertEquals("Listen, java: hello!", response.body);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testFreeMarker() {
        try {
            UrlResponse response = testUtil.doMethod("GET", "/test", null);
            Assert.assertEquals(200, response.status);
            Assert.assertEquals("text/html; charset=UTF-8", response.headers.get("Content-Type"));
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

}