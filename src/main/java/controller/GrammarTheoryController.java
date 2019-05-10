package controller;

import dao.GrammarTheoryRepo;
import model.GrammarTheory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/grammar")
public class GrammarTheoryController implements ElementGetter{

    private final GrammarTheoryRepo grRepo;

    @Autowired
    public GrammarTheoryController(GrammarTheoryRepo repo){
        grRepo = repo;
    }

    @Override
    @GetMapping("/get")
    public String get(@RequestParam("id") long theoryId){
        Optional<GrammarTheory> opt = grRepo.findById(theoryId);
        if(!opt.isPresent()){
            return "Invalid id";
        }

        GrammarTheory theory = opt.get();
        return util.JSONparser.toJSON(theory);
    }

    @Override
    @GetMapping("/getAll")
    public String getAll(){
        Iterable<GrammarTheory> set = grRepo.findAll();
        return util.JSONparser.toJSON(set);
    }
}