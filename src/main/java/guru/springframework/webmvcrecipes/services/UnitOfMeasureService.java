package guru.springframework.webmvcrecipes.services;

import guru.springframework.webmvcrecipes.commands.UnitOfMeasureCommand;

import java.util.List;

public interface UnitOfMeasureService {
    public List<UnitOfMeasureCommand> getAllUnitsOfMeasure();
}
