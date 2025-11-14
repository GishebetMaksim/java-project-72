package gg.jte.generated.ondemand;
import hexlet.code.dto.BasePage;
@SuppressWarnings("unchecked")
public final class JtebuildGenerated {
	public static final String JTE_NAME = "build.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,1,1,1,3,3,6,6,20,20,20,21,21,21,1,1,1,1};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, BasePage page) {
		jteOutput.writeContent("\r\n");
		gg.jte.generated.ondemand.JtepageGenerated.render(jteOutput, jteHtmlInterceptor, new gg.jte.html.HtmlContent() {
			public void writeTo(gg.jte.html.HtmlTemplateOutput jteOutput) {
				jteOutput.writeContent("\r\n        <body class=\"container mt-5\">\r\n            <h1>Анализатор страниц</h1>\r\n            <form action=\"/urls\" method=\"post\">\r\n                <div class=\"mb-3\">\r\n                    <label class=\"form-label\">\r\n                        Имя\r\n                        <input type=\"text\" name=\"url\" class=\"form-control\" placeholder=\"Введите URL\" />\r\n                    </label>\r\n                    <div class=\"form-text\">Пример: https://www.example.com</div>\r\n                </div>\r\n                <button type=\"submit\" class=\"btn btn-primary\">Зарегистрировать</button>\r\n            </form>\r\n        </body>\r\n    ");
			}
		}, page);
		jteOutput.writeContent("\r\n");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		BasePage page = (BasePage)params.get("page");
		render(jteOutput, jteHtmlInterceptor, page);
	}
}
