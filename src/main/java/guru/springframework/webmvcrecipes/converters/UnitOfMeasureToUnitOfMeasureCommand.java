package guru.springframework.webmvcrecipes.converters;

import guru.springframework.webmvcrecipes.commands.UnitOfMeasureCommand;
import guru.springframework.webmvcrecipes.domain.UnitOfMeasure;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * Created by jt on 6/21/17.
 */
@Component
public class UnitOfMeasureToUnitOfMeasureCommand implements Converter<UnitOfMeasure, UnitOfMeasureCommand> {

    @Synchronized
    @Nullable
    @Override
    public UnitOfMeasureCommand convert(UnitOfMeasure unitOfMeasure) {

        if (unitOfMeasure != null) {
            final UnitOfMeasureCommand umoc = new UnitOfMeasureCommand();
            umoc.setId(unitOfMeasure.getId());
            umoc.setDescription(unitOfMeasure.getDescription());
            return umoc;
        }
        return null;
    }
}
