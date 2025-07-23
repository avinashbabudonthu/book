# Passing null values
* We cannot stub method passing null value then use `Mockito.isNull()`
* Sample code
```
import org.mockito.Mockito;

Mockito.when(employeeDao.getEmployeeNames(Mockito.isNull())).thenReturn(new ArrayList<>());
or
Mockito.when(employeeDao.getEmployeeNames(Mockito.eq(null))).thenReturn(new ArrayList<>());
```