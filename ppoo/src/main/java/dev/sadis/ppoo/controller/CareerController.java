package dev.sadis.ppoo.controller;

import dev.sadis.ppoo.dto.request.career.CareerRequest;
import dev.sadis.ppoo.dto.response.CareerResponse;
import dev.sadis.ppoo.service.CareerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/careers")
public class CareerController {
    private final CareerService careerService;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)


    public Page<CareerResponse> getAll(
            @RequestParam(required = false) String name,
            Pageable pageable
    ){
        return careerService.getAll(pageable, name);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public CareerResponse create(@RequestBody @Valid CareerRequest request){
        return careerService.create(request);
    }
}
