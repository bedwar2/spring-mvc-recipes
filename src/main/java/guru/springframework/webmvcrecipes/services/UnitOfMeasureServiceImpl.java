package guru.springframework.webmvcrecipes.services;

import guru.springframework.webmvcrecipes.commands.UnitOfMeasureCommand;
import guru.springframework.webmvcrecipes.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.webmvcrecipes.domain.UnitOfMeasure;
import guru.springframework.webmvcrecipes.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.stream.*;

import java.util.List;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {
    UnitOfMeasureRepository unitOfMeasureRepository;
    UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

    public UnitOfMeasureServiceImpl(UnitOfMeasureRepository unitOfMeasureRepository, UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.unitOfMeasureToUnitOfMeasureCommand = unitOfMeasureToUnitOfMeasureCommand;
    }

    @Override
    public List<UnitOfMeasureCommand> getAllUnitsOfMeasure() {
        List<UnitOfMeasureCommand> uomList = new ArrayList<>();
        Iterable<UnitOfMeasure> uoms =  this.unitOfMeasureRepository.findAll();
        uoms.forEach(uom -> uomList.add(unitOfMeasureToUnitOfMeasureCommand.convert(uom)));

        return uomList;
    }
}
