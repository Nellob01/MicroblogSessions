package com.company;

import spark.ModelAndView;
import spark.Session;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    public static HashMap<String, User> users = new HashMap<>();


    public static User user;

    public static ArrayList<Message> messages = new ArrayList();

    public static void main(String[] args) {
        Spark.init();

        Spark.get(
                "/",
                ((request, response) -> {
                    Session session = request.session();
                    String name = session.attribute("loginName");
                    User user = users.get(name);

                    HashMap m = new HashMap();
                    if (user == null) {
                        return new ModelAndView(m, "index.html");
                    }
                    m.put("name", user.name);
                    m.put("message", messages);
                    return new ModelAndView(m, "messages.html");


                }),
                new MustacheTemplateEngine()
        );

        Spark.post(
                "/login",
                ((request, response) -> {
                    String name = request.queryParams("loginName");
                    String password = request.queryParams("loginPassword");
                    user = new User(name, password);


                    if (password.equals(user.password)) {


                    }
                    Session session = request.session();
                    session.attribute("loginName", name);

                    response.redirect("/");
                    return "";
                })
        );

        Spark.post(
                "/logout",
                ((request, response) -> {
                    user = null;
                    response.redirect("/");
                    return "";
                })
        );
        Spark.post(
                "/Create-message",
                ((request, response) -> {
                    String text = request.queryParams("enterMessage");
                    messages.add(new Message(text));
                    response.redirect("/");
                    return "";
                })
        );

    }
}
// write your code here


