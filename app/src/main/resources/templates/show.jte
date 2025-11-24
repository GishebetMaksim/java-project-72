@import hexlet.code.dto.urls.UrlPage
@import hexlet.code.util.NamedRoutes
@param UrlPage page

@template.page(
    page = page,
    content = @`
        <div>
            <h1>Сайт: ${page.getUrl().getName()}</h1>
        </div>

        <div>
            <table>
                <tr>
                    <td>ID</td>
                    <td>${page.getUrl().getId()}</td>
                </tr>
                <tr>
                    <td>Имя</td>
                    <td>${page.getUrl().getName()}</td>
                </tr>
                <tr>
                    <td>Дата создания</td>
                    <td>${page.getUrl().getCreatedAt().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))}</td>
                </tr>
            </table>
        </div>

        <div>
            <h1>Проверки</h1>
        </div>

        <div>
            <form method="post" action="${NamedRoutes.checkUrlPath(page.getUrl().getId())}">
                <button type="submit">Проверить</button>
            </form>
        </div>

        <div>
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Код ответа</th>
                        <th>title</th>
                        <th>h1</th>
                        <th>description</th>
                        <th>Дата проверки</th>
                    </tr>
                </thead>
                <tbody>
                    @for (var check : page.getChecks())
                        <tr>
                            <td>
                                ${check.getId()}
                            </td>
                            <td>
                                ${check.getStatusCode()}
                            </td>
                            <td>
                                ${check.getTitle()}
                            </td>
                            <td>
                                ${check.getH1()}
                            </td>
                            <td>
                                ${check.getDescription()}
                            </td>
                            <td>
                                ${check.getCreatedAt().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))}
                            </td>
                        </tr>
                    @endfor
                </tbody>
            </table>
        </div>
    `
)
