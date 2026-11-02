package dev.sadis.ppoo.service;

import dev.sadis.ppoo.dto.request.career.CareerRequest;
import dev.sadis.ppoo.dto.request.career.CareerUpdateRequest;
import dev.sadis.ppoo.dto.response.CareerResponse;
import dev.sadis.ppoo.entity.Career;
import dev.sadis.ppoo.exception.career.CareerCodeNotFoundException;
import dev.sadis.ppoo.exception.career.CareerCodeTakenException;
import dev.sadis.ppoo.exception.career.CareerNameTakenException;
import dev.sadis.ppoo.exception.career.CareerNotFoundException;
import dev.sadis.ppoo.repository.ICareerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CareerService {
    private final ICareerRepository careerRepository;
    private final Logger logger = LoggerFactory.getLogger(CareerService.class);

    @Transactional
    public CareerResponse create(CareerRequest request) {
        System.out.println(request.name());
        System.out.println(request.code());
        logger.info("Creating career");
        validateCodeNotTaken(request.code(), null);
        validateNameNotTaken(request.name(), null);
        var career = new Career();
        career.setCode(request.code());
        career.setName(request.name());
        var savedCareer = CareerResponse.build(careerRepository.save(career));
        logger.info("Created career with id {}", savedCareer.id());
        return savedCareer;
    }

    public Page<CareerResponse> getAll(Pageable pageable, String name) {
        logger.info("Getting all careers");

        //TODO: Arreglar filtro
        return careerRepository.findAllFiltered(pageable, name).map(CareerResponse::build);
    }

    public CareerResponse findById(Long id){
        logger.info("Searching career with id {}", id);
        var career = findCareer(id);
        return CareerResponse.build(career);
    }

    public CareerResponse findByCode(String code){
        logger.info("Searching career with code {}", code);
        var career = careerRepository.findByCode(code).orElseThrow(() -> new CareerCodeNotFoundException("The career not found with code " + code));
        return CareerResponse.build(career);
    }

    public void delete(Long id){
        logger.info("Deleting career with id {}", id);
        var career = findCareer(id);
        careerRepository.delete(career);
    }

    public CareerResponse update(Long id, CareerUpdateRequest request){
        logger.info("Updating career with id {}", id);
        var career = findCareer(id);
        validateCodeNotTaken(request.code(), career.getCode());
        validateNameNotTaken(request.name(), career.getName());

        career.setName(request.name());
        career.setCode(request.code());
        return CareerResponse.build(careerRepository.save(career));
    }

    public void validateCodeNotTaken(String code, String currentCode){
        if(careerRepository.existsByCode(code) && !Objects.equals(code, currentCode)) throw new CareerCodeTakenException("The career code " + code + " is already taken");
    }

    public void validateNameNotTaken(String name, String currentName){
        if(careerRepository.existsByNameIgnoreCase(name) && !Objects.equals(name, currentName)) throw new CareerNameTakenException("The career name " + name + " is already taken");
    }

    private Career findCareer(Long id){
        return careerRepository.findById(id).orElseThrow(()->new CareerNotFoundException("the career not found with id " + id));
    }
}
