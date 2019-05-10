package controller;

import dao.TestRepo;
import model.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/test")
public class TestController implements ElementGetter{

    private final TestRepo grRepo;

    @Autowired
    public TestController(TestRepo repo){
        grRepo = repo;
    }

    @Override
    @GetMapping("/get")
    public String get(@RequestParam("id") long testId){
        Optional<Test> opt = grRepo.findById(testId);
        if(!opt.isPresent()){
            return "Invalid id";
        }

        Test test = opt.get();
        return util.JSONparser.toJSON(test);
    }

    @Override
    @GetMapping("/getAll")
    public String getAll(){
        Iterable<Test> set = grRepo.findAll();
        return util.JSONparser.toJSON(set);
    }
}