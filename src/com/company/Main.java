package com.company;

import spark.ModelAndView;
import spark.Session;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;


import java.util.HashMap;

public class Main {
    public static HashMap<String, User> users = new HashMap<>();





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
                    else {
                        m.put("name", user.name);
                        m.put("message", user.user1);
                        return new ModelAndView(m, "messages.html");

                    }



                }),
                new MustacheTemplateEngine()
        );

        Spark.post(
                "/login",
                ((request, response) -> {
                    String name = request.queryParams("loginName");
                    String password = request.queryParams("loginPassword");
                    User user = users.get(name);
                    if (user == null) {
                        user = new User(name, password);
                        users.put(name, user);
                    }



                    if (password.equals(user.password)) {
                        Session session = request.session();
                        session.attribute("loginName", name);}

                    response.redirect("/");
                    return "";

                })

        );
        Spark.post(
                "/Create-message",
                ((request, response) -> {
                    Session session = request.session();
                    String name = session.attribute("loginName");
                    User user = users.get(name);
                    String enterMessage = request.queryParams("enterMessage");


                    Message message = new Message(enterMessage);

                    user.user1.add(message);

                    response.redirect("/");
                    return "";
                })
        );
        Spark.post(
                "/logout",
                ((request, response) -> {
                    Session session = request.session();
                    session.invalidate();
                    response.redirect("/");
                    return "";
                })
        );

        Spark.post(
                "/delete",
                ((request, response) -> {
                    Session session = request.session();
                    String name = session.attribute("loginName");
                    User user = users.get(name);
                    String deleteMessage = request.queryParams("deleteMessage");

                    int n = Integer.parseInt(deleteMessage);
                    user.user1.remove(n-1);
                    response.redirect("/");
                    return "";


                })
        );

        Spark.post(
                "/edit",
                ((request, response) -> {
                    Session session = request.session();
                    String name = session.attribute("loginName");
                    User user = users.get(name);
                    String chooseMessage = request.queryParams("chooseMessage");
                    int n = Integer.parseInt(chooseMessage);
                    user.user1.get(n-1);
                    user.user1.remove(n-1);

                    String editMessage = request.queryParams("editMessage");
                    Message message = new Message(editMessage);
                    user.user1.add(message);

                    response.redirect("/");
                    return "";
                })
        );

    }
}
// write your code here


