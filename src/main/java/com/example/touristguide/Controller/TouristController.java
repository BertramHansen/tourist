package com.example.touristguide.Controller;

import com.example.touristguide.model.AttractionTags;
import com.example.touristguide.model.TouristAttraction;
import com.example.touristguide.service.TouristService;
import org.springframework.boot.Banner;
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

//    @GetMapping("")
//    public ResponseEntity<List<TouristAttraction>> getAllAttractions() {
//        List<TouristAttraction> attractions = touristService.getAllAttractions();
//        return new ResponseEntity<>(attractions, HttpStatus.OK);
//    }

    @GetMapping("")
    public String getAllAttractions(){
        return "index";
    }

    @GetMapping("/{name}")
    public String getAttractionByName(@PathVariable String name, @RequestParam(required = false) String description, Model model)  {
        TouristAttraction touristAttraction = touristService.findAttractionByName(name, description);
        model.addAttribute("touristAttraction", touristAttraction);
        model.addAttribute("name", touristAttraction.getName());
        model.addAttribute("description", touristAttraction.getDescription());
        return "details";
    }

    @GetMapping("/{name}/tags")
    public String getAttractionTagsByName(@PathVariable String name, Model model){
        List<AttractionTags> tags = touristService.findAttractionByName(name).getTags();
        model.addAttribute("tags", tags);
        return "tagList";
    }

    @GetMapping("/all")
    public String viewallAttactions(Model model){

        List<TouristAttraction> attractions = touristService.getAllAttractions();
        model.addAttribute("attractions", attractions);
        return "attractionsList";
    }
    @PostMapping("/add")
    public ResponseEntity<TouristAttraction> addAttraction(@RequestBody TouristAttraction attraction) {
        TouristAttraction newAttraction = touristService.addAttraction(attraction);
        return new ResponseEntity<>(newAttraction, HttpStatus.CREATED);
    }

    @PostMapping("/delete/{name}")
    public ResponseEntity<String> deleteAttraction(@PathVariable String name){
        String returnMessage = touristService.deleteAttraction(name);
        return new ResponseEntity<>(returnMessage, HttpStatus.CREATED);
    }

    @PostMapping("/update/{name}")
    public ResponseEntity<String> updateAttraction(@PathVariable String name,
                                                   @RequestBody TouristAttraction newAttraction){
        //Hello
        String returnMessage = touristService.updateAttraction(name, newAttraction);
        System.out.println("We in the postmapping");
        return new ResponseEntity<>(returnMessage, HttpStatus.CREATED);
    }

}
