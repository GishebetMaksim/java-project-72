package hexlet.code.controller;
import hexlet.code.dto.BasePage;
import hexlet.code.dto.urls.UrlPage;
import hexlet.code.dto.urls.UrlsPage;
import hexlet.code.model.Url;
import hexlet.code.repository.UrlRepository;
import hexlet.code.util.UrlUtils;
import io.javalin.http.Context;
import hexlet.code.util.NamedRoutes;
import io.javalin.http.NotFoundResponse;

import static io.javalin.rendering.template.TemplateUtil.model;

import java.sql.SQLException;


public class UrlController {
    public static void create(Context ctx) {
        var name = ctx.formParam("url");

        try {
            String normalizedName = UrlUtils.normalize(name);

            if (UrlRepository.existsByName(normalizedName)) {
                ctx.sessionAttribute("flash", "Страница уже существует");
                ctx.sessionAttribute("flashType", "error");
            } else {
                var url = new Url(normalizedName);
                UrlRepository.save(url);
                ctx.sessionAttribute("flash", "Страница успешно добавлена");
                ctx.sessionAttribute("flashType", "success");
            }
            ctx.redirect(NamedRoutes.urlsPath());
        } catch (Exception e) {
            ctx.sessionAttribute("flash", "Некорректный URL");
            ctx.sessionAttribute("flashType", "error");
            ctx.redirect(NamedRoutes.buildUrlPath());
        }
    }

    public static void build(Context ctx) {
        String flash = ctx.consumeSessionAttribute("flash");
        String flashType = ctx.consumeSessionAttribute("flashType");
        var page = new BasePage();
        page.setFlash(flash);
        page.setFlashType(flashType);

        ctx.render("build.jte", model("page", page));
    }

    public static void index(Context ctx) throws SQLException {
        String flash = ctx.consumeSessionAttribute("flash");
        String flashType = ctx.consumeSessionAttribute("flashType");
        UrlsPage page = new UrlsPage(UrlRepository.getEntities());
        page.setFlash(flash);
        page.setFlashType(flashType);

        ctx.render("index.jte", model("page", page));
    }

    public static void show(Context ctx) throws SQLException {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var url = UrlRepository.find(id)
                .orElseThrow(() -> new NotFoundResponse("Entity with id = " + id + " not found"));
        var page = new UrlPage(url);
        ctx.render("show.jte", model("page", page));
    }
}
