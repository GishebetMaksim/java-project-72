package hexlet.code.dto.urlCheck;

import hexlet.code.dto.BasePage;
import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UrlCheckPage extends BasePage {
    UrlCheck urlCheck;
    Url url;
}
