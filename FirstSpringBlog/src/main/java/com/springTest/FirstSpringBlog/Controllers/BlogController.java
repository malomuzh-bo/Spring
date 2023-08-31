package com.springTest.FirstSpringBlog.Controllers;

import com.springTest.FirstSpringBlog.Models.Post;
import com.springTest.FirstSpringBlog.Repo.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collections;

@Controller
public class BlogController {
    @Autowired
    PostRepository repository;
    @GetMapping("/blog")
    public String blogMain(Model model){
        Iterable<Post> posts = repository.findAll();

        ArrayList<Post> arr = new ArrayList<>();
        for (Post post : posts) {
            arr.add(post);
        }
        Collections.reverse(arr);
        model.addAttribute("posts", posts);

        return "blogMain";
    }
    @GetMapping("/blog/add")
    public String blogAdd(Model model){
        return "blogAdd";
    }
    @PostMapping("/blog/add")
    public String createPost(@RequestParam String title, @RequestParam String tag, @RequestParam String shortInfo,
                             @RequestParam String longInfo, Model model){
        Post post = new Post(title, tag, shortInfo, longInfo);
        repository.save(post);

        return "redirect:/blog";
    }
    @GetMapping("/blog/{id}")
    public String postById(@PathVariable(value = "id")Long id, Model model){
        if (!repository.existsById(id)){
            return "redirect:/blog";
        }
        Post post = repository.findById(id).get();
        model.addAttribute("post", post);
        return "postInfo";
    }
    @GetMapping("/blog/{id}/remove")
    public String postRemove(@PathVariable(value = "id")Long id, Model model){
        if (!repository.existsById(id)){
            return "redirect:/blog";
        }
        Post post = repository.findById(id).get();
        repository.delete(post);

        return "redirect:/blog";
    }
    @GetMapping("/blog/{id}/edit")
    public String blogEdit(@PathVariable(value = "id") Long id, Model model){
        Post post = repository.findById(id).get();

        model.addAttribute("post", post);
        return "blogEdit";
    }
    @PostMapping("/blog/{id}/edit")
    public String postEdit(@PathVariable(value = "id")Long id, @RequestParam String title, @RequestParam String tag, @RequestParam String shortInfo,
                           @RequestParam String fullInfo, Model model){
        if (!repository.existsById(id)){
            return "redirect:/blog";
        }
        Post post = repository.findById(id).get();
        post.setTitle(title);
        post.setTag(tag);
        post.setShortInfo(shortInfo);
        post.setFullInfo(fullInfo);

        repository.save(post);

        return "redirect:/blog";
    }
    //edit: repository.save()
}
