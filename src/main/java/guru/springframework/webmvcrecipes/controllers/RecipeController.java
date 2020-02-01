package guru.springframework.webmvcrecipes.controllers;

import guru.springframework.webmvcrecipes.commands.RecipeCommand;
import guru.springframework.webmvcrecipes.services.ImageService;
import guru.springframework.webmvcrecipes.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Controller
@RequestMapping("/recipe")
public class RecipeController {
    private RecipeService recipeService;
    private ImageService imageService;
    private ServletContext servletContext;
    private ResourceLoader resourceLoader;

    @Value("classpath:/static/images/guacamole400x400WithX.jpg")
    private Resource defaultImage;

    public RecipeController(RecipeService recipeService, ImageService imageService,
                            ServletContext servletContext, ResourceLoader resourceLoader) {
        this.recipeService = recipeService;
        this.imageService = imageService;
        this.servletContext = servletContext;
        this.resourceLoader = resourceLoader;
    }

    @RequestMapping("/{id}/show")
    public String getRecipeById(@PathVariable String id, Model model) {
        Long longID = Long.valueOf(id);
        model.addAttribute("recipe", this.recipeService.findById(longID));

        return "recipe/show";
    }

    @GetMapping(value = "/new")
    public String newRecipe(Model model) {
        model.addAttribute("recipe", new RecipeCommand());

        return "recipe/recipeform";
    }

    @GetMapping(value = "/{id}/update")
    public String updateRecipe(@PathVariable String id,  Model model) {
        model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));

        return "recipe/recipeform";
    }

    @PostMapping(name = "/")
    public String saveOrUpdate(@ModelAttribute RecipeCommand command) {
        RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);

        return "redirect:/recipe/" + savedCommand.getId() + "/show";
    }

    @GetMapping(value = "/{id}/delete")
    public String deleteRecipe(@PathVariable String id) {

        recipeService.deleteById(Long.valueOf(id));
        log.debug("Deleting recipe " + id);
        return "redirect:/";
    }

    @GetMapping("/{id}/image")
    public String showUploadForm(@PathVariable String id, Model model) {
        //model.addAttribute("recipe", recipeService.findById(Long.valueOf(id)));
        model.addAttribute("id", id);
        return "recipe/imageuploadform";
    }

    @PostMapping("/{id}/image")
    public String handleImagePost(@PathVariable String id, @RequestParam("imagefile")MultipartFile file) {
        imageService.saveImageFile(Long.valueOf(id), file);

        return "redirect:/recipe/" + id + "/show";
    }

    @GetMapping("{id}/recipeimage")
    public void renderImageFromDb(@PathVariable String id, HttpServletResponse response) throws IOException {
        RecipeCommand recipeCommand = recipeService.findCommandById(Long.valueOf(id));

        byte[] byteArray = recipeService.getImageByteArray(recipeCommand.getImage());
        response.setContentType("image/jpeg");
        if (byteArray != null) {
            InputStream is = new ByteArrayInputStream(byteArray);
            IOUtils.copy(is, response.getOutputStream());
        } else {

            //Below is an alternative way of loading a Resource from the class path
            //Here we used the @Value annotation with classpath:
            //InputStream is = resourceLoader.getResource("classpath:/static/images/guacamole400x400WithX.jpg").getInputStream();

            InputStream is = defaultImage.getInputStream();
            IOUtils.copy(is, response.getOutputStream());
        }
    }
}
