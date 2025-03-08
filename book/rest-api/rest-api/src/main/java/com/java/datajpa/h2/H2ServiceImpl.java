package com.java.datajpa.h2;

import com.java.entity.EmployeeEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class H2ServiceImpl implements H2Service{

    private final EmployeeRepository employeeRepository;

    @Override
    public EmployeeEntity saveEmployee(EmployeeEntity employeeEntity) {
        return employeeRepository.save(employeeEntity);
    }

    @Override
    public List<EmployeeEntity> saveEmployeeEntityList(List<EmployeeEntity> employeeEntityList) {
        return employeeRepository.saveAll(employeeEntityList);
    }

    @Override
    public List<EmployeeEntity> findAllEmployeeEntities() {
        return employeeRepository.findAll();
    }
}