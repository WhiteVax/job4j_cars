package ru.job4j.cars.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.cars.model.*;
import ru.job4j.cars.service.CarService;
import ru.job4j.cars.service.DriverService;
import ru.job4j.cars.service.EngineService;
import ru.job4j.cars.service.PostService;

import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.HashSet;

import static ru.job4j.cars.util.UserSession.getUser;

@Slf4j
@Controller
public class PostController {

    private final PostService postService;
    private final CarService carService;
    private final EngineService engineService;

    public PostController(PostService postService, CarService carService, EngineService engineService) {
        this.postService = postService;
        this.carService = carService;
        this.engineService = engineService;
    }

    @GetMapping("/")
    public String index(Model model, HttpSession session) {
        model.addAttribute("user", getUser(session));
        return "post/all";
    }

    @GetMapping("/all")
    public String getAll(Model model, HttpSession session) {
        model.addAttribute("posts", postService.getAll());
        model.addAttribute("user", getUser(session));
        return "post/all";
    }

    @GetMapping("/add")
    public String addPost(Model model, HttpSession session) {
        model.addAttribute("user", getUser(session));
        model.addAttribute("engines", engineService.getAll());
        return "post/addPost";
    }

    @PostMapping("/create")
    public String createPost(@ModelAttribute("post") Post post, @RequestParam("brand") String brand,
                             @ModelAttribute("engineId") int engineId, @RequestParam("file") MultipartFile file,
                             @RequestParam("driverName") String driverName, @RequestParam("driverSurname") String driverSurname,
                             HttpSession session) {
        try {
            var user = getUser(session);
            var drive = new Driver(0, driverName, driverSurname, new ArrayList<>());
            var car = new Car(0, brand, engineService.getById(engineId).get(), drive, new HashSet<>());
            carService.add(car);
            post.setCar(car);
            post.setPhoto(file.getBytes());
            post.setUser(user);
            postService.create(post);
        } catch (Exception e) {
            log.error("Error in createPost.", e);
            return "redirect:/fail";
        }
        return "redirect:/all";
    }

    @GetMapping("/{id}")
    public String formPost(Model model, HttpSession session, @PathVariable("id") int id) {
        var post = postService.getById(id);
        if (post.isEmpty()) {
            return "redirect:/fail";
        }
        model.addAttribute("post", postService.getById(id).get());
        model.addAttribute("user", getUser(session));
        return "post/post";
    }

    @GetMapping("/delete/{postId}")
    public String delete(@PathVariable("postId") int id) {
        var post = postService.getById(id);
        if (post.isEmpty()) {
            return "redirect:/fail";
        }
        post.ifPresent(postService::deletePost);
        return "redirect:/all";
    }

    @GetMapping("/completed/{postId}")
    public String completedPost(@PathVariable("postId") int id) {
        postService.completedPost(id);
        return "redirect:/all";
    }

    @GetMapping("/update/{postId}")
    public String updatePost(HttpSession session, Model model, @PathVariable int postId) {
        var post = postService.getById(postId);
        if (post.isEmpty()) {
            return "redirect:/fail";
        }
        model.addAttribute("user", getUser(session));
        model.addAttribute("engines", engineService.getAll());
        model.addAttribute("post", post.get());
        return "post/update";
    }

    @PostMapping("/updatePost")
    public String updatePost(@ModelAttribute Post post, @ModelAttribute("engineId") int engineId,
                             @RequestParam("file") MultipartFile file, @ModelAttribute Car car,
                             @RequestParam(name = "car.name", required = false, defaultValue = "unknown") String brand,
                             @RequestParam(name = "car.driver.name", required = false, defaultValue = "unknown") String drName,
                             @RequestParam(name = "car.driver.surname", required = false, defaultValue = "unknown") String drSurname,
                             @RequestParam(name = "car.id") int carId, @RequestParam(name = "car.driver.id") int drId,
                             HttpSession session) {
        try {
            var user = getUser(session);
            car.setName(brand);
            var driver = Driver.builder().id(drId).name(drName).surname(drSurname).build();
            car.setDriver(driver);
            var engine = engineService.getById(engineId);
            car.setEngine(engine.get());
            car.setId(carId);
            carService.update(car);
            post.setCar(car);
            post.setUser(user);
            post.setPhoto(file.getBytes());
            postService.update(post);
        } catch (Exception e) {
            log.error("Error in updatePost.", e);
            return "redirect:/fail";
        }
        return "redirect:/all";
    }

    @GetMapping("/last")
    public String getLastPosts(Model model, HttpSession session) {
        model.addAttribute("user", getUser(session));
        model.addAttribute("posts", postService.getAllByLastDay());
        return "post/all";
    }

    @GetMapping("/photos")
    public String getPostsWithPhoto(Model model, HttpSession session) {
        model.addAttribute("user", getUser(session));
        model.addAttribute("posts", postService.getAllWithPhoto());
        return "post/all";
    }

    @GetMapping("/brand")
    public String getPostsByBrand(Model model, HttpSession session,
                                  @RequestParam("carBrand") String brand) {
        model.addAttribute("user", getUser(session));
        model.addAttribute("posts", postService.getAllWithByBrand(brand));
        return "post/all";
    }
}