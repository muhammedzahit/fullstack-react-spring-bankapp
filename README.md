# Section 4 : Implementing Presentation Layer

![alt text](/drawio/fullstack.png)

In this section, we will implement the presentation layer of our application. We will create Rest Controllers that communicates with Service (Business Logic) layer and return the response to the client. Service layer will communicate with the Data Access Layer to get the data from the database.
When Rest Controllers communicate with Service Layer, they will use DTOs (Data Transfer Objects) to transfer the data. DTOs are simple POJOs (Plain Old Java Objects) that contains only the attributes and have getters and setters for them. DTOs are used to transfer the data between layers. We will use ModelMapper to convert the DTOs to Entities and vice versa.

### Director Controller
```java

// implementations

@RestController
@RequestMapping("/api/director")
@RequiredArgsConstructor
public class DirectorController {

    private final DirectorService directorService;
    private final ModelMapper modelMapper;

    @PostMapping("/add_new_recruit")
    public ResponseEntity<String> addNewRecruit(@RequestBody AddNewRecruitDto addNewRecruitDto){
        try{
            Director directorById = directorService.getDirectorById(addNewRecruitDto.getDirectorId());
            Employee map = modelMapper.map(addNewRecruitDto, Employee.class);
            map.setDirector(directorById); map.setId(null);

            directorService.addNewRecruit(map);
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
        return ResponseEntity.ok("Hired new recruit with name " + addNewRecruitDto.getFullName());
    }

    @GetMapping("/monthly_operation")
    public ResponseEntity<String> monthlyOperation(){
        directorService.fulfillMonthlyOperations();
        return ResponseEntity.ok("Monthly operations has performed");
    }

    // ...

}

```

We annote class with `@RestController` annotation to tell Spring that this class is a Rest Controller. We also define the base path for this controller with `@RequestMapping` annotation. We will use this path to reach the methods in this controller.

As you can see we defined `private final` variables for `DirectorService` and `ModelMapper` and we used `@RequiredArgsConstructor` annotation to create a constructor with these variables. Because Spring will handle the dependency injection for us. We will use these variables in our methods. 

When writing a request method, first we define method type (GET, POST, PUT etc.) and if we want to give parameters firstly we must choose parameter type. In this example we used `@RequestBody` annotation to get the request body as a parameter. We also used `@PathVariable` annotation to get the path variable as a parameter. We also used `@RequestParam` annotation to get the request parameter as a parameter. We will use these annotations in our methods.

In methods we will return a `ResponseEntity`, this is a Spring class that contains the response body and the status code. With this class we can return 404, 400, 200 etc. status codes with a response body. We will use this class to return the response to the client.

### Director Service
```java

// implementations

@Service
@RequiredArgsConstructor
public class DirectorService {

    private final DirectorRepository directorRepository;
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    public Director getDirectorById(Long id){
        return directorRepository.findById(id).orElseThrow(() -> new RuntimeException("Director not found"));
    }

    public void addNewRecruit(Employee employee){
        employeeRepository.save(employee);
    }

    public void fulfillMonthlyOperations(){
        List<Employee> all = employeeRepository.findAll();
        all.forEach(employee -> {
            employee.setSalary(employee.getSalary() + 1000);
            employee.setWorkedHours(0);
        });
        employeeRepository.saveAll(all);
    }

    // ...

}

```

We annote class with `@Service` annotation to tell Spring that this class is a Service. Service classes are used to implement the business logic of the application. 

We explained Service and Presentation Layers on Director, if you understand the logic you can checkout the other controllers and services.

And finally we need an application to test our endpoints. We will use Postman to test our endpoints. You can download Postman from [here](https://www.postman.com/downloads/).

I created a collection for this application. You can import json file from `./spring_collections/v1.json` to Postman and test the endpoints.

![alt text](/drawio/postman_spring.png)

