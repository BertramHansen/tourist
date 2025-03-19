package com.example.touristguide.Controller;

import com.example.touristguide.model.AttractionCity;
import com.example.touristguide.model.AttractionTags;
import com.example.touristguide.model.TouristAttraction;
import com.example.touristguide.service.TouristService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/attractions")
public class TouristController {
    private final TouristService touristService;


    public TouristController(TouristService touristService) {
        this.touristService = touristService;
    }

    @GetMapping("")
    public String getAllAttractions() {
        return "index";
    }

    @GetMapping("/{name}")
    public String getAttractionByName(@PathVariable String name, @RequestParam(required = false) String description, Model model) {
        TouristAttraction touristAttraction = touristService.findAttractionByName(name, description);
        model.addAttribute("touristAttraction", touristAttraction);
        model.addAttribute("name", touristAttraction.getName());
        model.addAttribute("description", touristAttraction.getDescription());
        return "details";
    }

    @GetMapping("/{name}/tags")
    public String getAttractionTagsByName(@PathVariable String name, Model model) {
        List<AttractionTags> tags = touristService.findAttractionByName(name).getTags();
        model.addAttribute("attractionName", touristService.findAttractionByName(name).getName());
        model.addAttribute("tags", tags);
        return "tagList";
    }

    @GetMapping("/all")
    public String viewAllAttractions(Model model) {
       TouristAttraction attractions = touristService.getAllAttractions();
        model.addAttribute("attractions", attractions);
        return "attractionsList";
    }


    //Add function
    @GetMapping("/add")
    public String showAddForm(Model model) {
        TouristAttraction touristAttraction = new TouristAttraction();
        model.addAttribute("touristAttraction", touristAttraction);
        model.addAttribute("tags", AttractionTags.values());
        model.addAttribute("cities", AttractionCity.values());
        return "add-form";
    }

    @PostMapping("/save")
    public String addAttraction(@ModelAttribute TouristAttraction touristAttraction) {
        touristService.addAttraction(touristAttraction);
        return "redirect:/attractions/all";
    }

    @PostMapping("/delete/{name}")
    public String deleteAttraction(@PathVariable String name) {
        touristService.deleteAttraction(name);
        return  "redirect:/attractions/all";
    }


    //------------------------------------UPDATE MAPPINGS:-----------------------------------------------------------
    /*
    @PostMapping("/update/{name}")
    public ResponseEntity<String> updateAttraction(@PathVariable String name,
                                                   @RequestBody TouristAttraction newAttraction){
        //Hello
        String returnMessage = touristService.updateAttraction(name, newAttraction);
        System.out.println("We in the postmapping");
        return new ResponseEntity<>(returnMessage, HttpStatus.CREATED);
    }
     */

    @PostMapping("/update/{name}")
    public String updateAttraction(@PathVariable String name,
                                   Model model,
                                   @ModelAttribute("attraction") TouristAttraction attraction){
        touristService.updateAttraction(name, attraction);
        return "redirect:/attractions/all";
    }

    @GetMapping("/{name}/edit")
    public String editForm(@PathVariable String name,
                           Model model) {

        //test code, replace with the actual attraction the user clicked on
        /*
        List<AttractionTags> tags = List.of(AttractionTags.BOERNEVENLIG, AttractionTags.SHOPPING);
        TouristAttraction attraction = new TouristAttraction(
                "Fuji-Q Highland",
                "An amusement park near mount fuji",
                tags,
                AttractionCity.KAWASAKI);

         */
        TouristAttraction attraction = touristService.findAttractionByName(name);
        model.addAttribute("attraction", attraction);
        model.addAttribute("tags", AttractionTags.values());
        model.addAttribute("cities", AttractionCity.values());
        return "edit-form";
    }

}
