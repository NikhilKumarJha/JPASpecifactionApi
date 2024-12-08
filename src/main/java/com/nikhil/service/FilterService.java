package com.nikhil.service;

import com.nikhil.dto.PageRequestDto;
import com.nikhil.entity.Student;
import com.nikhil.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FilterService {

    private final StudentRepository studentRepository;

    public Student getStudentByName(String name) {
        return studentRepository.findByName(name);
    }


    public List<Student> getStudentByCity(String city) {
        return studentRepository.findByAddressCity(city);
    }

    public List<Student> getStudentBySubject(String subject) {
        return studentRepository.findBySubjectsName(subject);
    }

    public List<Student> getStudent(Specification<Student> searchSpecification) {
        return studentRepository.findAll(searchSpecification);
    }

    public Page<Student> getStudentPaginated(Specification<Student> searchSpecification, PageRequestDto pageDto) {
        Pageable pageable = new PageRequestDto().getPageable(pageDto);
        return studentRepository.findAll(searchSpecification, pageable);
    }

}
