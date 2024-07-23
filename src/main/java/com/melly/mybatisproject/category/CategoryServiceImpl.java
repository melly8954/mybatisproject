package com.melly.mybatisproject.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements ICategoryService<ICategory>{
    @Autowired
    private CategoryMybatisMapper mybatisMapper;  // 선언 및 스프링부트 service , autowired 사용

    private boolean isValidInsert(ICategory dto){
        if(dto == null){
            return false;
        }
        else return dto.getName() != null && !dto.getName().isEmpty();
    }

    @Override
    public ICategory findById(Long id) {
        CategoryDto dto = this.mybatisMapper.findById(id);
        return dto;
    }

    @Override
    public ICategory findByName(String name) {
        CategoryDto find = this.mybatisMapper.findByName(name);
        return find;
    }


    @Override
    public List<ICategory> getAllList() {
        List<ICategory> list = new ArrayList<>();
        for ( CategoryDto dto : this.mybatisMapper.findAll() ){
            list.add( (ICategory)dto );
        }
        return list;
    }


    @Override
    public ICategory insert(ICategory category) throws Exception {
        if ( !this.isValidInsert(category)){
            return null;
        }
        CategoryDto dto = new CategoryDto();
        dto.copyFields(category);
        dto.setId(0L);
        this.mybatisMapper.insert(dto);
        return dto;
    }

    @Override
    public boolean remove(Long id) throws Exception {
        ICategory find = this.findById(id);
        if ( find != null ) {
            this.mybatisMapper.deleteById(id);
            return false;
        }
        this.mybatisMapper.deleteById(id);
        return true;
    }

    private boolean setICategoryIsNotNull(ICategory to, ICategory from) {
        if ( to == null || from == null ) {
            return false;
        }
        if ( from.getName() != null && !from.getName().isEmpty() ) {
            to.setName(from.getName());
        }
        return true;
    }

    @Override
    public ICategory update(Long id, ICategory category) {
        ICategory find = this.findById(id);
        if ( find == null ) {
            return null;
        }
        CategoryDto dto = CategoryDto.builder()
                .id(id).name(find.getName())
                .build();
        dto.copyFields(category);
        this.mybatisMapper.update(dto);
        return find;
    }

    @Override
    public List<ICategory> findAllByNameContains(String name) {
        if (name == null || name.isEmpty()) {
            return new ArrayList<>();       // 빈 객체
        }
        List<CategoryDto> list = this.mybatisMapper.findAllByNameContains(name);
        List<ICategory> result = new ArrayList<>();
        for( CategoryDto item : list ){
            result.add((ICategory)item);
        }
        return result;
    }


}
