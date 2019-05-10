package controller;

import dao.LexicalTheoryRepo;
import model.LexicalTheory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/lexical")
public class LexicalTheoryController implements ElementGetter{

    private final LexicalTheoryRepo lxRepo;

    @Autowired
    public LexicalTheoryController(LexicalTheoryRepo repo){
        lxRepo = repo;
    }

    @Override
    @GetMapping("/get")
    public String get(@RequestParam("id") long theoryId){
        Optional<LexicalTheory> opt = lxRepo.findById(theoryId);
        if(!opt.isPresent()){
            return "Invalid id";
        }

        LexicalTheory theory = opt.get();
        return util.JSONparser.toJSON(theory);
    }

    @Override
    @GetMapping("/getAll")
    public String getAll(){
        Iterable<LexicalTheory> set = lxRepo.findAll();
        return util.JSONparser.toJSON(set);
    }
}