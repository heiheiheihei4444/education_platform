package com.xh.website.module.person.controller;

import com.xh.website.module.paper.entity.Paper;
import com.xh.website.module.paper.repository.PaperRepository;
import com.xh.website.module.person.entiy.Degree;
import com.xh.website.module.person.entiy.Person;
import com.xh.website.module.person.repository.PersonRepository;
//import com.sun.org.apache.regexp.internal.REUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author xinghao
 * @descreption
 * @date 2018/12/19
 */

@Controller
@RequestMapping("/person")
public class PersonController {
    private final
    PersonRepository personRepository;
    private final
    PaperRepository paperRepository;

    @Autowired
    public PersonController(PersonRepository personRepository, PaperRepository paperRepository) {
        this.personRepository = personRepository;
        this.paperRepository = paperRepository;
    }

    @GetMapping("/personlist")
    public String getAll(
            Model model) {
        //Pageable pageable = PageRequest.of(page, 6, Sort.Direction.DESC, "id");
        //Page<Person> personPage = personRepository.findAll(pageable);
        List<Person> personList = personRepository.findAll();
        model.addAttribute("personList", personList);
        return "personlist";
    }

    @GetMapping("/person")
    public String getOne(@RequestParam int id, @RequestParam(defaultValue = "all") String year, Model model) {
        Optional<Person> personOptional = personRepository.findById(id);
        if (!personOptional.isPresent()) {
            return "error";
        } else {
            Person person = personOptional.get();
            model.addAttribute("person", person);

            List<Map<String, Object>> countByYear = personRepository.findCountByDate(id);
            List<Paper> papers = paperRepository.findByPerson(id);
            model.addAttribute("countByYear", countByYear);
            model.addAttribute("year", year);
            model.addAttribute("papers", papers);
            return "person";
        }
    }

    @ResponseBody
    @GetMapping("/personpage")
    public Page<Person> getPersonPage(@RequestParam(defaultValue = "0") int personPageNow) {
        Pageable pageable = PageRequest.of(personPageNow, 6, Sort.Direction.DESC, "id");
        return personRepository.findAll(pageable);
    }

/*    private Page<Person> checkPage(Pageable pageable) {
        Page<Person> personPage = personRepository.findAll(pageable);

        return personPage;
    }*/
}
