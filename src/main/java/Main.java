import static spark.Spark.*;

import spark.*;

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
    }
}
