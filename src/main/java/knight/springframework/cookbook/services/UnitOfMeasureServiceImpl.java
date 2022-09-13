package knight.springframework.cookbook.services;

import knight.springframework.cookbook.commands.UnitOfMeasureCommand;
import knight.springframework.cookbook.converters.UnitOfMeasureToUnitOfMeasureCommand;
import knight.springframework.cookbook.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {


    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

    public UnitOfMeasureServiceImpl(UnitOfMeasureRepository unitOfMeasureRepository,
                                    UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.unitOfMeasureToUnitOfMeasureCommand = unitOfMeasureToUnitOfMeasureCommand;
    }

    @Override
    public Set<UnitOfMeasureCommand> listAllUoms() {
        //converts my domain object to command objects
    return StreamSupport.stream(unitOfMeasureRepository.findAll()
            .spliterator(),false ).map(unitOfMeasureToUnitOfMeasureCommand ::convert).collect(Collectors.toSet());



    }
}
