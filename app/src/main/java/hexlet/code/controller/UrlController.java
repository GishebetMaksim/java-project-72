package hexlet.code.controller;
import hexlet.code.dto.BasePage;
import hexlet.code.dto.urls.UrlPage;
import hexlet.code.dto.urls.UrlsPage;
import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;
import hexlet.code.util.UrlUtils;
import io.javalin.http.Context;
import hexlet.code.util.NamedRoutes;
import io.javalin.http.NotFoundResponse;
import kong.unirest.core.Unirest;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import static io.javalin.rendering.template.TemplateUtil.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;


public class UrlController {
    public static void create(Context ctx) throws SQLException {
        var name = ctx.formParam("url");

        String normalizedName;
        try {
            normalizedName = UrlUtils.normalize(name);
        } catch (Exception e) {
            ctx.sessionAttribute("flash", "Некорректный URL");
            ctx.sessionAttribute("flashType", "error");
            ctx.redirect(NamedRoutes.buildUrlPath());
            return;
        }

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

        List<Url> urls = UrlRepository.getEntities();
        Map<Long, UrlCheck> latestChecks = UrlCheckRepository.findLatestChecks();

        UrlsPage page = new UrlsPage(urls, latestChecks);

        page.setFlash(flash);
        page.setFlashType(flashType);

        ctx.render("index.jte", model("page", page));
    }

    public static void show(Context ctx) throws SQLException {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var url = UrlRepository.find(id)
                .orElseThrow(() -> new NotFoundResponse("Entity with id = " + id + " not found"));

        var urlChecksList = UrlCheckRepository.getUrlChecks(id);
        var page = new UrlPage(url, urlChecksList);

        String flash = ctx.consumeSessionAttribute("flash");
        String flashType = ctx.consumeSessionAttribute("flashType");
        page.setFlash(flash);
        page.setFlashType(flashType);

        ctx.render("show.jte", model("page", page));
    }

    public static void check(Context ctx) throws SQLException {
        var urlId = ctx.pathParamAsClass("id", Long.class).get();
        var url = UrlRepository.find(urlId)
                .orElseThrow(() -> new NotFoundResponse("Entity with id = " + urlId + " not found"));

        try {
            var urlName = url.getName();

            var response = Unirest.get(urlName).asString();

            var statusCode = response.getStatus();
            var document = Jsoup.parse(response.getBody());

            Element titleElement = document.selectFirst("title");
            String title = titleElement != null ? titleElement.text() : "no title";

            Element h1Element = document.selectFirst("h1");
            String h1 = h1Element != null ? h1Element.text() : "";

            Element metaDescription = document.selectFirst("meta[name=description]");
            String description = metaDescription != null ? metaDescription.attr("content") : "";

            var check = new UrlCheck(statusCode, title, h1, description, urlId);

            UrlCheckRepository.save(check);

            ctx.sessionAttribute("flash", "Страница успешно проверена");
            ctx.sessionAttribute("flashType", "success");

        } catch (Exception e) {
            ctx.sessionAttribute("flash", "Произошла ошибка при проверке страницы");
            ctx.sessionAttribute("flashType", "error");
        }
        ctx.redirect(NamedRoutes.urlPath(urlId));
    }
}
