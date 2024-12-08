package com.nikhil.controller;

import com.nikhil.dto.RequestDto;
import com.nikhil.entity.Student;
import com.nikhil.service.FilterService;
import com.nikhil.service.FilterSpecificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/filter")
@RequiredArgsConstructor
public class FilterController {

    private final FilterService filterService;
    private final FilterSpecificationService<Student> filterSpecificationService;

    @GetMapping("/student/{name}")
    public Student getStudentByName(@PathVariable(value = "name") String name){
        return filterService.getStudentByName(name);
    }

    @GetMapping("/city/{city}")
    public List<Student> getStudentByCity(@PathVariable(value = "city") String city){
        return filterService.getStudentByCity(city);
    }

    @GetMapping("/subject/{sub}")
    public List<Student> getStudentBySubject(@PathVariable(value = "sub") String subject){
        return filterService.getStudentBySubject(subject);
    }

    @PostMapping("specification")
    public List<Student> getStudent(@RequestBody RequestDto requestDto){
        return filterService.getStudent(filterSpecificationService.getSearchSpecification(requestDto.getSearchRequestDto(), requestDto.getGlobalOperator()));
    }

    @PostMapping("specification/pagination")
    public Page<Student> getStudentPaginated(@RequestBody RequestDto requestDto){
        return filterService.getStudentPaginated(filterSpecificationService.getSearchSpecification(requestDto.getSearchRequestDto(), requestDto.getGlobalOperator()), requestDto.getPageDto());
    }
}
