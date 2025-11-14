package gg.jte.generated.ondemand;
import gg.jte.Content;
import hexlet.code.dto.BasePage;
@SuppressWarnings("unchecked")
public final class JtepageGenerated {
	public static final String JTE_NAME = "page.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,2,2,2,2,32,32,32,33,33,33,33,34,34,34,37,37,38,38,38,41,41,41,2,3,3,3,3};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, Content content, BasePage page) {
		jteOutput.writeContent("\r\n<!doctype html>\r\n<html lang=\"en\">\r\n    <head>\r\n        <meta charset=\"utf-8\" />\r\n        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\" />\r\n        <title>Fourth project</title>\r\n\r\n        <link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3\" crossorigin=\"anonymous\">\r\n        <script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js\" integrity=\"sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p\" crossorigin=\"anonymous\"></script>\r\n    </head>\r\n    <body>\r\n        <nav class=\"navbar navbar-expand-lg navbar-light bg-light mb-3\">\r\n            <div class=\"container-fluid\">\r\n                <a class=\"navbar-brand\" href=\"/\">Анализатор страниц</a>\r\n                <div class=\"collapse navbar-collapse\">\r\n                    <ul class=\"navbar-nav me-auto mb-2 mb-lg-0\">\r\n                        <li class=\"nav-item\">\r\n                            <a class=\"btn btn-primary me-2\" href=\"/\">Главная</a>\r\n                        </li>\r\n                        <li class=\"nav-item\">\r\n                            <a class=\"btn btn-secondary\" href=\"/urls\">Сайты</a>\r\n                        </li>\r\n                    </ul>\r\n                </div>\r\n            </div>\r\n        </nav>\r\n\r\n         ");
		if (page != null && page.getFlash() != null) {
			jteOutput.writeContent("\r\n             <div class=\"alert ");
			jteOutput.setContext("div", "class");
			jteOutput.writeUserContent(page.getFlashType().equals("error") ? "alert-danger" : "alert-success");
			jteOutput.setContext("div", null);
			jteOutput.writeContent(" alert-dismissible fade show\" role=\"alert\">\r\n                     ");
			jteOutput.setContext("div", null);
			jteOutput.writeUserContent(page.getFlash());
			jteOutput.writeContent("\r\n                     <button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"alert\" aria-label=\"Close\"></button>\r\n             </div>\r\n         ");
		}
		jteOutput.writeContent("\r\n        ");
		jteOutput.setContext("body", null);
		jteOutput.writeUserContent(content);
		jteOutput.writeContent("\r\n    </body>\r\n</html>\r\n");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		Content content = (Content)params.get("content");
		BasePage page = (BasePage)params.getOrDefault("page", null);
		render(jteOutput, jteHtmlInterceptor, content, page);
	}
}
