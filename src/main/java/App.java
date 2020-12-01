import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import models.Post;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;
import static spark.Spark.*;

public class App {

    public static void main(String[] args) {

        staticFileLocation("/public");

        get("/", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            ArrayList<Post> posts = Post.getAll();
            model.put("posts", posts);
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        post("/posts/new", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            String content = request.queryParams("content");
            Post newPost = new Post(content);
            return new ModelAndView(model, "success.hbs");
        }, new HandlebarsTemplateEngine());

        get("/posts/new", ((request, response) -> {
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "post_form.hbs");
        }), new HandlebarsTemplateEngine());

        get("/posts/delete", ((request, response) -> {
            Map<String, Object> model = new HashMap<>();
            Post.clearAllPosts();
            return new ModelAndView(model, "success.hbs");
        }), new HandlebarsTemplateEngine());

        get("/posts/:id", ((request, response) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfPostToFind = Integer.parseInt(request.params(":id"));
            Post foundPost = Post.findById(idOfPostToFind);
            model.put("post", foundPost);
            return new ModelAndView(model, "post_details.hbs");
        }), new HandlebarsTemplateEngine());

        get("/posts/:id/update", ((request, response) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfPostToEdit =Integer.parseInt(request.params(":id"));
            Post editPost = Post.findById(idOfPostToEdit);
            model.put("editPost", editPost);
            return  new ModelAndView(model, "post_form.hbs");
        }), new HandlebarsTemplateEngine());

        post("/posts/:id/update", ((request, response) -> {
            Map<String, Object> model = new HashMap<>();
            String newContent = request.queryParams("content");
            int idOfPostToEdit =Integer.parseInt(request.params(":id"));
            Post editPost = Post.findById(idOfPostToEdit);
            editPost.updateThisPost(newContent);
            return  new ModelAndView(model, "success.hbs");
        }), new HandlebarsTemplateEngine());

        get("/post/:id/delete", ((request, response) -> {
            Map<String, Object> model =  new HashMap<>();
            int idOfPostToDelete = Integer.parseInt(request.params(":id"));
            Post deletePost = Post.findById(idOfPostToDelete);
            deletePost.deleteThisPost();
            return new ModelAndView(model, "success.hbs");
        }), new HandlebarsTemplateEngine());

    }


}
