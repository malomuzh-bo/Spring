package com.springGroup.rentingDB.Controllers;

import com.springGroup.rentingDB.Models.*;
import com.springGroup.rentingDB.Repo.ApartmentRepository;
import com.springGroup.rentingDB.Repo.ClientRepository;
import com.springGroup.rentingDB.Repo.LandlordRepository;
import com.springGroup.rentingDB.Repo.RentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
public class RentController {
    @Autowired
    RentRepository r_repository;
    @Autowired
    ApartmentRepository a_repository;
    @Autowired
    ClientRepository c_repository;
    @Autowired
    LandlordRepository l_repository;
    @GetMapping("/")
    public String main(Model model){
        Iterable<Apartment> apartments = a_repository.findAll();
        Iterable<Rent> rents = r_repository.findAll();
        Iterable<Client> clients = c_repository.findAll();
        Iterable<Landlord> landlords = l_repository.findAll();

        return "main";
    }
    //----------------------------APARTMENTS
    @GetMapping("/apartments")
    public String apartments(Model model){
        Iterable<Apartment> apartments = a_repository.findAll();
        ArrayList<Apartment> arr = new ArrayList<>();
        List<Integer> list = new ArrayList<>();
        for (Apartment apartment : apartments) {
            arr.add(apartment);
            list.add(apartment.getPrice());
        }
        float avg = avgPrice(list);
        int min = Collections.min(list);
        int max = Collections.max(list);
        model.addAttribute("apartments", arr);
        model.addAttribute("avg", avg);
        model.addAttribute("min", min);
        model.addAttribute("max", max);
        return "apartments/apartments";
    }
    private static float avgPrice(List<Integer> numbers) {
        Integer sum = 0;
        if (!numbers.isEmpty()) {
            for (Integer number : numbers) {
                sum += number;
            }
            return sum.floatValue() / numbers.size();
        }
        return 0;
    }
    @GetMapping("/apartments/add")
    public String addApart(Model model){return "apartments/apartAdd";}
    @PostMapping("/apartments/add")
    public String addApartment(@RequestParam(value = "title")String title,
                               @RequestParam(value = "price")int price, Model model){
        Apartment apartment = new Apartment(title, price);
        a_repository.save(apartment);

        return "redirect:/apartments";
    }
    @GetMapping("/apartments/{id}/edit")
    public String editApart(@PathVariable(value = "id")Long id, Model model){
        if (!a_repository.existsById(id)){
            return "redirect:/apartments";
        }
        Apartment apartment = a_repository.findById(id).get();

        model.addAttribute("apartment", apartment);
        return "apartments/apartEdit";
    }
    @PostMapping("/apartments/{id}/edit")
    public String editingApart(@PathVariable(value = "id")Long id, @RequestParam String title,
                               @RequestParam int price, Model model){
        if (!a_repository.existsById(id)){
            return "redirect:/apartments";
        }
        Apartment apartment = a_repository.findById(id).get();
        apartment.setTitle(title);
        apartment.setPrice(price);

        a_repository.save(apartment);

        return "redirect:/apartments";
    }
    @GetMapping("/apartments/{id}/remove")
    public String removeApart(@PathVariable(value = "id")Long id, Model model){
        if (!a_repository.existsById(id)){
            return "redirect:/apartments";
        }
        Apartment apartment = a_repository.findById(id).get();

        a_repository.delete(apartment);

        return "redirect:/apartments";
    }
    //----------------------------END APARTMENTS

    //----------------------------CLIENTS
    @GetMapping("/clients")
    public String clients(Model model){
        Iterable<Client> clients = c_repository.findAll();
        ArrayList<Client> arr = new ArrayList<>();
        for (Client client : clients) {
            arr.add(client);
        }

        model.addAttribute("clients", arr);
        return "clients/clients";
    }
    @GetMapping("/clients/add")
    public String addClient(Model model){
        Iterable<Apartment> apartments = a_repository.findAll();
        Iterable<Landlord> landlords = l_repository.findAll();
        Iterable<Client> clients = c_repository.findAll();
        ArrayList<Apartment> arr = new ArrayList<>();

        for (Apartment apartment : apartments) {
            boolean is_free = true;
            boolean has_landlord = false;
            for (Client client : clients) {
                if (client.getApartment() == apartment) {
                    is_free = false;
                    break;
                }
            }
            for (Landlord landlord : landlords) {
                if (landlord.getApartment() == apartment) {
                    has_landlord = true;
                    break;
                }
            }
            if (is_free && has_landlord) {
                arr.add(apartment);
            }
        }

        model.addAttribute("apartments", arr);

        return "clients/clientAdd";
    }
    @PostMapping("/clients/add")
    public String addingClient(@RequestParam(value = "username")String username,
                               @RequestParam(value = "pass")String pass,
                               @RequestParam(value = "apartment_id")Long id, Model model){
        if (!a_repository.existsById(id)){
            return "redirect:/clients";
        }
        Apartment apartment = a_repository.findById(id).get();
        Client client = new Client(username, pass, apartment);
        Landlord landlord = apartment.getLandlord();
        Rent rent = new Rent(client, landlord, apartment);

        c_repository.save(client);
        r_repository.save(rent);

        return "redirect:/clients";
    }
    @GetMapping("/clients/{id}/edit")
    public String editClient(@PathVariable(value = "id")Long id, Model model){
        if (!c_repository.existsById(id)){
            return "redirect:/clients";
        }
        Client client = c_repository.findById(id).get();
        Iterable<Apartment> apartments = a_repository.findAll();
        Iterable<Client> clients = c_repository.findAll();
        ArrayList<Apartment> arr = new ArrayList<>();

        for (Apartment apartment : apartments) {
            boolean is_free = true;
            for (Client cl : clients) {
                if (cl.getApartment() == apartment) {
                    is_free = false;
                    break;
                }
            }
            if (is_free) {
                arr.add(apartment);
            }
        }

        model.addAttribute("apartments", arr);

        model.addAttribute("client", client);
        return "clients/clientEdit";
    }
    @PostMapping("/clients/{id}/edit")
    public String editingClient(@PathVariable(value = "id")Long id,
                                @RequestParam(value = "username")String username,
                                @RequestParam(value = "pass")String pass,
                                @RequestParam(value = "apartment_id")Long apartment_id, Model model){
        if (!c_repository.existsById(id)){
            return "redirect:/clients";
        }
        Client client = c_repository.findById(id).get();
        Apartment apartment = a_repository.findById(apartment_id).get();
        client.setUsername(username);
        client.setPass(pass);
        client.setApartment(apartment);

        c_repository.save(client);

        return "redirect:/clients";
    }
    @GetMapping("/clients/{id}/remove")
    public String removeClient(@PathVariable(value = "id")Long id, Model model){
        if (!c_repository.existsById(id)){
            return "redirect:/clients";
        }
        Client client = c_repository.findById(id).get();
        client.setApartment(null);

        c_repository.delete(client);

        return "redirect:/clients";
    }
    @GetMapping("/clients/search")
    public String searchClients(
            @RequestParam(name = "search", required = false) String search,
            Model model
    ) {
        Iterable<Client> clients = c_repository.findAll();

        List<Client> total = new ArrayList<>();
        List<Client> searchByName = new ArrayList<>();
        List<Client> searchByNum = new ArrayList<>();
        List<Client> searchByApart = new ArrayList<>();
        for (Client client : clients) {
            searchByName.add(client);
            searchByNum.add(client);
            searchByApart.add(client);
        }
        searchByName = searchByName.stream().filter(q->q.getUsername().toLowerCase()
                .contains(search.toLowerCase())).toList();
        searchByNum = searchByNum.stream().filter(q->q.getNumber().toLowerCase()
                .contains(search.toLowerCase())).toList();
        searchByApart = searchByApart.stream().filter(q->q.getApartment().getTitle().toLowerCase()
                        .contains(search.toLowerCase()))
                .toList();

        total.addAll(searchByName);
        total.addAll(searchByNum);
        total.addAll(searchByApart);

        model.addAttribute("clients", total);
        return "clients/clientsSearch";
    }
    //----------------------------END CLIENTS

    //----------------------------LANDLORDS
    @GetMapping("/landlords")
    public String landlords(Model model){
        Iterable<Landlord> landlords = l_repository.findAll();
        ArrayList<Landlord> arr = new ArrayList<>();
        for (Landlord landlord : landlords) {
            arr.add(landlord);
        }

        model.addAttribute("landlords", arr);
        return "landlords/landlords";
    }
    @GetMapping("/landlords/add")
    public String addLandlord(Model model){
        Iterable<Apartment> apartments = a_repository.findAll();
        Iterable<Landlord> landlords = l_repository.findAll();
        ArrayList<Apartment> arr = new ArrayList<>();

        for (Apartment apartment : apartments) {
            boolean is_free = true;
            for (Landlord landlord : landlords) {
                if (landlord.getApartment() == apartment) {
                    is_free = false;
                    break;
                }
            }
            if (is_free) {
                arr.add(apartment);
            }
        }

        model.addAttribute("apartments", arr);

        return "landlords/landlordAdd";
    }
    @PostMapping("/landlords/add")
    public String addingLandlord(@RequestParam(value = "username")String username,
                                 @RequestParam(value = "pass")String pass,
                                 @RequestParam(value = "apartment_id")Long id, Model model){
        if (!a_repository.existsById(id)){
            return "redirect:/landlords";
        }
        Apartment apartment = a_repository.findById(id).get();
        Landlord landlord = new Landlord(username, pass, apartment);
        apartment.setLandlord(landlord);
        l_repository.save(landlord);
        a_repository.save(apartment);

        return "redirect:/landlords";
    }
    @GetMapping("/landlords/{id}")
    public String getLandlord(@PathVariable(value = "id")Long id, Model model){
        Iterable<Apartment> apartments = a_repository.findAll();
        Iterable<Landlord> landlords = l_repository.findAll();

        if (!l_repository.existsById(id)){
            return "redirect:/landlords";
        }

        ArrayList<Apartment> arr = new ArrayList<>();

        for (Apartment apartment : apartments) {
            boolean is_free = true;
            for (Landlord landlord : landlords) {
                if (landlord.getApartment() == apartment) {
                    is_free = false;
                    break;
                }
            }
            if (is_free) {
                arr.add(apartment);
            }
        }

        model.addAttribute("apartments", arr);

        Landlord landlord = l_repository.findById(id).get();

        model.addAttribute(landlord);
        return "landlords/landlordGet";
    }
    @PostMapping("/landlords/{id}/addApartment/{apartment_id}")
    public String addApartToLandlord(@PathVariable(value = "id")Long id,
                                     @PathVariable(value = "apartment_id")Long apartment_id, Model model){
        if (!a_repository.existsById(apartment_id) || !l_repository.existsById(id)){
            return "redirect:/landlords";
        }
        Landlord landlord = l_repository.findById(id).get();
        Apartment apartment = a_repository.findById(apartment_id).get();
        Landlord newLandlord = new Landlord(landlord.getUsername(), landlord.getPass(), apartment);

        apartment.setLandlord(newLandlord);
        l_repository.save(newLandlord);
        a_repository.save(apartment);

        return "redirect:/landlords";
    }
    @GetMapping("/landlords/{id}/edit")
    public String editLandlord(@PathVariable(value = "id")Long id, Model model){
        if (!l_repository.existsById(id)){
            return "redirect:/landlords";
        }
        Landlord landlord = l_repository.findById(id).get();
        Iterable<Apartment> apartments = a_repository.findAll();
        Iterable<Landlord> landlords = l_repository.findAll();
        ArrayList<Apartment> arr = new ArrayList<>();

        for (Apartment apartment : apartments) {
            boolean is_free = true;
            for (Landlord l : landlords) {
                if (l.getApartment() == apartment) {
                    is_free = false;
                    break;
                }
            }
            if (is_free) {
                arr.add(apartment);
            }
        }

        model.addAttribute("apartments", arr);

        model.addAttribute("landlord", landlord);
        return "landlords/landlordEdit";
    }
    @PostMapping("/landlords/{id}/edit")
    public String editingLandlord(@PathVariable(value = "id")Long id,
                                @RequestParam(value = "username")String username,
                                @RequestParam(value = "pass")String pass,
                                @RequestParam(value = "apartment_id")Long apartment_id, Model model){
        if (!l_repository.existsById(id)){
            return "redirect:/landlords";
        }
        Landlord landlord = l_repository.findById(id).get();
        Apartment apartment = a_repository.findById(apartment_id).get();
        landlord.setUsername(username);
        landlord.setPass(pass);
        landlord.setApartment(apartment);

        l_repository.save(landlord);

        return "redirect:/landlords";
    }
    @GetMapping("/landlords/{id}/remove")
    public String removeLandlord(@PathVariable(value = "id")Long id, Model model){
        if (!l_repository.existsById(id)){
            return "redirect:/landlords";
        }
        Landlord landlord = l_repository.findById(id).get();
        landlord.setApartment(null);

        l_repository.delete(landlord);

        return "redirect:/landlords";
    }
    //----------------------------END LANDLORDS

    //----------------------------RENTS
    @GetMapping("/rents")
    public String rents(Model model){
        Iterable<Rent> rents = r_repository.findAll();

        ArrayList<Rent> arr = new ArrayList<>();

        for (Rent rent : rents) {
            arr.add(rent);
        }

        model.addAttribute("rents", arr);

        return "rents/rents";
    }
    /*@GetMapping("/rents/{id}/edit")
    public String editRent(@PathVariable(value = "id")Long id, Model model){
        if (!r_repository.existsById(id)){
            return "redirect:/rents";
        }
        Rent rent = r_repository.findById(id).get();
        Iterable<Apartment> apartments = a_repository.findAll();
        Iterable<Client> clients = c_repository.findAll();
        Iterable<Landlord> landlords = l_repository.findAll();
        ArrayList<Apartment> arr = new ArrayList<>();
        ArrayList<Client> arr_clients = new ArrayList<>();

        for (Apartment apartment : apartments) {
            boolean is_free = true;
            boolean has_landlord = false;
            for (Client client : clients) {
                if (client.getApartment() == apartment) {
                    is_free = false;
                    break;
                }
            }
            for (Landlord landlord : landlords) {
                if (landlord.getApartment() == apartment) {
                    has_landlord = true;
                    break;
                }
            }
            if (is_free && has_landlord) {
                arr.add(apartment);
            }
        }
        for (Client cl : clients) {
            if (cl.getApartment() == null) arr_clients.add(cl);
        }

        model.addAttribute("apartments", arr);
        model.addAttribute("clients", arr_clients);
        model.addAttribute("rent", rent);

        return "landlords/landlordEdit";
    }*/
    @GetMapping("/rents/{id}/remove")
    public String removeRent(@PathVariable(value = "id")Long id, Model model){
        if (!r_repository.existsById(id)){
            return "redirect:/rents";
        }
        Rent rent = r_repository.findById(id).get();
        rent.setApartment(null);
        rent.setLandlord(null);

        r_repository.delete(rent);

        return "redirect:/rents";
    }
    //----------------------------END RENTS
}
