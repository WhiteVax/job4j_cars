package ru.job4j.cars.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.cars.model.*;
import ru.job4j.cars.service.EngineService;
import ru.job4j.cars.service.PostService;

import javax.servlet.http.HttpSession;

import java.util.Optional;

import static ru.job4j.cars.util.UserSession.getUser;

@Slf4j
@Controller
public class PostController {

    private final PostService postService;

    private final EngineService engineService;

    public PostController(PostService postService, EngineService engineService) {
        this.postService = postService;
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
                             HttpSession session, Model model) {
        post.setUser(getUser(session));
        var drive = Driver.builder().name(driverName).surname(driverSurname).build();
        var car = Car.builder().name(brand).driver(drive).build();
        if (!postService.create(post, car, file, engineId)) {
            model.addAttribute("message", "Error when try create post.");
            return "redirect:/fail";
        }
        return "redirect:/all";
    }

    @GetMapping("/post")
    public String formPost(Model model, HttpSession session, @RequestParam("id") int id) {
        var post = postService.getById(id);
        if (post.isEmpty()) {
            model.addAttribute("message", String.format("Not found post with this id = %s.", id));
            return "redirect:/fail";
        }
        model.addAttribute("post", postService.getById(id).get());
        model.addAttribute("user", getUser(session));
        return "post/post";
    }

    @GetMapping("/delete/{postId}")
    public String delete(@PathVariable("postId") int id, Model model) {
        var post = postService.getById(id);
        if (post.isEmpty()) {
            model.addAttribute("message", String.format("Not found post with this id = %s.", id));
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

    @GetMapping("/update/{id}")
    public String viewUpdate(HttpSession session, Model model, @PathVariable("id") int id) {
        var post = postService.getById(id);
        if (post.isEmpty()) {
            model.addAttribute("message", String.format("Not found post with this id = %s.", id));
            return "redirect:/fail";
        }
        model.addAttribute("user", getUser(session));
        model.addAttribute("engines", engineService.getAll());
        model.addAttribute("post", post.get());
        return "post/update";
    }

    @PostMapping("/updatePost")
    public String update(@ModelAttribute Post post, @ModelAttribute("engineId") int engineId,
                         @RequestParam("file") MultipartFile file, @ModelAttribute Car car,
                         @RequestParam(name = "car.name", required = false, defaultValue = "unknown") String brand,
                         @RequestParam(name = "car.driver.name", required = false, defaultValue = "unknown") String drName,
                         @RequestParam(name = "car.driver.surname", required = false, defaultValue = "unknown") String drSurname,
                         @RequestParam(name = "car.id") int carId, @RequestParam(name = "car.driver.id") int drId,
                         HttpSession session, Model model) {
        var driver = Driver.builder().id(drId).name(drName).surname(drSurname).build();
        car.setId(carId);
        car.setDriver(driver);
        car.setName(brand);
        post.setUser(getUser(session));
        if (!postService.update(post, engineId, file, car)) {
            model.addAttribute("message", "Error in updatePost.");
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
        model.addAttribute("posts", postService.getPostsWithPhoto());
        return "post/all";
    }

    @GetMapping("/brand")
    public String getPostsByBrand(Model model, HttpSession session,
                                  @RequestParam("carBrand") String brand) {
        model.addAttribute("user", getUser(session));
        model.addAttribute("posts", postService.getAllWithByBrand(brand));
        return "post/all";
    }

    @GetMapping("/photoCar/{id}")
    public ResponseEntity<?> getCarPhotoByPostId(@PathVariable int id) {
        Optional<Post> postOptional = postService.getById(id);
        if (postOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var post = postOptional.get();
        byte[] image = post.getPhoto();
        return ResponseEntity.ok(image);
    }
}