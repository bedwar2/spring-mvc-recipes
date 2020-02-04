package guru.springframework.webmvcrecipes.commands;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder
public class ErrorCommand {
    private Exception ex;
    private HttpStatus statusCode;
}
