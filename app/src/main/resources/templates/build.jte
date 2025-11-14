@import hexlet.code.dto.BasePage
@param BasePage page

@template.page(
    page = page,
    content = @`
        <body class="container mt-5">
            <h1>Анализатор страниц</h1>
            <form action="/urls" method="post">
                <div class="mb-3">
                    <label class="form-label">
                        Имя
                        <input type="text" name="url" class="form-control" placeholder="Введите URL" />
                    </label>
                    <div class="form-text">Пример: https://www.example.com</div>
                </div>
                <button type="submit" class="btn btn-primary">Зарегистрировать</button>
            </form>
        </body>
    `
)
