package com.nikhil.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Objects;

@Getter
@Setter
public class PageRequestDto {
    private Integer pageNo = 0;
    private Integer pageSize = 10;
    private Sort.Direction sort = Sort.Direction.ASC;

    private String sortByColumn = "id";

    public Pageable getPageable(PageRequestDto pageRequestDto) {
        Integer pageNo = Objects.nonNull(pageRequestDto.getPageNo()) ? pageRequestDto.getPageNo() : this.getPageNo();
        Integer pageSize = Objects.nonNull(pageRequestDto.getPageSize()) ? pageRequestDto.getPageSize() : this.getPageSize();
        Sort.Direction sort = Objects.nonNull(pageRequestDto.getSort()) ? pageRequestDto.getSort() : this.getSort();
        String sortByColumn = Objects.nonNull(pageRequestDto.getSortByColumn()) ? pageRequestDto.getSortByColumn() : this.getSortByColumn();

        return PageRequest.of(pageNo, pageSize, sort, sortByColumn);
    }
}
