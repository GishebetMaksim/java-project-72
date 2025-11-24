package hexlet.code.dto.urlCheck;

import hexlet.code.model.UrlCheck;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class UrlChecksPage {
    private List<UrlCheck> checks;
}
