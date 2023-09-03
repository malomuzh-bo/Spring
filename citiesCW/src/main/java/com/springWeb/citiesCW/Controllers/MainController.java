package com.springWeb.citiesCW.Controllers;

import com.springWeb.citiesCW.Models.City;
import com.springWeb.citiesCW.Repo.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class MainController {
    @Autowired
    CityRepository repository;
    @GetMapping("/")
    public String main(Model model){
        Iterable<City> cities = repository.findAll();

        ArrayList<City> arr = new ArrayList<>();
        for (City city : cities) {
            arr.add(city);
        }

        model.addAttribute("cities", arr);
        return "main";
    }
    @GetMapping("/add")
    public String addCity(Model model){
        return "cityAdd";
    }
    @PostMapping("/add")
    public String createCity(@RequestParam String cityName, @RequestParam int amo,
                             @RequestParam String info, @RequestParam long latitude,
                             @RequestParam long longitude, Model model){
        City city = new City(cityName, amo, info, latitude, longitude);
        repository.save(city);

        return "redirect:/";
    }
    @GetMapping("/{id}")
    public String cityInfo(@PathVariable(value = "id")Long id, Model model){
        if (!repository.existsById(id)){
            return "redirect:/";
        }
        City city = repository.findById(id).get();
        model.addAttribute(city);
        return "cityInfo";
    }
    @GetMapping("/{id}/remove")
    public String cityRemove(@PathVariable(value = "id")Long id, Model model){
        if (!repository.existsById(id)){
            return "redirect:/";
        }
        City city = repository.findById(id).get();
        repository.delete(city);

        return "redirect:/";
    }
    @GetMapping("/{id}/edit")
    public String cityEdit(@PathVariable(value = "id")Long id, Model model){
        City city = repository.findById(id).get();

        model.addAttribute("city", city);
        return "cityEdit";
    }
    @PostMapping("/{id}/edit")
    public String cityEditing(@PathVariable(value = "id")Long id, @RequestParam String cityName,
                              @RequestParam int amo, @RequestParam String info,
                              @RequestParam long latitude, @RequestParam long longitude, Model model){
        if (!repository.existsById(id)){
            return "redirect:/";
        }
        City city = repository.findById(id).get();
        city.setCityName(cityName);
        city.setPersonAmo(amo);
        city.setInfo(info);
        city.setLatitude(latitude);
        city.setLongitude(longitude);

        repository.save(city);

        return "redirect:/";
    }
    @GetMapping("/search")
    public String searchCities(
            @RequestParam(name = "search", required = false) String search,
            Model model
    ) {
        Iterable<City> cities = repository.findAll();

        List<City> total = new ArrayList<>();
        List<City> searchByName = new ArrayList<>();
        List<City> searchByAmo = new ArrayList<>();
        List<City> searchByInfo = new ArrayList<>();
        for (City city : cities) {
            searchByName.add(city);
            searchByAmo.add(city);
            searchByInfo.add(city);
        }
        searchByName = searchByName.stream().filter(q->q.getCityName().toLowerCase()
                .contains(search.toLowerCase())).toList();
        searchByAmo = searchByAmo.stream().filter(q->q.getPersonAmo().toString()
                        .contains(search.toString()))
                .toList();
        searchByInfo = searchByInfo.stream().filter(q->q.getInfo().toLowerCase().contains(search.toLowerCase()))
                .toList();

        total.addAll(searchByName);
        total.addAll(searchByAmo);
        total.addAll(searchByInfo);

        model.addAttribute("cities", total);
        return "search";
    }
}
