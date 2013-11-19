package sparklab;

import static spark.Spark.*;

import spark.*;
import spark.template.freemarker.FreeMarkerRoute;

import java.util.HashMap;
import java.util.Map;


public class Main {

    public static void main(String[] args) {

        staticFileLocation("/public");

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


    }
}
