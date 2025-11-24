@import hexlet.code.dto.urls.UrlsPage
@import hexlet.code.util.NamedRoutes
@param UrlsPage page

@template.page(
    page = page,
    content = @`
        <div class="mx-auto p-4 py-md-5">
            <main>
                <h1>Сайты</h1>

                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Имя</th>
                            <th>Последняя проверка</th>
                            <th>Код ответа</th>
                        </tr>
                    </thead>
                    <tbody>
                        @for (var url : page.getUrls())
                            <tr>
                                <td>
                                    ${url.getId()}
                                </td>
                                <td>
                                    <a href="${NamedRoutes.urlPath(url.getId())}">${url.getName()}</a>
                                </td>
                                <td>
                                     @if (page.getLatestChecks().get(url.getId()) != null)
                                        ${page.getLatestChecks().get(url.getId()).getCreatedAt().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))}
                                     @endif
                                </td>
                                <td>
                                    @if (page.getLatestChecks().get(url.getId()) != null)
                                        ${page.getLatestChecks().get(url.getId()).getStatusCode()}
                                    @endif
                                </td>
                            </tr>
                        @endfor
                    </tbody>
                </table>
            </main>
        </div>
    `
)
